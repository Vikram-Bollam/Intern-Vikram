package com.lol;

public class ProductDetails {
	private String productName;
	private String productId;
	private int productPrice;
	private int productDiscount;
	private int finalPrice;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(int productDiscount) {
		this.productDiscount = productDiscount;
	}

	public int getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}

	@Override
	public String toString() {
		return "ProductDetails [productName=" + productName + ", productId=" + productId + ", productPrice="
				+ productPrice + ", productDiscount=" + productDiscount + ", finalPrice=" + finalPrice + "]";
	}

}
