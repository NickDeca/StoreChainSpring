package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.StoreChain.spring.model.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {

	@Query(value = "SELECT TOP 1 * FROM CentralStoreCapital ORDER BY ID DESC", nativeQuery = true)
	Store findTopByOrderByIdDesc();
	
}

