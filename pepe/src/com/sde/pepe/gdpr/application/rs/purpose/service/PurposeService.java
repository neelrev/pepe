package com.sde.pepe.gdpr.application.rs.purpose.service;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.firstRefreshToken;
import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeCorruptRecordsInCSVFile;
import static com.sde.pepe.gdpr.framework.util.file.csv.WriteUtil.writeRecordsInCSVFile;

import java.util.List;

import com.sde.pepe.gdpr.application.rs.BaseService;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;

public class PurposeService extends BaseService<Purpose>{
	
	private Export export;
	private Create create;
	private Update update;
	private ResponseToken firstToken;
	
	public PurposeService() {
		super(Purpose.class);
		this.create=new Create();
		this.export=new Export();
		this.update=new Update();
		this.firstToken=new ResponseToken(firstRefreshToken);
	}
	
	public Response<List<Purpose>> searchPurpose(ResponseToken responseToken, String name) {
		if(responseToken==null)
			responseToken=firstToken;
		
		return export.searchPurpose(name, responseToken);	
	}
	
	public ResponseToken createPurpose(ResponseToken responseToken, Purpose purpose) {
		if(responseToken==null)
			responseToken=firstToken;
		return create.createPurpose(responseToken, purpose);
	}
	
	public ResponseToken updatePurpose(ResponseToken responseToken, Purpose purpose) {
		if(responseToken==null)
			responseToken=firstToken;
		return update.updatePurpose(responseToken, purpose);
	}

	public ResponseToken publishPurpose(ResponseToken responseToken, Purpose purpose) {
		if(responseToken==null)
			responseToken=firstToken;
		return update.publishPurpose(responseToken, purpose);
	}
	
	public Response<Purpose> getPurpose(String id,ResponseToken responseToken){
		if(responseToken==null)
			responseToken=firstToken;
		return export.getPurpose(id,responseToken);
	}
	
	public void exportPurposes(String csvFilePath) {
		writeRecordsInCSVFile(csvFilePath, export.getPurposes(firstToken),true);	
	}//exportPurposes
		
	public void exportCorruptPurposes(String csvFilePath) {
		writeCorruptRecordsInCSVFile(csvFilePath, export.getCorruptPurposes(firstToken));	
	}//exportCorruptPurposes
}
