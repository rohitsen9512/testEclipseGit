package dto.Common;

import java.io.InputStream;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

public class AssetOwner {

	
	public  JSONObject GetAssetOwnerDataFromPropertiesFile()
	{
		
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		Properties prop = new Properties();
		try
		{
			InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/assetOwner.properties");
			
			if (inputStream == null) {
				System.out.println("property file 'assetOwner.properties' not found in the classpath");
			}
			prop.load(inputStream);
			String userAccessData = prop.getProperty("ASSETOWNER");
			
			String []data = userAccessData.split(",");
			
	    	for(int i=0; i < data.length ; i++)
	    	{
	    		JSONObject jobj2 = new JSONObject();
	    		String key=data[i];
	    		String value = prop.getProperty(key);
	    		jobj2.put(key,value);
	    		jarr.put(jobj2);
	    	}
	    		json.put("data", jarr);
			
		}
		catch(Exception e)
		{
			System.out.println("Error...."+e);
		}
		
		return json;
		
	}
	
	
	
}
