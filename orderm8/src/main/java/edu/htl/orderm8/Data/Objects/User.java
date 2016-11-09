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
	
	
}
