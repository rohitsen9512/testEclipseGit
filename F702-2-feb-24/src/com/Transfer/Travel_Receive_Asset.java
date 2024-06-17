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


public class Travel_Receive_Asset extends HttpServlet {
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
		String dt_retrn_trvl="",id_wh="",dt_st_trvl="";
		
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("dt_retrn_trvl") !=null)
		{
			dt_retrn_trvl = request.getParameter("dt_retrn_trvl");
		}
		if(request.getParameter("id_wh") !=null)
		{
			id_wh = request.getParameter("id_wh");
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
		if(request.getParameter("dt_st_trvl") !=null)
		{
			dt_st_trvl = request.getParameter("dt_st_trvl");
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
	            	jobj = Travelsave(id,id_emp_user,id_wh,dt_retrn_trvl);	
	                break;
		        
		        case "Display":
	            	String tempSql="";
	            	String likeTempQuery="";
	            	DtoCommon dtoCommon = new DtoCommon();
	            	if(!word.equals(""))
        			{
        				 likeTempQuery = dtoCommon.MakeLikeTempQuery(word);
        			}
	        		if(!UserType.equals("SUPER")){
	        			String tempQuery = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
	        			tempSql= "select id_trvl,wh.id_wh,id_wh_dyn,ds_pro,ds_asst,no_mfr from A_Ware_House wh,T_Travel trvl where "+
		    					" trvl.id_wh=wh.id_wh and trvl.st_recv=0 and "+tempQuery+" "+likeTempQuery+"" ;
	        		}
	        		else
	        			tempSql= "select id_trvl,wh.id_wh,id_wh_dyn,ds_pro,ds_asst,no_mfr from A_Ware_House wh,T_Travel trvl where "+
		    					" trvl.id_wh=wh.id_wh and trvl.st_recv=0 "+likeTempQuery+"" ;
	            				
	        		jobj = dtoCommon.GetDataForDisplay(tempSql,  request);
	            	
	                break;
	             
		        case "Edit":
	            	jobj = TravelForEdit(id);	
	                break;
	                
		        case "CheckDate":
	            	jobj = CheckDate(dt_st_trvl,id);	
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
	
	
	public JSONObject CheckDate(String dt_alloc ,String id)
	{
		JSONObject jobj = new JSONObject();
		
		try
		{
		int t=1;
    	String  sql ="";
    	String d = "1900-01-01";
    	sql ="select dt_st_trvl from T_Travel where dt_st_trvl > '"+dt_alloc+"' and id_trvl="+id+" ";
    			  ps=con.prepareStatement(sql);
            		rs=ps.executeQuery();
            		    if(rs.next())
            		    {
            		    	t=0;
            		    	d = rs.getString(1);
            	        }
    		  
    	if(t == 1)
    	{
    		jobj.put("data", t);
    	}
    	else
    	{
    			jobj.put("data", t);
        		jobj.put("dt_st_trvl",d);
    	}	
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in G_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject TravelForEdit(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
			String sql="select wh.id_wh,id_wh_dyn,ds_pro,ds_asst,nm_emp,cd_emp,dt_st_trvl,dt_expc_trvl from A_Ware_House wh,M_Emp_User emp,T_Travel trvl where "+
					"id_emp_user=to_assign and trvl.id_wh=wh.id_wh and id_trvl="+id+"";
							
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
	
	



	

public JSONObject Travelsave(String id ,int id_emp_user,String id_wh,String dt_retrn_trvl)
{
	int j=0;
	JSONObject json=new JSONObject();
	try
	{
				stmt = con.createStatement();
				stmt.executeUpdate("update A_Ware_House set st_trvl=0 where id_wh="+id_wh+"");
				stmt.executeUpdate("update T_Travel set st_recv=1,dt_retrn_trvl='"+dt_retrn_trvl+"',recv_by="+id_emp_user+" where id_trvl="+id+"");
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
