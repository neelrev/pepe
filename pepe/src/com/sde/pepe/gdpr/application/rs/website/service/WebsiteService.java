package com.sde.pepe.gdpr.application.rs.website.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.*;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.website.vo.Website;

public class WebsiteService extends BaseService<Website>{
	
	private Create create;
	private ResponseToken firstToken;
	
	public WebsiteService() {
		super(Website.class);
		this.create=new Create();
		this.firstToken=new ResponseToken(firstRefreshToken);
	}

	public ResponseToken createWebsite(String domain, String orgGroupId,ResponseToken responseToken) {
		if(responseToken==null)
			responseToken=firstToken;
		return create.createWebsite(responseToken, new Website(domain), orgGroupId);
	}

}
