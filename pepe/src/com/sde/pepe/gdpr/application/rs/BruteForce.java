package com.sde.pepe.gdpr.application.rs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;
import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.google.resting.component.impl.ServiceResponse;
import com.google.resting.method.put.PutHelper;
import com.sde.pepe.gdpr.application.rs.token.vo.ResponseToken;

public class BruteForce {

	static String cfduid="dc6e2eab21a7f06e432da0035b2a6748c1528802510";
	static String arrAffinity="a5a342a80bda39eee708a0eb644abfcc337cf45e3ce2944cd9d9546b32f783ef";
	static String url="https://beta.pepe.com/api/v1/private/CollectionPoint/UpdateCollectionPoint/28d201f1-6182-437e-a537-da2f8a1f378c";
	static String payload="{\"CollectionPointType\":\"WEB_FORM\",\"Description\":\"testing\",\"WebFormUrl\":\"test\","
			+ "\"PrivacyPolicyUrl\":\"https://google:fr\","
			+ "\"Name\":\"contact us uk\",\"DoubleOptIn\":false,"
			+ "\"SubjectIdentifier\":\"customer\",\"ConsentType\":\"OPTINCHECKBOX\","
			+ "\"Status\":\"DRAFT\","
			+ "\"OrganizationId\":\"b0300299-1be6-4c27-a74f-8c77721eda5b\","
			+ "\"PurposeId\":\"5818e381-541d-4d0f-89f8-3fc57fa7e38c\",\"DataControllerName\":null,"
			+ "\"RightToWithdraw\":\"You have the right to withdraw your consent at any time.\","
			+ "\"HowToWithdraw\":null,\"OtherInformation\":null,\"DataElements\":[]}";
	static String firstRefreshToken="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ2aXJ0dWFsUm9sZSI6ZmFsc2UsInJvbGUiOiJTaXRlIEFkbWluIiwidXNlcl9uYW1lIjoic3VqYXRhLmRlQHBob3RvbmluZm90ZWNoLm5ldCIsImxhbmd1YWdlSWQiOjEsInNlc3Npb25JZCI6IjAzMmZiYzE0LWNmYjUtNGY2Ny04ZTZjLTU1MzRkOTljMTY5ZSIsInRlbmFudEd1aWQiOiI1MzU1MGFjZC04ZDNlLTQ3YzMtYjRmOC1mYmU2MjgwZjIwMjQiLCJhdXRob3JpdGllcyI6WyJTaXRlIEFkbWluIl0sImNsaWVudF9pZCI6Im9uZXRydXN0Iiwib3JnR3JvdXBJZCI6IjEiLCJzY29wZSI6WyJyZWFkIl0sInJvb3QiOiJiMDMwMDI5OS0xYmU2LTRjMjctYTc0Zi04Yzc3NzIxZWRhNWIiLCJhdGkiOiJlOTMzYzM4YS0wNmI1LTRjYjMtOWQzZS03YmQ2MWE2NTE3MzgiLCJ0ZW5hbnRJZCI6OSwiZ3VpZCI6IjAyYTg2NTVjLTc5NGItNDE5Ny05NTVmLWZhNzAwZWNiZTFmYSIsImRvTm90RGVsZXRlIjpmYWxzZSwiZXhwIjoxNTI4ODgyNzAxLCJqdGkiOiIzMjFiMzRjZi0zOTNkLTQ4NmUtYjY3NS1mNjZjYzFlMWFlMDMiLCJlbWFpbCI6InN1amF0YS5kZUBwaG90b25pbmZvdGVjaC5uZXQifQ.I03nzb32l-GSaQ-soJHBatgLsrHb_KLNez5tsnPxAO6K5wJ8HebPOxkK8JLse0kM_AifRKwWTN6qLXnpzEflps_BzaswZXUhdpWF6o0YhLADRU6uLmWNZ2vGQKLVrX87BPYN-Rb07VugnlGfznYsHu324P-MRy5BiSQal6Ps_oMgzc3ToMVUMUfhIZ91b5N2N9PzKHCn5evGsDkihYgykXMVnuKWLOUDMTfJF6XLpOlji4fycA_pZZiQTZPUVTbOyUz_eWRPCYZd-KfMvFUyy3hY6eebLXanaBOzEGKSnx4Z4Oz-KJ5vbnvuN1fOZxXLSIAc6Q17C6vv2pQFb5NXIw"
;

	public static void main(String[] args) {
		//Get refresh token
		List<Header> baseHeaders=new ArrayList<Header>();
		baseHeaders.add(new BasicHeader("__cfduid",cfduid));
		baseHeaders.add(new BasicHeader("ARRAffinity",arrAffinity));

		String messageToPost="grant_Type=refresh_token&client_id=pepe&refresh_token="+firstRefreshToken;
		ServiceResponse response=Resting.post("https://beta.pepe.com/api/v1/private/token", 443, null, messageToPost, null, baseHeaders, null);
		System.out.println(response);

		Gson gson=new Gson();
		ResponseToken responseToken=gson.fromJson(response.getResponseString(), ResponseToken.class);

		System.out.println(responseToken);

		//Execute put
		 baseHeaders.add(new BasicHeader(("Authorization"),"Bearer "+responseToken.getAccessToken()));
		 baseHeaders.add(new BasicHeader("Accept","application/json, text/plain, */*"));
		 baseHeaders.add(new BasicHeader("Accept-Encoding","gzip, deflate, br"));
		 baseHeaders.add(new BasicHeader("Accept-Language","en-US,en;q=0.9"));
		 baseHeaders.add(new BasicHeader("Content-Type","application/json"));
		 baseHeaders.add(new BasicHeader("Cookie","__cfduid=dc6e2eab21a7f06e432da0035b2a6748c1528802510; ARRAffinity=a5a342a80bda39eee708a0eb644abfcc337cf45e3ce2944cd9d9546b32f783ef"));

		 ServiceResponse serviceResponse=PutHelper.put(url, 443, payload, null, baseHeaders, null);
		 System.out.println(serviceResponse);
	}

}
