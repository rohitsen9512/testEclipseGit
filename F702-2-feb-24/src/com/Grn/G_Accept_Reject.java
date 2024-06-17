package com.Grn;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
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
import dto.Common.UserAccessData;


public class G_Accept_Reject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();

		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		Enumeration elemet = request.getParameterNames();
		HttpSession session = request.getSession();
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
		 
		    
		String action = "",id="0",id_inv_m="",word="",dt_to="",dt_frm="",status_approv="",remarks="",id_inv_ass="";
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		if(request.getParameter("id_inv_ass") !=null)
		{
			id_inv_ass = request.getParameter("id_inv_ass");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_inv_m") !=null)
		{
			id_inv_m = request.getParameter("id_inv_m");
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
		if(request.getParameter("remarks") !=null)
		{
			remarks = request.getParameter("remarks");
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
			temp = " and dt_grn <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_grn >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_grn >= '"+dt_frm+"' and dt_grn <= '"+dt_to+"'";
		}
		int id_emp_user = (int)session.getAttribute("id_emp_user");
		System.out.println("id emp is "+id_emp_user);
		String User_Type = (String)session.getAttribute("UserType");
		System.out.println("user type is "+User_Type);
		int id_log_user = (int)session.getAttribute("UserId");
		System.out.println("id_log_user is "+id_log_user);
		String id_dept = (String)session.getAttribute("DeptId");
		System.out.println("id dept is "+id_dept);
		
		String id_bu = (String)session.getAttribute("id_bu");
		System.out.println("id_bu is "+id_bu);
		
			con=Common.GetConnection(request);
			String UserType = (String)session.getAttribute("UserType");       
			switch (action)
	        {
	        case "Payment":
	        	System.out.println("vvvvvvvv");
            
        		int jp=0;
        		JSONObject json2=new JSONObject();
        		String payment_amount=request.getParameter("payment_amount");
        		String dt_payment=request.getParameter("dt_payment");
        		String no_utr=request.getParameter("no_utr");
        		String id_ven=request.getParameter("id_ven");
        		try 
        		{
        			String tot_pay=request.getParameter("tot_pay");
            		String acc_pay=request.getParameter("acc_pay");
            		String stpay="0";
            		if(Double.parseDouble(tot_pay)==(Double.parseDouble(acc_pay)+Double.parseDouble(payment_amount)))
            		
            				{
            			stpay="1";
            				}
            		
            				 
        			
        			 Date d = userDateFormat.parse(dt_payment); 
        			 System.out.println(dateFormatNeeded.format(d));
        				stmt=con.createStatement();
        				stmt.executeUpdate("update A_INVOICE_master set dt_payment='"+dateFormatNeeded.format(d)+"',no_utr='"+no_utr+"',payment_amount='"+(Double.parseDouble(acc_pay)+Double.parseDouble(payment_amount))+"' ,st_payment='"+stpay+"' where id_inv_m='"+id+"'");
        				
        				
        				stmt.executeUpdate("insert into payment_history ( dt_payment,no_utr,payment_amount,id_inv_m ) values('"+dateFormatNeeded.format(d)+"','"+no_utr+"','"+payment_amount+"' ,'"+id+"')");
        				Properties prop = new Properties();	
        				InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
        				
        				if (inputStream == null) {
        					System.out.println("property file 'configuration.properties' not found in the classpath");
        				}
        					
        					prop.load(inputStream);
        					
        					String Budgetemployeeidfrorapproval = prop.getProperty("Budgetemployeeidfrorapproval");
        					System.out.println(Budgetemployeeidfrorapproval);
        	        		
        				
        				////Mail TO usha and vendor
        				String replyMailId="",name="",ccMailId="";
                    	String  mailSql ="select id_emp,nm_emp from M_Emp_User emp where emp.id_emp_user="+Budgetemployeeidfrorapproval+" ";
                    	System.out.println(mailSql);
                    	
                    	DtoCommon dtcm = new DtoCommon();
            			rs = dtcm.GetResultSet(mailSql,  request);
            			if(rs.next())
            			{
            				replyMailId = rs.getString(1);
            				name = rs.getString(2);
            				ccMailId = replyMailId;
            			}
            			
            			mailSql ="select distinct(v.ct_mailid),nm_ven from M_Vendor v where v.id_ven="+id_ven+"";
            			System.out.println(mailSql);
            			List<String> recipients = dtcm.ReturnListData(mailSql,  request);
            			
            			String link = dtcm.ReturnParticularMessage("linkNego");
            			String footerMsg = dtcm.ReturnParticularMessage("footerMsgNego");
            			String mailSubject = dtcm.ReturnParticularMessage("PaymentSub");
            			
            			String mailBody = "<b> Hello </b>,<br><br><br>"+
            						  "payment has been done <b></b>.<br>Payment Amount:"+payment_amount+"<br>Payment Date:"+dt_payment+""+
            						  "<br>UTR Number:"+no_utr+"<br><br><br>"+link+""+
        							  "<p>"+footerMsg+"</p>";
            		
            			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
        			
        		
        				jp=1;
   				
        			
        			json2.put("data",jp);
        			
        		}
        		catch (Exception e)
        		{
        			System.out.println("Error in Invoice_Through_Po.."+e);
        			e.printStackTrace();
        		}
        	
        		break;
	        case "acceptReject":
	        	
        		int ja=0;
        		JSONObject json1=new JSONObject();
        		String Status=request.getParameter("Status");
        		String dt_accept_reject=request.getParameter("dt_accept_reject");
        		String remarks_reject=request.getParameter("remarks_reject");
        		try 
        		{
        				
        		            
        			
        			 Date d = userDateFormat.parse(dt_accept_reject); 
        			 System.out.println(dateFormatNeeded.format(d));
        				stmt=con.createStatement();
        				stmt.executeUpdate("update A_INVOICE_master set dt_accept_reject='"+dateFormatNeeded.format(d)+"',Status_approve='"+Status+"',remarks_reject='"+remarks_reject+"'  where id_inv_m='"+id+"'");
        				
        				
        		
        			
        		
   					ja=1;
   				
        			
        			json1.put("data",ja);
        			
        		}
        		catch (Exception e)
        		{
        			System.out.println("Error in Invoice_Through_Po.."+e);
        			e.printStackTrace();
        		}
        	
        		break;
	        case "Save":
	        	//System.out.println("vvvvvvvv");
            	String colName="",value="";
        		int j=0,id_grn=0;
        		JSONObject json=new JSONObject();
        		String sql = "";
        		try 
        		{
        				rs = Common.GetColumns("G_Grn",  request);
        				while (rs.next())
        				{
        				
        					if(!rs.getString(1).equals("id_grn"))
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
        				
        				 sql="insert into G_Grn("+colName+",add_by) values("+value+","+id_emp_user+")";
        					 System.out.println(sql);
        			     stmt=con.createStatement();
    				     stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
    				     rs = stmt.getGeneratedKeys();
    	                     while (rs.next()) 
        		            {
    	                    	 id_grn = rs.getInt(1);
        		            } 
        		            
        		            
        		 
        			String id_prod="",inv_val="0.0",id_po_asst="0.0",id_po="0.0",po_qty="0.0",rem_qty="",qty="",un_prc="",others="",id_tax1="",id_tax2="",tax_val1="",tax_val2="",buyback="",gr_tot="",id_grp="",id_sgrp="",tt_un_prc="",tt="",nm_acc_asset="";
        			String[] parts = id_inv_ass.split("Patel");
        			Set mySet = new HashSet(Arrays.asList(parts)); 
        			id = "";
        			String id_inv[] = request.getParameterValues("id_inv");
        			for (int i = 0; i < id_inv.length; i++) {
        				if(mySet.contains(id_inv[i])){
        					
        			sql = "select id_inv_m,id_po_asst,nm_acc_asset,id_grp,id_sgrp,id_inv_m,id_model,id_po,po_qty,rem_qty,qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot from A_Invoice where id_inv="+id_inv[i]+"";
        			PreparedStatement ps=con.prepareStatement(sql);
        			rs=ps.executeQuery();
        			if(rs.next()){
        				id_prod = rs.getString("id_model");
        				id_grp = rs.getString("id_grp");
        				id_sgrp = rs.getString("id_sgrp");
        				nm_acc_asset = rs.getString("nm_acc_asset");
        				
        				id_po = rs.getString("id_po");
        				id_po_asst=rs.getString("id_po_asst");
        				id_inv_m=rs.getString("id_inv_m");
        			}
        			others = request.getParameter("others"+i+"");
    				id_tax1 = request.getParameter("id_tax1"+i+"");
    				id_tax2 = request.getParameter("id_tax2"+i+"");
    				tax_val1 = request.getParameter("tax_val1"+i+"");
    				tax_val2 = request.getParameter("tax_val2"+i+"");
    				buyback = request.getParameter("buyback"+i+"");
    				tt_un_prc =request.getParameter("un_prc"+i+"");
    				un_prc = request.getParameter("un_prc"+i+"");
    				tt = request.getParameter("gr_tot"+i+"");
    				gr_tot = request.getParameter("gr_tot"+i+"");
    			String	po_no = request.getParameter("po_no"+i+"");
    			String	po_dt = request.getParameter("po_dt"+i+"");
    			String 	typ_asst = request.getParameter("typ_asst"+i+"");
    			
        			String ds_pro="";
           		 sql = "select * from M_Model where id_model='"+id_prod+"'";
    				ps=con.prepareStatement(sql);
    				rs=ps.executeQuery();
    				if(rs.next())
    				{
    					ds_pro=rs.getString("nm_model");
    					
    				}
        				 
        			
        			 rem_qty = request.getParameter("rem_qty"+i+"");
        			 qty = request.getParameter("qty"+i+"");
        				
        			double Diff=Double.parseDouble(rem_qty)-Double.parseDouble(qty);
        			 
        		
        			String query="insert into G_Grn_Asset(dt_po,no_po,id_inv,model,ds_pro,typ_asst,id_po_asst,gr_tot,buyback,tax_val2,tax_val1,id_tax2,id_tax1,id_po,id_dept,created_by,config,st_config,tt,tt_un_prc,id_grn,id_model,id_grp,id_sgrp,qty,un_prc,NM_ACC_ASSET,id_bu)values('"+po_dt+"','"+po_no+"','"+id_inv[i]+"','"+ds_pro+"','"+ds_pro+"','"+typ_asst+"','"+id_po_asst+"','"+gr_tot+"','"+buyback+"','"+tax_val2+"','"+tax_val1+"','"+id_tax2+"','"+id_tax1+"','"+id_po+"','"+id_dept+"','"+id_emp_user+"','Yes','Yes','"+tt+"','"+tt_un_prc+"','"+id_grn+"','"+id_prod+"','"+id_grp+"','"+id_sgrp+"','"+qty+"','"+un_prc+"','Asset','"+id_bu+"')";	
        			 
        			 System.out.println(query);
	        			ps=con.prepareStatement(query);
	        			j=ps.executeUpdate();
	        			System.out.println(j);
	        			
        			if(j > 0)
        			{
        				stmt=con.createStatement();
        				stmt.executeUpdate("update A_INVOICE set rem_qty="+Diff+" where id_inv="+id_inv[i]+"");
        				
        				
        			}
        			
        				}
        			}
        			
        			String queryrem="select sum(rem_qty) from A_INVOICE where id_inv_m="+id_inv_m+"";
   				 System.out.println(queryrem);
   				ps=con.prepareStatement(queryrem);
   				rs=ps.executeQuery();
   				if(rs.next())
   				{
   						
   					stmt=con.createStatement();
   					if(rs.getDouble(1)==0)
   					{
   						stmt.executeUpdate("update A_Invoice_Master set rec_status_grn=2 where id_inv_m="+id_inv_m+"");
   						
   					}
   					else
   					{
   					stmt.executeUpdate("update A_Invoice_Master set rec_status_grn=1 where id_inv_m="+id_inv_m+"");
   					
   						
   					}
   					j=1;
   				}
        			
        			json.put("data1",j);
        			
        		}
        		catch (Exception e)
        		{
        			System.out.println("Error in Invoice_Through_Po.."+e);
        			e.printStackTrace();
        		}
        	
        	
                break;
	            case "Display":
	            	String tempSql="";
	       		 	tempSql =" and (no_inv LIKE '%"+word+"%' or dt_inv LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' )";

	            	String sql3="select inv.id_inv_m,inv.no_inv,no_po,(replace(convert(NVARCHAR, inv.dt_po, 103), ' ', '-')) as dt_po,(replace(convert(NVARCHAR, inv.dt_inv, 103), ' ', '-')) as dt_inv,v.nm_ven from  A_Invoice_Master inv, M_Vendor v where inv.id_ven=v.id_ven and (rec_status_grn='1' or rec_status_grn='0')  "+tempSql+" ";
	            	System.out.println(sql3);
	            	jobj = Common.GetDataForDisplayInJsonFormat(sql3,  request);	
	            		
	                break;
	            case "DisplayForApprove":
	            	 tempSql="";
	       		 	tempSql =" and (no_inv LIKE '%"+word+"%' or dt_inv LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' )";

	            	 sql3="select inv.id_inv_m,inv.no_inv,no_po,(replace(convert(NVARCHAR, inv.dt_po, 103), ' ', '-')) as dt_po,(replace(convert(NVARCHAR, inv.dt_inv, 103), ' ', '-')) as dt_inv,v.nm_ven from  A_Invoice_Master inv, M_Vendor v where inv.id_ven=v.id_ven and (rec_status_grn='1' or rec_status_grn='0') and Status_approve='Waiting'  "+tempSql+" ";
	            	System.out.println(sql3);
	            	jobj = Common.GetDataForDisplayInJsonFormat(sql3,  request);	
	            		
	                break;
	            case "DisplayForpayment":
	            	 tempSql="";
	       		 	tempSql =" and (no_inv LIKE '%"+word+"%' or dt_inv LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' )";

	            	 sql3="select inv.id_inv_m,inv.no_inv,no_po,(replace(convert(NVARCHAR, inv.dt_po, 103), ' ', '-')) as dt_po,(replace(convert(NVARCHAR, inv.dt_inv, 103), ' ', '-')) as dt_inv,v.nm_ven from  A_Invoice_Master inv, M_Vendor v where inv.id_ven=v.id_ven and (rec_status_grn='1' or rec_status_grn='0') and Status_approve='Accept' and st_payment='0' "+tempSql+" ";
	            	System.out.println(sql3);
	            	jobj = Common.GetDataForDisplayInJsonFormat(sql3,  request);	
	            		
	                break;
	                
	            case "Update":
	            	jobj = UpdateGrnForAcceptReject(remarks,id,status_approv,  request);	
	                break;
	                
	            case "Edit":
	            		jobj=DisplayAssetForEdit(id);
	           
	                break;
	            case "DisplayAsset":
	            	
	            	jobj=DisplayAsset(id);
	           
	                break;   
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in G_Accept_Reject." +e);
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
	public JSONObject DisplayAssetForEdit(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		
		String sql="select (replace(convert(NVARCHAR, dt_accept_reject, 103), ' ', '-')) as dt_accept_reject,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po,A_Invoice_Master.*,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtinv  from A_Invoice_Master where id_inv_m="+id+"";
        
		
			 
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
		    
		    int id_fincance1=0;
        	stmt = con.createStatement();
			ps=con.prepareStatement("select id_fincance from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			rs=ps.executeQuery();
			if(rs.next())
			{
				id_fincance1=rs.getInt(1);
			}
		    
			String finyear="",poID2="";
			int PoId=1;
			
		
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
		
			ps=con.prepareStatement("select count(id_po) from P_Purchase_Order where SUBSTRING(no_po,1,8)='PO/"+strt+"-"+endt+"'  "  );
			rs=ps.executeQuery();
			
				if(rs.next())
				{
					PoId =	rs.getInt(1) +1;
					//PoId = PoId + 18000000;
					String PoDynId="",secCode="";
					
					String 	poID3 = String.format("%03d", PoId);
					
					poID2="GRN/"+strt+"-"+endt+"/"+poID3;
				
				}
				 jobj.put("no_grn", poID2);
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in G_Accept_Reject.");
		}
		 
		return jobj;
		
		
	}

	public JSONObject DisplayAsset(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		 
		String sql="select A_Invoice.*  from A_Invoice where  id_inv_m="+id+"";
		
		try
		{
			String id_po_asst="",sql6="",no_model="",qty="",no_po="",dt_po="";
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
		    	id_po_asst=rs.getString("id_po_asst");
   				sql6 = "select nm_model,qty,no_po,dt_po from m_model pc, P_Purchase_Order_Asset poa ,p_purchase_order po where po.id_po=poa.id_po and poa.id_prod=pc.id_model and id_po_asst='"+id_po_asst+"'  ";

				 System.out.println(sql6);
				 ps=con.prepareStatement(sql6);
				 rs1=ps.executeQuery();
				 if(rs1.next()){
					 no_model=rs1.getString(1);
					 qty=rs1.getString(2);
					 no_po=rs1.getString(3);
					 dt_po=rs1.getString(4);
					 
				   
			}
				 else{
					 
					 sql6 = "select nm_model,po_no,po_dt from m_model pc, A_Invoice poa  where  poa.id_model=pc.id_model and id_inv='"+rs.getString("id_inv")+"'  ";

					 System.out.println(sql6);
					 ps=con.prepareStatement(sql6);
					 rs1=ps.executeQuery();
					 if(rs1.next()){
						 no_model=rs1.getString(1);
						 qty="0";
						 no_po=rs1.getString(2);
						 dt_po=rs1.getString(3);
						 
					   
				} 
				 }
					jobj2.put("no_model", no_model);
			    	jobj2.put("qty_po", qty);
			    	jobj2.put("no_po", no_po);
			    	jobj2.put("dt_po", dt_po);
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
	
	
	public JSONObject UpdateGrnForAcceptReject(String remarks , String id , String status_approv,HttpServletRequest request)
	{
		
		int j=0;
		JSONObject json=new JSONObject();
		String stApprove="Approved";
		String query="update G_Grn set remarks ='"+remarks+"',status_approv ="+status_approv+" where id_grn="+id+"";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
				if(status_approv.equals("2"))
				{
				j=0;
				stApprove="Rejected";
				int id_inv=0,id_inv_m=0,TotQty=0;
				String	sql ="select id_inv_m,id_inv from G_Grn where id_grn="+id+"";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				 if(rs.next())
				 	{
					 id_inv_m = rs.getInt(1);
					 id_inv = rs.getInt(2);
				 	}
				 	sql ="select qty from A_Invoice where id_inv="+id_inv+"";
					ps=con.prepareStatement(sql);
					rs=ps.executeQuery();
					 if(rs.next())
					 	{
						 TotQty = rs.getInt(1);
						 
					 	}
				 
				sql ="select  qty_recv from G_Grn where id_inv_m = "+id_inv_m+" and status_approv =1";
				int TotRecvQty = 0;
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				 if(rs.next())
				 	{
					 	TotRecvQty = rs.getInt(1);
					 	
					 	if((TotRecvQty - TotQty) == 0)
						 {
					 		sql ="update A_Invoice_Master set rec_status_grn =0 where id_inv_m = "+id_inv_m+"";
					 		ps=con.prepareStatement(sql);
							j=ps.executeUpdate();
							if(j > 0)
							{
								j=0;
								sql ="update A_Invoice set rec_status_grn =0 where id_inv = "+id_inv+"";
						 		ps=con.prepareStatement(sql);
								j=ps.executeUpdate();
								if(j > 0)
								{
									j=1;
								}
							}
						 }
					 	else
					 	{
					 		j=0;
							sql ="update A_Invoice set rec_status_grn =0 where id_inv = "+id_inv+"";
							PreparedStatement	ps1=con.prepareStatement(sql);
							j=ps1.executeUpdate();
							if(j > 0)
							{
								j=1;
							}
					 	}
				 	}
				 else
				 	{
					 sql ="update A_Invoice_Master set rec_status_grn =0 where id_inv_m = "+id_inv_m+"";
					 PreparedStatement ps22=con.prepareStatement(sql);
						j=ps22.executeUpdate();
						if(j > 0)
						{
							j=0;
							sql ="update A_Invoice set rec_status_grn =0 where id_inv = "+id_inv+"";
							PreparedStatement ps33=con.prepareStatement(sql);
							j=ps33.executeUpdate();
							if(j > 0)
							{
								j=1;
							}
						}
				 	}
				
				}
			}
			DtoCommon dtcm = new DtoCommon();
			String replyMailId = "";
			String id_loc = "",typ_asst="";
			String id_sloc = "",no_po="",no_grn="";
			
			rs = dtcm.GetResultSet("select id_loc,id_subl,no_grn from A_Invoice_Master inv, G_Grn g where inv.id_inv_m=g.id_inv_m and id_grn= "+id+"",  request);
			if(rs.next())
			{
				id_loc = rs.getString(1);
				id_sloc = rs.getString(2);
				no_grn = rs.getString(3);
			}
			String mailSql = "select  e.id_emp from M_User_Login l,M_Emp_User e where e.id_emp_user=l.id_emp_user and "+
					 "type_user='STOREUSER' and e.status_emp='Working' and l.status_user='1'";
			
				List<String> recipients = dtcm.ReturnListData(mailSql,  request);
				
			if(!recipients.isEmpty())
			{
				replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
				recipients.remove(0);
				
				String link = dtcm.ReturnParticularMessage("link");
				String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
				String mailSubject = dtcm.ReturnParticularMessage("GRNAprvdRjct");
				
				String mailBody = "Hello <b>STORE Team</b>,<br><br><br>"+
						    "The "+no_grn+" has been "+stApprove+" by the asset executive.<br>"+
							  "<br><br><br>"+link+""+
							  "<p>"+footerMsg+"</p>";
			
				Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
			}
			
			j=1;
		
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	public JSONObject DisplayAssetOfParticularInvoiceForAcceptreject(String temp,String UserType)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		/*String tempSql = "select g.*,nm_s_assetdiv,ds_pro from G_Grn g,M_Subasset_Div sgrp,A_Invoice ai where status_approv = 0 and type_grn='INV' "+
				"and sgrp.id_s_assetdiv=ai.id_sgrp and ai.id_inv=g.id_inv ";
		UserAccessData uad = new UserAccessData();
		sql = uad.QueryMacker(tempSql , UserType , temp);
		*/
		if(!temp.equals(""))
		{
			sql = "select g.*,(replace(convert(NVARCHAR, g.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, g.dt_grn, 103), ' ', '-')) as dtGrn,nm_s_assetdiv,ds_pro from G_Grn g,M_Subasset_Div sgrp,A_Invoice ai where status_approv = 0 and type_grn='INV' "+
					"and sgrp.id_s_assetdiv=ai.id_sgrp and ai.id_inv=g.id_inv "+temp+" order by g.dt_grn";
		}
		else
			sql = "select g.*,(replace(convert(NVARCHAR, g.dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, g.dt_grn, 103), ' ', '-')) as dtGrn,nm_s_assetdiv,ds_pro from G_Grn g,M_Subasset_Div sgrp,A_Invoice ai where status_approv = 0 and type_grn='INV' "+
			"and sgrp.id_s_assetdiv=ai.id_sgrp and ai.id_inv=g.id_inv order by g.dt_grn";
		  /*sql ="select * from G_Grn where status_approv = 0 and type_grn='INV' ";*/
			
			 
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
			System.out.println("sql error in G_Accept_Reject.");
		}
		 
		return jobj;
		
		
	}
	
	
	}
