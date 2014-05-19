package models;

import java.util.*;

import javax.persistence.*;

import net.sf.cglib.transform.impl.AddDelegateTransformer;
import play.data.validation.Required;
import play.db.jpa.*;

@DiscriminatorColumn(
    name="type",
    discriminatorType=DiscriminatorType.STRING)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity 
public class User extends Model {
	@Required
	private String name;
	@Required
	private String firstName;
	@Required
	private String email;
	@Required
	private String password;
	
	public User(String name, String firstName, String email, String password) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
	}
	
	public static User connect(String email, String password) {
	    return find("byEmailAndPassword", email, password).first();
	}
	
	public static User getById(Integer id) {
		return findById(id);
	}
	
	public static User getByEmail(String email) {
		return find("byEmail", email).first();
	}
	
	public static List<User> getByBeginOfEmail(String email) {
		return find("email like ?", email + "%").fetch(5);
	}
	
	public static User register(String email, String name, String firstname, 
								  String password, String secondPassword) {
		models.User user = new models.User(name, firstname, email, password);
		user.save();
		return user;
	}
	
	public boolean isCustomer() {
		return false;
	}

	public String getEmail() {
		return email;
	}
	public String getName(){
		return name;
	}
	public String getFirstName(){
		return firstName;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
