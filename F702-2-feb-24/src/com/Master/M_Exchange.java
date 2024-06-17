package com.Master;

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


public class M_Exchange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ResultSet rs1=null;
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		HttpSession session = request.getSession();
		int UserId=0;
		 session = request.getSession(true);
		session.setAttribute("dbname", request.getParameter("dbname")); 
		
		 Enumeration elemet = request.getParameterNames();
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  

		 String paramName="";
		 String paramValues="";
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
		    	  paramValues=paramValues.replace("'", "''");
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		    
		    
		String action = "",id="0",value="",ColName="",id_loc="",id_country="",searchWord="",city="";
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
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("id_loc") !=null)
		{
			id_loc = request.getParameter("id_loc");
		}
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}
		if(request.getParameter("id_sloc") !=null) 
		{
			
			city=request.getParameter("id_sloc");
		}

		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddSrno(map,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplaySublocation(id,searchWord);	
	                break;

	            case "DropDownResult":
	            	jobj = DropDownResult(id,city);	
	                break; 
	                
	            case "Edit":
	            	jobj = DisplaySublocation(id,searchWord);	
	                break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in Sublocation Master.");
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
	

	public JSONObject DropDownResult(String id, String city)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		/*
		 * String city=""; city=request.getParameter("id_sloc");
		 */
		String sql="select serial_no from S_I_Ware_House where serial_no="+id+" and device_status='in_store' and id_sloc='"+city+"'" ;
		System.out.println(sql);
		if(id.equals("0"))
		{
			sql="select serial_no from S_I_Ware_House where device_status='in_store'";
		}
		
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		System.out.println(sql);
		
		
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
			System.out.println(e+"sql error in Location master.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject DisplaySublocation(String id, String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select *,l.nm_loc from M_Subloc s,M_Loc l where (nm_subl like '"+searchWord+"%') and l.id_loc=s.id_loc order by nm_subl";
		if(!id.equals("0"))
		{
			sql ="select * from M_Subloc where id_sloc = "+id+"";
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
			System.out.println("sql error in Exchange master.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddSrno(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="",typ_service="",serial_no="",sr_no="",id_lead="" ;
		int j=0;
		typ_service=map.get("equip_status");
		serial_no=map.get("sr_no_new");
		sr_no=map.get("sr_no");
		id_lead=map.get("id_lead");
		
		
		JSONObject json=new JSONObject();
		try
		{
				stmt = con.createStatement();
				rs = Common.GetColumns("M_Exchange",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_ex")
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
					System.out.println("Some error in Exchange master.");
				}
		
		String query="insert into M_Exchange("+colName+") values("+value+")";
		 System.out.println("update S_I_Ware_House set device_status ='Rental' where serial_no ='"+serial_no+"' and  device_status='in_store'");

		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				 System.out.println("update S_I_Ware_House set device_status ='Rental' where serial_no ='"+serial_no+"' and  device_status='in_store'");
				j=stmt.executeUpdate("update S_I_Ware_House set device_status ='Rental' where serial_no ='"+serial_no+"' and  device_status='in_store'");
				System.out.println(j);
				j=stmt.executeUpdate("update S_I_Ware_House set device_status='in_store' where serial_no='"+sr_no+"'");
				System.out.println(j);
				if(j > 0)
				{
					j=0;
					 System.out.println("update O_Lead set sr_no ='"+serial_no+"' where sr_no ='"+sr_no+"'");
					 System.out.println("update O_Assign_Lead set sr_no ='"+serial_no+"' where sr_no ='"+sr_no+"'");
					j=stmt.executeUpdate("update O_Lead set sr_no ='"+serial_no+"' where sr_no ='"+sr_no+"'");
					j=stmt.executeUpdate("update O_Assign_Lead set sr_no ='"+serial_no+"' where sr_no ='"+sr_no+"'");
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
}
