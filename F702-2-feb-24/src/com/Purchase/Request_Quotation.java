package com.Purchase;

import java.io.IOException;
import java.io.PrintWriter;
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
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;

import dto.Common.DtoCommon;

public class Request_Quotation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	Connection con = null;
	Statement stmt = null;
	Statement stmt2 = null;
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String UserName = (String) session.getAttribute("UserName");
		String User_Type = (String) session.getAttribute("UserType");
		String logged=(String) session.getAttribute("log_name");
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();

		HashMap<String, String> map = new HashMap<String, String>();
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		Enumeration elemet = request.getParameterNames();

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
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		
		String action = "",for_rate="",id_id_ind="",dt_rate_cont_valid="",word="", id = "0", confirm = "1", dt_to = "",id_ind="", dt_frm = "", ColName = "", no_req_val = "",dt_req="";
		if (request.getParameter("id_id_ind") != null) {
			id_id_ind = request.getParameter("id_id_ind");
		}
		if (request.getParameter("for_rate") != null) {
			for_rate = request.getParameter("for_rate");
		}
		if (request.getParameter("dt_rate_cont_valid") != null) {
			dt_rate_cont_valid = request.getParameter("dt_rate_cont_valid");
		}
		if (request.getParameter("id_ind") != null) {
			id_ind = request.getParameter("id_ind");
		}
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("no_req_val") != null) {
			no_req_val = request.getParameter("no_req_val");
		}
		if (request.getParameter("confirm") != null) {
			confirm = request.getParameter("confirm");
		}
		if (request.getParameter("dt_frm") != null) {
			dt_frm = request.getParameter("dt_frm");
		}
		if (request.getParameter("dt_to") != null) {
			dt_to = request.getParameter("dt_to");
		}
		if (request.getParameter("ColName") != null) {
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("dt_req") !=null)
		{
			dt_req = request.getParameter("dt_req");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		try {
			int id_emp_user = (int) session.getAttribute("id_emp_user");
			Date currDate = new Date();
			if(!dt_req.equals(""))
			{
				dt_req = dateFormatNeeded.format(userDateFormat.parse(dt_req));
			}
			if(dt_frm.equals(""))
			{
				dt_frm = "1990-01-01";
			}
			else
			{
				dt_frm = dateFormatNeeded.format(userDateFormat.parse(dt_frm));
			}
			if(dt_to.equals(""))
			{
				dt_to = dateFormatNeeded.format(currDate);
			}
			else
			{
				dt_to = dateFormatNeeded.format(userDateFormat.parse(dt_to));
			}
			con = Common.GetConnection(request);

			switch (action) {
			case "Modify":
            	//jobj = CheckRequestedDate(dt_req,id_ind);	
				int t=0;
				try
				{
					String id_req_quot="",dt_req_quot="",inq_tendr_num="",time_of_sub="";
					
					String dt_end_quot="";
					stmt =con.createStatement();
					stmt2 = con.createStatement();
					String id_req[] = request.getParameterValues("id_req");	
					String id_ind_asst[] = request.getParameterValues("id_ind_asst");
					String id_prod[] = request.getParameterValues("id_prod");
					String qty[] = request.getParameterValues("qty");
					String remarks[] = request.getParameterValues("remarks");
					String id_ven[] = request.getParameterValues("id_ven");
					//String id_apprv = request.getParameter("id_apprv");
					String id_apprv ="No";
					if(request.getParameter("dt_req_quot") !=null){
						dt_req_quot =request.getParameter("dt_req_quot");
						dt_req_quot = dateFormatNeeded.format(userDateFormat.parse(dt_req_quot));
					}
					if(!request.getParameter("dt_end_quot").equals("")){
						dt_end_quot =request.getParameter("dt_end_quot");
						dt_end_quot = dateFormatNeeded.format(userDateFormat.parse(dt_end_quot));
					}
					time_of_sub =request.getParameter("time_of_sub");
					inq_tendr_num=request.getParameter("inq_tendr_num");
					String remarkss =request.getParameter("remarkss");
					System.out.println("ttttt"+ remarkss);
					
					 System.out.println("pritesh");
					String quot_t_c =request.getParameter("quot_t_c");
					String no_ind =request.getParameter("no_ind");
					 System.out.println("update  P_Request_Quotation  set dt_req_quot ='"+dt_req_quot+"',dt_end_quot='"+dt_end_quot+"', time_of_sub='"+time_of_sub+"',inq_tendr_num='"+inq_tendr_num+"',remarkss='"+remarkss+"' where no_ind='"+no_ind+"'");
	    				
					stmt.executeUpdate("update  P_Request_Quotation  set dt_req_quot ='"+dt_req_quot+"',dt_end_quot='"+dt_end_quot+"', time_of_sub='"+time_of_sub+"',inq_tendr_num='"+inq_tendr_num+"',remarkss='"+remarkss+"' where no_ind='"+no_ind+"'");
    				
        			for(int i=0;i<id_ven.length;i++)
        			{
        				//System.out.println("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_end_quot,id_apprv)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_end_quot+"',"+id_apprv+")");
    					System.out.println("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_end_quot,id_apprv,time_of_sub,inq_tendr_num,remarkss)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_end_quot+"','"+id_apprv+"','"+time_of_sub+"','"+inq_tendr_num+"','"+remarkss+"'");
        				//System.out.println("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_end_quot,id_apprv,time_of_sub,inq_tendr_num)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_end_quot+"',"+id_apprv+",'"+time_of_sub+"','"+inq_tendr_num+"')");
        				stmt.executeUpdate("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_end_quot,id_apprv,time_of_sub,inq_tendr_num,remarkss)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_end_quot+"','"+id_apprv+"','"+time_of_sub+"','"+inq_tendr_num+"','"+remarkss+"')");
        				stmt.executeUpdate("insert into P_Req_Quot_Vendor(no_ind,id_ven) values ('"+no_ind+"','"+id_ven[i]+"')");
        				/*System.out.println("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_end_quot,id_apprv)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_end_quot+"',"+id_apprv+")");
        				System.out.println("insert into P_Req_Quot_Vendor(no_ind,id_ven) values ('"+no_ind+"','"+id_ven[i]+"')");
        			*/	rs=stmt.executeQuery("select max(id_req_quot) from P_Request_Quotation");
        				if(rs.next())
        				{
        					id_req_quot=rs.getString(1);
        				}
        				
        				for(int k=0;k<id_prod.length;k++){

        					String id_grp="",id_sgrp="",asset_consumable="",description="",mfr="",id_cc="",model="",un_prc="",typ_asst="";
        					String qty1 = qty[k];
        					Double tot_prc=0.00;
        					/*rs=stmt.executeQuery("select product_id from t_product_config where product_name='"+asset_name[k]+"'");
        					if(rs.next()){
        						strProdId	=	rs.getString(1);
        					}*/
        					rs=stmt.executeQuery("select id_grp,id_sgrp,asset_consumable,mfr,model,un_prc,typ_asst,id_cc,description from P_Indent_Asset where id_ind_asst="+id_ind_asst[k]+" ");
        					System.out.println("select id_grp,id_sgrp,asset_consumable,mfr,model,un_prc,tot_prc,typ_asst,id_cc from P_Indent_Asset where id_ind_asst="+id_ind_asst[k]+" ");
        					if(rs.next())
        					{
        						id_grp			=	rs.getString(1);
        						id_sgrp			=	rs.getString(2);
        						asset_consumable=	rs.getString(3);
        						mfr				=	rs.getString(4);
        						model			=	rs.getString(5);
        						un_prc			=	rs.getString(6);
        						description		=	rs.getString("description");
        						typ_asst		=	rs.getString(7);
        						id_cc			=	rs.getString(8);
        					}
        					if (Double.parseDouble(qty1) != 0)
        					{
        						tot_prc = Double.parseDouble(un_prc) * Double.parseDouble(qty1);
        						System.out.println("insert into P_Request_Quotation_Asset(id_req_quot,no_ind,id_prod,id_grp,id_sgrp,remarks,mfr,qty,un_prc,tot_prc,model,asset_consumable,typ_asst,id_cc,description) values('"+id_req_quot+"','"+no_ind+"','"+id_prod[k]+"','"+id_grp+"','"+id_sgrp+"','"+remarks[k]+"','"+mfr+"',"+qty[k]+",'"+un_prc+"','"+tot_prc+"','"+model+"','"+asset_consumable+"','"+typ_asst+"','"+id_cc+"','"+description+"')");
        						
        						stmt2.executeUpdate("insert into P_Request_Quotation_Asset(id_req_quot,no_ind,id_prod,id_grp,id_sgrp,remarks,mfr,qty,un_prc,tot_prc,model,asset_consumable,typ_asst,id_cc,description) values('"+id_req_quot+"','"+no_ind+"','"+id_prod[k]+"','"+id_grp+"','"+id_sgrp+"','"+remarks[k]+"','"+mfr+"',"+qty[k]+",'"+un_prc+"','"+tot_prc+"','"+model+"','"+asset_consumable+"','"+typ_asst+"','"+id_cc+"','"+description+"')");
        					}
        						stmt2.executeUpdate("UPDATE P_Indent_Asset SET req_qty='"+qty[k]+"' WHERE id_ind_asst='"+id_ind_asst[k]+"'");
        						//stmt2.executeUpdate("UPDATE P_Indent_Asset SET req_qty='"+qty[k]+"',description='"+description[k]+"' WHERE id_ind_asst='"+id_ind_asst[k]+"'");
        						
        						/*	System.out.println("UPDATE P_Indent_Asset SET req_qty='"+qty[k]+"',description='"+description[k]+"' WHERE id_ind_asst='"+id_ind_asst[k]+"'");
        				*/
        				}
        				
        				
        				
        				
        			}
        			stmt.executeUpdate("UPDATE P_Create_Indent SET st_ind='Yes' WHERE no_ind='"+no_ind+"'");
        			t=1;
        			
				}
				catch(Exception e)
				{
					t=0;
					System.out.println("Error "+e.toString());	
				}
				
				
    			jobj.put("data",t);
				
				break;

			
                
			case "Display":
				jobj = DisplayQuotation(id, dt_frm, dt_to, id_emp_user,word,User_Type,id_id_ind,dt_rate_cont_valid,for_rate);
				break;
			case "ModifyDisplay":
				jobj = DisplayQuotationModify(id, dt_frm, dt_to, id_emp_user,word,User_Type);
				break;
		
			case "CheckDate":
            	jobj = CheckRequestedDate(dt_req,id_ind);	
                break;
			case "Save":
				//jobj = SaveQuotation(map, id_emp_user, no_ind, id_req);
				String id_req_quot="",dt_req_quot="",inq_tendr_num="",time_of_sub="",for_rate_cont="",dt_rate_valid="";
				int j=0;
				try
				{
					String dt_end_quot="";
					String dt_start_quot="";
					stmt =con.createStatement();
					stmt2 = con.createStatement();
					String id_req[] = request.getParameterValues("id_req");	
					String id_ind_asst[] = request.getParameterValues("id_ind_asst");
					String id_prod[] = request.getParameterValues("id_prod");
					String qty[] = request.getParameterValues("qty");
					String remarks[] = request.getParameterValues("remarks");
					String id_ven[] = request.getParameterValues("id_ven");
					
					//String id_apprv ="No";
					if(request.getParameter("dt_req_quot") !=null){
						dt_req_quot =request.getParameter("dt_req_quot");
						dt_req_quot = dateFormatNeeded.format(userDateFormat.parse(dt_req_quot));
					}
					if(!request.getParameter("dt_end_quot").equals("")){
						dt_end_quot =request.getParameter("dt_end_quot");
						dt_end_quot = dateFormatNeeded.format(userDateFormat.parse(dt_end_quot));
					}
					if(!request.getParameter("dt_start_quot").equals("")){
						dt_start_quot =request.getParameter("dt_start_quot");
						dt_start_quot = dateFormatNeeded.format(userDateFormat.parse(dt_start_quot));
					}
					time_of_sub =request.getParameter("time_of_sub");
					inq_tendr_num=request.getParameter("inq_tendr_num");
					String remarkss =request.getParameter("remarkss");
					System.out.println("ttttt"+ remarkss);
					
					
					String quot_t_c =request.getParameter("quot_t_c");
					String no_ind =request.getParameter("no_ind");
					for_rate_cont =request.getParameter("for_rate_cont");
					dt_rate_valid=request.getParameter("dt_rate_valid");
					String id_apprv = "0";
					
        			for(int i=0;i<id_ven.length;i++)
        			{
        				//System.out.println("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_end_quot,id_apprv)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_end_quot+"',"+id_apprv+")");
    					System.out.println("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_start_quot,dt_end_quot,id_apprv,time_of_sub,inq_tendr_num,remarkss)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_start_quot+"','"+dt_end_quot+"','"+id_apprv+"','"+time_of_sub+"','"+inq_tendr_num+"','"+remarkss+"')");
            			//System.out.println("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_end_quot,id_apprv,time_of_sub,inq_tendr_num)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_end_quot+"',"+id_apprv+",'"+time_of_sub+"','"+inq_tendr_num+"')");
        				stmt.executeUpdate("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_start_quot,dt_end_quot,id_apprv,time_of_sub,inq_tendr_num,remarkss)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_start_quot+"','"+dt_end_quot+"','"+id_apprv+"','"+time_of_sub+"','"+inq_tendr_num+"','"+remarkss+"')");
        				stmt.executeUpdate("insert into P_Req_Quot_Vendor(no_ind,id_ven) values ('"+no_ind+"','"+id_ven[i]+"')");
        				/*System.out.println("insert into P_Request_Quotation(no_ind,quot_t_c,add_by,id_ven,dt_req_quot,dt_end_quot,id_apprv)values('"+no_ind+"','"+quot_t_c+"','"+id_emp_user+"','"+id_ven[i]+"','"+dt_req_quot+"','"+dt_end_quot+"',"+id_apprv+")");
        				System.out.println("insert into P_Req_Quot_Vendor(no_ind,id_ven) values ('"+no_ind+"','"+id_ven[i]+"')");
        			*/	rs=stmt.executeQuery("select max(id_req_quot) from P_Request_Quotation");
        				if(rs.next())
        				{
        					id_req_quot=rs.getString(1);
        				}
        				
        				for(int k=0;k<id_prod.length;k++){

        					String id_grp="",id_sgrp="",asset_consumable="",description="",mfr="",id_cc="",model="",un_prc="",typ_asst="";
        					String qty1 = qty[k];
        					Double tot_prc=0.00;
        					/*rs=stmt.executeQuery("select product_id from t_product_config where product_name='"+asset_name[k]+"'");
        					if(rs.next()){
        						strProdId	=	rs.getString(1);
        					}*/
        					rs=stmt.executeQuery("select id_grp,id_sgrp,asset_consumable,mfr,model,un_prc,typ_asst,description from P_Indent_Asset where id_ind_asst="+id_ind_asst[k]+" ");
        					System.out.println("select id_grp,id_sgrp,asset_consumable,mfr,model,un_prc,tot_prc,typ_asst from P_Indent_Asset where id_ind_asst="+id_ind_asst[k]+" ");
        					if(rs.next())
        					{
        						id_grp			=	rs.getString(1);
        						id_sgrp			=	rs.getString(2);
        						asset_consumable=	rs.getString(3);
        						mfr				=	rs.getString(4);
        						model			=	rs.getString(5);
        						un_prc			=	rs.getString(6);
        						description		=	"";
        						typ_asst		=	rs.getString(7);
        						
        					}
        					if (Double.parseDouble(qty1) != 0)
        					{
        						tot_prc = Double.parseDouble(un_prc) * Double.parseDouble(qty1);
        						System.out.println("insert into P_Request_Quotation_Asset(id_req_quot,no_ind,id_prod,id_grp,id_sgrp,remarks,mfr,qty,un_prc,tot_prc,model,asset_consumable,typ_asst,description) values('"+id_req_quot+"','"+no_ind+"','"+id_prod[k]+"','"+id_grp+"','"+id_sgrp+"','"+remarks[k]+"','"+mfr+"',"+qty[k]+",'"+un_prc+"','"+tot_prc+"','"+model+"','"+asset_consumable+"','"+typ_asst+"','"+description+"')");
        						
        						stmt2.executeUpdate("insert into P_Request_Quotation_Asset(id_req_quot,no_ind,id_prod,id_grp,id_sgrp,remarks,mfr,qty,un_prc,tot_prc,model,asset_consumable,typ_asst,description) values('"+id_req_quot+"','"+no_ind+"','"+id_prod[k]+"','"+id_grp+"','"+id_sgrp+"','"+remarks[k]+"','"+mfr+"',"+qty[k]+",'"+un_prc+"','"+tot_prc+"','"+model+"','"+asset_consumable+"','"+typ_asst+"','"+description+"')");
        					}
        						stmt2.executeUpdate("UPDATE P_Indent_Asset SET req_qty='"+qty[k]+"' WHERE id_ind_asst='"+id_ind_asst[k]+"'");
        						//stmt2.executeUpdate("UPDATE P_Indent_Asset SET req_qty='"+qty[k]+"',description='"+description[k]+"' WHERE id_ind_asst='"+id_ind_asst[k]+"'");
        						
        						/*	System.out.println("UPDATE P_Indent_Asset SET req_qty='"+qty[k]+"',description='"+description[k]+"' WHERE id_ind_asst='"+id_ind_asst[k]+"'");
        				*/
        				}
        				
        			}
        			System.out.println("UPDATE P_Create_Indent SET st_ind='Yes' WHERE no_ind='"+no_ind+"'");
        			stmt.executeUpdate("UPDATE P_Create_Indent SET st_ind='Yes' WHERE no_ind='"+no_ind+"'");
        			
        			
        			// Mail Trigger......
        			
        			String replyMailId="",name="",ccMailId="";
                	String mailSql ="select id_emp,nm_emp,no_ind from M_Emp_User emp,P_Request_Quotation r where emp.id_emp_user=r.add_by and id_req_quot='"+id_req_quot+"'";
                	System.out.println(mailSql);
                	
                	DtoCommon dtcm = new DtoCommon();
        			rs = dtcm.GetResultSet(mailSql,  request);
        			if(rs.next())
        			{
        				replyMailId = rs.getString(1);
        				name = rs.getString(2);
        				no_ind = rs.getString(3);
        				ccMailId = replyMailId;
        			}
        			
        			mailSql ="select distinct(v.ct_mailid),nm_ven from M_Vendor v,P_Request_Quotation r where  no_ind='"+no_ind+"' and r.id_ven=v.id_ven ";
        			System.out.println(mailSql);
        			List<String> recipients = dtcm.ReturnListData(mailSql,  request);
        			
        			String link = dtcm.ReturnParticularMessage("link");
        			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
        			String mailSubject = dtcm.ReturnParticularMessage("mailSubjectRFQ");
        			
        			String mailBody = "<b> Hello Vendor</b>,<br><br><br>"+
        						  "Your got the RFQ  <b>("+no_ind+")</b> Please Fill and Submit it.<br>"+
        						  "<br><br><br>"+link+""+
    							  "<p>"+footerMsg+"</p>";
        		
        			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
        			j=1;
		      
				}
				catch(Exception e)
				{
					j=0;
					System.out.println("Error "+e.toString());	
				}
				
				
    			jobj.put("data",j);
				
				break;

			}

			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());

		} catch (Exception e) {
			System.out.println("Error in Create_Indent.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public JSONObject CheckRequestedDate(String dt_req,String id_ind)
	{
		JSONObject json=new JSONObject();
		
		String query = "select to_char(to_date(dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_ind from P_Create_Indent where dt_ind >'"+dt_req+"' and id_ind='"+id_ind+"'";
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				
				json.put("data",0);
				json.put("dt_ind",rs.getString(1));
			}
			else
			{
				json.put("data",1);
				
			}
		}
			
		catch (Exception e)
		{
			
			System.out.println(e.toString());
		}
		return json;
	}

	public JSONObject DisplayQuotation(String no_ind, String dt_frm, String dt_to,int id_emp_user, String word,String User_Type,String id_id_ind,String dt_rate_cont_valid ,String for_rate) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		
		String sql6 = "";
		ResultSet rs6 = null;
		PreparedStatement ps6 = null;

		try {
			Date currDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (dt_frm.equals("")) {
				dt_frm = "1990-01-01";
			}

			if (dt_to.equals("")) {
				dt_to = sdf.format(currDate);
			}
			String tempSql ="";
			if(!word.isEmpty())
			 tempSql =" and (REGEXP_LIKE(no_ind,'"+word+"','i') or REGEXP_LIKE(dt_ind,'"+word+"','i') or REGEXP_LIKE(nm_emp,'"+word+"','i') )";
			System.out.println(for_rate);			
if(no_ind.equals("0"))
{
	if(for_rate.equals("0"))
	{
		
	if(User_Type.equals("SUPER") || User_Type.equals("PAPPRV") ){
		
			sql = "select ci.*,(replace(convert(NVARCHAR, dt_ind, 103), ' ', '-')) as  dtInd,nm_emp from P_Create_Indent ci,M_Emp_User emp where st_ind='No'  and emp.id_emp_user=ci.ind_add_by   "+tempSql+"  order by ci.dt_ind DESC";
}
	else{
		sql = "select ci.*,(replace(convert(NVARCHAR, dt_ind, 103), ' ', '-')) as  dtInd,nm_emp from P_Create_Indent ci,M_Emp_User emp where st_ind='No'  and emp.id_emp_user=ci.ind_add_by  "+tempSql+"  order by ci.dt_ind DESC";

	}
	}
	else{
		
		if(User_Type.equals("SUPER") || User_Type.equals("PAPPRV") ){
			
				sql = "select ci.*,(replace(convert(NVARCHAR, dt_ind, 103), ' ', '-')) as dtInd,nm_emp from P_Create_Indent ci,M_Emp_User emp where st_ind='No'  and emp.id_emp_user=ci.ind_add_by    "+tempSql+"  order by ci.dt_ind DESC";
	}
		else{
			sql = "select ci.*,(replace(convert(NVARCHAR, dt_ind, 103), ' ', '-')) as  dtInd,nm_emp from P_Create_Indent ci,M_Emp_User emp where st_ind='No'  and emp.id_emp_user=ci.ind_add_by   "+tempSql+"  order by ci.dt_ind DESC";

		}
	}
}
else
{
	if(dt_rate_cont_valid.equals("1")){
		sql = "select ci.*,(replace(convert(NVARCHAR, dt_ind, 103), ' ', '-'))  as dtInd,nm_assetdiv,nm_s_assetdiv,nm_model,cd_prod,ia.mfr,ia.description,ia.qty,ia.id_req,ia.id_ind_asst,ia.id_prod,uom from P_Create_Indent ci,P_Indent_Asset ia,M_Asset_Div ad,M_Subasset_Div sad,m_model pc "+
				" where ci.no_ind=ia.no_ind and pc.id_grp=ad.id_assetdiv and pc.id_sgrp=sad.id_s_assetdiv and ia.id_prod=pc.id_model "+
				" and ci.no_ind='"+no_ind+"' order by ID_IND_ASST";	
	
	}
	else{
		sql = "select ci.*,(replace(convert(NVARCHAR, dt_ind, 103), ' ', '-')) as dtInd,nm_assetdiv,nm_s_assetdiv,nm_model,cd_model,ia.mfr,ia.description,ia.qty,ia.id_req,ia.id_ind_asst,ia.id_prod from P_Create_Indent ci,P_Indent_Asset ia,M_Asset_Div ad,M_Subasset_Div sad,m_model pc "+
				" where ci.no_ind=ia.no_ind and pc.id_assetdiv=ad.id_assetdiv and pc.id_s_assetdiv=sad.id_s_assetdiv and ia.id_prod=pc.id_model "+
				" and ci.id_ind='"+id_id_ind+"' order by ID_IND_ASST";	
	
	}
}
System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			while (rs.next()) {
				String requestnum="",reqby="";
				
				
				JSONObject jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i).toLowerCase();
					jobj2.put(name, rs.getString(name));
					
						
				}
				 sql6="select distinct(no_req),nm_emp from P_Create_Indent ci, P_Indent_Asset ia,P_Request r,M_Emp_User emp where ci.no_ind=ia.no_ind and ia.id_req=r.id_req and r.req_by=emp.id_emp_user and ci.no_ind='"+rs.getString("no_ind")+"'";  
				  System.out.println(sql6);
				  ps6=con.prepareStatement(sql6);
					rs6=ps6.executeQuery();
					while(rs6.next())
				    {
						if(requestnum.isEmpty()) {
							requestnum=rs6.getString("no_req");
						reqby=rs6.getString("nm_emp");
						}else {
							requestnum +=", "+rs6.getString("no_req");
							reqby +=", "+rs6.getString("nm_emp");
						}
				    }
					jobj2.put("reqby", reqby);
					jobj2.put("requestnum", requestnum);
				jarr.put(jobj2);
				//System.out.println(jarr);
				
				
			}
			
		
			jobj.put("data", jarr);
			
			
			/*sql="select t_and_c from M_Term_Condition where quo_tc='yes'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				jobj.put("t_and_c", rs.getString(1));
			}
			else
			{
				jobj.put("t_and_c", "-");
			}*/
				
				
		} catch (Exception e) {
			System.out.println("sql error in Create_Indent."+e.toString());
		}

		return jobj;

	}

	public JSONObject DisplayQuotationModify(String no_ind, String dt_frm, String dt_to,int id_emp_user, String word ,String User_Type) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		try {
			Date currDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (dt_frm.equals("")) {
				dt_frm = "1990-01-01";
			}

			if (dt_to.equals("")) {
				dt_to = sdf.format(currDate);
			}
			String tempSql ="";
			if(!word.isEmpty())
			 tempSql =" and (REGEXP_LIKE(no_ind,'"+word+"','i') or REGEXP_LIKE(dt_ind,'"+word+"','i') or REGEXP_LIKE(nm_emp,'"+word+"','i') )";
						
if(no_ind.equals("0"))
{
	if(User_Type.equals("SUPER") || User_Type.equals("PAPPRV") ){
			sql = "select distinct no_ind,to_char(to_date(dt_req_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_req_quot,INQ_TENDR_NUM,nm_emp from P_REQUEST_QUOTATION pq,M_EMP_USER emp where pq.ADD_BY=emp.ID_EMP_USER and pq.ID_APPRV='No' "+tempSql+" order by dt_req_quot desc ";
	}
	else{
		sql = "select distinct no_ind,to_char(to_date(dt_req_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_req_quot,INQ_TENDR_NUM,nm_emp from P_REQUEST_QUOTATION pq,M_EMP_USER emp where pq.ADD_BY=emp.ID_EMP_USER and pq.ID_APPRV='No' "+tempSql+"  order by dt_req_quot desc ";

	}
	}
else
{
	sql = "select ci.*,to_char(to_date(ci.dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY') as dtInd,nm_assetdiv,nm_s_assetdiv,nm_prod,cd_prod,ia.mfr,ia.description,ia.qty,ia.id_req,ia.id_ind_asst,ia.id_prod,uom from P_Create_Indent ci,P_Indent_Asset ia,M_Asset_Div ad,M_Subasset_Div sad,M_Prod_Cart pc "+
			" where ci.no_ind=ia.no_ind and pc.id_grp=ad.id_assetdiv and pc.id_sgrp=sad.id_s_assetdiv and ia.id_prod=pc.id_prod "+
			" and ci.no_ind='"+no_ind+"' order by ci.dt_ind DESC";	
}
System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			while (rs.next()) {
				JSONObject jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i).toLowerCase();
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}
			String dt_req_quot="",inq_tendr_num="",time_of_sub="",remarkss="",dt_end_quot="";
			if(!no_ind.equals("0"))
			{
						sql = "select distinct remarkss,to_char(to_date(dt_end_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_end_quot, no_ind,to_char(to_date(dt_req_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_req_quot,time_of_sub,inq_tendr_num,nm_emp from P_REQUEST_QUOTATION pq,M_EMP_USER emp where pq.ADD_BY=emp.ID_EMP_USER and pq.ID_APPRV='No' and no_ind='"+no_ind+"'";
						System.out.println(sql);
						ps = con.prepareStatement(sql);
						rs1 = ps.executeQuery();
                       if(rs1.next()){
                    	   dt_req_quot=rs1.getString("dt_req_quot");
                    	   inq_tendr_num=rs1.getString("inq_tendr_num");
                    	   time_of_sub=rs1.getString("time_of_sub");
                    	   remarkss=rs1.getString("remarkss");
                    	   dt_end_quot=rs1.getString("dt_end_quot");
	     System.out.println(rs1.getString("dt_req_quot"));
                         }
			
                       jobj.put("dt_req_quot", dt_req_quot);
                       jobj.put("inq_tendr_num", inq_tendr_num);
                       jobj.put("time_of_sub", time_of_sub);
                       jobj.put("remarkss", remarkss);
                       jobj.put("dt_end_quot", dt_end_quot);
			
			
			}
			
			jobj.put("data", jarr);
			
			
			/*sql="select t_and_c from M_Term_Condition where quo_tc='yes'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				jobj.put("t_and_c", rs.getString(1));
			}
			else
			{
				jobj.put("t_and_c", "-");
			}*/
				
				
		} catch (Exception e) {
			System.out.println("sql error in Create_Indent."+e.toString());
		}

		return jobj;

	}

}
