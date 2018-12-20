package com.sde.pepe.gdpr.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.sde.pepe.gdpr.framework.Validated;

public abstract class BaseCheck<T extends Validated<String>> {

	public List<T> sources;

	public String[] countryCodes=Origin.COUNTRYCODE.getArray();
	public String[] divisions=Origin.DIVISION.getArray();
	public String[] brands=Origin.BRAND.getArray();

	protected List<String> sanitizedCCs;
	protected List<String> sanitizedDivisions;
	protected List<String> sanitizedBrands;

	public BaseCheck(List<T> sources) {
		this.sources=sources;
		this.sanitizedCCs=sanitize(countryCodes);
		this.sanitizedDivisions=sanitize(divisions);
		this.sanitizedBrands=sanitize(brands);

	}//BaseCheck

	public List<T> getSources() {
		return sources;
	}//getSources

	public boolean checkName(T source,String[] touchPoints) {

		//Check All the collection have the name of the touchpoint : 
		//Content Websites, CRM, Retail, Ecommerce Websites, Mobile App
		if(startsWith(source.getName(),touchPoints)>-1) 
			return true;
		else 
			return false;
	}//checkName

	public boolean checkEncoding(T source) {
		String name=source.getName();
		if((StringUtils.contains(name, "é"))||(StringUtils.contains(name, "ô")))
			return false;
		else
			return true;
	}//checkEncoding

	public boolean checkWhiteSpace(T source) {
		String name=source.getName();
		if((StringUtils.startsWith(name," "))||(StringUtils.endsWith(name, " ")))
			return false;
		else
			return true;
	}//checkWhiteSpace

	//Utility
	public int startsWith(String str,String[] prefixes) {
		str=StringUtils.remove(str, " ");
		int len=prefixes.length;

		for(int i=0;i<len;i++) {
			if (StringUtils.startsWith(str, StringUtils.remove(prefixes[i]," ")))
				return i;
		}

		return -1;		
	}//startsWith 

	public boolean checkNullOrEmpty(String str) {
		String str2=StringUtils.stripToEmpty(str);

		if((str2.equalsIgnoreCase("null"))||(StringUtils.isBlank(str2))||(StringUtils.isEmpty(str2))||(str2.equals("")))
			return false;
		else
			return true;

	}//checkNullOrEmpty

	public T  populateValidatorMap(T source,ConcurrentHashMap<String,Boolean> checks) {
		Map<String, String> validatorMap=source.getValidatorMap();

		Set<String> keys=checks.keySet();
		for(String key:keys) {
			validatorMap.put("**"+key,checks.get(key).toString());
			System.out.println(key+":"+checks.get(key));
		}

		source.setValidatorMap(validatorMap);

		return source;
	}//populateValidatorMap

	public boolean summarise(ConcurrentHashMap<String,Boolean> checks) {
		checks.forEach((k,v)->{
			if(!v) 
			{
				checks.put("Summary", false);
				return;
			}
		}
				);
		if(!checks.containsKey("Summary")) {
			checks.put("Summary", true);
			return true;
		}else
			return false;
	}//summarise

	public List<String> getNames(List<T> sources){
		List<String> names=new ArrayList<String>();

		for(T source:sources) {
			names.add(StringUtils.stripToEmpty(source.getName()));
		}

		return names;		
	}//getNames

	public List<T> getModifiedSources(T object){
		List<T> modified=new ArrayList<T>();
		for(T source:sources) {
			modified.add(source);
		}
		modified.remove(object);
		return modified;
	}

	public boolean checkDuplicate(T object) {

		List<T> modified=getModifiedSources(object);

		String name=StringUtils.stripToEmpty(object.getName());
		List<String> names=getNames(modified);
		if(names.contains(name))
			return false;
		else
			return true;
	}//checkDuplicate

	public boolean matchOrigin(T source, Origin origin) {
		String name=StringUtils.remove(source.getName()," ");
		String[] array=StringUtils.split(name, "|");
		String[] originArray=origin.getArray();
		for(String arrayElement:array) {
			if(ArrayUtils.contains(originArray, arrayElement))
				return true;
		}
		return false;
	}

