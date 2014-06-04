package models;

import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
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
	@OrderBy("created ASC")
	Set<Requirement> requirements;
	
	@OneToMany
	private Set<Document> documents;
	
	public Sprint(Date created, Date finished) {
		super();
		this.created = created;
		this.finished = finished;
		this.win_rate = 0;
		this.requirements = new HashSet<Requirement>();
		this.documents = new HashSet<Document>();
	}
	
	public void register() {
		this.save();
	}

	public void setRelease(Version release) {
		this.release = release;
	}

	public void setRequirements(Set<Requirement> requirements) {
		this.requirements = requirements;
	}

	public Set<Requirement> getRequirements() {
		return requirements;
	}

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
	
	static public Sprint getById(Long id) {
		return find("byId", id).first();
	}

	@Override
	public String toString() {
		return "Sprint " + this.id + " - " + this.created;
	}

	public boolean addRequirement(Requirement r) {
		return this.requirements.add(r);
	}
	
	/**
	 * Modify and save tasks of the sprint
	 * @param newTaskStates
	 * @param winrates
	 */
	public void saveTasks(Map<Long, models.Task.TaskState> newTaskStates,
						   Map<Long, Integer> winrates){
		
		//For all requirement of the sprint
		for(models.Requirement r : this.requirements){			
			//For all task of the requirements
			for(models.Task t : r.getTask()){
				//If the examinated task is in the given map
				if(newTaskStates.containsKey(t.getId())){					
					t.setCurTask(newTaskStates.get(t.getId()));					
				}
				if(winrates.containsKey(t.getId())){
					t.setWinRate(winrates.get(t.getId()));
				}
				t.save();
			}
		}
		this.save();
	}

	public Set<Document> getDocuments() {
		return documents;
	}
	
	public boolean addDocument(Document d){
		return this.documents.add(d);
	}
}
