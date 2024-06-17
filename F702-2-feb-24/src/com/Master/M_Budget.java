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

public class M_Budget extends HttpServlet {
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
		    
		String action = "",id="0",id_finance="",id_cc="",id_dept="",id_model="",id_bu="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_model") !=null)
		{
			id_model = request.getParameter("id_model");
		}
		if(request.getParameter("id_bu") !=null)
		{
			id_bu = request.getParameter("id_bu");
		}
		if(request.getParameter("id_cc") !=null)
		{
			id_cc = request.getParameter("id_cc");
		}
		if(request.getParameter("id_dept") !=null)
		{
			id_dept = request.getParameter("id_dept");
		}
		if(request.getParameter("id_finance") !=null)
		{
			id_finance = request.getParameter("id_finance");
		}
		
		
		
		
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddBudget(map,  request);	
	                break;
	            case "SaveAdHoc":
	            	jobj = SaveAdHoc(id);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayBudget(id,id_finance);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayBudget(id,id_finance);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateBudget(map,id,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteBudget(id);	
	                break; 
	            case "DropDownResult":
	            	jobj = DropDownResult();	
	                break;
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(id_bu,id_dept,id_finance,id_model);	
	                break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in M_Budget.");
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
	
	public JSONObject CheckExitsVal(String id_bu,String id_dept,String id_finance,String id_model)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_bud from M_Budget where id_bu="+id_bu+" and id_dept="+id_dept+" and id_finance="+id_finance+" and id_model="+id_model+"";
		try 
		{
			System.out.println(query);
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
			
		catch (Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject DropDownResult()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_bud,annual_bud from M_Budget";
		
		
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
			System.out.println("sql error in M_Budget.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DeleteBudget(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		String query = "delete M_Budget where id_bud = "+id+"";
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
	
	public JSONObject UpdateBudget(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		
		
		try
		{
				rs = Common.GetColumns("M_Budget",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_bud")
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
					System.out.println("Some error in M_Budget.");
				}
		
		String query="update M_Budget set "+col_Val+" where id_bud="+id+"";
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
	
	public JSONObject SaveAdHoc(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		
		int j;
		  String sql="select id_bud from M_Budget where adhoc='YES' and id_finance="+id+" ";
		  System.out.println(sql);
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		
		  if(rs.next())
		  {
			  j=2;
		    jobj.put("data",j);
		  }
		  else
		  {
			   sql="insert into M_Budget(id_bu,id_s_function,id_cc,id_dept,id_finance,annual_bud,adhoc) "
			  		+ " select id_bu,id_s_function,id_cc,id_dept,"+id+",1000000,'Yes' from M_BU ";
				System.out.println(sql);
				 ps=con.prepareStatement(sql);
					j=ps.executeUpdate();
					if(j > 0)
					{
						j=1;
					}
		  }
		  jobj.put("data",j);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayBudget(String id,String id_finance)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		
		  String
		  sql="select id_bud,nm_bu,nm_dept,(replace(convert(NVARCHAR, fy.end_finance, 103), ' ', '-')) as end_finance,(replace(convert(NVARCHAR, fy.std_finance, 103), ' ', '-')) as std_finance,annual_bud,b.id_dept,b.id_cc,b.id_s_function,b.id_bu,b.id_assetdiv,b.id_s_assetdiv,b.id_model from M_Budget b,M_Dept d,M_Finance_Year fy,M_BU cc"
		  +
		 " where b.id_dept=d.id_dept and b.id_finance=fy.id_fincance and b.id_bu=cc.id_bu  and fy.id_fincance="
		 +id_finance+" order by nm_bu";
		 sql="select nm_s_assetdiv,nm_assetdiv,nm_emp,nm_cc,nm_s_function,id_bud,nm_bu,nm_dept,(replace(convert(NVARCHAR, fy.end_finance, 103), ' ', '-')) as end_finance,\r\n" + 
		 		"(replace(convert(NVARCHAR, fy.std_finance, 103), ' ', '-')) as std_finance,annual_bud,b.id_dept,b.id_cc,\r\n" + 
		 		"b.id_s_function,b.id_bu,b.id_assetdiv,b.id_s_assetdiv,b.id_model from M_Budget b,M_Dept d,M_Finance_Year fy,M_BU cc,\r\n" + 
		 		"M_Emp_User emp,M_Asset_Div amd,M_Company_Costcenter mcc,M_S_Function msf,M_Subasset_Div sbd\r\n" + 
		 		"where b.id_dept=d.id_dept and b.id_finance=fy.id_fincance and b.id_bu=cc.id_bu and emp.id_emp_user=b.id_spoc\r\n" + 
		 		" and amd.id_assetdiv=b.id_assetdiv and mcc.id_cc=b.id_cc and msf.id_s_function=b.id_s_function and sbd.id_s_assetdiv=b.id_s_assetdiv"
		 		+" and fy.id_fincance="+id_finance+" order by nm_bu";
		  
		/*
		 * String
		 * sql="select id_bud,nm_dept,(replace(convert(NVARCHAR, fy.end_finance, 103), ' ', '-')) as end_finance,(replace(convert(NVARCHAR, fy.std_finance, 103), ' ', '-')) as std_finance,annual_bud,b.id_dept from M_Budget b,M_Dept d,M_Finance_Year fy "
		 * +
		 * " where b.id_dept=d.id_dept and b.id_finance=fy.id_fincance  and fy.id_fincance="
		 * +id_finance+" order by nm_dept";
		 */
		 System.out.println(sql);
		if(!id.equals("0"))
		{
			sql ="select * from M_Budget where id_bud = "+id+"";
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
			System.out.println("sql error in M_Budget.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddBudget(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		String id_finance=map.get("id_finance");
		String id_dept=map.get("id_dept");
		String id_bu=map.get("id_bu");
		String id_model=map.get("id_model");
		String sql ="select * from M_Budget where id_finance="+id_finance+" and id_dept="+id_dept+" and id_bu="+id_bu+" and id_model="+id_model+" ";
		try {
			System.out.println(sql);
			PreparedStatement ps1=con.prepareStatement(sql);
			rs=ps1.executeQuery();
			if(rs.next())
			{
				j=3;
			}
			else
			{
			rs = Common.GetColumns("M_Budget",  request);
			
			
			while (rs.next())
			{
			
				if(rs.getString(1) !="id_bud")
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
			

			String query="insert into M_Budget("+colName+") values("+value+")";
			System.out.println(query);
			 ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
			}
	
}
				
			catch(Exception e)
				{
					System.out.println("Some error in M_Budget.");
				}
		
		try 
		{
		
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
}	