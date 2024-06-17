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


public class Approval_Purchase_Order2 extends HttpServlet {
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
		    
		    
		String action = "",id="0",dt_to="",dt_frm="",status_approv="",id_po="",dt_approv="",acceptQuotNo="",typ_po="";
		
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
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
		}
		if(request.getParameter("typ_po") !=null)
		{
			typ_po = request.getParameter("typ_po");
		}
		String countID[] = request.getParameterValues("rejectQuotNo");
		try
		{
		Date currDate = new Date();
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
		String temp="";
		if(!dt_to.equals(""))
		{
			temp = " and dt_po <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_po >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_po >= '"+dt_frm+"' and dt_po <= '"+dt_to+"'";
		}
		/*if(!typ_po.equals(""))
		{
			temp +=" and typ_po ='"+typ_po+"'";
		}*/
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "Display":
	            	jobj = DisplayPurchaseOrderForApproval(temp);	
	                break;
	                
	            case "Update":
	            	jobj = UpdatePOForAcceptReject(id_po,status_approv,id_emp_user,dt_approv);	
	                break;
	                
	            case "Edit":
	            	jobj = PurchaseOrderDataForApproval(id_po);	
	                break;
	            case "CheckDate":
	            	jobj = CheckApprovRejectDate(dt_approv,id_po);	
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
	
	
	public JSONObject CheckApprovRejectDate(String dt_approv , String id_po)
	{
		JSONObject json=new JSONObject();
		
		String query = "select (replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv  from P_Purchase_Order where dt_approv >'"+dt_approv+"' and id_po='"+id_po+"'";
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				
				json.put("data",0);
				json.put("dt_approv",rs.getString(1));
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
	
	public JSONObject PurchaseOrderDataForApproval(String id_po)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
			String sql="select emp.nm_emp,po.*,poa.*,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_recv, 103), ' ', '-')) as dtRecv,nm_prod,nm_curr,nm_tax from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Currency c,M_Prod_Cart pc,M_Tax t,M_Emp_User emp  "+
					" where t.id_tax=poa.id_tax and po.id_po=poa.id_po and po.id_curr=c.id_curr and poa.id_prod=pc.id_prod and po.id_po="+id_po+" and emp.id_emp_user=po.approv_by";
			
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
	
	
	
	public JSONObject UpdatePOForAcceptReject(String id_po,String status_approv,int id_emp_user,String dt_approv)
	{
		 Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int j=0;
		String query="",id_quot="";
		JSONObject json=new JSONObject();
		
		 query="update P_Purchase_Order set dt_approv2 ='"+dt_approv+"',st_approv2 ='"+status_approv+"',approv_by2="+id_emp_user+" where id_po="+id_po+"";
		
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
				json.put("data",j);
			}
			else
			{
				json.put("data",0);
			}
			/*if(status_approv.equals("Accepted"))
			{
				String id_bud="";
				double annual_bud_done=0.0;
				query="select sum(tot_prc)*rate+sum(tot_prc)*rate*tax_per/100 as tot_prc,poa.id_cc,id_bud,annual_bud_done,c.id_curr,er.rate,po.insurance*rate,po.frieght*rate "+
						" from P_Purchase_Order_Asset poa,M_Budget b,M_Currency c ,P_Purchase_Order po,M_Exchange_Rate er "+
						" where poa.id_cc=b.id_cc and poa.id_po="+id_po+" and c.id_curr=po.id_curr and po.id_po=poa.id_po and c.id_curr=er.id_curr "+
						" group by poa.id_cc,id_bud,annual_bud_done,c.id_curr,er.rate,tax_per,po.insurance,po.frieght ";
			
				query="select gr_tot*rate as tot_prc,poa.id_cc,id_bud,annual_bud_done,c.id_curr,er.rate "+
						" from P_Purchase_Order_Asset poa,M_Budget b,M_Currency c ,P_Purchase_Order po,M_Exchange_Rate er "+
						" where poa.id_cc=b.id_cc and poa.id_po="+id_po+" and c.id_curr=po.id_curr and po.id_po=poa.id_po and c.id_curr=er.id_curr "+
						" group by poa.id_cc,id_bud,annual_bud_done,c.id_curr,er.rate,tax_per,gr_tot ";
			
				stmt=con.createStatement();
				rs=stmt.executeQuery(query);
				while(rs.next()){
					id_bud=rs.getString(3);
					annual_bud_done=rs.getDouble(1) + rs.getDouble(4);
					query ="update M_Budget set annual_bud_done="+annual_bud_done+" where id_bud="+id_bud+"";
					ps=con.prepareStatement(query);
	    			j=ps.executeUpdate();
	    			if(j > 0)
		    			{
	    					json.put("data",1);
		    			}
		    			else
		    			{
		    				json.put("data",0);
		    			}
				}
				
				
			}
			*/
			
			
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	public JSONObject DisplayPurchaseOrderForApproval(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select distinct(po.id_po),no_po,no_sap,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,nm_emp,nm_ven,no_quot from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Emp_User emp,M_Vendor v "+
					" where po.id_po=poa.id_po and po.id_ven=v.id_ven and po.add_by=emp.id_emp_user and st_approv='Accepted' and st_approv2='Waiting' and po.cancel=0 "+temp+"";
		}
		else
		{
			sql = "select distinct(po.id_po),no_po,no_sap,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,nm_emp,nm_ven,no_quot from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Emp_User emp,M_Vendor v "+
					" where po.id_po=poa.id_po and po.id_ven=v.id_ven and po.add_by=emp.id_emp_user and st_approv='Accepted' and st_approv2='Waiting' and po.cancel=0 ";
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
