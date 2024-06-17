package com.Purchase;

import java.io.IOException;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;

import dto.Common.DtoCommon;

public class Finalize_Vendor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	Connection con = null;
	Statement stmt = null;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String logged = (String) session.getAttribute("log_name");
		DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
		Enumeration elemet = request.getParameterNames();

		String paramName = "";
		String paramValues = "";
		try {

			while (elemet.hasMoreElements()) {

				paramName = (String) elemet.nextElement();
				String[] parts = paramName.split("_");
				paramValues = request.getParameter(paramName);
				if (parts[0].equals("dt") && !paramValues.equals("")) {
					paramValues = request.getParameter(paramName);

					Date d = userDateFormat.parse(paramValues);
					map.put(paramName, dateFormatNeeded.format(d));
				} else {
					paramValues = request.getParameter(paramName);
					map.put(paramName, paramValues);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		String action = "", word = "", st_rate_cont_for_po = "", remarks = "", id = "0", dt_to = "", dt_recv = "", id_quot = "", dt_frm = "", status_approv = "", no_ind = "", dt_approv = "", acceptQuotNo = "", rate_cont = "", dt_rate_cont_valid = "";
		if (request.getParameter("remarks") != null) {
			remarks = request.getParameter("remarks");
		}
		if (request.getParameter("st_rate_cont_for_po") != null) {
			st_rate_cont_for_po = request.getParameter("st_rate_cont_for_po");
		}
		if (request.getParameter("rate_cont") != null) {
			rate_cont = request.getParameter("rate_cont");
		}
		if (request.getParameter("dt_rate_cont_valid") != null) {
			dt_rate_cont_valid = request.getParameter("dt_rate_cont_valid");
		}
		if (request.getParameter("searchWord") != null) {
			word = request.getParameter("searchWord");
		}
		if (request.getParameter("dt_recv") != null) {
			dt_recv = request.getParameter("dt_recv");
		}
		if (request.getParameter("id_quot") != null) {
			id_quot = request.getParameter("id_quot");
		}
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("dt_approv") != null) {
			dt_approv = request.getParameter("dt_approv");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("acceptQuotNo") != null) {
			acceptQuotNo = request.getParameter("acceptQuotNo");
		}

		if (request.getParameter("dt_frm") != null) {
			dt_frm = request.getParameter("dt_frm");
		}
		if (request.getParameter("dt_to") != null) {
			dt_to = request.getParameter("dt_to");
		}
		if (request.getParameter("status_approv") != null) {
			status_approv = request.getParameter("status_approv");

		}
		if (request.getParameter("no_ind") != null) {
			no_ind = request.getParameter("no_ind");
		}
		String countID[] = request.getParameterValues("rejectQuotNo");

		String temp = "";
		try {
			Date currDate = new Date();

			if (!dt_recv.equals("")) {
				dt_recv = dateFormatNeeded
						.format(userDateFormat.parse(dt_recv));
			}
			if (!dt_approv.equals("")) {
				dt_approv = dateFormatNeeded.format(userDateFormat
						.parse(dt_approv));
			}
			if (!dt_rate_cont_valid.equals("")) {
				dt_rate_cont_valid = dateFormatNeeded.format(userDateFormat
						.parse(dt_rate_cont_valid));
			}
			if (dt_frm.equals("")) {
				dt_frm = "1990-01-01";
			} else {
				dt_frm = dateFormatNeeded.format(userDateFormat.parse(dt_frm));
			}
			if (dt_to.equals("")) {
				dt_to = dateFormatNeeded.format(currDate);
			} else {
				dt_to = dateFormatNeeded.format(userDateFormat.parse(dt_to));
			}

			if (!dt_to.equals("")) {
				temp = " and dt_rec_quot <= '" + dt_to + "'";
			}
			if (!dt_frm.equals("")) {
				temp = " and dt_rec_quot >= '" + dt_frm + "'";
			}

			if (!dt_frm.equals("") && !dt_to.equals("")) {
				temp = " and dt_rec_quot >= '" + dt_frm
						+ "' and dt_rec_quot <= '" + dt_to + "'";
			}

			int id_emp_user = (int) session.getAttribute("id_emp_user");
			String UserType = (String) session.getAttribute("UserType");
			con = Common.GetConnection(request);

			switch (action) {
			
			case "DisplayIvanApproval":
				jobj = DisplayQuotationForIvanApproval(temp, id_emp_user, UserType,
						word);
				break;
			case "Display":
				jobj = DisplayQuotationForApproval(temp, id_emp_user, UserType,
						word);
				break;
			case "Displayevaluation":
				jobj = DisplayQuotationForApprovalevalation(temp, id_emp_user, UserType,
						word);
				break;

			case "Update":
				try {
					String count[] = request.getParameterValues("place");
					stmt = con.createStatement();
					int j = 0;
					status_approv = "Accepted";
					Set<String> hash_Set = new HashSet<String>();
					String sncno_fnl = "", no_arc = "", no_format = "";
					int snc_no = 0, arc = 0;
					String PoDynId = "", pritesh_iso_num = "";
					String year = "", id_quot_asst1 = "";
					System.out.println(count.length);
					con = Common.GetConnection(request);
					for (int i = 0; i < count.length; i++) {

						String id_quot_asst = request
								.getParameter("id_quot_asset" + count[i] + "");
						String split_qty = request.getParameter("split_qty"
								+ count[i] + "");
						String qtysp = request.getParameter("qtysp" + count[i]
								+ "");
						String nev_prc = request.getParameter("nev_prc"
								+ count[i] + "");

						// id_quot =
						// request.getParameter("id_quot"+count[i]+"");
						String id_ven_selected = request
								.getParameter("id_ven_selected" + count[i] + "");
						String ven_select_remrk = request
								.getParameter("ven_select_remrk" + count[i]
										+ "");
						String id_prod = request.getParameter("id_prod"
								+ count[i] + "");
						// budget calculation.................
						String id_grp = "", id_sgrp = "", sqlbud = "";
						if (split_qty.equals("0")) {
							id_ven_selected = "0";
						}
						System.out
								.println("update P_Quotation_Asset set nev_prc='"
										+ nev_prc
										+ "',split_qty='"
										+ split_qty
										+ "', id_ven_selected='"
										+ id_ven_selected
										+ "',ven_select_remrk='"
										+ ven_select_remrk
										+ "' where id_quot_asst = "
										+ id_quot_asst + " ");

						stmt.executeUpdate("update P_Quotation_Asset set nev_prc='"
								+ nev_prc
								+ "',split_qty='"
								+ split_qty
								+ "', id_ven_selected='"
								+ id_ven_selected
								+ "',ven_select_remrk='"
								+ ven_select_remrk
								+ "' where id_quot_asst = "
								+ id_quot_asst
								+ " ");
					}

					String no_ven[] = request.getParameterValues("no_ven");
					System.out.println(no_ven.length);
					for (int k = 0; k < no_ven.length; k++) {
						String idquot = request.getParameter("idquot"+ count[k] + "");
						String noind = request.getParameter("noind" + count[k]+ "");
						
						j = 1;
						
						String query = "update P_Quotation set st_quot_flag =1 ,finance_approv_reject='Waiting',ivan_approv_reject='Waiting' where id_quot='"+ idquot + "'";
						System.out.println(query);
						ps = con.prepareStatement(query);
						j = ps.executeUpdate();

						if (j > 0) {
							j = 0;
							query = "update P_Req_Quot_Vendor set st_rec_quot ='Yes' where no_ind='"+ noind + "'";
							System.out.println(query);
							ps = con.prepareStatement(query);
							j = ps.executeUpdate();
							if (j > 0) {
								j = 1;
							}
						}
						if (j > 0) {
							j = 0;
							query = "update  P_Request_Quotation  set id_apprv1='Yes',finance_approv_reject='Waiting',ivan_approv_reject='Waiting' where no_ind='"
									+ noind + "'";
							System.out.println(query);
							ps = con.prepareStatement(query);
							j = ps.executeUpdate();
							if (j > 0) {
								j=1;
							}
						}
						
					}

					// stmt.executeUpdate("update  P_Request_Quotation  set id_apprv='Yes' where no_ind='"+no_ind+"'");

					jobj.put("data", j);
				} catch (Exception e) {
					System.out.println("Some error in quot." + e.toString());
				}
				break;
			case "approvalFinancialIvan":
				try 
				{
					int j=0;
					String id_ven = request.getParameter("id_ven");
					
					System.out.println(id_quot);
					
					String typeApprovalandreject = request.getParameter("typeApprovalandreject");
					String query ="";
					String no_indnew=request.getParameter("no_ind");
					//
					if(typeApprovalandreject.equals("Approvefinance")  ) {
					 query = "update  P_Quotation  set finance_approv_reject='Accept' where no_ind='"
								+ no_indnew + "'";
					 ps = con.prepareStatement(query);
						ps.executeUpdate();
						System.out.println(query);
					}
                    if(typeApprovalandreject.equals("Approveivan")) {
                    	 query = "update  P_Quotation  set ivan_approv_reject='Accept' where no_ind='"
  								+ no_indnew + "'";
                    	 System.out.println(query);
                    	 ps = con.prepareStatement(query);
     					ps.executeUpdate();
					}
                     if(typeApprovalandreject.equals("Rejectfinance")) {
                    	 query = "update  P_Quotation  set finance_approv_reject='Reject' ,st_quot_flag =0 , st_quot ='No'  ,  st_negotiation='0',  approv_nego='0' where no_ind='"
 								+ no_indnew + "'";
                    	 System.out.println(query);
                    	 ps = con.prepareStatement(query);
     					ps.executeUpdate();
     					
     					  
						/*
						 * query = "update  P_request_Quotation  set st_quot_flag =0  where no_ind='" +
						 * no_indnew + "'"; ps = con.prepareStatement(query); ps.executeUpdate();
						 */
					}
                     if( typeApprovalandreject.equals("Rejectivan")) {
 					
                    	 query = "update  P_Quotation  set ivan_approv_reject='Reject' ,st_quot_flag =0 , st_quot ='No'  ,  st_negotiation='0',  approv_nego='0' where no_ind='"
   								+ no_indnew + "'";
                    	 System.out.println(query);
                    	 ps = con.prepareStatement(query);
     					ps.executeUpdate();
     					
						/*
						 * query = "update  P_request_Quotation  set st_quot_flag =0  where no_ind='" +
						 * no_indnew + "'"; ps = con.prepareStatement(query); ps.executeUpdate();
						 */
 					}
                     

				     		
					j=1;
        			jobj.put("data", j);
				}
				catch (Exception e) {
					System.out.println("Some error in quot." + e.toString());
				}
				break;
			case "RejectVendor":
				try 
				{
					String id_ven = request.getParameter("id_ven");
					String no_quot = request.getParameter("no_quot");
					String query = "update  P_Quotation  set reject_ven='1' where id_quot='"
							+ id_quot + "'";
					
					ps = con.prepareStatement(query);
					ps.executeUpdate();
					 
					int j=0;
				      		
					j=1;
        			jobj.put("data", j);
				}
				
				catch (Exception e) {
					
					System.out.println("Some error in quot." + e.toString());
					
				}
				break;
				
			case "approvalrazorpay":
				try 
				{
					String id_ven = request.getParameter("id_ven");
					String no_quot = request.getParameter("no_quot");
					String query = "update  P_Quotation  set approv_nego='1' where no_ind='"
							+ no_ind + "'";
					System.out.println(query);
					ps = con.prepareStatement(query);
					ps.executeUpdate();
					 
					int j=0;
					/*
					 * String replyMailId="",name="",ccMailId=""; String mailSql
					 * ="select id_emp,nm_emp from M_Emp_User emp where emp.id_emp_user="
					 * +id_emp_user+" "; System.out.println(mailSql);
					 * 
					 * DtoCommon dtcm = new DtoCommon(); rs = dtcm.GetResultSet(mailSql);
					 * if(rs.next()) { replyMailId = rs.getString(1); name = rs.getString(2);
					 * ccMailId = replyMailId; }
					 * 
					 * mailSql
					 * ="select distinct(v.ct_mailid),nm_ven from M_Vendor v where v.id_ven="+id_ven
					 * +""; System.out.println(mailSql); List<String> recipients =
					 * dtcm.ReturnListData(mailSql);
					 * 
					 * String link = dtcm.ReturnParticularMessage("linkNego"); String footerMsg =
					 * dtcm.ReturnParticularMessage("footerMsgNego"); String mailSubject =
					 * dtcm.ReturnParticularMessage("mailSubjectNego");
					 * 
					 * String mailBody = "<b> Hello Vendor</b>,<br><br><br>"+
					 * "Please Re-Submit the RFQ  <b>("+no_quot+")</b>.<br>"+
					 * "<br><br><br>"+link+""+ "<p>"+footerMsg+"</p>";
					 * 
					 * //Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
					 */        		
					j=1;
        			jobj.put("data", j);
				}
				catch (Exception e) {
					System.out.println("Some error in quot." + e.toString());
				}
				break;
			case "Negotiation":
				try 
				{
					String id_ven = request.getParameter("id_ven");
					String no_quot = request.getParameter("no_quot");
					String query = "update  P_Quotation  set st_negotiation='1',nego_by_user='"+id_emp_user+"' where id_quot='"
							+ id_quot + "'";
					System.out.println(query);
					ps = con.prepareStatement(query);
					ps.executeUpdate();
					String mailSql ="select nego_no from P_Quotation emp where id_quot="+id_quot+" ";
                	System.out.println(mailSql);
                	String nego_no="";
                	DtoCommon dtcm = new DtoCommon();
        			rs = dtcm.GetResultSet(mailSql,  request);
        			if(rs.next())
        			{
        				nego_no = rs.getString(1);
        				
        			}
        			
					/*
					 * query = "update  Negotiation_History  set nego_by='"
					 * +id_emp_user+"'  where no_nego_ven='" + nego_no + "'";
					 * System.out.println(query); ps = con.prepareStatement(query);
					 * ps.executeUpdate();
					 */
					
					 
					int j=0;
					  String replyMailId="",name="",ccMailId="";
                	 mailSql ="select id_emp,nm_emp from M_Emp_User emp where emp.id_emp_user="+id_emp_user+" ";
                	System.out.println(mailSql);
                	
                	 dtcm = new DtoCommon();
        			rs = dtcm.GetResultSet(mailSql,  request);
        			if(rs.next())
        			{
        				replyMailId = rs.getString(1);
        				name = rs.getString(2);
        				ccMailId = replyMailId;
        			}
        			
        			mailSql ="select distinct(v.ct_mailid),nm_ven from M_Vendor v where v.id_ven="+id_ven+"";
        			System.out.println(mailSql);
        			List<String> recipients = dtcm.ReturnListData(mailSql,  request);
        			
        			String link = dtcm.ReturnParticularMessage("linkNego");
        			String footerMsg = dtcm.ReturnParticularMessage("footerMsgNego");
        			String mailSubject = dtcm.ReturnParticularMessage("mailSubjectNego");
        			
        			String mailBody = "<b> Hello Vendor</b>,<br><br><br>"+
        						  "Please Re-Submit the RFQ  <b>("+no_quot+")</b>.<br>"+
        						  "<br><br><br>"+link+""+
    							  "<p>"+footerMsg+"</p>";
        		
        			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
        			j=1;
        			jobj.put("data", j);
				}
				catch (Exception e) {
					System.out.println("Some error in quot." + e.toString());
				}
				break;
				
				
			case "GetNegotiationDetails":
				String idquotarr=request.getParameter("idquotarr");
				jobj = GetNegotiationdetails(idquotarr);
				break;
			case "EditIvan":
				jobj = QuotationDetailsivan(no_ind);
				break;
			case "Edit":
				jobj = QuotationDetails(no_ind);
				break;
			case "CheckDate":
				jobj = CheckApprovRejectDate(dt_recv, acceptQuotNo);
				break;

			case "ViewDisplay":
				jobj = ViewDisplayQuotationForApproval(temp, word);
				break;

			case "ViewEdit":
				jobj = ViewQuotationDetails(no_ind);
				break;
			}

			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());

		} catch (Exception e) {
			System.out.println("Error in Approval_Quotation.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public JSONObject CheckApprovRejectDate(String dt_recv, String id_quot) {
		JSONObject json = new JSONObject();

		String query = "select to_char(to_date(dt_rec_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_rec_quot  from P_Quotation where dt_rec_quot >'"
				+ dt_recv + "' and id_quot='" + id_quot + "'";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {

				json.put("data", 0);
				json.put("dt_rec_quot", rs.getString(1));
			} else {
				json.put("data", 1);

			}
		}

		catch (Exception e) {

			e.printStackTrace();
		}
		return json;
	}
	public JSONObject QuotationDetailsivan(String no_ind) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();

		try {
			
			String id_quot="";
			String sql ="select id_quot from p_quotation where no_ind='"+no_ind+"'";
			System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				
				id_quot=rs.getString(1);
				
			}

			 sql = "select finance_approv_reject,ivan_approv_reject,reject_ven,st_negotiation,q.no_ind,qa.*,(replace(convert(NVARCHAR, q.dt_rec_quot, 103), ' ', '-'))  as dt_reqqt , "
					+ "  nm_assetdiv,nm_s_assetdiv,nm_model,cd_model,nm_ven,v.id_ven,cd_ven,no_quot,t_c_quot,tot,t.nm_tax  as nm_tax1,tt.nm_tax as nm_tax2,nm_upload_file  "
					+ "  from M_Vendor v, P_Quotation q,P_Quotation_Asset qa left join M_TAX tt on tt.ID_TAX=qa.id_tax2,M_Asset_Div ad, "
					+ " M_Subasset_Div sad,m_model pc,M_Tax t  where t.id_tax=qa.id_tax1 and q.id_quot=qa.id_quot and  "
					+ "  qa.id_grp=ad.id_assetdiv and qa.id_sgrp=sad.id_s_assetdiv and qa.id_prod=pc.id_model  "
					+ "  and q.id_ven=v.id_ven and no_ind = '"
					+ no_ind
					+ "'  order by qa.id_quot_asst ASC";
			System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			JSONObject jobj2 = new JSONObject();
			while (rs.next()) {
				jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i).toLowerCase();
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}
			
			 sql = "select * from Negotiation_History where id_quot='"+id_quot+ "' order by id_quot_asst ASC";
				System.out.println(sql);
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();

				ResultSetMetaData metaData1 = rs.getMetaData();
				int columns1 = metaData1.getColumnCount();
				JSONObject jobj21 = new JSONObject();
				while (rs.next()) {
					jobj21 = new JSONObject();
					for (int i = 1; i <= columns1; i++) {
						String name = metaData1.getColumnName(i).toLowerCase();
						jobj21.put(name, rs.getString(name));
					}
					jarr1.put(jobj21);

				}
				jobj.put("nego", jarr1);
			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in Approval_Quotation."
					+ e.toString());
		}

		return jobj;

	}
	public JSONObject GetNegotiationdetails(String idquotarr) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();

		String splitquot[]=idquotarr.split(",");
		
	
		
		
		try {
			for(int k=0;k<splitquot.length;k++) 
			{
				
				String sql = "select cd_ven,nm_emp,no_quot,no_nego_ven, (replace(convert(NVARCHAR, nego_date, 103)\r\n" + 
						", ' ', '-'))  as nego_date ,(replace(convert(NVARCHAR, q.dt_rec_quot, 103)\r\n" + 
						", ' ', '-'))  as dt_quot  ,nm_ven,nm_model,t1.nm_tax as t1taxname, t2.nm_tax as t2taxname\r\n" + 
						"	,nh.qty,nh.un_prc,nh.others,nh.tax_val1,nh.tax_val2,nh.buyback,nh.tot_prc from Negotiation_History nh,P_Quotation q,P_Quotation_Asset qa,M_Vendor v,M_Model ml\r\n" + 
						"	,m_tax t1  , m_tax t2,M_Emp_User emp where nh.id_quot=q.id_quot and qa.id_quot_asst=nh.id_quot_asst and v.id_ven=q.id_ven \r\n" + 
						"	and qa.id_prod=ml.id_model and t1.id_tax=nh.id_tax1 and t2.id_tax=nh.id_tax2  and nh.nego_by=emp.id_emp_user and nh.id_quot='"+splitquot[k]+"'";
				System.out.println(sql);
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();

				ResultSetMetaData metaData = rs.getMetaData();
				int columns = metaData.getColumnCount();
				JSONObject jobj2 = new JSONObject();
				while (rs.next()) {
					jobj2 = new JSONObject();
					for (int i = 1; i <= columns; i++) {
						String name = metaData.getColumnName(i).toLowerCase();
						jobj2.put(name, rs.getString(name));
					}
					jarr.put(jobj2);

				}
				
			}
		

			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in Approval_Quotation."
					+ e.toString());
		}

		return jobj;

	}
	
	public JSONObject QuotationDetails(String no_ind) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();

		try {

			String sql = "select reject_ven,st_negotiation,q.no_ind,qa.*,(replace(convert(NVARCHAR, q.dt_rec_quot, 103), ' ', '-'))  as dt_reqqt , "
					+ "  nm_assetdiv,nm_s_assetdiv,nm_model,cd_model,nm_ven,v.id_ven,cd_ven,no_quot,t_c_quot,tot,t.nm_tax  as nm_tax1,tt.nm_tax as nm_tax2,nm_upload_file  "
					+ "  from M_Vendor v, P_Quotation q,P_Quotation_Asset qa left join M_TAX tt on tt.ID_TAX=qa.id_tax2,M_Asset_Div ad, "
					+ " M_Subasset_Div sad,m_model pc,M_Tax t  where t.id_tax=qa.id_tax1 and q.id_quot=qa.id_quot and  "
					+ "  qa.id_grp=ad.id_assetdiv and qa.id_sgrp=sad.id_s_assetdiv and qa.id_prod=pc.id_model  "
					+ "  and q.id_ven=v.id_ven and no_ind = '"
					+ no_ind
					+ "' and st_quot='No' order by qa.id_quot_asst ASC";
			System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			JSONObject jobj2 = new JSONObject();
			while (rs.next()) {
				jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i).toLowerCase();
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}

			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in Approval_Quotation."
					+ e.toString());
		}

		return jobj;

	}

	public JSONObject UpdateQuotationForAcceptReject(String dt_approv,
			String id, String status_approv, int id_emp_user, String countID[],
			String no_ind, String remarks,HttpServletRequest request) {

		int j = 0;
		String query = "", id_quot = "";
		JSONObject json = new JSONObject();
		if (status_approv.equals("Accepted"))
			query = "update P_Quotation set dt_approv ='" + dt_approv
					+ "',st_quot ='" + status_approv + "',approv_by="
					+ id_emp_user + ",remarks='" + remarks + "' where id_quot="
					+ id + "";
		else {
			for (int i = 0; i < countID.length; i++) {
				if (id_quot.equals("")) {
					id_quot = countID[i];
				} else {
					id_quot += "," + countID[i];
				}
			}
			query = "update P_Quotation set dt_approv ='" + dt_approv
					+ "',st_quot ='" + status_approv + "',approv_by="
					+ id_emp_user + " where id_quot in (" + id_quot + ")";
		}
		System.out.println(query);
		try {
			PreparedStatement ps = con.prepareStatement(query);
			j = ps.executeUpdate();
			if (j > 0) {
				j = 1;
				if (status_approv.equals("Accepted")) {
					j = 0;
					query = "update P_Quotation set st_quot_flag =1 where no_ind='"
							+ no_ind + "'";
					ps = con.prepareStatement(query);
					j = ps.executeUpdate();
					if (j > 0) {
						j = 0;
						query = "update P_Req_Quot_Vendor set st_rec_quot ='Yes' where no_ind='"
								+ no_ind + "'";
						ps = con.prepareStatement(query);
						j = ps.executeUpdate();
						if (j > 0) {
							j = 1;
						}
					}

				}

			}

			// Mail Trigger......
			String replyMailId = "", name = "";
			String mailSql = "select id_emp,nm_emp from M_Emp_User emp,P_Request_Quotation rq where emp.id_emp_user=rq.add_by and no_ind='"
					+ no_ind + "'";

			DtoCommon dtcm = new DtoCommon();
			rs = dtcm.GetResultSet(mailSql,  request);
			if (rs.next()) {
				replyMailId = rs.getString(1);
				name = rs.getString(2);
			}
			mailSql = "select id_emp,nm_emp from M_Emp_User emp,P_Quotation rq where emp.id_emp_user=rq.approv_by and id_quot='"
					+ id_quot + "'";

			List<String> recipients = dtcm.ReturnListData(mailSql,  request);

			String link = dtcm.ReturnParticularMessage("link");
			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
			String mailSubject = dtcm
					.ReturnParticularMessage("purchaseReqQuotApprv");

			String mailBody = "<b> Hello " + name + "</b>,<br><br><br>"
					+ "Your Quotation has been " + status_approv
					+ " having indent number <b>(" + no_ind + ")</b>."
					+ "<br><br><br>" + link + "" + "<p>" + footerMsg + "</p>";

			// Common.MailConfiguration(mailBody, replyMailId, recipients ,
			// mailSubject);
			j = 1;

			json.put("data", j);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return json;
	}
	
	public JSONObject DisplayQuotationForIvanApproval(String temp, int id_emp_user,
			String UserType, String word) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		String tempSql = "";
		
		if(UserType.equals("FINANCE")) {
			
			if (!word.isEmpty())
				tempSql = " and (REGEXP_LIKE(ci.no_ind,'"
						+ word
						+ "','i') or REGEXP_LIKE(to_char(to_date(ci.dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY'),'"
						+ word + "','i') or REGEXP_LIKE(nm_emp,'" + word
						+ "','i') )";

			if (UserType.equals("SUPER") || UserType.equals("PAPPRV")) {
				sql = "select distinct(ci.no_ind),id_quot,no_quot,(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-'))  as dt_ind,ci.dt_ind as dtind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "
						+ " where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot  and st_quot_flag =1 and st_po='No' and st_quot ='Accepted'  ' "
						+ tempSql + " AND finance_approv_reject='Waiting'  order by dtind DESC";
			} else {
				sql = "select distinct(ci.no_ind),id_quot,no_quot,(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-'))  as dt_ind,ci.dt_ind as dtind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "
						+ " where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot  and st_quot_flag =1 and st_po='No' and st_quot ='Accepted'  "
						+ tempSql + "   AND finance_approv_reject='Waiting'  order by dtind DESC";

			}	
		}
		else {
			
			if (!word.isEmpty())
				tempSql = " and (REGEXP_LIKE(ci.no_ind,'"
						+ word
						+ "','i') or REGEXP_LIKE(to_char(to_date(ci.dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY'),'"
						+ word + "','i') or REGEXP_LIKE(nm_emp,'" + word
						+ "','i') )";

			if (UserType.equals("SUPER") || UserType.equals("PAPPRV")) {
				sql = "select distinct(ci.no_ind),id_quot,no_quot,(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-'))  as dt_ind,ci.dt_ind as dtind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "
						+ " where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot  and st_quot_flag =1 and st_po='No' and st_quot ='Accepted'  ' "
						+ tempSql + " AND  ivan_approv_reject='Waiting' order by dtind DESC";
			} else {
				sql = "select distinct(ci.no_ind),id_quot,no_quot,(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-'))  as dt_ind,ci.dt_ind as dtind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "
						+ " where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot  and st_quot_flag =1 and st_po='No' and st_quot ='Accepted'  "
						+ tempSql + "   AND  ivan_approv_reject='Waiting' order by dtind DESC";

			}
		}
		
		System.out.println(sql);
		try {
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
			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in Approval_Quotation."
					+ e.toString());
		}

		return jobj;

	}
	public JSONObject DisplayQuotationForApprovalevalation(String temp, int id_emp_user,
			String UserType, String word) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		String tempSql = "";
		if (!word.isEmpty())
			tempSql = " and (REGEXP_LIKE(ci.no_ind,'"
					+ word
					+ "','i') or REGEXP_LIKE(to_char(to_date(ci.dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY'),'"
					+ word + "','i') or REGEXP_LIKE(nm_emp,'" + word
					+ "','i') )";

		if (UserType.equals("SUPER") || UserType.equals("PAPPRV")) {
			sql = "select distinct(ci.no_ind),(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-'))  as dt_ind,ci.dt_ind as dtind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "
					+ " where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot  and st_quot_flag =0  and st_quot ='No' and  approv_nego='0' "
					+ tempSql + " order by dtind DESC";
		} else {
			sql = "select distinct(ci.no_ind),(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-'))  as dt_ind,ci.dt_ind as dtind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "
					+ " where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot  and st_quot_flag =0  and st_quot ='No' and  approv_nego='0'"
					+ tempSql + "   order by dtind DESC";

		}
		System.out.println(sql);
		try {
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
			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in Approval_Quotation."
					+ e.toString());
		}

		return jobj;

	}
	public JSONObject DisplayQuotationForApproval(String temp, int id_emp_user,
			String UserType, String word) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		String tempSql = "";
		if (!word.isEmpty())
			tempSql = " and (REGEXP_LIKE(ci.no_ind,'"
					+ word
					+ "','i') or REGEXP_LIKE(to_char(to_date(ci.dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY'),'"
					+ word + "','i') or REGEXP_LIKE(nm_emp,'" + word
					+ "','i') )";

		if (UserType.equals("SUPER") || UserType.equals("PAPPRV")) {
			sql = "select distinct(ci.no_ind),(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-'))  as dt_ind,ci.dt_ind as dtind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "
					+ " where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot  and st_quot_flag =0  and st_quot ='No' and approv_nego='1'"
					+ tempSql + " order by dtind DESC";
		} else {
			sql = "select distinct(ci.no_ind),(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-'))  as dt_ind,ci.dt_ind as dtind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "
					+ " where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot  and st_quot_flag =0  and st_quot ='No' and  approv_nego='1' "
					+ tempSql + "   order by dtind DESC";

		}
		System.out.println(sql);
		try {
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
			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in Approval_Quotation."
					+ e.toString());
		}

		return jobj;

	}

	public JSONObject ViewDisplayQuotationForApproval(String temp, String word) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		String tempSql = "";
		if (!word.isEmpty())
			tempSql = " and (REGEXP_LIKE(ci.no_ind,'" + word
					+ "','i') or REGEXP_LIKE(ci.dt_ind,'" + word
					+ "','i') or REGEXP_LIKE(nm_emp,'" + word + "','i') )";

		sql = "select distinct(ci.no_ind),(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-')) as dt_ind,nm_emp from P_Create_Indent ci, P_Quotation q ,M_Emp_User emp, P_Request_Quotation rq "
				+ " where q.no_ind=ci.no_ind and ci.ind_add_by=emp.id_emp_user and q.id_req_quot=rq.id_req_quot and st_quot_flag =1  and st_quot ='Accepted' and st_po='No' "
				+  " order by ci.no_ind DESC";
		System.out.println(sql);
		try {
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
			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in Approval_Quotation."
					+ e.toString());
		}

		return jobj;

	}

	public JSONObject ViewQuotationDetails(String no_ind) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();

		try {

			String sql = "select q.*,qa.*,(replace(convert(NVARCHAR, q.dt_bid_open, 103), ' ', '-'))  as dt_bid_open1,  (replace(convert(NVARCHAR, q.dt_rec_quot, 103), ' ', '-')) as dtRecQuot,sec.nm_section as indent_dept,nm_curr,nm_assetdiv,nm_s_assetdiv,nm_prod,cd_prod,nm_ven,cd_ven,city,state,no_quot,t_c_quot,tot,nm_tax,nm_upload_file from M_Vendor v, P_Quotation q,P_Quotation_Asset qa,M_Asset_Div ad,M_Subasset_Div sad,M_Prod_Cart pc,M_Currency c,M_Tax t "
					+ " where t.id_tax=qa.id_tax and q.id_quot=qa.id_quot and qa.id_grp=ad.id_assetdiv and qa.id_sgrp=sad.id_s_assetdiv and qa.id_prod=pc.id_prod "
					+ " and q.id_curr=c.id_curr and qa.id_ven_selected=v.id_ven and no_ind = '"
					+ no_ind
					+ "' and st_quot='Accepted' and st_po='No' and  order by qa.id_quot ASC";
			System.out.println(sql);

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			JSONObject jobj2 = new JSONObject();
			while (rs.next()) {
				jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i).toLowerCase();
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}
			String mem1 = "", mem2 = "", mem3 = "", mem4 = "";
			sql = "select nm_emp from M_Emp_User emp, P_Quotation q where emp.id_emp_user = q.mem1 and no_ind = '"
					+ no_ind + "'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				mem1 = rs.getString(1);

			sql = "select nm_emp from M_Emp_User emp, P_Quotation q where emp.id_emp_user = q.mem2 and no_ind = '"
					+ no_ind + "'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				mem2 = rs.getString(1);

			sql = "select nm_emp from M_Emp_User emp, P_Quotation q where emp.id_emp_user = q.mem3 and no_ind = '"
					+ no_ind + "'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				mem3 = rs.getString(1);

			sql = "select nm_emp from M_Emp_User emp, P_Quotation q where emp.id_emp_user = q.mem4 and no_ind = '"
					+ no_ind + "'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				mem4 = rs.getString(1);

			jobj.put("data", jarr);
			jobj.put("mem1", mem1);
			jobj.put("mem2", mem2);
			jobj.put("mem3", mem3);
			jobj.put("mem4", mem4);
		} catch (Exception e) {
			System.out.println("sql error in Approval_Quotation."
					+ e.toString());
		}

		return jobj;

	}

}
