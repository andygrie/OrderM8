package edu.htl.orderm8.Data.Objects;

public class Point {
	public double x;
	public double y;
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Data [x=" + x + ", y=" + y + "]";
	}
}
