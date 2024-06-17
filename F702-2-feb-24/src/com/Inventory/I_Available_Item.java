package com.Inventory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;


public class I_Available_Item extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	PreparedStatement ps1=null;
	Connection con=null;
	Statement stmt=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",dt_to="",dt_frm="",id_inventory="",id_inventory_m="",SerialVal="",id_sgrp="",id_grp="";
		int qty_recv=0;
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		
		if(request.getParameter("id_inventory") !=null)
		{
			id_inventory = request.getParameter("id_inventory");
		}
		if(request.getParameter("qty_recv") !=null)
		{
			qty_recv = Integer.parseInt(request.getParameter("qty_recv"));
		}
		
		if(request.getParameter("id_grp") !=null)
		{
			id_grp = request.getParameter("id_grp");
		}
		if(request.getParameter("id_sgrp") !=null)
		{
			id_sgrp = request.getParameter("id_sgrp");
		}
		if(request.getParameter("id_inventory_m") !=null)
		{
			id_inventory_m = request.getParameter("id_inventory_m");
		}
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        case "TakeItem":
	            	jobj = AddToTakenInventory(map , qty_recv ,id_grp , id_sgrp,  request);	
	                break;
		                
	            case "Display":
	            	jobj = DisplayAccessoryForAvailableItem(dt_frm,dt_to);	
	                break;
	                
	            case "Edit":
	            	jobj = EditDataForAvailableItem(id_grp , id_sgrp);	
	                break;
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in A_Add_To_Store.");
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
	
	public JSONObject EditDataForAvailableItem( String id_grp , String id_sgrp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String sql="select nm_assetdiv as id_grp1 ,nm_s_assetdiv as id_sgrp1,qty_recv,id_grp,id_sgrp,id_inventory_store from I_Inventory_Store invs , M_Asset_Div ad , M_Subasset_Div sad where  id_grp ="+id_grp+" and id_sgrp = "+id_sgrp+" and id_grp = ad.id_assetdiv and id_sgrp = sad.id_s_assetdiv";
		
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    
		    if(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		jobj.put("data", jarr);
	        }
		    else
		    {
		    	jobj.put("data", "data");
		    }
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Add_To_Store.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayAccessoryForAvailableItem(String dt_frm,String dt_to)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		sql="select * from I_Inventory_Store";
		
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
			System.out.println("sql error in A_Add_To_Store.");
		}
		 
		return jobj;
	}
	


public JSONObject AddToTakenInventory(HashMap<String, String> map , int qty_recv , String id_grp , String id_sgrp,HttpServletRequest request)
{
	String colName="",value="";
	int j=0,qty_taken=0;
	JSONObject json=new JSONObject();
	
	try 
	{
			rs = Common.GetColumns("I_Item_Taken_History",  request);
			
			
			while (rs.next())
			{
			
				if(rs.getString(1) !="id_item_taken")
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
					    	
					    	if(rs.getString(1).equals("qty_taken"))
					    	{
					    		qty_taken = Integer.parseInt(map.get(rs.getString(1)));
					    	}
					    }
				  }
			}
			
			
			String sql = "insert into I_Item_Taken_History("+colName+") values("+value+")";
			ps = con.prepareStatement(sql);
			j = ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				sql="update I_Inventory_Store set qty_recv = "+(qty_recv - qty_taken)+" where id_grp ="+id_grp+" and id_sgrp = "+id_sgrp+"";
				ps=con.prepareStatement(sql);
				j = ps.executeUpdate();
				if(j>0)
					j=1;
				
			}
			
			json.put("data",j);
	
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
		return json;
	}

}

