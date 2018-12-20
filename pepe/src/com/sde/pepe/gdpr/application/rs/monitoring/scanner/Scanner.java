package com.sde.pepe.gdpr.application.rs.monitoring.scanner;

import com.google.resting.component.impl.ServiceResponse;

public interface Scanner {
	
	public ServiceResponse scan(String url);
	
	public void validateResponse(ServiceResponse response, String privacyPolicyUrl, String tncUrl);

}
