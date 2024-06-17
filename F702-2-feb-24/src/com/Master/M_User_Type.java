package com.Master;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;

import dto.Common.UserAccessData;


public class M_User_Type extends HttpServlet {
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
		    
		String action = "",id="0",value="",ColName="",id_usertype="";
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
		if(request.getParameter("id_usertype") !=null)
		{
			id_usertype = request.getParameter("id_usertype");
		}
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddDivision(map, request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayDivision(id);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayDivision(id);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateDivision(map,id, request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteDivision(id);	
	                break; 
	                
	            case "DropDownResult":
	            	jobj = DropDownResult();	
	                break;
	            case "DropDownResultLoginProvision":
	            	jobj = DropDownResultLoginProvision();	
	                break;
	                
	            case "SubMenuDropDownResult":
	            
	        		JSONArray jarrsubusertype = new JSONArray();
	            	Properties prop = new Properties();	
	    			InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/financeAccess.properties");
	    			
	    			if (inputStream == null) {
	    				System.out.println("property file 'configuration.properties' not found in the classpath");
	    			}
	    			prop.load(inputStream);
	    			String menu=request.getParameter("menu");
	    		
	    			String SubMenu = prop.getProperty(menu);
	    			String submenuARR []=SubMenu.split(",");
	    			
	    			for (int i=0; i<submenuARR.length;i++) {
	    				JSONObject jobjsubusertype = new JSONObject();
	    			
	    				String splitarr []=submenuARR[i].split("-");
	    				for (int j=0; j==0;j++) {
	    					
	    					jobjsubusertype.put("userclass",splitarr[0]);
	    					jobjsubusertype.put("username",splitarr[1]);
	    				}
	    				jarrsubusertype.put(jobjsubusertype);
	    				
	    				
	    			}
	    		
	    			
	    			
	    		
	    			System.out.println(jarrsubusertype);
	    			  jobj.put("data", jarrsubusertype);
	                break;
	                
	            case "SubMenuDropDownResult1":
		            
	        		JSONArray jarrsubusertype1 = new JSONArray();
	            	Properties prop1 = new Properties();	
	            	Properties prop2 = new Properties();	
	    			InputStream inputStream1 = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/financeAccess.properties");
	    			
	    			if (inputStream1 == null) {
	    				System.out.println("property file 'configuration.properties' not found in the classpath");
	    			}
	    			prop1.load(inputStream1);
InputStream inputStream2 = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/subMenuUserAccess.properties");
	    			
	    			if (inputStream2 == null) {
	    				System.out.println("property file 'configuration.properties' not found in the classpath");
	    			}
	    			prop1.load(inputStream1);
	    			prop2.load(inputStream2);
	    			String menu1=request.getParameter("menu1");
	    			String user=request.getParameter("user");
	    		
	    			String SubMenu1 = prop1.getProperty(menu1);
	    			String submenuARR1 []=SubMenu1.split(",");
	    			
	    			
	    			String SubMenu2 = prop2.getProperty(user);
	    			String submenuARR2 []=SubMenu2.split(",");
	    			String submenuARR3 []=SubMenu1.split("[,-]+");
	    			
	    			
	    		        List<String> s1List = new ArrayList(Arrays.asList(submenuARR3));
	    		        for (String s : submenuARR2) {
	    		            if (s1List.contains(s)) {
	    		                s1List.remove(s);
	    		            } else {
	    		                
	    		            }
	    		             System.out.println("intersect on " + s1List);
	    		        }
	    		        String submenu []= s1List.toArray(new String[0]);
	    			

	    		     ArrayList<String> menu2 = new ArrayList<String>();
	    			for (int j=0; j<submenu.length;j++) {
	    				
	    			for (int i=0; i<submenuARR1.length;i++) {
	    				
	    				String splitarr []=submenuARR1[i].split("-");
	    				     System.out.println(splitarr[0]);
	    				     System.out.println(submenu[j]);
	    				     System.out.println((!(splitarr[0]).equals(submenu[j])));
	    					if(!(splitarr[0]).equals(submenu[j])) {
	    						
	    					}
	    					else {
	    						menu2.add(splitarr[0]);
	    					}
	    				}
	    			}
	    			String stringArray []= menu2.toArray(new String[0]);
	    			ArrayList<String> menu3 = new ArrayList<String>();
	    			for (int j=0; j<stringArray.length;j++) {
		    			for (int i=0; i<submenuARR2.length;i++) {
		    					if((submenuARR2[i]).equals(stringArray[j])) {
		    						menu3.add(submenuARR2[0]);
		    					}
		    				}
		    			}
	    			String stringArray2 []= menu2.toArray(new String[0]);
                        
	    			for (int j=0; j<stringArray2.length;j++) {
		    			for (int i=0; i<submenuARR1.length;i++) {
		    				JSONObject jobjsubusertype1 = new JSONObject();
		    				String splitarr []=submenuARR1[i].split("-");
		    				     System.out.println(splitarr[0]);
		    				     System.out.println(stringArray2[j]);
		    				     System.out.println((!(splitarr[0]).equals(stringArray2[j])));
		    					if((splitarr[0]).equals(stringArray2[j])) {
		    						
		    						jobjsubusertype1.put("userclass",splitarr[0]);
			    					jobjsubusertype1.put("username",splitarr[1]);
		    						
		    					
		    					}
		    					jarrsubusertype1.put(jobjsubusertype1);
		    				}
		    				
		    				
		    				
		    			}
	    			System.out.println("eee"+jarrsubusertype1);
	    			  jobj.put("data", jarrsubusertype1);
	                break;
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	            case "getUserType":
	            	jobj = getUserType(id_usertype);	
	                break;
//	            case "GiveAccess":
//	            	try {
//	            	
//		     				
//	        	         
//	            	UserAccess hdm = new UserAccess();
//					hdm.SendEmailToAssignee();
//		     				
//	        		}
//					catch (Exception e)
//					{
//						
//						e.printStackTrace();
//					}
//	                break;    
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in M_User_Type."+e);
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
		
		String query = "select id_usertype from M_User_Type where "+ColName+" = '"+value+"'";
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
	
	public JSONObject DropDownResult()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_usertype,nm_usertype from M_User_Type" ;
	
		
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
			System.out.println(e+"sql error in LocationN master.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DropDownResultLoginProvision()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		//String sql="select id_usertype,nm_usertype from M_User_Type" ;
	
		String sql="select mt.id_usertype,mt.nm_usertype from M_User_Type mt,M_User_Permission up  where mt.id_usertype=up.id_usertype ";
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
			System.out.println(e+"sql error in Location master.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject getUserType( String id_usertype)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
//		String sql="select nm_usertype from M_User_Type where id_usertype="+id_usertype+" ";
		
		String sql="select nm_usertype from M_User_Type where id_usertype="+id_usertype+" ";
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DeleteDivision(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		String sql="";
		String query = "delete M_User_Type where id_usertype = "+id+"";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				sql = "select id_usertype from M_Main_Menu_Access where id_usertype="+id+"";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					query="delete from M_Main_Menu_Access  where id_usertype="+id+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				}
				 sql = "select id_usertype from M_Sub_Menu_Access where id_usertype="+id+"";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					query="delete from M_Sub_Menu_Access where id_usertype="+id+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				}
				sql = "select id_usertype from M_User_Permission where id_usertype="+id+"";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					query="delete from M_User_Permission  where id_usertype="+id+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				}
				sql = "select id_usertype from M_User_Login where id_usertype="+id+"";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					query="delete from M_User_Login  where id_usertype="+id+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				}
			}
			j=1;
		
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject UpdateDivision(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="",sql="";
		int j=0;
		String TypeUser=map.get("nm_usertype");
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_User_Type", request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_usertype")
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
					System.out.println("Some error in M_User_Type.");
				}
		
		String query="update M_User_Type set "+col_Val+" where id_usertype="+id+"";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				 sql = "select id_usertype from M_Main_Menu_Access where id_usertype="+id+"";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					query="update M_Main_Menu_Access set User_Type='"+TypeUser+"' where id_usertype="+id+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				}
				 sql = "select id_usertype from M_Sub_Menu_Access where id_usertype="+id+"";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					query="update M_Sub_Menu_Access set User_Type='"+TypeUser+"' where id_usertype="+id+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				}
				sql = "select id_usertype from M_User_Permission where id_usertype="+id+"";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					query="update M_User_Permission set type_user='"+TypeUser+"' where id_usertype="+id+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				}
				sql = "select id_usertype from M_User_Login where id_usertype="+id+"";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					query="update M_User_Login set type_user='"+TypeUser+"' where id_usertype="+id+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
				}
			}
		j=1;
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	
	public JSONObject DisplayDivision(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select * from M_User_Type";
		if(!id.equals("0"))
		{
			sql +=" where id_usertype = "+id+"";
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
			System.out.println("sql error in M_User_Type.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddDivision(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_User_Type", request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_usertype")
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
					System.out.println("Some error in M_User_Type.");
				}
		
		String query="insert into M_User_Type("+colName+") values("+value+")";
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
