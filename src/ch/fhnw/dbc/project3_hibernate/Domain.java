package ch.fhnw.dbc.project3_hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
public class Domain extends Hostname {
	@OneToMany(targetEntity=Subdomain.class, mappedBy="domain",
			cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<Subdomain> subdomains = new ArrayList<Subdomain>();
	
	@ManyToOne()
	private User owner;

	public Domain() {}

	public Domain(String _name, User _owner) {
		super(_name);
		
		this.owner = _owner;
		this.addSubdomain(new Subdomain("www." + _name));
		this.setRedirectTo(this.getSubdomain(0));
	}
	
	@Override
	public String toString() {
		return "Domain [getHostname()=" + getHostname() + "]";
	}
	
	public boolean hasAccess(User user, Role role) {
		return (this.owner.equals(user) && role == Role.OWN);
	}
	
	public void addSubdomain(Subdomain subdomain) {
		this.subdomains.add(subdomain);
	}
	
	public Subdomain getSubdomain(int index) {
		return this.subdomains.get(index);
	}

}
