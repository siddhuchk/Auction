package com.enterprise.adapter.web.dto.response;

import java.util.List;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class AllResultResponse {
	private List<AllResult> expiredBids;

	public List<AllResult> getExpiredBids() {
		return expiredBids;
	}

	public void setExpiredBids(List<AllResult> expiredBids) {
		this.expiredBids = expiredBids;
	}

	@Override
	public String toString() {
		return "AllResultResponse [expiredBids=" + expiredBids + "]";
	}

}
