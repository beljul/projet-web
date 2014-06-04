package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.SprintRetrospective;
import play.mvc.With;

@With(Secure.class)
public class Document extends WrapperController {
	/**
	 * Call stats page of the application
	 */
	public static void chart() {
		render();
	}
	
	/**
	 * Get documents according to the selectionned sprint
	 * Call printing documents page of the application
	 */
	public static void show() {
		// Verification
		models.Sprint sprint = models.Sprint.getById(Long.parseLong(session.get("sprintId")));
		validation.required(sprint).message("Aucun sprint sélectionné");
		if(validation.hasErrors()){
			redirect("/");
		}
		// Get documents
		Set<models.Document> documents = sprint.getDocuments();
		render(documents);
	}
	
	/**
	 * Function which verify if user has authorization to access to the page
	 */
	public static void create(){
		if(AccessRules.isDev()){
			HTMLFlash.notAuthorized();
			redirect("/");
		}
		render();
	}
	
	/**
	 * Function which record a new document
	 * @param type : can be daily scrum, sprint retrospective or sprint review
	 * @param document : content of the futur document
	 */
	public static void save(String type, String document){
		// Verification
		if(AccessRules.isDev()){
			HTMLFlash.notAuthorized();
			redirect("/");
		}
		
		// Get current user, sprint
		String msg;
		models.User curUser = controllers.User.getCurrentUser();
		models.Sprint curSprint = controllers.Sprint.getCurSprint();
		models.Document d = null;
		// Create the document...
		if("daily".equals(type)){
			d = new models.DailyScrum(document, curUser, curSprint);
		}
		else if("retrospective".equals(type)){
			d = new models.SprintRetrospective(document, curUser, curSprint);
		}
		else if("review".equals(type)){
			d = new models.SprintReview(document,curUser, curSprint);
		}
		else {
			msg = "Une erreur est survenue lors de l'enregistrement du document";
			HTMLFlash.screen(msg, HTMLFlash.ERROR, true);
			redirect("/document/create");
		}
		//... and record it
		curSprint.addDocument(d);
		curUser.addDocument(d);
		d.register();
		curUser.register();
		curSprint.register();
		msg = "Document sauvegardé";
		HTMLFlash.contextual(msg, HTMLFlash.VALIDATION, false);
		redirect("/");
	}
	
	/**
	 * Get requirements according to product, used for chart's creation.
	 */
	public static void getRequirements() {
		Map<String, Integer> requirements = new HashMap<String, Integer>();
		models.Product p = models.Product.getByName(session.get("productName"));
		for (models.Requirement r : p.getRequirements()) {
			requirements.put(r.getContent(), r.getDuration());
		}
		// Return a map of <Requirement content, requirement duration)
		renderJSON(requirements);
	}
}
