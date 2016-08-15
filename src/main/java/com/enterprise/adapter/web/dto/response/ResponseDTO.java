package com.enterprise.adapter.web.dto.response;

/**
 * 
 * @author anuj.kumar2
 *
 * @param <T>
 */
public class ResponseDTO<T> {

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
		builder.append("ResponseDTO [headers=");
		builder.append(headers);
		builder.append(", body=");
		builder.append(body);
		builder.append("]");
		return builder.toString();
	}

}
