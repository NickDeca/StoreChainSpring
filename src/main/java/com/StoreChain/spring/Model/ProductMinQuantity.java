package com.StoreChain.spring.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ProductMinQuantity")
public class ProductMinQuantity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public int Id ;
    public int ProductKey ;
    public int MinDisplay ;
    public int MinStorage ;

	public ProductMinQuantity(int productKey, int minDisplay, int minStorage) {
		super();
		ProductKey = productKey;
		MinDisplay = minDisplay;
		MinStorage = minStorage;
	}
	
	public ProductMinQuantity() {}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getProductKey() {
		return ProductKey;
	}

	public void setProductKey(int productKey) {
		ProductKey = productKey;
	}

	public int getMinDisplay() {
		return MinDisplay;
	}

	public void setMinDisplay(int minDisplay) {
		MinDisplay = minDisplay;
	}

	public int getMinStorage() {
		return MinStorage;
	}

	public void setMinStorage(int minStorage) {
		MinStorage = minStorage;
	}
	
	
}
