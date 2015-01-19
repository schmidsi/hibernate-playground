package ch.fhnw.dbc.project3_hibernate;

import javax.persistence.*;


@Entity
public class Subdomain extends Hostname {
	@ManyToOne
	private Domain domain;
	
	public Subdomain() {}

	public Subdomain(String _name) {
		super(_name);
	}

	public Subdomain(String _name, Hostname _redirectTo) {
		super(_name, _redirectTo);
	}

}
