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
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.DtoCommon;

/**
 * Servlet implementation class Lead_Tag_printing
 */
public class Lead_Tag_printing extends HttpServlet {
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
		HttpSession session = request.getSession();  
		 Enumeration elemet = request.getParameterNames();
		
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",searchWord="",id_wh="",id="",bulkinstallAssetDate="",to_assign="",id_grp="",id_sloc="",no_inv="",value="";
		String order = "",id_loc="";
		
		int j=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("id_iwh") !=null)
		{
			id_wh = request.getParameter("id_iwh");
		}
		if(request.getParameter("id_grp") !=null)
		{
			id_grp = request.getParameter("id_grp");
		}
		if(request.getParameter("id_sloc") !=null)
		{
			id_sloc = request.getParameter("id_sloc");
		}
		if(request.getParameter("id_loc") !=null)
		{
			id_loc = request.getParameter("id_loc");
		}
		if(request.getParameter("no_inv") !=null)
		{
			no_inv = request.getParameter("no_inv");
		}
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}
		if(request.getParameter("order") !=null)
		{
			order = request.getParameter("order");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		String UserType = (String)session.getAttribute("UserType"); 
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Display":
	            	jobj = DisplayproductForTaggPrint(id_grp,id_sloc,id_loc,searchWord,UserType,request);	
	                break;
	                
	               
	            
	           
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in T_Tagg_Print.");
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
	
	

	
	
	public JSONObject DisplayproductForTaggPrint(String id_grp,String id_sloc,String id_loc, String searchWord,String UserType,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		JSONArray jarr2 = new JSONArray();
		DtoCommon dtoCommon = new DtoCommon();
		String sql="";
		if(!UserType.equals("Super"))
		{
			String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
		
		sql="select id_iwh,serial_no,nm_product,mfr,no_inv,serial_no,nm_assetdiv,device_status,filling_status from S_I_Ware_House wh,M_Asset_Div ma where tag ='Yes' and ma.id_assetdiv=wh.id_product and wh.id_product = "+id_grp+" and wh.id_subl = "+id_sloc+" and (serial_no like '"+searchWord+"%' or nm_product like '"+searchWord+"%' or mfr like '"+searchWord+"%'  ) "+tempQuery2+" ";
		System.out.println("gg"+sql);
		
		}
		else {
			
			 sql="select id_iwh,ds_pro,mfr,no_inv,serial_no,nm_product,device_status,filling_status from S_I_Ware_House wh,M_Asset_Div ma where tag ='Yes' and "
			+"ma.id_assetdiv=wh.id_product and wh.id_subl = '"+id_sloc+"' and wh.id_product = "+id_grp+" and  (serial_no like '"+searchWord+"%' or nm_product like '"+searchWord+"%'  or mfr like '"+searchWord+"%'  ) ";
			//sql="select id_wh,id_wh_dyn,appNo,no_model,ds_asst,ds_pro,no_mfr,no_inv,serial_no,nm_s_assetdiv from A_Ware_House wh,M_Subasset_Div sd where tag ='Yes' and sd.id_s_assetdiv=wh.id_sgrp and wh.id_grp = "+id_grp+" and wh.id_sloc = "+id_sloc+" and (id_wh_dyn like '"+searchWord+"%' or no_model like '"+searchWord+"%' or no_mfr like '"+searchWord+"%' or serial_no like '"+searchWord+"%'  )  ";
			 System.out.println("gg"+sql);
		}
		System.out.println("kkk"+sql);
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
		    String sql1 = "select * from M_Company";	
			ps=con.prepareStatement(sql1);
			rs=ps.executeQuery();
			
			   ResultSetMetaData metaData1 = rs.getMetaData();
			    int columns1 = metaData1.getColumnCount();
			    while(rs.next())
			    {
			    	JSONObject jobj3 = new JSONObject();
			    	for(int i=1;i<=columns1;i++)
			    	{
			    		String name=metaData1.getColumnName(i).toLowerCase()
			    				;
			    		jobj3.put(name,rs.getString(name));
			    	}
			    	jarr1.put(jobj3);
			    }
			    jobj.put("company",jarr1);
		    
			    sql1 = "select * from M_Loc where id_loc='"+id_loc+"' ";	
				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				
				   ResultSetMetaData metaData11 = rs.getMetaData();
				    int columns11 = metaData11.getColumnCount();
				    while(rs.next())
				    {
				    	JSONObject jobj4 = new JSONObject();
				    	for(int i=1;i<=columns11;i++)
				    	{
				    		String name=metaData11.getColumnName(i).toLowerCase()
				    				;
				    		jobj4.put(name,rs.getString(name));
				    	}
				    	jarr2.put(jobj4);
				    }
				    jobj.put("entityname",jarr2);
		   
		}
		catch(Exception e)
		{
			System.out.println("sql error in T_Tagg_Print.");
		}
		 
		return jobj;
	}
	
}