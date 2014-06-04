package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import models.Product;

@With(Secure.class)
public class Application extends WrapperController {
	
	/**
	 * Call main page of the application
	 */
    public static void index() {
    		render();
    }
    
    /**
     * Call dashboard which is main page when we're connected
     */
    public static void dashboard(){
    	/*
    	 * Initialize session variables (product not selected)
    	 */
    	if (session.get("productName") == null) {
    		session.put("isNotPO", "disabled");
    		session.put("isNotDev", "disabled");
    		session.put("isNotPOAndDev", "disabled");
    	}
    	
    	render();
    }
}
