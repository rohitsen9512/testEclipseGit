package com.Asset;

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
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;
import com.Util.dtoUtils;

import dto.Common.UserAccessData;


public class Print_Invoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
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
		      if((parts[0].equals("dt") || (parts[0].equals("endt")) || (parts[0].equals("std"))) && !paramValues.equals(""))
		      {
		    	  paramValues = request.getParameter(paramName);
		    	  System.out.println("Date from forms"+paramValues);
		    	  Date d = userDateFormat.parse(paramValues);  
			      map.put(paramName,dateFormatNeeded.format(d));
		      }
		      else
		      {
		    	  paramValues = request.getParameter(paramName);
		    	  paramValues=paramValues.replace("'", "''");
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println("Date from forms:"+paramValues);
			 System.out.println("EXE:"+e.toString());
		 }
		    
		String action = "",word="",typ_asset="",id="",id_ven="",id_sales_invoice="",id_prod="",dt_to="",dt_frm="",id_inv="",id_inv_m="",SerialVal="",id_sgrp="",id_grn="",sapNo="",value="";
		
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("id_prod") !=null)
		{
			id_prod = request.getParameter("id_prod");
		}
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("typ_asset") !=null)
		{
			typ_asset = request.getParameter("typ_asset");
		}
		if(request.getParameter("id_grn") !=null)
		{
			id_grn = request.getParameter("id_grn");
		}
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("id_sales_invoice") !=null)
		{
			id_sales_invoice = request.getParameter("id_sales_invoice");
		}
		if(request.getParameter("id_ven") !=null)
		{
			id_ven = request.getParameter("id_ven");
		}
		
		if(request.getParameter("id_inv") !=null)
		{
			id_inv = request.getParameter("id_inv");
		}
		if(request.getParameter("id_inv_m") !=null)
		{
			id_inv_m = request.getParameter("id_inv_m");
		}
		if(request.getParameter("sapNo") !=null)
		{
			sapNo = request.getParameter("sapNo");
		}
		if(request.getParameter("SerialVal") !=null)
		{
			SerialVal = request.getParameter("SerialVal");
		}
		if(request.getParameter("id_sgrp") !=null)
		{
			id_sgrp = request.getParameter("id_sgrp");
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
			temp = " and dt_inv <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_inv >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_inv >= '"+dt_frm+"' and dt_inv <= '"+dt_to+"'";
		}
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType"); 
			con=Common.GetConnection(request);
			
			String sql="";
			switch (action)
	        {
	        
	            case "Display":
	            	 String tempSql =" and (no_inv LIKE '%"+word+"%' or dt_inv LIKE '%"+word+"%' or v.nm_ven LIKE '%"+word+"%' )";

	            	 sql="select pi.*,(replace(convert(NVARCHAR, pi.dt_inv, 103), ' ', '-')) as dtinvoice,v.nm_ven"+
	            	 	" from A_Invoice_Master pi,M_Vendor v"+
	            	 	" where  pi.id_ven=v.id_ven  "+tempSql+" ";
	            	 System.out.println(sql);
	            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql, request);
	            	  break;
	            	
	            case "PreviewExportInvoice":
	            	jobj = PreviewExportInvoice(id_emp_user,id_sales_invoice,id_ven);	  
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
	
	public JSONObject PreviewExportInvoice(int id_emp_user,String id_sales_invoice,String id_ven)
	{
		
		
		int j=0;
		String query="";
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		JSONArray jarr4 = new JSONArray();
		JSONArray jarr5 = new JSONArray();
		JSONArray jarr6 = new JSONArray();
		JSONArray jarr7 = new JSONArray();
		String sql="";
		try 
		{
			sql= " select *,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtinvoice from A_Invoice_Master im,A_Invoice i where i.id_inv_m=im.id_inv_m and im.id_inv_m='"+id_sales_invoice+"'";
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			   ResultSetMetaData metaData = rs.getMetaData();
			    int columns = metaData.getColumnCount();
			    JSONObject jobj2 = new JSONObject();
			    while(rs.next())
			    {
			    	jobj2 = new JSONObject();
			    	for(int i=1;i<=columns;i++)
			    	{
			    		String name=metaData.getColumnName(i).toLowerCase();
			    		jobj2.put(name,rs.getString(name));
			    	}
			    		jarr.put(jobj2);
			    		
		        }
			    json.put("data", jarr);
			    
			    String sql1 = "select * from M_Company";	
				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				
				   ResultSetMetaData metaData1 = rs.getMetaData();
				    int columns1 = metaData1.getColumnCount();
				    System.out.println(sql1);
				    while(rs.next())
				    {
				    	JSONObject jobj3 = new JSONObject();
				    	for(int i=1;i<=columns1;i++)
				    	{
				    		String name=metaData1.getColumnName(i).toLowerCase()
				    				;
				    		jobj3.put(name,rs.getString(name));
				    	}
				    	jarr1.put(jobj3);
				    }
			json.put("company",jarr1);
			
			
			sql1 = "select sum((qty*un_prc)+tax_val1+tax_val2) as total from A_Invoice where id_inv_m="+id_sales_invoice+"";	
			ps=con.prepareStatement(sql1);
			rs=ps.executeQuery();
			
			ResultSetMetaData metaData7 = rs.getMetaData();
			    int columns7 = metaData7.getColumnCount();
			    System.out.println(sql1);
			    while(rs.next())
			    {
			    	JSONObject jobj7 = new JSONObject();
			    	for(int i=1;i<=columns7;i++)
			    	{
			    		String name=metaData7.getColumnName(i).toLowerCase();
			    		jobj7.put(name,rs.getString(name));
			    	}
			    	jarr7.put(jobj7);
			    }
		json.put("total",jarr7);
			
			 sql1 = "select * from M_Vendor where id_ven="+id_ven+"";	
				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				
				ResultSetMetaData metaData3 = rs.getMetaData();
				    int columns3 = metaData3.getColumnCount();
				    System.out.println(sql1);
				    while(rs.next())
				    {
				    	JSONObject jobj3 = new JSONObject();
				    	for(int i=1;i<=columns3;i++)
				    	{
				    		String name=metaData3.getColumnName(i).toLowerCase();
				    		jobj3.put(name,rs.getString(name));
				    	}
				    	jarr4.put(jobj3);
				    }
			json.put("venDetails",jarr4);
			
			
			
			/*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		    Date date = new Date();  
	
		    json.put("todaydate",formatter.format(date));*/
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json; 
	}
	
}

