package com.sde.pepe.gdpr.application.rs.website.vo;

import com.sde.pepe.gdpr.framework.CRUDAble;
import com.sde.pepe.gdpr.framework.Writeable;
import com.sde.pepe.gdpr.framework.entity.Identifiable;

public class Website implements Identifiable<String>, Writeable<String>, CRUDAble<String>{
	
	private String id;
	private String domain;
	private boolean independentPaths=true;
	private String includedQueryParams="\"\"";
	private boolean isRescan=false;
	private boolean keepExistingData=true;
	private int numberOfPages=0;
	private String secondaryUris="\"\"";
	private String siteMapsUris="\"\"";
	
	private String fileHeader;
	private String writeableString;
	
	public Website(String domain) {
		this.domain=domain;
	}

	@Override
	public String getCreateString() {
		return "{\"domain\":\""+domain+
				"\",\"independentPaths\":"+independentPaths+
				",\"keepExistingData\":"+keepExistingData+
				",\"numberOfPages\":"+numberOfPages+
				",\"includedQueryParams\":"+includedQueryParams+
				",\"secondaryUris\":"+secondaryUris+
				",\"isRescan\":"+isRescan+
				",\"siteMapsUris\":"+siteMapsUris+
				"}";
	}

	@Override
	public String getDefaultFileHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultWritableString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return domain;
	}

	@Override
	public String getUpdateString() {
		return null;
	}

	@Override
	public String getFileHeader() {
		return fileHeader;
	}

	@Override
	public String getWritableString() {
		return writeableString;
	}
	@Override
	public Website setFileHeader(String fileHeader) {
		this.fileHeader=fileHeader;
		return this;
	}

	@Override
	public Website setWritableString(String writeableString) {
		this.writeableString=writeableString;
		return this;
	}

}
