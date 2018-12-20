package com.sde.pepe.gdpr.application.rs.generator.transform.impl;

import static com.sde.pepe.gdpr.application.rs.collectionpoint.components.CPTypes.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.collectionpoint.components.CPTypes;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPointType;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.ConsentType;
import com.sde.pepe.gdpr.application.rs.generator.vo.Input;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class InputCollectionPointTransformer implements Transformer<Input, List<CollectionPoint>>  {
	
	public static String[] touchpoints= {"Alerts / Notifications","Beauty Profile","Email Marketing",""
			+ "Group Marketing","Lottery","Phone Marketing","Postal Marketing",""
					+ "Profiling Marketing","Rating & reviews","Sampling pages",""
							+ "SMS Marketing","Users Right Management","Term of use",""
									+ "Terms & conditions","Child","Contest Games","User generated consent","Contact us"};


	@Override
	public List<CollectionPoint> getEntity(Input source) {
		List<CollectionPoint> collectionPoints=new ArrayList<CollectionPoint>();
		
		 for(CPTypes cptype:CPTypes.values()) {
			 CollectionPoint collectionPoint=new CollectionPoint();
			 String name="";
			 String domainName=source.getDomainName();
			 String domainURL=source.getDomainURL();
			 if(StringUtils.isNotEmpty(domainName)) {
				 if((cptype==CPTypes.ECOMMERCE)||(cptype==CONTENT)) 
					 name=cptype.getType()+" | "+source.getCountryCode()+" | "+source.getDivision()+" | "+source.getBrand()+" | "+domainName+" ("+source.getDomainURL()+")"; 
				 else
					 name=cptype.getType()+" | "+source.getCountryCode()+" | "+source.getDivision()+" | "+source.getBrand()+" | "+domainName; 
			 }
			 else {
				 if((cptype==CPTypes.ECOMMERCE)||(cptype==CONTENT)) 
					 name=cptype.getType()+" | "+source.getCountryCode()+" | "+source.getDivision()+" | "+source.getBrand()+" ("+source.getDomainURL()+")"; 
				 else
					 name=cptype.getType()+" | "+source.getCountryCode()+" | "+source.getDivision()+" | "+source.getBrand(); 

			 }//if
			 if(StringUtils.isNotEmpty(domainURL))
				 collectionPoint.setDomainUrl(source.getDomainURL());
			 collectionPoint.setOrganizationId(source.getOrganizationId());
			 collectionPoint.setName(name);
			 collectionPoint.setDescription(name);
			 collectionPoint.setConsentType(ConsentType.CONDITIONALTRIGGER);
			 collectionPoint.setCollectionPointType(CollectionPointType.WEB_FORM);
			 String orgName="";
				if(StringUtils.isNotEmpty(domainName)) {
					orgName=source.getCountryCode()+" | "+source.getDivision()+" | "+source.getBrand()+" | "+source.getDomainName();
				}else
					orgName=source.getCountryCode()+" | "+source.getDivision()+" | "+source.getBrand();
			 collectionPoint.setOrganizationName(orgName);
			 collectionPoint.setSubjectIdentifier("Email");
			 collectionPoint.setPurposeNames(getPurposes(source));
			 collectionPoint.setPrivacyPolicyUrl(source.getPrivacyPolicyURL());
			 
			 collectionPoints.add(collectionPoint);
		 }//for
		return collectionPoints;
	}//getEntity

	@Override
	public List<CollectionPoint> getEntity(Input source, CustomReadHeader readHeader) {
		List<CollectionPoint> collectionPoints=new ArrayList<CollectionPoint>();
		if(readHeader==CustomReadHeader.DEFAULT)
			collectionPoints=getEntity(source);
			
		return collectionPoints;
	}
	
	private String getPurposes(Input source) {	
		String domainName=source.getDomainName();
		String suffix=source.getCountryCode()+" | "+source.getDivision()+" | "+source.getBrand();
		
		String purposes="";
		 for(String touchpoint:touchpoints) {
			 if(purposes.equals(""))
				 purposes=touchpoint+" | "+suffix;
			 else
				 purposes=purposes+";"+touchpoint+" | "+suffix;
		 }
		
		return purposes;		
	}//getPurposes

	@Override
	public List<List<CollectionPoint>> getEntities(Input source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}

}//InputCollectionPointTransformer
