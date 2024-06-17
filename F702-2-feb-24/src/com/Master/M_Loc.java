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


public class M_Loc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ResultSet rs1=null;
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
		    
		String action = "",id="0",value="",ColName="",searchWord="",locIds="";
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
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}	
		if(request.getParameter("locIds") !=null)
		{
			locIds = request.getParameter("locIds");
		}
		String tempQuery="";
		if(!locIds.equals(""))
		{
			String id_loc[] = locIds.split(",");
			for(int i=0 ; i < id_loc.length ; i++)
			{
				if(tempQuery.equals(""))
				{
					tempQuery = " where id_loc = "+id_loc[i]+"";
				}
				else
				{
					tempQuery += " or id_loc = "+id_loc[i]+"";
				}
			}
		}
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddLocation(map,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayLocation(id,searchWord);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayLocation(id,searchWord);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateLocation(map,id,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteLocation(id);	
	                break; 
	                
	            case "DropDownResult":
	            	jobj = DropDownResult(id);	
	                break; 
	            
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	            case "GetAllSubloc":
	            	jobj = GetAllSubloc(tempQuery);	
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
	
	public JSONObject GetAllSubloc( String tempQuery)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_sloc,nm_subl from M_Subloc "+ tempQuery;
		//System.out.println("tempQuery"+sql);
		
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
			System.out.println(e+"sql error in Location master.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_loc from M_Loc where "+ColName+" = '"+value+"'";
		try 
		{
			System.out.println(query);
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
	
	public JSONObject DropDownResult(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String sql="select id_loc,nm_loc from M_Loc where id_div="+id+" order by nm_loc" ;
		if(id.equals("0"))
		{
			sql="select id_loc,nm_loc from M_Loc order by nm_loc";
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
			System.out.println(e+"sql error in Location master.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject DeleteLocation(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			System.out.println(id);
			System.out.println("iddelete");
			String query = "select id_wh from A_Ware_House where id_loc = "+id+"";
			PreparedStatement ps=con.prepareStatement(query);
			String query1 = "select id_emp_user from M_Emp_User where id_loc = "+id+"";
			PreparedStatement ps1=con.prepareStatement(query1);
			rs=ps.executeQuery();
			rs1=ps1.executeQuery();
			if(rs.next() || rs1.next())
			{
				j=2;
			}
			else
			{
				 query = "select id_loc from M_Subloc where id_loc = "+id+"";
				
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					j=3;
				}
				else
				{
					query = "delete M_Loc where id_loc = "+id+"";
				
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					
					if(j > 0)
					{
						j=1;
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
	
	public JSONObject UpdateLocation(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Loc",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_loc")
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
					System.out.println("Some error in Location master.");
				}
		
		String query="update M_Loc set "+col_Val+" where id_loc="+id+"";
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
	
	
	
	public JSONObject DisplayLocation(String id , String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select l.* from  M_Loc l where ( nm_loc like '"+searchWord+"%') order by nm_loc";
		System.out.println(sql);
		if(!id.equals("0"))
		{
			sql ="select * from M_Loc where id_loc = "+id+"";
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
			System.out.println(e+"sql error in Location master.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddLocation(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Loc",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_loc")
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
					System.out.println("Some error in Location master.");
				}
		
		String query="insert into M_Loc("+colName+") values("+value+")";
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
