package models;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	
//	@OneToMany
//	private Map<Role, User> users;
	
	public Product(String name, Date created, String description, int sprintDuration, Team team,
			User dbScrumMaster, User productOwner, User dbCustomer) {
		super();
		this.name = name;
		this.created = created;
		this.description = description;
		this.sprintDuration = sprintDuration;
		this.team = team;
		this.scrumMaster = dbScrumMaster;
		this.productOwner = productOwner;
		this.customer = dbCustomer;
		this.skills = new HashSet<Skill>();
//		this.users = new HashMap<Role, User>();
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
	
//	public User addRole(Role role, User user) {
////		return this.users.put(role, user);
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static Product getByName(String name) {
		return find("byName", name).first();
	}

	public static Product getById(Long id) {
		return findById(id);
	}

	public User getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(User productOwner) {
		this.productOwner = productOwner;
	}
	
	
}
