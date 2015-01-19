package ch.fhnw.dbc.project3_hibernate;

import javax.persistence.*;


@Entity
public class Access {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne()
	private Website target;
	
	@ManyToOne()
	private User actor;
	
	private Role role;
	
	public Access() {};
	
	public Access(Website _target, User _actor, Role _role) {
		this.target = _target;
		this.actor = _actor;
		this.role = _role;
	}
	
	public Website getTarget() {
		return target;
	}

	public void setTarget(Website target) {
		this.target = target;
	}

	public User getActor() {
		return actor;
	}

	public void setActor(User actor) {
		this.actor = actor;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}

