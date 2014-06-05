package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import models.Product;

//@With(Secure.class)
public class WrapperController extends Controller {
	
//	/**
//	 * Initialize a flash message if no product have been selected
//	 */
//    @Before 
//    static void checkSelection(){
//    	if(!HTMLFlash.present()) {
//    		if(session.get("productName") == null) {
//    			String msg = "Commencez par sélectionner un produit dans la barre de menu";
//    			HTMLFlash.contextual(msg,HTMLFlash.INFORMATION, true);
//    		}
//    		else if(session.get("releaseName") == null || session.get("sprintId") == null) {
//    			String msg = "Sélectionner une release et un sprint dans l'onglet produit";
//    			HTMLFlash.contextual(msg, HTMLFlash.INFORMATION, true);
//    		}
//    	}
//    }
//    
    /**
     * Get user informations in order to
     * know what kind of worker he's in the current product
     */
   @Before
    static void getUserInfo() {
    	if(!session.isEmpty()) {
    		models.User connected = models.User.getByEmail(session.get("username"));
    		if(connected != null && connected.getProducts() != null)  {    			
    			Collection<Product> products = connected.getProducts().values();    			   			    	
    			renderArgs.put("products", products);
    		}
    	}
    	
		Map<models.Version, Set<models.Sprint>> productContent = new LinkedHashMap<models.Version, Set<models.Sprint>>();
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
    		session.put("isNotSM", "disabled");
    		session.put("isNotPOAndDev", "disabled");
    	}
    	renderArgs.put("productContent", productContent);
    }    
}