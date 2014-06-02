package unit.models;

import java.sql.Date;

import models.Product;
import models.Team;
import models.User;
import models.Version;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class VersionTest extends UnitTest{
	
	@Before
	public void initProduct() {
		Fixtures.deleteAllModels();
		String name = "Beer";
		String description = "After 5pm, every days.";
		int sprintDuration = 3;
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		
		Team team = new Team("TeamRocket", created);
		User scrumMaster = new User("white", "walter", "walter.white@gmail.com", "rootroot");
		User productOwner = new User("pinkman", "jessie", "jessie.pinkman@gmail.com", "rootroot");
		User customer = new User("junkie", "noname", "junkie.42@gmail.com", "rootroot");
		assertNotNull(team);
		assertNotNull(scrumMaster);
		assertNotNull(productOwner);
		assertNotNull(customer);
		
		Product pSuccess = new Product(name, created, description, sprintDuration,
											team, scrumMaster, productOwner, customer);
		assertNotNull(pSuccess);
		team.register();
		scrumMaster.register();
		productOwner.register();
		customer.register();
		pSuccess.register();
	}
	
	@Test
	public void init() {
		String name = "Advanced Edition 4.1";
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date date = new Date(utilDate.getTime());
		
		Version vSuccess = new Version(name, date);
		assertNotNull(vSuccess);
		vSuccess.register();
		
		Product pSuccess = Product.getByName("Beer");
		assertNotNull(pSuccess);
		
		vSuccess.setProduct(pSuccess);
		assertSame(vSuccess.getProduct(), pSuccess);
	}
	
}
