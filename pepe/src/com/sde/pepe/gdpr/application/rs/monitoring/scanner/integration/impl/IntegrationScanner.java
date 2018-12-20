package com.sde.pepe.gdpr.application.rs.monitoring.scanner.integration.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;

import com.google.resting.Resting;
import com.google.resting.component.impl.ServiceResponse;
import com.sde.pepe.gdpr.application.rs.monitoring.scanner.Scanner;

public class IntegrationScanner implements Scanner {

	@Override
	public ServiceResponse scan(String url) {
		ServiceResponse response=Resting.get("http://"+url+"/");
		return response;
	}

	@Override
	public void validateResponse(ServiceResponse response,String privacyPolicyUrl, String tncUrl) {
		String responseStr=response.toString();
		System.out.println("\n\n-------- Pepe Integration Reports ----------");
		if (StringUtils.contains(responseStr, privacyPolicyUrl)) {
			System.out.println("\nCheck for Privacy Policy: PASS");

		} else	if (StringUtils.contains(responseStr, tncUrl)) {
			System.out.println("\nCheck for Terms and Conditions: PASS");
			System.out.println("\nIntegration with Pepe recommended.");
		}else {
			System.out.println("\nCheck for Privacy Policy and Terms and Conditions: FAIL");
			System.out.println("\nNote: Site is not integrated with GDPR");
			System.out.println("\nRecommendation: Integration is needed with GDPR implementation.");
		}
		
		Header[] headers=response.getResponseHeaders();
		
		for(Header header:headers) {
			System.out.println(header.getName()+":"+header.getValue());
		}
		

	}

}
