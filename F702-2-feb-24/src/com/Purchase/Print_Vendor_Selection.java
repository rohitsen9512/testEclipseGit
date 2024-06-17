package com.Purchase;

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


public class Print_Vendor_Selection extends HttpServlet {
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
		String action = "",id="0",dt_to="",dt_frm="",id_quot="",no_quot="",no_ind="";
		String bidding="0",lowest_bid="0",recurring_order="0",compt_bid="0",other="0",other_specify="0",id_ven_select1="0";
		
		if(request.getParameter("id_ven_select1") !=null)
		{
			id_ven_select1 = request.getParameter("id_ven_select1");
		}
		if(request.getParameter("bidding") !=null)
		{
			bidding = request.getParameter("bidding");
		}
		if(request.getParameter("lowest_bid") !=null)
		{
			lowest_bid = request.getParameter("lowest_bid");
		}
		if(request.getParameter("recurring_order") !=null)
		{
			recurring_order = request.getParameter("recurring_order");
		}
		if(request.getParameter("compt_bid") !=null)
		{
			compt_bid = request.getParameter("compt_bid");
		}
		if(request.getParameter("other") !=null)
		{
			other = request.getParameter("other");
		}
		if(request.getParameter("other_specify") !=null)
		{
			other_specify = request.getParameter("other_specify");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id_quot") !=null)
		{
			id_quot = request.getParameter("id_quot");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("no_ind") !=null)
		{
			no_ind = request.getParameter("no_ind");
		}
		
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("no_quot") !=null)
		{
			no_quot = request.getParameter("no_quot");
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
		
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "Display":
	            	jobj = DisplayQuotaionForPrintVendorSelection(temp);	
	                break;
	                
	           
	            case "Edit":
	            	jobj = PrintVendorSelection(no_quot,no_ind,id_quot);	
	                break;
	                
	       
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Print_Vendor_Selection.");
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
	
	
	public JSONObject PrintVendorSelection(String no_quot,String no_ind,String id_quot)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		
		try
		{
			
			String sql="select qa.*,nm_tax,nm_prod,q.tot,nm_ven,nm_curr,q.id_ven,q.no_quot,other,other_specify,recurring_order,compt_bid,lowest_bid from P_Quotation q,P_Quotation_Asset qa,M_Prod_Cart pc,M_Vendor v,M_Currency c,P_Vendor_Selection vs,M_Tax t  "+
					" where t.id_tax=qa.id_tax and q.id_quot=qa.id_quot and qa.id_prod=pc.id_prod and v.id_ven=q.id_ven and c.id_curr=q.id_curr and st_quot='Accepted' and vs.id_quot=q.id_quot and q.id_quot='"+id_quot+"'";
			
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
			   
			    sql="select nm_ven,tot,nm_curr,q.id_ven,no_quot from P_Quotation q,M_Vendor v,M_Currency c where c.id_curr=q.id_curr "+
			    		" and  no_ind='"+no_ind+"' and q.id_ven=v.id_ven  and st_quot !='Accepted'";
			    
			    ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
				    metaData = rs.getMetaData();
				     columns = metaData.getColumnCount();
				     jobj2 = new JSONObject();
				    while(rs.next())
				    {
				    	jobj2 = new JSONObject();
				    	for(int i=1;i<=columns;i++)
				    	{
				    		String name=metaData.getColumnName(i);
				    		jobj2.put(name,rs.getString(name));
				    	}
				    		jarr1.put(jobj2);
				    		
			        }
			    
				     sql="select id_ven_select from P_Vendor_Selection where id_quot="+id_quot+"";
        			ps=con.prepareStatement(sql);
        			rs=ps.executeQuery(); 
        			if(rs.next())
        			{
        				jobj.put("id_ven_select", rs.getInt(1));
        			}
        			else
        			{
        				jobj.put("id_ven_select", 0);
        			}
				    
			    jobj.put("data", jarr);
			    jobj.put("RejectedVen", jarr1);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Print_Vendor_Selection.");
		}
		 
		return jobj;
		
		
	}
	
	

	
	public JSONObject DisplayQuotaionForPrintVendorSelection(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select nm_ven,ci.no_ind,no_quot,(replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv, emp.nm_emp as approv_by,emp1.nm_emp as indent_by,q.id_quot from P_Quotation q,P_Create_Indent ci,M_Emp_User emp,M_Emp_User emp1,M_Vendor v  "+
					" where v.id_ven=q.id_ven and q.no_ind=ci.no_ind and q.approv_by=emp.id_emp_user and ci.ind_add_by=emp1.id_emp_user and st_ven_select=1 and st_quot='Accepted' "+temp+"";
		}
		else
		{
			sql = "select nm_ven,ci.no_ind,no_quot,(replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv, emp.nm_emp as approv_by,emp1.nm_emp as indent_by,q.id_quot from P_Quotation q,P_Create_Indent ci,M_Emp_User emp,M_Emp_User emp1,M_Vendor v  "+
					" where v.id_ven=q.id_ven and q.no_ind=ci.no_ind and q.approv_by=emp.id_emp_user and ci.ind_add_by=emp1.id_emp_user and st_ven_select=1 and st_quot='Accepted'";
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
			System.out.println("sql error in Print_Vendor_Selection.");
		}
		 
		return jobj;
	}
}