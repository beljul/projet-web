package controllers;

import play.*;

import play.mvc.*;

import java.util.*;


import models.*;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
        render();
    }
    
    @Before
    static void getUserInfo() {
    	models.User connected = models.User.getByEmail(session.get("username"));
    	List<models.Product> products = connected.getLastProducts();
    	renderArgs.put("products", products);       
    }
    
    public static void dashboard(){    	
    	render();
    }
}