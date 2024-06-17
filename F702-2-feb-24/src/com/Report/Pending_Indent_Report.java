package com.Report;

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

public class Pending_Indent_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	PreparedStatement ps = null;
	Connection con = null;
	Statement stmt = null;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String UserName = (String) session.getAttribute("UserName");
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
		

		String action = "", id = "0", id_req = "", confirm = "No", dt_to = "", no_ind = "", dt_frm = "", value = "", ColName = "", no_req_val = "";

		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("no_ind") != null) {
			no_ind = request.getParameter("no_ind");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("no_req_val") != null) {
			no_req_val = request.getParameter("no_req_val");
		}
		if (request.getParameter("id_req") != null) {
			id_req = request.getParameter("id_req");
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
		if (request.getParameter("value") != null) {
			value = request.getParameter("value");

		}
		if (request.getParameter("ColName") != null) {
			ColName = request.getParameter("ColName");
		}
		try {
			int id_emp_user = (int) session.getAttribute("id_emp_user");
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
			con = Common.GetConnection(request);

			switch (action) {

			case "Display":
				jobj = DisplayIndent(dt_frm, dt_to,id_emp_user);
				break;

			

			}

			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());

		} catch (Exception e) {
			System.out.println("Error in Delete_Indent.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}


	public JSONObject DisplayIndent(String dt_frm, String dt_to,
			int id) {
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
			sql = "select distinct(req.no_req),(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,ci.no_ind,(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-')) as dt_ind,emp.nm_emp as requestor,emp1.nm_emp as requestorFor from "+
					" P_Create_Indent ci,P_Indent_Asset ia,P_Request_Asset ra ,P_request req,M_Emp_User emp,M_Emp_User emp1 "+
					" where ia.no_ind=ci.no_ind and ra.id_req=ia.id_req and req.id_req=ra.id_req and req.add_by=emp.id_emp_user and req.req_by=emp1.id_emp_user "
					+ "and dt_ind >=(replace(convert(NVARCHAR, '"
						+ dt_frm
						+ "', 106), ' ', '-')) and dt_ind <= (replace(convert(NVARCHAR, '"
						+ dt_to + "', 106), ' ', '-'))";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next())
			{
			
				sql="select po.no_ind from P_Create_Indent ci,P_Purchase_Order po where po.no_ind='"+rs.getString(3)+"'";
				ps = con.prepareStatement(sql);
				rs1 = ps.executeQuery();
				if(rs1.next())
				{
					sql = "select distinct(req.no_req),(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,ci.no_ind,(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-')) as dt_ind,emp.nm_emp as requestor,emp1.nm_emp as requestorFor,po.st_approv from "+
							" P_Create_Indent ci,P_Indent_Asset ia,P_Request_Asset ra ,P_request req,M_Emp_User emp,M_Emp_User emp1,P_Purchase_Order po "+
							" where po.no_ind = ci.no_ind and ia.no_ind=ci.no_ind and ra.id_req=ia.id_req and req.id_req=ra.id_req and req.add_by=emp.id_emp_user and req.req_by=emp1.id_emp_user "
							+ "and dt_ind >=(replace(convert(NVARCHAR, '"
								+ dt_frm
								+ "', 106), ' ', '-')) and dt_ind <= (replace(convert(NVARCHAR, '"
								+ dt_to + "', 106), ' ', '-')) and ci.no_ind = '"+rs.getString(3)+"'";
				}
				else
				{
				sql = "select distinct(req.no_req),(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,ci.no_ind,(replace(convert(NVARCHAR, ci.dt_ind, 103), ' ', '-')) as dt_ind,emp.nm_emp as requestor,emp1.nm_emp as requestorFor,st_approv='Not Created' from "+
						" P_Create_Indent ci,P_Indent_Asset ia,P_Request_Asset ra ,P_request req,M_Emp_User emp,M_Emp_User emp1 "+
						" where ia.no_ind=ci.no_ind and ra.id_req=ia.id_req and req.id_req=ra.id_req and req.add_by=emp.id_emp_user and req.req_by=emp1.id_emp_user "
						+ "and dt_ind >=(replace(convert(NVARCHAR, '"
							+ dt_frm
							+ "', 106), ' ', '-')) and dt_ind <= (replace(convert(NVARCHAR, '"
							+ dt_to + "', 106), ' ', '-')) and ci.no_ind = '"+rs.getString(3)+"'";

				}
				ps = con.prepareStatement(sql);
				rs2 = ps.executeQuery();

				ResultSetMetaData metaData = rs2.getMetaData();
				int columns = metaData.getColumnCount();
				while (rs2.next()) {
					JSONObject jobj2 = new JSONObject();
					for (int i = 1; i <= columns; i++) {
						String name = metaData.getColumnName(i);
						jobj2.put(name, rs2.getString(name));
					}
					jarr.put(jobj2);

				}
			}
			
			
			jobj.put("data", jarr);
		} catch (Exception e) {
			System.out.println("sql error in Delete_Indent.");
		}

		return jobj;

	}

}
