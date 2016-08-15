package com.enterprise.adapter.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.adapter.domain.TestTable;
import com.enterprise.adapter.repository.TestTableRepository;
import com.enterprise.adapter.service.TestTableService;

/**
 * 
 * @author anuj.kumar2
 *
 */
@Service
public class TestTableServiceImpl implements TestTableService {

	private static final Logger logger = LoggerFactory
			.getLogger(TestTableServiceImpl.class);
	@Autowired
	TestTableRepository repository;

	@Override
	public TestTable addNewRow(TestTable table) {
		logger.info("Add row: " + table);
		return repository.save(table);
	}

	@Override
	public List<TestTable> findAll() {
		return repository.findAll();
	}

	@Override
	public TestTable findByName(String name) {
		logger.info("find for name: " + name);
		return repository.findByName(name);
	}

	@Override
	public void udpateRow(TestTable table) {
		logger.info("Update row: " + table);
		repository.save(table);
	}

	@Override
	public void deleteRow(TestTable table) {
		logger.info("delete row: " + table);
		repository.delete(table);
	}
}