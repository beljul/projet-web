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
	private User scrumMaster;
	@ManyToOne
	private User productOwner;
	@ManyToOne
	private User customer;
	
	@ManyToMany
	private Set<Skill> skills;
	
	@OneToMany(mappedBy = "idRole.product")
	private Set<Role> roles = new HashSet<Role>();
	
	public Product(String name, Date created, String description, int sprintDuration, Team team,
			User dbScrumMaster, User productOwner2, User dbCustomer) {
		super();
		this.name = name;
		this.created = created;
		this.description = description;
		this.sprintDuration = sprintDuration;
		this.team = team;
		this.scrumMaster = dbScrumMaster;
		this.productOwner = productOwner2;
		this.customer = dbCustomer;
		this.skills = new HashSet<Skill>();
	}
	
	public boolean addSkill(Skill s) {
		return this.skills.add(s);
	}
	
	public static Product register(String name, Date created, String description, int sprintDuration, 
			Team team, User dbScrumMaster, User productOwner2,
			User dbCustomer) {
		Product p = new Product(name, created, description, sprintDuration, team, dbScrumMaster, productOwner2, dbCustomer);
		p.save();
		return p;
	}
	
	public Set<Role> getRoles() {
        return this.roles;
	}
	public void setRoles(Set<Role> r) {
	        this.roles = r;
	}
	
}
