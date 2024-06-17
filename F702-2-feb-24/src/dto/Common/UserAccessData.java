package dto.Common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;


public class UserAccessData {
	
	PreparedStatement ps =null;
	ResultSet rs =null;
	Connection con = null;
	
	public JSONObject SendMailAccordingToDepartment(String id_dept,HttpServletRequest request){
		JSONObject jobj = new JSONObject();
		String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="";
		String sql="select emp.id_emp,emp.nm_emp,sd.nm_s_assetdiv,no_model,no_mfr,id_wh_dyn from A_Ware_House wh,M_Emp_User emp,M_Subasset_Div sd "+ 
					" where emp.id_dept="+id_dept+" and allocate=1 and emp.id_emp_user=to_assign and sd.id_s_assetdiv=wh.id_sgrp and emp.status_emp='Working'";
		List<String> recipients = new ArrayList<String>();     	
		DtoCommon dtcm = new DtoCommon();
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		String mailSubject = dtcm.ReturnParticularMessage("sendMail");
		String msg = dtcm.ReturnParticularMessage("msg");
		try{
		rs = dtcm.GetResultSet(sql,  request);
		int j=0;
		while(rs.next())
		{
			replyMailId = rs.getString(1);
			name = rs.getString(2);
			itemName = rs.getString(3);
			no_model = rs.getString(4);
			slNo = rs.getString(5);
			id_wh_dyn = rs.getString(6);
			
			String mailBody = "<b>Hello "+name+" , </b><br><br><br>"+
					msg+
					"<br>The <b>'"+itemName+"'</b> having Model No <b>'"+no_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b><br>"+
				  "<p>"+footerMsg+"</p>";

			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
			j=1;
		}
		jobj.put("data", j);
		}catch(Exception e){
			System.out.println("Error While SendMailAccordingToDepartment. "+e);
		}
		
		
		
		
		return jobj;
	}
	
	public JSONObject QueryMacker(String sql , String userType , String searchParameter , String id_dept,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		if(userType.equals("DEPT"))
		{
			if(!searchParameter.equals(""))
			{
				sql += MakeLikeTempQuery(searchParameter);
			}
			
				sql = sql + " and  wh.id_dept='"+id_dept+"'";
		}
		else if(userType.equals("IT") || userType.equals("NIT"))
		{
			if(!searchParameter.equals(""))
			{
				sql += MakeLikeTempQuery(searchParameter);
			}
			if(userType.equals("IT"))
				sql = sql + " and typ_asst='IT' and st_config ='Yes'";
			else
				sql = sql + " and typ_asst='NIT' ";
		}
		else
		{
			if(!searchParameter.equals(""))
			{
				sql += MakeLikeTempQuery(searchParameter);
				//sql = sql +" "+searchParameter;
			}
		}
		
		jobj = GetDataAsJsonFormate(sql,  request);
		return jobj;
	}
	
	public String MakeLikeTempQuery(String word)
	{
		String tempSql =" and (wh.ds_pro LIKE '%"+word+"%' or wh.no_mfr LIKE '%"+word+"%' or wh.ds_asst LIKE '%"+word+"%' or wh.serial_no LIKE '%"+word+"%' or wh.no_model LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%' or nm_emp LIKE '%"+word+"%' or cd_emp LIKE '%"+word+"%')";
		System.out.println(tempSql);
		return tempSql;
	}
	
	public JSONObject QueryMacker1(String sql , String userType , String searchParameter , String id_dept,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		if(userType.equals("DEPT"))
		{
			if(!searchParameter.equals(""))
			{
				sql += MakeLikeTempQuery1(searchParameter);
			}
			
				sql = sql + " and  wh.id_dept='"+id_dept+"'";
		}
		else if(userType.equals("IT") || userType.equals("NIT"))
		{
			if(!searchParameter.equals(""))
			{
				sql += MakeLikeTempQuery1(searchParameter);
			}
			if(userType.equals("IT"))
				sql = sql + " and typ_asst='IT' and st_config ='Yes'";
			else
				sql = sql + " and typ_asst='NIT' ";
		}
		else
		{
			if(!searchParameter.equals(""))
			{
				sql += MakeLikeTempQuery1(searchParameter);
				//sql = sql +" "+searchParameter;
			}
		}
		
		jobj = GetDataAsJsonFormate(sql,  request);
		return jobj;
	}
	
	public String MakeLikeTempQuery1(String word)
	{
		String tempSql =" and (wh.ds_pro LIKE '%"+word+"%' or wh.no_mfr LIKE '%"+word+"%' or wh.ds_asst LIKE '%"+word+"%' or wh.serial_no LIKE '%"+word+"%' or wh.no_model LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%' )";
		System.out.println(tempSql);
		return tempSql;
	}
	
	public JSONObject GetDataAsJsonFormate(String sql,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
		con = Common.GetConnection(request);	
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error while making json in UserAccessData.");
		}
		 
		return jobj;
		
	}
	
	public  JSONObject GetUserAccessPropertiesData(String userType , String fileName,HttpServletRequest request)
	{
		
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		Properties prop = new Properties();
		try
		{
			/*String propFileName = "com/Resources/mainMenuUserAccess.properties";
			if(action.equals("SUBMENU"))
				propFileName = "com/Resources/subMenuUserAccess.properties";*/
			 
//			InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/"+fileName);
//			
//			if (inputStream == null) {
//				System.out.println("property file '" + fileName + "' not found in the classpath");
//			}
//			prop.load(inputStream);
//			String userAccessData = prop.getProperty(userType);
			
			// For Database
			
			 String userAccessData="";
		        String query = "select access from "+fileName+" where User_Type='"+userType+"' ";
		        System.out.println("access query is " + query);
		        con=Common.GetConnection(request);
   	  	ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					System.out.println("access query is " + query);
					userAccessData=rs.getString(1);
					 System.out.println("access is " + userAccessData);
				}
				
			String []data = userAccessData.split(",");
			JSONObject jobj2 = new JSONObject();
			
	    	for(int i=0; i < data.length ; i++)
	    	{
	    		//String name=data[i].toLowerCase();
	    		String name=data[i];
	    		jobj2.put(name,name);
	    	}
	    		jarr.put(jobj2);
	    		json.put("data", jarr);
			
		}
		catch(Exception e)
		{
			System.out.println("Error...."+e);
		}
		
		return json;
		
	}
	

}
