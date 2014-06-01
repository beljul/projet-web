package models;

import java.sql.Date;

import javax.persistence.Entity;

@Entity
public class SprintReview extends Document {

	public SprintReview(Date created, String content) {
		super(created, content);
	}
	
	public SprintReview(String content, User author, Sprint sprint) {
		super(content, author, sprint);
	}
	
	public String getDocumentType(){
		return "Revue de sprint";
	}
}
