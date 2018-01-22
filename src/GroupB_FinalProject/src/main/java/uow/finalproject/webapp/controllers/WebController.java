package uow.finalproject.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
	@RequestMapping(value= {"/", "index"})
	public String index() {
		return "index";
	}
	
	@RequestMapping(value= {"/index_login"})
	public String index_login() {
		return "index_login";
	}
	
	@RequestMapping(value= {"/register"})
	public String register() {
		return "register";
	}
	
	@RequestMapping(value= {"/recommendation"})
	public String recommendation() {
		return "recommendation";
	}
	
	@RequestMapping(value= {"/prod_list"})
	public String prod_list() {
		return "prod_list";
	}
	
	@RequestMapping(value= {"/member_person"})
	public String member_person() {
		return "member_person";
	}
	
	@RequestMapping(value= {"/member_center"})
	public String member_center() {
		return "member_center";
	}
	
	@RequestMapping(value= {"/member_index"})
	public String member_index() {
		return "member_index";
	}
}
