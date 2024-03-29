package controllers;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Session;

import org.h2.upgrade.DbUpgrade;

import models.Customer;
import models.Developer;
import models.ProductOwner;
import models.Role;
import models.ScrumMaster;
import models.Team;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;


@With(Secure.class)
public class Product extends WrapperController {
	
	/**
	 * Check if a product has been selected
	 */
	@Before(only={"changeRequirementsOrder",
				  "setCurrentReleaseSprint"})
	public static void checkProductSelected(){
		if(!AccessRules.productDefined()){
			HTMLFlash.noProductDefined();
			redirect("/");
		}
	}
	
	/**
	 * Call the creation product page of the application
	 */
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
	
	/**
	 * Check values of fields
	 * @param name
	 * @param sprintDuration
	 */
	private static void registerConsistencies(String name, Integer sprintDuration) {
		/*Check that the values specified are consistent */		
		validation.match(name, RegexPatterns.ALPHA_NUM_EXT).message("le produit doit être alphanumérique");
		validation.range(sprintDuration, 1, 6)
				  .message("un sprint doit durer entre 1 et 6 semaines");
	}
	
	/**
	 * Record a product
	 * @param name
	 * @param description
	 * @param sprintDuration
	 * @param scrumMaster
	 * @param customer
	 * @param developers
	 * @param teamName
	 */
	public static void register(String name, String description, 
								  Integer sprintDuration, String scrumMaster,
								  String customer, Set<String> developers,
								  String teamName) {
		// Verification
		registerRequirements(name, description, sprintDuration,
							scrumMaster, customer, developers, teamName);
		registerConsistencies(name, sprintDuration);

		/* Retrieve not null developers */
		Set<String> notNullDevelopers = new HashSet<String>();
		for(String d : developers){
			if(! d.equals("")) {
				notNullDevelopers.add(d);
			}
		}
		if(notNullDevelopers.size() == 0){
			validation.addError("deveopers", "Au moins un développeur doit être spécifié");
		}
		
		/* Does not continue checking if some errors already exist */
		if(validation.hasErrors()) {
			renderTemplate("Product/create.html",name,description,
							sprintDuration,scrumMaster,customer,notNullDevelopers);
		}
		
		
		/*Check that customer, developers, and scrumMaster exist in the DB */		
		models.User dbScrumMaster = models.User.getByEmail(scrumMaster);

		if(dbScrumMaster == null) {
			validation.addError("scrumMaster", "le scrum master n'existe pas");
		}
		
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
		Team team = new Team("team of product " + name, created);
		Set<models.User> devs = new HashSet<models.User>();
		for(String d : notNullDevelopers){
			models.User dev = models.User.getByEmail(d);
			devs.add(dev);
			team.addMember(dev);
		}
		
		models.User productOwner = models.User.getByEmail(session.get("username"));
		if(notNullDevelopers.isEmpty()) { // Pas de devs saisis
			team = Team.getByname(teamName);
		}
		else {				
			team.addMember(productOwner);
			team.addMember(dbScrumMaster);
			team.addMember(dbCustomer);
		}
		
		team.register();
		
		models.Product product = models.Product.register(name, created, description, sprintDuration, 
						team, dbScrumMaster, productOwner, dbCustomer);
		
		team.addProduct(product);
		team.register();

		// Adding roles
		Role r = new ProductOwner();
		r.add(productOwner, product);
		productOwner.addRole(r, product);
		r.save();

		r = new ScrumMaster();
		r.add(dbScrumMaster, product);
		dbScrumMaster.addRole(r, product);
		r.save();

		r = new Customer();
		r.add(dbCustomer, product);
		dbCustomer.addRole(r, product);
		r.save();

		for (models.User  dev: devs) {
			r = new Developer();
			r.add(dev, product);
			dev.addRole(r, product);
			r.save();
		}
		HTMLFlash.contextual("Le produit " + name + " a été créé", 
				 HTMLFlash.VALIDATION, false);	
		
		//redirect in RSSFlux.add
		RSSFlux.add();		
	}
	
	/**
	 * List all products of the user connected
	 */
	public static void show() {
		String email = session.get("username");
		models.User user = models.User.getByEmail(email);
		Collection<models.Product> products = user.getProducts().values();
		render(email, products);
	}
	
	/**
	 * Set the current product to the given one
	 * and initalize a HTML Flash message
	 * @param id	
	 */
	public static void setCurrentProduct(Long id) {
		models.Product p = Product.__setCurrentProduct(id);
		HTMLFlash.contextual("Nouveau produit courant : " + p.getName(),
							HTMLFlash.INFORMATION, false);
		redirect("/sprint/progression");
	}
	
	/**
	 * Perform the product change in the session variable
	 * @param id
	 * @return
	 */
	static models.Product __setCurrentProduct(Long id){
		
		//Retrieve the product selected
		models.Product p = models.Product.getById(id);
		
		/*Only a team member can access to the product ... */
		if(! p.getTeam().containsMember(session.get("username"))){
			HTMLFlash.notAuthorized();
			redirect("/");
		}
		
		
		//Change the current product
		session.put("productName", p.getName());
		
		//Refresh rules of the product
		AccessRules.refresh();
    	
		models.Version release = p.getCurrentRelease();
		
		/*Remove the previous product session variables */
		session.remove("releaseName");
		session.remove("sprintName");
		session.remove("sprintId");
		
		/*Store the new ones */
		if(release != null) {
			session.put("releaseName", release.getName());
			models.Sprint sprint = release.getCurrentSprint();
			session.put("sprintName", sprint);
			session.put("sprintId", sprint.getId());
		}
		else {
			HTMLFlash.contextual("Aucune release trouvée.", HTMLFlash.ERROR, false);
		}
		return p;
	}
	
	/**
	 * Modify order (priority) of current product's requirements
	 * @param requirements : map of requirement's id and priority corresponding
	 */
	public static void changeRequirementsOrder(Map<Long,Integer> requirements){
		models.Product curProd = models.Product.getByName(session.get("productName"));		
		for(models.Requirement r : curProd.getRequirements()){						
			if(requirements.containsKey(r.getId())) {			
				r.setPriority(requirements.get(r.getId()));
				r.register();
			}
		}
		curProd.register();
	}
	
	/**
	 * Set current session variables of release and sprint
	 * thanks to the tree
	 * @param releaseName : name of release chosen
	 * @param sprintID : id of the sprint chosen
	 */
	public static void setCurrentReleaseSprint(String releaseName, Long sprintID) {
		session.put("releaseName", releaseName);
		session.put("sprintName", models.Sprint.getById(sprintID));
		session.put("sprintId", sprintID);
	}

}
