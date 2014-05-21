package controllers;

import java.sql.Date;
import java.util.Set;

import play.mvc.Controller;

public class Requirement extends Controller {
	
	public static void add() {
		render();
	}
	
	public static void register(Set<String> requirements, Set<Integer> priority, Set<Integer> duration) {
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		
		for (int i = 0; i < requirements.size(); i++) {
			models.Requirement req = new models.Requirement((String)requirements.toArray()[i], 
											created, (Integer)priority.toArray()[i], (Integer)duration.toArray()[i]);
			req.setProduct(product);
			req.register();
		}
		
		redirect("/Application/dashboard");
	}
}
