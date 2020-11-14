package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.StoreChain.spring.model.*;

public interface ProductRepository extends JpaRepository<Products, Integer> {

}

