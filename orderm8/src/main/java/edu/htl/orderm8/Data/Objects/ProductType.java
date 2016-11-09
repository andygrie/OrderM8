package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductType {
	private long idType;
	private String name;
	private long vat;
	
	public ProductType() {
		
	}
	
	public ProductType(long idType, String name, long vat) {
		super();
		this.idType = idType;
		this.name = name;
		this.vat = vat;
	}

	public long getIdType() {
		return idType;
	}

	public void setIdType(long idType) {
		this.idType = idType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getVat() {
		return vat;
	}

	public void setVat(long vat) {
		this.vat = vat;
	}

	@Override
	public String toString() {
		return "ProductType [idType=" + idType + ", name=" + name + ", vat=" + vat + "]";
	}
	
}
