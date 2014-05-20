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
    		System.out.println(session.get("username"));
    		System.out.println("********************" + connected);
    		if(connected.getProducts() != null)  {
    			Collection<Product> products = connected.getProducts().values();    	
    			renderArgs.put("products", products);
    		}
    	}
    }
    
    public static void dashboard(){    	
    	render();
    }
}