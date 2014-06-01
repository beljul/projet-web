package unit.models;

import java.sql.Date;

import models.Requirement;
import models.Task;
import models.Task.TaskState;

import org.junit.Before;
import org.junit.Test;

import play.test.*;

public class TaskTest extends UnitTest {
	@Before
	public void initRequirement() {
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
	public void init() {
		String description = "A new hope, Luke.";
		Integer ident = 1;
		Integer priority = 3;
		Integer duration = 3;
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		
		Requirement rSuccess = Requirement.getByContent("Cake is a lie");
		assertNotNull(rSuccess);
		
		Task tSuccess = new Task(description, ident, duration, created, priority, 
									rSuccess.getId());
		assertNotNull(tSuccess);
		tSuccess.register();
		
		assertSame(tSuccess.getState(), TaskState.TODO);
		tSuccess.setCurTask(TaskState.DONE);
		assertSame(tSuccess.getState(), TaskState.DONE);
	}
}
