package com.Util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.UserAccessData;

public class dtoUtils {
	boolean b =false;
	public static Connection con=null;
	public static ResultSet rs = null;
	public static ResultSet rs1 = null;
	public static Statement st=null;
	public static PreparedStatement ps=null;
	String output="";
	Statement stmt=null;
	
// Like query	
	public static String MakeLikeTempQuery(String word)
	{
		String tempSql =" and (wh.ds_pro LIKE '%"+word+"%' or wh.no_mfr LIKE '%"+word+"%' or wh.ds_asst LIKE '%"+word+"%' or wh.no_model LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%')";
		
		
		return tempSql;
	}
	
	// Use this function for list display and edit
	// Pass your sql query
	public static JSONObject GetDataForDisplayInJsonFormat(String sql,HttpServletRequest request)
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    json.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in GetDataForDisplayInJsonFormat while getting data for display. "+e);
		}
		
		
		return json;
		
	}

	// Use this function to delete any record.
	// Pass your delete query
public static JSONObject DeleteRecord(String query,HttpServletRequest request)
{
JSONObject json=new JSONObject();
int j=0;
	try 
	{
		con=Common.GetConnection(request);
		ps=con.prepareStatement(query);
		j=ps.executeUpdate();
		if(j > 0)
			j=1;
				
		json.put("data",j);
	}
	catch (Exception e)
	{
		System.out.println("Error in function DeleteRecord."+e);
	}
	
	return json;
}
	
// Use this function to insert record in table.
//Need to pass three parameter map, table name and primary key column name of same table
public static JSONObject AddRecordToTable(HashMap<String, String> map, String tableName, String primaryKeyColName,HttpServletRequest request)
{
	String colName="",value="";
	int j=0;
	JSONObject json=new JSONObject();
	try
	{
			rs = Common.GetColumns(tableName,  request);
					
					while (rs.next())
					{
						if(!rs.getString(1).equals(primaryKeyColName))
						{
							    if (map.containsKey(rs.getString(1)))
							    {
							    	if(colName.equals(""))
							    	{
								    	colName += rs.getString(1);
								    	value += "'"+ map.get(rs.getString(1))+"'";
							    	}
							    	else
							    	{
							    		colName +=","+ rs.getString(1);
								    	value +=", '"+ map.get(rs.getString(1))+"'";
							    		
							    	}
							    }
						  }
					}
		
	
	String query="insert into "+tableName+"("+colName+") values("+value+")";
		con=Common.GetConnection(request);
		PreparedStatement ps=con.prepareStatement(query);
		j=ps.executeUpdate();
		if(j > 0)
			j=1;
	
		json.put("data",j);
		
	}
	catch (Exception e)
	{
		System.out.println("Error in function AddRecordToTable."+e);
		e.printStackTrace();
	}
	
	
	return json;
}

// This function is used to update the record.
// Need to pass map,table name,primary key column name and primary key id, which you want to update.

public static JSONObject UpdateSingleRecord(HashMap<String, String> map , String tableName, String primaryKeyColName, 
		String primaryKeyId,HttpServletRequest request)
{
	String col_Val="";
	int j=0;
	JSONObject json=new JSONObject();
	try
	{
			rs = Common.GetColumns(tableName,  request);
					while (rs.next())
					{
						if(!rs.getString(1).equals(primaryKeyColName))
						{
							    if (map.containsKey(rs.getString(1)))
							    {
							    	if(col_Val.equals(""))
							    		col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
							    	else
							    		col_Val += ","+ rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
							    }
						  }
					}
			
		}
		catch(Exception e)
			{
				System.out.println("Some error in Department master.");
			}
	
	String query="update "+tableName+" set "+col_Val+" where "+primaryKeyColName+"="+primaryKeyId+"";
	try 
	{
		con=Common.GetConnection(request);
		PreparedStatement ps=con.prepareStatement(query);
		j=ps.executeUpdate();
		if(j > 0)
		{
			j=1;
		}
	
		json.put("data",j);
		
	}
	catch (Exception e)
	{
		System.out.println("Error in function UpdateSingleRecord. "+e);
		e.printStackTrace();
	}
	
	
	return json;
}

// Use this function to get the dropdown value...

public static JSONObject DropDownResult(String sql,HttpServletRequest request)
{
	JSONObject jobj = new JSONObject();
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
	    		String name=metaData.getColumnName(i).toLowerCase();
	    		jobj2.put(name,rs.getString(name));
	    	}
	    		jarr.put(jobj2);
	    		
        }
	    jobj.put("data", jarr);
	}
	catch(Exception e)
	{
		System.out.println("sql error in  function DropDownResult.");
	}
	 
	return jobj;
	
	
}

// Pass the sql query and Get Result Set	
	public static ResultSet GetResultSet(String sql,HttpServletRequest request)
	{
		try
		{
			con=Common.GetConnection(request);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println("sql error in GetResultSet. "+e);
		}
		
		
		return rs;
		
	}

}
