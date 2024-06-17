package com.Purchase;

import java.io.IOException;
import java.io.PrintWriter;
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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


public class Preview_Mail extends HttpServlet {
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
		
		String action = "",id_req_quot_ven="",id="0",id_req="",confirm="1",dt_to="",no_ind="",dt_frm="",value="",id_ven="",no_req_val="";
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id_ven") !=null)
		{
			id_req_quot_ven = request.getParameter("id_ven");
		}
		if(request.getParameter("no_ind") !=null)
		{
			no_ind = request.getParameter("no_ind");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("no_req_val") !=null)
		{
			no_req_val = request.getParameter("no_req_val");
		}
		if(request.getParameter("id_req") !=null)
		{
			id_req = request.getParameter("id_req");
		}
		if(request.getParameter("confirm") !=null)
		{
			confirm = request.getParameter("confirm");
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
		if(request.getParameter("id_ven") !=null)
		{
			id_ven = request.getParameter("id_ven");
		}
		try
		{
			int id_emp_user = (int)session.getAttribute("id_emp_user");
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
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		       
		        case "DisplayPreviewDetails":
	            	jobj = DisplayPreviewDetails(no_ind,id_ven);	
	                break;
	            case "Display":
	            	jobj = DisplayPreviewQuotation(id,dt_frm,dt_to,id_emp_user);	
	                break;
	           
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Preview_Mail.");
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
	
	
	public JSONObject DisplayPreviewQuotation(String id,String dt_frm,String dt_to,int id_emp_user)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
			stmt=con.createStatement();
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(dt_frm.equals(""))
		{
			dt_frm = "1990-01-01";
		}
		
		if(dt_to.equals(""))
		{
			dt_to = sdf.format(currDate);	
		}
		
		sql ="select distinct(rq.id_req_quot),id_req_quot_ven,rq.no_ind,nm_ven,rq.dt_req_quot,rqv.id_ven,emp.nm_emp from P_request r,M_Emp_User emp, P_Request_Quotation rq,M_Vendor v,P_Request_Quotation_Asset rqa,P_Req_Quot_Vendor rqv "+
				" where rqv.no_ind=rq.no_ind and rqv.id_ven=v.id_ven and st_rec_quot='No' and rq.add_by=emp.id_emp_user and rq.no_ind=rqa.no_ind and st_recv_quot='No' and rq.id_ven=v.id_ven and st_recv_quot='No' and dt_req_quot >=(replace(convert(NVARCHAR, '"+dt_frm+"', 106), ' ', '-')) and dt_req_quot <= (replace(convert(NVARCHAR, '"+dt_to+"', 106), ' ', '-')) order by rq.no_ind";
		System.out.println(sql);
	
	
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	String ProdName="";
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
		    	
		    	rs1=stmt.executeQuery("select nm_prod from M_Prod_Cart pc,P_Request_Quotation_Asset rqa where rqa.id_prod=pc.id_prod and rqa.id_req_quot="+rs.getString(1)+"");
		    	while(rs1.next()){
		    		if(ProdName.equals(""))
		    			ProdName =rs1.getString(1);
		    		else
					ProdName +=","+rs1.getString(1);
				}
		    	jobj2.put("nm_prod",ProdName);
		    	
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Preview_Mail.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayPreviewDetails(String no_ind,String id_ven)
	{
		
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		JSONArray jarr4 = new JSONArray();

		try 
		{
			String sql = "select ci.*,nm_assetdiv,nm_s_assetdiv,nm_model,ia.mfr,ia.description,ia.qty,ia.id_req,ia.id_ind_asst,ia.id_prod,no_req,ia.req_qty from P_Create_Indent ci,P_Indent_Asset ia,P_request req,M_Asset_Div ad,M_Subasset_Div sad,M_Model pc "+
					" where ia.id_req=req.id_req and ci.no_ind=ia.no_ind and ia.id_grp=ad.id_assetdiv and ia.id_sgrp=sad.id_s_assetdiv and ia.id_prod=pc.id_model "+
					" and ci.no_ind='"+no_ind+"'";	
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
				json.put("data",jarr);
				
				String sql1 = "select file_name,nm_com,add1,add2,city,country,pin from M_Company";	
				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				
				   ResultSetMetaData metaData1 = rs.getMetaData();
				    int columns1 = metaData1.getColumnCount();
				    while(rs.next())
				    {
				    	JSONObject jobj2 = new JSONObject();
				    	for(int i=1;i<=columns1;i++)
				    	{
				    		String name=metaData1.getColumnName(i);
				    		jobj2.put(name,rs.getString(name));
				    	}
				    	jarr1.put(jobj2);
				    }
			json.put("company",jarr1);
			sql1 = "select nm_ven,add1,add2,city,country,pin from M_Vendor where id_ven="+id_ven+"";	
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
			 sql = "select id_req_quot_ven from P_Req_Quot_Vendor where id_ven="+id_ven+" and no_ind='"+no_ind+"' and st_rec_quot='No'";	
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			Date currDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MMM/yyyy");
			
			if(rs.next())
			{
				json.put("id_quot",rs.getInt(1));
				json.put("year",sdf.format(currDate));
				json.put("currDate",sdf1.format(currDate));
			}
			
			sql="select quot_t_c from P_Request_Quotation where no_ind='"+no_ind+"'";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("quot_t_c",rs.getString(1));
			}
			else
			{
				json.put("quot_t_c","-");
			}
				
		}
		catch(Exception e)
		{
			System.out.println("Error in Preview_Mail");
		}
			
		return json;
	}
}
