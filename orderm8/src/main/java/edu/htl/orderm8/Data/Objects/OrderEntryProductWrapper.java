package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderEntryProductWrapper {
	private OrderEntry orderEntry;
	private Product product;
	
	public OrderEntryProductWrapper() {
		
	}

	public OrderEntryProductWrapper(OrderEntry orderEntry, Product product) {
		super();
		this.orderEntry = orderEntry;
		this.product = product;
	}

	public OrderEntry getOrderEntry() {
		return orderEntry;
	}

	public void setOrderEntry(OrderEntry orderEntry) {
		this.orderEntry = orderEntry;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "OrderEntryProductWrapper [orderEntry=" + orderEntry + ", product=" + product + "]";
	}
}
