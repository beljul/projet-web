package controllers;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.mail.Session;

import org.h2.upgrade.DbUpgrade;

import models.Customer;
import models.Developer;
import models.ProductOwner;
import models.ScrumMaster;
import models.Team;
import play.mvc.Controller;

public class Product extends Controller {

	public static void create() {
		render();
	}
	/**
	 * Check that all fields have been specified
	 * @param name
	 * @param description
	 * @param sprintDuration
	 * @param scrumMaster
	 * @param customer
	 * @param developers
	 * @param teamName
	 */
	private static void registerRequirements(String name, String description, 
								  Integer sprintDuration, String scrumMaster,
								  String customer, Set<String> developers,
								  String teamName){
		/*Check that all fields have been specified */
		validation.required(name).message("nom du projet requis");
		validation.required(description).message("description requise");
		validation.required(sprintDuration).message("durée de sprint requise");
		validation.required(scrumMaster).message("scrum master requis");
		validation.required(customer).message("interlocuteur client requis");
		validation.required(developers).message("Au moins un developpeur doit être spécifié");
		
	}
	private static void registerConsistencies(String name, String description, 
			  									Integer sprintDuration, String scrumMaster,
			  									String customer, Set<String> developers,
			  									String teamName) {
		/*Check that the values specified are consistent */		
		validation.match(name, RegexPatterns.ALPHA_NUM_EXT).message("le produit doit être alphanumérique");
		validation.range(sprintDuration, 1, 6)
				  .message("un sprint doit durer entre 1 et 6 semaines");
	}
	public static void register(String name, String description, 
								  Integer sprintDuration, String scrumMaster,
								  String customer, Set<String> developers,
								  String teamName) {
				
		registerRequirements(name, description, sprintDuration,
							scrumMaster, customer, developers, teamName);
		registerConsistencies(name, description, sprintDuration, 
							scrumMaster, customer, developers, teamName);

		/*Retrieve not null developers */
		Set<String> notNullDevelopers = new HashSet<String>();
		for(String d : developers){
			if(! d.equals("")) {
				notNullDevelopers.add(d);
			}
		}
		if(notNullDevelopers.size() == 0){
			validation.addError("deveopers", "Au moins un développeur doit être spécifié");
		}
		
		/*Does not continue checking if some errors already exist*/
		if(validation.hasErrors()) {
			renderTemplate("Product/create.html",name,description,
							sprintDuration,scrumMaster,customer,notNullDevelopers);
		}
		
		
		/*Check that customer, developers, and scrumMaster exist in the DB */		
		//models.ScrumMaster dbScrumMaster = ScrumMaster.getByEmail(scrumMaster);
		models.User dbScrumMaster = models.User.getByEmail(scrumMaster);

		if(dbScrumMaster == null) {
			validation.addError("scrumMaster", "le scrum master n'existe pas");
		}
		
		//models.Customer dbCustomer = Customer.getByEmail(customer);
		models.User dbCustomer = models.User.getByEmail(customer);
		if(dbCustomer == null) {
			validation.addError("scrumMaster", "le client n'existe pas");
		}
		
		if(validation.hasErrors())
			renderTemplate("Product/create.html",name,description,
							sprintDuration,scrumMaster,customer,notNullDevelopers);
						
		/*Check consistency in the form (customer !=  developer != PO) */ 
		for(String d : notNullDevelopers){
			if(d.equals(customer)){
				validation.addError("developers[]", "Un developpeur ne peux être client");
			}
			if(d.equals(session.get("username"))){
				validation.addError("developers[]", "Vous ne pouvez être développeur et PO");
			}
			
		}
		
		/*Does not continue checking if some errors already exist*/
		if(validation.hasErrors()) {
			renderTemplate("Product/create.html",name,description,
							sprintDuration,scrumMaster,customer,notNullDevelopers);
		}
		
		/*Data base insertion*/
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());		
		Team team = new Team(teamName, created);
		
		for(String d : notNullDevelopers){
			//Developer dev = (Developer) models.User.getByEmail(d);
			models.User dev = models.User.getByEmail(d);
			team.addMember(dev);
		}
		
		//ProductOwner productOwner = ProductOwner.getByEmail(session.get("username"));
		models.User productOwner = models.User.getByEmail(session.get("username"));
		if(notNullDevelopers.isEmpty()) {
			team = Team.getByname(teamName);
		}
		else {				
			team.addMember(productOwner);
			team.addMember(dbScrumMaster);
			team.addMember(dbCustomer);
		}
		
		models.Product.register(name, created, description, sprintDuration, 
						team, dbScrumMaster, productOwner, dbCustomer);
				
		redirect("/Application/dashboard");	    	 
	}
}
