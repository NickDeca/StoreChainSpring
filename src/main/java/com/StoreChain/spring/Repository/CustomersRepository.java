package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreChain.spring.model.Customers;


public interface CustomersRepository extends JpaRepository<Customers, Integer> {
	
	
}

