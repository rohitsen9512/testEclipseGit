package com.Master;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;


public class M_Subasset_Div extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      paramValues=paramValues.replace("'", "''");
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0",value="",ColName="",type="",searchWord="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("type") !=null)
		{
			type = request.getParameter("type");
		}
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddSubassetDiv(map,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplaySubassetDiv(id,type, searchWord);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplaySubassetDiv(id,type , searchWord);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateSubassetDiv(map,id,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteSubassetDiv(id);	
	                break; 
	            case "DropDownResult":
	            	jobj = DropDownResult(id);	
	                break; 
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	            case "DisplayName":
	            	jobj = DisplayName(id);	
	            	break;

	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in M_Subasset_Div.");
		}
		finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}
	
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_s_assetdiv from M_Subasset_Div where "+ColName+" = '"+value+"'";
		try 
		{
			
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("data",1);
			}
			else
			{
				json.put("data",0);
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	public JSONObject DropDownResult(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_s_assetdiv,nm_s_assetdiv from M_Subasset_Div where id_assetdiv="+id+"";
		if(id.equals("0"))
		{
			sql="select id_s_assetdiv,nm_s_assetdiv from M_Subasset_Div";
		}
		
		try
		{
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
			System.out.println("sql error in M_Subasset_Div.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayName(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select nm_s_assetdiv from M_Subasset_Div where id_s_assetdiv="+id+"";
		try
		{
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
			System.out.println("sql error in M_Subasset_Div.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DeleteSubassetDiv(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		
		try 
		{
			String query = "select id_wh from A_Ware_House where id_sgrp = "+id+"";
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				j=2;
			}
			else
			{
				 query = "select  id_s_assetdiv from M_model where id_s_assetdiv = "+id+"";
				 System.out.println("Sub query------" + query);
				PreparedStatement ps=con.prepareStatement(query);
				rs=ps.executeQuery();
					if(rs.next())
					{
						j=3;
					}
					else
					{
				query = "delete M_Subasset_Div where id_s_assetdiv = "+id+"";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
					}
			}
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject UpdateSubassetDiv(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Subasset_Div",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_s_assetdiv")
							{
								    if (map.containsKey(rs.getString(1)))
								    {
								    	if(col_Val.equals(""))
								    	{
								    		col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
									    	
								    	}
								    	else
								    	{
								    		
								    		col_Val += ","+ rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
									    	
								    		
								    	}
								    }
							  }
						}
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in M_Subasset_Div.");
				}
		
		String query="update M_Subasset_Div set "+col_Val+" where id_s_assetdiv="+id+"";
		try 
		{
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
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	
	public JSONObject DisplaySubassetDiv(String id, String type , String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select sd.*,nm_assetdiv from M_Subasset_Div sd , M_Asset_Div ad where sd.id_assetdiv=ad.id_assetdiv and type_grp = '"+type+"' and (nm_assetdiv like '%"+searchWord+"%' or nm_s_assetdiv like '%"+searchWord+"%' or cd_s_assetdiv like '%"+searchWord+"%')";
		if(!id.equals("0"))
		{
			sql ="select * from M_Subasset_Div where id_s_assetdiv = "+id+"";
		}
		
		try
		{
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
			System.out.println("sql error in M_Subasset_Div.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddSubassetDiv(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Subasset_Div",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_s_assetdiv")
							{
								    if (map.containsKey(rs.getString(1)))
								    {
								    	if(colName.equals(""))
								    	{
									    	colName += rs.getString(1);
									    	value += "'"+ map.get(rs.getString(1)).toUpperCase()+"'";
								    	}
								    	else
								    	{
								    		colName +=","+ rs.getString(1);
									    	value +=", '"+ map.get(rs.getString(1)).toUpperCase()+"'";
								    		
								    	}
								    }
							  }
						}
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in M_Subasset_Div.");
				}
		
		String query="insert into M_Subasset_Div("+colName+") values("+value+")";
		try 
		{
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
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	
	
	
	
	
	

}
