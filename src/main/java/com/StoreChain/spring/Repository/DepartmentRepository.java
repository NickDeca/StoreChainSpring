package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.StoreChain.spring.model.Department;


public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	@Query(value = "SELECT TOP 1 * FROM Department WHERE Id = ?1 AND [Prod_Id] = ?2")
	Department findConnectionProdDepart(int id, int prodId);

	@Query(value = "SELECT TOP 1 * FROM Department WHERE [Prod_Id] = ?1")
	Department findConnectionByProdId(int id);
}
