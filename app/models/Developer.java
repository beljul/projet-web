package models;

import javax.persistence.Entity;

@Entity
public class Developer extends User {

	public Developer(String name, String firstName, String email,
			String password) {
		super(name, firstName, email, password);
	}
	public static Developer getByEmail(String email){
		return find("byEmail", email).first();
	}
}
