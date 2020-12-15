package com.StoreChain.spring.Helper;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.StoreChain.spring.Enum.StateEnum;
import com.StoreChain.spring.Enum.StoreCalculationEnum;
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

		Date timeNow = HelperMethods.getCurrentDate();
		TransactionManager tManager = new TransactionManager();

		Store lastTStore = storeContext.findTopByOrderByIdDesc();

		Transactions transaction = tContext.findById(transactionId).get();

		if (lastTStore != null) {
			double finalSum = operation == 0 ? lastTStore.getCapital() - capital : lastTStore.getCapital() + capital;
			Store saving = new Store();
			saving.setCapital(finalSum);
			saving.setTransactionKey(transactionId);
			storeContext.save(saving);

		} else if (lastTStore == null || transaction.getState() == StateEnum.OkState.ordinal()) {
			Transactions first = new Transactions(0, 0, capital, 0, timeNow, 0, StateEnum.OkState.ordinal(), "");
			try {
				if (operation == StoreCalculationEnum.Subtraction.ordinal())
					throw new Exception("First ever transaction should be an addition");

				tManager.AddTransaction(first);

				Store newRow = new Store();
				newRow.setCapital(capital);
				newRow.setTransactionKey(transactionId);
				storeContext.save(newRow);

			} catch (Exception err) {
				first.setErrorText(err.getMessage());
				first.setState(StateEnum.ErrorState.ordinal());
				tManager.AddTransaction(first);
				throw err;
			}
		}

	}

}
