package ch.fhnw.dbc.project3_hibernate;

import javax.persistence.*;


@Entity
public class OAuth {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne()
	private User user;
	
	private OAuthProvider provider;
	private String providerId;
	private String accessToken;
	
	public OAuth() {};
	
	public OAuth(OAuthProvider _provider, String _id) {
		this.provider = _provider;
		this.providerId = _id;
	}
	
	public OAuth(OAuthProvider _provider, String _id, String _accessToken) {
		this(_provider, _id);
		this.accessToken = _accessToken;
	}
	
	public OAuth(User _user, OAuthProvider _provider, String _id, String _accessToken) {
		this(_provider, _id, _accessToken);
		this.user = _user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OAuthProvider getProvider() {
		return provider;
	}

	public void setProvider(OAuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String id) {
		this.providerId = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
