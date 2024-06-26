package com.Asset;

import java.io.IOException;
import javax.servlet.http.HttpSession;
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
import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;

import dto.Common.DtoCommon;


public class A_Edit_From_Store extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	Date currDate = new Date();
	 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		 HttpSession session = request.getSession();  
		 String paramName="";
		 String paramValues="";
		 try
		 {
		        
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      String[] parts = paramName.split("_");
		      paramValues = request.getParameter(paramName);
		      if((parts[0].equals("dt") || (parts[0].equals("endt")) || (parts[0].equals("std"))) && !paramValues.equals(""))
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
		    
		String id="",action="";
		String id_grp="",id_sgrp="",id_loc="",id_subl="",typ_asst="",searchWord="";
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("action") !=null)
		{
			action = request.getParameter("action");
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
		if(request.getParameter("id_subl") !=null)
		{
			id_subl = request.getParameter("id_subl");
		}
		if(request.getParameter("typ_asst") !=null)
		{
			typ_asst = request.getParameter("typ_asst");
		}	
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}	
		String temp="";
		if(!id_grp.equals(""))
		{
			if(!id_sgrp.equals(""))
			{
				temp += " and wh.id_grp = "+id_grp+" and wh.id_sgrp = "+id_sgrp+"";
			}
			else
			{
				temp += " and wh.id_grp = "+id_grp+"";
			}
			
		}
		if(!id_loc.equals(""))
		{
			if(!id_subl.equals(""))
			{
				temp += " and wh.id_loc = "+id_loc+" and wh.id_subl = "+id_subl+"";
			}
			else
			{
				temp += " and wh.id_loc = "+id_loc+"";
			}
		}
		if(!typ_asst.equals(""))
		{
			temp += " and wh.typ_asst = '"+typ_asst+"'";
		}
			
		int id_emp_user = (int)session.getAttribute("id_emp_user");
		String UserType = (String)session.getAttribute("UserType"); 
		
		String FlrId = (String)session.getAttribute("FlrId");
		int DivId = (int)session.getAttribute("DivId");
		if(!UserType.equals("SUPER")){
			//String Query = Common.returnQuery(FlrId);
			//temp = temp + " "+Query+" and wh.id_div ="+DivId+"";
		}
		  
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        
	            case "Display":
	            	jobj = DisplayAssetFromStoreForEdit(temp , searchWord,UserType, request);	
	                break;
	            case "DisplayAccess":
	            	jobj = DisplayAccess(id);	
	                break; 
	            case "Edit":
	            	jobj = DataForEditFromStore(id);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateDataForEditFromStore(map , id,id_emp_user,  request);	
	                break;
	            case "damagedash":
	            	jobj = DamageDashboard(map , id,id_emp_user,UserType,  request);	
	                break;
	            case "RecentAddDash":
	            	jobj = RecentAddDashboard(map , id,id_emp_user,UserType,  request);	
	                break;
	            case "Track":
	            	jobj = RecentTrackHistory(id);	
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
		
		
	
	
	
	public JSONObject RecentTrackHistory(String id) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select * from A_Asset_Track where id_wh='"+id+"' order by dt_edit ";
		String empname="";
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
		    		//System.out.println();
		    		String name=metaData.getColumnName(i);
		    		if(name.equals("edit_by")) {
		    			String ids=rs.getString(name);
		    			//System.out.println(ids);
		    			String sql2="select nm_emp from M_Emp_User where id_emp_user='"+ids+"'";
		    			ps=con.prepareStatement(sql2);
		    			rs1=ps.executeQuery();
		    			//System.out.println(sql2);
		    			if(rs1.next()) {
		    			empname= rs1.getString("nm_emp");
		    			//System.out.println(empname);
		    			//System.out.println("empname!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+empname);
		    			
		    			jobj2.put(name,empname);
		    			}
		    		}
		    		else
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





	public JSONObject RecentAddDashboard(HashMap<String, String> map, String id, int id_emp_user,String UserType,HttpServletRequest request) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql;
		DtoCommon dtoCommon = new DtoCommon();
		if(!UserType.equals("Super"))
    	{
			
        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"awh","awh", request);
		sql="select top 10 awh.id_wh, nm_model, awh.ds_pro,awh.typ_asst, awh.tt_un_prc, awh.id_loc,awh.id_wh_dyn from A_ware_house awh, M_model mm where awh.id_model=mm.id_model and awh.typ_asst!='accessories' "+tempQuery2+" Order By id_wh DESC ";
    	}
		else {
			sql="select top 10 awh.id_wh, nm_model, awh.ds_pro,awh.typ_asst, awh.tt_un_prc, awh.id_loc,awh.id_wh_dyn from A_ware_house awh, M_model mm where awh.id_model=mm.id_model and awh.typ_asst!='accessories' Order By id_wh DESC";
		}
		try
		{
			System.out.println(sql);
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
			System.out.println("sql error in A_ware_house dashboard recent product.");
		}
		 
		return jobj;
	
	


	}





	public JSONObject DamageDashboard(HashMap<String, String> map, String id, int id_emp_user,String UserType,HttpServletRequest request) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql;
		DtoCommon dtoCommon = new DtoCommon();
		if(!UserType.equals("Super"))
    	{
			
        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
		sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model,wh.tt_un_prc,wh.typ_asst from A_Ware_House wh , A_Invoice_Master inv , M_Vendor v ,M_Model m where  inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and id_rm_approve=0 and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr') "+tempQuery2+"";
    	}
		else {
			sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model,wh.tt_un_prc,wh.typ_asst from A_Ware_House wh , A_Invoice_Master inv , M_Vendor v ,M_Model m where  inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and id_rm_approve=0 and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr')";
		}
		try
		{
//			System.out.println(sql);
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
			System.out.println("sql error in A_ware_house dashboard damage.");
		}
		 
		return jobj;
	}
	





	public JSONObject UpdateDataForEditFromStore(HashMap<String, String> map , String id,int id_emp_user,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("A_Ware_House",request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_wh")
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
		
		String query="update A_Ware_House set "+col_Val+",edit_by="+id_emp_user+" where id_wh="+id+"";
		//		System.out.println(query);
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
	
	public JSONObject UpdateDamageDataForEditFromStore(HashMap<String, String> map , String id,int id_emp_user,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		String repo_mngr="";
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("A_Ware_House",request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_wh")
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
						
						
						String query  = "select repo_mngr from M_emp_user where id_emp_user="+id_emp_user+"";
						//						System.out.println(query);
						ps=con.prepareStatement(query);
						rs=ps.executeQuery();
						
						if(rs.next())
						{
								repo_mngr=rs.getString("repo_mngr");
						}
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in M_Asset_Div.");
				}
		
		
		
		
		String query="update A_Ware_House set "+col_Val+",edit_by="+id_emp_user+",repo_mngr='"+repo_mngr+"' where id_wh="+id+"";
		//		System.out.println(query);
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
	
	
	public JSONObject UpdateApproveDamageDataForEditFromStore(HashMap<String, String> map , String id,int id_emp_user,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("A_Ware_House",request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_wh")
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
		
		String query="update A_Ware_House set "+col_Val+",edit_by="+id_emp_user+",id_rm_approve=1 where id_wh="+id+"";
		//		System.out.println(query);
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
	
	
	public JSONObject DataForEditFromStore(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			sql="select *,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtinv,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtpo,(replace(convert(NVARCHAR, endt_lease, 103), ' ', '-')) as endtlease,(replace(convert(NVARCHAR, std_lease, 103), ' ', '-')) as stdlease,(replace(convert(NVARCHAR, dt_amc_exp, 103), ' ', '-')) as dtAmcExp,(replace(convert(NVARCHAR, dt_amc_start, 103), ' ', '-')) as dtAmcStart from A_Ware_House  where id_wh = "+id+" ";
			//			System.out.println(sql);
		
		
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
	
	public JSONObject DisplayAssetFromStoreForEdit(String temp , String searchWord,String UserType,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String tempSql="";
    	DtoCommon dtoCommon = new DtoCommon();
		if(!temp.equals(""))
		{
			if(!UserType.equals("Super"))
        	{
				
            	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
            	
            		tempSql="select distinct id_wh_dyn,wh.typ_asst,nm_dept,id_wh,ds_pro,no_mfr,wh.id_loc,serial_no,nm_model,nm_assetdiv,nm_s_assetdiv from A_Ware_House wh , M_Model m, M_Asset_Div mas, M_Subasset_Div msd,M_Dept d where wh.id_dept=d.id_dept  and m.id_model=wh.id_model and mas.id_assetdiv=wh.id_grp and  msd.id_s_assetdiv=wh.id_sgrp and st_trvl=0 and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%') "+tempQuery2+" "+temp+"";
            	
            	}
        	else
				tempSql="select distinct id_wh_dyn,wh.typ_asst,nm_dept,id_wh,ds_pro,no_mfr,wh.id_loc,serial_no,nm_model,nm_assetdiv,nm_s_assetdiv from A_Ware_House wh , M_Model m, M_Asset_Div mas, M_Subasset_Div msd,M_Dept d where wh.id_dept=d.id_dept  and m.id_model=wh.id_model and mas.id_assetdiv=wh.id_grp and  msd.id_s_assetdiv=wh.id_sgrp and st_trvl=0 and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%')"+temp+"";
			       	System.out.println(tempSql);
			
		
			
		}
		else
			if(!UserType.equals("Super"))
        	{
            	String tempQuery2 = dtoCommon.MakeTempQuery(UserType,"wh","wh", request);
            	
            		tempSql="select distinct id_wh_dyn,wh.typ_asst,nm_dept,id_wh,ds_pro,no_mfr,wh.id_loc,serial_no,nm_model,nm_assetdiv,nm_s_assetdiv from A_Ware_House wh , M_Model m, M_Asset_Div mas, M_Subasset_Div msd,M_Dept d where wh.id_dept=d.id_dept  and m.id_model=wh.id_model and mas.id_assetdiv=wh.id_grp and  msd.id_s_assetdiv=wh.id_sgrp and st_trvl=0 and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%') "+tempQuery2+" ";
            	
            	
        	}
        	else
				tempSql="select distinct id_wh_dyn,wh.typ_asst,nm_dept,id_wh,ds_pro,no_mfr,wh.id_loc,serial_no,nm_model,nm_assetdiv,nm_s_assetdiv from A_Ware_House wh , M_Model m, M_Asset_Div mas, M_Subasset_Div msd,M_Dept d where wh.id_dept=d.id_dept  and m.id_model=wh.id_model and mas.id_assetdiv=wh.id_grp and  msd.id_s_assetdiv=wh.id_sgrp and st_trvl=0 and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%')";
		//       	System.out.println(tempSql);
		try
		{
			System.out.println(tempSql);
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
			System.out.println("sql error in A_Install.");
		}
		 
		return jobj;
	}
	
	public JSONObject DisplayAccess(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		//String sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where allocate = 0 and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven";
		String sql="";
		
			 sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model,(replace(convert(NVARCHAR, wh.link_date, 103), ' ', '-')) as dtlink from A_Ware_House wh , A_Invoice_Master inv , M_Vendor v ,M_Model m where  inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and m.id_model=wh.id_model and wh.parent='"+id+"' ";
			
		try
		{
			//			System.out.println(sql);
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
			System.out.println("sql error in A_Install.");
		}
		 
		return jobj;
	}
	
	public JSONObject DisplayDamageAssetFromStoreForEdit(String temp , String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		//String sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where allocate = 0 and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven";
		String sql="";
		if(!temp.equals(""))
		{
			/*
			 * sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model from A_Ware_House wh , g_grn inv , M_Vendor v ,M_Model m where  inv.id_grn=wh.id_grn and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and (wh.serial_no like '"
			 * +searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"
			 * +searchWord+"%')"+temp+"";
			 */
			sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model from A_Ware_House wh , A_Invoice_Master inv , M_Vendor v ,M_Model m where  inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and id_rm_approve=0 and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr') and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%')"+temp+"";
		}
		else
		 //sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model from A_Ware_House wh , g_grn inv , M_Vendor v ,M_Model m where inv.id_grn=wh.id_grn and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and (wh.serial_no like  '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%') ";
			sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model from A_Ware_House wh , A_Invoice_Master inv , M_Vendor v ,M_Model m where  inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and id_rm_approve=0 and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr') and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%')";
		try
		{
			//			System.out.println(sql);
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
			System.out.println("sql error in A_Install.");
		}
		 
		return jobj;
	}
	
	public JSONObject DisplayApproveDamageAssetFromStoreForEdit(String temp , String searchWord, int id_emp_user)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		//String sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where allocate = 0 and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven";
		String sql="";
		if(!temp.equals(""))
		{
			/*
			 * sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model from A_Ware_House wh , g_grn inv , M_Vendor v ,M_Model m where  inv.id_grn=wh.id_grn and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and (wh.serial_no like '"
			 * +searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"
			 * +searchWord+"%')"+temp+"";
			 */
//			sql="select distinct(id_wh_dyn),nm_emp,id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model from A_Ware_House wh , g_grn inv , M_Vendor v ,M_Model m  ,M_Emp_User eu where wh.to_assign=eu.id_emp_user and  inv.id_grn=wh.id_grn and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and id_rm_approve=0 and wh.repo_mngr="+id_emp_user+" and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr') and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%')"+temp+"";
			sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,wh.id_loc,serial_no,nm_model from A_Ware_House wh , M_Model m where   m.id_model=wh.id_model and st_trvl=0 and id_rm_approve=0 and wh.repo_mngr="+id_emp_user+" and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr') and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%')"+temp+"";
		}
		else
		 //sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model from A_Ware_House wh , g_grn inv , M_Vendor v ,M_Model m where inv.id_grn=wh.id_grn and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and (wh.serial_no like  '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%') ";
		//	sql="select distinct(id_wh_dyn),nm_emp,id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model from A_Ware_House wh , g_grn inv , M_Vendor v ,M_Model m ,M_Emp_User eu where wh.to_assign=eu.id_emp_user and  inv.id_grn=wh.id_grn and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and id_rm_approve=0 and wh.repo_mngr="+id_emp_user+" and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr') and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%')";
		sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,wh.id_loc,serial_no,nm_model from A_Ware_House wh  ,M_Model m where m.id_model=wh.id_model and st_trvl=0 and id_rm_approve=0 and wh.repo_mngr="+id_emp_user+" and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr') and (wh.serial_no like '"+searchWord+"%' or nm_model like '"+searchWord+"%' or id_wh_dyn like '"+searchWord+"%')";
		try
		{
			//		System.out.println(sql);
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
			System.out.println("sql error in A_Install.");
		}
		 
		return jobj;
	}
	
}