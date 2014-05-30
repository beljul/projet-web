package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import play.mvc.Controller;
import play.mvc.With;

public class User extends WrapperController {

	public static void register(String email, String name, String firstname, 
								String firstPassword, String secondPassword) {		
		
		validation.required(email);
		validation.required(name);
		validation.match(name, "^[a-zA-Z ']+$").message("Name must be alphanumeric");
	    validation.required(firstname);
	    validation.match(firstname, "^[a-zA-Z]+$").message("Firstname must be alphanumeric");
	    validation.required("password", firstPassword);
	    validation.minSize(firstPassword, 6);
	    validation.required("password check",secondPassword);
	    validation.email(email);
	    
	    
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
	    	 redirect("/Application/dashboard");
	    	 
	         //renderTemplate("Application/dashboard.html",name,email,firstname,firstPassword,secondPassword);
	     }
	}

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
	
	public static boolean addSkills(Set<String> skills) {
		models.User user = models.User.getByEmail(session.get("username"));
		Set<models.Skill> newSkills= new HashSet<models.Skill>();
		for (String skill : skills) {
			if(skill != null && !skill.equals("")) {
				models.Skill s = new models.Skill(skill);
				s.save();
				newSkills.add(s);
			}
		}
		user.setSkills(newSkills);
		user.register();
		return true;
	}
	
	public static models.User getCurrentUser() {
		return models.User.getByEmail(session.get("username"));
	}

	public static void profile() {
		models.User user = models.User.getByEmail(session.get("username"));
		render(user);
	}
}
