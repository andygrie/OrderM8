package edu.htl.orderm8.Data.Objects;

public class StatsPerUser {
	private String username;
	private long profit;
	
	public StatsPerUser() {
		super();
	}

	public StatsPerUser(String username, long profit) {
		super();
		this.username = username;
		this.profit = profit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getProfit() {
		return profit;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "StatsPerUser [username=" + username + ", profit=" + profit + "]";
	}

	
}
