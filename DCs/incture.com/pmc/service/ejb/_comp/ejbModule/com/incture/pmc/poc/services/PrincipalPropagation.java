//package com.incture.pmc.poc.services;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import javax.ejb.Stateless;
//import javax.jws.WebService;
//import javax.servlet.http.Cookie;
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//
///**
// * Session Bean implementation class PrincipalPropagation
// */
//@WebService(name = "PrincipalPropagation", portName = "PrincipalPropagationPort", serviceName = "PrincipalPropagationService", targetNamespace = "http://incture.com/pmc/poc/services/")
//@Stateless
//public class PrincipalPropagation implements PrincipalPropagationLocal {
//
//	public PrincipalPropagation() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@WebMethod(operationName = "doPost", exclude = false)
//	@Override
//	public void doPost(@WebParam(name = "propagationDto") PrincipalPropagationDto propagationDto) {
//		try {
//
//			URL url = new URL("http://192.168.5.35:50000/RESTAdapter/Principal_Propagation");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			
//			
//			
//
//			
//
//            String encodedCredentials;
//        	conn.setRequestProperty("Authorization", this.getBasicAuth()); 
//			
//			conn.setDoOutput(true);
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type", "application/json");
//			conn.setRequestProperty("Accept", "application/json; charset=UTF-8");
//			
//			
//			
//			String payload = "{\"EmployeeId\":" + propagationDto.getEmployeeId() + ", \"City\":" + propagationDto.getCity() + ",\"FirstName\":" + propagationDto.getFirstName() + ",\"LastName\":"
//					+ propagationDto.getLastName() + "}";
//			
//			
//			
//			// OutputStream os = conn.getOutputStream();
//			// os.write(input.getBytes());
//			// os.flush();
//			// System.err.println("Response Code - " + conn.getResponseCode());
//			// if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//			// throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//			// }
//			// BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//			// String output;
//			// System.err.println("Output from Server .... \n");
//			// while ((output = br.readLine()) != null) {
//			// System.err.println(output);
//			// }
//			// conn.disconnect();
//			
//			
//			
//
//			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//			writer.write(payload);
//			writer.close();
//			
//			
//			System.err.println("Response Code - " + conn.getResponseCode());
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			StringBuffer jsonString = new StringBuffer();
//			String line;
//			while ((line = br.readLine()) != null) {
//				jsonString.append(line);
//			}
//			br.close();
//			conn.disconnect();
//		} catch (MalformedURLException e) {
//			System.err.println(e.getMessage());
//		} catch (IOException e) {
//			System.err.println(e.getMessage());
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//
//	}
//	private String getBasicAuth() {
//			String userpass = "jrohit" + ":" + "1234abcd";
//			System.err.println("auth  "+userpass);
//			return "Basic "
//					+ javax.xml.bind.DatatypeConverter.printBase64Binary(userpass
//							.getBytes());
//		} 
//}
