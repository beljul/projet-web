package controllers;

import play.mvc.Controller;

public class User extends Controller {

	public static void register(String email, String name, String firstname, 
								String password, String secondPassword) {		
		
		validation.required(name);
	    validation.required(email);
	    validation.required(firstname);
	    validation.required(password);
	    validation.required(secondPassword);
	    
	     if(validation.hasErrors()) {
	         for(play.data.validation.Error error : validation.errors()) {
	             System.out.println(error.message());
	         }
	         System.out.println("blabllaba");
	         renderTemplate("Secure/login.html",name,email,firstname,password,secondPassword);
	         System.out.println("blibliblib");
	     }
	}

}
