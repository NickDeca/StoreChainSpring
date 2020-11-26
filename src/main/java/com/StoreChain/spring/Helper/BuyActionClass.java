package com.StoreChain.spring.Helper;

public class BuyActionClass {

	private int ProductKey;

	private int CustomerKey;
	
	private int Quantity;

	public int getProductKey() {
		return ProductKey;
	}

	public void setProductKey(int productKey) {
		ProductKey = productKey;
	}

	public int getCustomerKey() {
		return CustomerKey;
	}

	public void setCustomerKey(int customerKey) {
		CustomerKey = customerKey;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
}
