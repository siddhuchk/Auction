package com.enterprise.adapter.service;

import java.util.List;

import com.enterprise.adapter.domain.TestTable;

/**
 * 
 * @author anuj.kumar2
 *
 */
public interface TestTableService {

	TestTable addNewRow(TestTable table);

	List<TestTable> findAll();

	TestTable findByName(String name);

	void udpateRow(TestTable table);

	void deleteRow(TestTable table);
}
