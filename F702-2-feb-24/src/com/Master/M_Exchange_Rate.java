package com.Master;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


public class M_Exchange_Rate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ResultSet rs1=null;
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
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
		      if(parts[0].equals("month") && !paramValues.equals(""))
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
		 
		    
		String action = "",id="0",value="",ColName="",id_curr="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_curr") !=null)
		{
			id_curr = request.getParameter("id_curr");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}

		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddExchangeRate(map,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayExchangeRate(id);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayExchangeRate(id);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateExchangeRate(map,id,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteExchangeRate(id);	
	                break; 
	                
	            case "DropDownResult":
	            	jobj = DropDownResult();	
	                break; 
	            
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(id_curr);	
	            	
	                break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Location Master.");
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
	
	public JSONObject CheckExitsVal(String id_curr)
	{
		JSONObject json=new JSONObject();
		try 
		{
			if(!id_curr.equals(""))
			{
				String query = "select id_curr from M_Exchange_Rate where id_curr = "+id_curr+"";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						json.put("data",0);
					}
					else
					{
						json.put("data",1);
					}
				
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
	
	public JSONObject DropDownResult()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_loc,nm_loc from M_Exchange_Rate";
		
		
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
			System.out.println("sql error in Exchange Rate.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject DeleteExchangeRate(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
					String query = "delete M_Exchange_Rate where id_exng_rate = "+id+"";
				
					ps=con.prepareStatement(query);
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
	
	public JSONObject UpdateExchangeRate(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Exchange_Rate",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_exng_rate")
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
					System.out.println("Some error in Exchange Rate.");
				}
		
		String query="update M_Exchange_Rate set "+col_Val+" where id_exng_rate="+id+"";
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
	
	
	
	public JSONObject DisplayExchangeRate(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select er.id_exng_rate,er.rate,(replace(convert(NVARCHAR, er.month_year, 103), ' ', '-')) as month_year,er.id_curr,nm_curr from M_Exchange_Rate er,M_currency curr where er.id_curr=curr.id_curr";
		if(!id.equals("0"))
		{
			sql ="select er.id_exng_rate,er.rate,(replace(convert(NVARCHAR, er.month_year, 103), ' ', '-')) as month_year,er.id_curr from M_Exchange_Rate er where id_exng_rate = "+id+"";
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
			System.out.println("sql error in Exchange Rate.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddExchangeRate(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Exchange_Rate",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_exng_rate")
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
					System.out.println("Some error in Exchange Rate.");
				}
		
		String query="insert into M_Exchange_Rate("+colName+") values("+value+")";
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
