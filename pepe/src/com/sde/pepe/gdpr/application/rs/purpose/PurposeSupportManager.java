package com.sde.pepe.gdpr.application.rs.purpose;


import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.PURPOSE_CHECK_RENAME;
import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeRecordsInCSVFile;

import java.util.ArrayList;
import java.util.List;

import com.sde.pepe.gdpr.application.rs.purpose.read.ReadPurpose;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.framework.Writeable;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class PurposeSupportManager extends PurposeManager {

	public void checkRename(String inputFilePath,String outputFilePath) {
		ReadPurpose read=new ReadPurpose<>();
		List<Purpose> purposes=new ArrayList<Purpose>();
		List<Purpose> purposesFromFile=read.read(new FileEntity(inputFilePath),PURPOSE_CHECK_RENAME);
		ResponseToken responseToken=null;
		 for(Purpose purposeFromFile:purposesFromFile) {
			 String name=purposeFromFile.getName();
			 String id=purposeFromFile.getId();
			 String description=purposeFromFile.getDescription();
			 System.out.println("Checking "+name);
			 Response<Purpose> response=getPurpose(id,responseToken);
			 Purpose purposeFromServer=response.getResult();
			  if(name.equalsIgnoreCase(purposeFromServer.getName()))
				  System.out.println("Renamed: Purpose with id :"+id+"| New name: "+name+" | New Description: "+description);
			  else
				  purposes.add(purposeFromServer);

			 responseToken=response.getResponseToken();
		 }//for

		 System.out.println("Number of purposes not renamed from both lists in Jira issue GDPROS-587: "+purposes.size());
		 List<Writeable> writeables=new ArrayList<>();
		 String fileHeader="Purpose Id,Unchanged Purpose Name ,Unchanged Purpose Description\n";
		 for(Purpose purpose:purposes) {
			 purpose.setFileHeader(fileHeader);
			 purpose.setWritableString(purpose.getId()+","+purpose.getName()+","+purpose.getDescription()+"\n");
			 writeables.add(purpose);
		 }
		 writeRecordsInCSVFile(outputFilePath,writeables,false);

		 for(Purpose purpose:purposes) {
			 System.out.println("Purpose not renamed: "+purpose.getId()+":"+purpose.getName()+": "+purpose.getDescription());
		 }

	}//checkRename
	public static void main(String[] args) {
		String readFilePath="C:\\work\\user records\\records\\purposes_to_be_renamed.csv";
		String writeFilePath="C:\\work\\user records\\records\\purposes_not_renamed.csv";
		PurposeSupportManager manager=new PurposeSupportManager();
		manager.checkRename(readFilePath,writeFilePath);
	}//main
}
