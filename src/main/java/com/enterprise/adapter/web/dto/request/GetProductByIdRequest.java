/**
 * 
 */
package com.enterprise.adapter.web.dto.request;

/**
 * @author karmveer.sharma
 *
 */
public class GetProductByIdRequest {
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GetProductByIdRequest [id=" + id + "]";
	}

}
