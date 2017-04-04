package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatsPerTable {
	private long table;
	private long profit;
	
	public StatsPerTable() {
		super();
	}

	public StatsPerTable(long table, long profit) {
		super();
		this.table = table;
		this.profit = profit;
	}

	public long getTable() {
		return table;
	}

	public void setTable(long table) {
		this.table = table;
	}

	public long getProfit() {
		return profit;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "StatsPerTable [table=" + table + ", profit=" + profit + "]";
	}
	
}
