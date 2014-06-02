package models;

import java.sql.Date;

import javax.persistence.Entity;

@Entity
public class DailyScrum extends Document {

	public DailyScrum(Date created, String content) {
		super(created, content);
	}
	public DailyScrum(String content, User author, Sprint sprint){
		super(content, author, sprint);
	}
	
	public String getDocumentType(){
		return "Revue journalière";
	}
}
