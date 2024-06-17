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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


import dto.Common.DtoCommon;


public class NewPurchaseOrder extends HttpServlet {
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
		    
		String action = "",word="",dt_po="",id_quot="",no_quot="",id="0",dt_to="",dt_frm="",status_approv="",no_ind="",dt_approv="",acceptQuotNo="";
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("dt_po") !=null)
		{
			dt_po = request.getParameter("dt_po");
		}
		if(request.getParameter("id_quot") !=null)
		{
			id_quot = request.getParameter("id_quot");
		}
		if(request.getParameter("no_quot") !=null)
		{
			no_quot = request.getParameter("no_quot");
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
			temp = " and dt_approv <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_approv >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_approv >= '"+dt_frm+"' and dt_approv <= '"+dt_to+"'";
		}
		
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			int id_log_user = (int)session.getAttribute("UserId");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
			
	            case "Display":
	            	jobj = DisplayQuotationForPO(temp,word);	
	                break;
	            case "IndentDisplay":
	            	jobj = DisplayIndentForPO(no_ind,word);	
	                break;
	           
	                
	            case "CreatePOIdDynamic":
	            	int id_fincance1=0;
	            	stmt = con.createStatement();
					ps=con.prepareStatement("select id_fincance from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
					rs=ps.executeQuery();
					if(rs.next())
					{
						id_fincance1=rs.getInt(1);
					}
					
					
	            	String PONum = CreatePOIdDynamic(id_fincance1);
	            	jobj.put("PONum",PONum);
	            	
	                break;
	                
	            case "Edit":
	            	jobj = CreatePurchaseOrder(no_quot,no_ind,id_quot);	
	                break;
	            case "Edit_vo":
	            	jobj = CreatePurchaseOrdervo(no_ind);	
	                break;
	            case "CheckDate":
	            	jobj = CheckPODate(dt_po,id_quot);	
	                break;
	                
	            case "Save":

	            	String colName="";
	            	
	            	String value="";
            		int j=0,id_po=0;
            		String count[] = request.getParameterValues("count");
            		String id_prod[] = request.getParameterValues("id_prod");
            		String id_grp[] = request.getParameterValues("id_grp");
            		String id_sgrp[] = request.getParameterValues("id_sgrp");
            	
            	
            	
            	
	            	try
	        		{
	            		
	        			stmt = con.createStatement();
	        				rs = Common.GetColumns("P_PURCHASE_ORDER",  request);
	        						while (rs.next())
	        						{
	        						
	        							if(rs.getString(1) !="id_po")
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
	            	
	            	String id_dept="",id_cc1="",id_section="";
	            	String sql="select id_dept from M_emp_user emp, M_User_Login l where emp.id_emp_user=l.id_emp_user and id_log_user="+id_log_user+" ";
	            	System.out.println(sql);
	            	ps=con.prepareStatement(sql);
					rs=ps.executeQuery();
					if(rs.next())
					{
						id_dept =rs.getString(1);
						
					}
					int id_fincance=0;
					ps=con.prepareStatement("select id_fincance from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
					rs=ps.executeQuery();
					if(rs.next())
					{
						id_fincance=rs.getInt(1);
					}
					String POID="";
				
					
					
					
	             POID = CreatePOIdDynamic(id_fincance);
	        		String query="insert into P_Purchase_Order("+colName+",no_po,id_dept) values("+value+",'"+POID+"','"+id_dept+"')";
	        		System.out.println(query);
	        		
	        		stmt=con.createStatement();
					stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
					rs1 = stmt.getGeneratedKeys();
					/* PreparedStatement stmt_pre = con.prepareStatement(query, new String[] { "id_po" });
					 stmt_pre.executeUpdate();
					 rs1 = stmt_pre.getGeneratedKeys();	        		
			      */  
					if (rs1.next()) 
					{
			            id_po = rs1.getInt(1);
			            stmt=con.createStatement();
	        			for(int i=0;i<count.length;i++)
	        			{					
	        				j=0;
	        				
	        				String id_uom = request.getParameter("id_uom"+count[i]+"");
	                		String qty = request.getParameter("qty"+count[i]+"");
	                		String un_prc = request.getParameter("un_prc"+count[i]+"");
	                		String others = request.getParameter("others"+count[i]+"");
	                		String id_tax1 = request.getParameter("id_tax1"+count[i]+"");
	                		String id_tax2 = request.getParameter("id_tax2"+count[i]+"");
	                		String tax_val1 = request.getParameter("tax_val1"+count[i]+"");
	                		String tax_val2 = request.getParameter("tax_val2"+count[i]+"");
	                		String buyback = request.getParameter("buyback"+count[i]+"");
	                		String gr_tot = request.getParameter("gr_tot"+count[i]+"");
	                		String item_remarks = request.getParameter("item_remarks"+count[i]+"");
	                		String dt_scheduled_dlvry = request.getParameter("dt_scheduled_dlvry"+count[i]+"");
	                		if(item_remarks == null)
	        	        		item_remarks="";
	                		
	                		if(!dt_scheduled_dlvry.equals("")){
	                		 dt_scheduled_dlvry = request.getParameter("dt_scheduled_dlvry"+i+"");
		        			 Date d = userDateFormat.parse(dt_scheduled_dlvry); 
		        			 System.out.println(dateFormatNeeded.format(d));
		        			 System.out.println("insert into P_Purchase_Order_Asset(item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"','"+dateFormatNeeded.format(d)+"',"+id_po+","+id_prod[i]+","+id_grp[i]+","+id_sgrp[i]+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+")");
					          
		                		stmt.executeUpdate("insert into P_Purchase_Order_Asset(item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"','"+dateFormatNeeded.format(d)+"',"+id_po+","+id_prod[i]+","+id_grp[i]+","+id_sgrp[i]+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+")");
			                	
		                		j=1;  
	                		}else{
	                		dt_scheduled_dlvry="";
	                		
	                  		System.out.println("insert into P_Purchase_Order_Asset(item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"','"+dt_scheduled_dlvry+"',"+id_po+","+id_prod[i]+","+id_grp[i]+","+id_sgrp[i]+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+")");
				              
	                		stmt.executeUpdate("insert into P_Purchase_Order_Asset(item_remarks,dt_scheduled_dlvry,id_po,id_prod,id_grp,id_sgrp,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"','"+dt_scheduled_dlvry+"',"+id_po+","+id_prod[i]+","+id_grp[i]+","+id_sgrp[i]+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+")");
				              
	                		
	                		j=1;
	        			}}
	        	
	        			if(j == 1)
	        			{
	        				j=0;
	        				stmt=con.createStatement();
	        				stmt.executeUpdate("update P_Quotation set st_po='Yes' where st_quot='Accepted' and no_quot='"+no_quot+"' and id_quot="+id_quot+""); 
	        				j=1;
	        			
	        			}
	        		}
			            
			    /*     // Mail Trigger......
						String replyMailId="",name="",no_po="";
			        	String mailSql ="select id_emp,nm_emp,no_po from M_Emp_User emp,P_Purchase_Order po where emp.id_emp_user=po.apprv and id_po='"+id_po+"'";
			        	
			        	DtoCommon dtcm = new DtoCommon();
						rs = dtcm.GetResultSet(mailSql);
						if(rs.next())
						{
							replyMailId = rs.getString(1);
							name = rs.getString(2);
							no_po = rs.getString(3);
						}
						mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_Purchase_Order po where emp.id_emp_user=po.add_by and id_po='"+id_po+"'";
						
						List<String> recipients = dtcm.ReturnListData(mailSql);
						
						String link = dtcm.ReturnParticularMessage("link");
						String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
						String mailSubject = dtcm.ReturnParticularMessage("purchaseReqPO");
						
						String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
									  "Purchase order <b>("+no_po+")</b> has been created. Please approve it."+
									  "<br><br><br>"+link+""+
									  "<p>"+footerMsg+"</p>";
					
						//Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
*/				        j=1;    
			            
			            
			            
			            jobj.put("data",j);
			            jobj.put("POID",POID);
	            	
	                break;
	                
	            
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in NewPurchaseOrder."+e);
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

	public JSONObject CheckPODate(String dt_po,String id_quot)
	{
		JSONObject json=new JSONObject();
		Common dbname=new Common();
	
		String query="";
		
			 query = "select  (replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv from P_Quotation where dt_approv >'"+dt_po+"' and id_quot='"+id_quot+"'";
		
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				
				json.put("data",0);
				json.put("dt_approv",rs.getString(1));
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
	
	
	public JSONObject CreatePurchaseOrder(String no_quot ,String no_ind,String id_quot)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		Common dbname=new Common();
		
		
		try
		{
		//String POID = CreatePOIdDynamic();
			String POID ="",sql="";
			int id_fincance1=0;
			sql="select qa.* ,oter_text,frt_text,q.frt_text as frttext,remarks,(replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dtRecQuot,nm_model,cd_model,tot, "+
                "   nm_ven,cst,cd_ven,q.id_ven,q.no_quot,q.id_quot,add1,add2,add3,city,"+
                "   state,country,q.id_curr,q.t_c_quot,q.tax_prc,id_tax1,id_tax2,tax_val1,tax_val2, "+
                "   discount from P_Quotation q,P_Quotation_Asset qa,m_model pc,M_Vendor v,  "+
                "   M_Tax t1,m_tax t2  where t2.id_tax=qa.id_tax2 and t1.id_tax=qa.id_tax1 and q.id_quot=qa.id_quot "+
                "   and qa.id_prod=pc.id_model  and st_quot='Accepted' and st_po='No' "+
				"   and no_ind='"+no_ind+"' and q.id_quot='"+id_quot+"' and qa.id_ven_selected=v.id_ven ";		
		
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
			    
			    
			    jobj.put("data", jarr);
			   
		}
		catch(Exception e)
		{
			System.out.println("sql error in Approval_Quotation."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	public String CreatePOIdDynamic(int id_fincance)
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
	
		ps=con.prepareStatement("select count(id_po) from P_Purchase_Order where SUBSTRING(no_po,1,8)='PO/"+strt+"-"+endt+"'  "  );
		rs=ps.executeQuery();
		
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				//PoId = PoId + 18000000;
				String PoDynId="",secCode="";
				
				String 	poID3 = String.format("%03d", PoId);
				
				poID2="PO/"+strt+"-"+endt+"/"+poID3;
			
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return poID2;
	}
	
	
	public JSONObject DisplayQuotationForPO(String temp,String word)
	{
		Common dbname=new Common();
		
		
	
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		String tempSql ="";
	
			// tempSql =" and (REGEXP_LIKE(no_quot,'"+word+"','i') or REGEXP_LIKE(ci.no_ind,'"+word+"','i') or REGEXP_LIKE(nm_ven,'"+word+"','i') or REGEXP_LIKE(nm_emp,'"+word+"','i') )";
			 tempSql =" and (no_quot LIKE '%"+word+"%' or ci.no_ind LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' or nm_emp LIKE '%"+word+"%' )";
					
				sql = "select ci.no_ind,no_quot,dt_approv as dt_approv1, (replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv,nm_emp,nm_ven,q.id_quot from P_Quotation q,P_Create_Indent ci,M_Emp_User emp,M_Vendor v "+
						" where q.no_ind=ci.no_ind and q.approv_by=emp.id_emp_user and q.id_ven=v.id_ven and  st_po='No' and st_quot='Accepted' and finance_approv_reject='Accept' and ivan_approv_reject='Accept'   "+tempSql+"  order by dt_approv1 DESC";
			
	
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
	

	public JSONObject DisplayIndentForPO(String no_ind, String word ) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		try {
			
			String tempSql ="";
			if(!word.isEmpty())
			 tempSql =" and (REGEXP_LIKE(no_ind,'"+word+"','i') or REGEXP_LIKE(dt_ind,'"+word+"','i') or REGEXP_LIKE(nm_emp,'"+word+"','i') )";
			sql = "select ci.*,to_char(to_date(ci.dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY') as dtInd,nm_emp from P_Create_Indent ci,M_Emp_User emp where st_ind='No' and po_type='Direct_po' and emp.id_emp_user=ci.ind_add_by "+tempSql+" order by ci.dt_ind DESC";
					
/*if(no_ind.equals(null))
{
			sql = "select ci.*,to_char(to_date(ci.dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY') as dtInd,nm_emp from P_Create_Indent ci,M_Emp_User emp where st_ind='No' and emp.id_emp_user=ci.ind_add_by "+tempSql+" order by ci.dt_ind DESC";
}
else
{
	sql = "select ci.*,to_char(to_date(ci.dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY') as dtInd,nm_assetdiv,nm_s_assetdiv,nm_prod,cd_prod,ia.mfr,ia.description,ia.qty,ia.id_req,ia.id_ind_asst,ia.id_prod,uom from P_Create_Indent ci,P_Indent_Asset ia,M_Asset_Div ad,M_Subasset_Div sad,M_Prod_Cart pc "+
			" where ci.no_ind=ia.no_ind and ia.id_grp=ad.id_assetdiv and ia.id_sgrp=sad.id_s_assetdiv and ia.id_prod=pc.id_prod "+
			" and ci.no_ind='"+no_ind+"' order by ci.dt_ind DESC";	
}*/
System.out.println(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			while (rs.next()) {
				JSONObject jobj2 = new JSONObject();
				for (int i = 1; i <= columns; i++) {
					String name = metaData.getColumnName(i).toLowerCase();
					jobj2.put(name, rs.getString(name));
				}
				jarr.put(jobj2);

			}
			jobj.put("data", jarr);
			
			/*sql="select t_and_c from M_Term_Condition where quo_tc='yes'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				jobj.put("t_and_c", rs.getString(1));
			}
			else
			{
				jobj.put("t_and_c", "-");
			}*/
				
				
		} catch (Exception e) {
			System.out.println("sql error in Create_Indent."+e.toString());
		}

		return jobj;

	}
	public JSONObject CreatePurchaseOrdervo(String no_ind)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
		//String POID = CreatePOIdDynamic();
			String POID ="";
	/*	String sql="select qa.*,indent_dept,pc.uom as uomval,q.frt_rate as frtrate ,q.add_chrg as addchrg,q.frt_text as frttext,q.buy_back as buyback,to_char(to_date(q.dt_rec_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dtRecQuot,nm_prod,cd_prod,tot,nm_ven,cst,cd_ven,nm_curr,q.id_ven,q.no_quot,q.propsl_num,q.id_quot,q.tender_num,nm_tax,add1,add2,add3,city,state,country,q.id_curr,q.t_c_quot,q.tax_prc,q.bnm_num,q.payment_term "+
		" from P_Quotation q,P_Quotation_Asset qa,M_Prod_Cart pc,M_Vendor v,M_Currency c,M_Tax t "+
				" where t.id_tax=qa.id_tax and q.id_quot=qa.id_quot and qa.id_prod=pc.id_prod and c.id_curr=q.id_curr and st_quot='Accepted' and st_po='No' and no_ind='"+no_ind+"'  and qa.id_ven_selected=v.id_ven ";
	*/
			String sql = "select ia.*,ci.PO_TYPE,ci.id_ind,ci.dt_ind,ci.st_ind,ci.ind_add_by,nm_assetdiv,nm_s_assetdiv,nm_prod,cd_prod,uom from P_Create_Indent ci,P_Indent_Asset ia,M_Asset_Div ad,M_Subasset_Div sad,M_Prod_Cart pc "+
					" where ci.no_ind=ia.no_ind and ia.id_grp=ad.id_assetdiv and ia.id_sgrp=sad.id_s_assetdiv and ia.id_prod=pc.id_prod "+
					" and ci.no_ind='"+no_ind+"'";
			
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
			    String iso_num="";
			    sql="Select * from M_Company";
			    			ps=con.prepareStatement(sql);
			    			rs=ps.executeQuery();
			    			if(rs.next())
			    				iso_num=rs.getString("iso_num");
			    
			    jobj.put("data", jarr);
			    jobj.put("POID", POID);
			    //jobj.put("iso_num", iso_num);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Approval_Quotation."+e.toString());
		}
		 
		return jobj;
		
		
	}
	public String CreatePOIdDynamicdirect(String typ_of_po, String fromSection,int id_fincance)
	{
		String finyear="",poID2="";
		int PoId=1;
		
		try
		{
			
		ps=con.prepareStatement("select count(id_po) from P_Purchase_Order where typ_of_po='"+typ_of_po+"' and id_fincance="+id_fincance+" and indent_dept='"+fromSection+"'" );
		rs=ps.executeQuery();
		
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				//PoId = PoId + 18000000;
				String PoDynId="",secCode="";
				int year=0;
				/*stmt=con.createStatement();
				rs=stmt.executeQuery("select UPPER(SUBSTR(nm_section,1,3)) as section,cd_section  from M_Section where id_section="+fromSection+" ");
				if(rs.next())
				{
					secCode=rs.getString("cd_section");
					
				}
				
				ps=con.prepareStatement("select extract(year from to_date(STD_FINANCE,'yyyy/mm/dd')) as stdate,extract(year from to_date(END_FINANCE,'yyyy/mm/dd')) as endyear from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
				rs=ps.executeQuery();
				if(rs.next())
				{
					year=rs.getInt("stdate");
				}
				//year = yearTemp + "000000";
				
				PoDynId= String.format("%04d", PoId);
				
				//PoId = PoId + Integer.parseInt(year);
*/				
				//poID2="SAL/"+year+"/"+secCode+"/"+PoDynId+"/"+ typ_of_po;
				poID2="PO-NO/"+ PoId;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return poID2;
	}
	
	
	}
