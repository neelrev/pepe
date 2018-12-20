package com.sde.pepe.gdpr.application.rs.organization.service.validation;

import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeValidationRecordsInCSVFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.BaseValidator;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.framework.Validated;

public class OrganizationValidationService extends BaseService<Organization> implements BaseValidator<Organization> {
	
	private Check check;
	
	public OrganizationValidationService(List<Organization> organizations) {
		super(Organization.class);
		this.check=new Check(organizations);
	}
	
	public void validateExport(String csvFilePath) {
		int i=0;
		List<Organization> organizations=check.getSources();
		System.out.println("Size of organizations for validation: "+organizations.size());
		List<Validated<String>> validated=new ArrayList<Validated<String>>();
		 for(Organization organization:organizations) {
			 if(!validate(organization))
			   i++;
			 validated.add(organization);
		 }
		 writeValidationRecordsInCSVFile(csvFilePath,validated);
		 
		 System.out.println("Number of organizations with discrepancies: "+i);
	}

	@Override
	public boolean validate(Organization source) {
		boolean checkDivisionBrand=check.checkDivisionBrand(source);
		boolean checkDuplicate=check.checkDuplicate(source);
		boolean checkEncoding=check.checkEncoding(source);
		boolean checkWhiteSpace=check.checkWhiteSpace(source);
		
		ConcurrentHashMap<String,Boolean> checks=new ConcurrentHashMap<String,Boolean>();
		
		checks.put("Check Division & Brand", checkDivisionBrand);
		checks.put("Check Duplicate", checkDuplicate);
		checks.put("Check Encoding of Org Name", checkEncoding);
		checks.put("Check White Space starting/trailing Org Name", checkWhiteSpace);
		
		
		boolean summary=check.summarise(checks);
		
		check.populateValidatorMap(source,checks);
		return summary;
	}

}
