package SAP.Models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZOrder {
	private long id_order;
	private String waiter;
	private Date date;
	private float profit;
	private String product;
	private String orderinfo;
	
	public ZOrder() {
		super();
	}
	
	public ZOrder(long id_order, String waiter, Date date, float profit, String product, String orderinfo) {
		super();
		this.id_order = id_order;
		this.waiter = waiter;
		this.date = date;
		this.profit = profit;
		this.product = product;
		this.orderinfo = orderinfo;
	}

	public long getId_order() {
		return id_order;
	}
	public void setId_order(long id_order) {
		this.id_order = id_order;
	}
	public String getWaiter() {
		return waiter;
	}
	public void setWaiter(String waiter) {
		this.waiter = waiter;
	}
	public String getDateBAPI() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(this.date);
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getProfit() {
		return profit;
	}
	public void setProfit(float profit) {
		this.profit = profit;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getOrderinfo() {
		return orderinfo;
	}

	public void setOrderinfo(String orderinfo) {
		this.orderinfo = orderinfo;
	}

	@Override
	public String toString() {
		return "ZOrder [id_order=" + id_order + ", waiter=" + waiter + ", date=" + date + ", profit=" + profit
				+ ", product=" + product + ", orderinfo=" + orderinfo + "]";
	}
	
}
