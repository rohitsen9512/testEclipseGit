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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;



public class Vendor_Acceptance extends HttpServlet {
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
		String logged=(String) session.getAttribute("log_name");
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
		String action = "",st_rate_cont_for_po="",word="",remarks="",id="0",dt_to="",dt_recv="",id_quot="",dt_frm="",status_approv="",no_ind="",dt_approv="",acceptQuotNo="",rate_cont="",dt_rate_cont_valid="";
		if(request.getParameter("st_rate_cont_for_po") !=null)
		{
			st_rate_cont_for_po = request.getParameter("st_rate_cont_for_po");
		}
		if(request.getParameter("rate_cont") !=null)
		{
			rate_cont = request.getParameter("rate_cont");
		}
		if(request.getParameter("dt_rate_cont_valid") !=null)
		{
			dt_rate_cont_valid = request.getParameter("dt_rate_cont_valid");
		}
		if(request.getParameter("remarks") !=null)
		{
			remarks = request.getParameter("remarks");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
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
		
		String temp="";
		try
		{
			Date currDate = new Date();
			
			if(!dt_recv.equals(""))
			{
				dt_recv = dateFormatNeeded.format(userDateFormat.parse(dt_recv));
			}
			if(!dt_rate_cont_valid.equals(""))
			{
				dt_rate_cont_valid = dateFormatNeeded.format(userDateFormat.parse(dt_rate_cont_valid));
			}
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
		
		if(!dt_to.equals(""))
		{
			temp = " and dt_rec_quot <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_rec_quot >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_rec_quot >= '"+dt_frm+"' and dt_rec_quot <= '"+dt_to+"'";
		}
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType"); 
			con=Common.GetConnection(request);;
			
			switch (action)
	        {
	                
	            case "Display":
	            	jobj = DisplayPurchaseOrderForVendorAcceptance(temp,word);	
	                break;
	           
        case "split":
	            	
	            	String count[] = request.getParameterValues("count");
	            	stmt=con.createStatement();
	            	int j=0;
	            	status_approv= "Accepted";
	            	Set<String> hash_Set = new HashSet<String>(); 
	            	 String sncno_fnl="",no_arc="",no_format="";
		               int snc_no=0,arc=0;
					
	            	
	            	String PoDynId="",pritesh_iso_num="",pritesh_arc="";
					String year="",id_quot_asst1="";
				
        			for(int i=0;i<count.length;i++)
        			{					
        				
        				String id_quot_asst = request.getParameter("id_quot_asst"+count[i]+"");
        				//id_quot = request.getParameter("id_quot"+count[i]+"");
        				String id_ven_selected = request.getParameter("id_ven_selected"+count[i]+"");
        				String ven_select_remrk = request.getParameter("ven_select_remrk"+count[i]+"");
        				String id_prod = request.getParameter("id_prod"+count[i]+"");
        				//budget calculation.................
        				String id_grp="",id_sgrp="",sqlbud="";
        				sqlbud = "select * from M_Prod_Cart where id_prod='"+id_prod+"'";
        				ps=con.prepareStatement(sqlbud);
        				rs=ps.executeQuery();
        				if(rs.next())
        				{
        					id_grp=rs.getString("id_grp");
        					id_sgrp=rs.getString("id_sgrp");
        					id_prod=rs.getString("id_prod");
        					
        				}
        				for(int p=0;p<i;p++){
        					String id_prod2 = request.getParameter("id_prod"+count[p]+"");
        					if(id_prod2.equals(id_prod)){
        						id_quot_asst1="eql";
        						p=i;
        					}
                			
        					
        				}
        				
        		if(!id_quot_asst1.equals("eql")){
        				String sql="select id_quot_asst,q.id_quot,id_prod from P_Quotation_Asset qa, P_Quotation q where no_ind='"+no_ind+"' and id_ven="+id_ven_selected+" and qa.id_quot=q.id_quot and id_prod="+id_prod+"";
        				ps=con.prepareStatement(sql);
    					rs=ps.executeQuery();
    					
    					//auto sanction number ......
    						if(rs.next())
    					{
    						
    						id_quot_asst =rs.getString("id_quot_asst");
    						id_quot_asst1=rs.getString("id_prod");
    					}
        		}
    				
        		/*String sql23="select grnd_tot,id_quot_asst,q.id_quot,id_prod from P_Quotation_Asset qa, P_Quotation q where no_ind='"+no_ind+"' and id_ven="+id_ven_selected+" and qa.id_quot=q.id_quot and id_prod="+id_prod+"";
				ps=con.prepareStatement(sql23);
				rs=ps.executeQuery();
				
				//auto sanction number ......
					if(rs.next())
				{
					
					id_quot_asst =rs.getString("id_quot_asst");
					id_quot_asst1=rs.getString("id_prod");
				}*/
        		
    					
    					////end////////////////////////////////////////
    					
                		System.out.println("update P_Quotation_Asset set  nev_prc='0',  id_ven_selected="+id_ven_selected+",ven_select_remrk='"+ven_select_remrk+"' where id_quot_asst = "+id_quot_asst+" ");
        				
                		stmt.executeUpdate("update P_Quotation_Asset set  nev_prc='0',  id_ven_selected="+id_ven_selected+",ven_select_remrk='"+ven_select_remrk+"' where id_quot_asst = "+id_quot_asst+" ");
                		hash_Set.add(id_ven_selected);
        			}
        			//System.out.println(hash_Set);
        			String id_ven ="";
        			//String quotationId[] = request.getParameterValues("quotationId");
        			for(Iterator<String> it = hash_Set.iterator(); it.hasNext();)
        			{	
        				id_ven = it.next();
        				System.out.println(id_ven);
                		String query="update P_Quotation set dt_approv ='"+dt_approv+"',st_quot ='"+status_approv+"',approv_by="+id_emp_user+",finance_approv_reject='Waiting',ivan_approv_reject='Waiting' where id_ven="+id_ven+" and no_ind='"+no_ind+"' ";
                		PreparedStatement ps=con.prepareStatement(query);
            			j=ps.executeUpdate();
            			j=1;
        			}
            			if(j > 0)
            			{
            				
            					j=0;
            					String query="update P_Quotation set st_quot_flag =1,finance_approv_reject='Waiting',ivan_approv_reject='Waiting' where no_ind='"+no_ind+"'";
            					ps=con.prepareStatement(query);
            					j=ps.executeUpdate();
            				/*	String query2="update P_Quotation set id_apprv ='Yes' where no_ind='"+no_ind+"'";
            					ps=con.prepareStatement(query2);
            					j=ps.executeUpdate();
            				*/
            					if(j > 0)
            					{
            						j=0;
            						query="update P_Req_Quot_Vendor set st_rec_quot ='Yes' where no_ind='"+no_ind+"'";
            						ps=con.prepareStatement(query);
            						j=ps.executeUpdate();
            						if(j > 0)
            						{
            							j=1;
            						}
            					}
            					if(j > 0)
            					{
            						j=0;
            						query="update  P_Request_Quotation  set id_apprv1='Yes' where no_ind='"+no_ind+"'";
            						System.out.println(query);
            						ps=con.prepareStatement(query);
            						j=ps.executeUpdate();
            						if(j > 0)
            						{
            							query = "update P_Quotation set st_final =1,finance_approv_reject='Waiting',ivan_approv_reject='Waiting' where no_ind='"+no_ind+"'";
        								System.out.println(query);
        								ps = con.prepareStatement(query);
        								j = ps.executeUpdate();
        								if (j > 0) {
        									j = 1;
        								}
            						}
            					}
            					  String msg1 = no_ind +" is finalized for PO by "+logged;
            				      
            					//stmt.executeUpdate("update  P_Request_Quotation  set id_apprv='Yes' where no_ind='"+no_ind+"'");
                			
        			}
        			jobj.put("data",j);
        			
        			//jobj = UpdateQuotationForAcceptReject(dt_approv,acceptQuotNo,status_approv,id_emp_user,countID,no_ind,remarks);	
 	               
	            	break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Approval_Quotation." +e);
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
	
	public JSONObject GetPoDetails(String id_po)
	{
		JSONObject json=new JSONObject();
		
		String query = "select sd_amt,to_char(to_date(dt_sd_amt,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_sd_amt from P_Purchase_Order where id_po="+id_po+"";
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				
				json.put("dt_sd_amt",rs.getString("dt_sd_amt"));
				json.put("sd_amt",rs.getString("sd_amt"));
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
	
	public JSONObject CheckPODate(String id_po,String dt_sched)
	{
		JSONObject json=new JSONObject();
		
		String query = "select to_char(to_date(dt_approv,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_approv from P_Purchase_Order where dt_approv >'"+dt_sched+"' and id_po="+id_po+"";
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
	
	public JSONObject SaveVendorAcceptance(HashMap<String, String> map,int id_emp_user,String id_po,HttpServletRequest request)
	{
		int j=0;
		String query="",sql="",colName="",value="";
		JSONObject json=new JSONObject();
		try 
		{
			rs = Common.GetColumns("P_VENDOR_ACCEPTANCE",  request);
			while (rs.next())
			{
			
				if(!rs.getString(1).equals("id_ven_accept"))
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
			
		
			query="insert into P_Vendor_Acceptance("+colName+",add_by)values("+value+","+id_emp_user+")";
			System.out.println(query);
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			
			
				sql="update P_Purchase_Order set st_ven_acceptance=1 where id_po="+id_po+"";
				System.out.println(sql);
				ps=con.prepareStatement(sql);
				j=ps.executeUpdate();
				j=1;
				
			json.put("data",j);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return json;
	}
	
	
	public JSONObject DisplayPurchaseOrderForVendorAcceptance(String temp, String word)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		String tempSql ="";
		if(!word.isEmpty())
		 tempSql =" and (REGEXP_LIKE(no_po,'"+word+"','i') or REGEXP_LIKE(no_quot,'"+word+"','i') or REGEXP_LIKE(brb_ref_num,'"+word+"','i') or REGEXP_LIKE(nm_ven,'"+word+"','i') or REGEXP_LIKE(nm_emp,'"+word+"','i') )";
		
		sql = "select po.*,nm_ven,nm_emp from P_Purchase_Order po,M_Emp_User emp,M_Vendor v where emp.id_emp_user=po.approv_by and v.id_ven=po.id_ven "+
				"  and cancel=0 and shortclosed=0 and st_ven_acceptance=0 and recv_invoice=0 "+tempSql+" ";

		/*if(!temp.equals(""))
		{
			sql = "select po.*,nm_ven,nm_emp from P_Purchase_Order po,M_Emp_User emp,M_Vendor v where emp.id_emp_user=po.approv_by and v.id_ven=po.id_ven "+
					"  and cancel=0 and shortclosed=0 and st_ven_acceptance=0 and recv_invoice=0 "+temp+"";
		}
		else
		{
			sql = "select po.*,nm_ven,nm_emp from P_Purchase_Order po,M_Emp_User emp,M_Vendor v where emp.id_emp_user=po.approv_by and v.id_ven=po.id_ven "+
					"  and cancel=0 and shortclosed=0 and st_ven_acceptance=0 and recv_invoice=0";
		}*/
			 
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
