package edu.htl.orderm8.Service;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Objects.ProductType;
import edu.htl.orderm8.Exception.ConflictException;
import edu.htl.orderm8.Exception.DataNotFoundException;

public class ProductTypeService {
	
	public ProductTypeService() {
		
	}
	
	public ProductType getProductType(long idType) {
		ProductType pt = Database.getInstance().getProductType(idType);
		
		if(pt == null)
			throw new DataNotFoundException("ProductType with id " + idType + " not found");
		
		return pt;
	}
	
	public List<ProductType> getProductTypes() {
		return Database.getInstance().getProductTypes();
	}
	
	public ProductType insertProductType(ProductType pt) {
		try {
			return Database.getInstance().insertProductType(pt);
		} catch(SQLException exception)  {
			if(exception.getErrorCode() == 1)
				throw new ConflictException("");
			else 
				throw new InternalServerErrorException("Database error");
		}
	}
	
	public void updateProductType(long id, ProductType pt) {
		try {
			if(Database.getInstance().getProductType(id) == null)
				throw new DataNotFoundException("ProductType with id " + id + " not found");
			
			Database.getInstance().updateProductType(id, pt);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			
			throw new InternalServerErrorException("");
		}
	}
	
	public void deleteProductType(long id) {
		if(Database.getInstance().getProductType(id) == null)
			throw new DataNotFoundException("ProductType with id " + id + " not found");
		
		try {
			Database.getInstance().deleteProductType(id);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			throw new InternalServerErrorException("Database error");
		}
	}
}
