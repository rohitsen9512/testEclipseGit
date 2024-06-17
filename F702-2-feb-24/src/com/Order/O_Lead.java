package com.Order;

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
import com.Incident.IncidentModel;


public class O_Lead extends HttpServlet {
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
		      else if(paramName.equals("po_date") && !paramValues.equals(""))
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
		String action = "", temp1 = "", id = "0", upload_inv = "", lead_no = "", leadId = "",prodstatus="" ,dt_to = "", dt_frm = "",
				value = "", ColName = "", id_lead_m = "00",searchWord="";
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
		if (request.getParameter("leadId") != null) {
			leadId = request.getParameter("leadId");
		}
		if (request.getParameter("dt_frm") != null) {
			dt_frm = request.getParameter("dt_frm");
		}
		if (request.getParameter("dt_to") != null) {
			dt_to = request.getParameter("dt_to");
		}
		if (request.getParameter("prodstatus") != null) {
			prodstatus = request.getParameter("prodstatus");
		}
		if (request.getParameter("value") != null) {
			value = request.getParameter("value");
		}
		if (request.getParameter("ColName") != null) {
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("searchWord") !=null)
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
			
			String UserType = (String) session.getAttribute("UserType");
			
			
			con = Common.GetConnection(request);
			switch (action) {
			case "Save":
				String colName = "";
				int j = 0, id_lead_m1 = 0; String sql="";
				String cd_prod="";
				String id_sloc="",id_loc="",dt_exp_rent1="",fill_lead="", nm_src="",other_src1="",created_by="",nm_pstn="",pstn_ct="",pstn_email="";
				
				String id_src1="";
				 id_sloc=map.get("id_sloc");
				 id_loc=map.get("id_loc");
				 id_src1=map.get("id_src");
				 created_by=map.get("created_by");
				 String state=map.get("state");
				 lead_no=map.get("lead_no");	
				 other_src1=map.get("other_src");
				
//				 sql = "select id_lead_m from O_lead_Master where lead_no = '" + lead_no + "' ";
				System.out.println(map);
				try {
//					ps = con.prepareStatement(sql);
//					rs = ps.executeQuery();
//					if (!rs.next()) {
						rs = Common.GetColumns("O_lead_Master", request);
						while (rs.next()) {
							if (rs.getString(1) != "id_lead_m") {
								if (map.containsKey(rs.getString(1))) {
									if(!rs.getString(1).equals("lead_no")&& !rs.getString(1).equals("id_fincance_lead")&& !rs.getString(1).equals("id_src")&&!rs.getString(1).equals("cd_src") &&!rs.getString(1).equals("nm_src")&&!rs.getString(1).equals("id_design"))
									{
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
						}
						
						String lead_no_auto="";
						int id_fincance=0;
						ps=con.prepareStatement("select id_fincance from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
						rs=ps.executeQuery();
						if(rs.next())
						{
							id_fincance=rs.getInt(1);
						}
						String leadNo="";
						lead_no_auto = CreateAuotoNumber(id_fincance,map.get("id_sloc"),map.get("id_loc"));
						
						System.out.println("select * from M_Source where id_src='"+id_src1+"'");
						ps=con.prepareStatement("select * from M_Source where id_src='"+id_src1+"'");
						
						rs=ps.executeQuery();
				      		String id_src="",cd_src="";
				      		stmt = con.createStatement();
						if(rs.next())
						{
							nm_src = rs.getString("nm_src");
							if(nm_src.equals("NA")) {
								
								System.out.println("insert into M_Source (nm_src,cd_src) values('"+ other_src1 + "','NA')");
								stmt.executeUpdate("insert into M_Source (nm_src,cd_src) values('"+ other_src1 + "','NA')");
								
								System.out.println("select * from M_Source where nm_src='"+other_src1+"'");
								sql = "select * from M_Source where nm_src='" + other_src1 + "'";
								ps = con.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									id_src = rs.getString("id_src");
									cd_src = rs.getString("cd_src");
									nm_src = rs.getString("nm_src");
									
								}
								
							}else {
								
                               System.out.println("select * from M_Source where nm_src='" + nm_src + "'");
								sql = "select * from M_Source where nm_src='" + nm_src + "'";
								ps = con.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									id_src = rs.getString("id_src");
									cd_src = rs.getString("cd_src");
									nm_src = rs.getString("nm_src");
							}
							
						}
							}
					
						sql = "insert into O_lead_Master(" + colName
								+ ",lead_no,id_fincance_lead,id_src,nm_src,cd_src,id_design) values(" + value + ",'"+lead_no_auto+"','"+id_fincance+"','"+id_src+"','"+nm_src+"','"+cd_src+"','1')";

								
						System.out.println(sql);
						stmt = con.createStatement();
						stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
						rs1 = stmt.getGeneratedKeys();
						if (rs1.next()) {
							id_lead_m1 = rs1.getInt(1);
							System.out.println(id_lead_m1+"hhhrit");
						}
						String ds_pro ="",id_prod="",ds_assetdiv="",mfr_assetdiv="" ,oxy_prod="", in_stoc_qty="";
						String count[] = request.getParameterValues("count");
						stmt = con.createStatement();
						j = 0;
						for (int i = 0; i < count.length; i++) {
							
							 cd_prod = request.getParameter("id_prod" + count[i] + "");
						
						
							
								
							if (!cd_prod.isEmpty()) {
								
								sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod + "'";
								ps = con.prepareStatement(sql);
								rs = ps.executeQuery();
								System.out.println(sql);
								if (rs.next()) {
									id_prod = rs.getString("id_assetdiv");
									ds_pro = rs.getString("nm_assetdiv");
			                        ds_assetdiv = rs.getString("ds_assetdiv");
			                        mfr_assetdiv = rs.getString("mfr_assetdiv");
								}else {
									
								       cd_prod = request.getParameter("id_prod" + count[i] + "");
									String mfr = request.getParameter("mfr" + count[i] + "");
									String ds_product = request.getParameter("ds_product" + count[i] + "");
//									
//									stmt.executeUpdate(
//											"insert into M_Asset_Div (nm_assetdiv,ds_assetdiv,mfr_assetdiv) values('"
//													+ cd_prod + "','" + ds_product + "','" + mfr + "')");
									sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod + "'";
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
								
									if (rs.next()) {
										id_prod = rs.getString("id_assetdiv");
										ds_pro = rs.getString("nm_assetdiv");
				                        ds_assetdiv = rs.getString("ds_assetdiv");
				                        mfr_assetdiv = rs.getString("mfr_assetdiv");
									}
								} 
								
								String un_prc = request.getParameter("un_prc" + count[i] + "");
								
								String id_lead = request.getParameter("id_lead" + count[i] + "");
							
								if(id_lead.equals("")) {
									
									id_lead="";
                               }
							
								String sr_no_refill = request.getParameter("sr_no_refill" + count[i] + "");
								 
								if(sr_no_refill.equals("")||sr_no_refill.equals("NULL")||sr_no_refill.equals("null")) {
									
									sr_no_refill="";
	                                }
							
								String cylndr_fill_mt = request.getParameter("cylndr_fill_mt" + count[i] + "");
							
								String serial_no = request.getParameter("sr_no" + count[i] + "");
								 if (serial_no.equals("")) {
									serial_no="";
                               }
								String others = request.getParameter("others" + count[i] + "");
								
								String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
								
								String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
								System.out.println("H"+id_tax1);
								String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
							
								String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
								
								String buyback = request.getParameter("buyback" + count[i] + "");
								 
			                   
			                    String typ_cylinder = request.getParameter("typ_cylinder" + count[i] + "");
			                 
			                    String rental_day = request.getParameter("rental_day" + count[i] + "");
			                	
			                     dt_exp_rent1 = request.getParameter("dt_exp_rent" + count[i] + "");
			                 
			                    String dt_exp_rent="";
			                    if(dt_exp_rent1.equals("")) {
			                    	dt_exp_rent="";
			                    }
			                    else {
			                     dt_exp_rent =dateFormatNeeded.format(userDateFormat.parse(dt_exp_rent1));
			                    }
                                String gr_tot = request.getParameter("gr_tot" + count[i] + "");
			                    in_stoc_qty = request.getParameter("in_stoc_qty" + count[i] + "");
			                	String prod_status = request.getParameter("prod_status" + count[i] + "");
								System.out.println(prod_status);
							
									System.out.println(prod_status);
									 String typ_service = request.getParameter("typ_service" + count[i] + "");
					                    System.out.println("h"+typ_service);
									 if(typ_service.equals("Rental")||typ_service.equals("Sale")) {
										 fill_lead="No";
										 System.out.println(fill_lead);
										 if(prod_status.equals("")) {
											 if(typ_service.equals("Rental")) {
												 prod_status="Product_on_Rental";
											 }
											 if(typ_service.equals("Sale")){
												  prod_status="Product_on_Sale";
											 }
											 
										 }
										 }
									  if (typ_service.equals("Refill")) {
			                            	
		                            	 prod_status="Fill";
		                            	 //cd_prod = request.getParameter("id_product" + count[i] + "");
		                            	 fill_lead="yes";
		                            }
									 
									 // cd_prod = request.getParameter("id_prod" + count[i] + "");
										 
										 
	                            
									 
									
			                    
			                    if(prod_status.equals("Refill")||prod_status.equals("Refill_and_Extend")) {
			                    	
			                    	System.out.println(
											"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,refill_id_lead,state,prev_sr_no,prev_lead_no,typ_cylinder) "
													+ "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+sr_no_refill+"','"
													+ created_by + "','1'," + un_prc + ","
													+ others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
													+ typ_service + "','"+ rental_day +"','"+ dt_exp_rent +"'," + gr_tot + ",'" + id_lead_m1 + "','"
													+ id_prod + "','"+cylndr_fill_mt+"',''"+lead_no_auto+"','"+prod_status+"','"+id_lead+"','New','"+serial_no+"','"+lead_no+"','"+typ_cylinder+"')");
									j=stmt.executeUpdate(
											"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by, quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,refill_id_lead,state,prev_sr_no,prev_lead_no,typ_cylinder) "
				                                    + "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+sr_no_refill+"','"
				                                    + created_by + "', '1'," + un_prc + ","
				                                    + others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
				                                    + typ_service + "','"+ rental_day + "','"+ dt_exp_rent +"'," + gr_tot + ",'" + id_lead_m1 + "','"
				                                    + id_prod + "','"+cylndr_fill_mt+"','"+lead_no_auto+"','"+prod_status+"','"+id_lead+"','New','"+serial_no+"','"+lead_no+"','"+typ_cylinder+"')");
							      
									
									if(j>0) {
										System.out.println("update  O_Lead set sr_no_refill='"+sr_no_refill+"',   lead_no_refill='"+lead_no_auto+"',cr_lead_refill_status='1' where  id_lead='"+id_lead+"'");
										
										j=stmt.executeUpdate("update  O_Lead set sr_no_refill='"+sr_no_refill+"',  lead_no_refill='"+lead_no_auto+"',cr_lead_refill_status='1' where   id_lead='"+id_lead+"'" );
										
										if(j>0) {
											System.out.println("update  O_Assign_Lead set  cr_lead_refill_status='1' where  id_lead='"+id_lead+"'");
											
											j=stmt.executeUpdate("update  O_Assign_Lead set cr_lead_refill_status='1' where   id_lead='"+id_lead+"'" );
										    
									
										if(j>0) {
											j=0;
											if(typ_service.equals("Sale")) {
							
												System.out.println("update  S_I_Ware_House set sr_no_status='1',  device_status='Sale' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
												
												j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1' ,  device_status='Sale' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
											}else {
												System.out.println("update  S_I_Ware_House set sr_no_status='1',   device_status='Rental' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
												
												j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1',  device_status='Rental' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
											    
												if(j>0) {
													System.out.println("update  S_I_Ware_House set sr_no_status='1',   device_status='Rental' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+sr_no_refill+"'");
													
													j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1',  device_status='Rental' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+sr_no_refill+"'" );
												    
												}
											}
											
										}
								}
									}
									
			                    }if(prod_status.equals("Product_on_Rental")||prod_status.equals("Product_on_Sale")) {
			                    	j=0;
			                    	System.out.println(
											"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,state,typ_cylinder) "
													+ "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+serial_no+"','"
													+ created_by + "','1'," + un_prc + ","
													+ others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
													+ typ_service + "','"+ rental_day +"','"+ dt_exp_rent +"'," + gr_tot + ",'" + id_lead_m1 + "','"
													+ id_prod + "','"+cylndr_fill_mt+"',''"+lead_no_auto+"','"+prod_status+"','New','"+typ_cylinder+"')");
									j=stmt.executeUpdate(
											"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by, quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,state,typ_cylinder) "
				                                    + "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+serial_no+"','"
				                                    + created_by + "', '1'," + un_prc + ","
				                                    + others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
				                                    + typ_service + "','"+ rental_day + "','"+ dt_exp_rent +"'," + gr_tot + ",'" + id_lead_m1 + "','"
				                                    + id_prod + "','"+cylndr_fill_mt+"','"+lead_no_auto+"','"+prod_status+"','New','"+typ_cylinder+"')");
							      
									
									
									if(j>0) {
										
										
										j=0;
										if(typ_service.equals("Sale")) {
									
											System.out.println("update  S_I_Ware_House set sr_no_status='1',  device_status='Sale' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
											
											j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1' ,  device_status='Sale' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
										}else {
											
											System.out.println("update  S_I_Ware_House set sr_no_status='1',   device_status='Rental' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
											
										j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1',  device_status='Rental' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
										}
										
									}
//									
			                    }if(prod_status.equals("Fill")) {
			                    	
			                    	System.out.println(
											"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,state,typ_cylinder) "
													+ "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+serial_no+"','"
													+ created_by + "','1'," + un_prc + ","
													+ others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
													+ typ_service + "','"+ rental_day +"','"+ dt_exp_rent +"'," + gr_tot + ",'" + id_lead_m1 + "','"
													+ id_prod + "','"+cylndr_fill_mt+"',''"+lead_no_auto+"','"+prod_status+"','New','"+typ_cylinder+"')");
									j=stmt.executeUpdate(
											"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by, quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,state,typ_cylinder) "
				                                    + "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+serial_no+"','"
				                                    + created_by + "', '1'," + un_prc + ","
				                                    + others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
				                                    + typ_service + "','"+ rental_day + "','"+ dt_exp_rent +"'," + gr_tot + ",'" + id_lead_m1 + "','"
				                                    + id_prod + "','"+cylndr_fill_mt+"','"+lead_no_auto+"','"+prod_status+"','New','"+typ_cylinder+"')");
							      
									
			                   
			                    }//
							
							
							}
							//j= 1;
							
					}//loop
						if(j > 0)
							{
							j=0;
							sql = "update O_lead_Master set fill_lead ='" +fill_lead+ "' where id_lead_m='"+id_lead_m1+"'";
							System.out.println(sql);
							
							
							
							stmt = con.createStatement();
							j=stmt.executeUpdate(sql);
							if (j>0) {
								
							
								System.out.println("jjj");
							sql ="select nm_pstn,pstn_ct,pstn_email ,dt_ld from O_lead_Master where id_lead_m='"+id_lead_m1+"' ";
							System.out.println(sql);
							ps = con.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								System.out.println();
								nm_pstn = rs.getString("nm_pstn");
								pstn_ct = rs.getString("pstn_ct");
								pstn_email = rs.getString("pstn_email");
								String dt_ld = rs.getString("dt_ld");
		                       
								sql = "insert into M_Customer(id_lead_m,nm_customer,username,pwd_customer,dt_ld,lead_no) values('"+id_lead_m1+"','"+nm_pstn+"','"+pstn_ct+"','"+pstn_ct+"','"+dt_ld+"','"+lead_no_auto+"')";
                              
								
								System.out.println(sql);
							   stmt = con.createStatement();
								j=stmt.executeUpdate(sql);
							}
							
							if(j>0) {
								
							
						     j=0;
							LeadModel ldmd = new LeadModel();
						ldmd.SendEmailToAssignee(map.get("to_asign_cordi"),id_lead_m1, request);
								j=1;	
							
//						LeadModel ldmd1 = new LeadModel();
//						ldmd1.SendEmailToCustomerforTrackingcordential(id_lead_m1, nm_pstn,pstn_ct,request);		
							
							}
								
						//whatsapp
								LeadModel_Whatsapp ldmd1 = new LeadModel_Whatsapp();
								ldmd1.SendWhatsAppToAssignee(map.get("to_asign_cordi"),id_lead_m1, request);
										j=1;
							}
				}
//					} else {
//						System.out.println("will be duplicate invoice");
//						j = 2;
//					}
						
				} catch (Exception e) {
					System.out.println("Error in O_Lead" + e);
				}
				jobj.put("data", j);
				break;
			case "Display":
				jobj = DisplayLead(id, lead_no, leadId, dt_frm, dt_to,  UserType,prodstatus,searchWord);
				break;
			case "Display1":
				jobj = DisplayLead1(id, lead_no, leadId, dt_frm, dt_to,  UserType);
				break;
			case "Edit":
				jobj = DisplayLead(id, lead_no, leadId, dt_frm, dt_to,  UserType,prodstatus,searchWord);
				break;
			case "Edit1":
				jobj = DisplayLead1(id, lead_no, leadId, dt_frm, dt_to,  UserType);
				break;
		    case "GetCustomerRecordDetail":
            	jobj = GetCustomerRecordDetail(id);	
                break; 
			case "Update":
				String col_Val = "";
				int k = 0;
				String cd_prod1="", id_fincance1="",nm_src1="",other_src="",refill_id_lead="";
				
				 id_src1="";
				 id_sloc=map.get("id_sloc");
				 id_loc=map.get("id_loc");
				 id_src1=map.get("id_src");
				 other_src=map.get("other_src");
				 created_by=map.get("created_by");
				 
				//id_sloc=map.get("id_sloc");
				id_fincance1=map.get("id_fincance_lead");
				System.out.println(map.get("lead_no"));
				try {
					stmt = con.createStatement();
					rs = Common.GetColumns("O_lead_Master", request);
					while (rs.next()) {
						if (rs.getString(1) != "id_lead_m") {
							if (map.containsKey(rs.getString(1))) {
								if(!rs.getString(1).equals("lead_no")&& !rs.getString(1).equals("id_src")&&!rs.getString(1).equals("cd_src")&&!rs.getString(1).equals("nm_src") &&!rs.getString(1).equals("id_design")) 
								{
								if (col_Val.equals("")) {
									col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								} else {
									col_Val += "," + rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								}
							}
							}
						}
					}
					
					String lead_no_auto="";
					int id_fincance=Integer.parseInt(id_fincance1);
				
					
					
				//	lead_no_auto = CreateAuotoNumber(id_fincance,map.get("id_sloc"),map.get("id_loc"));
					
					
					System.out.println("select * from M_Source where id_src='"+id_src1+"'");
					ps=con.prepareStatement("select * from M_Source where id_src='"+id_src1+"'");
					
					rs=ps.executeQuery();
			      		String id_src="",cd_src="";
			      		stmt = con.createStatement();
					if(rs.next())
					{
						nm_src1 = rs.getString("nm_src");
						if(nm_src1.equals("NA")) {
							
							System.out.println("insert into M_Source (nm_src,cd_src) values('"+ other_src + "','NA')");
							stmt.executeUpdate("insert into M_Source (nm_src,cd_src) values('"+ other_src + "','NA')");
							
							System.out.println("select * from M_Source where nm_src='"+other_src+"'");
							sql = "select * from M_Source where nm_src='" + other_src + "'";
							ps = con.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								id_src = rs.getString("id_src");
								cd_src = rs.getString("cd_src");
								nm_src=rs.getString("nm_src");
								
							}
							
						}else {
							
                           System.out.println("select * from M_Source where nm_src='" + nm_src1 + "'");
							sql = "select * from M_Source where nm_src='" + nm_src1 + "'";
							ps = con.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								id_src = rs.getString("id_src");
								cd_src = rs.getString("cd_src");
								//nm_src=rs.getString("nm_src");
								
						}
						
					}
						}
					String query = "update O_lead_Master set " + col_Val + ",id_src='" + id_src+"',nm_src='" + nm_src1+"',cd_src='" + cd_src+"'  where id_lead_m=" + id + "";
					System.out.println(query);
					try {
						PreparedStatement ps = con.prepareStatement(query);
						k = ps.executeUpdate();
						if (k > 0) {
							String ds_pro ="",id_prod="",ds_assetdiv="",mfr_assetdiv="" , in_stoc_qty="";
							String count[] = request.getParameterValues("count");
							for (int i = 0; i < count.length; i++) {
								 cd_prod1 = request.getParameter("id_prod" + count[i] + "");
								if (!cd_prod1.isEmpty()) {
									sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod1 + "'";
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										id_prod = rs.getString("id_assetdiv");
										ds_pro = rs.getString("nm_assetdiv");
				                        ds_assetdiv = rs.getString("ds_assetdiv");
				                        mfr_assetdiv = rs.getString("mfr_assetdiv");
									}
									else {
									       cd_prod1 = request.getParameter("id_prod" + count[i] + "");
										String mfr = request.getParameter("mfr" + count[i] + "");
										String ds_product = request.getParameter("ds_product" + count[i] + "");
										
//										stmt.executeUpdate(
//												"insert into M_Asset_Div (nm_assetdiv,ds_assetdiv,mfr_assetdiv) values('"
//														+ cd_prod1 + "','" + ds_product + "','" + mfr + "')");
										sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod1 + "'";
										ps = con.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											id_prod = rs.getString("id_assetdiv");
											ds_pro = rs.getString("nm_assetdiv");
					                        ds_assetdiv = rs.getString("ds_assetdiv");
					                        mfr_assetdiv = rs.getString("mfr_assetdiv");
										}
									}
									
									String sr_no_refill = request.getParameter("sr_no_refill" + count[i] + "");
									System.out.println(sr_no_refill); 
									if(sr_no_refill.equals("")) {
										System.out.println(sr_no_refill); 	
										sr_no_refill="";
		                                }
									String prod_status = request.getParameter("prod_status" + count[i] + "");
									String typ_service = request.getParameter("typ_service" + count[i] + "");
									System.out.println(prod_status);
									if(prod_status.equals("")) {
										System.out.println(prod_status);
										 if(typ_service.equals("Rental")) {
											 
										  prod_status="Product_on_Rental";
										 // cd_prod = request.getParameter("id_prod" + count[i] + "");
											 
											 
		                            }else if(typ_service.equals("Sale")) {
		                            	
		                            	  prod_status="Product_on_Sale";
		                            	  
		                            }else if (typ_service.equals("Refill")) {
		                            	
		                            	 prod_status="Fill";
		                            	 //cd_prod = request.getParameter("id_product" + count[i] + "");
		                           
		                            }
										 
										 }
									String un_prc = request.getParameter("un_prc" + count[i] + "");
									String cylndr_fill_mt = request.getParameter("cylndr_fill_mt" + count[i] + "");
									String serial_no = request.getParameter("sr_no" + count[i] + "");
									String others = request.getParameter("others" + count[i] + "");
									String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
									System.out.println("h"+id_tax1); 
									String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
									System.out.println("h"+id_tax2); 
									String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
									String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
									String buyback = request.getParameter("buyback" + count[i] + "");
				                    
				                    String typ_cylinder = request.getParameter("typ_cylinder" + count[i] + "");
				                    String rental_day = request.getParameter("rental_day" + count[i] + "");
				                     dt_exp_rent1 = request.getParameter("dt_exp_rent" + count[i] + "");
				                    String dt_exp_rent="";
				                    if(dt_exp_rent1.equals("")) {
				                    	dt_exp_rent="";
				                    }
				                    else {
				                     dt_exp_rent =dateFormatNeeded.format(userDateFormat.parse(dt_exp_rent1));
				                    }

				                    String gr_tot = request.getParameter("gr_tot" + count[i] + "");
				                    in_stoc_qty = request.getParameter("in_stoc_qty" + count[i] + "");
									String id_lead = request.getParameter("id_lead" + count[i] + "");
								System.out.println("id"+id_lead);
									int diff=0; 
									
									
									 if(prod_status.equals("Refill")||prod_status.equals("Refill_and_Extend")) {
										 
											 
											
												if (!id_lead.equals("")) {
													
													 System.out.println("update O_Lead set  nm_prod='" + ds_pro + "' ,ds_product= '"
																+ ds_assetdiv + "',mfr='" + mfr_assetdiv + "',sr_no="+sr_no_refill+"',quantity='1',un_prc='" + un_prc
																+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
																+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "', buyback ='" + buyback + "',typ_service='"
																+ typ_service + "',rental_day='" + rental_day + "',created_by='"+created_by+"' dt_exp_rent='" + dt_exp_rent + "',gr_tot='"+ gr_tot + "',id_prod='" + id_prod + "', cylndr_fill_mt='"+cylndr_fill_mt+"' ,prod_status='"+prod_status+"'   where id_lead='" + id_lead + "';");
													
													j=stmt.executeUpdate("update O_Lead set  nm_prod='" + ds_pro + "' ,ds_product= '"
															+ ds_assetdiv + "',mfr='" + mfr_assetdiv + "',sr_no='"+sr_no_refill+"', quantity='1',un_prc='" + un_prc
															+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
															+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "',buyback ='" + buyback + "',typ_service='"
															+ typ_service + "',rental_day='" + rental_day + "',created_by='"+created_by+"',dt_exp_rent='" + dt_exp_rent + "' ,gr_tot='"+ gr_tot + "',id_prod='" + id_prod + "'  , cylndr_fill_mt = '"+cylndr_fill_mt+"' , prod_status='"+prod_status+"' where id_lead='" + id_lead + "' ;");
												
												
													if(j>0) {
														sql="select * from O_Lead where  id_lead='"+id_lead+"' ";
														
														ps = con.prepareStatement(sql);
														rs = ps.executeQuery();
														if (rs.next()) {
															refill_id_lead = rs.getString("refill_id_lead");
															
									                      
														}
														System.out.println("update  O_Lead set sr_no_refill='"+sr_no_refill+"' where  id_lead='"+refill_id_lead+"'");
														
														j=stmt.executeUpdate("update  O_Lead set sr_no_refill='"+sr_no_refill+"' where   id_lead='"+refill_id_lead+"'" );
													    
													
														if(j>0) {
															j=0;
															if(typ_service.equals("Sale")) {
											
																System.out.println("update  S_I_Ware_House set sr_no_status='1',  device_status='Sale' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
																
																j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1' ,  device_status='Sale' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
															}else {
																System.out.println("update  S_I_Ware_House set sr_no_status='1',   device_status='Rental' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
																
																j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1',  device_status='Rental' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
															    
																if(j>0) {
																	System.out.println("update  S_I_Ware_House set sr_no_status='1',   device_status='Rental' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+sr_no_refill+"'");
																	
																	j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1',  device_status='Rental' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+sr_no_refill+"'" );
																    
																}
															}
															
														}
//														
													}
												} else {
													System.out.println(
															"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,refill_id_lead,state) "
																	+ "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+sr_no_refill+"','"
																	+ created_by + "','1'," + un_prc + ","
																	+ others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
																	+ typ_service + "','"+ rental_day +"','"+ dt_exp_rent +"'," + gr_tot + ",'" + id + "','"
																	+ id_prod + "','"+cylndr_fill_mt+"',''"+lead_no_auto+"','"+prod_status+"','"+id_lead+"','New')");
													j=stmt.executeUpdate(
															"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by, quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,refill_id_lead,state) "
								                                    + "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+sr_no_refill+"','"
								                                    + created_by + "', '1'," + un_prc + ","
								                                    + others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
								                                    + typ_service + "','"+ rental_day + "','"+ dt_exp_rent +"'," + gr_tot + ",'" + id + "','"
								                                    + id_prod + "','"+cylndr_fill_mt+"','"+lead_no_auto+"','"+prod_status+"','"+id_lead+"','New')");
											      
												
													if(j>0) {
														System.out.println("update  O_Lead set sr_no_refill='"+sr_no_refill+"',   lead_no_refill='"+lead_no_auto+"',cr_lead_refill_status='1' where  id_lead='"+id_lead+"'");
														
														j=stmt.executeUpdate("update  O_Lead set sr_no_refill='"+sr_no_refill+"',  lead_no_refill='"+lead_no_auto+"',cr_lead_refill_status='1' where   id_lead='"+id_lead+"'" );
													    
													
														if(j>0) {
															j=0;
															if(typ_service.equals("Sale")) {
											
																System.out.println("update  S_I_Ware_House set sr_no_status='1',  device_status='Sale' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
																
																j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1' ,  device_status='Sale' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
															}else {
																System.out.println("update  S_I_Ware_House set sr_no_status='1',   device_status='Rental' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
																
																j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1',  device_status='Rental' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
															    
																if(j>0) {
																	System.out.println("update  S_I_Ware_House set sr_no_status='1',   device_status='Rental' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+sr_no_refill+"'");
																	
																	j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1',  device_status='Rental' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+sr_no_refill+"'" );
																    
																}
															}
															
														}
//														
													}
												
												} 
											 
											 
										 
											 }if(prod_status.equals("Product_on_Rental")||prod_status.equals("Product_on_Sale")) {
												 if (!id_lead.equals("")) {
														
													 System.out.println("update O_Lead set  nm_prod='" + ds_pro + "' ,ds_product= '"
																+ ds_assetdiv + "',mfr='" + mfr_assetdiv + "',sr_no='"+serial_no+"',quantity='1',un_prc='" + un_prc
																+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
																+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "', buyback ='" + buyback + "',typ_service='"
																+ typ_service + "',rental_day='" + rental_day + "',created_by='"+created_by+"' dt_exp_rent='" + dt_exp_rent + "',gr_tot='"+ gr_tot + "',id_prod='" + id_prod + "', cylndr_fill_mt='"+cylndr_fill_mt+"' where id_lead='" + id_lead + "';");
													
													j=stmt.executeUpdate("update O_Lead set  nm_prod='" + ds_pro + "' ,ds_product= '"
															+ ds_assetdiv + "',mfr='" + mfr_assetdiv + "',sr_no='"+serial_no+"',quantity='1',un_prc='" + un_prc
															+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
															+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "',buyback ='" + buyback + "',typ_service='"
															+ typ_service + "',rental_day='" + rental_day + "',created_by='"+created_by+"',dt_exp_rent='" + dt_exp_rent + "' ,gr_tot='"+ gr_tot + "',id_prod='" + id_prod + "'  , cylndr_fill_mt = '"+cylndr_fill_mt+"' where id_lead='" + id_lead + "' ;");
												
													if(j>0) {
													if(typ_service.equals("Sale")) {
														
														System.out.println("update  S_I_Ware_House set sr_no_status='1',  device_status='Sale' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
														
														j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1' ,  device_status='Sale' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
													}else {
														System.out.println("update  S_I_Ware_House set sr_no_status='1',   device_status='Rental' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
														
														j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1',  device_status='Rental' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
													    
														
														}
													}
												 
												 
												 } else {
													System.out.println(
															"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,state) "
																	+ "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+serial_no+"','"
																	+ created_by + "','1'," + un_prc + ","
																	+ others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
																	+ typ_service + "','"+ rental_day +"','"+ dt_exp_rent +"'," + gr_tot + ",'" + id + "','"
																	+ id_prod + "','"+cylndr_fill_mt+"',''"+lead_no_auto+"','"+prod_status+"','New')");
													j=stmt.executeUpdate(
															"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by, quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,state) "
								                                    + "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+serial_no+"','"
								                                    + created_by + "', '1'," + un_prc + ","
								                                    + others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
								                                    + typ_service + "','"+ rental_day + "','"+ dt_exp_rent +"'," + gr_tot + ",'" + id + "','"
								                                    + id_prod + "','"+cylndr_fill_mt+"','"+lead_no_auto+"','"+prod_status+"','New')");
											      
													
													if(j>0) {
														if(typ_service.equals("Sale")) {
															
															System.out.println("update  S_I_Ware_House set sr_no_status='1',  device_status='Sale' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
															
															j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1' ,  device_status='Sale' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
														}else {
															System.out.println("update  S_I_Ware_House set sr_no_status='1',   device_status='Rental' where  id_subl="+id_sloc+" and id_loc="+id_loc+"  and serial_no='"+serial_no+"'");
															
															j=stmt.executeUpdate("update  S_I_Ware_House set sr_no_status='1',  device_status='Rental' where id_subl="+id_sloc+"  and id_loc="+id_loc+" and serial_no='"+serial_no+"'" );
														    
															
															}
														}
												} 
											 }//
                                           if(prod_status.equals("Fill")) {
                                        	   if (!id_lead.equals("")) {
													
                                        		   System.out.println("update O_Lead set  nm_prod='" + ds_pro + "' ,quantity='1',un_prc='" + un_prc
															+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
															+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "', buyback ='" + buyback + "',typ_service='"
															+ typ_service + "',created_by='"+created_by+"' ,gr_tot='"+ gr_tot + "',id_prod='" + id_prod + "', cylndr_fill_mt='"+cylndr_fill_mt+"',typ_cylinder='"+typ_cylinder+"' where id_lead='" + id_lead + "';");
												
												j=stmt.executeUpdate("update O_Lead set  nm_prod='" + ds_pro + "' ,quantity='1',un_prc='" + un_prc
														+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
														+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "',buyback ='" + buyback + "',typ_service='"
														+ typ_service + "',created_by='"+created_by+"',gr_tot='"+ gr_tot + "',id_prod='" + id_prod + "'  , cylndr_fill_mt = '"+cylndr_fill_mt+"',typ_cylinder='"+typ_cylinder+"'  where id_lead='" + id_lead + "' ;");
											
                                        	   }else {

                                        		   System.out.println(
															"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,state,typ_cylinder) "
																	+ "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+serial_no+"','"
																	+ created_by + "','1'," + un_prc + ","
																	+ others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
																	+ typ_service + "','"+ rental_day +"','"+ dt_exp_rent +"'," + gr_tot + ",'" + id + "','"
																	+ id_prod + "','"+cylndr_fill_mt+"',''"+lead_no_auto+"','"+prod_status+"','New','"+typ_cylinder+"')");
													j=stmt.executeUpdate(
															"insert into O_Lead(nm_prod,ds_product,mfr,sr_no,created_by, quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,typ_service,rental_day,dt_exp_rent,gr_tot,id_lead_m,id_prod,cylndr_fill_mt,lead_no,prod_status,state,typ_cylinder) "
								                                    + "values('" + ds_pro + "','" + ds_assetdiv + "','" + mfr_assetdiv + "','"+serial_no+"','"
								                                    + created_by + "', '1'," + un_prc + ","
								                                    + others + "," + id_tax1 + "," + id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback + ",'"
								                                    + typ_service + "','"+ rental_day + "','"+ dt_exp_rent +"'," + gr_tot + ",'" + id + "','"
								                                    + id_prod + "','"+cylndr_fill_mt+"','"+lead_no_auto+"','"+prod_status+"','New','"+typ_cylinder+"')");
											      
													
                                        	   }
			                    	
									
			                   
			                    }//	
										 
                   			
									
//									
//										 
//									
									
								}
								k = 1;
							}
						}
						jobj.put("data", k);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					System.out.println(e+"Some error in O_lead.");
				}
				break;
            case "CheckExistforSource":
			jobj = CheckExistforSource(id);
				break;
			}//
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
		} catch (Exception e) {
			System.out.println(e+"Error in A_Invoice.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println(e+"err");
				e.printStackTrace();
				
			}
		}
	}

//	public JSONObject CheckExitsVal(String value, String ColName) {
//		JSONObject json = new JSONObject();
//		String query = "select id_inv_m from O_lead_Master where " + ColName + " = '" + value + "'";
//		try {
//			ps = con.prepareStatement(query);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				json.put("data", 1);
//			} else {
//				json.put("data", 0);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return json;
//	}
//


	public JSONObject DisplayLead(String id, String id_lead_m, String no_asset, String dt_frm, String dt_to, String UserType,String prodstatus,String searchWord) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			if (!no_asset.equals("")) {
				sql = "select * from O_Lead led where id_lead = " + no_asset + " and status_proceed=0";
			}
			if (id_lead_m.equals("") && no_asset.equals("")) {
				if (!UserType.equals("SUPER"))
//					sql = "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,nm_subl,nm_loc from O_Lead_Master led_m ,O_lead  ld, M_Subloc ms,M_Loc l where  "
//							+ " led_m.status_proceed = 0 and  led_m.id_sloc=ms.id_sloc and led_m.id_loc= l.id_loc  and  dt_ld >=(replace(convert(NVARCHAR, '"
//							+ dt_frm + "', 106), ' ', '-')) and " + "dt_ld <=(replace(convert(NVARCHAR, '" + dt_to
//							+ "', 106), ' ', '-'))  ";
					sql = "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,nm_subl,nm_loc from O_Lead_Master led_m ,O_lead  ld, M_Subloc ms,M_Loc l where  "
							+ " led_m.status_proceed = 0 and  led_m.id_sloc=ms.id_sloc and led_m.id_loc= l.id_loc   and (led_m.lead_no like '"+searchWord+"%' or dt_ld like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";
				else
					sql = "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,nm_subl,nm_loc from O_Lead_Master led_m , M_Subloc ms,M_Loc l  where   "
							+ " status_proceed = 0  and led_m.id_sloc=ms.id_sloc and led_m.id_loc= l.id_loc  and dt_ld >=(replace(convert(NVARCHAR, '"
							+ dt_frm + "', 106), ' ', '-')) and " + "dt_ld <=(replace(convert(NVARCHAR, '" + dt_to
							+ "', 106), ' ', '-')) ";
			} else if (!id_lead_m.equals("")) {
				sql = "select id_lead_m,id_lead,quantity,un_prc,mfr,ds_product,id_prod from O_Lead led where id_lead_m = "
						+ id_lead_m + " and status_proceed=0";
			}
			if (!id.equals("0")) {
				
				sql="select prod_status from O_lead where id_lead_m="+id+"";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					prodstatus=rs.getString("prod_status");
					System.out.println(prodstatus);
				}
				if(prodstatus.equals("Fill")) {
					
				
					
					System.out.println("Fill");
					sql = "select  distinct led.*,led_m.po_no,led_m.lead_no,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,(replace(convert(NVARCHAR, led_m.po_date, 103), ' ', '-')) as dtpo,(replace(convert(NVARCHAR, led_m.dt_billing, 103), ' ', '-')) as dtbil ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,pay_mode_rfd,cheque_no_rfd,ld_time,led_m.id_sloc,led_m.id_loc,id_design,to_asign_cordi,led.state, ma.type_grp ,"
							+" add_hsptl,ct_hsptl,nm_hsptl ,dr_sp,nm_dr,dl_no,gstin_no,address,adhar_no,pstn_email,att_name,pstn_ct,alt_pstn_ct,nm_pstn,tot,id_src,cd_src,refund_amt,trnsprt_amt,id_fincance_lead from O_Lead_Master led_m ,O_Lead led,M_Asset_Div ma where led_m.id_lead_m=led.id_lead_m   and "
							+ " led_m.status_proceed = 0 and led.id_prod=ma.id_assetdiv "
							+ "and led_m.id_lead_m ="+id+" ";
				}else {
					System.out.println(id);
					sql = "select  distinct led.*,led_m.po_no,led_m.lead_no,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,(replace(convert(NVARCHAR, led_m.po_date, 103), ' ', '-')) as dtpo,(replace(convert(NVARCHAR, led_m.dt_billing, 103), ' ', '-')) as dtbil ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,pay_mode_rfd,cheque_no_rfd,ld_time,led_m.id_sloc,led_m.id_loc,id_design,to_asign_cordi,led.state, ma.type_grp ,"
							+" add_hsptl,ct_hsptl,nm_hsptl ,dr_sp,nm_dr,dl_no,gstin_no,address,adhar_no,pstn_email,att_name,pstn_ct,alt_pstn_ct,nm_pstn,tot,id_src,cd_src,refund_amt,trnsprt_amt,qt.tot_aval_qty as in_stoc_qty,id_fincance_lead from O_Lead_Master led_m ,O_Lead led,S_Store_Qty qt ,M_Asset_Div ma where led_m.id_lead_m=led.id_lead_m  and qt.id_sloc=led_m.id_sloc and qt.id_loc=led_m.id_loc and qt.id_product=led.id_prod and "
							+ " led_m.status_proceed = 0 and led.id_prod=ma.id_assetdiv "
							+ "and led_m.id_lead_m ="+id+" ";
				}
			
				
			}
			
			System.out.println("DisplayLead"+sql);
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
			System.out.println(e+"sql error in O_Lead.");
		}
		System.out.println(jobj+"hr");
		return jobj;
	}
	public JSONObject DisplayLead1(String id, String id_lead_m, String no_asset, String dt_frm, String dt_to, String UserType) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			if (!no_asset.equals("")) {
				sql = "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,nm_subl,nm_loc,O.*  from O_Assign_Lead O,O_Assign_Lead_Master led_m , M_Subloc ms,M_Loc l  where   "
						+ " O.id_lead_m=led_m.id_lead_m and O.typ_service='Rental' and O.status_deliver_ld=1 and O.status_returnto_str=0  and led_m.id_sloc=ms.id_sloc and led_m.id_loc= l.id_loc and dt_ld >=(replace(convert(NVARCHAR, '"
						+ dt_frm + "', 106), ' ', '-')) and " + "dt_ld <=(replace(convert(NVARCHAR, '" + dt_to
						+ "', 106), ' ', '-')) ";
			}
			if (id_lead_m.equals("") && no_asset.equals("")) {
				if (!UserType.equals("SUPER"))
					sql =  "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,nm_subl,nm_loc,O.*  from O_Assign_Lead O,O_Assign_Lead_Master led_m , M_Subloc ms,M_Loc l  where   "
							+ "  O.id_lead_m=led_m.id_lead_m and O.typ_service='Rental' and O.status_deliver_ld=1 and O.status_returnto_str=0  and led_m.id_sloc=ms.id_sloc and led_m.id_loc= l.id_loc and dt_ld >=(replace(convert(NVARCHAR, '"
							+ dt_frm + "', 106), ' ', '-')) and " + "dt_ld <=(replace(convert(NVARCHAR, '" + dt_to
							+ "', 106), ' ', '-')) ";
				else
					sql = "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,nm_subl,nm_loc,O.*  from O_Assign_Lead O,O_Assign_Lead_Master led_m , M_Subloc ms,M_Loc l  where   "
							+ " O.id_lead_m=led_m.id_lead_m and O.typ_service='Rental' and O.status_deliver_ld=1 and  O.status_returnto_str=0 and led_m.id_sloc=ms.id_sloc and led_m.id_loc= l.id_loc and dt_ld >=(replace(convert(NVARCHAR, '"
							+ dt_frm + "', 106), ' ', '-')) and " + "dt_ld <=(replace(convert(NVARCHAR, '" + dt_to
							+ "', 106), ' ', '-')) ";
			} else if (!id_lead_m.equals("")) {
				sql = "select distinct led_m.*,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,nm_subl,nm_loc,O.* from O_Assign_Lead O,O_Assign_Lead_Master led_m , M_Subloc ms,M_Loc l  where   "
						+ " O.id_lead_m=led_m.id_lead_m and O.typ_service='Rental' and O.status_deliver_ld=1 and O.status_returnto_str=0   and led_m.id_sloc=ms.id_sloc and led_m.id_loc= l.id_loc and dt_ld >=(replace(convert(NVARCHAR, '"
						+ dt_frm + "', 106), ' ', '-')) and " + "dt_ld <=(replace(convert(NVARCHAR, '" + dt_to
						+ "', 106), ' ', '-')) ";
			}
			if (!id.equals("0")) {
			
				sql = "select  distinct led.*,led_m.po_no,led_m.lead_no,(replace(convert(NVARCHAR, led_m.dt_ld, 103), ' ', '-')) as dtlead,(replace(convert(NVARCHAR, led_m.po_date, 103), ' ', '-')) as dtpo ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,led_m.id_sloc,led_m.id_loc,id_design,to_asign_cordi,led.state,"
						+"add_hsptl,ct_hsptl,nm_hsptl ,dr_sp,nm_dr,dl_no,gstin_no,address,adhar_no,pstn_email,pstn_ct,alt_pstn_ct,nm_pstn,att_name,tot,id_src,cd_src,refund_amt,trnsprt_amt,qt.tot_aval_qty as in_stoc_qty,id_fincance_lead from O_Lead_Master led_m ,O_Lead led,S_Store_Qty qt where led_m.id_lead_m=led.id_lead_m  and qt.id_sloc=led_m.id_sloc and qt.id_loc=led_m.id_loc and qt.id_product=led.id_prod "
						+ "and led.id_lead ="+id+"";
				
				
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
			System.out.println(e+"sql error in O_Lead.");
		}
		System.out.println(jobj+"hr");
		return jobj;
	}
//public String CreateAuotoNumber(int id_fincance,String id_sloc,String ledNo)
//	{
//		String finyear="",poID2="";
//		int PoId=1;
//		
//		try
//		{
//			if(!ledNo.equals("")) {
//				String led_n[]=ledNo.split("-");
//				System.out.println(led_n[2]);
//				String cd_subl="";
//				ps=con.prepareStatement("select cd_subl from M_Subloc where id_sloc ="+id_sloc+"");
//				rs=ps.executeQuery();
//				if(rs.next()) {
//					cd_subl=rs.getString(1);
//					for (int i = 0; i < led_n.length; i++) {
//					      
//					    String val=led_n[i];
//					    System.out.println(val);
//					    System.out.println(led_n[i]);
//						if(val==cd_subl) {
//							
//							String year="",end="";
//							ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
//							rs=ps.executeQuery();
//						if(rs.next())
//						{
//							year=rs.getString("start");
//							end=rs.getString("enddt");
//						}
//				    
//						String strt = year.substring (2,4);
//						String endt = end.substring (2,4);
//						String asset_prefix="";
//						ps=con.prepareStatement("select asset_prefix from M_Company");
//						rs=ps.executeQuery();
//					if(rs.next())
//					{
//						asset_prefix=rs.getString(1);
//						 
//					}
//					String cd_subl1="";
//					ps=con.prepareStatement("select cd_subl from M_Subloc where id_sloc ="+id_sloc+"");
//					rs=ps.executeQuery();
//				if(rs.next())
//				{
//					cd_subl1=rs.getString(1);
//					 
//				}
//						
//						//String sql1= "select count(lead_no) ,cd_subl from O_lead_Master lm, M_Subloc msl  where lm.id_sloc=msl.id_sloc  and lm.id_sloc ="+id_sloc+" group by cd_subl";
//				        String sql1= "select count(lead_no) ,cd_subl from O_lead_Master lm, M_Subloc msl where lm.id_sloc=msl.id_sloc  and lm.id_sloc ="+id_sloc+"  group by cd_subl ";
//				        System.out.println(sql1);
//						ps=con.prepareStatement(sql1);
//						rs=ps.executeQuery();
//						
//							if(rs.next())
//							{
//								 System.out.println(rs.getInt(1));
//								PoId =	rs.getInt(1) +1;
//							 	
//								String 	poID3 = String.format("%03d", PoId);
//								
//								poID2=asset_prefix+"-"+rs.getString(2)+"-"+poID3;
//							
//							}
//							else {
//				                String 	poID3 = String.format("%03d", 1);
//								
//								poID2=asset_prefix+"-"+cd_subl1+"-"+poID3;
//								
//							}
//							
//							
//							
//							
//							
//						}//
//						else {
//							 poID2= ledNo;
//						}
//						
//						
//					}
//				
//					
//				}
//				
//				System.out.println("ledNo not blank");
//			}
//			else {
//				
//				String year="",end="";
//				ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
//				rs=ps.executeQuery();
//			if(rs.next())
//			{
//				year=rs.getString("start");
//				end=rs.getString("enddt");
//			}
//	    
//			String strt = year.substring (2,4);
//			String endt = end.substring (2,4);
//			String asset_prefix="";
//			ps=con.prepareStatement("select asset_prefix from M_Company");
//			rs=ps.executeQuery();
//		if(rs.next())
//		{
//			asset_prefix=rs.getString(1);
//			 
//		}
//		String cd_subl="";
//		ps=con.prepareStatement("select cd_subl from M_Subloc where id_sloc ="+id_sloc+"");
//		rs=ps.executeQuery();
//	if(rs.next())
//	{
//		cd_subl=rs.getString(1);
//		 
//	}
//			
//			//String sql1= "select count(lead_no) ,cd_subl from O_lead_Master lm, M_Subloc msl  where lm.id_sloc=msl.id_sloc  and lm.id_sloc ="+id_sloc+" group by cd_subl";
//	        String sql1= "select count(lead_no) ,cd_subl from O_lead_Master lm, M_Subloc msl where lm.id_sloc=msl.id_sloc  and lm.id_sloc ="+id_sloc+"  group by cd_subl ";
//	        System.out.println(sql1);
//			ps=con.prepareStatement(sql1);
//			rs=ps.executeQuery();
//			
//				if(rs.next())
//				{
//					 System.out.println(rs.getInt(1));
//					PoId =	rs.getInt(1) +1;
//				 	
//					String 	poID3 = String.format("%03d", PoId);
//					
//					poID2=asset_prefix+"-"+rs.getString(2)+"-"+poID3;
//				
//				}
//				else {
//	                String 	poID3 = String.format("%03d", 1);
//					
//					poID2=asset_prefix+"-"+cd_subl+"-"+poID3;
//					
//				}
//				
//			}
//		
//			
//		}
//		catch(Exception e)
//		{
//			System.out.println("Eroor ...  "+e.toString());
//		}
//		return poID2;
//	}

	
	
	public String CreateAuotoNumber(int id_fincance,String id_sloc,String id_loc)
	{
		String finyear="",poID2="";
		int PoId=1;
		
		try
		{
			
			
			String year="",end="";
			ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			rs=ps.executeQuery();
		if(rs.next())
		{
			year=rs.getString("start");
			end=rs.getString("enddt");
		}
    
		String strt = year.substring (2,4);
		String endt = end.substring (2,4);
		String asset_prefix="";
		ps=con.prepareStatement("select cd_loc from M_Loc where id_loc="+id_loc+";");
		rs=ps.executeQuery();
	if(rs.next())
	{
		asset_prefix=rs.getString(1);
		 
	}
	String cd_subl="";
	ps=con.prepareStatement("select cd_subl from M_Subloc where id_sloc ="+id_sloc+"");
	rs=ps.executeQuery();
if(rs.next())
{
	cd_subl=rs.getString(1);
	 
}
		
String sql1="SELECT MAX(CAST(REVERSE(SUBSTRING(REVERSE(lead_no), 1, PATINDEX('%[^0-9]%', REVERSE(lead_no)) - 1)) AS INT)) AS max_numeric_value "
        +" FROM O_lead_Master WHERE PATINDEX('%[^0-9]%', REVERSE(lead_no)) > 0 and  id_sloc="+id_sloc+"";

             System.out.println(sql1);
	         ps=con.prepareStatement(sql1);
	rs=ps.executeQuery();
		ps=con.prepareStatement(sql1);
		rs=ps.executeQuery();
		
		if(rs.next()) {
			
			if(rs.getString("max_numeric_value")!=null) {
				
				 System.out.println(rs.getString("max_numeric_value"));
				// if(!rs.getString("lead_no").equals("NULL")||!rs.getString("lead_no").equals("null")) {
				 int lead_no=rs.getInt("max_numeric_value");
				  PoId=lead_no+1;
					String 	poID3 = String.format("%03d", PoId);
					 System.out.println(poID3);
					
					//poID2="F7"+asset_prefix+"-"+lead[1]+"-"+poID3;
					poID2="F7"+asset_prefix+"-"+cd_subl+"-"+poID3;
					 }
					else {
						   System.out.println(sql1);
		                String 	poID3 = String.format("%03d", 1);
						
						poID2="F7"+asset_prefix+"-"+cd_subl+"-"+poID3;
						 System.out.println(poID3);
							
						
					}
				}
				
			
			
			
		}
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return poID2;
	}
	
	
	public JSONObject CheckExistforSource(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String sql = "select  nm_src from M_Source where id_src='"+id+"'";
		System.out.println(sql);
		try 
		{
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
			System.out.println(e+"sql error in O_Lead.");
		}
		System.out.println(jobj);
		return jobj;
	}
	
	
	public JSONObject GetCustomerRecordDetail(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		
			sql="Select * from O_lead_Master where pstn_ct='"+id+"'";
	
		
		System.out.println(sql);
	;
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
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println(e+"sql error in O_lead.");
		}
		 
		return jobj;
		
		
	}
	
	
}




