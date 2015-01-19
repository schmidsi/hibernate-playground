package ch.fhnw.dbc.project4_db4o;


public class Access {
	private int id;
	
	private Website target;
	
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

