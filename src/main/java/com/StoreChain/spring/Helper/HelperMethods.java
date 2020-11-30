package com.StoreChain.spring.Helper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.StoreChain.spring.Enum.DepartmentProductState;
import com.StoreChain.spring.Enum.StateEnum;
import com.StoreChain.spring.Enum.StoreCalculationEnum;
import com.StoreChain.spring.Repository.DepartmentRepository;
import com.StoreChain.spring.Repository.ProductRepository;
import com.StoreChain.spring.Repository.SuppliersRepository;
import com.StoreChain.spring.Repository.TransactionsRepository;
import com.StoreChain.spring.model.Customers;
import com.StoreChain.spring.model.Department;
import com.StoreChain.spring.model.Products;
import com.StoreChain.spring.model.Suppliers;
import com.StoreChain.spring.model.Transactions;


public class HelperMethods {

	@Autowired
	private TransactionsRepository tContext;
	@Autowired
	private static SuppliersRepository sContext;
	@Autowired
	private static ProductRepository pContext;
	@Autowired
	private static DepartmentRepository dContext;
	
	public static void Supply(int suppliersKey, Products productForSupply, int quantityToSupply) throws Exception {
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
            
            throw err;
		}
	}

	private static void UpdateProductInStorage(Products productForSupply, int suppliersKey, int quantityToSupply) throws Exception {
		try {
			if (productForSupply.getSupplier_Key() != suppliersKey)
				throw new Exception("The specified supplier does not contain the product");
			
			int end = productForSupply.getQuantityInStorage() + quantityToSupply;
			
			productForSupply.setQuantityInStorage(end);
		}catch(Exception err) {
			throw err;
		}
	}

	private static void UpdateSuppliersDue(int suppliersKey, double boughtValue) throws Exception {
		try {
			Suppliers supplier = sContext.findById(suppliersKey).get();
			if (supplier == null)
				throw new Exception("The specified supplier was not found");
			
			double end = supplier.getPaymentDue() + boughtValue;
			
			supplier.setPaymentDue(end);
			
			sContext.save(supplier);
		}catch(Exception err) {
			throw err;
		}
	}

	public static ArrayList<Products> BringAllProductsDepartments() {
		
		return new ArrayList<Products>(); //TODO 
	}

	public static void Display(Products product, Integer numToBeDisplayed, Integer department) throws Exception {		
		try {
			Products foundProduct = pContext.findById(product.getid()).get();
			
	        if (foundProduct == null)
	            throw new Exception("No such Product in the database");
			int newQuantity = product.getQuantityInDisplay() + numToBeDisplayed;
			foundProduct.setQuantityInDisplay(newQuantity);	        
	        
			Department connection = dContext.findConnectionProdDepart(foundProduct.getid(), department);
			
			if(connection == null) {
				
				Department newConnection = new Department();
				newConnection.setDepartmentKey(department);
				newConnection.setDescription(foundProduct.getDescription());
				newConnection.setNumber(numToBeDisplayed);
				newConnection.setState(product.getQuantityInDisplay() == numToBeDisplayed ? DepartmentProductState.Filled.ordinal() : DepartmentProductState.NeedFilling.ordinal());
				
				dContext.save(newConnection);				
			}else {
				int newNumber = connection.getNumber() + numToBeDisplayed;
				connection.setNumber(newNumber);
				
                if (connection.getNumber() == product.getMaxDisplay())
                	connection.setState(DepartmentProductState.Filled.ordinal());
                else if (connection.getNumber() > product.getMaxDisplay())
                	connection.setState(DepartmentProductState.OverFilled.ordinal());
                else
                	connection.setState(DepartmentProductState.NeedFilling.ordinal());
				dContext.save(connection);	
			}
			
		}catch(Exception err) {
			throw err;
		}
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
