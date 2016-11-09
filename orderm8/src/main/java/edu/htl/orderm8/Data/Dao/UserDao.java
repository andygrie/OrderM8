package edu.htl.orderm8.Data.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import edu.htl.orderm8.Data.Database.OracleDatabase;
import edu.htl.orderm8.Data.Objects.User;

public class UserDao {
	private static final String TB_NAME = "USERS";
	private static final String SEQ_NAME = "SEQ_USERS";
	private static final String FIELD_IDUSER = "IDUSER";
	private static final String FIELD_USERNAME = "USERNAME";
	private static final String FIELD_PASSWORD = "PASSWORD";
	private static final String FIELD_UTYPE = "UTYPE";
	
	private static final String SQL_SELECT_ALL = "SELECT * FROM " + TB_NAME;
	private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE " + FIELD_IDUSER + " = ?";
	private static final String SQL_INSERT = "INSERT INTO " + TB_NAME + " VALUES ("+ SEQ_NAME +".NEXTVAL, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE " + TB_NAME + " SET " + FIELD_USERNAME + "=?, "+ FIELD_PASSWORD + "=?, " + FIELD_UTYPE + "=? WHERE " + FIELD_IDUSER + "=?";
	private static final String SQL_DELETE = "DELETE FROM " + TB_NAME + " WHERE " + FIELD_IDUSER + "=?";
	/*__________________________________________________________________*/


	private static User getUser(ResultSet rs) throws SQLException {
		long idUser = rs.getLong(FIELD_IDUSER);
		String username = rs.getString(FIELD_USERNAME);
		String password = rs.getString(FIELD_PASSWORD);
		long uType = rs.getLong(FIELD_UTYPE);

		return new User(idUser, username, password, uType);
	}
	
	private static long getGeneratedKey(ResultSet rs) throws SQLException {
		if(rs.next())
			return rs.getInt(1);
		else
			return -1;
	}
	
	public static List<User> getUsers() {
		List<User> lUser = new Vector<User>();
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);
			
			while (rs.next()) {
				User u = getUser(rs);
				lUser.add(u);
			}
			
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return lUser;
	}
	
	public static User getUser(long idUser) {
		User u = null;
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			PreparedStatement prepStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			prepStmt.setLong(1, idUser);
			ResultSet rs = prepStmt.executeQuery();
			
			if (rs.next())
				u = getUser(rs);
			
			conn.commit();
			conn.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
		}
		
		return u;
	}
	
	/* INSERT */
	public static User insertUser(User u) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(SQL_INSERT, new String[] {FIELD_IDUSER});
		prepStmt.setString(1, u.getUsername());
		prepStmt.setString(2, u.getPassword());
		prepStmt.setLong(3, u.getuType());
		prepStmt.executeUpdate();
		u.setIdUser(getGeneratedKey(prepStmt.getGeneratedKeys()));
		
		conn.commit();
		conn.close();
		
		return u;
	}
	
	/* UPDATE */
	public static void updateUser(long id, User u) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_UPDATE);
		prepStmt.setString(1, u.getUsername());
		prepStmt.setString(2, u.getPassword());
		prepStmt.setLong(3, u.getuType());
		prepStmt.setLong(4, id);
		prepStmt.executeUpdate();
		
		conn.commit();
		conn.close();
	}
	
	/* DELETE */
	public static void deleteUser(long id) throws SQLException {
		Connection conn = OracleDatabase.getConnection();
		
		PreparedStatement prepStmt = conn.prepareStatement(SQL_DELETE);
		prepStmt.setLong(1, id);
		prepStmt.execute();
		
		conn.commit();
		conn.close();
	}
}
