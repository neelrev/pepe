package com.sde.pepe.gdpr.application.rs.token.vo;

import com.google.gson.annotations.SerializedName;

public class ResponseToken {
	
	@SerializedName("access_token")
	private String accessToken;
	
	@SerializedName("refresh_token")
	private String refreshToken;
	
	private boolean used=false;
	
	public ResponseToken(String refreshToken) {
		this.refreshToken=refreshToken;
	}
	
	public String toString() {
		return "access_token:"+accessToken+"| refresh_token="+refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
