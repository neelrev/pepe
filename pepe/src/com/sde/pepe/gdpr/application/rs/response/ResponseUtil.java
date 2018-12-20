package com.sde.pepe.gdpr.application.rs.response;

import com.google.resting.component.impl.ServiceResponse;
import com.sde.pepe.gdpr.application.rs.CRUDTypes;
import com.sde.pepe.gdpr.framework.CRUDAble;

public class ResponseUtil {
	
	public static boolean checkResponse(ServiceResponse serviceResponse,CRUDAble crudable, CRUDTypes crudType) {
		String payload="";
		
		switch(crudType) {
			
			case CREATE: payload=crudable.getCreateString().toString();
						 break;
			
			case UPDATE: payload=crudable.getUpdateString().toString();
						 break;
		
		}
		
		int statusCode=serviceResponse.getStatusCode();
		
		if((statusCode!=200)&&(statusCode!=201)&&((statusCode!=204))) {
			try {
				System.out.println("Problem in "+crudType.toString()+": \n"
						     + "Object: "+payload+"\n"
				             + "HTTP status code: "+statusCode+"\n");
				if(serviceResponse.getContentData()!=null)
					System.out.println("Message from Service: "+serviceResponse.getResponseString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}//if
		else {
			System.out.println("Success in "+crudType.toString()+" operation!!!");
			System.out.println("Object "+crudType.toString()+"d successfully: "+payload);
			if(serviceResponse.getContentData()!=null)
				System.out.println("Message from service: "+serviceResponse.getResponseString());
			return true;
		}
	}

}
