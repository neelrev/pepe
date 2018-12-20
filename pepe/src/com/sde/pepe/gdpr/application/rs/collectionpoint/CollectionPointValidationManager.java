package com.sde.pepe.gdpr.application.rs.collectionpoint;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.collectionFilePath;
import static com.sde.pepe.gdpr.application.rs.BaseProperties.collectionValidationFilePath;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.collectionpoint.read.ReadCollectionPoint;
import com.sde.pepe.gdpr.application.rs.collectionpoint.service.validation.CollectionPointValidationService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class CollectionPointValidationManager {
	public void validateExport() {
		ReadCollectionPoint<CollectionPoint> read=new ReadCollectionPoint<CollectionPoint>();
		List<CollectionPoint> collectionPoints=read.read(new FileEntity(collectionFilePath),CustomReadHeader.COLLECTIONPOINT_REST);
		
		CollectionPointValidationService collectionPointValidationService=new CollectionPointValidationService(collectionPoints);
		collectionPointValidationService.validateExport(collectionValidationFilePath);
	}
public static void main(String[] args) {
		
	CollectionPointValidationManager manager=new CollectionPointValidationManager();
		manager.validateExport();
	}//main
}
