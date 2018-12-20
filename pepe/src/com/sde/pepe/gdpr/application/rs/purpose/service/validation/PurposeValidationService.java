package com.sde.pepe.gdpr.application.rs.purpose.service.validation;

import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeValidationRecordsInCSVFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.BaseValidator;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.framework.Validated;

public class PurposeValidationService extends BaseService<Purpose> implements BaseValidator<Purpose> {
	
	private Check check;
	
	public PurposeValidationService(List<Purpose> purposes) {
		super(Purpose.class);
		this.check=new Check(purposes);
	}
	
	public void validateExport(String csvFilePath) {
		int i=0;
		List<Purpose> purposes=check.getSources();
		System.out.println("Size of purposes for validation: "+purposes.size());
		List<Validated<String>> validated=new ArrayList<Validated<String>>();
		 for(Purpose purpose:purposes) {
			 if(!validate(purpose))
			   i++;
			 validated.add(purpose);
		 }
		 writeValidationRecordsInCSVFile(csvFilePath,validated);
		 
		 System.out.println("Number of purposes with discrepancies: "+i);
	}

	@Override
	public boolean validate(Purpose source) {
		boolean checkName=check.checkName(source);
		boolean checkDuplicate=check.checkDuplicate(source);
		boolean checkCollectionPoint=check.checkCollectionPoint(source);
		boolean checkCCDivisionBrand=check.checkCCDivisionBrand(source);
		boolean checkEncoding=check.checkEncoding(source);
		boolean checkWhiteSpace=check.checkWhiteSpace(source);
		boolean checkCC=check.checkCC(source);
		boolean checkDivision=check.checkDivision(source);
		boolean checkBrand=check.checkBrand(source);
		
		
		ConcurrentHashMap<String,Boolean> checks=new ConcurrentHashMap<String,Boolean>();
		
		checks.put("Check Name contains Touchpoint", checkName);
		checks.put("Check Duplicate", checkDuplicate);
		checks.put("Check Name contains Country Code Division and Brand",checkCCDivisionBrand);
		checks.put("Check Name contains Correct Country Code",checkCC);
		checks.put("Check Name contains Correct Division",checkDivision);
		checks.put("Check Name contains Correct Brand",checkBrand);
		
		checks.put("Check Name is aligned with CollectionPoint", checkCollectionPoint);
		checks.put("Check Name has correct encoding", checkEncoding);
		checks.put("Check Name does not have white space in starting/trailing", checkWhiteSpace);
		
		boolean summary=check.summarise(checks);
		
		check.populateValidatorMap(source,checks);
		return summary;
	}
	

	

	


}
