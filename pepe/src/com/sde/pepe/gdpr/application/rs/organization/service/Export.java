package com.sde.pepe.gdpr.application.rs.organization.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.uri.URITypes.SEARCHORGANIZATIONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.google.resting.component.impl.ServiceResponse;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.framework.Writeable;

public class Export extends BaseService<Organization>{
	
	public Export() {
		super(Organization.class);
	}
	
	private String getOrganizationUrl() {
		return domain+SEARCHORGANIZATIONS.getUri();
	}
	protected Response<List<Organization>> fetchOrganizations(ResponseToken oldResponseToken) {
		List<Organization> organizations=new ArrayList<Organization>();		
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		String transactionUrl=getOrganizationUrl();
		List<Header> headers=tokenManager.getBaseHeaders();
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		Gson gson=new Gson();
		ServiceResponse response=Resting.get(transactionUrl, 443, null, null, headers);
			try {
			JSONArray array=new JSONArray(response.getResponseString());
			 for(int i=0;i<array.length();i++) {
				 Organization org=gson.fromJson(array.getJSONObject(i).toString(), Organization.class);
				 organizations.add(org);
			 }
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		responseToken=tokenManager.getNewResponseToken(responseToken);
		System.out.println("Size of organizations: "+organizations.size());
		return new Response<List<Organization>>(organizations,responseToken);
	}//getOrganizations	
	
	protected Response<Map<String,Organization>> fetchOrganizationMap(ResponseToken oldResponseToken) {
		Map<String,Organization> organizations=new ConcurrentHashMap<String,Organization>();		
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		String transactionUrl=getOrganizationUrl();
		List<Header> headers=tokenManager.getBaseHeaders();
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		Gson gson=new Gson();
		ServiceResponse response=Resting.get(transactionUrl, 443, null, EncodingTypes.UTF8, headers);
		
		while(response==null) {
			response=Resting.get(transactionUrl, 443, null, EncodingTypes.UTF8, headers);
		}
			try {
			JSONArray array=new JSONArray(response.getResponseString());
			 for(int i=0;i<array.length();i++) {
				 Organization org=gson.fromJson(array.getJSONObject(i).toString(), Organization.class);
				 organizations.put(org.getId(),org);
			 }
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		responseToken=tokenManager.getNewResponseToken(responseToken);
		System.out.println("Size of organizations: "+organizations.size());
		return new Response<Map<String,Organization>>(organizations,responseToken);
	}//fetchOrganizationMap	
	
	protected List<Writeable> getOrganizations(ResponseToken oldResponseToken) {
		List<Writeable> organizations=new ArrayList<Writeable>();		
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		String transactionUrl=getOrganizationUrl();
		List<Header> headers=tokenManager.getBaseHeaders();
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		Gson gson=new Gson();
		ServiceResponse response=Resting.get(transactionUrl, 443, null, EncodingTypes.UTF8, headers);
			try {
			JSONArray array=new JSONArray(response.getResponseString());
			 for(int i=0;i<array.length();i++) {
				 Organization org=gson.fromJson(array.getJSONObject(i).toString(), Organization.class);
				 organizations.add(org);
			 }
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//organizations=Resting.getByJSON(transactionUrl, 443, null, Organization.class, "", null, headers);
		
		
		
		// for(Organization org:orgtree) {
		//	 traverse(org);
		// }
		//create a simple org object
		//System.out.println(organizations.get(0));
		System.out.println(organizations.size());


		return organizations;
	}//getOrganizations

	
	
}//Export
