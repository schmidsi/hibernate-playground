package ch.fhnw.dbc.project3_hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
public class Website {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne()
	private Hostname hostname;
	
	@OneToMany(targetEntity=Access.class, mappedBy="target",
			cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<Access> access = new ArrayList<Access>();
	
	public Website() {};
	
	public Website(Hostname _hostname, User _owner) {
		this.setHostname(_hostname);
		this.addAccess(new Access(this, _owner, Role.OWN));
	}
	
	public void addAccess(Access access) {
		this.access.add(access);
	}

	public Hostname getHostname() {
		return hostname;
	}

	public void setHostname(Hostname hostname) {
		this.hostname = hostname;
	}
}
