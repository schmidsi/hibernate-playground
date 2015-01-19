package ch.fhnw.dbc.project3_hibernate;

public class Subdomain extends Hostname {

	public Subdomain() {}

	public Subdomain(String _name) {
		super(_name);
	}

	public Subdomain(String _name, Hostname _redirectTo) {
		super(_name, _redirectTo);
	}

}
