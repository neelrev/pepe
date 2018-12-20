package com.sde.pepe.gdpr.application.rs.collectionpoint.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.collectionListingPages;
import static com.sde.pepe.gdpr.application.rs.BaseProperties.domain;
import static com.sde.pepe.gdpr.application.rs.uri.URITypes.GETCOLLECTIONPOINTAPITOKEN;
import static com.sde.pepe.gdpr.application.rs.uri.URITypes.SEARCHCOLLECTIONPOINT;
import static com.sde.pepe.gdpr.application.rs.uri.URITypes.SEARCHCOLLECTIONPOINTS;

import java.net.ConnectException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.google.resting.component.impl.ServiceResponse;
import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.ApiToken;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.Referential;
import com.sde.pepe.gdpr.application.rs.organization.OrganizationManager;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.application.rs.purpose.service.PurposeService;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.framework.Writeable;

public class Export extends BaseService<CollectionPoint>{
	
	public Export() {
		super(CollectionPoint.class);
	}
	
	private String getCollectionPointUrl(int i) {
		return domain+SEARCHCOLLECTIONPOINTS.getUri()+"?page="+i+"&size=20&sort=Id,desc";
	}
	
	private String getCollectionPointDetailsUrl(String collectionPointId) {
		return domain+SEARCHCOLLECTIONPOINT.getUri()+"/"+collectionPointId;
	}
	
	private String getSearchCollectionPointUrl(String encodedCollectionPointName) {
		return domain+SEARCHCOLLECTIONPOINTS.getUri()+"?page=0&size=20&sort=Id,desc&name="+encodedCollectionPointName;
	}
	
	private String getCollectionPointApiToken(String collectionPointId) {
		return domain+GETCOLLECTIONPOINTAPITOKEN.getUri()+"/"+collectionPointId;
	}
	
	public Response<CollectionPoint> getCollectionPoint(String id, ResponseToken oldResponseToken) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getBaseHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		
		CollectionPoint collectionPoint=getExtraction(getCollectionPointDetailsUrl(id),headers);
		
