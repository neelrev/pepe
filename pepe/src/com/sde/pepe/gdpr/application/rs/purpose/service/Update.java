package com.sde.pepe.gdpr.application.rs.purpose.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.CRUDTypes.UPDATE;
import static com.sde.pepe.gdpr.application.rs.response.ResponseUtil.checkResponse;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.uri.URITypes;


public class Update extends BaseService<Purpose>{
	
	public Update() {
		super(Purpose.class);
	}
	
	private String getUpdatePurposeUrl(String purposeId) {
		return domain+URITypes.UPDATEPURPOSE.getUri()+purposeId;
	}

	private String getPublishPurposeUrl(String purposeId) {
		return domain+URITypes.PUBLISHPURPOSE.getUri()+purposeId;
	}	
	
	private String getUpdatePurposeMessage(Purpose purpose) {
		return purpose.getUpdateString();	
	}
	
	private String getPublishPurposeMessage(Purpose purpose) {
		return purpose.getPublishString();
		
	}	
	
	public ResponseToken updatePurpose(ResponseToken oldResponseToken,Purpose purpose) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getCreateHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		String transactionUrl=getUpdatePurposeUrl(purpose.getId());
				
		checkResponse((Resting.put(transactionUrl, 443, getUpdatePurposeMessage(purpose), headers, EncodingTypes.UTF8)),purpose,UPDATE);
		return responseToken;
		
	}
	public ResponseToken publishPurpose(ResponseToken oldResponseToken,Purpose purpose) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getCreateHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		String transactionUrl=getPublishPurposeUrl(purpose.getId());
				
		checkResponse((Resting.put(transactionUrl, 443, getPublishPurposeMessage(purpose), headers, EncodingTypes.UTF8)),purpose,UPDATE);
		return responseToken;
		
	}	

}
