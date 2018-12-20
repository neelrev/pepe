package com.sde.pepe.gdpr.application.rs.token.vo;

public class Token {
	
	private String grantType;
	private String clientId;
	private String refreshToken;
	private String accessToken;
	
	
	public Token(String grantType,String clientId, String refreshToken) {
		this.grantType=grantType;
		this.refreshToken=refreshToken;
		this.clientId=clientId;
	}//Token
	
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	

	@Override
	public String toString() {
		return "grant_Type="+grantType+"&client_id="+clientId+"&refresh_token="+refreshToken;
	}
	
}
