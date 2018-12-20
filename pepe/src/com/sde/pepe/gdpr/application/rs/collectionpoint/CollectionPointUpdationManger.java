package com.sde.pepe.gdpr.application.rs.collectionpoint;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.*;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sde.pepe.gdpr.application.rs.collectionpoint.read.ReadCollectionPoint;
import com.sde.pepe.gdpr.application.rs.collectionpoint.service.CollectionPointService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.organization.OrganizationManager;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.application.rs.purpose.PurposeSearchManager;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class CollectionPointUpdationManger extends CollectionPointManager{

	public ResponseToken updateCollectionPoint(ResponseToken responseToken, CollectionPoint collectionPoint) {
		CollectionPointService collectionPointService=new CollectionPointService();
		return collectionPointService.updateCollectionPoint(responseToken, collectionPoint);
	}//updateCollectionPoint


	public List<CollectionPoint> extractCollectionPointsForUpdate(String filePath){
		ReadCollectionPoint read=new ReadCollectionPoint<>();
		List<CollectionPoint> collectionPoints=read.read(new FileEntity(filePath),COLLECTIONPOINT_CREATE); //Includes Id
		return collectionPoints;
	}//extractCollectionPointsForUpdate

	public void readAndUpdateCollectionPoint(String filePath, boolean isOrgIdGiven) {

		OrganizationManager organizationManager=new OrganizationManager();
		Response<List<Organization>> exportOrgResponse=organizationManager.getOrganizations(null);
		List<Organization> organizations=exportOrgResponse.getResult();
		System.out.println("Size of organizations in server: "+organizations.size());

		ResponseToken responseToken=exportOrgResponse.getResponseToken();
		//id and organization id (if not given) are missing. read name, match with live cps in server and get both ids
		//Response<List<CollectionPoint>>

		//Reading all new collection points which are already created from csv file.
		List<CollectionPoint> newCollectionPoints=extractCollectionPointsForUpdate(filePath);

		for(CollectionPoint newCollectionPoint:newCollectionPoints) {

			//Update collection point id in new collection points
			//Update collection point version in new collection points

			//match collection point name
			String searchable=newCollectionPoint.getName();
			Response<List<CollectionPoint>> searchResponse=null;
			//StringUtils.sp
			try {
				searchResponse = searchCollectionPoint(URLEncoder.encode(StringUtils.substringBefore(searchable,"("),"UTF-8"), responseToken);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			List<CollectionPoint> collectionPoints=searchResponse.getResult();
			responseToken=searchResponse.getResponseToken();

			//Update collectionpoint id and organization id (if not given)
			for(CollectionPoint collectionPoint:collectionPoints) {
				if(searchable.equals(collectionPoint.getName())) {
					newCollectionPoint.setId(collectionPoint.getId());
					newCollectionPoint.setVersion(collectionPoint.getVersion());
					if(!isOrgIdGiven)
						newCollectionPoint.setOrganizationId(collectionPoint.getOrganizationId());
				}//if
			}//for collectionPoint

			//Update organization name

			for(Organization organization:organizations) {
				if(newCollectionPoint.getOrganizationId().equals(organization.getId()))
					newCollectionPoint.setOrganizationName(organization.getName());
			}

			System.out.println(newCollectionPoint.getId()+"::"+newCollectionPoint.getName()+"::"+newCollectionPoint.getOrganizationId()+"::"+newCollectionPoint.getOrganizationName());

		}//for newCollectionPoint

			PurposeSearchManager purposeSearchManager=new PurposeSearchManager();
			for(CollectionPoint collectionPoint:newCollectionPoints) {
				Response<CollectionPoint> response=purposeSearchManager.updatePurposeIds(responseToken, collectionPoint,true);
				collectionPoint=response.getResult();
				responseToken=response.getResponseToken();
				System.out.println(collectionPoint.getUpdateString());
				responseToken=updateCollectionPoint(responseToken,collectionPoint);
			}//for
	}

			public static void main(String[] args) {

				CollectionPointUpdationManger manager=new CollectionPointUpdationManger();
				String cpUpdateFilePath="C:\\work\\user records\\records\\create_cp_"+env+".csv";
				System.out.println("Updating collection points for "+env);
				manager.readAndUpdateCollectionPoint(cpUpdateFilePath,true);
			}//main


	}

