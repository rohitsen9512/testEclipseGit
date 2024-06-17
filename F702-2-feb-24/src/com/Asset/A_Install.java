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


public class A_Install extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	ResultSet rs4=null;
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
			    
		String asst_stat="",id_dept1="", temp1="",id_div="",id_deptinst="" ,id_bu="",action = "",id_wh_dyn="",upload_asset="",word="",DisplayType="",likeTempQuery="",dt_allocate="",id="",uninstallAssetDate="",bulkinstallAssetDate="",to_assign="",id_flr="",id_cc="";
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
		if(request.getParameter("id_dept") !=null)
		{
			id_dept1 = request.getParameter("id_dept");
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
	        case "DropDownResult":
            	jobj = DropDownResult(id);	
                break;
	        case  "Display_additional":
            	jobj = Display_additional(id);
            	break;    
	        case "link_accessories":
        		jobj = link_accessories(request);
        		break;	
	        case "Update1":
            	jobj = update1(request);
            	break;	
		        case "Save":
	            	jobj = InstallAsset(map,id,dt_alloc,Edit,id_emp_user,  id_wh_dyn ,  UserId,  request);	
	                break;
		                
	           
	            case "Edit":
	            	jobj = EditDataForInstall(id);	
	                break;
	            case "Repair":
	            	jobj = Repair(id);	
	                break;
	            case "Sold":
	            	jobj = Sold(id);	
	                break;
	            case "Active":
	            	jobj = Active(id);	
	                break;
	            case "link_down":
	            	jobj = link_down(word);	
	                break; 
	            case "linked_prods":
	            	jobj = linked_prods(word);
	            	break;    
	            case "Search":
	            	String tempSub="",id_s_assetdiv="";
	            	
	            	System.out.println("subgroup id is---- "+id_s_assetdiv);
	            	System.out.println("temp sub is---- "+tempSub);
	            	DtoCommon dtoCommon = new DtoCommon();
	            	
	            	if(DisplayType.equals("UnInstall"))
        			{
	            		if(!UserType.equals("Super"))
	                	{
	            			
	            			String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
	            			
	                    		if(!tempSub.equals(""))
	        	            		tempSql="select distinct top 100 id_wh_dyn,id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,dt_alloc,rmk_asst,remarks,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc ,nm_emp,cd_emp from A_Ware_House wh ,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No'  and (device_status='allct_to_emp' or device_status='allct_to_emp_temp') "+tempQuery2+" "+temp+" "+tempSub+" ";
	        	            		else
	        		            	tempSql="select distinct top 100 id_wh_dyn,id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,dt_alloc,rmk_asst,remarks,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc ,nm_emp,cd_emp from A_Ware_House wh ,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No' and (device_status='allct_to_emp' or device_status='allct_to_emp_temp') "+tempQuery2+" "+temp+"  ";
	                    	
	            		
	                	}
	            		else {
	            			if(!tempSub.equals(""))
	    	            		tempSql="select distinct top 100 id_wh_dyn,id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,dt_alloc,remarks,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc ,nm_emp,cd_emp from A_Ware_House wh ,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No'  and (device_status='allct_to_emp' or device_status='allct_to_emp_temp') "+temp+" "+tempSub+" ";
	    	            		else
	    		            	tempSql="select distinct top 100 id_wh_dyn,id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,dt_alloc,remarks,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc ,nm_emp,cd_emp from A_Ware_House wh ,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No' and (device_status='allct_to_emp' or device_status='allct_to_emp_temp') "+temp+"  ";
	            		}
	            		jobj = uad.QueryMacker1(tempSql , UserType , word ,id_dept,  request);
        			}
        			else {
        				if(!UserType.equals("Super"))
	                	{
	            			String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
	            			
	                    		if(!tempSub.equals(""))
	    	            			tempSql="select distinct top 100 (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,remarks,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh ,M_Model modl where  wh.id_model=modl.id_model and ( allocate =0 or  allocate !=1) and sold='No' and (wh.typ_asst='IT' or wh.typ_asst='nonit' or wh.typ_asst='NON-IT' or wh.typ_asst='NON-IT')  and (device_status='in_store' or device_status='temporary_laptop'  or device_status='advance_laptop' or device_status='refreshment_working') "+tempQuery2+" "+temp+" "+tempSub+" ";
	            				else
	                		tempSql="select distinct top 100 (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,remarks,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh ,M_Model modl where  wh.id_model=modl.id_model and ( allocate =0 or  allocate !=1) and sold='No' and (wh.typ_asst='IT' or wh.typ_asst='nonit' or wh.typ_asst='NON-IT' or wh.typ_asst='NON-IT') and (device_status='in_store' or device_status='temporary_laptop'  or device_status='advance_laptop' or device_status='refreshment_working') "+tempQuery2+" "+temp+" ";
	                    	
	            		
	                	}
	            		else {
	            			if(!tempSub.equals(""))
	            				tempSql="select distinct top 100 (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,remarks,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh ,M_Model modl where  wh.id_model=modl.id_model and ( allocate =0 or  allocate !=1) and sold='No' and (wh.typ_asst='IT' or wh.typ_asst='nonit' or wh.typ_asst='NON-IT' or wh.typ_asst='NON-IT')  and (device_status='in_store' or device_status='temporary_laptop' or device_status='advance_laptop' or device_status='refreshment_working') "+temp+" "+tempSub+" and (wh.ds_pro LIKE '%"+word+"%' or wh.no_mfr LIKE '%"+word+"%' or wh.ds_asst LIKE '%"+word+"%' or wh.serial_no LIKE '%"+word+"%' or wh.no_model LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%' )";
	        				else
	            		tempSql="select distinct top 100 (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,remarks,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh ,M_Model modl where  wh.id_model=modl.id_model and ( allocate =0 or  allocate !=1) and sold='No' and (wh.typ_asst='IT' or wh.typ_asst='nonit' or wh.typ_asst='NON-IT' or wh.typ_asst='NON-IT') and (device_status='in_store' or device_status='temporary_laptop' or device_status='advance_laptop' or device_status='refreshment_working') "+temp+" and (wh.ds_pro LIKE '%"+word+"%' or wh.no_mfr LIKE '%"+word+"%' or wh.ds_asst LIKE '%"+word+"%' or wh.serial_no LIKE '%"+word+"%' or wh.no_model LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%' )";
	            		}
        				jobj = uad.QueryMacker1(tempSql , UserType , word ,id_dept,  request);
        			}
	              	System.out.println(tempSql);
	                break;
	                
	            	case "SearchSoft":
	            		 tempSub="";
	            		 id_s_assetdiv="";
	            		 
		            	id_s_assetdiv=request.getParameter("id_s_assetdiv");
		            	if(!id_s_assetdiv.equals(""))
		        		{
		            		 id_s_assetdiv=request.getParameter("id_s_assetdiv");
		            		 tempSub=" and wh.id_sgrp="+id_s_assetdiv+" ";
		        		}
		            	System.out.println("subgroup id is---- "+id_s_assetdiv);
		            	System.out.println("temp sub is---- "+tempSub);
	            	
	            	if(UserType.equals("SUPER"))
	            	{
	            		
	            		if(DisplayType.equals("UnInstall"))
	            		{
		            		tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,dt_alloc,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc ,nm_emp,cd_emp from A_Ware_House wh ,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit')";
	            		}
	            		else
		        			tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh ,M_Model modl where  wh.id_model=modl.id_model and ( allocate =0 or  allocate !=1) and sold='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') ";
	            		
	            	}
	            	if(UserType.equals("RIT"))
	            		{
	            		
	            			if(DisplayType.equals("UnInstall"))
	            			{
			            		tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,dt_alloc,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc ,nm_emp,cd_emp from A_Ware_House wh ,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') "+temp+"";
		            		}
	            			else
			        			tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh ,M_Model modl  where wh.id_model=modl.id_model and ( allocate =5) and sold='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') "+temp+"";
		            		
	            		}
	            		if(UserType.equals("NIT"))
	            		{
		            		
	            			if(DisplayType.equals("UnInstall"))
	            			{
			            		tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,ds_asst,serial_no,id_subl,dt_alloc,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh,M_Model modl   where allocate =1 and wh.id_model=modl.id_model and sold='No' and st_trvl=0 and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') "+temp+"";
		            		}
	            			else
			        			tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh,M_Model modl  where  wh.id_model=modl.id_model and ( allocate =5 or allocate !=1) and sold='No' and st_trvl=0 and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') "+temp+"";
		            		
	            		}
	            		
	            		if(UserType.equals("RHead"))
	            		{
		            		
	            			if(DisplayType.equals("UnInstall"))
	            			{
			            		tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,dt_alloc,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc ,nm_emp from A_Ware_House wh ,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No' and st_trvl=0 and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') "+temp+"";
		            		}
	            			else
			        			tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh,M_Model modl  where  wh.id_model=modl.id_model and ( allocate =5 or allocate !=1) and sold='No' and st_trvl=0 and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') "+temp+"";
		            		
	            		}
	            		
	            	if(UserType.equals("DEPT"))
	            	{
	            		tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc,nm_emp,cd_emp from A_Ware_House wh,M_Emp_User emp,M_Model modl where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model  and ( allocate =5 or allocate !=1) and st_trvl=0 and sold='No' and st_config ='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') "+temp1+"";
	            		
	            	if(DisplayType.equals("UnInstall"))
	            		tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,ds_asst,serial_no,id_subl,dt_alloc,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc,nm_emp from A_Ware_House wh,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No' and st_trvl=0 and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') "+temp+"";
	            	else
	        			tempSql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh,M_Model modl  where  wh.id_model=modl.id_model and ( allocate =5 or allocate !=1) and st_trvl=0 and sold='No' and st_config='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') "+temp+"";
	            	}
	            	
	            	
	            	
	            	if(DisplayType.equals("UnInstall"))
        			{
	            		if(!tempSub.equals(""))
	            		tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,dt_alloc,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc ,nm_emp,cd_emp from A_Ware_House wh ,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') and (device_status='allct_to_emp' or device_status='allct_to_emp_temp') "+temp+" "+tempSub+" ";
	            		else
			            	tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,dt_alloc,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc ,nm_emp,cd_emp from A_Ware_House wh ,M_Emp_User emp,M_Model modl  where wh.to_assign=emp.id_emp_user and wh.id_model=modl.id_model and allocate =1 and sold='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') and (device_status='allct_to_emp' or device_status='allct_to_emp_temp') "+temp+"  ";
	            		jobj = uad.QueryMacker(tempSql , UserType , word ,id_dept,  request);
        			}
        			else {
        				if(!tempSub.equals(""))
        				tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh ,M_Model modl where  wh.id_model=modl.id_model and ( allocate =0 or  allocate !=1) and sold='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') and (device_status='in_store' or device_status='temporary_laptop'  or device_status='physical_dmg_mnr' or device_status='advance_laptop' or device_status='refreshment_working') "+temp+" "+tempSub+" ";
        				else 	
            				tempSql="select distinct  (id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,serial_no,id_subl,rmk_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh ,M_Model modl where  wh.id_model=modl.id_model and ( allocate =0 or  allocate !=1) and sold='No' and (modl.typ_asst!='soft' or modl.typ_asst!='nonit') and (device_status='in_store' or device_status='temporary_laptop'  or device_status='physical_dmg_mnr' or device_status='advance_laptop' or device_status='refreshment_working') "+temp+" ";
        				jobj = uad.QueryMacker1(tempSql , UserType , word ,id_dept,  request);
        			}
	              	System.out.println(tempSql);	
	  	          
	            	
	            	
	            	
	                break;
	                
	                
	            case "CheckDate":
	            	jobj = CheckDate(dt_grn,id_grn,id);	
	                break;
	            case "BulkInstallCheckDate":
	            try
	            {
	            	 
	        		int t=1;
	            	String  sql ="",id_trvl="";
	            	String d = "1900-01-01",d2="1900-01-01";
	            	String d1 = "1900-01-01",d3="1900-01-01";
	            	String d4 = "1900-01-01",d5="1900-01-01";
	            	for(int i=0; i<chk.length;i++)
	            	{
	            		id = chk[i];
	            		String SQL1 ="select typ_asst from A_Ware_House where id_wh="+id+" and allocate=5 and to_assign=0 and dt_alloc='1900-01-01'";
	            		ps=con.prepareStatement(SQL1);
	            		rs=ps.executeQuery();
	            		if(rs.next())
	            		{
	            			typ_asst = rs.getString(1);
	            			if(typ_asst.equals("IT"))
	                		{
	                		  sql ="select dt_config,(replace(convert(NVARCHAR, dt_config, 103), ' ', '-')) as dt_config from A_Config where id= (select MAX(id) from A_Config where id_wh=8 ) and dt_config > '"+bulkinstallAssetDate+"' ";
	                			  ps=con.prepareStatement(sql);
	                        		rs=ps.executeQuery();
	                        		    if(rs.next())
	                        		    {
	                        		    	t=0;
	                        		    	d = rs.getString(1);
	                        		    	d2 = rs.getString(2);
	                        	        }
	                		}else
	                		{
	                			sql ="select dt_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dt_inv from A_Ware_House where id_wh="+id+" and dt_inv > '"+bulkinstallAssetDate+"' ";
	              			  ps=con.prepareStatement(sql);
	                      		rs=ps.executeQuery();
	                      		    if(rs.next())
	                      		    {
	                      		    	t=0;
	                      		    	d = rs.getString(1);
	                      		    	d2 = rs.getString(2);
	                      	        }
	                		}
	            		}else
	            		{
	            			sql ="select dt_de_allocate,(replace(convert(NVARCHAR, dt_de_allocate, 103), ' ', '-')) as dt_de_allocate from A_Ware_House where id_wh="+id+" and dt_de_allocate > '"+bulkinstallAssetDate+"' ";
	                		ps=con.prepareStatement(sql);
	                		rs=ps.executeQuery();
	                		    if(rs.next())
	                		    {
	                		    	t=0;
	                		    	d1 = rs.getString(1);
	                		    	d3 = rs.getString(2);
	                	        }
	            		}
	            	
	            	}
	            	if(t == 1)
	            	{
	            		jobj.put("data", 1);
	            	}
	            	else
	            	{
	            		if(sdf.parse(d).before(sdf.parse(d1)))
	            		{
	                			jobj.put("data", 0);
	                    		jobj.put("dt_grn",d3);
	            		}
	            		else
	            		{
	                			jobj.put("data", 0);
	                    		jobj.put("dt_grn",d2);
	            		}
	            		

	            	}		
	            		}
	            		catch(Exception e)
	            		{
	            			System.out.println("sql error in A_Install while checking bulk install date.");
	            		}
	            		
	                break;
	            case "CheckUnInstallDate":
	            	jobj = CheckUnInstallDate(dt_alloc,id);	
	                break;
	                
	            case "BulkInstall":
            		try
    				{
            			String table="<table style='border:1px solid black'><tr style='border:1px solid black'><td style='border:1px solid black'><center><strong>SL.NO</strong></center></td>" +
    							"<td style='border:1px solid black'><center><strong>Asset ID</strong></center></td>" +
    							"<td style='border:1px solid black'><center><strong>Serial No.</strong></center></td>" +
    							"<td style='border:1px solid black'><center><strong>Item Name</strong></center></td>" +
    							"<td style='border:1px solid black'><center><strong>Type</strong></center></td>" +
    							"<td style='border:1px solid black'><center><strong>Group</strong></center></td>" +
    							"<td style='border:1px solid black'><center><strong>Sub-Group</strong></center></td></tr>";
            			String colName="",value="",query="",itemName="",slNo="",nm_model="";
            			List<String> recipients = new ArrayList<String>();
            			DtoCommon dtcm = new DtoCommon();	
            			rs = Common.GetColumns("A_Iut_History",  request);
	    				colName="";value="";
	    				while (rs.next())
	    				{
	    				
	    					if(rs.getString(1) !="id_iut")
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
	    				int serialNo    =   0;	
	            	for(int i=0; i<chk.length;i++)
	            	{
	            		
	            		j=0;
	            		id=chk[i];
	            		uninstallRmk =request.getParameter("installRmk"+chk[i]);
	            		device_status2 =request.getParameter("device_status"+chk[i]);
	            		String sql="select dt_allocate from A_Ware_House where allocate in(0,5) and id_wh="+chk[i]+"";
	            		System.out.println(sql);
	            		PreparedStatement ps=con.prepareStatement(sql);
		    			rs=ps.executeQuery();
		    			if(rs.next())
		    			{
		    				query =" update A_Ware_House  set st_config ='No',allocate= 1, dt_alloc = '"+bulkinstallAssetDate+"',dt_allocate = '"+bulkinstallAssetDate+"' "+
		    	            		" , id_loc = "+id_loc+",id_dept="+id_dept1+",id_subl = "+id_subl+",id_building="+id_building+",device_status ='"+device_status2+"' , to_assign = "+to_assign+" , id_flr = "+id_flr+",id_cc = 0 ,rmk_asst='"+uninstallRmk+"',repo_mngr="+repo_mngr+",edit_by="+id_emp_user+" where id_wh = "+chk[i]+"";
		    			}
		    			else
		    			{
		    				query =" update A_Ware_House  set allocate= 1, dt_alloc = '"+bulkinstallAssetDate+"' "+
			    	            		"  , id_loc = "+id_loc+",id_dept="+id_dept1+",id_subl = "+id_subl+",id_building="+id_building+",device_status = '"+device_status2+"' , to_assign = "+to_assign+" , id_flr = "+id_flr+",id_cc = 0 ,rmk_asst='"+uninstallRmk+"',edit_by="+id_emp_user+" where id_wh = "+chk[i]+"";
		    			}
		    			System.out.println(query);
		    			ps=con.prepareStatement(query);
		    			j=ps.executeUpdate();
		    			if(j>0)
		    			{
		    				j=0;
		    				query = "insert into A_Iut_History ("+colName+",id_wh,dt_start,type_tran,allocate_by) values("+value+","+chk[i]+",'"+bulkinstallAssetDate+"','Intra',"+id_emp_user+") ";
		    				System.out.println(query);
		    				ps=con.prepareStatement(query);
		    				j=ps.executeUpdate();
		    				
		    			}
		    				query ="select nm_s_assetdiv,id_wh_dyn,no_mfr as slNo,mm.nm_model from M_Subasset_Div sa,M_model mm,A_Ware_House wh where wh.id_sgrp=sa.id_s_assetdiv and wh.id_model=mm.id_model and wh.id_wh="+chk[i]+"";
		    				System.out.println(query);
		    				rs = dtcm.GetResultSet(query,  request);
			    			System.out.println("query="+query);
			    			if(rs.next())
			    			{
			    				if(itemName.equals(""))
			    				{
			    					itemName = rs.getString(1);
			    					id_wh_dyn = rs.getString(2);
			    					slNo =  rs.getString(3);
			    					nm_model =  rs.getString(4);
			    				}
			    				else
			    				{
			    					itemName += " , "+ rs.getString(1);
			    					id_wh_dyn += " , "+ rs.getString(2);
			    					slNo += " , "+ rs.getString(3);
			    					nm_model += " , "+ rs.getString(4);
			    				}
			    			}
			    			
			     			
		    				j=1;
		    				String sql1="select ah.*,nm_assetdiv,nm_s_assetdiv,nm_model from A_Ware_House ah,M_Asset_Div ad,M_Subasset_Div sd,M_Model m where ah.id_model=m.id_model and ah.id_grp=ad.id_assetdiv and sd.id_s_assetdiv=ah.id_sgrp and ah.id_wh="+chk[i]+"";
		    				System.out.println(sql1);	
		    				String replyMailId = "";
		    				
		    				String m_emp = "";
		    				String asset_id = "";
		    				ps=con.prepareStatement(sql1);
		    				rs4=ps.executeQuery();
		    					 j=1;
		    					 
		    					
		    					while(rs4.next()){
		    						
		    					
		    					table+="<tr style='border:1px solid black'><td style='border:1px solid black'><center>"+ ++serialNo +"</center></td>" +
		    							"<td style='border:1px solid black'><center>"+ rs4.getString("id_wh_dyn") +"</center></td>" +
		    									"<td style='border:1px solid black'><center>"+ rs4.getString("serial_no") +"</center></td>" +
		    									"<td style='border:1px solid black'><center>"+ rs4.getString("nm_model") +"</center></td>" +
		    									"<td style='border:1px solid black'><center>"+ rs4.getString("typ_asst") +"</center></td>" +
		    									"<td style='border:1px solid black'><center>"+ rs4.getString("nm_assetdiv") +"</center></td>" +
		    											"<td style='border:1px solid black'><center>"+ rs4.getString("nm_s_assetdiv") +"</center></td></tr>";
		    				}
	            	}
		    					System.out.println(table);	
		    					table+="</table>";
		    			
	            	//.........for mail ........
	            	String replyMailId="",name="",assetHolderId="",mailSql="";
	            	// mailSql ="select id_emp from A_Ware_House wh , M_Emp_User emp ,M_User_Login ul where "+
					//	" wh.id_dept=emp.id_dept and type_user='DEPT' and emp.id_emp_user=ul.id_emp_user and id_wh="+id+" and status_user=1";
	             	
	            	 mailSql ="select emp.id_emp,emp.nm_emp,sa.nm_s_assetdiv,mm.nm_model,serial_no,id_wh_dyn from M_Subasset_Div sa,A_Ware_House wh,M_model mm, M_Emp_User emp"+ 
	            			 " where wh.id_sgrp=sa.id_s_assetdiv and wh.id_model=mm.id_model and wh.id_model=mm.id_model and wh.to_assign=emp.id_emp_user and  wh.id_wh="+id+"";
	            	 System.out.println("mailSql  "+ mailSql);
	             	 dtcm = new DtoCommon();
	     			rs = dtcm.GetResultSet(mailSql, request);
	     			if(rs.next())
	    			{
	     				replyMailId = rs.getString(1);
	    				name = rs.getString(2);
	    				itemName = rs.getString(3);
	    				nm_model = rs.getString(4);
	    				slNo = rs.getString(5);
	    				id_wh_dyn = rs.getString(6);
	    			}
	     			//recipients.add("infradesk1.l1support@landmarkgroup.in");
	    			 mailSql = "select id_emp from M_Emp_User emp ,M_User_Login ul "
	    			 		+ "where emp.id_emp_user=ul.id_emp_user and emp.id_emp_user="+id_emp_user+" and  status_user=1";	
	    			 
	    			 System.out.println("mailSql is "+ mailSql);
	    			  recipients = dtcm.ReturnListData(mailSql, request);
	    			  
	    			 rs = dtcm.GetResultSet(mailSql, request);
	    			 while(rs.next())
		    			{
		    				recipients.add(rs.getString("id_emp"));
		    			}
	    			 String support_mail="";
	    			 mailSql ="select support_mail from M_Mail_Config";
	    			ps=con.prepareStatement(mailSql);
	    			rs=ps.executeQuery();
	    			if(rs.next())
	    			{
	    				support_mail = rs.getString(1);
	    			}
	    			String nm_com="";
	    			 mailSql ="select nm_com from M_Company";
	    			ps=con.prepareStatement(mailSql);
	    			rs=ps.executeQuery();
	    			if(rs.next())
	    			{
	    				nm_com = rs.getString(1);
	    			}
	    			
	    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	    			String mailSubject = dtcm.ReturnParticularMessage("bulkInstall");
	    			String link = dtcm.ReturnParticularMessage("linkuser");
	    			
	    			String mailBody = "Hello <b>"+name+"</b>,<br><br><br>"+
							"Below assets have been assigned to you. Please collect the same from <a href='#'>IT Help Desk.</a><br>"+
							"<b>"+table+"</b><br>"+
						  "<p>'"+footerMsg+"' <b>"+support_mail+"</b> </p> <br><br><br><br>"+
						  "Warm Regards <br>"+
						  "<b>"+nm_com+"</b>";
	    			
	    			
	    			System.out.println("mailBody is "+ mailBody);
	    			System.out.println("replyMailId is "+ replyMailId);
	    			System.out.println("recipients is "+ recipients);
	    			System.out.println("mailSubject is "+ mailSubject);
	    			
	    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	    				
	            	}
            		catch(Exception e)
    				{
    					System.out.println("Error "+e);
    				}
            	
            		jobj.put("data",2);
            	
	            	
	            	
	                break;
	                
	            case "Update":
	            	
	            		
	            		try
	    				{
	            			int id_iut = 0;
	            			DtoCommon dtcm = new DtoCommon();
		            	for(int i=0; i<chk1.length;i++)
		            	{
		            		String status="";
		            		String asset_status =request.getParameter("asset_status"+chk1[i]);
		            		status=request.getParameter("asst_stat"+chk1[i]);
		            		
		            		uninstallAssetDate = request.getParameter("uninstallAssetDate"+chk1[i]);
		            		uninstallAssetDate = dateFormatNeeded.format(userDateFormat.parse(uninstallAssetDate));
		            		uninstallRmk =request.getParameter("uninstallRmk"+chk1[i]);
		            		//String asset_status =request.getParameter("asset_status"+chk1[i]);
		            		id = chk1[i];
		            		if(status.equals("0"))
			    			{
			    				String sql="select to_assign,id_loc,id_subl,id_building,id_flr,id_cc,id_wh,id_div from A_Ware_House where id_wh="+chk1[i]+"";
			    				System.out.println(sql);
			    				rs = dtcm.GetResultSet(sql,  request);
				    			if(rs.next())
				    			{
				    				to_assign = rs.getString(1);
				    				id_loc = rs.getString(2);
				    				id_subl = rs.getString(3);
				    				id_building = rs.getString(4);
				    				id_flr = rs.getString(5);
				    				id_cc = rs.getString(6);
				    				id_div = rs.getString(7);
				    			}
			    				
				    				 String query="insert into A_Iut_History(to_assign,id_loc,id_subl,id_flr,id_building,id_cc,id_wh,type_tran,de_allocate_by,st_ins,upload_asset,id_div) values("+to_assign+","+id_loc+","+id_subl+","+id_building+","+id_flr+","+id_cc+","+chk1[i]+",'Intra',"+id_emp_user+",'"+asset_status+"','upload_asset',"+id_div+")";
				    				 System.out.println(query);
				    				 ps=con.prepareStatement(query);
						    			j=ps.executeUpdate();
						    			if(j>0)
						    			{
						    				j=1;
						    			}
						    			if(asset_status.equals("physical_dmg_mjr") || asset_status.equals("physical_dmg_mnr")) {
						    			 
						    				//.........for mail ........
							            	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",nm_model="";
							            	String mailSql ="select emp.id_emp,emp.nm_emp,sa.nm_s_assetdiv,mm.nm_model,no_mfr,id_wh_dyn from M_Subasset_Div sa,A_Ware_House wh,M_model mm, M_Emp_User emp"+ 
							            			 " where wh.id_sgrp=sa.id_s_assetdiv and wh.id_model=mm.id_model and wh.id_model=mm.id_model and wh.to_assign=emp.id_emp_user and  wh.id_wh="+id+"";
							            	   
							            	
					            	 dtcm = new DtoCommon();
					    			rs = dtcm.GetResultSet(mailSql,  request);
					    			if(rs.next())
					    			{
					    				replyMailId = rs.getString(1);
					    				name = rs.getString(2);
					    				itemName = rs.getString(3);
					    				nm_model = rs.getString(4);
					    				slNo = rs.getString(5);
					    				id_wh_dyn = rs.getString(6);
					    			}
					    			List<String> recipients = new ArrayList<String>();
					    			mailSql="select id_emp from M_Emp_User emp ,M_User_Login ul "+
					    					"	where emp.id_emp_user=ul.id_emp_user and emp.id_emp_user="+id_emp_user+" and  status_user=1";
					             	 dtcm = new DtoCommon();
					     			rs = dtcm.GetResultSet(mailSql,  request);
					     			while(rs.next())
					    			{
					    				recipients.add(rs.getString("id_emp"));
					    			}
							    			
							    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
							    			String mailSubject = dtcm.ReturnParticularMessage("unInstall");
							    			String support_mail="";
							    			 mailSql ="select support_mail from M_Mail_Config";
							    			ps=con.prepareStatement(mailSql);
							    			rs=ps.executeQuery();
							    			if(rs.next())
							    			{
							    				support_mail = rs.getString(1);
							    			}
							    			
							    			String nm_com="";
							    			 mailSql ="select nm_com from M_Company";
							    			ps=con.prepareStatement(mailSql);
							    			rs=ps.executeQuery();
							    			if(rs.next())
							    			{
							    				nm_com = rs.getString(1);
							    			}
							    			String mailBody = "<b>Hello "+name+" , </b><br><br><br>"+
							    						  "The asset has been assign to you, is now damaged Condition."+
							    						  "<br><br>The <b>'"+itemName+"'</b> having Model No/Version <b>'"+nm_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b>.<br>"+
							    						  "<p>'"+footerMsg+"' <b>"+support_mail+"</b> </p> <br><br><br><br>"+
							    						  "Warm Regards <br>"+
							    						  "<b>"+nm_com+"</b>";
							    		
							    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
						            		
						    				j=1;
						    				
						    				query =" update A_Ware_House  set allocate= '"+status+"',device_status='"+asset_status+"',to_assign = 0  ,id_cc =0, dt_de_allocate = '"+uninstallAssetDate+"',rmk_asst='"+uninstallRmk+"' where id_wh = "+chk1[i]+"";
							    			PreparedStatement ps=con.prepareStatement(query);
							    			j=ps.executeUpdate();
						    			}
						    			else {
						    				
							    			//.........for mail ........
						            	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",nm_model="";
							            	String mailSql ="select emp.id_emp,emp.nm_emp,sa.nm_s_assetdiv,mm.nm_model,no_mfr,id_wh_dyn,serial_no from M_Subasset_Div sa,M_model mm,A_Ware_House wh, M_Emp_User emp "+
					            					" where wh.id_sgrp=sa.id_s_assetdiv and wh.id_model=mm.id_model and  wh.to_assign=emp.id_emp_user and wh.id_wh="+id+"";
							            	System.out.println(mailSql);
					            	 dtcm = new DtoCommon();
					    			rs = dtcm.GetResultSet(mailSql,  request);
					    			if(rs.next())
					    			{
					    				replyMailId = rs.getString(1);
					    				name = rs.getString(2);
					    				itemName = rs.getString(3);
					    				nm_model = rs.getString(4);
					    				slNo = rs.getString("serial_no");
					    				id_wh_dyn = rs.getString(6);
					    			}
					    			List<String> recipients = new ArrayList<String>();
					    			mailSql ="select id_emp,id_dept,emp.id_emp_user from M_Emp_User emp,M_User_Login ul where id_dept="+id_dept+" and ul.id_emp_user=emp.id_emp_user and type_user='DEPT' and status_user=1";
					    			System.out.println(mailSql);
					             	 dtcm = new DtoCommon();
					     			rs = dtcm.GetResultSet(mailSql,  request);
					     			while(rs.next())
					    			{
					    				recipients.add(rs.getString("id_emp"));
					    			}
							    			
							    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
							    			String mailSubject = dtcm.ReturnParticularMessage("unInstall");
							    			String support_mail="";
							    			 mailSql ="select support_mail from M_Mail_Config";
							    			ps=con.prepareStatement(mailSql);
							    			rs=ps.executeQuery();
							    			if(rs.next())
							    			{
							    				support_mail = rs.getString(1);
							    			}
							    			
							    			String nm_com="";
							    			 mailSql ="select nm_com from M_Company";
							    			ps=con.prepareStatement(mailSql);
							    			rs=ps.executeQuery();
							    			if(rs.next())
							    			{
							    				nm_com = rs.getString(1);
							    			}
							    			String mailBody = "<b>Hello "+name+" , </b><br><br><br>"+
							    						  "Below asset has been taken back from you."+
							    						  "<br><br>The <b>'"+itemName+"'</b> having Model No/Version <b>'"+nm_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b>.<br>"+
							    						  "<p>'"+footerMsg+"' <b>"+support_mail+"</b> </p> <br><br><br><br>"+
							    						  "Warm Regards <br>"+
							    						  "<b>"+nm_com+"</b>";
							    		
							    			
							    			System.out.println("mailBody is "+ mailBody);
							    			System.out.println("replyMailId is "+ replyMailId);
							    			System.out.println("recipients is "+ recipients);
							    			System.out.println("mailSubject is "+ mailSubject);
							    			
							    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
							    			j=1;
							    			
							    			query =" update A_Ware_House  set allocate= '"+status+"',device_status='in_store', to_assign = 0  ,id_cc =0, dt_de_allocate = '"+uninstallAssetDate+"',rmk_asst='"+uninstallRmk+"' where id_wh = "+chk1[i]+"";
							    			PreparedStatement ps=con.prepareStatement(query);
							    			j=ps.executeUpdate();
						    			}
						    			if(j>0)
							    			{
							    			 sql ="select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="+chk1[i]+"";
							    				ps=con.prepareStatement(sql);
							    				rs = ps.executeQuery();
							    				if(rs.next())
							    				{
							    					id_iut = rs.getInt(1);
							    				}
							    				j=0;
							    				query = "update A_Iut_History set dt_de_allocate ='"+uninstallAssetDate+"',de_allocate_by="+id_emp_user+" where id_iut = "+id_iut+"";
							    				 ps=con.prepareStatement(query);
							    				j=ps.executeUpdate();
							    				j=1;
							    				
							    			}
						    			
			    			}
		            		
		            		
		            	}
	    				}
		            		
	            		catch(Exception e)
	    				{
	    					System.out.println("Error "+e);
	    				}
	            	if(j > 0)
	            	{
	            		jobj.put("data",j);
	            	}
	            	
	            		
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
	
	public JSONObject CheckUnInstallDate(String dt_alloc , String id_wh)
	{
		JSONObject jobj = new JSONObject();
		
		String  sql ="select (replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dt_alloc from A_Ware_House where id_wh = "+id_wh+" and dt_alloc > '"+dt_alloc+"' ";
			
			 
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
	
	public JSONObject CheckDate(String dt_grn , String id_grn , String id)
	{
		JSONObject jobj = new JSONObject();
		
		try
		{
		int t=1;
    	String  sql ="";
    	String d = "1900-01-01",d2="1900-01-01";
    	String d1 = "1900-01-01",d3="1900-01-01";
    	String d4 = "1900-01-01",d5="1900-01-01";
    	String	typ_asst = "IT";
    		String SQL1 ="select typ_asst from A_Ware_House where id_wh="+id+" and allocate=5 and to_assign=0 and dt_alloc='1900-01-01' ";
    		ps=con.prepareStatement(SQL1);
    		rs=ps.executeQuery();
    		if(rs.next())
    		{
    			typ_asst = rs.getString(1);
    			if(typ_asst.equals("IT"))
        		{
        		  sql ="select dt_config,(replace(convert(NVARCHAR, dt_config, 103), ' ', '-')) as dt_config from A_Config where id= (select MAX(id) from A_Config where id_wh=8 ) and dt_config > '"+dt_grn+"' ";
        			  ps=con.prepareStatement(sql);
                		rs=ps.executeQuery();
                		    if(rs.next())
                		    {
                		    	t=0;
                		    	d = rs.getString(1);
                		    	d2 = rs.getString(2);
                	        }
        		}else
        		{
        			sql ="select dt_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dt_inv from A_Ware_House where id_wh="+id+" and dt_inv > '"+dt_grn+"' ";
      			  ps=con.prepareStatement(sql);
              		rs=ps.executeQuery();
              		    if(rs.next())
              		    {
              		    	t=0;
              		    	d = rs.getString(1);
              		    	d2 = rs.getString(2);
              	        }
        		}
    		}else
    		{
    			sql ="select dt_de_allocate,(replace(convert(NVARCHAR, dt_de_allocate, 103), ' ', '-')) as dt_de_allocate from A_Ware_House where id_wh="+id+" and dt_de_allocate > '"+dt_grn+"' ";
        		ps=con.prepareStatement(sql);
        		rs=ps.executeQuery();
        		    if(rs.next())
        		    {
        		    	t=0;
        		    	d1 = rs.getString(1);
        		    	d3 = rs.getString(2);
        	        }
    		}
    	if(t == 1)
    	{
    		jobj.put("data", 1);
    	}
    	else
    	{
    		if(sdf.parse(d).before(sdf.parse(d1)))
    		{
        			jobj.put("data", 2);
            		jobj.put("dt_grn",d3);
    		}
    		else
    		{
        			jobj.put("data", 3);
            		jobj.put("dt_grn",d2);
    		}
    		

    	}	
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install while checking installation date.");
		}
		 
		return jobj;
		
		
	}
	

	public JSONObject EditDataForInstall(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			sql="select id_wh_dyn,no_mfr,mfr,ds_pro,rmk_asst,ds_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as "+
					" dtAlloc,to_assign,id_loc,id_subl,id_building,id_flr,id_cc,id_grn,nm_dept from A_Ware_House wh,M_Dept d where wh.id_dept=d.id_dept and id_wh ='"+id+"'";
		
		
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
			String sql = "update A_Ware_House set parent = "+parent1+",rmk_asst = '"+remark+"',link_date='"+ linkedDate +"' where id_wh = " + total_count[i] +";";
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

	public JSONObject DropDownResult(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_wh,concat(serial_no,'--',ds_pro) as nm_prod from A_Ware_House where typ_asst='IT'";	
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
	
	public JSONObject InstallAsset(HashMap<String, String> map , String id , String dt_alloc,String Edit,int id_emp_user, String id_wh_dyn , int UserId,HttpServletRequest request)
	{
		String col_Val="",colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("A_Ware_House",  request);
				
						
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
					System.out.println("Some error in A_Install.");
				}
		try 
		{
		 String  query=" select id_wh from A_Ware_House where (allocate=5 and to_assign=0) and id_wh = "+id+"";
		 PreparedStatement ps=con.prepareStatement(query);
		 rs1 = ps.executeQuery();
		 int t=0;
		 if(!rs1.next())
		 {
			 t=1;
			 query="update A_Ware_House set "+col_Val+",allocate=1  where id_wh="+id+"";
		 }
		 else
		 {
			 
			 query="update A_Ware_House set "+col_Val+",allocate=1 ,dt_allocate = '"+dt_alloc+"',st_config ='No' where id_wh="+id+"";
		 }
		 
		
			ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				
				rs = Common.GetColumns("A_Iut_History",  request);
				col_Val ="";
				j=0;
				while (rs.next())
				{
				
					if(rs.getString(1) !="id_iut")
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
				if(t==1)
				{
					if(Edit.equals("Edit"))
					{
						
					
					String sql ="select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="+id+"";
					int id_iut=0;
    				ps=con.prepareStatement(sql);
    				rs = ps.executeQuery();
    				if(rs.next())
    				{
    					id_iut = rs.getInt(1);
    				}
    				
					query = "update A_Iut_History set "+col_Val+",id_wh ="+id+" ,dt_start = '"+dt_alloc+"',allocate_by="+id_emp_user+" where id_iut = "+id_iut+"";
					}
					else
					{
						query = "insert into A_Iut_History ("+colName+",id_wh,dt_start,type_tran,allocate_by) values("+value+","+id+",'"+dt_alloc+"','Intra',"+id_emp_user+") ";
						
					}
				}
				else
				{
					query = "insert into A_Iut_History ("+colName+",id_wh,dt_start,type_tran,allocate_by) values("+value+","+id+",'"+dt_alloc+"','Intra',"+id_emp_user+") ";
					
				}
				ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				if(j > 0)
				{
					//.........for mail ........
	            	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",nm_model="";
	            	String mailSql ="select emp.id_emp,emp.nm_emp,sa.nm_s_assetdiv,mm.nm_model,no_mfr from M_Subasset_Div sa,M_model mm,A_Ware_House wh, M_Emp_User emp "+
	            					" where wh.id_sgrp=sa.id_s_assetdiv and wh.id_model=mm.id_model and wh.to_assign=emp.id_emp_user and wh.id_wh="+id+"";
	            	
	            	DtoCommon dtcm = new DtoCommon();
	    			rs = dtcm.GetResultSet(mailSql,  request);
	    			if(rs.next())
	    			{
	    				replyMailId = rs.getString(1);
	    				name = rs.getString(2);
	    				itemName = rs.getString(3);
	    				nm_model = rs.getString(4);
	    				slNo = rs.getString(5);
	    				
	    			}
	    			List<String> recipients = new ArrayList<String>();
	    			//recipients.add(assetHolderId);
	    			
	    			mailSql ="select id_emp from A_Ware_House wh , M_Emp_User emp ,M_User_Login ul where "+
						" wh.id_dept=emp.id_dept and type_user='DEPT' and emp.id_emp_user=ul.id_emp_user and id_wh="+id+" and status_user=1";
	             	
	             	 dtcm = new DtoCommon();
	     			rs = dtcm.GetResultSet(mailSql,  request);
	     			while(rs.next())
	    			{
	    				recipients.add(rs.getString("id_emp"));
	    			}
	     			recipients.add("infradesk.l1support@landmarkgroup.in");
	    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	    			String mailSubject = dtcm.ReturnParticularMessage("singleInstall");
	    			String support_mail="";
	    			 mailSql ="select support_mail from M_Mail_Config";
	    			ps=con.prepareStatement(mailSql);
	    			rs=ps.executeQuery();
	    			if(rs.next())
	    			{
	    				support_mail = rs.getString(1);
	    			}
	    			
	    			String nm_com="";
	    			 mailSql ="select nm_com from M_Company";
	    			ps=con.prepareStatement(mailSql);
	    			rs=ps.executeQuery();
	    			if(rs.next())
	    			{
	    				nm_com = rs.getString(1);
	    			}
	    			String mailBody = "<b>Hello "+name+" , </b><br><br><br>"+
	    							"Below asset has been assigned to you. Please collect the same from <a href='#'>IT Help Desk.</a><br>"+
	    							"<br>The <b>'"+itemName+"'</b> having Model No/Version <b>'"+nm_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b><br>"+
	    							 "<p>'"+footerMsg+"' <b>"+support_mail+"</b> </p> <br><br><br><br>"+
		    						  "Warm Regards <br>"+
		    						  "<b>"+nm_com+"</b>";
	    		
	    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
					
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
	public JSONObject Repair(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		
		int j=0;
		
		 String sql1="update A_Ware_House set allocate=6 where id_wh ="+id+"";
		 try{
		 PreparedStatement ps=con.prepareStatement(sql1);
		 
			j=ps.executeUpdate();
			
			
			if(j>0)
			{
			json.put("data",1);
			}
		 }
		 catch(Exception e){}
		
		return json;
		
		
	}
	public JSONObject Sold(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		
		int j=0;
		
		 String sql1="update A_Ware_House set allocate=7 where id_wh ="+id+"";
		 try{
		 PreparedStatement ps=con.prepareStatement(sql1);
		 
			j=ps.executeUpdate();
			
			
			if(j>0)
			{
			json.put("data",1);
			}
		 }
		 catch(Exception e){}
		
		return json;
		
		
	}
	
	public JSONObject linked_prods(String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		
		String sql="select awh1.link_date,awh1.id_wh,awh1.rmk_asst,awh1.id_wh_dyn,awh1.ds_pro,awh1.serial_no,awh1.rmk_asst,awh2.id_wh as asset_id,awh2.id_wh_dyn as asset_cd,awh2.ds_pro as asset_nm from A_Ware_House awh1,A_Ware_House awh2 where awh1.parent != 0 and awh1.parent = awh2.id_wh and  ( awh1.id_wh_dyn like '"+searchWord+"%' or awh1.serial_no like '"+searchWord+"%' or awh1.ds_pro like '"+searchWord+"%')";
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
		 }
		catch(Exception e)
		{
			System.out.println("sql error in M_Emp_User.");
		}
		 
		return jobj;
		
	}
	
	public JSONObject link_down(String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		
		String sql="select id_wh,id_wh_dyn,no_model,ds_pro,serial_no,rmk_asst from A_Ware_House where (typ_asst='accessories' or typ_asst='soft') and parent = 0 and  ( id_wh_dyn like '"+searchWord+"%' or serial_no like '"+searchWord+"%' or ds_pro like '"+searchWord+"%');";
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
		  }
		catch(Exception e)
		{
			System.out.println("sql error in M_Emp_User.");
		}
		 
		return jobj;
		
	}
	
	public JSONObject Active(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		
		int j=0;
		
		 String sql1="update A_Ware_House set allocate=2 where id_wh ="+id+"";
		 try{
		 PreparedStatement ps=con.prepareStatement(sql1);
		 
			j=ps.executeUpdate();
			
			
			if(j>0)
			{
			json.put("data",1);
			}
		 }
		 catch(Exception e){}
		
		return json;
		
		
	}
	
	
	
}
