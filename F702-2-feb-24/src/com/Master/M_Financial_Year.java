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


public class M_Financial_Year extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
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
		      if((parts[0].equals("std") || parts[0].equals("end") || parts[0].equals("stdt") || parts[0].equals("endt") || parts[0].equals("std")) && !paramValues.equals(""))
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
		String action = "",id="0",value="",ColName="",enddate="",fin_years="",startdate="",dates="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		
		if(request.getParameter("fin_years") !=null)
		{
			fin_years = request.getParameter("fin_years");
		}
		if(request.getParameter("startdate") !=null)
		{
			startdate = request.getParameter("startdate");
		}
		if(request.getParameter("enddate") !=null)
		{
			enddate = request.getParameter("enddate");
		}
		if(request.getParameter("dates") !=null)
		{
			dates = request.getParameter("dates");
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
			if(!value.equals(""))
			{
				value = dateFormatNeeded.format(userDateFormat.parse(value));
			}
			
			if(!startdate.equals(""))
			{
				startdate = dateFormatNeeded.format(userDateFormat.parse(startdate));
			}
			if(!dates.equals(""))
			{
				dates = dateFormatNeeded.format(userDateFormat.parse(dates));
			}
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddFinancialYear(map,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayFinancialYear(id);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayFinancialYear(id);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateFinancialYear(map,id,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteFinancialYear(id);	
	                break; 
	                
	            case "DropDownResult":
	            	jobj = DropDownResult();	
	                break; 
	                
	            case "checkInvoiceDate":
	            	jobj = checkInvoiceDate(fin_years,dates);	
	                break; 
	                
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	                
	            case "Current":
	            	jobj = MakeCurrentYear(id);	
	                break; 
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in M_Finance_Year.");
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

	public JSONObject MakeCurrentYear(String id)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		String query="update M_Finance_Year set active_year=0";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				 query="update M_Finance_Year set active_year=1 where id_fincance="+id+"";
				  ps=con.prepareStatement(query);
					j=ps.executeUpdate();
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
	
	
	
	public JSONObject checkInvoiceDate(String fin_years,String dates )
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		System.out.println("dates is-----" +  dates);
		String sql="select id_fincance, end_finance,std_finance from M_Finance_Year where id_finance="+fin_years+"";
		
		
		try
		{
			String std_finance="",end_finance="";
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		if(rs.next())
		{
			end_finance = rs.getString("end_finance");
			std_finance = rs.getString("std_finance");
		}
		
		 if (dates.compareTo(std_finance) >= 0) {
			 
             if (dates.compareTo(end_finance) <= 0) {
                    System.out.println("dates is in between d1 and d2");
             } 
             else {
            	 System.out.println("dates is NOT in between d1 and d2");
            	 jobj.put("data", 0);
             }
       } else {
             System.out.println("dates is NOT in between d1 and d2");
             jobj.put("data", 0);
       }
		
		
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in Location master.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DropDownResult()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_fincance,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance,(replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance,active_year from M_Finance_Year order by std_finance";
		
		
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
			System.out.println("sql error in Location master.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_fincance from M_Finance_Year where "+ColName+" = '"+value+"'";
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
	
	public JSONObject DeleteFinancialYear(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		String query = "delete M_Finance_Year where id_fincance = "+id+"";
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
	
	public JSONObject UpdateFinancialYear(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Finance_Year",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_fincance")
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
					System.out.println("Some error in M_Finance_Year.");
				}
		
		String query="update M_Finance_Year set "+col_Val+" where id_fincance="+id+"";
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
	
	
	
	public JSONObject DisplayFinancialYear(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String sql="select id_fincance,active_year,(replace(convert(NVARCHAR, current_year, 103), ' ', '-')) as current_year,(replace(convert(NVARCHAR, endt_second, 103), ' ', '-')) as endt_second,(replace(convert(NVARCHAR, stdt_second, 103), ' ', '-')) as stdt_second,(replace(convert(NVARCHAR, endt_first, 103), ' ', '-')) as endt_first,(replace(convert(NVARCHAR, stdt_first, 103), ' ', '-')) as stdt_first,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance,(replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance from M_Finance_Year order by std_finance";
		if(!id.equals("0"))
		{
			sql ="select id_fincance,active_year,(replace(convert(NVARCHAR, current_year, 103), ' ', '-')) as current_year,(replace(convert(NVARCHAR, endt_second, 103), ' ', '-')) as endt_second,(replace(convert(NVARCHAR, stdt_second, 103), ' ', '-')) as stdt_second,(replace(convert(NVARCHAR, endt_first, 103), ' ', '-')) as endt_first,(replace(convert(NVARCHAR, stdt_first, 103), ' ', '-')) as stdt_first,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance,(replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance from M_Finance_Year where id_fincance = "+id+"";
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
			System.out.println("sql error in M_Finance_Year.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddFinancialYear(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Finance_Year",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_fincance")
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
					System.out.println("Some error in M_Finance_Year.");
				}
		
		
		String query="insert into M_Finance_Year("+colName+") values("+value+")";
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
