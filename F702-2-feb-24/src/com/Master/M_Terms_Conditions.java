package com.Master;

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


public class M_Terms_Conditions extends HttpServlet {
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
		      paramValues=paramValues.replace("'", "''");
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0",value="",ColName="",TC="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("TC") !=null)
		{
			TC = request.getParameter("TC");
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

		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddTermsConditions(map,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayTermsConditions(id);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayTermsConditions(id);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateTermsConditions(map,id,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteTermsConditions(id);	
	                break; 

	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	                
	            case "DropDownResult":
	            	jobj = DropDownResult();	
	                break; 
	            case "GetTermAndCondition":
	            	jobj = GetTermAndCondition(TC);	
	                break;

	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in TC."+e);
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
	
	public JSONObject GetTermAndCondition(String TC)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_t_c,t_and_c from M_Term_Condition where "+TC+"='yes'";
		
		
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
			System.out.println("sql error in M_Term_Condition.");
		}
		 
		return jobj;
		
	}
	
	public JSONObject DropDownResult()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_t_c,t_and_c from M_Term_Condition";
		
		
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
			System.out.println("sql error in M_Term_Condition.");
		}
		 
		return jobj;
		
	}
	
	
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_t_c from M_Term_Condition where "+ColName+" = '"+value+"'";
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
	
	
	public JSONObject DeleteTermsConditions(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		
		try 
		{
			
				String query = "delete M_Term_Condition where id_t_c = "+id+"";
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
	
	public JSONObject UpdateTermsConditions(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_TERM_CONDITION",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_t_c")
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
					System.out.println("Some error in TC.");
				}
		try 
		{
		stmt=con.createStatement();
		stmt.executeUpdate("update M_Term_Condition set po_tc='No',quo_tc='No',cpo_tc='No',spo_tc='No' where id_t_c="+id+"");
		
		String query="update M_Term_Condition set "+col_Val+" where id_t_c="+id+"";
		
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
	
	
	
	public JSONObject DisplayTermsConditions(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select * from M_Term_Condition order by t_and_c";
		if(!id.equals("0"))
		{
			sql +=" where id_t_c = "+id+"";
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
			System.out.println("sql error in TC.");
		}
		 
		return jobj;
	}
	
	public JSONObject AddTermsConditions(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_TERM_CONDITION",  request);
				
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_t_c")
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
					System.out.println("Some error in TC.");
				}
		
		String query="insert into M_Term_Condition("+colName+") values("+value+")";
		System.out.println(query);
		
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
