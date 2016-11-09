package edu.htl.orderm8.Service;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Objects.OrderEntry;
import edu.htl.orderm8.Exception.ConflictException;
import edu.htl.orderm8.Exception.DataNotFoundException;

public class OrderEntryService {
	public OrderEntryService() {
		
	}
	
	public OrderEntry getOrderEntry(long idOrderEntry) {
		OrderEntry o = Database.getInstance().getOrderEntry(idOrderEntry);
		
		if(o == null)
			throw new DataNotFoundException("OrderEntry with id " + idOrderEntry + " not found");
		
		return o;
	}
	
	public List<OrderEntry> getOrderEntries() {
		return Database.getInstance().getOrderEntries();
	}
	
	public OrderEntry insertOrderEntry(OrderEntry pt) {
		try {
			return Database.getInstance().insertOrderEntry(pt);
		} catch(SQLException exception)  {
			if(exception.getErrorCode() == 1)
				throw new ConflictException("");
			else 
				throw new InternalServerErrorException("Database error");
		}
	}
	
	public void updateOrderEntry(long id, OrderEntry pt) {
		try {
			if(Database.getInstance().getOrderEntry(id) == null)
				throw new DataNotFoundException("OrderEntry with id " + id + " not found");
			
			Database.getInstance().updateOrderEntry(id, pt);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			
			throw new InternalServerErrorException("");
		}
	}
	
	public void deleteOrderEntry(long id) {
		if(Database.getInstance().getOrderEntry(id) == null)
			throw new DataNotFoundException("OrderEntry with id " + id + " not found");
		
		try {
			Database.getInstance().deleteOrderEntry(id);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			throw new InternalServerErrorException();
		}
	}
}
