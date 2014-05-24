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
    	
		Map<models.Version, Set<Sprint>> productContent = new LinkedHashMap<models.Version, Set<Sprint>>();
    	Set<models.Version> releases = new LinkedHashSet<models.Version>();
    	
    	if(session.get("productName") != null) {
        	String productName = session.get("productName");
	    	Product product = models.Product.getByName(productName);
	    	
	    	// Get releases and sprint of current product
	    	releases.addAll(product.getReleases());
	    	for (models.Version v : releases) {
	    		productContent.put(v, v.getSprints());
	    	}
    	}
    	else {
    		session.put("isNotPO", "disabled");
    		session.put("isNotDev", "disabled");
    	}
    	renderArgs.put("productContent", productContent);
    }    
}