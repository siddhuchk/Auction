/**
 * 
 */
package com.enterprise.adapter.web.dto.response;

import java.util.List;

import com.enterprise.adapter.domain.Bidders;

/**
 * @author karmveer.sharma
 *
 */
public class GetAllBiddersResponse {
	private List<Bidders> bidders;

	public List<Bidders> getBidders() {
		return bidders;
	}

	public void setBidders(List<Bidders> bidders) {
		this.bidders = bidders;
	}

	@Override
	public String toString() {
		return "GetAllBiddersResponse [bidders=" + bidders + "]";
	}
}
