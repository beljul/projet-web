package models;

import java.sql.Date;

import javax.persistence.Entity;

@Entity
public class SprintRetrospective extends Document {

	public SprintRetrospective(Date created, String content) {
		super(created, content);
	}
	public SprintRetrospective(String content, User author, Sprint sprint){
		super(content, author, sprint);
	}
	
	public String getDocumentType(){
		return "Retrospective de sprint";
	}
}
