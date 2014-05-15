package models;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Team {
	
	private String name;
	private Date created;
	
	@OneToMany
	private Set<Product> products;

	@OneToMany
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
		if(u.isCustomer()) return false;
		return this.members.add(u);
	}
	
}
