package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.StoreChain.spring.model.Products;
import com.StoreChain.spring.model.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {

	@Query("SELECT TOP 1 * FROM CentralStoreCapital ORDER BY ID DESC")
	Store findTopByOrderByIdDesc();
	
}

