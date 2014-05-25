package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.mvc.Controller;

public class Sprint extends Controller {
	
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
		Set<models.Requirement> requirementsUnassigned = product.getRequirements();

		for (models.Version v : product.getReleases()) {
			for (models.Sprint sp : v.getSprints()) {
				requirementsUnassigned.removeAll(sp.getRequirements());
			}
		}
		
		List<JsonSearchItem> requirementsAvailable = new ArrayList<JsonSearchItem>();		
		for (models.Requirement requirement : requirementsUnassigned) {
			String content = requirement.getContent();
			JsonSearchItem jsi = new JsonSearchItem(requirement.getId(), content);
			requirementsAvailable.add(jsi);
		}
		renderJSON(requirementsAvailable);
	}
}
