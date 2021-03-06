package uow.finalproject.webapp.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import uow.finalproject.webapp.dao.CommentDAO;
import uow.finalproject.webapp.dao.ServiceDAO;
import uow.finalproject.webapp.dao.UserDAO;
import uow.finalproject.webapp.entity.Address;
import uow.finalproject.webapp.entity.Comment;
import uow.finalproject.webapp.entity.Purchased;
import uow.finalproject.webapp.entity.Service;
import uow.finalproject.webapp.entity.User;
import uow.finalproject.webapp.entityType.Nationality;
import uow.finalproject.webapp.entityType.Suburb;
import uow.finalproject.webapp.utility.EmailService;
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
	CommentDAO commentDAO;
	
	@Autowired
	PasswordGenerator passwordGenerator;
	
	@Autowired
	EmailService emailService;
	
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
		
		
		modelAndView.addObject("user", usr);
		modelAndView.addObject("nation", usr.getNationality());
		
		return modelAndView;
    }
	
	@RequestMapping(value="/member_purchase_records", method=RequestMethod.GET)
    public ModelAndView member_purchase_records() {
		
		ModelAndView modelAndView = initializeLoggedUser("member_purchase_records");
		
		
		if (usr == null) {
			return modelAndView;
		}
		
		HashMap<Purchased, Service>  map = serviceDAO.findPurchasedService(usr.getEmail());
		modelAndView.addObject("purchasedServices", map);

		return modelAndView;
    }
	
	@RequestMapping(value="/member_add_comment", method=RequestMethod.GET)
    public ModelAndView member_add_comment(@RequestParam(value="serviceID", required=true) int serviceID) {
		
		ModelAndView modelAndView = initializeLoggedUser("member_add_comment");
		
		
		if (usr == null) {
			return modelAndView;
		}
		
		modelAndView.addObject("serviceID", serviceID);

		return modelAndView;
    }
	
	@RequestMapping(value="/member_add_comment", method=RequestMethod.POST)
    public ModelAndView member_add_comment(@RequestParam(value="serviceID", required=true) int serviceID,
    		@RequestParam(value="content", required=true) String content) {
		
		ModelAndView modelAndView = initializeLoggedUser("member_add_comment");
		
		if (usr == null) {
			return modelAndView;
		}
		
		Comment cmt = new Comment(serviceID, usr, content, true, new Date());
		commentDAO.addNewComment(cmt);
		modelAndView.setViewName("redirect:comment_successfully?serviceID="+serviceID);

		return modelAndView;
    }
	
	@RequestMapping(value="/comment_successfully", method=RequestMethod.GET)
    public ModelAndView comment_successfully(@RequestParam(value="serviceID", required=true) int serviceID) {
		
		ModelAndView modelAndView = initializeLoggedUser("comment_successfully");
		
		if (usr == null) {
			return modelAndView;
		}
		modelAndView.addObject("serviceID", serviceID);

		return modelAndView;
    }
	
	@RequestMapping(value="/member_remark", method=RequestMethod.GET)
    public ModelAndView member_remark() {
		
		ModelAndView modelAndView = initializeLoggedUser("member_remark");
		
		if (usr == null) {
			return modelAndView;
		}
		HashMap<Service, Comment> cmts = commentDAO.findAllCommentByCommenter(usr.getEmail());
		modelAndView.addObject("commentList", cmts);

		return modelAndView;
    }

	@RequestMapping(value="/delete_comment", method=RequestMethod.POST)
    public ModelAndView delete_comment(@RequestParam(value="serviceID", required=true) int serviceID) {
		
		ModelAndView modelAndView = initializeLoggedUser("member_remark");
		
		if (usr == null) {
			return modelAndView;
		}
		
		commentDAO.deleteComment(serviceID);
		modelAndView.setViewName("redirect:member_remark");

		return modelAndView;
    }

	@RequestMapping(value="/register_firstStep", method=RequestMethod.GET)
    public ModelAndView register_firstStep(@RequestParam(value="email", required=true) String email,@RequestParam(value="register", required=true) boolean register) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!register && auth.getName().equals("anonymousUser")) {
			modelAndView.setViewName("index");
		} else {
			modelAndView.addObject("userName", email);
			modelAndView.addObject("nation", Nationality.Australian);
			initializeNationalityList(modelAndView);
			modelAndView.setViewName("register_finalStep");
		}
		
		return modelAndView;
    }

    @RequestMapping(value="/registerNewUser", method=RequestMethod.POST)
    public String registerNewUser(@RequestParam(value="userName", required=true) String userName
    								,@RequestParam(value="name", required=true) String name
    								,@RequestParam(value="zipCode", required=false) int zipCode
    								,@RequestParam(value="suburb", required=false) Suburb suburb
    								,@RequestParam(value="nation", required=false) Nationality nation
    								,@RequestParam(value="telNo", required=false) String telNo
    								,@RequestParam(value="address", required=false) String address
    								,@RequestParam(value="photo", required=false) MultipartFile photo
    								,@RequestParam(value="DOB", required=false ) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DOB) {
    		String generatedPassword = passwordGenerator.generateNewPassword();
    		System.out.println(generatedPassword);
    		String photoPath = savePhoto(photo, name);
    		emailService.sendGeneratedPassword(generatedPassword, userName);
    		
    		Address registeredAddress = new Address("home", nation.getCountryName(), zipCode, suburb, address, "Wollongong", 1, 1);
        User registeredUser = new User(name,name,name,userName, generatedPassword, nation);
        registeredUser.setDOB(DOB);
        registeredUser.setMobile(telNo);
        registeredUser.setAddress(registeredAddress);
        registeredUser.setPhoto(photoPath);
        
    		if (userDAO.registerNewUser(registeredUser)) {
    			return "index";
    		} 
    		
    		return "fail";
    }
    
    @RequestMapping(value="/updateUserProfile", method=RequestMethod.POST)
    public ModelAndView updateUserProfile(@RequestParam(value="userName", required=true) String userName
    								,@RequestParam(value="name", required=true) String name
    								,@RequestParam(value="zipCode", required=false) int zipCode
    								,@RequestParam(value="suburb", required=false) Suburb suburb
    								,@RequestParam(value="nation", required=false) Nationality nation
    								,@RequestParam(value="telNo", required=false) String telNo
    								,@RequestParam(value="address", required=false) String address
    								,@RequestParam(value="photo", required=false) MultipartFile photo
    								,@RequestParam(value="DOB", required=false ) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DOB) {

    		String photoPath = savePhoto(photo, name);
    	
    		Address updatedAddress = new Address("home", usr.getNationality().getCountryName(), zipCode, suburb, address, "Wollongong", 1, 1);
        User updatedUser = new User(name,name,name,userName,nation);
        updatedUser.setDOB(DOB);
        updatedUser.setMobile(telNo);
        updatedUser.setAddress(updatedAddress);
        updatedUser.setPhoto(photoPath);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", updatedUser);
        modelAndView.addObject("userName", updatedUser.getEmail());
		modelAndView.addObject("name", usr.getFirstName());
		initializeNationalityList(modelAndView);
        
    		if (userDAO.updateUser(updatedUser)) {
    			modelAndView.setViewName("redirect:member_person.html");
    			return modelAndView;
    		} 
    		
    		modelAndView.setViewName("fail");
    		return modelAndView;
    }
    
    private String savePhoto(MultipartFile photo, String usrName) {
    	// Handling photo upload
		String dbPath = null;
		String fullPath = null;
		try {
			dbPath = "images/profile/"+usrName+"_ProfilePic.jpg";
			fullPath = "src/main/resources/static/" + dbPath;
			String fileTo = new File(fullPath).getAbsolutePath();
		photo.transferTo(new File(fileTo));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbPath;
	}

	@RequestMapping(value="/updateUserPassword", method=RequestMethod.POST)
    public ModelAndView updateUserPassword(@RequestParam(value="userName", required=true) String userName
    								,@RequestParam(value="pwd", required=true) String pwd
    								,@RequestParam(value="newPwd", required=true) String newPwd
    								,@RequestParam(value="confirmPwd", required=true) String confirmPwd){

        User updatedUser = userDAO.findUser(userName);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", updatedUser);
        modelAndView.addObject("userName", updatedUser.getEmail());
		modelAndView.addObject("name", usr.getFirstName());
		initializeNationalityList(modelAndView);
        
		if (!newPwd.equals(confirmPwd)) {
			modelAndView.setViewName("member_person");
			modelAndView.addObject("newPwdAndConfirmNotMatched", true);
			return modelAndView;
		}
		
		int result = userDAO.changePassword(userName, pwd, newPwd);
    		if (result == 0) {
    			modelAndView.setViewName("redirect:member_person.html");
    			return modelAndView;
    		} else if (result == 1) { // old password is not match
    			modelAndView.setViewName("member_person");
    			modelAndView.addObject("pwdNotMatched", true);
    			return modelAndView;
    		}
    		
    		modelAndView.setViewName("fail");
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
		initializeNationalityList(modelAndView);
		modelAndView.setViewName(viewName);
		return modelAndView;
    }
    
    private ModelAndView initializeNationalityList(ModelAndView modelAndView) {
		List<Nationality> nationsSet = new ArrayList<Nationality>(EnumSet.allOf(Nationality.class));
		modelAndView.addObject("nationsSet", nationsSet);
		return modelAndView;
}
}
