package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Statistic {
	private long cntBills;
	private long cntUsers;
	private long cntTables;
	private long cntOrderEntries;
	private long cntProductTypes;
	private long open_bills;
	private long profit;
	
	public Statistic() {
		
	}
	
	public Statistic(long cntBills, long cntUsers, long cntTables, long cntOrderEntries, long cntProductTypes,
			long open_bills, long profit) {
		super();
		this.cntBills = cntBills;
		this.cntUsers = cntUsers;
		this.cntTables = cntTables;
		this.cntOrderEntries = cntOrderEntries;
		this.cntProductTypes = cntProductTypes;
		this.open_bills = open_bills;
		this.profit = profit;
	}

	public long getCntBills() {
		return cntBills;
	}

	public void setCntBills(long cntBills) {
		this.cntBills = cntBills;
	}

	public long getCntUsers() {
		return cntUsers;
	}

	public void setCntUsers(long cntUsers) {
		this.cntUsers = cntUsers;
	}

	public long getCntTables() {
		return cntTables;
	}

	public void setCntTables(long cntTables) {
		this.cntTables = cntTables;
	}

	public long getCntOrderEntries() {
		return cntOrderEntries;
	}

	public void setCntOrderEntries(long cntOrderEntries) {
		this.cntOrderEntries = cntOrderEntries;
	}

	public long getCntProductTypes() {
		return cntProductTypes;
	}

	public void setCntProductTypes(long cntProductTypes) {
		this.cntProductTypes = cntProductTypes;
	}

	public long getOpen_bills() {
		return open_bills;
	}

	public void setOpen_bills(long open_bills) {
		this.open_bills = open_bills;
	}

	public long getProfit() {
		return profit;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "Statistic [cntBills=" + cntBills + ", cntUsers=" + cntUsers + ", cntTables=" + cntTables
				+ ", cntOrderEntries=" + cntOrderEntries + ", cntProductTypes=" + cntProductTypes + ", open_bills="
				+ open_bills + ", profit=" + profit + "]";
	}
}
