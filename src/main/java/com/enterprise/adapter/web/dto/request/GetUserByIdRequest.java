/**
 * 
 */
package com.enterprise.adapter.web.dto.request;

/**
 * @author karmveer.sharma
 *
 */
public class GetUserByIdRequest {
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GetUserByIdRequest [id=" + id + "]";
	}

}
