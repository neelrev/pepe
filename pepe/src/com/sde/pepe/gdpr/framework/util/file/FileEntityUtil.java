package com.sde.pepe.gdpr.framework.util.file;

import com.sde.pepe.gdpr.constants.Environment;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;

/**
 * FileEntity util
 * 
 * @author sujata_d
 *
 */

public class FileEntityUtil {
	
	public static FileEntity getUATFileEntity(String filepath) {
		return new FileEntity(filepath,Environment.UAT);
	}
	
	public static FileEntity getPRODFileEntity(String filepath) {
		return new FileEntity(filepath,Environment.PROD);
	}

}
