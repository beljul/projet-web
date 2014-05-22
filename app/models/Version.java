package models;

import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Version extends Model {
	private String name;
	private Date date;
	
	@OneToMany
	private Set<Sprint> sprints;
	
	@ManyToOne(targetEntity = Product.class)
	@JoinTable( name="Product_Release", 
		joinColumns={@JoinColumn(name="Releases_ID", referencedColumnName="ID")}, 
		inverseJoinColumns={@JoinColumn(name="Product_ID", referencedColumnName="ID")})
	private Product product;
	
	
	public Version(String name, Date date) {
		super();
		this.name = name;
		this.date = date;
	}

	public void register() {
		this.save();
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public boolean addSprint(Sprint s) {
		return this.sprints.add(s);
	}
}
