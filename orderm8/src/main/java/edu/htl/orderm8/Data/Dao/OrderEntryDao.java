package edu.htl.orderm8.Data.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import edu.htl.orderm8.Data.Database.OracleDatabase;
import edu.htl.orderm8.Data.Objects.OrderEntry;
import edu.htl.orderm8.Data.Objects.User;

public class OrderEntryDao {
	/* 								SQL 								*/
	private static final String TB_NAME = "ORDERENTRIES";
	private static final String SEQ_NAME = "SEQ_ORDERENTRIES";
	private static final String FIELD_IDORDERENTRY = "IDORDERENTRY";
	private static final String FIELD_FKPRODUCT = "FKPRODUCT";
	private static final String FIELD_FKTABLE = "FKTABLE";
	private static final String FIELD_FKUSER = "FKUSER";
	private static final String FIELD_FKBILL = "FKBILL";
	private static final String FIELD_NOTE = "NOTE";
	private static final String FIELD_CANCELLED = "CANCELLED";
	private static final String FIELD_COUPON = "COUPON";
	
	private static final String SQL_SELECT_ALL = "SELECT * FROM " + TB_NAME;
	private static final String SQL_SELECT_ALL_OPEN = "SELECT * FROM " + TB_NAME + " WHERE " + FIELD_FKUSER + "=? AND " + FIELD_CANCELLED + " = 0";
	private static final String SQL_SELECT_ALL_OPEN_BY_TABLE = "SELECT * FROM " + TB_NAME + " WHERE " + FIELD_FKUSER + "=? AND " + FIELD_CANCELLED + " = 0 AND " + FIELD_FKTABLE + "=?";
	private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE " + FIELD_IDORDERENTRY + " = ?";
	private static final String SQL_INSERT = "INSERT INTO " + TB_NAME + " VALUES("+ SEQ_NAME +".NEXTVAL, ?, ?, ?, NULL, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE " + TB_NAME + " SET " + FIELD_FKPRODUCT + " =?, "+ FIELD_FKTABLE + " =?, "+ FIELD_FKUSER + " =?, "+ FIELD_FKBILL + " =?, "+ FIELD_NOTE + " =?, "+ FIELD_CANCELLED + " =?, " + FIELD_COUPON + " =? WHERE " + FIELD_IDORDERENTRY + " =?";
	private static final String SQL_DELETE = "DELETE FROM " + TB_NAME + " WHERE " + FIELD_IDORDERENTRY + " =?";
	/*__________________________________________________________________*/

	private static OrderEntry getOrderEntry(ResultSet rs) throws SQLException {
		long idorderentry = rs.getLong(FIELD_IDORDERENTRY);
		long fkproduct = rs.getLong(FIELD_FKPRODUCT);
		long fktable = rs.getLong(FIELD_FKTABLE);
		long fkuser = rs.getLong(FIELD_FKUSER);
		long fkBill = rs.getLong(FIELD_FKBILL);
		String note = rs.getString(FIELD_NOTE);
		Boolean cancelled = rs.getBoolean(FIELD_CANCELLED);
		Boolean coupon = rs.getBoolean(FIELD_COUPON);
		
		return new OrderEntry(idorderentry, fkproduct, fktable, fkuser, fkBill, note, cancelled, coupon);
	}
	
	private static long getGeneratedKey(ResultSet rs) throws SQLException {
		if(rs.next())
			return rs.getInt(1);
		else
			return -1;
	}
	
	public static List<OrderEntry> getOrderEntrys() {
		List<OrderEntry> lOrderEntry = new Vector<OrderEntry>();
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);
			
			while (rs.next()) {
				OrderEntry o = getOrderEntry(rs);
				lOrderEntry.add(o);
			}
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return lOrderEntry;
	}
	
	public static List<OrderEntry> getOrderEntriesOpenByTable(User user, long idtable) {
		List<OrderEntry> lOrderEntry = new ArrayList<OrderEntry>();
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			PreparedStatement prepStmt = conn.prepareStatement(SQL_SELECT_ALL_OPEN_BY_TABLE);
			prepStmt.setLong(1, user.getIdUser());
			prepStmt.setLong(2, idtable);
			ResultSet rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				OrderEntry o = getOrderEntry(rs);
				lOrderEntry.add(o);
			}
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return lOrderEntry;		
	}

	public static List<OrderEntry> getOrderEntriesOpen(User user) {
		List<OrderEntry> lOrderEntry = new ArrayList<OrderEntry>();
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			PreparedStatement prepStmt = conn.prepareStatement(SQL_SELECT_ALL_OPEN);
			prepStmt.setLong(1, user.getIdUser());
			ResultSet rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				OrderEntry o = getOrderEntry(rs);
				lOrderEntry.add(o);
			}
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return lOrderEntry;		
	}
	
	public static OrderEntry getOrderEntry(long idOrderEntry) {
		OrderEntry o = null;
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			PreparedStatement prepStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			prepStmt.setLong(1, idOrderEntry);
			ResultSet rs = prepStmt.executeQuery();
			
			if (rs.next())
				o = getOrderEntry(rs);
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return o;
	}
	
	/* INSERT */
	public static OrderEntry insertOrderEntry(OrderEntry oe) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(SQL_INSERT, new String[] {FIELD_IDORDERENTRY});
		prepStmt.setLong(1, oe.getFkProduct());
		prepStmt.setLong(2, oe.getFkTable());
		prepStmt.setLong(3, oe.getFkUser());
		prepStmt.setString(4, oe.getNote());
		prepStmt.setBoolean(5, oe.getCancelled());
		prepStmt.setBoolean(6, oe.getCoupon());
		
		prepStmt.executeUpdate();
		oe.setIdOrderEntry(getGeneratedKey(prepStmt.getGeneratedKeys()));
		
		conn.commit();
		conn.close();
		
		return oe;
	}
	
	/* UPDATE */
	public static void updateOrderEntry (long id, OrderEntry oe) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_UPDATE);
		prepStmt.setLong(1, oe.getFkProduct());
		prepStmt.setLong(2, oe.getFkTable());
		prepStmt.setLong(3, oe.getFkUser());
		prepStmt.setLong(4, oe.getFkBill());
		prepStmt.setString(5, oe.getNote());
		prepStmt.setBoolean(6, oe.getCancelled());
		prepStmt.setBoolean(7, oe.getCoupon());
		prepStmt.setLong(8, oe.getIdOrderEntry());
		
		conn.commit();
		conn.close();
	}
	
	/* DELETE */
	public static void delteOrderEntry(long id) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_DELETE);
		prepStmt.setLong(1, id);
		prepStmt.execute();
		
		conn.commit();
		conn.close();
	}
}
