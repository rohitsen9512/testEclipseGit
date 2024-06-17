package com.Master;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

import dto.Common.DtoCommon;


public class M_Emp_User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      paramValues=paramValues.replace("'", "''");
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0",id1="0",value="",ColName="",status_emp="",searchWord="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id1") !=null)
		{
			 id1 = request.getParameter("id1");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("status_emp") !=null)
		{
			status_emp = request.getParameter("status_emp");
		}
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}
		try
		{
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String User_Type = (String)session.getAttribute("UserType");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddEmp(map,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayEmp(id ,searchWord);	
	                break;
	                
	                
	            case "DisplayEmpForLogin":
	            	jobj = DisplayEmpForLogin();	
	                break;
	                
	                
	            case "Edit":
	            	jobj = DisplayEmpForEdit(id, searchWord);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateEmp(map,id,status_emp,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteEmp(id);	
	                break; 
	            case "find":
	            	jobj = findEmp(id,id);	
	                break; 
	            case "DropDownResult":
	            	jobj = DropDownResult(id,id1);	
	                break; 
	            case "LoginUserDetails":
	            	jobj = LoginUserDetails(id_emp_user);	
	                break;  
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	            case "Updateimg":
	            	jobj = UpdateEmpImg(id,id_emp_user);	
	                break; 
	            case "showAssset":
	            	jobj = showAssset(id_emp_user);	
	                break; 
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in M_Emp_User.");
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
	
	




	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_emp_user from M_Emp_User where "+ColName+" = '"+value+"'";
		System.out.println(query);
		try 
		{
			
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("data",1);
			}
			else
			{
				json.put("data",0);
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	public JSONObject LoginUserDetails(int id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		//String sql ="select * from M_Emp_User where id_emp_user = "+id+"";
		String sql ="select emp.*,ms.gstin_no_city,ms.dl_no_city,nm_emp+' [Code: '+cd_emp+'; Email: '+id_emp+']' as nm_emp1 ,"
				+ " ( SELECT repo_mngr FROM M_Emp_User where id_emp_user = emp.repo_mngr ) as secnd_app from M_Emp_User emp , M_Subloc ms where emp.id_sloc=ms.id_sloc and id_emp_user = "+id+"";
		
		System.out.println(sql);
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in M_Emp_User."+e.toString());
		}
		 
		return jobj;
		
		
	}
	public JSONObject findEmp(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_emp_user from M_Emp_User where "+ColName+" = '"+value+"'";
		System.out.println(query);
		try 
		{
			
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("data",1);
			}
			else
			{
				json.put("data",0);
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject DropDownResult(String id,String id1)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		System.out.println(id);
		System.out.println(id1);
		String sql="";
		if(id1.equals("0") ||id1.equals("")||id.equals("0") ||id.equals("")) {
		sql="select nm_emp+' [Code: '+cd_emp+'; Email: '+id_emp+']' as nm_emp,id_emp_user from M_Emp_User where status_emp='Working' order by nm_emp";
		}		
		//if(id1.equals("0") &&!id.equals("0"))
//		{
//			//sql="select id_emp_user,nm_emp+'-'+cd_emp as nm_emp from M_Emp_User where status_emp='Working' and id_dept="+id+" order by nm_emp";
//			sql="select id_emp_user,nm_emp+'-'+cd_emp as nm_emp from M_Emp_User where status_emp='Working' and id_design="+id+" order by nm_emp";
//		}
		if(!id.equals("0") &&id1.equals(""))
		{
			//sql="select id_emp_user,nm_emp+'-'+cd_emp as nm_emp from M_Emp_User where status_emp='Working' and id_dept="+id+" order by nm_emp";
			sql="select id_emp_user,nm_emp+' [Code: '+cd_emp+'; Email: '+id_emp+']' as nm_emp from M_Emp_User emp,M_Designation d  where status_emp='Working' and emp.id_design=d.id_design  and  id_sloc="+id+" and d.id_design='1' order by nm_emp";
		}
		if(id.equals("")||id.equals("0") &&!id1.equals("")&&!id1.equals("0"))
		{
			//sql="select id_emp_user,nm_emp+'-'+cd_emp as nm_emp from M_Emp_User where status_emp='Working' and id_dept="+id+" order by nm_emp";
			sql="select id_emp_user,nm_emp+' [Code: '+cd_emp+'; Email: '+id_emp+']' as nm_emp from M_Emp_User emp,M_Designation d  where status_emp='Working' and emp.id_design=d.id_design  and  d.id_design="+id1+"  order by nm_emp";
		  System.out.println("HH");
		}
		if(!id.equals("0") &&!id1.equals("")&&!id1.equals("0"))
		{
			//sql="select id_emp_user,nm_emp+'-'+cd_emp as nm_emp from M_Emp_User where status_emp='Working' and id_dept="+id+" order by nm_emp";
			sql="select id_emp_user,nm_emp+' [Code: '+cd_emp+'; Email: '+id_emp+']' as nm_emp from M_Emp_User emp,M_Designation d  where status_emp='Working' and emp.id_design=d.id_design  and  id_sloc="+id+" and d.id_design="+id1+" order by nm_emp";
		}
		System.out.println(sql);
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
		    System.out.println(jobj);
		}
		catch(Exception e)
		{
			System.out.println("sql error in M_Emp_User."+e);
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject DeleteEmp(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		
		try 
		{
			
			String query = "select id_wh from A_Ware_House where to_assign = "+id+"";
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				j=2;
			}
			else
			{
				query = "delete M_Emp_User where id_emp_user = "+id+"";
				
			 	ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					 query = "select id_log_user from M_User_Login where id_emp_user = "+id+"";
					
					PreparedStatement ps=con.prepareStatement(query);
					rs=ps.executeQuery();
				if(rs.next())
				{
					query = "delete M_User_Login where id_emp_user = "+id+"";
					
				 	ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						j=1;
					}
				}
				}
			}
		
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject UpdateEmp(HashMap<String, String> map , String id , String status_emp,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Emp_User",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_emp_user")
							{
								    if (map.containsKey(rs.getString(1)))
								    {
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
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in M_Emp_User.");
				}
		try 
		{
		String query="";
		if(status_emp.equals("NonWorking"))
		{
			 query="select id_wh from A_Ware_House where to_assign = "+id+" ";
			
				 ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					j=2;
				}
		
		else
		{
		 query="update M_Emp_User set "+col_Val+" where id_emp_user="+id+"";
		
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
			}
		}
		}
		else
		{
		 query="update M_Emp_User set "+col_Val+" where id_emp_user="+id+"";
		
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
			}
		}
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
public JSONObject UpdateEmpImg(String id,int id_emp_user) {
		
	int j=0;
	JSONObject json=new JSONObject();
	String query;
	try {	query="update M_Emp_User set emp_image= '"+id+"' where id_emp_user="+id_emp_user+"";
		
		PreparedStatement ps=con.prepareStatement(query);
		j=ps.executeUpdate();
		if(j > 0)
		{
			j=2;
		}

		json.put("data",j);
	}
	catch (Exception e)
	{
		
		e.printStackTrace();
	}
	
	
	return json;
}
public JSONObject showAssset(int id_emp_user)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	String sql="select ds_pro,id_wh_dyn,appNo,serial_no,typ_asst,remarks,rmk_asst from A_Ware_House where to_assign='"+id_emp_user+"' and sold='no'";
	
	try
	{
		System.out.println(sql);
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
		System.out.println("sql error in M_Emp_User.");
	}
	 
	return jobj;
	
	
}
	
	public JSONObject DisplayEmpForLogin()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_emp_user,nm_emp from M_Emp_User where id_emp_user not in (select id_emp_user from M_User_Login)";
		
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
			System.out.println("sql error in M_Emp_User.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayEmp(String id ,String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		//String sql="select top 100 id_emp_user,nm_emp,cd_emp,id_flr,id_emp,eu.id_design,des.nm_design,status_emp,id_loc,id_sloc,id_building,id_div,emp_type from M_Emp_User eu ,M_Designation des where eu.id_design=des.id_design and (nm_emp like '"+searchWord+"%' or cd_emp like '"+searchWord+"%' or nm_design like '"+searchWord+"%' or id_emp like '"+searchWord+"%'  ) order by nm_emp";
		String sql="select eu.id_cc,eu.id_design,eu.repo_mngr,eu.id_emp_user,eu.id_flr,eu.id_loc,eu.id_sloc,eu.id_building,eu.id_dept,eu.status_emp,eu.nm_emp,eu.cd_emp,id_emp,des.nm_design from M_Emp_User eu ,M_Designation des where eu.id_design=des.id_design and (nm_emp like '"+searchWord+"%' or cd_emp like '"+searchWord+"%' or nm_design like '"+searchWord+"%' or id_emp like '"+searchWord+"%'  ) order by nm_emp";

		if(!id.equals("0"))
		{
			sql ="select * from M_Emp_User where id_emp_user = "+id+"";
		}
		System.out.println(sql);
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
			System.out.println("sql error in M_Emp_User.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayEmpForEdit(String id ,String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		//String sql="select top 100 id_emp_user,nm_emp,cd_emp,id_flr,id_emp,eu.id_design,des.nm_design,status_emp,id_loc,id_sloc,id_building,id_div,emp_type from M_Emp_User eu ,M_Designation des where eu.id_design=des.id_design and (nm_emp like '"+searchWord+"%' or cd_emp like '"+searchWord+"%' or nm_design like '"+searchWord+"%' or id_emp like '"+searchWord+"%'  ) order by nm_emp";
		String sql="select eu.*,des.nm_design from M_Emp_User eu ,M_Designation des where eu.id_design=des.id_design and (nm_emp like '"+searchWord+"%' or cd_emp like '"+searchWord+"%' or nm_design like '"+searchWord+"%' or id_emp like '"+searchWord+"%'  ) order by nm_emp";

		if(!id.equals("0"))
		{
			sql ="select * from M_Emp_User where id_emp_user = "+id+"";
		}
		System.out.println(sql);
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
			System.out.println("sql error in M_Emp_User.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddEmp(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		int id_emp_user=0;
		String nm_login="";
		String pwd="";
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Emp_User",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_emp_user")
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
								    }
							  }
						}
		}
						catch(Exception e)
						{
							//System.out.println("Some error in M_Emp_User.");
						}				
						String query="insert into M_Emp_User("+colName+") values("+value+")";
						System.out.println(query);	
						/*
						 * stmt=con.createStatement();
						 * stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS); rs1 =
						 * stmt.getGeneratedKeys();
						 */
       						 
						/*
						 * while (rs1.next()) { id_emp_user = rs1.getInt(1); } String sql =
						 * "select nm_login,pwd from M_User_login where id_emp_user="+id_emp_user+"";
						 * ps=con.prepareStatement(sql); rs=ps.executeQuery(); if(rs.next()) { nm_login
						 * = rs.getString("nm_login"); pwd = rs.getString("pwd"); }
						 */
  				
  				//.........for mail ........
				/*
				 * String
				 * replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="";
				 * String mailSql
				 * ="select id_emp,nm_emp from M_Emp_User where id_emp_user="+id_emp_user+"";
				 * 
				 * 
				 * DtoCommon dtcm = new DtoCommon(); rs = dtcm.GetResultSet(mailSql, request);
				 * if(rs.next()) { replyMailId = rs.getString(1); name = rs.getString(2); }
				 * List<String> recipients = new ArrayList<String>();
				 * //recipients.add(assetHolderId);
				 * 
				 * String link = dtcm.ReturnParticularMessage("link"); String footerMsg =
				 * dtcm.ReturnParticularMessage("footerMsg"); String mailSubject =
				 * dtcm.ReturnParticularMessage("loginDetails");
				 * 
				 * String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
				 * "Below are the login credential details for Asset Management tool:<br>"+
				 * "User Name : <b>"+nm_login+"</b><br>"+ "Password : <b>"+pwd+"</b><br>"+
				 * "<br><br><br>"+link+""+ "<p>"+footerMsg+"</p>";
				 * 
				 * Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
				 */
       				try 
       				{
       					PreparedStatement ps=con.prepareStatement(query);
       					j=ps.executeUpdate();
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
