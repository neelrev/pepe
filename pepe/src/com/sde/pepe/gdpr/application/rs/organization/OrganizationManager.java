package com.sde.pepe.gdpr.application.rs.organization;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.*;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.ORGANIZATION_CREATE;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.ORGANIZATION_REST;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.ORGANIZATION_UPDATE;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.list.SetUniqueList;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.organization.read.ReadOrganization;
import com.sde.pepe.gdpr.application.rs.organization.service.OrganizationService;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class OrganizationManager {

	public ResponseToken createOrganization(ResponseToken responseToken,Organization organization) {
		OrganizationService service=new OrganizationService();
		return service.createOrganization(responseToken, organization);
	}//createOrganization

	public ResponseToken updateOrganization(ResponseToken responseToken,Organization organization) {
		OrganizationService service=new OrganizationService();
		return service.updateOrganization(responseToken, organization);
	}//updateOrganization

	public List<Organization> extractOrganizationsToCreate(String filePath){
		return extract(filePath,ORGANIZATION_CREATE);
	}//extractOrganizationsToCreate

	public List<Organization> extractOrganizationsToUpdate(String filePath){
		List<Organization> organizations=extract(filePath,ORGANIZATION_UPDATE);
		 for(Organization organization:organizations) {
			 String name=organization.getName();
			 System.out.println("Name: "+name);
			 System.out.println("Corrected Name: "+URLDecoder.decode(name));
			 organization.setName(URLDecoder.decode(name));
			 System.out.println("Update string: "+organization.getUpdateString());
		 }
		return organizations;
	}//extractOrganizationsToUpdate

	private List<Organization> extract(String filePath,CustomReadHeader customReadHeader ){
		ReadOrganization read=new ReadOrganization<>();
		List<Organization> organizations=read.read(new FileEntity(filePath),customReadHeader);
		List<Organization> uniqueOrgs=new ArrayList<Organization>();
		//All implementations fail regarding duplicates. Pepe has a bug which allows duplicates being created. Hence, brute force.
		 for(Organization organization:organizations) {
			 if(!uniqueOrgs.contains(organization))
				 uniqueOrgs.add(organization);
		 }
		 for(Organization uniqueOrg:uniqueOrgs) {
			 System.out.println(uniqueOrg.getName());
		 }
		return uniqueOrgs;
	}//extract

	public List<Organization> extractOrganizations(){
		return extract(organizationFilePath,ORGANIZATION_REST);
	}//extractOrganizations

	public void readAndCreateOrganizations(String filePath) {
		List<Organization> exractedOrganizations=extractOrganizationsToCreate(filePath);
		List<Organization> organizations=new ArrayList<Organization>();
		ResponseToken token=null;
		int i=0;
		Response<List<Organization>> orgExportResponse=getOrganizations(token);
		List<Organization> organizationsFromServer=orgExportResponse.getResult();
		token=orgExportResponse.getResponseToken();//organizationFromServer:organizationsFromServer
		boolean flag=true;
		 for(Organization extractedOrganization:exractedOrganizations) {
			 for(Organization organizationFromServer:organizationsFromServer) {
				 if(organizationFromServer.compareTo(extractedOrganization)==0){
					 System.out.println("org: "+extractedOrganization.getName()+" is present under parent: "+extractedOrganization.getParentId());
					 System.out.println("id: "+organizationFromServer.getId());
					 flag=false;
				 }
			 }//for
			 if(flag)
				 organizations.add(extractedOrganization);
			 flag=true;
		 }//for
		 for(Organization organization:organizations) {
			 //Search for the organization. if exists, do not create. else create
			 token=createOrganization(token,organization);
			 System.out.println(i);
			 i++;
		 }//for
	}//readAndCreateOrganizations

	public void readAndUpdateOrganizations(String filePath) {
		List<Organization> organizations=extractOrganizationsToUpdate(filePath);
		ResponseToken token=null;
		int i=0;
		 for(Organization organization:organizations) {
			 token=updateOrganization(token,organization);
			 System.out.println(i);
			 i++;
		 }//for
	}//readAndCreateOrganizations

	public void exportOrganizations() {
		OrganizationService service=new OrganizationService();
		service.exportOrganizations(organizationFilePath);
	}

	public Response<List<Organization>> getOrganizations(ResponseToken responseToken) {
		OrganizationService service=new OrganizationService();
		return service.getOrganizations(responseToken);
	}

	public Response<Map<String,Organization>> getOrganizationMap(ResponseToken responseToken) {
		OrganizationService service=new OrganizationService();
		return service.fetchOrganizationMap(responseToken);
	}

	public static void main(String[] args) {

		String csvFilePath="C:\\work\\ser records\\records\\create_org_"+env+".csv";
		//String csvFilePath="C:\\work\\user records\\records\\b.csv";
		OrganizationManager manager=new OrganizationManager();
		//manager.readAndCreateOrganizations(csvFilePath);
		manager.exportOrganizations();
		//manager.readAndUpdateOrganizations(csvFilePath);
		//manager.extractOrganizationsToUpdate(csvFilePath);
		//manager.extract(csvFilePath,ORGANIZATION_CREATE);




	}

}
