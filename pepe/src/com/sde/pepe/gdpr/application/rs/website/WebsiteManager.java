package com.sde.pepe.gdpr.application.rs.website;

import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.website.service.WebsiteService;

public class WebsiteManager {


	public ResponseToken createWebsite(String domain, String orgGroupId, ResponseToken responseToken) {
		WebsiteService websiteService=new WebsiteService();
		return websiteService.createWebsite(domain, orgGroupId,responseToken);
	}

	public static void main(String[] args) {
		String domain="pepe";
		String orgGroupId="b3c5ed34-8084-446a-ab65-decb09af797f";
		WebsiteManager websiteManager=new WebsiteManager();
		ResponseToken responseToken=null;

		for(int i=0;i<3;i++) {
				responseToken=websiteManager.createWebsite(domain+i+".com/abd/ddbbc/mp", orgGroupId,responseToken);
		}//for
	}//main

}
