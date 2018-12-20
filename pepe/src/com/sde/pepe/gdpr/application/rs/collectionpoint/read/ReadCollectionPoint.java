package com.sde.pepe.gdpr.application.rs.collectionpoint.read;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.collectionpoint.transform.impl.CollectionPointTransformer;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;
import com.sde.pepe.gdpr.framework.transform.Transformer;
import com.sde.pepe.gdpr.framework.util.file.csv.Read;

public class ReadCollectionPoint<CollectionPoint>  {
		
	public List<CollectionPoint> read(FileEntity fileEntity, CustomReadHeader readHeader) {
		Transformer transformer=new CollectionPointTransformer();
		Read<CollectionPoint> read=new Read<CollectionPoint>();
		return (read.read(fileEntity, transformer,readHeader));
	}//read

}
