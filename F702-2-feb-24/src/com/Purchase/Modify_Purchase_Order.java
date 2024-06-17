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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;
import com.Util.dtoUtils;


public class Modify_Purchase_Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		HttpSession session = request.getSession(); 
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		Enumeration elemet = request.getParameterNames();

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
		    
		String action = "",word="",dt_po="",do_direct="",no_quot="",typ_po="",id_ven="",id_po="0",dt_to="",dt_frm="",status_approv="",no_ind="",dt_approv="",acceptQuotNo="";
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		if(request.getParameter("do_direct") !=null)
		{
			do_direct = request.getParameter("do_direct");
		}
		if(request.getParameter("dt_po") !=null)
		{
			dt_po = request.getParameter("dt_po");
		}
		if(request.getParameter("no_quot") !=null)
		{
			no_quot = request.getParameter("no_quot");
		}
		if(request.getParameter("typ_po") !=null)
		{
			typ_po = request.getParameter("typ_po");
		}
		if(request.getParameter("id_ven") !=null)
		{
			id_ven = request.getParameter("id_ven");
		}
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("dt_approv") !=null)
		{
			dt_approv = request.getParameter("dt_approv");
		}
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
		}
		if(request.getParameter("acceptQuotNo") !=null)
		{
			acceptQuotNo = request.getParameter("acceptQuotNo");
		}
		
		
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("status_approv") !=null)
		{
			status_approv = request.getParameter("status_approv");
			
		}
		if(request.getParameter("no_ind") !=null)
		{
			no_ind = request.getParameter("no_ind");
		}
		String countID[] = request.getParameterValues("rejectQuotNo");
		try
		{
			Date currDate = new Date();
			if(!dt_po.equals(""))
			{
				dt_po = dateFormatNeeded.format(userDateFormat.parse(dt_po));
			}
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
			temp += " and dt_po <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp += " and dt_po >= '"+dt_frm+"'";
		}
		
		/*if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_po >= '"+dt_frm+"' and dt_po <= '"+dt_to+"'";
		}*/
		if(!id_ven.equals(""))
		{
			temp +=" and po.id_ven = "+id_ven+"";
		}
		if(!typ_po.equals(""))
		{
			temp +=" and typ_po = '"+typ_po+"'";
		}
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType"); 
			con=Common.GetConnection(request);
			String col_Val="",sql="";
			int j=0;
			switch (action)
	        {
	                
	            case "Display":
	            	jobj = DisplayPurchaseOrderForModification(temp,word,UserType);	
	                break;
	            case "Displayvo":
	            	jobj = DisplayPurchaseOrderForModificationvo(temp,word,UserType);	
	                break;
	                
	            case "Edit":
	            	jobj = ModifyPurchaseOrder(id_po);	
	                break;
	            case "EditMyPO":
	            	 sql="select frt_text,po.discount,oter_text,others,id_tax1,id_tax2,tax_val1,tax_val2,oter_text,add_by as req_by,poa.*, (replace(convert(NVARCHAR, poa.dt_scheduled_dlvry, 103), ' ', '/')) "+
	 						"   as dt_scheduled_dlvry2 , (replace(convert(NVARCHAR, dt_po_delivery, 103), ' ', '/')) as dt_po_delivery ,add_by,po.frt_text as frttext,po.upload_po,nm_model, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, "+
	 				     	"  	 po.ID_PO,po.ID_DEPT,NO_PO,NO_IND,NO_QUOT,BILL_TO,emp.ID_FLR,ID_VEN,PO_T_C,ST_PO,PO_MODE,TYP_PMT,IS_ADVANCE,po.ID_CURR,AMD,ANNEX,TOT ,TAX_PER,MODE_OF_DELV, "+
	 						"    WARR_GUAR,po.ID_SLOC,INSURANCE,FRIEGHT,VAT,ST_VEN_ACCEPTANCE,ST_PMT,REM_VAL_PO,emp.nm_emp  from P_Purchase_Order po,"+
	 						"    P_Purchase_Order_Asset poa,m_model pc,M_Emp_User emp    where   po.id_po=poa.id_po  and poa.id_prod=pc.id_model and po.add_by=emp.id_emp_user "+
	                         "   and po.id_po="+id_po+"";	
	            	 System.out.println(sql);
		            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);
	                break;
	            case "CheckDate":
	            	jobj = CheckPODate(dt_po,no_quot,no_ind,do_direct);	
	                break;
	                
	            case "Update":
	            	
	            	String count[] = request.getParameterValues("count");
	            	String id_po_asst[] = request.getParameterValues("id_po_asst");
	            	String rem_val_tot = request.getParameter("gr_tot");
	            	try
	        		{
	            		
	        			stmt = con.createStatement();
	        				rs = Common.GetColumns("P_PURCHASE_ORDER",  request);
	        						while (rs.next())
	        						{
	        						
	        							if(!rs.getString(1).equals("id_po"))
	        							{
	        								    if (map.containsKey(rs.getString(1)))
	        								    {
	        								    	if(col_Val.equals(""))
	        								    	{
	        								    		col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
	        								    	}
	        								    	else
	        								    	{
	        								    		col_Val += ","+ rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
	        								    	}
	        								    }
	        							  }
	        						}
	        					
	        		Double tt =0.0;
	        		
	        						
	        sql="update P_Purchase_Order set "+col_Val+" where id_po="+id_po+"";	
	        System.out.println(sql);
	        stmt.executeUpdate(sql);				
	       for(int i=0;i<count.length;i++)
	       	{					
	        	j=0;
	        	String id_grp="",id_sgrp="",id_prod="",ds_pro="";
	        	String dt_recv = "01/01/1990";
	        	dt_recv = dateFormatNeeded.format(userDateFormat.parse(dt_recv));
	           String nm_prod = request.getParameter("id_prod"+count[i]+"");
    		   String dir="";
	      	   System.out.println(nm_prod);
	      	    if(!nm_prod.equals(null) || !nm_prod.isEmpty()  ){
	      	    	
	      	    	id_grp= request.getParameter("id_grp"+count[i]+"");
					id_sgrp= request.getParameter("id_sgrp"+count[i]+"");
					String	 sql2 = "select * from M_Model where NM_MODEL='"+nm_prod+"'";
    				ps=con.prepareStatement(sql2);
    				rs=ps.executeQuery();
    				if(rs.next())
    				{
    					id_prod=rs.getString("id_model");
    					ds_pro=rs.getString("nm_model");
    				}
    				else{
    					
    					stmt.executeUpdate("insert into M_Model (nm_model,id_s_assetdiv,id_assetdiv,cd_model) values('"+nm_prod+"','"+id_sgrp+"','"+id_grp+"','NA')");
    					 sql = "select * from M_Model where NM_MODEL='"+nm_prod+"'";
         				ps=con.prepareStatement(sql);
         				rs=ps.executeQuery();
         				if(rs.next())
         				{
         					id_prod=rs.getString("id_model");
         					ds_pro=rs.getString("nm_model");
         				}
    				
    				}
				
	      	    	
    				dir="id_prod='"+id_prod+"',id_grp='"+id_grp+"',id_sgrp='"+id_sgrp+"',";
	      	    	
	      	  
	      	 	String qty = request.getParameter("qty"+count[i]+"");
        		String un_prc = request.getParameter("un_prc"+count[i]+"");
        		String others = request.getParameter("others"+count[i]+"");
        		String id_tax1 = request.getParameter("id_tax1"+count[i]+"");
        		String id_tax2 = request.getParameter("id_tax2"+count[i]+"");
        		String tax_val1 = request.getParameter("tax_val1"+count[i]+"");
        		String tax_val2 = request.getParameter("tax_val2"+count[i]+"");
        		String buyback = request.getParameter("buyback"+count[i]+"");
        		String gr_tot1 = request.getParameter("gr_tot"+count[i]+"");
        		String item_remarks = request.getParameter("item_remarks"+count[i]+"");
        		String dt_scheduled_dlvry = request.getParameter("dt_scheduled_dlvry"+count[i]+"");
       
        		if(item_remarks == null){
	        		item_remarks="";
	        	}
        	
	        	String actionType = request.getParameter("actionType"+count[i]+"");
	        	
	        	if(actionType.equals("Update")){
	        		
	        		if(dt_scheduled_dlvry==null){
	             		dt_scheduled_dlvry="";
	            	}
    				
    				
    				
	        	
	        		if(!dt_scheduled_dlvry.equals("")){
	        		 dt_scheduled_dlvry = request.getParameter("dt_scheduled_dlvry"+i+"");
        			 Date d = userDateFormat.parse(dt_scheduled_dlvry); 
        			 System.out.println(dateFormatNeeded.format(d));
        				System.out.println("update P_Purchase_Order_Asset set   "+dir+" dt_scheduled_dlvry='"+dateFormatNeeded.format(d)+"' ,rem_qty='"+qty+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"', tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot1+"',qty='"+qty+"',un_prc='"+un_prc+"',dt_recv='"+dt_recv+"',item_remarks='"+item_remarks+"' where id_po_asst="+id_po_asst[i]+"");

    	                
        			 stmt.executeUpdate("update P_Purchase_Order_Asset set   "+dir+" dt_scheduled_dlvry='"+dateFormatNeeded.format(d)+"' ,rem_qty='"+qty+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"', tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot1+"',qty='"+qty+"',un_prc='"+un_prc+"',dt_recv='"+dt_recv+"',item_remarks='"+item_remarks+"' where id_po_asst="+id_po_asst[i]+"");

	        		}else{
	        			dt_scheduled_dlvry="";
	        			System.out.println("update P_Purchase_Order_Asset set   "+dir+"   dt_scheduled_dlvry='"+dt_scheduled_dlvry+"' ,rem_qty='"+qty+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"', tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot1+"',qty='"+qty+"',un_prc='"+un_prc+"',dt_recv='"+dt_recv+"',item_remarks='"+item_remarks+"' where id_po_asst="+id_po_asst[i]+"");
		        		
		        	stmt.executeUpdate("update P_Purchase_Order_Asset set   "+dir+"   dt_scheduled_dlvry='"+dt_scheduled_dlvry+"' ,rem_qty='"+qty+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"', tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot1+"',qty='"+qty+"',un_prc='"+un_prc+"',dt_recv='"+dt_recv+"',item_remarks='"+item_remarks+"' where id_po_asst="+id_po_asst[i]+"");
	        		}
	        	}
	        else{
	     
	        	if(!dt_scheduled_dlvry.equals("")){
	        		 dt_scheduled_dlvry = request.getParameter("dt_scheduled_dlvry"+i+"");
       			 Date d = userDateFormat.parse(dt_scheduled_dlvry); 
       			 System.out.println(dateFormatNeeded.format(d));
                          System.out.println("insert into P_Purchase_Order_Asset(item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"','"+dateFormatNeeded.format(d)+"',"+id_po+","+id_prod+","+id_grp+","+id_sgrp+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot1+")");
                      	
        		stmt.executeUpdate("insert into P_Purchase_Order_Asset(item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"','"+dateFormatNeeded.format(d)+"',"+id_po+","+id_prod+","+id_grp+","+id_sgrp+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot1+")");
        	
	        		}else{
	        			dt_scheduled_dlvry="";
	        			System.out.println("insert into P_Purchase_Order_Asset(item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"','"+dt_scheduled_dlvry+"',"+id_po+","+id_prod+","+id_grp+","+id_sgrp+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot1+")");
		            	
	            		stmt.executeUpdate("insert into P_Purchase_Order_Asset(item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"','"+dt_scheduled_dlvry+"',"+id_po+","+id_prod+","+id_grp+","+id_sgrp+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot1+")");
	            		}
	      
	        	
	        			
    				System.out.println(sql);
	        		stmt.executeUpdate(sql);
	        		
	        	}
           		
	      	    }
           		j=1;
	        } 						
	        						
	        	jobj.put("data", j);					
	        				
	        			}
	        			catch(Exception e)
	        				{
	        					System.out.println("Some error in P_Purchase_Order."+e);
	        				}
	        		
	                break;
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Approval_Quotation.");
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
	
	public JSONObject CheckPODate(String dt_po,String no_quot,String no_ind,String do_direct)
	{
		JSONObject json=new JSONObject();
		String query = "";
		if(do_direct.equals("0"))
		 query = "select dt_approv from P_Quotation where dt_approv >'"+dt_po+"' and no_quot='"+no_quot+"' and no_ind='"+no_ind+"'";
		else
		query = "select dt_quot from P_Purchase_Order where dt_quot >'"+dt_po+"' and no_quot='"+no_quot+"' and no_ind='"+no_ind+"'";
			
		 try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				
				json.put("data",0);
				json.put("dt_approv",rs.getDate(1));
			}
			else
			{
				json.put("data",1);
				
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	public JSONObject ModifyPurchaseOrder(String id_po)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		Common dbname=new Common();
	
		
		try
		{
			String sql="";
			
				 
				 sql="select po.repo_mngr,po.discount,po.oter_text,others,id_tax1,id_tax2,tax_val1,tax_val2,add_by as req_by, (replace(convert(NVARCHAR, poa.dt_scheduled_dlvry, 103), ' ', '/'))  as dt_scheduled_dlvry2 , (replace(convert(NVARCHAR, dt_po_delivery, 103), ' ', '/')) as dt_po_delivery ,add_by,po.frt_text as frttext, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po from P_Purchase_Order po, P_Purchase_Order_Asset poa where po.id_po=poa.id_po and po.id_po="+id_po+"";		 
			
			 
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
			    		String name=metaData.getColumnName(i).toLowerCase();
			    		jobj2.put(name,rs.getString(name));
			    	}
			    		jarr.put(jobj2);
			    		
		        }
				/*
				 * sql="select tc.* from M_term_condition tc, P_PURCHASE_ORDER po where tc.ID_T_C=po.ID_T_C and po.ID_PO="
				 * +id_po+""; System.out.println(sql); ps=con.prepareStatement(sql);
				 * rs=ps.executeQuery(); if(rs.next()) { jobj.put("uploadedFile",
				 * rs.getString("file_name")); jobj.put("tc_name", rs.getString("t_and_c")); }
				 */
			    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Modify PO.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	
	
	public JSONObject DisplayPurchaseOrderForModification(String temp,String word, String UserType)
	{
		Common dbname=new Common();
	
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		String tempSql ="";
	
		
			 if(!word.isEmpty())
				tempSql =" and (no_po LIKE '%"+word+"%'  or dt_po LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%')";
			 
             sql="select distinct(po.id_po),po.tot,po.no_ind,po.id_dept,po.id_cc,no_po,"+ 
                          "   (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) "+ 
                    "     as dt_po1,nm_ven,no_quot from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v "+ 
        "        where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0  and Do_direct=0  and st_po='0' "+tempSql+" order by dt_po1 DESC ";
	if(UserType.equals("SUPER")){
	      sql="select distinct(po.id_po),po.tot,po.no_ind,po.id_dept,po.id_cc,no_po,"+ 
                  "   (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) "+ 
            "     as dt_po1,nm_ven,no_quot from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v "+ 
"        where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0  and Do_direct=0  and st_po='0' "+tempSql+" order by dt_po1 DESC ";

		}
			 
		 
		 
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
			System.out.println("sql error in Approval_Quotation.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DisplayPurchaseOrderForModificationvo(String temp,String word, String UserType)
	{
		Common dbname=new Common();
		
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		String tempSql ="";

		
			 if(!word.isEmpty())
				 tempSql =" and (no_po LIKE '%"+word+"%' or tot LIKE '%"+word+"%' or dt_po LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' or nm_emp LIKE '%"+word+"% '  or po.no_ind LIKE '%"+word+"%')";
				
				sql = "select distinct(po.id_po),po.id_dept,po.id_cc,po.id_section,no_po,no_sap,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,nm_emp,nm_ven,no_quot,brb_ref_num,nm_section,po.tot,po.no_ind,po.typ_of_po,tender_num from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Emp_User emp,M_Vendor v,M_Section s "+
						" where po.id_po=poa.id_po and po.id_ven=v.id_ven and po.add_by=emp.id_emp_user and po.cancel=0 and rem_qty!='0' and po.indent_dept=s.id_section and typ_of_po='O1'  "+tempSql+" order by no_po DESC";

		if(UserType.equals("SUPER")){
			sql = "select distinct(po.id_po),po.tot,po.no_ind,po.typ_of_po,po.id_dept,po.id_cc,po.id_section,no_po,no_sap, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po1,nm_emp,nm_ven,no_quot,brb_ref_num,nm_section,tender_num from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Emp_User emp,M_Vendor v,M_Section s "+
					" where po.id_po=poa.id_po and po.id_ven=v.id_ven and po.add_by=emp.id_emp_user and po.cancel=0 and rem_qty!='0' and po.indent_dept=s.id_section and typ_of_po='O1'  "+tempSql+" order by dt_po1 DESC";

			 
			 
		 }
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
			System.out.println("sql error in Approval_Quotation.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	}
