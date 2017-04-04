package edu.htl.orderm8.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import SAP.Models.ZOrder;
import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Database.SAPDatabase;
import edu.htl.orderm8.Data.Objects.BillWrapper;
import edu.htl.orderm8.Data.Objects.OrderEntry;
import edu.htl.orderm8.Data.Objects.Product;
import edu.htl.orderm8.Data.Objects.User;
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
	
	public List<OrderEntry> getOrderEntriesOpenByTable(User user, long idtable) {
		return Database.getInstance().getOrderEntriesOpenByTable(user, idtable);
	}
	
	public List<OrderEntry> getOrderEntriesOpen(User user) {
		return Database.getInstance().getOrderEntriesOpen(user);
	}
	
	public OrderEntry insertOrderEntry(OrderEntry pt) {
		try {
			return Database.getInstance().insertOrderEntry(pt);
		} catch(SQLException exception)  {
			System.out.println("Error - insertOrderEntry: " + exception.getMessage());
			
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
	
	public void pay(long id, BillWrapper billWrapper) {
		OrderEntry oe = Database.getInstance().getOrderEntry(id);
		if(oe == null)
			throw new DataNotFoundException("OrderEntry with id " + id + " not found");
		
		if(oe.getFkBill() != 0)
			throw new InternalServerErrorException("OrderEntry already payed");
		
		try {
			Database.getInstance().pay(id, billWrapper);
			
			User user = Database.getInstance().getUser(oe.getFkUser());
			Product product = Database.getInstance().getProduct(oe.getFkProduct());
			if(user != null && product != null) {
				ZOrder order = new ZOrder(-1, user.getUsername(), new Date(), (float)product.getPrice(), product.getName(), oe.getNote());
				SAPDatabase.getInstance().commitOrder(order);
			}
		} catch(Exception exception) {
			System.out.println(exception.getMessage());
			throw new edu.htl.orderm8.Exception.InternalServerErrorException(exception.getMessage());
		}
	}
}
