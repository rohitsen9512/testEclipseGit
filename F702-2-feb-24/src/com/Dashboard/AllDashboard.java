package com.Dashboard;

import java.io.IOException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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

import dto.Common.DtoCommon;




public class AllDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;


	private static final Object UserType = null;
	
	
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
		 int id_emp_user = (int)session.getAttribute("id_emp_user");
		 String std_finance=(String) session.getAttribute("std_finance");
		 System.out.println(std_finance);
		
			String end_finance=(String) session.getAttribute("end_finance");
			 System.out.println(end_finance);
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
		String action = "",id="0";
		
		String group="",subgrp="",model="",typ_asst="",addquery="",year="",month="";
		
		group = request.getParameter("group");
		subgrp = request.getParameter("subgrp");
		model = request.getParameter("model");
		typ_asst = request.getParameter("typ_asst");
		
				
		if(request.getParameter("group") !=null)
		{
			group = request.getParameter("group");
			 if(!group.equals("")) {
					addquery= addquery+" and id_grp='"+group+"'";
				}
		}
		if(request.getParameter("subgrp") !=null)
		{
			subgrp = request.getParameter("subgrp");
			if(!subgrp.equals("")) {
				addquery= addquery+" and id_sgrp='"+subgrp+"'";
			}
		}
		if(request.getParameter("model") !=null)
		{
			model = request.getParameter("model");
			if(!model.equals("")) {
				addquery= addquery+" and id_model='"+model+"'";
			}
			
		}
		if(request.getParameter("typ_asst") !=null)
		{
			typ_asst = request.getParameter("typ_asst");
			if(!typ_asst.equals("")) {
				addquery= addquery+" and typ_asst='"+typ_asst+"'";
			}
			
		}
		
