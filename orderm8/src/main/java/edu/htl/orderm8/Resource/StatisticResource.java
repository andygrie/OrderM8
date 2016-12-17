package edu.htl.orderm8.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.htl.orderm8.Authentication.Secured;
import edu.htl.orderm8.Data.Objects.Statistic;
import edu.htl.orderm8.Service.StatisticService;

@Path("statistic")
public class StatisticResource {
	private StatisticService statisticService = new StatisticService();
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Statistic getStatistic() {
		return statisticService.getStatistic();
	}
}
