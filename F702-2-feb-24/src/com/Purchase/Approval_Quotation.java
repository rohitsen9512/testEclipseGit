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


public class Approval_Quotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	protected void doPost(
		
		
		HttpServletRequest request
		
		
		
		
		
		
		, HttpServletResponse response) throws ServletException, IOException {
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
		String action = "",id="0",dt_to="",dt_recv="",id_quot="",dt_frm="",status_approv="",no_ind="",dt_approv="",acceptQuotNo="";
		
		if(request.getParameter("dt_recv") !=null)
		{
			dt_recv = request.getParameter("dt_recv");
		}
		if(request.getParameter("id_quot") !=null)
		{
			id_quot = request.getParameter("id_quot");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("dt_approv") !=null)
		{
			dt_approv = request.getParameter("dt_approv");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("acceptQuotNo") !=null)
		{
			acceptQuotNo = request.getParameter("acceptQuotNo");
		}
		
		
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("status_approv") !=null)
		{
			status_approv = request.getParameter("status_approv");
			
		}
		if(request.getParameter("no_ind") !=null)
		{
			no_ind = request.getParameter("no_ind");
		}
		String countID[] = request.getParameterValues("rejectQuotNo");
		
		String temp="";
		try
		{
			Date currDate = new Date();
			
			if(!dt_recv.equals(""))
			{
				dt_recv = dateFormatNeeded.format(userDateFormat.parse(dt_recv));
			}
			if(!dt_approv.equals(""))
			{
				dt_approv = dateFormatNeeded.format(userDateFormat.parse(dt_approv));
			}
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
		
		if(!dt_to.equals(""))
		{
			temp = " and dt_rec_quot <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_rec_quot >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_rec_quot >= '"+dt_frm+"' and dt_rec_quot <= '"+dt_to+"'";
		}
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "Display":
	            	jobj = DisplayQuotationForApproval(temp , id_emp_user);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateQuotationForAcceptReject(dt_approv,acceptQuotNo,status_approv,id_emp_user,countID,no_ind);	
	                break;
	                
	            case "Edit":
	            	jobj = QuotationDetails(no_ind);	
	                break;
	            case "CheckDate":
	            	jobj = CheckApprovRejectDate(dt_recv,acceptQuotNo);	
	                break;
	                
	            case "ViewDisplay":
	            	jobj = ViewDisplayQuotationForApproval(temp);	
	                break;
	              
	            case "ViewEdit":
	            	jobj = ViewQuotationDetails(no_ind);	
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
	
	
	public JSONObject CheckApprovRejectDate(String dt_recv,String id_quot)
	{
		JSONObject json=new JSONObject();
		
		String query = "select (replace(convert(NVARCHAR, dt_rec_quot, 103), ' ', '-')) as dt_rec_quot  from P_Quotation where dt_rec_quot >'"+dt_recv+"' and id_quot='"+id_quot+"'";
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				
				json.put("data",0);
				json.put("dt_rec_quot",rs.getString(1));
			}
			else
			{
				json.put("data",1);
				
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	public JSONObject QuotationDetails(String no_ind)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
			String sql="select qa.*,(replace(convert(NVARCHAR, qa.dt_rec_quot, 103), ' ', '-')) as dtRecQuot ,nm_curr,nm_assetdiv,nm_s_assetdiv,nm_prod,nm_ven,no_quot,t_c_quot,tot,nm_tax,nm_upload_file from M_Vendor v, P_Quotation q,P_Quotation_Asset qa,M_Asset_Div ad,M_Subasset_Div sad,M_Prod_Cart pc,M_Currency c,M_Tax t "+
					" where t.id_tax=qa.id_tax and q.id_quot=qa.id_quot and qa.id_grp=ad.id_assetdiv and qa.id_sgrp=sad.id_s_assetdiv and qa.id_prod=pc.id_prod "+
					" and q.id_curr=c.id_curr and q.id_ven=v.id_ven and no_ind = '"+no_ind+"' and st_quot='No' order by id_quot ASC";
			
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
			   
			    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Approval_Quotation.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject UpdateQuotationForAcceptReject(String dt_approv , String id , String status_approv,int id_emp_user,String countID[],String no_ind)
	{
		
		int j=0;
		String query="",id_quot="";
		JSONObject json=new JSONObject();
		if(status_approv.equals("Accepted"))
		 query="update P_Quotation set dt_approv ='"+dt_approv+"',st_quot ='"+status_approv+"',approv_by="+id_emp_user+" where id_quot="+id+"";
		else
		{
			for(int i=0; i < countID.length;i++)
			{
				if(id_quot.equals(""))
				{
					id_quot=countID[i];
				}
				else
				{
					id_quot +=","+countID[i];
				}
			}
			query="update P_Quotation set dt_approv ='"+dt_approv+"',st_quot ='"+status_approv+"',approv_by="+id_emp_user+" where id_quot in ("+id_quot+")";
		}
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
				if(status_approv.equals("Accepted"))
				{
					j=0;
					query="update P_Quotation set st_quot_flag =1 where no_ind='"+no_ind+"'";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						j=0;
						query="update P_Req_Quot_Vendor set st_rec_quot ='Yes' where no_ind='"+no_ind+"'";
						ps=con.prepareStatement(query);
						j=ps.executeUpdate();
						if(j > 0)
						{
							j=1;
						}
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
	
	
	public JSONObject DisplayQuotationForApproval(String temp , int id_emp_user)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select distinct(ci.no_ind), (replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-')) as dt_ind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "+
					" where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot and id_apprv="+id_emp_user+" and st_quot_flag =0  and st_quot ='No' "+temp+"";
		}
		else
		{
			sql = "select distinct(ci.no_ind), (replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-')) as dt_ind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "+
					" where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot and id_apprv="+id_emp_user+" and st_quot_flag =0  and st_quot ='No'";
		}
			 
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
			System.out.println("sql error in Approval_Quotation.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject ViewDisplayQuotationForApproval(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select distinct(ci.no_ind), (replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-')) as dt_ind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "+ 
					" where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot and st_quot_flag =1  and st_quot ='Accepted' and st_po='No' "+temp+" ";
		}
		else
		{
			sql = "select distinct(ci.no_ind), (replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-')) as dt_ind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "+ 
					" where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot and st_quot_flag =1  and st_quot ='Accepted' and st_po='No'";
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
	
	public JSONObject ViewQuotationDetails(String no_ind)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
			String sql="select qa.*,(replace(convert(NVARCHAR, qa.dt_rec_quot, 103), ' ', '-')) as dtRecQuot ,nm_assetdiv,nm_s_assetdiv,nm_model,nm_ven,no_quot,t_c_quot,tot,t1.nm_tax,t2.nm_tax,nm_upload_file"
					+ " from M_Vendor v, P_Quotation q,P_Quotation_Asset qa,M_Asset_Div ad,M_Subasset_Div sad,M_Model pc,M_Tax t1,M_Tax t2"
					+ "  where t1.id_tax=qa.id_tax1 and t2.id_tax=qa.id_tax2 and q.id_quot=qa.id_quot and qa.id_grp=ad.id_assetdiv and qa.id_sgrp=sad.id_s_assetdiv and qa.id_prod=pc.id_model  and q.id_ven=v.id_ven and no_ind = '"+no_ind+"' and st_quot='Accepted' and st_po='No' order by id_quot ASC";
					
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
			    		String name=metaData.getColumnName(i);
			    		jobj2.put(name,rs.getString(name));
			    	}
			    		jarr.put(jobj2);
			    		
		        }
			   
			    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Approval_Quotation." +e);
		}
		 
		return jobj;
		
		
	}
	
	
	}
