package com.Inventory;

import java.io.File;
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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


public class Invoice_Through_PO_For_Inventory extends HttpServlet {
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
		
		String action = "",id="0",id_po_asst="0",id_po="",qty="",rem_qty="",no_inv="",invoiceId="",dt_to="",dt_frm="",value="",ColName="",id_inv_m="00",strnewName="";
		
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
		if(request.getParameter("invoiceId") !=null)
		{
			invoiceId = request.getParameter("invoiceId");
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
	            case "Save":
	            	
	            	jobj = AddInvoice(map,no_inv,id_emp_user,rem_qty,qty,id,id_po_asst,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayPurchaseOrder(id,temp);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayPurchaseOrder(id,temp);	
	                break;
	                
	            case "DisplayAsset":
	            	jobj = DisplayAssetForParticularPO(id,id_po_asst);	
	                break;
	            case "EditAsset":
	            	jobj = DisplayAssetForParticularPO(id,id_po_asst);	
	                break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in A_Invoice.");
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
	
	public JSONObject DisplayAssetForParticularPO(String id_po,String id_po_asst)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
			String	sql = "";
			if(!id_po.equals("0"))
			{
			sql = "select poa.*,nm_prod,poa.id_grp as id_assetdiv,poa.id_sgrp as id_s_assetdiv from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Prod_Cart pc "+
					" where pc.id_prod=poa.id_prod and po.id_po=poa.id_po and st_approv='Accepted' and rem_qty !=0 and po.cancel=0 and poa.asset_consumable='ConG' and shortclosed=0 and (recv_invoice=0 or recv_invoice=1) and rem_qty !=0 and po.id_po="+id_po+"";
			}else
			{
				sql = "select poa.id_po,id_po_asst,poa.id_grp as id_assetdiv,poa.id_sgrp as id_s_assetdiv,poa.mfr,poa.model,poa.description, "+
						"un_prc,tot_prc,typ_asst,nm_prod as ds_pro,poa.asset_consumable as typ_po,poa.rem_qty,id_tax,tax_per "+
						 "from P_Purchase_Order po , P_Purchase_Order_Asset poa,M_Prod_Cart pc "+
						"where pc.id_prod=poa.id_prod and po.id_po=poa.id_po and poa.id_po_asst="+id_po_asst+"";
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
			System.out.println("sql error in A_Invoice.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject DisplayPurchaseOrder(String id_po ,String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select distinct(po.id_po) as po_id,po.*,(replace(convert(NVARCHAR, po.dt_quot, 103), ' ', '-')) as dtQuot,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtPo,nm_ven,nm_emp from P_Purchase_Order po,P_Purchase_Order_Asset poa, M_Emp_User emp,M_Vendor v where emp.id_emp_user=po.approv_by and v.id_ven=po.id_ven "+ 
					" and po.id_po=poa.id_po and st_approv='Accepted' and po.cancel=0 and rem_qty !=0 and shortclosed=0 and asset_consumable='ConG'  and (recv_invoice=1 or recv_invoice=0) "+temp+"";
		}
		else
		{
			sql = "select distinct(po.id_po) as po_id,po.*,(replace(convert(NVARCHAR, po.dt_quot, 103), ' ', '-')) as dtQuot,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtPo,nm_ven,nm_emp from P_Purchase_Order po,P_Purchase_Order_Asset poa, M_Emp_User emp,M_Vendor v where emp.id_emp_user=po.approv_by and v.id_ven=po.id_ven "+ 
					" and po.id_po=poa.id_po and st_approv='Accepted' and po.cancel=0 and rem_qty !=0 and shortclosed=0 and asset_consumable='ConG'  and (recv_invoice=1 or recv_invoice=0)";
		}
		if(!id_po.equals("0"))
		{
			sql = "select id_po,id_curr,no_po,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dt_po,id_ven,id_loc,po.id_sloc as id_subl from P_Purchase_Order po,M_Subloc sl where sl.id_sloc = po.id_sloc and id_po="+id_po+"";

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
			System.out.println("sql error in A_Invoice.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddInvoice(HashMap<String, String> map,String no_inv , int id_emp_user,String rem_qty,String qty,String id_po,String id_po_asst,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0,id_inv_m=0;
		JSONObject json=new JSONObject();
		String sql = "select id_inventory_m from I_Inventory_Master where no_inv = '"+no_inv+"'";
		try 
		{
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(!rs.next())
			{
				
				rs = Common.GetColumns("I_Inventory_Master",  request);
				while (rs.next())
				{
				
					if(!rs.getString(1).equals("id_inventory_m"))
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
				
				 sql="insert into I_Inventory_Master("+colName+",add_by,id_po) values("+value+","+id_emp_user+","+id_po+")";
					 
				 stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				
				 
				 rs1 = stmt.getGeneratedKeys();
		            while (rs1.next()) {
		            	id_inv_m = rs1.getInt(1);
		            } 
		            stmt.executeUpdate("update P_Purchase_Order set recv_invoice=1 where id_po="+id_po+"");
			}
			else
			{
				id_inv_m=rs.getInt(1);
			}
		
		}
		catch(Exception e)
		{
			System.out.println("Error in A_Invoice");
		}
		try
		{
			colName="";value="";
				rs = Common.GetColumns("I_Inventory",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_inventory")
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
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in A_Invoice.");
				}
		
		String query="insert into I_Inventory("+colName+",id_inventory_m) values("+value+","+id_inv_m+")";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				int remQtyForUpdate = Integer.parseInt(rem_qty)-Integer.parseInt(qty);
				query="select sum(rem_qty) from P_Purchase_Order_Asset where id_po="+id_po+"";
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					stmt=con.createStatement();
					if(Integer.parseInt(qty) == rs.getInt(1))
					{
						stmt.executeUpdate("update P_Purchase_Order set recv_invoice=2 where id_po="+id_po+"");
						stmt.executeUpdate("update P_Purchase_Order_Asset set rem_qty="+remQtyForUpdate+" where id_po_asst="+id_po_asst+"");
						j=00;
					}
					else
					{
					stmt.executeUpdate("update P_Purchase_Order_Asset set rem_qty="+remQtyForUpdate+" where id_po_asst="+id_po_asst+"");
					if(remQtyForUpdate == 0)
						j=00;
					else
						j=1;
					}
					
					 
				}
				
					
				
				
			}
			
			
			json.put("data",id_po);
			json.put("data1",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	
	
	
	
	
	

}
