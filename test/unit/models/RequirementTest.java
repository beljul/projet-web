package unit.models;

import java.sql.Date;

import org.junit.*;

import models.Requirement;
import models.Task;
import play.test.Fixtures;
import play.test.UnitTest;

public class RequirementTest extends UnitTest {
	@Before
	public void init() {
		Fixtures.deleteAllModels();
		String content = "Cake is a lie";
		Integer priority = 3;
		Integer duration = 3;
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		
		Requirement rSuccess = new Requirement(content, created, priority, duration);
		assertNotNull(rSuccess);
		rSuccess.register();
	}
	
	@Test
	public void getByContent() {
		Requirement rSuccess = Requirement.getByContent("Cake is a lie");
		assertNotNull(rSuccess);
		
		Requirement rFail = Requirement.getByContent("Pie is a lie");
		assertNull(rFail);
	}
	
	@Test
	public void addTask() {
		Requirement rSuccess = Requirement.getByContent("Cake is a lie");
		assertNotNull(rSuccess);
		
		String description = "Do this !";
		Integer priority = 3;
		Integer duration = 3;
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		Task tSuccess = new Task(description, 1, duration, created, priority, rSuccess.getId());
		assertNotNull(tSuccess);
		
		assertTrue(rSuccess.addTask(tSuccess));
		assertTrue(rSuccess.getTask().contains(tSuccess));
	}
}
