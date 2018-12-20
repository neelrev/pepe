package com.sde.pepe.gdpr.application.collectionpoint.service;

import static com.sde.pepe.gdpr.constants.Environment.*;
import static com.sde.pepe.gdpr.framework.util.file.csv.ReadUtil.*;

import java.util.ArrayList;
import java.util.List;

import com.sde.pepe.gdpr.application.collectionpoint.entity.CollectionPoint;
import com.sde.pepe.gdpr.constants.Environment;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class CollectionPointService {
	
	private void compareCollectionPoints(String filepath1, Environment env1, String filepath2, Environment env2) {
		compareCollectionPoints(new FileEntity(filepath1,env1),new FileEntity(filepath2,env2));
	}
	
	protected void compareCollectionPoints(FileEntity fileEntity1,FileEntity fileEntity2 ) {
		Environment env1=fileEntity1.getEnvironment();
		Environment env2=fileEntity2.getEnvironment();
		
		System.out.println("Discrepancies between "+env1+" and "+env2+": \n ------------------------------------\n");
		boolean flag1=false;
		int discrepancy=0;
			
		List<CollectionPoint> collectionPoints1=readCollectionPoints(fileEntity1);
		List<CollectionPoint> collectionPoints2=readCollectionPoints(fileEntity2);
		
		System.out.println("Size of collection points in "+env1+": "+collectionPoints1.size());
		System.out.println("Size of collection points in "+env2+": "+collectionPoints2.size());
		
		List<String> missingRecords=new ArrayList<String>();
		 
		 for(CollectionPoint collectionPoint1:collectionPoints1) {
			 String key1=collectionPoint1.getName();
			  for(CollectionPoint collectionPoint2:collectionPoints2) {
				  String key2=collectionPoint2.getName();
				   if(key1.equals(key2)){
					   flag1=true;
					   if(collectionPoint1.compareTo(collectionPoint2)!=0)
					   {
						   System.out.println("--------------------------------------------------------------");
						   System.out.println("The records for "+key1+" are not same for "+env1+" and "+env2+" environments");
						   System.out.println("Record in "+env1+": "+collectionPoint1);
						   System.out.println("Record in "+env2+": "+collectionPoint2);
						   discrepancy++;
						  
						   
					   }//if 
					   
				   }//if key1==key2
			  }//for 
			 if(!flag1) {
				 missingRecords.add("Records for "+key1+" are not found in "+env2);
			 }else
				 flag1=false;
			 
		 }//for 
		 
		 System.out.println("\n\n\n\n\n\n");
		 System.out.println("Number of records with mismatched data across "+env1+" and "+env2+": "+discrepancy);
		 System.out.println("\n\n\n\n\n\n");
		 System.out.println("Number of missing records between "+env1+" and "+env2+": "+missingRecords.size());
		 System.out.println("\n\n\n\n\n\n");

		for(String missingRecord:missingRecords)
			System.out.println(missingRecord);
		
	}
	
	public static void main(String[] args) {
		
		String prodFilepath="C:\\work\\user records\\records\\collections_prod.csv";
		String uatFilepath="C:\\work\\user records\\records\\collections_uat.csv";

		
		CollectionPointService collectionPointService=new CollectionPointService();
		collectionPointService.compareCollectionPoints(prodFilepath, PROD, uatFilepath, UAT);
		//collectionPointService.compareCollectionPoints(uatFilepath, UAT,prodFilepath, PROD);
		
	}//main

}//CollectionPointService
