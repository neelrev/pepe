package com.sde.pepe.gdpr.application.rs.collectionpoint;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.collectionPurposeFilePath;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.*;
import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeRecordsInCSVFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sde.pepe.gdpr.application.rs.collectionpoint.read.ReadCollectionPoint;
import com.sde.pepe.gdpr.application.rs.collectionpoint.read.ReadCollectionPointPurpose;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.Referential;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.framework.Writeable;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class CollectionPointSupportManager extends CollectionPointManager{

	public void checkDeletion(String filePath) {
		ReadCollectionPoint read=new ReadCollectionPoint<>();
		List<CollectionPoint> collectionPoints=new ArrayList<CollectionPoint>();
		List<CollectionPoint> collectionPointsFromFile=read.read(new FileEntity(filePath),COLLECTIONPOINT_CHECK_DELETE);
		ResponseToken responseToken=null;
		 for(CollectionPoint collectionPointFromFile:collectionPointsFromFile) {
			 String name=collectionPointFromFile.getName();
			 String id=collectionPointFromFile.getId();
			 Response<CollectionPoint> response=getCollectionPoint(id,responseToken);
			 CollectionPoint collectionPointFromServer=response.getResult();
			  if(collectionPointFromServer.getId()!=null)
				  collectionPoints.add(collectionPointFromServer);
			  else
				  System.out.println("Deleted: Collection point with id :"+id);

			 responseToken=response.getResponseToken();
		 }//for
		 for(CollectionPoint collectionPoint:collectionPoints) {
			 System.out.println("Collection point not deleted/wrongly deleted: "+collectionPoint.getName()+":"+collectionPoint.getId());
		 }

	}//checkDeletion

	public void checkRename(String filePath) {
		ReadCollectionPoint read=new ReadCollectionPoint<>();
		List<CollectionPoint> collectionPoints=new ArrayList<CollectionPoint>();
		List<CollectionPoint> collectionPointsFromFile=read.read(new FileEntity(filePath),COLLECTIONPOINT_CHECK_RENAME);
		ResponseToken responseToken=null;
		 for(CollectionPoint collectionPointFromFile:collectionPointsFromFile) {
			 String name=collectionPointFromFile.getName();
			 String id=collectionPointFromFile.getId();
			 System.out.println("Checking "+name);
			 Response<CollectionPoint> response=getCollectionPoint(id,responseToken);
			 CollectionPoint collectionPointFromServer=response.getResult();
			  if(name.equalsIgnoreCase(collectionPointFromServer.getName()))
				  System.out.println("Renamed: Collection point with id :"+id+"| New name: "+name);
			  else
				  collectionPoints.add(collectionPointFromServer);

			 responseToken=response.getResponseToken();
		 }//for
		 for(CollectionPoint collectionPoint:collectionPoints) {
			 System.out.println("Collection point not renamed: "+collectionPoint.getName()+":"+collectionPoint.getId());
		 }

	}//checkRename


	public void checkMove(String filePath) {
		ReadCollectionPoint read=new ReadCollectionPoint<>();
		List<CollectionPoint> collectionPoints=new ArrayList<CollectionPoint>();
		List<CollectionPoint> collectionPointsFromFile=read.read(new FileEntity(filePath),COLLECTIONPOINT_CHECK_MOVE);
		ResponseToken responseToken=null;
		 for(CollectionPoint collectionPointFromFile:collectionPointsFromFile) {
			 String name=collectionPointFromFile.getName();
			 String id=collectionPointFromFile.getId();
			 String orgId=collectionPointFromFile.getOrganizationId();
			 String orgName=collectionPointFromFile.getOrganizationName();

			 System.out.println("Checking "+name);
			 Response<CollectionPoint> response=getCollectionPoint(id,responseToken);
			 CollectionPoint collectionPointFromServer=response.getResult();
			  if(orgId.equals(collectionPointFromServer.getOrganizationId()))
				System.out.println("Moved: "+name+" under Org: "+orgName+" : "+orgId);
			  else
				  collectionPoints.add(collectionPointFromServer);

			 responseToken=response.getResponseToken();
		 }//for
		 for(CollectionPoint collectionPoint:collectionPoints) {
			 System.out.println("Collection point not moved: "+collectionPoint.getId()+":"+collectionPoint.getName()+" under Incorrect Org id: "+collectionPoint.getId());
		 }

	}//checkRename

	public void preparePurposeLinking(String inputFilePath,String outputFilePath) {
		String fileHeader="CollectionPoint Name,CollectionPoint ID,Status,Missing Purposes,Missing Purpose IDs\n";

		ReadCollectionPointPurpose read=new ReadCollectionPointPurpose<>();
		List<Referential> referentials=read.read(new FileEntity(inputFilePath),REFERENTIAL_SUPPORT_PURPOSELINKING);
		System.out.println("Size of referentials for missing purposes: "+referentials.size());

		 Collection<CollectionPoint> collectionPoints=getCompositeReferentialMap(referentials).values();
		 List<Writeable> writables=new ArrayList<>();
		  for(CollectionPoint collectionPoint:collectionPoints) {
			  collectionPoint.setFileHeader(fileHeader);
			  collectionPoint.setWritableString(collectionPoint.getName()+","
					 						  +collectionPoint.getId()+","
					 						  +collectionPoint.getStatus()+","
					 						  +collectionPoint.getPurposeNames()+","
					 						  +collectionPoint.getPurposes()+","
					 						  +"\n");
			 writables.add(collectionPoint);
		  }
		  writeRecordsInCSVFile(outputFilePath,writables,false);
	}//preparePurposeLinking

	public void preparePurposeLinkingInputForSupport(String inputFilePath,String outputFilePath) {
		String fileHeader="CollectionPoint Name,CollectionPoint ID,Missing Purpose\n";

		ReadCollectionPointPurpose read=new ReadCollectionPointPurpose<>();
		List<Referential> referentials=read.readAll(new FileEntity(inputFilePath),REFERENTIAL_SUPPORT_PURPOSELINKING_INPUT);
		System.out.println("Size of referentials for missing purposes: "+referentials.size());

		 List<Writeable> writables=new ArrayList<Writeable>();
		  for(Referential referential:referentials) {
			  referential.setFileHeader(fileHeader);
			  referential.setWritableString(referential.getCollectionPointName()+","
					 						  +referential.getCollectionPointId()+","
					 						  +referential.getPurposeLabel()
					 						  +"\n");
			 writables.add(referential);
		  }
		  writeRecordsInCSVFile(outputFilePath,writables,false);
	}//preparePurposeLinkingInputForSupport

	private Map<String,CollectionPoint> getCompositeReferentialMap(List<Referential> referentials) {
		Map<String,CollectionPoint> collectionPointMap=new HashMap<String,CollectionPoint>();
		 for(Referential referential:referentials) {
			 String collectionPointId=referential.getCollectionPointId();
			 CollectionPoint collectionPoint;
			 String purposeNames="";
			 String purposeIds="";

			 //New collection point which is not already there in the cp map
			 if(!collectionPointMap.containsKey(collectionPointId)) {
				 collectionPoint=new CollectionPoint();
				 collectionPoint.setId(collectionPointId);
				 collectionPoint.setName(referential.getCollectionPointName());
				 collectionPoint.setStatus(referential.getCollectionPointStatus());

				 purposeNames=referential.getPurposeLabel();
				 collectionPoint.setPurposeNames(purposeNames);

				 purposeIds=referential.getPurposeId();
				 collectionPoint.setPurposes(purposeIds);


			 }else {
				 //Collection point which is already in the map
				 collectionPoint=collectionPointMap.get(collectionPointId);

				 purposeNames=collectionPoint.getPurposeNames()+";"+referential.getPurposeLabel();
				 collectionPoint.setPurposeNames(purposeNames);

				 purposeIds=collectionPoint.getPurposes()+";"+referential.getPurposeId();
				 collectionPoint.setPurposes(purposeIds);

			 }//if
			 collectionPointMap.put(collectionPointId, collectionPoint);

		 }//for
		 return collectionPointMap;
	}//createCompositeReferentialMap

	public void checkRemovalOfReferentials(String inputFilePath,String outputFilePath) {
		ReadCollectionPointPurpose read=new ReadCollectionPointPurpose<>();
		List<Referential> referentialsFromFile=read.read(new FileEntity(inputFilePath),REFERENTIAL_SUPPORT_CHECK);
		System.out.println("Size of referentials for purpose deletion: "+referentialsFromFile.size());

		CollectionPointExportManager collectionPointExportManager=new CollectionPointExportManager();
		List<Referential> referentialsFromServer=collectionPointExportManager.extractCollectionPointPurposes();

		Map<String,Referential> referentialsFromServerMap=new HashMap<String,Referential>();
		 for(Referential referentialFromServer:referentialsFromServer) {
			 referentialsFromServerMap.put(referentialFromServer.getId(), referentialFromServer);
		 }//for

		 int i=0;
		 List<Writeable> writeables=new ArrayList<>();
		 String fileHeader="Collection Point Name, Collection Point ID, Purpose Not Deleted, Purpose ID,Collection Point Status\n";
		 for(Referential referentialFromFile:referentialsFromFile) {
			 if(referentialsFromServerMap.containsKey(referentialFromFile.getId())) {
				 referentialFromFile.setFileHeader(fileHeader);
				 referentialFromFile.setWritableString(referentialFromFile.getCollectionPointName()+","
						 							  +referentialFromFile.getCollectionPointId()+","
						 							  +referentialFromFile.getPurposeLabel()+","
						 							  +referentialFromFile.getPurposeId()+","
						 							  +referentialFromFile.getCollectionPointStatus()+"\n");
				 System.out.println("Not deleted: "+referentialFromFile);
				 writeables.add(referentialFromFile);
				 i++;
			 }
		 }//for

		 writeRecordsInCSVFile(outputFilePath,writeables,false);

		System.out.println("Number of Purposes not deleted from Collection Points: "+i);
	}


	public void checkPurposeLinking(String inputFilePath,String outputFilePath) {

		//Read the list of referentials to be created from missing purposes file
		ReadCollectionPointPurpose read=new ReadCollectionPointPurpose<>();
		List<Referential> compositeReferentials=read.read(new FileEntity(inputFilePath),REFERENTIAL_SUPPORT_PURPOSELINKING);
		List<Referential> referentials=new ArrayList<Referential>();
		 for(Referential compositeReferential:compositeReferentials) {
			 String compositePurposeLabel=compositeReferential.getPurposeLabel();
			 String compositePurposeId=compositeReferential.getPurposeId();
			 String collectionPointId=compositeReferential.getCollectionPointId();
			 String collectionPointName=compositeReferential.getCollectionPointName();
			 String collectionPointStatus=compositeReferential.getCollectionPointStatus();
			 if(!StringUtils.contains(compositePurposeLabel, ";"))
				 referentials.add(compositeReferential);
			 else {
				 String[]purposeNames=StringUtils.split(compositePurposeLabel, ";");
				 String[]purposeIds=StringUtils.split(compositePurposeId, ";");
				 int len=purposeNames.length;
				  for(int i=0;i<len;i++) {
					  referentials.add(new Referential(collectionPointId,collectionPointName,collectionPointStatus,purposeNames[i],purposeIds[i]));
				  }//for
			 }//if
		 }//for
		 System.out.println("Size of referentials to link: "+referentials.size());

		 List<Referential> allReferentialsExtracted=read.read(new FileEntity(collectionPurposeFilePath),COLLECTIONPOINT_PURPOSE_MAP);

		 int count=0;

		 List<Referential> referentialsNotLinked=new ArrayList<Referential>();
		  for(Referential referential:referentials) {
			   if(!searchReferentialId(allReferentialsExtracted,referential.getId())) {
				   System.out.println("Not linked: "+referential.getDefaultWritableString());
				   referentialsNotLinked.add(referential);
				   count++;
			   }//if
		  }//for

		 System.out.println("Referentials not linked: "+count);
		 Collection<CollectionPoint> collectionPoints=getCompositeReferentialMap(referentials).values();
		 String fileHeader="CollectionPoint Name,CollectionPoint ID,Status,Missing Purposes,Missing Purpose IDs\n";
		 List<Writeable> writables=new ArrayList<>();
		  for(CollectionPoint collectionPoint:collectionPoints) {
			  collectionPoint.setFileHeader(fileHeader);
			  collectionPoint.setWritableString(collectionPoint.getName()+","
					 						  +collectionPoint.getId()+","
					 						  +collectionPoint.getStatus()+","
					 						  +collectionPoint.getPurposeNames()+","
					 						  +collectionPoint.getPurposes()+","
					 						  +"\n");
			 writables.add(collectionPoint);
		  }
		  writeRecordsInCSVFile(outputFilePath,writables,false);
	}

	private boolean searchReferentialId(List<Referential> referentials, String id) {
		for (Referential referential:referentials) {
			if (id.equals(referential.getId()))
				return true;
		}
		return false;
	}//searchReferentialId

	public void untaglePurposeLinking(String inputFilePath,String outputFilePath) {

		//Read the list of referentials to be created from missing purposes file
		ReadCollectionPointPurpose read=new ReadCollectionPointPurpose<>();
		List<Referential> compositeReferentials=read.read(new FileEntity(inputFilePath),REFERENTIAL_SUPPORT_TANGLEDPURPOSELINKING);
		List<Referential> referentials=new ArrayList<Referential>();
		 for(Referential compositeReferential:compositeReferentials) {
			 String compositePurposeLabel=compositeReferential.getPurposeLabel();
			 String compositePurposeId=compositeReferential.getPurposeId();
			 String collectionPointId=compositeReferential.getCollectionPointId();
			 String collectionPointName=compositeReferential.getCollectionPointName();
			 String collectionPointStatus=compositeReferential.getCollectionPointStatus();
			 if(!StringUtils.contains(compositePurposeLabel, ";"))
				 referentials.add(compositeReferential);
			 else {
				 String[]purposeNames=StringUtils.split(compositePurposeLabel, ";");
				 String[]purposeIds=StringUtils.split(compositePurposeId, ";");
				 int len=purposeNames.length;
				  for(int i=0;i<len;i++) {
					  referentials.add(new Referential(collectionPointId,collectionPointName,collectionPointStatus,purposeNames[i],purposeIds[i]));
				  }//for
			 }//if
		 }//for
		 System.out.println("Size of referentials to link: "+referentials.size());
		 List<Writeable> writeables=new ArrayList<>();
		 String fileHeader="Collection Point Name,Collection Point ID,Status,Missing Purpose Name,Missing Purpose ID\n";
		 for(Referential referential:referentials) {
				 referential.setFileHeader(fileHeader);
				 referential.setWritableString(referential.getCollectionPointName()+","
						 							  +referential.getCollectionPointId()+","
						 							  +referential.getCollectionPointStatus()+","
						 							  +referential.getPurposeLabel()+","
						 							  +referential.getPurposeId()+"\n");
				 writeables.add(referential);
		 }//for

		 writeRecordsInCSVFile(outputFilePath,writeables,false);
	}//untanglePurposeLinking



	public static void main(String[] args) {
		String readFilePath="C:\\work\\records\\cpmp_prod.csv";
		String writeFilePath="C:\\work\\records\\untangled_cpmp_prod.csv";
		CollectionPointSupportManager manager=new CollectionPointSupportManager();
		manager.preparePurposeLinkingInputForSupport(readFilePath, writeFilePath);
	}//main

}//CollectionPointSupportManager
