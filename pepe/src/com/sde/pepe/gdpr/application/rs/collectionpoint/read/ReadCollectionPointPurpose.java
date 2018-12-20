package com.sde.pepe.gdpr.application.rs.collectionpoint.read;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.collectionpoint.transform.impl.CollectionPointPurposeTransformer;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;
import com.sde.pepe.gdpr.framework.transform.Transformer;
import com.sde.pepe.gdpr.framework.util.file.csv.Read;

public class ReadCollectionPointPurpose<CollectionPointPurpose>  {

	public List<CollectionPointPurpose> read(FileEntity fileEntity, CustomReadHeader readHeader) {
		Transformer transformer=new CollectionPointPurposeTransformer();
		Read<CollectionPointPurpose> read=new Read<CollectionPointPurpose>();
		return (read.read(fileEntity, transformer,readHeader));
	}//read

	public List<CollectionPointPurpose> readAll(FileEntity fileEntity, CustomReadHeader readHeader) {
		Transformer transformer=new CollectionPointPurposeTransformer();
		Read<CollectionPointPurpose> read=new Read<CollectionPointPurpose>();
		return (read.readAll(fileEntity, transformer,readHeader));
	}//read	
}
