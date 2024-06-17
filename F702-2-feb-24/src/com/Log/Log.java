package com.Log;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Log {

	
	public void LogGenerate() throws JSONException{
		
		 JSONObject employeeDetails = new JSONObject();
	        try {
				employeeDetails.put("firstName", "Lokesh");
			
	        employeeDetails.put("lastName", "Gupta");
	        employeeDetails.put("website", "howtodoinjava.com");
	         
	        JSONObject employeeObject = new JSONObject(); 
	        employeeObject.put("employee", employeeDetails);
	         
	       
	        //Add employees to list
	        JSONArray jarr = new JSONArray();
	        jarr.put(employeeObject);
	       FileWriter file = new FileWriter("E:/LogFile.json");
	 
	            file.write(jarr.toString());
	            file.flush();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
       
	}
	
}
