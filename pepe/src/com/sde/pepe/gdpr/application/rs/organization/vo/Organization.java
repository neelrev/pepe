package com.sde.pepe.gdpr.application.rs.organization.vo;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.annotations.SerializedName;
import com.sde.pepe.gdpr.framework.CRUDAble;
import com.sde.pepe.gdpr.framework.Validated;
import com.sde.pepe.gdpr.framework.Writeable;

public class Organization implements Validated<String>, Writeable<String>,CRUDAble<String>,Comparable<Organization> {
	
	@SerializedName("Id")
	private  String id;
	
	@SerializedName("Name")
	private String name;
	
	@SerializedName("ParentId")
	private String parentId;
	
	@SerializedName("LanguageId")
	private String languageId="1";
	
	@SerializedName("PrivacyOfficerId")
	private String privacyOfficerId;
	
	@SerializedName("PrivacyOfficerName")
	private String privacyOfficerName;
	
	@SerializedName("CanDelete")
	private boolean canDelete;
	
	@SerializedName("Children")
	List<Organization> children;
	
	private Map<String, String> validatorMap=new ConcurrentHashMap<String, String>();
	
	private String fileHeader;
	private String writeableString;
	
	public Organization() {
		
	}
	
	public Organization(String name, String parentId, String privacyOfficerId) {
		this.name=name;
		this.parentId=parentId;
		this.privacyOfficerId=privacyOfficerId;
	}

	@Override
	public String getCreateString() {
		String nname="";
		try {
			byte sbyte[]=name.getBytes("UTF-8");
			nname = new String(sbyte,"ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"Name\":\""+nname+"\",\"ParentId\":\""+parentId+"\",\"LanguageId\":"+languageId+",\"PrivacyOfficerId\":\""+privacyOfficerId+"\"}";
	}

	@Override
	public String getDefaultFileHeader() {
		return "Id,Name,ParentId,LanguageId,PrivacyOfficerId,PrivacyOfficerName,CanDelete\n";
	}

	@Override
	public String getDefaultWritableString() {
		return id+","+name+","+parentId+","+languageId+","+privacyOfficerId+","+privacyOfficerName+","+canDelete+"\n";
	}

	@Override
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getPrivacyOfficerId() {
		return privacyOfficerId;
	}

	public void setPrivacyOfficerId(String privacyOfficerId) {
		this.privacyOfficerId = privacyOfficerId;
	}

	public String getPrivacyOfficerName() {
		return privacyOfficerName;
	}

	public void setPrivacyOfficerName(String privacyOfficerName) {
		this.privacyOfficerName = privacyOfficerName;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Map<String, String> getValidatorMap() {
		validatorMap.put("*Name", name);
		validatorMap.put("ID", id);
		validatorMap.put("Parent Id", parentId);
		return validatorMap;
	}

	@Override
	public void setValidatorMap(Map<String, String> validatorMap) {
		this.validatorMap=validatorMap;		
	}
	
	public String toString() {
		return name;
	}

	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	@Override
	public String getUpdateString() {
		String nname="";
		try {
			byte sbyte[]=name.getBytes("UTF-8");
			nname = new String(sbyte,"ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"Id\":\""+id+"\",\"Name\":\""+nname+"\",\"PrivacyOfficerId\":\""+privacyOfficerId+"\",\"LanguageId\":"+languageId+"}";
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
	public Organization setFileHeader(String fileHeader) {
		this.fileHeader=fileHeader;
		return this;
	}

	@Override
	public Organization setWritableString(String writeableString) {
		this.writeableString=writeableString;
		return this;
	}

	@Override
	public int compareTo(Organization o) {
		String orgName=o.getName();
		
		try {
			byte sbyte[]=orgName.getBytes("UTF-8");
			orgName = new String(sbyte,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if((name.equalsIgnoreCase(orgName))&&(parentId.equalsIgnoreCase(o.getParentId())))
			return 0;
		else
			return 1;
	}
	
	@Override
	public boolean equals(Object o) {
		Organization org=null;
		if(o!=null && o instanceof Organization)
		   org=(Organization)o;
		String orgName=org.getName();
		if(this.name.equals(orgName)&&(this.parentId.equalsIgnoreCase(org.getParentId())))
			return true;
		else
			return false;
	}

}
