package com.sde.pepe.gdpr.application.rs.generator;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.*;
import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeRecordsInCSVFile;

import java.util.ArrayList;
import java.util.List;

import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.generator.service.Extract;
import com.sde.pepe.gdpr.application.rs.generator.vo.Input;
import com.sde.pepe.gdpr.application.rs.organization.CountryManager;
import com.sde.pepe.gdpr.application.rs.organization.vo.Country;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.framework.Writeable;

public class InputManager {

	public static void getInputs(String filePath) {
		Extract extract=new Extract();
		List<Input> inputs=extract.extractInputs(filePath);

		 for(Input input:inputs) {
			 System.out.println(input);
		 }
	}

	public static void getRootInputs(String orgId) {
		String userInputfilePath=filesystemRoot+orgId+".csv";
		Extract extract=new Extract();
		List<Input> inputs=extract.extractRootInputs(userInputfilePath);
		String outputFilePath=getRootInputFilePath(orgId);

		CountryManager countryManager=new CountryManager();
		List<Country> countries=countryManager.getAll();
		List<Writeable> writeables=new ArrayList<>();

		 for(Input input:inputs) {
			 String countryNameFromList=input.getCountry();
			  for(Country country:countries) {
				  if(country.getName().equalsIgnoreCase(countryNameFromList)) {
					  input.setCountryCode(country.getCc());
					  input.setCountryId(country.getId());
				  }
			  }

			 System.out.println(input);
			 writeables.add(input);
		 }
		 writeRecordsInCSVFile(outputFilePath,writeables,true);
	}


	public static void createCollectionPoints(String orgId, String outputFilePath) {
		String inputFilePath=getRootInputFilePath(orgId);
		Extract extract=new Extract();
		List<Writeable> writeables=new ArrayList<>();
		List<CollectionPoint> collectionPoints=extract.extractCollectionPoints(inputFilePath);
		String fileHeader="CollectionPointType,DomainUrl,OrganizationId,Name,Description,WebFormURL,PrivacyPolicyURL,SubjectIdentifier,ConsentInteractionType,Purposes\n";
		 for(CollectionPoint collectionPoint:collectionPoints) {
			 collectionPoint.setFileHeader(fileHeader);
			 collectionPoint.setWritableString(collectionPoint.getCollectionPointType()+","
					 						   +collectionPoint.getDomainUrl()+","
					 						   +collectionPoint.getOrganizationId()+","
					 						   +collectionPoint.getName()+","
					 						   +collectionPoint.getDescription()+","
					 						   +collectionPoint.getWebFormUrl()+","
					 						   +collectionPoint.getPrivacyPolicyUrl()+","
					 						   +collectionPoint.getSubjectIdentifier()+","
					 						   +collectionPoint.getConsentType()+","
					 						   +collectionPoint.getPurposeNames()
					 						   +"\n");
			 writeables.add(collectionPoint);
		 }
		 writeRecordsInCSVFile(outputFilePath,writeables,false);
	}//createCollectionPoints

	private static String getRootInputFilePath(String orgId) {
		return filesystemRoot+orgId+"_"+env+".csv";
	}//getRootInput

	public static void createCookieBanner() {

	}

	public static void createOrganizations(String orgId, String outputFilePath) {
		String inputFilePath=getRootInputFilePath(orgId);
		Extract extract=new Extract();
		List<Writeable> writeables=new ArrayList<>();
		List<Organization> organizations=extract.extractOrganizations(inputFilePath);
		List<Organization> uniqueOrgs=new ArrayList<Organization>();
		//All implementations fail regarding duplicates. Pepe has a bug which allows duplicates being created. Hence, brute force.
		 for(Organization organization:organizations) {
			 if(!uniqueOrgs.contains(organization))
				 uniqueOrgs.add(organization);
		 }
		String fileHeader="COUNTRY ID,ORGANIZATION NAME,LANGUAGE ID,PRIVACY OFFICER ID\n";
		 for(Organization uniqueOrg:uniqueOrgs) {
			 uniqueOrg.setFileHeader(fileHeader);
			 uniqueOrg.setWritableString(uniqueOrg.getParentId()+","
					 						+uniqueOrg.getName()+","
					 						+uniqueOrg.getLanguageId()+","
					 						+uniqueOrg.getPrivacyOfficerId()+"\n");
			 writeables.add(uniqueOrg);
		 }
		 writeRecordsInCSVFile(outputFilePath,writeables,false);
	}//createOrganizations

	public static void main(String[] args) {
		String id="all";
		String orgOutputFilePath="C:\\work\\user records\\records\\create_org_"+env+".csv";

		String cpOutputFilePath="C:\\work\\user records\\records\\create_cp_"+env+".csv";

		createCollectionPoints(id,cpOutputFilePath);


	}

}
