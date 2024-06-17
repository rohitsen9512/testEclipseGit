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


public class O_CreateInvoiceLead extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String String = null;
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
		String action = "", temp1 = "", id = "0", upload_inv = "", lead_no = "",inv_no="",prodstatus="", id_lead = "", dt_to = "", dt_frm = "",searchWord="",
				value = "", ColName = "", id_lead_m = "00";
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
		if (request.getParameter("id_lead") != null) {
			id_lead = request.getParameter("id_lead");
		}
		if (request.getParameter("inv_no") != null) {
			inv_no = request.getParameter("inv_no");
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
		}if(request.getParameter("searchWord") !=null)
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
				String colName = "" ,sql="";
				int k = 0;
				String cd_prod1="",inv_created_by="" ;
				int id_cr_inv_m=0 ,id_cr_inv_m_hist=0;
				String id_fincance1=map.get("id_fincance_lead");
				lead_no=map.get("lead_no");
			 // no_inv=map.get("no_inv");
				
					//String sql = "select id_cr_inv_m from O_Create_Invoice_Master  where inv_no = '" + inv_no + "' ";
						
				//	 System.out.println(sql);

					try {
						//ps = con.prepareStatement(sql);
						//rs = ps.executeQuery();
					
					
					//if (!rs.next()) {
						
						rs = Common.GetColumns("O_Create_Invoice_Master", request);
					while (rs.next()) {
						if (rs.getString(1) != "id_cr_inv_m") {
						 if (map.containsKey(rs.getString(1))) {
							if(rs.getString(1)!="id" && rs.getString(1)!="tot") {
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
			        System.out.println(map);
			        inv_created_by=map.get("inv_created_by");
			        inv_no=map.get("inv_no");
			        System.out.println(inv_no);
			       
			        String  dt_inv=map.get("dt_inv");
                    String tot=map.get("tot");
                    String  refund_amt=map.get("refund_amt");
                    double val1 = Double.parseDouble(tot)-Double.parseDouble(refund_amt);
                    String tot_inv_amt = Double.toString(val1);
			       
			        id_lead_m=map.get("id");
			      
			      
					int id_fincance=0;
					
					//temporary comment
					  ps=con.
					  prepareStatement("select id_fincance from M_FINANCE_YEAR where ACTIVE_YEAR='1'" ); 
					  rs=ps.executeQuery();
					  if(rs.next()) { 
						  id_fincance=rs.getInt(1); 
					  } 
					//  inv_no =CreateAuotoNumber(); System.out.println(inv_no);
					 
				  sql = "insert into O_Create_Invoice_Master(" + colName+ ",id_fincance,id_lead_m,tot_inv_amt) values(" + value + ","+id_fincance+","+id_lead_m+",'"+tot_inv_amt+"')";
					System.out.println(sql);
				
					
					stmt = con.createStatement();
					stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
					rs = stmt.getGeneratedKeys();
					while (rs.next()) {
						id_cr_inv_m = rs.getInt(1);
						System.out.println(id_cr_inv_m);
					
					
						
					}
					
					sql = "insert into O_create_inv__msater_history(" + colName+ ",id_fincance,id_lead_m,tot_inv_amt) values(" + value + ","+id_fincance+","+id_lead_m+",'"+tot_inv_amt+"')";
					stmt = con.createStatement();
					stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
					rs = stmt.getGeneratedKeys();
					while (rs.next()) {
						id_cr_inv_m_hist = rs.getInt(1);
						System.out.println(id_cr_inv_m_hist);
					}
					
	                 String ds_pro ="",id_prod="",ds_assetdiv="",mfr_assetdiv="" , hsn_cd_assetdiv="";
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
				                        hsn_cd_assetdiv = rs.getString("hsn_cd_assetdiv");
									}
									
									String un_prc = request.getParameter("un_prc" + count[i] + "");
									//String quantity = request.getParameter("quantity" + count[i] + "");
									String serial_no = request.getParameter("sr_no" + count[i] + "");
									String sr_no_refill = request.getParameter("sr_no_refill" + count[i] + "");
									System.out.println(sr_no_refill); 
									if(sr_no_refill.equals("")) {
										System.out.println(sr_no_refill); 	
										sr_no_refill="";
		                                }
									String others = request.getParameter("others" + count[i] + "");
									String cylndr_fill_mt = request.getParameter("cylndr_fill_mt" + count[i] + "");
								    String typ_cylinder = request.getParameter("typ_cylinder" + count[i] + "");
									String id_tax1 = request.getParameter("id_tax1" + count[i] + "");
									String id_tax2 = request.getParameter("id_tax2" + count[i] + "");
									String tax_val1 = request.getParameter("tax_val1" + count[i] + "");
									String tax_val2 = request.getParameter("tax_val2" + count[i] + "");
									String buyback = request.getParameter("buyback" + count[i] + "");
									System.out.println("hiii"+cylndr_fill_mt);
				                    String typ_service = request.getParameter("typ_service" + count[i] + "");
				                    String rental_day = request.getParameter("rental_day" + count[i] + "");
				                    String dt_exp_rent1 = request.getParameter("dt_exp_rent" + count[i] + "");
				                    String prod_status = request.getParameter("prod_status" + count[i] + "");
				                	System.out.println(prod_status); 
				                	System.out.println(rental_day); 
				                    
				                    
				                    String dt_exp_rent="";
				                    if(dt_exp_rent1.equals("")) {
				                    	dt_exp_rent="";
				                    }
				                    else {
				                     dt_exp_rent =dateFormatNeeded.format(userDateFormat.parse(dt_exp_rent1));
				                    }
				                   
				                    String gr_tot = request.getParameter("gr_tot" + count[i] + "");
				                    String id_lead1 = request.getParameter("id_lead" + count[i] + "");
								
									int diff=0; 
																		
									
								if (!id_lead1.equals("")) {
									System.out.println(
											"insert into O_Crt_Invoice(nm_prod ,ds_product,mfr ,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service ,rental_day,dt_exp_rent,hsn_cd,inv_created_by,id_lead,id_lead_m,id_cr_inv_m,current_inv_no,current_inv_dt,cylndr_fill_mt,lead_no,prod_status,typ_cylinder) "
													+ "values('"+ ds_pro + "', '" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"', '" + id_prod + "','1',"+ un_prc + ", " + others + " , "+id_tax1 + ", "+id_tax2+","+tax_val1+", "+tax_val2+","+buyback+","
													+ ""+gr_tot+",'" + typ_service+ "'," + rental_day + ", '"+dt_exp_rent+"', '"+hsn_cd_assetdiv+"',"+inv_created_by+","+id_lead1+","+id_lead_m+","+id_cr_inv_m+","+inv_no+",'"+dt_inv+"',"+cylndr_fill_mt+",'"+lead_no+"','"+prod_status+"','"+typ_cylinder+"')");
									k=stmt.executeUpdate(
											"insert into O_Crt_Invoice(nm_prod ,ds_product,mfr ,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service ,rental_day,dt_exp_rent,hsn_cd,inv_created_by,id_lead,id_lead_m,id_cr_inv_m,current_inv_no,current_inv_dt,cylndr_fill_mt,lead_no,prod_status,typ_cylinder) "
													+ "values('"+ ds_pro + "', '" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"', '" + id_prod + "','1',"+ un_prc + ", " + others + " , "+id_tax1 + ", "+id_tax2+","+tax_val1+", "+tax_val2+","+buyback+","
													+ ""+gr_tot+",'" + typ_service+ "'," + rental_day + ", '"+dt_exp_rent+"', '"+hsn_cd_assetdiv+"',"+inv_created_by+","+id_lead1+","+id_lead_m+","+id_cr_inv_m+","+inv_no+",'"+dt_inv+"',"+cylndr_fill_mt+",'"+lead_no+"','"+prod_status+"','"+typ_cylinder+"')");
									
									
									if(k>0) {
										
										System.out.println(
												"insert into O_create_invoice_history(nm_prod ,ds_product,mfr ,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service ,rental_day,dt_exp_rent,hsn_cd,inv_created_by,id_lead,id_lead_m,id_cr_inv_m_hist,current_inv_no,current_inv_dt,cylndr_fill_mt,lead_no,prod_status,typ_cylinder) "
														+ "values('"+ ds_pro + "', '" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"', '" + id_prod + "','1',"+ un_prc + ", " + others + " , "+id_tax1 + ", "+id_tax2+","+tax_val1+", "+tax_val2+","+buyback+","
														+ ""+gr_tot+",'" + typ_service+ "'," + rental_day + ", '"+dt_exp_rent+"', '"+hsn_cd_assetdiv+"',"+inv_created_by+","+id_lead1+","+id_lead_m+","+id_cr_inv_m_hist+", "+inv_no+",'"+dt_inv+"',"+cylndr_fill_mt+",'"+lead_no+"','"+prod_status+"','"+typ_cylinder+"')");
										k=stmt.executeUpdate(
												"insert into O_create_invoice_history(nm_prod ,ds_product,mfr ,sr_no,id_prod,quantity,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,typ_service ,rental_day,dt_exp_rent,hsn_cd,inv_created_by,id_lead,id_lead_m,id_cr_inv_m_hist ,current_inv_no,current_inv_dt,cylndr_fill_mt,lead_no,prod_status,typ_cylinder) "
														+ "values('"+ ds_pro + "', '" + ds_assetdiv + "','" + mfr_assetdiv + "','"+ serial_no+"', '" + id_prod + "','1',"+ un_prc + ", " + others + " , "+id_tax1 + ", "+id_tax2+","+tax_val1+", "+tax_val2+","+buyback+","
														+ ""+gr_tot+",'" + typ_service+ "'," + rental_day + ", '"+dt_exp_rent+"', '"+hsn_cd_assetdiv+"',"+inv_created_by+","+id_lead1+","+id_lead_m+","+id_cr_inv_m_hist+","+inv_no+",'"+dt_inv+"',"+cylndr_fill_mt+",'"+lead_no+"','"+prod_status+"','"+typ_cylinder+"')");
										
										//if(k>0) {
											//k=0;
										
										
										//k=stmt.executeUpdate("update O_Assign_Lead set current_inv_no='"+inv_no+"' where id_lead_m='"+id+"'");
										
										//} 
									}
									}
							}
								
								
						}
							if(k>0) {
								k=stmt.executeUpdate("update O_Assign_Lead_Master set ctr_inv_status=1 where id_lead_m='"+id+"'");
							    k=1;
							    
							    
							} 
					
				
				}catch (Exception e) {
						
					System.out.println(e+"Some error in O_CreateInvoiceLead.");
				}
				jobj.put("data", k);
				break;
			case "Update":
				String col_Val="";
				k = 0;
			    cd_prod1="";
			    inv_created_by="";
				int id_cr_inv_m1=0;
				
	
				try {
					stmt = con.createStatement();
					rs = Common.GetColumns("O_Create_Invoice_Master", request);
					while (rs.next()) {
						if ((rs.getString(1) != "id_cr_inv_m")||(rs.getString(1) == "no_po")||(rs.getString(1) == "dt_po")||(rs.getString(1) == "dt_inv")||(rs.getString(1) == "rv_charge")){
							if (map.containsKey(rs.getString(1))) {
							
								if (col_Val.equals("")) {
									col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								} else {
									col_Val += "," + rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								}
							
							}
						}
					}
					
					id_fincance1=map.get("id_fincance");
					String edit_by=map.get("edit_by");
					int id_fincance=Integer.parseInt(id_fincance1);
				
				String query = "update O_Create_Invoice_Master set " + col_Val + " where id_lead_m=" + id + "";
					System.out.println(query);
					try {
					PreparedStatement ps = con.prepareStatement(query);
					k = ps.executeUpdate();
					System.out.println(k);
					
                        if(k>0) {
                        	System.out.println(k);
    						
					
				      query = "update O_create_inv__msater_history set " + col_Val + " where id_lead_m=" + id + "";
					System.out.println(query);
						
				
						PreparedStatement ps1 = con.prepareStatement(query);
						k = ps1.executeUpdate();

						
						jobj.put("data", k);
						System.out.println(jobj);
					}
                        } catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					System.out.println(e+"Some error in O_lead.");
				}
				break;
			case "Display":
				jobj = DisplayforCreateInvoice(id, id_lead_m, id_lead, dt_frm, dt_to,  UserType,prodstatus,searchWord);
				break;
			case "Edit":
				jobj = DisplayforCreateInvoice(id, id_lead_m, id_lead, dt_frm, dt_to,  UserType,prodstatus,searchWord);
				break;
			case "DisplayforEdit":
				jobj = DisplayforUpdateInvoice(id, id_lead_m, id_lead, dt_frm, dt_to,  UserType,searchWord);
				break;
			case "Editforupdate":
				jobj = DisplayforUpdateInvoice(id, id_lead_m, id_lead, dt_frm, dt_to,  UserType,searchWord);
				break;
				
			case "CreateInvoiceNo":
				jobj = CreateAuotoInvoiceNumber();
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


	public JSONObject DisplayforCreateInvoice(String id, String id_lead_m, String id_lead, String dt_frm, String dt_to, String UserType,String prodstatus,String searchWord) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			if (!id_lead.equals("")) {
				sql = "select * from O_Lead led where id_lead = " + id_lead + " and status_proceed=0";
			}
			if (id_lead_m.equals("") && id_lead.equals("")) {
				if (!UserType.equals("SUPER"))
					sql = "select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as tagdt,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld,"
							+ "(select emp.nm_emp  from M_Emp_user emp  where ledm.tag_to_emp=emp.id_emp_user ) tagTo,"
							+"(select em.nm_emp  from M_Emp_user em  where ledm.created_by=em.id_emp_user ) createdBy,"
							+"(select e.nm_emp  from M_Emp_user e where ledm.approve_by=e.id_emp_user ) assignedBy,"
							+ "nm_subl,nm_loc, emp.nm_emp as tagBy  from O_Assign_Lead_Master ledm ,O_Assign_Lead led , M_Subloc ms ,M_Loc l,M_Emp_user emp where  "
							+ " led.status_cord_ld = 1 and led.state='Inprogress' and ledm.id_lead_m=led.id_lead_m and ledm.ctr_inv_status=0 and ledm.id_sloc=ms.id_sloc and  ledm.id_loc=l.id_loc and ledm.tag_by=emp.id_emp_user  and  (ledm.lead_no like '"+searchWord+"%' or dt_tag like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";  
				else
					sql = "select distinct ledm.*,(replace(convert(NVARCHAR, ledm.dt_tag, 103), ' ', '-')) as tagdt,"
							+ "(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld,(select emp.nm_emp  from M_Emp_user emp  where ledm.tag_to_emp=emp.id_emp_user ) tagTo,"
							+"(select em.nm_emp  from M_Emp_user em  where ledm.created_by=em.id_emp_user ) createdBy,"
							+"(select e.nm_emp  from M_Emp_user e where ledm.approve_by=e.id_emp_user ) assignedBy,"
							+ "nm_subl,nm_loc, emp.nm_emp as tagBy  from O_Assign_Lead_Master ledm , O_Assign_Lead led, M_Subloc ms ,M_Loc l,M_Emp_user emp where  "
							+ " led.status_cord_ld = 1 and led.state='Inprogress' and ledm.id_lead_m=led.id_lead_m and ledm.ctr_inv_status=0 and ledm.id_sloc=ms.id_sloc and  ledm.id_loc=l.id_loc and ledm.tag_by=emp.id_emp_user  and (ledm.lead_no like '"+searchWord+"%' or dt_tag like '"+searchWord+"%' or nm_pstn like '"+searchWord+"%'  ) order by lead_no ";  
					
			} else if (!id_lead_m.equals("")) {
				sql = "select id_lead_m,id_lead,quantity,un_prc,mfr,ds_product,id_prod from O_Assign_Lead led where id_lead_m = "
						+ id_lead_m + " and status_cord_ld=0";
			}
			if (!id.equals("0")) {
				sql="select prod_status from O_lead where id_lead_m="+id+"";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					prodstatus=rs.getString("prod_status");
					System.out.println(prodstatus);
				}
				System.out.println(id);
				if(prodstatus.equals("Fill")) {
					sql = "select distinct led.*,lm.po_no,(replace(convert(NVARCHAR, lm.po_date, 103), ' ', '-')) as dtpo,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,ledm.*,ma.hsn_cd_assetdiv as hsn_cd from  O_lead_Master lm,O_Assign_Lead_Master ledm ,O_Assign_Lead led ,S_Store_Qty qt ,M_Asset_Div ma, M_Subloc ms ,M_Loc l where lm.id_lead_m=ledm.id_lead_m and ledm.id_sloc=ms.id_sloc and "
							   +"ledm.id_loc=l.id_loc and led.id_lead_m=ledm.id_lead_m and led.status_returnto_str=0  and ledm.id_lead_m ="+id+" and ledm.status_cord_ld = 1 and ma.id_assetdiv=led.id_prod and ledm.state='Inprogress'";
					
				}
				else {
					sql = "select distinct led.*,lm.po_no,(replace(convert(NVARCHAR, lm.po_date, 103), ' ', '-')) as dtpo,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtld ,(replace(convert(NVARCHAR, led.dt_exp_rent, 103), ' ', '-')) as dt_exprent,ledm.*,qt.tot_aval_qty as in_stoc_qty,ma.hsn_cd_assetdiv as hsn_cd from  O_lead_Master lm,O_Assign_Lead_Master ledm ,O_Assign_Lead led ,S_Store_Qty qt ,M_Asset_Div ma, M_Subloc ms ,M_Loc l where lm.id_lead_m=ledm.id_lead_m and ledm.id_sloc=ms.id_sloc and "
							   +"ledm.id_loc=l.id_loc and led.id_lead_m=ledm.id_lead_m and led.status_returnto_str=0 and qt.id_sloc=ledm.id_sloc and qt.id_product=led.id_prod and ledm.id_lead_m ="+id+" and ledm.status_cord_ld = 1 and ma.id_assetdiv=led.id_prod and ledm.state='Inprogress'";
					
				}
					
				
				
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
			System.out.println(e+"sql error in O_createInvoice.");
		}
		System.out.println(jobj+"hr");
		return jobj;
	}


