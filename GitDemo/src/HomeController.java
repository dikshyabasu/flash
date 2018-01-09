

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.*;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.grievance.management.dao.ContactDAO;
import com.grievance.management.model.Admin_Detail;
import com.grievance.management.model.Contact;
import com.grievance.management.model.Billing;
import com.grievance.management.model.Product;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	 @Autowired
	 private ContactDAO contactDAO;
	 
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
		public String landing(Locale locale, Model model) {
			logger.info("Welcome home! The client locale is {}.", locale);
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
			String formattedDate = dateFormat.format(date);
			model.addAttribute("serverTime", formattedDate );
			return "index";
		}
	@RequestMapping(value = "/signup_login", method = RequestMethod.GET)
	public String signuplogin(Model model) {
		
		return "signup1";
	}
	 
	 @RequestMapping(value = "/newUser", method = RequestMethod.GET)
	 public ModelAndView newContact(ModelAndView model) {
	     Contact newContact = new Contact();
	     model.addObject("contact", newContact);
	     model.setViewName("userregd");
	     return model;
	 }
	 
	 
	 @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	 public ModelAndView saveContact(@ModelAttribute ("contact") Contact contact) {
		 System.out.println("inside");
	     contactDAO.save(contact);
	     return new ModelAndView("redirect:loginlanding");
	 }
	 
	 @RequestMapping(value = "/billing", method = RequestMethod.POST)
	 public ModelAndView savebilling(@ModelAttribute ("bil") Billing bil,ModelAndView model,HttpServletRequest request) throws IOException {
		 
		 HttpSession session=request.getSession(false);
			String emailid=(String)session.getAttribute("emailid");
		 
		 System.out.println(bil.getEmailaddress());
	    contactDAO.savebilling(bil);
	     model.addObject("emailaddress",bil.getEmailaddress());

	     model.addObject("firstname",bil.getFirstname());

	     model.addObject("nameoncard",bil.getNameoncard());
	     model.addObject("card",bil.getCard());

	     return new ModelAndView("billing_show");
	 }
	 
	 
	 
	 @RequestMapping(value = "/loginlanding", method = RequestMethod.GET)
		public String loginlanding1(Model model) {
			
			return "login";
		}
	 
	 
	 
	 @RequestMapping(value = "/product", method = RequestMethod.GET)
		public String product(Model model) {
			
			return "product";
		}
	 

		@RequestMapping(value="/search" , method = RequestMethod.POST)
		public ModelAndView search(@ModelAttribute ("contact") Contact contact,ModelAndView model,HttpServletRequest request) throws IOException {
		   // System.out.println(contact.getPassword()+" inside login   "+contact.getEmailid());
		  //List<Contact> list = contactDAO.searchproduct("babyproduct");
		   List<Contact> list = contactDAO.searchproduct(contact.getName());
		    System.out.println(contact.getName()+"inside cotroller....");
		    
		    System.out.println(" LIST DATA ææ:::::::::    "+list);
		    
		    
		    
		    
		    
			return new ModelAndView("view","list","list");
		   
		}
	 
	 
	 @RequestMapping(value = "/babyproduct", method = RequestMethod.GET)
		public String babyproduct(Model model) {
			
			return "baby_product";
		}
	 
	 @RequestMapping(value = "/diabetes_product", method = RequestMethod.GET)
		public String Diabetes_product(Model model) {
			
			return "diabetes_product";
		}
	 
	 
	 @RequestMapping(value = "/services", method = RequestMethod.GET)
		public String services(Model model) {
		 System.out.println("inside service");
			
			return "services";
		}
	 
	 

		@RequestMapping(value = "/loginlandingadmin", method = RequestMethod.GET)
		public String loginlandingadmin(Model model) {
			
			return "Admin_Login";
		}
		
		
		
		
		@RequestMapping(value = "/payment", method = RequestMethod.GET)
		public String payment(Model model) {
			
			return "Payment";
		}
	 
		
		 /*@RequestMapping(value = "/search", method = RequestMethod.GET)
			public String search(Model model) {
			
			 if(search.equals("babyproduct"))
			 {
				 
			 }
				
				return "baby_product";
			}
		*/
		
	 
	 @RequestMapping(value="/mydetail" , method = RequestMethod.GET)
		public ModelAndView getdetaildonor(@ModelAttribute ("contact1") Contact contact, ModelAndView model,HttpServletRequest request) throws IOException{
			//System.out.println("i am in getdetail method controller");
			
			HttpSession session=request.getSession(false);
			String emailid=(String)session.getAttribute("emailid");
			//System.out.println("Helloooooo"+e);
			
			Contact list = contactDAO.get(emailid);
			
			//System.out.println(list.getAddress());
			//System.out.println(list.getName());
		/*	return new ModelAndView("viewA_Detail","list",list);*/
			model = new ModelAndView("viewA_Detail");
			  
	        model.addObject("name", list.getName());
	        System.out.println(list.getName());
	       
	        model.addObject("address", list.getAddress());
	        System.out.println(list.getAddress());
	        model.addObject("emailid", list.getEmailid());
	        model.addObject("password", list.getPassword()); 
	        model.addObject("phone", list.getPhone());
	        return model;
		
		
		}
	

	 
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 /*@RequestMapping(value = "/saveDonor", method = RequestMethod.POST)
	 public ModelAndView saveDonor(@ModelAttribute ("donor") Contact contact) {
		 System.out.println("inside donor");
		 System.out.println(contact.getName());
		 contactDAO.savedonor(contact);
		 System.out.println("inside dao method");
	     return new ModelAndView("redirect:loginlandingdonor");
	 }
	 

	 
	 
	 
	 @RequestMapping(value = "/feedback", method = RequestMethod.POST)
	 public ModelAndView feedback(@ModelAttribute ("feedback") Feedback feedback,ModelAndView model,HttpServletRequest request) {
		 System.out.println("hello diku");
		 
		 HttpSession session=request.getSession(false);
		 String emailid=(String)session.getAttribute("emailid");
			System.out.println("Helloooooo"+emailid);
			
			
		
			
			contactDAO.feedback (emailid,feedback);
		 System.out.println("inside dao feedback method?????");
		   model.addObject("feedback", feedback.getFeedback());
		   model.addObject("emailid", emailid);
		   System.out.println(feedback.getFeedback()+"feddback in addobject");
		   
	     return new ModelAndView("view_feedback");
	     
	 }
	 
	 
	 
	 @RequestMapping(value = "/feedback_admin", method = RequestMethod.POST)
	 public ModelAndView feedbackviewadmin(@ModelAttribute ("feedback") Feedback feedback,ModelAndView model,HttpServletRequest request) {
		
				System.out.println("i am in listcontact controller");
				
				List<Contact> list = contactDAO.listfeedback();
				System.out.println(list.get(1));
				System.out.println(list.get(2));
				//System.out.println(list.get(5)+"hhhhhhhhhbloodgroup");
				return new ModelAndView("view","list",list);
	 
	 }
	 
	 @RequestMapping(value = "/updateContact", method = RequestMethod.POST)
	 public ModelAndView updateContact(@ModelAttribute ("contact") Contact contact,ModelAndView model,HttpServletRequest request) {
		 System.out.println("inside");
		 
		 
		 
		 
			HttpSession session=request.getSession(false);
			String emailid=(String)session.getAttribute("emailid");
			System.out.println("Helloooooo"+emailid);
		 
		 
		 
	     contactDAO.update(emailid);
	     return new ModelAndView("redirect:/");
	 }
	
	
	
	    @RequestMapping(value="/editsave",method = RequestMethod.POST)  
	    public ModelAndView editsave(@ModelAttribute("edit_acceptor") Contact contact,ModelAndView model,HttpServletRequest request){  

			HttpSession session=request.getSession(false);
			String emailid=(String)session.getAttribute("emailid");
			System.out.println("Helloooooo"+emailid);
		 
	    	Contact list = contactDAO.update(emailid); 
	        return new ModelAndView("redirect:/viewemp");  
	    }
	 @RequestMapping(value="/editAccepter")  
	    public ModelAndView edit(@ModelAttribute ("acceptor") Contact contact, ModelAndView model,HttpServletRequest request){  


			HttpSession session=request.getSession(false);
			String emailid=(String)session.getAttribute("emailid");
		System.out.println("Helloooooo"+emailid);
			
			Contact list = contactDAO.getAcceptorById(emailid);
			
			System.out.println(list.getAddress()+"ttttttt");
			//System.out.println(list.getName());
			return new ModelAndView("viewA_Detail","list",list);
			model = new ModelAndView("edit_acceptor");
	  
	        model.addObject("name", list.getName());
	        System.out.println(list.getName()+"modelinside");
	        model.addObject("address", list.getAddress());
	        model.addObject("emailid", list.getEmailid());
	        model.addObject("password", list.getPassword()); 
	        model.addObject("bgroup", list.getBgroup());
	        return model;

		}
	 
	 @RequestMapping(value="/editsave",method = RequestMethod.POST)  
	    public ModelAndView editsave(@ModelAttribute("accepter_edit") Contact contact,HttpServletRequest request){  
		 System.out.println("yyy");
		 System.out.println(contact.getName()+"inside controller");
		 System.out.println(contact.getEmailid()+"inside controller email");
			contactDAO.update(contact);
			 
	        return new ModelAndView("redirect:/viewemp");  
	    }    
	 
	 
	 
	  @RequestMapping(value="/delete_donor",method = RequestMethod.GET)  
      public ModelAndView delete(@ModelAttribute("delete_donor") Contact contact,HttpServletRequest request){  
			HttpSession session=request.getSession(false);
			String emailid=(String)session.getAttribute("emailid");
			System.out.println("Helloooooo delete method cntroller"+emailid);
		  
		 System.out.println("yyy");
			contactDAO.delete(emailid);
        return new ModelAndView("redirect:/viewdonor");  
    } 
	
	 
	  
	  @RequestMapping(value="/delete_accepter",method = RequestMethod.GET)  
      public ModelAndView delete_accepter(@ModelAttribute("acceptor1") Contact contact,HttpServletRequest request){  
		  System.out.println("inside delete controller");
		  HttpSession session=request.getSession(false);
			String emailid=(String)session.getAttribute("emailid");
			System.out.println("Helloooooo delete method cntroller"+emailid);
		  
		 System.out.println("yyy");
			contactDAO.deleteacceptor(emailid);
	 
	  @RequestMapping(value="/mydetail1" , method = RequestMethod.GET)
		public ModelAndView getd(@ModelAttribute ("acceptor1") Contact contact, ModelAndView 

	model,HttpServletRequest request) throws IOException{
			//System.out.println("i am in getdetail method controller");
			
			HttpSession session=request.getSession(false);
			String emailid=(String)session.getAttribute("emailid");
			System.out.println("Helloooooo mydetail"+emailid);
			return model;
	  }
*/
	  @RequestMapping(value="/listContact" , method = RequestMethod.GET)
			public ModelAndView listContact(ModelAndView model) throws IOException{
				System.out.println("i am in listcontact controller");
				
				List<Contact> list = contactDAO.list();
			
				//System.out.println(list.get(5)+"hhhhhhhhhbloodgroup");
				return new ModelAndView("view","list",list);
				/*System.out.println(listContact.get(1));

				System.out.println(listContact.get(2));
			    
				model.addObject("listContact", listContact);
			    model.setViewName("view");
			    model = new ModelAndView();
			    model.addObject("id", listContact.get(1));
			
			    model.addObject("firstname", listContact.get(2));
			 */
			/*	return "view";*/
			}

