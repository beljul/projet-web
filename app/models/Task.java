package models;

import java.sql.Date;
import java.util.Calendar;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Task extends Model {
	private String description;
	private Integer ident;
	private Integer win_rate;
	private Integer duration;
	private Date created;
	private Date finished;
	private Integer priority;
	
	/*Default task state is TODO */
	private TaskState curState = TaskState.TODO;
	
	/**
	 * States of task
	 */
	public enum TaskState {
		TODO, INPG, TOVERIFIED, DONE;
		public String toString(){
			return this.name();
		}
	}
	public final static TaskState STATE_TODO = TaskState.TODO;
	public final static TaskState STATE_INPG = TaskState.INPG;
	public final static TaskState STATE_TOVERIFIED = TaskState.TOVERIFIED;
	public final static TaskState STATE_DONE = TaskState.DONE;
	@ManyToOne
	private Requirement requirement;

	public Task(String description, Integer ident, Integer duration,
			Date created, Integer priority, Long requirementId) {
		super();
		this.description = description;
		this.ident = ident;
		this.duration = duration;
		this.created = created;
		this.priority = priority;
		this.win_rate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(created);
		calendar.add(Calendar.DATE, duration);
		this.finished = new Date(calendar.getTimeInMillis());
		this.requirement = Requirement.getById(requirementId);
	}

	public static Integer getCurrentId(Requirement requirement) {
		Task res = find("from Task t "
					 + 	"where t.requirement= ? "
					 +  "order by t.ident desc", requirement).first();
		if(res != null)
			return res.getIdent();
		else
			return 1;
	}
	
	private Integer getIdent() {
		return this.ident;
	}
	/**
	 * Get the current state of the task
	 * @return
	 */
	public TaskState getState(){
		return this.curState;
	}
	/**
	 * Change the current state of the task
	 * @param newState
	 */
	public void setCurTask(TaskState newState){
		this.curState = newState;
	}
	
	/**
	 * Change the task win rate if it is between 0 and 100 
	 * @param rate
	 */
	public void setWinRate(int rate) {
		if(rate >= 0 && rate <= 100) {
			this.win_rate = rate;
		}
	}
	public void register() {
		this.save();
	}
	
}
