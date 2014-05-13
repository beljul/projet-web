package models;

import java.util.*;

import javax.persistence.*;

import net.sf.cglib.transform.impl.AddDelegateTransformer;
import play.db.jpa.*;

@DiscriminatorColumn(
    name="type",
    discriminatorType=DiscriminatorType.STRING)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity 
public class User extends Model {
	private String name;
	private String firstName;
	private String email;
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
	public static User register(String email, String name, String firstname, 
								  String password, String secondPassword) {
		models.User user = new models.User(name, firstname, email, password);
		user.save();
		return user;
	}
	
}