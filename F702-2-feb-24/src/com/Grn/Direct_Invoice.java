package com.Grn;

import java.io.IOException;
import java.io.PrintWriter;
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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


import dto.Common.DtoCommon;


public class Direct_Invoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String UserName = (String)session.getAttribute("UserName");
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
		String storelocid="",upload_inv="",action = "",checkexitsingval="",id_dc1="0",word="",id="0",req_ast_id="",id_inv_m="",confirm="1",dt_to="",dt_frm="",value="",ColName="",no_req_val="",dt_req="",dt_inv="",dt_gd_rcv="",id_prod_loc="",id_store_loc="";
		if(request.getParameter("upload_inv") !=null)
		{
			upload_inv = request.getParameter("upload_inv");
		}
		
		if(request.getParameter("storelocid") !=null)
		{
			storelocid = request.getParameter("storelocid");
		}
		if(request.getParameter("id_dc1") !=null)
		{
			id_dc1 = request.getParameter("id_dc1");
		}
		
		
		if(request.getParameter("inward_name") !=null)
		{
			checkexitsingval = request.getParameter("inward_name");
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
		if(request.getParameter("dt_inv") !=null)
		{
			dt_inv = request.getParameter("dt_inv");
		}
		if(request.getParameter("dt_gd_rcv") !=null)
		{
			dt_gd_rcv = request.getParameter("dt_gd_rcv");
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
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			
			String id_dept = (String)session.getAttribute("DeptId");
			
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
	            case "Update":
	            
          	String count1[] = request.getParameterValues("count");
	            	
	            	String id_dc2="";
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
	        					
	        		
	        		
	        						
	        sql1="update A_Invoice_Master set "+col_Val1+" where id_inv_m="+id+"";	
	        System.out.println(sql1);
	        stmt.executeUpdate(sql1);				
	       for(int i=0;i<count1.length;i++)
	       	{	String id_po_asset = request.getParameter("id_po_asset"+count1[i]+"");
			
			if(!id_po_asset.isEmpty())
			{
			String id_po = request.getParameter("id_po"+count1[i]+"");
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
        			
        			
        			sql1="update A_INVOICE set id_po='"+id_po+"',id_po_asst='"+id_po_asset+"',id_model='"+id_prod+"',po_rem_qty'"+rem_qty+"',po_qty='"+po_qty+"',rem_qty='"+qty+"',qty='"+qty+"',un_prc='"+un_prc+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"',tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot+"' where id_inv="+id_inv+"";
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
	                
	            case "Display":
	            	String tempSql="";
	       		 	tempSql =" and (no_inv LIKE '%"+word+"%' or dt_inv LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' )";

	            	String sql3="select inv.id_inv_m,inv.no_inv,(replace(convert(NVARCHAR, inv.dt_inv, 103), ' ', '-')) as dt_inv,v.nm_ven from  A_Invoice_Master inv, M_Vendor v where inv.id_ven=v.id_ven  and rec_status_grn='0'and type_inv='ThroughPo' "+tempSql+" ";
	            	jobj = Common.GetDataForDisplayInJsonFormat(sql3,  request);	
	                break;
	            case "auto_jwno":
	            	jobj = Autoworkno();
	            	break;
	            case "DisplayMy":
	       		 	//tempSql =" and (no_inv LIKE '%"+word+"%' or dt_inv LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' )";
	            	System.out.println("jhjh");
	            	 sql3="select (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,no_po,inv.id_inv_m,inv.no_inv,(replace(convert(NVARCHAR, inv.dt_inv, 103), ' ', '-')) as dt_inv,v.nm_ven from  A_Invoice_Master inv, M_Vendor v where inv.id_ven=v.id_ven  and rec_status_grn!='0'and type_inv='ThroughPo' and inv.id_ven="+id_emp_user+" ";
	            	 System.out.println(sql3);
	            	 jobj = Common.GetDataForDisplayInJsonFormat(sql3,  request);	
	                break;
	                
	            case "DisplayForAcceptReject":
	       		 	tempSql =" and (no_inv LIKE '%"+word+"%' or dt_inv LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' )";

	            	String sql5="select dc.*,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtinv,(replace(convert(NVARCHAR, dt_gd_rcv, 103), ' ', '-')) as dtgdrcv,v.nm_ven from I_DC dc,M_Vendor v where acceptreject=0 and dc.id_ven=v.id_ven "+tempSql+" ";
	            	jobj = Common.GetDataForDisplayInJsonFormat(sql5,  request);	
	                break;
	            case "DropdownPOItem":
	            	jobj = DropdownPOItem(id);
	            
	                break; 
	            case "Edit":
	            	String sql4="select A_Invoice_Master.*,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtinv  from A_Invoice_Master where id_inv_m="+id+"";
	            	jobj=Common.GetDataForDisplayInJsonFormat(sql4,  request);
	                break;
	                
	            case "EditMy":
	            	 sql4="select (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,no_po,im.*,(replace(convert(NVARCHAR, im.dt_inv, 103), ' ', '-')) as dtinv,v.nm_ven from A_Invoice_Master im,M_Vendor v where im.id_inv_m="+id+" and im.id_ven=v.id_ven";
	            	 System.out.println(sql4);
	            	 jobj=Common.GetDataForDisplayInJsonFormat(sql4,  request);
	                break;
	            case "DisplayLineItem":
	            	String sqlline="select A_Invoice.*  from A_Invoice where id_inv_m="+id+"";
	            	System.out.println(sqlline);
	            	jobj=Common.GetDataForDisplayInJsonFormat(sqlline,  request);
	                break;
	                
	            case "DisplayLineItemMy":
	            	 sqlline="select i.*,m.nm_model,m.cd_model,t1.nm_tax as nm_tax1,t2.nm_tax as nm_tax2 "
	            	 		+ "from A_Invoice i,M_Model m,M_Tax t1,M_Tax t2 "
	            	 		+ "where i.id_model=m.id_model and i.id_tax1=t1.id_tax and i.id_tax2=t2.id_tax and id_inv_m="+id+"";
	            	System.out.println(sqlline);
	            	jobj=Common.GetDataForDisplayInJsonFormat(sqlline,  request);
	                break;
	             
	            case "EditLoc":
	            	String sql9="select I_DC.*,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtinv,(replace(convert(NVARCHAR, dt_gd_rcv, 103), ' ', '-')) as dtgdrcv from I_DC where id_dc="+id+"";
	            	jobj=Common.GetDataForDisplayInJsonFormat(sql9,  request);
	                break;
	                
	            case "CheckDate":
	            	jobj = CheckDate(dt_inv,dt_gd_rcv);	
	                break;
	        
	            case "DropDownResultpovendor":
	            	jobj = DropDownResultpovendor(id);	
	                break;
	            case "DropDownResultForClass":
	            	jobj = DropDownResultForClass(id);	
	                break;
	             case "GetPODetails":
	            	String sql="";
	            	sql="select pa.qty,pa.un_prc,rem_qty,tot_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,pc.id_model as id_prod,buyback,gr_tot from P_Purchase_Order_Asset pa,m_model pc where pa.id_prod=pc.id_model and id_po_asst="+id+"";
	            
	            	jobj=Common.GetDataForDisplayInJsonFormat(sql,  request);
	            	break;
	            	
	            case "GetRemQty":
		        	sql="select pa.rem_qty from P_Purchase_Order_Asset pa where id_po_asst="+id+"";
	            	jobj=Common.GetDataForDisplayInJsonFormat(sql,  request);
	            	break;
	               
	            case "Save":
	            	        		
	            	
	            	String colName="";
	        		int j=0;
	        		int id_inv_m1=0;
	        		
	        		try
	        		{
	        				rs = Common.GetColumns("A_Invoice_Master",  request);
	        				
	        						
	        						while (rs.next())
	        						{
	        						
	        							if(!rs.getString(1).equals("id_inv_m"))
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
	        		
	        		 sql="insert into A_Invoice_Master("+colName+",add_by,dept_id,id_dept,invoice_file,type_inv) values("+value+","+id_emp_user+","+id_dept+","+id_dept+",'"+upload_inv+"','ThroughPo')";
					 System.out.println(sql);
				     stmt=con.createStatement();
				     stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				     rs = stmt.getGeneratedKeys();
	                 if (rs.next()) 
	                 {
	                	 id_inv_m1 = rs.getInt(1);
	                 }
	        		try
	        		{
	        			colName="";value="";
	        			
	        			String count[] = request.getParameterValues("count");
	        			stmt=con.createStatement();
	        			j=0;
	        			for(int i=0;i<count.length;i++)
	        			{	
	        				String id_po_asset = request.getParameter("id_po_asset"+count[i]+"");
	        				
	        				if(!id_po_asset.isEmpty())
	        				{
	        				String id_po = request.getParameter("id_po"+count[i]+"");
	        				String id_prod = request.getParameter("id_prod"+count[i]+"");
	                		String po_qty = request.getParameter("po_qty"+count[i]+"");
	                		String rem_qty = request.getParameter("rem_qty"+count[i]+"");
	                		String qty = request.getParameter("qty"+count[i]+"");
	                		String un_prc = request.getParameter("un_prc"+count[i]+"");
	                		String others = request.getParameter("others"+count[i]+"");
	                		String id_tax1 = request.getParameter("id_tax1"+count[i]+"");
	                		String id_tax2 = request.getParameter("id_tax2"+count[i]+"");
	                		String tax_val1 = request.getParameter("tax_val1"+count[i]+"");
	                		String tax_val2 = request.getParameter("tax_val2"+count[i]+"");
	                		String buyback = request.getParameter("buyback"+count[i]+"");
	                		String gr_tot = request.getParameter("gr_tot"+count[i]+"");
	                		
	                		double rem=0.0;
	                		 sql=" select rem_qty from p_purchase_order_asset where id_po_asst='"+id_po_asset+"'";
	                		 ps=con.prepareStatement(sql);
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
	                			j=1;
	                		}
	                		String id_grp="",id_sgrp="";
	                		 sql = "select * from M_Model where id_model='"+id_prod+"'";
	         				ps=con.prepareStatement(sql);
	         				rs=ps.executeQuery();
	         				if(rs.next())
	         				{
	         					id_grp=rs.getString("id_assetdiv");
	         					id_sgrp=rs.getString("id_s_assetdiv");
	         				}
	               		 sql="insert into A_Invoice(po_rem_qty,id_grp,id_sgrp,id_inv_m,id_model,id_po_asst,id_po,po_qty,rem_qty,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot)" +
	               		 " values("+rem_qty+","+id_grp+","+id_sgrp+","+id_inv_m1+","+id_prod+","+id_po_asset+","+id_po+","+po_qty+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+")";
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
	        				
	        				}
		
	        			
	        		}
	        			 
		        			
	        			
	        			jobj.put("data", j);					
        				
        			}
        			catch(Exception e)
        				{
        					System.out.println(e);
        				}
	        		
	        		
	        		
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
			 
			
			ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
				rs=ps.executeQuery();
			if(rs.next())
			{
				year=rs.getString("start");
				end=rs.getString("enddt");
			}
		 
			String strt = year.substring (2,4);
			String endt = end.substring (2,4);
		ps=con.prepareStatement("select count(id_inv_m) from A_Invoice_Master");
		rs=ps.executeQuery();
		System.out.println("select count(id_inv_m) from A_Invoice_Master");
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				
			}
			
		String 	poID3 = String.format("%03d", PoId);
			poID2= "GRN"+poID3+"/"+strt+"-"+endt;
			  System.out.println(poID2);
			  jobj.put("wo_no",poID2);
			
		}
		
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return jobj;
	}
	
	
	public String CreatePOIdDynamic(String typ_of_po)
	{
		String finyear="",poID2="";
		int PoId=1;
		
		try
		{
		ps=con.prepareStatement("select count(id_inv_m) from P_Purchase_Order where typ_of_po='"+typ_of_po+"' ");
		rs=ps.executeQuery();
		
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				String year="0";
				int yearTemp=0;
				ps=con.prepareStatement("select extract(year from to_date(STD_FINANCE,'yyyy/mm/dd')) as stdate,extract(year from to_date(END_FINANCE,'yyyy/mm/dd')) as endyear from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
				rs=ps.executeQuery();
				if(rs.next())
				{
					yearTemp=rs.getInt("stdate");
				}
				year = yearTemp + "000000";
				
				//year= String.format("%08d", yearTemp);
				
				PoId = PoId + Integer.parseInt(year);
				
				poID2="SAL/"+typ_of_po+"/"+ PoId;
				
				/*ps=con.prepareStatement("select substring(convert(varchar(10),std_finance,101),7,10),substring(convert(varchar(10),end_finance,101),9,10) from M_Finance_Year where active_year=1 ");
				ps=con.prepareStatement("select EXTRACT(YEAR FROM to_date(std_finance,'yyyy-mm-dd')),EXTRACT(YEAR FROM to_date(end_finance,'yyyy-mm-dd')) from M_Finance_Year where ACTIVE_YEAR=1");
				rs1=ps.executeQuery();
				if(rs1.next())
				{
					finyear =rs1.getString(1)+"-"+rs1.getString(2);
				}*/
				
				/*if(PoId == 1)
				{
					poID2="SAL/"+typ_of_po+"/18000001";
				}
				else
				{
					poID2="SAL/"+typ_of_po+"/"+ PoId;
				}*/
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return poID2;
	}
	
	
	public JSONObject DisplayRequestForPreview(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
			sql ="select s.nm_section,r.no_req,r.dt_req, pc.nm_prod,pc.cd_prod,pc.uom,pc.description,ra.qty,ra.un_prc,ra.tot_prc from A_INVOICE_MASTER r, A_INVOICE ra,M_Prod_Cart pc,M_Section s  where r.id_inv_m=ra.id_inv_m and pc.id_prod=ra.id_prod and s.id_section=r.id_section and r.id_inv_m="+id+"";

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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
	
		    jobj.put("todaydate",formatter.format(date));
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_INVOICE."+e.toString());
		}
		 
		return jobj;
		
		
	}
	

	
	public JSONObject CheckDate(String dt_req,String dt_gd_rcv)
	{
		JSONObject json=new JSONObject();
		
		String query = "select std_finance,end_finance from M_Finance_Year where std_finance <= '"+dt_req+"' and end_finance >= '"+dt_req+"' and active_year=1";
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
				query = "select (replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance from M_Finance_Year where active_year=1";
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					json.put("data",0);
					json.put("std_finance",rs.getString(1));
					json.put("end_finance",rs.getString(2));
				}
				
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	public JSONObject LoginUserDetails(int id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String sql ="select * from M_Emp_User where id_emp_user = "+id+"";
		
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
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in M_Emp_User."+e.toString());
		}
		 
		return jobj;
		
		
	}


	public JSONObject CheckExitsVal(String CheckExitsVal )
	{
		JSONObject json=new JSONObject();
		System.out.println(CheckExitsVal);
		String query = "select id_inv_m from A_INVOICE_MASTER where transptr_name = '"+CheckExitsVal+"'";
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
	
	public JSONObject DropdownPOItem(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql ="";
		
			 sql="select id_po_asst,nm_model from m_model ml,p_purchase_order_asset poa where ml.id_model=poa.id_prod and rem_qty!='0' and  id_po="+id+"";
	   
		try
		{
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in item against dropdown.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DropDownResultpovendor(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql ="";
		if(id.equals("0"))
		{
			 sql="select distinct po.id_ven,nm_ven  from P_Purchase_Order po,m_vendor ven where po.id_ven=ven.id_ven and (recv_invoice='0' or recv_invoice='1') ";
		
		}
		
		try
		{
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in M_Floor.");
		}
		 
		return jobj;
		
		
	}
	
	
public String getReqNo(String id_section) throws SQLException
{
		
		ResultSet rs1=null;
		ResultSet rs2=null;
		ResultSet rs3=null;
		String strReqNo="-" ;
		String strfinyear="";
		String strTemp	=	"",year="",section="";
		int intPoId	=	0;
		boolean boolStatus			=	false;
		Statement stmt = con.createStatement();
		Statement stmt1 = con.createStatement();
		Statement stmt2 = con.createStatement();
		Statement stmt3 = con.createStatement();
		/*rs=stmt.executeQuery("select year(std_finance) as stdate,year(end_finance) as endyear from M_Finance_Year where active_year='1'");*/
		rs=stmt.executeQuery("select extract(year from to_date(STD_FINANCE,'yyyy/mm/dd')) as stdate,extract(year from to_date(END_FINANCE,'yyyy/mm/dd')) as endyear from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
		if(rs.next())
		{
			strfinyear=rs.getString("stdate")+"-"+rs.getString("endyear");
			year=rs.getString("stdate");
			boolStatus=false;
		}
		else
		{
			boolStatus=true;
			return strReqNo ;
		}
		rs=stmt.executeQuery("select UPPER(SUBSTR(nm_section,1,3)) as section  from M_Section where id_section="+id_section+" ");
		if(rs.next())
		{
			section=rs.getString("section");
			
		}
		
		
		if(!boolStatus)
		{
		rs=stmt.executeQuery("select * from P_request");
		if(rs.next())
		{
			/*rs1=stmt1.executeQuery("select * from P_request where substring(no_req,len(no_req)-8,len(no_req))='"+strfinyear+"'");*/
			rs1=stmt1.executeQuery("select no_req from P_request where SUBSTR(no_req,LENGTH(no_req)-7,4)='"+year+"'");
			if(rs1.next())
			{
				rs2=stmt2.executeQuery("select max(id_inv_m) from P_request where SUBSTR(no_req,LENGTH(no_req)-7,4)='"+year+"'");
				if(rs2.next())
				{
					intPoId=rs2.getInt(1);
					rs3=stmt3.executeQuery("select no_req from P_request r where id_inv_m="+intPoId+" ");
					if(rs3.next())
					{
						strTemp=rs3.getString(1);
						strTemp=strTemp.substring(strTemp.indexOf("q-")+2,strTemp.indexOf("/") );
						int intTempNo = Integer.parseInt(strTemp);
						intTempNo = intTempNo + 1;
						strReqNo="Req-"+intTempNo+"/"+year+"/"+section;
					}
				}
			}
			else
			{
				strReqNo="Req-1/"+year+"/"+section;
			}
		}
		else
		{
			strReqNo="Req-1/"+year+"/"+section;
		}
	}
	return strReqNo ;
	}	

public JSONObject DropDownResultForClass(String id)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	String sql ="select po.id_po,no_po "+
		" from P_Purchase_Order po"+
		" where id_ven ="+id+" ";
	
	
	try
	{
	
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
	    		String name=metaData.getColumnName(i).toLowerCase();
	    		jobj2.put(name,rs.getString(name));
	    	}
	    	
	    	
	    		jarr.put(jobj2);
	    		
        }
	    jobj.put("data", jarr);
	}
	catch(Exception e)
	{
		System.out.println("sql error in po subsubdrop.");
	}
	 
	return jobj;
	
	
}


}


