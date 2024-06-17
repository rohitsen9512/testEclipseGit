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


public class A_Conifg_For_IT extends HttpServlet {
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
			    
		String action = "",id_wh_dyn="",word="",DisplayType="",likeTempQuery="",id="",uninstallAssetDate="",bulkinstallAssetDate="",to_assign="",id_flr="",id_cc="";
		String id_grp="",id_sgrp="",id_loc="",id_subl="",typ_asst="",dt_alloc="",Edit="",dt_grn="",id_grn="",uninstallRmk="";
		int j=0;
		if(request.getParameter("id_wh_dyn") !=null)
		{
			id_wh_dyn = request.getParameter("id_wh_dyn");
		}
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
		if(request.getParameter("id_grn") !=null)
		{
			id_grn = request.getParameter("id_grn");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
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
			if(!id_loc.equals(""))
			{
				if(!id_subl.equals(""))
				{
					temp += " and id_loc = "+id_loc+" and id_subl = "+id_subl+"";
				}
				else
				{
					temp += " and id_loc = "+id_loc+"";
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
				String Query = Common.returnQuery(FlrId);
				temp = temp + " "+Query+" and wh.id_div ="+DivId+"";
			}
			
			
			String tempSql = "";
			con=Common.GetConnection(request);
			UserAccessData uad = new UserAccessData();
			switch (action)
	        {
		        case "Save":
	            	jobj = InstallAsset(map,id,dt_alloc,Edit,id_emp_user,  id_wh_dyn ,  UserId,request);	
	                break;
		                
	            /*case "Display":
	            	if(DisplayType.equals("UnInstall"))
	            		tempSql="select wh.*,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh where allocate =1 and id_wh not in(select id_wh from A_Iut_History where iut_approval=1 or iut_approval=2) and st_trvl=0";
	        		else
	        			tempSql="select wh.*,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh where ( allocate =0 or allocate =2 ) and asset_tran ='Intra' and st_trvl=0";
	            	
	            	
	            	jobj = uad.QueryMacker(tempSql , UserType , word);
	            	
	            	//jobj = DisplayAssetForInstallUnInstall(DisplayType,temp);	
	                break;*/
	                
	            case "Edit":
	            	jobj = EditDataForInstall(id);	
	                break;
	             
	            case "Display":
	        		//	tempSql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,ds_asst,serial_no,id_subl,rmk_asst,remarks,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc,nm_flr from A_Ware_House wh,M_Floor mf,M_Model modl  where (allocate=0 or allocate=2) and wh.id_flr=mf.id_flr and typ_asst IN('IT','soft','nonit') and modl.id_model=wh.id_model and st_trvl=0 and st_config='Yes' "+temp+"";
	        			
	        			tempSql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,rmk_asst,remarks,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh,M_Model modl  where (allocate=0 or allocate=2)  and modl.typ_asst IN('IT','soft','nonit') and modl.id_model=wh.id_model and st_trvl=0 and st_config='Yes' "+temp+" ";
	        			System.out.println(tempSql);
	        			jobj = uad.QueryMacker(tempSql , UserType , word , id_dept,  request);
	                break;
	                
	            case "CheckDate":
	            	jobj = CheckDate(dt_grn,id_grn,id);	
	                break;
	            case "Notify":
	            	jobj = Notify(id , id_dept , id_emp_user,  request);	
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
	            		id_grn = "0";
	            		String SQL1 ="select id_grn from A_Ware_House where id_wh="+chk[i]+" and allocate=0";
	            		ps=con.prepareStatement(SQL1);
	            		rs=ps.executeQuery();
	            		if(rs.next())
	            		{
	            			id_grn = rs.getString(1);
	            		}
	            		
	            		if(id_grn.equals("0"))
	            		{
	            		  sql ="select dt_de_allocate,(replace(convert(NVARCHAR, dt_de_allocate, 103), ' ', '-')) as dt_de_allocate from A_Ware_House where allocate=2 and dt_de_allocate > '"+bulkinstallAssetDate+"' and id_wh="+chk[i]+" ";
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
	            			 sql ="select dt_grn,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dt_grn from G_Grn where  id_grn = "+id_grn+" and dt_grn > '"+bulkinstallAssetDate+"' ";
		            		ps=con.prepareStatement(sql);
	                		rs=ps.executeQuery();
	                		    if(rs.next())
	                		    {
	                		    	t=0;
	                		    	d1 = rs.getString(1);
	                		    	d3 = rs.getString(2);
	                	        }
	            		} 
	            		
	            		String sql11="select max(id_trvl) from T_Travel where id_wh="+chk[i]+" and st_recv=1";
	            		ps=con.prepareStatement(sql11);
	            		rs=ps.executeQuery();
	            		if(rs.next())
	            		{
		            		String SQL11 ="select dt_retrn_trvl,(replace(convert(NVARCHAR, dt_retrn_trvl, 103), ' ', '-')) as dt_retrn_trvl1 from T_Travel where id_trvl="+rs.getString(1)+" and st_recv=1 and dt_retrn_trvl > '"+bulkinstallAssetDate+"'";
		            		ps=con.prepareStatement(SQL11);
		            		rs=ps.executeQuery();
		            		if(rs.next())
		            		{
		            			d4 = rs.getString(1);
		            			d5 = rs.getString(2);
		            			t=0;
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
                			if(sdf.parse(d1).before(sdf.parse(d4)))
                    		{
                    			jobj.put("data", 0);
                        		jobj.put("dt_grn",d5);
                    		}
                			else
                			{
	                			jobj.put("data", 0);
	                    		jobj.put("dt_grn",d3);
                			}
                		}
                		else
                		{
                			if(sdf.parse(d).before(sdf.parse(d4)))
                    		{
                    			jobj.put("data", 0);
                        		jobj.put("dt_grn",d5);
                    		}
                			else
                			{
	                			jobj.put("data", 0);
	                    		jobj.put("dt_grn",d2);
                			}
                		}
                		

                	}	
	            		}
	            		catch(Exception e)
	            		{
	            			System.out.println("sql error in A_Install.");
	            		}
	            		
	                break;
	            case "CheckUnInstallDate":
	            	jobj = CheckUnInstallDate(dt_alloc,id);	
	                break;
	                
	            case "BulkInstall":
            		try
    				{
            			String colName="",value="",query="",itemName="",slNo="",no_model="";
            			DtoCommon dtcm = new DtoCommon();	
            			rs = Common.GetColumns("A_Iut_History",request);
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
	    					
	            	for(int i=0; i<chk.length;i++)
	            	{
	            		
	            		j=0;
	            		uninstallRmk =request.getParameter("installRmk"+chk[i]);
	            		String sql="select dt_allocate from A_Ware_House where allocate=0 and id_wh="+chk[i]+"";
	            		PreparedStatement ps=con.prepareStatement(sql);
		    			rs=ps.executeQuery();
		    			if(rs.next())
		    			{
		    				 query =" update A_Ware_House  set allocate= 1, dt_alloc = '"+bulkinstallAssetDate+"',dt_allocate = '"+bulkinstallAssetDate+"' "+
		    	            		" , id_loc = "+id_loc+",id_subl = "+id_subl+", to_assign = "+to_assign+" , id_flr = "+id_flr+",id_cc = "+id_cc+" ,rmk_asst='"+uninstallRmk+"' where id_wh = "+chk[i]+"";
		    		    			
		    			}
		    			else
		    			{
		    				 query =" update A_Ware_House  set allocate= 1, dt_alloc = '"+bulkinstallAssetDate+"' "+
		    	            		" , id_loc = "+id_loc+",id_subl = "+id_subl+", to_assign = "+to_assign+" , id_flr = "+id_flr+",id_cc = "+id_cc+" ,rmk_asst='"+uninstallRmk+"' where id_wh = "+chk[i]+"";
		    		    			
		    			}
		    			ps=con.prepareStatement(query);
		    			j=ps.executeUpdate();
		    			if(j>0)
		    			{
		    				j=0;
		    				query = "insert into A_Iut_History ("+colName+",id_wh,dt_start,type_tran,allocate_by) values("+value+","+chk[i]+",'"+bulkinstallAssetDate+"','Intra',"+id_emp_user+") ";
		    				 ps=con.prepareStatement(query);
		    				j=ps.executeUpdate();
		    				
		    			}
		    				query ="select nm_s_assetdiv,id_wh_dyn,no_mfr as slNo,no_model from M_Subasset_Div sa,A_Ware_House wh where wh.id_sgrp=sa.id_s_assetdiv and wh.id_wh="+chk[i]+"";
			    			rs = dtcm.GetResultSet(query,  request);
			    			if(rs.next())
			    			{
			    				if(itemName.equals(""))
			    				{
			    					itemName = rs.getString(1);
			    					id_wh_dyn = rs.getString(2);
			    					slNo =  rs.getString(3);
			    					no_model =  rs.getString(4);
			    				}
			    				else
			    				{
			    					itemName += " , "+ rs.getString(1);
			    					id_wh_dyn += " , "+ rs.getString(2);
			    					slNo += " , "+ rs.getString(3);
			    					no_model += " , "+ rs.getString(4);
			    				}
			    			}
		    				j=1;
		    			}
	            	//.........for mail ........
	            	String replyMailId="",name="",assetHolderId="";
	    			String mailSql = "select distinct(to_emp.id_emp),to_emp.nm_emp,cc_emp.id_emp from  A_Ware_House wh,M_Emp_User to_emp,M_Emp_User cc_emp,M_User_Login l where "+
	            	" to_emp.id_emp_user="+to_assign+" and cc_emp.id_emp_user=l.id_emp_user and l.id_log_user="+UserId+" and wh.to_assign=to_emp.id_emp_user";	
	    			
	    			rs = dtcm.GetResultSet(mailSql,  request);
	    			if(rs.next())
	    			{
	    				replyMailId = rs.getString(1);
	    				name = rs.getString(2);
	    				assetHolderId = rs.getString(3);
	    			}
	    			List<String> recipients = new ArrayList<String>();
	    			recipients.add(assetHolderId);
	    			
	    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	    			String mailSubject = dtcm.ReturnParticularMessage("bulkInstall");
	    			
	    			String mailBody = "Hello <b>"+name+"</b>,<br><br><br>"+
	    							"Below assets have been assigned to you. Please collect the same from <a href='#'>IT Help Desk.</a><br>"+
	    						  "<br><p>The <b>'"+itemName+"'</b> having Model No/Version <b>'"+no_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b>  is assign to you.<br></p>"+
	    						  "<p>"+footerMsg+"</p>";
	    		
	    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	    				
	            	}
            		catch(Exception e)
    				{
    					System.out.println("Error "+e);
    				}
            	if(j > 0)
            	{
            		jobj.put("data",j+1);
            	}
	            	
	            	
	                break;
	                
	            case "Update":
	            	
	            		
	            		try
	    				{
	            			int id_iut = 0;
	            			DtoCommon dtcm = new DtoCommon();
		            	for(int i=0; i<chk1.length;i++)
		            	{
		            		uninstallAssetDate = request.getParameter("uninstallAssetDate"+chk1[i]);
		            		uninstallAssetDate = dateFormatNeeded.format(userDateFormat.parse(uninstallAssetDate));
		            		uninstallRmk =request.getParameter("uninstallRmk"+chk1[i]);
		            		
		            		//.........for mail ........
			            	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="";
			            	 String mailSql ="select to_emp.id_emp,to_emp.nm_emp,cc_emp.id_emp,sa.nm_s_assetdiv,no_model,no_mfr,wh.id_wh_dyn from M_Subasset_Div sa, A_Ware_House wh, M_Emp_User to_emp,M_Emp_User cc_emp,M_User_Login l where"+ 
			            			" l.id_log_user="+UserId+" and l.id_emp_user=cc_emp.id_emp_user and wh.to_assign=to_emp.id_emp_user and wh.id_wh='"+chk1[i]+"'"+
			            			" and sa.id_s_assetdiv=wh.id_sgrp";
			            	
			    			rs = dtcm.GetResultSet(mailSql,  request);
			    			if(rs.next())
			    			{
			    				replyMailId = rs.getString(1);
			    				name = rs.getString(2);
			    				assetHolderId = rs.getString(3);
			    				itemName = rs.getString(4);
			    				no_model = rs.getString(5);
			    				slNo = rs.getString(6);
			    				id_wh_dyn = rs.getString(7);
			    				
			    			}
			    			List<String> recipients = new ArrayList<String>();
			    			recipients.add(assetHolderId);
			    			
			    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
			    			String mailSubject = dtcm.ReturnParticularMessage("unInstall");
			    			
			    			String mailBody = "<b>Hello "+name+" , </b><br><br><br>"+
			    						  "Below asset has been taken back from you."+
			    						  "<br><br>The <b>'"+itemName+"'</b> having Model No/Version <b>'"+no_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b>.<br>"+
			    						  "<p>"+footerMsg+"</p>";
			    		
			    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
		            		
		            		
			    			String query =" update A_Ware_House  set allocate= 2,to_assign = 0 , id_flr = 0 ,id_cc =0, dt_de_allocate = '"+uninstallAssetDate+"',rmk_asst='"+uninstallRmk+"' where id_wh = "+chk1[i]+"";
			    			PreparedStatement ps=con.prepareStatement(query);
			    			j=ps.executeUpdate();
			    			if(j>0)
			    			{
			    				String sql ="select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="+chk1[i]+"";
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
			System.out.println("sql error in G_Create_Grn.");
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
    	String	id_grn1 = "0";
    		String SQL1 ="select id_grn from A_Ware_House where id_wh="+id+" and allocate=0";
    		ps=con.prepareStatement(SQL1);
    		rs=ps.executeQuery();
    		if(rs.next())
    		{
    			id_grn1 = rs.getString(1);
    		}
    		
    		if(id_grn1.equals("0"))
    		{
    		  sql ="select dt_de_allocate,(replace(convert(NVARCHAR, dt_de_allocate, 103), ' ', '-')) as dt_de_allocate from A_Ware_House where allocate=2 and dt_de_allocate > '"+dt_grn+"' and id_wh="+id+" ";
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
    			sql ="select dt_grn,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dt_grn from G_Grn where id_grn = "+id_grn+" and dt_grn > '"+dt_grn+"' ";
    			
        		ps=con.prepareStatement(sql);
        		rs=ps.executeQuery();
        		    if(rs.next())
        		    {
        		    	t=0;
        		    	d1 = rs.getString(1);
        		    	d3 = rs.getString(2);
        	        }
    		}
    		
    		String sql11="select max(id_trvl) from T_Travel where id_wh="+id+" and st_recv=1";
    		ps=con.prepareStatement(sql11);
    		rs=ps.executeQuery();
    		if(rs.next())
    		{
        		String SQL11 ="select dt_retrn_trvl,(replace(convert(NVARCHAR, dt_retrn_trvl, 103), ' ', '-')) as dt_retrn_trvl1 from T_Travel where id_trvl="+rs.getString(1)+" and st_recv=1 and dt_retrn_trvl > '"+dt_grn+"'";
        		ps=con.prepareStatement(SQL11);
        		rs=ps.executeQuery();
        		if(rs.next())
        		{
        			d4 = rs.getString(1);
        			d5 = rs.getString(2);
        			t=0;
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
    			if(sdf.parse(d1).before(sdf.parse(d4)))
        		{
        			jobj.put("data", 0);
            		jobj.put("dt_grn",d5);
        		}
    			else
    			{
        			jobj.put("data", 2);
            		jobj.put("dt_grn",d3);
    			}
    		}
    		else
    		{
    			if(sdf.parse(d).before(sdf.parse(d4)))
        		{
        			jobj.put("data", 0);
            		jobj.put("dt_grn",d5);
        		}
    			else
    			{
        			jobj.put("data", 3);
            		jobj.put("dt_grn",d2);
    			}
    		}
    		

    	}	
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in G_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	

	public JSONObject EditDataForInstall(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			sql="select id_wh_dyn,no_mfr,mfr,ds_pro,rmk_asst,ds_asst,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc,to_assign,id_loc,id_subl,id_flr,id_cc,id_grn from A_Ware_House where id_wh ='"+id+"'";
		
		
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


	public JSONObject InstallAsset(HashMap<String, String> map , String id , String dt_alloc,String Edit,int id_emp_user, String id_wh_dyn , int UserId,HttpServletRequest request)
	{
		String col_Val="",colName="",value="";
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
					System.out.println("Some error in A_Install.");
				}
		try 
		{
		 String  query=" select id_wh from A_Ware_House where (allocate=0) and id_wh = "+id+"";
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
			 
			 query="update A_Ware_House set "+col_Val+",allocate=1 ,dt_allocate = '"+dt_alloc+"' where id_wh="+id+"";
		 }
		 
		
			ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				
				rs = Common.GetColumns("A_Iut_History",request);
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
				//query = "insert into A_Iut_History ("+colName+",id_wh,dt_start,type_tran) values("+value+","+id+",'"+dt_alloc+"','Intra') ";
			
				ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				if(j > 0)
				{
					//.........for mail ........
	            	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="";
	            	String mailSql ="select to_emp.id_emp,to_emp.nm_emp,cc_emp.id_emp,sa.nm_s_assetdiv,no_model,no_mfr from M_Subasset_Div sa, A_Ware_House wh, M_Emp_User to_emp,M_Emp_User cc_emp,M_User_Login l where"+ 
	            			"  l.id_log_user="+UserId+" and l.id_emp_user=cc_emp.id_emp_user and wh.to_assign=to_emp.id_emp_user and wh.id_wh_dyn='"+id_wh_dyn+"'"+
	            			" and sa.id_s_assetdiv=wh.id_sgrp";
	            	
	            	DtoCommon dtcm = new DtoCommon();
	    			rs = dtcm.GetResultSet(mailSql,  request);
	    			if(rs.next())
	    			{
	    				replyMailId = rs.getString(1);
	    				name = rs.getString(2);
	    				assetHolderId = rs.getString(3);
	    				itemName = rs.getString(4);
	    				no_model = rs.getString(5);
	    				slNo = rs.getString(6);
	    				
	    			}
	    			List<String> recipients = new ArrayList<String>();
	    			recipients.add(assetHolderId);
	    			
	    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	    			String mailSubject = dtcm.ReturnParticularMessage("singleInstall");
	    			
	    			String mailBody = "<b>Hello "+name+" , </b><br><br><br>"+
	    							"Below asset has been assigned to you. Please collect the same from <a href='#'>IT Help Desk.</a><br>"+
	    							"<br>The <b>'"+itemName+"'</b> having Model No/Version <b>'"+no_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b><br>"+
	    						  "<p>"+footerMsg+"</p>";
	    		
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
	public JSONObject Notify(String id , String id_dept , int id_emp_user,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		
		int j=0;
		
		 String sql1="update A_Ware_House set allocate=4 where id_wh ="+id+"";
		 try{
		 PreparedStatement ps=con.prepareStatement(sql1);
		 
			j=ps.executeUpdate();
			
			if(j>0)
			{
				//.........for mail ........
            	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",nm_model="",id_wh_dyn="";
            	String mailSql ="select emp.nm_emp,emp.id_emp,id_wh_dyn,ds_pro,nm_model,no_mfr from A_Ware_House wh,M_Emp_User emp,M_User_Login ul,M_model mdl "+
            			" where mdl.id_model=wh.id_model and id_wh="+id+" and ul.type_user=wh.typ_asst and ul.id_emp_user=emp.id_emp_user";
            	
            	DtoCommon dtcm = new DtoCommon();
    			rs = dtcm.GetResultSet(mailSql,  request);
    			if(rs.next())
    			{
    				
    				name = rs.getString("nm_emp");
    				itemName = rs.getString("ds_pro");
    				nm_model = rs.getString("nm_model");
    				System.out.println("model"+nm_model);
    				slNo = rs.getString("no_mfr");
    				id_wh_dyn = rs.getString("id_wh_dyn");
    				System.out.println("model"+nm_model);
    			}
    			List<String> recipients = new ArrayList<String>();
    			/*while(rs.next())
    			{
    				recipients.add(rs.getString("id_emp"));
    			}*/
    			replyMailId = "infradesk1.l1support@landmarkgroup.in";
    			 mailSql ="select id_emp,id_dept,emp.id_emp_user from M_Emp_User emp,M_User_Login ul where id_dept="+id_dept+" and ul.id_emp_user=emp.id_emp_user and type_user='DEPT' and status_user=1";
             	System.out.println("mail"+mailSql);
             	 dtcm = new DtoCommon();
     			rs = dtcm.GetResultSet(mailSql,  request);
     			while(rs.next())
    			{
    				recipients.add(rs.getString("id_emp"));
    			}
    			
    			//recipients.remove(replyMailId);
    			
    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
    			String mailSubject = dtcm.ReturnParticularMessage("notifyAsset");
    			
    			String mailBody = "<b>Hello IT Team , </b><br><br><br>"+
    							"Below asset has been notified to you. Please collect the same from <a href='#'>IT Help Desk.</a><br>"+
    							"<br>The <b>'"+itemName+"'</b> having Model No/Version <b>'"+nm_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b><br>"+
    						  "<p>"+footerMsg+"</p>";
    		System.out.println("body"+mailBody);
    			//Common.MailConfigurationForNotify(mailBody, replyMailId, recipients , mailSubject , id_emp_user);
				
				j=1;
				
			
			}
			json.put("data",j);
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		
		return json;
		
		
	}
	
	
}
