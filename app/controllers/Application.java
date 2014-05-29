package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import models.Product;

@With(Secure.class)
public class Application extends WrapperController {
	
    public static void index() {
    	if(session.get("productName") != null)
    		Sprint.progression();    	
    	else {
    		render();
    	}
    }
      
    public static void dashboard(){
    	if (session.get("productName") == null) {
    		session.put("isNotPO", "disabled");
    		session.put("isNotDev", "disabled");
    		session.put("isNotPOAndDev", "disabled");
    	}
    	
    	render();
    }
}
