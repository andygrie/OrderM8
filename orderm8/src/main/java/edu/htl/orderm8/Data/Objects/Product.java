package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {
	private long idProduct;
	private long fkType;
	private String name;
	private double price;
	private long quantity;
	
	public Product() {
		
	}
	
	public Product(long idProduct, long fkType, String name, double price, long quantity) {
		super();
		this.idProduct = idProduct;
		this.fkType = fkType;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(long idProduct) {
		this.idProduct = idProduct;
	}

	public long getFkType() {
		return fkType;
	}

	public void setFkType(long fkType) {
		this.fkType = fkType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product [idProduct=" + idProduct + ", fkType=" + fkType + ", name=" + name + ", price=" + price
				+ ", quantity=" + quantity + "]";
	}
	
}
