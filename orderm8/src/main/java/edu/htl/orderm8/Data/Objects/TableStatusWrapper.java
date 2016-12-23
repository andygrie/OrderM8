package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TableStatusWrapper {
	private Table table;
	private boolean hasOpenOrders;
	
	public TableStatusWrapper() {
		
	}

	public TableStatusWrapper(Table table, boolean hasOpenOrders) {
		super();
		this.table = table;
		this.hasOpenOrders = hasOpenOrders;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public boolean isHasOpenOrders() {
		return hasOpenOrders;
	}

	public void setHasOpenOrders(boolean hasOpenOrders) {
		this.hasOpenOrders = hasOpenOrders;
	}
	@Override
	public String toString() {
		return "TableStatusWrapper [table=" + table + ", hasOpenOrders=" + hasOpenOrders + "]";
	}
}
