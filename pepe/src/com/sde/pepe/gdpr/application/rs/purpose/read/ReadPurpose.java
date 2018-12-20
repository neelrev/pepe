package com.sde.pepe.gdpr.application.rs.purpose.read;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.purpose.transform.impl.PurposeTransformer;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;
import com.sde.pepe.gdpr.framework.transform.Transformer;
import com.sde.pepe.gdpr.framework.util.file.csv.Read;

public class ReadPurpose<Purpose>  {
		
	public List<Purpose> read(FileEntity fileEntity, CustomReadHeader readHeader) {
		Transformer transformer=new PurposeTransformer();
		Read<Purpose> read=new Read<Purpose>();
		return (read.read(fileEntity, transformer,readHeader));
	}//read

}
