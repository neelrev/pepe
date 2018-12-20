package com.sde.pepe.gdpr.application.rs.collectionpoint.vo;

import com.google.gson.annotations.SerializedName;

public class ApiToken {
	
	@SerializedName("token")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
