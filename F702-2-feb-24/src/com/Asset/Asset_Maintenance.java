package com.Asset;

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

public class Asset_Maintenance extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	Connection con = null;
	Statement stmt = null;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		HttpSession session = request.getSession(); 
		HashMap<String, String> map = new HashMap<String, String>();
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
		    	  paramValues=paramValues.replace("'", "''");
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		
		String action="",id_grp="",id_sgrp="",id_wh="";
		
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		
		if (request.getParameter("id_grp") != null) {
			id_grp = request.getParameter("id_grp");
		}
		if (request.getParameter("id_sgrp") != null) {
			id_sgrp = request.getParameter("id_sgrp");
		}
		
		if (request.getParameter("id_wh") != null) {
			id_wh = request.getParameter("id_wh");
		}
		
		
		
		try {
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			con = Common.GetConnection(request);

			switch (action) {
			
	                
			case "Display":
				jobj = DisplayAssetMaintenance(id_wh);
				break;
				
			 case "DropDownResult":
	            	jobj = DropDownResult(id_grp,id_sgrp);	
	                break; 
	                
			 case "Save":
				 jobj = SaveAssetMaintenance(map,id_emp_user,  request);	
	                break; 
	                
	                

			}

			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());

		} catch (Exception e) {
			System.out.println("Error in D_Addition_Deletion.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	
	public JSONObject DropDownResult(String id_grp , String id_sgrp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		
		String sql="SELECT id_wh,id_wh_dyn FROM A_Ware_House where (allocate=1) and id_grp='"+id_grp+"' and id_sgrp='"+id_sgrp+"' and sold !='yes'  ORDER BY id_wh_dyn";
		
		
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
			System.out.println("sql error in D_Addition_Deletion.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject DisplayAssetMaintenance(String id_wh) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";

		sql="select id_wh,id_wh_dyn,ds_pro,ds_asst from A_Ware_House wh where  id_wh ='"+id_wh+"'";
		
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			while (rs.next()) {
				JSONObject jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i);
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}
			jobj.put("data", jarr);
			
			
		} catch (Exception e) {
			System.out.println("sql error in D_Depreciation_Config.");
		}

		return jobj;

	}

	
	public JSONObject SaveAssetMaintenance(HashMap<String, String> map , int id_emp_user,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("Asset_Maintenance",  request);
				
						
						while (rs.next())
						{
						
							if((!rs.getString(1).equals("id_mntnc")))
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
				
			
			 String query="insert into Asset_Maintenance("+colName+",mntnc_by) values("+value+","+id_emp_user+")";
			 ps=con.prepareStatement(query);
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
