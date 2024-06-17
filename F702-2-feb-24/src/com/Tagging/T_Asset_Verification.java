package com.Tagging;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;


public class T_Asset_Verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	Date currDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="",id_wh_dyn="",id_loc="",id_subl="",id_flr="";
	
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("id_wh_dyn") !=null)
		{
			id_wh_dyn = request.getParameter("id_wh_dyn");
		}
		if(request.getParameter("id_loc") !=null)
		{
			id_loc = request.getParameter("id_loc");
		}
		if(request.getParameter("id_subl") !=null)
		{
			id_subl = request.getParameter("id_subl");
		}
		if(request.getParameter("id_flr") !=null)
		{
			id_flr = request.getParameter("id_flr");
		}
			
		
		String temp="";
		
		if(!id_loc.equals(""))
		{
			if(!id_flr.equals(""))
			{
				temp += " and id_loc = "+id_loc+" and id_subl = "+id_subl+" and id_flr="+id_flr+"";
			}
			if(!id_subl.equals(""))
			{
				temp += " and id_loc = "+id_loc+" and id_subl = "+id_subl+"";
			}
			else
			{
				temp += " and id_loc = "+id_loc+"";
			}
		}
		
		
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        case "Save":
	            	jobj = VerifyAsset(map,id,id_wh_dyn,id_loc,id_subl,id_flr,  request);	
	                break;
		                
	            case "Display":
	            	jobj = DisplayAssetForAV();	
	                break;
	                
	            case "Edit":
	            	jobj = EditAssetForAV(id);	
	                break;
	             
	            case "Search":
	            	jobj = DisplayAssetForAVViaSearch(temp);	
	                break;
	                
	        }   
            		
	           
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		
		}catch(Exception e)
		{
			System.out.println("Error in T_Asset_Verification.");
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
	
	
	
	public JSONObject EditAssetForAV(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			sql="select wh.id_wh_dyn,wh.no_mfr,wh.ds_pro,wh.dt_alloc,l.nm_loc,s.nm_subl,f.nm_flr,a.nm_assetdiv,wh.typ_asst from A_Ware_House wh,M_Loc l,M_Subloc s,M_Floor f,M_Asset_Div a where wh.id_loc=l.id_loc and wh.id_subl=s.id_sloc and wh.id_flr=f.id_flr and wh.id_grp=a.id_assetdiv and wh.id_wh ='"+id+"'";
		
		
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
			System.out.println("sql error in T_Asset_Verification.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject DisplayAssetForAVViaSearch(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select wh.* from A_Ware_House wh   where allocate =1  "+temp+"";
		
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
		    if(jarr.length() > 0)
		    {
		    	jobj.put("data", jarr);
		    }
		    else
		    {
		    	jobj.put("data", 1);
		    }
		}
		catch(Exception e)
		{
			System.out.println("sql error in T_Asset_Verification.");
		}
		 
		return jobj;
	}
	
	
	
	
	public JSONObject DisplayAssetForAV()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		sql="select wh.*,nm_assetdiv from A_Ware_House wh, M_Asset_Div a where allocate = 1 and acc_asst  = '0'  and a.id_assetdiv=wh.id_grp";
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
		    if(jarr.length() > 0)
		    {
		    	jobj.put("data", jarr);
		    }
		    else
		    {
		    	jobj.put("data", 1);
		    }
		}
		catch(Exception e)
		{
			System.out.println("sql error in T_Asset_Verification.");
		}
		 
		return jobj;
	}
	


	public JSONObject VerifyAsset(HashMap<String, String> map , String id ,String id_wh_dyn,String id_loc,String 
			id_subl,String id_flr,HttpServletRequest request)
	{
		String col_Val="",colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		
		try 
		{
		 
 		 rs = Common.GetColumns("T_Asset_Verification",  request);
			
		
			while (rs.next())
			{
			
				if(rs.getString(1) !="id_av")
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
			String  query=" select id_av from T_Asset_Verification where id_wh_dyn='"+id_wh_dyn+"'";
			 PreparedStatement ps=con.prepareStatement(query);
			 rs1 = ps.executeQuery();
			 String query1="";
			 stmt =con.createStatement();
		 if(rs1.next())
		 {
			 stmt.executeUpdate("update A_Ware_House set id_loc = "+id_loc+", "+
                                   "id_subl = "+id_subl+",id_flr = "+id_flr+" "+
                                   " where id_wh_dyn = '"+id_wh_dyn+"' ");
			 
			 
				 query1="update into T_Asset_Verification("+col_Val+") where id_av ="+rs1.getInt(1)+"";
			 
		 }
		 else
		 {
			 stmt.executeUpdate("update A_Ware_House set id_loc = "+id_loc+", id_subl = "+id_subl+", id_flr = "+id_flr+" where id_wh_dyn = '"+id_wh_dyn+"' ");
			
				 query1="insert into T_Asset_Verification("+colName+") values("+value+")";
			 
		 }
		  ps=con.prepareStatement(query1);
		 j = ps.executeUpdate();
		 if(j >0)
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
