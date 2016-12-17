package edu.htl.orderm8.Service;

import java.sql.SQLException;
import java.util.List;

import edu.htl.orderm8.Data.Database.Database;
import edu.htl.orderm8.Data.Objects.Bill;
import edu.htl.orderm8.Data.Objects.Statistic;
import edu.htl.orderm8.Exception.ConflictException;
import edu.htl.orderm8.Exception.DataNotFoundException;
import edu.htl.orderm8.Exception.InternalServerErrorException;

public class StatisticService {
	
	public Statistic getStatistic () {
		try {
			return Database.getInstance().getStatistic();
		} catch(SQLException exception) {
			System.out.println(exception.getErrorCode());
			System.out.println(exception.getMessage());
			throw new javax.ws.rs.InternalServerErrorException("database error");
		}
	}
}
