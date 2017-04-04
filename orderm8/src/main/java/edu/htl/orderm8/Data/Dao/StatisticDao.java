package edu.htl.orderm8.Data.Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.htl.orderm8.Data.Database.OracleDatabase;
import edu.htl.orderm8.Data.Objects.Statistic;
import edu.htl.orderm8.Data.Objects.StatsPerHour;
import edu.htl.orderm8.Data.Objects.StatsPerTable;
import edu.htl.orderm8.Data.Objects.StatsPerUser;
import edu.htl.orderm8.Service.UserService;

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
	
	private static final String SQL_SELECT_STATS_HOUR = "select * from view_stats_profit_hour";
	private static final String FIELD_HOUR = "HOUR";
	
	private static final String SQL_SELECT_STATS_USER = "select * from view_stats_profit_user";
	private static final String FIELD_USERNAME = "USERNAME";
	
	private static final String SQL_SELECT_STATS_TABLE = "select * from view_stats_profit_table";
	private static final String FIELD_FKTABLE = "FKTABLE";
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
	
	private static StatsPerHour getStatsHour(ResultSet rs) throws SQLException {
		long hour = rs.getLong(FIELD_HOUR);
		long profit = rs.getLong(FIELD_PROFIT);
		
		return new StatsPerHour(hour, profit);
	}
	
	private static StatsPerTable getStatsTable(ResultSet rs) throws SQLException {
		long table = rs.getLong(FIELD_FKTABLE);
		long profit = rs.getLong(FIELD_PROFIT);
		
		return new StatsPerTable(table, profit);
	}
	
	private static StatsPerUser getStatsUser (ResultSet rs) throws SQLException {
		String username = rs.getString(FIELD_USERNAME);
		long profit = rs.getLong(FIELD_PROFIT);
		
		return new StatsPerUser(username, profit);
	}
	
	public static Statistic getStatistic() throws Exception {
		Statistic statistic = null;
		
		Connection conn = OracleDatabase.getConnection();
		
		Statement stmt = conn.createStatement();
		
		//Normal Stats
		ResultSet rs = stmt.executeQuery(SQL_SELECT_STATS);
		if (rs.next()) {
			statistic = getStatistic(rs);
		}
		//UserStats
		rs = stmt.executeQuery(SQL_SELECT_STATS_USER);
		while(rs.next()) {
			StatsPerUser spu = getStatsUser(rs);
			statistic.getStatsUser().add(spu);
		}
		//TableStats
		rs = stmt.executeQuery(SQL_SELECT_STATS_TABLE);
		while(rs.next()) {
			StatsPerTable spt = getStatsTable(rs);
			statistic.getStatsTable().add(spt);
		}
		//HourStats
		rs = stmt.executeQuery(SQL_SELECT_STATS_HOUR);
		while(rs.next()) {
			StatsPerHour sph = getStatsHour(rs);
			statistic.getStatsHour().add(sph);
		}
		
		conn.close();
		
		return statistic;
	}
}
