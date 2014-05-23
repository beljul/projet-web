package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import models.Product;

//@With(Secure.class)
public class WrapperController extends Controller {

  
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
}