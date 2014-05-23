package models;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Requirement extends Model {
	private String content;
	private Date created;
	private Integer priority;
	private Integer win_rate;
	private Date finished;
	private Integer duration;
	
	@ManyToOne(targetEntity = Product.class)
	@JoinTable( name="Product_Requirement", 
		joinColumns={@JoinColumn(name="Requirements_ID", referencedColumnName="ID")}, 
		inverseJoinColumns={@JoinColumn(name="Product_ID", referencedColumnName="ID")})
	private Product product;
	
	@ManyToMany
	Set<Sprint> sprints;
	
	@OneToMany
	private Set<Task> tasks;
	
	public Requirement(String content, Date created, Integer priority, Integer duration) {
		this.content = content;
		this.created = created;
		this.priority = priority;
		this.win_rate = 0;
		this.duration = duration;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(created);
		calendar.add(Calendar.DATE, duration);
		this.finished = new Date(calendar.getTimeInMillis());
	}

	public void register() {
		this.save();
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	public void setPriority(Integer priority){
		this.priority = priority;
	}
	
	
}
