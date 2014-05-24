package controllers;

public abstract class HTMLFlash {
	/**
	 * Definie message styles available
	 */
	public static final FlashStyle VALIDATION = FlashStyle.flashValidation;
	public static final FlashStyle ERROR = FlashStyle.flashError;
	public static final FlashStyle WARNING = FlashStyle.flashWarning;
	public static final FlashStyle INFORMATION = FlashStyle.flashInformation;
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
		if(closable) {
			flash.put("messageClosable", closable);
		}
		if(window){
			flash.put("messageWindow", window);
		}
	}
	/**
	 * Initialize a contextual flash message 
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
}