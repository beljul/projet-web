package controllers;

public abstract class HTMLFlash {
	/**
	 * Definie message styles available
	 */
	public static final FlashStyle VALIDATION = FlashStyle.flashValidation;
	public static final FlashStyle ERROR = FlashStyle.flashError;
	public static final FlashStyle WARNING = FlashStyle.flashWarning;
	public static final FlashStyle INFORMATION = FlashStyle.flashInformation;
	
	/*A generic authorization error message */
	public static final String AUTH_ERR = "Vous n'êtes pas authorisé à accéder à cette fonctionnalité";
	/*A generic sprint not selected error message */
	public static final String SPRINT_ERR = "Veuillez sélectionner un sprint";
	public static final String PROD_ERR = "Veuillez sélectionner un produit";
	public enum FlashStyle {
		  flashValidation,
		  flashError,
		  flashWarning,
		  flashInformation;	
		  public String toString() {
			return this.name();			  
		  }
	}
	private static void init(String message, FlashStyle style,
								boolean closable, boolean window){		
		play.mvc.Scope.Flash flash = play.mvc.Scope.Flash.current();
		flash.put("message", message);
		flash.put("messageStyle", style.toString());
		if (closable) {
			flash.put("messageClosable", closable);
		}
		else {
			flash.remove("messageClosable");
		}
		if (window) {
			flash.put("messageWindow", window);
		}
			
	}
	/**
	 * Initialize a contextual flash message. 
	 * @param message the content of the message
	 * @param style   the message style (validation,error ...)
	 * @param closable If true, a link will allow to close the message
	 * 					If false, the message will disappeared automaticaly
	 * 							  after a few seconds
	 */
	public static void contextual(String message, FlashStyle style,
									boolean closable){
		init(message, style, closable,false);
	}
	/**
	 * Initialize a screen flash message for the next HTTP request
	 * @param message the content of the message
	 * @param style   the message style (validation,error ...)
	 * @param closable If true, a link will allow to close the message
	 * 					If false, the message will disappeared automaticaly
	 * 							  after a few seconds
	 */
	public static void screen(String message, FlashStyle style,
								boolean closable){
		init(message, style, closable,true);
	}
	
	/**
	 * Cancel a previous initialized message
	 */
	public static void cancelFlash(){
		play.mvc.Scope.Flash flash = play.mvc.Scope.Flash.current();
		flash.remove("message");
		flash.remove("messageStyle");
		flash.remove("messageClosable");
		flash.remove("messageWindow");
	}
	
	/**
	 * Generate a flash message for non authorized access
	 */
	public static void notAuthorized(){
		HTMLFlash.screen(HTMLFlash.AUTH_ERR, HTMLFlash.ERROR, false);
	}
	
	/**
	 * Generate a flash message for product not selected error
	 */
	public static void noProductDefined(){
		HTMLFlash.contextual(HTMLFlash.PROD_ERR, HTMLFlash.ERROR, false);
	}
	/**
	 * Generate a flash message for sprint not defined error
	 */
	public static void noSprintDefined(){
		HTMLFlash.contextual(HTMLFlash.SPRINT_ERR, HTMLFlash.ERROR, false);
	}
	/**
	 * Determine if a Flash message has already been set
	 * @return true if a message has already been initialized, false otherwise
	 */
	public static boolean present(){
		play.mvc.Scope.Flash flash = play.mvc.Scope.Flash.current();
		return flash.get("message") != null;
	}
}