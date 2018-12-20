package com.sde.pepe.gdpr.framework.entity.impl;

import java.io.File;

import com.sde.pepe.gdpr.constants.Environment;

public class FileEntity {
	private File f;
	private Environment environment;
	
	public FileEntity(String filepath, Environment environment) {
		this.f=new File(filepath);
		this.environment=environment;
	}
	
	public FileEntity(String filepath) {
		this.f=new File(filepath);
		this.environment=Environment.ALL;
	}

	public File getF() {
		return f;
	}

	public void setF(File f) {
		this.f = f;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
