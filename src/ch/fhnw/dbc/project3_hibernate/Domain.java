package ch.fhnw.dbc.project3_hibernate;

import java.util.ArrayList;

public class Domain extends Hostname implements AccessControllable {	
	private ArrayList<Subdomain> subdomains = new ArrayList<Subdomain>();
	private ArrayList<Access> access = new ArrayList<Access>();

	public Domain() {}

	public Domain(String _name, User _owner) {
		super(_name);
		
		this.addAccess(new Access(this, _owner, Role.OWN));
		this.addSubdomain(new Subdomain("www." + _name));
		this.setRedirectTo(this.getSubdomain(0));
	}
	
	@Override
	public String toString() {
		return "Domain [getHostname()=" + getHostname() + "]";
	}

	public void addAccess(Access access) {
		this.access.add(access);
	}
	
	public boolean hasAccess(User user, Role role) {
		for (Access a: this.access) {
			if (a.getActor().equals(user) && a.getRole().equals(role)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void addSubdomain(Subdomain subdomain) {
		this.subdomains.add(subdomain);
	}
	
	public Subdomain getSubdomain(int index) {
		return this.subdomains.get(index);
	}

}
