package models;

import java.io.Serializable;

import javax.persistence.*;

import play.db.jpa.Model;

@DiscriminatorColumn(
name="type",
discriminatorType=DiscriminatorType.STRING)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity
public class Role extends Model {
	 
	@Embeddable
	public class RoleId implements Serializable {
	    private static final long serialVersionUID = 1L;
	     
	    @ManyToOne
	    @JoinColumn(name = "id")
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "id")
	    private Product product;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}   
	     
	}
	
	@Id
    RoleId idRole;
	public RoleId getRoleID() {
		return idRole;
	}
	
    private String name;
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public User getUser() {
		return idRole.user;
	}
	
	public Product getProduct() {
		return idRole.product;
	}
    
}
