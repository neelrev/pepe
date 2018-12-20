package com.sde.pepe.gdpr.application.rs.monitoring;

import com.google.resting.component.impl.ServiceResponse;
import com.sde.pepe.gdpr.application.rs.monitoring.scanner.integration.impl.IntegrationScanner;

import java.util.Scanner;

public class Monitor {
	
	private void execute(String url,String privacyPolicyUrl,String tncUrl) {
		scanIntegration(url,privacyPolicyUrl,tncUrl);		
	}
	
	private void scanIntegration(String url, String privacyPolicyUrl, String tncUrl) {
		System.out.println(" -------- Monitor Reports for "+url+" ----------");
		IntegrationScanner integrationScanner=new IntegrationScanner();
		ServiceResponse response=integrationScanner.scan(url);
		integrationScanner.validateResponse(response,privacyPolicyUrl,tncUrl);			
		
		System.out.println(" \n\n-------- End of Reports ----------");
	}
	
	public static void main(String[] args) {
		Monitor monitor=new Monitor();
		while(true) {
			System.out.println("Enter the name of the website for monitoring:");
			System.out.println("\n");
			Scanner scanner=new Scanner(System.in);
			String url = scanner.nextLine();
			System.out.println("Enter Link to Privacy Policy:");
			System.out.println("\n");
			scanner=new Scanner(System.in);
			String privacyPolicyUrl = scanner.nextLine();
			System.out.println("Enter Link to Terms and Conditions:");
			System.out.println("\n");
			scanner=new Scanner(System.in);
			String tncUrl = scanner.nextLine();
			System.out.println("\n\n");
			monitor.execute(url,privacyPolicyUrl,tncUrl);
			System.out.println("\n\n\n");
			
		}
		
	}

}
