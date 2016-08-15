/**
 * 
 */
package com.enterprise.adapter.web.dto.request;

import java.time.LocalDateTime;

/**
 * @author karmveer.sharma
 *
 */
public class CreateProductBidRequest {
	private Long productId;
	private LocalDateTime bidStartTime;
	private LocalDateTime bidStopTime;
	private Long winnerUserId;
	private Long minPrice;
	private Long numBidders;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public LocalDateTime getBidStartTime() {
		return bidStartTime;
	}

	public void setBidStartTime(LocalDateTime bidStartTime) {
		this.bidStartTime = bidStartTime;
	}

	public LocalDateTime getBidStopTime() {
		return bidStopTime;
	}

	public void setBidStopTime(LocalDateTime bidStopTime) {
		this.bidStopTime = bidStopTime;
	}

	public Long getWinnerUserId() {
		return winnerUserId;
	}

	public void setWinnerUserId(Long winnerUserId) {
		this.winnerUserId = winnerUserId;
	}

	public Long getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}

	public Long getNumBidders() {
		return numBidders;
	}

	public void setNumBidders(Long numBidders) {
		this.numBidders = numBidders;
	}

	@Override
	public String toString() {
		return "CreateProductBidRequest [productId=" + productId
				+ ", bidStartTime=" + bidStartTime + ", bidStopTime="
				+ bidStopTime + ", winnerUserId=" + winnerUserId
				+ ", minPrice=" + minPrice + ", numBidders=" + numBidders + "]";
	}

}
