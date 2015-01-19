package ch.fhnw.dbc.project3_hibernate;

public class OAuth {
	private User user;
	private OAuthProvider provider;
	private String id;
	private String accessToken;
	
	public OAuth() {};
	
	public OAuth(User _user, OAuthProvider _provider, String _id, String _accessToken) {
		this.user = _user;
		this.provider = _provider;
		this.id = _id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
