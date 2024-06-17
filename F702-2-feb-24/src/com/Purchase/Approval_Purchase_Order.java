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
import com.Util.dtoUtils;

import dto.Common.DtoCommon;


public class Approval_Purchase_Order extends HttpServlet {
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
		    
		    
		String action = "",word="",id="0",dt_to="",dt_frm="",status_approv="",id_po="",dt_approv="",acceptQuotNo="",typ_po="",remark_short_close="",dt_short_close="";
		if(request.getParameter("remark_short_close") !=null)
		{
			remark_short_close = request.getParameter("remark_short_close");
		}
		if(request.getParameter("dt_short_close") !=null)
		{
			dt_short_close = request.getParameter("dt_short_close");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("dt_approv") !=null)
		{
			dt_approv = request.getParameter("dt_approv");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
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
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
		}
		if(request.getParameter("typ_po") !=null)
		{
			typ_po = request.getParameter("typ_po");
		}
		String countID[] = request.getParameterValues("rejectQuotNo");
		try
		{
		Date currDate = new Date();
		if(!dt_approv.equals(""))
		{
			dt_approv = dateFormatNeeded.format(userDateFormat.parse(dt_approv));
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
			temp = " and dt_po <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_po >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_po >= '"+dt_frm+"' and dt_po <= '"+dt_to+"'";
		}
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType"); 
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	        case "Amend":
	        	con=Common.GetConnection(request);
				String col_Val="",sql="";
				int j=0;
			
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
        String colName="";
    	
    	String value="";
		
        stmt = con.createStatement();
		rs = Common.GetColumns("P_Purchase_Order_amend",  request);
				while (rs.next())
				{
				
					if(rs.getString(1) !="id_po_amend")
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
				int id_ven=0;
				ps=con.prepareStatement("select id_ven from P_Purchase_Order where id_po="+id_po+"");
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_ven=rs.getInt(1);
				}
				int id_po_amend=0;
				String query="insert into P_Purchase_Order_amend("+colName+",id_ven) values("+value+","+id_ven+")";
				stmt=con.createStatement();
				stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
				rs1 = stmt.getGeneratedKeys();
				if (rs1.next()) 
				{
					id_po_amend = rs1.getInt(1);
				}
        
       for(int i=0;i<count.length;i++)
       	{					
        	j=0;
        	String id_grp="",id_sgrp="";
        	String dt_recv = "01/01/1990";
        	dt_recv = dateFormatNeeded.format(userDateFormat.parse(dt_recv));
        	String id_prod = request.getParameter("id_prod"+count[i]+"");
      	    System.out.println(id_prod);
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
    		String rem_qty = request.getParameter("rem_qty"+count[i]+"");
    		
     	if(item_remarks == null){
        		item_remarks="";
        	}
     	if(dt_scheduled_dlvry==null){
     		dt_scheduled_dlvry="";
    	}
    	
     		
     	double remqt=0.0 ,mfrup=0.0;
		sql = "select * from P_Purchase_Order_Asset where id_po_asst="+id_po_asst[i]+"";
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		if(rs.next())
		{
		 	remqt=rs.getDouble("qty");
			mfrup=rs.getDouble("rem_qty");
			
		
		
	    }
		
		double remqtyy=0.0;
		double old_qty =Double.parseDouble(request.getParameter("qty"+count[i]+""));
		if(remqt >	old_qty)
		{
			remqtyy=mfrup - (remqt - old_qty);
			
		}else{
			remqtyy= mfrup + (old_qty - remqt);
		}
     	
        		if(!dt_scheduled_dlvry.equals("") ) {
        		dt_scheduled_dlvry = request.getParameter("dt_scheduled_dlvry"+i+"");
    			Date d = userDateFormat.parse(dt_scheduled_dlvry); 
    			System.out.println(dateFormatNeeded.format(d));
    		    System.out.println("update P_Purchase_Order_Asset set   dt_scheduled_dlvry='"+dateFormatNeeded.format(d)+"' ,rem_qty='"+remqtyy+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"', tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot1+"',qty='"+qty+"',un_prc='"+un_prc+"',dt_recv='"+dt_recv+"',item_remarks='"+item_remarks+"' where id_po_asst="+id_po_asst[i]+"");
                stmt.executeUpdate("update P_Purchase_Order_Asset set   dt_scheduled_dlvry='"+dateFormatNeeded.format(d)+"' ,rem_qty='"+remqtyy+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"', tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot1+"',qty='"+qty+"',un_prc='"+un_prc+"',dt_recv='"+dt_recv+"',item_remarks='"+item_remarks+"' where id_po_asst="+id_po_asst[i]+"");
    	      	String sql2 = "select * from P_Purchase_Order_Asset where id_po_asst='"+id_po_asst[i]+"'";
    			ps=con.prepareStatement(sql2);
    			rs=ps.executeQuery();
    			if(rs.next())
    			{
    				id_prod=rs.getString("id_prod");
    				id_grp=rs.getString("id_grp");
    				id_sgrp=rs.getString("id_sgrp");
    				}
          	    	stmt.executeUpdate("insert into P_Purchase_Order_Asset_amend(id_po_asst,item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+id_po_asst[i]+"','"+item_remarks+"','"+dateFormatNeeded.format(d)+"',"+id_po+","+id_prod+","+id_grp+","+id_sgrp+","+remqtyy+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot1+")");
    	    	}
        		else
        		{
        			dt_scheduled_dlvry="";
        			System.out.println("update P_Purchase_Order_Asset set    dt_scheduled_dlvry='"+dt_scheduled_dlvry+"' ,rem_qty='"+qty+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"', tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot1+"',qty='"+qty+"',un_prc='"+un_prc+"',dt_recv='"+dt_recv+"',item_remarks='"+item_remarks+"' where id_po_asst="+id_po_asst[i]+"");
        		    stmt.executeUpdate("update P_Purchase_Order_Asset set    dt_scheduled_dlvry='"+dt_scheduled_dlvry+"' ,rem_qty='"+qty+"',others='"+others+"',id_tax1='"+id_tax1+"',id_tax2='"+id_tax2+"', tax_val1='"+tax_val1+"',tax_val2='"+tax_val2+"',buyback='"+buyback+"',gr_tot='"+gr_tot1+"',qty='"+qty+"',un_prc='"+un_prc+"',dt_recv='"+dt_recv+"',item_remarks='"+item_remarks+"' where id_po_asst="+id_po_asst[i]+"");
                
        		 String sql2 = "select * from P_Purchase_Order_Asset where id_po_asst='"+id_po_asst[i]+"'";
    			ps=con.prepareStatement(sql2);
    			rs=ps.executeQuery();
    			if(rs.next())
    			{
    				id_prod=rs.getString("id_prod");
    				id_grp=rs.getString("id_grp");
    				id_sgrp=rs.getString("id_sgrp");
    				}
    			System.out.println("insert into P_Purchase_Order_Asset_amend(id_po_amend,id_po_asst,item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+id_po_amend+"','"+id_po_asst[i]+"','"+item_remarks+"','"+dt_scheduled_dlvry+"',"+id_po+","+id_prod+","+id_grp+","+id_sgrp+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot1+")");
    		    stmt.executeUpdate("insert into P_Purchase_Order_Asset_amend(id_po_amend,id_po_asst,item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+id_po_amend+"','"+id_po_asst[i]+"','"+item_remarks+"','"+dt_scheduled_dlvry+"',"+id_po+","+id_prod+","+id_grp+","+id_sgrp+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot1+")");
       
        		
        		}
       
       		
       		j=1;
        } 						
        						
        	jobj.put("data", j);					
        				
        			}
        			catch(Exception e)
        				{
        					System.out.println("Some error in amend."+e);
        				}
        		
                break;
	            case "Display":
	            	jobj = DisplayPurchaseOrderForApproval(temp , id_emp_user , UserType,word);	
	                break;
	            case "DisplayJW":
	            	jobj = DisplayPurchaseOrderForApprovalJW(temp , id_emp_user , UserType,word);	
	                break;
	                
	            case "DisplayAmend":
	            	jobj = DisplayPurchaseOrderForAmmend(temp , id_emp_user , UserType,word);	
	                break;
	                
	            case "DisplayMyPO":
	            		sql ="select distinct(po.id_po) as do_direct,po.id_po,po.tot,po.no_ind,po.no_quot,po.id_dept,no_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dt_po1,nm_ven,no_quot\r\n"
	            				+ " from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v \r\n"
	            				+ " where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0 and st_po='1' and po.id_ven="+id_emp_user+" ";
	            	 System.out.println(sql);
		            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);	
	                break;
	            case "Displayshort":
	            	jobj = DisplayPurchaseOrderForsclose(temp , id_emp_user , UserType,word);	
	                break;
	                
	            case "Update":
	            	jobj = UpdatePOForAcceptReject(id_po,status_approv,id_emp_user,dt_approv,remark_short_close,dt_short_close,  request);	
	                break;
	                
	            case "Edit":
	            	jobj = PurchaseOrderDataForApproval(id_po);	
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
	
	
	public JSONObject PurchaseOrderDataForApproval(String id_po)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		Common dbname=new Common();
	
		String sql="";
		try
		{
			
			
				 sql="select poa.*,tt.nm_tax as tttax,pc.uom as uomval,po.buy_back as buyback ,po.frt_rate as frtrate,po.add_chrg as addchrg,po.frt_text as frttext,po.upload_po,nm_prod,pc.description as description1 ,cd_prod,nm_curr,t.nm_tax,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,nm_prod,nm_curr,po.iso_num,po.pay_terms,po.ID_PO,po.ID_DEPT,NO_PO,NO_IND,NO_QUOT,BILL_TO,ID_FLR,ID_VEN,PO_T_C,ST_PO,PO_MODE,TYP_PMT,IS_ADVANCE,po.ID_CURR,AMD,ANNEX,TOT,GR_TOT,TAX_PER,MODE_OF_DELV,WARR_GUAR,ID_SLOC,INSURANCE,FRIEGHT,VAT,ST_VEN_ACCEPTANCE,ST_PMT,REM_VAL_PO,SD_AMT,BRB_REF_NUM,ID_T_C,ID_SECTION,ID_ORG_LOC from P_Purchase_Order po,P_Purchase_Order_Asset poa left join M_TAX tt on tt.ID_TAX=poa.TAXID_ADD,M_Currency c,M_Prod_Cart pc,M_Tax t  "+
							" where t.id_tax=poa.id_tax and po.id_po=poa.id_po and po.id_curr=c.id_curr and poa.id_prod=pc.id_prod and po.id_po="+id_po+"";
				
		
			
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
			     sql="select tc.* from M_term_condition tc, P_PURCHASE_ORDER po where tc.ID_T_C=po.ID_T_C and po.ID_PO="+id_po+"";
			     System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
			    {
					jobj.put("uploadedFile", rs.getString("file_name"));
					jobj.put("tc_name", rs.getString("t_and_c"));
			    }
			    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Approval_Quotation."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject UpdatePOForAcceptReject(String id_po,String status_approv,int id_emp_user,String 
			dt_approv,String remark_short_close,String dt_short_close,HttpServletRequest request)
	{
		 Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int j=0;
		String query="",id_quot="";
		JSONObject json=new JSONObject();
		if(status_approv.equals("short_cls")){
			 query="update P_Purchase_Order set  shortclosed ='1', dt_short_close ='"+dt_short_close+"',remark_short_close ='"+remark_short_close+"' where id_po="+id_po+"";
				
				
		}else{
			 query="update P_Purchase_Order set st_po=1, dt_approv ='"+dt_approv+"',st_approv ='"+status_approv+"',approv_by="+id_emp_user+" where id_po="+id_po+"";
				
					
		}
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				 
				
				
				/*
				// Mail Trigger......
				String replyMailId="",name="",no_po="",ccMailId="";
	        	String mailSql ="select id_emp,nm_emp,no_po from M_Emp_User emp,P_Purchase_Order po where emp.id_emp_user=po.add_by and id_po='"+id_po+"'";
	        	
	        	DtoCommon dtcm = new DtoCommon();
				rs = dtcm.GetResultSet(mailSql);
				if(rs.next())
				{
					replyMailId = rs.getString(1);
					name = rs.getString(2);
					ccMailId=replyMailId;
					no_po= rs.getString(3);
				}
				mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_Purchase_Order po where emp.id_emp_user="+id_emp_user+" and id_po='"+id_po+"'";
				List<String> recipients = dtcm.ReturnListData(mailSql);
				
				String link = dtcm.ReturnParticularMessage("link");
				String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
				String mailSubject = dtcm.ReturnParticularMessage("purchasePOApprov1");
				
				String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
							  "Purchase order <b>("+no_po+")</b> has been "+status_approv+" SR manager head."+
							  "<br><br><br>"+link+""+
							  "<p>"+footerMsg+"</p>";
			
				Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
				
				List<String> recipients1 = new ArrayList<String>();
				mailSql ="select id_emp,nm_emp from M_Emp_User emp,M_User_Login ul where emp.id_emp_user=ul.id_emp_user and type_user='PHEAD'";
				rs = dtcm.GetResultSet(mailSql);
				if(rs.next())
				{
					replyMailId = rs.getString(1);
					name = rs.getString(2);
				}
				mailSubject = dtcm.ReturnParticularMessage("purchasePOApprov");
				String mailBody1 = "<b> Hello "+name+"</b>,<br><br><br>"+
						  "Purchase order <b>("+no_po+")</b> has been created. Please approve it."+
						  "<br><br><br>"+link+""+
						  "<p>"+footerMsg+"</p>";
				recipients1.add(ccMailId);
				//Common.MailConfiguration(mailBody1, replyMailId, recipients1 , mailSubject);
				j=1;
				
				*/
				
				// Mail Trigger......
    			
    			String replyMailId="",name="",ccMailId="",no_po="";
            	String mailSql ="select id_emp,nm_emp,no_po from M_Emp_User emp,P_Purchase_Order r where emp.id_emp_user=r.add_by and id_po='"+id_po+"' ";
            	System.out.println(mailSql);
            	
            	DtoCommon dtcm = new DtoCommon();
    			rs = dtcm.GetResultSet(mailSql,  request);
    			if(rs.next())
    			{
    				replyMailId = rs.getString(1);
    				name = rs.getString(2);
    				no_po = rs.getString(3);
    				ccMailId = replyMailId;
    			}
    			
    			mailSql ="select distinct(v.ct_mailid),nm_ven from M_Vendor v,P_Purchase_Order r where  no_po='"+no_po+"' and r.id_ven=v.id_ven ";
    			System.out.println(mailSql);
    			List<String> recipients = dtcm.ReturnListData(mailSql,  request);
    			
    			String link = dtcm.ReturnParticularMessage("link");
    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
    			String mailSubject = dtcm.ReturnParticularMessage("mailSubjectPO");
    			
    			String mailBody = "<b> Hello Vendor</b>,<br><br><br>"+
    						  "You got the PO  <b>("+no_po+")</b> Please Check it.<br>"+
    						  "<br><br><br>"+link+""+
							  "<p>"+footerMsg+"</p>";
    		
    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
    			j=1;
				json.put("data",j);
			}
			else
			{
				json.put("data",0);
			}
			if(status_approv.equals("Accepted"))
			{
				String id_bud="",budget_type="";
				double annual_bud_done=0.0,rem_budget=0.0,gr_tot;
				/* query="select sum(tot_prc)*rate+sum(tot_prc)*rate*tax_per/100 as tot_prc,poa.id_cc,id_bud,annual_bud_done,c.id_curr,er.rate,po.insurance*rate,po.frieght*rate "+
						" from P_Purchase_Order_Asset poa,M_Budget b,M_Currency c ,P_Purchase_Order po,M_Exchange_Rate er "+
						" where poa.id_cc=b.id_cc and poa.id_po="+id_po+" and c.id_curr=po.id_curr and po.id_po=poa.id_po and c.id_curr=er.id_curr "+
						" group by poa.id_cc,id_bud,annual_bud_done,c.id_curr,er.rate,tax_per,po.insurance,po.frieght ";
			
				query="select gr_tot*rate as tot_prc,poa.id_cc,id_bud,annual_bud_done,c.id_curr,er.rate "+
						" from P_Purchase_Order_Asset poa,M_Budget b,M_Currency c ,P_Purchase_Order po,M_Exchange_Rate er "+
						" where poa.id_cc=b.id_cc and poa.id_po="+id_po+" and c.id_curr=po.id_curr and po.id_po=poa.id_po and c.id_curr=er.id_curr "+
						" group by poa.id_cc,id_bud,annual_bud_done,c.id_curr,er.rate,tax_per,gr_tot ";*/
			
				/*query="select b.nm_bu,b.id_bu,ra.id_req_asst,ra.id_prod,poa.gr_tot,bu.* "+
				" from P_Purchase_Order po,P_Indent_Asset ia,P_request r,M_BU b,P_Request_Asset ra,P_Purchase_Order_Asset poa,M_Budget bu "+
				" where po.no_ind=ia.no_ind and po.id_po="+id_po+" and ia.id_req=r.id_req and r.id_bu=b.id_bu and r.id_req=ra.id_req and po.id_po=poa.id_po and b.id_bu=bu.id_bu and bu.id_model=ra.id_prod";
*/
				query="select distinct r.budget_type"+
						" from P_Purchase_Order po,P_Indent_Asset ia,P_request r"+
						" where po.no_ind=ia.no_ind and po.id_po="+id_po+" and ia.id_req=r.id_req ";
				System.out.println(query);
				stmt=con.createStatement();
				rs=stmt.executeQuery(query);
				if(rs.next())
				{
					budget_type=rs.getString(1);
					System.out.println(budget_type);
					if(budget_type.equals("budget"))
					{
						query="select distinct b.nm_bu,b.id_bu,ra.id_req_asst,ra.id_prod,poa.gr_tot,bu.* "+
								" from P_Purchase_Order po,P_Indent_Asset ia,P_request r,M_BU b,P_Request_Asset ra,P_Purchase_Order_Asset poa,M_Budget bu "+
								" where po.no_ind=ia.no_ind and po.id_po="+id_po+" and ia.id_req=r.id_req and r.id_bu=b.id_bu and r.id_req=ra.id_req and po.id_po=poa.id_po and poa.id_prod=ra.id_prod and b.id_bu=bu.id_bu and bu.id_model=poa.id_prod and bu.adhoc='NO' ";


						System.out.println(query);
						stmt=con.createStatement();
						rs=stmt.executeQuery(query);
						while(rs.next()){
							/* id_bud=rs.getString(3);
							annual_bud_done=rs.getDouble(1) + rs.getDouble(4);
							query ="update M_Budget set annual_bud_done="+annual_bud_done+" where id_bud="+id_bud+"";
							ps=con.prepareStatement(query);
			    			j=ps.executeUpdate();
			    			if(j > 0)
				    			{
			    					json.put("data",1);
				    			}
				    			else
				    			{
				    				json.put("data",0);
				    			} */
							/*id_bud=rs.getString(6);
							annual_bud_done=rs.getDouble(5);*/
							
							id_bud=rs.getString(6);
							annual_bud_done=rs.getDouble(21);
							gr_tot=rs.getDouble(5);
							annual_bud_done=annual_bud_done+gr_tot;
							System.out.println(annual_bud_done);
							query ="update M_Budget set annual_bud_done="+annual_bud_done+" where id_bud="+id_bud+"";
							System.out.println(query);
							ps=con.prepareStatement(query);
			    			j=ps.executeUpdate();
			    			if(j > 0)
				    			{
			    					json.put("data",1);
				    			}
				    			else
				    			{
				    				json.put("data",0);
				    			}
						}
					}
					if(budget_type.equals("adhoc"))
					{
						query="select distinct bu.*,po.tot "+
						" from P_Purchase_Order po,P_Indent_Asset ia,P_request r,M_BU b,M_Budget bu "+
						" where po.no_ind=ia.no_ind and po.id_po="+id_po+" and ia.id_req=r.id_req and r.id_bu=b.id_bu and b.id_bu=bu.id_bu and bu.adhoc='YES' " ;
						System.out.println(query);
						stmt=con.createStatement();
						rs=stmt.executeQuery(query);
						while(rs.next()){
							
							id_bud=rs.getString(1);
							annual_bud_done=rs.getDouble("annual_bud_done");
							gr_tot=rs.getDouble("tot");
							annual_bud_done=annual_bud_done+gr_tot;
							System.out.println(annual_bud_done);
							query ="update M_Budget set annual_bud_done="+annual_bud_done+" where id_bud="+id_bud+"";
							System.out.println(query);
							ps=con.prepareStatement(query);
			    			j=ps.executeUpdate();
			    			if(j > 0)
				    			{
			    					json.put("data",1);
				    			}
				    			else
				    			{
				    				json.put("data",0);
				    			}
						}
					}
				}
				
				
				
			}
			
			
			
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	public JSONObject DisplayPurchaseOrderForApproval(String temp , int id_emp_user , String UserType,String word)
	{ 
		Common dbname=new Common();
	
	
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		String tempSql ="";
	
		
			 
			 if(!word.isEmpty())
			 tempSql =" and (no_po LIKE '%"+word+"%' or dt_po LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%')";
				
				if(UserType.equals("SUPER"))
				{
						
				      sql="select distinct(po.id_po) as do_direct,po.id_po,po.tot,po.no_ind,po.no_quot,po.id_dept,no_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dt_po1,nm_ven,no_quot"+
				    		"  from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v "+
				    		"  where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0   and st_po='0' "+tempSql+"    order by dt_po1 DESC";
								
				}
				else
				{
				      sql="select distinct(po.id_po) as do_direct,po.id_po,po.tot,po.no_ind,po.no_quot,po.id_dept,no_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dt_po1,nm_ven,no_quot"+
					    		"  from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v "+
					    		"  where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0   and st_po='0' "+tempSql+"    order by dt_po1 DESC";
								
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
			System.out.println("sql error in Approval_Quotation."+e.toString());
		}
		return jobj;
	}
	
	public JSONObject DisplayPurchaseOrderForApprovalJW(String temp , int id_emp_user , String UserType,String word)
	{ 
		Common dbname=new Common();
	
	
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		String tempSql ="";
		
		
			 
			 if(!word.isEmpty())
			 tempSql =" and (no_po LIKE '%"+word+"%' or dt_po LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' )";
				
				if(UserType.equals("SUPER"))
				{
						
				      sql="select distinct(po.id_po),TYP_OF_PO as do_direct,po.tot,po.no_ind,po.no_quot,po.typ_of_po,po.id_dept,po.id_cc,po.id_section,no_po,no_sap, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dt_po1,nm_ven,no_quot,brb_ref_num,tender_num"+
				    		"  from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v "+
				    		"  where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0   and st_po='0' "+tempSql+" and TYP_OF_PO='JobWork' order by dt_po1 DESC";
								
				}
				else
				{
				      sql="select distinct(po.id_po),TYP_OF_PO as do_direct,po.tot,po.no_ind,po.typ_of_po,po.id_dept,po.id_cc,po.id_section,no_po,no_sap, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dt_po1,nm_ven,no_quot,brb_ref_num,tender_num from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v  where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0 and st_po='0'   order by dt_po1 DESC";
						
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
			System.out.println("sql error in Approval_Quotation."+e.toString());
		}
		return jobj;
	}
	
	public JSONObject DisplayPurchaseOrderForAmmend(String temp , int id_emp_user , String UserType,String word)
	{ 
		Common dbname=new Common();
	
	
		
		JSONObject jobj = new JSONObject();		
		JSONArray jarr = new JSONArray();
		String sql="";
		
		String tempSql ="";
	
	
			 
			 if(!word.isEmpty())
			 tempSql =" and (no_po LIKE '%"+word+"%' or  dt_po LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%')";
				if(UserType.equals("SUPER"))
				{
						
				        sql="select distinct(po.id_po) as do_direct,po.id_po,po.tot,po.no_ind,po.no_quot,po.id_dept,no_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dt_po1,nm_ven,no_quot"+
					    		"  from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v "+
					    		"  where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0 and st_po='1'  "+tempSql+"    order by dt_po1 DESC";
						
				}
				else
				{
			        sql="select distinct(po.id_po) as do_direct,po.id_po,po.tot,po.no_ind,po.no_quot,po.id_dept,no_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dt_po1,nm_ven,no_quot"+
				    		"  from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v "+
				    		"  where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0 and st_po='1'  "+tempSql+"    order by dt_po1 DESC";
					
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
			System.out.println("sql error in Approval_Quotation."+e.toString());
		}
		return jobj;
	}
	public JSONObject DisplayPurchaseOrderForsclose(String temp , int id_emp_user , String UserType,String word)
	{ 
		Common dbname=new Common();

	
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		String tempSql ="";
		 tempSql =" and (no_po LIKE '%"+word+"%'  or dt_po LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%')";
				
				if(UserType.equals("SUPER"))
				{
						
				       sql="select distinct(po.id_po) as do_direct,po.id_po,po.tot,po.no_ind,po.no_quot,po.id_dept,no_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dt_po1,nm_ven,no_quot"+
					    		"  from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v "+
					    		"  where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0 and st_po='1'    and shortclosed ='0' "+tempSql+"    order by dt_po1 DESC";
						
				      
				}
				else
				{
					   sql="select distinct(po.id_po) as do_direct,po.id_po,po.tot,po.no_ind,po.no_quot,po.id_dept,no_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dt_po1,nm_ven,no_quot"+
					    		"  from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Vendor v "+
					    		"  where po.id_po=poa.id_po and po.id_ven=v.id_ven  and po.cancel=0 and st_po='1'    and shortclosed ='0' "+tempSql+"    order by dt_po1 DESC";
							
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
			System.out.println("sql error in Approval_Quotation."+e.toString());
		}
		return jobj;
	}
}