public JSONObject DisplayforUpdateInvoice(String id, String id_lead_m, String id_lead, String dt_frm, String dt_to, String UserType,String searchWord) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			if (!id_lead.equals("")) {
				sql = "select * from O_Lead led where id_lead = " + id_lead + " and status_proceed=0";
			}
			if (id_lead_m.equals("") && id_lead.equals("")) {
				if (!UserType.equals("SUPER"))
					sql ="select distinct top 1000 inv_m.nm_pstn,inv_m.lead_no ,inv_m.inv_no,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtinv,led_m.id_lead_m,"
							+"(replace(convert(NVARCHAR, inv_m.dt_ld, 103), ' ', '-')) as dtld,emp.nm_emp as createdBy,nm_subl,"
							+"nm_loc from  M_Subloc ms ,M_Loc l,O_create_inv__msater_history inv_m ,O_create_invoice_history inv,O_Assign_Lead_Master led_m,"
							+"M_Emp_user emp where led_m.ctr_inv_status=1 and led_m.state='inprogress'  and inv_m.id_sloc=ms.id_sloc and  inv_m.id_loc=l.id_loc  and inv_m.inv_created_by=emp.id_emp_user and led_m.id_lead_m=inv_m.id_lead_m and  inv_m.id_cr_inv_m_hist =inv.id_cr_inv_m_hist  "
							+"and   (inv_m.lead_no like '"+searchWord+"%' or dt_inv like '"+searchWord+"%' or inv_m.nm_pstn like '"+searchWord+"%' or inv_m.inv_no like '"+searchWord+"%' ) order by inv_m.lead_no ";
				else
					sql ="select distinct top 1000 inv_m.nm_pstn,inv_m.lead_no ,inv_m.inv_no,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtinv,led_m.id_lead_m,"
							+"(replace(convert(NVARCHAR, inv_m.dt_ld, 103), ' ', '-')) as dtld,emp.nm_emp as createdBy,nm_subl,"
							+"nm_loc from  M_Subloc ms ,M_Loc l,O_create_inv__msater_history inv_m ,O_create_invoice_history inv,O_Assign_Lead_Master led_m,"
							+"M_Emp_user emp where led_m.ctr_inv_status=1 and led_m.state='inprogress'  and inv_m.id_sloc=ms.id_sloc and  inv_m.id_loc=l.id_loc  and inv_m.inv_created_by=emp.id_emp_user and led_m.id_lead_m=inv_m.id_lead_m and  inv_m.id_cr_inv_m_hist =inv.id_cr_inv_m_hist  "
							+"and   (inv_m.lead_no like '"+searchWord+"%' or dt_inv like '"+searchWord+"%' or inv_m.nm_pstn like '"+searchWord+"%' or inv_m.inv_no like '"+searchWord+"%' ) order by inv_m.lead_no ";
					
			} else if (!id_lead_m.equals("")) {
				sql = "select id_lead_m,id_lead,quantity,un_prc,mfr,ds_product,id_prod from O_Assign_Lead led where id_lead_m = "
						+ id_lead_m + " and status_cord_ld=0 ";
				System.out.println("hh"+sql);
			}
			
			if (!id.equals("0")) {
				System.out.println(id);
				sql = "select distinct inv_m.*,inv.*,(replace(convert(NVARCHAR, inv_m.dt_ld, 103), ' ', '-')) as dtld ,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtpo ,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtinv ,(replace(convert(NVARCHAR, inv.dt_exp_rent, 103), ' ', '-')) as dt_exprent from "
						+ "O_Create_Invoice_Master inv_m,O_Crt_Invoice inv, M_Subloc ms ,M_Loc l where  inv_m.id_sloc=ms.id_sloc and "
						   +"inv_m.id_loc=l.id_loc and inv.id_lead_m=inv_m.id_lead_m and inv_m.id_lead_m ="+id+"";
				System.out.println("hh"+sql);
				
			}
			
			
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
			System.out.println(e+"sql error in O_createInvoice.");
		}
		System.out.println(jobj+"hr");
		return jobj;
	}	

	
