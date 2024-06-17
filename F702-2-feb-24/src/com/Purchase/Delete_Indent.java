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

public class Delete_Indent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ResultSet rs = null;
	ResultSet rs1 = null;
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
			int id_emp_user = (int) session.getAttribute("id_emp_user");

			con = Common.GetConnection(request);

			switch (action) {

			case "Display":
				jobj = DisplayIndent(id, dt_frm, dt_to, id_emp_user);
				break;

			case "Delete":
				jobj = DeleteIndent(id, confirm, id_req);
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

	public JSONObject DeleteIndent(String id, String confirm, String id_req) {
		JSONObject json = new JSONObject();
		int j = 0, idCount = 0;

		String[] parts = id.split("Patel");
		idCount = parts.length;
		String no_ind = "",id_req_asst="";
		id = "";
		for (int i = 0; i < idCount; i++) {
			if (id.equals(""))
				id = parts[i];
			else
				id += "," + parts[i];
		}
		try {
			stmt = con.createStatement();
			String sql = "select no_ind from P_Indent_Asset where id_ind_asst IN ("
					+ id + ")";
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				no_ind = rs.getString(1);

			}

			if (!confirm.equals("Yes")) {
				String query = "select COUNT(id_ind_asst) from P_Indent_Asset where no_ind IN ('"
						+ no_ind + "')";
				ps = con.prepareStatement(query);
				rs = ps.executeQuery();

				if (rs.next()) {
					int a = rs.getInt(1);
					if (a > idCount) {
						
						sql ="Select id_req_asst from P_Indent_Asset where id_ind_asst IN ("+ id + ")";
						ps=con.prepareStatement(sql);
						rs=ps.executeQuery();
						while(rs.next())
						{
							if(id_req_asst.equals(""))
									id_req_asst=rs.getString(1);
							else
								id_req_asst +=','+rs.getString(1);
						}
			stmt.executeUpdate("update P_Request_Asset set st_indent='No' WHERE id_req_asst IN ("+ id_req_asst + ") ");
						j=1;
						
						query = "delete P_Indent_Asset where id_ind_asst IN ("
								+ id + ")";

						ps = con.prepareStatement(query);
						j = ps.executeUpdate();

						
						
						if (j > 0) {
							j = 0;
							stmt.executeUpdate("update P_request set st_ind='No' where id_req IN ("
									+ id_req + ")");
							j = 1;
						}
						
						
					} else {
						j = 2;
					}

				}
			} else {

				sql ="Select id_req_asst from P_Indent_Asset where id_ind_asst IN ("+ id + ")";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
					if(id_req_asst.equals(""))
							id_req_asst=rs.getString(1);
					else
						id_req_asst +=','+rs.getString(1);
				}
	stmt.executeUpdate("update P_Request_Asset set st_indent='No' WHERE id_req_asst IN ("+ id_req_asst + ") ");
				j=1;
				
				String query = "delete P_Indent_Asset where id_ind_asst IN ("
						+ id + ")";
				ps = con.prepareStatement(query);
				
				j = ps.executeUpdate();
				if (j > 0) {
					
					
					
					
					query = "delete P_Create_Indent where no_ind = '" + no_ind
							+ "'";
					ps = con.prepareStatement(query);
					j = ps.executeUpdate();
					if (j > 0) {
						j = 0;
						stmt.executeUpdate("update P_request set st_ind='No' where id_req IN ("
								+ id_req + ")");
						j = 1;
					}
				}
			}

			json.put("data", j);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return json;
	}

	public JSONObject DisplayIndent(String id, String dt_frm, String dt_to,
			int id_emp_user) {
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
			if (id.equals("0")) {
				sql = "select id_ind,no_ind,(replace(convert(NVARCHAR, dt_ind, 103), ' ', '-')) as dt_ind,nm_emp from P_Create_Indent,M_Emp_User WHERE id_emp_user=ind_add_by and st_ind='No' and dt_ind >=(replace(convert(NVARCHAR, '"
						+ dt_frm
						+ "', 106), ' ', '-')) and dt_ind <= (replace(convert(NVARCHAR, '"
						+ dt_to + "', 106), ' ', '-'))";
				
			} else {
				sql="select ia.*,no_req,nm_emp,nm_assetdiv,nm_s_assetdiv,nm_model from P_Indent_Asset ia,P_Create_Indent ci,"+
		                   "   P_request pr,M_Emp_User emp,M_Asset_Div ad,M_Subasset_Div sad,m_model mm  where pr.id_req = ia.id_req and "+
		                   "   ad.id_assetdiv=ia.id_grp and sad.id_s_assetdiv=ia.id_sgrp  and mm.id_model=ia.id_prod and emp.id_emp_user=pr.add_by and "+
		                   "  ia.no_ind=ci.no_ind and ci.id_ind="+id+" and ci.st_ind='No' ";
				
				/*sql = "select ia.*,no_req,nm_emp,nm_assetdiv,nm_s_assetdiv from P_Indent_Asset ia,P_Create_Indent ci,P_request pr,M_Emp_User emp,M_Asset_Div ad,M_Subasset_Div sad "
						+ " where pr.id_req = ia.id_req and ad.id_assetdiv=ia.id_grp and sad.id_s_assetdiv=ia.id_sgrp "
						+ " and emp.id_emp_user=pr.add_by and ia.no_ind=ci.no_ind and ci.id_ind="
						+ id
						+ ""
						+ " and ci.st_ind='No' and dt_ind >=(replace(convert(NVARCHAR, '"
						+ dt_frm
						+ "', 106), ' ', '-')) and dt_ind <= (replace(convert(NVARCHAR, '"
						+ dt_to + "', 106), ' ', '-'))";*/
			}
System.out.println(sql);
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
			System.out.println("sql error in Delete_Indent.");
		}

		return jobj;

	}

}
