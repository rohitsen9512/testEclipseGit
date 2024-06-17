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


public class M_Tax extends HttpServlet {
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
		    
		String action = "",id="0",value="",id_tax1="",ColName="",nm_tax1="",nm_tax2="",per_tax1="",per_tax2="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_tax1") !=null)
		{
			id_tax1 = request.getParameter("id_tax1");
		}
		if(request.getParameter("nm_tax1") !=null)
		{
			nm_tax1 = request.getParameter("nm_tax1");
		}
		if(request.getParameter("nm_tax2") !=null)
		{
			nm_tax2 = request.getParameter("nm_tax2");
		}
		if(request.getParameter("per_tax1") !=null)
		{
			per_tax1 = request.getParameter("per_tax1");
		}
		if(request.getParameter("per_tax2") !=null)
		{
			per_tax2 = request.getParameter("per_tax2");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddTax(map,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayTax(id);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayTax(id);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateTax(map,id,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteTax(id);	
	                break; 
	                
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	                
	            case "CheckExitsValues":
	            	jobj = CheckExitsValues(nm_tax1,nm_tax2,per_tax1,per_tax2);	
	                break;
	                
	            case "DropDownResult":
	            	jobj = DropDownResult(id);	
	                break; 
	            case "DropDownResultForTax2":
	            	jobj = DropDownResultTax2(id);	
	                break;    
	                
	            case "getTax2":
	            	jobj = getTax2(id_tax1);	
	                break;  
	            case "getTax2Details":
	            	jobj = getTax2Details(id_tax1);	
	                break;      
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in M_Tax.");
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
	
	public JSONObject getTax2( String id_tax1)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_tax,nm_tax2 from M_Tax where id_tax="+id_tax1+"";
		
		
		try
		{
			System.out.println(sql);
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
			System.out.println(e);
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject getTax2Details( String id_tax1)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_tax,nm_tax2,per_tax1,per_tax2 from M_Tax where id_tax="+id_tax1+"";
		
		
		try
		{
			System.out.println(sql);
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
			System.out.println(e);
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DropDownResultTax2(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(id.equals("0"))
			 sql="select id_tax,nm_tax2 from M_Tax";
		else
			 sql="select id_tax,per_tax1,per_tax2 from M_Tax where id_tax="+id+"";
		try
		{
			System.out.println(sql);
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
			System.out.println("sql error in  M_Tax." + e);
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject DropDownResult(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(id.equals("0"))
			 sql="select id_tax,nm_tax1 from M_Tax";
		else
			 sql="select id_tax,per_tax1,per_tax2 from M_Tax where id_tax="+id+"";
		try
		{
			System.out.println(sql);
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
			System.out.println("sql error in  M_Tax." + e);
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_tax from M_Tax where "+ColName+" = '"+value+"'";
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
	
	public JSONObject CheckExitsValues(String nm_tax1,String nm_tax2,String per_tax1,String per_tax2)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_tax from M_Tax where nm_tax1='"+nm_tax1+"' and nm_tax2='"+nm_tax2+"' and per_tax1='"+per_tax1+"' and per_tax2='"+per_tax2+"' ";
		System.out.println(query);
		try 
		{
			
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("data",2);
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
	
	public JSONObject DeleteTax(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		String query = "delete M_Tax where id_tax = "+id+"";
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
	
	public JSONObject UpdateTax(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Tax",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_tax")
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
					System.out.println("Some error in M_Tax.");
				}
		
		String query="update M_Tax set "+col_Val+" where id_tax="+id+"";
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
	
	
	
	public JSONObject DisplayTax(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select * from M_Tax";
		if(!id.equals("0"))
		{
			sql +=" where id_tax = "+id+"";
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
			System.out.println("sql error in M_Tax.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddTax(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Tax",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_tax")
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
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in M_Tax.");
				}
		
		String query="insert into M_Tax("+colName+") values("+value+")";
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
