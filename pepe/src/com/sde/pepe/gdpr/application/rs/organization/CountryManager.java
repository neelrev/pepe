package com.sde.pepe.gdpr.application.rs.organization;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.cc;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.COUNTRY_CREATE;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.BaseProperties;
import com.sde.pepe.gdpr.application.rs.organization.read.ReadCountry;
import com.sde.pepe.gdpr.application.rs.organization.vo.Country;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class CountryManager {
	
	public CountryManager() {
		BaseProperties baseProperties=new BaseProperties();		
	}//CountryManager
	
	public List<Country> getAll() {
		ReadCountry<Country> read=new ReadCountry<Country>();
		List<Country> countries=read.read(new FileEntity(cc),COUNTRY_CREATE);
		return countries;
		
	}//getAll
}//CountryManager
