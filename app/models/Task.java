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
	
	@ManyToOne
	private Requirement requirement;

	public Task(String description, Integer ident, Integer duration,
			Date created, Integer priority) {
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
	}
	
}