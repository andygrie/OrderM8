package edu.htl.orderm8.Data.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import edu.htl.orderm8.Data.Database.OracleDatabase;
import edu.htl.orderm8.Data.Objects.Product;

public class ProductDao {
	/* 								SQL 								*/
	private static final String TB_NAME = "PRODUCTS";
	private static final String SEQ_NAME = "SEQ_PRODUCTS";
	private static final String FIELD_IDPRODUCT = "IDPRODUCT";
	private static final String FIELD_FKTYPE = "FKTYPE";
	private static final String FIELD_NAME = "NAME";
	private static final String FIELD_PRICE = "PRICE";
	private static final String FIELD_QUANTITY = "QUANTITY";
	
	private static final String SQL_SELECT_ALL = "SELECT * FROM " + TB_NAME;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TB_NAME + " WHERE " + FIELD_IDPRODUCT + " = ?";
	private static final String SQL_INSERT = "INSERT INTO " + TB_NAME + " VALUES("+ SEQ_NAME +".NEXTVAL, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE " + TB_NAME + " SET " + FIELD_FKTYPE + "=?, " + FIELD_NAME + "=?, " + FIELD_PRICE + "=?, " + FIELD_QUANTITY + "=?  WHERE " + FIELD_IDPRODUCT + " =?";
	private static final String SQL_DELETE = "DELETE FROM " + TB_NAME + " WHERE " + FIELD_IDPRODUCT + "=?";
	/*__________________________________________________________________*/

	private static Product getProduct(ResultSet rs) throws SQLException {
		long idproduct = rs.getLong(FIELD_IDPRODUCT);
		long fktype = rs.getLong(FIELD_FKTYPE);
		String name = rs.getString(FIELD_NAME);
		double price = rs.getDouble(FIELD_PRICE);
		long quantity = rs.getLong(FIELD_QUANTITY);
		
		return new Product(idproduct, fktype, name, price, quantity);
	}
	
	private static long getGeneratedKey(ResultSet rs) throws SQLException {
		if(rs.next())
			return rs.getInt(1);
		else
			return -1;
	}
	
	public static List<Product> getProducts() {
		List<Product> lProduct = new Vector<Product>();
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);
			
			while (rs.next()) {
				Product p = getProduct(rs);
				lProduct.add(p);
			}
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return lProduct;
	}
	
	public static Product getProduct(long idType) {
		Product p = null;
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			PreparedStatement prepStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			prepStmt.setLong(1, idType);
			ResultSet rs = prepStmt.executeQuery();
			
			if (rs.next())
				p = getProduct(rs);
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return p;
	}
	
	/* INSERT */
	public static Product insertProduct(Product p) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(SQL_INSERT, new String[] {FIELD_IDPRODUCT});
		prepStmt.setLong(1, p.getFkType());
		prepStmt.setString(2, p.getName());
		prepStmt.setDouble(3, p.getPrice());
		prepStmt.setDouble(4, p.getQuantity());
		prepStmt.executeUpdate();
		p.setIdProduct(getGeneratedKey(prepStmt.getGeneratedKeys()));
		
		conn.commit();
		conn.close();
		
		return p;
	}
	
	/* UPDATE */
	public static void updateProduct(long id, Product p) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_UPDATE);
		prepStmt.setLong(1, p.getFkType());
		prepStmt.setString(2, p.getName());
		prepStmt.setDouble(3, p.getPrice());
		prepStmt.setDouble(4, p.getQuantity());
		prepStmt.setLong(5, p.getIdProduct());
		prepStmt.executeUpdate();
		
		conn.commit();
		conn.close();
	}
	
	/* DELETE */	
	public static void deleteProduct(long id) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_DELETE);
		prepStmt.setLong(1, id);
		prepStmt.execute();
		
		conn.commit();
		conn.close();
	}
}
