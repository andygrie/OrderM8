package edu.htl.orderm8.Data.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Table {
	private long idTable;
	private double x;
	private double y;
	
	public Table() {
		
	}
	
	public Table(long idTable, double x, double y) {
		super();
		this.idTable = idTable;
		this.x = x;
		this.y = y;
	}

	public long getIdTable() {
		return idTable;
	}

	public void setIdTable(long idTable) {
		this.idTable = idTable;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Table [idTable=" + idTable + ", x=" + x + ", y=" + y + "]";
	}
	
}
