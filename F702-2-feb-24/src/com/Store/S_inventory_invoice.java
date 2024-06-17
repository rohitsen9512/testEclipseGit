package com.Store;

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
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.UserAccessData;



public class S_inventory_invoice extends HttpServlet {
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
		String action = "", temp1 = "", id = "0", upload_inv = "", no_inv = "", id_invn = "", dt_to = "", dt_frm = "",
				value = "", ColName = "", searchWord="",id_inventory_m = "00";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("upload_inv") != null) {
			upload_inv = request.getParameter("upload_inv");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("id_inventory_m") != null) {
			id_inventory_m = request.getParameter("id_inventory_m");
		}
		if (request.getParameter("no_inv") != null) {
			no_inv = request.getParameter("no_inv");
		}
		
		if (request.getParameter("id_invn") != null) {
			id_invn = request.getParameter("id_invn");
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
			
		}	if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
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
			//String id_dept = (String) session.getAttribute("DeptId");
			String UserType = (String) session.getAttribute("UserType");
		
			con = Common.GetConnection(request);
			UserAccessData uad = new UserAccessData();
			switch (action) {
			
			case "Save":
				String colName = "" ,sql="";
				String  id_prod = "",ds_prod = "",cd_prod ="" ,mfr="",hsn_sc_cd="",asset_prfx="",prod="",mrp="",type_grp="";
				
				int j = 0, id_inventory_m1  = 0;
				if(!no_inv.equals("NA")) {
				 sql = "select id_inventory_m from S_Inventory_Master  where no_inv = '" + no_inv + "' ";
				
				 System.out.println(sql);
				 System.out.println("has invoice ");
				try {
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					if (!rs.next()) {
						rs = Common.GetColumns("S_Inventory_Master ", request);
						while (rs.next()) {
							if (rs.getString(1) != "id_inventory_m") {
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
					String invNO=map.get("no_inv");
					String temp_no_inv=map.get("temp_no_inv");
						sql = "insert into S_Inventory_Master (" + colName
								+ ",add_by) values(" + value + "," + id_emp_user+ " )";
						System.out.println(sql);
						stmt = con.createStatement();
						stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
						rs1 = stmt.getGeneratedKeys();
						while (rs1.next()) {
							id_inventory_m1  = rs1.getInt(1);
							System.out.println(id_inventory_m1);
						}
	
								
						String count[] = request.getParameterValues("count");
						stmt = con.createStatement();
						j = 0;
						for (int i = 0; i < count.length; i++) {
							 cd_prod = request.getParameter("id_prod" + count[i] + "");
//							 ds_prod = request.getParameter("ds_pro" + count[i] + "");
//							 mfr = request.getParameter("mfr" + count[i] + "");
//							hsn_sc_cd = request.getParameter("prod_hsncd" + count[i] + "");
//							asset_prfx = request.getParameter("asset_prfx" + count[i] + "");
							// un_prc = request.getParameter("un_prc" + count[i] + "");
							if (!cd_prod.isEmpty()) {
								
								sql = "select * from M_Asset_Div  where nm_assetdiv='" + cd_prod + "'";
								ps = con.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									id_prod = rs.getString("id_assetdiv");
									prod = rs.getString("nm_assetdiv");
									ds_prod = rs.getString("ds_assetdiv");
									mfr = rs.getString("mfr_assetdiv");
									hsn_sc_cd = rs.getString("hsn_cd_assetdiv");
									asset_prfx = rs.getString("asset_prod_prefix");
									mrp = rs.getString("un_prc_assetdiv");
									type_grp = rs.getString("type_grp");
									
									
								} 
								//else {
									//stmt.executeUpdate(
										//	"insert into M_Asset_Div(nm_assetdiv,cd_assetdiv,ds_assetdiv,mfr_assetdiv,hsn_cd_assetdiv,asset_prod_prefix,un_prc_assetdiv) values('"+ cd_prod + "','NA','"+ds_prod+"','"+mfr1+"','"+hsn_sc_cd1+"','"+asset_prfx1+"','"+un_prc1+"')");
//									sql = "select * from M_Asset_Div  where nm_assetdiv='" + cd_prod + "'";
//									ps = con.prepareStatement(sql);
//									rs = ps.executeQuery();
//									if (rs.next()) {
//										id_prod = rs.getString("id_assetdiv");
//										prod = rs.getString("nm_assetdiv");
//										ds_pro = rs.getString("ds_assetdiv");
//										mfr = rs.getString("mfr_assetdiv");
//										hsn_sc_cd = rs.getString("hsn_cd_assetdiv");
//										asset_prfx = rs.getString("asset_prod_prefix");
//										mrp = rs.getString("un_prc_assetdiv");
//									}
//								}
								String un_prc = request.getParameter("un_prc" + count[i] + "");
								String qty = request.getParameter("qty" + count[i] + "");
								String qty_empty = request.getParameter("qty_empty" + count[i] + "");
								String qty_fill = request.getParameter("qty_fill" + count[i] + "");
							
								String others = request.getParameter("others" + count[i] + "");
								String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
								String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
								String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
								String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
								String buyback = request.getParameter("buyback" + count[i] + "");
								String gr_tot = request.getParameter("gr_tot" + count[i] + "");
								
					           if(type_grp.equals("TYPE-CYLINDER")) {
					       
					        	
							
									
									System.out.println(
											"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
													+ "values('" + prod + "','" + ds_prod + "','" + mfr + "','" + hsn_sc_cd + "','" + asset_prfx + "','"
													+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "" 
													+ un_prc + "," + others + "," + id_tax1 + ","
													+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
													+ "," + gr_tot + ",'" + id_inventory_m1 + "','" + id_prod + "','" + type_grp + "','"+invNO+"','"+temp_no_inv+"')");
									stmt.executeUpdate(
											"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
													+ "values('" + prod + "','" + ds_prod + "','" + mfr + "','" + hsn_sc_cd + "','" + asset_prfx + "','"
													+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "," 
													+ un_prc + "," + others + "," + id_tax1 + ","
													+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
													+ "," + gr_tot + ",'" + id_inventory_m1 + "','" + id_prod + "','" + type_grp + "','"+invNO+"','"+temp_no_inv+"')");
								
						
					           }else {
					        	   
					        	
								
								System.out.println(
										"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
												+ "values('" + prod + "','" + ds_prod + "','" + mfr + "','" + hsn_sc_cd + "','" + asset_prfx + "','"
												+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "" 
												+ un_prc + "," + others + "," + id_tax1 + ","
												+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
												+ "," + gr_tot + ",'" + id_inventory_m1 + "','" + id_prod + "','" + type_grp + "','"+invNO+"','"+temp_no_inv+"')");
								stmt.executeUpdate(
										"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
												+ "values('" + prod + "','" + ds_prod + "','" + mfr + "','" + hsn_sc_cd + "','" + asset_prfx + "','"
												+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "," 
												+ un_prc + "," + others + "," + id_tax1 + ","
												+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
												+ "," + gr_tot + ",'" + id_inventory_m1 + "','" + id_prod + "','" + type_grp + "','"+invNO+"','"+temp_no_inv+"')");
							
					           }
					           }
							j = 1;
						}
					}
				
					else {
						System.out.println("will be duplicate invoice");
						j = 2;
					}
				
				} catch (Exception e) {
					System.out.println("Error in S_Add_To_Stock" + e);
				}
				}
				else {
					 System.out.println("Temp invoice Na");
					
					 
					try {
						 
							rs = Common.GetColumns("S_Inventory_Master ", request);
							while (rs.next()) {
								if (rs.getString(1) != "id_inventory_m") {
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
							String invNO=map.get("no_inv");
							String temp_no_inv=map.get("temp_no_inv");
							sql = "insert into S_Inventory_Master (" + colName
									+ ",add_by) values(" + value + "," + id_emp_user+ " )";
							System.out.println("temp invoce"+sql);
							stmt = con.createStatement();
							stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
							rs1 = stmt.getGeneratedKeys();
							while (rs1.next()) {
								id_inventory_m1  = rs1.getInt(1);
							}
								
							String count[] = request.getParameterValues("count");
							stmt = con.createStatement();
							j = 0;
							for (int i = 0; i < count.length; i++) {
							 cd_prod = request.getParameter("id_prod" + count[i] + "");
								
//								 ds_prod = request.getParameter("ds_pro" + count[i] + "");
//								 mfr = request.getParameter("mfr" + count[i] + "");
//								hsn_sc_cd = request.getParameter("prod_hsncd" + count[i] + "");
//								 asset_prfx = request.getParameter("asset_prfx" + count[i] + "");
//								 un_prc = request.getParameter("un_prc" + count[i] + "");
								if (!cd_prod.isEmpty()) {
									
									sql = "select * from M_Asset_Div  where nm_assetdiv='" + cd_prod + "'";
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										id_prod = rs.getString("id_assetdiv");
										prod = rs.getString("nm_assetdiv");
										ds_prod = rs.getString("ds_assetdiv");
										mfr = rs.getString("mfr_assetdiv");
										hsn_sc_cd = rs.getString("hsn_cd_assetdiv");
										asset_prfx = rs.getString("asset_prod_prefix");
										mrp = rs.getString("un_prc_assetdiv");
										type_grp = rs.getString("type_grp");
									}
//									else {
//										stmt.executeUpdate(
//												"insert into M_Asset_Div(nm_assetdiv,cd_assetdiv,ds_assetdiv,mfr_assetdiv,hsn_cd_assetdiv,asset_prod_prefix,un_prc_assetdiv) values('"+ cd_prod + "','NA','"+ds_prod+"','"+mfr1+"','"+hsn_sc_cd1+"','"+asset_prfx1+"','"+un_prc1+"')");
//										sql = "select * from M_Asset_Div  where nm_assetdiv='" + cd_prod + "'";
//										ps = con.prepareStatement(sql);
//										rs = ps.executeQuery();
//										if (rs.next()) {
//											id_prod = rs.getString("id_assetdiv");
//											prod = rs.getString("nm_assetdiv");
//											ds_pro = rs.getString("ds_assetdiv");
//											mfr = rs.getString("mfr_assetdiv");
//											hsn_sc_cd = rs.getString("hsn_cd_assetdiv");
//											asset_prfx = rs.getString("asset_prod_prefix");
//											mrp = rs.getString("un_prc_assetdiv");
//										}
									//}
									
									
									String un_prc = request.getParameter("un_prc" + count[i] + "");
									String qty_empty = request.getParameter("qty_empty" + count[i] + "");
									String qty_fill = request.getParameter("qty_fill" + count[i] + "");
									String qty = request.getParameter("qty" + count[i] + "");
									//String NM_ACC_ASSET = "Asset";
									String others = request.getParameter("others" + count[i] + "");
									String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
									String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
									String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
									String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
									String buyback = request.getParameter("buyback" + count[i] + "");
									String gr_tot = request.getParameter("gr_tot" + count[i] + "");
									 if(type_grp.equals("Type-CYLINDER")) {
									       
							        		
											
											System.out.println(
													"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
															+ "values('" + prod + "','" + ds_prod + "','" + mfr + "','" + hsn_sc_cd + "','" + asset_prfx + "','"
															+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "" 
															+ un_prc + "," + others + "," + id_tax1 + ","
															+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
															+ "," + gr_tot + ",'" + id_inventory_m1 + "','" + id_prod + "','" + type_grp + "','"+invNO+"','"+temp_no_inv+"')");
											stmt.executeUpdate(
													"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
															+ "values('" + prod + "','" + ds_prod + "','" + mfr + "','" + hsn_sc_cd + "','" + asset_prfx + "','"
															+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "," 
															+ un_prc + "," + others + "," + id_tax1 + ","
															+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
															+ "," + gr_tot + ",'" + id_inventory_m1 + "','" + id_prod + "','" + type_grp + "','"+invNO+"','"+temp_no_inv+"')");
									
									
							           }else {
							        	   
							        	
										
										System.out.println(
												"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
														+ "values('" + prod + "','" + ds_prod + "','" + mfr + "','" + hsn_sc_cd + "','" + asset_prfx + "','"
														+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "," 
														+ un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id_inventory_m1 + "','" + id_prod + "','" + type_grp + "','"+invNO+"','"+temp_no_inv+"')");
										stmt.executeUpdate(
												"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
														+ "values('" + prod + "','" + ds_prod + "','" + mfr + "','" + hsn_sc_cd + "','" + asset_prfx + "','"
														+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "," 
														+ un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id_inventory_m1 + "','" + id_prod + "','" + type_grp + "','"+invNO+"','"+temp_no_inv+"')");
									
							           }
								}
								j = 1;
							}
						 
//						}
					
					} catch (Exception e) {
						System.out.println("Error in S_Add_To_Stock" + e);
					}
				}
				jobj.put("data", j);
				break;
			case "Display":
				jobj = DisplayInvoice(id, no_inv, id_invn, dt_frm, dt_to,  UserType, temp1, tempQuery,searchWord);
				break;
			case "Edit":
				jobj = DisplayInvoice(id, no_inv, id_invn, dt_frm, dt_to,  UserType, temp1, tempQuery,searchWord);
				break;
				
	        case "InvNoGeneration":
            	jobj = GenerateInvoiceNo();
            	break;
	      
			case "Update":
				String col_Val = "";
				String  id_prod1 = "",ds_prod1 = "",cd_prod1 ="" ,mfr1="",hsn_sc_cd1="",asset_prfx1="",prod1="",mrp1="",type_grp1="";
				
				int k = 0;
				try {
					stmt = con.createStatement();
					rs = Common.GetColumns("S_Inventory_Master", request);
					while (rs.next()) {
						if (rs.getString(1) != "id_inventory_m") {
							if (map.containsKey(rs.getString(1))) {
								if (col_Val.equals("")) {
									col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								} else {
									col_Val += "," + rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								}
							}
						}
					}
					String invNO=map.get("no_inv");
					String temp_no_inv=map.get("temp_no_inv");
					System.out.println("update S_Inventory_Master set " + col_Val + ",edit_by=" + id_emp_user
							+ " where id_inventory_m=" + id + "");
					String query = "update S_Inventory_Master set " + col_Val + ",edit_by=" + id_emp_user
							+ " where id_inventory_m=" + id + "";
					
					try {
						PreparedStatement ps = con.prepareStatement(query);
						k = ps.executeUpdate();
						if (k > 0) {
						
							String count[] = request.getParameterValues("count");
							for (int i = 0; i < count.length; i++) {
								 cd_prod1 = request.getParameter("id_prod" + count[i] + "");
                                 ds_prod1 = request.getParameter("ds_prod" + count[i] + "");
//								mfr = request.getParameter("mfr" + count[i] + "");
//								 hsn_sc_cd = request.getParameter("prod_hsncd" + count[i] + "");
//								 asset_prfx = request.getParameter("asset_prfx" + count[i] + "");
//								String un_prc = request.getParameter("un_prc" + count[i] + "");
								if (!cd_prod1.isEmpty()) {
									
									sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod1 + "'";
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										id_prod1 = rs.getString("id_assetdiv");
										prod1 = rs.getString("nm_assetdiv");
										ds_prod1 = rs.getString("ds_assetdiv");
										mfr1 = rs.getString("mfr_assetdiv");
										hsn_sc_cd1 = rs.getString("hsn_cd_assetdiv");
										asset_prfx1 = rs.getString("asset_prod_prefix");
										mrp1 = rs.getString("un_prc_assetdiv");
										type_grp1 = rs.getString("type_grp");
									} 
//									else {
//										stmt.executeUpdate(
//												"insert into M_Asset_Div(nm_assetdiv,cd_assetdiv,ds_assetdiv,mfr_assetdiv,hsn_cd_assetdiv,asset_prod_prefix,un_prc_assetdiv) values('"+ cd_prod + "','NA','"+ds_prod+"','"+mfr1+"','"+hsn_sc_cd1+"','"+asset_prfx1+"','"+un_prc1+"')");
//										sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod + "'";
//										ps = con.prepareStatement(sql);
//										rs = ps.executeQuery();
//										if (rs.next()) {
//											id_prod = rs.getString("id_assetdiv");
//											prod = rs.getString("nm_assetdiv");
//											ds_pro = rs.getString("ds_assetdiv");
//											mfr = rs.getString("mfr_assetdiv");
//											hsn_sc_cd = rs.getString("hsn_cd_assetdiv");
//											asset_prfx = rs.getString("asset_prod_prefix");
//											mrp = rs.getString("un_prc_assetdiv");
//										}
//									}
									
									String un_prc = request.getParameter("un_prc" + count[i] + "");
									String qty = request.getParameter("qty" + count[i] + "");
									String qty_fill = request.getParameter("qty_fill" + count[i] + "");
									String qty_empty = request.getParameter("qty_empty" + count[i] + "");
									//String NM_ACC_ASSET ="";
									String others = request.getParameter("others" + count[i] + "");
									String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
									String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
									String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
									String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
									String buyback = request.getParameter("buyback" + count[i] + "");
									String gr_tot = request.getParameter("gr_tot" + count[i] + "");
									String id_inventory = request.getParameter("id_inventory" + count[i] + "");
							 if(type_grp1.equals("TYPE-CYLINDER")) {
								       
						        		
									
								
									
									System.out.println("update S_Inventory set  product='" + prod1 + "' ,ds_pro= '"
											+ ds_prod1+"' , mfr= '" + mfr1 + "',prod_hsncd='"+hsn_sc_cd1+"',asset_prfx='"
											+asset_prfx1+"', qty='" + qty + "',qty_fill='" + qty_fill + "',qty_empty='" + qty_empty + "',un_prc='" + un_prc+"', others='"
											 + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
											+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "',buyback='"+ buyback+"',temp_no_inv='"+ temp_no_inv +"',no_inv='"
											+ invNO + "',gr_tot='" + gr_tot + "',id_product='" + id_prod1 + 
											 "' where id_inventory='" + id_inventory + "';");
									if (!id_inventory.equals("")) {
										stmt.executeUpdate("update S_Inventory set  product='" + prod1 + "' ,ds_pro= '"
												+ ds_prod1+"' , mfr= '" + mfr1 + "',prod_hsncd='"+hsn_sc_cd1+"',asset_prfx='"
												+asset_prfx1+"', qty='" + qty + "',qty_fill='" + qty_fill + "',qty_empty='" + qty_empty + "',un_prc='" + un_prc+"', others='"
												 + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
												+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "',buyback='"+ buyback+"',temp_no_inv='"+ temp_no_inv +"',no_inv='"
												+ invNO + "',gr_tot='" + gr_tot + "',id_product='" + id_prod1 + 
												 "' where id_inventory='" + id_inventory + "';");
									} else {
										System.out.println(
												"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
														+ "values('" + prod1 + "','" + ds_prod1+ "','" + mfr1 + "','" + hsn_sc_cd1 + "','" + asset_prfx1 + "','"
														+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "," 
														+ un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id + "','" + id_prod1 + "','" + type_grp1 + "','"+invNO+"','"+temp_no_inv+"')");
										stmt.executeUpdate(
												"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
														+ "values('" + prod1 + "','" + ds_prod1 + "','" + mfr1 + "','" + hsn_sc_cd1 + "','" + asset_prfx1 + "','"
														+ id_emp_user + "','Yes','Yes'," + qty + "," + qty_fill + "," + qty_empty + "," 
														+ un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id + "','" + id_prod1 + "','" + type_grp1 + "','"+invNO+"','"+temp_no_inv+"')");
									}
									
							 }else {
								 
								 
								 if (!id_inventory.equals("")) {
										stmt.executeUpdate("update S_Inventory set  product='" + prod1 + "' ,ds_pro= '"
												+ ds_prod1+"' , mfr= '" + mfr1 + "',prod_hsncd='"+hsn_sc_cd1+"',asset_prfx='"
												+asset_prfx1+"', qty='" + qty + "',qty_fill='"+qty_fill+"',qty_empty='"+qty_empty+"',un_prc='" + un_prc+"', others='"
												 + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
												+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "',buyback='"+ buyback+"',temp_no_inv='"+ temp_no_inv +"',no_inv='"
												+ invNO + "',gr_tot='" + gr_tot + "',id_product='" + id_prod1 + 
												 "' where id_inventory='" + id_inventory + "' and NM_ACC_ASSET='"+type_grp1+"'");
									} else {
										System.out.println(
												"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
														+ "values('" + prod1 + "','" + ds_prod1 + "','" + mfr1 + "','" + hsn_sc_cd1 + "','" + asset_prfx1 + "','"
														+ id_emp_user + "','Yes','Yes'," + qty + ","+qty_fill+","+qty_empty+"," 
														+ un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id + "','" + id_prod1 + "','" + type_grp1 + "','"+invNO+"','"+temp_no_inv+"')");
										stmt.executeUpdate(
												"insert into S_Inventory(product,ds_pro,mfr,prod_hsncd,asset_prfx,created_by,config,st_config,qty,qty_fill,qty_empty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET,no_inv,temp_no_inv) "
														+ "values('" + prod1 + "','" + ds_prod1 + "','" + mfr1 + "','" + hsn_sc_cd1 + "','" + asset_prfx1+ "','"
														+ id_emp_user + "','Yes','Yes'," + qty + ","+qty_fill+","+qty_empty+"," 
														+ un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id + "','" + id_prod1 + "','" + type_grp1 + "','"+invNO+"','"+temp_no_inv+"')");
									}
								 
								 
								 
								 
								 
								 
								 
								 
								 
								 
								 
								 
								 
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
					System.out.println(e+"Some error in A_Invoice.");
					
				}
				break;
				

				
			case "Delete":
				jobj = DeleteInventory(id, id_inventory_m);
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

public JSONObject DisplayInvoice(String id, String id_inventory_m, String no_asset, String dt_frm, String dt_to,
			 String UserType, String temp1, String tempQuery,String searchWord) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			if (!no_asset.equals("")) {
				sql = "select * from S_Inventory inv where id_inventory = " + no_asset + " and status_store=0";
			}
			if (id_inventory_m.equals("") && no_asset.equals("")) {
				if (!UserType.equals("SUPER"))
					sql = "select distinct inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,nm_subl,nm_loc from S_Inventory_Master inv_m , M_Subloc ms,M_Loc l where  "
							+ " status_store = 0 and inv_m.id_loc=l.id_loc  and inv_m.id_subl=ms.id_sloc  and (inv_m.no_inv like '"+searchWord+"%' or inv_m.dt_inv like '"+searchWord+"%' or nm_loc like '"+searchWord+"%' or nm_subl like '"+searchWord+"%'  or inv_m.dt_temp_inv like '"+searchWord+"%' or inv_m.temp_no_inv like '"+searchWord+"%'  ) order by no_inv ";
				
				else
					sql = "select distinct inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,nm_subl,nm_loc from S_Inventory_Master inv_m , M_Subloc ms,M_Loc l where   "
							+ " status_store = 0 and inv_m.id_loc=l.id_loc and inv_m.id_subl=ms.id_sloc  and (inv_m.no_inv like '"+searchWord+"%' or inv_m.dt_inv like '"+searchWord+"%' or nm_loc like '"+searchWord+"%' or nm_subl like '"+searchWord+"%'  or inv_m.dt_temp_inv like '"+searchWord+"%' or inv_m.temp_no_inv like '"+searchWord+"%'  ) order by no_inv ";
			} else if (!id_inventory_m.equals("")) {
				sql = "select id_inventory_m,id_inventory,qty,un_prc,nm_acc_asset,mfr,ds_pro,id_product from S_Inventory inv where id_inventory_m = "
						+ id_inventory_m + " and status_store=0";
			}
			if (!id.equals("0")) {
				sql = "select  distinct inv.*,inv_m.id_loc,inv_m.tot,inv_m.id_inventory_m,inv_m.id_loc,inv_m.no_inv,inv_m.temp_no_inv as temp_invNO,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,inv_m.id_subl,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot from S_Inventory_Master inv_m ,S_Inventory inv , M_Subloc ms where  inv_m.id_subl=ms.id_sloc and inv_m.id_inventory_m=inv.id_inventory_m and inv_m.id_inventory_m = " + id
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
			System.out.println(e+"sql error in S_Add_To_Stock.");
		}
		System.out.println(jobj);
		return jobj;
	}

	
public JSONObject CheckExitsVal(String value, String ColName) {
		JSONObject json = new JSONObject();
		String query = "select id_inventory_m from S_Inventory_Master where " + ColName + " = '" + value + "'";
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
	
	
public JSONObject DeleteInventory(String id, String id_inventory_m) {
		JSONObject json = new JSONObject();
		int j = 0;
		try {
			if (!id_inventory_m.equals("0")) {
				String query = "select COUNT(id_inventory) from A_Invoice where id_inv_m = " + id_inventory_m + " ";
				ps = con.prepareStatement(query);
				rs = ps.executeQuery();
				if (rs.next()) {
					int rowCount = rs.getInt(1);
					if (rowCount > 1) {
						query = "delete S_inventory where id_inventory = " + id + "";
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
				String query = "select id_inventory_m from S_inventory where id_inventory = " + id + "";
				ps = con.prepareStatement(query);
				rs = ps.executeQuery();
				if (rs.next()) {
					id_inventory_m = rs.getString(1);
				}
				query = "delete S_inventory where id_inventory = " + id + "";
				ps = con.prepareStatement(query);
				j = ps.executeUpdate();
				if (j > 0) {
					query = "delete S_inventory_master where id_inventory_m = " + id_inventory_m + "";
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

	
public JSONObject GenerateInvoiceNo()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String sql = "select COUNT(no_inv)+1 as InvNO from S_Inventory_Master";
		try
		{
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
		
		    ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    if(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
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
			System.out.println("sql error in A_Add_To_Stock."+e.toString());
		}
		 
		return jobj;
	}
	
	}
	

	




	


	
	
