package com.sde.pepe.gdpr.application.collectionpoint.transform.impl;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.sde.pepe.gdpr.application.collectionpoint.entity.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class CollectionPointTransformer implements Transformer<CSVRecord, CollectionPoint> {

	@Override
	public CollectionPoint getEntity(CSVRecord source) {
		CollectionPoint collectionPoint=new CollectionPoint();
		collectionPoint.setName(source.get("Name"));
		collectionPoint.setType(source.get("Type"));
		collectionPoint.setConsentInteractionType(source.get("Consent Interaction Type"));
		collectionPoint.setDoubleOptIn(source.get("Double Opt-In"));
		collectionPoint.setStatus(source.get("Status"));
		collectionPoint.setUniqueSubjectId(source.get("Unique Subject Id"));
		collectionPoint.setCreatedOn(source.get("Created On"));
		
		collectionPoint.setFirstReceiptOn(source.get("First Receipt On"));


	//	collectionPoint.setNumberOfReceipts(Integer.parseInt(source.get("Number of Receipts")));
		
		return collectionPoint;
	}

	@Override
	public CollectionPoint getEntity(CSVRecord source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CollectionPoint> getEntities(CSVRecord source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}

}
