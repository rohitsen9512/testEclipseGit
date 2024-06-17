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


public class I_Accept_Reject extends HttpServlet {
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
		    
		String action = "",id="0",id_inventory_m="",dt_to="",dt_frm="",status_approv="",remarks="";
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_inventory_m") !=null)
		{
			id_inventory_m = request.getParameter("id_inventory_m");
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
		if(request.getParameter("remarks") !=null)
		{
			remarks = request.getParameter("remarks");
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
			temp = " and dt_grn <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_grn >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_grn >= '"+dt_frm+"' and dt_grn <= '"+dt_to+"'";
		}
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "Display":
	            	jobj = DisplayAssetOfParticularInvoiceForAcceptreject(temp);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateGrnForAcceptReject(remarks,id,status_approv,id_emp_user);	
	                break;
	                
	            case "Edit":
	            	jobj = GrnDtaForEdit(id);	
	                break;
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in I_Accept_Reject.");
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
	
	
	public JSONObject GrnDtaForEdit(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
			String sql="select * from I_Grn where  id_inventory_grn = "+id+"";
			
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
			System.out.println("sql error in I_Accept_Reject.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject UpdateGrnForAcceptReject(String remarks , String id , String status_approv,int id_emp_user)
	{
		
		int j=0;
		JSONObject json=new JSONObject();
		String dynColumn="approv_by";
		if(status_approv.equals("2"))
		{
			dynColumn="reject_by";
		}
		
		
		String query="update I_Grn set remarks ='"+remarks+"',"+dynColumn+"="+id_emp_user+",status_approv ="+status_approv+" where id_inventory_grn="+id+"";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
				if(status_approv.equals("2"))
				{
				j=0;
				int id_inventory=0,id_inventory_m=0,TotQty=0;
				String	sql ="select id_inventory_m,id_inventory from I_Grn where id_inventory_grn="+id+"";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				 if(rs.next())
				 	{
					 id_inventory_m = rs.getInt(1);
					 id_inventory = rs.getInt(2);
				 	}
				 	sql ="select qty from I_Inventory where id_inventory="+id_inventory+"";
					ps=con.prepareStatement(sql);
					rs=ps.executeQuery();
					 if(rs.next())
					 	{
						 TotQty = rs.getInt(1);
						 
					 	}
				 
				sql ="select  qty_recv from I_Grn where id_inventory_grn="+id+" and status_approv =2";
				int TotRecvQty = 0;
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				 if(rs.next())
				 	{
					 	TotRecvQty = rs.getInt(1);
					 	
					 	if((TotRecvQty - TotQty) == 0)
						 {
					 		sql ="update I_Inventory_Master set rec_status_grn =0 where id_inventory_m = "+id_inventory_m+"";
					 		ps=con.prepareStatement(sql);
							j=ps.executeUpdate();
							if(j > 0)
							{
								j=0;
								sql ="update I_Inventory set rec_status_grn =0 where id_inventory = "+id_inventory+"";
						 		ps=con.prepareStatement(sql);
								j=ps.executeUpdate();
								if(j > 0)
								{
									j=1;
								}
							}
						 }
					 	else
					 	{
					 		j=0;
							sql ="update I_Inventory set rec_status_grn =0 where id_inventory = "+id_inventory+"";
							PreparedStatement	ps1=con.prepareStatement(sql);
							j=ps1.executeUpdate();
							if(j > 0)
							{
								j=1;
							}
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
	
	
	
	/*public JSONObject UpdateGrnForAcceptReject(String remarks , String id , String status_approv)
	{
		
		int j=0;
		JSONObject json=new JSONObject();
		
		String query="update I_Grn set remarks ='"+remarks+"',status_approv ="+status_approv+" where id_inventory_grn="+id+"";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
			}
		
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}*/
	
	
	public JSONObject DisplayAssetOfParticularInvoiceForAcceptreject(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String  sql ="";
		if(!temp.equals(""))
		  sql ="select *,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv from I_Grn where status_approv = 0 "+temp+"";
		else
			 sql ="select *,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn,(replace(convert(NVARCHAR, dt_dc, 103), ' ', '-')) as dtDc,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv from I_Grn where status_approv = 0";
			 
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
			System.out.println("sql error in I_Accept_Reject.");
		}
		 
		return jobj;
		
		
	}
	
	
	}
