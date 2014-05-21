package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import models.Product;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
        render();
    }
    
    @Before
    static void getUserInfo() {
    	if(!session.isEmpty()) {
    		models.User connected = models.User.getByEmail(session.get("username"));
    		if(connected != null && connected.getProducts() != null)  {
    			Collection<Product> products = connected.getProducts().values();    	
    			renderArgs.put("products", products);
    		}
    	}
    }
    
    public static void dashboard(){
    	String email = session.get("username");
    	String emailPO = new String();
    	
    	if (session.get("productName") != null) {
	    	String productName = session.get("productName");
	    	Product product = models.Product.getByName(productName);
	    	emailPO = product.getProductOwner().getEmail();
    	}
    	
    	String isNotPO= new String();
    	if(!email.equals(emailPO)) {
    		isNotPO = "disabled";
    	}
    	else {
    		isNotPO = "";
    	}
    	
    	render(isNotPO);
    }
}