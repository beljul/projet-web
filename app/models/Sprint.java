package models;

import java.sql.Date;
import java.util.HashSet;
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

//	@ManyToMany
//	Set<Requirement> requirements;
	
	@OneToMany
	private Set<Document> documents;
	
	public Sprint(Date created, Date finished) {
		super();
		this.created = created;
		this.finished = finished;
		this.win_rate = 0;
//		this.requirements = new HashSet<Requirement>();
		this.documents = new HashSet<Document>();
	}
	
	public void register() {
		this.save();
	}

	public void setRelease(Version release) {
		this.release = release;
	}

//	public void setRequirements(Set<Requirement> requirements) {
//		this.requirements = requirements;
//	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getFinished() {
		return finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}
	
}
