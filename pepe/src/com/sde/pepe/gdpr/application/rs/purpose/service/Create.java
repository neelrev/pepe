package com.sde.pepe.gdpr.application.rs.purpose.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.uri.URITypes.CREATEPURPOSE;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;;

public class Create extends BaseService<Purpose>{
	
	public Create() {
		super(Purpose.class);
	}
	
	private String getCreatePurposeUrl() {
		return domain+CREATEPURPOSE.getUri();
	}

	
	public ResponseToken createPurpose(ResponseToken oldResponseToken,Purpose purpose) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getCreateHeaders();
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		String transactionUrl=getCreatePurposeUrl();
		create(purpose,transactionUrl,headers);
		return responseToken;		
	}
	

}
