package com.Tagging;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;



public class T_Discrepancy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	Statement stmt1=null;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		response.setContentType("text/html");  
		String query = "",id="0",id_flr="";
		int json=0,j=0;
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		String chk[] = request.getParameterValues("check");
		try
		{
			con=Common.GetConnection(request);
			for(int i=0; i<chk.length;i++)
        	{
				j=0;
				id_flr=request.getParameter("id_flr_Update"+chk[i]);
				query = "update A_Ware_House set id_flr="+id_flr+" where id_wh = "+chk[i]+"";
				
			 ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				j=1;
        	}
			if(j == 1)
			{
				jobj.put("data",j);
			}
			else
			{
				jobj.put("data",j);
			}
			
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
		}
		catch(Exception e)
		{
			System.out.println("Sql error ... "+e);
		}
		
		
		
		
	}

}

	
	
	
	


