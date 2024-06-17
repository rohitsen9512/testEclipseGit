package com.Login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.Common.Common;


public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	JSONObject jobj = new JSONObject();
	JSONObject sesJobj = new JSONObject();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String dbname=(String)session.getAttribute("dbname");
		
		try
		{
			request.getSession().invalidate();
		
		}
		catch(Exception e)
		{
			System.out.println("Error "+e);
		}
		response.sendRedirect("index.html");
		
		//response.sendRedirect("index.html");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			HttpSession session = request.getSession();
		if(session == null || session.getAttribute("UserName") == null)
		{
			sesJobj.put("ses",false);
			request.setAttribute("ses", sesJobj.toString());
			response.getWriter().write(sesJobj.toString());
		}
		else
		{
			sesJobj.put("ses",true);
			request.setAttribute("ses", sesJobj.toString());
			response.getWriter().write(sesJobj.toString());
			
		}
		/*String action="",oldPwd="",newPwd="",confirmPwd="";
		int j=0,id=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("oldPwd") !=null)
		{
			oldPwd = request.getParameter("oldPwd");
		}
		if(request.getParameter("newPwd") !=null)
		{
			newPwd = request.getParameter("newPwd");
		}
		if(request.getParameter("confirmPwd") !=null)
		{
			confirmPwd = request.getParameter("confirmPwd");
		}
		
		//System.out.println("pritesh"+action);
		
		if(action.equals("ChangePassword"))
		{
		try 
		{
			 session = request.getSession(true);  
			id = (int)session.getAttribute("UserId");
			con=Common.GetConnection(request);
			String query="select id_log_user from M_User_Login where pwd='"+oldPwd+"' and id_log_user="+id+" ";
			System.out.println(query);
			PreparedStatement ps=con.prepareStatement(query);
			
			rs = ps.executeQuery();
			if(rs.next())
				{
					
					query="update M_User_Login set pwd ='"+newPwd+"' where id_log_user="+id+"";
					System.out.println(query);
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					
						j=2;
					
				
				}
			else
			{
				j=1;
			}
			jobj.put("data",j);
		}
		catch(Exception e)
		{
			System.out.println("Error "+e);
		}
		
		
		}
		request.setAttribute("data", jobj.toString());
		response.getWriter().write(jobj.toString());
		
	*/
		}
		catch(Exception e)
		{
			System.out.println("Error "+e);
		}
	}	
}
