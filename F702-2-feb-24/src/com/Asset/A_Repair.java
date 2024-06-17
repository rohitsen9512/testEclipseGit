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

public class A_Repair extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	Connection con = null;
	Connection con1 = null;
	Statement stmt = null;

	Date currDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();

		HashMap<String, String> map = new HashMap<String, String>();

		Enumeration elemet = request.getParameterNames();
		HttpSession session = request.getSession();

		DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");

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
					paramValues=paramValues.replace("'", "''");
					map.put(paramName, paramValues);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		String id = "", action = "", dt_active = "", asst_stat = "";
		String id_grp = "", id_sgrp = "", id_loc = "", id_subl = "", typ_asst = "", word = "", nm_table = "", ColName = "", value = "";
		if (request.getParameter("searchWord") != null) {
			word = request.getParameter("searchWord");
		}
		if (request.getParameter("nm_table") != null) {
			nm_table = request.getParameter("nm_table");
		}
		if (request.getParameter("ColName") != null) {
			ColName = request.getParameter("ColName");
		}
		if (request.getParameter("value") != null) {
			value = request.getParameter("value");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("id_grp") != null) {
			id_grp = request.getParameter("id_grp");
		}
		if (request.getParameter("id_sgrp") != null) {
			id_sgrp = request.getParameter("id_sgrp");
		}
		if (request.getParameter("id_loc") != null) {
			id_loc = request.getParameter("id_loc");
		}
		if (request.getParameter("id_subl") != null) {
			id_subl = request.getParameter("id_subl");
		}
		if (request.getParameter("typ_asst") != null) {
			typ_asst = request.getParameter("typ_asst");
		}
		if (request.getParameter("dt_active") != null) {
			dt_active = request.getParameter("dt_active");
		}
		if (request.getParameter("asst_stat") != null) {
			asst_stat = request.getParameter("asst_stat");
		}

		try {

			int id_emp_user = (int) session.getAttribute("id_emp_user");
			String UserType = (String) session.getAttribute("UserType");
			String id_dept = (String) session.getAttribute("DeptId");

			con = Common.GetConnection(request);

			switch (action) {

			case "Display":
				String tempSql = "select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,ds_asst,serial_no,nm_ven,wh.id_loc,(replace(convert(NVARCHAR, wh.dt_alloc, 103), ' ', '-')) dt_de_allocate from A_Ware_House wh , A_Invoice_master inv , M_Vendor v,M_Model modl where (allocate = 6) and inv.id_inv_m=wh.id_inv_m and wh.id_model=modl.id_model and v.id_ven=inv.id_ven and st_trvl=0 ";
				// jobj = DisplayAssetFromStoreForEdit(temp);
				UserAccessData uad = new UserAccessData();
				jobj = uad.QueryMacker(tempSql, UserType, word, id_dept,  request);
				break;

			case "Edit":
				jobj = DataForEditFromStore(id);
				break;

			case "Update":
				jobj = UpdateDataForEditFromStore(map, id, id_emp_user,  request);
				break;
			case "Active":
				dt_active = dateFormatNeeded.format(userDateFormat
						.parse(dt_active));
				jobj = Active(id, dt_active, asst_stat , id_dept,  request);
				break;

			case "CheckExitsVal":
				jobj = Common.CheckValWhichAllReadyExit("A_Ware_House",
						ColName, value,  request);
				break;

			}

			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());

		} catch (Exception e) {
			System.out.println("Error in A_Install.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public JSONObject UpdateDataForEditFromStore(HashMap<String, String> map,
			String id, int id_emp_user,HttpServletRequest request) {
		String col_Val = "";
		int j = 0;
		JSONObject json = new JSONObject();
		try {
			rs = Common.GetColumns("A_Ware_House",  request);

			while (rs.next()) {

				if (rs.getString(1) != "id_wh") {
					if (map.containsKey(rs.getString(1))) {
						if (col_Val.equals("")) {
							col_Val += rs.getString(1) + "='"
									+ map.get(rs.getString(1)) + "'";

						} else {

							col_Val += "," + rs.getString(1) + "='"
									+ map.get(rs.getString(1)) + "'";

						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Some error in M_Asset_Div.");
		}

		String query = "update A_Ware_House set " + col_Val + ",edit_by="
				+ id_emp_user + " where id_wh=" + id + "";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			j = ps.executeUpdate();
			if (j > 0) {
				j = 1;
			}

			json.put("data", j);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return json;
	}

	public JSONObject DataForEditFromStore(String id) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";

		sql = "select *,(replace(convert(NVARCHAR, std_lease, 103), ' ', '-')) as stdLease,(replace(convert(NVARCHAR, endt_lease, 103), ' ', '-')) as endtLease,(replace(convert(NVARCHAR, dt_amc_exp, 103), ' ', '-')) as dtAmcExp,(replace(convert(NVARCHAR, dt_amc_start, 103), ' ', '-')) as dtAmcStart,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv from A_Ware_House  where id_wh = "
				+ id + " ";

		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			while (rs.next()) {
				JSONObject jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i);
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}
			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in A_Install.");
		}

		return jobj;

	}

	public JSONObject DisplayAssetFromStoreForEdit(String temp) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();

		// String
		// sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where allocate = 0 and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven";
		String sql = "";
		if (!temp.equals("")) {
			sql = "select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where (allocate = 0 or allocate = 2) and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and st_trvl=0 "
					+ temp + "";

		} else
			sql = "select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where (allocate = 0 or allocate = 2) and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and st_trvl=0";

		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			while (rs.next()) {
				JSONObject jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i);
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}
			if (jarr.length() > 0) {
				jobj.put("data", jarr);
			} else {
				jobj.put("data", 1);
			}
		} catch (Exception e) {
			System.out.println("sql error in A_Install.");
		}

		return jobj;
	}

	public JSONObject Install(String id) {
		JSONObject jobj = new JSONObject();
		JSONObject json = new JSONObject();

		int j = 0;

		String sql1 = "update A_Ware_House set allocate=5 where id_wh =" + id
				+ "";
		try {
			PreparedStatement ps = con.prepareStatement(sql1);

			j = ps.executeUpdate();

			if (j > 0) {
				json.put("data", 1);
			}
		} catch (Exception e) {
		}

		return json;

	}

	public JSONObject Active(String id, String dt_active, String asst_stat , String id_dept,HttpServletRequest request) {
		JSONObject jobj = new JSONObject();
		JSONObject json = new JSONObject();
		String sql1 = "";
		int j = 0;
		try {
			if (asst_stat.equals("2")) {
				String sql = "update A_Iut_History set dt_active='" + dt_active
						+ "' where id_wh =" + id + "";
				PreparedStatement ps1 = con.prepareStatement(sql);
				j = ps1.executeUpdate();

				sql1 = "update A_Ware_House set allocate=1,dt_active='"
						+ dt_active + "' where id_wh =" + id + "";

				// .........for mail ........
				DtoCommon dtcm = new DtoCommon();
				String replyMailId = "", name = "", assetHolderId = "", itemName = "", slNo = "", no_model = "",id_wh_dyn="";
				String mailSql = "select emp.id_emp,emp.nm_emp,sa.nm_s_assetdiv,no_model,no_mfr,wh.id_wh_dyn from M_Subasset_Div sa,A_Ware_House wh, M_Emp_User emp "
						+ " where wh.id_sgrp=sa.id_s_assetdiv and wh.to_assign=emp.id_emp_user and wh.id_wh="
						+ id + "";

				dtcm = new DtoCommon();
				rs = dtcm.GetResultSet(mailSql,  request);
				if (rs.next()) {
					replyMailId = rs.getString(1);
					name = rs.getString(2);
					itemName = rs.getString(3);
					no_model = rs.getString(4);
					slNo = rs.getString(5);
					id_wh_dyn = rs.getString(6);
				}
				List<String> recipients = new ArrayList<String>();
				// recipients.add(assetHolderId);

				mailSql = "select id_emp,id_dept,emp.id_emp_user from M_Emp_User emp,M_User_Login ul where id_dept="+id_dept+" and ul.id_emp_user=emp.id_emp_user and type_user='DEPT' and status_user=1";

				dtcm = new DtoCommon();
				rs = dtcm.GetResultSet(mailSql,  request);
				while (rs.next()) {
					recipients.add(rs.getString("id_emp"));
				}

				String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
				String mailSubject = dtcm.ReturnParticularMessage("repairActive");

				String mailBody = "<b>Hello " + name + " , </b><br><br><br>"
						+ "Your asset has been active now."
						+ "<br><br>The <b>'" + itemName
						+ "'</b> having Model No/Version <b>'" + no_model
						+ "'</b> ,Serial No <b>'" + slNo
						+ "'</b> and asset id <b>'" + id_wh_dyn + "'</b>.<br>"
						+ "<p>" + footerMsg + "</p>";

				Common.MailConfiguration(mailBody, replyMailId, recipients,
						mailSubject);
			} else {
				sql1 = "update A_Ware_House set allocate=7,to_assign=0,id_flr = 0,dt_de_allocate='"
						+ dt_active
						+ "',dt_active='"
						+ dt_active
						+ "' where id_wh =" + id + "";
				// .........for mail ........
				DtoCommon dtcm = new DtoCommon();
				String replyMailId = "", name = "", assetHolderId = "", itemName = "", slNo = "", no_model = "",id_wh_dyn="";
				String mailSql = "select emp.id_emp,emp.nm_emp,sa.nm_s_assetdiv,no_model,no_mfr,wh.id_wh_dyn from M_Subasset_Div sa,A_Ware_House wh, M_Emp_User emp "
						+ " where wh.id_sgrp=sa.id_s_assetdiv and wh.to_assign=emp.id_emp_user and wh.id_wh="
						+ id + "";

				dtcm = new DtoCommon();
				rs = dtcm.GetResultSet(mailSql,  request);
				if (rs.next()) {
					replyMailId = rs.getString(1);
					name = rs.getString(2);
					itemName = rs.getString(3);
					no_model = rs.getString(4);
					slNo = rs.getString(5);
					id_wh_dyn=rs.getString(6);
				}
				List<String> recipients = new ArrayList<String>();
				// recipients.add(assetHolderId);

				mailSql = "select id_emp,id_dept,emp.id_emp_user from M_Emp_User emp,M_User_Login ul where id_dept="+id_dept+" and ul.id_emp_user=emp.id_emp_user and type_user='DEPT' and status_user=1";


				dtcm = new DtoCommon();
				rs = dtcm.GetResultSet(mailSql,  request);
				while (rs.next()) {
					recipients.add(rs.getString("id_emp"));
				}

				String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
				String mailSubject = dtcm.ReturnParticularMessage("repairSold");

				String mailBody = "<b>Hello " + name + " , </b><br><br><br>"
						+ "Below asset has been taken back from you."
						+ "<br><br>The <b>'" + itemName
						+ "'</b> having Model No/Version <b>'" + no_model
						+ "'</b> ,Serial No <b>'" + slNo
						+ "'</b> and asset id <b>'" + id_wh_dyn + "'</b>.<br>"
						+ "<p>" + footerMsg + "</p>";

				Common.MailConfiguration(mailBody, replyMailId, recipients,
						mailSubject);
			}

			// / Mail Trigger code here....
			

			PreparedStatement ps = con.prepareStatement(sql1);
			j = ps.executeUpdate();
			if (j > 0) {
				json.put("data", 1);
			}

		} catch (Exception e) {

		}
		return json;
	}
}