package ch.fhnw.dbc.project3_hibernate;

import java.util.ArrayList;

public class Domain extends Hostname implements AccessControllable {	
	private ArrayList<Subdomain> subdomains = new ArrayList<Subdomain>();
	private ArrayList<Access> access = new ArrayList<Access>();

	public Domain() {}

	public Domain(String _name, User _owner) {
		super(_name);
		
		this.addAccess(new Access(this, _owner, Role.OWN));
	}

	public Domain(String _name, User _owner, Hostname _redirectTo) {
		super(_name, _redirectTo);
		this.addAccess(new Access(this, _owner, Role.OWN));
	}
	
	public void addAccess(Access access) {
		this.access.add(access);
	}
	
	public void addSubdomain(Subdomain subdomain) {
		this.subdomains.add(subdomain);
	}

}
