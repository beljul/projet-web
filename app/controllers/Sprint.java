package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.mvc.Before;
import play.mvc.With;

@With(Secure.class)
public class Sprint extends WrapperController {
	
	/**
	 * Add a list of requirements to the current sprint
	 * @param requirements
	 */
	public static void addRequirements (Set<Long> requirements) {
		// Get current sprint
		Long sprintId = Long.parseLong(session.get("sprintId"));
		models.Sprint currentSprint = models.Sprint.getById(sprintId);
		// Add requirements
		for (Long id : requirements) {
			currentSprint.addRequirement(models.Requirement.getById(id));
		}
		currentSprint.register();
	}
	
	/**
	 * Class to return a specific JSON <id, content>
	 */
	private static class JsonSearchItem {
		private long id;		
		private String content;
		private JsonSearchItem(long id, String content){
			this.id = id;
			this.content = content;
		}
	};
	
	/**
	 * Get requirements of the current sprint
	 * and return to ajax request a list of <requirement id, content>
	 * @param sprintId
	 */
	public static void getRequirements (Long sprintId) {
		List<models.Requirement> requirements = new ArrayList(models.Sprint.getById(sprintId).getRequirements());
		List<JsonSearchItem> sprintRequirements = new ArrayList<JsonSearchItem>();		
		for (models.Requirement requirement : requirements) {
			String content = requirement.getContent();
			JsonSearchItem jsi = new JsonSearchItem(requirement.getId(), content);
			sprintRequirements.add(jsi);
		}
		
		
		renderJSON(sprintRequirements);
	}
	
	/**
	 * Return requirements not already assigned to the sprint
	 */
	public static void getRequirementsAvailable() {
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		Set<models.Requirement> requirementsUnassigned = product.getRequirementsUnassigned();
		
		List<JsonSearchItem> requirementsAvailable = new ArrayList<JsonSearchItem>();		
		for (models.Requirement requirement : requirementsUnassigned) {
			String content = requirement.getContent();
			JsonSearchItem jsi = new JsonSearchItem(requirement.getId(), content);
			requirementsAvailable.add(jsi);
		}
		renderJSON(requirementsAvailable);
	}
	/**
	 * Save the progression of the current sprint
	 */
	static public void saveProgression(Map<Long,models.Task.TaskState> states,
										 Map<Long,Integer> winrates){
		 models.Sprint s = models.Sprint.getById(Long.parseLong(session.get("sprintId")));
		 s.saveTasks(states, winrates);
	}
	
	/**
	 * Get all tasks of current sprint
	 * and call the dashboard page
	 */
	static public void progression(){
		models.Product p = models.Product.getByName(session.get("productName"));
		
		models.Version v = null;
		models.Sprint s = null;
		if(p != null)
			v = p.getReleaseByName(session.get("releaseName"));
		if(v != null)
			s = models.Sprint.getById(Long.parseLong(session.get("sprintId")));
		
		// Here we are sure that we have a product, a release and a sprint
		List<models.Task> tasks = new LinkedList<models.Task>();
		if(s != null) {
			for(models.Requirement r : s.getRequirements()){
				tasks.addAll(r.getTask());
			}
		}
		render(tasks);
	}
	
	/**
	 * Get current sprint selectionned
	 * @return
	 */
	static models.Sprint getCurSprint(){
		return models.Sprint.getById(Long.parseLong(session.get("sprintId")));
	}
}
