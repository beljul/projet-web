package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import models.Sprint;
import play.libs.Crypto.HashType;
import play.mvc.With;

@With(Secure.class)
public class Version extends WrapperController {
	
	/**
	 * Call add release page of the application
	 */
	public static void add() {
		int nbSprints = 4;
		/* Avoid release selection asking message */
		HTMLFlash.cancelFlash();
		render(nbSprints);		
	}
	
	/**
	 * Create and record a new release
	 * @param release
	 * @param nbSprints
	 */
	public static void register(String release, Integer nbSprints) {
		// Verification
		validation.required(release);
		validation.min(nbSprints, 1).message("Au moins 1 sprint par release");
		validation.max(nbSprints, 10).message("Maximum 10 sprints par release");
		if(validation.hasErrors()){
			renderTemplate("Version/add.html",release, nbSprints);
		}
		
		// Get current product and sprint duration
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		Integer sprintDuration = product.getSprintDuration();
		
		// Build the end and the begin date of the release
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(created);
		
		// Create and add sprints according to the number specified in parameter
		models.Version version = new models.Version(release, created);
		for (int i = 0; i < nbSprints; i++) {
			calendar.add(Calendar.DATE, sprintDuration);
			Date finished = new Date(calendar.getTimeInMillis());
			models.Sprint sprint = new models.Sprint(created, finished);
			created = finished;
			sprint.setRelease(version);
			version.save();
			sprint.register();
			version.addSprint(sprint);
		}
		
		// Record
		version.setProduct(product);
		version.register();
		
		product.addRelease(version);
		product.save();
		Product.__setCurrentProduct(product.getId());
		
		//Prepare a flash message for the next request
		HTMLFlash.contextual("La release " + release + " a été créée",
						 HTMLFlash.VALIDATION, false);
		
		redirect("/Application/dashboard");
	}
}
