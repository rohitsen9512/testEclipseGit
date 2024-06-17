package com.Inventory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;


public class I_Add_To_Store extends HttpServlet {
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
		 HttpSession session = request.getSession(); 
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		String paramName = "";
		String paramValues = "";
		 try
		 {
		        
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      String[] parts = paramName.split("_");
		      paramValues = request.getParameter(paramName);
		      if(parts[0].equals("dt") && !paramValues.equals(""))
		      {
		    	  paramValues = request.getParameter(paramName);
		    	  
		    	  Date d = userDateFormat.parse(paramValues);  
			      map.put(paramName,dateFormatNeeded.format(d));
		      }
		      else
		      {
		    	  paramValues = request.getParameter(paramName);
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		    
		String action = "",dt_to="",dt_frm="",id_inventory="",id_inventory_m="",SerialVal="",id_sgrp="",id_inventory_grn="",id_grp="";
		
		
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
		if(request.getParameter("id_inventory_grn") !=null)
		{
			id_inventory_grn = request.getParameter("id_inventory_grn");
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
			Date currDate = new Date();
			
			if(dt_frm.equals(""))
			{
				dt_frm = "1990-01-01";
			}
			else
			{
				dt_frm = dateFormatNeeded.format(userDateFormat.parse(dt_frm));
			}
			if(dt_to.equals(""))
			{
				dt_to = dateFormatNeeded.format(currDate);
			}
			else
			{
				dt_to = dateFormatNeeded.format(userDateFormat.parse(dt_to));
			}	
		String temp="";
		if(!dt_to.equals(""))
		{
			temp = " and invm.dt_inv <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and invm.dt_inv >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and invm.dt_inv >= '"+dt_frm+"' and invm.dt_inv <= '"+dt_to+"'";
		}
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        case "Save":
	            	jobj = AddToStore(map,id_grp,id_sgrp,id_inventory_grn , id_inventory ,id_inventory_m,id_emp_user,  request);	
	                break;
		                
	            case "Display":
	            	jobj = DisplayAccessoryForAddToStore(temp);	
	                break;
	                
	            case "Edit":
	            	jobj = EditDataForAddToStore(id_inventory_grn);	
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
	
	public JSONObject EditDataForAddToStore( String id_inventory_grn)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			sql="select grn.id_inventory_grn,grn.id_inventory,grn.id_inventory_m,grn.no_po,(replace(convert(NVARCHAR, grn.dt_po, 103), ' ', '-')) as dt_po,grn.no_inv,(replace(convert(NVARCHAR, grn.dt_inv, 103), ' ', '-')) as dt_inv,grn.qty_recv,id_assetdiv as id_grp1,id_s_assetdiv as id_sgrp1,id_loc,id_subl from I_Grn grn , I_Inventory inv ,I_Inventory_Master invm  where id_inventory_grn = "+id_inventory_grn+" and inv.id_inventory = grn.id_inventory and invm.id_inventory_m = grn.id_inventory_m";
		
		
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
	
	public JSONObject DisplayAccessoryForAddToStore(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
			sql="select I_Grn.*,nm_acc_asset,mfr,un_prc,tt_un_prc,tt from I_Grn , I_Inventory inv , I_Inventory_Master invm where I_Grn.status_store = 0 and I_Grn.status_approv = 1 and inv.id_inventory = I_Grn.id_inventory and invm.id_inventory_m = I_Grn.id_inventory_m "+temp+"";
		else
		sql="select I_Grn.*,nm_acc_asset,mfr,un_prc,tt_un_prc,tt from I_Grn , I_Inventory inv , I_Inventory_Master invm where I_Grn.status_store = 0 and I_Grn.status_approv = 1 and inv.id_inventory = I_Grn.id_inventory and invm.id_inventory_m = I_Grn.id_inventory_m";
		
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
	


public JSONObject AddToStore(HashMap<String, String> map , String id_grp , String id_sgrp, String id_inventory_grn , String id_inventory , String id_inventory_m,int id_emp_user,HttpServletRequest request)
{
	String colName="",value="",colName1="",value1="",col_Val="0";
	int j=0;
	JSONObject json=new JSONObject();
	
	try 
	{
			rs = Common.GetColumns("I_Ware_House",  request);
			
			
			while (rs.next())
			{
			
				if(rs.getString(1) !="id_wh_inventory")
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
			
			rs1 = Common.GetColumns("I_Inventory_Store",  request);
			while (rs1.next())
			{
			
				if(rs1.getString(1) !="id_inventory_store")
				{
					    if (map.containsKey(rs1.getString(1)))
					    {
					    	if(colName1.equals(""))
					    	{
						    	colName1 += rs1.getString(1);
						    	value1 += "'"+ map.get(rs1.getString(1))+"'";
					    	}
					    	else
					    	{
					    		colName1 +=","+ rs1.getString(1);
						    	value1 +=", '"+ map.get(rs1.getString(1))+"'";
					    	}
					    	if(rs1.getString(1).equals("qty_recv"))
					    	{
					    		col_Val =  map.get(rs1.getString(1));
					    	}
					    	
					    	
					    }
				  }
			}
			
			
			String sql = "insert into I_Ware_House("+colName+",add_by) values("+value+","+id_emp_user+")";
			ps = con.prepareStatement(sql);
			j = ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				sql ="update I_Grn set status_store = 1 where id_inventory_grn = "+id_inventory_grn+" ";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();
				
				sql = "select id_inventory_store , qty_recv from I_Inventory_Store where id_grp="+id_grp+" and id_sgrp = "+id_sgrp+"";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if(!rs.next())
				{
					j=0;
					 sql = "insert into I_Inventory_Store("+colName1+",add_by) values("+value1+","+id_emp_user+")";
					ps = con.prepareStatement(sql);
					j = ps.executeUpdate();
					if(j > 0)
					{
						j=0;
						String query ="update I_Inventory_Master set status_store = 1 where id_inventory_m ="+id_inventory_m+"";
						PreparedStatement ps=con.prepareStatement(query);
						j=ps.executeUpdate();
						
								j=1;
					}
				}
				else
				{
					int Tot = rs.getInt("qty_recv") + Integer.parseInt(col_Val);
					
					sql ="update I_Inventory_Store set qty_recv = "+Tot+" where id_inventory_store = "+rs.getInt(1)+" ";
					ps = con.prepareStatement(sql);
					ps.executeUpdate();
					j=1;
						
					}
				
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

