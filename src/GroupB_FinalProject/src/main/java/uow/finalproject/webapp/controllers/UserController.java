package uow.finalproject.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uow.finalproject.webapp.dao.UserDAO;
import uow.finalproject.webapp.entity.User;
import uow.finalproject.webapp.entityType.Suburb;
import uow.finalproject.webapp.utility.PasswordGenerator;

@Controller
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	PasswordGenerator passwordGenerator;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
    public ModelAndView loginUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userName", auth.getName());
		modelAndView.setViewName("member_index");
		return modelAndView;
    }
	
    @RequestMapping(value="/registerNewUser", method=RequestMethod.POST)
    public String registerNewUser(@RequestParam(value="email", required=true) String email) {
    		String generatedPassword = passwordGenerator.generateNewPassword();
    		System.out.println(generatedPassword);
    		
        User registeredUser = new User("nickname","fname","lname",email, generatedPassword, 111, Suburb.NSW, "link");
    		if (userDAO.registerNewUser(registeredUser)) {
    			return "member_person";
    		} 
    		
    		return "fail";
    }
}