	public List<String> sanitize(String[] array) {
		List<String> newArray=new ArrayList<String>();
		for(String arrayE:array)
			newArray.add(StringUtils.remove(arrayE," "));
		return newArray;
	}

	public String sanitizeStr(String str) {
		return StringUtils.remove(str, " ");
	}


	public boolean checkDivisionBrand(T source) {			
		String name=sanitizeStr(source.getName());
		String[] array=StringUtils.split(name, "|");
		//Check for hubs, countries and top level entities for which check does not matter
		if(array.length==1)
			return true;
		String division="";
		String brand="";
		String divisionBrand="";

		boolean checkDivision=false;
		boolean checkBrand=false;
		boolean checkDivisionBrand=false;

		for(String arrayElement:array) {

			for(String sanitizedDivision:sanitizedDivisions) {
				if(sanitizedDivision.equals(arrayElement)) {
					checkDivision=true;
					division=arrayElement;
				}
			}
			for(String sanitizedBrand:sanitizedBrands) {
				if(sanitizedBrand.equals(arrayElement)) {
					checkBrand=true;
					brand=arrayElement;
				}
			}

		}

		divisionBrand=division+"|"+brand;

		if(StringUtils.contains(name, divisionBrand))
			checkDivisionBrand=true;

		return checkDivision&&checkBrand&&checkDivisionBrand;
	}//checkDivisionBrand
	
	public boolean checkCC(T source) {
		String name=StringUtils.remove(source.getName()," ");
		String[] array=StringUtils.split(name, "|");
		String cc="";
		boolean checkCC=false;
		for(String arrayElement:array) {
			for(String sanitizedCC:sanitizedCCs) {
				if(sanitizedCC.equals(arrayElement)) {
					checkCC=true;
					cc=arrayElement;
				}
			}
		}
		return checkCC;
	}//checkCC
	
	public boolean checkDivision(T source) {
		String name=StringUtils.remove(source.getName()," ");
		String[] array=StringUtils.split(name, "|");
		String division="";
		boolean checkDivision=false;
		for(String arrayElement:array) {
			for(String sanitizedDivision:sanitizedDivisions) {
				if(sanitizedDivision.equals(arrayElement)) {
					checkDivision=true;
					division=arrayElement;
				}
			}
			
		}
		return checkDivision;
	}//checkDivision
	
	public boolean checkBrand(T source) {
		String name=StringUtils.remove(source.getName()," ");
		String[] array=StringUtils.split(name, "|");
		String brand="";
		boolean checkBrand=false;
		
		for(String arrayElement:array) {
			for(String sanitizedBrand:sanitizedBrands) {
				if(sanitizedBrand.equals(arrayElement)) {
					checkBrand=true;
					brand=arrayElement;
				}//if
			}//for
		
		}//for
		return checkBrand;
	}//checkBrand
		

	public boolean checkCCDivisionBrand(T source) {			
		String name=StringUtils.remove(source.getName()," ");
		String[] array=StringUtils.split(name, "|");
		String cc="";
		String division="";
		String brand="";
		String ccDivisionBrand="";

		boolean checkCC=false;
		boolean checkDivision=false;
		boolean checkBrand=false;
		boolean checkCCDivisionBrand=false;

		for(String arrayElement:array) {
			for(String sanitizedCC:sanitizedCCs) {
				if(sanitizedCC.equals(arrayElement)) {
					checkCC=true;
					cc=arrayElement;
				}
			}
			for(String sanitizedDivision:sanitizedDivisions) {
				if(sanitizedDivision.equals(arrayElement)) {
					checkDivision=true;
					division=arrayElement;
				}
			}
			for(String sanitizedBrand:sanitizedBrands) {
				if(sanitizedBrand.equals(arrayElement)) {
					checkBrand=true;
					brand=arrayElement;
				}
			}

		}

		ccDivisionBrand=cc+"|"+division+"|"+brand;

		if(StringUtils.contains(name, ccDivisionBrand))
			checkCCDivisionBrand=true;

		return checkCC&&checkDivision&&checkBrand&&checkCCDivisionBrand;
	}//checkCCDivisionBrand
}//BaseCheck
