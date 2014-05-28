package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.mvc.With;

@With(Secure.class)
public class Sprint extends WrapperController {
	
	public static void addRequirements (Set<Long> requirements) {
		Long sprintId = Long.parseLong(session.get("sprintId"));
		models.Sprint currentSprint = models.Sprint.getById(sprintId);
		for (Long id : requirements) {
			currentSprint.addRequirement(models.Requirement.getById(id));
		}
		currentSprint.register();
	}
	
	private static class JsonSearchItem {
		private long id;		
		private String content;
		private JsonSearchItem(long id, String content){
			this.id = id;
			this.content = content;
		}
	};
	
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
	
	public static void getRequirementsAvailable() {
		// A définir dans une méthode ?
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
	/*
	 * Save the progression of the current sprint
	 */
	static public void saveProgression(Map<Long,models.Task.TaskState> states,
										 Map<Long,Integer> winrates){
		 models.Sprint s = models.Sprint.getById(Long.parseLong(session.get("sprintId")));
		 s.saveTasks(states, winrates);
	}
}
