package edu.htl.orderm8.Data.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import edu.htl.orderm8.Data.Database.OracleDatabase;
import edu.htl.orderm8.Data.Objects.Bill;

public class BillDao {
	/* 								SQL 								*/
	private static final String TB_NAME = "BILLS";
	private static final String SEQ_NAME = "SEQ_BILLS";
	private static final String FIELD_IDBILL = "IDBILL";
	private static final String FIELD_BILLDATE = "billDate";
	
	private static final String SQL_SELECT_ALL = "SELECT * FROM " + TB_NAME;
	private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE " + FIELD_IDBILL + " = ?";
	private static final String SQL_INSERT = "INSERT INTO " + TB_NAME + " VALUES ("+ SEQ_NAME +".NEXTVAL, SYSTIMESTAMP)";
	private static final String SQL_DELETE = "DELETE FROM " + TB_NAME + " WHERE " + FIELD_IDBILL + "=?";
	/*__________________________________________________________________*/


	private static Bill getBill(ResultSet rs) throws SQLException {
		long idbill = rs.getLong(FIELD_IDBILL);
		Timestamp billdate = rs.getTimestamp(FIELD_BILLDATE);

		return new Bill(idbill, billdate);
	}
	
	private static long getGeneratedKey(ResultSet rs) throws SQLException {
		if(rs.next())
			return rs.getInt(1);
		else
			return -1;
	}
	
	public static List<Bill> getBills() {
		List<Bill> lBill = new Vector<Bill>();
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);
			
			while (rs.next()) {
				Bill b = getBill(rs);
				lBill.add(b);
			}
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return lBill;
	}
	
	public static Bill getBill(long idBill) {
		Bill b = null;
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			PreparedStatement prepStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			prepStmt.setLong(1, idBill);
			ResultSet rs = prepStmt.executeQuery();
			
			if (rs.next())
				b = getBill(rs);
			
			conn.commit();
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return b;
	}
	
	/* INSERT */
	public static Bill insertBill() throws SQLException {
		Bill b = new Bill(0, null);
		
		Connection conn = OracleDatabase.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(SQL_INSERT, new String[] {FIELD_IDBILL});
		prepStmt.executeUpdate();
		
		b.setIdBill(getGeneratedKey(prepStmt.getGeneratedKeys()));
		
		conn.commit();
		conn.close();
		
		return b;
	}
	
	/* DELETE */
	public static void deleteBill(long id) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_DELETE);
		prepStmt.setLong(1, id);
		prepStmt.execute();
		
		conn.commit();
		conn.close();
	}
}
