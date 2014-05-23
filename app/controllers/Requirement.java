package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Requirement extends WrapperController {
	
	@Before
	static void checkProductIsSelected(){
		String productName = session.get("productName");
		if(productName == null){
			flash.put("message", "Veuillez d'abord sélectionner un produit");
			flash.put("messageStyle", "error");
			redirect("/Application/dashboard");
		}
	}
	public static void add() {
		render();
	}
	
	public static void order(){
		String productName = session.get("productName");
		System.out.println("++++" + productName);
		models.Product product = models.Product.getByName(productName);
		System.out.println(product);
		Collection<models.Requirement> r = product.getRequirements();
		render(r);
	}
	
	public static void register(ArrayList<String> requirements, ArrayList<Integer> priority, ArrayList<Integer> duration) {
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());

		for (int i = 0; i < requirements.size(); i++) {
			System.out.println(requirements.toArray()[i]);			
			models.Requirement req = new models.Requirement((String)requirements.get(i), 
											created, (Integer)priority.get(i), 
											(Integer)duration.get(i));
			req.setProduct(product);
			req.register();
		}
		flash.put("message", "Nouvelle(s) exigence(s) ajoutée(s)");
		flash.put("messageStyle", "validation");
		redirect("/Application/dashboard");
	}
}
