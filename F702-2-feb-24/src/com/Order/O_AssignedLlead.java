package com.Order;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
import com.Incident.IncidentModel;

import dto.Common.DtoCommon;

import com.Common.Common;

/**
 * Servlet implementation class O_AssignedLlead
 */
public class O_AssignedLlead extends HttpServlet {
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
				if(paramName.equals("approve_dt"))
				{
					paramValues = request.getParameter(paramName);
					System.out.println(paramValues);
					Date d = userDateFormat.parse(paramValues);  
					map.put(paramName,dateFormatNeeded.format(d));
				}
				else if(parts[0].equals("dt") && !paramValues.equals(""))
				{
					paramValues = request.getParameter(paramName);
					System.out.println(paramValues);
					Date d = userDateFormat.parse(paramValues);  
					map.put(paramName,dateFormatNeeded.format(d));

				}

				else if(paramValues.equals("po_date") && !paramValues.equals(""))
				{
					paramValues = request.getParameter(paramName);
					System.out.println(paramValues);
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
		String action = "",type_product="", temp1 = "", id = "0", upload_inv = "", lead_no = "", leadid = "", dt_to = "", dt_frm = "",
				state="",tagto_me_state="" ,state_approve="",value = "", dt_return_str="",deliver_time="",ColName = "", id_lead_m = "00";
		String EditExtensionReturn="",delivery_man_id="",latitude="",longitude="",time_stamp="" ,po_no="",po_date="",searchWord="",prodstatus="";
		if (request.getParameter("po_no") != null) {
			po_no = request.getParameter("po_no");
		}
		if (request.getParameter("po_date") != null) {
			po_date = request.getParameter("po_date");
		}
		if (request.getParameter("EditExtensionReturn") != null) {
			EditExtensionReturn = request.getParameter("EditExtensionReturn");
		}
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("upload_inv") != null) {
			upload_inv = request.getParameter("upload_inv");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("id_lead_m") != null) {
			id_lead_m = request.getParameter("id_lead_m");
		}
		if (request.getParameter("lead_no") != null) {
			lead_no = request.getParameter("lead_no");
		}
		if (request.getParameter("leadid") != null) {
			leadid = request.getParameter("leadid");
		}
		if (request.getParameter("dt_frm") != null) {
			dt_frm = request.getParameter("dt_frm");
		}
		if (request.getParameter("dt_to") != null) {
			dt_to = request.getParameter("dt_to");
		}
		if (request.getParameter("state") != null) {
			state = request.getParameter("state");
		}
		if (request.getParameter("tagto_me_state") != null) {
			tagto_me_state = request.getParameter("tagto_me_state");
		}
		if (request.getParameter("state_aprvdelvr") != null) {
			state_approve = request.getParameter("state_aprvdelvr");
			System.out.println("state"+state_approve);
		}
		if (request.getParameter("dt_return_str") != null) {
			dt_return_str = request.getParameter("dt_return_str");
		}
		if (request.getParameter("deliver_time") != null) {
			deliver_time = request.getParameter("deliver_time");
		}
		if (request.getParameter("delivery_man_id") != null) {
			delivery_man_id = request.getParameter("delivery_man_id");
		}
		if (request.getParameter("latitude") != null) {
			latitude = request.getParameter("latitude");
		}
		if (request.getParameter("longitude") != null) {
			longitude = request.getParameter("longitude");
		}
		if (request.getParameter("time_stamp") != null) {
			time_stamp = request.getParameter("time_stamp");
		}
		if (request.getParameter("prodstatus") != null) {
			prodstatus = request.getParameter("prodstatus");
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
		if(request.getParameter("type_product") !=null)
		{
			type_product = request.getParameter("type_product");
		}	



		//String additional_prod[] = request.getParameterValues("add_prod");
		//System.out.println(request.getParameterValues("retrunstr"));
		String chk[] = request.getParameterValues("retrunstr");

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
			if(!dt_return_str.equals(""))
			{
				dt_return_str = dateFormatNeeded.format(userDateFormat.parse(dt_return_str));
			}
			String tempQuery = "";
			int id_emp_user = (int) session.getAttribute("id_emp_user");

			String UserType = (String) session.getAttribute("UserType");
			//int id_emp_user = 1;
			//			
			//			String UserType = "Super";


			con = Common.GetConnection(request);
			switch (action) {


			case "TagLead":
				String col_Val = "",id_loc="",id_sloc="",created_by="",  typ_service="",serial_no="",	colName1 = "" ;
				int k = 0,id_proceed_m = 0;
				String cd_prod1="",prev_id_proceed1="",fill_lead="",prev_id_proceed_m1="",prev_id_lead_m1="";
				String state1=map.get("state");


				String tagto_me_state1=map.get("tagto_me_state");
				String tag_by=map.get("tag_by");
				String state_aprvdelvr=map.get("state_aprvdelvr");
				id_lead_m=map.get("id_lead_m");
				id_sloc=map.get("id_sloc");
				id_loc=map.get("id_loc");
				created_by=map.get("created_by");
				lead_no=map.get("lead_no");
				

			
				try {

					stmt = con.createStatement();
					rs = Common.GetColumns("O_Assign_Lead_Master", request);
					while (rs.next()) {
						if (rs.getString(1) != "id_proceed_m") {
							if (map.containsKey(rs.getString(1))) {
								if(!rs.getString(1).equals("id_lead_m")&& !rs.getString(1).equals("state")&&!rs.getString(1).equals("tagto_me_state"))
								{
									if (colName1.equals("")) {
										colName1 += rs.getString(1);
										value += "'" + map.get(rs.getString(1)) + "'";
									} else {
										colName1 += "," + rs.getString(1);
										value += ", '" + map.get(rs.getString(1)) + "'";
									}
								}
							}
						}
					}

					String query ="select fill_lead from O_lead_Master where id_lead_m='"+id_lead_m+"'";
					ps = con.prepareStatement(query);
					rs = ps.executeQuery();
					if (rs.next()) {
						fill_lead=rs.getString("fill_lead");
						System.out.println("kajol"+fill_lead);
					
					//String query = "update O_Assign_Lead_Master set " + col_Val + ",status_cord_ld = '1' where id_lead_m=" + id + "";
					 query="insert into O_Assign_Lead_Master(" + colName1+",id_lead_m,status_cord_ld,state,tagto_me_state,fill_lead) values(" + value + ","+ id_lead_m+ ",'1','Inprogress','New','"+fill_lead+"')";
					System.out.println(query+"hi");
					try {
						stmt = con.createStatement();
						stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
						rs1 = stmt.getGeneratedKeys();
						while (rs1.next()) {
							id_proceed_m = rs1.getInt(1);
							
						}
						String count[] = request.getParameterValues("count");
						
						stmt = con.createStatement();
						//						PreparedStatement ps = con.prepareStatement(query);
						//						k = ps.executeUpdate();

						//if (k > 0) {
						String ds_pro ="",id_prod="",ds_assetdiv="",mfr_assetdiv="" , in_stoc_qty="";
						int j=0;
						for (int i = 0; i < count.length; i++) {
							cd_prod1 = request.getParameter("id_prod" + count[i] + "");
							if (!cd_prod1.isEmpty()) {
								String	sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod1 + "'";
								
								ps = con.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									id_prod = rs.getString("id_assetdiv");
									ds_pro = rs.getString("nm_assetdiv");
									ds_assetdiv = rs.getString("ds_assetdiv");
									mfr_assetdiv = rs.getString("mfr_assetdiv");
								}
                                System.out.println("hh"+id_prod);
								String un_prc = request.getParameter("un_prc" + count[i] + "");
								String cylndr_fill_mt = request.getParameter("cylndr_fill_mt" + count[i] + "");

								serial_no = request.getParameter("sr_no" + count[i] + "");
								String others = request.getParameter("others" + count[i] + "");
								String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
								String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
								String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
								String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
								String buyback = request.getParameter("buyback" + count[i] + "");
								System.out.println(buyback);
								typ_service = request.getParameter("typ_service" + count[i] + "");
								String typ_cylinder = request.getParameter("typ_cylinder" + count[i] + "");
								System.out.println(typ_cylinder);
								String rental_day = request.getParameter("rental_day" + count[i] + "");
								String dt_exp_rent1 = request.getParameter("dt_exp_rent" + count[i] + "");
								String dt_exp_rent="";
								if(dt_exp_rent1.equals("")) {
									dt_exp_rent="";
								}
								else {
									dt_exp_rent =dateFormatNeeded.format(userDateFormat.parse(dt_exp_rent1));
								}
								System.out.println(rental_day);
								//String dt_exp_rent1 = request.getParameter("dt_exp_rent" + count[i] + "");
								// System.out.println(dt_exp_rent1);
								// String dt_exp_rent =dateFormatNeeded.format(userDateFormat.parse(dt_exp_rent1));
								String gr_tot = request.getParameter("gr_tot" + count[i] + "");
								in_stoc_qty = request.getParameter("in_stoc_qty" + count[i] + "");
								String id_lead = request.getParameter("id_lead" + count[i] + "");
								String prod_status = request.getParameter("prod_status" + count[i] + "");
							
								String prev_sr_no = request.getParameter("prev_sr_no" + count[i] + "");
                                    if(prev_sr_no.equals("")) {
									
                                    	prev_sr_no="";
	                                }
								String prev_lead_no = request.getParameter("prev_lead_no" + count[i] + "");
                                    if(prev_lead_no.equals("")) {
									
                        	         prev_lead_no="";
	                                }
								String refill_id_lead = request.getParameter("refill_id_lead" + count[i] + "");
								if(refill_id_lead.equals("")) {
									
									refill_id_lead="";
	                                }
								System.out.println("id"+id_lead);
								int diff=0; 

								if(typ_service.equals("Rental")) {

									if(prod_status.equals("Refill")||prod_status.equals("Refill_and_Extend")) {

										if (!id_lead.equals("")) {

											sql="select * from O_Assign_Lead where id_lead='"+id_lead+"'";
											ps = con.prepareStatement(sql);
											rs = ps.executeQuery();
											if (rs.next()) {
												prev_id_proceed1 = rs.getString("id_proceed");
												prev_id_proceed_m1 = rs.getString("id_proceed_m");
												prev_id_lead_m1 = rs.getString("id_lead_m");

											}

											System.out.println("insert into O_Assign_Lead(nm_prod,ds_product,mfr,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service,rental_day,dt_exp_rent,current_extend_exp_dt,id_lead,id_lead_m,id_proceed_m,status_cord_ld,created_by,tagto_me_state,state,cylndr_fill_mt,lead_no,prev_sr_no,prev_lead_no,prod_status,refill_id_proceed)"
													+"values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"','" + id_prod + "','1','"+ un_prc + "','" + others + "','"+ id_tax1 + "','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','" + typ_service+ "','" + rental_day+ "','"+dt_exp_rent+"','"+dt_exp_rent+"','"+id_lead+"','"+id_lead_m+"','"+id_proceed_m+"','1','"+created_by+"','New','Inprogress','"+cylndr_fill_mt+"','"+lead_no+"','"+prev_sr_no+"','"+prev_lead_no+"','"+prod_status+"','"+prev_id_proceed1+"')");
											//							      



											k=stmt.executeUpdate("insert into O_Assign_Lead(nm_prod,ds_product,mfr,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service,rental_day,dt_exp_rent,current_extend_exp_dt,id_lead,id_lead_m,id_proceed_m,status_cord_ld,created_by,tagto_me_state,state,cylndr_fill_mt,lead_no,prev_sr_no,prev_lead_no,prod_status,refill_id_proceed)"
													+"values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"','" + id_prod + "','1','"+ un_prc + "','" + others + "','"+ id_tax1 + "','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','" + typ_service+ "','" + rental_day+ "','"+dt_exp_rent+"','"+dt_exp_rent+"','"+id_lead+"','"+id_lead_m+"','"+id_proceed_m+"','1','"+created_by+"','New','Inprogress','"+cylndr_fill_mt+"','"+lead_no+"','"+prev_sr_no+"','"+prev_lead_no+"','"+prod_status+"','"+prev_id_proceed1+"')");

											if(k>0) {
												k=0;
												System.out.println("update  O_Assign_Lead set sr_no_refill='"+serial_no+"',   lead_no_refill='"+lead_no+"' where  id_lead='"+refill_id_lead+"'");

												k=stmt.executeUpdate("update  O_Assign_Lead set sr_no_refill='"+serial_no+"',  lead_no_refill='"+lead_no+"' where   id_lead='"+refill_id_lead+"'" );

                                               
												if(k>0) {
													k=0;
													query = "select tot_aval_qty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc+" and id_loc="+id_loc+"  and id_product="+id_prod+"";
													ps = con.prepareStatement(query);
													rs = ps.executeQuery();
													if (rs.next()) {
					
														System.out.println("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"-1) ,tot_rent_aval_qty=("+rs.getInt(2)+"+1) where id_sloc="+id_sloc+" and  id_loc="+id_loc+" and id_product="+id_prod+"" );

														k=stmt.executeUpdate("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"-1) ,tot_rent_aval_qty=("+rs.getInt(2)+"+1) where id_sloc="+id_sloc+" and id_loc="+id_loc+" and id_product="+id_prod+"" );

														if(k>0) {

															k=0;
															System.out.println("update S_I_Ware_House set device_status='Rental' where serial_no ='"+serial_no+"' ");
															k=stmt.executeUpdate("update S_I_Ware_House set device_status ='"+typ_service+"' where serial_no ='"+serial_no+"' and  device_status='Rental'");

															k=stmt.executeUpdate("update S_I_Ware_House set device_status ='Rental' where serial_no ='"+serial_no+"'");
														}
													}
												}


											}

											
										}
									}
								

										if(prod_status.equals("Product_on_Rental"))  {
											if (!id_lead.equals("")) {


												System.out.println("insert into O_Assign_Lead(nm_prod,ds_product,mfr,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service,rental_day,dt_exp_rent,current_extend_exp_dt,id_lead,id_lead_m,id_proceed_m,status_cord_ld,created_by,tagto_me_state,state,cylndr_fill_mt,lead_no,prev_lead_no,prev_sr_no,prod_status,refill_id_proceed )"
														+"values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"','" + id_prod + "','1','"+ un_prc + "','" + others + "','"+ id_tax1 + "','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','" + typ_service+ "','" + rental_day+ "','"+dt_exp_rent+"','"+dt_exp_rent+"','"+id_lead+"','"+id_lead_m+"','"+id_proceed_m+"','1','"+created_by+"','New','Inprogress','"+cylndr_fill_mt+"','"+lead_no+"','"+prev_sr_no+"','"+prev_lead_no+"','"+prod_status+"','"+prev_id_proceed1+"')");
																			      



												k=stmt.executeUpdate("insert into O_Assign_Lead(nm_prod,ds_product,mfr,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service,rental_day,dt_exp_rent,current_extend_exp_dt,id_lead,id_lead_m,id_proceed_m,status_cord_ld,created_by,tagto_me_state,state,cylndr_fill_mt,lead_no,prev_lead_no,prev_sr_no,prod_status,refill_id_proceed)"
														+"values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"','" + id_prod + "','1','"+ un_prc + "','" + others + "','"+ id_tax1 + "','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','" + typ_service+ "','" + rental_day+ "','"+dt_exp_rent+"','"+dt_exp_rent+"','"+id_lead+"','"+id_lead_m+"','"+id_proceed_m+"','1','"+created_by+"','New','Inprogress','"+cylndr_fill_mt+"','"+lead_no+"','"+prev_sr_no+"','"+prev_lead_no+"','"+prod_status+"','"+prev_id_proceed1+"')");

																					

											}

											

											query = "select tot_aval_qty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc+" and id_loc="+id_loc+"  and id_product="+id_prod+"";
											System.out.println(query);
											ps = con.prepareStatement(query);
											rs = ps.executeQuery();
											if (rs.next()) {
												System.out.println("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"-1) ,tot_rent_aval_qty=("+rs.getInt(2)+"+1) where id_sloc="+id_sloc+" and  id_loc="+id_loc+" and id_product="+id_prod+"" );

												k=stmt.executeUpdate("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"-1) ,tot_rent_aval_qty=("+rs.getInt(2)+"+1) where id_sloc="+id_sloc+" and id_loc="+id_loc+" and id_product="+id_prod+"" );

												if(k>0) {

													k=0;
													System.out.println("update S_I_Ware_House set device_status ='Rental' where serial_no ='"+serial_no+"' ");

													k=stmt.executeUpdate("update S_I_Ware_House set device_status ='Rental' where serial_no ='"+serial_no+"' ");
												}
											}
										}

									}
								//Rental 
								else if(typ_service.equals("Sale")) {
									
									 if(prod_status.equals("Product_on_Sale")) { 

										if (!id_lead.equals("")) {


											System.out.println("insert into O_Assign_Lead(nm_prod,ds_product,mfr,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service,rental_day,dt_exp_rent,id_lead,id_lead_m,id_proceed_m,status_cord_ld,created_by,tagto_me_state,state,cylndr_fill_mt,lead_no,prev_lead_no,prev_sr_no,prod_status,refill_id_proceed,)"
													+"values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"','" + id_prod + "','1','"+ un_prc + "','" + others + "','"+ id_tax1 + "','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','" + typ_service+ "','" + rental_day+ "','"+dt_exp_rent+"','"+id_lead+"','"+id_lead_m+"','"+id_proceed_m+"','1','"+created_by+"','New','Inprogress','"+cylndr_fill_mt+"','"+lead_no+"','"+prev_sr_no+"','"+prev_lead_no+"','"+prod_status+"','"+prev_id_proceed1+"')");
											//							      



											k=stmt.executeUpdate("insert into O_Assign_Lead(nm_prod,ds_product,mfr,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service,rental_day,dt_exp_rent,id_lead,id_lead_m,id_proceed_m,status_cord_ld,created_by,tagto_me_state,state,cylndr_fill_mt,lead_no,prev_lead_no,prev_sr_no,prod_status,refill_id_proceed)"
													+"values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"','" + id_prod + "','1','"+ un_prc + "','" + others + "','"+ id_tax1 + "','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','" + typ_service+ "','" + rental_day+ "','"+dt_exp_rent+"','"+id_lead+"','"+id_lead_m+"','"+id_proceed_m+"','1','"+created_by+"','New','Inprogress','"+cylndr_fill_mt+"','"+lead_no+"','"+prev_sr_no+"','"+prev_lead_no+"','"+prod_status+"','"+prev_id_proceed1+"')");


											//										

										}



										query = "select tot_aval_qty from S_Store_Qty  where id_sloc="+id_sloc+" and id_loc="+id_loc+"  and id_product="+id_prod+"";
										ps = con.prepareStatement(query);
										rs = ps.executeQuery();
										if (rs.next()) {
											System.out.println("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"-1)  where id_sloc="+id_sloc+" and  id_loc="+id_loc+" and id_product="+id_prod+"" );

											k=stmt.executeUpdate("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"-1)  where id_sloc="+id_sloc+" and id_loc="+id_loc+" and id_product="+id_prod+"" );

											if(k>0) {
												k=0;
												System.out.println("update S_I_Ware_House set device_status ='Sale' where serial_no ='"+serial_no+"'" );

												k=stmt.executeUpdate("update S_I_Ware_House set device_status ='Sale' where serial_no ='"+serial_no+"' ");
											}
										}	

									 }

									}else if(typ_service.equals("Refill")) {
										if(prod_status.equals("Fill")) {
				                    	
											System.out.println("insert into O_Assign_Lead(nm_prod,ds_product,mfr,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service,rental_day,dt_exp_rent,id_lead,id_lead_m,id_proceed_m,status_cord_ld,created_by,tagto_me_state,state,cylndr_fill_mt,lead_no,,prev_lead_no,prev_sr_no,prod_status,refill_id_proceed,typ_cylinder)"
													+"values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"','" + id_prod + "','1','"+ un_prc + "','" + others + "','"+ id_tax1 + "','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','" + typ_service+ "','" + rental_day+ "','"+dt_exp_rent+"','"+id_lead+"','"+id_lead_m+"','"+id_proceed_m+"','1','"+created_by+"','New','Inprogress','"+cylndr_fill_mt+"','"+lead_no+"','"+prev_sr_no+"','"+prev_lead_no+"','"+prod_status+"','"+prev_id_proceed1+"','"+typ_cylinder+"')");
											//							      



											k=stmt.executeUpdate("insert into O_Assign_Lead(nm_prod,ds_product,mfr,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service,rental_day,dt_exp_rent,id_lead,id_lead_m,id_proceed_m,status_cord_ld,created_by,tagto_me_state,state,cylndr_fill_mt,lead_no,prev_lead_no,prev_sr_no,prod_status,refill_id_proceed,typ_cylinder)"
													+"values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"','" + id_prod + "','1','"+ un_prc + "','" + others + "','"+ id_tax1 + "','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','" + typ_service+ "','" + rental_day+ "','"+dt_exp_rent+"','"+id_lead+"','"+id_lead_m+"','"+id_proceed_m+"','1','"+created_by+"','New','Inprogress','"+cylndr_fill_mt+"','"+lead_no+"','"+prev_sr_no+"','"+prev_lead_no+"','"+prod_status+"','"+prev_id_proceed1+"','"+typ_cylinder+"')");

										
										}
				                    }//
							

								


								j +=1;
							}//loop
						}
						   if(j==count.length) {

						


							
							
							k=0;

							query ="update  O_lead_Master set status_proceed = 1 ,state='Inprogress' where id_lead_m ="+id_lead_m+"";
							System.out.println(query);
							PreparedStatement ps1=con.prepareStatement(query);
							k=ps1.executeUpdate();

							if(k>0) {
								k=0;
								query ="update O_lead set status_proceed = 1  , state='Inprogress' where id_lead_m ="+id_lead_m+"";
								System.out.println(query);
								ps1=con.prepareStatement(query);
								k=ps1.executeUpdate();
								k=1;

								if(k> 0)
								{
									LeadModel ldmd = new LeadModel();
									ldmd.SendEmailToTagperson(map.get("tag_to_emp"),id_lead_m, request);

									LeadModel_Whatsapp ldmd2 = new LeadModel_Whatsapp();
									ldmd2.SendWhatsAppToTagperson(map.get("tag_to_emp"),id_lead_m, request);
									//k=1;


									if(!map.get("pstn_email").equals("NA")) {
										//										LeadModel ldmd1 = new LeadModel();
										//										ldmd1.SendEmailToCustomerleadConfirmdelivery(id,map.get("tag_to_emp"), request);
										//										k=1; 
										//										
										//										

										LeadModel ldmd1 = new LeadModel();
										ldmd1.SendEmailToCustomerforTrackingcordential(id_lead_m, map.get("nm_pstn"),map.get("pstn_ct"),request);		
										System.out.println("mailCome");


										LeadModel_Whatsapp ldmd3 = new LeadModel_Whatsapp();
										ldmd3.SendWhatsAppToToCustomerforTrackingcordential(id_lead_m,request);
										k=1;

									}
									else {

										System.out.println("not Na");
										LeadModel_Whatsapp ldmd3 = new LeadModel_Whatsapp();
										ldmd3.SendWhatsAppToToCustomerforTrackingcordential(id_lead_m,request);
										k=1;

									}
								}





							}
						}
						   

						jobj.put("data", k);
					} catch (Exception e) {
						e.printStackTrace();
					}
					}
				} catch (Exception e) {
					System.out.println("Some error in O_lead.");
				}

				break;

			case "Approvefordeliver":
				String col_Val1 = "";
				int k1 = 0;
				String cd_prod11="" ,approve_by="",state_aprvdelvr1="";
				approve_by=map.get("apprvby_frdelvr");
				state_aprvdelvr1=map.get("state_aprvdelvr");
				System.out.println("state_aprvdelvr1");
				try {

					stmt = con.createStatement();
					rs = Common.GetColumns("O_Assign_Lead_Master", request);
					while (rs.next()) {
						if (rs.getString(1) != "id_proceed_m") {
							if (map.containsKey(rs.getString(1))) {
								if(!rs.getString(1).equals("lead_no")&&!rs.getString(1).equals("tag_by")  )
								{
									if (col_Val1.equals("")) {
										col_Val1 += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
									} else {
										col_Val1 += "," + rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
									}
								}
							}
						}
					}


					String query = "update O_Assign_Lead_Master set " + col_Val1 + ",status_apprvdelvr = '1' where id_lead_m=" + id + "";
					System.out.println(query);
					try {
						PreparedStatement ps = con.prepareStatement(query);
						k1 = ps.executeUpdate();
						if (k1 > 0) {
							String ds_pro ="",id_prod="",ds_assetdiv="",mfr_assetdiv="" , in_stoc_qty="";
							String count[] = request.getParameterValues("count");
							for (int i = 0; i < count.length; i++) {
								cd_prod11 = request.getParameter("id_prod" + count[i] + "");
								if (!cd_prod11.isEmpty()) {
									String	sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod11 + "'";
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										id_prod = rs.getString("id_assetdiv");
										ds_pro = rs.getString("nm_assetdiv");
										ds_assetdiv = rs.getString("ds_assetdiv");
										mfr_assetdiv = rs.getString("mfr_assetdiv");
									}

									String un_prc = request.getParameter("un_prc" + count[i] + "");

									serial_no = request.getParameter("sr_no" + count[i] + "");
									String others = request.getParameter("others" + count[i] + "");
									String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
									String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
									String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
									String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
									String buyback = request.getParameter("buyback" + count[i] + "");
									System.out.println(buyback);
									typ_service = request.getParameter("typ_service" + count[i] + "");
									String rental_day = request.getParameter("rental_day" + count[i] + "");
									String dt_exp_rent1 = request.getParameter("dt_exp_rent" + count[i] + "");
									String dt_exp_rent="";
									if(dt_exp_rent1.equals("")) {
										dt_exp_rent="";
									}
									else {
										dt_exp_rent =dateFormatNeeded.format(userDateFormat.parse(dt_exp_rent1));
										System.out.println(dt_exp_rent);
									}
									System.out.println(rental_day);

									String gr_tot = request.getParameter("gr_tot" + count[i] + "");
									in_stoc_qty = request.getParameter("in_stoc_qty" + count[i] + "");
									String id_lead = request.getParameter("id_lead" + count[i] + "");
									System.out.println("id"+id_lead);
									int diff=0; 


									if (!id_lead.equals("")) {

										System.out.println("update O_Assign_Lead set  nm_prod = '" + ds_pro + "', ds_product ='" + ds_assetdiv + "',mfr='" + mfr_assetdiv + "',sr_no='"+ serial_no+"', id_prod='" + id_prod + "',quantity='1',"
												+"un_prc ='"+ un_prc + "', others='" + others + "' , id_tax1= '"+id_tax1 + "',id_tax2 = '"+id_tax2+"', tax_val1= '"+tax_val1+"',buyback='"+buyback+"',gr_tot='"+gr_tot+"',typ_service='" + typ_service+ "',rental_day='" + rental_day + "' ,"
												+"dt_exp_rent='"+dt_exp_rent+"',state_aprvdelvr='"+state_aprvdelvr1+"',apprvby_frdelvr= '"+approve_by+"', status_apprvdelvr = '1' where id_lead_m=  '"+id+"' and  id_lead='" + id_lead + "'" ); 

										k1=stmt.executeUpdate("update O_Assign_Lead set  nm_prod = '" + ds_pro + "', ds_product ='" + ds_assetdiv + "',mfr='" + mfr_assetdiv + "',sr_no='"+ serial_no+"', id_prod='" + id_prod + "',quantity='1',"
												+"un_prc ='"+ un_prc + "', others='" + others + "' , id_tax1= '"+id_tax1 + "',id_tax2 = '"+id_tax2+"', tax_val1= '"+tax_val1+"',buyback='"+buyback+"',gr_tot='"+gr_tot+"',typ_service='" + typ_service+ "',rental_day='" + rental_day + "',"
												+ "dt_exp_rent='"+dt_exp_rent+"',state_aprvdelvr='"+state_aprvdelvr1+"',apprvby_frdelvr= '"+approve_by+"',status_apprvdelvr = '1' where id_lead_m=  '"+id+"' and  id_lead='" + id_lead + "'" );


										//									

									}
								}
								k1+=1;
								//								



							}

							if(k1==count.length) {



								//k=0;

								LeadModel ldmd = new LeadModel();
								ldmd.SendEmailToTagperson(map.get("tag_to_emp"),id, request);

								if(k1 > 0)
								{
									k1=0;
									//at that time customer also have mail and url tracking working point
									if(!map.get("pstn_email").equals("NA")) {
										LeadModel ldmd1 = new LeadModel();
										ldmd1.SendEmailToCustomerleadConfirmdelivery(id,map.get("tag_to_emp"), request);
										k1=1; 
										System.out.println("mailCome");
									}
									else {

										k1=1;
									}



								} 



							}
						}
						jobj.put("data", k1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					System.out.println("Some error in O_Assinglead.");
				}

				break;
			case "Update":
				jobj = UpdateDeliverDetails(map, id_emp_user, request);	
				break; 
			case "UpdateTrackinglocation":
				jobj = UpdateDeliverlivetrackingDetails(id,delivery_man_id,latitude,longitude ,time_stamp,request);	
				break; 

			case "Display":
				jobj = DisplayCrLead(id, lead_no, leadid, dt_frm, dt_to, state, UserType,id_emp_user,prodstatus,searchWord);
				break;
			case "DisplayFillClosedLead":
				jobj = DisplayFillClosedLead(id, lead_no, leadid, dt_frm, dt_to, state, UserType,id_emp_user,prodstatus,searchWord);
				break;
			case "EditFillClosedLead":
				jobj = DisplayFillClosedLead(id, lead_no, leadid, dt_frm, dt_to, state, UserType,id_emp_user,prodstatus,searchWord);
				break;
			case "Displayfor_RefillItem":
				jobj = DisplayforRefillLead(id, searchWord,  dt_frm, dt_to, state, UserType,id_emp_user);
				break;	
			case "EditInitiateRefillItem":
				jobj = DisplayforRefillLead(id, searchWord,  dt_frm, dt_to, state, UserType,id_emp_user);
				break;	
			case "Edit":
				jobj = DisplayCrLead(id, lead_no, leadid, dt_frm, dt_to,state,  UserType,id_emp_user,prodstatus,searchWord);
				break;
			case "DisplayPreview":
				jobj = DisplayPreview(id,dt_frm, dt_to,  UserType,searchWord);
				break;
				//case "AssignTome":
			case "DisplayMylead":
				jobj = DisplayAssignTome( id, lead_no, leadid, dt_frm, dt_to,tagto_me_state,  UserType,id_emp_user,searchWord,request);
				break;
			case "EditMylead":
				jobj = DisplayAssignTome( id, lead_no, leadid, dt_frm, dt_to,tagto_me_state,  UserType, id_emp_user,searchWord,request);
				break;
			case "EditMyleadHasReturn":
				jobj = DisplayEditMyleadHasReturn( id, lead_no, leadid, dt_frm, dt_to,tagto_me_state,  UserType, id_emp_user,request);
				break;
			case "EditExtensionReturn":
				jobj = DisplayEditExtensionHasReturn( id, lead_no, leadid, dt_frm, dt_to,tagto_me_state,  UserType, id_emp_user,request,EditExtensionReturn);
				break;
			case "Edit1":
				jobj = UpdateEditDeliverDetails(id);
				break;
				//			case "DisplayForAprove":
				//				jobj = DisplayForAprovedelivery( id, lead_no, leadid, dt_frm, dt_to,state_approve,  UserType,id_emp_user,request);
				//				break;	
				//			case "EditForAprove":
				//				jobj = DisplayForAprovedelivery( id, lead_no, leadid, dt_frm, dt_to,state_approve,  UserType,id_emp_user,request);
				//				break;
			case "Preview":
				jobj = PreviewInvoiceForLead( id_emp_user, id);
				break;

			case "CheckRetrunstkDate": 
				jobj = CheckRetrunstkDate(dt_return_str,id);	
				break;
			case "checkdeliverytime": 
				jobj = CheckDeliverydate(deliver_time,id);	
				break; 
			case "ClosedFillLead": 
				jobj = ClosedFillLead(id);	
				break; 
			case "Display_Refillhistory":
				jobj = DisplayRefillhistory( id, lead_no,type_product,request);
				break;
			case "ReturnStore": 

				try
				{

					int j = 0;		
					String colName="",value1="",sql="",itemName="",slNo="",Rmk="",
							idleadm1="",prod_status="",return_by="",pstn_email="" ,id_loc1="",id_sloc1="";
					List<String> recipients = new ArrayList<String>();
					String  dt_extend_str="";


					stmt = con.createStatement();
					rs = Common.GetColumns("O_Return_To_Store",  request);

					while (rs.next())
					{

						if(rs.getString(1) !="id_return_str")
						{
							if (map.containsKey(rs.getString(1)))
							{
								if(!rs.getString(1).equals("rmk")||!rs.getString(1).equals("amount")||!rs.getString(1).equals("") )
								{
									if(colName.equals(""))
									{
										colName += rs.getString(1);
										value1 += "'"+ map.get(rs.getString(1))+"'";
									}
									else
									{
										colName +=","+ rs.getString(1);
										value1 +=", '"+ map.get(rs.getString(1))+"'";

									}
								}
							}

						}
					}


					System.out.println(map);



					String refund_amt = map.get("refund_amt");
					String refund_amt_adjust = map.get("refund_amt_adjust");
					String tot_ref_amt = map.get("tot_ref_amt");
					id_sloc = map.get("id_sloc");
					id_loc = map.get("id_loc");
					idleadm1 = map.get("id_lead_m");

					return_by = map.get("return_by");

					String amt=map.get("amount");
					String colct_amount=map.get("colct_amount");
					String due_amount=map.get("due_amount");

					lead_no=map.get("lead_no");


					if(due_amount.equals("0")&& due_amount.equals("0.00")) {

						String query = "update O_Assign_Lead_Master set colct_amount='"+colct_amount+"',due_amount='"+due_amount+"'   where id_lead_m=" + idleadm1 + "";
						System.out.println(query);
						PreparedStatement ps = con.prepareStatement(query);
						j= ps.executeUpdate();

					}





					if(!refund_amt.equals("0")&&!refund_amt.equals("0.00")) {

						if(!refund_amt_adjust.equals("")&&!tot_ref_amt.equals("")) {




							float rfd=Float.parseFloat(refund_amt);
							float rfadjst=Float.parseFloat(refund_amt_adjust);
							float totrf=Float.parseFloat(tot_ref_amt);

							float tot_rf=rfd-(rfadjst+totrf);
							String refundamt=Float.toString(tot_rf);
							System.out.println("ee"+tot_rf);
							String query = "update O_Assign_Lead_Master set refund_amt='"+refundamt+"',refund_amt_adjust='"+refund_amt_adjust+"',tot_ref_amt='"+tot_ref_amt+"' where id_lead_m=" + idleadm1 + "";
							System.out.println(query);
							PreparedStatement ps = con.prepareStatement(query);
							j= ps.executeUpdate();

						}
					}


					int serialNo    =   0 ;

					for(int i=0; i<chk.length;i++)
					{

						String typeprod="";
						String s = chk[i];
						String dt_return_str1 =request.getParameter("dt_return_str"+chk[i]);
						dt_return_str = dateFormatNeeded.format(userDateFormat.parse(dt_return_str1));

						Rmk =request.getParameter("rmk"+chk[i]);
						prod_status =request.getParameter("prod_status"+chk[i]);
						String typeservice = request.getParameter("typ_service"+chk[i]);
						String id_lead = request.getParameter("id_lead"+chk[i]);
						String id_prod = request.getParameter("id_prod"+chk[i]);
						String last_inv_no = request.getParameter("current_inv_no"+chk[i]);





						sql = "insert into O_Return_To_Store(id_lead_m,rmk,dt_return_str,serial_no,prod_status,id_prod,id_lead,last_inv_no ,return_by,lead_no) values('"+idleadm1+"','"+Rmk+"','"+dt_return_str+"','"+s+"','"+prod_status+"','"+id_prod+"','"+id_lead+"','"+last_inv_no+"','"+id_emp_user+"','"+lead_no+"')";
						System.out.println(sql);
						PreparedStatement ps=con.prepareStatement(sql);
						j=ps.executeUpdate();
						System.out.println(j);

						if(j>0)
						{	

							sql="select typeprod from S_I_Ware_House where serial_no = '"+chk[i]+"'";
							ps = con.prepareStatement(sql);
							rs = ps.executeQuery(); 
							System.out.println(sql);
							if(rs.next()) {
								typeprod=rs.getString("typeprod");
							}
							if(typeprod.equals("Type-Cylinder")||typeprod.equals("TYPE-CYLINDER")) {

								System.out.println(typeprod);
								sql=" update S_I_Ware_House  set  device_status = 'in_store',filling_status='0' , sr_no_status =0 where serial_no = '"+chk[i]+"'";

								ps=con.prepareStatement(sql);
								j=ps.executeUpdate();
								System.out.println(sql);

								if(j>0)
								{
									j=0;
									sql ="update  O_Assign_Lead set status_returnto_str = 1, state='Closed',tagto_me_state='Closed' where id_lead ="+id_lead+" ";
									System.out.println(sql);
									ps=con.prepareStatement(sql);
									j=ps.executeUpdate();

									if(j>0) {
										j=0;
										sql="select * from O_Renew_lead  where id_lead="+id_lead+" ";
										ps = con.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											sql ="update  O_Renew_lead set status_return = 1  where id_lead ="+id_lead+" ";
											System.out.println(sql);
											ps=con.prepareStatement(sql);
											j=ps.executeUpdate();

											if(j>0) {
												j=0;
												sql = " select * from O_Assign_Lead_Master where id_lead_m='"+idleadm1+"'" ;
												System.out.println(" select * from O_Assign_Lead_Master where id_lead_m='"+idleadm1+"'" );
												ps = con.prepareStatement(sql);
												rs = ps.executeQuery();
												if (rs.next()) {

													id_sloc1=rs.getString("id_sloc");
													id_loc1=rs.getString("id_loc");

													sql = "select tot_aval_empty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"  and id_product="+id_prod+"";
													System.out.println("select tot_aval_empty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"   and id_product="+id_prod+"");
													ps = con.prepareStatement(sql);
													rs = ps.executeQuery();
													if (rs.next()) {
														System.out.println("update  S_Store_Qty set tot_aval_empty=("+rs.getInt(1)+"+1)  , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"   and id_product="+id_prod+"" );

														stmt.executeUpdate("update  S_Store_Qty set tot_aval_empty=("+rs.getInt(1)+"+1) , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"  and id_product="+id_prod+"" );
														j=1;

														if(j>0) {
															sql="select * from O_Assign_Lead_Master  where id_lead_m="+id_lead_m+" ";
															ps = con.prepareStatement(sql);
															rs = ps.executeQuery();


															if (rs.next()) {
																pstn_email=rs.getNString("pstn_email");

																//for F7team mail 
															}

															if(!pstn_email.equals("NA")) {

																LeadModel ldmd = new LeadModel();
																ldmd.SendEmailforReturntoStocktoF7team(id_lead_m,id_lead);

																LeadModel ldmd2 = new LeadModel();
																ldmd2.SendEmailforReturntoStocktoCustomer(id_lead_m,id_lead);

																LeadModel_Whatsapp ldmd3 = new LeadModel_Whatsapp();
																ldmd3.SendWhatsappforReturntoStocktoCustomer(id_lead_m,id_lead);

																LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();
																ldmd1.SendWhatsappforReturntoStocktoF7team(id_lead_m,id_lead);
															}else {
																LeadModel ldmd = new LeadModel();
																ldmd.SendEmailforReturntoStocktoF7team(id_lead_m,id_lead);


																LeadModel_Whatsapp ldmd3 = new LeadModel_Whatsapp();
																ldmd3.SendWhatsappforReturntoStocktoCustomer(id_lead_m,id_lead);

																LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();
																ldmd1.SendWhatsappforReturntoStocktoF7team(id_lead_m,id_lead);
																//															
															}









															j=1;
														}
													}
												}


											}




										}

										else {
											j=1;


											if(j>0) {
												j=0;
												sql = " select * from O_Assign_Lead_Master where id_lead_m='"+idleadm1+"'" ;
												System.out.println(" select * from O_Assign_Lead_Master where id_lead_m='"+idleadm1+"'" );
												ps = con.prepareStatement(sql);
												rs = ps.executeQuery();
												if (rs.next()) {

													id_sloc1=rs.getString("id_sloc");
													id_loc1=rs.getString("id_loc");
													sql = "select tot_aval_empty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"  and id_product="+id_prod+"";
													System.out.println("select tot_aval_empty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"   and id_product="+id_prod+"");
													ps = con.prepareStatement(sql);
													rs = ps.executeQuery();
													if (rs.next()) {
														System.out.println("update  S_Store_Qty set tot_aval_empty=("+rs.getInt(1)+"+1)  , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"   and id_product="+id_prod+"" );

														stmt.executeUpdate("update  S_Store_Qty set tot_aval_empty=("+rs.getInt(1)+"+1) , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"  and id_product="+id_prod+"" );
														j=1;

														if(j>0) {
															j=0;
															//for F7team mail 
															LeadModel ldmd = new LeadModel();
															ldmd.SendEmailforReturntoStocktoF7team(id_lead_m,id_lead);

															LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();
															ldmd1.SendWhatsappforReturntoStocktoF7team(id_lead_m,id_lead);

															LeadModel_Whatsapp ldmd2 = new LeadModel_Whatsapp();
															ldmd2.SendWhatsappforReturntoStocktoCustomer(id_lead_m,id_lead);


															j=1;

														}



													}
												}
											}
										}//else
									}
								}//if




							}else {

								System.out.println(typeprod+"kkProd");
								sql=" update S_I_Ware_House  set  device_status = 'in_store', sr_no_status=0 where serial_no = '"+chk[i]+"'";

								ps=con.prepareStatement(sql);
								j=ps.executeUpdate();  
								System.out.println(sql);



								if(j>0)
								{
									j=0;
									sql ="update  O_Assign_Lead set status_returnto_str = 1, state='Closed',tagto_me_state='Closed' where id_lead ="+id_lead+" ";
									System.out.println(sql);
									ps=con.prepareStatement(sql);
									j=ps.executeUpdate();

									if(j>0) {
										j=0;
										sql="select * from O_Renew_lead  where id_lead="+id_lead+" ";
										ps = con.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											sql ="update  O_Renew_lead set status_return = 1  where id_lead ="+id_lead+" ";
											System.out.println(sql);
											ps=con.prepareStatement(sql);
											j=ps.executeUpdate();

											if(j>1) {

												if(j>0) {
													j=0;
													sql = " select * from O_Assign_Lead_Master where id_lead_m='"+idleadm1+"'" ;
													System.out.println(" select * from O_Assign_Lead_Master where id_lead_m='"+idleadm1+"'" );
													ps = con.prepareStatement(sql);
													rs = ps.executeQuery();
													if (rs.next()) {

														id_sloc1=rs.getString("id_sloc");
														id_loc1=rs.getString("id_loc");
														sql = "select tot_aval_qty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"  and id_product="+id_prod+"";
														System.out.println("select tot_aval_qty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"   and id_product="+id_prod+"");
														ps = con.prepareStatement(sql);
														rs = ps.executeQuery();
														if (rs.next()) {
															System.out.println("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"+1)  , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"   and id_product="+id_prod+"" );

															stmt.executeUpdate("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"+1) , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"  and id_product="+id_prod+"" );
															j=1;

															if(j>0) {
																j=0;
																//for F7team mail 
																LeadModel ldmd = new LeadModel();
																ldmd.SendEmailforReturntoStocktoF7team(id_lead_m,id_lead);

																LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();
																ldmd1.SendWhatsappforReturntoStocktoF7team(id_lead_m,id_lead);

																LeadModel_Whatsapp ldmd2 = new LeadModel_Whatsapp();
																ldmd2.SendWhatsappforReturntoStocktoCustomer(id_lead_m,id_lead);

																j=1;

															}



														}
													}

												}
											}
										}
										else {
											j=1;


											if(j>0) {
												j=0;
												sql = " select * from O_Assign_Lead_Master where id_lead_m='"+idleadm1+"'" ;
												System.out.println(" select * from O_Assign_Lead_Master where id_lead_m='"+idleadm1+"'" );
												ps = con.prepareStatement(sql);
												rs = ps.executeQuery();
												if (rs.next()) {

													id_sloc1=rs.getString("id_sloc");
													id_loc1=rs.getString("id_loc");
													sql = "select tot_aval_qty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"  and id_product="+id_prod+"";
													System.out.println("select tot_aval_qty , tot_rent_aval_qty from S_Store_Qty  where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"   and id_product="+id_prod+"");
													ps = con.prepareStatement(sql);
													rs = ps.executeQuery();
													if (rs.next()) {
														System.out.println("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"+1)  , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"   and id_product="+id_prod+"" );

														stmt.executeUpdate("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"+1) , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc="+id_sloc1+" and id_loc="+id_loc1+"  and id_product="+id_prod+"" );
														j=1;

														if(j>0) {
															j=0;
															//for F7team mail 
															LeadModel ldmd = new LeadModel();
															ldmd.SendEmailforReturntoStocktoF7team(id_lead_m,id_lead);

															LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();
															ldmd1.SendWhatsappforReturntoStocktoF7team(id_lead_m,id_lead);

															LeadModel_Whatsapp ldmd2 = new LeadModel_Whatsapp();
															ldmd2.SendWhatsappforReturntoStocktoCustomer(id_lead_m,id_lead);

															j=1;

														}



													}
												}
											}
										}//else
									}
								}



							}


						}

						//}
						//}
					}//loop

					if(j>0) {

						sql = "select * from O_Assign_Lead  where  typ_service='Rental' and status_returnto_str=0  and  intiate_rfl=0 and  id_lead_m='"+idleadm1+"'";
						System.out.println("select * from O_Assign_Lead  where typ_service='Rental' and status_returnto_str=0 and id_lead_m='"+idleadm1+"'");
						ps = con.prepareStatement(sql);
						rs = ps.executeQuery();

						if (!rs.next()) {

							j=0;

							System.out.println("update  O_Assign_Lead_Master set status_returnto_str = 1, state='Closed',tagto_me_state='Closed' where id_lead_m ='"+id_lead_m+"' " );

							j=stmt.executeUpdate("update  O_Assign_Lead_Master set status_returnto_str = 1, state='Closed',tagto_me_state='Closed' where id_lead_m ='"+id_lead_m+"' " );

							//if() {
							//mail for lead closed all item return
							//}

						}
						else {
							j=1;
						}

					}


					jobj.put("data",j);
				}

				catch(Exception e)
				{
					System.out.println("Error "+e);
				}


				break;
			case "ExtendRentalDays": 

				try
				{
					String cr_pay_mode="", cr_chequeNo="";

					String dtINV="",pstn_email="";

					int j = 0;		
					String colName="",value1="",sql="",itemName="",slNo="",Rmk="",amt="",
							id_lead="", id_cr_inv_m_hist="",	extend_days="",idleadm1="",prod_status="",return_by="",id_sloc1="",id_loc1="";
					double amt1=0;
					List<String> recipients = new ArrayList<String>();

					stmt = con.createStatement();

					System.out.println("return his"+map);

					idleadm1 = map.get("id_lead_m");

					lead_no = map.get("lead_no");

					String trnsprt_amt = map.get("trnsprt_amt");
					System.out.println("hi"+trnsprt_amt);
					String extend_by=map.get("return_by");
					System.out.println("hhi"+extend_by);
					amt=map.get("amount");




					double amount=Double.parseDouble(amt);
					//String  inv_no=CreateAuotoNumber();
					String inv_no = CreateAuotoNumber();
					System.out.println("return his"+inv_no);


					int serialNo    =   0 ;

					sql="select * from  O_Assign_Lead_Master where id_lead_m='"+idleadm1+"'";
					System.out.println("return his"+sql);
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();

					if (rs.next()) {



						sql = "insert into O_create_inv__msater_history(no_po,att_name,lead_no,dt_ld,cd_src,nm_pstn,pstn_ct,alt_pstn_ct,pstn_email,adhar_no,id_loc,id_sloc,gstin_no,dl_no,address,id_lead_m,inv_no) "
								+ " values('NA','"+rs.getString("att_name")+"','"+rs.getString("lead_no")+"','"+rs.getString("dt_ld")+"', '"+rs.getString("cd_src")+"', '"+rs.getString("nm_pstn")+"' , '"+rs.getString("pstn_ct")+"','"+rs.getString("alt_pstn_ct")+"','"+rs.getString("pstn_email")+"',  '"+rs.getString("adhar_no")+"', '"+rs.getString("id_loc")+"',"
								+ "'"+rs.getString("id_sloc")+"','"+rs.getString("gstin_no")+"','"+rs.getString("dl_no")+"',"
								+ " '"+rs.getString("address")+"' , "+idleadm1+",'"+inv_no+"')";

						System.out.println("return his"+sql);
					}       


					stmt = con.createStatement();
					stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
					rs = stmt.getGeneratedKeys();
					while (rs.next()) {
						id_cr_inv_m_hist = rs.getString(1);
						System.out.println(id_cr_inv_m_hist);
					}


					//					
					for(int i=0; i<chk.length;i++)
					{



						String sr = chk[i];
						Rmk =request.getParameter("rmk"+chk[i]);

						String extension_status=request.getParameter("extension_status"+chk[i]);
						String renew_sr_no=request.getParameter("renew_sr_no"+chk[i]);


						String dt_extend_str1 =request.getParameter("dt_extend_str"+chk[i]);
						String dt_extend_str = dateFormatNeeded.format(userDateFormat.parse(dt_extend_str1));

						dtINV=dt_extend_str;
						String current_exp_dt="";
						prod_status =request.getParameter("prod_status"+chk[i]);
						if(prod_status.equals("Refill")) {
							System.out.println(dt_extend_str+"hh");	
							extend_days=request.getParameter("extend_days"+chk[i]);
							//current_exp_dt = dateFormatNeeded.format(userDateFormat.parse(current_exp_dt1));
							if(extend_days.equals("")||current_exp_dt.equals("")) {
								//it is coming from O_Assign_lead table								
								extend_days=request.getParameter("rental_day"+chk[i]);

								current_exp_dt =request.getParameter("dt_exprent"+chk[i]);

								current_exp_dt = dateFormatNeeded.format(userDateFormat.parse(current_exp_dt));
								System.out.println(current_exp_dt);
							}
						}
						else {
							extend_days=request.getParameter("extend_days"+chk[i]);
							String current_exp_dt1 =request.getParameter("current_exp_dt"+chk[i]);
							current_exp_dt = dateFormatNeeded.format(userDateFormat.parse(current_exp_dt1));
							System.out.println(current_exp_dt);

						}
						String typeservice = request.getParameter("typ_service"+chk[i]);
						String current_inv_no = request.getParameter("current_inv_no"+chk[i]);
						id_lead = request.getParameter("id_lead"+chk[i]);
						//id_lead_m = request.getParameter("id_lead_m"+chk[i]);

						String id_prod = request.getParameter("id_prod"+chk[i]);
						String type_prod = request.getParameter("type_prod"+chk[i]);
						String extend_un_prc = request.getParameter("un_prc"+chk[i]);
						//String extend_un_prc1 = request.getParameter("extend_un_prc"+chk[i]);
						String others=request.getParameter("others"+chk[i]);
						String id_tax1=request.getParameter("id_tax1"+chk[i]);
						String id_tax2=request.getParameter("id_tax2"+chk[i]);
						String tax_val1=request.getParameter("tax_val1"+chk[i]);
						String tax_val2=request.getParameter("tax_val2"+chk[i]);
						String buyback=request.getParameter("buyback"+chk[i]);
						String gr_tot=request.getParameter("gr_tot"+chk[i]);
						String nm_prod=request.getParameter("nm_prod"+chk[i]);
						String ds_product=request.getParameter("ds_product"+chk[i]);
						String cylndr_fill_mt=request.getParameter("cylndr_fill_mt"+chk[i]);
						cr_pay_mode=request.getParameter("cr_pay_mode"+chk[i]);
						String cr_cheque_no=request.getParameter("cr_cheque_no"+chk[i]);


						if(!cr_cheque_no.equals("")) {
							cr_chequeNo=cr_cheque_no;

						}
						else {
							cr_chequeNo="";
						}
						System.out.println("kajol"+renew_sr_no);
						System.out.println(nm_prod);
						System.out.println(ds_product);

						System.out.println(type_prod);
//						sql="select id_loc,id_subl from  S_I_Ware_House where serial_no='"+sr+"'";
//						System.out.println("siware his"+sql);
//						ps = con.prepareStatement(sql);
//						rs = ps.executeQuery();
//
//						if (rs.next()) {
//							id_loc1=rs.getString("id_loc");
//							id_sloc1=rs.getString("id_subl");		
//						}


						//if(type_prod.equals("TYPE-CYLINDER")||type_prod.equals("type-cylinder")) {

							sql="insert into O_Renew_lead(day_extend_by,rmk,extension_status,extend_days ,dt_extend_str,dt_exprent,prod_status,typ_service,current_inv_no,id_lead,id_lead_m,id_prod,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,sr_no,renew_sr_no,nm_prod,ds_product,cr_pay_mode,cr_cheque_no,cylndr_fill_mt,type_prod,lead_no,trnsprt_amt) "
									+ "values("+extend_by+",'"+Rmk+"','"+extension_status+"', '"+extend_days+"' ,'"+dt_extend_str+"','"+current_exp_dt+"','"+prod_status+"','"+typeservice+"','"+inv_no+"','"+id_lead+"','"+idleadm1+"','"+id_prod+"','"+extend_un_prc+"','"+others+"', '"+id_tax1+"','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','"+sr+"','"+renew_sr_no+"','"+nm_prod+"','"+ds_product+"','"+cr_pay_mode+"','"+cr_chequeNo+"','"+cylndr_fill_mt+"','"+type_prod+"','"+lead_no+"','"+trnsprt_amt+"')";
							System.out.println(sql);
							PreparedStatement ps=con.prepareStatement(sql);
							j=ps.executeUpdate();
							if(j>0) {

								System.out.println("update  O_Assign_Lead set current_extend_tot='"+gr_tot+"',extension_status='"+extension_status+"',current_extend_days='"+extend_days+"',current_inv_no='"+inv_no+"',current_extend_exp_dt='"+current_exp_dt+"' ,extend_dt='"+dt_extend_str+"' ,renew_sr_no='"+renew_sr_no+"' ,renew_status='Yes'  where id_lead ='"+id_lead+"'");

								j=stmt.executeUpdate("update  O_Assign_Lead set current_extend_tot='"+gr_tot+"',extension_status='"+extension_status+"',current_extend_days='"+extend_days+"',current_inv_no='"+inv_no+"',current_extend_exp_dt='"+current_exp_dt+"' , extend_dt='"+dt_extend_str+"',renew_sr_no='"+renew_sr_no+"' ,renew_status='Yes'   where id_lead ='"+id_lead+"'");
								//latest-last status and last extend days last unprice  update here at time of extension every time 

//								if(j>0)
//								{
//									j=0;
//									System.out.println("update S_I_Ware_House  set  device_status = 'in_store',filling_status='0' , sr_no_status =0 where serial_no = '"+sr+"'");
//									j=stmt.executeUpdate("update S_I_Ware_House  set  device_status = 'in_store',filling_status='0' , sr_no_status =0 where serial_no = '"+sr+"'");
//									System.out.println(j);
//									if(j>0) {


//										j=0;
//
//										sql = "select tot_aval_empty , tot_rent_aval_qty from S_Store_Qty  where id_sloc='"+id_sloc1+"' and id_loc='"+id_loc1+"'  and id_product="+id_prod+"";
//										System.out.println("select tot_aval_qty , tot_rent_aval_qty from S_Store_Qty  where id_sloc='"+id_sloc1+"' and id_loc='"+id_loc1+"'   and id_product="+id_prod+"");
//										ps = con.prepareStatement(sql);
//										rs = ps.executeQuery();
//										if (rs.next()) {
//											System.out.println("update  S_Store_Qty set tot_aval_empty=("+rs.getInt(1)+"+1)  , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc='"+id_sloc1+"' and id_loc='"+id_loc1+"'  and id_product="+id_prod+"");
//
//											stmt.executeUpdate("update  S_Store_Qty set tot_aval_empty=("+rs.getInt(1)+"+1) , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc='"+id_sloc1+"' and id_loc='"+id_loc1+"'  and id_product="+id_prod+"");
//											j=1;



//											if(j>0) {   
//
//												j=0;
//												System.out.println("update S_I_Ware_House  set  device_status = 'Rental', sr_no_status =1 where serial_no = '"+renew_sr_no+"'");
//
//												j=stmt.executeUpdate("update S_I_Ware_House  set  device_status = 'Rental', sr_no_status =1 where serial_no = '"+renew_sr_no+"'");
//
//												if(j>0) {
//													j=0;
//
//
//													sql = "select tot_aval_qty , tot_rent_aval_qty from S_Store_Qty  where id_sloc='"+id_sloc1+"' and id_loc='"+id_loc1+"'  and id_product="+id_prod+"";
//													System.out.println("select tot_aval_qty , tot_rent_aval_qty from S_Store_Qty  where id_sloc='"+id_sloc1+"' and id_loc='"+id_loc1+"'   and id_product="+id_prod+"");
//													ps = con.prepareStatement(sql);
//													rs = ps.executeQuery();
													//if (rs.next()) {
//														System.out.println("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"-1)  , tot_rent_aval_qty=("+rs.getInt(2)+"+1) where id_sloc='"+id_sloc1+"' and id_loc='"+id_loc1+"'  and id_product="+id_prod+"");
//
//														stmt.executeUpdate("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"-1) , tot_rent_aval_qty=("+rs.getInt(2)+"+1) where id_sloc='"+id_sloc1+"' and id_loc='"+id_loc1+"'  and id_product="+id_prod+"");
//														j=1;

														if(j>0) {

															sql="select * from  M_Asset_Div where id_assetdiv='"+id_prod+"'";
															System.out.println("return his"+sql);
															ps = con.prepareStatement(sql);
															rs = ps.executeQuery();

															if(rs.next()) {

																System.out.println(
																		"insert into O_create_invoice_history(nm_prod ,ds_product,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service ,rental_day,dt_exp_rent,cylndr_fill_mt,id_lead,id_lead_m,id_cr_inv_m_hist,current_inv_no,hsn_cd,current_inv_dt,cr_pay_mode,cr_cheque_no,lead_no) "
																				+ "values('"+ nm_prod + "', '" + ds_product + "','" + renew_sr_no + "','"+ id_prod+"', '1','" + extend_un_prc + "', " + others + " , '"+id_tax1 + "', '"+id_tax2+"','"+tax_val1+"', '"+tax_val2+"',"+buyback+","
																				+ ""+gr_tot+",'" + typeservice+ "'," + extend_days + ", '"+current_exp_dt+"',"+cylndr_fill_mt+","+id_lead+","+idleadm1+","+id_cr_inv_m_hist+", '"+rs.getString("hsn_cd_assetdiv")+"','"+inv_no+"','"+dt_extend_str+"','"+cr_pay_mode+"','"+lead_no+"')");
																j=stmt.executeUpdate(
																		"insert into O_create_invoice_history(nm_prod ,ds_product,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service ,rental_day,dt_exp_rent,cylndr_fill_mt,id_lead,id_lead_m,id_cr_inv_m_hist,current_inv_no,hsn_cd,current_inv_dt,cr_pay_mode,cr_cheque_no,lead_no) "
																				+ "values('"+ nm_prod + "', '" + ds_product + "','" + renew_sr_no + "','"+ id_prod+"', '1','" + extend_un_prc + "'," + others + " , '"+id_tax1 + "', '"+id_tax2+"','"+tax_val1+"', '"+tax_val2+"','"+buyback+"', "
																				+ ""+gr_tot+",'" + typeservice+ "'," + extend_days + ", '"+current_exp_dt+"',  "+cylndr_fill_mt+","+id_lead+","+idleadm1+","+id_cr_inv_m_hist+",'"+rs.getString("hsn_cd_assetdiv")+"', '"+inv_no+"','"+dt_extend_str+"','"+cr_pay_mode+"','"+cr_chequeNo+"','"+lead_no+"')");

															}
														}
//													}
//												}
//											}
//
//										} 
//									}
//								}
//							}
						//}//if
					//	else {

//							sql="insert into O_Renew_lead(day_extend_by,rmk,extension_status,extend_days ,dt_extend_str,dt_exprent,prod_status,typ_service,current_inv_no,id_lead,id_lead_m,id_prod,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,sr_no,renew_sr_no,nm_prod,ds_product,cr_pay_mode,cr_cheque_no,cylndr_fill_mt,type_prod,lead_no) "
//									+ "values("+extend_by+",'"+Rmk+"','"+extension_status+"', '"+extend_days+"' ,'"+dt_extend_str+"','"+current_exp_dt+"','"+prod_status+"','"+typeservice+"','"+inv_no+"','"+id_lead+"','"+idleadm1+"','"+id_prod+"','"+extend_un_prc+"','"+others+"', '"+id_tax1+"','"+id_tax2+"','"+tax_val1+"','"+tax_val2+"','"+buyback+"','"+gr_tot+"','"+sr+"','"+sr+"','"+nm_prod+"','"+ds_product+"','"+cr_pay_mode+"','"+cr_chequeNo+"','"+cylndr_fill_mt+"','"+type_prod+"','"+lead_no+"')";
//							System.out.println(sql);
//							PreparedStatement ps=con.prepareStatement(sql);
//							j=ps.executeUpdate();
//							if(j>0) {
//
//
//								//System.out.println("update  O_Assign_Lead set gr_tot='"+gr_tot1+"',extension_status='"+extension_status+"',extend_days='"+extend_days+"',extend_un_prc='"+extend_un_prc+"',dt_exp_rent ='"+dt_extend_str+"' where id_lead ='"+id_lead+"'");
//
//								//j=stmt.executeUpdate("update  O_Assign_Lead set gr_tot='"+gr_tot1+"',extension_status='"+extension_status+"',extend_days='"+extend_days+"',extend_un_prc='"+extend_un_prc+"',dt_exp_rent='"+dt_extend_str+"' where id_lead ='"+id_lead+"'");
//								System.out.println("update  O_Assign_Lead set current_extend_tot='"+gr_tot+"',extension_status='"+extension_status+"',current_extend_days='"+extend_days+"',current_inv_no='"+inv_no+"',current_extend_exp_dt='"+current_exp_dt+"' ,extend_dt='"+dt_extend_str+"',renew_sr_no='"+renew_sr_no+"' where id_lead ='"+id_lead+"'");
//
//								j=stmt.executeUpdate("update  O_Assign_Lead set current_extend_tot='"+gr_tot+"',extension_status='"+extension_status+"',current_extend_days='"+extend_days+"',current_inv_no='"+inv_no+"',current_extend_exp_dt='"+current_exp_dt+"' , extend_dt='"+dt_extend_str+"' ,renew_sr_no='"+renew_sr_no+"'  where id_lead ='"+id_lead+"'");
//								//latest-last status and last extend days last unprice  update here at time of extension every time 
//
//								if(j>0) {
//
//									sql="select * from  M_Asset_Div where id_assetdiv='"+id_prod+"'";
//									System.out.println("return his"+sql);
//									ps = con.prepareStatement(sql);
//									rs = ps.executeQuery();
//
//									if(rs.next()) {
//
//										System.out.println(
//												"insert into O_create_invoice_history(nm_prod ,ds_product,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service ,rental_day,dt_exp_rent,cylndr_fill_mt,id_lead,id_lead_m,id_cr_inv_m_hist,current_inv_no,hsn_cd,current_inv_dt,cr_pay_mode,cr_cheque_no,lead_no) "
//														+ "values('"+ nm_prod + "', '" + ds_product + "','" + renew_sr_no + "','"+ id_prod+"', '1','" + extend_un_prc + "', " + others + " , '"+id_tax1 + "', '"+id_tax2+"','"+tax_val1+"', '"+tax_val2+"',"+buyback+","
//														+ ""+gr_tot+",'" + typeservice+ "'," + extend_days + ", '"+current_exp_dt+"',"+cylndr_fill_mt+","+id_lead+","+idleadm1+","+id_cr_inv_m_hist+",'"+inv_no+"', '"+rs.getString("hsn_cd_assetdiv")+"','"+dt_extend_str+"','"+cr_pay_mode+"','"+lead_no+"')");
//										j=stmt.executeUpdate(
//												"insert into O_create_invoice_history(nm_prod ,ds_product,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service ,rental_day,dt_exp_rent,cylndr_fill_mt,id_lead,id_lead_m,id_cr_inv_m_hist,current_inv_no,hsn_cd,current_inv_dt,cr_pay_mode,cr_cheque_no,lead_no) "
//														+ "values('"+ nm_prod + "', '" + ds_product + "','" + renew_sr_no + "','"+ id_prod+"', '1','" + extend_un_prc + "'," + others + " , '"+id_tax1 + "', '"+id_tax2+"','"+tax_val1+"', '"+tax_val2+"','"+buyback+"', "
//														+ ""+gr_tot+",'" + typeservice+ "'," + extend_days + ", '"+current_exp_dt+"',  "+cylndr_fill_mt+","+id_lead+","+idleadm1+","+id_cr_inv_m_hist+",'"+inv_no+"','"+rs.getString("hsn_cd_assetdiv")+"', '"+dt_extend_str+"','"+cr_pay_mode+"','"+cr_chequeNo+"','"+lead_no+"')");
//
//									}
//								}
//
//							}
//						}

						//}

					}//loop
					//            	amount+=amt1;
					//            	System.out.println(amount);
					if(j>0) {


						sql="update O_create_inv__msater_history set inv_created_by ="+extend_by+",dt_inv='"+dtINV+"' ,pay_mode='"+cr_pay_mode+"' ,cheque_no='"+cr_chequeNo+"' where  id_cr_inv_m_hist="+id_cr_inv_m_hist+"";

						System.out.println(sql);
						PreparedStatement ps1=con.prepareStatement(sql);
						j=ps1.executeUpdate();

						if(j>0) {
							sql="select * from O_Assign_Lead_Master  where id_lead_m ='"+idleadm1+"'";
							ps1 = con.prepareStatement(sql);
							rs = ps1.executeQuery();
							if(rs.next()) {

								pstn_email=rs.getNString("pstn_email");
							}

							if(!pstn_email.equals("NA")) {

								LeadModel ldmd = new LeadModel();
								ldmd.SendEmailforRenewitemtoF7team(id_lead_m,id_lead);

								LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();//for now it is sending only customer
								ldmd1.SendWhatsappforRenewitemF7team(id_lead_m,id_lead);
							}else {


								LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();//for now it is sending only customer
								ldmd1.SendWhatsappforRenewitemF7team(id_lead_m,id_lead);
							}
						}





						j=1;
						//					System.out.println("update  O_Assign_Lead_Master set tot='"+amount+"' where id_lead_m ='"+idleadm1+"'");
						//					
						//					j=stmt.executeUpdate("update  O_Assign_Lead_Master set tot='"+amount+"' where id_lead_m ='"+idleadm1+"'");
						//				    
					}
					}

					jobj.put("data",j);
				}

				catch(Exception e)
				{
					System.out.println("Error "+e);
				}


				break;
			case "InitiateForRefill": 

				try
				{

					int j = 0;		
					String colName="",value1="",sql="";
					List<String> recipients = new ArrayList<String>();
					con = Common.GetConnection(request);
					stmt = con.createStatement();
					int serialNo    =   0 ;
					String refill_status ="";
					String renew_status = "";
					String refill_renew_status ="";
					String idleadm1 = map.get("id_lead_m");
					for(int i=0; i<chk.length;i++)
					{

						String s = chk[i];
						String id_lead = request.getParameter("id_lead"+chk[i]);
						String id_prod = request.getParameter("id_prod"+chk[i]);
						String prod_status = request.getParameter("prod_status"+chk[i]);
						if(prod_status.equals("Refill")) {
							refill_status ="Yes";
							renew_status = "NO";
							refill_renew_status ="NO";
							
						}
                         if(prod_status.equals("Refill_and_Extend")) {
                        	        refill_status ="NO";
         							renew_status = "NO";
         							refill_renew_status ="Yes";
         							
							
						}
                      
						String typeprod="";
						sql="select typeprod from S_I_Ware_House where serial_no = '"+chk[i]+"'";
						ps = con.prepareStatement(sql);
						rs = ps.executeQuery(); 
						System.out.println(sql);
						if(rs.next()) {
							typeprod=rs.getString("typeprod");
						}
						if(typeprod.equals("TYPE-CYLINDER")||typeprod.equals("type-cylinder")) {

							sql ="update  O_Lead set intiate_rfl = 1, prod_status='"+prod_status+"',refill_status='"+refill_status+"', renew_status ='"+renew_status+"',refill_renew_status='"+refill_renew_status+"' where id_lead ="+id_lead+" ";
							System.out.println(sql);
							ps=con.prepareStatement(sql);
							j=ps.executeUpdate();
							if(j>0) {
								// sql ="update  O_Assign_Lead set status_returnto_str = 1, state='Closed',tagto_me_state='Closed' where id_lead ="+id_lead+" ";
								// System.out.println(sql);//use this line for after
								sql ="update  O_Assign_Lead set intiate_rfl = 1,state='Closed',tagto_me_state ='Closed' ,status_returnto_str='1',prod_status='"+prod_status+"',refill_status='"+refill_status+"', renew_status ='"+renew_status+"',refill_renew_status='"+refill_renew_status+"' where id_lead ="+id_lead+" ";
								System.out.println(sql);
								ps=con.prepareStatement(sql);
								j=ps.executeUpdate();
							}	
						}else {
							j=2;
						}


					}//loop			
					if(j==1) {

						sql = "select * from O_Assign_Lead  where  intiate_rfl='0'  and id_lead_m='"+idleadm1+"'";
						System.out.println("select * from O_Assign_Lead  where  intiate_rfl='0' and id_lead_m='"+idleadm1+"'");
						ps = con.prepareStatement(sql);
						rs = ps.executeQuery();

						if (!rs.next()) {

							j=0;

							System.out.println("update  O_Lead_Master set intiate_rfl_all = 1 where id_lead_m ='"+id_lead_m+"' " );

							j=stmt.executeUpdate("update  O_Lead_Master set intiate_rfl_all = 1 where id_lead_m ='"+id_lead_m+"' " );

							if(j>0) {

								System.out.println("update  O_Assign_Lead_Master set intiate_rfl_all = 1 refill_status='"+refill_status+"' where id_lead_m ='"+id_lead_m+"' " );
										
								j=stmt.executeUpdate("update  O_Assign_Lead_Master set intiate_rfl_all = 1 where id_lead_m ='"+id_lead_m+"'" ); 
										

							}

						}
						else {
							j=1;
						}

					}

					System.out.println("Error "+jobj);
					jobj.put("data",j);
				
				}

				catch(Exception e)
				{
					System.out.println("Error "+e);
				}

				////			case "CheckExitsVal":
				////				jobj = CheckExitsVal(value, ColName);
				////				break;
			}
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println("kk"+jobj);
		} catch (Exception e) {
			System.out.println(e+"Error in O_rlead.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public JSONObject UpdateDeliverDetails(HashMap<String, String> map,int id_emp_user,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		String id="",amt="" ,refund="" ,due_amount="",colct_amount="",tag_by="" ,Rental_day="",reason_visit="",paymode="",deliver_by="",dt_deliver="";
		String query1="",cheque_no="",pstn_email="",pstn_ct="";
		DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		JSONObject jobj=new JSONObject();
		String result_add_prod = "";

		//	        if (additional_prod.length > 0) {
		//	            StringBuilder sb = new StringBuilder();
		//
		//	            for (String s : additional_prod) {
		//	                sb.append(s).append(",");
		//	            }
		//
		//	            result_add_prod = sb.deleteCharAt(sb.length() - 1).toString();
		//	        }
		try
		{
			rs = Common.GetColumns("O_Deliver_Detail_Lead",  request);


			id=map.get("id_lead_m");
			amt=map.get("amount");
			refund=map.get("refund_amt");
			colct_amount=map.get("colct_amount");
			due_amount=map.get("due_amount");
			tag_by=map.get("tag_by");
			Rental_day=map.get("rental_day");
			reason_visit=map.get("reason_visit");
			paymode=map.get("pay_mode");
			cheque_no=map.get("cheque_no");

			if(!cheque_no.equals("")) {
				cheque_no=map.get("cheque_no");
			}else{
				cheque_no="-";
			}



			deliver_by=map.get("deliver_by");
			dt_deliver=map.get("dt_deliver");

			while (rs.next())
			{

				if(rs.getString(1) !="id_deliver" )
				{		
					if (map.containsKey(rs.getString(1)))
					{

						if(!rs.getString(1).equals("add_prod")||!rs.getString(1).equals("lead_no") )
						{
							System.out.println(rs.getString(1));
							if(colName.equals(""))
							{

								colName += rs.getString(1);
								value += "'"+ map.get(rs.getString(1))+"'";
							}
							else
							{
								colName +=","+ rs.getString(1);
								value +=", '"+ map.get(rs.getString(1))+"'";




							}
						}

					}
				}
			}

		}
		catch(Exception e)
		{
			System.out.println(e+"Some error in deliver lead.");
		}

		try 
		{
			String query="select lead_no,* from O_Assign_Lead_Master where   id_lead_m='"+id+"'";
			System.out.println(query);
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if(rs.next()) {

				String lead_no=rs.getString("lead_no");
				String id_loc=rs.getString("id_loc");
				String id_sloc=rs.getString("id_sloc");
				pstn_email = rs.getString("pstn_email");
				tag_by = rs.getString("tag_by");
				System.out.println(lead_no);
				query="insert into O_Deliver_Detail_Lead("+colName+",delivery_status,lead_no) values("+value+",'1','"+lead_no+"')";

				System.out.println(query);
			//}




			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j>0)
			{
				//j=1;

				if(reason_visit.equals("New_Installation")) {
					j=0;

					System.out.println("newinstallation");
					query1 ="update  O_Assign_Lead set status_deliver_ld = 1, state='Closed', tagto_me_state='Closed',deliver_by='"+deliver_by+"',dt_deliver='"+dt_deliver+"' where id_lead_m ="+id+"";
					System.out.println(query1);
					PreparedStatement ps1=con.prepareStatement(query1);
					j=ps1.executeUpdate();

					if(j>0) {
						//
						j=0;




						query1 ="update  O_Assign_Lead_Master set pay_mode='"+paymode+"',status_deliver_ld = 1,state='Closed',tagto_me_state='Closed',deliver_by='"+deliver_by+"',dt_deliver='"+dt_deliver+"' where id_lead_m ="+id+"";
						System.out.println(query1);
						PreparedStatement ps11=con.prepareStatement(query1);
						j=ps11.executeUpdate();

						if(j>0) {
							j=0;
							query1 ="update O_Create_Invoice_Master set pay_mode='"+paymode+"',cheque_no='"+cheque_no+"' where id_lead_m ="+id+"";
							System.out.println(query1);
							PreparedStatement ps12=con.prepareStatement(query1);
							j =ps12.executeUpdate();


							if(j>0) {

								j=0;
								/*
								 * query1="select pstn_email,tag_by from O_Assign_Lead_Master  where  id_lead_m ="
								 * +id+"";
								 * 
								 * ps = con.prepareStatement(query1); rs = ps.executeQuery(); if (rs.next()) {
								 * pstn_email = rs.getString("pstn_email"); tag_by = rs.getString("tag_by");
								 * //pstn_ct = rs.getString("pstn_ct");
								 * 
								 * }
								 */
								LeadModel ldmd = new LeadModel();
								ldmd.SendEmailToAssignee1(tag_by,id, request);

								LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();
								ldmd1.SendWhatsAppToAssignee1(tag_by,id, request);

								if(!pstn_email.equals("NA")) {

									LeadModel ldmd2 = new LeadModel();
									ldmd2.SendEmailToCustomer(deliver_by,id, request);

									LeadModel_Whatsapp ldmd21 = new LeadModel_Whatsapp();
									ldmd21.SendWhatsAppToCustomer(deliver_by,id, request);

									j=1;
								}
								else {

									System.out.println("NONA");
									LeadModel_Whatsapp ldmd21 = new LeadModel_Whatsapp();
									ldmd21.SendWhatsAppToCustomer(deliver_by,id, request);

									j=1;
								}

							}
						}

					} 

				}//rent
				else if(reason_visit.equals("Refill_Installation"))
				{
					System.out.println("refiilinstallation");
                    String id_prod="";
                    String count[] = request.getParameterValues("count");
                    System.out.println(count);
					for (int i = 0; i < count.length; i++) {
						 
						 String cd_prod1 = request.getParameter("id_prod" + count[i] + "");
						if (!cd_prod1.isEmpty()) {
							String	sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod1 + "'";
							ps = con.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								id_prod = rs.getString("id_assetdiv");
								
							}

						String pickup_sr_no = request.getParameter("prev_sr_no" + count[i] + "");
						String id_lead = request.getParameter("id_lead" + count[i] + "");
					
						query1 ="update  O_Assign_Lead set status_deliver_ld = 1, tagto_me_state='Inprogress' ,deliver_by='"+deliver_by+"' ,dt_deliver='"+dt_deliver+"' where id_lead ="+id_lead+"";
						System.out.println(query1);
						PreparedStatement ps1=con.prepareStatement(query1);
						j=ps1.executeUpdate();
						
						if(j>0) {
							
							query1 = "select tot_aval_empty , tot_rent_aval_qty from S_Store_Qty  where id_sloc='"+id_sloc+"' and id_loc='"+id_loc+"'  and id_product="+id_prod+"";
							System.out.println("select tot_aval_empty , tot_rent_aval_qty from S_Store_Qty  where id_sloc='"+id_sloc+"' and id_loc='"+id_loc+"'   and id_product="+id_prod+"");
							ps = con.prepareStatement(query1);
							rs = ps.executeQuery();
							stmt = con.createStatement();
							if (rs.next()) {
							
								System.out.println("update  S_Store_Qty set tot_aval_empty=("+rs.getInt(1)+"+1)  , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc='"+id_sloc+"' and id_loc='"+id_loc+"'  and id_product="+id_prod+"");

								stmt.executeUpdate("update  S_Store_Qty set tot_aval_empty=("+rs.getInt(1)+"+1) , tot_rent_aval_qty=("+rs.getInt(2)+"-1) where id_sloc='"+id_sloc+"' and id_loc='"+id_loc+"'  and id_product="+id_prod+"");
								j=1;
                               


								if(j>0) {   

									j=0;
									System.out.println("update S_I_Ware_House  set  device_status = 'in_store',filling_status=0 ,sr_no_status =0 where serial_no = '"+pickup_sr_no+"'");

									j=stmt.executeUpdate("update S_I_Ware_House  set  device_status = 'in_store',filling_status=0, sr_no_status =0 where serial_no = '"+pickup_sr_no+"'");

							
								}
						}//
						
						
						}
						}
					
					
					query1 ="update  O_Assign_Lead set status_deliver_ld = 1, tagto_me_state='Inprogress' ,deliver_by='"+deliver_by+"' ,dt_deliver='"+dt_deliver+"' where id_lead_m ="+id+"";
					System.out.println(query1);
					PreparedStatement ps1=con.prepareStatement(query1);
					j=ps1.executeUpdate();

					if(j>0) {
						//
						j=0;

						if(due_amount.equals("0")&& due_amount.equals("0.00")) {

							query = "update O_Assign_Lead_Master set pay_mode='"+paymode+"',cheque_no='"+cheque_no+"', status_deliver_ld = 1, tagto_me_state='Inprogress',deliver_by='"+deliver_by+"',dt_deliver='"+dt_deliver+"' ,colct_amount='"+colct_amount+"' , due_amount='"+due_amount+"' where id_lead_m ="+id+"";
							System.out.println(query);
							PreparedStatement ps11 = con.prepareStatement(query);
							j= ps11.executeUpdate();




							if(j>0) {

								j=0;
								query1 ="update O_Create_Invoice_Master set pay_mode='"+paymode+"',cheque_no='"+cheque_no+"' where id_lead_m ="+id+"";
								System.out.println(query1);
								PreparedStatement ps12=con.prepareStatement(query1);
								j =ps12.executeUpdate();

								if(j>0) {
									
									
								
									

									LeadModel ldmd = new LeadModel();
									ldmd.SendEmailToAssignee1(tag_by,id, request);

									LeadModel ldmd1 = new LeadModel();
									ldmd1.SendEmailToCustomer(deliver_by,id, request);

									j=1;
								  
							}
								}
						}else {

							query = "update O_Assign_Lead_Master set pay_mode='"+paymode+"',cheque_no='"+cheque_no+"', status_deliver_ld = 1, tagto_me_state='Inprogress',deliver_by='"+deliver_by+"',dt_deliver='"+dt_deliver+"' ,colct_amount='"+colct_amount+"' , due_amount='"+due_amount+"'  where id_lead_m ="+id+"";
							System.out.println(query);
							PreparedStatement ps11 = con.prepareStatement(query);
							j= ps11.executeUpdate();




							if(j>0) {

								j=0;
								query1 ="update O_Create_Invoice_Master set pay_mode='"+paymode+"',cheque_no='"+cheque_no+"' where id_lead_m ="+id+"";
								System.out.println(query1);
								PreparedStatement ps12=con.prepareStatement(query1);
								j =ps12.executeUpdate();

								if(j>0) {
									j=0;
									query1="select pstn_email,tag_by from O_Assign_Lead_Master  where  id_lead_m ="+id+"";  

									ps = con.prepareStatement(query1);
									rs = ps.executeQuery();
									if (rs.next()) {
										pstn_email = rs.getString("pstn_email");
										tag_by = rs.getString("tag_by");
										//pstn_ct = rs.getString("pstn_ct");

									}
									LeadModel ldmd = new LeadModel();
									ldmd.SendEmailToAssignee1(tag_by,id, request);

									LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();
									ldmd1.SendWhatsAppToAssignee1(tag_by,id, request);

									if(!pstn_email.equals("NA")) {

										LeadModel ldmd2 = new LeadModel();
										ldmd2.SendEmailToCustomer(deliver_by,id, request);

										LeadModel_Whatsapp ldmd21 = new LeadModel_Whatsapp();
										ldmd21.SendWhatsAppToCustomer(deliver_by,id, request);

										j=1;
									}
									else {

										System.out.println("NONA");
										LeadModel_Whatsapp ldmd21 = new LeadModel_Whatsapp();
										ldmd21.SendWhatsAppToCustomer(deliver_by,id, request);

										j=1;
									}


								}  
							}
						}


					}


					} 
					}
			else{
					query1 ="update  O_Assign_Lead set status_deliver_ld = 1, tagto_me_state='Inprogress' ,deliver_by='"+deliver_by+"' ,dt_deliver='"+dt_deliver+"' where id_lead_m ="+id+"";
					System.out.println(query1);
					PreparedStatement ps1=con.prepareStatement(query1);
					j=ps1.executeUpdate();

					if(j>0) {
						//
						j=0;

						if(due_amount.equals("0")&& due_amount.equals("0.00")) {

							query = "update O_Assign_Lead_Master set pay_mode='"+paymode+"',cheque_no='"+cheque_no+"', status_deliver_ld = 1, tagto_me_state='Inprogress',deliver_by='"+deliver_by+"',dt_deliver='"+dt_deliver+"' ,colct_amount='"+colct_amount+"' , due_amount='"+due_amount+"' where id_lead_m ="+id+"";
							System.out.println(query);
							PreparedStatement ps11 = con.prepareStatement(query);
							j= ps11.executeUpdate();




							if(j>0) {

								j=0;
								query1 ="update O_Create_Invoice_Master set pay_mode='"+paymode+"',cheque_no='"+cheque_no+"' where id_lead_m ="+id+"";
								System.out.println(query1);
								PreparedStatement ps12=con.prepareStatement(query1);
								j =ps12.executeUpdate();

								if(j>0) {
									j=0;

									LeadModel ldmd = new LeadModel();
									ldmd.SendEmailToAssignee1(tag_by,id, request);

									LeadModel ldmd1 = new LeadModel();
									ldmd1.SendEmailToCustomer(deliver_by,id, request);

									j=1;
								}  
							}
						}else {

							query = "update O_Assign_Lead_Master set pay_mode='"+paymode+"',cheque_no='"+cheque_no+"', status_deliver_ld = 1, tagto_me_state='Inprogress',deliver_by='"+deliver_by+"',dt_deliver='"+dt_deliver+"' ,colct_amount='"+colct_amount+"' , due_amount='"+due_amount+"'  where id_lead_m ="+id+"";
							System.out.println(query);
							PreparedStatement ps11 = con.prepareStatement(query);
							j= ps11.executeUpdate();




							if(j>0) {

								j=0;
								query1 ="update O_Create_Invoice_Master set pay_mode='"+paymode+"',cheque_no='"+cheque_no+"' where id_lead_m ="+id+"";
								System.out.println(query1);
								PreparedStatement ps12=con.prepareStatement(query1);
								j =ps12.executeUpdate();

								if(j>0) {
									j=0;
									query1="select pstn_email,tag_by from O_Assign_Lead_Master  where  id_lead_m ="+id+"";  

									ps = con.prepareStatement(query1);
									rs = ps.executeQuery();
									if (rs.next()) {
										pstn_email = rs.getString("pstn_email");
										tag_by = rs.getString("tag_by");
										//pstn_ct = rs.getString("pstn_ct");

									}
									LeadModel ldmd = new LeadModel();
									ldmd.SendEmailToAssignee1(tag_by,id, request);

									LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();
									ldmd1.SendWhatsAppToAssignee1(tag_by,id, request);

									if(!pstn_email.equals("NA")) {

										LeadModel ldmd2 = new LeadModel();
										ldmd2.SendEmailToCustomer(deliver_by,id, request);

										LeadModel_Whatsapp ldmd21 = new LeadModel_Whatsapp();
										ldmd21.SendWhatsAppToCustomer(deliver_by,id, request);

										j=1;
									}
									else {

										System.out.println("NONA");
										LeadModel_Whatsapp ldmd21 = new LeadModel_Whatsapp();
										ldmd21.SendWhatsAppToCustomer(deliver_by,id, request);

										j=1;
									}


								}  
							}
						}


					}


				} 



			}//



			System.out.println(jobj);
			jobj.put("data",j);

		}
			}
		
		catch (Exception e)
		{

			e.printStackTrace();
			System.out.println(e);

		}



		System.out.println("HIII"+jobj);

		return jobj;
	}


	public JSONObject UpdateDeliverlivetrackingDetails(String id, String delivery_man_id ,String latitude ,String longitude,String time_stamp ,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		System.out.println(time_stamp);
		String query1="";
		DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  


		JSONObject jobj=new JSONObject();





		try 
		{


			time_stamp = dateFormatNeeded.format(userDateFormat.parse(time_stamp));


			System.out.println(time_stamp);

			String query=" select * from  delivery_man_locations where id_lead_m='"+id+"'";


			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {

				query ="update  delivery_man_locations set delivery_man_id = '"+delivery_man_id+"',latitude='"+latitude+"',longitude='"+longitude+"' ,time_stamp ='"+time_stamp+"' where id_lead_m ="+id+" ";
				System.out.println(query);
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();
			}
			else {

				query="insert into delivery_man_locations(id_lead_m,delivery_man_id,latitude,longitude,time_stamp)"
						+ " values('"+id+"','"+delivery_man_id+"','"+latitude+"','"+longitude+"','"+time_stamp+"')";

			}


			System.out.println(query);



			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();



			System.out.println(jobj);
			jobj.put("data",j);

		}
		catch (Exception e)
		{

			e.printStackTrace();
			System.out.println(e);

		}



		System.out.println("HIII"+jobj);

		return jobj;
	}






	public JSONObject UpdateEditDeliverDetails(String id)
	{

		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";


			/*  if (!id.equals("0")) {*/


			sql="select distinct led.*,(replace(convert(NVARCHAR, led.dt_deliver, 103), ' ', '-')) as dtdeliver"
					+" from O_Deliver_Detail_Lead led,"
					+ "O_Assign_Lead ld where led.id_lead_m=ld.id_lead_m and delivery_status =1 and led.id_lead_m ="+id+" ";
			//	}
			System.out.println("updatedeliver"+sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			while (rs.next()) {
				JSONObject jobj2 = new JSONObject();
				String relativeWebPath1 = "InvoiceScanFile/"+rs.getString("proof_cltn_file");
				String relativeWebPath2 = "InvoiceScanFile/"+rs.getString("idproof_file");
				String relativeWebPath3 = "InvoiceScanFile/"+rs.getString("equpment_file");

				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i);
					jobj2.put(name, rs.getString(name));
					jobj2.put("path_forproofofcltn_file",relativeWebPath1);
					jobj2.put("path_foridproof_file",relativeWebPath2);
					jobj2.put("path_forequpment_file",relativeWebPath3);



				}
				jarr.put(jobj2);
			}
			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println(e+"sql error in Assign_Lead.");
		}
		//System.out.println(jobj+"hi");
		return jobj;
	}


	//new	
	public JSONObject DisplayforRefillLead(String id,  String searchWord, String dt_frm, String dt_to,String state, String UserType,int id_emp_user) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		System.out.println("Newkk");
		try {
			String sql = "";

			if (!UserType.equals("SUPER")) {

				sql=" select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld,l.nm_loc,ms.nm_subl "  
						+" from O_Assign_Lead_Master ledm ,O_Assign_lead led , O_lead ld , M_Subloc ms ,M_Loc l,M_Emp_user emp where " 
						+ " led.status_cord_ld = 1 and ledm.status_deliver_ld = 1  and led.intiate_rfl=1  and ld.cr_lead_refill_status=0 " + 
						"and ledm.id_sloc=ms.id_sloc  and ledm.id_loc=l.id_loc and ledm.id_lead_m=led.id_lead_m  and ld.id_lead=led.id_lead " ;
						//and led.status_returnto_str=0 " 	;
				//						     +" and  led.status_returnto_str=0 and ledm.id_sloc=ms.id_sloc and  (nm_pstn like 'searchWord%' or cd_emp like 'searchWord%' or pstn_ct like 'searchWord%' or pstn_email like 'searchWord%' or alt_pstn_ct like 'searchWord%' or " 
				//							+" address like 'searchWord%' or adhar_no like 'searchWord%') order by nm_pstn ";


				//System.out.println("New"+sql);
			}
			else {

				sql=" select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld,l.nm_loc,ms.nm_subl "  
						+" from O_Assign_Lead_Master ledm ,O_Assign_lead led ,O_lead ld , M_Subloc ms ,M_Loc l,M_Emp_user emp where " 
						+ " led.status_cord_ld = 1 and ledm.status_deliver_ld = 1  and led.intiate_rfl=1  and ld.cr_lead_refill_status=0 " + 
						"and ledm.id_sloc=ms.id_sloc  and ledm.id_loc=l.id_loc and  ledm.id_lead_m=led.id_lead_m  and ld.id_lead=led.id_lead ";
						//and led.status_returnto_str=0 " 	;
				//						     +" and  led.status_returnto_str=0 and ledm.id_sloc=ms.id_sloc and  (nm_pstn like 'searchWord%' or cd_emp like 'searchWord%' or pstn_ct like 'searchWord%' or pstn_email like 'searchWord%' or alt_pstn_ct like 'searchWord%' or " 
				//							+" address like 'searchWord%' or adhar_no like 'searchWord%') order by nm_pstn ";



			}


			if (!id.equals("0")) {
				sql="select distinct invm.inv_no, led.*,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtld ,"
						+"led_m.inv_no,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent ," 
						+ "(replace(convert(NVARCHAR, led_m.dt_billing, 103), ' ', '-')) as dtbill,led_m.*,qt.tot_aval_qty as " 
						+ "in_stoc_qty, nm_src, o_led.id_src as idsrc, tax.nm_tax1,tax.nm_tax2,ma.type_grp, invm.tot_inv_amt   from O_Assign_Lead_Master led_m, " 
						+"O_Assign_Lead led ,O_lead_Master o_led, O_Create_Invoice_Master invm,M_Asset_Div ma ,S_Store_Qty qt , M_Subloc ms,M_Loc l ," 
						+"O_Deliver_Detail_Lead del,M_Tax tax where led.status_returnto_str=1  and   led.intiate_rfl=1 and  " 
						+"led.typ_service='Rental' and led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and " 
						+"led.id_tax1=tax.id_tax and led.id_tax2=tax.id_tax and led.id_lead_m=led_m.id_lead_m " 
						+"and led_m.id_lead_m=invm.id_lead_m and qt.id_sloc=led_m.id_sloc and qt.id_product=led.id_prod and " 
						+"ma.id_assetdiv=led.id_prod and led_m.id_lead_m ='"+id+"' and led_m.id_lead_m=o_led.id_lead_m and led_m.status_cord_ld = 1 " 
						+"and led_m.status_deliver_ld = 1 " ;
				System.out.println("Newedit"+sql);
			}
			//System.out.println("New"+sql);
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
			System.out.println(e+"sql error in Assign_Lead.");
		}
		System.out.println(jobj+"hi");
		return jobj;
	}



	public JSONObject DisplayFillClosedLead(String id, String id_lead_m, String no_asset, String dt_frm, String dt_to,String state, String UserType,int id_emp_user,String prodstatus,String searchWord) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			if (!no_asset.equals("")) {
				sql = "select * from O_Assign_Lead where id_lead = " + no_asset + " and status_cord_ld=0";
			}
			if (id_lead_m.equals("") && no_asset.equals("")) {
				if (!UserType.equals("SUPER")) {

					
					
				

						sql = "select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as installdt,CONVERT(varchar(15),  CAST(ld_time AS TIME), 100) as my_time ,"  

								+ "(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as dttag," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.to_asign_cordi=emp.id_emp_user ) assignTo," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.tag_by=emp.id_emp_user ) TagBy," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.tag_to_emp=emp.id_emp_user ) TagTo," 
								+ "nm_subl, nm_loc ,emp.nm_emp as approveBy  from O_Assign_Lead_Master ledm ,O_Assign_Lead led, " 
								+ "M_Subloc ms ,M_Loc l,M_Emp_user emp where  led.state='Closed' and  ledm.id_lead_m=led.id_lead_m and " 
								+ "led.tagto_me_state='Closed'   and ledm.id_sloc=ms.id_sloc and  ledm.id_loc=l.id_loc and  ledm.fill_lead='Yes' and " 
								+ "ledm.tag_by=emp.id_emp_user   and (ledm.lead_no like '"+searchWord+"%' or dt_ld like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";


					
				}
				else {
				

						sql = "select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as installdt,CONVERT(varchar(15),  CAST(ld_time AS TIME), 100) as my_time ,"  

								+ "(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as dttag," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.to_asign_cordi=emp.id_emp_user ) assignTo," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.tag_by=emp.id_emp_user ) TagBy," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.tag_to_emp=emp.id_emp_user ) TagTo," 
								+ "nm_subl,nm_loc , emp.nm_emp as approveBy  from O_Assign_Lead_Master ledm ,O_Assign_Lead led, " 
								+ "M_Subloc ms ,M_Loc l,M_Emp_user emp where   led.state='Closed' and  ledm.id_lead_m=led.id_lead_m " 
								+ "led.tagto_me_state='Closed'  and ledm.id_sloc=ms.id_sloc and ledm.id_loc=ms.id_loc and  ledm.fill_lead='Yes' and " 
								+ "ledm.tag_by=emp.id_emp_user  and (ledm.lead_no like '"+searchWord+"%' or dt_ld like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";

					

				}
			}
			
			if (!id.equals("0")) {

				
			
					 
					
				
						sql = "select distinct led.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as installdt ,(replace(convert(NVARCHAR, ledm.dt_billing, 103), ' ', '-')) as dtbill ,ledm.*,t.nm_tax1,t.nm_tax2 from O_Assign_Lead_Master ledm ,O_Assign_Lead led , M_Subloc ms,M_Loc l,M_Tax t  where  ledm.id_sloc=ms.id_sloc and  led.id_tax1=t.id_tax  and "
								+"ledm.id_loc=l.id_loc and led.id_lead_m=ledm.id_lead_m  and ledm.id_lead_m ="+id+" and ledm.status_cord_ld = 1 and ledm.state='Closed'";
						
					
				
					
				}
			
			System.out.println("New"+sql);
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
			System.out.println(e+"sql error in Assign_Lead.");
		}
		//System.out.println(jobj+"hi");
		return jobj;
	}

	
	public JSONObject DisplayCrLead(String id, String id_lead_m, String no_asset, String dt_frm, String dt_to,String state, String UserType,int id_emp_user,String prodstatus,String searchWord) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			if (!no_asset.equals("")) {
				sql = "select * from O_Assign_Lead where id_lead = " + no_asset + " and status_cord_ld=0";
			}
			if (id_lead_m.equals("") && no_asset.equals("")) {
				if (!UserType.equals("SUPER")) {

					if(state.equals("New")) {
						sql = "select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld,CONVERT(varchar(15),  CAST(ld_time AS TIME), 100) as my_time ," 
								+"(select emp.nm_emp  from M_Emp_user emp  where ledm.to_asign_cordi=emp.id_emp_user ) assignTocr, "  
								+"nm_subl,nm_loc ,emp.nm_emp as createdby from O_Lead_Master ledm ,O_Lead led, M_Subloc ms , M_Loc l,M_Emp_user emp where " 
								+" led.status_proceed = 0 and led.state='New' and ledm.id_sloc=ms.id_sloc  and ledm.id_lead_m=led.id_lead_m and  " 
								+"ledm.id_loc=l.id_loc and ledm.created_by=emp.id_emp_user  and (ledm.lead_no like '"+searchWord+"%' or dt_ld like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";
						
						System.out.println("New"+sql);
					}
					else if(state.equals("Inprogress")) {

						sql = "select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as tagdt,(select emp.nm_emp  from M_Emp_user emp  where ledm.tag_to_emp=emp.id_emp_user ) tagTo,CONVERT(varchar(15),  CAST(ld_time AS TIME), 100) as my_time ,"
								+"(select em.nm_emp  from M_Emp_user em  where ledm.created_by=em.id_emp_user ) createdBy,"
								+"(select e.nm_emp  from M_Emp_user e where ledm.approve_by=e.id_emp_user ) assignedBy,"
								+ "nm_subl,nm_loc, emp.nm_emp as tagBy  from O_Assign_Lead_Master ledm , O_Assign_Lead led,M_Subloc ms ,M_Loc l,M_Emp_user emp where  "
								+ " led.status_cord_ld = 1 and led.state='Inprogress' and ledm.status_deliver_ld = 0 and ledm.id_lead_m=led.id_lead_m  and ledm.id_sloc=ms.id_sloc and  ledm.id_loc=l.id_loc and ledm.tag_by=emp.id_emp_user  and (ledm.lead_no like '"+searchWord+"%' or dt_ld like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";
					}
					else {

						sql = "select distinct  ledm.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as installdt,(REPLACE(CONVERT(NVARCHAR, led.current_extend_exp_dt, 103), ' ', '-')) AS closeddt,CONVERT(varchar(15),  CAST(ld_time AS TIME), 100) as my_time ,"  

								+ "(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as dttag," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.to_asign_cordi=emp.id_emp_user ) assignTo," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.tag_by=emp.id_emp_user ) TagBy," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.tag_to_emp=emp.id_emp_user ) TagTo," 
								+ "nm_subl, nm_loc ,emp.nm_emp as approveBy  from O_Assign_Lead_Master ledm ,O_Assign_Lead led,O_Deliver_Detail_Lead d, " 
								+ "M_Subloc ms ,M_Loc l,M_Emp_user emp where  ledm.state='Closed' and  ledm.id_lead_m=led.id_lead_m and " 
								+ "ledm.tagto_me_state='closed' and ledm.id_lead_m=d.id_lead_m  and ledm.id_sloc=ms.id_sloc and  ledm.id_loc=l.id_loc and " 
								+ "ledm.tag_by=emp.id_emp_user and (ledm.lead_no like '"+searchWord+"%' or dt_ld like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";

						System.out.println("Closed" +sql);
						
						

					}
				}
				else {
					if(state.equals("New")) {
						sql = "select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld,CONVERT(varchar(15),  CAST(ld_time AS TIME), 100) as my_time ," 
								+"(select emp.nm_emp  from M_Emp_user emp  where ledm.to_asign_cordi=emp.id_emp_user ) assignTocr,"  
								+"nm_subl,nm_loc ,emp.nm_emp as createdby  from O_Lead_Master ledm ,O_Lead led, M_Subloc ms , M_Loc l,M_Emp_user emp where" 
								+"   led.status_proceed = 0 and led.state='New' and ledm.id_sloc=ms.id_sloc and  ledm.id_lead_m=led.id_lead_m and" 
								+"ledm.id_loc=l.id_loc and ledm.created_by=emp.id_emp_user and  (ledm.lead_no like '"+searchWord+"%' or dt_ld like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";
						System.out.println("New"+sql);
					}

					else if(state.equals("Inprogress")) {

						sql = "select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as tagdt,(select emp.nm_emp from M_Emp_user emp where ledm.tag_to_emp=emp.id_emp_user ) tagTo,CONVERT(varchar(15),  CAST(ld_time AS TIME), 100) as my_time ,"
								+"(select em.nm_emp  from M_Emp_user em  where ledm.created_by=em.id_emp_user ) createdBy,"
								+"(select e.nm_emp  from M_Emp_user e where ledm.approve_by=e.id_emp_user ) assignedBy,"
								+ "nm_subl,nm_loc ,emp.nm_emp as tagBy from O_Assign_Lead_Master ledm , O_Assign_Lead led,M_Subloc ms,M_Loc l,M_Emp_user emp where   "
								+ " led.status_cord_ld = 1   and led.state='Inprogress' and ledm.id_lead_m=led.id_lead_m and ledm.status_deliver_ld = 0 and  ledm.id_sloc=ms.id_sloc and ledm.id_loc=ms.id_loc  and ledm.tag_by=emp.id_emp_user  and (ledm.lead_no like '"+searchWord+"%' or dt_ld like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";
					}

					else {

						sql = "select  distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as installdt," + 
								"CONVERT(varchar(15),  CAST(ld_time AS TIME), 100) as my_time ,"  

								+ "(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as dttag," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.to_asign_cordi=emp.id_emp_user ) assignTo," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.tag_by=emp.id_emp_user ) TagBy," 
								+ "(select emp.nm_emp  from M_Emp_user emp   where ledm.tag_to_emp=emp.id_emp_user ) TagTo," 
								+ "nm_subl,nm_loc , emp.nm_emp as approveBy   from O_Assign_Lead_Master ledm ,O_Assign_Lead led,O_Deliver_Detail_Lead d, " 
								+ "M_Subloc ms ,M_Loc l,M_Emp_user emp where   ledm.state='Closed' and  ledm.id_lead_m=led.id_lead_m " 
								+ "ledm.tagto_me_state='closed' and ledm.id_lead_m=d.id_lead_m  and ledm.id_sloc=ms.id_sloc and ledm.id_loc=ms.id_loc and  " 
								+ "ledm.tag_by=emp.id_emp_user  and  (ledm.lead_no like '"+searchWord+"%' or dt_ld like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";
								
					}

				}
			}
			//tommorow check this conditon for aply
			else if (!id_lead_m.equals("")) {
				sql = "select id_lead_m,id_lead,quantity,un_prc,mfr,ds_product,id_prod from O_Assign_Lead led where id_lead_m = "
						+ id_lead_m + " and status_cord_ld=0 and and status_proceed = 1";
			}
			if (!id.equals("0")) {

				
				sql="select prod_status from O_lead where id_lead_m="+id+"";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					prodstatus=rs.getString("prod_status");
					System.out.println(prodstatus);
				}
				if(state.equals("New")) {

					if(prodstatus.equals("Fill")) {
						
						System.out.println("fill");
						sql = "select distinct led.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld ,(replace(convert(NVARCHAR, ledm.dt_billing, 103), ' ', '-')) as dtbill ,"
								+"(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,ledm.fill_lead ,ledm.* from O_Lead_Master ledm ,O_Lead led,"
								+ "M_Subloc ms ,M_Loc l where  ledm.id_sloc=ms.id_sloc and ledm.id_loc=l.id_loc and led.id_lead_m=ledm.id_lead_m and  "
								+" ledm.id_lead_m ='"+id+"' and ledm.status_proceed=0 and led.state='New'";
						
					}else {
						
						sql = "select distinct led.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld ,(replace(convert(NVARCHAR, ledm.dt_billing, 103), ' ', '-')) as dtbill ,"
								+"(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,ledm.*,qt.tot_aval_qty as in_stoc_qty from O_Lead_Master ledm ,O_Lead led,S_Store_Qty qt , "
								+ "M_Subloc ms ,M_Loc l where  ledm.id_sloc=ms.id_sloc and ledm.id_loc=l.id_loc and led.id_lead_m=ledm.id_lead_m and  "
								+"qt.id_sloc=ledm.id_sloc and qt.id_product=led.id_prod and ledm.id_lead_m ='"+id+"' and ledm.status_proceed=0 and led.state='New'";
						System.out.println("c"+sql);
					}
				

				}

				else if(state.equals("Inprogress")) {
                        
					if(prodstatus.equals("Fill")) {
						
						System.out.println("fill");

					sql = "select distinct led.*,(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as tagdt ,(replace(convert(NVARCHAR, ledm.dt_billing, 103), ' ', '-')) as dtbill ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,ledm.tag_by as tgbY,ledm.* from O_Assign_Lead_Master ledm ,O_Assign_Lead led , M_Subloc ms ,M_Loc l where  ledm.id_sloc=ms.id_sloc and "
							+"ledm.id_loc=l.id_loc and led.id_lead_m=ledm.id_lead_m and led.status_returnto_str=0 and ledm.id_lead_m ="+id+" and ledm.status_cord_ld = 1 and ledm.state='Inprogress'";
					
				}else {
					sql = "select distinct led.*,(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as tagdt ,(replace(convert(NVARCHAR, ledm.dt_billing, 103), ' ', '-')) as dtbill ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,ledm.tag_by as tgbY,ledm.*,qt.tot_aval_qty as in_stoc_qty from O_Assign_Lead_Master ledm ,O_Assign_Lead led ,S_Store_Qty qt , M_Subloc ms ,M_Loc l where  ledm.id_sloc=ms.id_sloc and "
							+"ledm.id_loc=l.id_loc and led.id_lead_m=ledm.id_lead_m and led.status_returnto_str=0 and qt.id_sloc=ledm.id_sloc and qt.id_product=led.id_prod and ledm.id_lead_m ="+id+" and ledm.status_cord_ld = 1 and ledm.state='Inprogress'";
					
					}
					
					
				}
				
				else  {   
					
					if(prodstatus.equals("Fill")) {
						sql = "select distinct led.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as installdt ,(replace(convert(NVARCHAR, ledm.dt_billing, 103), ' ', '-')) as dtbill ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,ledm.*,t.nm_tax1,t.nm_tax2 ,led.prod_status from O_Assign_Lead_Master ledm ,O_Assign_Lead led ,O_Deliver_Detail_Lead d, M_Subloc ms,M_Loc l,M_Tax t  where  ledm.id_sloc=ms.id_sloc and  led.id_tax1=t.id_tax  and "
								+"ledm.id_loc=l.id_loc and led.id_lead_m=ledm.id_lead_m  and ledm.id_lead_m ="+id+" and ledm.status_cord_ld = 1 and ledm.state='Closed'";
						
					
				}else {
					sql = "select distinct led.*,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as installdt ,(replace(convert(NVARCHAR, ledm.dt_billing, 103), ' ', '-')) as dtbill ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,ledm.*,qt.tot_aval_qty as in_stoc_qty ,t.nm_tax1,t.nm_tax2 ,led.prod_status from O_Assign_Lead_Master ledm ,O_Assign_Lead led ,O_Deliver_Detail_Lead d,S_Store_Qty qt , M_Subloc ms,M_Loc l,M_Tax t  where  ledm.id_sloc=ms.id_sloc and  led.id_tax1=t.id_tax  and "
							+"ledm.id_loc=l.id_loc and led.id_lead_m=ledm.id_lead_m  and qt.id_product=led.id_prod and qt.id_sloc=ledm.id_sloc and ledm.id_lead_m ="+id+" and ledm.status_cord_ld = 1 and ledm.state='Closed'";
					
				}
					
				}
			}
			System.out.println("kajol"+sql);
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
			System.out.println(e+"sql error in Assign_Lead.");
		}
		//System.out.println(jobj+"hi");
		return jobj;
	}
	public JSONObject DisplayPreview( String id, String dt_frm, String dt_to, String UserType,String searchWord) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";

			if (!UserType.equals("SUPER"))
			{

				sql="select distinct top 1000 inv_m.lead_no,inv_m.dt_ld,inv_m.nm_pstn,inv_m.att_name,inv_m.inv_no,inv_m.dt_inv,inv_m.id_cr_inv_m_hist,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtinv,"
						+"(replace(convert(NVARCHAR, inv_m.dt_ld, 103), ' ', '-')) as dtld,nm_subl,nm_loc "  
						+"from  O_create_inv__msater_history inv_m ,O_create_invoice_history inv,M_Loc l, M_Subloc ms,O_Assign_Lead_Master led_m where "
						+" inv_m.id_sloc=ms.id_sloc and  inv_m.id_loc=l.id_loc and inv_m.id_cr_inv_m_hist =inv.id_cr_inv_m_hist and  led_m.id_lead_m=inv_m.id_lead_m and state='inprogress' and (tagto_me_state='inprogress' or tagto_me_state='New') and (inv_m.lead_no like '"+searchWord+"%' or dt_inv like '"+searchWord+"%' or inv_m.nm_pstn like '"+searchWord+"%' or inv_m.inv_no like '"+searchWord+"%' ) order by lead_no ";
				System.out.println("previewinv"+sql);
			}



			else {

				sql="select distinct top 1000 inv_m.lead_no,led_m.remark,led_m.pay_mode,inv_m.dt_ld,inv_m.nm_pstn,inv_m.att_name,inv_m.inv_no,inv_m.dt_inv,inv_m.id_cr_inv_m_hist,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtinv,"
						+"(replace(convert(NVARCHAR, inv_m.dt_ld, 103), ' ', '-')) as dtld,nm_subl,nm_loc "  
						+"from  O_create_inv__msater_history inv_m ,O_create_invoice_history inv,M_Loc l, M_Subloc ms,O_Assign_Lead_Master led_m where "
						+" inv_m.id_sloc=ms.id_sloc and  inv_m.id_loc=l.id_loc and inv_m.id_cr_inv_m_hist =inv.id_cr_inv_m_hist and  led_m.id_lead_m=inv_m.id_lead_m and state='inprogress'and (tagto_me_state='inprogress' or tagto_me_state='New')  and (inv_m.lead_no like '"+searchWord+"%' or dt_inv like '"+searchWord+"%' or inv_m.nm_pstn like '"+searchWord+"%' or inv_m.inv_no like '"+searchWord+"%' ) order by lead_no ";
				System.out.println("previewinv"+sql);
			}


			System.out.println("previewinv"+sql);
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
			System.out.println(e+"sql error in Assign_Lead.");
		}
		System.out.println("previewinv"+jobj);
		return jobj;
	}

	public JSONObject DisplayEditMyleadHasReturn( String id, String id_lead_m, String no_asset, String dt_frm, String dt_to,String tagto_me_state, String UserType,int id_emp_user ,HttpServletRequest request) {
		con = Common.GetConnection(request);
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";




			sql =	"select distinct led.*,led_m.rmk,led_m.prod_status,(replace(convert(NVARCHAR, led_m.dt_return_str, 103), ' ', '-')) as dt_return_strdt ,  "
					+ "(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent , led_m.last_inv_no as latest_inv_no ,"
					+ " tax.nm_tax1,tax.nm_tax2 from O_Return_To_Store led_m, O_Assign_Lead led  ,  "
					+ "O_Deliver_Detail_Lead del,M_Tax tax where led.status_returnto_str=1   and  tagto_me_state='Closed' and  "
					+ "led.id_tax1=tax.id_tax and led.id_tax2=tax.id_tax and led.id_lead=led_m.id_lead and del.id_lead_m=led_m.id_lead_m "
					+ "   and led_m.id_lead_m ='"+id+"'";



			System.out.println("returnstck"+sql);
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
			System.out.println(jobj+"hi");
		} catch (Exception e) {
			System.out.println(e+"sql error in Assign_Lead.");
		}

		return jobj;
	}

	public JSONObject DisplayEditExtensionHasReturn( String id, String id_lead_m, String no_asset, String dt_frm, String dt_to,String tagto_me_state, String UserType,int id_emp_user ,HttpServletRequest request,String EditExtensionReturn) {
		con = Common.GetConnection(request);
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		try {
			String sql = "", idlead="";



			sql="select  id_lead from O_Assign_Lead  where id_lead_m='"+id+"' and status_returnto_str=0  ";
			System.out.println("test"+sql);		
			ps = con.prepareStatement(sql);
			rs1 = ps.executeQuery();
			//			ResultSetMetaData metaData = rs.getMetaData();
			//			int columns = metaData.getColumnCount();
			while (rs1.next()) {
				JSONObject jobj2 = new JSONObject();
				idlead= rs1.getString(1);


				sql = "select distinct rt.*,(replace(convert(NVARCHAR, rt.dt_exprent, 103), ' ', '-')) as dtexprent ,(replace(convert(NVARCHAR, rt.dt_extend_str, 103), ' ', '-')) as dtextend_str,rt.extend_days as rental_day ,"  
						+"led_m.*,del.services,tax.nm_tax1,tax.nm_tax2  from O_Assign_Lead_Master led_m, O_Assign_Lead led ,O_Renew_lead rt, " 
						+"S_Store_Qty qt , M_Subloc ms,M_Loc l,O_Deliver_Detail_Lead del,M_Tax tax where led.status_returnto_str=0  and led.typ_service='Rental' and led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led.id_tax1=tax.id_tax and led.id_tax2=tax.id_tax and "
						+ "led.id_lead_m=led_m.id_lead_m and led.id_lead=rt.id_lead and del.id_lead_m=led_m.id_lead_m and qt.id_sloc=led_m.id_sloc  and rt.id_lead ='"+idlead+"' and led_m.status_cord_ld = 1 and led_m.status_deliver_ld = 1  and led_m.tagto_me_state='Inprogress' and rt.status_return=0  order by id_extend desc";



				System.out.println("test extension"+sql);
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				ResultSetMetaData metaData1 = rs.getMetaData();
				int columns1 = metaData1.getColumnCount();
				if (rs.next()) {

					for (int i1 = 1; i1 <= columns1; i1++) {
						String name1 = metaData1.getColumnName(i1);
						jobj2.put(name1, rs.getString(name1));
					}


				} 

				jarr.put(jobj2);
			}//




			jobj.put("data", jarr);
			System.out.println("test extension"+jobj);
		} catch (Exception e) {
			System.out.println(e+"sql error in Assign_Lead.");
		}

		return jobj;
	}

	//	public JSONObject DisplayForAprovedelivery( String id, String id_lead_m, String no_asset, String dt_frm, String dt_to,String state_aprove, String UserType,int id_emp_user ,HttpServletRequest request) {
	//		con = Common.GetConnection(request);
	//		JSONObject jobj = new JSONObject();
	//		JSONArray jarr = new JSONArray();
	//		try {
	//			String sql = "";
	////			if (!no_asset.equals("")) {
	////				sql = "select * from O_Lead led where id_lead = " + no_asset + " and status_proceed=0";
	////			}
	//			if (id_lead_m.equals("") && no_asset.equals("")) {
	//				if (!UserType.equals("SUPER")) {
	//					
	//					if(state_aprove.equals("NotApproved")) {
	//						sql = "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt,(select emp.nm_emp  from M_Emp_user emp  where led_m.tag_to_emp=emp.id_emp_user ) tagTo,"
	//								+ "nm_subl,nm_loc,led_m.tot, emp.nm_emp as tagBy from O_Assign_Lead_Master led_m , M_Subloc ms ,M_Loc l,M_Emp_user emp where  "
	//								+ " status_cord_ld = 1  and status_apprvdelvr = 0 and  led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led_m.tag_by=emp.id_emp_user  and dt_tag >=(replace(convert(NVARCHAR, '"
	//								+ dt_frm + "', 106), ' ', '-')) and " + "dt_tag <=(replace(convert(NVARCHAR, '" + dt_to
	//								+ "', 106), ' ', '-'))  ";
	//					}
	//					else if(state_aprove.equals("Approved")) {
	//						
	//						sql = "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt,(select emp.nm_emp  from M_Emp_user emp  where led_m.tag_to_emp=emp.id_emp_user ) tagTo,"
	//								+ "nm_subl,nm_loc, emp.nm_emp as tagBy from O_Assign_Lead_Master led_m, M_Subloc ms ,M_Loc l,M_Emp_user emp where  "
	//								+ " status_cord_ld = 1 and status_apprvdelvr = 1    and status_deliver_ld=0  and led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led_m.tag_by=emp.id_emp_user  and dt_tag >=(replace(convert(NVARCHAR, '"
	//								+ dt_frm + "', 106), ' ', '-')) and " + "dt_tag <=(replace(convert(NVARCHAR, '" + dt_to
	//								+ "', 106), ' ', '-'))  ";
	//					}
	//
	//				}
	//				else {
	//					if(state_aprove.equals("NotApproved")) {
	//						sql = "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt,(select emp.nm_emp  from M_Emp_user emp  where led_m.tag_to_emp=emp.id_emp_user ) tagTo,"
	//                                 + "nm_subl,nm_loc,led_m.tot, emp.nm_emp as tagBy from O_Assign_Lead_Master led_m , M_Subloc ms ,M_Loc l,M_Emp_user emp where  "
	//								+ " status_cord_ld = 1  and status_apprvdelvr = 0  and  led_m.id_sloc=ms.id_sloc and  led_m.id_loc=l.id_loc and led_m.tag_by=emp.id_emp_user  and dt_tag >=(replace(convert(NVARCHAR, '"
	//								+ dt_frm + "', 106), ' ', '-')) and " + "dt_tag <=(replace(convert(NVARCHAR, '" + dt_to
	//								+ "', 106), ' ', '-'))  ";
	//				     }
	//					//(select sum((quantity*un_prc)+tax_val1+tax_val2)  from O_Assign_Lead led,O_Assign_Lead_Master led_m where led_m.id_lead_m=led.id_lead_m) total,
	//					else if(state_aprove.equals("Approved")) {
	//						
	//						sql =  "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt,(select emp.nm_emp  from M_Emp_user emp  where led_m.tag_to_emp=emp.id_emp_user ) tagTo,"
	//								+ "nm_subl,nm_loc, emp.nm_emp as tagBy from O_Assign_Lead_Master led_m, M_Subloc ms ,M_Loc l,M_Emp_user emp where  "
	//								+ " status_cord_ld = 1 and status_apprvdelvr = 1 and status_deliver_ld=0 and  led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led_m.tag_by=emp.id_emp_user  and dt_tag >=(replace(convert(NVARCHAR, '"
	//								+ dt_frm + "', 106), ' ', '-')) and " + "dt_tag <=(replace(convert(NVARCHAR, '" + dt_to
	//								+ "', 106), ' ', '-'))  ";
	//				     }
	//					
	//
	//				
	//			       }
	//			}
	//		
	//			else if (!id_lead_m.equals("")) {
	//				
	//				sql = "select id_lead_m,id_lead,quantity,un_prc,mfr,ds_product,id_prod from O_Assign_Lead led where id_lead_m = "
	//						+ id_lead_m + " and status_cord_ld=1";
	//			}
	//			if (!id.equals("0")) {
	//				
	//				if(state_aprove.equals("NotApproved")) {
	//
	//					  sql = "select distinct led.*,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent , "
	//					  		+ "led_m.*,qt.tot_aval_qty as in_stoc_qty,emp.nm_emp as tagBy,tax.nm_tax1,tax.nm_tax2 from  O_Assign_Lead_Master led_m,O_Assign_Lead led ,S_Store_Qty qt , M_Subloc ms ,M_Loc l,M_Tax tax,M_Emp_user emp  where  led_m.tag_by=emp.id_emp_user and led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and "
	//							   +"led.id_lead_m=led_m.id_lead_m and qt.id_sloc=led_m.id_sloc and qt.id_loc=led_m.id_loc and led.id_tax1=tax.id_tax and led.id_tax2=tax.id_tax and  qt.id_product=led.id_prod and led_m.id_lead_m ="+id+" and led_m.status_cord_ld = 1 and  led_m.status_apprvdelvr = 0  ";
	//				}
	//				
	//				else if(state_aprove.equals("Approved"))  {
	//					
	//
	//					  sql = "select distinct led.*,(replace(convert(NVARCHAR, led_m.dt_apprv_delvr, 103), ' ', '-')) as approvdt ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent ," 
	//					  		+"led_m.*,qt.tot_aval_qty as in_stoc_qty,emp.nm_emp as approvBy,tax.nm_tax1,tax.nm_tax2 from O_Assign_Lead_Master led_m, O_Assign_Lead led ," 
	//					  		+"S_Store_Qty qt , M_Subloc ms,M_Loc l,M_Tax tax ,M_Emp_user emp  where  led_m.apprvby_frdelvr=emp.id_emp_user and   led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led.id_tax1=tax.id_tax and led.id_tax2=tax.id_tax and led.id_lead_m=led_m.id_lead_m  and qt.id_sloc=led_m.id_sloc and qt.id_product=led.id_prod and led_m.id_lead_m ='"+id+"' and led_m.status_cord_ld = 1 and led_m.status_deliver_ld=0  and led_m.status_apprvdelvr = 1  ";
	//					  //and led_m.tagto_me_state='Inprogress' ";
	//				}
	//				 
	//				
	//			}
	//			System.out.println("New"+sql);
	//			ps = con.prepareStatement(sql);
	//			rs = ps.executeQuery();
	//			ResultSetMetaData metaData = rs.getMetaData();
	//			int columns = metaData.getColumnCount();
	//			while (rs.next()) {
	//				JSONObject jobj2 = new JSONObject();
	//				for (int i = 1; i <= columns; i++) {
	//					String name = metaData.getColumnName(i);
	//					jobj2.put(name, rs.getString(name));
	//				}
	//				jarr.put(jobj2);
	//			}
	//			jobj.put("data", jarr);
	//		} catch (Exception e) {
	//			System.out.println(e+"sql error in Assign_Lead.");
	//		}
	//		//System.out.println(jobj+"hi");
	//		return jobj;
	//	}


	public JSONObject DisplayAssignTome( String id, String id_lead_m, String no_asset, String dt_frm, String dt_to,String tagto_me_state, String UserType,int id_emp_user ,String searchWord ,HttpServletRequest request) {
		con = Common.GetConnection(request);
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			//			if (!no_asset.equals("")) {
			//				sql = "select * from O_Lead led where id_lead = " + no_asset + " and status_proceed=0";
			//			}
			if (id_lead_m.equals("") && no_asset.equals("")) {
				if (!UserType.equals("SUPER")) {

					if(tagto_me_state.equals("New")) {
						sql = "select distinct led_m.lead_no,led_m.dt_tag,led_m.nm_pstn,led_m.trnsprt_amt ,led_m.refund_amt,led_m.id_lead_m,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt,(select emp.nm_emp  from M_Emp_user emp  where led_m.tag_to_emp=emp.id_emp_user ) assignTo,"
								+ "nm_subl,nm_loc,led_m.tot, emp.nm_emp as assingBy,emp.cont_no as tagBY_contact_no from O_Assign_Lead_Master led_m , M_Subloc ms ,M_Loc l,M_Emp_user emp where  "
								+ " status_cord_ld = 1 and tagto_me_state='New' and led_m.status_apprvdelvr = 0 and  led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led_m.tag_by=emp.id_emp_user and fill_lead='No' and (led_m.lead_no like '"+searchWord+"%' or dt_tag like '"+searchWord+"%' or led_m.nm_pstn like '"+searchWord+"%' or led_m.id_sloc like '"+searchWord+"%' or led_m.id_loc like '"+searchWord+"%' ) order by  led_m.lead_no ";
						//System.out.println("New"+sql); 

					}
					else if(tagto_me_state.equals("Inprogress")) {

						sql = "select distinct led_m.lead_no,led_m.dt_tag,led_m.nm_pstn,led_m.trnsprt_amt,led_m.refund_amt,led_m.id_lead_m,led_m.tot,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt,led.lead_no_refill,led.prev_sr_no,prev_lead_no,led.sr_no_refill,(select emp.nm_emp  from M_Emp_user emp  where led_m.tag_to_emp=emp.id_emp_user ) assignTo,"
								+ "nm_subl,nm_loc, emp.nm_emp as assingBy ,emp.cont_no as tagBY_contact_no,ma.type_grp,led_m.id_proceed_m from O_Assign_Lead_Master led_m,O_Assign_Lead led, M_Subloc ms ,M_Loc l,M_Emp_user emp,M_Asset_Div ma  where led.id_prod=ma.id_assetdiv and   "
								+ " led_m.status_cord_ld = 1 and led_m.status_deliver_ld = 1  and led_m.status_returnto_str = 0 and fill_lead='No' and led_m.tagto_me_state='Inprogress' and led_m.id_proceed_m=led.id_proceed and led_m.intiate_rfl_all = 0  and led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led_m.tag_by=emp.id_emp_user  and (led_m.lead_no like '"+searchWord+"%' or dt_tag like '"+searchWord+"%' or led_m.nm_pstn like '"+searchWord+"%' or led_m.id_sloc like '"+searchWord+"%' or led_m.id_loc like '"+searchWord+"%' ) order by  led_m.lead_no  ";
						System.out.println("Inprogress"+sql); 

					}

				}
				else {
					if(tagto_me_state.equals("New")) {
						sql = "select distinct led_m.lead_no,led_m.dt_tag,led_m.nm_pstn,led_m.trnsprt_amt ,led_m.refund_amt,led_m.id_lead_m,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt,(select emp.nm_emp  from M_Emp_user emp  where led_m.tag_to_emp=emp.id_emp_user ) assignTo,"
								+ "nm_subl,nm_loc,led_m.tot, emp.nm_emp as assingBy,emp.cont_no as tagBY_contact_no from O_Assign_Lead_Master led_m , M_Subloc ms ,M_Loc l,M_Emp_user emp where  "
								+ " status_cord_ld = 1 and tagto_me_state='New' and led_m.status_apprvdelvr = 0 and  led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led_m.tag_by=emp.id_emp_user and fill_lead='No' and (led_m.lead_no like '"+searchWord+"%' or dt_tag like '"+searchWord+"%' or led_m.nm_pstn like '"+searchWord+"%' or led_m.id_sloc like '"+searchWord+"%' or led_m.id_loc like '"+searchWord+"%' ) order by  led_m.lead_no ";
						//System.out.println("New"+sql); 

					}
					//(select sum((quantity*un_prc)+tax_val1+tax_val2)  from O_Assign_Lead led,O_Assign_Lead_Master led_m where led_m.id_lead_m=led.id_lead_m) total,
					else if(tagto_me_state.equals("Inprogress")) {

						sql =  "select distinct led_m.lead_no,led_m.dt_tag,led_m.nm_pstn,led_m.trnsprt_amt,led_m.refund_amt,led_m.id_lead_m ,led_m.tot,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt,led.lead_no_refill,led.prev_sr_no,prev_lead_no,led.sr_no_refill,(select emp.nm_emp  from M_Emp_user emp  where led_m.tag_to_emp=emp.id_emp_user ) assignTo,"
								+ "nm_subl,nm_loc, emp.nm_emp as assingBy,emp.cont_no as tagBY_contact_no, ma.type_grp,led_m.id_proceed_m from O_Assign_Lead_Master led_m, O_Assign_Lead led, M_Subloc ms ,M_Loc l,M_Emp_user emp,M_Asset_Div ma  where led.id_prod=ma.id_assetdiv and  "
								+ " led_m.status_cord_ld = 1 and led_m.status_deliver_ld = 1  and  fill_lead='No' and  led_m.tagto_me_state='Inprogress' and led_m.id_proceed_m=led.id_proceed  and led_m.intiate_rfl_all = 0 and led_m.status_returnto_str = 0 and  led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led_m.tag_by=emp.id_emp_user  and   (led_m.lead_no like '"+searchWord+"%' or dt_tag like '"+searchWord+"%' or led_m.nm_pstn like '"+searchWord+"%' or led_m.id_sloc like '"+searchWord+"%' or led_m.id_loc like '"+searchWord+"%' ) order by lead_no ";
								
						System.out.println("Inprogress"+sql);
					}



				}
			}

			else if (!id_lead_m.equals("")) {

				sql = "select id_lead_m,id_lead,quantity,un_prc,mfr,ds_product,id_prod from O_Assign_Lead led where id_lead_m = "
						+ id_lead_m + " and status_cord_ld=1";
			}
			if (!id.equals("0")) {

				if(tagto_me_state.equals("New")) {

					sql = "select distinct led.*,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent , "
							+ "led_m.*,qt.tot_aval_qty as in_stoc_qty,tax.nm_tax1,tax.nm_tax2 from  O_Assign_Lead_Master led_m,O_Assign_Lead led ,S_Store_Qty qt , M_Subloc ms ,M_Loc l,M_Tax tax where  led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and "
							+"led.id_lead_m=led_m.id_lead_m and qt.id_sloc=led_m.id_sloc and qt.id_loc=led_m.id_loc and led.id_tax1=tax.id_tax and led.id_tax2=tax.id_tax and  qt.id_product=led.id_prod and led_m.id_lead_m ="+id+" and led_m.status_cord_ld = 1 and  led_m.status_apprvdelvr = 0 and led_m.tagto_me_state='New' ";
					//System.out.println("New"+sql);
				}

				else if(tagto_me_state.equals("Inprogress")) {


					sql = "select distinct invm.inv_no, led.*,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as tagdt ,led_m.inv_no,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent ,(replace(convert(NVARCHAR, led_m.dt_billing, 103), ' ', '-')) as dtbill," 
							+"led_m.*,qt.tot_aval_qty as in_stoc_qty, tax.nm_tax1,tax.nm_tax2,ma.type_grp, invm.tot_inv_amt   from O_Assign_Lead_Master led_m, O_Assign_Lead led ,O_Create_Invoice_Master invm,M_Asset_Div ma ," 
							+"S_Store_Qty qt , M_Subloc ms,M_Loc l,O_Deliver_Detail_Lead del,M_Tax tax where led.status_returnto_str=0  and   led.intiate_rfl=0 and  led.typ_service='Rental' and led_m.id_sloc=ms.id_sloc and led_m.id_loc=l.id_loc and led.id_tax1=tax.id_tax and led.id_tax2=tax.id_tax and "
							+ "led.id_lead_m=led_m.id_lead_m  and led_m.id_lead_m=invm.id_lead_m and qt.id_sloc=led_m.id_sloc and qt.id_product=led.id_prod and  ma.id_assetdiv=led.id_prod and led_m.id_lead_m ='"+id+"' and led_m.status_cord_ld = 1 and led_m.status_deliver_ld = 1  ";
					//and led_m.tagto_me_state='Inprogress' ";
					System.out.println("Inprogress"+sql);
				}


			}
			System.out.println("Inprogress"+sql);
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
			System.out.println("Inprogress"+jobj);
		} catch (Exception e) {
			System.out.println(e+"sql error in Assign_Lead.");
		}

		return jobj;
	}


	public JSONObject PreviewInvoiceForLead(int id_emp_user,String id)
	{


		int j=0;
		String query="";
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();

		String sql="";
		try 
		{
			sql="select distinct led_m.*,led.*,lm.*,(replace(convert(NVARCHAR, led_m.dt_inv, 103), ' ', '-')) as dtinv ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dtexprent ,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtld "+ 
					" , (led.quantity*led.un_prc) as total_price , nm_subl ,nm_loc,ms.office_add,t.nm_tax1,t.nm_tax2, (t.per_tax1+t.per_tax2) as tax_rate," + 
					"(led.tax_val1+led.tax_val2) as tot_tax_amt , ma.hsn_cd_assetdiv,ma.ds_assetdiv,ma.nm_assetdiv,led_m.pay_mode,led_m.cheque_no,e.cd_emp as createdby  ,em.nm_emp as deliveryboy,em.cont_no  from  O_create_inv__msater_history led_m ," + 
					"O_create_invoice_history led  ,O_Assign_Lead_Master lm,M_Emp_User e,M_Emp_User em, M_Subloc ms,M_loc l, M_Tax t,M_Asset_Div ma  where  led_m.id_sloc=ms.id_sloc and lm.created_by=e.id_emp_user and lm.tag_to_emp=em.id_emp_user and led_m.id_loc=l.id_loc and  led.id_tax1=t.id_tax and " + 
					"led.id_prod= ma.id_assetdiv  and  lm.id_lead_m=led_m.id_lead_m and  led_m.id_cr_inv_m_hist=led.id_cr_inv_m_hist  and led_m.id_cr_inv_m_hist =  '"+id+"'";
			//			sql=  "select led_m.*,led.*,(replace(convert(NVARCHAR, led_m.dt_tag, 103), ' ', '-')) as dttag ," +  
			//					"(led.quantity*led.un_prc) as total_price , nm_subl ,t.nm_tax1,t.nm_tax2, " + 
			//					"(t.per_tax1+t.per_tax2) as tax_rate,(led.tax_val1+led.tax_val2) as tot_tax_amt , " + 
			//					"ma.hsn_cd_assetdiv,ma.ds_assetdiv from O_Assign_Lead_Master led_m ,O_Assign_Lead led  , M_Subloc ms, " + 
			//					"M_Tax t,M_Asset_Div ma where  led_m.id_sloc=ms.id_sloc and  " + 
			//					"led.id_tax1=t.id_tax and  led.id_prod= ma.id_assetdiv  and " + 
			//					"led.id_lead_m=led.id_lead_m and led.id_lead_m ="+id+" and led_m.ctr_inv_status = 1";
			System.out.println("hhhhh"+sql);
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
					String name=metaData.getColumnName(i).toLowerCase();
					jobj2.put(name,rs.getString(name));
				}
				jarr.put(jobj2);

			}
			json.put("data", jarr);

			String sql1 = "select * from M_Company";	
			ps=con.prepareStatement(sql1);
			rs=ps.executeQuery();

			ResultSetMetaData metaData1 = rs.getMetaData();
			int columns1 = metaData1.getColumnCount();
			while(rs.next())
			{
				JSONObject jobj3 = new JSONObject();
				for(int i=1;i<=columns1;i++)
				{
					String name=metaData1.getColumnName(i).toLowerCase()
							;
					jobj3.put(name,rs.getString(name));
				}
				jarr1.put(jobj3);
			}
			json.put("company",jarr1);




		}
		catch (Exception e)
		{

			e.printStackTrace();
		}


		return json;
	}


	public JSONObject CheckRetrunstkDate(String dt_return_str , String srno)
	{
		JSONObject jobj = new JSONObject();

		String  sql ="select (replace(convert(NVARCHAR, dt_exp_rent, 103), ' ', '-')) as expirydate from O_Assign_Lead where sr_no = '"+srno+"' and dt_exp_rent > '"+dt_return_str+"' ";


		try
		{
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next())
			{
				jobj.put("data", 0);
				jobj.put("dt_return_str", rs.getString(1));
			}
			else
			{
				jobj.put("data", 1);

			}

		}
		catch(Exception e)
		{
			System.out.println("sql error in UnInstall");
		}

		return jobj;


	}
