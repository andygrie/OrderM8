package edu.htl.orderm8.Service;

import java.sql.SQLException;
import java.util.List;

import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Objects.Bill;
import edu.htl.orderm8.Exception.ConflictException;
import edu.htl.orderm8.Exception.DataNotFoundException;
import edu.htl.orderm8.Exception.InternalServerErrorException;

public class BillService {
	public BillService() {
		
	}
	
	public Bill getBill(long idBill) {
		Bill b = Database.getInstance().getBill(idBill);
		
		if(b == null)
			throw new DataNotFoundException("Bill with id " + idBill + " not found");
		
		return b;
	}
	
	public List<Bill> getBills() {
		return Database.getInstance().getBills();
	}
	
	public Bill insertBill() {
		try {
			return Database.getInstance().insertBill();
		} catch(SQLException exception)  {
			if(exception.getErrorCode() == 1)
				throw new ConflictException("");
			else 
				throw new InternalServerErrorException("Database error");
		}
	}
	
	public void deleteBill(long id) {
		if(Database.getInstance().getBill(id) == null)
			throw new DataNotFoundException("Bill with id " + id + " not found");
		
		try {
			Database.getInstance().deleteBill(id);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			
			throw new InternalServerErrorException("");
		}
	}
}
