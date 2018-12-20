package com.sde.pepe.gdpr.application.rs.organization.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.firstRefreshToken;
import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeRecordsInCSVFile;

import java.util.List;
import java.util.Map;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;

public class OrganizationService  extends BaseService<Organization> {
	
	private Create create;
	private Export export;
	private ResponseToken firstToken;
	private Update update;
	
	public OrganizationService() {
		super(Organization.class);
		this.create=new Create();
		this.export=new Export();
		this.update=new Update();
		this.firstToken=new ResponseToken(firstRefreshToken);
	}//OrganizationService
	
	public ResponseToken createOrganization(ResponseToken responseToken, Organization organization) {
		if(responseToken==null)
			responseToken=firstToken;
		return create.createOrganization(responseToken, organization);
	}//createOrganization
	
	public ResponseToken updateOrganization(ResponseToken responseToken, Organization organization) {
		if(responseToken==null)
			responseToken=firstToken;
		return update.updateOrganization(responseToken, organization);
	}//updateOrganization

	public Response<List<Organization>> getOrganizations(ResponseToken responseToken) {
		if(responseToken==null)
			responseToken=firstToken;
		return export.fetchOrganizations(responseToken);
	}//getOrganizations
	
	public Response<Map<String,Organization>> fetchOrganizationMap(ResponseToken responseToken) {
		if(responseToken==null)
			responseToken=firstToken;
		return export.fetchOrganizationMap(responseToken);
	}
	
	public void exportOrganizations(String csvFilePath) {
		writeRecordsInCSVFile(csvFilePath, export.getOrganizations(firstToken),true);	

	}//exportOrganizations
}
