package edu.htl.orderm8.Data.Objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BillOrderEntriesWrapper {
	private Bill bill;
	private ArrayList<OrderEntry> orderEntries;
	
	public BillOrderEntriesWrapper() {
		
	}

	public BillOrderEntriesWrapper(Bill bill, ArrayList<OrderEntry> orderEntries) {
		super();
		this.bill = bill;
		this.orderEntries = orderEntries;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public ArrayList<OrderEntry> getOrderEntries() {
		return orderEntries;
	}

	public void setOrderEntries(ArrayList<OrderEntry> orderEntries) {
		this.orderEntries = orderEntries;
	}

	@Override
	public String toString() {
		return "BillOrderEntriesWrapper [bill=" + bill + ", orderEntries=" + orderEntries + "]";
	}
}
