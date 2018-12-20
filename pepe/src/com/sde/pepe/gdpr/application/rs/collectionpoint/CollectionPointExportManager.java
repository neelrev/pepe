package com.sde.pepe.gdpr.application.rs.collectionpoint;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.collectionFilePath;
import static com.sde.pepe.gdpr.application.rs.BaseProperties.collectionPurposeFilePath;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.COLLECTIONPOINT_PURPOSE_MAP;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.collectionpoint.read.ReadCollectionPointPurpose;
import com.sde.pepe.gdpr.application.rs.collectionpoint.service.CollectionPointExportService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.Referential;
import com.sde.pepe.gdpr.framework.Writeable;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class CollectionPointExportManager extends CollectionPointManager{

	public void exportIntoFile() {
		CollectionPointExportService collectionPointService=new CollectionPointExportService();
		collectionPointService.recordCollectionPoints(collectionFilePath);
	}

	public List<Writeable> exportList() {
		CollectionPointExportService collectionPointService=new CollectionPointExportService();
		return collectionPointService.exportCollectionPoints();

	}

	public void exportReferentialIntoFile() {
		CollectionPointExportService collectionPointService=new CollectionPointExportService();
		collectionPointService.exportReferential(collectionPurposeFilePath);
	}

	public void exportReferentalWithApiToken() {
		CollectionPointExportService collectionPointService=new CollectionPointExportService();
		collectionPointService.exportReferentialWithApiToken(collectionPurposeFilePath);
	}


	public List<Referential> extractCollectionPointPurposes(){
		ReadCollectionPointPurpose read=new ReadCollectionPointPurpose<>();
		List<Referential> referentials=read.read(new FileEntity(collectionPurposeFilePath),COLLECTIONPOINT_PURPOSE_MAP);
		return referentials;
	}//extractCollectionPointPurposes

	public void extractCollectionPointsWithPurposeCount(String csvFilepath) {
		CollectionPointExportService collectionPointService=new CollectionPointExportService();
		collectionPointService.extractCollectionPointsWithPurposeCount(csvFilepath);
	}

	public void extractCollectionPointsWithMissingPurposes(String csvFilepath) {
		CollectionPointExportService collectionPointService=new CollectionPointExportService();
		collectionPointService.extractCollectionPointsWithMissingPurposes(csvFilepath);
	}

	public static void main(String[] args) {
		CollectionPointExportManager manager=new CollectionPointExportManager();
		String cpCreateFilePath="C:\\work\\user records\\records\\cpmp.csv";
		//collectionPointManager.readAndCreateCollectionPoints(cpCreateFilePath,true);
		//manager.exportReferentialIntoFile();
		manager.exportIntoFile();
		//CollectionPoint collectionPoint=new CollectionPoint();
		//collectionPoint.setId("00cc1f99-226d-4064-8e03-9677caebbf84");
		//collectionPointManager.deleteCollectionPoint(null, collectionPoint);
		//manager.extractCollectionPointsWithPurposeCount(cpCreateFilePath);
		//manager.extractCollectionPointsWithMissingPurposes(cpCreateFilePath);
		//manager.exportReferentalWithApiToken();

	}//main

}//CollectionPointExportManager

