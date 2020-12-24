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

	public void Supply(int suppliersKey, Products productForSupply, int quantityToSupply,
			ProductRepository productContext, SuppliersRepository SupplierContext, TransactionsRepository tContext, StoreRepository storeContext) throws Exception {
		java.sql.Date date = getCurrentDate();
		TransactionManager tManager = new TransactionManager();
		
		Transactions transaction = new Transactions(suppliersKey, 0, new Double(0), productForSupply.getid(), date, quantityToSupply, StateEnum.UndeterminedState.ordinal(), "", "Bought from supplier");
		
		try {
			double boughtValue = productForSupply.getCostBought() * quantityToSupply;
			
            UpdateSuppliersDue(suppliersKey, boughtValue, productContext, SupplierContext);
            UpdateProductInStorage(productForSupply, suppliersKey, quantityToSupply, productContext);
            
            
            transaction.setCapital(boughtValue);
            transaction.setState(StateEnum.OkState.ordinal());
            int transactionId = tManager.AddTransaction(transaction, tContext).getId();
                        
            StoreManager sManager = new StoreManager();
            sManager.CreateStoreRow(boughtValue, transactionId, StoreCalculationEnum.Subtraction.ordinal(), storeContext, tContext);
		}catch(Exception err) {
			transaction.setErrorText(err.getMessage());
			transaction.setState(StateEnum.ErrorState.ordinal());
            tManager.AddTransaction(transaction, tContext);      
            
            throw err;
		}
	}

	private void UpdateProductInStorage(Products productForSupply, int suppliersKey, int quantityToSupply, ProductRepository productContext) throws Exception {
		try {
			if (productForSupply.getSupplier_Key() != suppliersKey)
				throw new Exception("The specified supplier does not contain the product");
									
			int end = productForSupply.getQuantityInStorage() + quantityToSupply;
			
			productForSupply.setQuantityInStorage(end);
			
			productContext.save(productForSupply);
		}catch(Exception err) {
			throw err;
		}
	}

	private void UpdateSuppliersDue(int suppliersKey, double boughtValue,ProductRepository productContext, SuppliersRepository SupplierContext) throws Exception {
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

	public List<Products> BringAllProductsDepartments(ProductRepository productContext) {
		
		return productContext.findAll();
	}

	public void Display(int productKey, Integer numToBeDisplayed, Integer department, ProductRepository productContext, DepartmentRepository departmentContext) throws Exception {		
		try {
			Products foundProduct = productContext.findById(productKey).get();
			
	        if (foundProduct == null)
	            throw new Exception("No such Product in the database");
			int newQuantity = foundProduct.getQuantityInDisplay() + numToBeDisplayed;
			foundProduct.setQuantityInDisplay(newQuantity);	        
	        
			Department connection = departmentContext.findConnectionProdDepart(foundProduct.getid(), department);
			
			if(connection == null) {
				
				Department newConnection = new Department( foundProduct.getDescription(),
						department,	
						numToBeDisplayed, 
						foundProduct.getQuantityInDisplay() == numToBeDisplayed ? DepartmentProductState.Filled.ordinal() : DepartmentProductState.NeedFilling.ordinal(),
								foundProduct.getid());
				
				departmentContext.save(newConnection);				
			}else {
				int newNumber = connection.getNumber() + numToBeDisplayed;
				connection.setNumber(newNumber);
				
                if (connection.getNumber() == foundProduct.getMaxDisplay())
                	connection.setState(DepartmentProductState.Filled.ordinal());
                else if (connection.getNumber() > foundProduct.getMaxDisplay())
                	connection.setState(DepartmentProductState.OverFilled.ordinal());
                else
                	connection.setState(DepartmentProductState.NeedFilling.ordinal());
                departmentContext.save(connection);	
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

	public void UpdateProductInDisplay(Products productBought, DepartmentRepository departmentContext, ProductRepository productContext) throws Exception {
		Department departmentConnection = departmentContext.findConnectionByProdId(productBought.getid());
		
        if(departmentConnection == null)
            throw new Exception("Connection in department by product id was not found");
        
        int newNumber = departmentConnection.getNumber() - productBought.getTransactionQuantity();
        departmentConnection.setNumber(newNumber);
        departmentContext.save(departmentConnection);
        
        int newQuantity = productBought.getQuantityInDisplay() - productBought.getTransactionQuantity();
        productBought.setQuantityInDisplay(newQuantity);
        
        productContext.save(productBought);      
	}

	public void Buy(Products productBought, Customers customer, CustomersRepository customerContext, StoreRepository storeContext,DepartmentRepository departmentContext, 
			ProductRepository productContext, SuppliersRepository supplierContext, TransactionsRepository tContext) {

		try {
			double summedValue = productBought.getCostSold() * productBought.getTransactionQuantity();
			
	        if (summedValue == 0)
	            throw new Exception("No Products bought");
        	        
            if (customer.getCapital() - summedValue <= 0)
                throw new Exception("Customer Does not have the capital required to the transaction");           
            BuyTransaction(productBought, customer, summedValue, customerContext, storeContext, departmentContext, productContext, supplierContext, tContext);
            
		}catch(Exception err) {
			
		}
	}
	
	@Transactional
	public void BuyTransaction(Products product, Customers buyer, double summedValue, CustomersRepository customerContext, StoreRepository storeContext, DepartmentRepository departmentContext, 
			ProductRepository productContext, SuppliersRepository supplierContext , TransactionsRepository tContext) throws Exception {
        TransactionManager tManager = new TransactionManager();
        Date transactionTime = getCurrentDate();
		double newCapital = buyer.getCapital() - summedValue;		
		buyer.setCapital(newCapital);
		
		Transactions newTransaction = new Transactions(0,buyer.getId(),summedValue,product.getid(), transactionTime, 0, StateEnum.UndeterminedState.ordinal(), "", "Sold to customer");
        try {
        	customerContext.save(buyer);
        	
        	UpdateProductInDisplay(product, departmentContext , productContext);
        	
        	newTransaction.setProductQuantity(product.getTransactionQuantity());
        	newTransaction.setErrorText("OK!");
        	newTransaction.setState(StateEnum.OkState.ordinal());
        	
            tManager.AddTransaction(newTransaction, tContext);
            
            StoreManager sManager = new StoreManager();
            sManager.CreateStoreRow(summedValue, newTransaction.getId(), StoreCalculationEnum.Addition.ordinal(), storeContext, tContext);
        } catch(Exception err) {
        	newTransaction.setErrorText(err.getMessage());
        	newTransaction.setState(StateEnum.ErrorState.ordinal());
        	
            tManager.AddTransaction(newTransaction, tContext);
        	throw err;
        }
        
        CheckIfNeedReSupply(product, productContext, supplierContext, tContext, storeContext);
	}
	
	private void CheckIfNeedReSupply(Products product, ProductRepository productContext, SuppliersRepository supplierContext, TransactionsRepository tContext, StoreRepository storeContext) throws Exception {
		List<ResupplyHelperClass> list = productContext.CheckIfNeedReSupply(product.getid(), product.getQuantityInStorage());		
		for(ResupplyHelperClass x : list){
			Products pSupplier = productContext.findById(x.getProduct()).get();
			Supply(pSupplier.getSupplier_Key(), pSupplier, x.getQuantityToSupply() - product.getQuantityInStorage(), productContext, supplierContext, tContext, storeContext);
		}
		
	}

	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();

		java.util.Date currentDate = calendar.getTime();
		return new java.sql.Date(currentDate.getTime());
	}
}
