package com.sde.pepe.gdpr.application.rs.collectionpoint.service.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sde.pepe.gdpr.application.rs.collectionpoint.CollectionPointExportManager;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.Referential;
import com.sde.pepe.gdpr.application.rs.organization.OrganizationManager;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.framework.util.BaseCheck;

public class Check extends BaseCheck<CollectionPoint>{
	

	private static String[] touchpoints= {"CRM","Content Websites","Retail","Ecommerce Websites","Mobile App"};
	
	private List<Referential> referentials;
	private List<Organization> organizations;

	public Check(List<CollectionPoint> sources) {
		super(sources);

		
		OrganizationManager organizationManager=new OrganizationManager();
		organizations=organizationManager.extractOrganizations();
		System.out.println("Number of organizations: "+organizations.size());

		CollectionPointExportManager collectionPointExportManager=new CollectionPointExportManager();
		referentials=collectionPointExportManager.extractCollectionPointPurposes();		
	}
	
	protected boolean checkOrgnizationHasFourCollectionPoints(CollectionPoint source) {
		String name=source.getName();
		String orgId=source.getOrganizationId();
		
		//There should be 4 collection points for every organization, except for Mobile App
		if(!StringUtils.startsWith(name, "Mobile")) {
			if(getCollectionPointCount(orgId)!=4)
				return false;
		}
		return true;
	}//checkOrgnizationHasFourCollectionPoints
	
	protected boolean checkCollectionPointContainsOrgName(CollectionPoint source) {
		String name=source.getName();
		String orgId=source.getOrganizationId();
		//Collection point should contain the name of the organization
		Organization org=getOrganization(orgId);
		 if(org==null) return false;
		 
		String orgName=org.getName();
		System.out.println("Print org name: "+orgName);
		source.setOrganizationName(orgName);
		 if(!StringUtils.contains(name, orgName))
			 return false;
		
		 return true;
	}//checkCollectionPointContainsOrgName
	
	//Check for each cp containing 18 purposes
	protected boolean checkPurposes(CollectionPoint source) {
		int i=getPurposeCount(source);
		source.setNumberOfPurposes(i);
		if(i==18)
			return true;
		else
			return false;	
	}//checkPurposes
	
	protected int getPurposeCount(CollectionPoint source) {
		String id=source.getId();
		int i=0;
		 for(Referential referential:referentials) {
			 if(id.equals(referential.getCollectionPointId()))
					 i++;
		 }
		 return i;	
	}

	protected boolean checkName(CollectionPoint source) {
		
		//Check All the collection have the name of the touchpoint : 
		//Content Websites, CRM, Retail, Ecommerce Websites, Mobile App
		
		 if(startsWith(source.getName(),touchpoints)>-1) 
			 return true;
		 else 
			 return false;
	}//checkNameStartsWithTouchPoints
	
	protected boolean checkPolicy(CollectionPoint source) {
		String status=source.getStatus();
		String privacyPolicyUrl=source.getPrivacyPolicyUrl();
		
		
		//Active collection points should have valid privacy policy
		 if(status.equalsIgnoreCase("ACTIVE")) {
			 if(!checkNullOrEmpty(privacyPolicyUrl))
				 return false;
		 }

		 return true;
			 		
	}//checkpolicy
	
	protected boolean checkIsRemoveableDuplicate(CollectionPoint source) {
		String status=source.getStatus();
		String firstReceiptOn=source.getFirstReceiptOn();
		String receiptCount=source.getReceiptCount();
		boolean isDuplicate=checkName(source);
		if((!isDuplicate)&&(checkNullOrEmpty(firstReceiptOn))&&(checkNullOrEmpty(receiptCount))) {
			return false;
		}
		return true;
	}
	
	protected boolean checkTransactionStatus(CollectionPoint source) {
		String status=source.getStatus();
		String firstReceiptOn=source.getFirstReceiptOn();
		String receiptCount=source.getReceiptCount();
		

		 //Collections with transactions should be active
		 if((checkNullOrEmpty(firstReceiptOn))&&(checkNullOrEmpty(receiptCount))) {
			 if(!status.equalsIgnoreCase("ACTIVE"))
				 return false;
		 }
			 
		 return true;
			 		
	}//checkTransactionStatus	
	
	private Organization getOrganization(String orgId) {
		System.out.println(orgId);
		if(orgId == null)return null;
		for(Organization org:organizations) {
			if(orgId.equals(org.getId()))
				return org;
		}
		return null;
	}
	
	private int getCollectionPointCount(String orgId){
		int count=0;
		for(CollectionPoint source:sources) {
			if(source.getOrganizationId().equals(orgId))
				count++;
		}
		return count;
	}
	
}
