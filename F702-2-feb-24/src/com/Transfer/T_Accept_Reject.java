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


public class T_Accept_Reject extends HttpServlet {
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
		    
		String action = "",id="0",id_wh_dyn="",dt_to="",dt_frm="",status_approv="",word="",req_no="";
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
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
			String id_dept = (String)session.getAttribute("DeptId");
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
	        			tempSql= "select distinct(req_no),dt_req,nm_emp from A_Ware_House wh, M_Emp_User emp where  and emp.id_emp_user=wh.req_by and "+
								 " id_wh in (select id_wh from A_Iut_History where iut_approval =1 and type_tran='Intra') and  acc_asst=0 and emp.repo_mngr="+id_emp_user+"  "+likeTempQuery+" "+tempQuery+"" ;
	        		}
	        		else
	        			tempSql= "select distinct(req_no),dt_req,nm_emp from A_Ware_House wh, M_Emp_User emp where emp.id_emp_user=wh.req_by and "+
								 " id_wh in (select id_wh from A_Iut_History where iut_approval =1 and type_tran='Intra') and  acc_asst=0  "+likeTempQuery+"" ;
	        		System.out.println(tempSql);
	        		jobj = dtoCommon.GetDataForDisplay(tempSql,  request);
	                break;
	                
	            case "Update":
	            	jobj = UpdateTransferRequestForAcceptReject(id,status_approv,id_emp_user,  request);	
	                break;
	                
	            case "Edit":
	            	jobj = TransferRequestDtaForEdit(id);	
	                break;
	                
	            case "Preview":
	            	 dtoCommon = new DtoCommon();
	            	tempSql= "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst,serial_no from A_Ware_House where req_no="+req_no+" " ;
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
	
	
	public JSONObject TransferRequestDtaForEdit(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			/*
			String sql="select ds_pro,no_mfr as mfr,nm_assetdiv,ds_asst,nm_loc,nm_cc,nm_flr,nm_subl "+
					"from A_Ware_House wh , M_Asset_Div ad , M_Loc l, M_Subloc sl ,M_Floor f ,"+
					" M_Company_Costcenter cc where  id_wh = "+id+" and wh.id_loc=l.id_loc and wh.id_subl = sl.id_sloc "+
					" and wh.id_cc = cc.id_cc and wh.id_flr = f.id_flr and wh.id_grp = ad.id_assetdiv";*/

			String sql="select ds_pro,no_mfr as mfr,nm_assetdiv,ds_asst,nm_loc,nm_flr,nm_subl "+
					"from A_Ware_House wh , M_Asset_Div ad , M_Loc l, M_Subloc sl ,M_Floor f "+
					"  where  id_wh = "+id+" and wh.id_loc=l.id_loc and wh.id_subl = sl.id_sloc "+
					"  and wh.id_flr = f.id_flr and wh.id_grp = ad.id_assetdiv";
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
			    /*sql="select nm_loc as nm_loc_tran,nm_cc as nm_cc_tran,nm_flr as nm_flr_tran,nm_subl as nm_subl_tran "+
			    		 "from A_Iut_History wh ,M_Loc l, M_Subloc sl ,M_Floor f ,"+
			    		" M_Company_Costcenter cc where  id_wh = "+id+" and iut_approval = 1 and wh.id_loc_tran=l.id_loc and wh.id_subl_tran = sl.id_sloc "+
			    		" and wh.id_cc_tran = cc.id_cc and wh.id_flr_tran = f.id_flr ";	   */
 sql="select nm_loc as nm_loc_tran,nm_flr as nm_flr_tran,nm_subl as nm_subl_tran "+
	 "from A_Iut_History wh ,M_Loc l, M_Subloc sl ,M_Floor f "+
	"  where  id_wh = "+id+" and iut_approval = 1 and wh.id_loc_tran=l.id_loc and wh.id_subl_tran = sl.id_sloc "+
	"  and wh.id_flr_tran = f.id_flr ";
 
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
	
	
	
	public JSONObject UpdateTransferRequestForAcceptReject( String id , String status_approv,int id_emp_user,HttpServletRequest request)
	{
		
		int j=0,id_iut=0;
		JSONObject json=new JSONObject();
		String apprvMsg="Rejected";
		String req_no = id;
		try 
		{
			String sql="select id_wh from A_Ware_House where req_no="+id+"";
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs1 = ps.executeQuery();
			while(rs1.next()){
				
			id = rs1.getString(1);
			
			 sql ="select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="+id+" and iut_approval=1";
			 System.out.println(sql);
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
				System.out.println(query);
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
			}
			else
			{
				System.out.println();
				rs=stmt.executeQuery("select id_loc,id_subl,id_flr,id_cc,dt_start,to_assign,allocate_by  from A_Iut_History where id_iut="+id_iut+" and iut_approval=1");
				stmt1.executeUpdate("update A_Iut_History set iut_approval=4,tran_reject_by="+id_emp_user+" where id_iut="+id_iut+" and iut_approval=1");   
				if(rs.next()){
						stmt1.executeUpdate("insert into A_Iut_History (id_wh,id_loc,id_subl,id_flr,id_cc,dt_start ,type_tran,iut_flag,to_assign,allocate_by)"+
" values ("+id+","+rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+", "+rs.getString(4)+",'"+rs.getString(5)+"','Intra',2,'"+rs.getString(6)+"','"+rs.getString(7)+"')");

						
				   }
				stmt.executeUpdate("update A_Ware_House set st_trvl=0 where id_wh="+id+"");	
				   j=1;
			}
			
			}
		
			 j=1;  
			 	stmt = con.createStatement();
				stmt.executeUpdate("update T_Transfer_Track set apprv_by="+id_emp_user+",dt_appv=GETDATE() where req_no="+req_no+"");
				
				//.........for mail ........
		    	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="",nm_db="";
		    	
		    	String Sql ="select id_emp from A_Ware_House wh,M_User_Login l,M_Emp_User emp "+
						" where  wh.id_wh="+id+"  and emp.id_emp_user=l.id_emp_user and emp.id_emp_user="+id_emp_user+"   ";
		    	System.out.println(Sql);
		    	DtoCommon dtcm = new DtoCommon();
				List<String> recipients = dtcm.ReturnListData(Sql,  request);
				
			if(!recipients.isEmpty())
			{
				//replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
				
				Sql ="select id_emp,nm_emp from A_Ware_House wh,M_User_Login l,A_IUT_History ih,M_Emp_User emp "+
	    				" where wh.id_wh=ih.id_wh and  wh.id_wh="+id+"  and emp.id_emp_user=l.id_emp_user and emp.id_emp_user=ih.tran_req_by and ih.id_iut="+id_iut+"";
				System.out.println(Sql);
				recipients = dtcm.ReturnEmail(Sql, recipients,  request);
				rs = dtcm.GetResultSet(Sql,  request);
				if(rs.next())
				{
					replyMailId = rs.getString(1);
					recipients.remove(replyMailId);
					name=rs.getString(2);
				}
				String mailSql ="select DB_NAME() AS [Current Database],c.nm_com from M_Company c";
				 rs1 = dtcm.GetResultSet(mailSql,  request);
					if(rs1.next())
					{
						
						nm_db= rs1.getString(1);
						
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
				String aprv = "<p>Your request for intra transfer movement having request number <b>(Request No-"+req_no+")</b> has been <b>"+apprvMsg+"</b>. Please Update the transfer date.</p>";
				if (!status_approv.equals("1"))
				{
					aprv="<p>Your request for intra transfer movement  having request number <b>(Request No-"+req_no+")</b> has been <b>"+apprvMsg+"</b>.</p>";
				}
				String link = dtcm.ReturnParticularMessage("baseUrlLink");
				String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
				String footerMsg2 = dtcm.ReturnParticularMessage("footerMsg2");
				String mailSubject = dtcm.ReturnParticularMessage("intraTrnAprv");
				
				String mailBody = "Hello <b>Executive Team</b>,<br><br><br>"+
									aprv +
									
									 "<br>For login....<a href='"+link+""+nm_db+".html'>Click Here</a>"+
									  "<br><br><p>"+footerMsg+"</p>";
			
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
