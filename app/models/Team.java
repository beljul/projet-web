package models;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Team extends Model {
	
	private String name;
	private Date created;
	
	@OneToMany
	private Set<Product> products;

//	@OneToMany(mappedBy="teams")
	@ManyToMany 
	@JoinTable( name="Team_User", 
			joinColumns={@JoinColumn(name="Teams_ID", referencedColumnName="ID")}, 
			inverseJoinColumns={@JoinColumn(name="Users_ID", referencedColumnName="ID")})
	private Set<User> members;
	
	public Team(String name, Date created) {
		super();
		this.name = name;
		this.created = created;
		this.products = new HashSet<Product>();
		this.members = new HashSet<User>();
	}
	
	public boolean addProduct(Product p) {
		return this.products.add(p);
	}
	
	public boolean addMember(User u) {
		//if(u.isCustomer()) return false;	
		return this.members.add(u);
	}
	
	public static Team register(String name, Date created) {
		Team team = new Team(name, created);
		team.save();
		return team;
	}
	public void register(){
		this.save();
	}
	
	public static Team getByname(String name) {
		return find("byName", name).first();
	}

	public Set<User> getMembers() {
		return members;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}
	
	public void setCreated(java.util.Date created){
		this.created = new Date(created.getTime());
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
