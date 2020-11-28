package com.StoreChain.spring.Helper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.StoreChain.spring.Repository.TransactionsRepository;
import com.StoreChain.spring.model.Customers;
import com.StoreChain.spring.model.Products;
import com.StoreChain.spring.model.Transactions;


public class HelperMethods {

	@Autowired
	private TransactionsRepository tContext;
	
	public static void Supply(int suppliersKey, Products productForSupply, int quantityToSupply) {
		java.sql.Date date = getCurrentDate();
		TransactionManager tManager = new TransactionManager();
		
		Transactions transaction = new Transactions();
		transaction.setRecipientKey(suppliersKey);
		transaction.setProviderKey(0);
		transaction.setDateOfTransaction(date);
		transaction.setErrorText("");
		transaction.setState(StateEnum.UndeterminedState.ordinal());
		
		try {
			double boughtValue = productForSupply.getCostBought() * quantityToSupply;
			
            UpdateSuppliersDue(suppliersKey, boughtValue);
            UpdateProductInStorage(productForSupply, suppliersKey, quantityToSupply);
            
            transaction.setCapital(boughtValue);
            transaction.setState(StateEnum.OkState.ordinal());
            tManager.AddTransaction(transaction);
            
            int transactionId = tManager.GetTransaction(transaction).getId();
            
            StoreManager sManager = new StoreManager();
            sManager.CreateStoreRow(boughtValue, transactionId, StoreCalculationEnum.Subtraction.ordinal());
		}catch(Exception err) {
			transaction.setErrorText(err.getMessage());
			transaction.setState(StateEnum.ErrorState.ordinal());
            tManager.AddTransaction(transaction);            
		}
	}

	private static void UpdateProductInStorage(Products productForSupply, int suppliersKey, int quantityToSupply) {
		// TODO Auto-generated method stub
		
	}

	private static void UpdateSuppliersDue(int suppliersKey, double boughtValue) {
		// TODO Auto-generated method stub
		
	}

	public static ArrayList<Products> BringAllProductsDepartments() {
		
		return new ArrayList<Products>();
	}

	public static void Display(Products productForDisplay, Integer transactionQuantity, Integer department) {
		
	}

	public static void CheckValidity(BuyActionClass product) {
		
	}

	public static void UpdateProductInDisplay(Products productBought) {
		
	}

	public static void Buy(Products productBought, Customers customer) {
		
	}
	
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();

		java.util.Date currentDate = calendar.getTime();
		return new java.sql.Date(currentDate.getTime());
	}
}
