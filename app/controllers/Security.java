package controllers;

import models.*;

public class Security extends Secure.Security {
	
	public static boolean authenticate (String email, String password) {
		System.out.println(email);
		System.out.println(password);
		return models.User.connect(email, password) != null;
	}
	
	static void onDisconnected() {
	    Application.index();
	}
}
