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

public class D_Dep_Config extends HttpServlet {
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

		String action="",type_id="",DepnType="",YearOnly="";
		int j=0;
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		
		if (request.getParameter("type_id") != null) {
			type_id = request.getParameter("type_id");
		}
		if (request.getParameter("DepnType") != null) {
			DepnType = request.getParameter("DepnType");
		}
		if (request.getParameter("YearOnly") != null) {
			YearOnly = request.getParameter("YearOnly");
		}
		
		String capitalization_comp[]		=	request.getParameterValues("capitalization_comp1");
		String comp ="";
		int len=0;
		try{
			if(capitalization_comp.length>0){
				len=capitalization_comp.length;
			}
		}catch(Exception e){
			len=0;
		}
		if(len > 0)
		{
			for(int i=0;i<capitalization_comp.length;i++)
			{
				if(comp.equals(""))
				{
					comp = capitalization_comp[i];
				}
				else
				{
					comp +=","+ capitalization_comp[i];
				}
			}
		}
		try {

			con = Common.GetConnection(request);

			switch (action) {
			 case "Save":
	            	jobj = AddDepConfig(map , type_id , comp,  request);	
	                break;
	                
			case "Display":
				jobj = DisplayDataForDepConfig(type_id);
				break;
				
			 case "DropDownResult":
	            	jobj = DropDownResult(DepnType,YearOnly);	
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

	
	public JSONObject DropDownResult(String DepnType , String YearOnly)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(DepnType.equals(""))
		{
			sql="SELECT type_id,dep_act_name FROM dep_param ORDER BY dep_act_name";
		}
		else
		{
			sql="SELECT type_id,dep_act_name FROM dep_param where type_id NOT IN('IT') ORDER BY dep_act_name";
		}
		if(!DepnType.equals("") && !YearOnly.equals(""))
		{
			sql="SELECT Distinct(year(std_finance)) as std_finance FROM M_Finance_Year ORDER BY year(std_finance) DESC";
		}
		
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
			System.out.println("sql error in D_Dprn_Config.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject DisplayDataForDepConfig(String type_id) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";

		sql="select * from dep_param where type_id = '"+type_id+"'";
		
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
			System.out.println("sql error in D_Depreciation_Config.");
		}

		return jobj;

	}

	
	public JSONObject AddDepConfig(HashMap<String, String> map , String type_id , String capitalization_comp,HttpServletRequest request)
	{
		String colName="",value="",col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("dep_param",  request);
				
						
						while (rs.next())
						{
						
							if((rs.getString(1) !="dep_param_id"))
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
				
		String sql = "select dep_param_id from dep_param where type_id = '"+type_id+"' ";
		ps1 = con.prepareStatement(sql);
		rs1 = ps1.executeQuery();
		if(rs1.next())
		{
			String query="update dep_param set "+col_Val+",capitalization_comp = '"+capitalization_comp+"' where dep_param_id ="+rs1.getInt(1)+"";
			ps=con.prepareStatement(query);
			j=ps.executeUpdate();
		}
		else
		{
			String query="insert into dep_param("+colName+",capitalization_comp) values("+value+",'"+capitalization_comp+"')";
			 ps=con.prepareStatement(query);
			j=ps.executeUpdate();
		}
			if(j > 0)
			{
				j=1;
			}
		
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
}
