package com.StoreChain.spring.Helper;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.StoreChain.spring.Repository.StoreRepository;
import com.StoreChain.spring.Repository.TransactionsRepository;
import com.StoreChain.spring.model.Store;
import com.StoreChain.spring.model.Transactions;

public class StoreManager {
	@Autowired
	private StoreRepository storeContext;
	@Autowired
	private TransactionsRepository tContext;
	
	public void CreateStoreRow(double capital, int transactionId, int operation) throws Exception {
		
		TransactionManager tManager = new TransactionManager();
		try {
			Store lastTStore = storeContext.findTopByOrderByIdDesc();
			
			Transactions transaction = tContext.findById(transactionId).get();
			
			if(lastTStore != null) {
				double finalSum = operation == 0 ? lastTStore.getCapital() - capital : lastTStore.getCapital() + capital;
				Store saving = new Store();
				saving.setCapital(finalSum);
				saving.setTransactionKey(transactionId);
				storeContext.save(saving);
				
			}else if(lastTStore == null || transaction.getState() == StateEnum.OkState.ordinal()) {
				
                if(operation == StoreCalculationEnum.Subtraction.ordinal())
                    throw new Exception("First ever transaction should be an addition");
                
                Date timeNow = HelperMethods.getCurrentDate();
                
                Transactions first = new Transactions();
                first.setRecipientKey(0);
                first.setProviderKey(0);
                first.setCapital(capital);
                first.setProductKey(0);
                first.setDateOfTransaction(timeNow);
                first.setProductQuantity(0);
                first.setState(StateEnum.OkState.ordinal());
                first.setErrorText("");
                
                tManager.AddTransaction(first);
                
                Store newRow = new Store();
                newRow.setCapital(capital);
                newRow.setTransactionKey(transactionId);
                storeContext.save(newRow);
			}
			
		}catch(Exception err) {
			throw err;
		}
		
	}

}
