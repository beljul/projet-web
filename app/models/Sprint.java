package models;

import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Sprint extends Model {
	private Date created;
	private Date finished;
	private Integer win_rate;
	
	@ManyToOne(targetEntity = Version.class)
	@JoinTable( name="Release_Sprint", 
		joinColumns={@JoinColumn(name="Sprints_ID", referencedColumnName="ID")}, 
		inverseJoinColumns={@JoinColumn(name="Release_ID", referencedColumnName="ID")})
	private Version release;

	@ManyToMany
	Set<Requirement> requirements;
	
	@OneToMany
	private Set<Document> documents;
	
	public Sprint(Date created, Date finished) {
		super();
		this.created = created;
		this.finished = finished;
		this.win_rate = 0;
	}
	
	public void register() {
		this.save();
	}

	public void setRelease(Version release) {
		this.release = release;
	}

}
