package com.enterprise.adapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enterprise.adapter.domain.TestTable;

/**
 * 
 * @author anuj.kumar2
 *
 */
@Repository
public interface TestTableRepository extends JpaRepository<TestTable, Integer> {
	
	TestTable findByName(String name);

	List<TestTable> findAll();
}
