package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderEntry {
	private long idOrderEntry;
	private long fkProduct;
	private long fkTable;
	private long fkUser;
	private long fkBill;
	private String note;
	private Boolean cancelled;
	private Boolean coupon;
	
	public OrderEntry() {
		
	}
	
	public OrderEntry(long idOrderEntry, long fkProduct, long fkTable, long fkUser, long fkBill, String note,
			Boolean cancelled, Boolean coupon) {
		super();
		this.idOrderEntry = idOrderEntry;
		this.fkProduct = fkProduct;
		this.fkTable = fkTable;
		this.fkUser = fkUser;
		this.fkBill = fkBill;
		this.note = note;
		this.cancelled = cancelled;
		this.coupon = coupon;
	}

	public long getIdOrderEntry() {
		return idOrderEntry;
	}

	public void setIdOrderEntry(long idOrderEntry) {
		this.idOrderEntry = idOrderEntry;
	}

	public long getFkProduct() {
		return fkProduct;
	}

	public void setFkProduct(long fkProduct) {
		this.fkProduct = fkProduct;
	}

	public long getFkTable() {
		return fkTable;
	}

	public void setFkTable(long fkTable) {
		this.fkTable = fkTable;
	}

	public long getFkUser() {
		return fkUser;
	}

	public void setFkUser(long fkUser) {
		this.fkUser = fkUser;
	}

	public long getFkBill() {
		return fkBill;
	}

	public void setFkBill(long fkBill) {
		this.fkBill = fkBill;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getCancelled() {
		return cancelled;
	}

	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Boolean getCoupon() {
		return coupon;
	}

	public void setCoupon(Boolean coupon) {
		this.coupon = coupon;
	}

	@Override
	public String toString() {
		return "OrderEntry [idOrderEntry=" + idOrderEntry + ", fkProduct=" + fkProduct + ", fkTable=" + fkTable
				+ ", fkUser=" + fkUser + ", fkBill=" + fkBill + ", note=" + note + ", cancelled=" + cancelled
				+ ", coupon=" + coupon + "]";
	}
	
}
