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


public class Vendor_Selection extends HttpServlet {
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
	            	
	                int j=0,temp1=0,id_ven_select=0;
	        		String query="",colName="",value="",colName1="",value1="";
	        		
	        		try
	        		{
	        			String sql="select id_quot from P_Vendor_Selection where id_quot="+id_quot+"";
	        			ps=con.prepareStatement(sql);
	        			rs=ps.executeQuery();
	        			if(rs.next())
	        			{
	        				temp1=1;
	        			}
	        			if(temp1 == 0)
	        			{
	        			 
	        			 rs = Common.GetColumns("P_Vendor_Selection",  request);
	        				while (rs.next())
	        				{
	        					if(rs.getString(1) !="id_ven_select")
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
	        				
	        				rs = Common.GetColumns("P_Vendor_Binding",  request);
	        				while (rs.next())
	        				{
	        					if(rs.getString(1) !="id_bid")
	        					{
	        						    if (map.containsKey(rs.getString(1)))
	        						    {
	        						    	if(colName1.equals(""))
	        						    	{
	        							    	colName1 += rs.getString(1);
	        							    	value1 += "'"+ map.get(rs.getString(1))+"'";
	        						    	}
	        						    	else
	        						    	{
	        						    		colName1 +=","+ rs.getString(1);
	        							    	value1 +=", '"+ map.get(rs.getString(1))+"'";
	        						    		
	        						    	}
	        						    }
	        					  }
	        				}
	        			
	        				query="insert into P_Vendor_Selection ("+colName+") values("+value+")";	
	        				stmt=con.createStatement();
	       				 stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
	       				 
	       				 rs = stmt.getGeneratedKeys();
	       		            if (rs.next()) {
	       		            	id_ven_select = rs.getInt(1);
	       		            } 
	       		            if(id_ven_select !=0)
	       		            {
	       		            	String reject_no_quot[] = request.getParameterValues("reject_no_quot");
	       		            	String final_bid[] = request.getParameterValues("final_bid");
	       		            	String id_ven[] = request.getParameterValues("reject_id_ven");
	       		            	
	       		            	if(reject_no_quot != null)
	       		            	{
	       		            	for(int i=0;i<reject_no_quot.length;i++)
	       		            	{
	       		            		
		       		            	query="insert into  P_Vendor_Binding (id_ven_select,final_bid,no_quot,id_quot,id_ven) values('"+id_ven_select+"','"+final_bid[i]+"','"+reject_no_quot[i]+"',"+id_quot+",'"+id_ven[i]+"')";	
		       		            	stmt=con.createStatement();
		       		            	stmt.executeUpdate(query);
	       		            	}
		        				j=0;
    	        				query="update P_Vendor_Selection set bidding="+bidding+",lowest_bid="+lowest_bid+",recurring_order="+recurring_order+",compt_bid="+compt_bid+",other="+other+",other_specify='"+other_specify+"' where id_ven_select="+id_ven_select1+"";	
    	        				stmt=con.createStatement();
    	        				stmt.executeUpdate(query);
    	        				j=0;
    	        				query="update P_Quotation set st_ven_select=1 where id_quot="+id_quot+"";	
    	        				stmt=con.createStatement();
    	        				stmt.executeUpdate(query);
    	        				j=1;
    	        				jobj.put("data", j);
	       		            	}
	       		            	else
	       		            	{
	       		            		j=0;
	    	        				query="update P_Vendor_Selection set bidding="+bidding+",lowest_bid="+lowest_bid+",recurring_order="+recurring_order+",compt_bid="+compt_bid+",other="+other+",other_specify='"+other_specify+"' where id_ven_select="+id_ven_select1+"";	
	    	        				stmt=con.createStatement();
	    	        				stmt.executeUpdate(query);
	    	        				j=0;
	    	        				query="update P_Quotation set st_ven_select=1 where id_quot="+id_quot+"";	
	    	        				stmt=con.createStatement();
	    	        				stmt.executeUpdate(query);
	    	        				j=1;
	    	        				jobj.put("data", j);
	       		            	}
	       		            
	       		            }
	       		            
	        				
	        			}
	        			else
	        			{
	        				j=0;
	        				query="update P_Vendor_Selection set bidding="+bidding+",lowest_bid="+lowest_bid+",recurring_order="+recurring_order+",compt_bid="+compt_bid+",other="+other+",other_specify='"+other_specify+"' where id_ven_select="+id_ven_select1+"";	
	        				stmt=con.createStatement();
	        				stmt.executeUpdate(query);
	        				j=0;
	        				query="update P_Quotation set st_ven_select=1 where id_quot="+id_quot+"";	
	        				stmt=con.createStatement();
	        				stmt.executeUpdate(query);
	        				j=1;
	        				jobj.put("data", j);
	        				
	        			}
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
			 System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery(); 
			if(!rs.next())
			{
				 sql="select qa.*,nm_model,cd_model,tot,nm_ven,q.id_ven,q.no_quot,t1.nm_tax as nm_tax1,t2.nm_tax as nm_tax2 from P_Quotation q,P_Quotation_Asset qa,M_Model pc,M_Vendor v,M_Tax t1,M_Tax t2 "+
							" where t1.id_tax=qa.id_tax1 and t2.id_tax=qa.id_tax2 and q.id_quot=qa.id_quot and qa.id_prod=pc.id_model and v.id_ven=q.id_ven  and st_quot='Accepted' and q.id_quot="+id_quot+"";

			}
			else
			{
				sql="select qa.*,nm_model,q.tot,nm_ven,q.id_ven,q.no_quot,bidding,lowest_bid,recurring_order, "+
						"compt_bid,other,other_specify from P_Quotation q,P_Quotation_Asset qa,M_Model pc,M_Vendor v, "+
						"P_Vendor_Selection vs where q.id_quot=qa.id_quot and qa.id_prod=pc.id_model and "+
						"v.id_ven=q.id_ven  and st_quot='Accepted' and q.id_quot="+id_quot+"";

			}
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
				     System.out.println(sql);
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
				    
        			 sql="select nm_ven,tot,score,id_quot from p_quotation pq,m_vendor ven where pq.id_ven=ven.id_ven  and no_ind='"+no_ind+"'";
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
					" where v.id_ven=q.id_ven and q.no_ind=ci.no_ind and q.approv_by=emp.id_emp_user and ci.ind_add_by=emp1.id_emp_user and st_ven_select=0 and st_quot='Accepted' "+temp+" ";
		}
		else
		{
			sql = "select nm_ven,ci.no_ind,no_quot,id_quot,(replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv, emp.nm_emp as approv_by,emp1.nm_emp as indent_by,q.id_quot from P_Quotation q,P_Create_Indent ci,M_Emp_User emp,M_Emp_User emp1,M_Vendor v  "+
					" where v.id_ven=q.id_ven and q.no_ind=ci.no_ind and q.approv_by=emp.id_emp_user and ci.ind_add_by=emp1.id_emp_user and st_ven_select=0 and  st_quot='Accepted' ";
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
	
	
	}
