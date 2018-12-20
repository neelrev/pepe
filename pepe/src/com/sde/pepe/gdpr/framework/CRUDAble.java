package com.sde.pepe.gdpr.framework;

import java.io.Serializable;

public interface CRUDAble <ID extends Serializable>{
	
	public abstract ID getCreateString();
	public abstract ID getUpdateString();

}
