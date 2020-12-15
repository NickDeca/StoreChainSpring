package com.StoreChain.spring.Helper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.StoreChain.spring.Enum.DepartmentProductState;
import com.StoreChain.spring.Enum.StateEnum;
import com.StoreChain.spring.Enum.StoreCalculationEnum;
import com.StoreChain.spring.Repository.CustomersRepository;
import com.StoreChain.spring.Repository.DepartmentRepository;
import com.StoreChain.spring.Repository.ProductRepository;
import com.StoreChain.spring.Repository.StoreRepository;
import com.StoreChain.spring.Repository.SuppliersRepository;
import com.StoreChain.spring.Repository.TransactionsRepository;
import com.StoreChain.spring.model.Customers;
import com.StoreChain.spring.model.Department;
import com.StoreChain.spring.model.Products;
import com.StoreChain.spring.model.Store;
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
	@Autowired
	private static CustomersRepository cContext;
	@Autowired
	private static StoreRepository storeContext;
	
	public static void Supply(int suppliersKey, Products productForSupply, int quantityToSupply) throws Exception {
		java.sql.Date date = getCurrentDate();
		TransactionManager tManager = new TransactionManager();
		
		Transactions transaction = new Transactions(suppliersKey, 0, new Double(0), productForSupply.getid(), date, 0, StateEnum.UndeterminedState.ordinal(), "");
		
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

	public static List<Products> BringAllProductsDepartments() {
		
		return pContext.findAll();
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
				
				Department newConnection = new Department( foundProduct.getDescription(),
						department,	
						numToBeDisplayed, 
						product.getQuantityInDisplay() == numToBeDisplayed ? DepartmentProductState.Filled.ordinal() : DepartmentProductState.NeedFilling.ordinal(),
						product.getid());
				
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

	public static void CheckValidity(BuyActionClass buyClass) throws Exception {
        if (buyClass.getCustomerKey() == 0)
            throw new Exception("Please select a customer");
        if(buyClass.getProductKey() == 0)
            throw new Exception("Please select a product");
        if(buyClass.getQuantity() == 0)
            throw new Exception("Please give an amount of product you want to buy");
	}

	public static void UpdateProductInDisplay(Products productBought, Department departConn) throws Exception {
		Department departmentConnection = dContext.findConnectionByProdId(productBought.getid());
		
        if(departmentConnection == null)
            throw new Exception("Connection in department by product id was not found");
        
        int newNumber = departmentConnection.getNumber() - productBought.getTransactionQuantity();
        departmentConnection.setNumber(newNumber);
        dContext.save(departmentConnection);
        
        int newQuantity = productBought.getQuantityInDisplay() - productBought.getTransactionQuantity();
        productBought.setQuantityInDisplay(newQuantity);
        departConn.setNumber(newQuantity);
        
        pContext.save(productBought);       
        dContext.save(departConn);
	}

	public static void Buy(Products productBought, Customers customer) {

		try {
			double summedValue = productBought.getCostSold() * productBought.getTransactionQuantity();
			
	        if (summedValue == 0)
	            throw new Exception("No Products bought");
        	        
            if (customer.getCapital() - summedValue <= 0)
                throw new Exception("Customer Does not have the capital required to the transaction");           
            BuyTransaction(productBought, customer, summedValue);
            
		}catch(Exception err) {
			
		}
	}
	
	@Transactional
	public static void BuyTransaction(Products product, Customers buyer, double summedValue) throws Exception {
        TransactionManager tManager = new TransactionManager();
        Date transactionTime = getCurrentDate();
		double newCapital = buyer.getCapital() - summedValue;		
		buyer.setCapital(newCapital);
		
		Department departConn = dContext.findConnectionByProdId(product.getid());
		
		Transactions newTransaction = new Transactions(0,buyer.getId(),summedValue,product.getid(), transactionTime, 0, StateEnum.UndeterminedState.ordinal(), "");
        try {
        	cContext.save(buyer);
        	
        	UpdateProductInDisplay(product, departConn);
        	
        	newTransaction.setProductQuantity(product.getTransactionQuantity());
        	newTransaction.setErrorText("OK!");
        	newTransaction.setState(StateEnum.OkState.ordinal());
        	
            tManager.AddTransaction(newTransaction);
        } catch(Exception err) {
        	newTransaction.setErrorText(err.getMessage());
        	newTransaction.setState(StateEnum.ErrorState.ordinal());
        	
            tManager.AddTransaction(newTransaction);
        	throw err;
        }
        
        CheckIfNeedReSupply(product);
        
        Store newStoreState = new Store();
        storeContext.save(newStoreState);
	}
	
	private static void CheckIfNeedReSupply(Products product) throws Exception {
		List<ResupplyHelperClass> list = pContext.CheckIfNeedReSupply(product.getid(), product.getQuantityInStorage());		
		for(ResupplyHelperClass x : list){
			Products pSupplier = pContext.findById(x.getProduct()).get();
			Supply(pSupplier.getSupplier_Key(), pSupplier, x.getQuantityToSupply() - product.getQuantityInStorage());
		}
		
	}

	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();

		java.util.Date currentDate = calendar.getTime();
		return new java.sql.Date(currentDate.getTime());
	}
}
