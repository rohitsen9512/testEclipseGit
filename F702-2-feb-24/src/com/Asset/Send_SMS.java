package com.Asset;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Send_SMS {

	public static void main(String[] args) {
        
        try {
            // Construct data
            String apiKey = "apikey=" + "AIzaSyCp1oacd0laahLEXDofzifEkBFCrA_AoWk";
            String message = "&message=" + "Greetings from Simplifying Tech! Have a nice day!";
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + "8960153195";
 
            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("AIzaSyCp1oacd0laahLEXDofzifEkBFCrA_AoWk").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
             
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
            System.out.println(stringBuffer.toString());
            rd.close();
 
 
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
