import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class UserTest extends UnitTest {
	@Test
	public void authentication() {
		new User("bob@gmail.com","dat'pwd").save();
		// Retrieve the user with e-mail address bob@gmail.com
	    User bob = User.find("byEmail", "bob@gmail.com").first();
	    
	    // A failed authentication
	    boolean auth = 
	    		new User("bob@gmail.com", "fakePwd").authenticate();
	    assertEquals(false, auth);
	    
	    // A good authentication
	    //
	    
	    
	    // Test 
	    assertNotNull(bob);
	    assertEquals("Bob", bob.fullname);
	}
}
