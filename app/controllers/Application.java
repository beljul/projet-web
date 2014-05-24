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
    	email = session.get("username");
    	String emailPO = new String();
    	Map<models.Version, Set<Sprint>> productContent = new LinkedHashMap<models.Version, Set<Sprint>>();
    	Set<models.Version> releases = new LinkedHashSet<models.Version>();
    	
    	if (session.get("productName") != null) {
	    	String productName = session.get("productName");
	    	Product product = models.Product.getByName(productName);
	    	emailPO = product.getProductOwner().getEmail();
	    	
	    	// Get releases and sprint of current product
	    	releases.addAll(product.getReleases());
	    	for (models.Version v : releases) {
	    		productContent.put(v, v.getSprints());
	    	}
    	}
    	
    	// To verify if current user is the current product's product owner
    	String isNotPO= new String();
    	if(!email.equals(emailPO)) {
    		isNotPO = "disabled";
    	}
    	else {
    		isNotPO = "";
    	}
    	
    	System.out.println("=========================================");
    	for (models.Version v : releases) {
    		
			System.out.println("Release : " + v.getName());
			for (Sprint s : v.getSprints()) {
				System.out.println("------->Sprint : " + s.getCreated());
			}
		}
    	System.out.println("=========================================");

    	render(isNotPO, productContent);
    }
}