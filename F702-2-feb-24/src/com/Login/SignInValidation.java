package com.Login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;
import com.HelpDesk.HelpDeskModel;
import javax.servlet.http.HttpSession;
import java.util.Random;


public class SignInValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0",value="",ColName="",type_grp="";
		int json=0;
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
		if(request.getParameter("type_grp") !=null)
		{
			type_grp = request.getParameter("type_grp");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		
		/*
		 * HttpSession session = request.getSession(); int id_emp_user =
		 * (int)session.getAttribute("id_emp_user"); String UserTupe =
		 * (String)session.getAttribute("userType");
		 */
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "GenerateOTP":
	            	jobj = AddVendor(map,  request);	
	                break;
	                
	            case "CheckEmail":
	            	jobj = CheckMail(map);	
	                break;
	                
	            case "UpdatePassword":
	            	jobj = UpdatePassword(map);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayDepartment(id);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayDepartment(id);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateDepartment(map,id,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteDepartment(id);	
	                break; 
	                
	            case "DropDownResult":
	            	jobj = DropDownResult(type_grp);	
	                break; 
	                
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName,id);	
	                break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in Department Master.");
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
	
	public JSONObject CheckExitsVal(String value , String ColName,String id)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_dept from M_Dept where "+ColName+" = '"+value+"'";
		if(!id.equals("0"))
			query = "select id_dept from M_Dept where "+ColName+" = '"+value+"' and id_dept!="+id+"";
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
	
	
	public JSONObject DropDownResult(String type)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_dept,nm_dept from M_Dept order by nm_dept ";
		
		
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
			System.out.println("sql error in  M_Dept.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DeleteDepartment(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
		String query = "select id_emp_user from M_Emp_User where id_dept = "+id+"";
		PreparedStatement ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		if(rs.next())
		{
			j=2;
		}
		else
			{
				 query = "delete M_Dept where id_dept = "+id+"";
				
				 	ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						j=1;
					}
				
					json.put("data",j);
				
			}
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject UpdateDepartment(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_DEPT",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_dept")
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
					System.out.println("Some error in Department master.");
				}
		
		String query="update M_Dept set "+col_Val+" where id_dept="+id+"";
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
	
	
	
	public JSONObject DisplayDepartment(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select * from M_Dept order by nm_dept asc";
		if(!id.equals("0"))
		{
			sql ="select * from M_Dept where id_dept = "+id+" order by nm_dept asc";
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Department master.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject CheckMail(HashMap<String, String> map)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String otp1="";
		String mail = map.get("ct_mailid");
		String otp = map.get("otp");
		String sql="select id_ven,otp from M_Vendor where ct_mailid='"+mail+"'";
		System.out.println(sql);
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		if(rs.next())
		{
			 otp1=rs.getString(2);
		}
		if(otp.equals(otp1))
		{
			jobj.put("data", 1);
		}
		  
		    
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		 
		return jobj;
		
		
	}
	public JSONObject UpdatePassword(HashMap<String, String> map)
	{
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		String mail1 = map.get("mail1");
		String pswrd = map.get("new_password");
		String sql="select id_ven from M_vendor where ct_mailid='"+mail1+"' ";
		System.out.println(sql);
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		if (rs.next()) 
		 { 
			  String id_ven = rs.getString(1); 
			  String query="update M_vendor set password='"+pswrd+"' where id_ven="+id_ven+" ";
			  System.out.println(query);
			  PreparedStatement ps=con.prepareStatement(query);
   			j=ps.executeUpdate();
   			if(j>0)
   			{
   				 query="update M_vendor set otp=0 where id_ven="+id_ven+" ";
   				 System.out.println(query);
  			  PreparedStatement ps1=con.prepareStatement(query);
     			j=ps1.executeUpdate();
     			if(j>0)
     			{
     				 j=1;
     			}
   			}
					  
		 }
			 
		json.put("data",j);			 
		  
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		 
		return json;
		
		
	}
	public JSONObject AddVendor(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		String mail = map.get("ct_mailid");
		String ct_mob = map.get("ct_mob");
		String nm_ven = map.get("nm_ven");
		String query="select ct_mailid from M_Vendor where (ct_mailid='"+mail+"' or nm_ven='"+nm_ven+"' or ct_mob='"+ct_mob+"') ";
		System.out.println(query);
		
		try
		{
		ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		if(rs.next())
		{
			 j=2;
		}
		
		else
		{
				rs = Common.GetColumns("M_Vendor",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_ven")
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
		
		String id_ven="";
		char otp1[]=GenerateOTP(4);
		String otp = new String(otp1);
		//System.out.println(GenerateOTP(4));
		System.out.println(otp);
		  query="insert into M_Vendor("+colName+",otp) values("+value+","+otp+")";
		System.out.println(query);
		
			 stmt=con.createStatement();
			  stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS); 
			  rs =stmt.getGeneratedKeys();
			 if (rs.next()) 
			 { 
				 id_ven = rs.getString(1); 
				 j=1;
			 }
			  
			//String assign_grp = map.get("assign_grp");
			
			if(!mail.equals(""))
			{ 
				HelpDeskModel hdm = new HelpDeskModel();
			hdm.SendEmailToAssignee1(mail,id_ven,  request);
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
	
	public  char[] GenerateOTP(int length)
	{
	        
		String numbers = "0123456789"; 
	  
	        // Using random method 
	        Random rndm_method = new Random(); 
	  
	        char[] otp = new char[length]; 
	  
	        for (int i = 0; i < length; i++) 
	        { 
	            // Use of charAt() method : to get character value 
	            // Use of nextInt() as it is scanning the value as int 
	            otp[i] = 
	             numbers.charAt(rndm_method.nextInt(numbers.length())); 
	        }
	        
	        return otp; 
	    
		
	}
	
	
}
