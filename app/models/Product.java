package models;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Product extends Model {
	
	private String name;
	private Date created;
	private String description;
	private int sprintDuration;
	
	@ManyToOne
	private Team team;
	
	@ManyToOne
	private ScrumMaster scrumMaster;
	@ManyToOne
	private ProductOwner productOwner;
	@ManyToOne
	private Customer customer;
	
	@ManyToMany
	private Set<Skill> skills;

	public Product(String name, Date created, String description, int sprintDuration, Team team,
			ScrumMaster scrumMaster, ProductOwner productOwner, Customer customer) {
		super();
		this.name = name;
		this.created = created;
		this.description = description;
		this.sprintDuration = sprintDuration;
		this.team = team;
		this.scrumMaster = scrumMaster;
		this.productOwner = productOwner;
		this.customer = customer;
		this.skills = new HashSet<Skill>();
	}
	
	public boolean addSkill(Skill s) {
		return this.skills.add(s);
	}
	
	public static Product register(String name, Date created, String description, int sprintDuration, 
			Team team, ScrumMaster scrumMaster, ProductOwner productOwner,
			Customer customer) {
		Product p = new Product(name, created, description, sprintDuration, team, scrumMaster, productOwner, customer);
		p.save();
		return p;
	}
	
}
