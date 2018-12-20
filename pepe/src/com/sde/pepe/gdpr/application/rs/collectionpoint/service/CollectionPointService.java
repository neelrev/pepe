package com.sde.pepe.gdpr.application.rs.collectionpoint.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.firstRefreshToken;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.COLLECTIONPOINT_CREATE;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.COLLECTIONPOINT_CREATE_VARIATION;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.read.ReadCollectionPoint;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.organization.OrganizationManager;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.application.rs.purpose.PurposeSearchManager;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.website.WebsiteManager;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;


public class CollectionPointService extends BaseService<CollectionPoint>{
	
	private Create create;
	private ResponseToken firstToken;
	private Update update;
	
	public CollectionPointService() {
		super(CollectionPoint.class);
		this.create=new Create();
		this.update=new Update();
		this.firstToken=new ResponseToken(firstRefreshToken);
	}//CollectionPointService
	
	public ResponseToken createCollectionPoint(ResponseToken responseToken, CollectionPoint collectionPoint) {
		if(responseToken==null)
			responseToken=firstToken;
		return create.createCollectionPoint(responseToken, collectionPoint);
	}//createCollectionPoint
	
	public ResponseToken updateCollectionPoint(ResponseToken responseToken, CollectionPoint collectionPoint) {
		if(responseToken==null)
			responseToken=firstToken;
		return update.updateCollectionPoint(responseToken, collectionPoint);
	}//updateCollectionPoint
	
	public void deleteCollectionPoint(ResponseToken responseToken, CollectionPoint collectionPoint) {
		if(responseToken==null)
			responseToken=firstToken;
		update.deleteCollectionPoint(responseToken, collectionPoint);
	}//deleteCollectionPoint
	
	public void readAndCreateCollectionPoints(String filePath) {
		List<CollectionPoint> collectionPoints=extractCollectionPoints(filePath,true);
		ResponseToken responseToken=updatePurposes(collectionPoints,null);
		createScan(collectionPoints,responseToken);
	}//readAndCreateCollectionPoints
	
	public void readAndCreateCollectionPoints(String filePath, boolean givenOrgId) {
		
		if(givenOrgId)

			readAndCreateCollectionPoints(filePath);

		else {

			
			//Read new collection points to be created from Create Sheet	
			List<CollectionPoint> newCollectionPoints=extractCollectionPoints(filePath,false);	

			//Read all organizations from server
			OrganizationManager organizationManager=new OrganizationManager();
			Response<List<Organization>> orgExportResponse=organizationManager.getOrganizations(null);
			List<Organization> organizations=orgExportResponse.getResult();

			//Update organization id in the list of new collection points
			for(CollectionPoint newCollectionPoint:newCollectionPoints) {

				//Read org name from collection point and update with id fetched from org list
				String orgName=dirty(newCollectionPoint.getOrganizationName());
				for(Organization organization:organizations) {
					if(orgName.equals(organization.getName()))
						newCollectionPoint.setOrganizationId(organization.getId());
				}//for organization
				
				System.out.println(newCollectionPoint.getName()+"::"+newCollectionPoint.getOrganizationName()+"::"+newCollectionPoint.getOrganizationId());

			}//for newCollectionPoint
			
			ResponseToken token=orgExportResponse.getResponseToken();
			
			token=updatePurposes(newCollectionPoints, token);
			
			createScan(newCollectionPoints,token);
		}//if

	}//readAndCreateCollectionPoints	

	protected String dirty(String  name) {
		String nname="";
		try {
			byte sbyte[]=name.getBytes("UTF-8");
			nname = new String(sbyte,"ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nname;
	}	
	private List<CollectionPoint> extractCollectionPoints(String filePath,boolean givenOrgId){
		ReadCollectionPoint read=new ReadCollectionPoint<>();
		List<CollectionPoint> collectionPoints;
		 if(givenOrgId)
			 collectionPoints=read.read(new FileEntity(filePath),COLLECTIONPOINT_CREATE);
		 else
			 collectionPoints=read.read(new FileEntity(filePath),COLLECTIONPOINT_CREATE_VARIATION);
		return collectionPoints;
	}//extractCollectionPoints
	
	private void createScan(List<CollectionPoint> collectionPoints, ResponseToken responseToken) {
		WebsiteManager websiteManager=new WebsiteManager();
		 for(CollectionPoint collectionPoint:collectionPoints) {
			 String domainUrl=collectionPoint.getDomainUrl();
			 String orgId=collectionPoint.getOrganizationId();
			  if(StringUtils.isNotEmpty(domainUrl))
				  responseToken=websiteManager.createWebsite(domainUrl, orgId,responseToken);
		 }
	}//createScan
	
	private ResponseToken updatePurposes(List<CollectionPoint> collectionPoints, ResponseToken responseToken) {
		PurposeSearchManager purposeSearchManager=new PurposeSearchManager();
		 for(CollectionPoint collectionPoint:collectionPoints) {
			 Response<CollectionPoint> response=purposeSearchManager.updatePurposeIds(responseToken, collectionPoint,true);
			 collectionPoint=response.getResult();
			 System.out.println(collectionPoint.getCreateString());
			 responseToken=response.getResponseToken();
			 responseToken=createCollectionPoint(responseToken,collectionPoint);		 
		 }//for
	  return responseToken;
	}//updatePurposes

}//CollectionPointService
