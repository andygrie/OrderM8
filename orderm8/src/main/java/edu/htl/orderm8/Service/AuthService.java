package edu.htl.orderm8.Service;

import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.ws.rs.InternalServerErrorException;

import edu.htl.orderm8.Authentication.Authenticator;
import edu.htl.orderm8.Authentication.TokenWrapper;

public class AuthService {
	
	public AuthService() {
		
	}
	
	public TokenWrapper getToken(String username, String password) {
		try {
			return Authenticator.getInstance().login(username, password);
		} catch(SQLException sqle) {
			throw new InternalServerErrorException("Database error!");
		}
	}
}
