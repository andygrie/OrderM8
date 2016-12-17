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
import edu.htl.orderm8.Data.Objects.Table;

public class TableDao {
	/* 								SQL 								*/
	private static final String TB_NAME = "TABLES";
	private static final String SEQ_NAME = "SEQ_TABLES";
	private static final String FIELD_IDTABLE = "IDTABLE";
	private static final String FIELD_SDOCOORDINATES = "SDOCOORDINATES ";
	private static final String FIELD_X1 = "X1";
	private static final String FIELD_Y1 = "Y1";
	
	private static final String FIELD_X2 = "X2";
	private static final String FIELD_Y2 = "Y2";
	
	private static final String SQL_SELECT_ALL = "SELECT IDTABLE, X1, Y1, X2, Y2 from "+ TB_NAME +" t,(SELECT IDTABLE as t1, X as X1, Y as Y1 FROM tables, TABLE(SDO_UTIL.GETVERTICES(SDOCOORDINATES)) T WHERE ID = 1),(SELECT IDTABLE as t2 , X as X2, Y as Y2 FROM tables, TABLE(SDO_UTIL.GETVERTICES(SDOCOORDINATES)) T WHERE ID = 2)where t1 = t.IDTABLE AND t2 = t.IDTABLE";
	private static final String SQL_SELECT_BY_ID = "SELECT IDTABLE, X1, Y1, X2, Y2 from "+ TB_NAME +" t,(SELECT IDTABLE as t1, X as X1, Y as Y1 FROM tables, TABLE(SDO_UTIL.GETVERTICES(SDOCOORDINATES)) T WHERE ID = 1),(SELECT IDTABLE as t2 , X as X2, Y as Y2 FROM tables, TABLE(SDO_UTIL.GETVERTICES(SDOCOORDINATES)) T WHERE ID = 2)where t1 = t.IDTABLE AND t2 = t.IDTABLE AND t.IDTABLE = ?";
	private static final String SQL_INSERT = "INSERT INTO " + TB_NAME + " VALUES("+ SEQ_NAME +".NEXTVAL, SDO_GEOMETRY(2003,null,null,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY(?,?,?,?)))";
	private static final String SQL_DELETE = "DELETE FROM " + TB_NAME + " WHERE " + FIELD_IDTABLE + "=?";
	private static final String SQL_UPDATE = "UPDATE " + TB_NAME + " SET " + FIELD_SDOCOORDINATES + " = SDO_GEOMETRY(2003,null,null,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY(?,?,?,?)) WHERE " + FIELD_IDTABLE + " =?";
	/*__________________________________________________________________*/

	
	/* SELECT */

	private static Table getTable(ResultSet rs) throws SQLException {
		long idtable = rs.getLong(FIELD_IDTABLE);
		
		double x1 = rs.getDouble(FIELD_X1);
		double y1 = rs.getDouble(FIELD_Y1);
		
		double x2 = rs.getDouble(FIELD_X2);
		double y2 = rs.getDouble(FIELD_Y2);
		
		return new Table(idtable, x1, y1, x2, y2);
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
		prepStmt.setDouble(1, t.getxLL());
		prepStmt.setDouble(2, t.getyLL());
		prepStmt.setDouble(3, t.getxUR());
		prepStmt.setDouble(4, t.getyUR());
		
		prepStmt.executeUpdate();
		
		t.setIdTable(getGeneratedKey(prepStmt.getGeneratedKeys()));
		
		conn.commit();
		conn.close();
		
		return t;
	}
	
	public static int insertTables(ArrayList<Table> tables) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		
		conn.commit();
		conn.close();
		return 1;
	}
	
	/* UPDATE */
	public static void updateTable(long id, Table t) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_UPDATE);
		prepStmt.setDouble(1, t.getxLL());
		prepStmt.setDouble(2, t.getyLL());
		prepStmt.setDouble(3, t.getxUR());
		prepStmt.setDouble(4, t.getyUR());
		prepStmt.setLong(5, id);
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
