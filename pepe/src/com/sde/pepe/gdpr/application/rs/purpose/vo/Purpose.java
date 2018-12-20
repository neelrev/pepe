package com.sde.pepe.gdpr.application.rs.purpose.vo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.google.gson.annotations.SerializedName;
import com.sde.pepe.gdpr.framework.CRUDAble;
import com.sde.pepe.gdpr.framework.Correctable;
import com.sde.pepe.gdpr.framework.Validated;
import com.sde.pepe.gdpr.framework.Writeable;

public class Purpose implements Writeable<String>, CRUDAble<String>,Validated<String>,Correctable<String>{
	
	@SerializedName ("Id")
	private String id;
	
	@SerializedName ("Label")
	private String label;
	
	@SerializedName ("Description")
	private String description;
	
	@SerializedName ("Status")
	private String status;
	
	@SerializedName ("LifeSpan")
	private int lifeSpan=0;
	
	private Map<String, String> validatorMap=new ConcurrentHashMap<String, String>();
	
	private String fileHeader;
	private String writeableString;
	
	@SerializedName("ActiveDataSubjects")
	private int activeDataSubjectCount;
	
	@SerializedName("PendingDataSubjects")
	private int pendingDataSubjectCount;
	
	@SerializedName("WithdrawnDataSubjects")
	private int withdrawnDataSubjectCount;
	
	@SerializedName("CollectionPoints")
	private int collectionPointCount;
	
	private String defaultLanguage="en-us";
	
	private int version=1;
	
	public Purpose() {
		
	}

	public Purpose(String label, String description) {
		this.label=label;
		this.description=description;
	}

	@Override
	public String getDefaultFileHeader() {
		return "Id,Label,Description,Status,LifeSpan,ActiveDataSubjects,PendingDataSubjects,WithdrawnDataSubjects,CollectionPoints\n";
	}

	@Override
	public String getDefaultWritableString() {
		String ndesc=StringUtils.replace(description, ",", " ");
		String nlabel = StringUtils.replace(label, ",", " ");
		return id+","
				+nlabel+","
				+ndesc+","
				+status+","
				+lifeSpan+","
				+activeDataSubjectCount+","
				+pendingDataSubjectCount+","
				+withdrawnDataSubjectCount+","
				+collectionPointCount
				+"\n";
	}

	@Override
	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String getCreateString() {
		String nlabel="";
		try {
			byte sbyte[]=label.getBytes("UTF-8");
			nlabel = new String(sbyte,"ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String ndesc="";
		try {
			byte sbyte[]=description.getBytes("UTF-8");
			ndesc = new String(sbyte,"ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "{\"Name\":\""+nlabel+"\",\"Description\":\""+ndesc+"\",\"DefaultLanguage\":\"en-us\",\"ConsentLifeSpan\":"+lifeSpan+"}";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Map<String, String> getValidatorMap() {
		validatorMap.put("*Name", label);
		validatorMap.put("ID", id);
		validatorMap.put("Status", status);
		return validatorMap;
	}

	@Override
	public void setValidatorMap(Map<String, String> validatorMap) {
		this.validatorMap=validatorMap;
		
	}

	@Override
	public String getName() {
		return label;
	}

	@Override
	public String getUpdateString() {
		return "{"	
				+ "\"Label\":\""+label+"\","
				+ "\"Description\":\""+description+"\","
						+ "\"Status\":\"DRAFT\","
						+ "\"LifeSpan\":"+lifeSpan+"}";

	}
	
	public String getPublishString() {
		String nlabel="";
		try {
			byte sbyte[]=label.getBytes("UTF-8");
			nlabel = new String(sbyte,"ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String ndesc="";
		try {
			byte sbyte[]=description.getBytes("UTF-8");
			ndesc = new String(sbyte,"ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String publishString="{"	
				+ "\"Name\":\""+nlabel+"\","
				+ "\"Description\":\""+ndesc+"\","
				+ "\"DefaultLanguage\":\""+defaultLanguage+"\","
				+ "\"ConsentLifeSpan\":"+lifeSpan+","
				+ "\"Status\":\"DRAFT\","
				+ "\"Version\":"+version+"}";
		System.out.println("Publish string: "+publishString);
		return publishString;

	}

	@Override
	public String getCorrectionFileHeader() {
		return "Id,Current Label,Corrected Label,Current Description,Corrected Description\n";
	}

	@Override
	public String getCorrectionWritableString() {
		String nlabel="";
		try {
			byte sbyte[]=label.getBytes("ISO-8859-1");
			nlabel = new String(sbyte,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String ndesc="";
		try {
			byte sbyte[]=description.getBytes("ISO-8859-1");
			ndesc = new String(sbyte,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id+","+label+","+nlabel+","+description+","+ndesc+"\n";
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
	public Purpose setFileHeader(String fileHeader) {
		this.fileHeader=fileHeader;
		return this;
	}

	@Override
	public Purpose setWritableString(String writeableString) {
		this.writeableString=writeableString;
		return this;
	}

	public int getCollectionPointCount() {
		return collectionPointCount;
	}

	public void setCollectionPointCount(int collectionPointCount) {
		this.collectionPointCount = collectionPointCount;
	}
	public int getActiveDataSubjectCount() {
		return activeDataSubjectCount;
	}

	public void setActiveDataSubjectCount(int activeDataSubjectCount) {
		this.activeDataSubjectCount = activeDataSubjectCount;
	}

	public int getPendingDataSubjectCount() {
		return pendingDataSubjectCount;
	}

	public void setPendingDataSubjectCount(int pendingDataSubjectCount) {
		this.pendingDataSubjectCount = pendingDataSubjectCount;
	}

	public int getWithdrawnDataSubjectCount() {
		return withdrawnDataSubjectCount;
	}

	public void setWithdrawnDataSubjectCount(int withdrawnDataSubjectCount) {
		this.withdrawnDataSubjectCount = withdrawnDataSubjectCount;
	}
	
}

