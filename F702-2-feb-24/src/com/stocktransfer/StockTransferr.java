package com.stocktransfer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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


public class StockTransferr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String UserName = (String)session.getAttribute("UserName");
		int id_emp_user = (int)session.getAttribute("id_emp_user");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		HashMap<String, String> map =new HashMap<String,String>();
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd"); 
		 Enumeration elemet = request.getParameterNames();
		 
		 con=Common.GetConnection(request);
			String col_Val1="",sql1="";
			int k=0;
		 
		 String paramName="";
		 String paramValues="";
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
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		String id_work="",action = "",id_locfr="",id_frstr="",id_prc="",id_dc1="0",word="",id="0",req_ast_id="",id_inv_m="",confirm="1",dt_to="",dt_frm="",value="",ColName="",no_req_val="",dt_req="",id_prod_loc="",id_store_loc="";
		if(request.getParameter("id_create_work_order") !=null)
		{
			id_work = request.getParameter("id_create_work_order");
		}
		if(request.getParameter("frm_id_loc") !=null)
		{
			id_locfr = request.getParameter("frm_id_loc");
		}
		if(request.getParameter("frm_id_store") !=null)
		{
			id_frstr = request.getParameter("frm_id_store");
		}
		if(request.getParameter("frm_id_process") !=null)
		{
			id_prc = request.getParameter("frm_id_process");
		}
		
		
		if(request.getParameter("id_dc1") !=null)
		{
			id_dc1 = request.getParameter("id_dc1");
		}
		
		if(request.getParameter("id_prod") !=null)
		{
			id_prod_loc = request.getParameter("id_prod");
		}if(request.getParameter("id_wh_inventory") !=null)
		{
			id_store_loc = request.getParameter("id_wh_inventory");
		}
		if(request.getParameter("dt_req") !=null)
		{
			dt_req = request.getParameter("dt_req");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("no_req_val") !=null)
		{
			no_req_val = request.getParameter("no_req_val");
		}
		if(request.getParameter("invoiceId") !=null)
		{
			req_ast_id = request.getParameter("invoiceId");
		}
		if(request.getParameter("id_inv_m") !=null)
		{
			id_inv_m = request.getParameter("id_inv_m");
		}
		if(request.getParameter("confirm") !=null)
		{
			confirm = request.getParameter("confirm");
		}
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
			
		}
		
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		try
		{
			/*int id_emp_user = (int)session.getAttribute("id_emp_user");
			String User_Type = (String)session.getAttribute("UserType");
			int id_log_user = (int)session.getAttribute("UserId");*/
			
			
			Date currDate = new Date();
			if(dt_frm.equals(""))
			{
				dt_frm = "1990-01-01";
			}
			else
			{
				dt_frm = dateFormatNeeded.format(userDateFormat.parse(dt_frm));
			}
			if(dt_to.equals(""))
			{
				dt_to = dateFormatNeeded.format(currDate);
			}
			else
			{
				dt_to = dateFormatNeeded.format(userDateFormat.parse(dt_to));
			}
			
			if(!dt_req.equals(""))
			dt_req = dateFormatNeeded.format(userDateFormat.parse(dt_req));
			con=Common.GetConnection(request);
 
			switch (action)
	        {
	        case "CheckExitsVal":
            	jobj = CheckExitsVal(value,ColName);	
                break;
                
	        case "transfer":
				String colName = "";
				int j = 0, id_lead_m1 = 0;
				String cd_prod="";
				String to_id_loc="",to_id_sloc="",frm_id_loc="",frm_id_sloc="",dt_exp_rent1="", nm_src="";
			    int id_Transfer_stock1= 0;
			    to_id_loc=map.get("to_id_loc");
			    to_id_sloc=map.get("to_id_sloc");
			    frm_id_loc=map.get("frm_id_loc");
			    frm_id_sloc=map.get("frm_id_sloc");
				try {
				/*	ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					if (!rs.next()) {*/
					stmt = con.createStatement();
					rs = Common.GetColumns("T_TRANSFER_DC_Master", request);
				
						while (rs.next()) {
							if (rs.getString(1) != "id_Transfer_stock") {
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
						
						String sql="";
						
				
						
					
					
					
						sql = "insert into T_TRANSFER_DC_Master(" + colName
								+") values(" + value + ")";

								
						System.out.println(sql);
						stmt = con.createStatement();
						stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
						rs = stmt.getGeneratedKeys();
						while (rs.next()) {
							id_Transfer_stock1 = rs.getInt(1);
							System.out.println(id_Transfer_stock1);
						}
						String ds_pro ="",id_prod="",ds_assetdiv="", in_stoc_qty="";
						String count[] = request.getParameterValues("count");
						stmt = con.createStatement();
						j = 0;
						for (int i = 0; i < count.length; i++) {
							 cd_prod = request.getParameter("id_prod" + count[i] + "");
							if (!cd_prod.isEmpty()) {
								
								sql = "select * from M_Asset_Div where nm_assetdiv='" + cd_prod + "'";
								ps = con.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									id_prod = rs.getString("id_assetdiv");
									ds_pro = rs.getString("nm_assetdiv");
			                        ds_assetdiv = rs.getString("ds_assetdiv");
			                       
								} 
								
 
								String un_prc = request.getParameter("un_prc" + count[i] + "");
								//String quantity = request.getParameter("quantity" + count[i] + "");
								String serial_no = request.getParameter("sr_no" + count[i] + "");
								String gr_tot = request.getParameter("gr_tot" + count[i] + "");
			                    in_stoc_qty = request.getParameter("in_stoc_qty" + count[i] + "");
								System.out.println(
										"insert into T_Transfer_Stock_DC(nm_prod,ds_product,sr_no,transfer_by,quantity,un_prc,id_prod,id_Transfer_stock) "
												+ "values('" + ds_pro + "','" + ds_assetdiv + "','"+serial_no+"','"
												+ id_emp_user+ "','1'," + un_prc + ",'"+ id_prod + "','"+id_Transfer_stock1+"')");
								j=stmt.executeUpdate(
										"insert into T_Transfer_Stock_DC(nm_prod,ds_product,sr_no,transfer_by,quantity,un_prc,id_prod,id_Transfer_stock) "
												+ "values('" + ds_pro + "','" + ds_assetdiv + "','"+serial_no+"','"
												+ id_emp_user+ "','1'," + un_prc + ",'"+ id_prod + "','"+id_Transfer_stock1+"')");
							
							
							
							if(j>0) {
								j=0;
								 System.out.println("update S_I_Ware_House set id_loc='"+to_id_loc+"',id_subl='"+to_id_sloc+"' where serial_no ='"+serial_no+"'");
									
								j= stmt.executeUpdate("update S_I_Ware_House set id_loc='"+to_id_loc+"',id_subl='"+to_id_sloc+"'  where serial_no ='"+serial_no+"'");
							    if(j>0) {
							    	j=0;
									sql = "select tot_aval_qty from S_Store_Qty where id_product='" + id_prod + "' and id_sloc='"+ frm_id_sloc +"' and id_loc='"+ frm_id_loc +"'";
									System.out.println(sql);
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
									stmt= con.createStatement();
									if (rs.next()) {
										System.out.println("Update  S_Store_Qty  set tot_aval_qty =("+rs.getInt(1)+"-1) where id_product="+id_prod+" and id_sloc="+frm_id_sloc+" and id_loc='"+ frm_id_loc +"' ");
										j=stmt.executeUpdate(
												"Update  S_Store_Qty  set tot_aval_qty =("+rs.getInt(1)+"-1) where id_product="+id_prod+" and id_sloc="+frm_id_sloc+" and id_loc='"+ frm_id_loc +"' ");
                                        if(j>0) 
                                        {
                                        	j=0;
                                        	sql = "select tot_aval_qty from S_Store_Qty where id_product='" + id_prod + "' and id_sloc='"+ to_id_sloc +"' and id_loc='"+ to_id_loc +"'";
                                        	System.out.println(sql);
                                        	ps = con.prepareStatement(sql);
        									rs = ps.executeQuery();
        									stmt= con.createStatement();
        									if (rs.next()) {
        										System.out.println("updatquery");
        										System.out.println("Update  S_Store_Qty  set tot_aval_qty =("+rs.getInt(1)+"+1) where id_product="+id_prod+" and id_sloc="+to_id_sloc+" and id_loc='"+ to_id_loc +"' ");
        										j=stmt.executeUpdate(
        												"Update  S_Store_Qty  set tot_aval_qty =("+rs.getInt(1)+"+1) where id_product="+id_prod+" and id_sloc="+to_id_sloc+" and id_loc='"+ to_id_loc +"' ");
        									
        									}else {
        										System.out.println("insertquery");
        										System.out.println("insert into S_Store_Qty (id_product,id_loc,id_sloc,nm_product,tot_aval_qty) values('"+ id_prod + "','" + to_id_loc + "','" + to_id_sloc + "','"+ds_pro+"','1')");
        										j=stmt.executeUpdate(	
        												"insert into S_Store_Qty (id_product,id_loc,id_sloc,nm_product,tot_aval_qty) values('"+ id_prod + "','" + to_id_loc + "','" + to_id_sloc + "','"+ds_pro+"','1')");
        				
        									}
                                        }
									    
									}
									
										
//										}
							    }
								 
							}
							
							
						}
						
							
					} 
//				else {
//						System.out.println("will be duplicate invoice");
//						j = 2;
//					}
				} catch (Exception e) {
					System.out.println("Error in O_Lead" + e);
				}
				jobj.put("data", j);
				break;
            case "autogenrateno":
            	jobj = Autoworkno();	
            	
                break;
                
         }
	
		request.setAttribute("data", jobj.toString());
		response.getWriter().write(jobj.toString());
		
		
	}catch(Exception e)
	{
		System.out.println(e);
	}
		finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	
}
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_Transfer_stock from T_TRANSFER_DC_Master where "+ColName+" = '"+value+"'";
		System.out.println(query);
		try 
		{
			
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("data",1);
			}
			else
			{
				json.put("data",0);
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}

	public JSONObject Autoworkno()
	{
		String finyear="",poID2="";
		int PoId=1;
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			String year="",end="";
		//	ps=con.prepareStatement("select extract(year from to_date(STD_FINANCE,'yyyy/mm/dd')) as stdate,extract(year from to_date(END_FINANCE,'yyyy/mm/dd')) as endyear from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			System.out.println("select std_finance as  start , endt_second as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			//con=Common.GetConnection();
			
			ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
				rs=ps.executeQuery();
			if(rs.next())
			{
				year=rs.getString("start");
				end=rs.getString("enddt");
			}
			
			
			String strt = year.substring (2,4);
			String endt = end.substring (2,4);
		ps=con.prepareStatement("select id_transfer_stock from T_TRANSFER_DC_Master order by id_transfer_stock  desc  ");
		rs=ps.executeQuery();
		
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				
			}
			
		String 	poID3 = String.format("%03d", PoId);
			poID2= "Stock-DC/"+poID3+"/"+strt+"-"+endt;
			  System.out.println(poID2);
			  jobj.put("dc",poID2);
			
		}
		
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return jobj;
	}
	
	
}
