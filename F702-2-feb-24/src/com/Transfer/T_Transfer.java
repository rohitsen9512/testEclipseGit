package com.Transfer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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

public class T_Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	PreparedStatement ps = null;
	Connection con = null;
	Statement stmt = null;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map = new HashMap<String, String>();

		Enumeration elemet = request.getParameterNames();
		HttpSession session = request.getSession();
		String paramName = "";
		String paramValues = "";
		while (elemet.hasMoreElements()) {

			paramName = (String) elemet.nextElement();
			paramValues = request.getParameter(paramName);
			map.put(paramName, paramValues);

		}

		String action = "", id = "0", word = "", dt_tran = "", req_no = "";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("dt_tran") != null) {
			dt_tran = request.getParameter("dt_tran");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("id_flr") != null) {
			id = request.getParameter("id_flr");
		}
		if (request.getParameter("id_cc") != null) {
			id = request.getParameter("id_cc");
		}
		if (request.getParameter("searchWord") != null) {
			word = request.getParameter("searchWord");
		}
		if (request.getParameter("req_no") != null) {
			req_no = request.getParameter("req_no");
		}
		try {
			int id_emp_user = (int) session.getAttribute("id_emp_user");
			String UserType = (String) session.getAttribute("UserType");
			String id_dept = (String)session.getAttribute("DeptId");
			int UserId = (int) session.getAttribute("UserId");
			con = Common.GetConnection(request);

			switch (action) {

			case "Display":
				String tempSql = "";
				String likeTempQuery = "";
				DtoCommon dtoCommon = new DtoCommon();
				if (!word.equals("")) {
					likeTempQuery = dtoCommon.MakeLikeTempQuery(word);
				}
				if (!UserType.equals("Super")) {
					String tempQuery = dtoCommon
							.MakeTempQuery(UserType,"wh","wh", request);
					tempSql = "select distinct(req_no),dt_req,nm_emp from A_Ware_House wh, A_Iut_History iut,M_Emp_User emp where  "
							+ " wh.id_wh=iut.id_wh and iut_approval=2  and  acc_asst=0 and emp.id_emp_user=iut.tran_aprov_by  "
							  + likeTempQuery + " "+tempQuery+"";
				} else
					tempSql = "select distinct(req_no),dt_req,nm_emp from A_Ware_House wh, A_Iut_History iut,M_Emp_User emp where  "
							+ " wh.id_wh=iut.id_wh and iut_approval=2  and  acc_asst=0 and emp.id_emp_user=iut.tran_aprov_by "
							+ likeTempQuery + "";

				jobj = dtoCommon.GetDataForDisplay(tempSql,  request);

				break;

			case "Update":
				jobj = UpdateTransfer(req_no, dt_tran, id_emp_user, request);
				break;

			case "Edit":
				jobj = TransferForEdit(id);
				break;
			case "checkTransferDate":
				jobj = checkTransferDate(dt_tran, req_no);
				break;
			case "Preview":
				dtoCommon = new DtoCommon();
				tempSql = "select id_wh,id_wh_dyn,ds_pro,serial_no,ds_asst from A_Ware_House where req_no="
						+ req_no + " ";
				jobj = dtoCommon.GetDataForDisplay(tempSql,  request);
				break;

			}

			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());

		} catch (Exception e) {
			System.out.println("Error in T_Transfer.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public JSONObject checkTransferDate(String dt_alloc, String req_no) {
		JSONObject jobj = new JSONObject();
		String id_wh = "", sql = "", dt_tran = "";
		int j = 1;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sdf.parse(dt_alloc);

			sql = "select id_wh from A_Ware_House where req_no=" + req_no + "";
			ps = con.prepareStatement(sql);
			rs1 = ps.executeQuery();
			while (rs1.next()) {
				id_wh = rs1.getString(1);

				sql = "select max(dt_start) from A_Iut_History where id_wh = "
						+ id_wh + "";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					if (d.before(rs.getDate(1))) {
						dt_tran = rs.getString(1);
						j = 0;
					}
				}
			}

			if (j == 0) {
				jobj.put("data", 0);
				jobj.put("dt_tran", dt_tran);
			} else
				jobj.put("data", 1);

		} catch (Exception e) {
			System.out.println("sql error in A_Install.");
		}

		return jobj;

	}

	public JSONObject TransferForEdit(String id) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();

		try {

			String sql = "select ds_pro,no_mfr as mfr,nm_assetdiv,ds_asst,nm_loc,nm_flr,nm_subl "
					+ "from A_Ware_House wh , M_Asset_Div ad , M_Loc l, M_Subloc sl ,M_Floor f "
					+ "  where  id_wh = "
					+ id
					+ " and wh.id_loc=l.id_loc and wh.id_subl = sl.id_sloc "
					+ "  and wh.id_flr = f.id_flr and wh.id_grp = ad.id_assetdiv";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			JSONObject jobj2 = new JSONObject();
			while (rs.next()) {
				jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i);
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}

			sql = "select nm_loc as nm_loc_tran,nm_flr as nm_flr_tran,nm_subl as nm_subl_tran "
					+ "from A_Iut_History wh ,M_Loc l, M_Subloc sl ,M_Floor f "
					+ "  where  id_wh = "
					+ id
					+ " and iut_approval = 2 and wh.id_loc_tran=l.id_loc and wh.id_subl_tran = sl.id_sloc "
					+ "  and wh.id_flr_tran = f.id_flr ";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			metaData = rs.getMetaData();
			columns = metaData.getColumnCount();
			jobj2 = new JSONObject();
			while (rs.next()) {
				jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i);
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}

			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in T_Accept_Reject.");
		}

		return jobj;

	}

	public JSONObject UpdateTransfer(String req_no, String dt_tran, int id_emp_user,HttpServletRequest request) {
		String today = "",sql = "",id="";
		int id_iut = 0;
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		today = sdf.format(currDate);
		int j = 0;
		JSONObject json = new JSONObject();
		try {
			sql = "select id_wh from A_Ware_House where req_no="+req_no+"";
			ps = con.prepareStatement(sql);
			rs2 = ps.executeQuery();
			while(rs2.next()){
			id = rs2.getString(1);
			 sql = "select id_loc,id_flr_tran as id_flr,id_subl, wh.to_assign,wh.allocate_by "
					+ "from A_Iut_History wh "
					+ "  where  id_wh = "
					+ id
					+ " and iut_approval = 2 ";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				stmt = con.createStatement();
				stmt.executeUpdate("update A_Ware_House set " + "id_loc = "
						+ rs.getString(1) + ",id_flr = " + rs.getString(2)
						+ ", id_subl = " + rs.getString(3) + " ,st_trvl=0"
						+ " where id_wh = " + id + " ");

				String sql1 = "select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="
						+ id + " and iut_approval=2";
				PreparedStatement ps1 = con.prepareStatement(sql1);
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					id_iut = rs1.getInt(1);
				}

				stmt.executeUpdate("update A_Iut_History set iut_approval=3,dt_start_tran='"
						+ dt_tran
						+ "',tran_transfer_by="
						+ id_emp_user
						+ " where id_iut=" + id_iut + " and iut_approval=2");

//				stmt.executeUpdate("insert into A_Iut_History (id_wh,id_loc,id_subl,id_flr, "
//						+ "dt_start,type_tran,iut_flag,to_assign,allocate_by) values ("
//						+ id
//						+ ", "
//						+ ""
//						+ rs.getString(1)
//						+ ","
//						+ rs.getString(3)
//						+ ","
//						+ rs.getString(2)
//						+ ", "
//						+ "'"
//						+ dt_tran
//						+ "','Intra',2,"
//						+ rs.getString(4)
//						+ ","
//						+ rs.getString(5) + ")");
				j = 1;
			}
			}
			j=0;
			stmt = con.createStatement();
			stmt.executeUpdate("update T_Transfer_Track set tran_by="+id_emp_user+",dt_tran='"+dt_tran+"' where req_no="+req_no+"");
			//.........for mail ........
	    	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="",nm_db="";
	    	
	    	String Sql ="select id_emp from A_Ware_House wh,M_User_Login l,A_IUT_History ih,M_Emp_User emp "+
					" where wh.id_wh=ih.id_wh and  wh.id_wh="+id+"  and emp.id_emp_user=l.id_emp_user and emp.id_emp_user=ih.tran_aprov_by  and ih.id_iut="+id_iut+" ";
	    	System.out.println(Sql);
	    	DtoCommon dtcm = new DtoCommon();
			List<String> recipients = dtcm.ReturnListData(Sql,  request);
			
		if(!recipients.isEmpty())
		{
			//replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
			
			Sql ="select id_emp from A_Ware_House wh,M_User_Login l,A_IUT_History ih,M_Emp_User emp "+
    				" where wh.id_wh=ih.id_wh and  wh.id_wh="+id+"  and emp.id_emp_user=l.id_emp_user and emp.id_emp_user=ih.tran_req_by and ih.id_iut="+id_iut+"";
			System.out.println(Sql);
			recipients = dtcm.ReturnEmail(Sql, recipients,  request);
			rs = dtcm.GetResultSet(Sql,  request);
			if(rs.next())
			{
				replyMailId = rs.getString(1);
				recipients.remove(replyMailId);
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
			String aprv = "<p>Your request for intra transfer movement having request number <b>(Request No-"+req_no+")</b> has been tranferred successfully. </p>";
			
			String link = dtcm.ReturnParticularMessage("baseUrlLink");
			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
			String footerMsg2 = dtcm.ReturnParticularMessage("footerMsg2");
			String mailSubject = dtcm.ReturnParticularMessage("intraTrnAprv");
			
			String mailBody = "Hello <b>Executive Team</b>,<br><br><br>"+
								aprv +
								"<br><br><br>"+link+""+
								 "<br>For login....<a href='"+link+""+nm_db+".html'>Click Here</a>"+
								  "<br><br><p>"+footerMsg+"</p>";
		
			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
		
			j=1;
		}
			j=1;
			json.put("data", j);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return json;
	}
}
