package com.StoreChain.spring.Helper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.StoreChain.spring.Enum.DepartmentProductState;
import com.StoreChain.spring.Enum.StateEnum;
import com.StoreChain.spring.Enum.StoreCalculationEnum;
import com.StoreChain.spring.Model.Customers;
import com.StoreChain.spring.Model.Department;
import com.StoreChain.spring.Model.Products;
import com.StoreChain.spring.Model.Store;
import com.StoreChain.spring.Model.Suppliers;
import com.StoreChain.spring.Model.Transactions;
import com.StoreChain.spring.Repository.CustomersRepository;
import com.StoreChain.spring.Repository.DepartmentRepository;
import com.StoreChain.spring.Repository.ProductRepository;
import com.StoreChain.spring.Repository.StoreRepository;
import com.StoreChain.spring.Repository.SuppliersRepository;
import com.StoreChain.spring.Repository.TransactionsRepository;

public class HelperMethods {
	
	private ProductRepository ProductContext;	
	private CustomersRepository CustomerContext;
	private DepartmentRepository DepartmentContext;
	private TransactionsRepository TransactionContext;
	private SuppliersRepository SupplierContext;
	private StoreRepository StoreContext;
	
	public HelperMethods(ProductRepository pContext, CustomersRepository cContext, DepartmentRepository dContext, TransactionsRepository tContext, SuppliersRepository suContext, StoreRepository stContext) {
		ProductContext = pContext;
		CustomerContext = cContext;
		DepartmentContext = dContext;
		TransactionContext = tContext;
		SupplierContext = suContext;
		StoreContext = stContext;
	}

	public void Supply(int suppliersKey, Products productForSupply, int quantityToSupply) throws Exception {
		java.sql.Date date = getCurrentDate();
		TransactionManager tManager = new TransactionManager();
		
		Transactions transaction = new Transactions(suppliersKey, 0, new Double(0), productForSupply.getid(), date, quantityToSupply, StateEnum.UndeterminedState.ordinal(), "", "Bought from supplier");
		
		try {
			double boughtValue = productForSupply.getCostBought() * quantityToSupply;
			
            UpdateSuppliersDue(suppliersKey, boughtValue);
            UpdateProductInStorage(productForSupply, suppliersKey, quantityToSupply);
            
            
            transaction.setCapital(boughtValue);
            transaction.setState(StateEnum.OkState.ordinal());
            int transactionId = tManager.AddTransaction(transaction, TransactionContext).getId();
                        
            StoreManager sManager = new StoreManager();
            sManager.CreateStoreRow(boughtValue, transactionId, StoreCalculationEnum.Subtraction.ordinal(), StoreContext, TransactionContext);
		}catch(Exception err) {
			transaction.setErrorText(err.getMessage());
			transaction.setState(StateEnum.ErrorState.ordinal());
            tManager.AddTransaction(transaction, TransactionContext);      
            
            throw err;
		}
	}

	private void UpdateProductInStorage(Products productForSupply, int suppliersKey, int quantityToSupply) throws Exception {
		try {
			if (productForSupply.getSupplier_Key() != suppliersKey)
				throw new Exception("The specified supplier does not contain the product");
									
			int end = productForSupply.getQuantityInStorage() + quantityToSupply;
			
			productForSupply.setQuantityInStorage(end);
			
			ProductContext.save(productForSupply);
		}catch(Exception err) {
			throw err;
		}
	}

	private void UpdateSuppliersDue(int suppliersKey, double boughtValue) throws Exception {
		try {
			Suppliers supplier = SupplierContext.findById(suppliersKey).get();
			if (supplier == null)
				throw new Exception("The specified supplier was not found");
			
			double end = supplier.getPayment_Due() + boughtValue;
			
			supplier.setPayment_Due(end);
			
			SupplierContext.save(supplier);
		}catch(Exception err) {
			throw err;
		}
	}

	public List<Products> BringAllProductsDepartments() {
		
		return ProductContext.findAll();
	}

