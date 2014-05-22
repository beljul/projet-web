package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import models.Product;

@With(Secure.class)
public class Application extends WrapperController {

    public static void index() {
        render();
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