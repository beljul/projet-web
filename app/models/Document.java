package models;

import java.sql.Date;

import javax.persistence.*;

import play.db.jpa.Model;

@DiscriminatorColumn(
name="type",
discriminatorType=DiscriminatorType.STRING)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity
public class Document extends Model {
	private String type;
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
