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


public class I_Create_Grn extends HttpServlet {
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
		    
		String action = "",id="0",id_inventory_m="",dt_to="",dt_frm="",value="",ColName="",id_inventory="";
		int id_emp_user=0;
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
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
			
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("id_inventory") !=null)
		{
			id_inventory = request.getParameter("id_inventory");
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
			temp = " and inv_m.dt_inv <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and inv_m.dt_inv >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and inv_m.dt_inv >= '"+dt_frm+"' and inv_m.dt_inv <= '"+dt_to+"'";
		}
		
		
			 id_emp_user = (int)session.getAttribute("id_emp_user");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddGRN(map,id_inventory_m,id_inventory,id_emp_user,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayInventoryForCreateGrn(temp);	
	                break;
	           
	            case "DisplayGrn":
	            	jobj = DisplayGrnForParticularInventory(id_inventory);	
	                break;
	                
	            case "Edit":
	            	jobj = InventoryDataForGrnEdit(id , id_inventory_m);	
	                break;
	                
	            case "CountQty":
	            	jobj = CountQty(id_inventory);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateGrn(map,id,  request);	
	                break;
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in I_Create_Grn.");
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
	
	
	public JSONObject UpdateGrn(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("I_Grn",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_inventory_grn")
							{
								    if (map.containsKey(rs.getString(1)))
								    {
								    	if(col_Val.equals(""))
								    	{
								    		col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
									    	
								    	}
								    	else
								    	{
								    		
								    		col_Val += ","+ rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
									    	
								    		
								    	}
								    }
							  }
						}
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in country master.");
				}
		
		String query="update I_Grn set "+col_Val+" where id_inventory_grn="+id+"";
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
	}
	
	
	
	public JSONObject CountQty(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String  sql ="select SUM(qty_recv) from I_Grn where  status_approv !=2 and id_inventory = "+id+"";
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		    while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	
		    		jobj2.put("TotQty",rs.getInt(1));
		    	
		    		jarr.put(jobj2);
	        }
		    
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in I_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject InventoryDataForGrnEdit(String id , String id_inventory_m)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="",Grn_No="",tempGrnId="";
		try
		{
		if(id.equals(""))
		{
			
			ps=con.prepareStatement("select max(id_inventory_grn) from I_Grn ");
			rs1=ps.executeQuery();
			
				if(rs1.next())
				{
					tempGrnId =	rs1.getString(1);
					if(tempGrnId == null)
					{
						tempGrnId ="1";
					}
				}
				
				Grn_No	= "GRN-"+tempGrnId;
			
			 sql="select no_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,nm_ven,inv_m.id_ven,no_po,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo from I_Inventory_Master inv_m , M_Vendor v where id_inventory_m = "+id_inventory_m+" and inv_m.id_ven=v.id_ven";
		}
		else
		{
			sql="select g.*,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_dc, 103), ' ', '-')) as dtDc,nm_ven from I_Grn g , M_Vendor v where g.id_ven=v.id_ven and  id_inventory_grn = "+id+"";
			
		}
		
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
		    if(id.equals(""))
		    {
			    jobj2.put("no_grn",Grn_No);
			    jarr.put(jobj2);
		    }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in I_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject DisplayGrnForParticularInventory( String id_inventory)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String  sql ="select *,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn from I_Grn where status_approv =0 and id_inventory = "+id_inventory+"";
			
			 
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
			System.out.println("sql error in I_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject DisplayInventoryForCreateGrn(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
			sql ="select inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,nm_ven,qty,id_inventory from I_Inventory_Master inv_m ,I_Inventory ai , M_Vendor v where inv_m.id_ven=v.id_ven and ai.rec_status_grn=0 and ai.id_inventory_m = inv_m.id_inventory_m "+temp+"";
			
		else
			 sql ="select inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,nm_ven,qty,id_inventory from I_Inventory_Master inv_m ,I_Inventory ai , M_Vendor v where inv_m.id_ven=v.id_ven and ai.rec_status_grn=0 and ai.id_inventory_m = inv_m.id_inventory_m";
			
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
		    		
		    		
		    		
		    		 sql ="select SUM(qty_recv) as qty_recv from I_Grn where id_inventory = "+rs.getInt("id_inventory")+" and status_approv != 2";
		 			int TotRecvQty = 0;
		 			int TotQty = rs.getInt("qty");
		 			ps=con.prepareStatement(sql);
		 			rs1=ps.executeQuery();
		 			 if(rs1.next())
		 			 	{
		 				 	TotRecvQty = rs1.getInt(1);
		 			 	}
		 			 
		 			 jobj2.put("ReminQty",(TotQty - TotRecvQty)); 		
		 			jarr.put(jobj2);
		    		
	        }
		   
		    
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in I_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddGRN(HashMap<String, String> map,String id_inventory_m , String id_inventory,int id_emp_user,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0,qty_recv=0,TotQty=0;
		JSONObject json=new JSONObject();
		try
		{
		
		    
				rs = Common.GetColumns("I_Grn",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_inventory_grn")
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
							if(map.containsKey("qty"))
						    	TotQty = Integer.parseInt( map.get("qty"));
							if(map.containsKey("qty_recv"))
							qty_recv = Integer.parseInt( map.get("qty_recv"));
						}
				
		String  sql ="select SUM(qty_recv) from I_Grn where status_approv !=2 and  id_inventory = "+id_inventory+"";
		int TotRecvQty = 0,temp=0;
		PreparedStatement ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		 if(rs.next())
		 	{
			 	TotRecvQty = rs.getInt(1);
			 	
			 	TotRecvQty = TotRecvQty + qty_recv;
			 	if(TotRecvQty == TotQty)
			 	{
			 		sql ="update I_Inventory set rec_status_grn =1 where id_inventory = "+id_inventory+"";
			 		ps=con.prepareStatement(sql);
					j=ps.executeUpdate();
					temp =1;
					
					
			 	}
			 	 sql ="update I_Inventory_Master set rec_status_grn =1 where id_inventory_m = "+id_inventory_m+"";
			 		ps=con.prepareStatement(sql);
					j=ps.executeUpdate();
					
		 	}
		
			
		String query="insert into I_Grn("+colName+",add_by) values("+value+","+id_emp_user+")";
	
			ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
			}
		if(temp == 1)
		{
			j=2;
		}
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
}
