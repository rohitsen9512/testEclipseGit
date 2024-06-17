package com.Login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


public class LoginForMobile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	HttpSession session;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0",value="",ColName="",type_grp="",type_Login="",name="",pwd="",username="",pwd_customer="" ,dt_ld ="";
		 
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)		
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("name") !=null)
		{
			name = request.getParameter("name");
		}
		if(request.getParameter("pwd") !=null)
		{
			pwd = request.getParameter("pwd");
		}
		if(request.getParameter("username") !=null)
		{
			username = request.getParameter("username");
		}
		if(request.getParameter("pwd_customer") !=null)
		{
			pwd_customer = request.getParameter("pwd_customer");
		}
		if(request.getParameter("type_grp") !=null)
		{
			type_grp = request.getParameter("type_grp");
		}
		
		if(request.getParameter("type_Login") !=null)
		{
			type_Login = request.getParameter("type_Login");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
//		if(request.getParameter("dt_ld") !=null)
//		{
//			dt_ld = request.getParameter("dt_ld");
//		}
	
		
		try
		{
			if(!dt_ld.equals(""))
			{
				dt_ld = dateFormatNeeded.format(userDateFormat.parse(dt_ld));
			}
			
			
			session = request.getSession(true);
			System.out.println(request.getParameter("dbname"));
			session.setAttribute("dbname", request.getParameter("dbname"));
			
			
			System.out.println(session);
			 
			System.out.println("dbname");
			 
			con=Common.GetConnection(request);
			//jobj = LoginUser(name,pwd,request);	
			JSONObject json=new JSONObject();
			String j="";
			String query="";
			try 
			{
				
			if(type_Login.equals("comp")) {
				query="select nm_emp,id_emp,id_log_user,l.id_emp_user,type_user,l.id_div,id_bu,id_s_function,emp.emp_image,c.file_name,mf.std_finance, mf.end_finance   ,mf.id_fincance, id_design  from M_User_Login l, M_Emp_User emp,M_Company c ,M_Finance_Year mf where  nm_login='"+name+"' and pwd='"+pwd+"' and status_user=1 and "
						+ " emp.id_emp_user=l.id_emp_user and mf.active_year='1'";
				
			}
		
			else {
				//query = "select id_customer,id_lead_m,nm_customer,pwd_customer from M_Customer where username='"+username+"' and pwd_customer='"+pwd_customer+"'  ";
				query =" select id_customer,m.id_lead_m,nm_customer,pwd_customer,username ,(replace(convert(NVARCHAR, ledm.dt_ld, 103), ' ', '-')) as dtldcreate, ledm.lead_no,ledm.pstn_ct,ledm.pstn_email ,mf.std_finance, mf.end_finance   ," + 
						" mf.id_fincance,  ledm.id_loc,ledm.id_sloc,l.nm_loc,s.nm_subl ,ledm.address ,ledm.status_deliver_ld  " + 
						" from M_Customer m,O_Assign_Lead_Master ledm , M_Emp_User emp ,M_loc l ,M_Subloc s ,M_Finance_Year mf where " + 
						" ledm.id_lead_m=m.id_lead_m and ledm.tag_to_emp = emp.id_emp_user  " + 
						" and ledm.id_loc=l.id_loc and ledm.id_sloc=s.id_sloc  and username='"+username+"' and pwd_customer='"+pwd_customer+"'  and ledm.dt_ld >=(replace(convert(NVARCHAR, ledm.dt_ld, 106), ' ', '-')) and ledm.status_deliver_ld= 0 and  mf.active_year='1' ";
			}
			
			System.out.println(query);
			PreparedStatement stmt=con.prepareStatement(query);	
			 
			rs = stmt.executeQuery();
			
				if (rs.next())
				{
					j="1";
					if (type_Login.equals("comp")) {
					session.setAttribute("UserName", name);
					session.setAttribute("email", rs.getString("id_emp"));
					//session.setAttribute("email", rs.getString("id_emp"));
					
					session.setAttribute("id_emp_user", rs.getInt("id_emp_user"));
					session.setAttribute("UserType", rs.getString("type_user"));
					//session.setAttribute("DeptId", rs.getString(5));
					//session.setAttribute("FlrId", rs.getString(6));
					session.setAttribute("DivId", rs.getInt("id_div"));
					session.setAttribute("UserId", rs.getInt("id_log_user"));
					session.setAttribute("std_finance", rs.getString("std_finance"));
					session.setAttribute("end_finance", rs.getString("end_finance"));
					session.setAttribute("id_finance", rs.getString("id_fincance"));
					
				
				    String relativeWebPath3 = "InvoiceScanFile/"+rs.getString("emp_image");
				    
				    json.put("type_user",rs.getString("type_user"));
					json.put("image_name",relativeWebPath3);
					json.put("user_name",rs.getString("nm_emp"));
					json.put("email",rs.getString("id_emp"));
					json.put("id_emp_user",rs.getString("id_emp_user"));
					json.put("id_log_user",rs.getString("id_log_user"));
					}
					else{
						session.setAttribute("id_customer",rs.getString("id_customer"));
						session.setAttribute("nm_customer",rs.getString("nm_customer"));
						// json.put("id_lead_m",rs.getString("id_lead_m"));
						//json.put("username",rs.getString("nm_customer"));
						json.put("pwd",rs.getString("pwd_customer"));
						json.put("username",rs.getString("username"));
						json.put("pstn_ct",rs.getString("pstn_ct"));
						json.put("status_deliver_ld",rs.getString("status_deliver_ld"));
						//json.put("dtldcreate",rs.getString("dtldcreate"));
						//json.put("lead_no",rs.getString("lead_no"));
						//json.put("pstn_email",rs.getString("pstn_email"));
						
						//json.put("iddeliverBY",rs.getString("tag_to_emp"));
						//json.put("delivry_boy_contact",rs.getString("delivry_boy_contact"));
						//json.put("delivery_boy",rs.getString("delivery_boy"));
						//json.put("id_loc",rs.getString("id_loc"));
						//json.put("id_sloc",rs.getString("id_sloc"));
						//json.put("nm_loc",rs.getString("nm_loc"));
						//json.put("nm_subl",rs.getString("nm_subl"));
						//json.put("address",rs.getString("address"));
						
					}
				}
			else
				{
				j="0";
				}
			
			json.put("data",j);
			}
			catch (Exception e)
			{
				
				e.printStackTrace();
			}
			jobj= json;
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in mobile.");
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
	
	
	public JSONObject LoginUser(String name,String pwd,HttpServletRequest request)
	{
		
		JSONObject json=new JSONObject();
		String j="";
		try 
		{
			session = request.getSession(true);
			
		String 	 query="select nm_emp,id_log_user,l.id_emp_user,type_user,id_dept,l.id_flr,l.id_div,id_bu,id_s_function,emp.emp_image,c.file_name ,id_emp from M_User_Login l, M_Emp_User emp,M_Company c where  nm_login='"+name+"' and pwd='"+pwd+"' and status_user=1 and emp.id_emp_user=l.id_emp_user";
		
		System.out.println(query);
		PreparedStatement stmt=con.prepareStatement(query);	
		 
		rs = stmt.executeQuery();
		
			if (rs.next())
			{
				j="1";
				
				
				
				session.setAttribute("email", rs.getString("id_emp"));
				
				session.setAttribute("id_emp_user", rs.getInt("id_emp_user"));
				session.setAttribute("UserType", rs.getString("type_user"));
				//session.setAttribute("DeptId", rs.getString(5));
				//session.setAttribute("FlrId", rs.getString(6));
				session.setAttribute("DivId", rs.getInt(7));
				//session.setAttribute("UserId", rs.getInt(3));
			
			    String relativeWebPath3 = "InvoiceScanFile/"+rs.getString("emp_image");
			    
			    json.put("type_user",rs.getString("type_user"));
				json.put("image_name",relativeWebPath3);
				json.put("user_name",rs.getString("nm_emp"));
				json.put("email",rs.getString("id_emp"));
				json.put("id_emp_user",rs.getString("id_emp_user"));
				json.put("id_log_user",rs.getString("id_log_user"));
				System.out.println(session);
				System.out.println("test sessjkj"+session.getAttribute("UserType"));
			}
		else
			{
			j="0";
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
