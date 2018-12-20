package com.sde.pepe.gdpr.application.rs.token;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.*;

import java.net.ConnectException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.google.resting.component.impl.ServiceResponse;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.ApiToken;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.token.vo.Token;


public class TokenManager {

	private String domain;
	private String grantType="refresh_token";
	private String clientId="pepe";
	private String tokenUri="/api/v1/private/token";

	public TokenManager(String domain) {
		this.domain=domain;
	}

	private String getTokenUrl(String domain) {
		return domain+tokenUri;
	}

	public List<Header> getCreateHeaders(){
		List<Header> headers=getBaseHeaders();
		headers.add(new BasicHeader("Accept","application/json, text/plain, */*"));
		headers.add(new BasicHeader("Accept-Encoding","gzip, deflate, br"));
		headers.add(new BasicHeader("Accept-Language","en-US,en;q=0.9"));
		headers.add(new BasicHeader("Content-Type","application/json"));
		headers.add(new BasicHeader("Cookie","__cfduid="+cfduid+"; ARRAffinity="+arrAffinity));
		headers.add(new BasicHeader("Expires","86400"));
		headers.add(new BasicHeader("Host",StringUtils.strip(domain, "https://")));
		headers.add(new BasicHeader("Origin",domain));
		headers.add(new BasicHeader("Referer",domain+"/app/"));
		headers.add(new BasicHeader("Pragma","no-cache"));
		headers.add(new BasicHeader("Cache-Control","no-cache"));
		headers.add(new BasicHeader("Connection","keep-alive"));
		headers.add(new BasicHeader("If-Modified-Since","Mon, 26 Jul 1997 05:00:00 GMT"));
		headers.add(new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36"));
		return headers;
	}

	public List<Header> getSearchHeaders(){
		List<Header> headers=getBaseHeaders();
		headers.add(new BasicHeader("Accept","application/json, text/plain, */*"));
		headers.add(new BasicHeader("Accept-Encoding","gzip, deflate, br"));
		headers.add(new BasicHeader("Accept-Language","en-US,en;q=0.9"));
		headers.add(new BasicHeader("Cookie","__cfduid="+cfduid+"; ARRAffinity="+arrAffinity));
		//headers.add(new BasicHeader("Cookie","__cfduid="+cfduid+"; ARRAffinity="+arrAffinity+"; ai_user="+ai_user+"; ai_session="+ai_session));
		headers.add(new BasicHeader("Expires","86400"));
		headers.add(new BasicHeader("Host",StringUtils.strip(domain, "https://")));
		headers.add(new BasicHeader("Referer",domain+"/app/"));
		headers.add(new BasicHeader("Pragma","no-cache"));
		headers.add(new BasicHeader("Cache-Control","no-cache"));
		headers.add(new BasicHeader("Connection","keep-alive"));
		headers.add(new BasicHeader("If-Modified-Since","Mon, 26 Jul 1997 05:00:00 GMT"));
		headers.add(new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36"));
		return headers;
	}

	public List<Header> getBaseHeaders(){
		List<Header> baseHeaders=new ArrayList<Header>();
		baseHeaders.add(new BasicHeader("__cfduid",cfduid));
		baseHeaders.add(new BasicHeader("ARRAffinity",arrAffinity));
		//baseHeaders.add(new BasicHeader("ai_user",ai_user));
		//baseHeaders.add(new BasicHeader("ai_session",ai_session));
		return baseHeaders;
	}//getBaseHeaders

	private String getMessageToPost(ResponseToken responseToken) {
		String refreshToken=responseToken.getRefreshToken();
		return new Token(grantType,clientId,refreshToken).toString();
	}//getMessageToPost

	public Response<ApiToken> getApiToken(ResponseToken oldResponseToken, String transactionUrl){
		ResponseToken responseToken=getNewResponseToken(oldResponseToken);
		List<Header> headers=getBaseHeaders();
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		ApiToken apiToken=null;
		Gson gson=new Gson();
		ServiceResponse response=Resting.get(transactionUrl, 443, null, EncodingTypes.UTF8, headers);
		try {
			apiToken=gson.fromJson(response.getResponseString(),ApiToken.class);

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return new Response<ApiToken>(apiToken, responseToken);
	}//getApiToken

	public ResponseToken getNewResponseToken(ResponseToken oldResponseToken) {
		List<Header> headers=getBaseHeaders();
		String messageToPost=getMessageToPost(oldResponseToken);
		ServiceResponse response=Resting.post(getTokenUrl(domain), 443, null, messageToPost, null, headers, null);
		Gson gson=new Gson();
		ResponseToken responseToken=null;
		 while(responseToken==null) {
			 try {
				responseToken=gson.fromJson(response.getResponseString(), ResponseToken.class);
			 } catch (Exception e) {
					e.printStackTrace();
					 if ((e instanceof ConnectException)||(e instanceof SocketException)||(e instanceof NullPointerException))
						try {
							System.out.println("Exception in connecting. Waiting for 10 seconds and retrying: ");
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}//try thread.sleep
				}//try
		 }//while

		//System.out.println("New response token: "+responseToken);
		return responseToken;
	}//getNewResponseToken

	public static void main(String[] args) {
		String firsttoken="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ2aXJ0dWFsUm9sZSI6ZmFsc2UsInJvbGUiOiJTaXRlIEFkbWluIiwidXNlcl9uYW1lIjoic3VqYXRhLmRlQHBob3RvbmluZm90ZWNoLm5ldCIsImxhbmd1YWdlSWQiOjEsInNlc3Npb25JZCI6ImQ4MTNmMjg1LWNmMjQtNDZlOS04NzYxLWZiODE5MjU2YWZmZCIsInRlbmFudEd1aWQiOiIzMDljNDQzYS1lOGI5LTQ0NzMtYjE4Yi0xZGY1MzZlMDlhNWIiLCJhdXRob3JpdGllcyI6WyJTaXRlIEFkbWluIl0sImNsaWVudF9pZCI6Im9uZXRydXN0Iiwib3JnR3JvdXBJZCI6IjEiLCJzY29wZSI6WyJyZWFkIl0sInJvb3QiOiI0MTMxN2VkZS1hZTIyLTQ4ZmEtOTc2OS1mMmM4OTQ2M2ZkMzQiLCJhdGkiOiI2NDI4OWY3YS1jMTliLTQ2ZGYtYjE0Yi01Y2RlMDc5ODUzY2UiLCJ0ZW5hbnRJZCI6ODYsImd1aWQiOiJkOWVmMDFiYi1jOWIwLTQxMTMtOGQwYi1mNzgxYTZjNTU4MzQiLCJkb05vdERlbGV0ZSI6ZmFsc2UsImV4cCI6MTUyOTA1NzEyNCwianRpIjoiNDA0ZGVlNjAtZDM2ZS00MDU2LWE1MTItOTQwN2NjZTI5ZWZjIiwiZW1haWwiOiJzdWphdGEuZGVAcGhvdG9uaW5mb3RlY2gubmV0In0.TU0h9Snj7EP_ye1bF0On0eGUf91uKMalckLuB_r5XlBpCzrDwscEw4tSBgYa72mitTWV4EiGaGtS7ZMC67xvBTtd7vqmRT4KNU6Si_3zUXG1eKLV4j-nhx7dx7Ash1Ril08NM3WJ4PxpX4Y8VkvD1_W7y3zuRA4GEqZ226tGm7IEQiJT-DF392lyZcSHEh6NCV_lRynwvkPZlXQR_1oAzeASRrA7rWCYSU65wtNpI0lx8yfFWrRD0eoGOgjnRNfW2oGmLSL6nTuaIqSUOIH_wC0tVjih9uxqZ4MLXu6wrWoMnraUEpHaGf9PUqZXsQpMsChI2GrchKDyGL3rWRsb9Q"
;
		ResponseToken token=new ResponseToken(firsttoken);
		TokenManager tokenManager=new TokenManager("https://uat-de.pepe.com");
		while(true) {
			ResponseToken responseToken=tokenManager.getNewResponseToken(token);
			System.out.println("refresh token: \n---------\n"+token.getRefreshToken());
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			token=responseToken;

		}//true
	}


}//TokenManager
