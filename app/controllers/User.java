package controllers;

import play.mvc.Controller;

public class User extends Controller {

	public static void register(String email, String name, String firstname, 
								String password, String secondPassword) {
		models.User user = new models.User(name, firstname, email, password);
		user.save();
	}

}
