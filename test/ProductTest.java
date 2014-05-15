import java.sql.Date;

import models.Customer;
import models.Product;
import models.ProductOwner;
import models.ScrumMaster;
import models.Team;
import models.User;

import org.junit.Test;

import play.test.UnitTest;


public class ProductTest extends UnitTest {
	@Test
	public void register(){
		String name = "Beer";
		String description = "After 5pm, every days.";
		int sprintDuration = 3;
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		
		Team team = new Team("TeamRocket", created);
		ScrumMaster scrumMaster = new ScrumMaster("white", "walter", "walter.white@gmail.com", "rootroot");
		ProductOwner productOwner = new ProductOwner("pinkman", "jessie", "jessie.pinkman@gmail.com", "rootroot");
		Customer customer = new Customer("junkie", "noname", "junkie.42@gmail.com", "rootroot");

		
		Product pSuccess = Product.register(name, created, description, sprintDuration,
											team, scrumMaster, productOwner, customer);
		assertNotNull(pSuccess);	
	}
}
