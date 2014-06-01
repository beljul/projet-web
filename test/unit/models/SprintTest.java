package unit.models;

import java.sql.Date;

import jj.play.org.eclipse.mylyn.wikitext.core.parser.DocumentBuilder;

import org.junit.Before;
import org.junit.Test;

import models.DailyScrum;
import models.Document;
import models.Requirement;
import models.Sprint;
import play.test.Fixtures;
import play.test.UnitTest;

public class SprintTest extends UnitTest {
	private Long id;
	
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
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		Date finished = new Date(utilDate.getTime() + 3);
		
		Sprint sSuccess = new Sprint(created, finished);
		assertNotNull(sSuccess);
		sSuccess.register();
		
		String content = "Confidential.";
		Document dSuccess = new DailyScrum(created, content);
		assertNotNull(dSuccess);
		dSuccess.register();
		
		assertTrue(sSuccess.addDocument(dSuccess));
		assertTrue(sSuccess.getDocuments().contains(dSuccess));
		
		this.id = sSuccess.getId();

		Sprint s2Success = Sprint.getById(this.id);
		assertNotNull(s2Success);
		
		Requirement rSuccess = Requirement.getByContent("Cake is a lie");
		assertNotNull(rSuccess);
		
		assertTrue(sSuccess.addRequirement(rSuccess));
		assertTrue(sSuccess.getRequirements().contains(rSuccess));
	}

}
