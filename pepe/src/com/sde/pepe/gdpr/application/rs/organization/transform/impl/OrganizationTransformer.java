package com.sde.pepe.gdpr.application.rs.organization.transform.impl;

import org.apache.commons.csv.CSVRecord;

import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.*;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class OrganizationTransformer implements Transformer<CSVRecord, Organization> {

	@Override
	public Organization getEntity(CSVRecord source) {
		Organization organization=new Organization();		
		organization.setName(source.get("ORGANIZATION NAME"));
		organization.setParentId(source.get("COUNTRY ID"));
		organization.setPrivacyOfficerId(source.get("PRIVACY OFFICER ID"));
		organization.setLanguageId(source.get("LANGUAGE ID"));
		return organization;
	}

	@Override
	public Organization getEntity(CSVRecord source, CustomReadHeader readHeader) {
		
		Organization organization=new Organization();
		
		if(readHeader==ORGANIZATION_CREATE)
			organization=getEntity(source);
		
		if(readHeader==ORGANIZATION_REST) {
			organization.setId(source.get("Id"));
			organization.setName(source.get("Name"));
			organization.setParentId(source.get("ParentId"));
			organization.setPrivacyOfficerId(source.get("PrivacyOfficerId"));
			organization.setPrivacyOfficerName(source.get("PrivacyOfficerName"));
			organization.setLanguageId(source.get("LanguageId"));
			organization.setCanDelete(Boolean.getBoolean(source.get("CanDelete")));
		}
		
		if(readHeader==ORGANIZATION_UPDATE) {
			organization.setId(source.get("Id"));
			organization.setName(source.get("Name"));
			organization.setPrivacyOfficerId(source.get("PrivacyOfficerId"));
			organization.setLanguageId(source.get("LanguageId"));
		}
		
		return organization;
	}

	@Override
	public List<Organization> getEntities(CSVRecord source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}

}
