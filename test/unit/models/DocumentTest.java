package unit.models;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import models.DailyScrum;
import models.Document;
import models.Sprint;
import models.SprintRetrospective;
import models.SprintReview;
import models.User;
import play.test.Fixtures;
import play.test.UnitTest;

public class DocumentTest extends UnitTest {
	private Long idSprint;
	
	@Before
	public void initAuthor() {
		Fixtures.deleteAllModels();
		User uSuccess = new User("leponge", "bob", 
				"sebastien.viardot@gmail.com", "password");
		assertNotNull(uSuccess);
		uSuccess.register();

		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date created = new Date(utilDate.getTime());
		Date finished = new Date(utilDate.getTime() + 3);
		
		Sprint sSuccess = new Sprint(created, finished);
		assertNotNull(sSuccess);
		sSuccess.register();
		
		this.idSprint = sSuccess.getId();
	}
	@Test
	public void init() {
		User uSuccess = User.getByEmail("sebastien.viardot@gmail.com");
		assertNotNull(uSuccess);

		Sprint sSuccess = Sprint.getById(this.idSprint);
		assertNotNull(sSuccess);

		String content = "404 error";
		Document dSuccess = new DailyScrum(content, uSuccess, sSuccess);
		assertNotNull(dSuccess);
		assertSame(dSuccess.getDocumentType(), "Revue journalière");
		
		dSuccess = new SprintRetrospective(content, uSuccess, sSuccess);
		assertNotNull(dSuccess);
		assertSame(dSuccess.getDocumentType(), "Retrospective de sprint");

		dSuccess = new SprintReview(content, uSuccess, sSuccess);
		assertNotNull(dSuccess);
		assertSame(dSuccess.getDocumentType(), "Revue de sprint");
	}
}
