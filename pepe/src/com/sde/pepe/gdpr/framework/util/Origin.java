package com.sde.pepe.gdpr.framework.util;

import org.apache.commons.lang.StringUtils;

public enum Origin {


	COUNTRYCODE(new String[]{"AT","BE","BG","HR","CZ","DK","FI","DMI","EE","FI","FR","DE",
			  "GR","HU","INT","IE","IL","IT","LV","LT","LU","ME","NL","NO","PL",
			  "PT","RO","RS","SK","SI","ES","SE","CH","TR","UA","GB"
			  }),
	DIVISION(new String[]{"Internet","Intranet","Associate"}),
	BRAND(new String[]{"Perfect Dog","PePe","Good Dog", "Wikipedia"
			});

	private String[] array;

	private Origin(String[] array) {

		this.array=array;
	}

	public String[] getArray() {

		return array;
	}

}
