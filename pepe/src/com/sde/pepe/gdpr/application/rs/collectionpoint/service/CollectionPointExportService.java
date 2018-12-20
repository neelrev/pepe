package com.sde.pepe.gdpr.application.rs.collectionpoint.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.firstRefreshToken;
import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeRecordsInCSVFile;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.framework.Writeable;


public class CollectionPointExportService extends BaseService<CollectionPoint>{
	
	private Export export;
	private ResponseToken firstToken;
	
	public CollectionPointExportService(){
		super(CollectionPoint.class);
		this.export=new Export();
		this.firstToken=new ResponseToken(firstRefreshToken);
	}//CollectionPointExportService
	
	public List<Writeable> exportCollectionPoints(){
		return export.getCollectionPoints(firstToken);
	}//exportCollectionPoints
	
	public Response<CollectionPoint> getCollectionPoint(String id,ResponseToken responseToken){
		if(responseToken==null)
			responseToken=firstToken;
		return export.getCollectionPoint(id,responseToken);
	}
	
	public Response<List<CollectionPoint>> exportCollectionPoints(ResponseToken responseToken){
		if(responseToken==null)
			responseToken=firstToken;
		return export.getAllCollectionPoints(responseToken);
	}
	
	public Response<List<CollectionPoint>> searchCollectionPoint(String name, ResponseToken responseToken){
		if(responseToken==null)
			responseToken=firstToken;
		return export.searchCollectionPoint(name, responseToken);
	}

	
	public void recordCollectionPoints(String csvFilePath) {
		writeRecordsInCSVFile(csvFilePath, export.getCollectionPoints(firstToken),true);	
	}//recordCollectionPoints

	public void exportReferential(String csvFilePath) {
		writeRecordsInCSVFile(csvFilePath, export.getReferential(firstToken),true);	
	}//exportReferential
	
	public void exportReferentialWithApiToken(String csvFilePath) {
		writeRecordsInCSVFile(csvFilePath, export.getReferentialWithApiToken(firstToken),false);	
	}//exportReferentialWithApiToken
	
	public void extractCollectionPointsWithPurposeCount(String csvFilePath) {
		writeRecordsInCSVFile(csvFilePath, export.extractCollectionPointsWithPurposeCount(firstToken),false);	
	}//extractCollectionPointsWithPurposeCount

	public void extractCollectionPointsWithMissingPurposes(String csvFilePath) {
		writeRecordsInCSVFile(csvFilePath, export.extractCollectionPointsWithMissingPurposes(firstToken),false);	
	}//extractCollectionPointsWithPurposeCount
	

}//CollectionPointExportService
