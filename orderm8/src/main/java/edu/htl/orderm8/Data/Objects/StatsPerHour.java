package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatsPerHour {
	private long hour;
	private long profit;
	
	public StatsPerHour() {
		super();
	}

	public StatsPerHour(long hour, long profit) {
		super();
		this.hour = hour;
		this.profit = profit;
	}

	public long getHour() {
		return hour;
	}

	public void setHour(long hour) {
		this.hour = hour;
	}

	public long getProfit() {
		return profit;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "StatsPerHour [hour=" + hour + ", profit=" + profit + "]";
	}
	
}
