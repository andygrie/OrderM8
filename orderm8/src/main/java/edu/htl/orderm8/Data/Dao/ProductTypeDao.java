package edu.htl.orderm8.Data.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import edu.htl.orderm8.Data.Database.OracleDatabase;
import edu.htl.orderm8.Data.Objects.ProductType;

public class ProductTypeDao {
	
	/* 								SQL 								*/
	private static final String TB_NAME = "TYPES";
	private static final String SEQ_NAME = "SEQ_TYPES";
	private static final String FIELD_IDTYPE = "IDTYPE";
	private static final String FIELD_NAME = "NAME";
	private static final String FIELD_VAT = "VAT";
	
	private static final String SQL_SELECT_ALL = "SELECT * FROM " + TB_NAME;
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TB_NAME + " WHERE " + FIELD_IDTYPE + " = ?";
	private static final String SQL_INSERT = "INSERT INTO " + TB_NAME + " VALUES ("+ SEQ_NAME +".NEXTVAL, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE " + TB_NAME + " SET " + FIELD_NAME + "=?, " + FIELD_VAT + "=? WHERE " + FIELD_IDTYPE + "=?";
	private static final String SQL_DELETE = "DELETE FROM " + TB_NAME + " WHERE " + FIELD_IDTYPE + "=?";
	/*__________________________________________________________________*/
	
	/*  SELECT  */
	private static ProductType getProductType(ResultSet rs) throws SQLException {
		long idtype = rs.getLong(FIELD_IDTYPE);
		String name = rs.getString(FIELD_NAME);
		long vat = rs.getLong(FIELD_VAT);
		
		return new ProductType(idtype, name, vat);
	}
	
	private static long getGeneratedKey(ResultSet rs) throws SQLException {
		if(rs.next())
			return rs.getInt(1);
		else
			return -1;
	}
	
	public static List<ProductType> getProductTypes() {
		List<ProductType> lProductType = new Vector<ProductType>();
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);
			
			while (rs.next()) {
				ProductType pt = getProductType(rs);
				lProductType.add(pt);
			}
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return lProductType;
	}
	
	public static ProductType getProductType(long idType) {
		ProductType pt = null;
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			PreparedStatement prepStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			prepStmt.setLong(1, idType);
			ResultSet rs = prepStmt.executeQuery();
			
			if (rs.next())
				pt = getProductType(rs);
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return pt;
	}
	
	/* INSERT */
	public static ProductType insertProductType(ProductType pt) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(SQL_INSERT, new String[] {FIELD_IDTYPE});
		prepStmt.setString(1, pt.getName());
		prepStmt.setLong(2, pt.getVat());
		prepStmt.execute();
		pt.setIdType(getGeneratedKey(prepStmt.getGeneratedKeys()));
		
		conn.commit();
		conn.close();
		
		return pt;
	}
	
	/* UPDATE */
	public static void updateProductType(long id, ProductType pt) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_UPDATE);
		prepStmt.setString(1, pt.getName());
		prepStmt.setLong(2, pt.getVat());
		prepStmt.setLong(3, id);
		prepStmt.executeUpdate();
		
		conn.commit();
		conn.close();
	}
	
	/* DELETE */
	public static void deleteProductType(long id) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_DELETE);
		prepStmt.setLong(1, id);
		prepStmt.execute();
		
		conn.commit();
		conn.close();
	}
}
