package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Set;

import play.mvc.With;


@With(Secure.class)
public class Task extends WrapperController {
	
	static public void add() {
		String productName = session.get("productName");
		Set<models.Requirement> requirements = models.Product.getByName(productName).getRequirements();
		render(requirements);
	}
	
	static public void register(String req, ArrayList<String> descriptions, 
								ArrayList<Integer> durations, ArrayList<Integer> prioritys) {
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		
		models.Requirement requirement = models.Requirement.getByContent(req);
		Integer currentId = models.Task.getCurrentId(requirement);
		
		System.out.println(req);
		for (int i = 0; i < descriptions.size(); i++) {
			System.out.println(descriptions.get(i) + " - " + prioritys.get(i) + " - " + durations.get(i) + " - " + (i+currentId));
			models.Task task = new models.Task(descriptions.get(i), i+currentId, durations.get(i), created, prioritys.get(i));
			task.register();
			requirement.addTask(task);
		}
		requirement.register();
		HTMLFlash.contextual("De nouvelles tâches ont été ajoutées", HTMLFlash.VALIDATION, false);
		redirect("/Application/dashboard");
	}

}
