package edu.htl.orderm8.Service;

import java.sql.SQLException;
import java.util.List;

import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Objects.Bill;
import edu.htl.orderm8.Data.Objects.BillOrderEntriesWrapper;
import edu.htl.orderm8.Data.Objects.OrderEntryProductWrapper;
import edu.htl.orderm8.Data.Objects.TableOrderWrapper;
import edu.htl.orderm8.Data.Objects.TableStatusWrapper;
import edu.htl.orderm8.Data.Objects.User;
import edu.htl.orderm8.Exception.DataNotFoundException;
import edu.htl.orderm8.Exception.InternalServerErrorException;

public class MiscService {
	
	public MiscService() {
		
	}
	
	public List<TableOrderWrapper> getTableOrderWrappers(User user) {
		try {
			return Database.getInstance().getTableOrderWrappers(user);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Database - error: " + e.getMessage());
		}
	}
	
	public List<OrderEntryProductWrapper> getOrderEntryProductWrappers(User user) {
		try {
			return Database.getInstance().getOrderEntryProductWrappers(user);
		} catch(SQLException e) {
			String errorMsg = "Database - error: " + e.getMessage();
			System.out.println(errorMsg);
			throw new InternalServerErrorException(errorMsg);
		}
	}
	
	public List<OrderEntryProductWrapper> getOrderEntryProductWrappersByTable(User user, long tableid) {
		try {
			return Database.getInstance().getOrderEntryProductWrappersByTable(user, tableid);
		} catch(SQLException e) {
			String errorMsg = "Database - error: " + e.getMessage();
			System.out.println(errorMsg);
			throw new InternalServerErrorException(errorMsg);
		}
	}
	
	public List<BillOrderEntriesWrapper> getBillOrderEntriesWrappers(User user) {
		try {
			return Database.getInstance().getBillOrderEntriesWrappers(user);
		} catch(SQLException e) {
			String errorMsg = "Database - error: " + e.getMessage();
			System.out.println(errorMsg);
			throw new InternalServerErrorException(errorMsg);
		}
	}
	
	public List<TableStatusWrapper> getTableStatusWrappers(User user) {
		try {
			return Database.getInstance().getTableStatusWrappers(user);
		} catch(SQLException e) {
			String errorMsg = "Database - error: " + e.getMessage();
			System.out.println(errorMsg);
			throw new InternalServerErrorException(errorMsg);
		}
	}
}
