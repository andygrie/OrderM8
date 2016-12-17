package edu.htl.orderm8.Authentication;

import java.security.Principal;

import edu.htl.orderm8.Data.Objects.User;

public class UserPrincipal implements Principal {
	
	private User user;
	
	public UserPrincipal(User u) {
		this.user = u;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String getName() {
		return user.getUsername();
	}

}
