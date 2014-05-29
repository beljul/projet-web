package controllers;

import play.mvc.With;

@With(Secure.class)
public class Document extends WrapperController {
	
	public static void chart() {
		render();
	}
}
