package com.auction;

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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
import com.Common.BidTask;


import dto.Common.DtoCommon;

public class StartAuction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	Connection con = null;
	Statement stmt = null;
	Statement stmt2 = null;
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String UserName = (String) session.getAttribute("UserName");
		String User_Type = (String) session.getAttribute("UserType");
		String logged=(String) session.getAttribute("log_name");
		int LastBiderVen =(int) session.getAttribute("id_emp_user");
		
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();

		HashMap<String, String> map = new HashMap<String, String>();
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
		
		String action = "",for_rate="",id_id_ind="",dt_rate_cont_valid="",word="", id = "0", confirm = "1", dt_to = "",id_ind="", dt_frm = "", ColName = "", no_req_val = "",dt_req="";
		if (request.getParameter("id_id_ind") != null) {
			id_id_ind = request.getParameter("id_id_ind");
		}
		if (request.getParameter("for_rate") != null) {
			for_rate = request.getParameter("for_rate");
		}
		if (request.getParameter("dt_rate_cont_valid") != null) {
			dt_rate_cont_valid = request.getParameter("dt_rate_cont_valid");
		}
		if (request.getParameter("id_ind") != null) {
			id_ind = request.getParameter("id_ind");
		}
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("no_req_val") != null) {
			no_req_val = request.getParameter("no_req_val");
		}
		if (request.getParameter("confirm") != null) {
			confirm = request.getParameter("confirm");
		}
		if (request.getParameter("dt_frm") != null) {
			dt_frm = request.getParameter("dt_frm");
		}
		if (request.getParameter("dt_to") != null) {
			dt_to = request.getParameter("dt_to");
		}
		if (request.getParameter("ColName") != null) {
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("dt_req") !=null)
		{
			dt_req = request.getParameter("dt_req");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		try {
			int id_emp_user = (int) session.getAttribute("id_emp_user");
			Date currDate = new Date();
			if(!dt_req.equals(""))
			{
				dt_req = dateFormatNeeded.format(userDateFormat.parse(dt_req));
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
			con = Common.GetConnection(request);

			switch (action) {
			
			case "userbid":
				String bid_amount =request.getParameter("bid_amount");
				String tndr_num =request.getParameter("tndr_num");
				stmt =con.createStatement();
				 System.out.println("update  B_start_Auction    set submitted_bid_amount ='"+bid_amount+"' ,LastBiderVen ='"+LastBiderVen+"'   where inq_tendr_num='"+tndr_num+"'");
					
				try
					{
					stmt.executeUpdate("update  B_start_Auction  set submitted_bid_amount ='"+bid_amount+"' , LastBiderVen ='"+LastBiderVen+"'  where inq_tendr_num='"+tndr_num+"' ");
					 System.out.println("insert into  AuctionBidder ( submitted_bid_amount,id_ven,inq_tendr_num) values ('"+bid_amount+"' , '"+LastBiderVen+"' ,'"+tndr_num+"' )");
			 			
					stmt.executeUpdate("insert into  AuctionBidder ( submitted_bid_amount,id_ven,inq_tendr_num) values ('"+bid_amount+"' , '"+LastBiderVen+"' ,'"+tndr_num+"' )");
		 			
					}
				 catch(Exception e)
					{
					 
						System.out.println("Error "+e.toString());	
					}
					
				
				break;
			case "cancelbid":
				String tndr_num1 =request.getParameter("tndr_num");
				stmt =con.createStatement();
				 System.out.println("update  B_start_Auction  set st_bid ='0'  where inq_tendr_num='"+tndr_num1+"'");
				 try
					{
					stmt.executeUpdate("update  B_start_Auction  set st_bid ='0' where inq_tendr_num='"+tndr_num1+"' ");
 			
				
					}
				 catch(Exception e)
					{
					 
						System.out.println("Error "+e.toString());	
					}
					
				
				break;
		           
			case "Display":
				jobj = DisplayQuotation(id, dt_frm, dt_to, id_emp_user,word,User_Type,id_id_ind,dt_rate_cont_valid,for_rate);
				break;
				
			case "startedAuction":
				jobj = DisplaystartedAuction();
				break;
				
			case "ModifyDisplay":
				jobj = DisplayQuotationModify(id, dt_frm, dt_to, id_emp_user,word,User_Type);
				break;
		
			case "CheckDate":
            	jobj = CheckRequestedDate(dt_req,id_ind);	
                break;
			case "Save":
				//jobj = SaveQuotation(map, id_emp_user, no_ind, id_req);
				String id_req_quot="",bd_date="",inq_tendr_num="",hr="",secnd="",mint="",min_amount_bid="";
				int j=0;
				try
				{
					String dt_end_quot="";
					stmt =con.createStatement();
					stmt2 = con.createStatement();
					String id_req[] = request.getParameterValues("id_req");	
					String id_ind_asst[] = request.getParameterValues("id_ind_asst");
					String id_prod[] = request.getParameterValues("id_prod");
					String qty[] = request.getParameterValues("qty");
					String un_prc[] = request.getParameterValues("un_prc");
					
					String tot_prc[] = request.getParameterValues("tot_prc");
					
					String id_ven[] = request.getParameterValues("id_ven");
					if(request.getParameter("bd_date") !=null){
						bd_date =request.getParameter("bd_date");
						bd_date = dateFormatNeeded.format(userDateFormat.parse(bd_date));
					}
				
					inq_tendr_num=request.getParameter("inq_tendr_num");
					String remarkss =request.getParameter("remarkss");
					String auc_type =request.getParameter("auc_type");
					
					min_amount_bid=request.getParameter("min_amount_bid");
					hr=request.getParameter("hr");
					mint=request.getParameter("mint");
					secnd=request.getParameter("secnd");
					
					
					//bidding Start from here..........
					
					double hrd=Double.parseDouble(hr);
					double mintd=Double.parseDouble(mint);
					double secndd=Double.parseDouble(secnd);
					double sss=0;
					if(secndd!=0) {
						sss=secndd/60;
						
					}
					
					int calculatebidtime=(int)(hrd*60 + mintd +sss);
					long Autocancletime=calculatebidtime*60*1000;
					Common.cancleBid=Autocancletime;
					Common.tendor_name=inq_tendr_num;
					
					new BidTask().startTask();
				 	
					LocalTime fiveMinutesLater = LocalTime.now().plusMinutes(calculatebidtime);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
					String timeString = fiveMinutesLater.format(formatter);
					
					LocalTime bit_time_sub = LocalTime.now();
					String bit_time_submi = fiveMinutesLater.format(formatter);
				
					
        			for(int i=0;i<id_ven.length;i++)
        			{
        				System.out.println("insert into B_start_Auction(auc_type,id_auc,Last_Bid_Amount,bit_time_submi,start_time,calculatebidtime,min_amount_bid,add_by,id_ven,bd_date,inq_tendr_num,remarkss)values('"+auc_type+"','"+id_req[0]+"','"+min_amount_bid+"','"+bit_time_submi+"','"+timeString+"','"+calculatebidtime+"','"+min_amount_bid+"' ,'"+id_emp_user+"','"+id_ven[i]+"','"+bd_date+"','"+inq_tendr_num+"','"+remarkss+"')");
            			stmt.executeUpdate("insert into B_start_Auction(auc_type,id_auc,Last_Bid_Amount,bit_time_submi,start_time,calculatebidtime,min_amount_bid,add_by,id_ven,bd_date,inq_tendr_num,remarkss)values('"+auc_type+"','"+id_req[0]+"','"+min_amount_bid+"','"+bit_time_submi+"','"+timeString+"','"+calculatebidtime+"','"+min_amount_bid+"' ,'"+id_emp_user+"','"+id_ven[i]+"','"+bd_date+"','"+inq_tendr_num+"','"+remarkss+"')");
        				rs=stmt.executeQuery("select max(id_start_bid) from B_start_Auction");
        				if(rs.next())
        				{
        					id_req_quot=rs.getString(1);
        				}
        				
        				for(int k=0;k<id_prod.length;k++){

        					
        					
        					
        						System.out.println("insert into B_Start_Auction_Asset(id_auc_asset,id_start_bid,qty,un_prc,tot_prc,id_prod) values('"+id_ind_asst[k]+"','"+id_req_quot+"',"+qty[k]+",'"+un_prc[k]+"','"+tot_prc[k]+"','"+id_prod[k]+"')");
        						
        						stmt2.executeUpdate("insert into B_Start_Auction_Asset(id_auc_asset,id_start_bid,qty,un_prc,tot_prc,id_prod) values('"+id_ind_asst[k]+"','"+id_req_quot+"', "+qty[k]+",'"+un_prc[k]+"','"+tot_prc[k]+"','"+id_prod[k]+"')");
        					
        				}
        				
        				
        				
        				
        			}
        			//System.out.println("UPDATE P_Create_Indent SET st_ind='Yes' WHERE no_ind='"+no_ind+"'");
        		//stmt.executeUpdate("UPDATE P_Create_Indent SET st_ind='Yes' WHERE no_ind='"+no_ind+"'");
        			j=1;
        		  
		      
				}
				catch(Exception e)
				{
					j=0;
					System.out.println("Error "+e.toString());	
				}
				
				
    			jobj.put("data",j);
    			
				
				break;

			}

			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());

		} catch (Exception e) {
			System.out.println("Error in Create_Indent.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}
	
	
	 public void cancelbid(String tndr_num1)
		{
			
		System.out.println("Pritesh KUmar Shanu");
		
			 System.out.println("update  B_start_Auction  set st_bid ='0'  where inq_tendr_num='"+tndr_num1+"'");
			 try
				{
					stmt =con.createStatement();
				stmt.executeUpdate("update  B_start_Auction  set st_bid ='0' where inq_tendr_num='"+tndr_num1+"' ");
				
					
				}
			 catch(Exception e)
				{
				 
					System.out.println("cancelbid "+e.toString());	
				}
			
			
		}

	
	 
	public JSONObject CheckRequestedDate(String dt_req,String id_ind)
	{
		JSONObject json=new JSONObject();
		
		String query = "select to_char(to_date(dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_ind from P_Create_Indent where dt_ind >'"+dt_req+"' and id_ind='"+id_ind+"'";
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				
				json.put("data",0);
				json.put("dt_ind",rs.getString(1));
			}
			else
			{
				json.put("data",1);
				
			}
		}
			
		catch (Exception e)
		{
			
			System.out.println(e.toString());
		}
		return json;
	}

	public JSONObject DisplayQuotation(String no_ind, String dt_frm, String dt_to,int id_emp_user, String word,String User_Type,String id_id_ind,String dt_rate_cont_valid ,String for_rate) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		
		

		try {
			
			String tempSql ="";
			if(!word.isEmpty())
			 tempSql =" and (REGEXP_LIKE(no_ind,'"+word+"','i') or REGEXP_LIKE(dt_ind,'"+word+"','i') or REGEXP_LIKE(nm_emp,'"+word+"','i') )";
			System.out.println(for_rate);			
if(no_ind.equals("0"))
{
	
	
		
		if(User_Type.equals("SUPER") || User_Type.equals("PAPPRV") ){
			sql ="select (replace(convert(NVARCHAR, dt_auc, 103), ' ', '-')) as dt_auc,grand_tot,auc_name,id_auc  from b_auction  where st_auc_create='0'    ";
	}
		else{
			sql = "select ci.*,(replace(convert(NVARCHAR, dt_ind, 103), ' ', '-')) as  dtInd,nm_emp from P_Create_Indent ci,M_Emp_User emp where st_ind='No'  and emp.id_emp_user=ci.ind_add_by   "+tempSql+"  order by ci.dt_ind DESC";

		
	}
}
else
{
	sql=" select id_auc_asset,nm_model,qty,un_prc,tot_prc,id_prod,(replace(convert(NVARCHAR, dt_auc, 103), ' ', '-')) as dt_auc,grand_tot,  "+
	"  auc_name,ba.id_auc  from b_auction ba,b_auction_asset baa  "+
	"   ,m_model mm where ba.id_auc=baa.id_auc and baa.id_prod=mm.id_model  and ba.id_auc='"+no_ind+"'";
	
	
	
	
}
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
				//System.out.println(jarr);
				
				
			}
			
		
			jobj.put("data", jarr);
			
		
				
				
		} catch (Exception e) {
			System.out.println("sql error in Create_Indent."+e.toString());
		}

		return jobj;

	}
	
	
	public JSONObject DisplaystartedAuction( ) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		
		String sql6 = "";
		ResultSet rs6 = null;
		PreparedStatement ps6 = null;

		try {
	
		
			

		    sql="select distinct auc_type,bit_time_submi,Submitted_Bid_Amount,Last_Bid_Amount,inq_tendr_num,bd_date,min_amount_bid,\r\n" + 
		                "min_amount_bid,calculatebidtime,start_time,nm_model,cd_model,qty,un_prc,tot_prc from \r\n" + 
		                 "B_start_Auction pq ,B_Start_Auction_Asset pa,m_model pc  where pq.id_start_bid=pa.id_start_bid\r\n" + 
		                              " and pc.id_model=pa.id_prod and st_bid='1'\r\n" ;
		

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
				//System.out.println(jarr);
			}
			
		
			jobj.put("data", jarr);
			
				
				
		} catch (Exception e) {
			System.out.println("sql error in Create_Indent."+e.toString());
		}

		return jobj;

	}

	public JSONObject DisplayQuotationModify(String no_ind, String dt_frm, String dt_to,int id_emp_user, String word ,String User_Type) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";
		try {
			Date currDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (dt_frm.equals("")) {
				dt_frm = "1990-01-01";
			}

			if (dt_to.equals("")) {
				dt_to = sdf.format(currDate);
			}
			String tempSql ="";
			if(!word.isEmpty())
			 tempSql =" and (REGEXP_LIKE(no_ind,'"+word+"','i') or REGEXP_LIKE(dt_ind,'"+word+"','i') or REGEXP_LIKE(nm_emp,'"+word+"','i') )";
						
if(no_ind.equals("0"))
{
	if(User_Type.equals("SUPER") || User_Type.equals("PAPPRV") ){
			sql = "select distinct no_ind,to_char(to_date(dt_req_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_req_quot,INQ_TENDR_NUM,nm_emp from P_REQUEST_QUOTATION pq,M_EMP_USER emp where pq.ADD_BY=emp.ID_EMP_USER and pq.ID_APPRV='No' "+tempSql+" order by dt_req_quot desc ";
	}
	else{
		sql = "select distinct no_ind,to_char(to_date(dt_req_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_req_quot,INQ_TENDR_NUM,nm_emp from P_REQUEST_QUOTATION pq,M_EMP_USER emp where pq.ADD_BY=emp.ID_EMP_USER and pq.ID_APPRV='No' "+tempSql+"  order by dt_req_quot desc ";

	}
	}
else
{
	sql = "select ci.*,to_char(to_date(ci.dt_ind,'YYYY-MM-DD'),'DD/MM/YYYY') as dtInd,nm_assetdiv,nm_s_assetdiv,nm_prod,cd_prod,ia.mfr,ia.description,ia.qty,ia.id_req,ia.id_ind_asst,ia.id_prod,uom from P_Create_Indent ci,P_Indent_Asset ia,M_Asset_Div ad,M_Subasset_Div sad,M_Prod_Cart pc "+
			" where ci.no_ind=ia.no_ind and pc.id_grp=ad.id_assetdiv and pc.id_sgrp=sad.id_s_assetdiv and ia.id_prod=pc.id_prod "+
			" and ci.no_ind='"+no_ind+"' order by ci.dt_ind DESC";	
}
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
			String dt_req_quot="",inq_tendr_num="",time_of_sub="",remarkss="",dt_end_quot="";
			if(!no_ind.equals("0"))
			{
						sql = "select distinct remarkss,to_char(to_date(dt_end_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_end_quot, no_ind,to_char(to_date(dt_req_quot,'YYYY-MM-DD'),'DD/MM/YYYY') as dt_req_quot,time_of_sub,inq_tendr_num,nm_emp from P_REQUEST_QUOTATION pq,M_EMP_USER emp where pq.ADD_BY=emp.ID_EMP_USER and pq.ID_APPRV='No' and no_ind='"+no_ind+"'";
						System.out.println(sql);
						ps = con.prepareStatement(sql);
						rs1 = ps.executeQuery();
                       if(rs1.next()){
                    	   dt_req_quot=rs1.getString("dt_req_quot");
                    	   inq_tendr_num=rs1.getString("inq_tendr_num");
                    	   time_of_sub=rs1.getString("time_of_sub");
                    	   remarkss=rs1.getString("remarkss");
                    	   dt_end_quot=rs1.getString("dt_end_quot");
	     System.out.println(rs1.getString("dt_req_quot"));
                         }
			
                       jobj.put("dt_req_quot", dt_req_quot);
                       jobj.put("inq_tendr_num", inq_tendr_num);
                       jobj.put("time_of_sub", time_of_sub);
                       jobj.put("remarkss", remarkss);
                       jobj.put("dt_end_quot", dt_end_quot);
			
			
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

}
