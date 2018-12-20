package com.sde.pepe.gdpr.application.rs.generator.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.*;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.INPUT_CREATE;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.ROOT_INPUT_CREATE;

import java.util.ArrayList;
import java.util.List;

import com.sde.pepe.gdpr.application.rs.BaseProperties;
import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.generator.read.ReadInput;
import com.sde.pepe.gdpr.application.rs.generator.transform.impl.InputCollectionPointTransformer;
import com.sde.pepe.gdpr.application.rs.generator.transform.impl.InputOrganizationTransformer;
import com.sde.pepe.gdpr.application.rs.generator.vo.Input;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;


public class Extract{
	
	public Extract(){
		BaseProperties baseProperties=new BaseProperties();
	}
	
	public List<Input> extractRootInputs(String filePath){
		ReadInput read=new ReadInput<>();
		List<Input> inputs=read.read(new FileEntity(filePath),ROOT_INPUT_CREATE);
		
		
		
		return inputs;
	}//extractRootInputs
	
	public List<Input> extractInputs(String filePath){
		ReadInput read=new ReadInput<>();
		List<Input> inputs=read.read(new FileEntity(filePath),INPUT_CREATE);
		return inputs;
	}//extractInputs
	
	public List<Input> extractInputs(String filePath, CustomReadHeader readHeader){
		ReadInput read=new ReadInput<>();
		List<Input> inputs=read.read(new FileEntity(filePath),readHeader);
		return inputs;
	}//extractInputs
	
	public List<CollectionPoint> extractCollectionPoints(String filePath){
		List<Input> inputs=extractInputs(filePath);
		InputCollectionPointTransformer transformer=new InputCollectionPointTransformer();
		List<CollectionPoint> collectionPoints=new ArrayList<CollectionPoint>();
		 for(Input input:inputs) {
			 collectionPoints.addAll(transformer.getEntity(input));	 
		 }
		return collectionPoints;
	}//extractCollectionPoints
	
	public List<CollectionPoint> extractCollectionPoints(String filePath,CustomReadHeader readHeader){
		List<Input> inputs=extractInputs(filePath,readHeader);
		InputCollectionPointTransformer transformer=new InputCollectionPointTransformer();
		List<CollectionPoint> collectionPoints=new ArrayList<CollectionPoint>();
		 for(Input input:inputs) {
			 collectionPoints.addAll(transformer.getEntity(input,readHeader));	 
		 }
		return collectionPoints;
	}//extractCollectionPoints

	public List<Organization> extractOrganizations(String filePath){
		List<Input> inputs=extractInputs(filePath);
		InputOrganizationTransformer transformer=new InputOrganizationTransformer();
		List<Organization> organizations=new ArrayList<Organization>();
		 for(Input input:inputs) {
			 organizations.add(transformer.getEntity(input));	 
		 }
		return organizations;
	}//extractOrganizations
	
	
}//Extract
