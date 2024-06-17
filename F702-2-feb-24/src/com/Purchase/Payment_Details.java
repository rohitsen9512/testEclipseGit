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


public class Payment_Details extends HttpServlet {
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
		    
		String action = "",rem_po_val="",amount="",id_po="0";
		
		if(request.getParameter("rem_val_po") !=null)
		{
			rem_po_val = request.getParameter("rem_val_po");
		}
		if(request.getParameter("amount") !=null)
		{
			amount = request.getParameter("amount");
		}
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
		}
		
		try
		{
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	
	            	jobj = AddPaymentDetails(map,id_emp_user,id_po,rem_po_val,amount,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayPOForPayment(id_po);	
	                break;
	                
	            case "Edit":
	            	jobj = EditPOForPayment(id_po);	
	                break;
	                
	            case "DropDownResult":
	            	jobj = DropDownResult();	
	                break; 
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Payment_Details.");
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
	
	
	
	public JSONObject DropDownResult()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select no_po,id_po from P_Purchase_Order po where st_pmt=0 and st_approv4='Accepted' and cancel=0";
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
			System.out.println("sql error in Payment_Details.");
		}
		 
		return jobj;
		
		
	}
	 
	
	 public JSONObject EditPOForPayment(String id_po)
		{
			JSONObject jobj = new JSONObject();
			JSONArray jarr = new JSONArray();
			try
			{
				String sql="select id_po from P_Payment where id_po="+id_po+"";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					sql="select po.no_po,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dt_po,gr_tot,gr_tot*rate as po_val,rem_val_po as rem_val,rate from P_Payment pmt,P_Payment_details pmtd, "+
							" P_Purchase_Order po,M_Exchange_Rate er,M_Currency c "+
							" where pmt.id_pmt=pmtd.id_pmt and po.id_po=pmt.id_po and c.id_curr=po.id_curr and c.id_curr=er.id_curr "+
							 " and po.id_po="+id_po+"  GROUP by amount,po.no_po,po.dt_po,gr_tot,rate,rem_val_po";
				}
				else
				{
					sql="select po.no_po,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dt_po,gr_tot,gr_tot*rate as po_val,rem_val_po as rem_val,rate from"+
					" P_Purchase_Order po,M_Exchange_Rate er,M_Currency c where c.id_curr=po.id_curr and "+
					"c.id_curr=er.id_curr and id_po="+id_po+"";
				}
				  
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
				System.out.println("sql error in Payment_Details.");
			}
			 
			return jobj;
			
			
		}
	 public JSONObject DisplayPOForPayment(String id_po)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
			String sql="";
			if(id_po.equals(""))
			  sql="select id_po,no_po,no_sap,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,gr_tot*rate as gr_tot,rem_val_po,nm_ven from P_Purchase_Order po,M_Vendor v,M_Currency c,M_Exchange_Rate er "+
					  " where v.id_ven=po.id_ven and po.id_curr=c.id_curr and c.id_curr=er.id_curr and st_pmt=0 and st_approv4='Accepted' and cancel=0";
			else
				 sql="select id_po,no_po,no_sap,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,gr_tot*rate as gr_tot,rem_val_po,nm_ven from P_Purchase_Order po,M_Vendor v,M_Currency c,M_Exchange_Rate er "+
						 " where v.id_ven=po.id_ven and po.id_curr=c.id_curr and c.id_curr=er.id_curr and st_pmt=0 and st_approv4='Accepted' and cancel=0 and po.id_po="+id_po+"";
			
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
			System.out.println("sql error in Payment_Details.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddPaymentDetails(HashMap<String, String> map, int id_emp_user,
			String id_po,String rem_po_val,String amount,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0,id_pmt=0;
		JSONObject json=new JSONObject();
		String sql = "select id_pmt from P_Payment where id_po='"+id_po+"'";
		try 
		{
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(!rs.next())
			{
				
				rs = Common.GetColumns("P_Payment",  request);
				while (rs.next())
				{
				
					if(!rs.getString(1).equals("id_pmt"))
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
				
				 sql="insert into P_Payment("+colName+",add_by) values("+value+","+id_emp_user+")";
					 
				 stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs1 = stmt.getGeneratedKeys();
		            while (rs1.next()) {
		            	id_pmt = rs1.getInt(1);
		            } 
				
			}
			else
			{
				id_pmt=rs.getInt(1);
			}
		
			colName="";value="";
			rs = Common.GetColumns("P_Payment_Details",  request);
			while (rs.next())
			{
			
				if(!rs.getString(1).equals("id_pmt_details"))
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
			j=0;
			 sql="insert into P_Payment_Details("+colName+",id_pmt) values("+value+","+id_pmt+")";
			 
			 ps=con.prepareStatement(sql);
				j=ps.executeUpdate();
				if(j > 0)
				{
					if(Double.parseDouble(rem_po_val) == Double.parseDouble(amount))
					{
						j=0;
						sql="update P_Purchase_Order set st_pmt=1,rem_val_po="+((Double.parseDouble(rem_po_val)) - (Double.parseDouble(amount)))+" where id_po="+id_po+"";
						stmt=con.createStatement();
						 stmt.executeUpdate(sql);
						 j=1;
					}
					else
					{
						j=0;
						sql="update P_Purchase_Order set rem_val_po="+((Double.parseDouble(rem_po_val)) - (Double.parseDouble(amount)))+" where id_po="+id_po+"";
						stmt=con.createStatement();
						 stmt.executeUpdate(sql);
						 j=1;
					}
					
				}
			
			json.put("data", j);	
		}
		catch(Exception e)
		{
			System.out.println("Error in Payment_Details");
		}
		
		
		
		return json;
	}

}
