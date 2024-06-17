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


public class FinancialApproval extends HttpServlet {
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
		String temp="";
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
	            	jobj = DisplayQuotaionForVendorSelection(temp);	
	                break;
	                
	           
	            case "Edit":
	            	jobj = PreviewVendorSelection(no_quot,no_ind,id_quot);	
	                break;
	                
	            case "Save":
	            	System.out.println("dfsdgdhng");
	                int j=0;
	        		String query="";
	        		
	        		try
	        		{
	        			
	        			
						
						  String Approve = request.getParameter("Approve");
						  String financial_rmrk = request.getParameter("financial_rmrk");
							
						  
						  
						  query="update p_quotation set financial_approval='"+Approve+"',financial_rmrk='"+financial_rmrk+"' where id_quot="+id_quot+""; 
						  System.out.println(query);
						  		stmt=con.createStatement();
						  		stmt.executeUpdate(query);
						  j=1;
						  
						 
	       		            
	        				
						  jobj.put("data", j);
	        			
	        		}
	        		catch(Exception e)
	        		{
	        			System.out.println("Sql Error...."+e);
	        		}
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
	
	
	public JSONObject PreviewVendorSelection(String no_quot,String no_ind,String id_quot)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		JSONArray jarr2 = new JSONArray();
		
		try
		{
			String sql="select id_ven_select from P_Vendor_Selection where no_quot='"+no_quot+"'";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery(); 
			if(!rs.next())
			{
				 sql="select qa.*,nm_model,cd_model,tot,nm_ven,q.id_ven,q.no_quot,t1.nm_tax as nm_tax1,t2.nm_tax as nm_tax2 from P_Quotation q,P_Quotation_Asset qa,M_Model pc,M_Vendor v,M_Tax t1,M_Tax t2 "+
							" where t1.id_tax=qa.id_tax1 and t2.id_tax=qa.id_tax2 and q.id_quot=qa.id_quot and qa.id_prod=pc.id_model and v.id_ven=q.id_ven  and st_quot='Accepted' and q.id_quot="+id_quot+"";

			}
			else
			{
				sql="select qa.*,nm_model,cd_model,q.tot,nm_ven,q.id_ven,q.no_quot,bidding,lowest_bid,recurring_order, "+
						"compt_bid,other,other_specify from P_Quotation q,P_Quotation_Asset qa,M_Model pc,M_Vendor v, "+
						"P_Vendor_Selection vs where q.id_quot=qa.id_quot and qa.id_prod=pc.id_model and "+
						"v.id_ven=q.id_ven and q.id_quot=vs.id_quot and st_quot='Accepted' and q.id_quot="+id_quot+"";

			}
			 sql="select qa.*,nm_model,cd_model,tot,nm_ven,q.id_ven,q.no_quot,t1.nm_tax as nm_tax1,t2.nm_tax as nm_tax2 from P_Quotation q,P_Quotation_Asset qa,M_Model pc,M_Vendor v,M_Tax t1,M_Tax t2 "+
						" where t1.id_tax=qa.id_tax1 and t2.id_tax=qa.id_tax2 and q.id_quot=qa.id_quot and qa.id_prod=pc.id_model and v.id_ven=q.id_ven  and st_quot='Accepted' and q.id_quot="+id_quot+"";

			 System.out.println(sql);
						
			 			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			   ResultSetMetaData metaData = rs.getMetaData();
			    int columns = metaData.getColumnCount();
			    JSONObject jobj2 = new JSONObject();
			    JSONObject jobj3 = new JSONObject();
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
			    System.out.println(sql);
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
				    
        			 sql="select nm_ven,tot,score from p_quotation pq,m_vendor ven where pq.id_ven=ven.id_ven  and no_ind='"+no_ind+"'";
     			    System.out.println(sql);
     			    ps=con.prepareStatement(sql);
     				rs=ps.executeQuery();
     				
     				    metaData = rs.getMetaData();
     				     columns = metaData.getColumnCount();
     				    jobj3 = new JSONObject();
     				    while(rs.next())
     				    {
     				    	jobj3 = new JSONObject();
     				    	for(int i=1;i<=columns;i++)
     				    	{
     				    		String name=metaData.getColumnName(i);
     				    		jobj3.put(name,rs.getString(name));
     				    	}
     				    		jarr2.put(jobj3);
     				    		
     			        }
        			
     					JSONArray jarr3 = new JSONArray();
     				    double budg_allo=0.0,budg_util=0.0;
     				    sql="select annual_bud,annual_bud_done from M_Budget b,M_Finance_Year fy where b.id_finance=fy.id_fincance and active_year=1 ";
     					ps=con.prepareStatement(sql);
            			rs=ps.executeQuery(); 
            			if(rs.next())
     					{
     						budg_allo=rs.getDouble(1);
     						budg_util=rs.getDouble(2);
     					}
     					JSONObject jobj4 = new JSONObject();
     					jobj4.put("budg_allo",budg_allo);
     					jobj4.put("budg_util",budg_util);
     					jobj4.put("budg_rem",(budg_allo-budg_util));
     					jarr3.put(jobj4);  
     					jobj.put("budget", jarr3);
        			
     				   jobj.put("bidlw", jarr2);
			    jobj.put("data", jarr);
			    jobj.put("RejectedVen", jarr1);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		 
		return jobj;
		
		
	}
	
	

	
	public JSONObject DisplayQuotaionForVendorSelection(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
	
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select nm_ven,ci.no_ind,no_quot,id_quot,(replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv, emp.nm_emp as approv_by,emp1.nm_emp as indent_by,q.id_quot from P_Quotation q,P_Create_Indent ci,M_Emp_User emp,M_Emp_User emp1,M_Vendor v  "+
					" where v.id_ven=q.id_ven and q.no_ind=ci.no_ind and q.approv_by=emp.id_emp_user and ci.ind_add_by=emp1.id_emp_user and st_ven_select=1 and st_quot='Accepted' and financial_approval='Waiting' "+temp+"";
		}
		else
		{
			sql = "select nm_ven,ci.no_ind,no_quot,id_quot,(replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv, emp.nm_emp as approv_by,emp1.nm_emp as indent_by,q.id_quot from P_Quotation q,P_Create_Indent ci,M_Emp_User emp,M_Emp_User emp1,M_Vendor v  "+
					" where v.id_ven=q.id_ven and q.no_ind=ci.no_ind and q.approv_by=emp.id_emp_user and ci.ind_add_by=emp1.id_emp_user and st_quot='Accepted' and st_ven_select=1 and financial_approval='Waiting' ";
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
