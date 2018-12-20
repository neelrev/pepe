package com.sde.pepe.gdpr.application.rs.monitoring.scanner.cookie;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.uri.URITypes.GETDOMAINS;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.monitoring.scanner.vo.Domain;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;

public class Export extends BaseService<Domain>{
	
	public Export() {
		super(Domain.class);
	}
	
	private String getDomainsUrl() {
		return domain+GETDOMAINS.getUri();
	}
	
	public List<Domain> getDomains(ResponseToken oldResponseToken) {
		List<Domain> domains=new ArrayList<Domain>();
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		String transactionUrl=getDomainsUrl();
		System.out.println(transactionUrl);
		List<Header> headers=tokenManager.getSearchHeaders();
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		domains.addAll(Resting.getByJSON(transactionUrl, 443, null, Domain.class, "domains", EncodingTypes.UTF8, headers));
		//System.out.println(domains.size());
		//oldResponseToken=responseToken;
		return domains;
	}//getDomains
	
}//Export
