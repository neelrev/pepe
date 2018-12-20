package com.sde.pepe.gdpr.application.rs.generator.read;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.BaseProperties;
import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.generator.transform.impl.InputTransformer;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;
import com.sde.pepe.gdpr.framework.transform.Transformer;
import com.sde.pepe.gdpr.framework.util.file.csv.Read;

public class ReadInput<Input>  {

	public List<Input> read(FileEntity fileEntity, CustomReadHeader readHeader) {
		Transformer transformer=new InputTransformer();
		Read<Input> read=new Read<Input>();
		return (read.read(fileEntity, transformer,readHeader));
	}//read

}
