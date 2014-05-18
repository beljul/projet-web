package controllers;

public abstract class RegexPatterns {
	public static final String ALPHA_NUM = "^[a-zA-Z0-9]+$";
	public static final String ALPHA = "^[a-zA-Z]+$";
	public static final String NUM = "^[0-9]+$";
	public static final String ALPHA_EXT = "^[a-zA-Z -']+$";
	public static final String ALPHA_NUM_EXT = "^[a-zA-Z0-9 '\\-]+$";
	public static final String NAME = "^[a-zA-Z -']+$";
	public static final String FIRSTNAME = "^[a-zA-Z-]+$";
	
}