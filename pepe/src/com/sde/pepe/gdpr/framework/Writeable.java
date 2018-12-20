package com.sde.pepe.gdpr.framework;

import java.io.Serializable;

public interface Writeable <ID extends Serializable>{
	
	public abstract ID getDefaultFileHeader();
	public abstract ID getDefaultWritableString();

	public abstract ID getFileHeader();
	public abstract ID getWritableString();
	
	public abstract Writeable setFileHeader(String fileHeader);
	public abstract Writeable setWritableString(String writeableString);
	
}
