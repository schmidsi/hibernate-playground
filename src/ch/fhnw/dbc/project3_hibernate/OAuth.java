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
	
	public OAuth(User _user, OAuthProvider _provider, String _id, String _accessToken) {
		this.user = _user;
		this.provider = _provider;
		this.providerId = _id;
		this.accessToken = _accessToken;
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
}
