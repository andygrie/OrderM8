package edu.htl.orderm8.Service;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Objects.Product;
import edu.htl.orderm8.Exception.ConflictException;
import edu.htl.orderm8.Exception.DataNotFoundException;

public class ProductService {
	public ProductService() {
		
	}
	
	public Product getProduct(long idType) {
		Product p = Database.getInstance().getProduct(idType);
		
		if(p == null)
			throw new DataNotFoundException("Product with id " + idType + " not found");
		
		return p;
	}
	
	public List<Product> getProducts() {
		return Database.getInstance().getProducts();
	}
	
	public List<Product> getProducts(long prodType) {
		return Database.getInstance().getProducts(prodType);
	}
	
	public Product insertProduct(Product p) {
		try {
			return Database.getInstance().insertProduct(p);
		} catch(SQLException exception)  {
			if(exception.getErrorCode() == 1)
				throw new ConflictException("");
			else 
				throw new InternalServerErrorException("Database error");
		}
	}
	
	public void updateProduct(long id, Product p) {
		try {
			if(Database.getInstance().getProduct(id) == null)
				throw new DataNotFoundException("Product with id " + id + " not found");
			
			Database.getInstance().updateProduct(id, p);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			
			throw new InternalServerErrorException("");
		}
	}
	
	public void deleteProduct(long id) {
		if(Database.getInstance().getProduct(id) == null)
			throw new DataNotFoundException("Product with id " + id + " not found");
		
		try {
			Database.getInstance().deleteProduct(id);
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			throw new InternalServerErrorException();
		}
	}
}
