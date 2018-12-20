package com.sde.pepe.gdpr.application.rs.purpose.transform.impl;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class PurposeTransformer implements Transformer<CSVRecord, Purpose> {

	@Override
	public Purpose getEntity(CSVRecord source) {
		Purpose purpose=new Purpose();		
		purpose.setLabel(source.get("Purpose Name"));
		purpose.setDescription(source.get("Purpose Description"));
		return purpose;
	}

	@Override
	public Purpose getEntity(CSVRecord source, CustomReadHeader readHeader) {
		Purpose purpose=new Purpose();

		switch(readHeader) {
		
		case PURPOSE_REST: 
			purpose.setId(source.get("Id"));
			purpose.setLabel(source.get("Label"));
			purpose.setDescription(source.get("Description"));
			purpose.setStatus(source.get("Status"));
			purpose.setLifeSpan(new Integer(source.get("LifeSpan")));
			purpose.setActiveDataSubjectCount(new Integer(source.get("ActiveDataSubjects")));
			purpose.setPendingDataSubjectCount(new Integer(source.get("PendingDataSubjects")));
			purpose.setWithdrawnDataSubjectCount(new Integer(source.get("WithdrawnDataSubjects")));
			purpose.setCollectionPointCount(new Integer(source.get("CollectionPoints")));
			return purpose;
		case PURPOSE_CREATE:
			purpose=getEntity(source);
			return purpose;
		case PURPOSE_UPDATE:
			purpose.setId(source.get("Purpose Id"));
			purpose.setLabel(source.get("Purpose Name"));
			purpose.setDescription(source.get("Purpose Description"));
			return purpose;
		case PURPOSE_CHECK_RENAME:
			purpose.setId(source.get("Purpose ID"));
			purpose.setLabel(source.get("New Purpose Name"));
			purpose.setDescription(source.get("New Purpose Description"));
			return purpose;
			
		}
		
		return purpose;
	}//getEntity

	@Override
	public List<Purpose> getEntities(CSVRecord source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}

}
