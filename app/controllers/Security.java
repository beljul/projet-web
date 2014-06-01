package controllers;

import models.*;

public class Security extends Secure.Security {
	
	public static boolean authenticate (String email, String password) {
		if(models.User.connect(email, password) != null)
			return true;
		else
			HTMLFlash.screen("Echec d'authentification", HTMLFlash.ERROR, true);
		return false;
	}			
	static void onAuthenticated(){
		HTMLFlash.contextual("Bienvenue sur scrumnch", HTMLFlash.INFORMATION, false);
		Application.index();		
	}
	
	static void onDisconnected() {
	    redirect("/");	 
	}
}
