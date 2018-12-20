package com.sde.pepe.gdpr.application.rs.organization;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.organizationFilePath;
import static com.sde.pepe.gdpr.application.rs.BaseProperties.organizationValidationFilePath;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.ORGANIZATION_REST;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.organization.read.ReadOrganization;
import com.sde.pepe.gdpr.application.rs.organization.service.validation.OrganizationValidationService;
import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

public class OrganizationValidationManager {
	public void validateExport() {
		ReadOrganization<Organization> read=new ReadOrganization<Organization>();
		List<Organization> organizations=read.read(new FileEntity(organizationFilePath),ORGANIZATION_REST);
		
		OrganizationValidationService organizationValidationService=new OrganizationValidationService(organizations);
		organizationValidationService.validateExport(organizationValidationFilePath);
	}
	
	public static void main(String[] args) {
		OrganizationValidationManager manager=new OrganizationValidationManager();
		manager.validateExport();
	}

}
