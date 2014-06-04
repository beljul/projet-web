package controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.HTML;

import models.SprintRetrospective;
import play.mvc.Before;
import play.mvc.With;

@With(Secure.class)
public class Document extends WrapperController {
	
	/**
	 * Check that method show is disabled for developers
	 */
	@Before(only="show") 
	public static void checkPOOrSM(){
		if(!AccessRules.isPO() && !AccessRules.isSM()) {
			HTMLFlash.notAuthorized();
			redirect("/");
		}
		if(!AccessRules.sprintDefined()){
			HTMLFlash.noSprintDefined();
			redirect("/");
		}
	}
	
	/**
	 * Check that other method are only for PO
	 */
	@Before(unless="show")
	public static void checkPO(){		
		if(!AccessRules.isPO()) {
			HTMLFlash.notAuthorized();
			redirect("/");
		}
		//PO is the only user who can create a document
		if(!request.actionMethod.equals("create") && !AccessRules.sprintDefined()) {
			HTMLFlash.noSprintDefined();
			redirect("/");
		}
	}
	
	public static void chart() {
		render();
	}
	public static void show() {
		models.Sprint sprint = models.Sprint.getById(Long.parseLong(session.get("sprintId")));
		validation.required(sprint).message("Aucun sprint sélectionné");
		if(validation.hasErrors()){
			redirect("/");
		}
		Set<models.Document> documents = sprint.getDocuments();
		render(documents);
	}
	
	public static void create(){
		if(AccessRules.isDev()){
			HTMLFlash.notAuthorized();
			redirect("/");
		}
		render();
	}
	
	public static void save(String type, String document){
		if(AccessRules.isDev()){
			HTMLFlash.notAuthorized();
			redirect("/");
		}
		String msg;
		models.User curUser = controllers.User.getCurrentUser();
		models.Sprint curSprint = controllers.Sprint.getCurSprint();
		models.Document d = null;
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
		curSprint.addDocument(d);
		curUser.addDocument(d);
		d.register();
		curUser.save();
		curSprint.save();
		msg = "Document sauvegardé";
		HTMLFlash.contextual(msg, HTMLFlash.VALIDATION, false);
		redirect("/");
	}
	
	public static void getRequirements() {
		Map<String, Integer> requirements = new HashMap<String, Integer>();
		models.Product p = models.Product.getByName(session.get("productName"));
		for (models.Requirement r : p.getRequirements()) {
			requirements.put(r.getContent(), r.getDuration());
		}
		renderJSON(requirements);
	}
}