/*
	
	
	
	@RequestMapping(value="/listContactDonor" , method = RequestMethod.GET)
	public ModelAndView listContactdonor(ModelAndView model) throws IOException{
		System.out.println("i am in listcontact controller");
		
		List<Contact> list = contactDAO.listdonor();
		return new ModelAndView("viewdonor","list",list);
	
	}
	
	@RequestMapping(value="/view_feedback" , method = RequestMethod.GET)
	public ModelAndView viewfeedback(@ModelAttribute ("feedback") Feedback feedback,ModelAndView model,HttpServletRequest request) throws IOException{
		System.out.println("i am in listcontact controller");
		
		
		 HttpSession session=request.getSession(false);
		 String emailid=(String)session.getAttribute("emailid");
			System.out.println("Helloooooo"+emailid);
			
		
		List<Contact> list = contactDAO.listfeedback(emailid,feedback);
		return new ModelAndView("view","list",list);
		System.out.println(listContact.get(1));

		System.out.println(listContact.get(2));
	    
		model.addObject("listContact", listContact);
	    model.setViewName("view");
	    model = new ModelAndView();
	    model.addObject("id", listContact.get(1));
	
	    model.addObject("firstname", listContact.get(2));
	 
		return "view"; 
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	@RequestMapping(value="/mydetail" , method = RequestMethod.GET)
	public ModelAndView getdetails(@ModelAttribute ("contact") Contact contact, ModelAndView 

model,HttpServletRequest request) throws IOException{
		//System.out.println("i am in getdetail method controller");
		
		HttpSession session=request.getSession(false);
		String emailid=(String)session.getAttribute("emailid");
		System.out.println("Helloooooo mydetail"+emailid);
		
		Contact list = contactDAO.get(emailid);
		
		//System.out.println(list.getAddress());
		//System.out.println(list.getName());
		return new ModelAndView("viewA_Detail","list",list);
		model = new ModelAndView("viewA_Detail");
  
        model.addObject("name", list.getName());
        model.addObject("address", list.getAddress());
        model.addObject("emailid", list.getEmailid());
        model.addObject("password", list.getPassword()); 
        model.addObject("bgroup", list.getBgroup());
        return model;

	
	
	}

	
	/*
	
	@RequestMapping(value="/mydetail_donor" , method = RequestMethod.GET)
	public ModelAndView getdetaildonor(@ModelAttribute ("contact1") Contact contact, ModelAndView model,HttpServletRequest request) throws IOException{
		//System.out.println("i am in getdetail method controller");
		
		HttpSession session=request.getSession(false);
		String emailid=(String)session.getAttribute("emailid");
		//System.out.println("Helloooooo"+e);
		
		Contact list = contactDAO.getdonor(emailid);
		
		//System.out.println(list.getAddress());
		//System.out.println(list.getName());
		return new ModelAndView("viewA_Detail","list",list);
		model = new ModelAndView("viewA_Detail");
  
        model.addObject("name", list.getName());
        model.addObject("address", list.getAddress());
        model.addObject("emailid", list.getEmailid());
        model.addObject("password", list.getPassword()); 
        model.addObject("bgroup", list.getBgroup());
        return model;

	
	
	}
	
	@RequestMapping(value = "/signup_login", method = RequestMethod.GET)
	public String loginlanding(Model model) {
		
		return "signup1";
	}
	
	
	/*

	@RequestMapping(value = "/loginlanding", method = RequestMethod.GET)
	public String loginlanding(Model model) {
		
		return "login";
	}
	
	
	
	@RequestMapping(value = "/loginlandingdonor", method = RequestMethod.GET)
	public String loginlandingdonor(Model model) {
		
		return "logindonor";
	}
	@RequestMapping(value = "/reglanding", method = RequestMethod.GET)
	public String reglanding(Model model) {
		
		return "Regd";
	}
	
	@RequestMapping(value = "/reglandingdonor", method = RequestMethod.GET)
	public String reglandingdonor(Model model) {
		
		return "RegdDonor";
	}
	
	
	@RequestMapping(value = "/loginlandingadmin", method = RequestMethod.GET)
	public String loginlandingadmin(Model model) {
		
		return "A_Login";
	}
	
	@RequestMapping(value = "/feedback_accepter", method = RequestMethod.GET)
	public String feedback(Model model) {
		
		return "feedback";
	}
	
	
	
	
	@RequestMapping(value = "/signup_login", method = RequestMethod.GET)
	public String signuplogin(Model model) {
		
		return "signup1";
	}
	
	@RequestMapping(value = "/view_feedback", method = RequestMethod.GET)
	public String feedbackview(Model model) {
		
		return "view_feedback";
	}
	

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		
		return "logout";
	}
	
	
	
	@RequestMapping(value = "/logout_admin", method = RequestMethod.GET)
	public String logoutacceptor(Model model) {
		
		return "admin_dash1";
	}
	
	@RequestMapping(value = "/viewfeedback", method = RequestMethod.GET)
	public String feedbackview(Model model) {
		
		return "view_feedback_admin";
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact(Model model) {
		
		return "contact";
	}
	
	*/
	
	@RequestMapping(value="/authenticate" , method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute ("contact") Contact contact,ModelAndView model,HttpServletRequest request) throws IOException {
	   // System.out.println(contact.getPassword()+" inside login   "+contact.getEmailid());
	    Contact c = contactDAO.login(contact);
	    if (null != c) {
	    	
	    	
	    	String n=request.getParameter("emailid");
	    	System.out.println(n+"hhhhhh");
	    	HttpSession session=request.getSession(true);
	    	session.setAttribute("emailid", n);
	    	System.out.println("HelloBye"+n);
	    	
	    	model = new ModelAndView("home");
	        model.addObject("firstname", c.getName());
	        return model;
	        } else {
	        	model = new ModelAndView("login");
	        	model.addObject("message", "Username or Password is wrong!!");
	        	return model;
	        }
	   
	}
	
	/*
	@RequestMapping(value="/authenticatedonor" , method = RequestMethod.POST)
	public ModelAndView logindonor(@ModelAttribute ("contact") Contact contact,ModelAndView model,HttpServletRequest request) throws IOException{
	    System.out.println(contact.getPassword()+" inside login   "+contact.getEmailid());
	    Contact c = contactDAO.logindonor(contact);
	    if (null != c) {
	    	
	    	String n=request.getParameter("emailid");
	    	System.out.println(n);
	    	HttpSession session=request.getSession(true);
	    	session.setAttribute("emailid", n);
	    	System.out.println("HelloBye"+n);
	    	
	    	
	    	
	    	model = new ModelAndView("DonorDashboard");
	        model.addObject("firstname", c.getName());
	        return model;
	        } else {
	        	model = new ModelAndView("login");
	        	model.addObject("message", "Username or Password is wrong!!");
	        	return model;
	        }
	   
	}
	*/
	
	@RequestMapping(value="/authenticate_admin" , method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute ("admin") Admin_Detail admin,ModelAndView model,HttpServletRequest request) throws IOException{
	    System.out.println(admin.getPassword()+" inside login   "+admin.getEmailid());
	    String s1=request.getParameter("emailid");
	    String s2=request.getParameter("password");
	    System.out.println(s1);
	    
	     contactDAO.loginadmin(admin);
	    if (null != admin) {
	    	model = new ModelAndView("admin_dash1");
	    	//create session object here and keep the emailid..
	        model.addObject("firstname", admin.getEmailid());
	        return model;
	        } else {
	        	model = new ModelAndView("Login_admin");
	        	model.addObject("message", "Username or Password is wrong!!Please Check...");
	        	return model;
	        }
	   
	}
	
	/*
	
	@RequestMapping(value="/search" , method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute ("contact") Contact contact,ModelAndView model,HttpServletRequest request) throws IOException {
	   // System.out.println(contact.getPassword()+" inside login   "+contact.getEmailid());
	  //  List<Contact> list = contactDAO.search("B+");
	    List<Contact> list = contactDAO.search(contact.getBgroup());
	    System.out.println(contact.getBgroup()+"inside cotroller....");
	    
	    System.out.println(" LIST DATA ææ:::::::::    "+list);
	    
	    
	    
	    
	    
		return new ModelAndView("view","list",list);
	   
	}
	
	
	
	
	
	@RequestMapping(value="/search_donor" , method = RequestMethod.POST)
	public ModelAndView search_donor(@ModelAttribute ("donor") Contact contact,ModelAndView model,HttpServletRequest request) throws IOException {
	    System.out.println(contact.getPassword()+" inside login   "+contact.getEmailid());
	    List<Contact> list = contactDAO.search("B+");
	    List<Contact> list = contactDAO.searchdonor(contact.getBgroup());
	    System.out.println(contact.getBgroup()+"inside cotroller....");
	    
	    System.out.println(" LIST DATA ææ:::::::::    "+list);
	    
	    
	    
	    
	    
		return new ModelAndView("viewdonor","list",list);
	   
	}
	
	
	
	
	
	
	

	   */
	}
	
	
	
	
	
	
	
	
	
/*}*/
	
	
	
	
	

