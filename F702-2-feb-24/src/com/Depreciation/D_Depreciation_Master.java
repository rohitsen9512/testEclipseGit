package com.Depreciation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

public class D_Depreciation_Master extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	Connection con = null;
	Statement stmt = null;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();

		HashMap<String, String> map = new HashMap<String, String>();

		Enumeration elemet = request.getParameterNames();

		String paramName = "";
		String paramValues = "";
		while (elemet.hasMoreElements()) {

			paramName = (String) elemet.nextElement();
			paramValues = request.getParameter(paramName);
			map.put(paramName, paramValues);

		}

		String action="";
		int j=0;
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		
		String assetgroups [] = request.getParameterValues("assetgroups");
		String inCometaxact [] = request.getParameterValues("inCometaxact");
		String companyact [] = request.getParameterValues("companyact");
		//String usgaap [] = request.getParameterValues("usgaap");
		String query="",sql="";
		try {

			con = Common.GetConnection(request);

			switch (action) {
			case "Save":
				
				for(int i=0;i<assetgroups.length;i++)
				{
					sql="select id_grp from D_Dprn_Master where id_grp = "+assetgroups[i]+"";
					ps=con.prepareStatement(sql);
					rs=ps.executeQuery();
					if(rs.next())
					{
						query ="update D_Dprn_Master set iwdv = '"+inCometaxact[i]+"' , cslm = '"+companyact[i]+"'  where id_grp = "+assetgroups[i]+" ";
		    			 ps1=con.prepareStatement(query);
		    			j=ps1.executeUpdate();
					}
					else
					{
						query ="insert into D_Dprn_Master (id_grp,iwdv,cslm) values("+assetgroups[i]+",'"+inCometaxact[i]+"','"+companyact[i]+"')";
		    			 ps1=con.prepareStatement(query);
		    			j=ps1.executeUpdate();
					}
	    			 
					
				}
				if(j > 0)
				{
					jobj.put("data",j);
				}
				break;

			
			case "Display":
				jobj = DisplayDataForDepreciationMaster();
				break;

			}

			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());

		} catch (Exception e) {
			System.out.println("Error in A_Add_To_Store.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public JSONObject DisplayDataForDepreciationMaster() {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";

		//sql="select a.id_assetdiv,a.nm_assetdiv,b.iwdv,b.cslm,b.usgaap from M_Asset_Div a LEFT OUTER JOIN D_Dprn_Master b on a.id_assetdiv = b.id_grp  order by a.nm_assetdiv";
		sql = "select a.id_assetdiv,a.nm_assetdiv,b.iwdv,b.cwdv,b.cslm,b.usgaap from M_Asset_Div a LEFT OUTER JOIN D_Dprn_Master b on a.id_assetdiv=b.id_grp  order by a.nm_assetdiv";
		try {
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
			System.out.println("sql error in D_Depreciation_Master.");
		}

		return jobj;

	}

	
}
