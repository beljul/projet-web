package models;

import java.util.*;

import javax.persistence.*;

import net.sf.cglib.transform.impl.AddDelegateTransformer;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.*;

//@DiscriminatorColumn(
//    name="type",
//    discriminatorType=DiscriminatorType.STRING)
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity 
public class User extends Model {

	private String name;
	private String firstName;
	
	@Column(unique=true)
	private String email;
	
	private String password;
	
	@ManyToMany
	private Map<Role, Product> products;
	
	//private Set<Team> teams;
	@OneToMany
	private Set<Document> documents;
	
	public User(String name, String firstName, String email, String password) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.products = new HashMap<Role, Product>();
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
	public List<Product> getLastProducts(){
		return null;
	}
	public static User register(String email, String name, String firstname, 
								  String password, String secondPassword) {
		models.User user = new models.User(name, firstname, email, password);
		user.save();
		return user;
	}
	
	/*public boolean isCustomer() {
		return false;
	}*/

	public String getEmail() {
		return email;
	}
	public String getName(){
		return name;
	}
	public String getFirstName(){
		return firstName;
	}
	public String getPassword(){
		return password;
	}
	public Map<Role, Product> getProducts() {
		return products;
	}

	public void setProducts(Map<Role, Product> products) {
		this.products = products;
	}

	public Product addRole(Role role, Product product) {
		return this.products.put(role, product);
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
