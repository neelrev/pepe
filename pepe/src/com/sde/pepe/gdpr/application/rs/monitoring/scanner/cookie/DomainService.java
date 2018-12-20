package com.sde.pepe.gdpr.application.rs.monitoring.scanner.cookie;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.firstRefreshToken;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.monitoring.scanner.vo.Domain;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;

public class DomainService  extends BaseService<Domain> {
	
	private Export export;
	private ResponseToken firstToken;

	
	public DomainService() {
		super(Domain.class);
		this.export=new Export();
		this.firstToken=new ResponseToken(firstRefreshToken);
	}//DomainService
	


	public List<Domain> getDomains(ResponseToken responseToken) {
		if(responseToken==null)
			responseToken=firstToken;
		return export.getDomains(responseToken);
	}//getDomains
	
	public static void main(String[] args) {
		DomainService domainService=new DomainService();
		domainService.getDomains(null);
	}
	

}
