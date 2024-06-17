package com.Purchase;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
public class P_Request_PR_Approval extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		String action="",st_approv="Super",id_bu="",sql="",apprvColName="",no_req="",approval_val="",id="",id_req="",approv_by_col_name="approv_super_by",approv_Status="",remarks="",qty="0",query="",AcceptRejectStatus="",approv_col_name="st_apprv_super";
		double un_prc=0.0,tot_prc=0.0;
		HttpSession session = request.getSession();  
	 
		try
		{
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			String User_Type = (String)session.getAttribute("UserType");
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			
			// Getting Dynamic column name....
			if(User_Type.equals("PROCHEAD"))
			{
				approv_col_name="st_apprv_rm";
				approv_by_col_name="approv_rm_by";
				st_approv="RM";
			}
			else if(User_Type.equals("IT"))
			{
				approv_col_name="st_apprv_it";
				approv_by_col_name="approv_it_by";
				st_approv="IT";
			}
			else if(User_Type.equals("FD"))
			{
				approv_col_name="st_apprv_fd";
				approv_by_col_name="approv_fd_by";
				st_approv="FD";
			}
			
			if(request.getParameter("action") !=null)
			{
				 action = request.getParameter("action");
			}
			if(request.getParameter("approval_val") !=null)
			{
				approval_val = request.getParameter("approval_val");
			}
			if(request.getParameter("approv_Status") !=null)
			{
				approv_Status = request.getParameter("approv_Status");
			}
			if(request.getParameter("AcceptRejectStatus") !=null)
			{
				AcceptRejectStatus = request.getParameter("AcceptRejectStatus");
			}
			
			if(request.getParameter("id") !=null)
			{
				id = request.getParameter("id");
			}
			if(request.getParameter("id_req") !=null)
			{
				id_req = request.getParameter("id_req");
			}
			if(request.getParameter("id_bu") !=null)
			{
				id_bu = request.getParameter("id_bu");
			}
			String non_budget=request.getParameter("non_budget");
			String chk[] = request.getParameterValues("approvalPR");
			
			con=Common.GetConnection(request);
			stmt=con.createStatement();
			if(action.equals("Display"))
		           jobj = DisplayRequest(approv_Status,id_emp_user,User_Type);	
				
				else
			if(action.equals("budgetCheck"))
	           jobj = budgetCheck(id,id_bu);	
			
			else if(action.equals("Edit"))
				
				
				jobj = DisplayParticularRequest(id,approv_Status,non_budget);	
			
			else if(action.equals("Update"))
			{
				String budget_type =request.getParameter("budget_type");
				int j=0;
				double util_prc=0.0;
				for(int i=0; i<chk.length;i++)
            	{
					remarks =request.getParameter("remarks"+chk[i]);
					qty =request.getParameter("qty"+chk[i]);
					
					un_prc =Double.parseDouble(request.getParameter("un_prc"+chk[i]));
					tot_prc =Double.parseDouble(request.getParameter("tot_prc"+chk[i]));
					util_prc +=tot_prc;
				
				rs=stmt.executeQuery("select st_apprv_rm,st_apprv_it,st_apprv_fd,st_apprv_super from P_Request_Asset where id_req_asst="+chk[i]+"");
    			if(rs.next())
    			{
    				
    				//st_approv="Super";
    				if(rs.getString(1).equals("waiting"))
    				{
    					approv_col_name="st_apprv_rm";
    					approv_by_col_name="approv_rm_by";
    					apprvColName="second_la";
    				}
    				else if(rs.getString(2).equals("waiting"))
    				{
    					approv_col_name="st_apprv_it";
    					approv_by_col_name="approv_it_by";
    					apprvColName="third_la";
    				}
    				else if(rs.getString(3).equals("waiting"))
    				{
    					approv_col_name="st_apprv_fd";
    					approv_by_col_name="approv_fd_by";
    					apprvColName="fourth_la";
    				}
    				else if(rs.getString(4).equals("waiting"))
    				{
    					approv_col_name="st_apprv_super";
    					approv_by_col_name="approv_super_by";
    					apprvColName="dontDoAnyThing";
    				}
    					
    			}
    			Properties prop = new Properties();	
				InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
				
				if (inputStream == null) {
					System.out.println("property file 'configuration.properties' not found in the classpath");
				}
					
					prop.load(inputStream);
					
					String Budgetemployeeidfrorapproval = prop.getProperty("Budgetemployeeidfrorapproval");
    			//int secondla=1486;
					String id_grp="",id_sgrp="",id_prod="",nm_model="";
				
					id_grp=request.getParameter("id_grp"+chk[i]);
					id_sgrp=request.getParameter("id_sgrp"+chk[i]);
					id_prod=request.getParameter("id_prod"+chk[i]);
					nm_model=request.getParameter("nm_model"+chk[i]);
					System.out.println("nulltest"+id_grp);
					
					query ="update P_Request_Asset set approv_remarks='"+remarks+"',qty="+qty+",un_prc="+un_prc+",tot_prc="+tot_prc+
							","+approv_col_name+" = '"+AcceptRejectStatus+"',"+approv_by_col_name+"="+id_emp_user+",dt_approv='"+sdf.format(today)+"' where id_req_asst="+chk[i]+"";
					ps=con.prepareStatement(query);
	    			j=ps.executeUpdate();
	    			if(j>0)
	    			{
	    				 j=0;
	    				 
	    				 
	    				 budget_type=request.getParameter("budget_type"+chk[i]);
	    				 String thirdla="";
	    				 if(approv_col_name.equals("st_apprv_it")) {
	    				 if(budget_type.equals("non_budget") ) {
	    					 
	    					 String mailSql ="select firstla from p_request where id_req="+id_req+"";
	    		    			
	    		            	String rm_rm="";
	    		            	DtoCommon dtcm = new DtoCommon();
	    		    			rs = dtcm.GetResultSet(mailSql,  request);
	    		    			if(rs.next())
	    		    			{
	    		    				 
	    		    				rm_rm =rs.getString(1);
	    		    			 
	    		    			}
	    					 
	    					  mailSql ="select repo_mngr from m_emp_user where id_emp_user=   '"+rm_rm+"'";
	    		    			
	    		            	
	    		            	
	    		    			rs = dtcm.GetResultSet(mailSql,  request);
	    		    			if(rs.next())
	    		    			{
	    		    				 
	    		    				thirdla ="thirdla='"+rs.getString(1)+"',";
	    		    			 
	    		    			}
	    				 }else {
	    					 
	    					 
	    					 
	    				 }
	    				 
	    				 }
                        if(approv_col_name.equals("st_apprv_rm")) {
                      	  query ="update P_Request set secondla='"+Budgetemployeeidfrorapproval+"', st_approv='"+st_approv+"',approv_by="+id_emp_user+" where id_req="+id_req+"";
      	    				 
	    				 }
                        if(approv_col_name.equals("st_apprv_it")) {
                      	  query ="update P_Request set "+thirdla+" st_approv='"+st_approv+"',budget_type='"+budget_type+"',approv_by="+id_emp_user+" where id_req="+id_req+"";
      	    				
                        }
                        if(approv_col_name.equals("st_apprv_fd")) {
                        	  query ="update P_Request set fourthla='"+Budgetemployeeidfrorapproval+"', st_approv='"+st_approv+"',budget_type='"+budget_type+"',approv_by="+id_emp_user+" where id_req="+id_req+"";
        	    				
                          }
	    				 ps=con.prepareStatement(query);
	 	    			j=ps.executeUpdate();
	 	    			if(j > 0)
	 	    			{
	 	    				jobj.put("data",1);
	 	    			}
	 	    			else
	 	    			{
	 	    				jobj.put("data",0);
	 	    			}
	 	    			
	 	    			 query ="update P_Request_Asset  set id_grp='"+id_grp+"',id_sgrp='"+id_sgrp+"',asset_bud_type='"+budget_type+"'  where id_req_asst="+chk[i]+"  ";
	 	    			 System.out.println(query);
	 	 		    	
	 	    			 ps=con.prepareStatement(query);
	 		    			j=ps.executeUpdate();
	 		    			
	 		    			
	 		    		query ="update m_model  set nm_model='"+nm_model+"', id_assetdiv='"+id_grp+"',id_s_assetdiv='"+id_sgrp+"'  where id_model="+id_prod+"  ";
	 		    		ps=con.prepareStatement(query);
		 		    	j=ps.executeUpdate();
	    			}
				
            	}
				if(AcceptRejectStatus.equals("Accepted"))
				{
					
					j=0;
					
	    				
   				 query ="update P_Request_Asset  set   "+approv_col_name+"='Rejected',"+approv_by_col_name+"="+id_emp_user+" where id_req="+id_req+" and "+approv_col_name+"='waiting'";
							
   				 ps=con.prepareStatement(query);
	    			j=ps.executeUpdate();
	    				jobj.put("data",1);
					
				}
				
				// Mail Trigger......
	            
            	String replyMailId="",name="",ccMailId="";
            	String mailSql ="select id_emp,nm_emp,no_req from M_Emp_User emp,P_request r where emp.id_emp_user=r.req_by and id_req='"+id_req+"'";
    			
            	
            	DtoCommon dtcm = new DtoCommon();
    			rs = dtcm.GetResultSet(mailSql,  request);
    			if(rs.next())
    			{
    				replyMailId = rs.getString(1);
    				name = rs.getString(2);
    				no_req = rs.getString(3);
    				ccMailId = replyMailId;
    			}
    			
    			mailSql ="select distinct(id_emp),nm_emp from M_Emp_User emp,P_Request_Asset r where emp.id_emp_user=r."+approv_by_col_name+" and id_req='"+id_req+"'";
    			System.out.println(mailSql);
    			List<String> recipients = dtcm.ReturnListData(mailSql,  request);
    			
    			String link = dtcm.ReturnParticularMessage("link");
    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
    			String mailSubject = dtcm.ReturnParticularMessage("purchaseReqApprv");
    			
    			String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
    						  "Your purchase request <b>("+no_req+")</b> has been "+AcceptRejectStatus+".<br>"+
    						  "<br><br><br>"+link+""+
							  "<p>"+footerMsg+"</p>";
    		
    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
    			
    			/*if(!apprvColName.equals("dontDoAnyThing")){
	    			List<String> recipients1 = new ArrayList<String>();
	    			mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_request r where emp.id_emp_user=r."+apprvColName+" and id_req='"+id_req+"'";
	    			rs = dtcm.GetResultSet(mailSql);
					if(rs.next())
					{
						replyMailId = rs.getString(1);
						name = rs.getString(2);
					}
					mailSubject = dtcm.ReturnParticularMessage("purchaseReq");
					String mailBody1 = "<b> Hello "+name+"</b>,<br><br><br>"+
							  "Purchase request <b>("+no_req+")</b> has been created. Please approve it.<br>"+
							  "<br><br><br>"+link+""+
							  "<p>"+footerMsg+"</p>";
					recipients1.add(ccMailId);
					Common.MailConfiguration(mailBody1, replyMailId, recipients1 , mailSubject);
    			}*/
    	        j=1;  
    	        jobj.put("data",j);
				
				
				
			}
			
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in P_Request_PR_Approval."+e.toString());
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
	public JSONObject DisplayParticularRequest(String id,String approv_Status,String non_budget)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		String sql="",dt="",nm_bu="";
		int intMonth=0,intYear=0,id_bu=0,id_dept=0;
		double budg_util=0.0,budg_allo=0.0,budg_rem=0.0;
		String DynColName="";
		if(approv_Status.equals("waiting"))
			DynColName = "st_apprv_rm = 'waiting'";
		else if(approv_Status.equals("RM"))
			DynColName = "st_apprv_rm = 'Accepted' and st_apprv_it = 'waiting'";
		else if(approv_Status.equals("IT"))
			DynColName = "st_apprv_it = 'Accepted' and st_apprv_fd = 'waiting'";
		else if(approv_Status.equals("CEO"))
			DynColName = "st_apprv_fd = 'Accepted' and st_apprv_super = 'waiting'";
		
		try
		{
			stmt=con.createStatement();
		rs=stmt.executeQuery("select month(getdate()),year(getdate())");
		if(rs.next())
		{
			intMonth=rs.getInt(1);
			intYear=rs.getInt(2);
		}
		dt=intYear+"-04-01";
		sql="select pr.id_bu,cc.nm_bu,pr.id_dept from P_request pr, M_BU cc where pr.id_bu=cc.id_bu and pr.id_req="+id+"";
		rs=stmt.executeQuery(sql);
		if(rs.next()){
			id_bu=rs.getInt(1);
			nm_bu=rs.getString(2);
			//id_dept=rs.getInt(3);
		}
		/*sql="select annual_bud,annual_bud_done from M_Budget b,M_Finance_Year fy where b.id_finance=fy.id_fincance and fy.std_finance='"+dt+"' and id_cc="+id_cc+"";
		*/
		sql="select annual_bud,annual_bud_done,m.nm_model,m.id_model from M_Budget b,M_Finance_Year fy,M_Model m where b.id_finance=fy.id_fincance and active_year=1 and b.id_model=m.id_model and id_bu="+id_bu+" ";
		System.out.println(sql);
		rs=stmt.executeQuery(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		   ResultSetMetaData metaData1 = rs.getMetaData();
		    int columns1 = metaData1.getColumnCount();
		    while(rs.next())
		    {
		    	JSONObject jobj3 = new JSONObject();
		    	for(int i=1;i<=columns1;i++)
		    	{
		    		budg_allo=rs.getDouble("annual_bud");
					budg_util=rs.getDouble("annual_bud_done");
					budg_rem=budg_allo-budg_util;
					 jobj3.put("nm_cc",nm_bu);
					 jobj3.put("nm_model",rs.getString("nm_model"));
					    jobj3.put("budg_allo",budg_allo);
					    jobj3.put("budg_util",budg_util);
					    jobj3.put("budg_rem",budg_rem);
		    	}
		    		jarr1.put(jobj3);
		    		
	        }
		    /*if(rs.next())
		{
			budg_allo=rs.getDouble(1);
			budg_util=rs.getDouble(2);
		}
		budg_rem=budg_allo-budg_util;
		JSONObject jobj3 = new JSONObject();
		    jobj3.put("nm_cc",nm_bu);
		    jobj3.put("budg_allo",budg_allo);
		    jobj3.put("budg_util",budg_util);
		    jobj3.put("budg_rem",budg_rem);
		    jarr1.put(jobj3);  */
		    String checkbud="";
		if(non_budget.equals("non_budget")) {
			checkbud="and asset_bud_type='non_budget'";
		}
		
			sql = "select pra.*,pr.no_req,dt_req,remarks,nm_emp,nm_model,cd_model,pr.budget_type,pr.id_bu,id_s_assetdiv,id_assetdiv from P_Request_Asset pra , P_request pr ,m_model pc,M_Emp_User emp "+
					" where pr.id_req=pra.id_req and pra.id_prod=pc.id_model and pr.req_by=emp.id_emp_user and "+DynColName+" and pr.id_req="+id+" "+checkbud+"";
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    	
		    	sql="select distinct annual_bud,annual_bud_done,(annual_bud - annual_bud_done) as budg_rem from M_Budget b,M_Finance_Year fy,M_Subasset_Div sd where b.id_finance=fy.id_fincance and active_year=1 and sd.id_s_assetdiv=b.id_s_assetdiv  and id_bu="+rs.getString("id_bu")+" and sd.id_s_assetdiv="+rs.getString("id_s_assetdiv")+" ";
				System.out.println(sql);
				rs1=stmt.executeQuery(sql);
				ps=con.prepareStatement(sql);
				rs1=ps.executeQuery();
				
				if(rs1.next()) {
					jobj2.put("budg_allo",rs1.getString("annual_bud"));
					jobj2.put("budg_util",rs1.getString("annual_bud_done"));
					jobj2.put("budg_rem",rs1.getString("budg_rem"));
					
					
				}
				else {
					
					jobj2.put("budg_allo","0");
					jobj2.put("budg_util","0");
					jobj2.put("budg_rem","0");
					
				}
		    	
		    	
		    	
		    		jarr.put(jobj2);
		    		
	        }
		    
		   
		    jobj.put("data", jarr);
		    jobj.put("budget", jarr1);
		}
		catch(Exception e)
		{
			System.out.println("sql error in P_Request_PR_Approval."+e.toString());
		}
		 
		return jobj;
		
		
	}
	public JSONObject UpdateRequest(String id , String approval_val)
	{
		
		int j=0;
		JSONObject json=new JSONObject();
		
		String query="update P_Request st_approv ="+approval_val+" where id_req="+id+"";
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
	
	
	public JSONObject DisplayRequest(String approv_Status,int id_emp_user,String User_Type)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="",approv_col_name="st_apprv_super",col_name="";
		if(approv_Status.equals("waiting"))
		{
			approv_col_name="st_apprv_rm = 'waiting'";
			col_name="firstla";
		}
		else if(approv_Status.equals("RM"))
		{
			approv_col_name="st_apprv_rm ='Accepted' and st_apprv_it ='waiting'" ;
			col_name="secondla";
		}
		
		  else if(approv_Status.equals("IT")) {
		  approv_col_name="st_apprv_it ='Accepted'  ";
			col_name = "third_la";
		} 
		  else if(approv_Status.equals("CEO")) {
			  approv_col_name="st_apprv_fd ='Accepted'  ";
				col_name = "fourth_la";
			} 
		
		if(approv_Status.equals("waiting"))
		{	
			sql = "select distinct(r.id_req),no_req,nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_bu from P_Request r,P_Request_Asset pr,M_Emp_User emp,M_BU b"+
			"  where r.req_by=emp.id_emp_user and firstla="+id_emp_user+" and r.id_bu=b.id_bu and "+
			" pr.id_req=r.id_req and "+approv_col_name+"";
		}
		
		if(approv_Status.equals("RM"))
		{	
			sql = "select distinct(r.id_req),no_req,nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_bu from P_Request r,P_Request_Asset pr,M_Emp_User emp,M_BU b"+
						" where r.req_by=emp.id_emp_user and secondla="+id_emp_user+" and r.id_bu=b.id_bu and"+
			" pr.id_req=r.id_req and "+approv_col_name+"";
		}
		
		if(approv_Status.equals("IT"))
		{	
			sql = "select distinct(r.id_req),no_req,nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_bu from P_Request r,P_Request_Asset pr,M_Emp_User emp,M_BU b"+
						" where r.req_by=emp.id_emp_user and   thirdla="+id_emp_user+"  and r.id_bu=b.id_bu and"+
			" pr.id_req=r.id_req and asset_bud_type='non_budget' and  "+approv_col_name+"";
		}
		 
		if(approv_Status.equals("CEO"))
		{	
			sql = "select distinct(r.id_req),no_req,nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_bu from P_Request r,P_Request_Asset pr,M_Emp_User emp,M_BU b"+
						" where r.req_by=emp.id_emp_user and   fourthla="+id_emp_user+" and r.id_bu=b.id_bu and"+
			" pr.id_req=r.id_req and asset_bud_type='non_budget'  and st_apprv_super='waiting' and "+approv_col_name+"";
		}
		 
            	 
            	 
		
		/*
		 * if(User_Type.equals("SUPER") || User_Type.equals("PAPPRV") ||
		 * User_Type.equals("PRPOCODNTR") || User_Type.equals("APPROVERUSER") ||
		 * User_Type.equals("STOREHEAD")) sql =
		 * "select distinct(r.id_req),no_req,nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_cc from P_Request r,P_Request_Asset pr,M_Emp_User emp,M_Company_Costcenter cc "
		 * + " where r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc and "+
		 * "  pr.id_req=r.id_req   and send_for_apprvl='Yes' and "+approv_col_name+"  ";
		 * else sql =
		 * "select distinct(r.id_req),no_req,nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_cc from P_Request r,P_Request_Asset pr,M_Emp_User emp,M_Company_Costcenter cc "
		 * + " where r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc  and "+
		 * " "+col_name+"="+
		 * id_emp_user+" and  pr.id_req=r.id_req    and send_for_apprvl='Yes' and "
		 * +approv_col_name+""; if(approv_Status.equals("waiting")) { sql =
		 * "select distinct(r.id_req),no_req,nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_cc from P_Request r,P_Request_Asset pr,M_Emp_User emp,M_Company_Costcenter cc "
		 * +
		 * " where r.req_by=emp.id_emp_user and r.add_by IN (select id_emp_user from M_Emp_User where repo_mngr="
		 * +id_emp_user+") and r.id_cc=cc.id_cc and"+
		 * " pr.id_req=r.id_req and "+approv_col_name+""; }
		 * 
		 * 
		 * sql =
		 * "select distinct(r.id_req),no_req,nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_cc,b.nm_bu from P_Request r,P_Request_Asset pr,M_Emp_User emp,M_Company_Costcenter cc,M_BU b "
		 * +
		 * " where r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc and r.id_bu=b.id_bu and"
		 * + "  pr.id_req=r.id_req   and send_for_apprvl='Yes' and "+col_name+"="+
		 * id_emp_user+" and "+approv_col_name+"  ";
		 */
		if(User_Type.equals("SUPER") || User_Type.equals("PAPPRV") || User_Type.equals("PRPOCODNTR") || User_Type.equals("APPROVERUSER") || User_Type.equals("STOREHEAD"))
			sql = "select distinct(r.id_req),no_req,nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req  ,b.nm_bu from P_Request r,P_Request_Asset pr,M_Emp_User emp ,M_BU b "+
					" where r.req_by=emp.id_emp_user   and  r.id_bu=b.id_bu and"+
					"  pr.id_req=r.id_req   and send_for_apprvl='Yes' and "+approv_col_name+"  ";
		
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in P_Request_PR_Approval."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject budgetCheck(String id,String id_bu )
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		 
		 
		
		try
		{
			
			
			
			sql="select distinct annual_bud,annual_bud_done,(annual_bud - annual_bud_done) as budg_rem from M_Budget b,M_Finance_Year fy where b.id_finance=fy.id_fincance and active_year=1"+
					 " and id_bu="+ id_bu+" and id_s_assetdiv="+id+" ";
			  	System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		    
		    JSONObject jobj2 = new JSONObject();
		    	if(rs.next()) {
					jobj2.put("budg_allo",rs.getString("annual_bud"));
					jobj2.put("budg_util",rs.getString("annual_bud_done"));
					jobj2.put("budg_rem",rs.getString("budg_rem"));
					
					
				}
				else {
					
					jobj2.put("budg_allo","0");
					jobj2.put("budg_util","0");
					jobj2.put("budg_rem","0");
					
				}
		    		jarr.put(jobj2);
		    		
	      
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in budcheck."+e.toString());
		}
		 
		return jobj;
		
		
	}
}
