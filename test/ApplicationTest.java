import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
//import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void requireAuthentication() {
        Response response = GET("/");        
        assertStatus(302, response);     
    }
    
//    @Test
//    public void badRoute() {
//        Result result = routeAndCall(fakeRequest(GET, "/xx/Kiki"));
//        assertThat(result).isNull();
//    }
    
    
}