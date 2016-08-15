/**
 * 
 */
package com.enterprise.adapter.web.dto.request;

import java.time.LocalDateTime;

/**
 * @author anuj.kumar2
 *
 */
public class CreateBidderRequest {
	private Long bidderUserId;
	private Long productBidId;
	private Long amount;
	private LocalDateTime bidTime;

	public Long getBidderUserId() {
		return bidderUserId;
	}

	public void setBidderUserId(Long bidderUserId) {
		this.bidderUserId = bidderUserId;
	}

	public Long getProductBidId() {
		return productBidId;
	}

	public void setProductBidId(Long productBidId) {
		this.productBidId = productBidId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public LocalDateTime getBidTime() {
		return bidTime;
	}

	public void setBidTime(LocalDateTime bidTime) {
		this.bidTime = bidTime;
	}

	@Override
	public String toString() {
		return "CreateBidderRequest [bidderUserId=" + bidderUserId
				+ ", productBidId=" + productBidId + ", amount=" + amount
				+ ", bidTime=" + bidTime + "]";
	}

}
