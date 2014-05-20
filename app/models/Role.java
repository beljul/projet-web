package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import play.db.jpa.Model;

@DiscriminatorColumn(
name="type",
discriminatorType=DiscriminatorType.STRING)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity
public class Role extends Model {
	
	@ManyToMany
	private Map<User, Product> products;
	
	public Role() {
		super();
		this.products = new HashMap<User, Product>();
	}

	public Product add(User user, Product product) {
		return this.products.put(user, product);
	}

	public Map<User, Product> getProducts() {
		return products;
	}
}
