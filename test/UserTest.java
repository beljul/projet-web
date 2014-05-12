import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class UserTest extends UnitTest {
	@Test
	public void connect() {		
		ScrumMaster sm = new ScrumMaster("leponge", "bob", 
							"bob.leponge@gmail.fr", "bob");
		sm.save();
		// Retrieve the user with e-mail address bob@gmail.com
	    User bob = User.find("byEmail", "bob.leponge@gmail.com").first();
	    
	    // A failed authentication
	    User u = ((User) sm).connect("bob.leponge@gmail.com","bob404");
	    assertNotNull(u);	    
	    
	    // A good authentication
	    //
	    
	    
	    // Test 
//	    assertNotNull(bob);
	    
	}
}
