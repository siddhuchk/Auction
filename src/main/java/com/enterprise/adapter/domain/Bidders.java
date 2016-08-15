/**
 * 
 */
package com.enterprise.adapter.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author karmveer.sharma
 *
 */
@Entity
@Table(name = "bidders")
public class Bidders implements Serializable {
	private static final long serialVersionUID = -3028114538116703624L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "bidderUserId", nullable = false)
	private Long bidderUserId;
	@Column(name = "productBidId", nullable = false)
	private Long productBidId;
	@Column(name = "amount", nullable = false)
	private Long amount;
	@Column(name = "bidTime", nullable = false)
	private Date bidTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getBidTime() {
		return bidTime;
	}

	public void setBidTime(Date bidTime) {
		this.bidTime = bidTime;
	}

	
	@Override
	public String toString() {
		return "Bidders [id=" + id + ", bidderUserId=" + bidderUserId + ", productBidId=" + productBidId + ", amount="
				+ amount + ", bidTime=" + bidTime + "]";
	}

}
