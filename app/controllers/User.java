package controllers;

import play.mvc.Controller;

public class User extends Controller {

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
	    	 session.put("username", name);
	    	 redirect("/Application/dashboard");	    	 
	         //renderTemplate("Application/dashboard.html",name,email,firstname,firstPassword,secondPassword);
	     }
	}

}
