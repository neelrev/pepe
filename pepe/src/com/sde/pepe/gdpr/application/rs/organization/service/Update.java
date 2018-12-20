package com.sde.pepe.gdpr.application.rs.organization.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.CRUDTypes.UPDATE;
import static com.sde.pepe.gdpr.application.rs.response.ResponseUtil.checkResponse;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.uri.URITypes;


public class Update extends BaseService<Organization>{
	
	public Update() {
		super(Organization.class);
	}
	
	private String getUpdateOrganizationUrl() {
		return domain+URITypes.UPDATEORGANIZATION.getUri();
	}
	
	
	private String getUpdateOrganizationMessage(Organization organization) {
		return organization.getUpdateString();
		
	}
	
	public ResponseToken updateOrganization(ResponseToken oldResponseToken,Organization organization) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getCreateHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		String transactionUrl=getUpdateOrganizationUrl();
				
		checkResponse((Resting.put(transactionUrl, 443, getUpdateOrganizationMessage(organization), headers, EncodingTypes.UTF16)),organization,UPDATE);
		return responseToken;
		
	}
	

}
