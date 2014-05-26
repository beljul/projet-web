package models;

import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Version extends Model {
	private String name;
	private Date date;
	
	@OneToMany
	@OrderBy("created ASC")
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
		this.sprints = new LinkedHashSet<Sprint>();
	}

	public void register() {
		this.save();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = new Date(date.getTime());
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public boolean addSprint(Sprint s) {
		return this.sprints.add(s);
	}

	public Sprint getCurrentSprint() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date currentDate = new Date(utilDate.getTime());

		return find("select sp "
				+ 	"from Version v "
				+ 	"inner join v.sprints sp "
				+ 	"where sp.created <= ?  and v = ? "
				+ 	"order by sp.created desc", currentDate, this).first();
	}

	public Set<Sprint> getSprints() {
		return sprints;
	}
	
	
}
