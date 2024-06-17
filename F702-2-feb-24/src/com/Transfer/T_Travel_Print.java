package com.Transfer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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

import dto.Common.DtoCommon;


public class T_Travel_Print extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	Date currDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String today1 = sdf.format(currDate);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		 HttpSession session = request.getSession();  
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="",word="";
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("id_flr") !=null)
		{
			id = request.getParameter("id_flr");
		}
		if(request.getParameter("id_cc") !=null)
		{
			id = request.getParameter("id_cc");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}
		try
		{
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType");
			int UserId = (int)session.getAttribute("UserId");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        case "Save":
	            	jobj = Travelsave(map,id_emp_user,id,  request);	
	                break;
		        
		        case "Display":
		        	String tempSql="";
	            	String likeTempQuery="";
	            	DtoCommon dtoCommon = new DtoCommon();
	            	if(!word.equals(""))
        			{
        				 likeTempQuery = dtoCommon.MakeLikeTempQuery(word);
        			}
	        		if(!UserType.equals("Super")){
	        			String tempQuery = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
	        			tempSql= "select id_trvl,wh.id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst from A_Ware_House wh,T_Travel trvl where trvl.id_wh=wh.id_wh and trvl.st_aprvl =1 and trvl.st_recv=0 and "+tempQuery+" "+likeTempQuery+"" ;
	        		}
	        		else
	        			tempSql= "select id_trvl,wh.id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst from A_Ware_House wh,T_Travel trvl where trvl.id_wh=wh.id_wh and trvl.st_aprvl =1 and trvl.st_recv=0 "+likeTempQuery+"" ;
	            				
	        		jobj = dtoCommon.GetDataForDisplay(tempSql,  request);
	            	
	                break;
	             
		        case "Edit":
	            	jobj = TravelForEdit(id);	
	                break;
	        }
		
		request.setAttribute("data", jobj.toString());
		response.getWriter().write(jobj.toString());
		}
	       catch(Exception e)
			{
				System.out.println("Error in A_Install.");
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
	
	public JSONObject TravelForEdit(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
			String sql="select id_wh,id_wh_dyn,ds_pro,ds_asst,nm_emp,cd_emp from A_Ware_House wh,M_Emp_User emp where id_emp_user=to_assign and id_wh="+id+"";
							
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			   ResultSetMetaData metaData = rs.getMetaData();
			    int columns = metaData.getColumnCount();
			    JSONObject jobj2 = new JSONObject();
			    while(rs.next())
			    {
			    	jobj2 = new JSONObject();
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
			System.out.println("sql error in T_Transfer.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject UpdateDataForIntraUnitTransferRequest(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("A_Ware_House",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_wh")
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
					System.out.println("Some error in T_Transfer_Request.");
				}
		
		String query="update A_Iut_History set "+col_Val+" where id_iut="+id+"";
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




	

public JSONObject Travelsave(HashMap<String, String> map ,int id_emp_user,String id,HttpServletRequest request)
{
	String col_Val="",colName="",value="";
	int j=0;
	JSONObject json=new JSONObject();
	try
	{
			rs = Common.GetColumns("T_Travel",  request);
			
					
					while (rs.next())
					{
					
						if(rs.getString(1) !="id_trvl")
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
					
				String query="insert into T_Travel("+colName+",req_by) values("+value+","+id_emp_user+")";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();	
				stmt = con.createStatement();
				stmt.executeUpdate("update A_Ware_House set st_trvl=1 where id_wh="+id+"");
				j=1;
		
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
