package com.StoreChain.spring.Helper;

import javax.persistence.Column;

import org.springframework.stereotype.Component;

import com.StoreChain.spring.model.Products;

@Component
public class ResupplyHelperClass {
	@Column(nullable = true)
	private Integer productId;
	@Column(nullable = true)
	private Integer quantityToSupply;
	
	//public ResupplyHelperClass(int asd) {} //TODO fix !!!!!!!!!
	
	public ResupplyHelperClass(Integer ProductId, Integer quantityToSupply) {
		this.productId = ProductId;
		this.quantityToSupply = quantityToSupply;
	}

	public Integer getProduct() {
		return productId;
	}

	public void setProduct(int productId) {
		this.productId = productId;
	}

	public Integer getQuantityToSupply() {
		return quantityToSupply;
	}

	public void setQuantityToSupply(int quantityToSupply) {
		this.quantityToSupply = quantityToSupply;
	}
	
	
}
