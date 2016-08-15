package com.enterprise.adapter.web.dto.response;

import java.util.Date;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class AllResult {

	private long id;
	private String productName;
	private Date startTime;
	private Date endTime;
	private String winnerName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}
}