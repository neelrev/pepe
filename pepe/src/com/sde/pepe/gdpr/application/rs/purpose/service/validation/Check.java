package com.sde.pepe.gdpr.application.rs.purpose.service.validation;


import java.util.List;

import com.sde.pepe.gdpr.application.rs.collectionpoint.CollectionPointExportManager;
import com.sde.pepe.gdpr.application.rs.collectionpoint.service.validation.CollectionPointPurposeCheck;
import com.sde.pepe.gdpr.application.rs.collectionpoint.vo.Referential;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.framework.util.BaseCheck;

public class Check extends BaseCheck<Purpose>{
	

	public static String[] touchpoints= {"Alerts / Notifications","Beauty Profile","Email Marketing",""
			+ "Group Marketing","Lottery","Phone Marketing","Postal Marketing",""
					+ "Profiling Marketing","Rating & reviews","Sampling pages",""
							+ "SMS Marketing","Users Right Management","Term of use",""
									+ "Terms & conditions","Child","Contest Games","User generated consent","Contact us"};
	private List<Referential> referentials;
	
	private CollectionPointPurposeCheck cpCheck;

	public Check(List<Purpose> sources) {
		super(sources);
		
		CollectionPointExportManager manager=new CollectionPointExportManager();
		referentials=manager.extractCollectionPointPurposes();	
		this.cpCheck=new CollectionPointPurposeCheck(referentials);

	}

	protected boolean checkName(Purpose source) {
		return checkName(source, touchpoints);
		
		//Check All the collection have the name of the touchpoint : 
	}//checkName
	
	//Check all the associated collection points have the same CC, Brand and Divisions as the purpose
	protected boolean checkCollectionPoint(Purpose source) {
		String id=source.getId();
		 for(Referential referential:referentials) {
			 if(id.equals(referential.getPurposeId())) {
				 if (!cpCheck.checkCCDivisionBrand(referential))
					 return false;
			 }//if
		 }//for		
		return true;
	}//checkCollectionPoint
	
	
}//Check
