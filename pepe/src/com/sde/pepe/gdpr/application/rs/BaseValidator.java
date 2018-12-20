package com.sde.pepe.gdpr.application.rs;

public interface BaseValidator<T> {
	
	public boolean validate(T source);

}
