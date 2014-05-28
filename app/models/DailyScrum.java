package models;

import java.sql.Date;

public class DailyScrum extends Document {

	public DailyScrum(Date created, String content) {
		super(created, content);
	}

}
