package com.sde.pepe.gdpr.application.rs.purpose;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.purposeFilePath;
import static com.sde.pepe.gdpr.application.rs.BaseProperties.purposeValidationFilePath;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.PURPOSE_REST;
import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeRecordsInCSVFile;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sde.pepe.gdpr.application.rs.purpose.read.ReadPurpose;
import com.sde.pepe.gdpr.application.rs.purpose.service.validation.PurposeValidationService;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.framework.Writeable;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class PurposeValidationManager extends PurposeManager{
	public void validateExport() {
		ReadPurpose<Purpose> read=new ReadPurpose<Purpose>();
		List<Purpose> purposes=read.read(new FileEntity(purposeFilePath),PURPOSE_REST);

		PurposeValidationService purposeValidationService=new PurposeValidationService(purposes);
		purposeValidationService.validateExport(purposeValidationFilePath);
	}
	public void arbitCheck(String outputFilePath) {
		ReadPurpose<Purpose> read=new ReadPurpose<Purpose>();
		List<Purpose> purposes=read.read(new FileEntity(purposeFilePath),PURPOSE_REST);
		List<Writeable> writeables=new ArrayList<Writeable>();
		int i=0;
		 for(Purpose purpose:purposes) {
			 purpose.setFileHeader("Purpose Id, Purpose Name, Reason\n");
			 String name=purpose.getName();
			 if(StringUtils.startsWith(name," ")){
				 purpose.setWritableString(purpose.getId()+","+purpose.getName()+",Name and Description start with empty space\n");
				 writeables.add(purpose);
				 i++;
			 }//if

			if(StringUtils.contains(name, "é")) {
				purpose.setWritableString(purpose.getId()+","+purpose.getName()+",Wrong encoding: Name and Description contain é\n");
				writeables.add(purpose);
				i++;
			}//if

			if(StringUtils.contains(name, "ô")) {
				purpose.setWritableString(purpose.getId()+","+purpose.getName()+",Wrong encoding: Name and Description contains ô\n");
				writeables.add(purpose);
				i++;
			}//if

		 }//for
		 writeRecordsInCSVFile(outputFilePath,writeables,false);
		 System.out.println("Total number of purposes to be renamed: "+i);
	}//arbitcheck

	public static void main(String[] args) {
		String csvFilePath="C:\\work\\user records\\records\\purposes_badname.csv";

		PurposeValidationManager manager=new PurposeValidationManager();
		//manager.readAndCreatePurposes(csvFilePath);
		//manager.exportIntoFile();
		manager.validateExport();
		//manager.arbitCheck(csvFilePath);
	}
}
