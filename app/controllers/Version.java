package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import models.Sprint;
import play.mvc.With;

@With(Secure.class)
public class Version extends WrapperController {
	
	public static void add() {
		render();
	}
	
	public static void register(String release, Integer nbSprints) {
		System.out.println(release);
		System.out.println(nbSprints);
		String productName = session.get("productName");
		models.Product product = models.Product.getByName(productName);
		Integer sprintDuration = product.getSprintDuration();
		
		// Calcule de la date de début et de fin
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(created);
		
		System.out.println("*****************************");
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
			System.out.println(sprint.getCreated());
		}
		System.out.println("*****************************");

		version.setProduct(product);
		version.register();
		
		product.addRelease(version);
		product.save();
		Product.setCurrentProduct(product.getId());

		flash.put("message", "La release " + release + " a été créée");
		flash.put("messageStyle", "validation");
		redirect("/Application/dashboard");
	}
}
