package models;

import javax.persistence.Entity;

@Entity
public class ScrumMaster extends User {

	public ScrumMaster(String name, String firstName, String email,
			String password) {
		super(name, firstName, email, password);
	}

}
