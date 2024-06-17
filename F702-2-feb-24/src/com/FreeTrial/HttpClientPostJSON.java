
package com.FreeTrial;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientPostJSON {

    public static void main(String[] args) {
        String url = "https://app.kwiqreply.io/v2.0/trigger_event?token=b2daCK1eXFL7qwVC6uCeDRPUEiihkv";
        String postData = "{\r\n" + 
        		"                \"company-name\" : \"www.ezatlas.com\",\r\n" + 
        		"                \"event-data\": {\r\n" + 
        		"                    \"event-name\" : \"test\",\r\n" + 
        		"                    \"event-id\": \"5902\"\r\n" + 
        		"                },\r\n" + 
        		"                \"parameters\":{\r\n" + 
        		"                    \"phone-number\":\"7067927593\",\r\n" + 
        		"                    \"variables\":[\"testing by pritesh.........\",\"checked\",\"sad\",\"dfg\",\"gfh\",\"fgh\",\"fgh\"],\r\n" + 
        		"                    \"url-variable\":\"url-endpoint\",\r\n" + 
        		"                    \"header\" : {\r\n" + 
        		"                        \"link\" : \"https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aHVtYW58ZW58MHx8MHx8fDA%3D&w=1000&q=80\",\r\n" + 
        		"                    \"filename\":\"required if the media type is of document type\"\r\n" + 
        		"                    }\r\n" + 
        		"                }\r\n" + 
        		"            }"; // Your POST data
        
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            
            // Set the necessary HTTP Method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(postData.length()));
            connection.setRequestProperty("Authorization", "Bearer 53522eb8-bac3-4f13-bf24-6880ac3798ef"); // Add your custom header
            
            connection.setDoOutput(true);
            
            // Write the POST data to the connection
            try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
                dataOutputStream.writeBytes(postData);
                dataOutputStream.flush();
            }
            
            // Get the response from the server
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("HTTP POST request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}