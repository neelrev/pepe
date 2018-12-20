package com.sde.pepe.gdpr.application.rs.collectionpoint.service.validation;

import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeValidationRecordsInCSVFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.BaseValidator;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.framework.Validated;

public class CollectionPointValidationService extends BaseService<CollectionPoint> implements BaseValidator<CollectionPoint> {
	
	private Check check;
	
	public CollectionPointValidationService(List<CollectionPoint> collectionPoints) {
		super(CollectionPoint.class);
		this.check=new Check(collectionPoints);
	}
	
	public void validateExport(String csvFilePath) {
		int i=0;
		List<CollectionPoint> collectionPoints=check.getSources();
		System.out.println("Size of collection points for validation: "+collectionPoints.size());
		List<Validated<String>> validated=new ArrayList<Validated<String>>();
		 for(CollectionPoint collectionPoint:collectionPoints) {
			 if(!validate(collectionPoint))
			   i++;
			 validated.add(collectionPoint);
		 }
		 writeValidationRecordsInCSVFile(csvFilePath,validated);
		 
		 System.out.println("Number of collection points with discrepancies: "+i);
	}

	@Override
	public boolean validate(CollectionPoint source) {
		boolean checkName=check.checkName(source);
		boolean checkOrgName=check.checkCollectionPointContainsOrgName(source);
		boolean checkPolicy=check.checkPolicy(source);
		boolean checkDuplicate=check.checkDuplicate(source);
		boolean checkTransaction=check.checkTransactionStatus(source);
		//boolean checkOrganization=check.checkOrgnizationHasFourCollectionPoints(source);
		boolean checkPurposes=check.checkPurposes(source);
		boolean checkIsRemovableDuplicate=check.checkIsRemoveableDuplicate(source);
		boolean checkEncoding=check.checkEncoding(source);
		boolean checkWhiteSpace=check.checkWhiteSpace(source);
		
		ConcurrentHashMap<String,Boolean> checks=new ConcurrentHashMap<String,Boolean>();
		
		checks.put("Check Name Contains Touchpoints", checkName);
		checks.put("Check Name is Aligned with Org Name", checkOrgName);
		checks.put("Check ACTIVE Collection Points have Privacy Policy",checkPolicy);
		checks.put("Check Duplicate", checkDuplicate);
		checks.put("Check Duplicate Can Be Deleted", checkIsRemovableDuplicate);
		checks.put("Check Transaction Status", checkTransaction);
		//checks.put("Check Organization Has Four Collection Points", checkOrganization); This check is for org, not collection point
		checks.put("Check Collection Point Contains 18 Purposes", checkPurposes);
		checks.put("Check Name has correct encoding", checkEncoding);
		checks.put("Check Name does not have white space in starting/trailing", checkWhiteSpace);
		
		boolean summary=check.summarise(checks);
		
		check.populateValidatorMap(source,checks);
		
		return summary;
	}
	

	

	


}
