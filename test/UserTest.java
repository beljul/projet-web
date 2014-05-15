import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class UserTest extends UnitTest {
	
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
}
