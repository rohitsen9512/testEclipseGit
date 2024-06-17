package dto.Common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

public class DtoCommon {
	boolean b =false;
	public static Connection con=null;
	public static ResultSet rs = null;
	public static ResultSet rs1 = null;
	public static Statement st=null;
	public static PreparedStatement ps=null;
	String output="";
	Statement stmt=null;
	
	
	public String MakeLikeTempQuery(String word)
	{
		String tempSql =" and (wh.ds_pro LIKE '%"+word+"%' or wh.no_mfr LIKE '%"+word+"%' or wh.ds_asst LIKE '%"+word+"%' or wh.no_model LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%' or wh.nm_computer LIKE '%"+word+"%' ";
		
		
		return tempSql;
	}
	
	public String MakeTempQuery(String UserType ,String Table1, String Table2,HttpServletRequest request)
	{
		String typ_asst="",id_loc="",id_sloc="",id_design="";
		String sql1="select id_design,id_loc,id_subl from M_User_Permission where type_user='"+UserType+"' ";
		try
		{
			con = Common.GetConnection(request);
			ps = con.prepareStatement(sql1);
			rs = ps.executeQuery();
			if(rs.next())
			{
				//typ_asst = rs.getString(1);
				id_design = rs.getString(1);
				id_loc = rs.getString(2);
				id_sloc = rs.getString(3);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error while making temp query in DtoCommon.");
		}
		String sql="";
		//sql += " and "+ QueryBuilderForAssetOwner(typ_asst,Table1);
		sql += " and "+ QueryBuilderForDept(id_design,Table2);
		sql += " and "+ QueryBuilderForLoc(id_loc,Table2);
		sql += " and "+ QueryBuilderForSubl(id_sloc,Table2);
		return sql;
	}
	public String QueryBuilderForSubLocation(String data)
	{
		String sql="";
		int len=0;
		String arr[] = data.split(",");
		try{
			if(arr.length>0)
				len=arr.length;
		}catch(Exception e){
			len=0;
		}
		if(len > 0){
			sql="";
			for(int i=0;i<len;i++)
			{
				if(sql.equals(""))
					sql = " wh.id_subl = '"+arr[i]+"'";
				else
					sql +=" or wh.id_subl = '"+arr[i]+"'";
			}
		}
		sql = "("+sql+")";
		return sql;
	}
	public String QueryBuilderForLocation(String data)
	{
		String sql="";
		int len=0;
		String arr[] = data.split(",");
		try{
			if(arr.length>0)
				len=arr.length;
		}catch(Exception e){
			len=0;
		}
		if(len > 0){
			sql="";
			for(int i=0;i<len;i++)
			{
				if(sql.equals(""))
					sql = " wh.id_loc = '"+arr[i]+"'";
				else
					sql +=" or wh.id_loc = '"+arr[i]+"'";
			}
		}
		sql = "("+sql+")";
		return sql;
	}
	
	public String QueryBuilderForDept(String data, String table)
	{
		String sql="";
		int len=0;
		String arr[] = data.split(",");
		try{
			if(arr.length>0)
				len=arr.length;
		}catch(Exception e){
			len=0;
		}
		if(len > 0){
			sql="";
			for(int i=0;i<len;i++)
			{
				if(sql.equals(""))
					sql = " "+table+".id_dept = '"+arr[i]+"'";
				else
					sql +=" or "+table+".id_dept = '"+arr[i]+"'";
			}
		}
		sql = "("+sql+")";
		return sql;
	}
	
	public String QueryBuilderForLoc(String data, String table)
	{
		String sql="";
		int len=0;
		String arr[] = data.split(",");
		try{
			if(arr.length>0)
				len=arr.length;
		}catch(Exception e){
			len=0;
		}
		if(len > 0){
			sql="";
			for(int i=0;i<len;i++)
			{
				if(sql.equals(""))
					sql = " "+table+".id_loc = '"+arr[i]+"'";
				else
					sql +=" or "+table+".id_loc = '"+arr[i]+"'";
			}
		}
		sql = "("+sql+")";
		return sql;
	}
	
	public String QueryBuilderForSubl(String data, String table)
	{
		String sql="";
		int len=0;
		String arr[] = data.split(",");
		try{
			if(arr.length>0)
				len=arr.length;
		}catch(Exception e){
			len=0;
		}
		if(len > 0){
			sql="";
			for(int i=0;i<len;i++)
			{
				if(sql.equals(""))
					sql = " "+table+".id_subl = '"+arr[i]+"'";
				else
					sql +=" or "+table+".id_subl = '"+arr[i]+"'";
			}
		}
		sql = "("+sql+")";
		return sql;
	}
	
	public String QueryBuilderForAssetOwner(String data,String table)
	{
		String sql="";
		int len=0;
		String arr[] = data.split(",");
		try{
			if(arr.length>0)
				len=arr.length;
		}catch(Exception e){
			len=0;
		}
		if(len > 0){
			sql="";
			for(int i=0;i<len;i++)
			{
				if(sql.equals(""))
					sql = " "+table+".typ_asst = '"+arr[i]+"'";
				else
					sql +=" or "+table+".typ_asst = '"+arr[i]+"'";
			}
		}
		sql = "("+sql+")";
		return sql;
	}
	
	public JSONObject GetDataForDisplay(String sql,HttpServletRequest request)
	{
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
			con=Common.GetConnection(request);
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
		    json.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in DtoCommon while getting data for display.");
		}
		
		
		return json;
		
	}

	
	public List<String> ReturnEmail(String sql , List<String> arr,HttpServletRequest request)
	{
		try
		{
			con=Common.GetConnection(request);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next())
			{
				if(!arr.contains(rs.getString(1)))
					arr.add(rs.getString(1));
			}
		}
		catch(Exception e)
		{
			System.out.println("sql error in DtoCommon while ReturnListData.");
		}
		
		return arr;
		
	}
	
	public List<String> ReturnListData(String sql,HttpServletRequest request)
	{
		List<String> arr = new ArrayList<String>();	
		try
		{
			con=Common.GetConnection(request);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next())
			{
				arr.add(rs.getString(1));
			}
		}
		catch(Exception e)
		{
			System.out.println("sql error in DtoCommon while ReturnListData.");
		}
		
		return arr;
		
	}
	
	public String ReturnReplyMailIdFromList(List<String> recipients)
	{
		String replyMailId=""; 
		try
		{
			replyMailId = recipients.get(0);
		}
		catch(Exception e)
		{
			System.out.println("sql error in DtoCommon while ReturnReplyMailIdFromList.");
		}
		
		return replyMailId;
		
	}
	
	public String ReturnName(String sql,HttpServletRequest request)
	{
		String name = "";
		try
		{
			con=Common.GetConnection(request);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next())
			{
				name = rs.getString(1);
			}
		}
		catch(Exception e)
		{
			System.out.println("sql error in DtoCommon while ReturnName.");
		}
		
		return name;
		
	}
	public String ReturnParticularMessage(String key)
	{
		Properties prop = new Properties();	
		String link="";
InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/mailConfiguration.properties");
		
		if (inputStream == null) {
			System.out.println("property file 'mailConfiguration.properties' not found in the classpath");
		}
		try
		{
			prop.load(inputStream);
			 link = prop.getProperty(key);
		}
		catch(Exception e)
		{
			System.out.println("Error while geting ReturnLinkForLogin... "+e);
		}
		
		return link;
		
	}
	
	public ResultSet GetResultSet(String sql,HttpServletRequest request)
	{
		try
		{
			con=Common.GetConnection(request);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println("sql error in DtoCommon while GetResultSet.");
		}
		
		
		return rs;
		
	}

}
