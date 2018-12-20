package com.sde.pepe.gdpr.application.rs.collectionpoint.transform.impl;

import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPoint;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.CollectionPointType;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.ConsentType;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class CollectionPointTransformer implements Transformer<CSVRecord, CollectionPoint> {

	@Override
	public CollectionPoint getEntity(CSVRecord source) {
		CollectionPoint collectionPoint = new CollectionPoint();
		collectionPoint.setId(source.get("Id"));
		collectionPoint.setName(source.get("Name"));
		collectionPoint.setOrganizationId(source.get("OrganizationId"));
		collectionPoint.setPrivacyPolicyUrl(source.get("PrivacyPolicyUrl"));
		collectionPoint.setStatus(source.get("Status"));
		return collectionPoint;
	}

	@Override
	public CollectionPoint getEntity(CSVRecord source, CustomReadHeader readHeader) {
		CollectionPoint collectionPoint = new CollectionPoint();

		//Read: Just check if Pepe updated/deleted cps properly
		if(readHeader == CustomReadHeader.COLLECTIONPOINT_CHECK_DELETE) {
			collectionPoint.setId(source.get("Collection Point ID"));
			collectionPoint.setName(source.get("Collection Point Name"));
		}

		//Read: Just check if Pepe has renamed the collection points properly
		if(readHeader == CustomReadHeader.COLLECTIONPOINT_CHECK_RENAME) {
			collectionPoint.setId(source.get("Collection Point ID"));
			collectionPoint.setName(source.get("New Collection Point Name"));
		}

		//Read: Just check if Pepe has moved the collection points under the proper orgs
		if(readHeader == CustomReadHeader.COLLECTIONPOINT_CHECK_MOVE) {
			collectionPoint.setId(source.get("Collection Point ID"));
			collectionPoint.setName(source.get("Collection Point Name"));
			collectionPoint.setOrganizationId(source.get("New Organization ID"));
			collectionPoint.setOrganizationName(source.get("New Organization Name"));
		}

		// Default: "CollectionPointType", "OrganizationId",
		// "Name","Description","WebFormURL",
		// "PrivacyPolicyURL","SubjectIdentifier","ConsentInteractionType","Purposes"
		if (readHeader == CustomReadHeader.COLLECTIONPOINT_CREATE) {

			String collectionPointType = source.get("CollectionPointType");
			if (StringUtils.isNotEmpty(collectionPointType))
				collectionPoint.setCollectionPointType(CollectionPointType.valueOf(collectionPointType));

			collectionPoint.setDomainUrl(source.get("DomainUrl"));
			collectionPoint.setOrganizationId(source.get("OrganizationId"));
			collectionPoint.setName(source.get("Name"));
			collectionPoint.setDescription(source.get("Description"));
			collectionPoint.setWebFormUrl(source.get("WebFormURL"));
			collectionPoint.setPrivacyPolicyUrl(source.get("PrivacyPolicyURL"));
			collectionPoint.setSubjectIdentifier(source.get("SubjectIdentifier"));

			String consentType = source.get("ConsentInteractionType");
			if (StringUtils.isNotEmpty(consentType))
				collectionPoint.setConsentType(ConsentType.valueOf(consentType));

			collectionPoint.setPurposeNames(source.get("Purposes"));
			return collectionPoint;
		}
		// "CollectionPointType", "OrganizationName", "Name","Description","WebFormURL",
		// "PrivacyPolicyURL","SubjectIdentifier","ConsentInteractionType","Purposes"

		if (readHeader == CustomReadHeader.COLLECTIONPOINT_CREATE_VARIATION) {

			String collectionPointType = source.get("CollectionPointType");
			if (StringUtils.isNotEmpty(collectionPointType))
				collectionPoint.setCollectionPointType(CollectionPointType.valueOf(collectionPointType));

			collectionPoint.setDomainUrl(source.get("DomainUrl"));

			collectionPoint.setOrganizationName(source.get("OrganizationName"));
			collectionPoint.setName(source.get("Name"));
			collectionPoint.setDescription(source.get("Description"));
			collectionPoint.setWebFormUrl(source.get("WebFormURL"));
			collectionPoint.setPrivacyPolicyUrl(source.get("PrivacyPolicyURL"));
			collectionPoint.setSubjectIdentifier(source.get("SubjectIdentifier"));

			String consentType = source.get("ConsentInteractionType");
			if (StringUtils.isNotEmpty(consentType))
				collectionPoint.setConsentType(ConsentType.valueOf(consentType));

			collectionPoint.setPurposeNames(source.get("Purposes"));
			return collectionPoint;
		}
		if (readHeader == CustomReadHeader.COLLECTIONPOINT_CREATE_VARIATION_2) {

			String collectionPointType = source.get("CollectionPointType");
			if (StringUtils.isNotEmpty(collectionPointType))
				collectionPoint.setCollectionPointType(CollectionPointType.valueOf(collectionPointType));

			collectionPoint.setId(source.get("Id"));
			collectionPoint.setOrganizationName(source.get("OrganizationName"));
			collectionPoint.setName(source.get("Name"));
			collectionPoint.setDescription(source.get("Description"));
			collectionPoint.setWebFormUrl(source.get("WebFormURL"));
			collectionPoint.setPrivacyPolicyUrl(source.get("PrivacyPolicyURL"));
			collectionPoint.setSubjectIdentifier(source.get("SubjectIdentifier"));

			String consentType = source.get("ConsentInteractionType");
			if (StringUtils.isNotEmpty(consentType))
				collectionPoint.setConsentType(ConsentType.valueOf(consentType));

			collectionPoint.setPurposeNames(source.get("Purposes"));
			return collectionPoint;
		}

		if (readHeader == CustomReadHeader.COLLECTIONPOINT_REST) {
			System.out.println(source.get("Id"));
			collectionPoint.setId(source.get("Id"));
			collectionPoint.setName(source.get("Name"));
			String collectionPointType = source.get("CollectionPointType");
			if (StringUtils.isNotEmpty(collectionPointType))
				collectionPoint.setCollectionPointType(CollectionPointType.valueOf(collectionPointType));
			String consentType = source.get("ConsentType");
			if (StringUtils.isNotEmpty(consentType))
				collectionPoint.setConsentType(ConsentType.valueOf(consentType));

			collectionPoint.setStatus(source.get("Status"));
			collectionPoint.setSubjectIdentifier(source.get("SubjectIdentifier"));
			collectionPoint.setCreateDate(source.get("CreateDate"));
			collectionPoint.setFirstReceiptOn(source.get("FirstReceiptOn"));
			collectionPoint.setReceiptCount(source.get("ReceiptCount"));
			collectionPoint.setDescription(source.get("Description"));
			collectionPoint.setWebFormUrl(source.get("WebFormURL"));
			collectionPoint.setPrivacyPolicyUrl(source.get("PrivacyPolicyURL"));

			collectionPoint.setDataControllerName(source.get("DataControllerName"));
			collectionPoint.setRightToWithdraw(source.get("RightToWithdraw"));
			collectionPoint.setHowToWithdraw(source.get("HowToWithdraw"));
			collectionPoint.setOtherInformation(source.get("OtherInformation"));
			collectionPoint.setActivationDate(source.get("ActivationDate"));
			collectionPoint.setOrganizationId(source.get("OrganizationId"));
			collectionPoint.setDoubleOptIn(Boolean.getBoolean(source.get("DoubleOptIn")));
			return collectionPoint;

		}

		return collectionPoint;
	}

	@Override
	public List<CollectionPoint> getEntities(CSVRecord source, CustomReadHeader readHeader) {
		// TODO Auto-generated method stub
		return null;
	}
}
