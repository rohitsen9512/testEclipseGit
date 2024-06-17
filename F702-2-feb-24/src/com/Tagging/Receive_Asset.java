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


public class Receive_Asset extends HttpServlet {
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
		    
		String action = "",id_wh="",id="",type_tran="",dt_frm="",dt_to="",id_iut="",dt_recv="",id_gp="";
		
		int j=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("id_wh") !=null)
		{
			id_wh = request.getParameter("id_wh");
		}
		if(request.getParameter("id_iut") !=null)
		{
			id_iut = request.getParameter("id_iut");
		}
		if(request.getParameter("type_tran") !=null)
		{
			type_tran = request.getParameter("type_tran");
		}
		if(request.getParameter("dt_recv") !=null)
		{
			dt_recv = request.getParameter("dt_recv");
		}
		if(request.getParameter("id_gp") !=null)
		{
			id_gp = request.getParameter("id_gp");
		}
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Display":
	            	jobj = DisplayReceiveAsset(type_tran,dt_frm,dt_to);	
	                break;
	                
	            case "Edit":
	            	jobj = DataForEdit(id);	
	                break;
	                
	            case "Save":
	            	jobj = ReceiveAsset(id_wh,id_iut,dt_recv,id_gp);	
	                break;
	                
	            case "CheckDate":
	            	jobj = CheckDate(dt_recv,id);	
	                break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Receive_Asset.");
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
	
	public JSONObject CheckDate(String dt_recv ,String id)
	{
		JSONObject jobj = new JSONObject();
		
		try
		{
		int t=1;
    	String  sql ="";
    	String d = "1900-01-01";
    	
		    	sql ="select dt_start_tran from A_Iut_History where dt_start_tran > '"+dt_recv+"' and id_iut="+id+" ";
    			  ps=con.prepareStatement(sql);
            		rs=ps.executeQuery();
            		    if(rs.next())
            		    {
            		    	t=2;
            		    	d = rs.getString(1);
            	        }
    	if(t == 1)
    	{
    		jobj.put("data", t);
    	}
    	else
    	{
    			jobj.put("data", t);
        		jobj.put("dt_iut",d);
    	}	
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in G_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject ReceiveAsset(String id_wh,String id_iut,String dt_recv,String id_gp)
	{
		int j=0;
		JSONObject json=new JSONObject();
	try
	{
		String sql="select id_loc_tran,id_subl_tran from A_Iut_History iut where id_iut="+id_iut+"";
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		if(rs.next())
		{
			stmt = con.createStatement();
			stmt.executeUpdate("update A_Ware_House set asset_tran = 'Intra', "+
					"id_loc = "+rs.getString(1)+", id_subl = "+rs.getString(2)+",st_trvl=0 "+
					" where id_wh = "+id_wh+" ");

			stmt.executeUpdate("update A_Iut_History set dt_recv='"+dt_recv+"',st_recv=1 where id_iut="+id_iut+" and iut_approval=3");
			stmt.executeUpdate("update T_Gate_Pass set st_recv=1 where id_gp="+id_gp+"");
			
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
	
	
	public JSONObject DataForEdit(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			sql="select gp.*,id_wh_dyn,ds_pro,no_mfr from T_Gate_Pass gp,A_Ware_House wh where st_recv=0 and gp.id_wh=wh.id_wh and id_gp="+id+"";
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
			System.out.println("sql error in A_Install.");
		}
		 
		return jobj;
		
		
	}
	

	
	public JSONObject DisplayReceiveAsset(String type_tran,String dt_frm,String dt_to)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		sql="select gp.*,id_wh_dyn,ds_pro,no_mfr from T_Gate_Pass gp,A_Ware_House wh where st_recv=0 and gp.id_wh=wh.id_wh and dt_create >= '"+dt_frm+"' and dt_create <='"+dt_to+"' and type_tran = '"+type_tran+"'";
		
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
			System.out.println("sql error in Receive_Asset.");
		}
		 
		return jobj;
	}
}