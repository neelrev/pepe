package com.sde.pepe.gdpr.application.rs.collectionpoint.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.google.gson.annotations.SerializedName;
import com.sde.pepe.gdpr.framework.CRUDAble;
import com.sde.pepe.gdpr.framework.Validated;
import com.sde.pepe.gdpr.framework.Writeable;

public class CollectionPoint implements Writeable<String>,CRUDAble<String>,Validated<String> {

	@SerializedName ("Id")
	private String id;
	
	@SerializedName ("Name")
	private String name;
	
	@SerializedName ("CollectionPointType")
	private CollectionPointType collectionPointType=CollectionPointType.WEB_FORM;
	
	@SerializedName ("ConsentType")
	private ConsentType consentType=ConsentType.FORMSUBMIT;
	
	@SerializedName ("Status")
	private String status="DRAFT";
	
	@SerializedName ("SubjectIdentifier")
	private String subjectIdentifier="Email";
	
	@SerializedName ("CreateDate")
	private String createDate;
	
	@SerializedName ("FirstReceiptOn")
	private String firstReceiptOn;

	@SerializedName ("ReceiptCount")
	private String receiptCount;

	@SerializedName ("Description")
	private String description;

	@SerializedName ("WebFormUrl")
	private String webFormUrl="";

	@SerializedName ("PrivacyPolicyUrl")
	private String privacyPolicyUrl="";
	
	@SerializedName ("DataControllerName")
	private String dataControllerName=null;
	
	@SerializedName ("RightToWithdraw")
	private String rightToWithdraw="You have the right to withdraw your consent at any time";
	
	@SerializedName ("HowToWithdraw")
	private String howToWithdraw=null;

	@SerializedName ("OtherInformation")
	private String otherInformation=null;

	@SerializedName ("ActivationDate")
	private String activationDate;

	@SerializedName ("OrganizationId")
	private String organizationId;

	@SerializedName ("DoubleOptIn")
	private boolean doubleOptIn=false;
	
	@SerializedName ("Version")
	private int version=1;	
	
	private String purposes;
	
	private String purposeNames;
	
	private String organizationName="";
	
	private Map<String,String> validatorMap=new ConcurrentHashMap<String, String>();
	
	private int numberOfPurposes;
	
	private String country;
	
	private String fileHeader;
	
	private String writeableString;
	
	private String missingTouchpoints="";
	
	private String missingPurposes="";
	
	private String apiToken="";
	
	private String domainUrl="";

	@Override
	public String getDefaultFileHeader() {
		return "Id,Name,CollectionPointType,ConsentType,Status,"
				+ "SubjectIdentifier,CreateDate,"
				+ "FirstReceiptOn,ReceiptCount,Description,"
				+ "WebFormUrl,PrivacyPolicyUrl,DataControllerName,RightToWithdraw,HowToWithdraw,"
				+ "OtherInformation,ActivationDate,OrganizationId,DoubleOptIn\n";
	}

	@Override
	public String getDefaultWritableString() {
		return id+","+name+","+collectionPointType+","+consentType+","+status+","
				+subjectIdentifier+","+createDate+","
				+ firstReceiptOn+","+receiptCount+","+description+","
				+ webFormUrl+","+privacyPolicyUrl+","+dataControllerName+","+rightToWithdraw+","+howToWithdraw+","
				+ otherInformation+","+activationDate+","+organizationId+","+doubleOptIn+"\n";
	}
	
	@Override
	public String toString() {
		return "Id: "+id+"| Name: "+name+"| Privacy Policy: "+privacyPolicyUrl+"| Status: "+status+"| Org id: "+organizationId+"| First receipt on: "+firstReceiptOn+"| Receipt count: "+receiptCount;
	}
	
	public CollectionPoint() {
		
	}
	
