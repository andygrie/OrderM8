package edu.htl.orderm8.Authentication;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Objects.User;
import edu.htl.orderm8.Exception.UnauthorizedException;

public final class Authenticator {
	/* Singelton */
	private static Authenticator _instance;
	
	public static Authenticator getInstance() {
		if(_instance == null)
			_instance = new Authenticator();
		
		return _instance;
	}
	/*___*/
	
	private final Map<String, User> authTokens = new HashMap();
	
	public Authenticator() {
		try {
			authTokens.put("org", Database.getInstance().findUser("org", "org"));
			authTokens.put("wat", Database.getInstance().findUser("wat", "wat"));
			authTokens.put("bar", Database.getInstance().findUser("bar", "bar"));
		} catch (SQLException e) {
			System.out.println("Authenticator(): " + e.getMessage());
		}
	}
	
	public TokenWrapper login(String username, String password) throws SQLException {
		User u = Database.getInstance().findUser(username, password);
		if(u != null) { 
			String token = UUID.randomUUID().toString();
			authTokens.put(token, u);
			return new TokenWrapper(token);	
		} else {
			throw new UnauthorizedException("User not found!");
		}
	}
	
	public boolean isValid (String token) {
		return authTokens.containsKey(token);
	}
	
	public User getUser(String token) {
		return authTokens.get(token);
	}
}
