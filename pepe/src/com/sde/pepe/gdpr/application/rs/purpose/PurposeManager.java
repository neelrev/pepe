package com.sde.pepe.gdpr.application.rs.purpose;

import static com.sde.pepe.gdpr.application.rs.BaseProperties.purposeFilePath;
import static com.sde.pepe.gdpr.application.rs.CRUDTypes.CREATE;
import static com.sde.pepe.gdpr.application.rs.CRUDTypes.UPDATE;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.PURPOSE_CREATE;
import static com.sde.pepe.gdpr.application.rs.CustomReadHeader.PURPOSE_UPDATE;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.resting.Resting;
import com.sde.pepe.gdpr.application.rs.CRUDTypes;
import com.sde.pepe.gdpr.application.rs.purpose.read.ReadPurpose;
import com.sde.pepe.gdpr.application.rs.purpose.service.PurposeService;
import com.sde.pepe.gdpr.application.rs.purpose.vo.Purpose;
import com.sde.pepe.gdpr.application.rs.response.Response;
import com.sde.pepe.gdpr.application.rs.token.TokenManager;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;
import com.sde.pepe.gdpr.application.rs.uri.URITypes;
import com.sde.pepe.gdpr.framework.Writeable;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;


public class PurposeManager {

	private String domain;
	private int numberOfPages;
	private TokenManager tokenManager;

	public PurposeManager() {

	}

	public PurposeManager(String domain,int numberOfPages) {
		this.domain=domain;
		this.numberOfPages=numberOfPages;
		this.tokenManager=new TokenManager(domain);
	}

	private String getPurposeUrl() {
		return domain+URITypes.SEARCHPURPOSES.getUri();
	}

	public ResponseToken createPurpose(ResponseToken responseToken, Purpose purpose) {
		PurposeService purposeService=new PurposeService();
		return purposeService.createPurpose(responseToken, purpose);
	}

	public ResponseToken updatePurpose(ResponseToken responseToken, Purpose purpose) {
		PurposeService purposeService=new PurposeService();
		return purposeService.updatePurpose(responseToken, purpose);
	}

	public ResponseToken publishPurpose(ResponseToken responseToken, Purpose purpose) {
		PurposeService purposeService=new PurposeService();
		return purposeService.publishPurpose(responseToken, purpose);
	}

	public List<Writeable> getPurposes(ResponseToken oldResponseToken) {
		List<Writeable> purposes=new ArrayList<Writeable>();

		for(int i=0;i<numberOfPages;i++) {
			ResponseToken responseToken=tokenManager.getNewResponseToken(oldResponseToken);
			String transactionUrl=getPurposeUrl()+"?page="+i+"&size=20&sort=Id,desc";
			List<Header> headers=tokenManager.getBaseHeaders();
			headers.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
			purposes.addAll(Resting.getByJSON(transactionUrl, 443, null, Purpose.class, "content", null, headers));
			System.out.println(purposes.size());
			oldResponseToken=responseToken;
		}//for

		return purposes;
	}//getPurposes


	public List<Purpose> extractPurposes(String filePath, CRUDTypes crudType){
		ReadPurpose read=new ReadPurpose<>();
		List<Purpose> purposes=null;

		switch(crudType) {
			case CREATE:
				purposes=read.read(new FileEntity(filePath),PURPOSE_CREATE);
				return purposes;
			case UPDATE:
				purposes=read.read(new FileEntity(filePath),PURPOSE_UPDATE);
				return purposes;
		}
		return purposes;
	}

	public void exportIntoFile() {
		PurposeService purposeService=new PurposeService();
		purposeService.exportPurposes(purposeFilePath);
	}//exportIntoFile

	public void exportCorruptRecords(String filePath) {
		PurposeService purposeService=new PurposeService();
		purposeService.exportCorruptPurposes(filePath);
	}//exportCorruptRecords


	public void readAndCreatePurposes(String filePath) {
		List<Purpose> purposes=extractPurposes(filePath,CREATE);
		ResponseToken token=null;
		 for(Purpose purpose:purposes) {
			 token=createPurpose(token,purpose);
		 }//for
	}//readAndCreatePurposes

	public void readAndUpdatePurposes(String filePath) {
		List<Purpose> purposes=extractPurposes(filePath,UPDATE);
		ResponseToken token=null;
		 for(Purpose purpose:purposes) {
			 token=updatePurpose(token,purpose);
		 }//for
	}//readAndUpdatePurposes

	public Response<Purpose> getPurpose(String id,ResponseToken responseToken){
		PurposeService service=new PurposeService();
		return service.getPurpose(id, responseToken);
	}

	public static void main(String[] args) {
		String csvFilePath="C:\\work\\user records\\records\\purposes.csv";

		PurposeManager manager=new PurposeManager();
		//Purpose purpose=new Purpose("Test | L'Oréal Paris","Test | L'Oréal Paris");
		//manager.createPurpose(null, purpose);
		//manager.readAndCreatePurposes(csvFilePath);

		//manager.readAndUpdatePurposes(csvFilePath);
		manager.exportIntoFile();
		//manager.exportCorruptRecords(csvFilePath);
	}

}
