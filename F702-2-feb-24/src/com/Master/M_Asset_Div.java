package com.Master;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;


public class M_Asset_Div extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      paramValues=paramValues.replace("'", "''");
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0",idsloc="",id1="",value="",ColName="",type="",searchWord="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
			 System.out.println(id);
		}
		if(request.getParameter("id1") !=null)
		{
			 id1 = request.getParameter("id1");
			 System.out.println(id);
		}
		if(request.getParameter("idsloc") !=null)
		{
			idsloc = request.getParameter("idsloc");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("type") !=null)
		{
			type = request.getParameter("type");
		}
		
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddAssetDiv(map, request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayAssetDiv(id,type,searchWord);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayAssetDiv(id,type,searchWord);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateAssetDiv(map,id,  request);	
	                break; 
	            case "GetProductdetails":
	            	jobj = GetProductdetails(id,idsloc);	
	                break;   
	            case "Delete":
	            	jobj = DeleteAssetDiv(id);	
	                break; 
	                
	            case "DropDownResult":
	            	jobj = DropDownResult(id,id1);	
	                break; 
	                
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in M_Asset_Div.");
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
		
		String query = "select id_assetdiv from M_Asset_Div where "+ColName+" = '"+value+"'";
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
	
	public JSONObject DropDownResult(String id,String id1)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		//String sql="select id_assetdiv,nm_assetdiv from M_Asset_Div where type_grp='"+type+"'";
		if(!id.equals("0")) {
			
			 sql="select distinct id_assetdiv,nm_assetdiv from M_Asset_Div m,S_I_Ware_House sm where sm.id_subl='"+id+"' and sm.id_loc='"+id1+"' and sm.id_product = m.id_assetdiv";
		System.out.println(sql);
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
			System.out.println("sql error in Location master.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DeleteAssetDiv(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{

			String query = "select id_wh from A_Ware_House where id_grp = "+id+"";
			System.out.println("wh query------" + query);
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				j=2;
			}
			else
			{
				 query = "select  id_assetdiv from M_Subasset_Div where id_assetdiv = "+id+"";
				 System.out.println("Sub query------" + query);
				PreparedStatement ps=con.prepareStatement(query);
				rs=ps.executeQuery();
					if(rs.next())
					{
						j=3;
					}
					else
					{
					   query = "delete M_Asset_Div where id_assetdiv = "+id+"";
					
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
	
	public JSONObject UpdateAssetDiv(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Asset_Div",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_assetdiv")
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
					System.out.println("Some error in M_Asset_Div.");
				}
		String nm_product=map.get("nm_assetdiv");//when name is update in master so also upate store_qty
		String query="update M_Asset_Div set "+col_Val+" where id_assetdiv="+id+"";
		System.out.println(query);
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				query="update S_Store_Qty set nm_product ='"+nm_product+"' where id_product="+id+"";
				System.out.println(query);
				PreparedStatement ps1=con.prepareStatement(query);
				j=ps1.executeUpdate();
				if(j > 0) {
					j=1;
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
	
	public JSONObject GetProductdetails(String id,String idsloc )
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		if(idsloc.equals("")) 
		    {
			sql="Select * from M_Asset_Div where nm_assetdiv='"+id+"'";
		}
		else {
		//String sql ="select * from M_Asset_Div where nm_assetdiv  = '"+id+"'";
//		 sql ="Select  * from M_Asset_Div ma," + 
//				"(select sum(qty_recv) tot_qty  from S_Inventory_store st, M_Subloc ms where st.id_subl=ms.id_sloc and product_nm='"+id+"' and id_subl='"+idsloc+"' group by product_nm,nm_subl) as tot_qty "+
//						 " where nm_assetdiv='"+id+"' ";
//		
		 sql=" Select  * from M_Asset_Div m,(select  tot_aval_qty  from  S_Store_Qty where  nm_product='"+id+"' and id_sloc='"+idsloc+"' ) tot_aval_qty where nm_assetdiv='"+id+"' ";    
		
		}
		System.out.println(sql);
		System.out.println("sqlprod");
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
			System.out.println(e+"sql error in M_Asset_Div.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DisplayAssetDiv(String id,String type_grp,String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select * from M_Asset_Div where ( nm_assetdiv like '"+searchWord+"%')";
		System.out.println(sql);
		if(!id.equals("0"))
		{
			sql ="select * from M_Asset_Div where id_assetdiv = "+id+"";
			System.out.println(sql);
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
			System.out.println("sql error in M_Asset_Div.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddAssetDiv(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Asset_Div",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_assetdiv")
							{
								    if (map.containsKey(rs.getString(1)))
								    {
								    	if(colName.equals(""))
								    	{
									    	colName += rs.getString(1);
									    	value += "'"+ map.get(rs.getString(1)).toUpperCase()+"'";
								    	}
								    	else
								    	{
								    		colName +=","+ rs.getString(1);
									    	value +=", '"+ map.get(rs.getString(1)).toUpperCase()+"'";
								    		
								    	}
								    }
							  }
						}
						System.out.println(map);
				
			}

			catch(Exception e)
				{
					System.out.println("Some error in M_Asset_Div.");
				}
		
		String query="insert into M_Asset_Div("+colName+") values("+value+")";
		System.out.println(query);
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
	
	
	
	
	
	
	
	

}
