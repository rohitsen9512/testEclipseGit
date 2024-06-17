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
import dto.Common.UserAccessData;


public class T_Inter_EBS_Accept_Reject extends HttpServlet {
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
		    
		String action = "",id="0",id_wh_dyn="",dt_to="",dt_frm="",status_approv="",tranType="",word="",req_no="";
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("tranType") !=null)
		{
			tranType = request.getParameter("tranType");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_wh_dyn") !=null)
		{
			id_wh_dyn = request.getParameter("id_wh_dyn");
		}
		if(request.getParameter("status_approv") !=null)
		{
			status_approv = request.getParameter("status_approv");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}	
		if(request.getParameter("req_no") !=null)
		{
			req_no = request.getParameter("req_no");
		}	
		try
		{
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType");
			int UserId = (int)session.getAttribute("UserId");
			
			String id_dept = (String)session.getAttribute("DeptId");
			String temp="";
			String FlrId = (String)session.getAttribute("FlrId");
			int DivId = (int)session.getAttribute("DivId");
			if(!UserType.equals("SUPER")){
				String Query = Common.returnQuery(FlrId);
				temp = temp + " "+Query+" and wh.id_div ="+DivId+"";
			}
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "Display":
	            	//jobj = DisplayAssetOfTransferRequestForAcceptreject(temp,UserType);	
	            	
	            	String tempSql="";
	            	String likeTempQuery="";
	            	DtoCommon dtoCommon = new DtoCommon();
	            	if(!word.equals(""))
        			{
        				 likeTempQuery = dtoCommon.MakeLikeTempQuery(word);
        			}
	        		if(!UserType.equals("SUPER")){
	        			//String tempQuery = dtoCommon.MakeTempQuery(UserType , UserId);
	        			tempSql= "select distinct(req_no),dt_req,nm_emp from A_Ware_House wh,A_Iut_History iut,M_Emp_User emp where (allocate=0 or allocate=2) and "+
		            			" wh.id_wh= iut.id_wh and iut_approval =2 and type_tran !='Intra' and emp.id_emp_user=wh.req_by and  acc_asst=0 and emp.id_emp_user=wh.req_by and emp.repo_mngr="+id_emp_user+" ";
	        		}
	        		else
	        			tempSql= "select distinct(req_no),dt_req,nm_emp from A_Ware_House wh,A_Iut_History iut,M_Emp_User emp where (allocate=0 or allocate=2) and "+
    							" wh.id_wh= iut.id_wh and iut_approval =2 and type_tran !='Intra' and emp.id_emp_user=wh.req_by and  acc_asst=0  ";
	            				
	        		UserAccessData uad = new UserAccessData();
	            	jobj = uad.QueryMacker(tempSql , UserType , word,id_dept,  request);
	            	//System.out.println(tempSql);
	            	
	                break;
	                
	            case "Update":
	            	jobj = UpdateTransferRequestForAcceptReject(req_no,status_approv,id_emp_user,  request);	
	                break;
	                
	            case "Edit":
	            	jobj = TransferRequestDtaForEdit(id,tranType);	
	                break;
	                
	            case "Preview":
	            	 dtoCommon = new DtoCommon();
	            	tempSql= "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst from A_Ware_House where req_no="+req_no+" " ;
	            		jobj = dtoCommon.GetDataForDisplay(tempSql,  request);	
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
	
	
	public JSONObject TransferRequestDtaForEdit(String id , String tranType)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{

			String sql="select ds_pro,nm_assetdiv,ds_asst,nm_loc,nm_subl,mfr "+
					"from A_Ware_House wh , M_Asset_Div ad , M_Loc l, M_Subloc sl "+
					"  where  id_wh = "+id+" and wh.id_loc=l.id_loc and wh.id_subl = sl.id_sloc "+
					"   and wh.id_grp = ad.id_assetdiv";
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
			    if(tranType.equals("Temporary"))
			    {
			    	sql="select type_tran,nm_ven from A_Iut_History wh , M_Vendor v where  id_wh = "+id+" and iut_approval = 1 and wh.id_ven=v.id_ven";
			    }	
			    else
			    {
			    	sql="select nm_loc as nm_loc_tran,nm_subl as nm_subl_tran,type_tran "+
			    			 "from A_Iut_History wh ,M_Loc l, M_Subloc sl "+
			    			"  where  id_wh = "+id+" and iut_approval = 1 and wh.id_loc_tran=l.id_loc and wh.id_subl_tran = sl.id_sloc";
			    			
			    }
 
 
 ps=con.prepareStatement(sql);
	rs=ps.executeQuery();
	
	    metaData = rs.getMetaData();
	     columns = metaData.getColumnCount();
	     jobj2 = new JSONObject();
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
	
	
	
	public JSONObject UpdateTransferRequestForAcceptReject( String req_no , String status_approv,int id_emp_user,HttpServletRequest request)
	{
		
		int j=0,id_iut=0;
		String id="",sql="";
		JSONObject json=new JSONObject();
		String apprvMsg="Rejected";
		String req_by="",id_loc="",id_sloc="",id_building="",id_flr="",id_div="";
		try 
		{
			
			sql = "select id_wh from A_Ware_House where req_no="+req_no+"";
			ps=con.prepareStatement(sql);
			rs1 = ps.executeQuery();
			while(rs1.next()){
				id = rs1.getString(1);
			
			 sql ="select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="+id+" and iut_approval=2 ";
			ps=con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				id_iut = rs.getInt(1);
			}
			
			
			sql ="select tran_req_by,id_loc_tran,id_subl_tran,id_building_tran,id_flr_tran,id_div_tran from A_Iut_History where id_iut ="+id_iut+" and iut_approval=2 ";
			ps=con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				req_by = rs.getString(1);
				id_loc=rs.getString(2);
				/*id_sloc=rs.getString(3);
				id_building=rs.getString(4);
				id_flr=rs.getString(5);
				id_div=rs.getString(6);*/
			}
			
			
			
			stmt = con.createStatement();
			stmt1 = con.createStatement();
			String query="";
			if (status_approv.equals("1"))
			{
				apprvMsg = "Approved";
				query="update A_Iut_History set iut_approval=3,tran_ebs_apprv_by="+id_emp_user+" where id_iut="+id_iut+" and iut_approval=2";
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
			}
			else
			{
				stmt1.executeUpdate("update A_Iut_History set iut_approval=4,tran_reject_by="+id_emp_user+" where id_iut="+id_iut+" and iut_approval=2");   
				stmt1.executeUpdate("update A_Ware_House set asset_tran='Intra',st_trvl=0 where id_wh="+id+"");   
				j=1;
			}
			
			}
			stmt = con.createStatement();
			stmt.executeUpdate("update T_Transfer_Track set apprv_by_fnc="+id_emp_user+",dt_apprv_by_fnc=GETDATE() where req_no="+req_no+"");
			
			//.........for mail ........
	    	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="";
	    	
	    	String Sql ="select id_emp from M_Emp_User e , M_User_Login u where e.id_emp_user=u.id_emp_user and u.id_div="+id_div+" and u.id_loc="+id_loc+"";
			
	    	
	    	DtoCommon dtcm = new DtoCommon();
			List<String> recipients = dtcm.ReturnListData(Sql,  request);
			
		if(!recipients.isEmpty())
		{
			//replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
			
			Sql ="select id_emp from  M_Emp_User m where id_emp_user="+req_by+"";
	    	
			recipients = dtcm.ReturnEmail(Sql, recipients,  request);
			rs = dtcm.GetResultSet(Sql,  request);
			if(rs.next())
			{
				replyMailId = rs.getString(1);
				recipients.remove(replyMailId);
			}
	    	
	    	/*String mailSql ="select sa.nm_s_assetdiv,no_model,no_mfr,wh.id_wh_dyn from M_Subasset_Div sa, A_Ware_House wh where wh.id_wh='"+id+"' and sa.id_s_assetdiv=wh.id_sgrp";
	    
			rs = dtcm.GetResultSet(mailSql);
			if(rs.next())
			{
				itemName = rs.getString(1);
				no_model = rs.getString(2);
				slNo = rs.getString(3);
				id_wh_dyn = rs.getString(4);
				
			}*/
			
			String aprv="<p>Your request for transfer movement  having request number <b>(Request No-"+req_no+")</b> has been <b>"+apprvMsg+"</b>. Please Update the transfer date.</p>";
			if (!status_approv.equals("1"))
			{
				aprv="<p>Your request for transfer movement  having request number <b>(Request No-"+req_no+")</b> has been <b>"+apprvMsg+"</b>.</p>";
			}
			String link = dtcm.ReturnParticularMessage("link");
			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
			String footerMsg2 = dtcm.ReturnParticularMessage("footerMsg2");
			String mailSubject = dtcm.ReturnParticularMessage("interTrnAprv");
			
			String mailBody = "Hello <b>Executive Team</b>,<br><br><br>"+
							aprv +
						  "<br><br><br>"+link+""+
						  "<br><br><p>"+footerMsg+"</p>"+
						  "<br><br>"+footerMsg2+"<b>";
		
			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	
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
