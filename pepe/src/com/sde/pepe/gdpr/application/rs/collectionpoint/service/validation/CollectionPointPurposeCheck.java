package com.sde.pepe.gdpr.application.rs.collectionpoint.service.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.Referential;
import com.sde.pepe.gdpr.framework.util.BaseCheck;

public class CollectionPointPurposeCheck extends BaseCheck<Referential>{

	public CollectionPointPurposeCheck(List<Referential> sources) {
		super(sources);
	}
	
	@Override
	public boolean checkCCDivisionBrand(Referential source) {			
		
		String pName=sanitizeStr(source.getPurposeLabel());
		String[] pArray=StringUtils.split(pName, "|");
		String pCC="";
		String pDivision="";
		String pBrand="";
		
		
		for(String arrayElement:pArray) {
			if(contains(arrayElement,sanitizedCCs))
				pCC=arrayElement;
			if(contains(arrayElement,sanitizedDivisions))
				pDivision=arrayElement;
			if(contains(arrayElement,sanitizedBrands))
				pBrand=arrayElement;		
		}//for

		String cpName=sanitizeStr(source.getCollectionPointName());
		String[] cpArray=StringUtils.split(cpName, "|");
		String cpCC="";
		String cpDivision="";
		String cpBrand="";
		
		
		for(String arrayElement:cpArray) {
			if(contains(arrayElement,sanitizedCCs))
				cpCC=arrayElement;
			if(contains(arrayElement,sanitizedDivisions))
				cpDivision=arrayElement;
			if(contains(arrayElement,sanitizedBrands))
				cpBrand=arrayElement;		
		}//for
		
		if((pCC.equals(cpCC))
			&&
		   (pDivision.equals(cpDivision)))
	//	   &&
		//   (pBrand.equals(cpBrand)))
			
			return true;
		else
			return false;
	}//checkCCDivisionBrand

	private boolean contains(String str, List<String> array) {
		for(String arrayElement:array) {
			 if(arrayElement.equals(str)) {
				 return true;
			 }
		 }
		return false;
	}

}
