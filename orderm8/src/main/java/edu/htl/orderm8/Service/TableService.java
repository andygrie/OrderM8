package edu.htl.orderm8.Service;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Objects.Table;
import edu.htl.orderm8.Exception.ConflictException;
import edu.htl.orderm8.Exception.DataNotFoundException;

public class TableService {
	public TableService() {
		
	}
	
	public Table getTable(long idTable) {
		Table t = Database.getInstance().getTable(idTable);
		
		if(t == null)
			throw new DataNotFoundException("Table with id " + idTable + " not found");
		
		return t;
	}
	
	public List<Table> getTables() {
		return Database.getInstance().getTables();
	}
	
	public Table insertTable(Table t) {
		try {
			return Database.getInstance().insertTable(t);
		} catch(SQLException exception)  {
			if(exception.getErrorCode() == 1)
				throw new ConflictException("");
			else 
				throw new InternalServerErrorException("Database error");
		}
	}
	
	public void updateTable(long id, Table t) {
		try {
			if(Database.getInstance().getTable(id) == null)
				throw new DataNotFoundException("Table with id " + id + " not found");
			
			Database.getInstance().updateTable(id, t);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			
			throw new InternalServerErrorException("");
		}
	}
	
	public void deleteTable(long id) {
		if(Database.getInstance().getTable(id) == null)
			throw new DataNotFoundException("Table with id " + id + " not found");
		
		try {
			Database.getInstance().deleteTable(id);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			throw new InternalServerErrorException();
		}
	}
}