//
	public JSONObject DisplayRefillhistory( String id, String lead_no,String type_product, HttpServletRequest request) {
	con = Common.GetConnection(request);
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	try {
		String sql = "";
		
	sql =  "select distinct led.lead_no,led_m.id_lead_m ,led.lead_no_refill,led.prev_sr_no,prev_lead_no,led.sr_no_refill," + 
			"ma.type_grp,sr_no from O_Assign_Lead_Master led_m, O_Assign_Lead led, M_Subloc ms ,M_Loc l,M_Asset_Div ma " + 
			"where led.id_prod=ma.id_assetdiv and   led_m.status_cord_ld = 1 and led_m.status_deliver_ld = 1  and " + 
			"led_m.fill_lead='No' and  led.tagto_me_state='Closed'  and led.refill_status='Yes'  and led.status_returnto_str=1    and led_m.id_proceed_m=led.id_proceed_m " + 
			"and led.lead_no='"+lead_no+"' and led.id_proceed_m='"+id+"' and ma.type_grp='TYPE-CYLINDER'    ";
							
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
		System.out.println("Inprogress"+jobj);
	} catch (Exception e) {
		System.out.println(e+"sql error in Assign_Lead.");
	}

	return jobj;
}
	
	
	public JSONObject ClosedFillLead(String id)
	{
		JSONObject jobj = new JSONObject();
		int j=0;
		String query = "update O_Assign_Lead_Master set state='Closed',tagto_me_state='Closed' ,fill_lead='Yes' where id_lead_m=" + id + "";
		System.out.println(query);
		try {
			Statement stmt=con.createStatement();
			j=stmt.executeUpdate(query);
		    if(j>0) {
		    	j=0;
		    	 
				
				j=stmt.executeUpdate("update O_Assign_Lead set state='Closed',tagto_me_state='Closed'  where id_lead_m=" + id + "");
				
				
				
		    }
		    if(j>0) {
				j=1;
				jobj.put("data", j);
			}
			

			
			
			
			
			

		}
		catch(Exception e)
		{
			System.out.println(e+"sql error in UnInstall");
		}

		return jobj;


	}

