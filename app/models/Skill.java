package models;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class Skill extends Model {
	private String name;

	public Skill(String name) {
		super();
		this.name = name;
	}
	
	public void register() {
		this.save();
	}
}
