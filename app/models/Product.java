package models;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Product {
	
	private String name;
	private Date created;
	private String description;
	
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

	public Product(String name, Date created, String description, Team team,
			ScrumMaster scrumMaster, ProductOwner productOwner,
			Customer customer) {
		super();
		this.name = name;
		this.created = created;
		this.description = description;
		this.team = team;
		this.scrumMaster = scrumMaster;
		this.productOwner = productOwner;
		this.customer = customer;
		this.skills = new HashSet<Skill>();
	}
	
	public boolean addSkill(Skill s) {
		return this.skills.add(s);
	}
	
}
