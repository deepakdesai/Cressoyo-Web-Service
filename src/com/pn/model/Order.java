package com.pn.model;

import java.sql.Date;

public class Order {

	int userId;
	int orderId;
	String productName;
	Double rate;
	int qty;
	Date orderDate;
	
	public Order() {
		
	}

	public Order(int userId, int orderId, String productName, Double rate, int qty, Date orderDate) {
		super();
		this.userId = userId;
		this.orderId = orderId;
		this.productName = productName;
		this.rate = rate;
		this.qty = qty;
		this.orderDate = orderDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "Order [userId=" + userId + ", orderId=" + orderId + ", productName=" + productName + ", rate=" + rate
				+ ", qty=" + qty + ", orderDate=" + orderDate + "]";
	}
	
}
