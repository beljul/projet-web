package models;

import javax.persistence.*;

import play.db.jpa.Model;

@Entity
public class HaveSkills extends Model {
	private int level;
	
	@ManyToOne
	private Skill skill;
	
	@ManyToOne
	private User developer;

	public HaveSkills(int level, Skill skill, User developer) {
		super();
		this.level = level;
		this.skill = skill;
		this.developer = developer;
	}
}
