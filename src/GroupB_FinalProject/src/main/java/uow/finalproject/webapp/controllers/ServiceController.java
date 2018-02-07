package uow.finalproject.webapp.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uow.finalproject.webapp.dao.ServiceDAO;
import uow.finalproject.webapp.dao.UserDAO;
import uow.finalproject.webapp.entity.Service;
import uow.finalproject.webapp.entity.User;
import uow.finalproject.webapp.utility.PasswordGenerator;

@Controller
public class ServiceController {

	// logged user
	private User usr;
	private int NUMBER_OF_SERVICE_ON_PAGE=9;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	ServiceDAO serviceDAO;
	
	@Autowired
	PasswordGenerator passwordGenerator;
    
    @RequestMapping(value="/new_service", method=RequestMethod.GET)
    public ModelAndView new_service(@RequestParam(value="crrPage", required=false) Integer crrPage) {
    		ModelAndView modelAndView = initializeLoggedUser("new_service");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		
    		ArrayList<Service>  arr = serviceDAO.findAllService();
    		
    		// Pagination
    		if (arr.isEmpty()) {
    			return modelAndView;
    		}

    		int totalService = arr.size(); 
    		int totalPage = 0;
    		
    		// No need to paginating 
    		if (totalService < NUMBER_OF_SERVICE_ON_PAGE) {
    			modelAndView.addObject("services", arr);
    			return modelAndView;
    		}
    		
    		if (totalService%NUMBER_OF_SERVICE_ON_PAGE != 0) {
    			totalPage = totalService/NUMBER_OF_SERVICE_ON_PAGE +1;
    		} else {
    			totalPage = totalService/NUMBER_OF_SERVICE_ON_PAGE;
    		}
    	
    		if (crrPage == null) crrPage = 1;
    		// calculate start pos and end pos
    		int startPos = 0, endPos = 0;
    		
    		startPos = (crrPage-1)*NUMBER_OF_SERVICE_ON_PAGE;
    		endPos = crrPage*NUMBER_OF_SERVICE_ON_PAGE;
    		
    		if (startPos < 0) startPos = 0; // for the beginning of array
    		if (endPos > totalService) endPos = totalService;
    		
    		ArrayList<Service> paginationArr = new ArrayList<Service> (arr.subList(startPos, endPos) );
    		ArrayList<Integer> pageNum = new ArrayList<Integer>();
    		for (int i = 1; i <= totalPage; i++) {
    			pageNum.add(i);
    		}
    		
    		modelAndView.addObject("crrPage", crrPage);
    		modelAndView.addObject("totalPage", totalPage);
    		modelAndView.addObject("services", paginationArr);
    		modelAndView.addObject("pageNum", pageNum);
    		
		return modelAndView;
    }
    
    private ModelAndView initializeLoggedUser(String viewName) {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView modelAndView = new ModelAndView();
		if (auth.getName().equals("anonymousUser")) {
			modelAndView.setViewName("index");
			return modelAndView;
		}
		
		
		String loggedUser = auth.getName();
		usr = userDAO.findUser(loggedUser);
		
		modelAndView.addObject("userName", loggedUser);
		modelAndView.addObject("name", usr.getFirstName());
		modelAndView.setViewName(viewName);
		return modelAndView;
    }
}
