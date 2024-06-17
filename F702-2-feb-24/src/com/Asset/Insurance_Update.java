package com.Asset;

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


public class Insurance_Update extends HttpServlet {
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
		    
		String action = "",id="0",id_grp="",id_finance="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_grp") !=null)
		{
			id_grp = request.getParameter("id_grp");
		}
		if(request.getParameter("id_finance") !=null)
		{
			id_finance = request.getParameter("id_finance");
		}

		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
			
	                
	            case "Edit":
	            	jobj = DisplayCompany();	
	                break;
	                
	            case "getGroupData":
	            	jobj = getSubGroupData(id_grp,id_finance);	
	                break; 
	            case "Save":
	           
	            String col_Val="",colName="",value="";
	    		int j=0;
	    		try
	    		{
	    				rs = Common.GetColumns("Insurance_Update",  request);
	    				
	    						
	    						while (rs.next())
	    						{
	    						
	    							if(rs.getString(1) !="id_ins")
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
	    						
	    		String sql="select id_ins from Insurance_Update where id_grp="+id_grp+" and id_finance="+id_finance+"";				
	    		PreparedStatement ps=con.prepareStatement(sql);
	    		rs=ps.executeQuery();
	    		if(rs.next())
	    		{
	    			j=0;
	    			sql="delete Insurance_Update where id_grp="+id_grp+" and id_finance="+id_finance+"";
	    			 ps=con.prepareStatement(sql);
	    			j=ps.executeUpdate();
	    			if(j > 0)
	    			{
	    				j=1;
	    			}
	    		}
	    		else
	    		{
	    			j=1;
	    		}
	    		if(j == 1)
	    		{
	    			j=0;
	    			String count1 = request.getParameter("count");
	    			String maxPrc="",insPrc="",id_sgrp="";
	    			int count = Integer.parseInt(count1);
	    			for(int i=0 ; i<count;i++)
	    			{
		    			j=0;
		    			maxPrc = request.getParameter("maxPrc"+i+"");
		    			insPrc = request.getParameter("insPrc"+i+"");
		    			id_sgrp = request.getParameter("id_sgrp"+i+"");
		    			
		    			sql="insert into Insurance_Update("+colName+",asset_val,ins_val,id_sgrp) values("+value+","+maxPrc+","+insPrc+","+id_sgrp+")";
		    			 ps=con.prepareStatement(sql);
		    			j=ps.executeUpdate();
		    			if(j > 0)
		    			{
		    				j=1;
		    			}
	    			}
	    		}
	    		jobj.put("data",j);	
	    			}
	    			catch(Exception e)
	    				{
	    					System.out.println("Some error in Insurance Update.");
	    				}
	            
	            break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Insurance Update.");
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
	
	
	
	public JSONObject getSubGroupData(String id_grp , String id_finance)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String std_finance="",end_finance="";
		try
		{
		String query = "select std_finance,end_finance from M_Finance_Year where id_fincance ="+id_finance+"";
		ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		if(rs.next())
		{
			std_finance = rs.getString(1);
			end_finance = rs.getString(2);
		}
		
		String sql="select nm_s_assetdiv,MAX(tt_un_prc) as maxAssetVal,id_sgrp from M_Subasset_Div sgrp,A_Ware_House wh where wh.id_sgrp=sgrp.id_s_assetdiv "+
					"and id_s_assetdiv IN(select wh.id_sgrp from A_Ware_House wh where wh.id_grp="+id_grp+" and allocate !=3 and dt_inv >= (replace(convert(NVARCHAR, '"+std_finance+"', 106), ' ', '-')) "+
					"and dt_inv <= (replace(convert(NVARCHAR, '"+end_finance+"', 106), ' ', '-')) group by id_sgrp) group by nm_s_assetdiv,id_sgrp";
		/*String sql="select wh.id_sgrp,max(tt_un_prc) as maxAssetVal,nm_s_assetdiv from A_Ware_House wh,M_Subasset_Div sgrp where wh.id_grp="+id_grp+" and allocate !=3 and wh.id_sgrp=sgrp.id_assetdiv  "+
		" and dt_inv >= (replace(convert(NVARCHAR, '"+std_finance+"', 106), ' ', '-')) and dt_inv <= (replace(convert(NVARCHAR, '"+end_finance+"', 106), ' ', '-')) group by id_sgrp,nm_s_assetdiv";
		
		*/
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
			System.out.println("sql error in Insurance Update.");
		}
		 
		return jobj;
		
		
	}
	


	public JSONObject DisplayCompany()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select * from M_Company";
		
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
			System.out.println("sql error in Insurance Update.");
		}
		 
		return jobj;
		
		
	}
	

}
