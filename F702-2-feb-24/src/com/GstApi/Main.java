package com.GstApi;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /*
        Maven dependency for JSON-simple:
            <dependency>
                <groupId>com.googlecode.json-simple</groupId>
                <artifactId>json-simple</artifactId>
                <version>1.1.1</version>
            </dependency>
         */

        try {
            //Public API:
            //https://www.metaweather.com/api/location/search/?query=<CITY>
            //https://www.metaweather.com/api/location/44418/

            URL url = new URL("https://ezatlas.com/ezAtlas-FAM-HUNGER-BOX-TESTING/ReportView/Invoice_Detail_Report2.jsp");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                System.out.println(informationString);


                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONObject jobj = new JSONObject();
        		JSONArray jarr = new JSONArray();
        		
                JSONArray dataObject = (jarr);

                //Get the first JSON object in the JSON array
                System.out.println(dataObject.get(0));

                JSONObject countryData = (JSONObject) dataObject.get(0);

                System.out.println(countryData.get("woeid"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}