package com.sde.pepe.gdpr.application.rs.organization.transform.impl;

import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.COUNTRY_CREATE;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.organization.vo.Country;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class CountryTransformer implements Transformer<CSVRecord, Country> {

	@Override
	public Country getEntity(CSVRecord source) {
		Country country=new Country();		
		country.setName(source.get("COUNTRY"));
		country.setId(source.get("COUNTRY ID"));
		country.setCc(source.get("COUNTRY CODE"));
		return country;
	}
	

	@Override
	public Country getEntity(CSVRecord source, CustomReadHeader readHeader) {
		
		Country country=new Country();
		
		if(readHeader==COUNTRY_CREATE)
			country=getEntity(source);
		
		
		return country;
	}

	@Override
	public List<Country> getEntities(CSVRecord source, CustomReadHeader readHeader) {
		return null;
	}

}
