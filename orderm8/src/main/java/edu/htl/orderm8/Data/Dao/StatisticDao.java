package edu.htl.orderm8.Data.Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.htl.orderm8.Data.Database.OracleDatabase;
import edu.htl.orderm8.Data.Objects.Statistic;

public class StatisticDao {
	/* 								SQL 								*/
	private static final String SQL_SELECT_STATS = "SELECT * FROM VIEW_STATS";
	
	private static final String FIELD_CNTBILLS = "CNTBILLS";
	private static final String FIELD_CNTUSERS = "CNTUSERS";
	private static final String FIELD_CNTTABLES = "CNTTABLES";
	private static final String FIELD_CNTORDERENTRIES = "CNTORDERENTRIES";
	private static final String FIELD_CNTPRODUCTTYPES = "CNTPRODUCTTYPES";
	private static final String FIELD_CNTPRODUCTS = "CNTPRODUCTS";
	private static final String FIELD_OPEN_BILLS = "OPEN_BILLS";
	private static final String FIELD_PROFIT = "PROFIT";
	/*__________________________________________________________________*/
	
	private static Statistic getStatistic(ResultSet rs) throws SQLException {
		long cntBills = rs.getLong(FIELD_CNTBILLS);
		long cntUsers = rs.getLong(FIELD_CNTUSERS);
		long cntTables = rs.getLong(FIELD_CNTTABLES);
		long cntOrderEntries = rs.getLong(FIELD_CNTORDERENTRIES);
		long cntProductTypes = rs.getLong(FIELD_CNTPRODUCTTYPES);
		long cntProducts = rs.getLong(FIELD_CNTPRODUCTS);
		long open_bills= rs.getLong(FIELD_OPEN_BILLS);
		long profit = rs.getLong(FIELD_PROFIT);
		

		return new Statistic(cntBills, cntUsers, cntTables, cntOrderEntries, cntProductTypes, cntProducts, open_bills, profit);
	}
	
	public static Statistic getStatistic() {
		Statistic statistic = null;
		
		try {
			Connection conn = OracleDatabase.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_SELECT_STATS);
			
			if (rs.next()) {
				statistic = getStatistic(rs);
			}
			
			conn.close();
		} catch(Exception e) {
			System.out.println("DAO:getStatistic() failed! " + e.getMessage());
		}
		
		return statistic;
	}
}
