import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class UserTest extends UnitTest {
	
	@Before
	public void setUp() {
	    Fixtures.deleteAll();
	}
	
	@Test
	public void connect() {
		String pwd = "bob";
		User sm = new ScrumMaster("leponge", "bob", 
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
	public void register(){
		String firstname = "patrick";
		String name = "letoile";
		String email = "patrick.letoile42@gmail.com";
		String pwd = "root";
		String pwdConfirm = "root";
		
		User uSuccess = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNotNull(uSuccess);	
	}
	
	
	// TODO : Selenium
	@Test
	public void registerFailPwd() {
		String firstname = "patrick";
		String name = "letoile";
		String email = "patrick.letoile@gmail.com";
		String pwd = "root40";
		String pwdConfirm = "root404";
		
		// Both have to be the same
		User uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		
		// Pwd not empty
		pwd = "";
		pwdConfirm = "";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		
		// Pwd >= 6
		pwd = "size5";
		pwdConfirm = "size5";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
	}
	
	@Test
	public void registerFailMail() {
		String firstname = "patrick";
		String name = "letoile";
		String email = "patrick.letoile.gmail.com";
		String pwd = "root";
		String pwdConfirm = "root";
		
		// Need @ into mail address
		User uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		
		// No special chars : just allow alpha numeric
		email = "#patrick!@gmail.com";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		
		// Need extension at the end
		email = "patrick.letoile@gmail";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		
		// No space in email
		email = "patrick letoile@gmail";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
	}
	
	@Test
	public void registerFailName() {
		String firstname = "patrick23";
		String name = "letoile";
		String email = "patrick.letoile@gmail.com";
		String pwd = "root";
		String pwdConfirm = "root";
		
		// No numbers in firstname
		User uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		
		// No special chars in firstname
		firstname="patrick!";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		
		// No spaces in firstname
		firstname=" patr  ick";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		firstname="patrick";
		
		// No special chars in name
		name="leto#ile";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		
		// No numbers in name
		name="letoile42";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
		
		// No spaces in name
		name="  letoile ";
		uFailed = User.register(email, name, firstname, pwd, pwdConfirm);
		assertNull(uFailed);
	}
}
