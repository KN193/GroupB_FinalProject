package uow.finalproject.webapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uow.finalproject.webapp.dao.CommentDAO;
import uow.finalproject.webapp.dao.FavoriteDAO;
import uow.finalproject.webapp.dao.MessageDAO;
import uow.finalproject.webapp.dao.UserDAO;
import uow.finalproject.webapp.entity.Message;
import uow.finalproject.webapp.entity.Service;
import uow.finalproject.webapp.entity.User;
import uow.finalproject.webapp.entityType.Nationality;
import uow.finalproject.webapp.utility.PasswordGenerator;

@Controller
public class MessageController {

	private User usr;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	FavoriteDAO favoriteDAO;
	
	@Autowired
	MessageDAO messageDAO;
	
	@Autowired
	CommentDAO commentDAO;
	
	@Autowired
	PasswordGenerator passwordGenerator;
    
    @RequestMapping(value="/member_gbook", method=RequestMethod.GET)
    public ModelAndView member_gbook() {
    		ModelAndView modelAndView = initializeLoggedUser("member_gbook");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		ArrayList<Message> messages = new ArrayList<Message>();
    		messages = messageDAO.findAllMessageByReceiver(usr.getEmail());

    		initializeUserList(modelAndView);
    		modelAndView.addObject("messages", messages);
    		
		return modelAndView;
    }
    
    @RequestMapping(value="/member_gbook_detail", method=RequestMethod.GET)
    public ModelAndView member_gbook_detail(@RequestParam(value="userName", required=true) String senderName) {
    		ModelAndView modelAndView = initializeLoggedUser("member_gbook_detail");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		ArrayList<Message> messages = new ArrayList<Message>();
    		messages = messageDAO.findMessageBySenderAndReceiver(senderName, usr.getEmail());
    		Collections.sort(messages);;
    		
    		modelAndView.addObject("messages", messages);
    		modelAndView.addObject("crrUser", usr.getEmail());
    		modelAndView.addObject("sender", senderName);
    		
		return modelAndView;
    }
    
    @RequestMapping(value="/insertMsg", method=RequestMethod.POST)
    public ModelAndView insertMsg(@RequestParam(value="reply", required=true) String reply,
    								@RequestParam(value="sender", required=true) String sender) {
    		Message insert =  new Message(1, userDAO.findUser(usr.getEmail()), userDAO.findUser(sender), reply, new Date(), false);
    		messageDAO.insertNewMessage(insert);
    	
		return member_gbook_detail(sender);
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
    
    private ModelAndView initializeUserList(ModelAndView modelAndView) {
		List<Nationality> nationsSet = new ArrayList<Nationality>(EnumSet.allOf(Nationality.class));
		ArrayList<User> users = userDAO.findAllUser();
		modelAndView.addObject("userSet", users);
		return modelAndView;
}
    
}
