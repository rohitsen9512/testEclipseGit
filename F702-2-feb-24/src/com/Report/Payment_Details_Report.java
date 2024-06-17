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


public class Payment_Details_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String UserName = (String)session.getAttribute("UserName");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		HashMap<String, String> map =new HashMap<String,String>();
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
		
		    
		String action = "",id="0",id_po_asst="0",id_po="",qty="",rem_qty="",no_inv="",dt_to="",id_inv="0",dt_frm="",value="",ColName="",id_inv_m="0",strnewName="";
		
		if(request.getParameter("id_po_asst") !=null)
		{
			id_po_asst = request.getParameter("id_po_asst");
		}
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_inv_m") !=null)
		{
			id_inv_m = request.getParameter("id_inv_m");
		}
		if(request.getParameter("no_inv") !=null)
		{
			no_inv = request.getParameter("no_inv");
		}
		
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
			
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("qty") !=null)
		{
			qty = request.getParameter("qty");
		}
		if(request.getParameter("rem_qty") !=null)
		{
			rem_qty = request.getParameter("rem_qty");
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
			temp = " and po.dt_po <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and po.dt_po >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and po.dt_po >= '"+dt_frm+"' and po.dt_po <= '"+dt_to+"'";
		}
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "Display":
	            	jobj = DisplayInvoiceForPaymentReport(temp);	
	                break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Payment_Details_Report.");
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
	
	
	public JSONObject DisplayInvoiceForPaymentReport(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
			String  sql="select po.id_po,po.no_po,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dt_po,po.gr_tot*rate as gr_tot,nm_ven from P_Payment p,P_Purchase_Order po,M_Vendor v,M_Exchange_Rate er where po.id_po=p.id_po and v.id_ven=po.id_ven and er.id_curr=po.id_curr "+temp+"";

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
			System.out.println("sql error in Payment_Details_Report.");
		}
		 
		return jobj;
		
		
	}
}
	
	