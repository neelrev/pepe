package com.sde.pepe.gdpr.application.rs.website.service;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.resting.Resting;
import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.organization.OrganizationManager;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.application.rs.token.TokenManager;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.uri.URITypes;
import com.sde.pepe.gdpr.application.rs.website.vo.Website;

import static com.google.resting.component.EncodingTypes.*;
import static com.sde.pepe.gdpr.application.rs.BaseProperties.*;
import static com.sde.pepe.gdpr.application.rs.CRUDTypes.*;
import static com.sde.pepe.gdpr.application.rs.response.ResponseUtil.checkResponse;

public class Create extends BaseService<Website>{

	private TokenManager tokenManager;
	private List<Organization> organizations=null;
	
	public Create() {
		super(Website.class);
		this.tokenManager=new TokenManager(domain);
		
		OrganizationManager organizationManager=new OrganizationManager();
		//organizations=organizationManager.extractOrganizationsToCreate(organizationFilePath);
	}
	
	
	private String getCreateWebsiteUri(String orgGroupId) {
		return domain+URITypes.CREATEWEBSITE.getUri()+"request?orgGroupId="+orgGroupId;
	}
	
	
	
	public ResponseToken createWebsite(ResponseToken oldResponseToken,Website website,String orgGroupId) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		System.out.println(responseToken);
		List<Header> headers=tokenManager.getCreateHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		String transactionUrl=getCreateWebsiteUri(orgGroupId);
		checkResponse(Resting.post(transactionUrl, 443, null, website.getCreateString(), UTF8, headers, null),website,CREATE);
		return responseToken;
		
	}
}
