package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import play.mvc.Controller;
import play.mvc.With;

public class User extends WrapperController {

	/**
	 * Create and record a new user during subscribing
	 * Informations about new user : 
	 * @param email
	 * @param name
	 * @param firstname
	 * @param firstPassword
	 * @param secondPassword
	 */
	public static void register(String email, String name, String firstname, 
								String firstPassword, String secondPassword) {		
		// Verification
		validation.required(email);
		validation.required(name);
		validation.match(name, "^[a-zA-Z ']+$").message("Name must be alphanumeric");
	    validation.required(firstname);
	    validation.match(firstname, "^[a-zA-Z]+$").message("Firstname must be alphanumeric");
	    validation.required("password", firstPassword);
	    validation.minSize(firstPassword, 6);
	    validation.required("password check",secondPassword);
	    validation.email(email);
	    // Password verification
	    play.data.validation.Error e = validation.equals("password", firstPassword, "password verif", secondPassword).error;

	     if(validation.hasErrors()) {
	         for(play.data.validation.Error error : validation.errors()) {
	             System.out.println(error.message());	             
	         }	         
	         renderTemplate("Secure/login.html",name,email,firstname,firstPassword,secondPassword);
	         
	     }
	     else {
	    	 //Record new user
	    	 models.User.register(email, name, firstname, firstPassword, secondPassword);
	    	 //Connect the user 
	    	 session.put("username", email);	    	 
	    	 redirect("/");
	    }
	}

	/**
	 * Class in order to return a JSON <label, name, value>
	 */
	private static class JsonSearchItem {
		private String label;
		private String name;
		private long value;		
		private JsonSearchItem(String label, String name, long value){
			this.label = label;
			this.value = value;
			this.name = name;
		}
	};
	
	/**
	 * Return JSON for ajax request in order to find
	 * users whose email begins as search in the input
	 * @param term : what current user is writing 
	 */
	public static void jsonSearch(String term) {
		List<models.User> users = models.User.getByBeginOfEmail(term);
		List<JsonSearchItem> items = new ArrayList<JsonSearchItem>();		
		for (models.User user : users) {
			String name = "(" + user.getFirstName() + " " + user.getName() + ")";
			JsonSearchItem jsi = new JsonSearchItem(user.getEmail(), name, user.getId());
			items.add(jsi);
		}		
		renderJSON(items);
	}
	
	/**
	 * Add skills to the current user
	 * Skills are from linkedin API
	 * @param skills
	 * @return
	 */
	public static boolean addSkills(Set<String> skills) {
		models.User user = models.User.getByEmail(session.get("username"));
		Set<models.Skill> newSkills= new HashSet<models.Skill>();
		// Add and record skills
		for (String skill : skills) {
			// Verification skill is not null or empty
			if(skill != null && !skill.equals("")) {
				models.Skill s = new models.Skill(skill);
				s.save();
				newSkills.add(s);
			}
		}
		user.setSkills(newSkills);
		user.register();
		// TODO: If we don't return something, linkedin API crash, don't know why.
		return true;
	}
	
	/**
	 * Get current user
	 * @return
	 */
	public static models.User getCurrentUser() {
		return models.User.getByEmail(session.get("username"));
	}

	/**
	 * Call profile page of the current user
	 */
	public static void profile() {
		models.User user = getCurrentUser();
		render(user);
	}
}
