package unit.models;
import java.sql.Date;

import models.Team;
import models.User;

import org.junit.Test;

import play.test.UnitTest;


public class TeamTest extends UnitTest {
	@Test
	public void register(){
		String name = "TeamRocket";
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		
		Team tSuccess = Team.register(name, created);
		assertNotNull(tSuccess);	
	}
}
