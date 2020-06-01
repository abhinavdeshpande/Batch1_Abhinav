package com.cg;

public class ProductCart {

	private long id;
	private String productName;
	private double price;

	public ProductCart(long id, String productName, double price) {
		this.id = id;
		this.productName = productName;
		this.price = price;
	}

	public ProductCart() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductCart [id=" + id + ", productName=" + productName + ", price=" + price + "]";
	}

}
