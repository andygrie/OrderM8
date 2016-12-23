package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BillWrapper {
	private long id;
	
	public BillWrapper() {
		
	}

	public BillWrapper(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BillWrapper [id=" + id + "]";
	}
}
