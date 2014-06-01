package models;

import java.sql.Date;

import javax.persistence.*;

import play.db.jpa.Model;

@DiscriminatorColumn(
name="type",
discriminatorType=DiscriminatorType.STRING)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity
public abstract class  Document extends Model {
//	private String type;
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
	
	public Document(String content, User author, Sprint sprint){
		Date created;
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		created = new Date(utilDate.getTime());
		this.created = created;
		this.content = content;
		this.author = author;
		this.sprint = sprint;
	}
	
	public void register(){
		this.save();
	}
	
	
	public String getContent(){
		return this.content;
	}	
	public Sprint getSprint(){
		return this.sprint;
	}
	public Date getDate(){
		return this.created;
	}
	public User getAuthor(){
		return this.author;
	}
	public abstract String getDocumentType();
}
