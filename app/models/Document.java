package models;

import java.sql.Date;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Document extends Model {
	private Date created;
	private String content;
	
	@ManyToOne
	private User author;
	
	@ManyToOne
	private Sprint sprint;

	public Document(Date created, String content) {
		super();
		this.created = created;
		this.content = content;
	}
	
}
