package com.sde.pepe.gdpr.application.rs.organization.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.CRUDTypes.*;
import static com.sde.pepe.gdpr.application.rs.response.ResponseUtil.checkResponse;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.resting.Resting;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.application.rs.token.TokenManager;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.uri.URITypes;;

public class Create {
	
	private TokenManager tokenManager;
	public Create() {
		this.tokenManager=new TokenManager(domain);
	}
	
	private String getCreateOrganizationUrl() {
		return domain+URITypes.CREATEORGANIZATION.getUri();
	}
	public ResponseToken createOrganization(ResponseToken oldResponseToken,Organization organization) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getCreateHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		String transactionUrl=getCreateOrganizationUrl();
		System.out.println(organization.getCreateString());
		
		checkResponse((Resting.post(transactionUrl, 443, null, organization.getCreateString(), null, headers, null)),organization,CREATE);
		
		return responseToken;
		
	}
}
