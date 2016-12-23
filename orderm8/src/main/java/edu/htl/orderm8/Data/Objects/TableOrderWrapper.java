package edu.htl.orderm8.Data.Objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TableOrderWrapper {
	private Table table;
	private ArrayList<OrderEntry> orders;
	
	public TableOrderWrapper() {
		
	}
	
	public TableOrderWrapper(Table table, ArrayList<OrderEntry> orders) {
		super();
		this.table = table;
		this.orders = orders;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public ArrayList<OrderEntry> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<OrderEntry> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "TableOrderWrapper [table=" + table + ", orders=" + orders + "]";
	}
}
