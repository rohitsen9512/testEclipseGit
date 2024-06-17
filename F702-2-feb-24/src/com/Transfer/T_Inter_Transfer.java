package com.Transfer;

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

public class T_Inter_Transfer extends HttpServlet {
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
		
		DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd"); 
		HttpSession session = request.getSession();
		String paramName = "";
		String paramValues = "";
		while (elemet.hasMoreElements()) {

			paramName = (String) elemet.nextElement();
			paramValues = request.getParameter(paramName);
			map.put(paramName, paramValues);

		}

		String action = "", id = "0", word = "", req_no = "";
		String dt_tran = "", id_wh = "", tranType = "";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("id_wh") != null) {
			id_wh = request.getParameter("id_wh");
		}
		if (request.getParameter("id_cc") != null) {
			id = request.getParameter("id_cc");
		}
		if (request.getParameter("dt_tran") != null) {
			dt_tran = request.getParameter("dt_tran");
		}
		if (request.getParameter("tranType") != null) {
			tranType = request.getParameter("tranType");
		}
		if (request.getParameter("searchWord") != null) {
			word = request.getParameter("searchWord");
		}
		if (request.getParameter("req_no") != null) {
			req_no = request.getParameter("req_no");
		}
		try {
			
			
			if(!dt_tran.equals(""))
			{
				dt_tran = dateFormatNeeded.format(userDateFormat.parse(dt_tran));
			}
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
					String tempQuery2 = dtoCommon.MakeTempQuery(UserType,"wh","wh", request);
					tempSql = "select distinct(req_no),dt_req,nm_emp,type_tran from A_Ware_House wh,A_Iut_History iut,M_Emp_User emp where  "
							+ " wh.id_wh=iut.id_wh and iut_approval =2 and  acc_asst=0  and emp.id_emp_user=iut.tran_aprov_by and wh.req_by = "+id_emp_user+" "+tempQuery2+" order by req_no DESC";
							
				} else
					tempSql = "select distinct(req_no),dt_req,nm_emp,type_tran from A_Ware_House wh,A_Iut_History iut,M_Emp_User emp where  "
							+ " wh.id_wh=iut.id_wh and iut_approval =2 and   acc_asst=0  and emp.id_emp_user=iut.tran_aprov_by order by req_no DESC";
							
				
				System.out.println(tempSql);

				jobj = dtoCommon.GetDataForDisplay(tempSql,  request);

				break;

			case "Update":
				jobj = UpdateTransfer(req_no, dt_tran, id_emp_user,  request);
				break;

			case "Edit":
				jobj = TransferForEdit(id, tranType);
				break;

			case "CheckDate":
				jobj = CheckDate(dt_tran, req_no, id_wh);
				break;

			case "Preview":
				dtoCommon = new DtoCommon();
				tempSql = "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst from A_Ware_House where req_no="
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

	public JSONObject CheckDate(String dt_tran, String req_no, String id_wh) {
		JSONObject jobj = new JSONObject();

		try {
			int t = 1;
			String sql = "", id = "";
			String d = "1900-01-01";
			sql = "select id_wh from A_Ware_House where req_no=" + req_no + "";
			ps = con.prepareStatement(sql);
			rs1 = ps.executeQuery();
			while (rs1.next()) {
				id = rs1.getString(1);
				sql = "select id_grn from A_Ware_House where id_wh=" + id
						+ " and allocate=0";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					sql = "select dt_grn from G_Grn where id_grn="
							+ rs.getString(1) + " and dt_grn > '" + dt_tran
							+ "'";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next()) {
						t = 2;
						d = rs.getString(1);
					}
				} else {

					sql = "select dt_de_allocate from A_Ware_House where allocate=2 and dt_de_allocate > '"
							+ dt_tran + "' and id_wh=" + id + " ";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next()) {
						t = 3;
						d = rs.getString(1);
					}

				}
			}

			if (t == 1) {
				jobj.put("data", t);
			} else {
				jobj.put("data", t);
				jobj.put("dt_iut", d);
			}

		} catch (Exception e) {
			System.out.println("sql error in T_Inter_Transfer.");
		}

		return jobj;

	}

	public JSONObject TransferForEdit(String id, String tranType) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();

		try {

			String sql = "select ds_pro,nm_assetdiv,ds_asst,nm_loc,nm_subl,mfr "
					+ "from A_Ware_House wh , M_Asset_Div ad , M_Loc l, M_Subloc sl "
					+ "  where  id_wh = "
					+ id
					+ " and wh.id_loc=l.id_loc and wh.id_subl = sl.id_sloc "
					+ "   and wh.id_grp = ad.id_assetdiv";
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
			if (tranType.equals("Temporary")) {
				sql = "select type_tran,nm_ven from A_Iut_History wh , M_Vendor v where  id_wh = "
						+ id + " and iut_approval = 2 and wh.id_ven=v.id_ven";
			} else {
				sql = "select nm_loc as nm_loc_tran,nm_subl as nm_subl_tran,type_tran "
						+ "from A_Iut_History wh ,M_Loc l, M_Subloc sl  "
						+ "  where  id_wh = "
						+ id
						+ " and iut_approval = 2 and type_tran !='Intra' and wh.id_loc_tran=l.id_loc and wh.id_subl_tran = sl.id_sloc ";
			}
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

	public JSONObject UpdateTransfer(String req_no, String dt_tran,
			int id_emp_user,HttpServletRequest request) {
		String today = "", id = "", sql = "";
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		today = sdf.format(currDate);
		int j = 0;
		JSONObject json = new JSONObject();
		try {
			sql = "select id_wh from A_Ware_House where req_no=" + req_no + "";
			ps = con.prepareStatement(sql);
			rs2 = ps.executeQuery();
			while (rs2.next()) {
					id = rs2.getString(1);
				sql = "select id_loc_tran as id_loc , id_subl_tran as id_subl,id_div_tran,id_building_tran,id_flr_tran,id_dept_tran "
						+ "from A_Iut_History wh ,M_Loc l, M_Subloc sl ,M_Building b, M_Floor f,M_Dept d"
						+ "  where  id_wh = "+ id
						+ " and iut_approval = 2 and wh.id_loc_tran=l.id_loc and wh.id_subl_tran = sl.id_sloc  "
						+" and  wh.id_building_tran = b.id_building and wh.id_flr_tran = f.id_flr and wh.id_dept_tran=d.id_dept";
                 System.out.println(sql);
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				int id_iut = 0;

				if (rs.next()) {
					stmt = con.createStatement();
					stmt.executeUpdate("update A_Ware_House set  "
							+ "id_loc = "
							+ rs.getString(1)
							+ ", id_subl = "
							+ rs.getString(2)
						
							+ ", id_building = "
							+ rs.getString(4)
							+ ", id_flr = "
							+ rs.getString(5)
							+ ",id_dept="+rs.getString(6)+" "
							+ "  where id_wh = "
							+ id
							+ " ");

					String sql1 = "select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="
							+ id + " and iut_approval=2";
					PreparedStatement ps1 = con.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					if (rs1.next()) {
						id_iut = rs1.getInt(1);
					}
					stmt = con.createStatement();
					stmt.executeUpdate("update A_Iut_History set iut_approval=5,dt_start_tran='"
							+ dt_tran
							+ "',tran_transfer_by="
							+ id_emp_user
							+ " where id_iut=" + id_iut + " and iut_approval=2");
					j = 1;
				} else {
					String sql1 = "select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="
							+ id + " and iut_approval=2";
					PreparedStatement ps1 = con.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					if (rs1.next()) {
						id_iut = rs1.getInt(1);
					}
					stmt = con.createStatement();
					stmt.executeUpdate("update A_Iut_History set iut_approval=5,dt_start_tran='"
							+ dt_tran
							+ "',tran_transfer_by="
							+ id_emp_user
							+ " where id_iut=" + id_iut + " and iut_approval=2");
					j = 1;

				}
			}
			j=0;
			stmt = con.createStatement();
			stmt.executeUpdate("update T_Transfer_Track set tran_by="+id_emp_user+",dt_tran='"+dt_tran+"' where req_no="+req_no+"");
			j=1;
			
			
			//.........for mail ........
	    	String replyMailId="",ccEmailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="";
	    	
	    	String Sql ="select id_emp from M_Emp_User emp,M_User_Login ul where ul.id_emp_user=emp.id_emp_user ";
	    	
	    	DtoCommon dtcm = new DtoCommon();
	    	List<String> recipients = dtcm.ReturnListData(Sql,  request);
	    	rs = dtcm.GetResultSet(Sql,  request);
			if(rs.next())
			{
				replyMailId = rs.getString(1);
				recipients.remove(replyMailId);
			}
			
			//replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
			
			Sql ="select id_emp from T_Transfer_Track tt,M_Emp_User emp where tt.req_by=emp.id_emp_user and req_no="+req_no+" and ststus_emp='Working'";
			
			recipients = dtcm.ReturnEmail(Sql, recipients,  request);
			
			String link = dtcm.ReturnParticularMessage("link");
			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
			String footerMsg2 = dtcm.ReturnParticularMessage("footerMsg2");
			String mailSubject = dtcm.ReturnParticularMessage("interTrnAprv");
			
			String mailBody = "Hello <b>Logistic Team</b>,<br><br><br>"+
				 	  "<p>Executive has been raised a request for inter transfer movement having request number <b>(Request No-"+req_no+")</b>. Please approve the same.</p><br>"+
					  "<br><br><br>"+link+""+
					  "<br><br><p>"+footerMsg+"</p>"+
					  "<br><br>"+footerMsg2+"<b>";
	
		Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
		
			json.put("data", j);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return json;
	}
}
