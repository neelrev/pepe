package com.sde.pepe.gdpr.framework;

import java.io.Serializable;
import java.util.Map;

import com.sde.pepe.gdpr.framework.entity.Identifiable;

public interface Validated <ID extends Serializable> extends Identifiable<ID>{
	
	public abstract Map<ID,ID> getValidatorMap();
	public abstract void setValidatorMap(Map<ID,ID> validatorMap);
}
