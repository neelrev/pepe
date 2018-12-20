package com.sde.pepe.gdpr.application.collectionpoint.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.sde.pepe.gdpr.framework.entity.Identifiable;

public class CollectionPoint implements Comparable<CollectionPoint>, Identifiable{
	private String id;
	private String name;
	private String type;
	private String consentInteractionType;
	private String doubleOptIn;
	private String status;
	private String uniqueSubjectId;
	private String createdOn;
	private String firstReceiptOn;
	private int numberOfReceipts;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getConsentInteractionType() {
		return consentInteractionType;
	}
	public void setConsentInteractionType(String consentInteractionType) {
		this.consentInteractionType = consentInteractionType;
	}
	public String getDoubleOptIn() {
		return doubleOptIn;
	}
	public void setDoubleOptIn(String doubleOptIn) {
		this.doubleOptIn = doubleOptIn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUniqueSubjectId() {
		return uniqueSubjectId;
	}
	public void setUniqueSubjectId(String uniqueSubjectId) {
		this.uniqueSubjectId = uniqueSubjectId;
	}

	
	public int getNumberOfReceipts() {
		return numberOfReceipts;
	}
	public void setNumberOfReceipts(int numberOfReceipts) {
		this.numberOfReceipts = numberOfReceipts;
	}


	@Override
	public int compareTo(CollectionPoint bean) {
		if(bean!=null) {
			CompareToBuilder compareToBuilder= new CompareToBuilder()
					.append(name, bean.getName())
					.append(type, bean.getType())
					.append(consentInteractionType, bean.getConsentInteractionType())
					.append(doubleOptIn, bean.getDoubleOptIn())
					.append(status, bean.getStatus())
					.append(uniqueSubjectId, bean.getUniqueSubjectId())
		//			.append(createdOn, bean.getCreatedOn())
		//			.append(firstReceiptOn, bean.getFirstReceiptOn())
		//			.append(numberOfReceipts, bean.getNumberOfReceipts())
			;
			return compareToBuilder.toComparison();				
		}else
			return 1;
		//if
	}//compareTo
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getFirstReceiptOn() {
		return firstReceiptOn;
	}
	public void setFirstReceiptOn(String firstReceiptOn) {
		this.firstReceiptOn = firstReceiptOn;
	}
	
	@Override
	public String toString() {
		return "Name: "+name+"| Type: "+type+"| Consent Interaction Type: "+consentInteractionType+"| Double Opt-In: "+doubleOptIn+"| Status: "+status+"| Unique Subject Id: "+uniqueSubjectId;
	}
	@Override
	public Serializable getId() {
		return id;
	}

}//CollectionPoint
