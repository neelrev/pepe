package com.sde.pepe.gdpr.application.rs;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.CRUDTypes.CREATE;
import static com.sde.pepe.gdpr.application.rs.response.ResponseUtil.checkResponse;

import java.net.ConnectException;
import java.net.SocketException;
import java.util.List;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.google.resting.component.impl.ServiceResponse;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;
import com.sde.pepe.gdpr.application.rs.token.TokenManager;
import com.sde.pepe.gdpr.framework.CRUDAble;

public abstract class BaseService<T extends CRUDAble<String>> {
	
	protected Class<T> type;
	protected TokenManager tokenManager;
	
	public BaseService(Class<T> type) {
		BaseProperties baseProperties=new BaseProperties();
		this.tokenManager=new TokenManager(domain);
		this.type=type;
	}
	
	protected T getExtraction(String transactionUrl,List<Header> headers) {
		T extracted=null;
		Gson gson=new Gson();
		ServiceResponse response=Resting.get(transactionUrl, 443, null, EncodingTypes.UTF8, headers);
		try {
			extracted=gson.fromJson(response.getResponseString(), type);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} 
		return extracted;
	}//getExtraction
	
	
	public List<T> getExtractions(String transactionUrl,List<Header> headers,String alias){
		List<T> extractions=null;
	
		while(extractions==null) {
			try {
				extractions=Resting.getByJSON(transactionUrl, 443, null, type, alias, EncodingTypes.UTF8, headers);
			} catch (Exception e) {
				e.printStackTrace();
				 if ((e instanceof ConnectException)||(e instanceof SocketException)||(e instanceof NullPointerException)||(e instanceof JsonParseException))
					try {
						System.out.println("Connect exception. Waiting for 10 seconds and retrying: ");
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}//try thread.sleep
			}//try extractions
		}//while
		
		return extractions;
	} //getExtractions
	
	protected boolean create(T object, String transactionUrl,List<Header> headers ) {
		return checkResponse(Resting.post(transactionUrl, 443, null, object.getCreateString(), null, headers, null),object,CREATE);
	}
}
