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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.DtoCommon;


public class Create_Invoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		HttpSession session = request.getSession(); 
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		Enumeration elemet = request.getParameterNames();
		DtoCommon ResuldData=new DtoCommon();
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
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		String id_inv_m="", id_po="",id_ven="",action = "",id="0",dt_to="",dt_recv="",id_quot="",dt_frm="",status_approv="",no_ind="",dt_approv="",acceptQuotNo="";
		
		if(request.getParameter("dt_recv") !=null)
		{
			dt_recv = request.getParameter("dt_recv");
		}
		if(request.getParameter("id_quot") !=null)
		{
			id_quot = request.getParameter("id_quot");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
		}
		if(request.getParameter("id_ven") !=null)
		{
			id_ven = request.getParameter("id_ven");
		}
		int id_emp_user = (int)session.getAttribute("id_emp_user");
		String id_dept = (String)session.getAttribute("DeptId");
		String UserType = (String)session.getAttribute("UserType"); 
	
		
		String temp="";
		try
		{
			
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
			
			case "Save":

				String colName="",value="";
        		int j=0,id_inv_m1=0;
        	
        		try 
        		{
        				rs = Common.GetColumns("A_Invoice_Master",  request);
        				
        				
        				while (rs.next())
        				{
        				
        					if(rs.getString(1) !="id_inv_m")
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
        				
        			String	 sql="insert into A_Invoice_Master("+colName+",add_by,dept_id,id_dept,type_inv) values("+value+","+id_emp_user+",'"+id_dept+"','"+id_dept+"','throughpo')";
        					 System.out.println(sql);
        				 stmt=con.createStatement();
        				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
        				
        				 
        				 rs1 = stmt.getGeneratedKeys();
        		            while (rs1.next()) {
        		            	id_inv_m1= rs1.getInt(1);
        		            } 
        				
        			
        		
        			
        		}
        		catch(Exception e)
        		{
        			System.out.println("Error in A_Invoice master"+e);
        		}
        		try 
        		{
        		String id_grp="",id_sgrp="",id_prod="",model="",mfr="",ds_pro="";
        		stmt=con.createStatement();
    				int k=0;
    			j=0;
    			String id_po_asst = request.getParameter("id_po_asst");
    			String[] parts = id_po_asst.split("Patel");
    			Set mySet = new HashSet(Arrays.asList(parts)); 
    			// id_po = request.getParameter("numberpo");
    			
    			id = "";
    			String po_asstets_id[] = request.getParameterValues("po_asstets_id");
    			for (int i = 0; i < po_asstets_id.length; i++) {
    				if(mySet.contains(po_asstets_id[i])){
    								
    				    id_prod = request.getParameter("id_prod"+i+"");
                		String po_qty = request.getParameter("po_qty"+i+"");
                		String rem_qty = request.getParameter("rem_qty"+i+"");
                		String qty = request.getParameter("qty"+i+"");
                		String un_prc = request.getParameter("un_prc"+i+"");
                		String others = request.getParameter("others"+i+"");
                		String id_tax1 = request.getParameter("id_tax1"+i+"");
                		String id_tax2 = request.getParameter("id_tax2"+i+"");
                		String tax_val1 = request.getParameter("tax_val1"+i+"");
                		String tax_val2 = request.getParameter("tax_val2"+i+"");
                		String buyback = request.getParameter("buyback"+i+"");
                		String gr_tot = request.getParameter("gr_tot"+i+"");
                		
                		double rem=0.0;
                		String sql=" select rem_qty from p_purchase_order_asset where id_po_asst='"+po_asstets_id[i]+"'";
                		 ps=con.prepareStatement(sql);
                		 rs=ps.executeQuery();
                		if(rs.next())
                		{
                			double qtyr=0.0;
                			qtyr=Double.parseDouble(qty);
                			 rem=rs.getDouble("rem_qty")- qtyr;
                			String sqlrem="update p_purchase_order_asset set rem_qty='"+rem+"' where id_po_asst="+po_asstets_id[i]+"";
                			System.out.println(sqlrem);
                			ps=con.prepareStatement(sqlrem);
                			k=ps.executeUpdate();
                			j=1;
                		}
                	 sql = "select * from M_Model where id_model='"+id_prod+"'";
         				ps=con.prepareStatement(sql);
         				rs=ps.executeQuery();
         				if(rs.next())
         				{
         					id_grp=rs.getString("id_assetdiv");
         					id_sgrp=rs.getString("id_s_assetdiv");
         				}
               		 sql="insert into A_Invoice(po_rem_qty,id_grp,id_sgrp,id_inv_m,id_model,id_po_asst,id_po,po_qty,rem_qty,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot)" +
               		 " values("+rem_qty+","+id_grp+","+id_sgrp+","+id_inv_m1+","+id_prod+","+po_asstets_id[i]+","+id_po+","+po_qty+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+")";
               		System.out.println(sql);
                		con=Common.GetConnection(request);
        				PreparedStatement ps=con.prepareStatement(sql);
        				j=ps.executeUpdate();
        				
        				String  sql12=" select sum(rem_qty) from p_purchase_order_asset where id_po='"+id_po+"'";
	              		System.out.println(sql12);
	        			 ps=con.prepareStatement(sql12);
	              		 rs=ps.executeQuery();
	              		if(rs.next())
	              		{
	              			if(rs.getString(1).equals(0.00)){
	              				String sqlrem="update p_purchase_order set recv_invoice='2' where id_po='"+id_po+"'";
	                  			System.out.println(sqlrem);
	                  			PreparedStatement ps1=con.prepareStatement(sqlrem);
	                  			k=ps1.executeUpdate();
	              			}
	              			else{
	              				String sqlrem="update p_purchase_order set recv_invoice='1' where id_po='"+id_po+"'";
	                  			System.out.println(sqlrem);
	                  			PreparedStatement ps1=con.prepareStatement(sqlrem);
	                  			k=ps1.executeUpdate();
	              			
	              			}
	              		}
            		j=1;
    			}
    			}
    					}
    			catch(Exception e)
    				{
    					System.out.println("Some error in A_INVOICE."+e);
    				}
    			
    		jobj.put("data",j);
            	
            	
				
				
				
				
				
				
				
				
				
				
				break;
				
			 case "Update":
		            int k=0;
		          	String count1[] = request.getParameterValues("count");
			            	
			            	String col_Val1="";
			            	try
			        		{
			            		
			        			stmt = con.createStatement();
			        				rs = Common.GetColumns("A_Invoice_Master",  request);
			        						while (rs.next())
			        						{
			        						
			        							if(!rs.getString(1).equals("id_inv_m"))
			        							{
			        								    if (map.containsKey(rs.getString(1)))
			        								    {
			        								    	if(col_Val1.equals(""))
			        								    	{
			        								    		col_Val1 += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
			        								    	}
			        								    	else
			        								    	{
			        								    		col_Val1 += ","+ rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
			        								    	}
			        								    }
			        							  }
			        						}
			        					
			        		
			        		
			        						
			       String sql1="update A_Invoice_Master set "+col_Val1+" where id_inv_m="+id+"";	
			        System.out.println(sql1);
			        stmt.executeUpdate(sql1);				
			       for(int i=0;i<count1.length;i++)
			       	{	String id_po_asset = request.getParameter("id_po_asset"+count1[i]+"");
					
					if(!id_po_asset.isEmpty())
					{
						 
					String id_prod = request.getParameter("id_prod"+count1[i]+"");
		    		String po_qty = request.getParameter("po_qty"+count1[i]+"");
		    		String rem_qty = request.getParameter("rem_qty"+count1[i]+"");
		    		String qty = request.getParameter("qty"+count1[i]+"");
		    		String un_prc = request.getParameter("un_prc"+count1[i]+"");
		    		String others = request.getParameter("others"+count1[i]+"");
		    		String id_tax1 = request.getParameter("id_tax1"+count1[i]+"");
		    		String id_tax2 = request.getParameter("id_tax2"+count1[i]+"");
		    		String tax_val1 = request.getParameter("tax_val1"+count1[i]+"");
		    		String tax_val2 = request.getParameter("tax_val2"+count1[i]+"");
		    		String buyback = request.getParameter("buyback"+count1[i]+"");
		    		String gr_tot = request.getParameter("gr_tot"+count1[i]+"");
		        		
		        		
		        		 String sql2=" select id_inv,qty from A_INVOICE where id_po_asst='"+id_po_asset+"' and id_inv_m="+id+"";
		        		 System.out.println(sql2);
		        		 ps=con.prepareStatement(sql2);
		        		 rs=ps.executeQuery();
		        		if(rs.next())
		        		{
		        			String id_inv = rs.getString(1);
		        			double qtyr=0.0;
		        			qtyr=Double.parseDouble(qty);
		        			 System.out.println(qtyr);
		        			double qtycal = rs.getDouble(2);
		        			 System.out.println(qtycal);
		        			double diff=0.0,rem=0.0;
		        			
		    				String  sqlrem=" select rem_qty from p_purchase_order_asset where id_po_asst='"+id_po_asset+"'";
		    				System.out.println(sqlrem);
		            		 ps=con.prepareStatement(sqlrem);
		            		 rs=ps.executeQuery();
		            		if(rs.next())
		            		{
		            			
		            			if(qtyr>qtycal){
		            				System.out.println("This Is Executed...");
		            				 diff=qtyr-qtycal;//-
		       					 rem=rs.getDouble("rem_qty")- diff;
		       				}
		       				else{
		       					diff=qtycal-qtyr;//+
		       					 rem=rs.getDouble("rem_qty")+ diff;
		       				}
		            			
		            		   sqlrem="update p_purchase_order_asset set rem_qty='"+rem+"' where id_po_asst="+id_po_asset+"";
		            			System.out.println(sqlrem);
		            			ps=con.prepareStatement(sqlrem);
		            			k=ps.executeUpdate();
		            			k=1;
		            		}
		        			
		        			
		        			sql1="update A_INVOICE set id_po='"+id_po+"',id_po_asst='"+id_po_asset+"',id_model='"+id_prod+"',po_rem_qty='"+rem+"',po_qty='"+po_qty+"',rem_qty='"+qty+"',qty='"+qty+"',un_prc='"+un_prc+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"',tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot+"' where id_inv="+id_inv+"";
		        			System.out.println(sql1);
		        		con=Common.GetConnection(request);
						PreparedStatement ps=con.prepareStatement(sql1);
						ps.executeUpdate();
						k=1;
						 String  sql=" select sum(rem_qty) from p_purchase_order_asset where id_po='"+id_po+"'";
		        		 System.out.println(sql);
		        		 ps=con.prepareStatement(sql);
		          		 rs=ps.executeQuery();
		          		if(rs.next())
		          		{
		          			if(rs.getString(1).equals(0.00)){
		          				String sqlre="update p_purchase_order set recv_invoice='2' where id_po='"+id_po+"'";
		              			System.out.println(sqlre);
		              			PreparedStatement	ps1=con.prepareStatement(sqlrem);
		              			k=ps1.executeUpdate();
		          			}
		          			else{
		          				String sqlre="update p_purchase_order set recv_invoice='1' where id_po='"+id_po+"'";
		              			System.out.println(sqlre);
		              			PreparedStatement	ps1=con.prepareStatement(sqlrem);
		              			k=ps1.executeUpdate();
		          		
		          			}
		          			k=1;
		          		}
						
		        		}
		        		else
		        		{double rem=0.0;
		        			String  sql44=" select rem_qty from p_purchase_order_asset where id_po_asst='"+id_po_asset+"'";
		             		 ps=con.prepareStatement(sql44);
		             		 rs=ps.executeQuery();
		             		if(rs.next())
		             		{
		             			double qtyr=0.0;
		             			qtyr=Double.parseDouble(qty);
		             			 rem=rs.getDouble("rem_qty")- qtyr;
		             			String sqlrem="update p_purchase_order_asset set rem_qty='"+rem+"' where id_po_asst="+id_po_asset+"";
		             			System.out.println(sqlrem);
		             			ps=con.prepareStatement(sqlrem);
		             			k=ps.executeUpdate();
		             			k=1;
		             		}
		       
		        			String sql="insert into A_INVOICE(id_inv_m,id_model,id_po_asst,id_po,po_qty,po_rem_qty,rem_qty,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values("+id+","+id_prod+","+id_po_asset+","+id_po+","+po_qty+","+rem_qty+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+")";
		        			con=Common.GetConnection(request);
		    				PreparedStatement ps=con.prepareStatement(sql);
		    				ps.executeUpdate();
		    				k=1;
		    				 String  sql22=" select sum(rem_qty) from p_purchase_order_asset where id_po='"+id_po+"'";
		            		 System.out.println(sql22);
		            		 ps=con.prepareStatement(sql22);
		              		 rs=ps.executeQuery();
		              		if(rs.next())
		              		{
		              			if(rs.getString(1).equals(0.00)){
		              				String sqlrem="update p_purchase_order set recv_invoice='2' where id_po='"+id_po+"'";
		                  			System.out.println(sqlrem);
		                  			PreparedStatement	ps1=con.prepareStatement(sqlrem);
		                  			k=ps1.executeUpdate();
		              			}
		              			else{
		              				String sqlrem="update p_purchase_order set recv_invoice='1' where id_po='"+id_po+"'";
		                  			System.out.println(sqlrem);
		                  			PreparedStatement	ps1=con.prepareStatement(sqlrem);
		                  			k=ps1.executeUpdate();
		              			
		              			}
		              			k=1;
		              		}
		    				
		        		}
		        		
		        		 
		        		 
			        } 	
			       	}
			        						
			        	jobj.put("data", k);					
			        				
			        			}
			        			catch(Exception e)
			        				{
			        					System.out.println(e);
			        				}
			        		
		            	   break;
				
				

			case "sendMail":
				System.out.println("sendMail");

				String no_inv = request.getParameter("no_inv");
				String dt_inv = request.getParameter("dt_inv");
				String remarks = request.getParameter("remarks");
				String no_po = request.getParameter("no_po");
				String upload_inv = request.getParameter("upload_inv");
				stmt = con.createStatement();
				stmt.executeUpdate("insert into create_invoice (no_inv,dt_inv,remarks,id_po,upload_inv) " + "  values('"
						+ no_inv + "','" + dt_inv + "','" + remarks + "','" + no_po + "','" + upload_inv + "')");

				String sql = "select no_po from P_Purchase_Order where id_po='" + no_po + "'";

				System.out.println(sql);
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					no_po = rs.getString(1);
					;
				}

				sql = "select mailid,nm_com from M_Company";
				String replyMailId = "", nm_com = "";
				System.out.println(sql);
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					replyMailId = rs.getString(1);
					nm_com = rs.getString(2);
				} else {
					replyMailId = "priteshtheprofessional@gmail.com";
				}

				List<String> recipients = new ArrayList<String>();

				String link = "";
				String footerMsg = "";
				String mailSubject = "";

				String mailBody = "<b> Hello " + nm_com + "</b>,<br><br><br>"
						+ "Find the attached invoice against PO. <br>" + "Purchase Number : <b>" + no_po + "</b><br>"
						+ "Invoice Number  : <b>" + no_po + "</b><br>" + "Invoice Date  : <b>" + dt_inv + "</b><br>"
						+ "Note  : <b>" + no_po + "</b><br>" +

						"<br><br><br>" + link + "" + "<p>" + footerMsg + "</p>";

				Common.MailConfiguration(mailBody, replyMailId, recipients, mailSubject);
				 j = 1;

				jobj.put("data", j);
				break;
			case "Edit":
				String sql4 = "select (replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtinv,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,A_Invoice_Master.*  from A_Invoice_Master where id_inv_m="
						+ id + "";
				jobj = Common.GetDataForDisplayInJsonFormat(sql4,  request);
				break;
				
			case "getPODetails":
				 sql4 = "select * from P_Purchase_Order where id_po="
						+ id + "";
				jobj = Common.GetDataForDisplayInJsonFormat(sql4,  request);
				break;
			case "DisplayLineItem":
				String sqlline = "select inv.* ,nm_model,cd_model from A_Invoice inv ,m_model mm  where mm.id_model=inv.id_model and id_inv_m=" + id + "";
				System.out.println(sqlline);
				jobj = Common.GetDataForDisplayInJsonFormat(sqlline,  request);
				break;
			case "DropDownResult":
				jobj = DropDownResult(id_emp_user, id);
				break;

			case "Displayinvoice":
				jobj = Displayinvoice(id_ven, id_po);
				break;
			case "Display":
				 sql ="select distinct inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,no_po,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtPo,nm_ven from A_Invoice_Master inv_m , M_Vendor v where inv_m.id_ven=v.id_ven and "+
						 " status_store = 0  and rec_status_grn = 0 and v.id_ven="+id_emp_user+" ";
			
				 if(!id_inv_m.equals(""))
				{
					sql ="select id_inv_m,id_inv,qty,un_prc,nm_acc_asset,tt_un_prc,tt,mfr,id_grp,ds_pro,id_model from A_Invoice inv where id_inv_m = "+id_inv_m+" and status_store=0";
				}
				if(!id.equals("0"))
				{
					sql="select  inv.*,id_flr ,(replace(convert(NVARCHAR, dt_boe, 103), ' ', '-')) as dtBoe,(replace(convert(NVARCHAR, po_dt, 103), ' ', '-')) as podt,inv_m.id_inv_m,inv_m.id_ven,nm_ven,no_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,no_po,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,id_curr,id_loc,id_subl,id_building,bd,no_boe,dt_boe,de_dt_bd,dt_exp,inv_m.id_div,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot from A_Invoice_Master inv_m , M_Vendor v,A_Invoice inv where "+
							 "inv_m.id_ven=v.id_ven and id_po=0 and inv_m.id_inv_m=inv.id_inv_m and inv_m.id_inv_m = "+id+" and inv_m.status_store = 0";
				
				}
				
				System.out.println(sql);
				jobj = ResuldData.GetDataForDisplay(sql,  request);
				//jobj = DisplayInvoicepo(id, id_inv_m);
				break;

			case "DropDownResultPO":
				sql = "select id_po,no_po from p_purchase_order where (recv_invoice='1' or recv_invoice='0') and id_ven="+id_emp_user+"";
				jobj = ResuldData.GetDataForDisplay(sql,  request);
				break;
			case "DisplayAssetPO":
				sql = "select nm_model,cd_model,id_ven,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,no_po,poa.* from P_Purchase_Order po,P_Purchase_Order_Asset poa ,m_model mm where poa.id_prod=mm.id_model and  po.id_po=poa.id_po and  po.id_po='"
						+ id + "' and poa.rem_qty>0 ";
				System.out.println(sql);
				jobj = ResuldData.GetDataForDisplay(sql,  request);
				break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in action."+e);
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
	
	public JSONObject DisplayInvoicepo(String id , String id_inv_m)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		
		
		String sql="";
		 sql ="select distinct inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,no_po,(replace(convert(NVARCHAR, inv_m.dt_po, 103), ' ', '-')) as dtPo,nm_ven from A_Invoice_Master inv_m , M_Vendor v where inv_m.id_ven=v.id_ven and "+
				 " status_store = 0  and rec_status_grn = 0 ";
	
		 if(!id_inv_m.equals(""))
		{
			sql ="select id_inv_m,id_inv,qty,un_prc,nm_acc_asset,tt_un_prc,tt,mfr,id_grp,ds_pro,id_model from A_Invoice inv where id_inv_m = "+id_inv_m+" and status_store=0";
		}
		if(!id.equals("0"))
		{
			sql="select  inv.*,id_flr ,(replace(convert(NVARCHAR, dt_boe, 103), ' ', '-')) as dtBoe,(replace(convert(NVARCHAR, po_dt, 103), ' ', '-')) as podt,inv_m.id_inv_m,inv_m.id_ven,nm_ven,no_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,no_po,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,id_curr,id_loc,id_subl,id_building,bd,no_boe,dt_boe,de_dt_bd,dt_exp,inv_m.id_div,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot from A_Invoice_Master inv_m , M_Vendor v,A_Invoice inv where "+
					 "inv_m.id_ven=v.id_ven and id_po=0 and inv_m.id_inv_m=inv.id_inv_m and inv_m.id_inv_m = "+id+" and inv_m.status_store = 0";
		
		}
		
		System.out.println(sql);
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
			System.out.println("sql error in A_Invoice.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject Displayinvoice(String id_ven,String id_po)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
			String sql="select no_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dt_inv,invoice_file  from A_Invoice_Master where  id_po='"+id_po+"'";
					
			System.out.println(sql);
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
			    		String name=metaData.getColumnName(i);
			    		jobj2.put(name,rs.getString(name));
			    	}
			    		jarr.put(jobj2);
			    		
		        }
			   
			    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in poven." +e);
		}
		 
		return jobj;
		
		
	}
	public JSONObject DropDownResult(int UserId,String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
			String sql="select no_po,id_po from P_Purchase_Order where id_ven='"+UserId+"' and recv_invoice !='2'";
			if(!id.equals("0"))
			{
				sql ="select no_po,id_po from P_Purchase_Order where id_ven='"+id+"'";
			}		
			System.out.println(sql);
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
			    		String name=metaData.getColumnName(i);
			    		jobj2.put(name,rs.getString(name));
			    	}
			    		jarr.put(jobj2);
			    		
		        }
			   
			    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in poven." +e);
		}
		 
		return jobj;
		
		
	}

	
	
	
	}
