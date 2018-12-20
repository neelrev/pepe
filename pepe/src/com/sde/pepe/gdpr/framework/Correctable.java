package com.sde.pepe.gdpr.framework;

import java.io.Serializable;

public interface Correctable <ID extends Serializable>{
	
	public abstract ID getCorrectionFileHeader();
	public abstract ID getCorrectionWritableString();

}