String type_repo="";
		
		if(request.getParameter("type_repo") !=null)
		{
			type_repo = request.getParameter("type_repo");
			 
		}
		 
		if(request.getParameter("year") !=null)
		{
		year = request.getParameter("year");

		}
		if(request.getParameter("month") !=null)
		{
		month = request.getParameter("month");

		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		
		String UserType = (String)session.getAttribute("UserType");
		//String id_dept = (String)session.getAttribute("DeptId");
		int UserId = (int)session.getAttribute("UserId");
		//String id_finance = (String) session.getAttribute("id_fincance");
		String id_finance=request.getParameter("id_fincance");
		//	System.out.println("pritesh"+id_finance);
		
//		if(action.equals("alldevicestatus")) {
//			
//			addquery= addquery+" and typ_asst='"+request.getParameter("typ_asst")+"'";
//		}
		
		
		 try
		 {
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	           
	        case "poDashboard":
            	jobj = poDashboard(std_finance,type_repo,end_finance,UserType,request);	
                break; 
//	        case "tranferDashboard":
//            	jobj = tranferDashboard(id_finance,type_repo, id_dept,UserType);	
//                break;     
//	        case "poVendorDashboard":
//            	jobj = poVendorDashboard(id_emp_user);	
//                break;   
	        case "invoiceVendorDashboard":
            	jobj = invoiceVendorDashboard(id_emp_user);	
                break;       
	        case "PendingReject":
            	jobj = PendingReject();	
                break;
	        case "DashCount":
            	jobj = DashCount(id_emp_user,UserType,std_finance,end_finance,request);	
                break; 
//	        case "devicestatus":
//            	jobj = devicestatus(addquery,id_dept,UserType,UserId,request);
//            	 break;
//	        case "devicestatus1":
//            	jobj = devicestatus1(addquery,id_dept,UserType,UserId,request);
//            	 break;	 
	        case "helpdeskdash":
            	jobj = helpdeskdash(addquery);
            	 break;	 
	        case "devicestatusSoft":
            	jobj = devicestatusSoft(addquery);
                break;  
//	        case "alldevicestatus":
//            	jobj = completeStock(addquery,id_dept,UserType,UserId,request);
//                break; 
//	        case "alldevicestatus1":
//            	jobj = completeStock1(addquery,id_dept,UserType,UserId,request);
//                break;    
	        case "AMCDashboard":
	        	jobj = AMCDashboard(id_finance,type_repo,id,year,month);
	        	break;        
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Dashboard.");
		}
			finally
			{
//				try {
//					con.close();
//				} catch (SQLException e) {
//					
//					e.printStackTrace();
//				}
			}
		
		 
		 
		 
	}
	private JSONObject AMCDashboard(String id_finance, String type_repo, String id, String year, String month) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		  String year1="",end1="";
		
		
		
		try
		{
		String tempQuery="";
		
		int year2=Integer.valueOf(year);
		int month1=Integer.valueOf(month);	
		ArrayList<String> menu2 = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year2);
        cal.set(Calendar.MONTH, month1-1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(cal.getTime()));
        for (int i = 1; i < maxDay; i++) 
        {
        cal.set(Calendar.DAY_OF_MONTH, i );
        System.out.println(df.format(cal.getTime()));
	        menu2.add(df.format(cal.getTime()));
	    }		
	    String[] submenu2 = menu2.toArray(new String[0]);
	    
			
			for(int q=0;q<submenu2.length;q++){
				String sql=" select count (*) as count, (case  WHEN  sum(id_ven_proc) is null then 0  else  sum(id_ven_proc) end) as tot from A_Ware_House where dt_amc_exp =  '"+submenu2[q]+"'  ";	
			System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
			int count=0;
			  JSONObject jobj2 = new JSONObject();
		    	
				   ResultSetMetaData metaData = rs.getMetaData();
				    int columns = metaData.getColumnCount();
				    while(rs.next())
				    {
				    	count=rs.getInt("count")	;
				    	jobj2.put(submenu2[q]+"("+rs.getString("tot")+")",count);
					    
			        }	
				  
			    	 jarr.put(jobj2);
			    	 System.out.println(jobj2);	
					}
	
	jobj.put("data1", jarr);
	System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}
	public JSONObject completeStock(String addquery,String id_dept,String UserType,int UserId,HttpServletRequest request)
{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		DtoCommon dtoCommon = new DtoCommon();
		  String year1="",end1="";
		String tempqry="";
		String tempQuery2="";
		if(!UserType.equals("Super")) {
			tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
		}
		
		
		try
		{
		String tempQuery="";
		String sql="";
				
		 String devicests[]={"allct_to_emp","in_store"};
		    String devicestsname[]={"Allocated to employee","In Store"};
			
		
	for(int q=0;q<devicests.length;q++){
		if(devicests[q].equals("allct_to_emp")) {
			sql="select count(id_wh_dyn) from a_ware_house wh where  (device_status = 'allct_to_emp' or device_status = 'allct_to_emp_temp')  "+addquery+" "+tempQuery2+" ";
		}
		else {
		 sql="select count(id_wh_dyn) from a_ware_house wh where  device_status = '"+devicests[q] +"' "+addquery+" "+tempQuery2+" ";	
		}
		System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	jobj2.put(devicestsname[q],rs.getString(1));
		    	 jarr.put(jobj2);
		    	 	
	        }	
		   
			}
	
	jobj.put("data1", jarr);
	System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}
	public JSONObject completeStock1(String addquery,String id_dept, String UserType,int UserId,HttpServletRequest request)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		DtoCommon dtoCommon = new DtoCommon();
		  String year1="",end1="";
		  String tempQuery2="";
		  if(!UserType.equals("Super")) {
				tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
			}
		
		
		try
		{
		String tempQuery="";
		
				
		 String devicests[]={"allct_to_emp","in_store"};
		    String devicestsname[]={"Allocated to employee","In Store"};
			
		
	for(int q=0;q<devicests.length;q++){
		
		
		String sql="select count(id_wh_dyn) as count from a_ware_house wh where  device_status = '"+devicests[q] +"' "+addquery+" "+tempQuery2+" ";	

		//System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
int count=0;
		
		while(rs.next())
	    {
	    	count=rs.getInt("count")	;
	    	
        }	
	    JSONObject jobj2 = new JSONObject();
    	
    	jobj2.put("count",count);
    	 jarr.put(jobj2);
    	 //System.out.println(jobj2);	
		}

jobj.put("data", jarr);
//System.out.println(jarr);
	}
	catch(Exception e)
	{
		System.out.println("sql error in A_Install."+e);
	}
	 
	return jobj;
}
	public JSONObject devicestatus(String addquery,String id_dept,String UserType,int UserId,HttpServletRequest request)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		DtoCommon dtoCommon = new DtoCommon();
		  String year1="",end1="";
		  String tempQuery2="";
		  if(!UserType.equals("Super")) {
				tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
			}
			
		
		
		  try
			{
			String tempQuery="";
			String sql="";
					
		    String devicests[]={"allct_to_emp","under_service","not_workin","in_store","temporary_laptop","buyback","physical_dmg_mjr","physical_dmg_mnr","scraped"};
		    String devicestsname[]={"Allocated to employee","Under service","Not Working","In Store","Temporary Laptop","Buy back","Physical Damage (Major)","Physical Damage (Minor)","Scrapped / disposed"};
			
			
		for(int q=0;q<devicests.length;q++){
			if(devicests[q].equals("allct_to_emp")) {
				sql="select count(id_wh_dyn) from a_ware_house wh where  (device_status = 'allct_to_emp' or device_status = 'allct_to_emp_temp') "+addquery+" "+tempQuery2+" ";
			}
			else {
			
			 sql="select count(id_wh_dyn) from a_ware_house wh where  device_status = '"+devicests[q] +"' "+addquery+" "+tempQuery2+" ";	
			}
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next())
			    {
				
			    	JSONObject jobj2 = new JSONObject();
			    	jobj2.put(devicestsname[q],rs.getString(1));
			    	 jarr.put(jobj2);
			    	 	
		        }	
			   
				}
		
		jobj.put("data1", jarr);
		System.out.println(jarr);
			}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}
	
	public JSONObject devicestatus1(String addquery,String id_dept,String UserType,int UserId,HttpServletRequest request)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		DtoCommon dtoCommon = new DtoCommon();
		  String year1="",end1="";
		  String tempQuery2="";
		  if(!UserType.equals("Super")) {
				tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
			}
			
		
		
		try
		{
		String tempQuery="";
		
				
	    String devicests[]={"allct_to_emp","under_service","not_workin","in_store","temporary_laptop","buyback","physical_dmg_mjr","physical_dmg_mnr","scraped"};
	    String devicestsname[]={"Allocated to employee","Under service","Not Working","In Store","Temporary Laptop","Buy back","Physical Damage (Major)","Physical Damage (Minor)","Scrapped / disposed"};
		
		
	for(int q=0;q<devicests.length;q++){
		
		
		String sql="select count(id_wh_dyn) as count from a_ware_house where  device_status = '"+devicests[q] +"' "+addquery+" "+tempQuery2+" ";	
		
		//	System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		int count=0;
		
		while(rs.next())
	    {
	    	count=rs.getInt("count")	;
	    	
        }	
	    JSONObject jobj2 = new JSONObject();
    	
    	jobj2.put("count",count);
    	 jarr.put(jobj2);
    	 // System.out.println(jobj2);	
		}

jobj.put("data", jarr);
//System.out.println(jarr);
	}
	catch(Exception e)
	{
		System.out.println("sql error in A_Install."+e);
	}
	 
	return jobj;
}
	public JSONObject group(String addquery)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		  String year1="",end1="";
		
		
		
		try
		{
		String tempQuery="";
		
				
	    String devicests[]={"allct_to_emp","under_service","not_workin","in_store","temporary_laptop","buyback","physical_dmg_mjr","physical_dmg_mnr","scraped"};
	    String devicestsname[]={"Allocated to employee","Under service","Not Working","In Store","Temporary Laptop","Buy back","Physical Damage (Major)","Physical Damage (Minor)","Scrapped / disposed"};
		
		
	for(int q=0;q<devicests.length;q++){
		
		
		String sql="select count(id_wh_dyn) as count from a_ware_house where  device_status = '"+devicests[q] +"' "+addquery+" ";	
		
		//System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		int count=0;
		
		while(rs.next())
	    {
	    	count=rs.getInt("count")	;
	    	
        }	
	    JSONObject jobj2 = new JSONObject();
    	
    	jobj2.put("count",count);
    	 jarr.put(jobj2);
    	 //System.out.println(jobj2);	
		}

jobj.put("data", jarr);
//System.out.println(jarr);
	}
	catch(Exception e)
	{
		System.out.println("sql error in A_Install."+e);
	}
	 
	return jobj;
}
	
	public JSONObject helpdeskdash(String addquery)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		  String year1="",end1="";
		
		
		
		try
		{
		String tempQuery="";
		
				
	    String ticsts[]={"New","Active","Awaiting User Info","Closed","Closed as in complete","Cancelled"};
	    String ticstsname[]={"New","In-Progress","Awaiting User Info","Closed","Closed as in complete","Cancelled"};
		
		
	for(int q=0;q<ticsts.length;q++){
		
		
		String sql="select count(id_ticket) from HD_Tickets where state = '"+ticsts[q] +"' "+addquery+" ";	

		//System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	jobj2.put(ticstsname[q],rs.getString(1));
		    	 jarr.put(jobj2);
		    	 	
	        }	
		   
			}
	
	jobj.put("data1", jarr);
	//System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}
	
	public JSONObject devicestatusSoft(String addquery)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		  String year1="",end1="";
		
		
		
		try
		{
		String tempQuery="";
		
				
	    String devicests[]={"allct_to_emp","in_store"};
	    String devicestsname[]={"Allocated to employee","In Store"};
		
		
	for(int q=0;q<devicests.length;q++){
		
		
		String sql="select count(id_wh_dyn) from a_ware_house where typ_asst='soft' and device_status = '"+devicests[q]+"' "+addquery+"";	

		//System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	jobj2.put(devicestsname[q],rs.getString(1));
		    	 jarr.put(jobj2);
		    	 	
	        }	
		   
			}
	
	jobj.put("data1", jarr);
	//System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}
	
	public JSONObject poDashboard(String std_finance, String type_repo, String end_finance,String UserType,HttpServletRequest request)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr2 = new JSONArray();
		  String year1="",end1="";
		  DtoCommon dtoCommon = new DtoCommon();
		
		
		
		try
		{
		String tempQuery="";
		
				
		String strtdt[]={"-04-01","-05-01","-06-01","-07-01","-08-01","-09-01","-10-01","-11-01","-12-01","-01-01","-02-01","-03-01",};
		String enddt[]={"-04-30","-05-31","-06-30","-07-31","-08-31","-09-30","-10-31","-11-30","-12-31","-01-30","-02-28","-03-30",};
		String Month[]={"April","May","June","July","August","September","October","November","December","January","February","March",};
		
			ps=con.prepareStatement("select std_finance as  startdt , end_finance as  enddt from M_FINANCE_YEAR where std_finance='"+std_finance+"' and end_finance='"+end_finance+"'");
			rs=ps.executeQuery();
			if(rs.next())
			{
				year1=rs.getString("startdt");
				end1=rs.getString("enddt");
			}
			String strt1 = year1.substring (0,4);
			String endt1 = end1.substring (0,4);
			//System.out.println(endt1);
			String addqr="";
			if(type_repo.equals("Direct")) {
				addqr=" and do_direct=1";
			}
			
			if(type_repo.equals("Cancel")) {
				addqr=" and cancel=1";
			}
			
			if(type_repo.equals("Open")) {
				addqr=" and  (recv_invoice=0 or recv_invoice=1)";
			}
			if(type_repo.equals("Short")) {
				addqr=" and shortclosed=1";
			}
			
			String stnddt="";
			for(int q=0;q<=11;q++){

			if(q>=9) {
			stnddt=endt1;
			}
			else{
			stnddt=strt1;
			}
				String sql="";
				if(!UserType.equals("Super"))
		    	{
					
		        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"inv","inm", request);
				 sql="   select count (*) as count, (case  WHEN  sum(gr_tot) is null then 0  else  sum(gr_tot) end) as tot from A_invoice_master inm, A_invoice inv where inv.id_inv=inm.id_inv_m and  inm.dt_inv between  '"+stnddt+""+strtdt[q]+"' AND '"+stnddt+""+enddt[q]+"' "+addqr+" "+tempQuery2+"";	
			
		    	}
				else {
					sql="   select count (*) as count, (case  WHEN  sum(gr_tot) is null then 0  else  sum(gr_tot) end) as tot from A_invoice_master inm, A_invoice inv where inv.id_inv=inm.id_inv_m and  inm.dt_inv between  '"+stnddt+""+strtdt[q]+"' AND '"+stnddt+""+enddt[q]+"' "+addqr+"";	
				}
				//System.out.println("invt"+sql);
				//}
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
			int count=0;
			  JSONObject jobj2 = new JSONObject();
		    	
				   ResultSetMetaData metaData = rs.getMetaData();
				    int columns = metaData.getColumnCount();
				    while(rs.next())
				    {
				    	count=rs.getInt("count");
				    	jobj2.put(Month[q],count);
					    
			        }	
				  
			    	 jarr.put(jobj2);
			    	 //System.out.println(jobj2);	
					}
			jobj.put("data1", jarr);
					
					 String strt2 = year1.substring (0,4); int b=Integer.parseInt(strt2); b=b-1; String strt3=Integer.toString(b);
					 String endt2 = end1.substring (0,4); int c=Integer.parseInt(endt2); c=c-1; String endt3= Integer.toString(c);
					 
					 String stnddt1="";
						for(int q=0;q<=11;q++){

						if(q>=9) {
						stnddt1=endt3;
						}
						else{
						stnddt1=strt3;
						}
						String sql="";
						if(!UserType.equals("Super"))
				    	{
							
				        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"inv","inm", request);
					  sql="select count (*) as count, (case  WHEN  sum(gr_tot) is null then 0  else  sum(gr_tot) end) as tot from A_invoice_master inm, A_invoice inv where inv.id_inv=inm.id_inv_m and  inm.dt_inv between  '"+stnddt1+""+strtdt[q]+"' AND '"+stnddt1+""+enddt[q]+"' "+addqr+" "+tempQuery2+"";
				    	
				    	}
						else {
							sql="select count (*) as count, (case  WHEN  sum(gr_tot) is null then 0  else  sum(gr_tot) end) as tot from A_invoice_master inm, A_invoice inv where inv.id_inv=inm.id_inv_m and  inm.dt_inv between  '"+stnddt1+""+strtdt[q]+"' AND '"+stnddt1+""+enddt[q]+"' "+addqr+"";
						}
							// System.out.println("inv"+sql); 
					 ps=con.prepareStatement(sql); 
					  rs=ps.executeQuery();
					  int count=0; 
					  JSONObject jobj2 = new JSONObject();
					  
					  ResultSetMetaData metaData = rs.getMetaData(); 
					  int columns = metaData.getColumnCount(); 
					  while(rs.next()) { 
					  count=rs.getInt("count") ;
					  jobj2.put(Month[q],count);
					  
					  }
					  
					  jarr2.put(jobj2); 
					  //System.out.println(jobj2); 
					  }
					 
	jobj.put("data2", jarr2);
		//System.out.println("chanchal!!!!!!!=============!!!!!!!!!!"+jarr2);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}
	
	public JSONObject tranferDashboard(String id_finance, String type_repo,String id_dept, String UserType)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		  String year1="",end1="";
		  String tempqry="";
			if(!UserType.equals("Super")) {
				tempqry="and id_dept="+id_dept;
			}
		
		
		try
		{
		String tempQuery="";
		
				
		String strtdt[]={"-04-01","-05-01","-06-01","-07-01","-08-01","-09-01","-10-01","-11-01","-12-01","-01-01","-02-01","-03-01",};
		String enddt[]={"-04-30","-05-31","-06-30","-07-31","-08-31","-09-30","-10-31","-11-30","-12-31","-01-30","-02-28","-03-30",};
		String Month[]={"April","May","June","July","August","September","October","November","December","January","February","March",};
		
			
				
			
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		   LocalDateTime now = LocalDateTime.now();  
		   String data = dtf.format(now);
			String strt1 = data.substring (0,4);
			
			if(Integer.parseInt(strt1)%4 ==0) {
				enddt[10]="-02-29";
			}
			else{
				enddt[10]="-02-28";
			}
			
			
			
			for(int q=0;q<=11;q++){
				
				
				
				String sql=" select count (*) as count, (case  WHEN  sum(tran_count) is null then 0  else  sum(tran_count) end) as tot from A_Iut_History where (iut_approval=5   or iut_approval=4 or iut_approval=3) and dt_start_tran between  '"+strt1+""+strtdt[q]+"' AND '"+strt1+""+enddt[q]+"' ";	
					System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
			int count=0;
			  JSONObject jobj2 = new JSONObject();
		    	
				   ResultSetMetaData metaData = rs.getMetaData();
				    int columns = metaData.getColumnCount();
				    while(rs.next())
				    {
				    	count=rs.getInt("count")	;
				    	jobj2.put(Month[q]+"("+rs.getString("tot")+")",count);
					    
			        }	
				  
			    	 jarr.put(jobj2);
			    	 //System.out.println(jobj2);	
					}
	
	jobj.put("data1", jarr);
	//System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}
	public JSONObject poVendorDashboard(int id_emp_user)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		  String year1="",end1="";
		
		
		
		try
		{ 
		String tempQuery="";
		
			
			
				
		String strtdt[]={"-01-01","-02-01","-03-01","-04-01","-05-01","-06-01","-07-01","-08-01","-09-01","-10-01","-11-01","-12-01"};
		String enddt[]={"-01-31","-02-29","-03-31","-04-30","-05-31","-06-30","-07-31","-08-31","-09-30","-10-31","-11-30","-12-31"};
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		   LocalDateTime now = LocalDateTime.now();  
		   String data = dtf.format(now);
			String strt1 = data.substring (0,4);
			
			if(Integer.parseInt(strt1)%4 ==0) {
				enddt[1]="-02-29";
			}
			else{
				enddt[1]="-02-28";
			}
			
	for(int q=0;q<=11;q++){
		
		
		
		String sql=" select count (*) as count from P_Purchase_Order where dt_po between  '"+strt1+""+strtdt[q]+"' AND '"+strt1+""+enddt[q]+"' and id_ven='"+id_emp_user+"'";	
		//System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
	int count=0;
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs.next())
		    {
		    	count=rs.getInt("count")	;
		    	
	        }	
		    JSONObject jobj2 = new JSONObject();
	    	
	    	jobj2.put("count",count);
	    	 jarr.put(jobj2);
	    	 //System.out.println(jobj2);	
			}
	
	jobj.put("data", jarr);
	//System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}
	
	public JSONObject invoiceVendorDashboard(int id_emp_user)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		  String year1="",end1="";
		
		
		
		try
		{ 
		String tempQuery="";
		
			
			
				
		String strtdt[]={"-01-01","-02-01","-03-01","-04-01","-05-01","-06-01","-07-01","-08-01","-09-01","-10-01","-11-01","-12-01"};
		String enddt[]={"-01-31","-02-29","-03-31","-04-30","-05-31","-06-30","-07-31","-08-31","-09-30","-10-31","-11-30","-12-31"};
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		   LocalDateTime now = LocalDateTime.now();  
		   String data = dtf.format(now);
			String strt1 = data.substring (0,4);
			
			if(Integer.parseInt(strt1)%4 ==0) {
				enddt[1]="-02-29";
			}
			else{
				enddt[1]="-02-28";
			}
			
	for(int q=0;q<=11;q++){
		
		
		
		String sql=" select count (*) as count from A_Invoice_Master where dt_inv between  '"+strt1+""+strtdt[q]+"' AND '"+strt1+""+enddt[q]+"' and id_ven='"+id_emp_user+"'";	
		//System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
	int count=0;
		   ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs.next())
		    {
		    	count=rs.getInt("count")	;
		    	
	        }	
		    JSONObject jobj2 = new JSONObject();
	    	
	    	jobj2.put("count",count);
	    	 jarr.put(jobj2);
	    	 // System.out.println(jobj2);	
			}
	
	jobj.put("data", jarr);
	//System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}

	public JSONObject PendingReject()
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		  String year1="",end1="";
		
		
		
		try
		{
			String tempQuery="";
		String sql="select count(id_po) as comp from P_Purchase_Order where recv_invoice='2' ";	
		//System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		    if(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    		jobj2.put("comp",rs.getString("comp"));
		    	 jarr.put(jobj2);
		    	 		
	        }	
		   
		    sql="select count(id_po) as cancel from P_Purchase_Order where cancel='1' ";	
		    //System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			    if(rs.next())
			    {
			    	JSONObject jobj2 = new JSONObject();
			    		jobj2.put("cancel",rs.getString("cancel"));
			    	 jarr.put(jobj2);
			    	 		
		        }
		    
			    sql="select count(id_po) as approve from P_Purchase_Order where st_approv='Accepted' and approv_by !='0'";	
			    //System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
				    if(rs.next())
				    {
				    	JSONObject jobj2 = new JSONObject();
				    		jobj2.put("approve",rs.getString("approve"));
				    	 jarr.put(jobj2);
				    	 		
			        }
		    
				    sql="select count(id_po) as reject from P_Purchase_Order where st_approv='Rejected'";	
				    //System.out.println(sql);
					ps=con.prepareStatement(sql);
					rs=ps.executeQuery();
					
					    if(rs.next())
					    {
					    	JSONObject jobj2 = new JSONObject();
					    		jobj2.put("reject",rs.getString("reject"));
					    	 jarr.put(jobj2);
					    	 		
				        }
					    sql="select count(id_po) as pending from P_Purchase_Order where st_approv='Accepted' and approv_by='0'";	
					    //System.out.println(sql);
						ps=con.prepareStatement(sql);
						rs=ps.executeQuery();
						
						    if(rs.next())
						    {
						    	JSONObject jobj2 = new JSONObject();
						    		jobj2.put("pending",rs.getString("pending"));
						    	 jarr.put(jobj2);
						    	 		
					        }
	
	jobj.put("data", jarr);
	System.out.println(jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install."+e);
		}
		 
		return jobj;
	}
	




	public JSONObject DashCount(int id_ven1,String UserType, String std_finance, String end_finance,HttpServletRequest request)
	{
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		 DtoCommon dtoCommon = new DtoCommon();
		
		 try
			{
				String sql="";
				if(!UserType.equals("Super"))
		    	{
					//doubt
		        	//String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"poa","po", request);
					sql = "select count (*) as count, (case  WHEN  sum(quantity)  is null then 0 "
							+"else sum(quantity) end) as qty  from O_Assign_Lead l, O_Assign_Lead_Master lm where "
							+" l.status_deliver_ld=1 and l.status_returnto_str=0 and  "
							+"l.id_lead_m=lm.id_lead_m and typ_service='Rental' and lm.dt_ld between '"+std_finance+"' and '"+end_finance+"' ";
		    	}
				else {
					sql = "select count (*) as count, (case  WHEN  sum(quantity)  is null then 0 "
							+"else sum(quantity) end) as qty  from O_Assign_Lead l, O_Assign_Lead_Master lm where "
							+" l.status_deliver_ld=1 and l.status_returnto_str=0 and  "
							+"l.id_lead_m=lm.id_lead_m and typ_service='Rental' and lm.dt_ld between '"+std_finance+"' and '"+end_finance+"' ";
				}
				
			 System.out.println("Rental_Product"+sql);
								ps=con.prepareStatement(sql);
								rs=ps.executeQuery();
								JSONObject jobj2 = new JSONObject();
								    if(rs.next())
								    {
								     
								    	   
								    	   jobj2.put("Rental_Product",rs.getString("qty"));
									    	 jarr.put(jobj2);
									    	 		
								       
								       
							        } 

								    sql= "select count (*) as count,(case  WHEN  sum(quantity)  is null then 0  else "
								         +"sum(quantity) end) as qty1 from O_Assign_Lead l,O_Assign_Lead_Master lm where "
								    	+"(lm.state ='Inprogress' and lm.tagto_me_state ='Inprogress' or  lm.state ='Closed' and "
								    	+"lm.tagto_me_state ='Closed' )and    l.status_returnto_str=0 and lm.status_deliver_ld=1 "
								    	+"and l.id_lead_m=lm.id_lead_m and typ_service='Sale' and dt_ld between '"+std_finance+"' and '"+end_finance+"' ";
								    System.out.println("Total_sale"+sql);
									ps=con.prepareStatement(sql);
									rs=ps.executeQuery();
									
									    if(rs.next())
									    {
									    	 
									    		jobj2.put("Total_sale",rs.getString("qty1"));
									    	 jarr.put(jobj2);
									    	 		
								        }
									    
									    sql="select count (*) as count, (case  WHEN  sum(tot_aval_qty)  is null then 0  else sum(tot_aval_qty) end) as qtystore from S_Store_Qty st ," + 
									    		"M_Subloc ms where  st.id_sloc= ms.id_sloc ";
									    			
										//System.out.println("ticket query iss"  +sql);
									    //System.out.println("In_store_Product"+sql);
										ps=con.prepareStatement(sql);
										rs=ps.executeQuery();
										
										    if(rs.next())
										    {
										    	 
										    		jobj2.put("In_store_Product",rs.getString("qtystore"));
										    	 jarr.put(jobj2);
										    	 		
									        }  
										    if(!UserType.equals("Super"))
									    	{
												
									        	//String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
										    sql="select count(distinct sm.id_inventory_m) as invoiceinventory from S_Inventory_Master sm,S_Inventory si"  
										    	+" where sm.id_inventory_m=si.id_inventory_m and sm.dt_inv between '"+std_finance+"' and '"+end_finance+"' ";
									    	}
										    else {
										    	 sql="select count(distinct sm.id_inventory_m) as invoiceinventory from S_Inventory_Master sm,S_Inventory si"  
													    	+" where sm.id_inventory_m=si.id_inventory_m and sm.dt_inv between '"+std_finance+"' and '"+end_finance+"' ";
										    }
										  // System.out.println("invoicepending" +  sql);
											ps=con.prepareStatement(sql);
											rs=ps.executeQuery();
											
											    if(rs.next())
											    {
											    
											    		jobj2.put("invoicepending",rs.getString("invoiceinventory"));
											    	 jarr.put(jobj2);
											    	 	
										        } 
											    if(!UserType.equals("Super"))
										    	{
													
										        	//String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
											    	sql="select count(distinct ledm.id_cr_inv_m_hist) as crtinvoice from O_create_inv__msater_history ledm, " 
											    			+"O_create_invoice_history led where ledm.id_cr_inv_m_hist=ledm.id_cr_inv_m_hist and ledm.dt_inv between '"+std_finance+"' and '"+end_finance+"' ";	
											    		
										    	}
											    else {
											    	sql="select count(distinct ledm.id_cr_inv_m_hist) as crtinvoice from O_create_inv__msater_history ledm, " 
											    			+"O_create_invoice_history led where ledm.id_cr_inv_m_hist=ledm.id_cr_inv_m_hist and ledm.dt_inv between '"+std_finance+"' and '"+end_finance+"' ";											    }
											  System.out.println("create_inv"+ sql);
												ps=con.prepareStatement(sql);
												rs=ps.executeQuery();
												
												    if(rs.next())
												    {
												     
												    		jobj2.put("create_inv",rs.getString("crtinvoice"));
												    	 jarr.put(jobj2);
												    	  		
											        }
//												    if(!UserType.equals("Super"))
//											    	{
//														
//											        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
//												    sql="select count(device_status) as damagedAsset from A_Ware_House wh , A_Invoice_Master inv , M_Vendor v ,M_Model m where  inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and m.id_model=wh.id_model  and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr') and wh.typ_asst!='accessories' "+tempQuery2+"";	
//											    	}
//												    else {
//												    	sql="select count(device_status) as damagedAsset from A_Ware_House wh , A_Invoice_Master inv , M_Vendor v ,M_Model m where  inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and m.id_model=wh.id_model  and (device_status='physical_dmg_mjr' or device_status='physical_dmg_mnr') and wh.typ_asst!='accessories'";	
//												    }
//												    //System.out.println(sql);
//													ps=con.prepareStatement(sql);
//													rs=ps.executeQuery();
//													
//													    if(rs.next())
//													    {
//													     
//													    		jobj2.put("damagedAsset",rs.getString("damagedAsset"));
//													    	 jarr.put(jobj2);
//													    	  		
//												        }
//													    if(!UserType.equals("Super"))
//												    	{
//															
//												        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
//												        		 sql="select count(typ_asst) as totalaccess from A_ware_house wh where wh.typ_asst='accessories' "+tempQuery2+"";	
//												    	}
//													    else {
//													    	sql="select count(typ_asst) as totalaccess from A_ware_house wh where wh.typ_asst='accessories'";	
//													    }
//													    //System.out.println(sql);
//														ps=con.prepareStatement(sql);
//														rs=ps.executeQuery();
//														
//														    if(rs.next())
//														    {
//														 
//														    		jobj2.put("totalaccess",rs.getString("totalaccess"));
//														    	 jarr.put(jobj2);
//														    	  		
//													        }
//														    if(!UserType.equals("Super"))
//													    	{
//																
//													        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
//														    sql="select count(typ_asst) as availAccess from A_ware_house wh where wh.typ_asst='accessories' and parent=0 "+tempQuery2+"";	
//													    	}
//														    else {
//														    	sql="select count(typ_asst) as availAccess from A_ware_house wh where wh.typ_asst='accessories' and parent=0 ";	
//													    	}
//														    //System.out.println(sql);
//															ps=con.prepareStatement(sql);
//															rs=ps.executeQuery();
//															
//															    if(rs.next())
//															    {
//															 
//															    		jobj2.put("availAccess",rs.getString("availAccess"));
//															    	 jarr.put(jobj2);
//															    	  		
//														        }
//															    if(!UserType.equals("Super"))
//														    	{
//																	
//														        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
//															    sql="select count(id_wh) as linkacc from A_ware_house wh where typ_asst='accessories' and parent!=0 "+tempQuery2+"";	
//														    	}
//															    else {
//															    	sql="select count(id_wh) as linkacc from A_ware_house wh where typ_asst='accessories' and parent!=0";	
//															    }
//															    // System.out.println(sql);
//																ps=con.prepareStatement(sql);
//																rs=ps.executeQuery();
//																
//																    if(rs.next())
//																    {
//																     
//																    		jobj2.put("linkacc",rs.getString("linkacc"));
//																    	 jarr.put(jobj2);
//																    	  		
//															        }
																   
												    
					    

		jobj.put("data", jarr);
		System.out.println("chaznchal"+jarr);
			}
			catch(Exception e)
			{
				System.out.println("sql error in A_Install chaznchal."+e);
			}
		 
		return jobj;
	}
}

