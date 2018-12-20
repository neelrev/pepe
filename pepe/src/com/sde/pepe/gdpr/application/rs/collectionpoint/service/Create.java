package com.sde.pepe.gdpr.application.rs.collectionpoint.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.CRUDTypes.CREATE;
import static com.sde.pepe.gdpr.application.rs.response.ResponseUtil.checkResponse;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.resting.Resting;
import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.uri.URITypes;


public class Create extends BaseService<CollectionPoint>{
	
	public Create() {
		super(CollectionPoint.class);
	}
	
	private String getCreateCollectionPointUrl() {
		return domain+URITypes.CREATECOLLECTIONPOINT.getUri();
	}
	
	
	private String getCreateCollectionMessage(CollectionPoint collectionPoint) {
		return collectionPoint.getCreateString();
		
	}
	
	public ResponseToken createCollectionPoint(ResponseToken oldResponseToken,CollectionPoint collectionPoint) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getCreateHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		String transactionUrl=getCreateCollectionPointUrl();
		checkResponse((Resting.post(transactionUrl, 443, null, getCreateCollectionMessage(collectionPoint), null, headers, null)),collectionPoint,CREATE);
		return responseToken;
		
	}
	

}
