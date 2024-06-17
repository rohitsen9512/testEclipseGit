package com.Master;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.DtoCommon;
import dto.Common.UserAccessData;


public class M_Group extends HttpServlet {
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
		 UserAccessData uad = new UserAccessData();
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      paramValues=paramValues.replace("'", "''");
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0",value="",ColName="",searchWord="";
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
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":

	            	jobj = AddGroup(map,  request);	
	                break;
            case "SaveEMPGroup":
                 
             String chk[] = request.getParameterValues("bulkInstallEmp");	
            	System.out.println(id);	            	
            	try
    				{
	            		int j = 0;
            			String colName="",value1="",query="",itemName="",slNo="",nm_model="";
            		 
            
            			for(int i=0; i<chk.length;i++)
        				{
            				query = "insert into M_GroupMember (id_grp,id_emp_user) values("+id+","+chk[i]+")";
    	    				System.out.println(query);
    	    				ps=con.prepareStatement(query);
    	    				j=ps.executeUpdate();
    	    				System.out.println("chanchal");
    	    				if(j>0)
    	    				{
    	    					jobj.put("data",j);
    	    				}             
        				}
                    	
	    				  
	    				 
    				}
	            	
	            	catch(Exception e)
    				{
    					System.out.println("Error "+e);
    				}
            	
	            	
	            	
	            	
	                break;
            case "UpdateEMPGroup":
            		
            	 String chk1[] = request.getParameterValues("bulkUninstallEmp");	
                	System.out.println(chk1);	            	
                	try
        				{
    	            		int k = 0;
                			//String colName="",value1="";
    	            		String qry="";
                		 
                
                			for(int i=0; i<chk1.length;i++)
            				{
                				System.out.println(qry);
                				qry = "delete M_groupmember where id_emp_user="+chk1[i]+" and id_grp="+id+"";
        	    				
        	    				ps=con.prepareStatement(qry);
        	    				k=ps.executeUpdate();
        	    				System.out.println("chanchal");
        	    				if(k>0)
        	    				{
        	    					jobj.put("data",k);
        	    				}             
            				}
                        	
    	    				  
    	    				 
        				}
    	            	
    	            	catch(Exception e)
        				{
        					System.out.println("Error "+e);
        				}
                	
    	            	
   	            	
   	            	
   	                break;
	                
	                
	            case "Display":
	            	jobj = DisplayGroup(id,searchWord);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayGroup(id,searchWord);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateGroup(map,id,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteGroup(id);	
	                break; 
	                
	            case "DropDownResult":
	            	jobj = DropDownResult(id);	
	                break; 
	            
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	            	//con.close();
	                break;
	            case "Search":
	            	
	            	jobj= AssignGroup(searchWord);
	            	break;
	            case "GroupEdit":
	            	
	            	jobj= AssignGroupEdit(id,searchWord);
	                break;
    				//jobj = uad.QueryMacker1(tempSql,  request);
    			
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Group Master.");
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
	
	
	public JSONObject AssignGroupEdit(String id,String searchWord) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		JSONObject json=new JSONObject();
		String tempSub="",tempSql;
		tempSql="select  emp.id_emp_user,emp.id_emp, emp.nm_emp  from M_groupmember gp,M_Emp_User emp, m_group grp where ( emp.nm_emp like '"+searchWord+"%') and grp.id_grp=gp.id_grp and emp.id_emp_user=gp.id_emp_user and gp.id_grp='"+id+"' order by emp.nm_emp";
		try 
		{
				
			System.out.println(tempSql);
			ps=con.prepareStatement(tempSql);
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
				
			
			System.out.println("Chanchal"+ jarr);
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return jobj;
	}
	


	public JSONObject AssignGroup(String searchWord ) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		JSONObject json=new JSONObject();
		String tempSub="",tempSql;
		tempSql="select  emp.id_emp_user,emp.nm_emp,emp.id_emp from M_Emp_User emp  where ( nm_emp like '%"+searchWord+"%' or id_emp like '%"+searchWord+"%') and id_emp_user not in (select id_emp_user from M_GroupMember) ";
		try 
		{
				
			System.out.println(tempSql);
			ps=con.prepareStatement(tempSql);
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
				
			System.out.println("Chanchal");
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return jobj;
	}

	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_grp from M_Group where "+ColName+" = '"+value+"'";
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
			System.out.println("Chanchal");
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
		String sql="select id_grp,nm_grp from M_Group where id_grp="+id+" order by nm_Grp" ;
		if(id.equals("0"))
		{
			sql="select id_grp,nm_grp from M_Group order by nm_grp";
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
			System.out.println("sql error in Group master.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject DeleteGroup(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			System.out.println(id);
			String query = "select id_wh from A_Ware_House where id_grp = "+id+"";
			PreparedStatement ps=con.prepareStatement(query);
			String query1 = "select id_emp_user from M_Emp_User where id_grp = "+id+"";
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
	
	public JSONObject UpdateGroup(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Group",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_grp")
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
					System.out.println("Some error in Group master.");
				}
		
		String query="update M_Group set "+col_Val+" where id_grp="+id+"";
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
	
	
	
	public JSONObject DisplayGroup(String id , String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select l.* from  M_Group l where ( nm_grp like '"+searchWord+"%') order by nm_grp";
		System.out.println(sql);
		if(!id.equals("0"))
		{
			sql ="select * from M_Group where id_grp = "+id+"";
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
			System.out.println("sql error in Group master.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddGroup(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Group",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_grp")
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
					System.out.println("Some error in Group master.");
				}
		
		String query="insert into M_Group("+colName+") values("+value+")";
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
