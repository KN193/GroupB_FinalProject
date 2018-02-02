package uow.finalproject.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uow.finalproject.webapp.dao.ServiceDAO;
import uow.finalproject.webapp.dao.UserDAO;
import uow.finalproject.webapp.entity.User;
import uow.finalproject.webapp.utility.PasswordGenerator;

@Controller
public class ServiceController {

	// logged user
	private User usr;

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	ServiceDAO serviceDAO;
	
	@Autowired
	PasswordGenerator passwordGenerator;
    
    @RequestMapping(value="/new_service", method=RequestMethod.GET)
    public ModelAndView new_service() {
    		ModelAndView modelAndView = initializeLoggedUser("new_service");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		
    		
    		
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
