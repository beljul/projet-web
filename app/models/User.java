package models;

import java.util.*;
import java.util.regex.Pattern;

import javax.persistence.*;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuth10aServiceImpl;
import org.scribe.oauth.OAuthService;

import net.sf.cglib.transform.impl.AddDelegateTransformer;
import play.Play;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.*;
import play.modules.linkedin.LinkedInPlugin;
import play.mvc.Controller;

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
	@JoinTable( name="User_Product", 
		joinColumns={@JoinColumn(name="Users_ID", referencedColumnName="ID")}, 
		inverseJoinColumns={@JoinColumn(name="Products_ID", referencedColumnName="ID")})
	@MapKeyJoinColumn(name="Role_ID", referencedColumnName="ID")
	private Map<Role, Product> products;
	
	//private Set<Team> teams;
	@OneToMany
	private Set<Document> documents;
	
	@ManyToMany
	@JoinTable( name="User_Skills", 
		joinColumns={@JoinColumn(name="Skills_Id", referencedColumnName="ID")}, 
		inverseJoinColumns={@JoinColumn(name="Users_Id", referencedColumnName="ID")})
	private Set<Skill> skills;
	
	public User(String name, String firstName, String email, String password) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.products = new HashMap<Role, Product>();
		this.skills = new HashSet<Skill>();
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
	
	public boolean addSkill(Skill s) {
		return this.skills.add(s);
	}
	
	// Have to use directly the model ? To improve.
	public static void linkedinOAuthCallback(play.modules.linkedin.LinkedInProfile o) {
		Pattern p = Pattern.compile("[^a-zA-Z]+ [^a-zA-Z]*");
		Set<String> skills = new HashSet<>(Arrays.asList(p.split(o.getSkills())));
		boolean b = controllers.User.addSkills(skills);
	}

	public void register() {
		this.save();
	}
	@Override
	public boolean equals(Object other) {
		//check for self-comparison
	    if ( this == other) return true;	    
	    if ( !(other instanceof User) ) return false;
	    System.out.println("Jussqu'ici tout va bien");
	    System.out.println(this.getEmail() + "==" + ((User)other).getEmail());
	    return this.email.equals(((User) other).email);	  
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return this.firstName + " " + this.name;
	}
	
}
