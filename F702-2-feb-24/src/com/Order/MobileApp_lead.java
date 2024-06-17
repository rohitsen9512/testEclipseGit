package com.Order;

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


public class MobileApp_lead extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	Connection con = null;
	Statement stmt = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String UserName = (String)session.getAttribute("UserName");

		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		Enumeration elemet = request.getParameterNames();
		HashMap<String, String> map =new HashMap<String,String>();
		DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		JSONObject json=new JSONObject();
		String paramName = "";
		String paramValues = "";
		try
		{

			while(elemet.hasMoreElements())
			{

				paramName = (String)elemet.nextElement();
				String[] parts = paramName.split("_");
				paramValues = request.getParameter(paramName);
				if(paramName.equals("approve_dt"))
				{
					paramValues = request.getParameter(paramName);
					System.out.println(paramValues);
					Date d = userDateFormat.parse(paramValues);  
					map.put(paramName,dateFormatNeeded.format(d));
				}
				else if(parts[0].equals("dt") && !paramValues.equals(""))
				{
					paramValues = request.getParameter(paramName);
					System.out.println(paramValues);
					Date d = userDateFormat.parse(paramValues);  
					map.put(paramName,dateFormatNeeded.format(d));

				}
				else
				{
					paramValues = request.getParameter(paramName);
					paramValues=paramValues.replace("'", "''");
					map.put(paramName, paramValues);
				}


			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		String action = "", temp1 = "", id = "0", upload_inv = "", lead_no = "", leadid = "", dt_to = "", dt_frm = "",
				state="",tagto_me_state="" ,state_approve="",value = "", dt_return_str="",deliver_time="",ColName = "", id_lead_m = "00";
		String EditExtensionReturn="",delivery_man_id="",latitude="",longitude="",time_stamp="",pstn_ct="";

		
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("upload_inv") != null) {
			upload_inv = request.getParameter("upload_inv");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("id_lead_m") != null) {
			id_lead_m = request.getParameter("id_lead_m");
		}
		if (request.getParameter("lead_no") != null) {
			lead_no = request.getParameter("lead_no");
		}
		if (request.getParameter("leadid") != null) {
			leadid = request.getParameter("leadid");
		}
		if (request.getParameter("dt_frm") != null) {
			dt_frm = request.getParameter("dt_frm");
		}
		if (request.getParameter("dt_to") != null) {
			dt_to = request.getParameter("dt_to");
		}
		if (request.getParameter("state") != null) {
			state = request.getParameter("state");
		}
		if (request.getParameter("tagto_me_state") != null) {
			tagto_me_state = request.getParameter("tagto_me_state");
		}
		if (request.getParameter("state_aprvdelvr") != null) {
			state_approve = request.getParameter("state_aprvdelvr");
			System.out.println("state"+state_approve);
		}
		if (request.getParameter("dt_return_str") != null) {
			dt_return_str = request.getParameter("dt_return_str");
		}
		if (request.getParameter("deliver_time") != null) {
			deliver_time = request.getParameter("deliver_time");
		}
		if (request.getParameter("delivery_man_id") != null) {
			delivery_man_id = request.getParameter("delivery_man_id");
		}
		if (request.getParameter("latitude") != null) {
			latitude = request.getParameter("latitude");
		}
		if (request.getParameter("longitude") != null) {
			longitude = request.getParameter("longitude");
		}
		if (request.getParameter("time_stamp") != null) {
			time_stamp = request.getParameter("time_stamp");
	}
		if (request.getParameter("value") != null) {
			value = request.getParameter("value");
		}
		if (request.getParameter("ColName") != null) {
			ColName = request.getParameter("ColName");
		}
		if (request.getParameter("pstn_ct") != null) {
			pstn_ct = request.getParameter("pstn_ct");
		}


		
		String chk[] = request.getParameterValues("retrunstr");

		try {
			Date currDate = new Date();
			if (dt_frm.equals("")) {
				dt_frm = "1990-01-01";
			} else {
				dt_frm = dateFormatNeeded.format(userDateFormat.parse(dt_frm));
			}
			if (dt_to.equals("")) {
				dt_to = dateFormatNeeded.format(currDate);
			} else {
				dt_to = dateFormatNeeded.format(userDateFormat.parse(dt_to));
			}
			if(!dt_return_str.equals(""))
			{
				dt_return_str = dateFormatNeeded.format(userDateFormat.parse(dt_return_str));
			}
			String tempQuery = "";
//			int id_emp_user = (int) session.getAttribute("id_emp_user");
//
//			String UserType = (String) session.getAttribute("UserType");
			


			con = Common.GetConnection(request);
			switch (action) {

            case "DipplayCutomerOrderHistory":
				jobj = DipplayCutomerOrderHistory(pstn_ct);
				break;

			case "Diplaytrcakinglistview": 
				jobj = Diplaytrcakinglistview(pstn_ct);	
				break;
			case "DiplaylocTrackdetails": 
				jobj = DiplaylocTrackdetails(id_lead_m);	
				break;
			}
			
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println("kk"+jobj);
		} catch (Exception e) {
			System.out.println(e+"Error in MobileApp.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

public JSONObject DipplayCutomerOrderHistory( String pstn_ct) {
					JSONObject jobj = new JSONObject();
					JSONArray jarr = new JSONArray();
					try {
						String sql = "";

						
							sql="select id_customer,m.id_lead_m,nm_customer,pwd_customer, (replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtldcreate,"
							+" ledm.lead_no,ledm.pstn_ct,ledm.pstn_email,ledm.tag_to_emp,emp.nm_emp as delivery_boy ,emp.cont_no as delivry_boy_contact,"
							+" ledm.id_loc,ledm.id_sloc,l.nm_loc,s.nm_subl ,ledm.address ,ledm.status_deliver_ld   from M_Customer m,O_Assign_Lead_Master ledm ," 
							+" M_Emp_User emp ,M_loc l ,M_Subloc s where ledm.id_lead_m=m.id_lead_m and ledm.tag_to_emp = emp.id_emp_user  and "
							+" ledm.id_loc=l.id_loc and ledm.id_sloc=s.id_sloc  and ledm.pstn_ct='"+pstn_ct+"'    and ledm.status_deliver_ld=1";


						System.out.println("history"+sql);
						ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						ResultSetMetaData metaData = rs.getMetaData();
						int columns = metaData.getColumnCount();
						while (rs.next()) {
							JSONObject jobj2 = new JSONObject();
							for (int i = 1; i <= columns; i++) {
								String name = metaData.getColumnName(i);
								jobj2.put(name, rs.getString(name));
							}
							jarr.put(jobj2);
						}
						jobj.put("data", jarr);
					} catch (Exception e) {
						System.out.println(e+"sql error in Mobile App.");
					}
					System.out.println("history"+jobj);
					return jobj;
				}
	
	



	

public JSONObject Diplaytrcakinglistview( String  pstn_ct) {
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	try {
		String sql = "";


		sql="select id_customer,m.id_lead_m,nm_customer,pwd_customer, (replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtldcreate,"
				+" ledm.lead_no,ledm.pstn_ct,ledm.pstn_email,ledm.tag_to_emp,emp.nm_emp as delivery_boy ,emp.cont_no as delivry_boy_contact,"
				+" ledm.id_loc,ledm.id_sloc,l.nm_loc,s.nm_subl ,ledm.address ,ledm.status_deliver_ld   from M_Customer m,O_Assign_Lead_Master ledm ," 
				+" M_Emp_User emp ,M_loc l ,M_Subloc s where ledm.id_lead_m=m.id_lead_m and ledm.tag_to_emp = emp.id_emp_user  and "
				+" ledm.id_loc=l.id_loc and ledm.id_sloc=s.id_sloc  and ledm.pstn_ct='"+pstn_ct+"'  and ledm.status_deliver_ld=0";

		

		System.out.println("ppst"+sql);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int columns = metaData.getColumnCount();
		while (rs.next()) {
			JSONObject jobj2 = new JSONObject();
			for (int i = 1; i <= columns; i++) {
				String name = metaData.getColumnName(i);
				jobj2.put(name, rs.getString(name));
			}
			jarr.put(jobj2);
		}
		jobj.put("data", jarr);
	} catch (Exception e) {
		System.out.println(e+"sql error in MobileApp.");
	}
	System.out.println("Mobile"+jobj);
	return jobj;
}



public JSONObject DiplaylocTrackdetails(String id_lead_m) {
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	try {
		String sql = "";

		
			sql="	select * from  delivery_man_locations d,O_Assign_Lead_Master led_m where  d.id_lead_m=led_m.id_lead_m and d.id_lead_m='"+id_lead_m+"'";


		System.out.println("history"+sql);
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int columns = metaData.getColumnCount();
		while (rs.next()) {
			JSONObject jobj2 = new JSONObject();
			for (int i = 1; i <= columns; i++) {
				String name = metaData.getColumnName(i);
				jobj2.put(name, rs.getString(name));
			}
			jarr.put(jobj2);
		}
		jobj.put("data", jarr);
	} catch (Exception e) {
		System.out.println(e+"sql error in Mobile App.");
	}
	System.out.println("history"+jobj);
	return jobj;
}









	
}







