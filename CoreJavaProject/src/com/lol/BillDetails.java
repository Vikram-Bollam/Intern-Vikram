package com.lol;

public class BillDetails {
	String orderProduct;
	int orderQuantity;
	int totalProductprice;

	public String getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(String orderProduct) {
		this.orderProduct = orderProduct;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public int getTotalProductprice() {
		return totalProductprice;
	}

	public void setTotalProductprice(int totalProductprice) {
		this.totalProductprice = totalProductprice;
	}

	@Override
	public String toString() {
		return "" + orderProduct + "    " + orderQuantity + "      " + totalProductprice + "\n";
	}

}
