package com.sde.pepe.gdpr.application.rs.collectionpoint.transform.impl;

import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.Referential;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class CollectionPointPurposeTransformer implements Transformer<CSVRecord, Referential> {

	@Override
	public Referential getEntity(CSVRecord source) {
		Referential referential=new Referential
				(source.get("CollectionPoint Id"), 
				source.get("CollectionPoint Name"), 
				source.get("Purpose Id"), 
				source.get("Purpose Name"),
				source.get("Organization Id"),
				source.get("Organization Name"),
				source.get("Country"));	
			
		return referential;
	}
	
	@Override
	public List<Referential> getEntities(CSVRecord source, CustomReadHeader readHeader) {
		
		List<Referential> referentials = new ArrayList<Referential>();
		
		if (readHeader == REFERENTIAL_SUPPORT_PURPOSELINKING_INPUT) {
			String collectionPointId = source.get("CollectionPoint ID");
			String collectionPointName = source.get("CollectionPoint Name");
			String purposes = source.get("Missing Purposes");
			String[] names = StringUtils.split(purposes, ";");

			for (String name : names) {
				Referential referential = new Referential();
				referential.setCollectionPointId(collectionPointId);
				referential.setCollectionPointName(collectionPointName);
				referential.setPurposeLabel(name);

				referentials.add(referential);
			}
		}

		return referentials;
	}//getEntities

	@Override
	public Referential getEntity(CSVRecord source, CustomReadHeader readHeader) {
		Referential referential=null;
		if(readHeader==COLLECTIONPOINT_PURPOSE_MAP) {
			referential=getEntity(source);
		}
		if(readHeader==REFERENTIAL_SUPPORT_PURPOSELINKING) {
			referential=new Referential(source.get("Collection Point ID"),source.get("Collection Point Name"),source.get("Status"),source.get("Missing Purpose"),source.get("Missing Purpose ID"));
		}
		if(readHeader==REFERENTIAL_SUPPORT_CHECK) {
			referential=new Referential(source.get("Collection Point ID"),source.get("Collection Point Name"),source.get("Status"),source.get("Purpose Name"),source.get("Purpose ID"));
		}	
		if(readHeader==REFERENTIAL_SUPPORT_TANGLEDPURPOSELINKING) {
			referential=new Referential(source.get("Collection Point Name"),source.get("Collection Point ID"),source.get("Status"),source.get("Missing Purposes"),source.get("Missing Purpose IDs"));
		}		
	
		return referential;
	}

}
