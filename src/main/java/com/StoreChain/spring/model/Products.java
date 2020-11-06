package com.StoreChain.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Products")
public class Products {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int Supplier_Key;
	
	private int Category;
	
	private int Department;
	private Boolean isDisplay;
	
	@Column(name="CostSold")
	private double CostSold;

	@Column(name="CostBought")   
	@GeneratedValue(strategy = GenerationType.AUTO)
	private double CostBought;

	@Column(name="QuantityInStorage")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int QuantityInStorage;

	@Column(name="QuantityInDisplay")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int QuantityInDisplay;

	@Column(name="TransactionQuantity")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int TransactionQuantity;

	@Column(name="DepartmentForeignId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int DepartmentForeignId;

	public int getSupplier_Key() {
		return Supplier_Key;
	}

	public void setSupplier_Key(int supplier_Key) {
		Supplier_Key = supplier_Key;
	}

	public int getCategory() {
		return Category;
	}

	public void setCategory(int category) {
		Category = category;
	}

	public int getDepartment() {
		return Department;
	}

	public void setDepartment(int department) {
		Department = department;
	}

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public double getCostSold() {
		return CostSold;
	}

	public void setCostSold(double costSold) {
		CostSold = costSold;
	}

	public double getCostBought() {
		return CostBought;
	}

	public void setCostBought(double costBought) {
		CostBought = costBought;
	}

	public int getQuantityInStorage() {
		return QuantityInStorage;
	}

	public void setQuantityInStorage(int quantityInStorage) {
		QuantityInStorage = quantityInStorage;
	}

	public int getQuantityInDisplay() {
		return QuantityInDisplay;
	}

	public void setQuantityInDisplay(int quantityInDisplay) {
		QuantityInDisplay = quantityInDisplay;
	}

	public int getTransactionQuantity() {
		return TransactionQuantity;
	}

	public void setTransactionQuantity(int transactionQuantity) {
		TransactionQuantity = transactionQuantity;
	}

	public int getDepartmentForeignId() {
		return DepartmentForeignId;
	}

	public void setDepartmentForeignId(int departmentForeignId) {
		DepartmentForeignId = departmentForeignId;
	}
	
}