	public void Display(int productKey, Integer numToBeDisplayed, Integer department) throws Exception {		
		try {
			Products foundProduct = ProductContext.findById(productKey).get();
			
	        if (foundProduct == null)
	            throw new Exception("No such Product in the database");
			int newQuantityDisplay = foundProduct.getQuantityInDisplay() + numToBeDisplayed;
			int newQuantityStorage = foundProduct.getQuantityInStorage() - numToBeDisplayed;
			foundProduct.setQuantityInDisplay(newQuantityDisplay);	        
	        foundProduct.setQuantityInStorage(newQuantityStorage);
			Department connection = DepartmentContext.findConnectionByProdId(foundProduct.getid());
			
			ProductContext.save(foundProduct);
			if(connection == null) {		
				
				foundProduct.setDepartment(department);
				foundProduct.setDepartmentForeignId(department);
				
				Department newConnection = new Department( foundProduct.getDescription(),
						department,	
						numToBeDisplayed, 
						foundProduct.getQuantityInDisplay() == numToBeDisplayed ? DepartmentProductState.Filled.ordinal() : DepartmentProductState.NeedFilling.ordinal(),
								foundProduct.getid());
				
				DepartmentContext.save(newConnection);				
			}else {
				int newNumber = connection.getNumber() + numToBeDisplayed;
				connection.setNumber(newNumber);
				
                if (connection.getNumber() == foundProduct.getMaxDisplay())
                	connection.setState(DepartmentProductState.Filled.ordinal());
                else if (connection.getNumber() > foundProduct.getMaxDisplay())
                	connection.setState(DepartmentProductState.OverFilled.ordinal());
                else
                	connection.setState(DepartmentProductState.NeedFilling.ordinal());
                DepartmentContext.save(connection);	
			}
			
		}catch(Exception err) {
			throw err;
		}
	}

	public void CheckValidity(BuyActionClass buyClass) throws Exception {
        if (buyClass.getCustomerKey() == 0)
            throw new Exception("Please select a customer");
        if(buyClass.getProductKey() == 0)
            throw new Exception("Please select a product");
        if(buyClass.getQuantity() == 0)
            throw new Exception("Please give an amount of product you want to buy");
	}

	public void UpdateProductInDisplay(Products productBought) throws Exception {
		Department departmentConnection = DepartmentContext.findConnectionByProdId(productBought.getid());
		
        if(departmentConnection == null)
            throw new Exception("Connection in department by product id was not found");
        
        int newNumber = departmentConnection.getNumber() - productBought.getTransactionQuantity();
        departmentConnection.setNumber(newNumber);
        DepartmentContext.save(departmentConnection);
        
        int newQuantity = productBought.getQuantityInDisplay() - productBought.getTransactionQuantity();
        productBought.setQuantityInDisplay(newQuantity);
        
        ProductContext.save(productBought);      
	}

	public void Buy(Products productBought, Customers customer) {

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
	public void BuyTransaction(Products product, Customers buyer, double summedValue) throws Exception {
        TransactionManager tManager = new TransactionManager();
        Date transactionTime = getCurrentDate();
		double newCapital = buyer.getCapital() - summedValue;		
		buyer.setCapital(newCapital);
		
		Transactions newTransaction = new Transactions(0,buyer.getId(),summedValue,product.getid(), transactionTime, 0, StateEnum.UndeterminedState.ordinal(), "", "Sold to customer");
        try {
        	CustomerContext.save(buyer);
        	
        	UpdateProductInDisplay(product);
        	
        	newTransaction.setProductQuantity(product.getTransactionQuantity());
        	newTransaction.setErrorText("OK!");
        	newTransaction.setState(StateEnum.OkState.ordinal());
        	
            tManager.AddTransaction(newTransaction, TransactionContext);
            
            StoreManager sManager = new StoreManager();
            sManager.CreateStoreRow(summedValue, newTransaction.getId(), StoreCalculationEnum.Addition.ordinal(), StoreContext, TransactionContext);
        } catch(Exception err) {
        	newTransaction.setErrorText(err.getMessage());
        	newTransaction.setState(StateEnum.ErrorState.ordinal());
        	
            tManager.AddTransaction(newTransaction, TransactionContext);
        	throw err;
        }
        
        CheckIfNeedReSupply(product);
	}
	
	private void CheckIfNeedReSupply(Products product) throws Exception {
		List<ResupplyHelperClass> list = ProductContext.CheckIfNeedReSupply(product.getid(), product.getQuantityInStorage());		
		for(ResupplyHelperClass x : list){
			Products pSupplier = ProductContext.findById(x.getProduct()).get();
			Supply(pSupplier.getSupplier_Key(), pSupplier, x.getQuantityToSupply() - product.getQuantityInStorage());
		}
		
	}

	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();

		java.util.Date currentDate = calendar.getTime();
		return new java.sql.Date(currentDate.getTime());
	}
}
