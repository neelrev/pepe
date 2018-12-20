package com.sde.pepe.gdpr.application.rs.collectionpoint.vo;

import java.util.Map;

import com.sde.pepe.gdpr.framework.Validated;
import com.sde.pepe.gdpr.framework.Writeable;


public class Referential implements Writeable<String>,Validated<String> {
	
	private String id;
	private String name;
	private Map<String,String> validatorMap;
	
	private String collectionPointId;
	private String collectionPointName;
	private String collectionPointStatus;
	private String collectionPointApiToken;
	
	private String purposeId;
	private String purposeLabel;
	
	private String organizationId;
	private String organizationName;
	
	private String countryName;
	
	private String fileHeader;
	private String writeableString;
	
	public Referential() {
		
	}
	
	
	public Referential(String collectionPointId,String collectionPointName,String purposeId, String purposeLabel){
		this.collectionPointName=collectionPointName;
		this.collectionPointId=collectionPointId;
		this.purposeId=purposeId;
		this.purposeLabel=purposeLabel;
		this.id=collectionPointId+"_"+purposeId;
		this.name=collectionPointName+"_"+purposeLabel;
	}
	
	public Referential(String collectionPointId,String collectionPointName,String collectionPointStatus, String purposeLabel,String purposeId){
		this.collectionPointName=collectionPointName;
		this.collectionPointId=collectionPointId;
		this.purposeId=purposeId;
		this.purposeLabel=purposeLabel;
		this.collectionPointStatus=collectionPointStatus;
		this.id=collectionPointId+"_"+purposeId;
		this.name=collectionPointName+"_"+purposeLabel;
	}
	
	public Referential(String collectionPointId,String collectionPointName,String purposeId, String purposeLabel,String organizationId, String organizationName, String countryName){
		this.collectionPointName=collectionPointName;
		this.collectionPointId=collectionPointId;
		this.purposeId=purposeId;
		this.purposeLabel=purposeLabel;
		this.organizationId=organizationId;
		this.organizationName=organizationName;
		this.countryName=countryName;
		this.id=collectionPointId+"_"+purposeId;
		this.name=collectionPointName+"_"+purposeLabel;
	}
	
	public Referential(String collectionPointId,String collectionPointName,String purposeId, String purposeLabel,String organizationId, String organizationName, String countryName, String apiToken){
		this.collectionPointName=collectionPointName;
		this.collectionPointId=collectionPointId;
		this.purposeId=purposeId;
		this.purposeLabel=purposeLabel;
		this.organizationId=organizationId;
		this.organizationName=organizationName;
		this.countryName=countryName;
		this.collectionPointApiToken=apiToken;
		this.id=collectionPointId+"_"+purposeId;
		this.name=collectionPointName+"_"+purposeLabel;	
	}

	@Override
	public String getDefaultFileHeader() {
		//return "CollectionPoint Name,Purpose Name,CollectionPoint Id,Purpose Id\n";
		return "Purpose Name,Purpose Id,CollectionPoint Name,CollectionPoint Id,Organization Name, Organization Id, Country\n";
	}

	@Override
	public String getDefaultWritableString() {
		//return collectionPointName+","+purposeLabel+","+collectionPointId+","+purposeId+"\n";
		return purposeLabel+","+purposeId+","+collectionPointName+","+collectionPointId+","+organizationName+","+organizationId+","+countryName+"\n";
	}

	public String getCollectionPointId() {
		return collectionPointId;
	}

	public void setCollectionPointId(String collectionPointId) {
		this.collectionPointId = collectionPointId;
	}

	public String getCollectionPointName() {
		return collectionPointName;
	}

	public void setCollectionPointName(String collectionPointName) {
		this.collectionPointName = collectionPointName;
	}

	public String getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(String purposeId) {
		this.purposeId = purposeId;
	}

	public String getPurposeLabel() {
		return purposeLabel;
	}

	public void setPurposeLabel(String purposeLabel) {
		this.purposeLabel = purposeLabel;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Map<String, String> getValidatorMap() {
		return validatorMap;
	}

	@Override
	public void setValidatorMap(Map<String, String> validatorMap) {
		this.validatorMap=validatorMap;
	}

	@Override
	public String getFileHeader() {
		return fileHeader;
	}

	@Override
	public String getWritableString() {
		return writeableString;
	}

	@Override
	public Writeable setFileHeader(String fileHeader) {
		this.fileHeader=fileHeader;
		return this;
	}

	@Override
	public Writeable setWritableString(String writeableString) {
		this.writeableString=writeableString;
		return this;
	}

	public String getCollectionPointStatus() {
		return collectionPointStatus;
	}

	public void setCollectionPointStatus(String collectionPointStatus) {
		this.collectionPointStatus = collectionPointStatus;
	}
	
	@Override
	public String toString() {
		return "Collection Point Name: "+collectionPointName+
				"| Collection Point ID: "+collectionPointId+
				"| Purpose Name: "+purposeLabel+
				"| Purpose ID: "+purposeId+ 
				"| Collection Point Status: "+collectionPointStatus;
				
				
	}


	public String getCollectionPointApiToken() {
		return collectionPointApiToken;
	}


	public void setCollectionPointApiToken(String collectionPointApiToken) {
		this.collectionPointApiToken = collectionPointApiToken;
	}


	public String getOrganizationId() {
		return organizationId;
	}


	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}


	public String getOrganizationName() {
		return organizationName;
	}


	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
