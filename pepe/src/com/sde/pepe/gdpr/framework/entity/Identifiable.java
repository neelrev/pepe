package com.sde.pepe.gdpr.framework.entity;

import java.io.Serializable;
import org.apache.commons.lang.ObjectUtils;


public interface Identifiable <ID extends Serializable>{
	
	public abstract ID getId();
	
	public abstract ID getName();
	
	public static class HashKey<T extends Identifiable<?>>{
		private final T ident;
		public HashKey(final T ident){
			assert ident!=null: "";
			this.ident=ident;			
		}//HashKey
		
		public T unwrap(){
			return ident;
		}//unwrap
		
		@Override
		public boolean equals(final Object obj){
			if(obj instanceof HashKey<?>){
				     return ObjectUtils.equals(ident.getId(), ((HashKey<?>)obj).unwrap().getId());			
			}else{
				return false;
			}//if
			
		}//equals
		
		@Override
		public int hashCode(){
			return ObjectUtils.hashCode(ident.getId());
		}
		
		@Override
		public String toString(){
			return "HashKey("+ident+")";
		}
		
	}
	

}

