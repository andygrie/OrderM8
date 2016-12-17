package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private long idUser;
	private String username;
	private String password;
	private long uType;
	
	public User() {
		
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(long idUser, String username, String password, long uType) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.uType = uType;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getuType() {
		return uType;
	}

	public void setuType(long uType) {
		this.uType = uType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
