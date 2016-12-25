/**
 * 
 */
package com.aks.web.dto.response;

import java.util.List;

import com.aks.domain.ProductBids;

/**
 * @author karmveer.sharma
 *
 */
public class GetAllProductBidsResponse {
	private List<ProductBids> productBids;

	public List<ProductBids> getProductBids() {
		return productBids;
	}

	public void setProductBids(List<ProductBids> productBids) {
		this.productBids = productBids;
	}

	@Override
	public String toString() {
		return "GetAllProductBidsResponse [productBids=" + productBids + "]";
	}

}
