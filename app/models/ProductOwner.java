package models;

import javax.persistence.Entity;

@Entity
public class ProductOwner extends User {

	public ProductOwner(String name, String firstName, String email,
			String password) {
		super(name, firstName, email, password);
	}

}
