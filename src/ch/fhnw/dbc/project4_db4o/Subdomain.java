package ch.fhnw.dbc.project4_db4o;


public class Subdomain extends Hostname {
	private Domain domain;
	
	public Subdomain() {}

	public Subdomain(String _name) {
		super(_name);
	}

	public Subdomain(String _name, Hostname _redirectTo) {
		super(_name, _redirectTo);
	}

}
