package edu.htl.orderm8.Data.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import edu.htl.orderm8.Data.Database.OracleDatabase;
import edu.htl.orderm8.Data.Objects.Table;

public class TableDao {
	/* 								SQL 								*/
	private static final String TB_NAME = "TABLES";
	private static final String SEQ_NAME = "SEQ_TABLES";
	private static final String FIELD_IDTABLE = "IDTABLE";
	private static final String FIELD_SDOCOORDINATES = "SDOCOORDINATES ";
	private static final String FIELD_X = "X";
	private static final String FIELD_Y = "Y";
	
	private static final String SQL_SELECT_ALL = "SELECT "+ FIELD_IDTABLE +", T.X " + FIELD_X + ",T.Y " + FIELD_Y + " FROM " + TB_NAME + ", TABLE(SDO_UTIL.GETVERTICES(" + FIELD_SDOCOORDINATES + ")) T";
	private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE " + FIELD_IDTABLE + " = ?";
	private static final String SQL_INSERT = "INSERT INTO " + TB_NAME + " VALUES("+ SEQ_NAME +".NEXTVAL, SDO_GEOMETRY(2001,NULL,MDSYS.SDO_POINT_TYPE(?,?,NULL),NULL, NULL))";
	private static final String SQL_DELETE = "DELETE FROM " + TB_NAME + " WHERE " + FIELD_IDTABLE + "=?";
	private static final String SQL_UPDATE = "UPDATE " + TB_NAME + " SET " + FIELD_SDOCOORDINATES + " = SDO_GEOMETRY(2001,NULL,MDSYS.SDO_POINT_TYPE(?,?,NULL),NULL, NULL) WHERE " + FIELD_IDTABLE + " =?";
	/*__________________________________________________________________*/

	
	/* SELECT */
	private static Table getTable(ResultSet rs) throws SQLException {
		long idtable = rs.getLong(FIELD_IDTABLE);
		double x = rs.getDouble(FIELD_X);
		double y = rs.getDouble(FIELD_Y);
		
		return new Table(idtable, x, y);
	}
	
	private static long getGeneratedKey(ResultSet rs) throws SQLException {
		if(rs.next())
			return rs.getInt(1);
		else
			return -1;
	}
	
	public static List<Table> getTables() {
		List<Table> lTable = new Vector<Table>();
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);
			
			while (rs.next()) {
				Table t = getTable(rs);
				lTable.add(t);
			}
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return lTable;
	}
	
	public static Table getTable(long idTable) {
		Table t = null;
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			PreparedStatement prepStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			prepStmt.setLong(1, idTable);
			ResultSet rs = prepStmt.executeQuery();
			
			if (rs.next())
				t = getTable(rs);
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return t;
	}
	
	/* INSERT */
	public static Table insertTable(Table t) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_INSERT, new String[] {FIELD_IDTABLE});
		prepStmt.setDouble(1, t.getX());
		prepStmt.setDouble(2, t.getY());
		prepStmt.executeUpdate();
		
		t.setIdTable(getGeneratedKey(prepStmt.getGeneratedKeys()));
		
		conn.commit();
		conn.close();
		
		return t;
	}
	
	/* UPDATE */
	public static void updateTable(long id, Table t) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_UPDATE);
		prepStmt.setDouble(1, t.getX());
		prepStmt.setDouble(2, t.getY());
		prepStmt.setLong(3, id);
		prepStmt.executeUpdate();
		
		conn.commit();
		conn.close();
	}
	
	/* DELETE */
	public static void deleteTable(long id) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_DELETE);
		prepStmt.setLong(1, id);
		prepStmt.execute();
		
		conn.commit();
		conn.close();
	}
}
