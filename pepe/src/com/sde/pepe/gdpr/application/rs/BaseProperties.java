package com.sde.pepe.gdpr.application.rs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;


public class BaseProperties {
	
	//Environment specific Property file
	public static String env="beta";
	
	private static String propertyFileName=env+".properties";
	
	//Domain
	public static String domain="";
	
	//Filesystem root
	public static String filesystemRoot="";
	
	//utils
	public static String cc="";
	public static String user="";
	public static String languageId="";
	
	//cookies
	public static String cfduid="";
	public static String arrAffinity="";
	public static String ai_user="";
	public static String ai_session="";
	
	//First Refresh Token to start the run
	public static String firstRefreshToken="";

	//Collection 
	public static int collectionListingPages=0;
	
	//Purpose 
	public static int purposeListingPages=0;
	
	//Transactions
	public static int transactionListingPages=0;
	
	//Websites
	public static int websiteListingPages=0;
		
	//Local files
	public static String websiteFilePath="";
	public static String collectionFilePath="";
	public static String collectionPurposeFilePath="";
	public static String purposeFilePath="";
	public static String transactionFilePath="";
	public static String collectionValidationFilePath="";
	public static String purposeValidationFilePath="";
	public static String organizationFilePath="";
	public static String organizationValidationFilePath="";
	public static String websiteValidationFilePath="";


	static{
		Properties props=new Properties();
		try {
			FileInputStream fis=new FileInputStream(propertyFileName);
			props.load(fis);
			IOUtils.closeQuietly(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		//Domain
		domain=props.getProperty("domain");
		
		//Filesystem root
		filesystemRoot=props.getProperty("filesystem.root");
		
		//Util
		cc=props.getProperty("cc");
		user=props.getProperty("user");
		languageId=props.getProperty("languageId");
		
		//Cookies
		cfduid=props.getProperty("__cfduid");
		arrAffinity=props.getProperty("ARRAffinity");
		ai_user=props.getProperty("ai_user");
		ai_session=props.getProperty("ai_session");
		
		//First RefreshToken
		firstRefreshToken=props.getProperty("refreshtoken");
		
		//Collections
		collectionListingPages=Integer.parseInt(props.getProperty("collection.numberOfPages"));
		
		//Purposes
		purposeListingPages=Integer.parseInt(props.getProperty("purpose.numberOfPages"));
		
		//Transactions
		transactionListingPages=Integer.parseInt(props.getProperty("consent.transaction.numberOfPages"));
		
		//Website
		websiteListingPages=Integer.parseInt(props.getProperty("website.numberOfPages"));
		
		//Local files
		websiteFilePath=filesystemRoot+props.getProperty("website.filePath");
		collectionFilePath=filesystemRoot+props.getProperty("collection.filePath");
		collectionPurposeFilePath=filesystemRoot+props.getProperty("collection.purpose.filePath");
		
		purposeFilePath=filesystemRoot+props.getProperty("purpose.filePath");
		transactionFilePath=filesystemRoot+props.getProperty("consent.transaction.filePath");
		
		collectionValidationFilePath=filesystemRoot+props.getProperty("collection.validation.filePath");
		purposeValidationFilePath=filesystemRoot+props.getProperty("purpose.validation.filePath");
		organizationFilePath=filesystemRoot+props.getProperty("organization.filePath");
		organizationValidationFilePath=filesystemRoot+props.getProperty("organization.validation.filePath");
		websiteValidationFilePath=filesystemRoot+props.getProperty("website.validation.filePath");
		
		
	}//static
	



}//BaseProperties
