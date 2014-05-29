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
	
	@Before
	static void checkAccessRules(){
		String msg = "Vous n'êtes pas autorisé à accéder à cette functionnalité";
		System.out.println("titi");
		if(! AccessRules.isDev()) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!titi");
			HTMLFlash.cancelFlash();
			HTMLFlash.screen(msg, HTMLFlash.ERROR, false);
		}
	}
	
	public static void add() {
		render();
	}
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
		
		// Get requirements of current product
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		Set<models.Requirement> requirementsUnassigned = product.getRequirementsUnassigned();
		
		render(requirementsUnassigned, sprintRequirements);
	}
}
