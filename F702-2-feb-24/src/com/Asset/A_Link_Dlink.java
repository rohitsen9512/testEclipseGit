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
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.DtoCommon;
import dto.Common.UserAccessData;


public class A_Link_Dlink extends HttpServlet {
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
			    	  paramValues=paramValues.replace("'", "''");
				      map.put(paramName, paramValues);
			      }
			     
			      
			    }
			 }catch(Exception e)
			 {
				 System.out.println(e);
			 }
			    
		String asst_stat="", temp1="",id_div="",id_deptinst="" ,id_bu="",action = "",id_wh_dyn="",upload_asset="",word="",DisplayType="",likeTempQuery="",dt_allocate="",id="",uninstallAssetDate="",bulkinstallAssetDate="",to_assign="",id_flr="",id_cc="";
		String id_grp="",id_sgrp="",id_loc="",id_subl="",id_building="",repo_mngr="",typ_asst="",dt_alloc="",Edit="",dt_grn="",id_grn="",uninstallRmk="",device_status2="";
		int j=0;
		if(request.getParameter("id_dept") !=null)
		{
			id_deptinst = request.getParameter("id_dept");
		}
		if(request.getParameter("id_bu") !=null)
		{
			id_bu = request.getParameter("id_bu");
		}
		if(request.getParameter("id_wh_dyn") !=null)
		{
			id_wh_dyn = request.getParameter("id_wh_dyn");
		}
		if(request.getParameter("asst_stat") !=null)
		{
			asst_stat = request.getParameter("asst_stat");
		}
		System.out.println(asst_stat);
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("Edit") !=null)
		{
			Edit = request.getParameter("Edit");
		}
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("to_assign") !=null)
		{
			to_assign = request.getParameter("to_assign");
		}
		if(request.getParameter("id_flr") !=null)
		{
			id_flr = request.getParameter("id_flr");
		}
		if(request.getParameter("id_cc") !=null)
		{
			id_cc = request.getParameter("id_cc");
		}
		if(request.getParameter("DisplayType") !=null)
		{
			DisplayType = request.getParameter("DisplayType");
		}
		if(request.getParameter("id_grp") !=null)
		{
			id_grp = request.getParameter("id_grp");
		}
		if(request.getParameter("id_sgrp") !=null)
		{
			id_sgrp = request.getParameter("id_sgrp");
		}
		if(request.getParameter("id_loc") !=null)
		{
			id_loc = request.getParameter("id_loc");
		}
		if(request.getParameter("repo_mngr") !=null)
		{
			repo_mngr = request.getParameter("repo_mngr");
		}
		if(request.getParameter("id_div") !=null)
		{
			id_div = request.getParameter("id_div");
		}
		if(request.getParameter("id_subl") !=null)
		{
			id_subl = request.getParameter("id_subl");
		}
		if(request.getParameter("dt_alloc") !=null)
		{
			dt_alloc = request.getParameter("dt_alloc");
		}
		if(request.getParameter("typ_asst") !=null)
		{
			typ_asst = request.getParameter("typ_asst");
		}
		if(request.getParameter("dt_grn") !=null)
		{
			dt_grn = request.getParameter("dt_grn");
		}
		if(request.getParameter("upload_asset") !=null)
		{
			upload_asset = request.getParameter("upload_asset");
		}
		if(request.getParameter("id_grn") !=null)
		{
			id_grn = request.getParameter("id_grn");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		if(request.getParameter("id_building") !=null)
		{
			id_building = request.getParameter("id_building");
		}
		if(request.getParameter("dt_allocate") !=null)
		{
			bulkinstallAssetDate = request.getParameter("dt_allocate");
		}
			String chk[] = request.getParameterValues("bulkInstallAsset");
			String chk1[] = request.getParameterValues("uninstallAsset");
			try
			{	
			String temp="";
			if(!id_grp.equals(""))
			{
				if(!id_sgrp.equals(""))
				{
					temp += " and id_grp = "+id_grp+" and id_sgrp = "+id_sgrp+"";
				}
				else
				{
					temp += " and id_grp = "+id_grp+"";
				}
				
			}
			
			if(!typ_asst.equals(""))
			{
				temp += " and typ_asst = '"+typ_asst+"'";
			}
			if(!dt_alloc.equals(""))
			{
				dt_alloc = dateFormatNeeded.format(userDateFormat.parse(dt_alloc));
			}
			if(!dt_grn.equals(""))
			{
				dt_grn = dateFormatNeeded.format(userDateFormat.parse(dt_grn));
			}
			if(!bulkinstallAssetDate.equals(""))
			{
				bulkinstallAssetDate = dateFormatNeeded.format(userDateFormat.parse(bulkinstallAssetDate));
			}
			
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType"); 
			int UserId = (int)session.getAttribute("UserId");
			String id_dept = (String)session.getAttribute("DeptId");
			
			String FlrId = (String)session.getAttribute("FlrId");
			int DivId = (int)session.getAttribute("DivId");
			if(!UserType.equals("SUPER")){
				
			}
			
			String tempSql = "";
			con=Common.GetConnection(request);
			UserAccessData uad = new UserAccessData();
			switch (action)
	        {
	        case "DropDownResult": //
            	jobj = DropDownResult(id,UserType,request);	
                break;
	        case  "Display_additional": //
            	jobj = Display_additional(id);
            	break;    
	        case "link_accessories": //
        		jobj = link_accessories(request);
        		break;	
	        case "Update1": //
            	jobj = update1(request);
            	break;
	            case "link_down":  //
	            	jobj = link_down(word,UserType,request);	
	                break; 
	            case "linked_prods": //
	            	jobj = linked_prods(word,UserType,request);
	            	break;    
	           
	            case "CheckDlinkDate": //
	            	jobj = CheckDlinkDate(dt_alloc,id);	
	                break;
	                
	           
	           
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in A_Install.");
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
	
	public JSONObject CheckDlinkDate(String dt_alloc , String id_wh)
	{
		JSONObject jobj = new JSONObject();
		
		String  sql ="select (replace(convert(NVARCHAR, link_date, 103), ' ', '-')) as link_date from A_Ware_House where id_wh = "+id_wh+" and link_date > '"+dt_alloc+"' ";
			
			 
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		    if(rs.next())
		    {
		    	jobj.put("data", 0);
		    	jobj.put("dt_alloc", rs.getString(1));
	        }
		    else
		    {
		    	jobj.put("data", 1);
		    	
		    }
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in UnInstall");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject update1(HttpServletRequest request)
	{   DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
        DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd"); 
		JSONObject jobj = new JSONObject();
		System.out.println("hello world");
		String total_count[] = request.getParameterValues("uninstallAsset");
		 int j=0;
		 
		for(int i=0; i<total_count.length;i++)
		{  try {
			String DlinkedDate = request.getParameter("uninstallAssetDate"+total_count[i]);  
		    DlinkedDate = dateFormatNeeded.format(userDateFormat.parse(DlinkedDate));
			String remark = request.getParameter("uninstallRmk"+total_count[i]);
			String sql = "update A_Ware_House set parent = "+0+",rmk_asst = '"+remark+"',Dlinked_date = '"+DlinkedDate+"' where id_wh = " + total_count[i] +";";
		    System.out.println(sql);
		
			 PreparedStatement ps=con.prepareStatement(sql);
			 
				j=ps.executeUpdate();
				
				
				if(j>0)
				{
					sql = "update A_Accesssory_History set remark = '"+remark+"',dt_dlink = '"+DlinkedDate+"' where id_accessory = " + total_count[i] +";";
					ps=con.prepareStatement(sql);
					j=ps.executeUpdate();
					if(j>0)
					{
					jobj.put("data",1);
					}
				}
			 }
			 catch(Exception e){}
		 
		}
		return jobj;
		
		
	}
	
	public JSONObject link_accessories(HttpServletRequest request)
	{   DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
        DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd"); 
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String total_count[] = request.getParameterValues("bulkInstallAsset");
		String parent =  request.getParameter("to_assign");
		String linkedDate = request.getParameter("dt_allocate");
		try {
		linkedDate = dateFormatNeeded.format(userDateFormat.parse(linkedDate));}
		catch(Exception e){}
		 int parent1  = Integer.valueOf(parent);
		 int j=0;
		 
		for(int i=0; i<total_count.length;i++)
		{   String remark = request.getParameter("installRmk"+total_count[i]);
			String sql = "update A_Ware_House set parent = "+parent1+",rmk_asst = '"+remark+"',link_date='"+ linkedDate +"',id_loc=(select id_loc from A_Ware_House where id_wh="+parent1+"),id_subl=(select id_subl from A_Ware_House where id_wh="+parent1+"),id_building=(select id_building from A_Ware_House where id_wh="+parent1+"),id_flr=(select id_flr from A_Ware_House where id_wh="+parent1+"),id_dept=(select id_flr from A_Ware_House where id_wh="+parent1+"),device_status='link_to_asset' where id_wh = " + total_count[i] +";";
		    System.out.println(sql);
		 try{
			 PreparedStatement ps=con.prepareStatement(sql);
			 
				j=ps.executeUpdate();
				
				
				if(j>0)
				{
					sql = "insert into A_Accesssory_History(id_parent,id_accessory,dt_link,remark)values("+parent1+","+total_count[i]+",'"+linkedDate+"','"+remark+"')";
					ps=con.prepareStatement(sql);
					j=ps.executeUpdate();
					if(j>0)
					{
					jobj.put("data",1);
					}
				}
			 }
			 catch(Exception e){}
		 
		}
		return jobj;
		
		
	}
	
	public JSONObject Display_additional(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		
		String sql="select awh.id_wh_dyn,msd.nm_s_assetdiv from A_Ware_House awh, M_Subasset_Div msd where awh.id_sgrp= msd.id_s_assetdiv and awh.id_wh = " + id +"";	
		System.out.println(sql);
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
		  //  System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in M_Emp_User.");
		}
		 
		return jobj;
		
	}

	public JSONObject DropDownResult(String id,String UserType,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String tempSql="";
    	DtoCommon dtoCommon = new DtoCommon();
		if(!UserType.equals("Super"))
    	{
        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
        	tempSql="select id_wh,concat(serial_no,'--',ds_pro) as nm_prod from A_Ware_House wh where allocate=1 "+tempQuery2+"";
        	
        	
    	}
    	else
			tempSql="select id_wh,concat(serial_no,'--',ds_pro) as nm_prod from A_Ware_House wh where allocate=1";
    	
		
		System.out.println(tempSql);
		try
		{
		ps=con.prepareStatement(tempSql);
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
		  //  System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in M_Emp_User.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	
	public JSONObject linked_prods(String searchWord,String UserType,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		String tempSql="";
		DtoCommon dtoCommon = new DtoCommon();
		if(!UserType.equals("Super"))
    	{
        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"awh1","awh1", request);
        	
        		tempSql="select awh1.link_date,awh1.id_wh,awh1.rmk_asst,awh1.id_wh_dyn,awh1.ds_pro,awh1.serial_no,awh1.rmk_asst,awh2.id_wh as asset_id,awh2.id_wh_dyn as asset_cd,awh2.ds_pro as asset_nm from A_Ware_House awh1,A_Ware_House awh2 where awh1.parent != 0 and awh1.parent = awh2.id_wh and  ( awh1.id_wh_dyn like '"+searchWord+"%' or awh1.serial_no like '"+searchWord+"%' or awh1.ds_pro like '"+searchWord+"%') "+tempQuery2+"";
        	
        	
    	}
    	else
			tempSql="select awh1.link_date,awh1.id_wh,awh1.rmk_asst,awh1.id_wh_dyn,awh1.ds_pro,awh1.serial_no,awh1.rmk_asst,awh2.id_wh as asset_id,awh2.id_wh_dyn as asset_cd,awh2.ds_pro as asset_nm from A_Ware_House awh1,A_Ware_House awh2 where awh1.parent != 0 and awh1.parent = awh2.id_wh and  ( awh1.id_wh_dyn like '"+searchWord+"%' or awh1.serial_no like '"+searchWord+"%' or awh1.ds_pro like '"+searchWord+"%')";
		System.out.println(tempSql);
		try
		{
		ps=con.prepareStatement(tempSql);
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
			System.out.println("sql error in M_Emp_User.");
		}
		 
		return jobj;
		
	}
	
	public JSONObject link_down(String searchWord,String UserType,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		String tempSql="";
		DtoCommon dtoCommon = new DtoCommon();
		if(!UserType.equals("Super"))
    	{
        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
        	
        		tempSql="select id_wh,id_wh_dyn,no_model,ds_pro,serial_no,rmk_asst from A_Ware_House wh where (typ_asst='accessories' or typ_asst='soft' or typ_asst='software') and parent = 0 and  ( id_wh_dyn like '%"+searchWord+"%' or serial_no like '%"+searchWord+"%' or ds_pro like '%"+searchWord+"%')  "+tempQuery2+" ";
        	
        	
    	}
    	else
			tempSql="select id_wh,id_wh_dyn,no_model,ds_pro,serial_no,rmk_asst from A_Ware_House wh where (typ_asst='accessories' or typ_asst='soft' or typ_asst='software') and parent = 0 and  ( id_wh_dyn like '%"+searchWord+"%' or serial_no like '%"+searchWord+"%' or ds_pro like '%"+searchWord+"%')";
		System.out.println(tempSql);
		try
		{
		ps=con.prepareStatement(tempSql);
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
			System.out.println("sql error in M_Emp_User.");
		}
		 
		return jobj;
		
	}
	
	
	
	
	
}
