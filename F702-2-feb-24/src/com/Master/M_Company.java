package com.Master;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


public class M_Company extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	
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
		      paramValues=paramValues.replace("'", "''");
		      map.put(paramName, paramValues);
		      
		    }
		   
		    
		     
		    
		String action = "",id="0";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		

		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            
	                
	            case "Edit":
	            	jobj = DisplayCompany();	
	                break;
	                
	            case "Update":
	            	jobj = UpdateCompany(map,id, request);	
	                break; 
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Company Master.");
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
	
	
	
	public JSONObject UpdateCompany(HashMap<String, String> map,String id,HttpServletRequest request)
	{
		String col_Val="",colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Company", request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_com")
							{
								    if (map.containsKey(rs.getString(1)))
								    {
								    	if(col_Val.equals(""))
								    	{
								    		col_Val += rs.getString(1) + "='" + map.get(rs.getString(1)).trim() + "'";
									    	
								    	}
								    	else
								    	{
								    		
								    		col_Val += ","+ rs.getString(1) + "='" + map.get(rs.getString(1)).trim() + "'";
									    	
								    		
								    	}
								    	
								    	
								    	if(colName.equals(""))
								    	{
									    	colName += rs.getString(1);
									    	value += "'"+ map.get(rs.getString(1)).trim()+"'";
								    	}
								    	else
								    	{
								    		colName +=","+ rs.getString(1);
									    	value +=", '"+ map.get(rs.getString(1)).trim()+"'";
								    		
								    	}
								    	
								    	
								    }
							  }
						}
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in Company master.");
				}
		
		String sql="select id_com from M_Company";
		
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		    if(rs.next())
		    {
		    	String query="update M_Company set "+col_Val+" where id_com="+rs.getInt(1)+"";
		    	
		    	System.out.println(query);
				
					PreparedStatement ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						j=1;
					}
				
					json.put("data",j);
			}
		    else
		    {
		    	String query="insert into M_Company("+colName+") values("+value+")";
		    
		    	PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
				json.put("data",j);
		    }
		}
		catch(Exception e)
		{
			System.out.println("sql error in Company master.");
		}
		return json;
}


	public JSONObject DisplayCompany()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select * from M_Company";
		
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
			System.out.println("sql error in Company master.");
		}
		 
		return jobj;
		
		
	}
	

}
