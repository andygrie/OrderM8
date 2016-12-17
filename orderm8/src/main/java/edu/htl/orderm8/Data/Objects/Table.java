package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Table {
	private long idTable;
	private double xLL;
	private double yLL;
	
	private double xUR;
	private double yUR;
	
	public Table() {
		
	}
	
	public Table(long idTable, double xll, double yll, double xur, double yur) {
		super();
		this.idTable = idTable;
		this.xLL = xll;
		this.yLL = yll;
		
		this.xUR = xur;
		this.yUR = yur;
	}

	public long getIdTable() {
		return idTable;
	}

	public void setIdTable(long idTable) {
		this.idTable = idTable;
	}

	public double getxLL() {
		return xLL;
	}

	public void setxLL(double xLL) {
		this.xLL = xLL;
	}

	public double getyLL() {
		return yLL;
	}

	public void setyLL(double yLL) {
		this.yLL = yLL;
	}

	public double getxUR() {
		return xUR;
	}

	public void setxUR(double xUR) {
		this.xUR = xUR;
	}

	public double getyUR() {
		return yUR;
	}

	public void setyUR(double yUR) {
		this.yUR = yUR;
	}

	@Override
	public String toString() {
		return "Table [idTable=" + idTable + ", xLL=" + xLL + ", yLL=" + yLL + ", xUR=" + xUR + ", yUR=" + yUR + "]";
	}
}
