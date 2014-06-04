package controllers;

import models.*;

public class Security extends Secure.Security {
	
	/**
	 * Verify user is already subscribed
	 * @param email
	 * @param password
	 * @return
	 */
	public static boolean authenticate (String email, String password) {
		if(models.User.connect(email, password) != null)
			return true;
		else
			HTMLFlash.screen("Echec d'authentification", HTMLFlash.ERROR, true);
		return false;
	}
	
	/**
	 * If authentification is a success call the welcome page
	 */
	static void onAuthenticated(){
		HTMLFlash.contextual("Bienvenue sur scrumnch", HTMLFlash.INFORMATION, false);
		Application.index();		
	}
	
	/**
	 * When user is disconnected call the page of sign up / sign in
	 */
	static void onDisconnected() {
	    redirect("/");	 
	}
}
