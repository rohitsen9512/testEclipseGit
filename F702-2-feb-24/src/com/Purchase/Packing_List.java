package com.Purchase;

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
import com.Util.dtoUtils;

import dto.Common.UserAccessData;


public class Packing_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		 HttpSession session = request.getSession();  
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  

		String paramName = "";
		String paramValues = "";
		 try
		 {
		        
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      String[] parts = paramName.split("_");
		      paramValues = request.getParameter(paramName);
		      if((parts[0].equals("dt") || (parts[0].equals("endt")) || (parts[0].equals("std"))) && !paramValues.equals(""))
		      {
		    	  paramValues = request.getParameter(paramName);
		    	  System.out.println("Date from forms"+paramValues);
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
			 System.out.println("Date from forms:"+paramValues);
			 System.out.println("EXE:"+e.toString());
		 }
		    
		String action = "",word="",typ_asset="",id="",id_prod="",dt_to="",dt_frm="",id_inv="",id_inv_m="",SerialVal="",id_sgrp="",id_grn="",sapNo="",value="";
		
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("id_prod") !=null)
		{
			id_prod = request.getParameter("id_prod");
		}
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("typ_asset") !=null)
		{
			typ_asset = request.getParameter("typ_asset");
		}
		if(request.getParameter("id_grn") !=null)
		{
			id_grn = request.getParameter("id_grn");
		}
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		
		if(request.getParameter("id_inv") !=null)
		{
			id_inv = request.getParameter("id_inv");
		}
		if(request.getParameter("id_inv_m") !=null)
		{
			id_inv_m = request.getParameter("id_inv_m");
		}
		if(request.getParameter("sapNo") !=null)
		{
			sapNo = request.getParameter("sapNo");
		}
		if(request.getParameter("SerialVal") !=null)
		{
			SerialVal = request.getParameter("SerialVal");
		}
		if(request.getParameter("id_sgrp") !=null)
		{
			id_sgrp = request.getParameter("id_sgrp");
		}
		try
		{
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
		String temp="";
		if(!dt_to.equals(""))
		{
			temp = " and dt_inv <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_inv >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_inv >= '"+dt_frm+"' and dt_inv <= '"+dt_to+"'";
		}
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType"); 
			con=Common.GetConnection(request);
			Common dbname=new Common();
			//String DBNM=dbname.dbname();
			String sql="";
			switch (action)
	        {
	        case "Save":
        		
	        	String colName="";
        		int j=0;
        		int id_pack_list=0;
        		
        		try
        		{
        				rs = Common.GetColumns("S_Packing_List",  request);
        				
        						
        						while (rs.next())
        						{
        						
        							if(!rs.getString(1).equals("id_pack_list"))
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
        				
        			}
        			catch(Exception e)
        				{
        					System.out.println(e);
        				}
        		
        		String query="insert into S_Packing_List("+colName+") values("+value+")";
        		System.out.println(query);
        		 PreparedStatement stmt_pre = con.prepareStatement(query, new String[] { "id_pack_list" });
        		 stmt_pre.executeUpdate();
        		 rs = stmt_pre.getGeneratedKeys();
        		 
                 if (rs.next()) 
                 {
                	 id_pack_list = rs.getInt(1);
                	 
                 }
        		try
        		{
        			colName="";value="";
        			String id_po = map.get("id_po");
        			String count[] = request.getParameterValues("count");
        			String count1[] = request.getParameterValues("count1");
        			stmt=con.createStatement();
        			j=0;
        			for(int i=0;i<count.length;i++)
        			{	
        				String id_prod1 = request.getParameter("id_prod"+count[i]+"");
        				
        				if(!id_prod1.isEmpty())
        				{
                		String no_small_box = request.getParameter("no_small_box"+count[i]+"");
                		String weight = request.getParameter("weight"+count[i]+"");
                		String container_no = request.getParameter("container_no"+count[i]+"");
                		String qty = request.getParameter("qty"+count[i]+"");
                		
                	 sql="insert into S_Packing_List_Asset(id_pack_list,id_prod,no_small_box,weight,container_no,id_po,qty) values("+id_pack_list+","+id_prod1+",'"+no_small_box+"',"+weight+",'"+container_no+"',"+id_po+","+qty+")";
                	 System.out.println(sql);
                		con=Common.GetConnection(request);
        				PreparedStatement ps=con.prepareStatement(sql);
        				j=ps.executeUpdate();
        				}
	
        			
        		}
        			for(int i=0;i<count1.length;i++)
        			{
        				String box_no = request.getParameter("box_no"+count1[i]+"");
        				String no_box = request.getParameter("no_box"+count1[i]+"");
        				if(!box_no.isEmpty() && !no_box.isEmpty())
        				{
            		String gross_wt = request.getParameter("gross_wt"+count1[i]+"");
            		String net_wt = request.getParameter("net_wt"+count1[i]+"");
            		String dimensions = request.getParameter("dimensions"+count1[i]+"");
            	 sql="insert into S_Packing_List_Asset1(id_pack_list,box_no,no_box,gross_wt,net_wt,dimensions) values("+id_pack_list+",'"+box_no+"','"+no_box+"','"+gross_wt+"','"+net_wt+"','"+dimensions+"')";
            	 System.out.println(sql);
            		con=Common.GetConnection(request);
    				PreparedStatement ps=con.prepareStatement(sql);
    				j=ps.executeUpdate();
        			}
        		}
        			
        			query="update P_Purchase_Order set packing=1 where id_po='"+id_po+"' ";
        			System.out.println(query);
    				ps=con.prepareStatement(query);
    				j=ps.executeUpdate();
    				
        			jobj.put("data", j);					
    				
    			}
    			catch(Exception e)
    				{
    					System.out.println(e);
    				}
        		
        		
        		
           break;

	        
		                
	            case "Display":
	            	 sql="select si.id_po,si.no_po,(replace(convert(NVARCHAR, si.dt_po, 103), ' ', '-')) as dtpo from P_Purchase_Order si where si.packing=0 ";
	            	 System.out.println(sql);
	            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);
	        		
	                break;
	                
	            case "DisplayPreview":
	            	 sql="select p.*,v.nm_ven,(replace(convert(NVARCHAR, p.dt_po, 103), ' ', '-')) as dtpo from S_Packing_List p,P_Purchase_Order po,M_Vendor v where p.id_po=po.id_po and po.id_ven=v.id_ven ";
	            	 System.out.println(sql);
	            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);
	        		
	                break;
	                
	            case "Edit":
	            	 sql="select *,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtpo from P_Purchase_Order where id_po="+id+"";
	            	 System.out.println(sql);
	            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);
	        		
	                break;
	                
	            case "EditPreview":
	            	 sql="select *,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtpo,(replace(convert(NVARCHAR, dt_exp_dlvr, 103), ' ', '-')) as dtexpdlvr from S_packing_List where id_pack_list="+id+"";
	            	 System.out.println(sql);
	            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);
	        		
	                break;
	                
	            case "DisplayLineItem":
	             sql="select dc.*,t1.nm_tax nm_tax1 ,t2.nm_tax nm_tax2,pa.id_prod,pc.nm_model,pc.cd_model,pa.qty"+
	            	" from P_Purchase_Order dc,M_Tax t1,M_Tax t2,P_Purchase_Order_Asset pa,M_Model pc"+
	             	 " where   pa.id_tax1=t1.id_tax and pa.id_tax2=t2.id_tax and dc.id_po=pa.id_po and pa.id_prod=pc.id_model and dc.id_po="+id+" ";
	             System.out.println(sql);
	            	 	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);	
	                break;
	                
	            case "DisplayLineItemPreview":
		             sql="select p.*,po.no_po,pc.nm_model,pc.cd_model from S_Packing_List_Asset p,P_Purchase_Order po,M_model pc where p.id_po=po.id_po and p.id_prod=pc.id_model and p.id_pack_list="+id+" ";
		             System.out.println(sql);
		            	 	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);	
		                break;
	            case "DisplayLineItem1Preview":
		             sql="select * from S_Packing_List_Asset1 where id_pack_list="+id+" ";
		             System.out.println(sql);
		            	 	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);	
		                break;   
	            /*case "DisplayLineItem":
	            	
	            	 sql="";
	        		String id = map.get("id");
	        		if(!DBNM.equals("SQL")){
	            		
		    			
	    			sql = "select TO_CHAR(CURRENT_DATE, 'DD/MM/YYYY') || '-' || g.no_po ||'-'|| pc.cd_prod as lot_num,pc.nm_prod,pc.cd_prod,pc.uom,ga.qty_recv,ga.id_grn_asset,wmp,wmp_qty,pc.id_prod from G_Grn g,G_Grn_Asset ga,M_Prod_Cart pc where g.id_grn=ga.id_grn and pc.id_prod=ga.id_prod and zeroqty=0 and g.id_grn="+id+" ";
	        		}
	        		
	        		else{
	        			sql = "select convert(varchar, getdate(), 103)  + '-'+ g.no_po  + '-'+ pc.cd_prod as lot_num,pc.nm_prod,pc.cd_prod,pc.uom,ga.qty_recv,ga.id_grn_asset,wmp,wmp_qty,pc.id_prod from G_Grn g,G_Grn_Asset ga,M_Prod_Cart pc where g.id_grn=ga.id_grn and pc.id_prod=ga.id_prod and zeroqty=0 and g.id_grn="+id+" ";
		        		
	        		}
	    			System.out.println(sql);
	            	
	            	//jobj = DisplayAccessoryForAddToStore(temp);	
	            	 uad = new UserAccessData();
	            	jobj = uad.GetDataAsJsonFormate(sql);
	            	
	                break;*/
	           /* case "DisplayLineItemForStore":
	            	
	        		id = map.get("id");
	    			sql = "select ga.id_grn_asset,pc.id_prod,id_store_loc from G_Grn g,G_Grn_Asset ga,M_Prod_Cart pc,I_INVENTORY_STORE invs where g.id_grn=ga.id_grn and pc.id_prod=ga.id_prod and invs.id_prod=pc.id_prod and zeroqty=0 and g.id_grn="+id+" ";
	    		
	    			System.out.println(sql);
	            	
	            	 uad = new UserAccessData();
	            	jobj = uad.GetDataAsJsonFormate(sql);
	            	
	                break;*/
	                
	           /* case "Edit":
	            	jobj = EditDataForAddToStore(id_inv,id_inv_m);	
	                break;*/
	            case "CheckSlAndSpNo":
	            	jobj = CheckExitsVal(SerialVal,sapNo);	
	                break;
	            case "CheckStoreLocation":
	            	jobj = CheckStoreLocation(id_prod);	
	                break;
	                
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in A_Add_To_Store.");
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
	
	public JSONObject CheckStoreLocation(String id_prod)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="",storeLoc="";
		
		sql="Select id_store_loc from I_INVENTORY_STORE where id_prod="+id_prod+"";
		System.out.println(sql);
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		    if(rs.next())
		    {
		    	storeLoc = rs.getString(1);
		    	jobj.put("storeLoc",storeLoc );
	        }
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Add_To_Store."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	
	/* public JSONObject AddToStorenventory(HashMap<String, String> map , String id_prod1  , String id_inventory , String id_inventory_m, String id_grn, int id_emp_user)
	{
		Common dbname=new Common();
		String DBNM=dbname.dbname();
		
		String colName="",value="",colName1="",value1="",col_Val="0";
		int j=0;
		JSONObject json=new JSONObject();
		
		try 
		{
				rs = Common.GetColumns("I_WARE_HOUSE");
				
				
				while (rs.next())
				{
				
					if(rs.getString(1) !="id_wh_inventory")
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
				
				rs1 = Common.GetColumns("I_INVENTORY_STORE");
				while (rs1.next())
				{
				
					if(rs1.getString(1) !="id_inventory_store")
					{
						    if (map.containsKey(rs1.getString(1)))
						    {
						    	if(colName1.equals(""))
						    	{
							    	colName1 += rs1.getString(1);
							    	value1 += "'"+ map.get(rs1.getString(1))+"'";
						    	}
						    	else
						    	{
						    		colName1 +=","+ rs1.getString(1);
							    	value1 +=", '"+ map.get(rs1.getString(1))+"'";
						    	}
						    	if(rs1.getString(1).equals("qty_recv"))
						    	{
						    		col_Val =  map.get(rs1.getString(1));
						    	}
						    	
						    	
						    }
					  }
				}
String no_grn="",dt_grn="",no_inv="",dt_inv="",no_po="",dt_po="",id_inv_m="",cst_asst="",lot_num="",storeLocId="",id_grn_asset="",no_pobud="";
String NM_ACC_ASSET="",QTY_RECV="",reclass="",grnd_tot="",UN_PRC="",total_prc="",wmp="",rate="",ID_GRP="",ID_SGRP="",ID_GRN_ASSET="",ID_INV="",id_prod="",storeColName="",wmp_qty="";

id_grn = map.get("id_grn");
String sql="";
if(!DBNM.equals("SQL")){
	
			 sql = "select no_po,reclass,grnd_tot, rate,total_prc,wmp, TO_CHAR(CURRENT_DATE, 'DD/MM/YYYY') || '-' || g.no_po ||'-'|| pc.cd_prod as lot_num,g.no_grn,g.dt_grn,g.no_inv,g.dt_inv,g.no_po,g.dt_po,g.id_inv_m,TT_UN_PRC*ga.QTY_RECV as cst_asst,ga.* from G_Grn g, G_Grn_Asset ga,M_Prod_Cart pc ,M_EXCHANGE_RATE ex where g.id_grn=ga.id_grn and pc.id_prod=ga.id_prod and ex.ID_CURR=ga.ID_CURR and zeroqty=0 and g.id_grn="+id_grn+" ";
		
}
else{
	
	 sql = "select no_po,reclass,grnd_tot, rate,total_prc,wmp, convert(varchar, getdate(), 103) + '-'+ g.no_po + '-'+ pc.cd_prod as lot_num,g.no_grn,g.dt_grn,g.no_inv,g.dt_inv,g.no_po,g.dt_po,g.id_inv_m,TT_UN_PRC*ga.QTY_RECV as cst_asst,ga.* from G_Grn g, G_Grn_Asset ga,M_Prod_Cart pc ,M_EXCHANGE_RATE ex where g.id_grn=ga.id_grn and pc.id_prod=ga.id_prod and ex.ID_CURR=ga.ID_CURR and zeroqty=0 and g.id_grn="+id_grn+" ";
		
}

System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs1 = ps.executeQuery();
			while(rs1.next())
			{
				NM_ACC_ASSET = rs1.getString("NM_ACC_ASSET");
				reclass = rs1.getString("reclass");
				ID_GRP = rs1.getString("ID_GRP");
				ID_SGRP = rs1.getString("ID_SGRP");
				ID_GRN_ASSET = rs1.getString("ID_GRN_ASSET");
				ID_INV = rs1.getString("ID_INV");
				id_inv_m = rs1.getString("id_inv_m");
				id_prod =rs1.getString("id_prod");
				grnd_tot =rs1.getString("grnd_tot");
				UN_PRC = rs1.getString("TT_UN_PRC");
				total_prc= rs1.getString("total_prc");
				cst_asst =rs1.getString("cst_asst");
				lot_num = rs1.getString("lot_num");
				wmp_qty= rs1.getString("wmp_qty");
				wmp= rs1.getString("wmp");
				//"rate"  is used for currency rate........
				rate=rs1.getString("rate");
				Double TT_UN_PRC=0.0;
				double ind_budget=0.0;
				double bud_diff=0.0;
				if(wmp.equals("Yes")){
					QTY_RECV = rs1.getString("QTY_RECV");
					 TT_UN_PRC=(Double.parseDouble(rate))*(Double.parseDouble(grnd_tot))/ Double.parseDouble(wmp_qty);
					// TT_UN_PRC=Double.parseDouble(rate) * TT_UN_PRC;
					 ind_budget=TT_UN_PRC*Double.parseDouble(wmp_qty);
					 bud_diff=TT_UN_PRC*Double.parseDouble(wmp_qty);
						QTY_RECV = rs1.getString("wmp_qty");
				}else {
					QTY_RECV = rs1.getString("QTY_RECV");
				 TT_UN_PRC=(Double.parseDouble(rate))*(Double.parseDouble(grnd_tot))/Double.parseDouble(QTY_RECV);
				 ind_budget=TT_UN_PRC*Double.parseDouble(QTY_RECV);
				 bud_diff=TT_UN_PRC*Double.parseDouble(wmp_qty);
				}
				
			//...................budget....................calculation......................
				double qty_bud=rs1.getDouble("QTY_RECV");
				String budgetsq="",id_bud="";
				double first_bud=0.0;				
				no_pobud=lot_num = rs1.getString("no_po");
				budgetsq = "select sec_bud,ID_BUD,first_bud from m_budget b,M_Finance_Year fy where b.id_finance=fy.id_fincance and fy.active_year=1 and  id_prod="+id_prod+" ";
			    
				System.out.println(budgetsq);
						ps = con.prepareStatement(budgetsq);
						rs = ps.executeQuery();
						if(rs.next())
						{
							ind_budget= rs.getDouble("sec_bud")-ind_budget;
							id_bud=rs.getString("id_bud");
							first_bud=rs.getDouble("first_bud");
							budgetsq = "select rate ,req_qty,un_prc,pa.no_ind,tot_prc,id_prod from P_INDENT_ASSET pa,p_purchase_order po,P_REQUEST pr,M_EXCHANGE_RATE cr where cr.ID_CURR=pr.ID_CURR and  pr.id_req=pa.ID_REQ and po.NO_IND=pa.NO_IND and po.no_po='"+no_pobud+"' and id_prod="+id_prod+" ";
						    
							System.out.println(budgetsq);
									ps = con.prepareStatement(budgetsq);
									rs = ps.executeQuery();
									if(rs.next())
									{
											bud_diff=bud_diff - (qty_bud*rs.getDouble("un_prc")*(Double.parseDouble(rate)));
										
									}
									first_bud=first_bud+bud_diff;
						}else{
							budgetsq = "select sec_bud,ID_BUD,first_bud from m_budget b,M_Finance_Year fy where b.id_finance=fy.id_fincance and fy.active_year=1 and  ID_SGRP="+ID_SGRP+" ";
						    
							System.out.println(budgetsq);
									ps = con.prepareStatement(budgetsq);
									rs = ps.executeQuery();
									if(rs.next())
									{
										id_bud=rs.getString("id_bud");
										ind_budget= rs.getDouble("sec_bud")-ind_budget;
										first_bud=rs.getDouble("first_bud");
										
										budgetsq = "select rate ,req_qty,un_prc,pa.no_ind,tot_prc,id_prod from P_INDENT_ASSET pa,p_purchase_order po,P_REQUEST pr,M_EXCHANGE_RATE cr where cr.ID_CURR=pr.ID_CURR and  pr.id_req=pa.ID_REQ and po.NO_IND=pa.NO_IND and po.no_po='"+no_pobud+"' and id_prod="+id_prod+" ";
									    
										System.out.println(budgetsq);
												ps = con.prepareStatement(budgetsq);
												rs = ps.executeQuery();
												if(rs.next())
												{
													bud_diff=bud_diff - (qty_bud*rs.getDouble("un_prc")*(Double.parseDouble(rate)));
																
												}
												first_bud=first_bud+bud_diff;
		        							}else{
										budgetsq = "select sec_bud,ID_BUD ,first_bud from m_budget b,M_Finance_Year fy where b.id_finance=fy.id_fincance and fy.active_year=1  and  ID_GRP="+ID_GRP+" ";
									    
										System.out.println(budgetsq);
												ps = con.prepareStatement(budgetsq);
												rs = ps.executeQuery();
												if(rs.next())
												{
													id_bud=rs.getString("id_bud");
													ind_budget= rs.getDouble("sec_bud")-ind_budget;
													first_bud=rs.getDouble("first_bud");
														
													budgetsq = "select rate ,req_qty,un_prc,pa.no_ind,tot_prc,id_prod from P_INDENT_ASSET pa,p_purchase_order po,P_REQUEST pr,M_EXCHANGE_RATE cr where cr.ID_CURR=pr.ID_CURR and  pr.id_req=pa.ID_REQ and po.NO_IND=pa.NO_IND and po.no_po='"+no_pobud+"' and id_prod="+id_prod+" ";
												    
													System.out.println(budgetsq);
															ps = con.prepareStatement(budgetsq);
															rs = ps.executeQuery();
															if(rs.next())
															{
																bud_diff=bud_diff - (qty_bud*rs.getDouble("un_prc")*(Double.parseDouble(rate)));
																			
															}
															first_bud=first_bud+bud_diff;
												
												}
										
									}
						}
				if(!id_bud.equals("")){
				System.out.println("update m_budget set sec_bud="+ind_budget+",first_bud="+first_bud+" where id_bud="+id_bud+"");
					
				String bud_sql="update m_budget set sec_bud="+ind_budget+",first_bud="+first_bud+" where id_bud="+id_bud+"";
				ps = con.prepareStatement(bud_sql);
				ps.executeUpdate();
				}
				
				//end.........................................
				
				
				double unit_rate=0.0,totActiveQty=0.0,totActivePrc=0.0,wamtotprc=0.0,wamtotqty=0.0;
				id_grn_asset = rs1.getString("id_grn_asset");
				storeColName = "storeLoc"+id_grn_asset;
				storeLocId = map.get(storeColName);
				
				if(NM_ACC_ASSET.equals("ConG") || NM_ACC_ASSET.equals("CapG")){
					
					double tot_lot_prc=Double.parseDouble(QTY_RECV) * (TT_UN_PRC);
					
				sql = "insert into I_WARE_HOUSE("+colName+",ID_STORE_LOC,lot_num,ID_GRN_ASSET,ID_GRP,ID_SGRP,ID_INV,id_inv_m,id_prod,QTY_RECV,add_by,lot_qty,TT_UN_PRC,cst_asst,val_asst,wmp_qty,tot_lot_prc) " +
						" values("+value+","+storeLocId+",'"+lot_num+"',"+ID_GRN_ASSET+","+ID_GRP+","+ID_SGRP+","+ID_INV+","+id_inv_m+","+id_prod+","+QTY_RECV+","+id_emp_user+","+QTY_RECV+","+TT_UN_PRC+","+cst_asst+","+cst_asst+","+wmp_qty+","+tot_lot_prc+")";
				
				System.out.println(sql);
				ps = con.prepareStatement(sql);
				j = ps.executeUpdate();
				if(j > 0)
				{
					sql = "select id_inventory_store ,tot_active_qty,tot_active_prc, qty_recv,wmp_qty from I_Inventory_Store where id_prod="+id_prod+" ";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					if(!rs.next())
					{
						
						unit_rate=(tot_lot_prc+wamtotprc)/(wamtotqty+Double.parseDouble(QTY_RECV));
							j=0;
						 sql = "insert into I_INVENTORY_STORE(ID_GRP,ID_SGRP,id_prod,QTY_RECV,add_by,ID_STORE_LOC,wmp_qty)" +
						 		" values("+ID_GRP+","+ID_SGRP+","+id_prod+","+QTY_RECV+","+id_emp_user+","+storeLocId+","+wmp_qty+")";
						ps = con.prepareStatement(sql);
						j = ps.executeUpdate();
						j=1;
					}
					else
					{
						wamtotprc=rs.getDouble("tot_active_prc");
					wamtotqty=rs.getDouble("tot_active_qty");
					unit_rate=(tot_lot_prc+wamtotprc)/(wamtotqty+Double.parseDouble(QTY_RECV));
		                   System.out.println(unit_rate);
						double Tot = rs.getDouble("qty_recv") + Double.parseDouble(QTY_RECV);
						double WmpTot = rs.getDouble("wmp_qty") + Double.parseDouble(wmp_qty);
					
						sql ="update I_Inventory_Store set qty_recv = "+Tot+",wmp_qty="+WmpTot+",ID_STORE_LOC="+storeLocId+" where id_inventory_store = "+rs.getInt(1)+" ";
						ps = con.prepareStatement(sql);
						ps.executeUpdate();
						j=1;
							
					}
					
				}
				
				// Calculate WAM
				
				sql ="Select SUM(LOT_QTY),SUM(tot_lot_prc) from I_WARE_HOUSE where id_prod="+id_prod+" and lot_num_status=1";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next())
				{
					//unit_rate = rs.getDouble(2)/rs.getDouble(1);
					totActiveQty=rs.getDouble(1);
					totActivePrc=rs.getDouble(2);
				}
				
				sql ="Select id_wh_invntory from I_WARE_HOUSE where id_prod="+id_prod+" and lot_num_status=1 ";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next())
				{
					sql ="update I_WARE_HOUSE set unit_rate = "+unit_rate+",TOT_ACTIVE_QTY="+totActiveQty+",TOT_ACTIVE_PRC="+totActivePrc+" where id_wh_invntory = "+rs.getString("id_wh_invntory")+" ";
					System.out.println(sql);
					ps = con.prepareStatement(sql);
					ps.executeUpdate();
					
				}
				
				sql = "select id_inventory_store,id_prod from I_Inventory_Store where id_prod="+id_prod+" ";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next())
				{
					sql ="update I_Inventory_Store set unit_rate = "+unit_rate+",TOT_ACTIVE_QTY="+totActiveQty+",TOT_ACTIVE_PRC="+totActivePrc+" where id_inventory_store = "+rs.getInt(1)+" ";
					ps = con.prepareStatement(sql);
					ps.executeUpdate();
					j=1;
					sql ="update m_prod_cart set prod_unit_prc = "+unit_rate+" where id_prod = "+rs.getInt("id_prod")+" ";
					ps = con.prepareStatement(sql);
					ps.executeUpdate();
					j=1;
					
				}
				
		/////end		
				
				 
				
			  }else{
				 String colName3="",value3="";
				  rs = Common.GetColumns("A_WARE_HOUSE");
					
					
					while (rs.next())
					{
					
						if(rs.getString(1) !="id_wh")
						{
							    if (map.containsKey(rs.getString(1)))
							    {
							    	if(colName3.equals(""))
							    	{
								    	colName3 += rs.getString(1);
								    	value3 += "'"+ map.get(rs.getString(1))+"'";
							    	}
							    	else
							    	{
							    		colName3 +=","+ rs.getString(1);
								    	value3 +=", '"+ map.get(rs.getString(1))+"'";
							    		
							    	}
							    }
						  }
					}
					
					String id_wh_dyn="",preFix="",preFix1="",preFix2="";
					int tempAstId=0;
					
					ps=con.prepareStatement("select distinct ltrim(pre_asset) from M_Subasset_Div where id_s_assetdiv = "+ID_SGRP+"");
					rs=ps.executeQuery();
					if(rs.next())
					{
						preFix2  = rs.getString(1);
		            }
					preFix1  = "BRBNMPL";
					
					preFix =preFix1+"/"+preFix2+"-";
					
					
						
					 sql="";
					
					int count = Integer.parseInt(QTY_RECV);
					while (count >0) 
					{
						count--;
						
						ps=con.prepareStatement("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
								"from A_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') ");
						sql="select max(SUBSTR(id_wh_dyn,INSTR(id_wh_dyn, '-')+1,LENGTH(id_wh_dyn)-(INSTR(id_wh_dyn, '-'))))+1 as Current_id from A_Ware_House where id_wh_dyn like upper('"+preFix+"%')";
						ps=con.prepareStatement(sql);
						rs1=ps.executeQuery();
							if(rs1.next())
							{
								String tem=  rs1.getString(1);
								if(tem == null || tem == "null")
									tem=  "1";
								
								
								System.out.println("Temp val:"+tem);
								
								tempAstId =	Integer.parseInt(tem);
							}
							String formatValue  = String.format("%04d", tempAstId);
							id_wh_dyn	=	preFix + formatValue;
						String query="insert into A_Ware_House("+colName3+" ,no_mfr , id_wh_dyn,add_by,serial_no,ID_GRN_ASSET,ID_GRP,ID_SGRP,ID_INV,id_inv_m,id_prod) values("+value3+" ,'NA' , '"+id_wh_dyn+"',"+id_emp_user+",'NA',"+ID_GRN_ASSET+","+ID_GRP+","+ID_SGRP+","+ID_INV+","+id_inv_m+","+id_prod+")";
						System.out.println(query);
						PreparedStatement ps=con.prepareStatement(query);
						j=ps.executeUpdate();
					}
				  
			  }
			}
			
			
			
			/////reclassification...............................................
			String rem_qty_reclass="";
			String temprec="";
			sql="select sum(reclass_rem_qty) as rem_qty_reclass from g_grn g,G_GRN_ASSET ga where g.id_grn=ga.ID_GRN and g.id_grn='"+id_grn+"'";
			ps=con.prepareStatement(sql);
			rs1=ps.executeQuery();
			if(rs1.next()){
				rem_qty_reclass=rs1.getString("rem_qty_reclass");
			}
			if(rem_qty_reclass.equals("0")){
				temprec="   ,reclass=4";
			}
			else{
			if(reclass.equals("2")){
				temprec="   ,reclass=1";
			}
			}
				j=0;
				sql ="update G_Grn set status_store = 1 "+temprec+" where id_grn = "+id_grn+" ";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();
				j=1;
				
				json.put("data",j);
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
			return json;
	}

	*/
	
	public JSONObject CheckExitsVal(String SerialVal , String sapNo)
	{
		JSONObject json=new JSONObject();
		StringTokenizer st = new StringTokenizer(SerialVal , ",,");
		StringTokenizer st1 = new StringTokenizer(sapNo , ",,");
		String query = "";
		int t=1,Count=0;
		String no_mfr="",serialNo="";
		try 
		{
			
			while (st.hasMoreElements()) 
			{
				Count ++;
				no_mfr = (String) st.nextElement();
				if(!no_mfr.equals("NA"))
				{
					query = "select id_wh from A_Ware_House where no_mfr='"+no_mfr+"'";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						json.put("data",1);
						json.put("Count",Count);
						t=0;
						break;
					}
				}
			}
			if(t == 1)
			{
				Count = 0;
				while (st1.hasMoreElements()) 
				{
					Count ++;
					serialNo = (String) st1.nextElement();
					
						query = "select id_wh from A_Ware_House where serial_no='"+serialNo+"'";
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
	
	
	public JSONObject EditDataForAddToStore(String id_inv , String id_inv_m)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		Common dbname=new Common();
		//String DBNM=dbname.dbname();
		String sql="";
		
			/*sql="select aim.*,inv.id_sgrp,inv.id_grp,ds_pro,inv.mfr,inv.model as no_model,tt_un_prc as cst_asst,typ_asst from A_Invoice_Master aim ,A_Invoice inv  where  aim.id_inv_m =inv.id_inv_m and inv.id_inv = "+id_inv+" ";
		*/
			/*
			 * if(!DBNM.equals("SQL")){
			 * 
			 * sql="select aim.*,to_char (to_date(g.dt_grn,'YYYY-MM-DD'), 'DD/MM/YYYY') as dt_grn,no_grn,to_char (to_date(aim.dt_po,'YYYY-MM-DD'), 'DD/MM/YYYY') as dtPo,to_char (to_date(aim.dt_inv,'YYYY-MM-DD'), 'DD/MM/YYYY') as dtInv,inv.id_sgrp,inv.id_grp,ds_pro,inv.mfr,inv.model "
			 * +
			 * " as no_model,tt_un_prc as cst_asst,typ_asst,tt_un_prc*rate as val_in_inr,rate as ex_rate,inv.id_prod "
			 * + " from A_Invoice_Master aim ,A_Invoice inv,M_Exchange_Rate er, G_Grn g  "+
			 * " where er.id_curr=aim.id_curr and aim.id_inv_m =inv.id_inv_m and g.id_inv_m=aim.id_inv_m and inv.id_inv = "
			 * +id_inv+" "; } else{
			 * sql="select aim.*, (replace(convert(NVARCHAR, g.dt_grn, 103), ' ', '-')) as dt_grn,no_grn, (replace(convert(NVARCHAR, aim.dt_po, 103), ' ', '-')) as dtPo,  (replace(convert(NVARCHAR, aim.dt_inv, 103), ' ', '-')) as dtInv,inv.id_sgrp,inv.id_grp,ds_pro,inv.mfr,inv.model "
			 * +
			 * " as no_model,tt_un_prc as cst_asst,typ_asst,tt_un_prc*rate as val_in_inr,rate as ex_rate,inv.id_prod "
			 * + " from A_Invoice_Master aim ,A_Invoice inv,M_Exchange_Rate er, G_Grn g  "+
			 * " where er.id_curr=aim.id_curr and aim.id_inv_m =inv.id_inv_m and g.id_inv_m=aim.id_inv_m and inv.id_inv = "
			 * +id_inv+" ";
			 * 
			 * 
			 * }
			 */
		 System.out.println(sql);
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		//System.out.println("NAME:"+name);
		    		jobj2.put(name,rs.getString(name));
		    	}
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
	
	public JSONObject DisplayAccessoryForAddToStore(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select g.*,to_char (to_date(dt_grn,'YYYY-MM-DD'), 'DD/MM/YYYY') as dtGrn from G_Grn g where status_store = 0 and status_approv = 1 and type_grn='INV' and typ_po='CapG' "+temp+"";
		}
		else
		sql = "select g.*,to_char (to_date(dt_grn,'YYYY-MM-DD'), 'DD/MM/YYYY') as dtGrn from G_Grn g where status_store = 0 and status_approv = 1 and type_grn='INV' and typ_po='CapG'";

		
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
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
	


public JSONObject AddToStore(HashMap<String, String> map , String id_inv , String id_inv_m , String SerialVal , String sapNo, String id_sgrp ,int id_emp_user,String id_grn,HttpServletRequest request)
{
	String colName="",value="";
	int j=0;
	JSONObject json=new JSONObject();
	
	try 
	{
			rs = Common.GetColumns("A_WARE_HOUSE",  request);
			
			
			while (rs.next())
			{
			
				if(rs.getString(1) !="id_wh")
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
			
/*			while (rs.next())
			{

			
				if(rs.getString(1) !="id_wh")
				{
					if(rs.getString(1).equalsIgnoreCase("dt_amc_start") || 
							rs.getString(1).equalsIgnoreCase("dt_amc_exp"))
					{
						String warr = map.get("warr_amc");
						if(warr.equalsIgnoreCase("O"))
						{
							if(value.length() > 0 ) value += ",";

							value += (rs.getString(1) + "='1900-01-01'");
						}
						else
						{
							if(value.length() > 0 ) value += ",";
							value += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
						}
							
					}
					else
					if(rs.getString(1).equalsIgnoreCase("std_lease") || 
							rs.getString(1).equalsIgnoreCase("endt_lease"))
					{
						String lease = map.get("st_lease");
						if(lease.equalsIgnoreCase("NUL"))
						{
							if(value.length() > 0 ) value += ",";

							value += (rs.getString(1) + "='1900-01-01'");
						}
						else
						{
							if(value.length() > 0 ) value += ",";
							value += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
						}
							
					}
					else 
					if (map.containsKey(rs.getString(1)))
					    {
					    	if(value.equals(""))
					    	{
					    		value += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
						    	
					    	}
					    	else
					    	{
					    		
					    		value += ","+ rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
						    	
					    	}
					    }
					    
				  }
			}*/
			
			
			
			String id_wh_dyn="",preFix="",preFix1="",preFix2="";
			int tempAstId=0;
			
			ps=con.prepareStatement("select distinct ltrim(pre_asset) from M_Subasset_Div where id_s_assetdiv = "+id_sgrp+"");
			rs=ps.executeQuery();
			if(rs.next())
			{
				preFix2  = rs.getString(1);
            }
			preFix1  = "BRBNMPL";
			
			preFix =preFix1+"/"+preFix2+"-";
			
			
				
			String sql="";
			
			StringTokenizer st = new StringTokenizer(SerialVal , ",,");
			StringTokenizer st1 = new StringTokenizer(sapNo , ",,");
			while (st.hasMoreElements()) 
			{
				
				/*ps=con.prepareStatement("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
						"from A_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') ");*/
				sql="select max(SUBSTR(id_wh_dyn,INSTR(id_wh_dyn, '-')+1,LENGTH(id_wh_dyn)-(INSTR(id_wh_dyn, '-'))))+1 as Current_id from A_Ware_House where id_wh_dyn like upper('"+preFix+"%')";
				ps=con.prepareStatement(sql);
				rs1=ps.executeQuery();
					if(rs1.next())
					{
						String tem=  rs1.getString(1);
						if(tem == null || tem == "null")
							tem=  "1";
						
						
						System.out.println("Temp val:"+tem);
						
						tempAstId =	Integer.parseInt(tem);
					}
					String formatValue  = String.format("%04d", tempAstId);
					id_wh_dyn	=	preFix + formatValue;
				String query="insert into A_Ware_House("+colName+" ,no_mfr , id_wh_dyn,add_by,serial_no) values("+value+" ,'"+st.nextElement()+"' , '"+id_wh_dyn+"',"+id_emp_user+",'"+st1.nextElement()+"')";
				System.out.println(query);
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
			}
			if(j > 0)
			{
				
				j=0;
				String query ="update G_Grn set status_store = 1 where id_grn ="+id_grn+"";
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				
						j=1;
			
			}
			
			
			json.put("data",j);
	
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
		return json;
	}

}

