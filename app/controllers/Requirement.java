package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTML;

import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Requirement extends WrapperController {
	
	/**
	 * Check if a product has been selected
	 */
	@Before
	public static void checkProductSelection(){
		if(!AccessRules.productDefined()){
			HTMLFlash.noProductDefined();
			redirect("/");
		}		
	}
	
	/**
	 * Check if a sprint has been selected
	 */
	@Before(only="assign")
	public static void checkSprintSelection(){
		if(!AccessRules.sprintDefined()){
			HTMLFlash.noSprintDefined();
			redirect("/");
		}		
	}
	
	/**
	 * Product owner access
	 */
	@Before(only={"add","register"})
	public static void checkPO(){
		if(!AccessRules.isPO()){
			HTMLFlash.notAuthorized();
			redirect("/");
		}
	}
	
	/**
	 * Developer access
	 */
	@Before(only={"order"})
	public static void checkDev(){
		if(!AccessRules.isDev()) {
			HTMLFlash.notAuthorized();
			redirect("/");
		}
	}
	
	/**
	 * Developer or product owner access
	 */
	@Before(only={"assign"})
	public static void checkDevOrPO(){
		if(!AccessRules.isPO() && !AccessRules.isDev()){
			HTMLFlash.notAuthorized();
			redirect("/");
		}
	}
	
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
		if(! AccessRules.isDev()) {			
			HTMLFlash.notAuthorized();
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
		HTMLFlash.contextual("Nouvelle(s) exigence(s) ajoutée(s)",
							 HTMLFlash.VALIDATION, false);
		redirect("/");
	}
	
	/**
	 * Sort requirements which are already assigned or not 
	 * to the current sprint
	 */
	public static void assign() {
		// Get requirements of current sprint
		Set<models.Requirement> sprintRequirements;		
		Long sprintId = Long.parseLong(session.get("sprintId"));
		sprintRequirements = models.Sprint.getById(sprintId).getRequirements();	
		
		// Get requirements of current product (unassigned)
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		Set<models.Requirement> requirementsUnassigned = product.getRequirementsUnassigned();
		
		render(requirementsUnassigned, sprintRequirements);
	}
}
