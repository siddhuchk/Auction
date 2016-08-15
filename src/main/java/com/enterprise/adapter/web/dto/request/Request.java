package com.enterprise.adapter.web.dto.request;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class Request {
	private String name;
	private long salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Request [name=" + name + ", salary=" + salary + "]";
	}
	
}