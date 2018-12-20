package com.sde.pepe.gdpr.framework.util.file.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.sde.pepe.gdpr.application.collectionpoint.entity.CollectionPoint;
import com.sde.pepe.gdpr.application.collectionpoint.transform.impl.CollectionPointTransformer;
import com.sde.pepe.gdpr.application.user.entity.UserRecord;
import com.sde.pepe.gdpr.application.user.transform.impl.UserRecordTransformer;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class ReadUtil {

	private static final String [] USER_HEADER_MAPPING = {"User Name","Email", "First Name", "Last Name","Role","Managed By", "Internal/External"};
	private static final String [] COLLECTION_POINT_HEADER_MAPPING = {"Name","Type", "Consent Interaction Type", "Double Opt-In","Status","Unique Subject Id", "Created On","First Receipt On"," Number of Receipts"};


	public static List<UserRecord> readUserRecordsFromFileEntity(FileEntity fileEntity) {
		File f=fileEntity.getF();
		List<UserRecord> userRecords = new ArrayList<UserRecord>();

		try {
			UserRecordTransformer userRecordTransformer=new UserRecordTransformer();
			UserRecord userRecord=null;

			Reader reader = new FileReader(f);
			CSVFormat format = CSVFormat.EXCEL.withHeader(USER_HEADER_MAPPING);
			CSVParser parser = format.parse(reader);
			List<CSVRecord> csvRecords = parser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				userRecord=userRecordTransformer.getEntity(csvRecord);
				userRecords.add(userRecord);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		return userRecords;
	}

	public static List<CollectionPoint> readCollectionPoints(FileEntity fileEntity) {
		File f=fileEntity.getF();
		List<CollectionPoint> collectionPoints = new ArrayList<CollectionPoint>();

		try {
			CollectionPointTransformer collectionPointTransformer=new CollectionPointTransformer();
			CollectionPoint collectionPoint=null;

			Reader reader = new FileReader(f);
			CSVFormat format = CSVFormat.EXCEL.withHeader(COLLECTION_POINT_HEADER_MAPPING);
			CSVParser parser = format.parse(reader);
			List<CSVRecord> csvRecords = parser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				collectionPoint=collectionPointTransformer.getEntity(csvRecord);
				collectionPoints.add(collectionPoint);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		return collectionPoints;
	}

	public static void main(String[] args) {
		File f=new File("C:\\work\\user records\\records\\codetesting\\brands.csv");
		try {
			Reader reader = new FileReader(f);
			CSVFormat format = CSVFormat.EXCEL.withHeader(new String[]{"Brand"});
			CSVParser parser = format.parse(reader);
			List<CSVRecord> csvRecords = parser.getRecords();
			String str="";
			for (CSVRecord csvRecord : csvRecords) {
				str=str+",\""+csvRecord.get("Brand")+"\"";
			}
			System.out.println(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
