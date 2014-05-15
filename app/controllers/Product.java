package controllers;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.mail.Session;

import models.Customer;
import models.Developer;
import models.ProductOwner;
import models.ScrumMaster;
import models.Team;
import play.mvc.Controller;

public class Product extends Controller {

	public static void create(){
		render();
	}
	public static void register(String name, String description, Integer sprintDuration, 
			Integer idScrumMaster, Integer idCustomer, 
			Set<Integer> idDevelopers, String teamName) {		
		
		validation.required(name);
		validation.required(description);
		validation.required(sprintDuration);
		validation.required(idScrumMaster);
		validation.required(idCustomer);
		validation.required(teamName);

		validation.match(name, "^[0-9a-zA-Z ']+$").message("Product name must be alphanumeric");
		validation.match(name, "^[0-9a-zA-Z ']+$").message("Team name must be alphanumeric");
		validation.range(sprintDuration, 1, 5).message("A sprint can last between 1 and 5 weeks.");
		validation.match(idScrumMaster, "^[0-9]+$").message("It isn't an ID.");
		validation.match(idCustomer, "^[0-9]+$").message("It isn't an ID.");

		 if(validation.hasErrors()) {
	         for(play.data.validation.Error error : validation.errors()) {
	             System.out.println(error.message());	             
	         }	         
	         renderTemplate("Product/create.html",name,description,sprintDuration,teamName);
	    }
		 
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		
		ProductOwner productOwner = (ProductOwner) ProductOwner.getByEmail(session.get("username"));
		ScrumMaster scrumMaster = (ScrumMaster) ScrumMaster.getById(idScrumMaster);
		Customer customer = (Customer) Customer.getById(idCustomer);
		
		Team team;
		if(idDevelopers.isEmpty()) {
			team = Team.getByname(teamName);
		}
		else {
			team = new Team(teamName, created);

			for (Integer id : idDevelopers) {
				team.addMember(Developer.getById(id));
			}
			team.addMember(productOwner);
			team.addMember(scrumMaster);
			team.addMember(customer);
		}
		
		models.Product.register(name, created, description, sprintDuration, 
				team, scrumMaster, productOwner, customer);
		
		redirect("/Application/dashboard");	    	 
	}
}
