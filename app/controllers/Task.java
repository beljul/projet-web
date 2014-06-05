package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.mvc.Before;
import play.mvc.With;


@With(Secure.class)
public class Task extends WrapperController {
	
	/**
	 * Check if there is a product and a sprint selected
	 */
	@Before
	public static void checkSelection(){
		if(!AccessRules.productDefined()){
			HTMLFlash.noProductDefined();
			redirect("/");
		}
		if(!AccessRules.sprintDefined()){
			HTMLFlash.noSprintDefined();
			redirect("/");
		}
	}
	
	/**
	 * Control PO and Dev access rules
	 */
	@Before(only={"add","register"})
	public static void checkPOOrDev() {
		if(!AccessRules.isPO() && !AccessRules.isDev()){
			HTMLFlash.notAuthorized();
			redirect("/");
		}
	}
	
	/**
	 * Call the adding task page of the application
	 */
	static public void add() {
		String productName = session.get("productName");
		Set<models.Requirement> requirements = models.Product.getByName(productName).getRequirements();
		render(requirements);		
	}
	
	/**
	 * Add new tasks of a specific requirement
	 * @param req : requirement selected
	 * @param descriptions
	 * @param durations
	 * @param prioritys
	 */
	static public void register(String req, ArrayList<String> descriptions, 
								ArrayList<Integer> durations, ArrayList<Integer> prioritys) {
		// Get current date
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		
		models.Requirement requirement = models.Requirement.getByContent(req);
		/* 
		 * Get current id of a task according to a specific requirement
		 * A attribute is used to know the sub-id of a task
		 */
		Integer currentId = models.Task.getCurrentId(requirement);
		if(currentId != 1) ++currentId;
		
		// Create tasks and record them
		for (int i = 0; i < descriptions.size(); i++) {
			models.Task task = new models.Task(descriptions.get(i), currentId+i, durations.get(i),
												created, prioritys.get(i), requirement.getId());
			task.register();
			requirement.addTask(task);			
		}
		requirement.register();
		HTMLFlash.contextual("De nouvelles tâches ont été ajoutées", HTMLFlash.VALIDATION, false);
		redirect("/");
	}
}
