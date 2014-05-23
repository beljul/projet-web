package controllers;

import models.*;

public class Security extends Secure.Security {
	
	public static boolean authenticate (String email, String password) {		
		return models.User.connect(email, password) != null;
	}			
	static void onAuthenticated(){
		flash.put("message", "Bienvenue sur scrumnch");
		Application.dashboard();
		
	}
	
	static void onDisconnected() {
	    redirect("/");
	}
}