	public CollectionPoint(String name, String privacyPolicyUrl, String organizationId) {
		this.name=name;
		this.privacyPolicyUrl=privacyPolicyUrl;
		this.organizationId=organizationId;

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

	public CollectionPointType getCollectionPointType() {
		return collectionPointType;
	}

	public void setCollectionPointType(CollectionPointType collectionPointType) {
		this.collectionPointType = collectionPointType;
	}

	public ConsentType getConsentType() {
		return consentType;
	}

	public void setConsentType(ConsentType consentType) {
		this.consentType = consentType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubjectIdentifier() {
		return subjectIdentifier;
	}

	public void setSubjectIdentifier(String subjectIdentifier) {
		this.subjectIdentifier = subjectIdentifier;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getFirstReceiptOn() {
		return firstReceiptOn;
	}

	public void setFirstReceiptOn(String firstReceiptOn) {
		this.firstReceiptOn = firstReceiptOn;
	}

	public String getReceiptCount() {
		return receiptCount;
	}

	public void setReceiptCount(String receiptCount) {
		this.receiptCount = receiptCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebFormUrl() {
		return webFormUrl;
	}

	public void setWebFormUrl(String webFormUrl) {
		this.webFormUrl = webFormUrl;
	}

	public String getPrivacyPolicyUrl() {
		return privacyPolicyUrl;
	}

	public void setPrivacyPolicyUrl(String privacyPolicyUrl) {
		this.privacyPolicyUrl = privacyPolicyUrl;
	}

	public String getDataControllerName() {
		return dataControllerName;
	}

	public void setDataControllerName(String dataControllerName) {
		this.dataControllerName = dataControllerName;
	}

	public String getRightToWithdraw() {
		return rightToWithdraw;
	}

	public void setRightToWithdraw(String rightToWithdraw) {
		this.rightToWithdraw = rightToWithdraw;
	}

	public String getHowToWithdraw() {
		return howToWithdraw;
	}

	public void setHowToWithdraw(String howToWithdraw) {
		this.howToWithdraw = howToWithdraw;
	}

	public String getOtherInformation() {
		return otherInformation;
	}

	public void setOtherInformation(String otherInformation) {
		this.otherInformation = otherInformation;
	}

	public String getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(String activationDate) {
		this.activationDate = activationDate;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public boolean getDoubleOptIn() {
		return doubleOptIn;
	}

	public void setDoubleOptIn(boolean doubleOptIn) {
		this.doubleOptIn = doubleOptIn;
	}

	public void setId(String id) {
		this.id = id;
	}


//{"CollectionPointType":"WEB_FORM","Description":"tada","WebFormUrl":"https://form.com","PrivacyPolicyUrl":"https://google.com/privacy","ConsentType":"FORMSUBMIT","RightToWithdraw":"You have the right to withdraw your consent at any time.","OrganizationId":"6d998625-f7e3-4fe1-8988-59a22a2bf7de","Name":"tada","SubjectIdentifier":"tada","PurposeId":"f317d297-da12-4062-8085-221a2c102887","DataElements":["FirstName","Address"]}
//{"CollectionPointType":"WEB_FORM","Description":"nanaa","WebFormUrl":"https://abc.com","PrivacyPolicyUrl":"
	//https://abc.com/privacy","ConsentType":"OPTINCHECKBOX","RightToWithdraw":"You have the right to withdraw your consent at any time.",
	//"OrganizationId":"550e25b2-d2af-4303-bfc7-fd67c767e34d","Name":"naa","SubjectIdentifier":"name","
	//PurposeId":"ffd62272-6bfc-47ff-8349-8ccfa5950a35,ee066f97-cd38-4f1b-8e6c-8072aa8563ef,8bc4d56e-24f4-44dd-a75d-2ce2438da844,bca6f887-1bb6-48f8-899f-0d04e088886d","DataElements":["Address","Email","Name"]}

	@Override
	public String getCreateString() {
		String nname="";
		try {
			byte sbyte[]=name.getBytes("UTF-8");
			nname = new String(sbyte,"ISO-8859-1");
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
		
		String nPrivacyPolicyUrl="";
		 if(StringUtils.isNotEmpty(privacyPolicyUrl))
			try {
				nPrivacyPolicyUrl=URLEncoder.encode(privacyPolicyUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 String nWebFormUrl="";
		 if(StringUtils.isNotEmpty(webFormUrl))
			try {
				nWebFormUrl=URLEncoder.encode(webFormUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
		return "{\"CollectionPointType\":\""+collectionPointType+"\",\"Description\":\""+ndesc+"\",\""
				+ "WebFormUrl\":\""+nWebFormUrl+"\",\"PrivacyPolicyUrl\":\""+nPrivacyPolicyUrl+"\",\""
				+ "ConsentType\":\""+consentType+"\",\"RightToWithdraw\":\""+rightToWithdraw+"\",\""
				+ "OrganizationId\":\""+organizationId+"\",\"Name\":\""+nname+"\",\"SubjectIdentifier\":\""+subjectIdentifier+"\",\""
				+ "PurposeId\":\""+purposes+"\",\""
				+ "DataElements\":[\"Email\"]}";
	}
	
	public String getUpdateString() {
		String nname="";
		try {
			byte sbyte[]=name.getBytes("UTF-8");
			nname = new String(sbyte,"ISO-8859-1");
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
		
		String nPrivacyPolicyUrl="";
		 if(StringUtils.isNotEmpty(privacyPolicyUrl))
			try {
				nPrivacyPolicyUrl=URLEncoder.encode(privacyPolicyUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 String nWebFormUrl="";
		 if(StringUtils.isNotEmpty(webFormUrl))
			try {
				nWebFormUrl=URLEncoder.encode(webFormUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		
		return "{\"CollectionPointType\":\""+collectionPointType+"\",\"Description\":\""+ndesc+"\",\""
				+ "WebFormUrl\":\""+nWebFormUrl+"\",\"PrivacyPolicyUrl\":\""+nPrivacyPolicyUrl+"\",\""			
				+ "Name\":\""+nname+"\",\"DoubleOptIn\":"+doubleOptIn+",\""
				+ "SubjectIdentifier\":\""+subjectIdentifier+"\",\"ConsentType\":\""+consentType+"\",\"Status\":\""+status+"\",\""
				+ "OrganizationId\":\""+organizationId+"\",\""
				+ "PurposeId\":\""+purposes+"\",\""
				+ "DataControllerName\":"+dataControllerName+",\""
				+ "RightToWithdraw\":\""+rightToWithdraw+"\",\""
				+ "HowToWithdraw\":"+howToWithdraw+",\""
				+ "OtherInformation\":"+otherInformation+",\""
				+ "DataElements\":[\"Email\"]}";
	}

	public String getPurposes() {
		return purposes;
	}

	public void setPurposes(String purposes) {
		this.purposes = purposes;
	}

	public String getPurposeNames() {
		return purposeNames;
	}

	public void setPurposeNames(String purposeNames) {
		this.purposeNames = purposeNames;
	}

	@Override
	public Map<String, String> getValidatorMap() {
		validatorMap.put("Number of Purposes", new Integer(numberOfPurposes).toString());
		validatorMap.put("*Name", name);
		validatorMap.put("*Organization Name", organizationName);
		validatorMap.put("ID", id);
		validatorMap.put("Organization Id", organizationId);
		validatorMap.put("Status", status);
		validatorMap.put("Privacy Policy", privacyPolicyUrl);
		return validatorMap;
	}

	@Override
	public void setValidatorMap(Map<String, String> validatorMap) {
		this.validatorMap=validatorMap;
		
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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
	public CollectionPoint setFileHeader(String fileHeader) {
		this.fileHeader=fileHeader;
		return this;
	}

	@Override
	public CollectionPoint setWritableString(String writeableString) {
		this.writeableString=writeableString;
		return this;
	}

	public int getNumberOfPurposes() {
		return numberOfPurposes;
	}

	public void setNumberOfPurposes(int numberOfPurposes) {
		this.numberOfPurposes = numberOfPurposes;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMissingTouchpoints() {
		return missingTouchpoints;
	}

	public void setMissingTouchpoints(String missingTouchpoints) {
		this.missingTouchpoints = missingTouchpoints;
	}

	public String getMissingPurposes() {
		return missingPurposes;
	}

	public void setMissingPurposes(String missingPurposes) {
		this.missingPurposes = missingPurposes;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}	
}
