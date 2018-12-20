package com.sde.pepe.gdpr.application.rs.collectionpoint.components;

public enum CPTypes {
	
	CRM("CRM"),
	ECOMMERCE("Ecommerce Websites"),
	CONTENT("Content Websites"),
	RETAIL("Retail");
	
	private String type;
	
	private CPTypes(String type) {
		this.type=type;
	}
	
	public String getType() {
		return type;
	}

}//CPTypes
