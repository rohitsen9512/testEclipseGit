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


public class T_Create_Gate_Pass extends HttpServlet {
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
		    
		String action = "",id_wh="",id="",type_tran="",dt_frm="",dt_to="",id_iut="",dt_gp_vldty="";
		
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
		if(request.getParameter("dt_gp_vldty") !=null)
		{
			dt_gp_vldty = request.getParameter("dt_gp_vldty");
		}
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Display":
	            	jobj = DisplayAssetForCreateGatePass(type_tran,dt_frm,dt_to);	
	                break;
	                
	            case "Edit":
	            	jobj = DataForEdit(id);	
	                break;
	                
	            case "Save":
	            	jobj = AddGatePass(map,id_iut,  request);	
	                break;
	                
	            case "CheckDate":
	            	jobj = CheckDate(dt_gp_vldty,id);	
	                break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in T_Create_Gate_Pass.");
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
	
	public JSONObject CheckDate(String dt_gp_vldty ,String id)
	{
		JSONObject jobj = new JSONObject();
		
		try
		{
		int t=1;
    	String  sql ="";
    	String d = "1900-01-01";
    	
		    	sql ="select dt_start_tran from A_Iut_History where dt_start_tran > '"+dt_gp_vldty+"' and id_iut="+id+" ";
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
	
	
	public JSONObject AddGatePass(HashMap<String, String> map,String id_iut,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("T_Gate_Pass",  request);
						while (rs.next())
						{
							if(rs.getString(1) !="id_gp")
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
				
			
		
		String query="insert into T_Gate_Pass("+colName+",dt_create) values("+value+",'"+sdf.format(currDate)+"')";
		
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				 query="update A_Iut_History set st_gate_pass=1 where id_iut="+id_iut+"";
				 ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				j=1;
			}
		
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			System.out.println(e);
			
		}
		
		
		return json;
	}
	
	
	public JSONObject DataForEdit(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			sql="select id_wh_dyn,ds_pro,no_mfr,typ_asst,wh.id_wh,type_tran from A_Ware_House wh,A_Iut_History iut  where wh.id_wh=iut.id_wh and id_iut="+id+" ";
		
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
	

	
	public JSONObject DisplayAssetForCreateGatePass(String type_tran,String dt_frm,String dt_to)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		sql="select iut.id_iut,wh.id_wh,wh.id_wh_dyn,type_tran,ds_pro,no_mfr,type_tran from A_Iut_History iut,A_Ware_House wh where type_tran ='"+type_tran+"'"+
		"and iut_approval=3 and wh.id_wh=iut.id_wh and st_gate_pass =0 and dt_start_tran >= '"+dt_frm+"' and dt_start_tran <= '"+dt_to+"' and st_gate_pass=0";
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
			System.out.println("sql error in T_Create_Gate_Pass.");
		}
		 
		return jobj;
	}
}