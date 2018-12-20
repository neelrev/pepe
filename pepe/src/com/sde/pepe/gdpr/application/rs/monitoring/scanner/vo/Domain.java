package com.sde.pepe.gdpr.application.rs.monitoring.scanner.vo;

import com.google.gson.annotations.SerializedName;
import com.sde.pepe.gdpr.framework.CRUDAble;

public class Domain implements CRUDAble<String>{
	
	@SerializedName ("DomainId")
	private String domainId;
	
	@SerializedName ("DomainName")
	private String domainName;

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	@Override
	public String getCreateString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUpdateString() {
		// TODO Auto-generated method stub
		return null;
	}

}
