package uow.finalproject.webapp.controllers;

import java.util.ArrayList;
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
import uow.finalproject.webapp.dao.ServiceDAO;
import uow.finalproject.webapp.dao.UserDAO;
import uow.finalproject.webapp.entity.Comment;
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
	CommentDAO commentDAO;
	
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
    
    @RequestMapping(value="/prod_show", method=RequestMethod.GET)
    public ModelAndView prod_show(@RequestParam(value="serviceID", required=true) int serviceID) {
    		ModelAndView modelAndView = initializeLoggedUser("prod_show");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		
    		Service  service = serviceDAO.findServiceByID(serviceID);
    		ArrayList<Comment> comments = commentDAO.findCommentByServiceID(serviceID);
    		
    		if (service != null) {
    			modelAndView.addObject("service", service);
    			modelAndView.addObject("comments", comments);
    		}
    		
		return modelAndView;
    }

    @RequestMapping(value="/buyProduct", method=RequestMethod.GET, params="action=buyOne")
    public ModelAndView buyOne(@RequestParam(value="serviceID", required=true) int serviceID,
    							@RequestParam(value="quantity", required=true) int quantity) {
    		ModelAndView modelAndView = initializeLoggedUser("buyOne");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		
    		Service  service = serviceDAO.findServiceByID(serviceID);
    		User purchasedUsr = userDAO.findUserWithDeliveryAddress(usr.getEmail());
    		usr.getShoppingCart().put(service, quantity);
    		float total = calculateTotalShoppingCart();
    		
    		if (service != null) {
    			modelAndView.addObject("service", service);
    			modelAndView.addObject("purchasedUsr", purchasedUsr);
    			modelAndView.addObject("shoppingCart", usr.getShoppingCart());
    			modelAndView.addObject("total", total);
    		}
    		
		return modelAndView;
    }
    
    @RequestMapping(value="/buyProduct", method=RequestMethod.GET, params="action=cart")
    public ModelAndView cart(@RequestParam(value="serviceID", required=true) int serviceID,
    							@RequestParam(value="quantity", required=true) int quantity) {
    		ModelAndView modelAndView = initializeLoggedUser("shopping");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		
    		Service  service = serviceDAO.findServiceByID(serviceID);
    		User purchasedUsr = userDAO.findUserWithDeliveryAddress(usr.getEmail());
    		if (usr.getShoppingCart().containsKey(service)) {
    			int value = usr.getShoppingCart().get(service);
    			value = value + quantity;
    			usr.getShoppingCart().put(service, value);
    		} else {
    			usr.getShoppingCart().put(service, quantity);
    		}
    		
    		float total = calculateTotalShoppingCart();
    		
    		if (service != null) {
    			modelAndView.addObject("service", service);
    			modelAndView.addObject("purchasedUsr", purchasedUsr);
    			modelAndView.addObject("shoppingCart", usr.getShoppingCart());
    			modelAndView.addObject("total", total);
    		}
    		
		return modelAndView;
    }
    
    @RequestMapping(value="/remove_shoppingCartItem", method=RequestMethod.GET)
    public ModelAndView remove_shoppingCartItem(@RequestParam(value="serviceID", required=true) int serviceID) {
    		ModelAndView modelAndView = initializeLoggedUser("shopping");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		User purchasedUsr = userDAO.findUserWithDeliveryAddress(usr.getEmail());
    		Service  service = serviceDAO.findServiceByID(serviceID);
    		if (usr.getShoppingCart().containsKey(service)) {
    			usr.getShoppingCart().remove(service);
    		}
    		
    		float total = calculateTotalShoppingCart();
    		
    		if (service != null) {
    			modelAndView.addObject("service", service);
    			modelAndView.addObject("purchasedUsr", purchasedUsr);
    			modelAndView.addObject("shoppingCart", usr.getShoppingCart());
    			modelAndView.addObject("total", total);
    		}
    		
		return modelAndView;
    }
    
    @RequestMapping(value="/clearShoppingCart", method=RequestMethod.GET)
    public ModelAndView clearShoppingCart() {
    		ModelAndView modelAndView = initializeLoggedUser("shopping");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		User purchasedUsr = userDAO.findUserWithDeliveryAddress(usr.getEmail());
    		usr.getShoppingCart().clear();
    		
    		float total = calculateTotalShoppingCart();
    		
    		modelAndView.addObject("purchasedUsr", purchasedUsr);
    		modelAndView.addObject("shoppingCart", usr.getShoppingCart());
    		modelAndView.addObject("total", total);
    		
		return modelAndView;
    }
    
    @RequestMapping(value="/shoppingAction", method=RequestMethod.POST, params="action=payShoppingCart")
    public ModelAndView shoppingAction(){
    		return buyOneProduct();
    }
    
    @RequestMapping(value="/shoppingAction", method=RequestMethod.POST, params="action=delete selected services")
    public ModelAndView shoppingAction(@RequestParam(value = "idChecked", required=false) List<String> idrates){
    		ModelAndView modelAndView = initializeLoggedUser("shopping");
    		
    		if(idrates == null || idrates.size()==0) {
    			User purchasedUsr = userDAO.findUserWithDeliveryAddress(usr.getEmail());
        		float total = calculateTotalShoppingCart();
        	
        		modelAndView.addObject("purchasedUsr", purchasedUsr);
        		modelAndView.addObject("shoppingCart", usr.getShoppingCart());
        		modelAndView.addObject("total", total);
    			return modelAndView;
    		}
    		
    		Iterator<Entry<Service, Integer>> it = usr.getShoppingCart().entrySet().iterator();
    		while (it.hasNext()) {
	    		Map.Entry<Service, Integer> pair = (Map.Entry<Service, Integer>)it.next();
	    		for (String t : idrates) {
	    			if ((pair.getKey().getServiceID()+"").equals(t)) {
	    				it.remove();
	    			}
	    		}
	    }
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		User purchasedUsr = userDAO.findUserWithDeliveryAddress(usr.getEmail());
    		float total = calculateTotalShoppingCart();
    	
    		modelAndView.addObject("purchasedUsr", purchasedUsr);
    		modelAndView.addObject("shoppingCart", usr.getShoppingCart());
    		modelAndView.addObject("total", total);
    		
		return modelAndView;
    }
    
    @RequestMapping(value="/orderover", method=RequestMethod.GET)
    public ModelAndView orderover(@RequestParam(value="total", required=false) Float total) {
    		ModelAndView modelAndView = initializeLoggedUser("orderover");
    		ArrayList<Service>  services = serviceDAO.findAllService();
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		modelAndView.addObject("services", services);
    		modelAndView.addObject("total", total);
    		
    		usr.getShoppingCart().clear();
		return modelAndView;
    }

    @RequestMapping(value="/buyOneProduct", method=RequestMethod.POST)
    public ModelAndView buyOneProduct() {
    		int result = serviceDAO.purchaseItems(usr); // 0 mean no error, 1 mean error with SQL connection or query
    		ModelAndView modelAndView = new ModelAndView();
    		
    		float total = calculateTotalShoppingCart();
    		modelAndView.setViewName("redirect:orderover?total="+total);
		return modelAndView;
    }

	@RequestMapping(value="/prod_list", method=RequestMethod.GET)
    public ModelAndView prod_list(@RequestParam(value="crrPage", required=false) Integer crrPage) {
    		ModelAndView modelAndView = initializeLoggedUser("new_service");
    		
    		if (usr == null) {
    			return modelAndView;
    		}
    		
    		ArrayList<Service>  arr = serviceDAO.findServiceByProvider(usr.getEmail());
    		
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
	
	@RequestMapping(value="/shopping", method=RequestMethod.GET)
    public ModelAndView shopping(@RequestParam(value="serviceID", required=true) Integer serviceID,
    								@RequestParam(value="quantity", required=true) Integer quantity) {
    		ModelAndView modelAndView = initializeLoggedUser("shopping");
    		Service  service = serviceDAO.findServiceByID(serviceID);
    		usr.getShoppingCart().put(service, quantity);
    		
    		if (usr == null) {
    			return modelAndView;
    		}
//    		modelAndView.addObject("services", services);
//    		modelAndView.addObject("total", total);
    		
    		usr.getShoppingCart().clear();
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
    
    private float calculateTotalShoppingCart() {
    		float total = 0;
    		Iterator<Entry<Service, Integer>> it = usr.getShoppingCart().entrySet().iterator();
    		while (it.hasNext()) {
	    		Map.Entry<Service, Integer> pair = (Map.Entry<Service, Integer>)it.next();
	    		total += pair.getKey().getCurrentPrice() * pair.getValue();
	    }
		return total;
	}
}
