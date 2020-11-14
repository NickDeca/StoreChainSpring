package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreChain.spring.model.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

}
