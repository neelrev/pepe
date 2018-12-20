package com.sde.pepe.gdpr.application.rs.organization.service.validation;

import java.util.Hashtable;
import java.util.List;

import com.sde.pepe.gdpr.application.rs.organization.vo.Organization;
import com.sde.pepe.gdpr.framework.util.BaseCheck;

public class Check extends BaseCheck<Organization> {
	
	private Hashtable<String,String> childParentMappings;

	public Check(List<Organization> sources) {
		super(sources);
		this.childParentMappings=getChildParentMappings();
	}

	@Override
	public boolean checkDuplicate(Organization source) {
				
		String name=sanitizeStr(source.getName());
		String parentId=source.getParentId();
		
		List<Organization> modifiedSources=getModifiedSources(source);
		
		 for(Organization entry:modifiedSources) {
			 if((name.equals(sanitizeStr(entry.getName())))&&(parentId.equals(entry.getParentId())))
				 return false;
		 }
		
		return true;
	}//checkDuplicate
	
	private Hashtable<String,String> getChildParentMappings(){
		childParentMappings=new Hashtable<String,String>();
		
		for(Organization source:sources) {
			childParentMappings.put(source.getId(), source.getParentId());
		}
		
		return childParentMappings;		
	}//getNames

}
