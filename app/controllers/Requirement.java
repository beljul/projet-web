package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Requirement extends WrapperController {
	
	/**
	 * Call adding requirements page of the application
	 */
	public static void add() {
		render();
	}
	
	/**
	 * Get requirements of current product
	 * and send them in order to order them then
	 * Call the order page of requirements of the application
	 */
	public static void order(){
		String msg = "Vous n'êtes pas autorisé à accéder à cette functionnalité";		
		if(! AccessRules.isDev()) {			
			HTMLFlash.cancelFlash();
			HTMLFlash.screen(msg, HTMLFlash.ERROR, false);
			redirect("/");
		}
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		Collection<models.Requirement> r = product.getRequirements();
		render(r);
	}
	
	/**
	 * Record new requirements
	 * @param requirements : content of each requirement
	 * @param priority : priority of each requirement
	 * @param duration : duration of each requirement
	 */
	public static void register(ArrayList<String> requirements, ArrayList<Integer> priority, ArrayList<Integer> duration) {
		// Get current product
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		
		// Get current date
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());

		// Create and record new requirements
		for (int i = 0; i < requirements.size(); i++) {
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
	
	/**
	 * Sort requirements which are already assigned or not 
	 * to the current sprint
	 */
	public static void assign() {
		// Get requirements of current sprint
		Set<models.Requirement> sprintRequirements;
		if(!session.get("sprintId").isEmpty()) {
			Long sprintId = Long.parseLong(session.get("sprintId"));
			sprintRequirements = models.Sprint.getById(sprintId).getRequirements();
		}
		else { // TODO: error page
			sprintRequirements = new HashSet<models.Requirement>();
		}
		
		// Get requirements of current product (unassigned)
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		Set<models.Requirement> requirementsUnassigned = product.getRequirementsUnassigned();
		
		render(requirementsUnassigned, sprintRequirements);
	}
}
