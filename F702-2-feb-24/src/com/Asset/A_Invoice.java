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
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;

public class A_Invoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	Connection con = null;
	Statement stmt = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String UserName = (String)session.getAttribute("UserName");
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		Enumeration elemet = request.getParameterNames();
		HashMap<String, String> map =new HashMap<String,String>();
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
	 	JSONObject json=new JSONObject();
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
		    	  paramValues=paramValues.replace("'", "''");
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		String action = "", temp1 = "", id = "0", upload_inv = "", no_inv = "", invoiceId = "", dt_to = "", dt_frm = "",
				value = "", ColName = "", id_inv_m = "00";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("upload_inv") != null) {
			upload_inv = request.getParameter("upload_inv");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("id_inv_m") != null) {
			id_inv_m = request.getParameter("id_inv_m");
		}
		if (request.getParameter("no_inv") != null) {
			no_inv = request.getParameter("no_inv");
		}
		if (request.getParameter("invoiceId") != null) {
			invoiceId = request.getParameter("invoiceId");
		}
		if (request.getParameter("dt_frm") != null) {
			dt_frm = request.getParameter("dt_frm");
		}
		if (request.getParameter("dt_to") != null) {
			dt_to = request.getParameter("dt_to");
		}
		if (request.getParameter("value") != null) {
			value = request.getParameter("value");
		}
		if (request.getParameter("ColName") != null) {
			ColName = request.getParameter("ColName");
		}
		try {
			Date currDate = new Date();
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
			String tempQuery = "";
			int id_emp_user = (int) session.getAttribute("id_emp_user");
			String id_dept = (String) session.getAttribute("DeptId");
			String UserType = (String) session.getAttribute("UserType");
			String FlrId = (String) session.getAttribute("FlrId");
			if (!UserType.equals("SUPER"))
				if (!FlrId.equals("")) {
					String id_flr1[] = FlrId.split(",");
					for (int i = 0; i < id_flr1.length; i++) {
						if (tempQuery.equals("")) {
							tempQuery = " and (id_flr = " + id_flr1[i] + "";
						} else {
							tempQuery += " or id_flr = " + id_flr1[i] + "";
						}
					}
					tempQuery += ")";
				}
			con = Common.GetConnection(request);
			switch (action) {
			case "Save":
				String colName = "";
				int j = 0, id_inv_m1 = 0;
				String sql = "select id_inv_m from A_Invoice_Master where no_inv = '" + no_inv + "' ";
				System.out.println(sql);
				try {
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					if (!rs.next()) {
						rs = Common.GetColumns("A_Invoice_Master", request);
						while (rs.next()) {
							if (rs.getString(1) != "id_inv_m") {
								if (map.containsKey(rs.getString(1))) {
									if (colName.equals("")) {
										colName += rs.getString(1);
										value += "'" + map.get(rs.getString(1)) + "'";
									} else {
										colName += "," + rs.getString(1);
										value += ", '" + map.get(rs.getString(1)) + "'";
									}
								}
							}
						}
						sql = "insert into A_Invoice_Master(" + colName
								+ ",add_by,dept_id,type_inv) values(" + value + "," + id_emp_user
								+ "," + id_dept + ",'Direct')";
						System.out.println(sql);
						stmt = con.createStatement();
						stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
						rs1 = stmt.getGeneratedKeys();
						while (rs1.next()) {
							id_inv_m1 = rs1.getInt(1);
						}
						String id_grp = "", id_sgrp = "", id_prod = "",ds_pro = "",
								typ_asst = "";
						String count[] = request.getParameterValues("count");
						stmt = con.createStatement();
						j = 0;
						for (int i = 0; i < count.length; i++) {
							String cd_prod = request.getParameter("id_prod" + count[i] + "");
							if (!cd_prod.isEmpty()) {
								id_grp = request.getParameter("id_grp" + count[i] + "");
								id_sgrp = request.getParameter("id_sgrp" + count[i] + "");
								typ_asst = request.getParameter("typ_asst" + count[i] + "");
								sql = "select * from M_Model where NM_MODEL='" + cd_prod + "'";
								ps = con.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									id_prod = rs.getString("id_model");
									ds_pro = rs.getString("nm_model");
								} else {
									stmt.executeUpdate(
											"insert into M_Model (nm_model,id_s_assetdiv,id_assetdiv,cd_model,typ_asst) values('"
													+ cd_prod + "','" + id_sgrp + "','" + id_grp + "','NA','" + typ_asst
													+ "')");
									sql = "select * from M_Model where NM_MODEL='" + cd_prod + "'";
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										id_prod = rs.getString("id_model");
										ds_pro = rs.getString("nm_model");
									}
								}
								String un_prc = request.getParameter("un_prc" + count[i] + "");
								String qty = request.getParameter("qty" + count[i] + "");
								String NM_ACC_ASSET = "Asset";
								String others = request.getParameter("others" + count[i] + "");
								String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
								String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
								String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
								String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
								String buyback = request.getParameter("buyback" + count[i] + "");
								String gr_tot = request.getParameter("gr_tot" + count[i] + "");
								System.out.println(
										"insert into A_INVOICE(model,ds_pro,id_dept,created_by,config,st_config,rem_qty,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inv_m,id_model,id_grp,id_sgrp,typ_asst,NM_ACC_ASSET,upload_inv) "
												+ "values('" + ds_pro + "','" + ds_pro + "','" + id_dept + "','"
												+ id_emp_user + "','Yes','Yes'," + qty + "," + qty + "," + un_prc + ","
												+ others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + ","
												+ tax_val2 + "," + buyback + "," + gr_tot + ",'" + id_inv_m1 + "','"
												+ id_prod + "','" + id_grp + "','" + id_sgrp + "','" + typ_asst + "','"
												+ NM_ACC_ASSET + "','" + upload_inv + "')");
								stmt.executeUpdate(
										"insert into A_INVOICE(model,ds_pro,id_dept,created_by,config,st_config,rem_qty,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inv_m,id_model,id_grp,id_sgrp,typ_asst,NM_ACC_ASSET,upload_inv) "
												+ "values('" + ds_pro + "','" + ds_pro + "','" + id_dept + "','"
												+ id_emp_user + "','Yes','Yes'," + qty + "," + qty + "," + un_prc + ","
												+ others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + ","
												+ tax_val2 + "," + buyback + "," + gr_tot + ",'" + id_inv_m1 + "','"
												+ id_prod + "','" + id_grp + "','" + id_sgrp + "','" + typ_asst + "','"
												+ NM_ACC_ASSET + "','" + upload_inv + "')");
							}
							j = 1;
						}
					} else {
						System.out.println("will be duplicate invoice");
						j = 2;
					}
				} catch (Exception e) {
					System.out.println("Error in A_Invoice" + e);
				}
				jobj.put("data", j);
				break;
			case "Display":
				jobj = DisplayInvoice(id, no_inv, invoiceId, dt_frm, dt_to, id_dept, UserType, temp1, tempQuery);
				break;
			case "Edit":
				jobj = DisplayInvoice(id, no_inv, invoiceId, dt_frm, dt_to, id_dept, UserType, temp1, tempQuery);
				break;
			case "Update":
				String col_Val = "";
				int k = 0;
				try {
					stmt = con.createStatement();
					rs = Common.GetColumns("A_Invoice_Master", request);
					while (rs.next()) {
						if (rs.getString(1) != "id_inv_m") {
							if (map.containsKey(rs.getString(1))) {
								if (col_Val.equals("")) {
									col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								} else {
									col_Val += "," + rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								}
							}
						}
					}
					String query = "update A_Invoice_Master set " + col_Val + ",edit_by=" + id_emp_user
							+ " where id_inv_m=" + id + "";
					System.out.println(query);
					try {
						PreparedStatement ps = con.prepareStatement(query);
						k = ps.executeUpdate();
						if (k > 0) {
							String id_grp = "", id_sgrp = "", id_prod = "", ds_pro = "";
							String count[] = request.getParameterValues("count");
							for (int i = 0; i < count.length; i++) {
								String cd_prod = request.getParameter("id_prod" + count[i] + "");
								if (!cd_prod.isEmpty()) {
									id_grp = request.getParameter("id_grp" + count[i] + "");
									id_sgrp = request.getParameter("id_sgrp" + count[i] + "");
									sql = "select * from M_Model where NM_MODEL='" + cd_prod + "'";
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										id_prod = rs.getString("id_model");
										ds_pro = rs.getString("nm_model");
									} else {
										stmt.executeUpdate(
												"insert into M_Model (nm_model,id_s_assetdiv,id_assetdiv,cd_model) values('"
														+ cd_prod + "','" + id_sgrp + "','" + id_grp + "','NA')");
										sql = "select * from M_Model where NM_MODEL='" + cd_prod + "'";
										ps = con.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											id_prod = rs.getString("id_model");
											ds_pro = rs.getString("nm_model");
										}
									}
									String typ_asst = request.getParameter("typ_asst" + count[i] + "");
									String un_prc = request.getParameter("un_prc" + count[i] + "");
									String qty = request.getParameter("qty" + count[i] + "");
									String NM_ACC_ASSET = "Asset";
									String others = request.getParameter("others" + count[i] + "");
									String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
									String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
									String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
									String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
									String buyback = request.getParameter("buyback" + count[i] + "");
									String gr_tot = request.getParameter("gr_tot" + count[i] + "");
									String id_inv = request.getParameter("id_inv" + count[i] + "");
									System.out.println("update A_Invoice set  model='" + ds_pro + "' ,ds_pro= '"
											+ ds_pro + "',rem_qty='" + qty + "',qty='" + qty + "',un_prc='" + un_prc
											+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
											+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "',buyback='"
											+ buyback + "',gr_tot='" + gr_tot + "',id_model='" + id_prod + "',id_grp='"
											+ id_grp + "',id_sgrp='" + id_sgrp + "' where id_inv='" + id_inv + "';");
									if (!id_inv.equals("")) {
										stmt.executeUpdate("update A_Invoice set model='" + ds_pro + "' ,ds_pro= '"
												+ ds_pro + "',rem_qty='" + qty + "',qty='" + qty + "',un_prc='" + un_prc
												+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='"
												+ id_tax2 + "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2
												+ "',buyback='" + buyback + "',gr_tot='" + gr_tot + "',id_model='"
												+ id_prod + "',id_grp='" + id_grp + "',id_sgrp='" + id_sgrp
												+ "' where id_inv='" + id_inv + "';");
									} else {
										System.out.println(
												"insert into A_INVOICE(model,ds_pro,id_dept,created_by,config,st_config,typ_asst,rem_qty,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inv_m,id_model,id_grp,id_sgrp,NM_ACC_ASSET,upload_inv) "
														+ "values('" + ds_pro + "','" + ds_pro + "','" + id_dept + "','"
														+ id_emp_user + "','Yes','Yes','" + typ_asst + "','" + qty
														+ "'," + qty + "," + un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id + "','" + id_prod + "','" + id_grp
														+ "','" + id_sgrp + "','" + NM_ACC_ASSET + "','" + upload_inv
														+ "')");
										stmt.executeUpdate(
												"insert into A_INVOICE(model,ds_pro,id_dept,created_by,config,st_config,typ_asst,rem_qty,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inv_m,id_model,id_grp,id_sgrp,NM_ACC_ASSET,upload_inv) "
														+ "values('" + ds_pro + "','" + ds_pro + "','" + id_dept + "','"
														+ id_emp_user + "','Yes','Yes','" + typ_asst + "','" + qty
														+ "'," + qty + "," + un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id + "','" + id_prod + "','" + id_grp
														+ "','" + id_sgrp + "','" + NM_ACC_ASSET + "','" + upload_inv
														+ "')");
									}
								}
								k = 1;
							}
						}
						jobj.put("data", k);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					System.out.println("Some error in A_Invoice.");
				}
				break;
			case "Delete":
				jobj = DeleteInvoice(id, id_inv_m);
				break;
			case "DeleteAsset":
				jobj = DeleteAsset(id, id_inv_m);
				break;
			case "CheckExitsVal":
				jobj = CheckExitsVal(value, ColName);
				break;
			}
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
		} catch (Exception e) {
			System.out.println("Error in A_Invoice.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public JSONObject CheckExitsVal(String value, String ColName) {
		JSONObject json = new JSONObject();
		String query = "select id_inv_m from A_Invoice_Master where " + ColName + " = '" + value + "'";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				json.put("data", 1);
			} else {
				json.put("data", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public JSONObject DeleteAsset(String id, String id_inv_m) {
		JSONObject json = new JSONObject();
		int j = 0;
		try {
			if (!id_inv_m.equals("0")) {
				String query = "select COUNT(id_inv) from A_Invoice where id_inv_m = " + id_inv_m + " ";
				ps = con.prepareStatement(query);
				rs = ps.executeQuery();
				if (rs.next()) {
					int rowCount = rs.getInt(1);
					if (rowCount > 1) {
						query = "delete A_Invoice where id_inv = " + id + "";
						ps = con.prepareStatement(query);
						j = ps.executeUpdate();
						if (j > 0) {
							j = 1;
						}
					} else {
						j = 2;
					}
				}
			} else {
				String query = "select id_inv_m from A_Invoice where id_inv = " + id + "";
				ps = con.prepareStatement(query);
				rs = ps.executeQuery();
				if (rs.next()) {
					id_inv_m = rs.getString(1);
				}
				query = "delete A_Invoice where id_inv = " + id + "";
				ps = con.prepareStatement(query);
				j = ps.executeUpdate();
				if (j > 0) {
					query = "delete A_Invoice_master where id_inv_m = " + id_inv_m + "";
					ps = con.prepareStatement(query);
					j = ps.executeUpdate();
					if (j > 0) {
						j = 1;
					}
				}
			}
			json.put("data", j);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public JSONObject DeleteInvoice(String id, String id_inv_m) {
		JSONObject json = new JSONObject();
		int j = 0;
		try {
			if (!id_inv_m.equals("0")) {
				String query = "select COUNT(id_inv) from A_Invoice where id_inv_m = " + id + " and status_store = 0";
				ps = con.prepareStatement(query);
				rs = ps.executeQuery();
				if (rs.next()) {
					int a = rs.getInt(1);
					if (a > 1) {
						query = "delete A_Invoice where id_inv_m = " + id + "";
						PreparedStatement ps = con.prepareStatement(query);
						j = ps.executeUpdate();
						if (j > 0) {
							query = "delete A_Invoice_master where id_inv_m = " + id + "";
							ps = con.prepareStatement(query);
							j = ps.executeUpdate();
							if (j > 0) {
								j = 1;
							}
						}
					} else {
						j = 2;
					}
				}
			} else {
				String query = "delete A_Invoice where id_inv_m = " + id + "";
				PreparedStatement ps = con.prepareStatement(query);
				j = ps.executeUpdate();
				if (j > 0) {
					query = "delete A_Invoice_master where id_inv_m = " + id + "";
					ps = con.prepareStatement(query);
					j = ps.executeUpdate();
					if (j > 0) {
						j = 1;
					}
				}
			}
			json.put("data", j);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public JSONObject UpdateInvoice(HashMap<String, String> map, String id, String no_asset, int id_emp_user,
			HttpServletRequest request) {
		String col_Val = "";
		int j = 0;
		JSONObject json = new JSONObject();
		try {
			rs = Common.GetColumns("A_Invoice_Master", request);
			while (rs.next()) {
				if (rs.getString(1) != "id_inv_m") {
					if (map.containsKey(rs.getString(1))) {
						if (col_Val.equals("")) {
							col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
						} else {
							col_Val += "," + rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
						}
					}
				}
			}
			String query = "update A_Invoice_Master set " + col_Val + ",edit_by=" + id_emp_user + " where id_inv_m="
					+ id + "";
			try {
				PreparedStatement ps = con.prepareStatement(query);
				j = ps.executeUpdate();
				if (j > 0) {
					j = 0;
					col_Val = "";
					rs = Common.GetColumns("A_Invoice", request);
					while (rs.next()) {
						if (rs.getString(1) != "id_inv") {
							if (map.containsKey(rs.getString(1))) {
								if (col_Val.equals("")) {
									col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								} else {
									col_Val += "," + rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								}
							}
						}
					}
					query = "update A_Invoice set " + col_Val + " where id_inv=" + no_asset + "";
					ps = con.prepareStatement(query);
					j = ps.executeUpdate();
					if (j > 0) {
						json.put("data", id);
					} else {
						json.put("data", j);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Some error in A_Invoice.");
		}
		return json;
	}

	public JSONObject DisplayInvoice(String id, String id_inv_m, String no_asset, String dt_frm, String dt_to,
			String id_dept, String UserType, String temp1, String tempQuery) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			if (!no_asset.equals("")) {
				sql = "select * from A_Invoice inv where id_inv = " + no_asset + " and status_store=0";
			}
			if (id_inv_m.equals("") && no_asset.equals("")) {
				if (!UserType.equals("SUPER"))
					sql = "select distinct inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,no_po,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtPo,nm_ven,inv_m.invoice_file from A_Invoice_Master inv_m , M_Vendor v where inv_m.id_ven=v.id_ven and "
							+ " status_store = 0  and rec_status_grn = 0 and dt_inv >=(replace(convert(NVARCHAR, '"
							+ dt_frm + "', 106), ' ', '-')) and " + "dt_inv <=(replace(convert(NVARCHAR, '" + dt_to
							+ "', 106), ' ', '-'))  and  type_inv='Direct' ";
				else
					sql = "select distinct inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,no_po,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtPo,nm_ven,inv_m.invoice_file from A_Invoice_Master inv_m , M_Vendor v where inv_m.id_ven=v.id_ven and "
							+ " status_store = 0 and  type_inv='Direct'  and rec_status_grn = 0 and dt_inv >=(replace(convert(NVARCHAR, '"
							+ dt_frm + "', 106), ' ', '-')) and " + "dt_inv <=(replace(convert(NVARCHAR, '" + dt_to
							+ "', 106), ' ', '-')) ";
			} else if (!id_inv_m.equals("")) {
				sql = "select id_inv_m,id_inv,qty,un_prc,nm_acc_asset,tt_un_prc,tt,mfr,id_grp,ds_pro,id_model from A_Invoice inv where id_inv_m = "
						+ id_inv_m + " and status_store=0";
			}
			if (!id.equals("0")) {
				sql = "select  inv.*,id_flr ,(replace(convert(NVARCHAR, dt_boe, 103), ' ', '-')) as dtBoe,(replace(convert(NVARCHAR, po_dt, 103), ' ', '-')) as podt,inv_m.id_inv_m,inv_m.no_dc,inv_m.id_ven,nm_ven,no_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,no_po,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_dc, 103), ' ', '-')) as dtdc,no_grn,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGRN,id_curr,id_loc,id_subl,id_building,inv_m.id_dept as id_dept1,bd,no_boe,dt_boe,de_dt_bd,dt_exp,inv_m.id_div,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,inv_m.invoice_file from A_Invoice_Master inv_m , M_Vendor v,A_Invoice inv where "
						+ "inv_m.id_ven=v.id_ven and inv_m.id_inv_m=inv.id_inv_m and inv_m.id_inv_m = " + id
						+ " and inv_m.status_store = 0";
			}
			System.out.println(sql);
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
			System.out.println("sql error in A_Invoice.");
		}
		return jobj;
	}

	public JSONObject AddInvoice(HashMap<String, String> map, String no_inv, int id_emp_user, String id_dept, String id,
			HttpServletRequest request) {
		String colName = "", value = "";
		int j = 0, id_inv_m = 0;
		JSONObject json = new JSONObject();
		String sql = "select id_inv_m from A_Invoice_Master where no_inv = '" + no_inv + "' and id_inv_m = " + id + " ";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (!rs.next()) {
				rs = Common.GetColumns("A_Invoice_Master", request);
				while (rs.next()) {
					if (rs.getString(1) != "id_inv_m") {
						if (map.containsKey(rs.getString(1))) {
							if (colName.equals("")) {
								colName += rs.getString(1);
								value += "'" + map.get(rs.getString(1)) + "'";
							} else {
								colName += "," + rs.getString(1);
								value += ", '" + map.get(rs.getString(1)) + "'";
							}
						}
					}
				}
				sql = "insert into A_Invoice_Master(" + colName + ",add_by,dept_id) values(" + value + "," + id_emp_user
						+ "," + id_dept + ")";
				stmt = con.createStatement();
				stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				rs1 = stmt.getGeneratedKeys();
				while (rs1.next()) {
					id_inv_m = rs1.getInt(1);
				}
			} else {
				id_inv_m = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("Error in A_Invoice");
		}
		try {
			colName = "";
			value = "";
			rs = Common.GetColumns("A_Invoice", request);
			while (rs.next()) {
				if (rs.getString(1) != "id_inv") {
					if (map.containsKey(rs.getString(1))) {
						if (colName.equals("")) {
							colName += rs.getString(1);
							value += "'" + map.get(rs.getString(1)) + "'";
						} else {
							colName += "," + rs.getString(1);
							value += ", '" + map.get(rs.getString(1)) + "'";
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Some error in A_Invoice.");
		}
		String query = "insert into A_Invoice(" + colName + ",id_inv_m,id_dept) values(" + value + "," + id_inv_m + ","
				+ id_dept + ")";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			j = ps.executeUpdate();
			if (j > 0) {
				j = 1;
			}
			json.put("data", id_inv_m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
