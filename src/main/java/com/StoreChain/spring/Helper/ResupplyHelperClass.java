package com.StoreChain.spring.Helper;

import org.springframework.stereotype.Component;

import com.StoreChain.spring.model.Products;

@Component
public class ResupplyHelperClass {

	private Products product;
	private int quantityToSupply;
	
	public ResupplyHelperClass(Products Product, int quantityToSupply) {
		this.product = Product;
		this.quantityToSupply = quantityToSupply;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public int getQuantityToSupply() {
		return quantityToSupply;
	}

	public void setQuantityToSupply(int quantityToSupply) {
		this.quantityToSupply = quantityToSupply;
	}
	
	
}
