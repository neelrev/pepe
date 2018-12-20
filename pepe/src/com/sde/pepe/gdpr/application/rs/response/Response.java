package com.sde.pepe.gdpr.application.rs.response;

import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;

public class Response<T> {
	
	private T  result;
	private ResponseToken responseToken;
	
	public Response(T result,ResponseToken responseToken ) {
		this.result=result;
		this.responseToken=responseToken;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public ResponseToken getResponseToken() {
		return responseToken;
	}

	public void setResponseToken(ResponseToken responseToken) {
		this.responseToken = responseToken;
	}

}
