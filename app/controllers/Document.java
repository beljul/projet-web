package controllers;

import java.util.HashSet;
import java.util.Set;

import play.mvc.With;

@With(Secure.class)
public class Document extends WrapperController {
	
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
}
