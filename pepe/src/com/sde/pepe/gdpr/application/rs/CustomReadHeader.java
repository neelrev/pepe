package com.sde.pepe.gdpr.application.rs;

public enum CustomReadHeader {
	
	//Default read, no header used
	DEFAULT(new String[] {}),

	//Read input to create entities
	COUNTRY_CREATE(new String[] {"COUNTRY","COUNTRY CODE","COUNTRY ID"}),
	
	
	//Read input to create entities
	ROOT_INPUT_CREATE(new String[] {"COUNTRY","DIVISION","BRAND","DOMAIN NAME","DOMAIN URL","PRIVACY POLICY URL"}),
	
	
	//Read input to create entities
	INPUT_CREATE(new String[] {"COUNTRY CODE","COUNTRY ID","DIVISION","BRAND","DOMAIN NAME","DOMAIN URL","PRIVACY OFFICER ID","LANGUAGE ID","ORGANIZATION ID","PRIVACY POLICY URL"}),
	
	//Create CP from excel sheet
	COLLECTIONPOINT_CREATE(new String[]{"CollectionPointType", "DomainUrl","OrganizationId", 
			"Name","Description","WebFormURL", "PrivacyPolicyURL",
			"SubjectIdentifier","ConsentInteractionType","Purposes"}),
	
	COLLECTIONPOINT_CREATE_VARIATION(new String[]{"CollectionPointType", "DomainUrl","OrganizationName", 
			"Name","Description","WebFormURL", "PrivacyPolicyURL",
			"SubjectIdentifier","ConsentInteractionType","Purposes"}),
	
	COLLECTIONPOINT_CREATE_VARIATION_2(new String[]{"Id","CollectionPointType", "OrganizationName", 
			"Name","Description","WebFormURL", "PrivacyPolicyURL",
			"SubjectIdentifier","ConsentInteractionType","Purposes"}),
	
	COLLECTIONPOINT_REST(new String[]{"Id","Name","CollectionPointType", "ConsentType", 
		"Status","SubjectIdentifier","CreateDate", "FirstReceiptOn",
		"ReceiptCount","Description","WebFormURL","PrivacyPolicyURL",
		"DataControllerName","RightToWithdraw","HowToWithdraw",
		"OtherInformation","ActivationDate","OrganizationId","DoubleOptIn"}),
	
	COLLECTIONPOINT_PURPOSE_CORRECTION(new String[] {"Name","Organization Name","ID","Organization Id"}),
	
	COLLECTIONPOINT_CHECK_DELETE(new String[] {"Collection Point ID","Collection Point Name"}),
	
	COLLECTIONPOINT_CHECK_RENAME(new String[] {"Collection Point ID","New Collection Point Name"}),
	
	COLLECTIONPOINT_CHECK_MOVE(new String[] {"Collection Point ID","Collection Point Name","New Organization ID","New Organization Name"}),
	
	REFERENTIAL_SUPPORT_PURPOSELINKING(new String[] {"Collection Point ID","Collection Point Name","Status", "Missing Purpose","Missing Purpose ID"}),
	
	REFERENTIAL_SUPPORT_PURPOSELINKING_INPUT(new String[] {"CollectionPoint Name","CollectionPoint ID","Missing Purposes","Organization Name","Organization ID","Country"}),
	
	REFERENTIAL_SUPPORT_TANGLEDPURPOSELINKING(new String[] {"Collection Point Name","Collection Point ID","Status", "Missing Purposes","Missing Purpose IDs"}),

	REFERENTIAL_SUPPORT_CHECK(new String[] {"Collection Point Name","Collection Point ID","Status","Purpose Name","Purpose ID"}),
	
	COLLECTIONPOINT_PURPOSE_MAP(new String[] {"Purpose Name","Purpose Id","CollectionPoint Name","CollectionPoint Id", "Organization Name","Organization Id","Country"}),
	
	PURPOSE_CREATE(new String[] {"Purpose Name","Purpose Description"}),
	
	PURPOSE_UPDATE(new String[] {"Purpose Name","Purpose Id","Purpose Description"}),
	
	PURPOSE_REST(new String[] {"Id","Label","Description","Status","LifeSpan","ActiveDataSubjects","PendingDataSubjects","WithdrawnDataSubjects","CollectionPoints"}),
	
	PURPOSE_CHECK_RENAME(new String[] {"Purpose ID","New Purpose Name","New Purpose Description"}),
	
	ORGANIZATION_CREATE(new String[] {"COUNTRY ID","ORGANIZATION NAME","LANGUAGE ID","PRIVACY OFFICER ID"}),
	
	ORGANIZATION_UPDATE(new String[] {"Name","Id","PrivacyOfficerId","LanguageId"}),
	
	ORGANIZATION_REST(new String[] {"Id","Name","ParentId","LanguageId","PrivacyOfficerId","PrivacyOfficerName","CanDelete"});
	
	;
	

	
	private String[] headerMapping;
	
	private CustomReadHeader(String[] headerMapping) {
		this.headerMapping=headerMapping;
	}
	
	public String[] getHeaderMapping() {
		return headerMapping;
	}

}
