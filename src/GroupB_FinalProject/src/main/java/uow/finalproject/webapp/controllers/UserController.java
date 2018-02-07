package uow.finalproject.webapp.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import uow.finalproject.webapp.entityType.Nationality;
import uow.finalproject.webapp.entityType.Suburb;
import uow.finalproject.webapp.utility.PasswordGenerator;

@Controller
public class UserController {

	// logged user
	private User usr;

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	ServiceDAO serviceDAO;
	
	@Autowired
	PasswordGenerator passwordGenerator;
	
	@RequestMapping(value="/member_index", method=RequestMethod.GET)
    public ModelAndView loginUser() {
		
		ModelAndView modelAndView = initializeLoggedUser("member_index");
		
		if (usr == null) {
			return modelAndView;
		}
		
		ArrayList<Service>  arr = serviceDAO.findNumberOfService(8);
		modelAndView.addObject("services", arr);
		
		return modelAndView;
    }
	
	@RequestMapping(value="/member_center", method=RequestMethod.GET)
    public ModelAndView member_center() {
		
		ModelAndView modelAndView = initializeLoggedUser("member_center");
		
		if (usr == null) {
			return modelAndView;
		}
		
		return modelAndView;
    }
	
	@RequestMapping(value="/member_person", method=RequestMethod.GET)
    public ModelAndView member_person() {
		
		ModelAndView modelAndView = initializeLoggedUser("member_person");
		
		if (usr == null) {
			return modelAndView;
		}
		
		List<Nationality> nationsSet = new ArrayList<Nationality>(EnumSet.allOf(Nationality.class));
		modelAndView.addObject("nation", usr.getNationality());
		modelAndView.addObject("nationsSet", nationsSet);
		
		return modelAndView;
    }
	
	@RequestMapping(value="/register_firstStep", method=RequestMethod.POST)
    public ModelAndView register_firstStep(@RequestParam(value="email", required=true) String email,@RequestParam(value="register", required=true) boolean register) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!register && auth.getName().equals("anonymousUser")) {
			modelAndView.setViewName("index");
		} else {
			List<Nationality> nationsSet = new ArrayList<Nationality>(EnumSet.allOf(Nationality.class));
			modelAndView.addObject("userName", email);
			modelAndView.addObject("nation", Nationality.Australian);
			modelAndView.addObject("nationsSet", nationsSet);
			modelAndView.setViewName("member_person");
		}
		
		return modelAndView;
    }

    @RequestMapping(value="/registerNewUser", method=RequestMethod.POST)
    public String registerNewUser(@RequestParam(value="userName", required=true) String userName
    								,@RequestParam(value="name", required=true) String name
    								,@RequestParam(value="zipCode", required=false) int zipCode
    								,@RequestParam(value="suburb", required=false) Suburb suburb
    								,@RequestParam(value="nation", required=false) Nationality nation
    								,@RequestParam(value="DOB", required=false ) @DateTimeFormat(pattern = "yyyy-mm-dd") Date DOB) {
    		String generatedPassword = passwordGenerator.generateNewPassword();
    		System.out.println(generatedPassword);
    		
        User registeredUser = new User(name,name,name,userName, generatedPassword, zipCode, suburb, nation);
        registeredUser.setDOB(DOB);
    		if (userDAO.registerNewUser(registeredUser)) {
    			return "index";
    		} 
    		
    		return "fail";
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
