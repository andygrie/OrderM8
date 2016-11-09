package edu.htl.orderm8.Service;

import java.sql.SQLException;
import java.util.List;

import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Objects.User;
import edu.htl.orderm8.Exception.ConflictException;
import edu.htl.orderm8.Exception.DataNotFoundException;
import edu.htl.orderm8.Exception.InternalServerErrorException;

public class UserService {
	public UserService() {
		
	}
	
	public User getUser(long idUser) {
		User b = Database.getInstance().getUser(idUser);
		
		if(b == null)
			throw new DataNotFoundException("User with id " + idUser + " not found");
		
		return b;
	}
	
	public List<User> getUsers() {
		return Database.getInstance().getUsers();
	}
	
	public User insertUser(User u) {
		try {
			return Database.getInstance().insertUser(u);
		} catch(SQLException exception)  {
			if(exception.getErrorCode() == 1)
				throw new ConflictException("");
			else 
				throw new InternalServerErrorException("Database error");
		}
	}
	
	public void updateUser(long id, User u) {
		try {
			if(Database.getInstance().getUser(id) == null)
				throw new DataNotFoundException("User with id " + id + " not found");
			
			Database.getInstance().updateUser(id, u);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			
			throw new InternalServerErrorException("");
		}
	}
	
	public void deleteUser(long id) {
		if(Database.getInstance().getUser(id) == null)
			throw new DataNotFoundException("User with id " + id + " not found");
		
		try {
			Database.getInstance().deleteUser(id);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			
			throw new InternalServerErrorException("");
		}
	}
}
