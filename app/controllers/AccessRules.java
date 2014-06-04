package controllers;

public abstract class AccessRules {
	
	/**
	 * Define some session variables name
	 */
	//On User rules
	private static final String SESSION_IS_PO = "isPO";
	private static final String SESSION_IS_SM = "isSM";
	private static final String SESSION_IS_DEV = "isDev";
	private static final String SESSION_NOT = "false";
	private static final String SESSION_IS = "true";
	//On product informations
	private static final String SESSION_SPRINT = "sprintId";
	private static final String SESSION_RELEASE = "releaseName";
	private static final String SESSION_PRODUCT = "productName";
	/**
	 * Refresh the rules with the current product 
	 */
	public static void refresh() {
		play.mvc.Scope.Session session =  play.mvc.Scope.Session.current();
		AccessRules.refresh(session.get("productName"));
	}
	
	/**
	 * Refresh rules considering a given product name
	 * @param productName
	 */
	private static void refresh(String productName) {
		play.mvc.Scope.Session session =  play.mvc.Scope.Session.current();
		AccessRules.reset();
		models.Product p = models.Product.getByName(productName);
		models.User curUser = models.User.getByEmail(session.get("username"));
		if(p != null) {
			session.put(SESSION_IS_PO, p.getProductOwner().equals(curUser)?
										SESSION_IS:SESSION_NOT);
			session.put(SESSION_IS_SM, p.getScrumMaster().equals(curUser)?
										SESSION_IS:SESSION_NOT);
	    	/* 
	    	 * To verify if current user is a developer of current product
	    	 * He has to be in the team and he can't be the scrum master,
	    	 * product owner or customer at the same time
	    	 */
			boolean isDevelopper = false;
			if(p.getTeam().containsMember(curUser) 
					&& !p.getScrumMaster().equals(curUser)
					&& !p.getProductOwner().equals(curUser)
					&& !p.getCustomer().equals(curUser)) {
				session.put(SESSION_IS_DEV, SESSION_IS);
				isDevelopper = true;
			}
			/*if(!p.getProductOwner().equals(curUser) && !isDevelopper)
				session.put(SESSION_NOT_DEV_NOR_PO, "disabled");*/
		}
	}
	
	/**
	 * Delete all rules session variables
	 */
	private static void reset(){
		play.mvc.Scope.Session session =  play.mvc.Scope.Session.current();
		session.remove(SESSION_IS_PO);
		session.remove(SESSION_IS_SM);
		session.remove(SESSION_IS_DEV);		
	}
	
	/**
	 * Determine if the current user is the product owner of the product
	 * @return true if the current user is PO, false otherwise
	 */
	public static boolean isPO(){
		play.mvc.Scope.Session session =  play.mvc.Scope.Session.current();
		play.mvc.Scope.Session session2 = play.mvc.Scope.Session.current();
		System.out.println(session2.get("productName"));
		return session.get(SESSION_IS_PO) != null 
				&& session.get(SESSION_IS_PO).equals(SESSION_IS);
	}
	
	/**
	 * Determine if the current user is the scrum master of the product
	 * @return true if it is the scrum master, false otherwise
	 */
	public static boolean isSM(){
		play.mvc.Scope.Session session =  play.mvc.Scope.Session.current();
		return session.get(SESSION_IS_SM) != null 
				&& session.get(SESSION_IS_SM).equals(SESSION_IS);
	}
	
	/**
	 * Determine if the current user is a developer of the product
	 * @return true if it is a developer, false otherwise
	 */
	public static boolean isDev(){
		play.mvc.Scope.Session session =  play.mvc.Scope.Session.current();
		return session.get(SESSION_IS_DEV) != null
				&& session.get(SESSION_IS_DEV).equals(SESSION_IS);
	}
	
	/**
	 * Determine if a sprint have been selected
	 * @return true if a sprint is selected
	 */
	public static boolean sprintDefined(){
		play.mvc.Scope.Session session =  play.mvc.Scope.Session.current();
		return session.get(SESSION_RELEASE) != null
				&& session.get(SESSION_SPRINT) != null;
	}
	/**
	 * Generate a css class for product owner access
	 * @return
	 */
	public static String cssAccessPO(){
		return isPO()?"":"disabled";
	}
	
	/**
	 * Generate a css class for the scrum master access
	 * @return
	 */
	public static String cssAccessSM(){
		return isSM()?"":"disabled";
	}
	
	/**
	 * Genereate a css class for the developer access
	 * @return
	 */
	public static String cssAccessDev(){
		return isDev()?"":"disabled";
	}
	
	public static String cssAccessAll(){
		return isDev() || isSM() || isPO() ? "" : "disabled";
	}
	/**
	 * Genereate a css class for the scrum master access 
	 * or the Product owner one
	 * @return
	 */
	public static String cssAccessSMOrPO(){
		return isPO() || isSM() ? "" : "disabled";
	}
	/**
	 * Generate a css class for the developer access or the Product owner one
	 * @return
	 */
	public static String cssAccessDevOrPO(){
		return isDev() || isPO() ? "" : "disabled";
	}
	
}