package models;

import javax.persistence.*;

@Entity
public class Customer extends User {

	public Customer(String name, String firstName, String email, String password) {
		super(name, firstName, email, password);
	}

	public boolean isCustomer() {
		return true;
	}
}
