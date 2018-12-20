package com.sde.pepe.gdpr.application.rs.organization.read;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.organization.transform.impl.CountryTransformer;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;
import com.sde.pepe.gdpr.framework.transform.Transformer;
import com.sde.pepe.gdpr.framework.util.file.csv.Read;

public class ReadCountry<Country>  {
	
	
	public List<Country> read(FileEntity fileEntity, CustomReadHeader readHeader) {
		Transformer transformer=new CountryTransformer();
		Read<Country> read=new Read<Country>();
		return (read.read(fileEntity, transformer,readHeader));
	}//read

}
