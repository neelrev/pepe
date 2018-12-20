package com.sde.pepe.gdpr.framework.util.file.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.sde.pepe.gdpr.application.user.entity.UserRecord;
import com.sde.pepe.gdpr.constants.Action;
import com.sde.pepe.gdpr.constants.Environment;
import com.sde.pepe.gdpr.framework.Correctable;
import com.sde.pepe.gdpr.framework.Validated;
import com.sde.pepe.gdpr.framework.Writeable;

public class WriteUtil {
	
	//User
	private static final String createHeaderString = "Email Address,First Name,Last Name,Organization,Role,User Type,Expiration Date,Action\n";
	private static final String exportHeaderString = "User Name,Email, First Name, Last Name,Role,Managed By, Internal/External";
	private static final String exportHeaderStringWithMismatchedOrg = "User Name,Email, First Name, Last Name,Role,Managed By(UAT),Managed By(PROD), Internal/External";

	
	//Generic
	
	public static void writeRecordsInCSVFile(String csvFilePath,String fileHeader, List<String> writeableStrings ) {
		try {
			Writer writer=new FileWriter(csvFilePath);
			writer.write(fileHeader);
			 for(String writeableString :writeableStrings) {
				 writer.write(writeableString);
			 }
			 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void writeRecordsInCSVFile(String csvFilePath,List<Writeable> writeables) {
		try {
			Writer writer=new FileWriter(csvFilePath);
			writer.write(writeables.get(0).getDefaultFileHeader().toString());
			 for(Writeable<String> writeable:writeables) {
				 writer.write(writeable.getDefaultWritableString());
			 }
			 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	//Generic
	public static void writeRecordsInCSVFile(String csvFilePath,List<Writeable> writeables,boolean isDefault) {
		if(isDefault)
			writeRecordsInCSVFile(csvFilePath,writeables);
		else {

			try {
				Writer writer=new FileWriter(csvFilePath);
				writer.write(writeables.get(0).getFileHeader().toString());
				for(Writeable<String> writeable:writeables) {
					writer.write(writeable.getWritableString());
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}//try
		}//else

	}	//writeRecordsInCSVFile
	
	//Generic
		public static void writeCorruptRecordsInCSVFile(String csvFilePath,List<Correctable> correctables) {
			try {
				Writer writer=new FileWriter(csvFilePath);
				writer.write(correctables.get(0).getCorrectionFileHeader().toString());
				 for(Correctable<String> correctable:correctables) {
					 String correctionString=correctable.getCorrectionWritableString();
					 if(correctionString!=null)
						 writer.write(correctionString);
				 }
				 writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	//Generic
	public static void writeValidationRecordsInCSVFile(String csvFilePath,List<Validated<String>> validateds) {
		
		TreeMap<String,String> sorted=new TreeMap<>();
		sorted.putAll(validateds.get(0).getValidatorMap());
		
		Set<String> keys=sorted.keySet();
		
		String fileHeader=getFileHeader(keys);		
		try {
			Writer writer=new FileWriter(csvFilePath);
			writer.write(fileHeader);
			 for(Validated<String> validated:validateds) {
				 writer.write(getValidationWriteString(validated.getValidatorMap(),keys));
			 }
			 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String getValidationWriteString(Map<String,String> validatorMap,Set<String> keys) {
		String validationWriteString="";		
		 for(String key:keys) {
				if(validationWriteString.equals(""))
					validationWriteString=validatorMap.get(key);
				else
					validationWriteString=validationWriteString+","+validatorMap.get(key);
		 }
		 validationWriteString=validationWriteString+"\n";
		 return validationWriteString;	
	}//getValidationWriteString
	
	private static String getFileHeader(Set<String> headers) {
		String headerString="";
		for(String header:headers) {
			if(headerString.equals(""))
				headerString=header;
			else
				headerString=headerString+","+header;
		}
		headerString=headerString+"\n";
		return headerString;
	}//getFileHeader
	
	public static void writeUserRecordsIntoCSVFile(List<UserRecord> userRecords,String filepath,Environment env, Action action) {
		try {
			Writer writer=new FileWriter(filepath) ;
			if(action==Action.Create)
				writer.write(createHeaderString);
			else
				writer.write(exportHeaderString);
			writer.write("\n");
			 for(UserRecord userRecord:userRecords) {
				  if(userRecord!=null)
					  writer.write(writeUserRecordToCSVString(userRecord,env,action));
			 }		
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String writeUserRecordToCSVString(UserRecord userRecord, Environment env,Action action) {
		
		if(action==Action.Create) {
		 if(env==Environment.UAT)
			 return(userRecord.getEmail()+","+userRecord.getFirstName()+","+userRecord.getLastName()+",L'Oréal (UAT),"+userRecord.getRole()+","+userRecord.getType()+",,Create\n");
		 else
			 return(userRecord.getEmail()+","+userRecord.getFirstName()+","+userRecord.getLastName()+","+userRecord.getManagedBy()+","+userRecord.getRole()+","+userRecord.getType()+",,Create\n");
			 
		}//if action==Create
		else
			 return(userRecord.getFirstName()+" "+userRecord.getLastName()+","+userRecord.getEmail()+","+userRecord.getFirstName()+","+userRecord.getLastName()+","+userRecord.getRole()+","+userRecord.getManagedBy()+","+userRecord.getType()+"\n");

	}
	

	public static void main(String[] args) {
		//writeUserRecordsIntoCSVFile((getMissingRecordsInUAT(),"C:\\missing.csv", Environment.UAT,Action.Create);


	}

}
