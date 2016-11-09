package edu.htl.orderm8.Data.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDatabase {
	
	public static Connection getConnection() throws SQLException {
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
		}catch(ClassNotFoundException e) { 
			System.out.println("Can't find class oracle.jdbc.driver.OracleDriver"); 
		} 
		
		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE","teohasele","0123456789");
	}
	
}
