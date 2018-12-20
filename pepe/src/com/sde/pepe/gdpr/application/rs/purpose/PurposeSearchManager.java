package com.sde.pepe.gdpr.application.rs.purpose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.purpose.service.PurposeService;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;

public class PurposeSearchManager extends PurposeManager{
	

	public Response<List<Purpose>> searchPurpose(ResponseToken responseToken,String name){
		 PurposeService service=new PurposeService();
		 return service.searchPurpose(responseToken, name);
	}
	protected String dirty(String  name) {
		String nname="";
		try {
			byte sbyte[]=name.getBytes("UTF-8");
			nname = new String(sbyte,"ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nname;
	}
	public Response<CollectionPoint> updatePurposeIds(ResponseToken responseToken, CollectionPoint collectionPoint, boolean createIfMissing) {
		PurposeService service=new PurposeService();
		String purposeIds="";
		String[] purposeNames=StringUtils.split(collectionPoint.getPurposeNames(), ';');
		 for(String purposeName:purposeNames) {
			 //make it dirty :D
			 //purposeName=dirty(StringUtils.trimToEmpty(purposeName));
			 Response<List<Purpose>> response=service.searchPurpose(responseToken, purposeName);			
			 List<Purpose> purposesFromResponse=response.getResult();
			 List<Purpose> purposes=new ArrayList<Purpose>();
			 
			 responseToken=response.getResponseToken();
			 
			 //Handle partial search. If name does not match exactly, remove from list. If list size becomes 0 then, add purpose
			 //as the actual purpose is not there
			 if((purposesFromResponse.size()>0)){
				 for(Purpose purposeFromResponse:purposesFromResponse) {
					 if(purposeName.equalsIgnoreCase(purposeFromResponse.getName()))
						 purposes.add(purposeFromResponse);
				 }//for
			 }//if
			 
			 //If the purpose is not there, create it
			 if((purposes.size()==0)&&(createIfMissing)) {
				 System.out.println(purposeName+" is missing. Creating it.");
				 responseToken=createPurpose(responseToken, new Purpose(purposeName,purposeName));
				 response=service.searchPurpose(responseToken, purposeName);
				 purposes=response.getResult();
				 responseToken=response.getResponseToken();
				 
				 System.out.println("Publishing the newly created purpose: "+purposeName);
				 responseToken=publishPurpose(responseToken,purposes.get(0));
				 response=service.searchPurpose(responseToken, purposeName);
				 purposes=response.getResult();
				 responseToken=response.getResponseToken();
				 
			 }//if
			 
			
			 for(Purpose purpose:purposes) {
				   if(purposeIds.equalsIgnoreCase(""))
					   purposeIds=purpose.getId();
				   else
					   purposeIds=purposeIds+","+purpose.getId();
				  System.out.println("idstr "+purposeIds);
			  }//for purpose			 
		 
		 }//for purposeName
		 collectionPoint.setPurposes(purposeIds);
		 Response<CollectionPoint> response=new Response<CollectionPoint>(collectionPoint, responseToken);
		 return response;
	}//updatePurposeIds	
	
	
	public static void main(String[] args) {
		PurposeSearchManager purposeSearchManager=new PurposeSearchManager();
		//String namelist="Alerts / Notifications | BE | ACD | CeraVe, Beauty Profile | BE | ACD | CeraVe, Email Marketing | BE | ACD | CeraVe, Group Marketing | BE | ACD | CeraVe, Lottery | BE | ACD | CeraVe, Phone Marketing | BE | ACD | CeraVe, Postal Marketing | BE | ACD | CeraVe, Profiling Marketing | BE | ACD | CeraVe, Rating & reviews | BE | ACD | CeraVe, Sampling pages | BE | ACD | CeraVe, SMS Marketing | BE | ACD | CeraVe, Users Right Management | BE | ACD | CeraVe, Term of use | BE | ACD | CeraVe, Terms & conditions | BE | ACD | CeraVe, Child | BE | ACD | CeraVe, Contest Games | BE | ACD | CeraVe, User generated consent | BE | ACD | CeraVe, Contact us | BE | ACD | CeraVe";
		//String[] names=StringUtils.split(namelist, ",");
		String[] names=new String[] {" Beauty Profile | BE | ACD | CeraVe","Alerts / Notifications | BE | ACD | CeraVe","Email Marketing | BE | ACD | CeraVe"};
		ResponseToken token=null;
		for(String name:names) {
			name=StringUtils.trimToEmpty(name);
			Response<List<Purpose>> response=purposeSearchManager.searchPurpose(token, name);
			List<Purpose> purposes=response.getResult();token=response.getResponseToken();
			for(Purpose purpose:purposes)
				System.out.println(purpose.getDefaultWritableString());
		}//for String
		
	}

}
