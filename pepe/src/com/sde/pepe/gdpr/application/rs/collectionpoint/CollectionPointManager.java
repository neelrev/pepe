package com.sde.pepe.gdpr.application.rs.collectionpoint;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.*;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.collectionpoint.service.CollectionPointExportService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.service.CollectionPointService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;

public class CollectionPointManager {

	public Response<List<CollectionPoint>> searchCollectionPoint(String collectionPointName, ResponseToken responseToken){
		CollectionPointExportService collectionPointService=new CollectionPointExportService();
		return collectionPointService.searchCollectionPoint(collectionPointName, responseToken);
	}

	public ResponseToken createCollectionPoint(ResponseToken responseToken, CollectionPoint collectionPoint) {
		CollectionPointService collectionPointService=new CollectionPointService();
		return collectionPointService.createCollectionPoint(responseToken, collectionPoint);
	}//createCollectionPoint


	public void deleteCollectionPoint(ResponseToken responseToken, CollectionPoint collectionPoint) {
		CollectionPointService collectionPointService=new CollectionPointService();
		collectionPointService.deleteCollectionPoint(responseToken, collectionPoint);
	}//deleteCollectionPoint

	public Response<CollectionPoint> getCollectionPoint(String id,ResponseToken responseToken){
		CollectionPointExportService service=new CollectionPointExportService();
		return service.getCollectionPoint(id, responseToken);
	}

	public void readAndCreateCollectionPoints(String filePath, boolean givenOrgId) {
		CollectionPointService collectionPointService=new CollectionPointService();
		collectionPointService.readAndCreateCollectionPoints(filePath, givenOrgId);
	}

	public static void main(String[] args) {
		CollectionPointManager collectionPointManager=new CollectionPointManager();
		String cpCreateFilePath="C:\\work\\user records\\records\\create_cp_"+env+".csv";
		System.out.println("Creating collection points for "+env);
		collectionPointManager.readAndCreateCollectionPoints(cpCreateFilePath,true);
		//CollectionPoint collectionPoint=new CollectionPoint();
		//collectionPoint.setId("00cc1f99-226d-4064-8e03-9677caebbf84");
		//collectionPointManager.deleteCollectionPoint(null, collectionPoint);
	}//main




}//CollectionPointManager

