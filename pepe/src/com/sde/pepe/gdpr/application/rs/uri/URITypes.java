package com.sde.pepe.gdpr.application.rs.uri;

public enum URITypes {
	
	//Create
	CREATECOLLECTIONPOINT("/api/v1/private/CollectionPoint/CreateCollectionPoint"),
	CREATEORGANIZATION("/api/v1/private/orggroup/post"),
	CREATEWEBSITE("/api/v1/private/audit/"),
	CREATEPURPOSE("/api/v1/private/ConsentPurpose/CreatePurpose"),
	CREATEUSER(""),
	
	//Update
	UPDATECOLLECTIONPOINT("/api/v1/private/CollectionPoint/UpdateCollectionPoint/"),
	UPDATEORGANIZATION("/api/v1/private/orggroup/put"),
	UPDATEPURPOSE("/api/v1/private/ConsentPurpose/UpdatePurpose/"),
	
	//Publish
	PUBLISHPURPOSE("/api/v1/private/ConsentPurpose/PublishPurpose/"),
	
	//Search
	SEARCHCOLLECTIONPOINT("/api/v1/private/CollectionPoint/GetCollectionPointDetails"),
	SEARCHCOLLECTIONPOINTS("/api/v1/private/CollectionPoint/GetCollectionPoints"),
	SEARCHORGANIZATIONS("/api/v1/private/orggroup/get"),
	SEARCHWEBSITE(""),
	SEARCHPURPOSE("/api/v1/private/ConsentPurpose/GetPurposeDetails"),
	SEARCHPURPOSES("/api/v1/private/ConsentPurpose/GetPurposes"),
	SEARCHUSER(""),
	
	//Get all domain
	GETDOMAINS("/api/v1/private/consentNotice/domains"),
	
	//TOKEN
	GETCOLLECTIONPOINTAPITOKEN("/api/v1/private/CollectionPoint/GetCollectionPointToken"),
	
	//DELETE
	DELETECOLLECTIONPOINT("/api/v1/private/CollectionPoint/"),
	DELETEPURPOSE("/api/v1/private/ConsentPurpose/");
	
	private String uri;
	
	private URITypes(String uri) {
		this.uri=uri;
	}
	
	public String getUri() {
		return uri;
	}

}
