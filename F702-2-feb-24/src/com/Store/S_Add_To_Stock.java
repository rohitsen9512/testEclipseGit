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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;
import dto.Common.UserAccessData;
import java.util.StringTokenizer;


public class S_Add_To_Stock extends HttpServlet {
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
				value = "", ColName = "",SerialVal="",type_grp="", id_grp="",id_inventory_m = "00",id1="",id2="",searchWord="";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("upload_inv") != null) {
			upload_inv = request.getParameter("upload_inv");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("id1") != null) {
			id1 = request.getParameter("id1");
		}
		if (request.getParameter("id2") != null) {
			id2 = request.getParameter("id2");
		}
		if (request.getParameter("id_inventory_m") != null) {
			id1 = request.getParameter("id_inventory_m");
		}
		if (request.getParameter("no_inv") != null) {
			no_inv = request.getParameter("no_inv");
		}
		if (request.getParameter("SerialVal") != null) {
			SerialVal = request.getParameter("SerialVal");
		}
		if (request.getParameter("id_inventory") != null) {
			id_invn = request.getParameter("id_inventory");
		}
		if (request.getParameter("id_product") != null) {
			id_grp = request.getParameter("id_product");
			System.out.println(id_grp);
		}if (request.getParameter("type_grp") != null) {
			type_grp = request.getParameter("type_grp");
			System.out.println(type_grp);
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
			//String id_dept = (String) session.getAttribute("DeptId");
			String UserType = (String) session.getAttribute("UserType");
		
			con = Common.GetConnection(request);
			UserAccessData uad = new UserAccessData();
			switch (action) {
			
			
			case "Display":
				jobj = DisplayInventoryafterStock(id, no_inv, id_invn, dt_frm, dt_to,  UserType, temp1, tempQuery,searchWord);
				break;
			case "Edit":
				jobj = DisplayInventoryafterStock(id, no_inv, id_invn, dt_frm, dt_to,  UserType, temp1, tempQuery,searchWord);
				break;
			case "DisplayInvforaddtostock":
				jobj = DisplayInventoryforAddtoStock(id, no_inv, id_invn, dt_frm, dt_to,  UserType, temp1, tempQuery,searchWord);
				break;
			case "Editforaddtostock":
				jobj = DisplayInventoryforAddtoStock(id, no_inv, id_invn, dt_frm, dt_to,  UserType, temp1, tempQuery,searchWord);
				break;
		    case "DisplayInStock":
				jobj = DisplayInStock();
				break;
		    case "InvNoGeneration":
            	jobj = GenerateInvoiceNo();
            	break;
	        case "SerialNoGeneration":
            	jobj = GenerateSerialNoAndSAPNo(id_invn);
            	break;
	        case "CheckSlAndSpNo":
            	jobj = CheckExitsVal(SerialVal);	
                break;
	        case "DropDownResult":
            	jobj = DropDownResult(id,id1,id2,type_grp);	
                break; 
	        case "DropDownResultwithAlldata":
            	jobj = DropDownResultwithAlldata(id,id1,id2,SerialVal,type_grp);	
                break; 
                
	        case "Save":
	        	jobj = AddToStock(map,id1, id_invn,SerialVal,id_grp,id_emp_user,request);
                break;
			case "Update":
				String col_Val1 = "",sql;
				int k1 = 0;
				if(!no_inv.equals("NA")) {
					 sql = "select id_inventory_m from S_Inventory_Master  where no_inv = '" + no_inv + "' ";
					 System.out.println(sql);
					 System.out.println("nan");
			try {
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();

					
					if (!rs.next()) {
						stmt = con.createStatement();
						rs = Common.GetColumns("S_Inventory_Master", request);
					while (rs.next()) {
						if (rs.getString(1) != "id_inventory_m") {
							if (map.containsKey(rs.getString(1))) {
								if (col_Val1.equals("")) {
									col_Val1 += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								} else {
									col_Val1 += "," + rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								}
							}
						}
					}
					String query = "update S_Inventory_Master set " + col_Val1 + ",updated_by=" + id_emp_user
							+ " where id_inventory_m=" + id + "";
					System.out.println(query);
					try {
						PreparedStatement ps = con.prepareStatement(query);
						k1 = ps.executeUpdate();
						if (k1 > 0) {
							String  id_prod = "", ds_pro = "";
							String count[] = request.getParameterValues("count");
							for (int i = 0; i < count.length; i++) {
								String cd_prod = request.getParameter("id_prod" + count[i] + "");
								if (!cd_prod.isEmpty()) {
									
									sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod + "'";
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										id_prod = rs.getString("id_assetdiv");
										ds_pro = rs.getString("nm_assetdiv");
									} else {
										stmt.executeUpdate(
												"insert into M_Asset_Div (nm_assetdiv,cd_assetdiv) values('"
														+ cd_prod + "','NA')");
										sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod + "'";
										ps = con.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											id_prod = rs.getString("id_assetdiv");
											ds_pro = rs.getString("nm_assetdiv");
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
									String id_inventory = request.getParameter("id_inventory" + count[i] + "");
									System.out.println("update S_Inventory set  product='" + ds_pro + "' ,ds_pro= '"
											+ ds_pro +   "',qty='" + qty + "',un_prc='" + un_prc
											+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
											+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "',buyback='"
											+ buyback + "',gr_tot='" + gr_tot + "',id_product='" + id_prod + 
											 "' where id_inventory='" + id_inventory + "';");
									if (!id_inventory.equals("")) {
										stmt.executeUpdate("update S_Inventory set product='" + ds_pro + "' ,ds_pro= '"
												+ ds_pro +   "',qty='" + qty + "',un_prc='" + un_prc
												+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='"
												+ id_tax2 + "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2
												+ "',buyback='" + buyback + "',gr_tot='" + gr_tot + "',id_product='"
												+ id_prod +  "' where id_inventory='" + id_inventory + "';");
									} else {
										System.out.println(
												"insert into S_Inventory(product,ds_pro,created_by,config,st_config,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET) "
														+ "values('" + ds_pro + "','" + ds_pro + "','"+ id_emp_user + "'"
														+ ",'Yes','Yes'," + qty + "," 
														+ un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id + "','" + id_prod + "','" + NM_ACC_ASSET + "')");
										stmt.executeUpdate(
												"insert into S_Inventory(product,ds_pro,created_by,config,st_config,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET) "
														+ "values('" + ds_pro + "','" + ds_pro + "','"
														+ id_emp_user + "','Yes','Yes'," + qty + "," 
														+ un_prc + "," + others + "," + id_tax1 + ","
														+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
														+ "," + gr_tot + ",'" + id + "','" + id_prod + "','" + NM_ACC_ASSET + "')");
									}
								}
								k1 = 1;
							}
						}
						jobj.put("data", k1);
					} catch (Exception e) {
						e.printStackTrace();
						
					}
				}
//				
				else {
					System.out.println("will be duplicate invoice");
					k1 = 2;
					System.out.println(jobj);
				}
				}
			//}//
				catch (Exception e) {
					System.out.println(e+"Some error in A_Invoice.");
					
				}
				}
				else {
					try {
						System.out.println("na");
						stmt= con.createStatement();
						rs = Common.GetColumns("S_Inventory_Master", request);
						while (rs.next()) {
							if (rs.getString(1) != "id_inventory_m") {
								if (map.containsKey(rs.getString(1))) {
									if (col_Val1.equals("")) {
										col_Val1 += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
									} else {
										col_Val1 += "," + rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
									}
								}
							}
						}
						String query = "update S_Inventory_Master set " + col_Val1 + ",updated_by=" + id_emp_user
								+ " where id_inventory_m=" + id + "";
						System.out.println(query);
						try {
							PreparedStatement ps = con.prepareStatement(query);
							k1 = ps.executeUpdate();
							if (k1 > 0) {
								String  id_prod = "", ds_pro = "";
								String count[] = request.getParameterValues("count");
								for (int i = 0; i < count.length; i++) {
									String cd_prod = request.getParameter("id_prod" + count[i] + "");
									if (!cd_prod.isEmpty()) {
										
										sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod + "'";
										ps = con.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											id_prod = rs.getString("id_assetdiv");
											ds_pro = rs.getString("nm_assetdiv");
										} else {
											stmt.executeUpdate(
													"insert into M_Asset_Div (nm_assetdiv,cd_assetdiv) values('"
															+ cd_prod + "','NA')");
											sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod + "'";
											ps = con.prepareStatement(sql);
											rs = ps.executeQuery();
											if (rs.next()) {
												id_prod = rs.getString("id_assetdiv");
												ds_pro = rs.getString("nm_assetdiv");
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
										String id_inventory = request.getParameter("id_inventory" + count[i] + "");
										System.out.println("update S_Inventory set  product='" + ds_pro + "' ,ds_pro= '"
												+ ds_pro +   "',qty='" + qty + "',un_prc='" + un_prc
												+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='" + id_tax2
												+ "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2 + "',buyback='"
												+ buyback + "',gr_tot='" + gr_tot + "',id_product='" + id_prod + 
												 "' where id_inventory='" + id_inventory + "';");
										if (!id_inventory.equals("")) {
											stmt.executeUpdate("update S_Inventory set product='" + ds_pro + "' ,ds_pro= '"
													+ ds_pro +   "',qty='" + qty + "',un_prc='" + un_prc
													+ "',others='" + others + "',id_tax1='" + id_tax1 + "',id_tax2='"
													+ id_tax2 + "',tax_val1='" + tax_val1 + "',tax_val2='" + tax_val2
													+ "',buyback='" + buyback + "',gr_tot='" + gr_tot + "',id_product='"
													+ id_prod +  "' where id_inventory='" + id_inventory + "';");
										} else {
											System.out.println(
													"insert into S_Inventory(product,ds_pro,created_by,config,st_config,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET) "
															+ "values('" + ds_pro + "','" + ds_pro + "','"+ id_emp_user + "'"
															+ ",'Yes','Yes'," + qty + "," 
															+ un_prc + "," + others + "," + id_tax1 + ","
															+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
															+ "," + gr_tot + ",'" + id + "','" + id_prod + "','" + NM_ACC_ASSET + "')");
											stmt.executeUpdate(
													"insert into S_Inventory(product,ds_pro,created_by,config,st_config,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,id_inventory_m,id_product,NM_ACC_ASSET) "
															+ "values('" + ds_pro + "','" + ds_pro + "','"
															+ id_emp_user + "','Yes','Yes'," + qty + "," 
															+ un_prc + "," + others + "," + id_tax1 + ","
															+ id_tax2 + "," + tax_val1 + "," + tax_val2 + "," + buyback
															+ "," + gr_tot + ",'" + id + "','" + id_prod + "','" + NM_ACC_ASSET + "')");
										}
										
										//System.out.println("update S_I_Ware_House set no_inv='"+no_inv+"' ,edit_by='"+id_emp_user +"' where id_inventory_m=" + id + "");
										//stmt.executeUpdate("update S_I_Ware_House set no_inv='"+no_inv+"' ,edit_by='"+id_emp_user +"' where id_inventory_m=" + id + "");
									}
									
									k1 = 1;
								}
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							
						}
				
					}
				//}//
					catch (Exception e) {
						System.out.println(e+"Some error in A_Invoice.");
						
					}
					
					
					
				}
				jobj.put("data", k1);
				System.out.println(jobj);
				
				break;
				
			}
		
				
	request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
		} catch (Exception e) {
			System.out.println(e+"Error in A_Invoice.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

public JSONObject AddToStock(HashMap<String, String> map , String id , String no_asset, String SerialVal , String id_prod , int id_emp_user, HttpServletRequest request)
	{
		String colName="",value="",model="",id_subl="",
		 dt_inv="", no_invoice="",qty="",nm_product="",id_product="",id_loc="",typeprod="";
		int j=0,k=0;
		JSONObject json=new JSONObject();
		
		try 
		{
				rs = Common.GetColumns("S_I_Ware_House",request);
				
				while (rs.next())
	            {
	           
	                    if(!(rs.getString(1).trim().equalsIgnoreCase("id_iwh") || rs.getString(1).trim().equalsIgnoreCase("serial_no") || rs.getString(1).trim().equalsIgnoreCase("st_config")))
	                    {
	                                if (map.containsKey(rs.getString(1)))
	                                {
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
				
				 id_loc=map.get("id_loc");
				 id_subl=map.get("id_subl");
				 dt_inv=map.get("dt_inv");
				 no_invoice=map.get("no_inv");	
				 qty=map.get("qty_recv");
				String qty_fill=map.get("qty_fill");
				int qtyFill=Integer.parseInt(qty_fill);
				String qty_empty=map.get("qty_empty");
				int qtyEmpty=Integer.parseInt(qty_empty);
				 typeprod=map.get("typeprod");
				 System.out.println(typeprod);
				
				 nm_product=map.get("nm_product");
				int q=Integer.parseInt(qty);
				//System.out.println(q);
				 
				//System.out.println("COL NAME:"+colName);
				//System.out.println("VALUE:"+value);
				
				
				
				String id_wh_dyn="",preFix="",preFix1="",preFix2="",pre="";
				int tempAstId=0;
				System.out.println("select ltrim(asset_prod_prefix) from M_Asset_Div gp where  gp.id_assetdiv="+id_prod+"");
				
				ps=con.prepareStatement("select ltrim(asset_prod_prefix) from M_Asset_Div gp where  gp.id_assetdiv="+id_prod+"");
				rs=ps.executeQuery();
				if(rs.next())
				{
					preFix2  = rs.getString(1);
					//pre  = rs.getString(1);
					//pre = preFix2+"/"+preFix3;
	            }
				//ps=con.prepareStatement("select ltrim(asset_prefix) from M_Company");
				ps=con.prepareStatement("select ltrim(cd_loc) from M_loc where id_loc="+id_loc+"");
				rs=ps.executeQuery();
				if(rs.next())
				{
					preFix1 =  rs.getString(1);
					
	            }
				String preFix3="";
				preFix1= preFix1.toUpperCase();
				System.out.println("Prefix is"+ preFix1);
				//System.out.println("PRE****"+pre);
				if(preFix2.length() <= 4)
				{
					preFix3=preFix2;
				}
				else
				{
					preFix3=pre.substring(0, Math.min(pre.length(), 4));
				}
				preFix =preFix1+"/"+preFix3+"-";
				
				
			
					
				String sql="";
				
				
				StringTokenizer st = new StringTokenizer(SerialVal , ",,");
		int		countcyl=1;
				
				k=0;
				while (st.hasMoreElements()) 
				{
					
					System.out.println("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
							"from S_I_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') ");
					
					ps=con.prepareStatement("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
							"from S_I_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') ");
					
					//sql="select  COUNT(id_wh)+1 from A_Ware_House where substring(id_wh_dyn,1,patindex('%000%', id_wh_dyn)-2) = upper('"+preFix+"')";
					//ps=con.prepareStatement(sql);
					rs1=ps.executeQuery();
					
						if(rs1.next())
						{
							tempAstId =	rs1.getInt(1);
						}
						String formatValue  = String.format("%04d", tempAstId);
						id_wh_dyn	=	preFix + formatValue;
					
						String query="";
						if(typeprod.equals("TYPE-CYLINDER")) {
							System.out.println(typeprod);
							   
						
							if(countcyl<=qtyFill) {
								///for fill cylynder
							//if(!map.get("qty_empty").equals("")&&!map.get("qty_empty").equals("0")&&!map.get("qty_fill").equals("0")&&!map.get("qty_fill").equals("")) {
								System.out.println(typeprod+"gg");
								System.out.println(qtyEmpty);
							
								query="insert into S_I_Ware_House("+colName+", id_wh_dyn,add_by,filling_status,serial_no) values("+value+" , '"+id_wh_dyn+"',"+id_emp_user+",'1','"+st.nextElement()+"')";
								System.out.println(query);
							
								PreparedStatement ps=con.prepareStatement(query);
								j=ps.executeUpdate();
							}
							
							else{
							///	for empty cylynder..colName........colName.....colName...
								query="insert into S_I_Ware_House("+colName+", id_wh_dyn,add_by,filling_status,serial_no) values("+value+" , '"+id_wh_dyn+"',"+id_emp_user+",'0','"+st.nextElement()+"')";
								System.out.println(query);
							
								PreparedStatement ps=con.prepareStatement(query);
								j=ps.executeUpdate();
								
							}
							
								++countcyl;
							//}
							}
						else {
							System.out.println("NOT"+typeprod);
					          query="insert into S_I_Ware_House("+colName+", id_wh_dyn,add_by,filling_status,serial_no) values("+value+" , '"+id_wh_dyn+"',"+id_emp_user+",'2','"+st.nextElement()+"')";
					          System.out.println(query);
								PreparedStatement ps=con.prepareStatement(query);
								j=ps.executeUpdate();
						}//
					
					//String query="insert into A_Ware_House("+colName+",no_mfr , id_wh_dyn,add_by,serial_no,appNo,st_config,no_model,mdl_num,process_typ,storeage_typ,ram_typ) values("+value+" ,'"+st.nextElement()+"' , '"+id_wh_dyn+"',"+id_emp_user+",'"+st1.nextElement()+"','"+st2.nextElement()+"','"+max+"','"+model+"','"+mdl_num1.nextElement()+"','"+process_typ1.nextElement()+"','"+storeage_typ1.nextElement()+"','"+ram_typ1.nextElement()+"')";
					//String query="insert into S_I_Ware_House("+colName+", id_wh_dyn,add_by,serial_no) values("+value+" , '"+id_wh_dyn+"',"+id_emp_user+",'"+st.nextElement()+"')";
						
//					System.out.println(query);
//					PreparedStatement ps=con.prepareStatement(query);
//					j=ps.executeUpdate();
//					System.out.println("J:"+j);
//					if(j>0) {
//						
//						
//						sql = "select * from S_Store_Qty where id_product='" + id_prod + "' and id_sloc='"+ id_subl +"'";
//						ps = con.prepareStatement(sql);
//						rs = ps.executeQuery();
//						stmt= con.createStatement();
//						if (rs.next()) {
//							System.out.println("Update  S_Store_Qty  set tot_aval_qty =tot_aval_qty+"+qty+" where id_product="+id_prod+" and id_sloc="+id_subl+" ");
//							stmt.executeUpdate(
//									"Update  S_Store_Qty  set tot_aval_qty =tot_aval_qty+"+qty+" where id_product="+id_prod+" and id_sloc="+id_subl+" ");
//							
//						} else {
//							System.out.println("insert into S_Store_Qty(id_product,id_sloc,nm_product,tot_aval_qty)"
//									+ "values('"+id_prod+"','"+id_subl+"','"+nm_product+"','"+qty+"')");
//							stmt.executeUpdate(
//									"insert into S_Store_Qty(id_product,id_sloc,nm_product,tot_aval_qty)"
//									+ "values('"+id_prod+"','"+id_subl+"','"+nm_product+"','"+qty+"')");
//						}
//						
//					}
					
					//k= k+1;
					//k++;	
				}//
				if(j>0) {
					
					if(typeprod.equals("Type-Cylinder")||typeprod.equals("TYPE-CYLINDER")) {
						
						System.out.println("if"+typeprod);

						System.out.println("if"+qty_fill);
						
						j=0;
						sql = "select * from S_Store_Qty where id_product='" + id_prod + "' and id_sloc='"+ id_subl +"' and id_loc='"+ id_loc +"'";
						ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						stmt= con.createStatement();
						if (rs.next()) {
							System.out.println("Update  S_Store_Qty  set tot_aval_qty =tot_aval_qty+"+qty_fill+",tot_aval_empty="+qty_empty+" where id_product="+id_prod+" and id_sloc="+id_subl+" and id_loc='"+ id_loc +"' ");
							stmt.executeUpdate(
									"Update  S_Store_Qty  set tot_aval_qty =tot_aval_qty+"+qty_fill+" ,tot_aval_empty="+qty_empty+" where id_product="+id_prod+" and id_sloc="+id_subl+" and id_loc='"+ id_loc +"' ");
							j=1;
							
						} else {
							System.out.println("insert into S_Store_Qty(id_product,id_loc,id_sloc,nm_product,tot_aval_qty,tot_aval_empty)"
									+ "values('"+id_prod+"','"+id_loc+"','"+id_subl+"','"+nm_product+"','"+qty_fill+"',"+qty_empty+")");
							stmt.executeUpdate(
									"insert into S_Store_Qty(id_product,id_loc,id_sloc,nm_product,tot_aval_qty,tot_aval_empty)"
									+ "values('"+id_prod+"','"+id_loc+"','"+id_subl+"','"+nm_product+"','"+qty_fill+"',"+qty_empty+")");
							j=1;
							
						}
					}
					
					else {
						System.out.println("else"+typeprod);
						j=0;
						sql = "select * from S_Store_Qty where id_product='" + id_prod + "' and id_sloc='"+ id_subl +"' and id_loc='"+ id_loc +"'";
						ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						stmt= con.createStatement();
						if (rs.next()) {
							System.out.println("Update  S_Store_Qty  set tot_aval_qty =tot_aval_qty+"+qty+",tot_aval_empty="+qty_empty+" where id_product="+id_prod+" and id_sloc="+id_subl+" and id_loc='"+ id_loc +"' ");
							stmt.executeUpdate(
									"Update  S_Store_Qty  set tot_aval_qty =tot_aval_qty+"+qty+" ,tot_aval_empty="+qty_empty+" where id_product="+id_prod+" and id_sloc="+id_subl+" and id_loc='"+ id_loc +"' ");
							j=1;
							
						} else {
							System.out.println("insert into S_Store_Qty(id_product,id_loc,id_sloc,nm_product,tot_aval_qty,tot_aval_empty)"
									+ "values('"+id_prod+"','"+id_loc+"','"+id_subl+"','"+nm_product+"','"+qty+"',"+qty_empty+")");
							stmt.executeUpdate(
									"insert into S_Store_Qty(id_product,id_loc,id_sloc,nm_product,tot_aval_qty,tot_aval_empty)"
									+ "values('"+id_prod+"','"+id_loc+"','"+id_subl+"','"+nm_product+"','"+qty+"',"+qty_empty+")");
							j=1;
							
						}
					}
					 
				
					
				}
				if(j>0)
					{
						
						
						j=0;
						String query ="update  S_Inventory set status_store = 1  where id_inventory ="+no_asset+"";
						System.out.println(query);
						PreparedStatement ps=con.prepareStatement(query);
						j=ps.executeUpdate();
						if(j>0)
						{
							query ="update S_Inventory_Master set status_store = 1 where id_inventory_m ="+id+"";
							System.out.println(query);
							 ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							j=1;
							
						}
					}
				
				
				
//				System.out.println(k);
//				System.out.println(q);
//				System.out.println(st);
//				if(k==q)
//				{
//					
//					
//					j=0;
//					String query ="update  S_Inventory set status_store = 1  where id_inventory ="+no_asset+"";
//					System.out.println(query);
//					PreparedStatement ps=con.prepareStatement(query);
//					j=ps.executeUpdate();
//					if(j>0)
//					{
//						query ="update S_Inventory_Master set status_store = 1 where id_inventory_m ="+id+"";
//						System.out.println(query);
//						 ps=con.prepareStatement(query);
//						j=ps.executeUpdate();
//						j=1;
//						
//					}
//				}
				
				json.put("data",j);
		}
		catch(Exception e)
		{
			System.out.println("SAVEtostock:"+e.toString());
		}
			return json;
	}




	


public JSONObject DisplayInventoryforAddtoStock(String id, String id_inventory_m, String no_asset, String dt_frm, String dt_to,
			 String UserType, String temp1, String tempQuery,String searchWord) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try {
			String sql = "";
			if (!no_asset.equals("")||!no_asset.equals("0")) {
				sql = "select  distinct inv_m.id_inventory_m, si.id_inventory,inv_m.no_inv,inv_m.temp_no_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,"
						+"(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,inv_m.id_subl,inv_m.id_loc,gr_tot ,others as others_cst, un_prc,si.qty_fill,si.qty_empty,si.nm_acc_asset ,id_product,product,ma.ds_assetdiv,ma.mfr_assetdiv,ms.nm_subl,l.nm_loc  from S_Inventory_Master inv_m ," 
						+"S_Inventory si ,M_Asset_Div ma, M_Subloc ms,M_Loc l where  inv_m.id_subl=ms.id_sloc and inv_m.id_loc=l.id_loc and inv_m.id_inventory_m=si.id_inventory_m and si.id_product=ma.id_assetdiv "
						+"and si.id_inventory = '"+no_asset+"'  and si.status_store = 0";
				
			}
			if (id_inventory_m.equals("") && no_asset.equals("")) {
				if (!UserType.equals("SUPER"))
					sql = "select distinct inv_m.*,si.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,si.product,si.qty_fill,si.qty_empty,si.nm_acc_asset ,nm_subl,l.nm_loc from S_Inventory_Master inv_m ,S_Inventory si, M_Subloc ms,M_Loc l where  "
							+ " si.status_store = 0  and inv_m.id_inventory_m=si.id_inventory_m  and inv_m.id_subl=ms.id_sloc and  inv_m.id_loc=l.id_loc and (inv_m.no_inv like '"+searchWord+"%' or inv_m.dt_inv like '"+searchWord+"%' or nm_loc like '"+searchWord+"%' or nm_subl like '"+searchWord+"%'  or si.asset_prfx like '"+searchWord+"%' ) order by inv_m.no_inv ";
			
				else
					sql = "select distinct inv_m.*,si.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,si.product,si.qty_fill,si.qty_empty,si.nm_acc_asset ,nm_subl,l.nm_loc from S_Inventory_Master inv_m ,S_Inventory si, M_Subloc ms,M_Loc l where   "
							+ " si.status_store = 0  and inv_m.id_inventory_m=si.id_inventory_m  and inv_m.id_subl=ms.id_sloc and  inv_m.id_loc=l.id_loc and   and (inv_m.no_inv like '"+searchWord+"%' or inv_m.dt_inv like '"+searchWord+"%' or nm_loc like '"+searchWord+"%' or nm_subl like '"+searchWord+"%'  or si.asset_prfx like '"+searchWord+"%' ) order by inv_m.no_inv ";
			
			} 
//			if (!no_asset.equals("0")) {
//				sql = "select  distinct inv_m.id_inventory_m, inv.id_inventory,inv_m.no_inv,inv_m.temp_no_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,"
//						+"(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,inv_m.id_subl,gr_tot ,others as others_cst, un_prc,id_product,product from S_Inventory_Master inv_m ," 
//						+"S_Inventory inv ,M_Asset_Div ma, M_Subloc ms where  inv_m.id_subl=ms.id_sloc and inv_m.id_inventory_m=inv.id_inventory_m and inv.id_product=ma.id_assetdiv "
//						+"and inv.id_inventory = '"+no_asset+"'  and inv_m.status_store = 0";
//			}
			System.out.println("KK"+sql);
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
	
	
	//display after add to stock
public JSONObject DisplayInventoryafterStock(String id, String id_inventory_m, String no_asset, String dt_frm, String dt_to,
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
					sql = "select distinct inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,"
							+ "nm_subl,nm_loc from S_Inventory_Master inv_m , M_Subloc ms ,M_Loc l  where  "
							+ " status_store = 1  and  inv_m.id_loc=l.id_loc and inv_m.id_subl=ms.id_sloc and  (inv_m.no_inv like '"+searchWord+"%' or dt_inv like '"+searchWord+"%' or dt_temp_inv like '"+searchWord+"%' or temp_no_inv like '"+searchWord+"%'  or nm_subl like '"+searchWord+"%'   or nm_loc like '"+searchWord+"%') order by inv_m.no_inv ";
				else
					sql = "select distinct inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,"
							+ "nm_subl,nm_loc from S_Inventory_Master inv_m , M_Subloc ms ,M_Loc l where   "
							+ " status_store = 1  and inv_m.id_loc=l.id_loc and  inv_m.id_subl=ms.id_sloc  and (inv_m.no_inv like '"+searchWord+"%' or dt_inv like '"+searchWord+"%' or dt_temp_inv like '"+searchWord+"%' or temp_no_inv like '"+searchWord+"%'  or nm_subl like '"+searchWord+"%'   or nm_loc like '"+searchWord+"%') order by inv_m.no_inv ";
			} else if (!id_inventory_m.equals("")) {
				sql = "select id_inventory_m,id_inventory,qty,un_prc,nm_acc_asset,mfr,ds_pro,id_product from S_Inventory inv where id_inventory_m = "
						+ id_inventory_m + " and status_store=1";
			}
			if (!id.equals("0")) {
				sql = "select  distinct inv.*,inv_m.id_inventory_m,inv_m.no_inv as invNO,inv_m.temp_no_inv as temp_invNO,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, inv_m.dt_temp_inv, 103), ' ', '-')) as dttempInv,"
						+"inv_m.id_loc,inv_m.id_subl,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot from S_Inventory_Master inv_m ,S_Inventory inv , M_Subloc ms,M_Loc l where inv_m.id_loc=l.id_loc and inv_m.id_subl=ms.id_sloc and inv_m.id_inventory_m=inv.id_inventory_m and inv_m.id_inventory_m = " + id
						+ " and inv_m.status_store = 1";
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
			System.out.println("sql error in S_Add_To_Stock.");
		}
		return jobj;
	}

		
	
	
	
	
	
	
	

	
public JSONObject DisplayInStock() {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try {
		
		
			
	
			  //sql = "select product_nm ,sum(qty) qty,nm_subl from S_Inventory_store st ,M_Subloc ms  where st.id_subl=ms.id_sloc  group by product_nm,nm_subl";
			
				sql= "select id_product, nm_assetdiv ,ma.asset_prod_prefix, tot_aval_qty ,tot_aval_empty,tot_rent_aval_qty ,tot_servc_aval_qty ,ms.nm_subl,l.nm_loc from S_Store_Qty st, M_Asset_Div ma," 
						+"M_Subloc ms,M_Loc l where st.id_sloc= ms.id_sloc and st.id_loc= l.id_loc and st.id_product= ma.id_assetdiv ";
			 
		
		    
			
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
			System.out.println(e+" error in S_Add_To_Stock.");
		}
		System.out.println(jobj);
		return jobj;
	}

	
//	public JSONObject GenerateInvoiceNo()
//	{
//		String finyear="",poID2="";
//		int PoId=1;
//		String name="";
//		JSONObject jobj = new JSONObject();
//		JSONArray jarr = new JSONArray();
//		
//		try
//		{
//			
//			
//			String year="",end="";
//			ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
//			rs=ps.executeQuery();
//		if(rs.next())
//		{
//			year=rs.getString("start");
//			end=rs.getString("enddt");
//		}
//    
//		String strt = year.substring (2,4);
//		String endt = end.substring (2,4);
//		String asset_prefix="";
//		ps=con.prepareStatement("select asset_prefix from M_Company");
//		rs=ps.executeQuery();
//	if(rs.next())
//	{
//		asset_prefix=rs.getString(1);
//		 
//	}
//
//		
//		//String sql1= "select count(lead_no) ,cd_subl from O_lead_Master lm, M_Subloc msl  where lm.id_sloc=msl.id_sloc  and lm.id_sloc ="+id_sloc+" group by cd_subl";
//        String sql1= "select COUNT(no_inv)+1 as InvNO from S_Inventory_Master";
//        System.out.println(sql1);
//		ps=con.prepareStatement(sql1);
//		rs=ps.executeQuery();
//		JSONObject jobj2 = new JSONObject();
//			if(rs.next())
//			{
//				 System.out.println(rs.getInt(1));
//				PoId =	rs.getInt(1) +1;
//			 	
//				String 	poID3 = String.format("%03d", PoId);
//				
//				poID2=asset_prefix+"-"+"INV/"+"-"+poID3;
//				jobj2.put(name,poID2);
//			}
//			else {
//                String 	poID3 = String.format("%03d", 1);
//				
//				poID2=asset_prefix+"-"+"INV/"+"-"+poID3;
//				jobj2.put(name,poID2);
//			}
//			
//			jarr.put(jobj2);
//			jobj.put("data", jarr);
//		}
//		catch(Exception e)
//		{
//			System.out.println("Eroor ...  "+e.toString());
//		}
//		return jobj;
//	}
	
	
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
	
	
//public JSONObject GenerateSerialNoAndSAPNo(String id_inven)
//	{
//		JSONObject jobj = new JSONObject();
//		JSONArray jarr = new JSONArray();
//		JSONObject jobj2 = new JSONObject();
//		String id_prod="";
//	String sql = "select id_product from S_Inventory where id_inventory ='"+id_inven+"'";
//	try
//	{
//	ps=con.prepareStatement(sql);
//	if(rs.next())
//	{
//		id_prod  = rs.getString(1);
//		
//    }
//		
//			String id_wh_dyn="",preFix="",preFix1="",preFix2="",pre="";
//			int tempAstId=0;
//			System.out.println("select ltrim(asset_prod_prefix) from M_Asset_Div gp where  gp.id_assetdiv="+id_prod+"");
//			
//			ps=con.prepareStatement("select ltrim(asset_prod_prefix) from M_Asset_Div gp where  gp.id_assetdiv="+id_prod+"");
//			rs=ps.executeQuery();
//			if(rs.next())
//			{
//				preFix2  = rs.getString(1);
//				//pre  = rs.getString(1);
//				//pre = preFix2+"/"+preFix3;
//            }
//			//ps=con.prepareStatement("select ltrim(asset_prefix) from M_Company");
//			ps=con.prepareStatement("select ltrim(cd_loc) from M_loc where id_loc="+id_loc+"");
//			rs=ps.executeQuery();
//			if(rs.next())
//			{
//				preFix1 =  rs.getString(1);
//				
//            }
//			String preFix3="";
//			preFix1= preFix1.toUpperCase();
//			System.out.println("Prefix is"+ preFix1);
//			//System.out.println("PRE****"+pre);
//			if(preFix2.length() <= 4)
//			{
//				preFix3=preFix2;
//			}
//			else
//			{
//				preFix3=pre.substring(0, Math.min(pre.length(), 4));
//			}
//			preFix =preFix1+"/"+preFix3+"-";
//			
//			
//		
//				
//			String sql="";
//			
//			
//			StringTokenizer st = new StringTokenizer(SerialVal , ",,");
//			
//			k=0;
//			while (st.hasMoreElements()) 
//			{
//				
//				System.out.println("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
//						"from S_I_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') ");
//				
//				ps=con.prepareStatement("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
//						"from S_I_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') ");
//				
//				//sql="select  COUNT(id_wh)+1 from A_Ware_House where substring(id_wh_dyn,1,patindex('%000%', id_wh_dyn)-2) = upper('"+preFix+"')";
//				//ps=con.prepareStatement(sql);
//				rs1=ps.executeQuery();
//				
//					if(rs1.next())
//					{
//						tempAstId =	rs1.getInt(1);
//					}
//					String formatValue  = String.format("%04d", tempAstId);
//					id_wh_dyn	=	preFix + formatValue;
//									
//				
//			
//				String query="insert into S_I_Ware_House("+colName+", id_wh_dyn,add_by,serial_no) values("+value+" , '"+id_wh_dyn+"',"+id_emp_user+",'"+st.nextElement()+"')";
//					
//				System.out.println(query);
//				PreparedStatement ps=con.prepareStatement(query);
//				j=ps.executeUpdate();
//				System.out.println("J:"+j);
//
//			}//
//		}
//		catch(Exception e)
//		{
//			System.out.println("sql error in A_Add_To_Store."+e.toString());
//		}
//		 
//		return jobj;
//	}
	

public JSONObject GenerateSerialNoAndSAPNo(String id_inven)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	JSONObject jobj2 = new JSONObject();
	String sql = "select COUNT(id_iwh)+1 as slNo from S_I_Ware_House Iwh";
	try
	{
		System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
	 
	    if(rs.next())
	    {
	    	
	    	
	    	jobj2.put("slNo",rs.getInt("slNo"));
	     
	    	jarr.put(jobj2);
        }
	    else
	    {
	    	jobj2.put("slNo","1");
		     
	    	jarr.put(jobj2);
        }
	    jobj.put("data", jarr);
	}
	catch(Exception e)
	{
		System.out.println("sql error in A_Add_To_Store."+e.toString());
	}
	 
	return jobj;
}



public JSONObject DropDownResult(String id,String id1,String id2,String type_grp)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();

	String sql="";
	if(type_grp.equals("TYPE-CYLINDER")) {
		System.out.println("hh"+type_grp);
	 sql=" select serial_no ,serial_no from S_I_Ware_House where device_status='in_store' and sr_no_status=0  and filling_status=1 and id_product="+id+" and id_subl="+id1+" and id_loc="+id2+"" ;
		System.out.println(sql);
	}else {
		System.out.println("hhh"+type_grp);
		System.out.println(type_grp);
	sql=" select serial_no ,serial_no from S_I_Ware_House where device_status='in_store' and sr_no_status=0  and id_product="+id+" and id_subl="+id1+" and id_loc="+id2+"" ;
	System.out.println(sql);
	}
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
	    System.out.println(jobj);
	}
	catch(Exception e)
	{
		System.out.println(e+"sql error in Add to stock.");
	}
	 
	return jobj;
	
	
}

public JSONObject DropDownResultwithAlldata(String id,String id1,String id2,String serial_no,String type_grp)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	System.out.println(id2);
	String sql="";
	if(type_grp.equals("TYPE-CYLINDER"))  {
		
		 sql=" select serial_no ,serial_no from S_I_Ware_House where device_status='in_store' and filling_status=1 and  sr_no_status=0  and id_product="+id+" and id_subl="+id1+" and id_loc="+id2+" or serial_no='"+serial_no+"'"  ;
		System.out.println(sql);
		
	}
	else {
	
		sql=" select serial_no ,serial_no from S_I_Ware_House where device_status='in_store' and sr_no_status=0  and id_product="+id+" and id_subl="+id1+" and id_loc="+id2+" or serial_no='"+serial_no+"'"  ;
		System.out.println(sql);
		
	}
	
	
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
	    System.out.println(jobj);
	}
	catch(Exception e)
	{
		System.out.println(e+"sql error in Add to stock.");
	}
	 
	return jobj;
	
	
}



	
public JSONObject CheckExitsVal(String SerialVal)
{
	JSONObject json=new JSONObject();
	StringTokenizer st = new StringTokenizer(SerialVal , ",,");

	String query = "";
	int t=1,Count=0;
	String no_mfr="",serialNo="";
	try 
	{
		
		/*
		 * while (st.hasMoreElements()) { Count ++; no_mfr = (String) st.nextElement();
		 * if(!no_mfr.equals("NA")) { query =
		 * "select id_iwh from S_I_Ware_House where no_mfr='"+no_mfr+"'";
		 * ps=con.prepareStatement(query); rs=ps.executeQuery(); if(rs.next()) {
		 * json.put("data",1); json.put("Count",Count); t=0; break; } } }
		 */
		

		
		if(t == 1)
		{
			Count = 0;
			while (st.hasMoreElements()) 
			{
				Count ++;
				serialNo = (String) st.nextElement();
				
					query = "select id_iwh from S_I_Ware_House where serial_no='"+serialNo+"'";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						json.put("data",2);
						json.put("Count",Count);
						t=0;
						break;
					}
				
			}
			if(t == 1)
			{
				json.put("data",0);
			}
			
		}
		
		
	}
		
	catch (Exception e)
	{
		
		e.printStackTrace();
	}
	return json;
}
	
	
}