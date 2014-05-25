package unit.models;
import org.junit.*;

import java.sql.Date;
import java.util.*;

import play.test.*;
import models.*;

public class UserTest extends UnitTest {
	@Before
	public void init(){
		Fixtures.deleteAllModels();
		Fixtures.loadModels("data/user.yml");
	}
	
	@Test
	public void connect() {
		String pwd = "superbob";
		User sm = new User("leponge", "bob", 
							"bob.leponge@gmail.com", pwd);
		sm.save();
		
	    // A failed authentication	    
	    User uFailed = sm.connect("bob.leponge@gmail.com",pwd + 404);
	    assertNull(uFailed);	    
	    
	    // A good authentication
	    User uSuccess = sm.connect("bob.leponge@gmail.com",pwd);
	    assertNotNull(uSuccess);
	    	    
	}
	@Test
	public void find(){
	    String pwd = "bobbob";
	    models.User u = models.User
	    					  .find("byEmail","bob1@gmail.com")
	    					  .first();
	    assertEquals(u.getFirstName(), "bob");
	    assertEquals(u.getName(), "bob");
	    assertEquals(u.getPassword(), pwd);
	    assertEquals(u.getEmail(),"bob1@gmail.com");
	    
	    u = models.User.find("byEmail","notFound").first();
	    assertNull(u);
	}
	@Test
	public void register(){

		String firstname = "patrick";
		String name = "letoile";
		String email = "patrick.letoile42@gmail.com";
		String pwd = "root";
		String pwdConfirm = "root";
		
		User uSuccess = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNotNull(uSuccess);	
	}
	@Test
	public void getByBeginOfEmail(){
		List<models.User> users = models.User.getByBeginOfEmail("bob");
		assertEquals(5, users.size());
		users = models.User.getByBeginOfEmail("bobdylan");
		assertEquals(0, users.size());
		users = models.User.getByBeginOfEmail("bob1");
		assertEquals(2,users.size());
	}
	@Test
	public void addRole(){
		//Retrieve some users defined in th Fixtures
//		Map<String, Object> users = Fixtures.idCache;		
//		models.User bob1 = User.findById(users.get("models.User-bob1"));
//		models.User bob2 = User.findById(users.get("models.User-bob2"));
//		models.User bob3 = User.findById(users.get("models.User-bob3"));
//		models.User bob4 = User.findById(users.get("models.User-bob4"));		
//		
//		//Build a data object
//		java.util.Calendar cal = java.util.Calendar.getInstance();
//		java.util.Date utilDate = cal.getTime();
//		Date created = new Date(utilDate.getTime());
//		
//		models.Team t = new models.Team("team de test", created);
//		t.addMember(bob1);t.addMember(bob2);t.addMember(bob3);t.addMember(bob4);
//		//bob1 : sm, bob2 : po, bob3 : customer		
//		models.Product p = new models.Product("produit test",created,"description",4,t,bob1,bob2,bob3);
//		
//		bob1.addRole(new ScrumMaster(), p);
//		bob2.addRole(ne)
	}
}
