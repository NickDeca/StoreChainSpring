package com.StoreChain.spring.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.StoreChain.spring.Helper.ResupplyHelperClass;
import com.StoreChain.spring.model.*;

public interface ProductRepository extends JpaRepository<Products, Integer> {

	@Query(value = "SELECT new com.StoreChain.spring.Helper.ResupplyHelperClass(?1 AS product, pmq.MinStorage - ?1.QuantityInStorage AS quantityToSupply)"
					+ "FROM [ProductMinQuantity] pmq"
					+ "WHERE pmq.id = ?1.Id AND ?1.QuantityInStorage < pmq.[MinStorage]")
	List<ResupplyHelperClass> CheckIfNeedReSupply(Products product);
}