		return new Response<CollectionPoint>(collectionPoint, responseToken);		
	}
	

	public Response<List<CollectionPoint>> searchCollectionPoint(String name, ResponseToken oldResponseToken) {
		ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
		List<Header> headers=tokenManager.getBaseHeaders();
	
		headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		
		List<CollectionPoint> collectionPoints=getExtractions(getSearchCollectionPointUrl(name),headers, "content");
		
		return new Response<List<CollectionPoint>>(collectionPoints, responseToken);		
	}
	
	protected Response<List<CollectionPoint>> getAllCollectionPoints(ResponseToken oldResponseToken) {
		List<CollectionPoint> collectionPoints=new ArrayList<CollectionPoint>();		
		for(int i=0;i<collectionListingPages;i++) {
			ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
			String transactionUrl=getCollectionPointUrl(i);
			List<Header> headers=tokenManager.getBaseHeaders();
			headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
			collectionPoints.addAll(getExtractions(transactionUrl, headers, "content"));
			System.out.println(collectionPoints.size());
			oldResponseToken=responseToken;
		}//for
		return new Response<List<CollectionPoint>>(collectionPoints,oldResponseToken);
	}//getCollectionPoints
	
	protected List<Writeable> getCollectionPoints(ResponseToken oldResponseToken) {
		List<Writeable> collectionPoints=new ArrayList<Writeable>();		
		for(int i=0;i<collectionListingPages;i++) {
			ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
			String transactionUrl=getCollectionPointUrl(i);
			List<Header> headers=tokenManager.getBaseHeaders();
			headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
			List<CollectionPoint> collectionPointsToAdd=getExtractions(transactionUrl,headers, "content");
			collectionPoints.addAll(collectionPointsToAdd);
			System.out.println(collectionPoints.size());
			oldResponseToken=responseToken;
		}//for
		
		return collectionPoints;
	}//getCollectionPoints
	
	private String getOrgName(Map<String,Organization> organizations,String orgId) {
		return organizations.get(orgId).getName();
	}
	
	private String getCountryName(Map<String,Organization> organizations,String orgId) {
		String parentId= organizations.get(orgId).getParentId();
		 if(parentId==null)
			 return ""; //Top level entity
		 else
			 return organizations.get(parentId).getName();
	}
		
	protected List<Writeable> extractCollectionPointsWithPurposeCount(ResponseToken oldResponseToken){
		String fileHeader="CollectionPoint Name,CollectionPoint ID,Number of Purposes,Organization Name,Organization ID, Country\n";
		OrganizationManager manager=new OrganizationManager();
		Response<Map<String,Organization>> orgExportResponse=manager.getOrganizationMap(oldResponseToken);
		
		Map<String,Organization> organizationMap=orgExportResponse.getResult();
		ResponseToken responseToken=orgExportResponse.getResponseToken();
		
		List<Writeable> collectionPoints=new ArrayList<Writeable>();	
		List<CollectionPoint> extractions=new ArrayList<CollectionPoint>();
		
		for(int i=0;i<collectionListingPages;i++) {
			System.out.println("Collection page: "+i);
			responseToken=tokenManager.getNewResponseToken(responseToken);
			String transactionUrl=getCollectionPointUrl(i);
			List<Header> headers=tokenManager.getBaseHeaders();
			headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
			extractions=getExtractions(transactionUrl,headers,"content");
			 for (CollectionPoint extracted: extractions) {
				 String orgId=extracted.getOrganizationId();
				 
				 responseToken=tokenManager.getNewResponseToken(responseToken);
				 transactionUrl=getCollectionPointDetailsUrl(extracted.getId());
				 List<Header> newHeaders=tokenManager.getBaseHeaders();
				 newHeaders.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
				 
				 Export export=new Export();
				 
				 List<Purpose> purposes=Resting.getByJSON(transactionUrl, 443, null, Purpose.class, "Purposes", EncodingTypes.UTF8, newHeaders);
				 
				 extracted.setNumberOfPurposes(purposes.size());
				 extracted.setOrganizationName(getOrgName(organizationMap,orgId));
				 extracted.setCountry(getCountryName(organizationMap,orgId));
				 
				 String writeableString=extracted.getName()+","
				 						+ extracted.getId()+","
				 						+ extracted.getNumberOfPurposes()+","
				 						+ extracted.getOrganizationName()+","
				 						+ extracted.getOrganizationId()+","
				 						+ extracted.getCountry()
				 						+"\n";
				 extracted.setFileHeader(fileHeader);
				 extracted.setWritableString(writeableString);
				 collectionPoints.add(extracted); 			
			 }//for
		 }//for
		
		return collectionPoints;

	}//extractCollectionPointsWithPurposeCount

	protected List<Writeable> extractCollectionPointsWithMissingPurposes(ResponseToken oldResponseToken){
		String fileHeader="CollectionPoint Name,CollectionPoint ID,Number of Purposes,Missing Purposes,Organization Name,Organization ID, Country\n";
		OrganizationManager manager=new OrganizationManager();
		Response<Map<String,Organization>> orgExportResponse=manager.getOrganizationMap(oldResponseToken);
		
		Map<String,Organization> organizationMap=orgExportResponse.getResult();
		ResponseToken responseToken=orgExportResponse.getResponseToken();
		
		List<Writeable> collectionPoints=new ArrayList<Writeable>();	
		List<CollectionPoint> extractions=new ArrayList<CollectionPoint>();
		
		for(int i=0;i<collectionListingPages;i++) {
			System.out.println("Collection page: "+i);
			responseToken=tokenManager.getNewResponseToken(responseToken);
			String transactionUrl=getCollectionPointUrl(i);
			List<Header> headers=tokenManager.getBaseHeaders();
			headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
			extractions=getExtractions(transactionUrl,headers,"content");
			 for (CollectionPoint extracted: extractions) {
				 String orgId=extracted.getOrganizationId();
				 
				 responseToken=tokenManager.getNewResponseToken(responseToken);
				 transactionUrl=getCollectionPointDetailsUrl(extracted.getId());
				 List<Header> newHeaders=tokenManager.getBaseHeaders();
				 newHeaders.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
				 List<Purpose> purposes=null;
				  while(purposes==null) {
					  try {
						purposes=Resting.getByJSON(transactionUrl, 443, null, Purpose.class, "Purposes", EncodingTypes.UTF8, newHeaders);
					  }catch (Exception e) {
							e.printStackTrace();
							 if ((e instanceof ConnectException)||(e instanceof SocketException)||(e instanceof NullPointerException))
								try {
									System.out.println("Exception in connecting. Waiting for 10 seconds and retrying: ");
									Thread.sleep(10000);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}//try thread.sleep
					 }//try 	
				  }//while
				 
				 extracted.setNumberOfPurposes(purposes.size());
				 extracted.setOrganizationName(getOrgName(organizationMap,orgId));
				 extracted.setCountry(getCountryName(organizationMap,orgId));
				 extracted.setMissingPurposes(getMissingPurposes(purposes,extracted.getName()));
				 
				 String writeableString=extracted.getName()+","
				 						+ extracted.getId()+","
				 						+ extracted.getNumberOfPurposes()+","
				 						+ extracted.getMissingPurposes()+","
				 						+ extracted.getOrganizationName()+","
				 						+ extracted.getOrganizationId()+","
				 						+ extracted.getCountry()
				 						+"\n";
				 extracted.setFileHeader(fileHeader);
				 extracted.setWritableString(writeableString);
				 collectionPoints.add(extracted); 	
			 }//for
		 }//for
		
		return collectionPoints;

	}//extractCollectionPointsWithMissingPurposes
	
	private String startsWith(String str) {
		List<String> prefixes=getTouchpoints();
		str=StringUtils.remove(str, " ");
		
		 for(String prefix:prefixes) {
			 if (StringUtils.startsWith(str, StringUtils.remove(prefix," ")))
				 return prefix;
		 }
		 
		 return null;		
	}//startsWith 
	
	private List<String> getTouchpoints() {
		String[] tpArray= new String[] {"Alerts / Notifications","Beauty Profile","Email Marketing",""
				+ "Group Marketing","Lottery","Phone Marketing","Postal Marketing",""
						+ "Profiling Marketing","Rating & reviews","Sampling pages",""
								+ "SMS Marketing","Users Right Management","Term of use",""
										+ "Terms & conditions","Child","Contest Games","User generated consent","Contact us"};
		List<String> touchpoints=new ArrayList<String>();
		 for(String tp:tpArray) {
			 touchpoints.add(tp);
		 }
		 return touchpoints;
	}
		
	private String getMissingPurposes(List<Purpose> purposes, String name) {
		String missingPurposes="";	
		System.out.println("Name: "+name);
		String[] parts=StringUtils.stripAll(StringUtils.split(name, "|"));
		String suffix="";
		 if(parts.length==3)
			 suffix=" | "+parts[1]+" | "+parts[2];
		 else
			 suffix=" | "+parts[1]+" | "+parts[2]+" | "+parts[3];
		List<String> touchpoints=getTouchpoints();
		 for(Purpose purpose:purposes) {
			 String prefix=startsWith(purpose.getName());
			 if((prefix!=null)&&(touchpoints.contains(prefix))) //There can be multiple occurrences for the same purpose
				 touchpoints.remove(prefix);
		 }//for
		 for(String touchpoint:touchpoints) {
			 if(missingPurposes.equals("")) {
				 missingPurposes=touchpoint+suffix;
			 }else
				 missingPurposes=missingPurposes+";"+touchpoint+suffix;
		 }
		System.out.println("Missing purposes: "+missingPurposes);
			
		return missingPurposes;	
	}//getMissingPurposes
	
	protected Response<ApiToken> getApiToken(String collectionPointId,ResponseToken oldResponseToken) {
		String transactionUrl=getCollectionPointApiToken(collectionPointId);
		return tokenManager.getApiToken(oldResponseToken, transactionUrl);
	}//getApiToken

	
	protected List<Writeable> getReferentialWithApiToken(ResponseToken oldResponseToken){
		OrganizationManager manager=new OrganizationManager();
		Response<Map<String,Organization>> orgExportResponse=manager.getOrganizationMap(oldResponseToken);
		
		Map<String,Organization> organizationMap=orgExportResponse.getResult();
		ResponseToken responseToken=orgExportResponse.getResponseToken();
		
		List<Referential> referentials=new ArrayList<Referential>();	
		List<CollectionPoint> extractions=new ArrayList<CollectionPoint>();
		
		for(int i=0;i<collectionListingPages;i++) {
			System.out.println("Collection page: "+i);
			responseToken=tokenManager.getNewResponseToken(responseToken);
			String transactionUrl=getCollectionPointUrl(i);
			List<Header> headers=tokenManager.getBaseHeaders();
			headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
			extractions=getExtractions(transactionUrl,headers,"content");
			 for (CollectionPoint extracted: extractions) {
				 String id=extracted.getId();
				 String orgId=extracted.getOrganizationId();
				 
				 //Get API Token
				 Response<ApiToken> apiTokenExportResponse=getApiToken(id, responseToken);
				 String apiToken=apiTokenExportResponse.getResult().getToken();
				 System.out.println("Id: "+id+" | token: "+apiToken);
				 extracted.setApiToken(apiToken);
				 
				 //Get Purpose linking
				 responseToken=tokenManager.getNewResponseToken(apiTokenExportResponse.getResponseToken());
				 transactionUrl=getCollectionPointDetailsUrl(id);
				 List<Header> newHeaders=tokenManager.getBaseHeaders();
				 newHeaders.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
				 
				 PurposeService purposeService=new PurposeService();
				 List<Purpose> purposes=purposeService.getExtractions(transactionUrl, newHeaders, "Purposes");
				 
				// List<Purpose> purposes=Resting.getByJSON(transactionUrl, 443, null, Purpose.class, "Purposes", EncodingTypes.UTF8, newHeaders);
				 if(purposes.size()<1) {
					 referentials.add(new Referential( extracted.getId(),
	  							extracted.getName(),
	  							"",
	  							"",
	  							orgId,
	  							getOrgName(organizationMap,orgId),
	  							getCountryName(organizationMap,orgId),
	  							extracted.getApiToken()
	  						   )); 
				 } else
				 for(Purpose purpose:purposes) {
					  referentials.add(new Referential( extracted.getId(),
							  							extracted.getName(),
							  							purpose.getId(),
							  							purpose.getLabel(),
							  							orgId,
							  							getOrgName(organizationMap,orgId),
							  							getCountryName(organizationMap,orgId),
							  							extracted.getApiToken()
							  						   ));
					  System.out.println(referentials.size());
				  }//for
			 }//for
		 }//for
		
		 List<Writeable> writeables=new ArrayList<>();
		 String fileHeader="Purpose GUID(Lower),Purpose GUID(Upper),Purpose Name, CollectionPoint GUID(Lower),CollectionPoint GUID(Upper),CollectionPoint Name,Organization GUID, Organization Name, Country,API Token\n";
		 for(Referential referential:referentials) {
				 referential.setFileHeader(fileHeader);
				 referential.setWritableString(referential.getPurposeId()+","
						 							  +referential.getPurposeId().toUpperCase()+","
						 							  +referential.getPurposeLabel()+","
						 							  +referential.getCollectionPointId()+","
						 							  +referential.getCollectionPointId().toUpperCase()+","
						 							  +referential.getCollectionPointName()+","
						 							  +referential.getOrganizationId()+","
						 							  +referential.getOrganizationName()+","
						 							  +referential.getCountryName()+","
						 							  +referential.getCollectionPointApiToken()
						 							  
						 							  +"\n");
				 writeables.add(referential);

			 }
	
		return writeables;

	}//getReferential	
	
	protected List<Writeable> getReferential(ResponseToken oldResponseToken){
		OrganizationManager manager=new OrganizationManager();
		Response<Map<String,Organization>> orgExportResponse=manager.getOrganizationMap(oldResponseToken);
		
		Map<String,Organization> organizationMap=orgExportResponse.getResult();
		ResponseToken responseToken=orgExportResponse.getResponseToken();
		
		List<Writeable> referentials=new ArrayList<Writeable>();	
		List<CollectionPoint> extractions=new ArrayList<CollectionPoint>();
		
		for(int i=0;i<collectionListingPages;i++) {
			System.out.println("Collection page: "+i);
			responseToken=tokenManager.getNewResponseToken(responseToken);
			String transactionUrl=getCollectionPointUrl(i);
			List<Header> headers=tokenManager.getBaseHeaders();
			headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
			extractions=getExtractions(transactionUrl,headers,"content");
			 for (CollectionPoint extracted: extractions) {
				 String id=extracted.getId();
				 String orgId=extracted.getOrganizationId();
				 
				 //Get Purpose linking
				 responseToken=tokenManager.getNewResponseToken(responseToken);
				 transactionUrl=getCollectionPointDetailsUrl(id);
				 List<Header> newHeaders=tokenManager.getBaseHeaders();
				 newHeaders.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
				 
				 PurposeService purposeService=new PurposeService();
				 List<Purpose> purposes=purposeService.getExtractions(transactionUrl, newHeaders, "Purposes");
				 
				// List<Purpose> purposes=Resting.getByJSON(transactionUrl, 443, null, Purpose.class, "Purposes", EncodingTypes.UTF8, newHeaders);
				 if(purposes.size()<1) {
					 referentials.add(new Referential( extracted.getId(),
	  							extracted.getName(),
	  							"",
	  							"",
	  							orgId,
	  							getOrgName(organizationMap,orgId),
	  							getCountryName(organizationMap,orgId)
	  						   )); 
				 } else
				 for(Purpose purpose:purposes) {
					  referentials.add(new Referential( extracted.getId(),
							  							extracted.getName(),
							  							purpose.getId(),
							  							purpose.getLabel(),
							  							orgId,
							  							getOrgName(organizationMap,orgId),
							  							getCountryName(organizationMap,orgId)
							  						   ));
					  System.out.println(referentials.size());
				  }//for
			 }//for
		 }//for
		
		return referentials;

	}//getReferential	
	

}//Export
