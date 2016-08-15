/**
 * 
 */
package com.enterprise.adapter.web.dto.response;

import java.util.List;

import com.enterprise.adapter.domain.ProductBids;

/**
 * @author karmveer.sharma
 *
 */
public class GetProductBidsByProductResponse {
	private List<ProductBids> productBids;

	public List<ProductBids> getProductBids() {
		return productBids;
	}

	public void setProductBids(List<ProductBids> productBids) {
		this.productBids = productBids;
	}

	@Override
	public String toString() {
		return "GetProductBidsByProductResponse [productBids=" + productBids
				+ "]";
	}

}
