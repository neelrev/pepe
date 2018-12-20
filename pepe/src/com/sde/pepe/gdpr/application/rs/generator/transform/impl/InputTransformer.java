package com.sde.pepe.gdpr.application.rs.generator.transform.impl;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.*;

import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.generator.vo.Input;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class InputTransformer implements Transformer<CSVRecord, Input> {

	@Override
	public Input getEntity(CSVRecord source) {
		Input input=new Input();
		input.setCountryCode(source.get("COUNTRY CODE"));
		input.setCountryId(source.get("COUNTRY ID"));
		input.setDivision(source.get("DIVISION"));
		input.setBrand(source.get("BRAND"));
		input.setDomainName(source.get("DOMAIN NAME"));
		input.setDomainURL(source.get("DOMAIN URL"));
		input.setLanguageId(source.get("LANGUAGE ID"));
		input.setPrivacyOfficerId(source.get("PRIVACY OFFICER ID"));
		input.setOrganizationId(source.get("ORGANIZATION ID"));
		input.setPrivacyPolicyURL(source.get("PRIVACY POLICY URL"));
		return input;
	}//getEntity

	@Override
	public Input getEntity(CSVRecord source, CustomReadHeader readHeader) {
		Input input=new Input();
		if(readHeader==CustomReadHeader.INPUT_CREATE) {
			input=getEntity(source);
			return input;
		}
		if(readHeader==CustomReadHeader.ROOT_INPUT_CREATE) {
			input.setCountry(source.get("COUNTRY"));			
			input.setDivision(source.get("DIVISION"));
			input.setBrand(source.get("BRAND"));
			input.setDomainName(source.get("DOMAIN NAME"));
			//StringUtils.re
			String domainURL=StringUtils.removeIgnoreCase(source.get("DOMAIN URL"),"https://");
			domainURL=StringUtils.removeIgnoreCase(domainURL,"https://");
			domainURL=StringUtils.removeIgnoreCase(domainURL, "www.");
			input.setPrivacyPolicyURL(source.get("PRIVACY POLICY URL"));
			input.setDomainURL(domainURL);	
			input.setLanguageId(languageId);
			input.setPrivacyOfficerId(user);
		}
		/*if(readHeader==CustomReadHeader.COLLECTIONPOINT_CORRECTION) {
			input.setCollectionPointName(source.get("Name"));
			input.setOrganizationName(source.get("Organization Name"));
			input.setCollectionPointId(source.get("ID"));
			input.setOrganizationId(source.get("Organization Id"));	
		}*/
		return input;
	}//getEntity

	@Override
	public List<Input> getEntities(CSVRecord source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}

}//InputTransformer
