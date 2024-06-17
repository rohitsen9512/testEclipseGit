package com.Transfer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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


public class T_Travel_Accept_Reject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	Statement stmt1=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		 HttpSession session = request.getSession();  
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0",status_approv="",id_wh="",word="",aprvl_remarks="";
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("aprvl_remarks") !=null)
		{
			aprvl_remarks = request.getParameter("aprvl_remarks");
		}
		if(request.getParameter("id_wh") !=null)
		{
			id_wh = request.getParameter("id_wh");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("status_approv") !=null)
		{
			status_approv = request.getParameter("status_approv");
			
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}	
		try
		{
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType");
			int UserId = (int)session.getAttribute("UserId");
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "Display":
	            	String tempSql="";
	            	String likeTempQuery="";
	            	DtoCommon dtoCommon = new DtoCommon();
	            	if(!word.equals(""))
        			{
        				 likeTempQuery = dtoCommon.MakeLikeTempQuery(word);
        			}
	        		if(!UserType.equals("Super")){
	        			String tempQuery = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
	        			tempSql= "select id_trvl,wh.id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst from A_Ware_House wh,T_Travel trvl where trvl.id_wh=wh.id_wh and trvl.st_aprvl =0 and "+tempQuery+" "+likeTempQuery+"" ;
	        		}
	        		else {
	        			tempSql= "select id_trvl,wh.id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst from A_Ware_House wh,T_Travel trvl where trvl.id_wh=wh.id_wh and trvl.st_aprvl =0 "+likeTempQuery+"" ;
	        		
	        		}
	        		tempSql= "select id_trvl,wh.id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst from A_Ware_House wh,T_Travel trvl where trvl.id_wh=wh.id_wh and trvl.st_aprvl =0 "+likeTempQuery+"" ;
	        					
	        		
	        		System.out.println(tempSql);
	        		jobj = dtoCommon.GetDataForDisplay(tempSql,  request);
	            	
	                break;
	                
	            case "Update":
	            	jobj = UpdateTravelForAcceptReject(id,status_approv,id_emp_user,id_wh,aprvl_remarks,  request);	
	                break;
	                
	            case "Edit":
	            	jobj = TravelDtaForEdit(id);	
	                break;
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in T_Accept_Reject.");
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
	
	
	public JSONObject TravelDtaForEdit(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{

			String sql= "select wh.id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst,nm_emp,cd_emp,dt_st_trvl,dt_expc_trvl,trvl.remarks "+
					 "from A_Ware_House wh,T_Travel trvl,M_Emp_User emp "+
					" where trvl.id_wh=wh.id_wh and id_trvl="+id+" and wh.to_assign=emp.id_emp_user";
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			   ResultSetMetaData metaData = rs.getMetaData();
			    int columns = metaData.getColumnCount();
			    JSONObject jobj2 = new JSONObject();
			    while(rs.next())
			    {
			    	jobj2 = new JSONObject();
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
			System.out.println("sql error in T_Accept_Reject.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject UpdateTravelForAcceptReject(String id_trvl , String status_approv,int id_emp_user,
			String id,String aprvl_remarks,HttpServletRequest request)
	{
		
		int j=0,id_iut=0;
		JSONObject json=new JSONObject();
		
		
		try 
		{
			
			
			stmt = con.createStatement();
			stmt1 = con.createStatement();
			String query="";
			if (status_approv.equals("1"))
			{
				query="update T_Travel set st_aprvl="+status_approv+",aprlv_by="+id_emp_user+",aprvl_remarks='"+aprvl_remarks+"' where id_trvl="+id_trvl+"";
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
			}
			else
			{
				
				stmt.executeQuery("update T_Travel set st_aprvl="+status_approv+",reject_by="+id_emp_user+",aprvl_remarks='"+aprvl_remarks+"' where id_trvl="+id_trvl+"");
				stmt1.executeUpdate("update A_Ware_House set st_recv=0 where id_wh="+id+"");   
				
				 j=0;  
					//.........for mail ........
			    	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="";
			    	
			    	String Sql ="select id_emp from A_Ware_House wh,M_User_Login l,M_Emp_User emp "+
			    				" where wh.id_wh="+id+" and l.typ_asst like '%'+wh.typ_asst+'%' and emp.id_emp_user=l.id_emp_user "+
			    				" and (l.type_user='EXCTV' or l.type_user='MNGR')";
			    	
			    	DtoCommon dtcm = new DtoCommon();
					List<String> recipients = dtcm.ReturnListData(Sql,  request);
					
				if(!recipients.isEmpty())
				{
					//replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
					
					Sql ="select id_emp from A_Ware_House wh,M_User_Login l,M_Emp_User emp "+
							" where wh.id_wh="+id+" and l.typ_asst like '%'+wh.typ_asst+'%' and emp.id_emp_user=l.id_emp_user "+
							" and  l.type_user='EXCTV'";
					rs = dtcm.GetResultSet(Sql,  request);
					if(rs.next())
					{
						replyMailId = rs.getString(1);
						recipients.remove(rs.getString(1));
					}
			    	
			    	String mailSql ="select sa.nm_s_assetdiv,no_model,no_mfr,wh.id_wh_dyn from M_Subasset_Div sa, A_Ware_House wh where wh.id_wh='"+id+"' and sa.id_s_assetdiv=wh.id_sgrp";
			    
					rs = dtcm.GetResultSet(mailSql,  request);
					if(rs.next())
					{
						itemName = rs.getString(1);
						no_model = rs.getString(2);
						slNo = rs.getString(3);
						id_wh_dyn = rs.getString(4);
						
					}
					
					String link = dtcm.ReturnParticularMessage("link");
					String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
					
					String mailBody = "Hello <b>Executive Team</b>,<br><br><br>"+
								  "The <b>'"+itemName+"'</b> having Model No <b>'"+no_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b>  has been approved for travel.<br>"+
								  "<br><br><br>Click here for login....<a href='"+link+"'>Click Here</a>"+
								  "<br><br>"+footerMsg+""+
								  "<br><br><b>Thanks & Regards, <b>"+
								  "<br>Intuit India";
				
					Common.MailConfiguration(mailBody, replyMailId, recipients , "");
				
					j=1;
				}
				
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
