package com.sde.pepe.gdpr.application.rs.generator.transform.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.generator.vo.Input;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class InputOrganizationTransformer implements Transformer<Input, Organization>  {

	@Override
	public Organization getEntity(Input source) {
		Organization organization=new Organization();
		organization.setParentId(source.getCountryId());
		organization.setLanguageId(source.getLanguageId());
		organization.setPrivacyOfficerId(source.getPrivacyOfficerId());
		String domainName=source.getDomainName();
		String orgName="";
		if(StringUtils.isNotEmpty(domainName)) {
			orgName=source.getDivision()+" | "+source.getBrand()+" | "+source.getDomainName();
		}else
			orgName=source.getDivision()+" | "+source.getBrand();
		organization.setName(orgName);

		return organization;
	}//getEntity

	@Override
	public Organization getEntity(Input source, CustomReadHeader readHeader) {
		Organization organization=new Organization();
		if(readHeader==CustomReadHeader.DEFAULT)
			organization=getEntity(source);
			
		return organization;
	}//getEntity

	@Override
	public List<Organization> getEntities(Input source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}

}//InputOrganizationTransformer
