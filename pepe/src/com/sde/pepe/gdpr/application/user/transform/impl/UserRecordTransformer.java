package com.sde.pepe.gdpr.application.user.transform.impl;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.user.entity.UserRecord;
import com.sde.pepe.gdpr.framework.transform.Transformer;

/**
 * Base transformer for transforming CSV Record. 
 * 
 * @author de.sujata
 *
 * @param <T> Target type
 */
public class UserRecordTransformer implements Transformer<CSVRecord, UserRecord> {

	@Override
	public UserRecord getEntity(CSVRecord source) {
		UserRecord userRecord=new UserRecord();
		userRecord.setUserName(source.get("User Name"));
		userRecord.setFirstName(source.get("First Name"));
		userRecord.setLastName(source.get("Last Name"));
		userRecord.setEmail(source.get("Email"));
		userRecord.setRole(source.get("Role"));
		userRecord.setManagedBy(source.get("Managed By"));
		userRecord.setType(source.get("Internal/External"));
		return userRecord;
	}

	@Override
	public UserRecord getEntity(CSVRecord source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRecord> getEntities(CSVRecord source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}




	


}//CSVRecordTransformer
