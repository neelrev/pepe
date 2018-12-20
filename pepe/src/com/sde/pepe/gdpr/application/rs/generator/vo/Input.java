package com.sde.pepe.gdpr.application.rs.generator.vo;

import com.sde.pepe.gdpr.framework.Writeable;

public class Input  implements Writeable<String> {
	
	//For Support
	private String collectionPointName="";
	private String organizationName="";
	private String collectionPointId="";
	private String privacyPolicyURL="";
	
	//For entity creation
	private String country;
	private String countryCode="";
	private String countryId;
	private String division;
	private String brand;
	private String domainName="";
	private String domainURL;
	
	//Common
	private String privacyOfficerId="";
	private String languageId="";
	private String organizationId="";
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getDomainURL() {
		return domainURL;
	}
	public void setDomainURL(String domainURL) {
		this.domainURL = domainURL;
	}
	public String getPrivacyOfficerId() {
		return privacyOfficerId;
	}
	public void setPrivacyOfficerId(String privacyOfficerId) {
		this.privacyOfficerId = privacyOfficerId;
	}
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String toString() {
		return  countryCode+"|"
				+countryId+"|"
				+division+"|"
				+brand+"|"
				+domainName+"|"
				+domainURL+"|"
				+languageId+"|"
				+privacyOfficerId+"|"
				+privacyPolicyURL;			
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String getDefaultFileHeader() {
		return "COUNTRY CODE,COUNTRY ID,DIVISION,BRAND,DOMAIN NAME,DOMAIN URL,PRIVACY OFFICER ID,LANGUAGE ID,ORGANIZATION ID,PRIVACY POLICY URL\n";
	}
	@Override
	public String getDefaultWritableString() {
		return countryCode+","
				+countryId+","
				+division+","
				+brand+","
				+domainName+","
				+domainURL+","
				+privacyOfficerId+","
				+languageId+","
				+organizationId+","
				+privacyPolicyURL+"\n";
	}
	@Override
	public String getFileHeader() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getWritableString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Writeable setFileHeader(String fileHeader) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Writeable setWritableString(String writeableString) {
		// TODO Auto-generated method stub
		return null;
	}
	public String getCollectionPointName() {
		return collectionPointName;
	}
	public void setCollectionPointName(String collectionPointName) {
		this.collectionPointName = collectionPointName;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getCollectionPointId() {
		return collectionPointId;
	}
	public void setCollectionPointId(String collectionPointId) {
		this.collectionPointId = collectionPointId;
	}
	public String getPrivacyPolicyURL() {
		return privacyPolicyURL;
	}
	public void setPrivacyPolicyURL(String privacyPolicyURL) {
		this.privacyPolicyURL = privacyPolicyURL;
	}

}
