package edu.htl.orderm8.Data.Objects;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Bill {
	private long idBill;
	private Timestamp billDate;
	
	public Bill() {
		
	}
	
	public Bill(long idBill, Timestamp billDate) {
		super();
		this.idBill = idBill;
		this.billDate = billDate;
	}

	public long getIdBill() {
		return idBill;
	}

	public void setIdBill(long idBill) {
		this.idBill = idBill;
	}

	public Timestamp getBillDate() {
		return billDate;
	}

	public void setBillDate(Timestamp billDate) {
		this.billDate = billDate;
	}

	@Override
	public String toString() {
		return "Bill [idBill=" + idBill + ", billDate=" + billDate + "]";
	}
	
}