//public String CreateAuotoNumber()
public JSONObject CreateAuotoInvoiceNumber()
{
	String finyear="",poID2="",poID3="",name="inv_no";
	int PoId=1;
	JSONObject jobj = new JSONObject();
	JSONObject jobj2 = new JSONObject();
	JSONArray jarr = new JSONArray();
	
	try
	{
     //String sql1= "select count(inv_no)  from O_Create_Invoice_Master lm, M_Subloc msl where lm.id_sloc=msl.id_sloc  and lm.id_sloc ="+id_sloc+"  group by cd_subl ";
   
    String sql1= "select top 1 inv_no from O_create_inv__msater_history lm  order by  inv_no Desc";
    System.out.println(sql1);

	ps=con.prepareStatement(sql1);
	rs=ps.executeQuery();
	
		if(rs.next())
		{
			System.out.println(rs.getInt("inv_no"));
			 int PoId2= rs.getInt("inv_no");
			
			 //int i=Integer.parseInt(PoId2);  
			 
			 PoId=PoId2+1;  
				System.out.println(PoId);
			 
               poID3 = String.format("%04d", PoId);
				System.out.println(poID3);
		           jobj2.put(name, poID3);
		
		          jarr.put(jobj2);
	
	              jobj.put("data", jarr);
			//poID2=asset_prefix+"-"+rs.getString(2)+"-"+poID3;
		
		}
		else {
			
           	poID3 = String.format("%04d", 1);
			System.out.println(poID3);
			//poID2=asset_prefix+"-"+cd_subl+"-"+poID3;
			  jobj2.put(name, poID3);

	          jarr.put(jobj2);

              jobj.put("data", jarr);
		}
		System.out.println(jobj);
		
	}
	catch(Exception e)
	{
		System.out.println("Eroor ...  "+e.toString());
	}
	return jobj;
}





	}
