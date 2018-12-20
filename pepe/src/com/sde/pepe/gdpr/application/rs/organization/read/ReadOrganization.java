package com.sde.pepe.gdpr.application.rs.organization.read;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.organization.transform.impl.OrganizationTransformer;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;
import com.sde.pepe.gdpr.framework.transform.Transformer;
import com.sde.pepe.gdpr.framework.util.file.csv.Read;

public class ReadOrganization<Organization>  {
	
	
	public List<Organization> read(FileEntity fileEntity, CustomReadHeader readHeader) {
		Transformer transformer=new OrganizationTransformer();
		Read<Organization> read=new Read<Organization>();
		return (read.read(fileEntity, transformer,readHeader));
	}//read

}
