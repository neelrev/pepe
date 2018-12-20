package com.sde.pepe.gdpr.application.rs.purpose.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.BaseProperties.purposeListingPages;
import static com.sde.pepe.gdpr.application.rs.uri.URITypes.SEARCHPURPOSE;
import static com.sde.pepe.gdpr.application.rs.uri.URITypes.SEARCHPURPOSES;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.framework.Correctable;
import com.sde.pepe.gdpr.framework.Writeable;

public class Export extends BaseService<Purpose>{
	
	public Export() {
		super(Purpose.class);
	}
	
	private String getSearchPurposeUrl(String name) {
		String encodedPurposeName="";
		try {
			encodedPurposeName=URLEncoder.encode(name,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}//try
		return domain+SEARCHPURPOSES.getUri()+"?page=0&size=20&sort=Id,desc&name="+encodedPurposeName;
	}
	
	private String getPurposes(int i) {
		return domain+SEARCHPURPOSES.getUri()+"?page="+i+"&size=20&sort=Id,desc";
	}
	
	private String getPurposeUrl(String purposeId) {
		return domain+SEARCHPURPOSE.getUri()+"/"+purposeId;
	}
	
	public Response<Purpose> getPurpose(String id, ResponseToken oldResponseToken) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getBaseHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		
		Purpose purpose=getExtraction(getPurposeUrl(id),headers);
				
		return new Response<Purpose>(purpose, responseToken);		
	}//getPurpose
	
	public Response<List<Purpose>> searchPurpose(String name, ResponseToken oldResponseToken) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getBaseHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		
		List<Purpose> purposes=getExtractions(getSearchPurposeUrl(name), headers, "content");
		
		return new Response<List<Purpose>>(purposes, responseToken);		
	}
	
	protected List<Writeable> getPurposes(ResponseToken oldResponseToken) {
		List<Writeable> purposes=new ArrayList<Writeable>();		
		for(int i=0;i<purposeListingPages;i++) {
			ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
			String transactionUrl=getPurposes(i);
			List<Header> headers=tokenManager.getBaseHeaders();
			headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
			purposes.addAll(getExtractions(transactionUrl,headers,  "content"));
			System.out.println(purposes.size());
			oldResponseToken=responseToken;
		}//for
		
		return purposes;
	}//getPurposes
	
	protected List<Correctable> getCorruptPurposes(ResponseToken oldResponseToken) {
		List<Correctable> purposes=new ArrayList<Correctable>();
		List<Purpose> purposesFromService=null;
		List<Purpose> purposesToAdd=null;
		for(int i=0;i<purposeListingPages;i++) {
			ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
			String transactionUrl=getPurposes(i);
			List<Header> headers=tokenManager.getBaseHeaders();
			headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
			purposesFromService=getExtractions(transactionUrl,headers, "content");
			purposesToAdd=new ArrayList<Purpose>();
			 for(Purpose purposeFromService:purposesFromService) {
				 //add lancome here
				 if ((StringUtils.contains(purposeFromService.getLabel(), "é"))||(StringUtils.contains(purposeFromService.getLabel(), "ô")))
					 purposesToAdd.add(purposeFromService);
			 }
			purposes.addAll(purposesToAdd);
			System.out.println(purposes.size());
			oldResponseToken=responseToken;
		}//for
		
		return purposes;
	}//getCorruptPurposes	

}
