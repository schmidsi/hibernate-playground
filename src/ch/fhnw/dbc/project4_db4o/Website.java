package ch.fhnw.dbc.project4_db4o;

import java.util.ArrayList;
import java.util.List;


public class Website {
	private int id;
	
	private String title;
	
	private Hostname hostname;
	
	private List<Access> access = new ArrayList<Access>();
	
	public Website() {};
	
	public Website(String _title, Hostname _hostname, User _owner) {
		this.title = _title;
		
		if (_hostname.getRedirect() != null) {
			this.setHostname(_hostname.getRedirect());
		} else {
			this.setHostname(_hostname);
		}
		
		this.addAccess(new Access(this, _owner, Role.OWN));
	}
	
	public String render() {
		return "<html><title>" + this.getTitle() + "</title></html>";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String _title) {
		this.title = _title;
	}
	
	public void addAccess(Access _access) {
		this.access.add(_access);
		_access.getActor().addAccess(_access);
	}
	
	public boolean hasAccess(User _user, Role _role) {
		for (Access a: this.access) {
			if (a.getActor().equals(_user) && a.getRole().equals(_role)) {
				return true;
			}
		}
		
		return false;
	}

	public Hostname getHostname() {
		return hostname;
	}

	public void setHostname(Hostname hostname) {
		this.hostname = hostname;
		hostname.setWebsite(this);
	}
}
