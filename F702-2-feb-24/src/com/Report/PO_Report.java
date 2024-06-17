package com.Report;

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


public class PO_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		HttpSession session = request.getSession(); 
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		Enumeration elemet = request.getParameterNames();

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
		    
		String action = "",dt_to="",dt_frm="",id_po="",id_ven="",no_po="";
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("no_po") !=null)
		{
			no_po = request.getParameter("no_po");
		}
		if(request.getParameter("id_ven") !=null)
		{
			id_ven = request.getParameter("id_ven");
		}
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
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
			temp = " and dt_approv <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_approv >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_approv >= '"+dt_frm+"' and dt_approv <= '"+dt_to+"'";
		}
		if(!id_ven.equals(""))
		{
			temp +=" and po.id_ven="+id_ven+"";
		}
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "Display":
	            	jobj = DisplayPurchaseOrderForPrintMailPurchaseOrder(temp);	
	                break;
	                
	            case "Preview":
	            	jobj = PreviewPurchaseOrderForPrintMail(id_emp_user,id_po,no_po,id_ven);	
	                break;
	                
	           
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Approval_Quotation.");
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
	
	
	
	public JSONObject PreviewPurchaseOrderForPrintMail(int id_emp_user,String id_po,String no_po,String id_ven)
	{
		int j=0;
		String query="";
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		JSONArray jarr4 = new JSONArray();
		try 
		{
		
			String sql="select po.*,poa.*,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, po.dt_quot, 103), ' ', '-')) as dtQuot,nm_prod,nm_curr,sl.* from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Currency c,M_Prod_Cart pc,M_Subloc sl  "+
					" where po.id_po=poa.id_po and sl.id_sloc=po.id_sloc and po.id_curr=c.id_curr and poa.id_prod=pc.id_prod and po.id_po="+id_po+"";
			
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
			    		String name=metaData.getColumnName(i);
			    		jobj2.put(name,rs.getString(name));
			    	}
			    		jarr.put(jobj2);
			    		
		        }
			   
			    json.put("data", jarr);
			    
			    String sql1 = "select nm_com,add1,add2,city,country,pin from M_Company";	
				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				
				   ResultSetMetaData metaData1 = rs.getMetaData();
				    int columns1 = metaData1.getColumnCount();
				    while(rs.next())
				    {
				    	JSONObject jobj3 = new JSONObject();
				    	for(int i=1;i<=columns1;i++)
				    	{
				    		String name=metaData1.getColumnName(i);
				    		jobj3.put(name,rs.getString(name));
				    	}
				    	jarr1.put(jobj3);
				    }
			json.put("company",jarr1);
			 sql1 = "select * from M_Vendor where id_ven="+id_ven+"";	
				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				
				ResultSetMetaData metaData3 = rs.getMetaData();
				    int columns3 = metaData3.getColumnCount();
				    while(rs.next())
				    {
				    	JSONObject jobj3 = new JSONObject();
				    	for(int i=1;i<=columns3;i++)
				    	{
				    		String name=metaData3.getColumnName(i);
				    		jobj3.put(name,rs.getString(name));
				    	}
				    	jarr4.put(jobj3);
				    }
			json.put("venDetails",jarr4);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	public JSONObject DisplayPurchaseOrderForPrintMailPurchaseOrder(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select po.*,nm_ven,nm_emp from P_Purchase_Order po,M_Emp_User emp,M_Vendor v where emp.id_emp_user=po.approv_by and v.id_ven=po.id_ven "+
					" and st_approv='Accepted' and cancel=0 and shortclosed=0 "+temp+"";
		}
		else
		{
			sql = "select po.*,nm_ven,nm_emp from P_Purchase_Order po,M_Emp_User emp,M_Vendor v where emp.id_emp_user=po.approv_by and v.id_ven=po.id_ven "+
					" and st_approv='Accepted' and cancel=0 and shortclosed=0";
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
			System.out.println("sql error in Approval_Quotation.");
		}
		 
		return jobj;
		
		
	}
	
	
	}
