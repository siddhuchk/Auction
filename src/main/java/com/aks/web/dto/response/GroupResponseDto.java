package com.aks.web.dto.response;

public class GroupResponseDto<T> {
	private ResponseHeaderDto headers;
	private T body;

	public ResponseHeaderDto getHeaders() {
		return headers;
	}

	public void setHeaders(ResponseHeaderDto headers) {
		this.headers = headers;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GroupResponseDto [headers=");
		builder.append(headers);
		builder.append(", body=");
		builder.append(body);
		builder.append("]");
		return builder.toString();
	}

}
