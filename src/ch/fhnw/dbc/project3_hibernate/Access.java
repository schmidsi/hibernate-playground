package ch.fhnw.dbc.project3_hibernate;

public class Access {
	private AccessControllable target;
	private User actor;
	private Role role;
	
	public Access() {};
	
	public Access(AccessControllable _target, User _actor, Role _role) {
		this.target = _target;
		this.actor = _actor;
		this.role = _role;
	}
	
	public AccessControllable getTarget() {
		return target;
	}

	public void setTarget(AccessControllable target) {
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

