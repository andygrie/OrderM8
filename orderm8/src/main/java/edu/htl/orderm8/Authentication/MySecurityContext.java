package edu.htl.orderm8.Authentication;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class MySecurityContext implements SecurityContext {
	private UserPrincipal user;
	
	public MySecurityContext (UserPrincipal u) {
		this.user = u;
	}
	
	@Override
	public Principal getUserPrincipal() {
		return user;
	}

	@Override
	public boolean isUserInRole(String role) {
		return true;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public String getAuthenticationScheme() {
		return null;
	}

}
