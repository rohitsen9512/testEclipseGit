package com.HelpDesk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
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

import dto.Common.UserAccessData;


public class Ess_New_Ticket extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
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
		      if(parts[0].equals("dt") && !paramValues.equals(""))
		      {
		    	  paramValues = request.getParameter(paramName);
		    	  
		    	  Date d = userDateFormat.parse(paramValues);  
			      map.put(paramName,dateFormatNeeded.format(d));
		      }
		      else
		      {
		    	  paramValues = request.getParameter(paramName);
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		    
		String action = "",id="0",value="",ColName="",typeOfResult="",searchWord="";
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
		if(request.getParameter("typeOfResult") !=null)
		{
			typeOfResult = request.getParameter("typeOfResult");
		}
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}
		
		
		
		HttpSession session = request.getSession(); 
		int id_emp_user = (int)session.getAttribute("id_emp_user");
		String UserTupe = (String)session.getAttribute("userType");		
		try
		{
			
			con=Common.GetConnection(request);
			UserAccessData uad = new UserAccessData();
			switch (action)
	        {
	            case "Save":
	            	jobj = AddNewTicket(map,id_emp_user,  request);	
	                break;
	                
	            case "List":
	            	//jobj = DisplayTickets(searchWord,typeOfResult,id);	
	            	String sql="";
	            	if(typeOfResult.equals("Assigned_To"))
	            		sql="select id_ticket,ticket_no,priority,state,reqEmp.nm_emp as reqEmp,agnTo.nm_emp as agnEmp from HD_Tickets t,M_Emp_User reqEmp,M_Emp_User agnTo "+
	            				" where t.req_by=reqEmp.id_emp_user and t.assign_to=agnTo.id_emp_user and t.state !='Closed' and t.state='Active' and t.assign_to=+id_emp_user+";
	            		
	            		//sql="select id_ticket,t.id_grp,t.id_sgrp,ticket_no,priority,state,reqEmp.nm_emp as reqEmp,agnTo.nm_emp as agnEmp,wh.id_wh_dyn as nm_prod from HD_Tickets t,M_Emp_User reqEmp,M_Emp_User agnTo,A_Ware_House wh "+
	            			//" where t.req_by=reqEmp.id_emp_user and t.assign_to=agnTo.id_emp_user and t.id_prod=wh.id_wh and t.state !='Closed' and t.state='Active' and t.assign_to="+id_emp_user+"";
	            	else if(typeOfResult.equals("Closed"))
	            		sql="select id_ticket,t.id_grp,t.id_sgrp,ticket_no,priority,state,reqEmp.nm_emp as reqEmp,agnTo.nm_emp as agnEmp,wh.id_wh_dyn as nm_prod from HD_Tickets t,M_Emp_User reqEmp,M_Emp_User agnTo,A_Ware_House wh "+
			            		" where t.req_by=reqEmp.id_emp_user and t.assign_to=agnTo.id_emp_user and t.id_prod=wh.id_wh and t.state='Closed'";
		            	
	            	else if(typeOfResult.equals("All"))
	            		//sql="select * from HD_Tickets where req_by="+id_emp_user+"";
	            		sql="select id_ticket,ticket_no,priority,state,reqEmp.nm_emp as reqEmp from HD_Tickets t,M_Emp_User reqEmp "+
	            				" where t.req_by=reqEmp.id_emp_user and req_by="+id_emp_user+" ";
	            		
	            	else if(typeOfResult.equals("Inprogress"))
	            		sql="select id_ticket,t.id_grp,t.id_sgrp,ticket_no,priority,state,reqEmp.nm_emp as reqEmp,agnTo.nm_emp as agnEmp,wh.id_wh_dyn as nm_prod from HD_Tickets t,M_Emp_User reqEmp,M_Emp_User agnTo,A_Ware_House wh "+
		            			" where t.req_by=reqEmp.id_emp_user and t.assign_to=agnTo.id_emp_user and t.id_prod=wh.id_wh and t.state !='Closed' and t.assign_to="+id_emp_user+"";

	            	jobj = uad.GetDataAsJsonFormate(sql,  request);
	            	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayTickets(id,  request);	
	            	
	                break;
	                
	            case "Update":
	            	jobj = UpdateTicket(map,id,id_emp_user,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteDepartment(id);	
	                break; 
	                
	            case "DropDownResult":
	            	jobj = DropDownResult();	
	                break; 
	                
	            case "NextTicketNumber":
	            	jobj = DynamicTicketNumber();	
	                break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in Department Master.");
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
	
	public JSONObject DynamicTicketNumber()
	{
		JSONObject json=new JSONObject();
		String finyear="",ticktNo2="";
		int ticktNo=1;
		try 
		{
			ps=con.prepareStatement("select max(id_ticket) from HD_Tickets");
			rs=ps.executeQuery();
			
				if(rs.next())
				{
					ticktNo =	rs.getInt(1) +1;
					String formatValue  = String.format("%05d", ticktNo);
					
					ticktNo2="TICKET-"+formatValue;
				}
				json.put("data", ticktNo2);
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_ticket from HD_Tickets where "+ColName+" = '"+value+"'";
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
		String sql="select id_ticket,nm_dept from HD_Tickets order by nm_dept";
		
		
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
			System.out.println("sql error in  HD_Tickets.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DeleteDepartment(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
		String query = "select id_emp_user from M_Emp_User where id_ticket = "+id+"";
		PreparedStatement ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		if(rs.next())
		{
			j=2;
		}
		else
			{
				 query = "delete HD_Tickets where id_ticket = "+id+"";
				
				 	ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						j=1;
					}
				
					json.put("data",j);
				
			}
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject UpdateTicket(HashMap<String, String> map , String id,int id_emp_user,HttpServletRequest request)
	{
		String col_Val="",id_ticket=id;
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("HD_Tickets",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_ticket")
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
					System.out.println("Some error in New Ticket.");
				}
		
		String query="";
		try 
		{
			
			/*String assign_grp = map.get("assign_grp");
			String assign_to = map.get("assign_to");
			*/
			/*if(!assign_grp.equals("")){
				String oldAssign_grp="";
				query = "select assign_grp from HD_Tickets where id_ticket="+id+" ";
				PreparedStatement ps=con.prepareStatement(query);
				rs = ps.executeQuery();
				if(rs.next()){
					oldAssign_grp = rs.getString(1);
				}
				if(!oldAssign_grp.equals(assign_grp)){
					HelpDeskModel hdm = new HelpDeskModel();
					hdm.SendEmailToAssignmentGroup(assign_grp,id_ticket);
				}
			}*/
			/*if(!assign_to.equals("")){
				String oldAssign_to="";
				query = "select assign_to from HD_Tickets where id_ticket="+id+" ";
				PreparedStatement ps=con.prepareStatement(query);
				rs = ps.executeQuery();
				if(rs.next()){
					oldAssign_to = rs.getString(1);
				}
				if(!oldAssign_to.equals(assign_to)){
					HelpDeskModel hdm = new HelpDeskModel();
					hdm.SendEmailToAssignee(assign_to,id_ticket);
				}
			}*/
			
			
			query="update HD_Tickets set "+col_Val+" where id_ticket="+id+"";
			
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			
			String work_note = map.get("work_note");   
			
	         if(!work_note.equals("")){
	        	 
	        	 query="insert into HD_Ticket_History(work_note,id_ticket,comment_by) values('"+work_note+"',"+id+","+id_emp_user+")";
	        	 ps=con.prepareStatement(query);
	     		j=ps.executeUpdate();
	     		
	     					
	         }
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
	
	
	
	public JSONObject DisplayTickets(String id,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,t.* from HD_Tickets t where id_ticket = "+id+"";
		UserAccessData uad = new UserAccessData();
		jobj = uad.GetDataAsJsonFormate(sql,  request);
    	
		sql="select work_note,(replace(convert(NVARCHAR, dt_comment, 103), ' ', '-')) as dt_comment,nm_emp from HD_Ticket_History th,M_Emp_User emp where th.comment_by=emp.id_emp_user and id_ticket="+id+" order by th.id DESC";

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
		    jobj.put("history", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in New Ticket.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddNewTicket(HashMap<String, String> map,int id,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("HD_Tickets",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_ticket")
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
					System.out.println("Some error in New Ticket.");
				}
		String id_ticket="";
		String query="insert into HD_Tickets("+colName+",req_by) values("+value+","+id+")";
		try 
		{
			stmt=con.createStatement();
			 stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			 rs = stmt.getGeneratedKeys();
	            if (rs.next()) {
	            	id_ticket = rs.getString(1);
	            	j=1;
	            } 
	         String work_note = map.get("work_note");   
	         if(!work_note.equals("")){
	        	 
	        	 query="insert into HD_Ticket_History(work_note,id_ticket,comment_by) values('"+work_note+"',"+id_ticket+","+id+")";
	        	 ps=con.prepareStatement(query);
	     		j=ps.executeUpdate();
	         j=1;
	         }
	         
			/*if(j > 0)
			{
				j=1;
			}*/
		
			json.put("data",j);
			
			String assign_grp = map.get("assign_grp");
			String assign_to = map.get("assign_to");
			
			if(!assign_grp.equals("")){
				HelpDeskModel hdm = new HelpDeskModel();
				hdm.SendEmailToAssignmentGroup(id,id_ticket,  request);
			}
			if(!assign_grp.equals("")){
				HelpDeskModel hdm = new HelpDeskModel();
				hdm.SendEmailToAssignee(assign_to,id_ticket,  request);
			}
			
			String add_by = String.valueOf(id);
			HelpDeskModel hdm = new HelpDeskModel();
			hdm.SendEmailToOpenByAndAdditional(add_by,id_ticket,map.get("email_ids"),  request);
			
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
}
