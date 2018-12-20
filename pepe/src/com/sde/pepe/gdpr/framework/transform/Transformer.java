package com.sde.pepe.gdpr.framework.transform;





import java.util.List;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;



/**
 * Base interface for transformer
 * 
 * @author de.sujata
 *
 * @param <T> source type
 * @param <U> destination type
 */

public interface Transformer<T,U> {
	public U getEntity(T source);
	public U getEntity(T source,CustomReadHeader readHeader);
	public List<U> getEntities(T source,CustomReadHeader readHeader);
}
