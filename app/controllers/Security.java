package controllers;

import models.*;

public class Security extends Secure.Security {
	
	public static boolean authenticate (String email, String password) {		
		return models.User.connect(email, password) != null;
	}			
	static void onAuthenticated(){
		HTMLFlash.contextual("Bienvenue sur scrumnch", HTMLFlash.INFORMATION, false);
		Application.index();		
	}
	
	static void onDisconnected() {
	    redirect("/");
	}
}
