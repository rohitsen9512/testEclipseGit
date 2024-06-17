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


public class I_Inventory extends HttpServlet {
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
		
		 Enumeration elemet = request.getParameterNames();
		 HttpSession session = request.getSession(); 
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  

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
		String action = "",id="0",no_inv="",invoiceId="",dt_to="",dt_frm="",value="",ColName="",id_inventory_m="00";
		
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
			temp = " and dt_inv <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_inv >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_inv >= '"+dt_frm+"' and dt_inv <= '"+dt_to+"'";
		}
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddInventory(map,no_inv,id_emp_user,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayInventory(id,no_inv,invoiceId,temp);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayInventory(id,no_inv,invoiceId,temp);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateInventory(map,id,invoiceId,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteInventory(id , id_inventory_m);	
	                break;
	            case "DeleteAsset":
	            	jobj = DeleteAsset(id , id_inventory_m);	
	                break;
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in I_Inventory.");
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
	
	
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_inventory_m from I_Inventory_Master where "+ColName+" = '"+value+"'";
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("data",1);
			}
			else
			{
				json.put("data",0);
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	public JSONObject DeleteAsset(String id ,String id_inventory_m)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			if(!id_inventory_m.equals("0"))
			{
					String query = "select COUNT(id_inventory_m) from I_Inventory where id_inventory_m = "+id_inventory_m+" ";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						int rowCount = rs.getInt(1);
						if(rowCount > 1)
						{
							query = "delete I_Inventory where id_inventory = "+id+"";
							ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								j=1;
							}
						}
						else
						{
							j=2;
						}
					}
					
			}
			else
			{
				
				String query = "select id_inventory_m from I_Inventory where id_inventory = "+id+"";
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_inventory_m = rs.getString(1);
				}
				
				 query = "delete I_Inventory where id_inventory = "+id+"";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					query = "delete I_Inventory_Master where id_inventory_m = "+id_inventory_m+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						j=1;
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
	
	
	public JSONObject DeleteInventory(String id , String id_inventory_m)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			if(!id_inventory_m.equals("0"))
			{
		String query = "select COUNT(id_inventory) from I_Inventory where id_inventory_m = "+id+" and status_store = 0";
		ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		
				if(rs.next())
				{
					int a= rs.getInt(1);
						if(a > 1)
						{
							query = "delete I_Inventory where id_inventory_m = "+id+"";
							
							PreparedStatement ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								query = "delete I_Inventory_Master where id_inventory_m = "+id+"";
								ps=con.prepareStatement(query);
								j=ps.executeUpdate();
								if(j > 0)
								{
									j=1;
								}
							}
						}
							else
							{
								j=2;
							}
					
				}
			}
		else
		{
			String query = "delete I_Inventory where id_inventory_m = "+id+"";
			
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				query = "delete I_Inventory_Master where id_inventory_m = "+id+"";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					j=1;
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
	
	public JSONObject UpdateInventory(HashMap<String, String> map , String id,String no_asset,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
			
				rs = Common.GetColumns("I_Inventory_Master",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_inventory_m")
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
						
						
						String query="update I_Inventory_Master set "+col_Val+" where id_inventory_m="+id+"";
						try 
						{
							PreparedStatement ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								j=0;
								col_Val="";
								rs = Common.GetColumns("I_Inventory",  request);
								
								
								while (rs.next())
								{
								
									if(rs.getString(1) !="id_inventory")
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
								 query="update I_Inventory set "+col_Val+" where id_inventory="+no_asset+"";
								 ps=con.prepareStatement(query);
								j=ps.executeUpdate();
								if(j > 0)
								{
									json.put("data",id);
								}
								else
								{
									json.put("data",j);
								}
								
								
							}
						
							
							
						}
						catch (Exception e)
						{
							
							e.printStackTrace();
						}
					
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in I_Inventory.");
				}
		
		
		
		
		return json;
	}
	
	
	
	public JSONObject DisplayInventory(String id , String id_inventory_m,String no_asset,String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!no_asset.equals(""))
		{
			sql ="select * from I_Inventory  where id_inventory = "+no_asset+"";
		}
		if(id_inventory_m.equals("") && no_asset.equals(""))
		{
			if(!temp.equals(""))
			{
				 sql ="select inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtPo,nm_ven from I_Inventory_Master inv_m , M_Vendor v where inv_m.id_ven=v.id_ven and rec_status_grn = 0 "+temp+"";
					
			}
			else
			 sql ="select inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtPo,nm_ven from I_Inventory_Master inv_m , M_Vendor v where inv_m.id_ven=v.id_ven and rec_status_grn = 0";
			
			// sql ="select inv_m.*,nm_ven from I_Inventory_Master inv_m , M_Vendor v where inv_m.id_ven=v.id_ven and "+
					// " status_store = 1 and dt_inv >=(replace(convert(NVARCHAR, '"+dt_frm+"', 106), ' ', '/')) and "+
					// "dt_inv <=(replace(convert(NVARCHAR, '"+dt_to+"', 106), ' ', '/'))";
		}
		else if(!id_inventory_m.equals(""))
		{
			sql ="select id_inventory,qty,un_prc,nm_acc_asset,tt_un_prc,tt,mfr,id_assetdiv,ds_pro from I_Inventory inv where id_inventory_m = "+id_inventory_m+" and rec_status_grn = 0";
		}
		if(!id.equals("0"))
		{
			sql="select  inv.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtPo,inv_m.id_inventory_m,inv_m.id_ven,nm_ven,no_inv,dt_inv,no_po,dt_po,id_curr,id_loc,id_subl,bd,ds_pro from I_Inventory_Master inv_m , M_Vendor v,I_Inventory inv where "+
					 "inv_m.id_ven=v.id_ven and inv_m.id_inventory_m=inv.id_inventory_m and inv_m.id_inventory_m = "+id+" and inv_m.status_store = 0";
		
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
			System.out.println("sql error in I_Inventory.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddInventory(HashMap<String, String> map,String no_inv,int id_emp_user,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0,id_inventory_m=0;
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
				
				 sql="insert into I_Inventory_Master("+colName+",add_by) values("+value+","+id_emp_user+")";
					 
				 stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				
				 
				 rs1 = stmt.getGeneratedKeys();
		            while (rs1.next()) {
		            	id_inventory_m = rs1.getInt(1);
		            } 
				
			}
			else
			{
				id_inventory_m=rs.getInt(1);
			}
		
		}
		catch(Exception e)
		{
			System.out.println("Error in I_Inventory");
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
					System.out.println("Some error in I_Inventory.");
				}
		
		String query="insert into I_Inventory("+colName+",id_inventory_m) values("+value+","+id_inventory_m+")";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
			}
			
			
			json.put("data",id_inventory_m);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	
	
	
	
	
	

}
