package com.Transfer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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


public class T_Inter_Transfer_Approval_Finance extends HttpServlet {
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
	        			tempSql= "select req_no,dt_tran,nm_emp from T_Transfer_Track tt,M_Emp_User emp where tt.req_by=emp.id_emp_user and apprv_by_lgstc=1 and apprv_by_fnc=0  "+likeTempQuery+"" ;
	        		
	        			jobj = dtoCommon.GetDataForDisplay(tempSql,  request);
	            	
	                break;
	                
	            case "Update":
	            	jobj = UpdateTransferRequestForApprovalLogistic(req_no,status_approv,id_emp_user,  request);	
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
	
	
	
	public JSONObject UpdateTransferRequestForApprovalLogistic( String req_no , String status_approv,int id_emp_user,HttpServletRequest request)
	{
		
		int j=0,id_iut=0;
		String id="",sql="";
		JSONObject json=new JSONObject();
		String apprvMsg="Rejected";
		
		try 
		{
			
			sql = "select id_wh from A_Ware_House where req_no="+req_no+"";
			ps=con.prepareStatement(sql);
			rs1 = ps.executeQuery();
			while(rs1.next()){
				id = rs1.getString(1);
			
			 sql ="select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="+id+" and iut_approval=1 ";
			ps=con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				id_iut = rs.getInt(1);
			}
			
			stmt = con.createStatement();
			stmt1 = con.createStatement();
			String query="";
			if (status_approv.equals("1"))
			{
				apprvMsg = "Approved";
				query="update A_Iut_History set iut_approval=2,tran_aprov_by="+id_emp_user+" where id_iut="+id_iut+" and iut_approval=1";
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
			}
			else
			{
				stmt1.executeUpdate("update A_Iut_History set iut_approval=4,tran_reject_by="+id_emp_user+" where id_iut="+id_iut+" and iut_approval=1");   
				stmt1.executeUpdate("update A_Ware_House set asset_tran='Intra',st_trvl=0 where id_wh="+id+"");   
				j=1;
			}
			
			}
			stmt = con.createStatement();
			stmt.executeUpdate("update T_Transfer_Track set apprv_by_fnc="+status_approv+" where req_no="+req_no+"");
			
			//.........for mail ........
	    	String replyMailId="",ccEmailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="";
	    	
	    	String Sql ="select id_emp from T_Transfer_Track tt,M_Emp_User emp where tt.req_by=emp.id_emp_user and req_no="+req_no+"";
	    	
	    	DtoCommon dtcm = new DtoCommon();
	    	rs = dtcm.GetResultSet(Sql,  request);
			if(rs.next())
			{
				replyMailId = rs.getString(1);
				ccEmailId = replyMailId;
				
			}
	    	
			List<String> recipients = new ArrayList<String>();
			List<String> recipients1 = new ArrayList<String>();
			
			//replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
			
			Sql ="select id_emp from M_Emp_User emp,M_User_Login ul where ul.id_emp_user=emp.id_emp_user and type_user='FINC'";
			
			recipients1 = dtcm.ReturnEmail(Sql, recipients,  request);
			
	    	
	    	/*String mailSql ="select sa.nm_s_assetdiv,no_model,no_mfr,wh.id_wh_dyn from M_Subasset_Div sa, A_Ware_House wh where wh.id_wh='"+id+"' and sa.id_s_assetdiv=wh.id_sgrp";
	    
			rs = dtcm.GetResultSet(mailSql);
			if(rs.next())
			{
				itemName = rs.getString(1);
				no_model = rs.getString(2);
				slNo = rs.getString(3);
				id_wh_dyn = rs.getString(4);
				
			}*/
			
			String aprv="<p>Your request for inter transfer movement  having request number <b>(Request No-"+req_no+")</b> has been <b>"+apprvMsg+"</b> by finance team.</p>";
			if (!status_approv.equals("1"))
			{
				aprv="<p>Your request for inter transfer movement  having request number <b>(Request No-"+req_no+")</b> has been <b>"+apprvMsg+"</b> by finance team.</p>";
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
		
			Common.MailConfiguration(mailBody, replyMailId, recipients1 , mailSubject);
			
			recipients = new ArrayList<String>();
			String id_loc="",id_sloc="";
			Sql ="select id_loc,id_subl from A_Ware_House where req_no="+req_no+"";
			//recipients = dtcm.ReturnEmail(Sql, recipients);
			rs = dtcm.GetResultSet(Sql,  request);
			if(rs.next())
			{
				id_loc = rs.getString(1);
				id_sloc = rs.getString(2);
				
			}
			Sql = "select e.id_emp from M_User_Login l,M_Emp_User e where e.id_emp_user=l.id_emp_user and "+
					 "type_user='SEQRT' and e.status_emp='Working' and l.status_user='1' and l.id_sloc like '%"+id_sloc+"%' and l.id_loc like '%"+id_loc+"%'";

			//Sql ="select id_emp from M_Emp_User emp,M_User_Login ul where ul.id_emp_user=emp.id_emp_user and type_user='SEQRT'";
			recipients = dtcm.ReturnEmail(Sql, recipients,  request);
			rs = dtcm.GetResultSet(Sql,  request);
			if(rs.next())
			{
				replyMailId = rs.getString(1);
				recipients.remove(replyMailId);
				
			}
			mailSubject = dtcm.ReturnParticularMessage("interTrnGP");
			recipients.add(ccEmailId);
			
			 mailBody = "Hello <b>Security Team</b>,<br><br><br>"+
					 	  "<p>Executive has been raised a request for inter transfer movement having request number <b>(Request No-"+req_no+")</b>. Please create the gate pass.</p><br>"+
						  "<br><br><br>"+link+""+
						  "<br><br><p>"+footerMsg+"</p>"+
						  "<br><br>"+footerMsg2+"<b>";
		
			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);

		j=1;
		
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
}
