package com.sde.pepe.gdpr.application.rs.collectionpoint.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.CRUDTypes.UPDATE;
import static com.sde.pepe.gdpr.application.rs.response.ResponseUtil.checkResponse;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.uri.URITypes;


public class Update extends BaseService<CollectionPoint>{
	
	public Update() {
		super(CollectionPoint.class);
	}
	
	private String getUpdateCollectionPointUrl(String collectionPointId, int collectionPointVersion) {
		return domain+URITypes.UPDATECOLLECTIONPOINT.getUri()+collectionPointId+"?version="+collectionPointVersion;
	}
	
	private String getDeleteCollectionPointUrl(String collectionPointId) {
		return domain+URITypes.DELETECOLLECTIONPOINT.getUri()+collectionPointId;
	}
	
	
	private String getUpdateCollectionMessage(CollectionPoint collectionPoint) {
		return collectionPoint.getUpdateString();
		
	}
	
	public ResponseToken updateCollectionPoint(ResponseToken oldResponseToken,CollectionPoint collectionPoint) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getCreateHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		String transactionUrl=getUpdateCollectionPointUrl(collectionPoint.getId(),collectionPoint.getVersion());
				
		checkResponse((Resting.put(transactionUrl, 443, getUpdateCollectionMessage(collectionPoint), headers, EncodingTypes.UTF16)),collectionPoint,UPDATE);
		return responseToken;
		
	}
	
	//Does not work yet
	public void deleteCollectionPoint(ResponseToken oldResponseToken,CollectionPoint collectionPoint) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getCreateHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		String transactionUrl=getDeleteCollectionPointUrl(collectionPoint.getId());
				
		System.out.println(Resting.delete(transactionUrl, 443));
	}
	

}