//
	public JSONObject CheckDeliverydate(String deliver_time , String id)
	{
		JSONObject jobj = new JSONObject();

		String  sql ="select ld_time from O_Assign_Lead_Master where id_lead_m ='"+id+"' and  ld_time < '"+deliver_time+"' ";


		try
		{
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next())
			{
				jobj.put("data", 0);
				jobj.put("deliver_time", rs.getString(1));
			}
			else
			{
				jobj.put("data", 1);

			}

		}
		catch(Exception e)
		{
			System.out.println("sql error in UnInstall");
		}

		return jobj;


	}


	//public JSONObject ReturnToStoreDetail(HashMap<String, String> map,int id_emp_user,HttpServletRequest request)
	//	{
	//		String colName="",value="";
	//		int j=0;
	//		String id="",id_sloc="";
	//	    String dt_return="", typ_service="",services="";
	//		JSONObject jobj=new JSONObject();
	//		try
	//	{
	//				rs = Common.GetColumns("O_Return_To_Store",  request);
	//			   
	//				
	//					id=map.get("id_lead_m");
	//					id_sloc = map.get("id_sloc");	
	//					dt_return = map.get("dt_return");
	//				
	//					System.out.println(dt_return);
	//					System.out.println(map);
	//						while (rs.next())
	//						{
	//						
	//							if(rs.getString(1) !="id_return_str")
	//							{	
	//								if (map.containsKey(rs.getString(1)))
	//								    {
	//								    	if(colName.equals(""))
	//								    	{
	//									    	colName += rs.getString(1);
	//									    	value += "'"+ map.get(rs.getString(1))+"'";
	//								    	}
	//								    	else
	//								    	{
	//								    		colName +=","+ rs.getString(1);
	//									    	value +=", '"+ map.get(rs.getString(1))+"'";
	//								    		
	//									    	
	//									    	
	//										  
	//								    	}
	//								    	
	//								    }
	//							  }
	//						}
	//				
	//				}
	//			catch(Exception e)
	//				{
	//					System.out.println("Some error in deliver lead.");
	//				}
	//		
	//		String query="insert into O_Return_To_Store("+colName+",return_by) values("+value+",'"+id_emp_user+"')";
	//
	//		System.out.println(query);
	//		
	//		try 
	//		{
	//			PreparedStatement ps=con.prepareStatement(query);
	//			j=ps.executeUpdate();
	//			if(j > 0)
	//			{
	//				j=0;
	//			
	//			String query1 ="update  O_Assign_Lead_Master set dt_return_str='"+dt_return+"',  state='Closed',tagto_me_state='Closed' where id_lead_m ="+id+" ";
	//			System.out.println(query1);
	//			PreparedStatement ps1=con.prepareStatement(query1);
	//			j=ps1.executeUpdate();
	//			
	//			if(j > 0) {
	//				String cd_prod ="";  
	//				String ds_pro ="",id_prod="",ds_assetdiv="",mfr_assetdiv="" , in_stoc_qty="";
	//				String count[] = request.getParameterValues("count");
	//				stmt = con.createStatement();
	//				j=0;
	//				for (int i = 0; i < count.length; i++) {
	//					 cd_prod = request.getParameter("id_prod" + count[i] + "");
	//					if (!cd_prod.isEmpty()) {
	//					String	sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod + "'";
	//						ps = con.prepareStatement(sql);
	//						rs = ps.executeQuery();
	//						if (rs.next()) {
	//							id_prod = rs.getString("id_assetdiv");
	//							
	//						}
	//						
	//						String quantity = request.getParameter("quantity" + count[i] + "");
	//						String service = request.getParameter("id_lead" + count[i] + "");
	//						typ_service = request.getParameter("typ_service" + count[i] + "");
	//						String id_lead = request.getParameter("id_lead" + count[i] + "");
	//						
	//					System.out.println("id"+id_lead);
	//						int diff=0; 
	//						int  k=0;									
	//						if(typ_service.equals("Rental")) {
	//							
	//							query1 ="update  O_Assign_Lead set status_returnto_str = 1, state='Closed',tagto_me_state='Closed' where id_lead ="+id_lead+" ";
	//	              			System.out.println(query1);
	//              			    PreparedStatement ps2=con.prepareStatement(query1);
	//              			    k=ps2.executeUpdate();
	//							if(k>0) {
	//							
	//								sql = "select tot_aval_qty from S_Store_Qty  where id_sloc="+id_sloc+"  and id_product="+id_prod+"";
	//								System.out.println("select tot_aval_qty from S_Store_Qty  where id_sloc="+id_sloc+"  and id_product="+id_prod+"");
	//								ps = con.prepareStatement(sql);
	//								rs = ps.executeQuery();
	//								if (rs.next()) {
	//									System.out.println("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"+"+quantity+") where id_sloc="+id_sloc+"  and id_product="+id_prod+"" );
	//									
	//									stmt.executeUpdate("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"+"+quantity+") where id_sloc="+id_sloc+"  and id_product="+id_prod+"" );
	//									
	//									 
	//								}
	//							}
	//							
	//						
	//						}
	//	                      if(typ_service.equals("Sale")) {
	//							
	//	                    	query1 ="update  O_Assign_Lead set status_returnto_str = 0, state='Closed',tagto_me_state='Closed' where id_lead ="+id_lead+" ";
	//	              			System.out.println(query1);
	//              			    PreparedStatement ps2=con.prepareStatement(query1);
	//              			    k=ps2.executeUpdate();
	//							
	//						}
	//						
	//					}
	//					j+=1;
	//				} 
	//				if(j==count.length) {
	//					
	//					
	//					
	//				    j=0;
	//					
	//					LeadModel ldmd = new LeadModel();
	//					ldmd.SendEmailToinformReturntoStore(map.get("tag_by"),id, request);
	//					j=1;
	//					
	//					if(j > 0)
	//					{
	//						j=1;
	//					
	//					  }
	//					}
	//				
	//		     }
	//			     
	//			}	
	//			 
	//			
	//			
	//			jobj.put("data",j);
	//			
	//		}
	//		catch (Exception e)
	//		{
	//			
	//			e.printStackTrace();
	//			System.out.println(e);
	//			
	//		}
	//	
	//		
	//		
	//		System.out.println("HIII"+jobj);
	//		
	//		return jobj;
	//	}






	public String CreateAuotoNumber()
	{
		String finyear="",poID2="",poID3="";
		int PoId=1;

		try
		{
			//String sql1= "select count(inv_no)  from O_Create_Invoice_Master lm, M_Subloc msl where lm.id_sloc=msl.id_sloc  and lm.id_sloc ="+id_sloc+"  group by cd_subl ";

			String sql1= "select top 1 inv_no from O_create_inv__msater_history lm  order by  inv_no desc";
			System.out.println(sql1);

			ps=con.prepareStatement(sql1);
			rs=ps.executeQuery();

			if(rs.next())
			{
				System.out.println(rs.getString("inv_no"));
				String PoId2= rs.getString("inv_no");

				int i=Integer.parseInt(PoId2);  
				PoId=i+1;  
				System.out.println(PoId);



				poID3 = String.format("%04d", PoId);
				System.out.println(poID3);
				//poID2=asset_prefix+"-"+rs.getString(2)+"-"+poID3;

			}
			else {

				poID3 = String.format("%04d", 1);
				System.out.println(poID3);
				//poID2=asset_prefix+"-"+cd_subl+"-"+poID3;

			}


		}
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return poID3;
	}




	//public String CreateAuotoNumber(String id_sloc)
	//{
	//	String finyear="",poID2="",poID3="";
	//	int PoId=1;
	//	
	//	try
	//	{
	//     String sql1= "select count(ld_inv_no)  from O_Assign_Lead_Master lm, M_Subloc msl where lm.id_sloc=msl.id_sloc  and lm.id_sloc ="+id_sloc+"  group by cd_subl ";
	//    System.out.println(sql1);
	//	ps=con.prepareStatement(sql1);
	//	rs=ps.executeQuery();
	//	
	//		if(rs.next())
	//		{
	//			 System.out.println(rs.getInt(1));
	//			PoId =	rs.getInt(1) +1;
	//		 	
	//				poID3 = String.format("%03d", PoId);
	//			
	//			//poID2=asset_prefix+"-"+rs.getString(2)+"-"+poID3;
	//		
	//		}
	//		else {
	//           	poID3 = String.format("%03d", 1);
	//			
	//			//poID2=asset_prefix+"-"+cd_subl+"-"+poID3;
	//			
	//		}
	//		
	//		
	//	}
	//	catch(Exception e)
	//	{
	//		System.out.println("Eroor ...  "+e.toString());
	//	}
	//	return poID3;
}






