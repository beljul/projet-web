package models;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import com.mysql.jdbc.Util;

import play.db.jpa.Model;

@Entity
public class Product extends Model {
	//rgret
	private String name;
	private Date created;
	private String description;
	private int sprintDuration;

	@OneToMany
	@OrderBy("priority ASC")
	private Set<Requirement> requirements;
	
	@OneToMany
	private Set<Version> releases;
	
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

	public void setCreated(java.util.Date created){
		this.created = new Date(created.getTime());
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
	public Set<Requirement> getRequirements(){
		return this.requirements;
	}
	
	public User getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(User productOwner) {
		this.productOwner = productOwner;
	}
	
	public int getSprintDuration() {
		return sprintDuration;
	}

	public void setSprintDuration(int sprintDuration) {
		this.sprintDuration = sprintDuration;
	}
	
	public boolean addRelease(Version r) {
		return this.releases.add(r);
	}

	public void register(){
		this.save();
	}
	
	public Version getCurrentRelease() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date currentDate = new Date(utilDate.getTime());
		return find("select real "
				+ 	"from Product p "
				+ 	"inner join p.releases real "
				+ 	"where real.date <= ?  and p = ?"
				+ 	"order by real.date desc", currentDate, this).first();
	}

	public Set<Version> getReleases() {
		return releases;
	}

	public Team getTeam() {
		return team;
	}

	public User getScrumMaster() {
		return scrumMaster;
	}

	public User getCustomer() {
		return customer;
	}
	/**
	 * Get a release of the product by a given name
	 * @param name the name of the product
	 * @return
	 */
	public models.Version getReleaseByName(String name){
		for(models.Version r: this.releases){
			if(r.getName().equals(name)){
				return r;
			}
		}
		return null;
	}
	
	public Set<Requirement> getRequirementsUnassigned() {
		Set<Requirement> requirementsUnassigned = this.requirements;
		for (models.Version v : this.releases) {
			for (models.Sprint sp : v.getSprints()) {
				requirementsUnassigned.removeAll(sp.getRequirements());
			}
		}
		return requirementsUnassigned;
	}

	public void setScrumMaster(User scrumMaster) {
		this.scrumMaster = scrumMaster;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}

	public void setRequirements(Set<Requirement> requirements) {
		this.requirements = requirements;
	}
}
