package com.Incident;

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
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection.Request;
import org.jsoup.select.Evaluator.IsEmpty;

import com.Common.Common;
//import com.Problem.ProblemModel;

import dto.Common.UserAccessData;


public class New_Incident extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs2=null;
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
		    	  paramValues=paramValues.replace("'", "''");
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		    
		String action = "",search_state="",state="",startdate="",enddate="",id_parent="",search_nm_category="",search_nm_subcategory="",id="0",value="",ColName="",typeOfResult="",searchWord="";
		int json=0;
		String priority="",note="";
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("search_state") !=null)
		{
			search_state = request.getParameter("search_state");
		}
		if(request.getParameter("search_nm_category") !=null)
		{
			search_nm_category = request.getParameter("search_nm_category");
		}
		if(request.getParameter("search_nm_subcategory") !=null)
		{
			search_nm_subcategory = request.getParameter("search_nm_subcategory");
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
		if(request.getParameter("priority") !=null)
		{
			priority = request.getParameter("priority");
		}
		if(request.getParameter("id_parent") !=null)
		{
			id_parent = request.getParameter("id_parent");
		}
		if(request.getParameter("note") !=null)
		{
			note = request.getParameter("note");
		}
		if(request.getParameter("state") !=null)
		{
			state = request.getParameter("state");
		}
		if(request.getParameter("startdate") !=null)
		{
			startdate = request.getParameter("startdate");
		}
		if(request.getParameter("enddate") !=null)
		{
			enddate = request.getParameter("enddate");
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
	            	jobj = AddNewTicket(map,id_emp_user,  request,id_parent);	
	                break;
	            case "Mytickets":
	            	//jobj = DisplayTickets(searchWord,typeOfResult,id);	
	            	String sql1="";
	            
	            	if(typeOfResult.equals("All"))
	            		//sql="select * from HD_Tickets where req_by="+id_emp_user+"";
	            		sql1="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,id_Incident,t.nm_category,t.short_description,Incident_no,priority,state,ch.nm_attribute,t.dt_update,t.assign_grp from INC_Incidents t,M_Emp_User reqEmp, M_choicelist ch" + 
	            				" where t.req_by=reqEmp.id_emp_user and ch.nm_field='priority' and t.priority=ch.val_attr and req_by="+id_emp_user+" order by id_Incident desc " ; 
	            			
	            		System.out.println(sql1);
	            	
	            	jobj = uad.GetDataAsJsonFormate(sql1,  request);
	            	
	                break;
	            case "List":
	            		
	            	String sql="";
	            	if(typeOfResult.equals("Assigned_To"))
	            		sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req, id_Incident,t.nm_category,t.short_description,t.assign_grp,t.nm_subcategory,Incident_no,priority,state,t.assign_grp,t.ph_no,t.short_description,reqEmp.nm_emp as reqEmp,agnTo.nm_emp as agnEmp,ch.nm_attribute,t.dt_update,(select emp.nm_emp from M_emp_user emp where t.edit_by=emp.id_emp_user) as edit_name,(select gm.nm_grp from M_Group gm where t.assign_grp=gm.id_grp) as grpname,(select emp.nm_emp from M_Emp_User emp where t.assign_to=emp.id_emp_user) as assignemp from INC_Incidents t,M_Emp_User reqEmp,M_Emp_User agnTo,M_choicelist ch "+
	            				" where t.req_by=reqEmp.id_emp_user and t.assign_to=agnTo.id_emp_user and  ch.nm_field='priority' and t.priority=ch.val_attr and (t.state = 'New' or t.state ='Inprogress' or t.state = 'Hold')  and t.assign_to="+id_emp_user+" order by id_Incident desc";
	            		
	            		else if(typeOfResult.equals("Resolved"))
	            			sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,id_Incident,t.nm_category,t.short_description,t.assign_grp,t.nm_subcategory,Incident_no,priority,state,t.assign_grp,t.ph_no,t.short_description,reqEmp.nm_emp as reqEmp ,ch.nm_attribute,t.dt_update,(select emp.nm_emp from M_emp_user emp where t.edit_by=emp.id_emp_user) as edit_name,(select gm.nm_grp from M_Group gm where t.assign_grp=gm.id_grp) as grpname,(select emp.nm_emp from M_Emp_User emp where t.assign_to=emp.id_emp_user) as assignemp from INC_Incidents t,M_Emp_User reqEmp, M_choicelist ch "+
            				" where t.req_by=reqEmp.id_emp_user and t.state='Resolved' and  ch.nm_field='priority' and t.priority=ch.val_attr and t.assign_to="+id_emp_user+" order by id_Incident desc";
	            	
	            	else if(typeOfResult.equals("All"))
	            		
	            		sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,id_Incident,t.nm_category,t.short_description,t.assign_grp,t.nm_subcategory,Incident_no,priority,state,t.assign_grp,t.ph_no,t.short_description,reqEmp.nm_emp as reqEmp,ch.nm_attribute,t.dt_update,(select emp.nm_emp from M_emp_user emp where t.edit_by=emp.id_emp_user) as edit_name,(select gm.nm_grp from M_Group gm where t.assign_grp=gm.id_grp) as grpname,(select emp.nm_emp from M_Emp_User emp where t.assign_to=emp.id_emp_user) as assignemp from INC_Incidents t,M_Emp_User reqEmp, M_choicelist ch "+
	            				" where t.req_by=reqEmp.id_emp_user  and ch.nm_field='priority' and t.priority=ch.val_attr DESC order by id_Incident desc";
	            		
	            	else if(typeOfResult.equals("Inprogress"))
	            		sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,id_Incident,t.nm_category,t.short_description,t.assign_grp,t.nm_subcategory,Incident_no,priority,state,t.assign_grp,t.ph_no,t.short_description,reqEmp.nm_emp as reqEmp,ch.nm_attribute,t.dt_update,(select emp.nm_emp from M_emp_user emp where t.edit_by=emp.id_emp_user) as edit_name,(select gm.nm_grp from M_Group gm where t.assign_grp=gm.id_grp) as grpname,(select emp.nm_emp from M_Emp_User emp where t.assign_to=emp.id_emp_user) as assignemp from INC_Incidents t,M_Emp_User reqEmp, M_choicelist ch"+
	            				" where t.req_by=reqEmp.id_emp_user and ch.nm_field='priority' and t.priority=ch.val_attr and (t.state='Inprogress'  or t.state='Hold')order by id_Incident desc";
	            	
	            	else if(typeOfResult.equals("New"))
	            		sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,id_Incident,t.nm_category,t.short_description,t.assign_grp,t.nm_subcategory,Incident_no,priority,state,t.assign_grp,t.ph_no,t.short_description,reqEmp.nm_emp as reqEmp,ch.nm_attribute,t.dt_update,(select emp.nm_emp from M_emp_user emp where t.edit_by=emp.id_emp_user) as edit_name,(select gm.nm_grp from M_Group gm where t.assign_grp=gm.id_grp) as grpname,(select emp.nm_emp from M_Emp_User emp where t.assign_to=emp.id_emp_user) as assignemp from INC_Incidents t,M_Emp_User reqEmp, M_choicelist ch"+
	            				" where t.req_by=reqEmp.id_emp_user and ch.nm_field='priority' and t.priority=ch.val_attr and (t.state='New')order by id_Incident desc";
	            	
	            	else if(typeOfResult.equals("Hold"))
	            		sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,id_Incident,t.nm_category,t.short_description,t.assign_grp,t.nm_subcategory,Incident_no,priority,state,t.assign_grp,t.ph_no,t.short_description,reqEmp.nm_emp as reqEmp,ch.nm_attribute,t.dt_update,(select emp.nm_emp from M_emp_user emp where t.edit_by=emp.id_emp_user) as edit_name,(select gm.nm_grp from M_Group gm where t.assign_grp=gm.id_grp) as grpname,(select emp.nm_emp from M_Emp_User emp where t.assign_to=emp.id_emp_user) as assignemp from INC_Incidents t,M_Emp_User reqEmp, M_choicelist ch"+
	            				" where t.req_by=reqEmp.id_emp_user and ch.nm_field='priority' and t.priority=ch.val_attr and ( t.state='Hold')order by id_Incident desc";
	            	
	            	else if(typeOfResult.equals("Search_All")){

	            		String sqlTemp="";
	            		if(!search_state.equals("All")){
		            		if(!search_nm_category.equals("") && search_nm_subcategory.equals("") && enddate.equals("")&& startdate.equals(""))
		            			sqlTemp = " and t.state='"+search_state+"'";
		            		else if(search_nm_category.equals("") && search_nm_subcategory.equals("")&& enddate.equals("")&& startdate.equals(""))
		            			sqlTemp = " and t.state='"+search_state+"' ";
		            		else if(!search_nm_category.equals("") && !search_nm_subcategory.equals("")&& !enddate.equals("")&& !startdate.equals(""))
		            			sqlTemp = " and t.state='"+search_state+"' and t.nm_category='"+search_nm_category+"' and t.nm_subcategory='"+search_nm_subcategory+"'and  t.dt_update BETWEEN '"+startdate+"' AND '"+enddate+"' ";
		            		else if(search_nm_category.equals("") && search_nm_subcategory.equals("")&& !enddate.equals("")&& !startdate.equals(""))
		            			sqlTemp = " and t.state='"+search_state+"' and  t.dt_update BETWEEN '"+startdate+"' AND '"+enddate+"' ";
		            	
	            		
	            		}else{
		            		if(!search_nm_category.equals("") && search_nm_subcategory.equals("")&& !enddate.equals("")&& !startdate.equals(""))
		            			sqlTemp = " and t.nm_category='"+search_nm_category+"'and  t.dt_update BETWEEN '"+startdate+"' AND '"+enddate+"' ";
		            		else if(!search_nm_category.equals("") && !search_nm_subcategory.equals("")&& !enddate.equals("")&& !startdate.equals(""))
		            			sqlTemp = " and t.nm_category='"+search_nm_category+"' and t.nm_subcategory='"+search_nm_subcategory+"'  and  t.dt_update BETWEEN '"+startdate+"' AND '"+enddate+"'";
		            		else if(search_nm_category.equals("") && search_nm_subcategory.equals("")&& !enddate.equals("")&& !startdate.equals(""))
		            			sqlTemp = "  and  t.dt_update BETWEEN '"+startdate+"' AND '"+enddate+"'";
		            		else if(search_nm_category.equals("") && search_nm_subcategory.equals("")&& enddate.equals("")&& startdate.equals(""))
		            			sqlTemp = "";

	            		}
	            		
	            			
	            		
	            		sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req, (select count(*) from INC_Incidents t,M_Emp_User reqEmp where t.req_by=reqEmp.id_emp_user "+sqlTemp+" ) as tcount,id_Incident,t.ph_no,t.nm_category,t.short_description,t.assign_grp,t.nm_subcategory,Incident_no,priority,state,reqEmp.nm_emp as reqEmp,ch.nm_attribute,t.dt_update,(select emp.nm_emp from M_emp_user emp where t.edit_by=emp.id_emp_user) as edit_name,(select gm.nm_grp from M_Group gm where t.assign_grp=gm.id_grp) as grpname,(select emp.nm_emp from M_Emp_User emp where t.assign_to=emp.id_emp_user) as assignemp from INC_Incidents t,M_Emp_User reqEmp,M_choicelist ch "+
	            				" where t.req_by=reqEmp.id_emp_user and ch.nm_field='priority' and t.priority=ch.val_attr"+sqlTemp+" order by id_Incident desc";
	            	
	            	}
	            	System.out.println(sql);
	            	System.out.println("sql");
	            	jobj = uad.GetDataAsJsonFormate(sql,  request);
	            	
	            	
	                break;
	                
	            case "Ticket_Dashboard":
	            	jobj=incidashboard(id_emp_user,request);
	            	
	            	
	            	
	                break;
	            case "Today_Dashboard":
	            	jobj=todaydashboard(id_emp_user,request);
	            	
	            	
	            	
	                break;
	            case "Urgdash":
	            	jobj= Urgdash();
	
	                break; 
	                
	            case "statedash":
	            	jobj= statedash();
	            	break;
	            case "Edit":
	            	jobj = DisplayIncidents(id,  request);	
	            	
	                break;
	            case "parent":
	            jobj = DisplayIncidents(id,  request);	
            	
                break;
	            case "Update":
	            	jobj = UpdateTicket(map,id,id_emp_user,  request);	
	                break; 
	            case "Reopen":
	            	jobj = ReopenTicket(map,id,state,id_emp_user);	
	                break;
	            case "UpdateAssign":
	            	jobj = UpdateAssignTicket(map,id,id_emp_user,  request);	
	                break;  
	            case "Delete":
	            	jobj = DeleteIncidents(id);	
	                break; 
	            case "Upload":
	            	jobj = UploadIncidentsDocument(map,id,id_emp_user);	
	                break;
	            case "DropDownResult":
	            	jobj = DropDownResult();	
	                break; 
	                
	            case "DetailsforCaller":
	            	jobj = DetailsforCaller(id_emp_user,  request);	
	                break;
	            case "NextTicketNumber":
	            	jobj = DynamicTicketNumber();	
	                break;
	            case "ParentList":
	            	jobj = ParentList();	
	                break;
	            case "Displaychild":
	            	jobj = DisplayChildIncidents(id,request);	
	                break;
	            case "getSLA":
	            	jobj = DisplaySLA(priority,request,id);	
	                break;
	            case "Update_worknote":
	            	jobj = Update_worknote(note,id,id_emp_user,request);;	
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
	
	public JSONObject Update_worknote(String note, String id,int id_emp_user,HttpServletRequest request) {
		int j=0;
		JSONObject json=new JSONObject();
		System.out.println(note);
		String wrknote=request.getParameter("work_note");
		note=note.replace("'", "''");
		String sql="update INC_Incidents set work_note='"+note+"',edit_by="+id_emp_user+",dt_update=getdate() where id_incident="+id+"";
		System.out.println(sql);
		try {
			ps=con.prepareStatement(sql);
			j=ps.executeUpdate();
		System.out.println(sql);
		JSONObject jobj= new JSONObject();
			if(j > 0)
			{
				j=1;
				sql="select th.field_name,th.new_value,th.dt_comment,(select emp.nm_emp from M_Emp_User emp where comment_by=emp.id_emp_user)as edit_emp, " + 
						"(select ch.nm_attribute from M_ChoiceList ch where th.field_name= 'Priority' and th.new_value =ch.val_attr and ch.nm_field ='Priority') as priority, (select gm.nm_grp from " + 
						"M_Group gm where th.field_name= 'Assign Group' and th.new_value =(cast(gm.id_grp as nvarchar))) as grpname,(select em.nm_emp from M_Emp_User em where " + 
						"th.field_name= 'Assign To' and th.new_value=(cast(em.id_emp_user as nvarchar))) as assignemp from INC_Incident_History th,M_Emp_User emp where  " + 
						"th.comment_by=emp.id_emp_user and id_Incident="+id+" order by th.id DESC";
				System.out.println(sql);
				
				try
				{
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
				   ResultSetMetaData metaData = rs.getMetaData();
				    int columns = metaData.getColumnCount();
				    JSONArray jarr = new JSONArray();
					while(rs.next())
				    {
				    	JSONObject jobj2 = new JSONObject();
				    	for(int i=1;i<=columns;i++)
				    	{
				    		
				    		String name=metaData.getColumnName(i);
				    		
				    		
				    		
				    		jobj2.put(name,rs.getString(name));
				    	}
				    		jarr.put(jobj2);
				    		System.out.println("history"+jarr);
			        }
				    jobj= new JSONObject().put("history", jarr);
				   
				}
				catch(Exception e)
				{
					System.out.println("sql error in New Incident."+e);
				}
				 
				
			}
		
		
	

			return jobj;
	
}
catch (Exception e)
{
	
	e.printStackTrace();
}
return json;
	}

	public JSONObject incidashboard(int id_emp_user,HttpServletRequest request) {
		// TODO Auto-generated method stub
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select (select count(*) from INC_Incidents) as totalcount,(select  count (distinct id_incident) from INC_Incident_History  where field_name='Reopen Flag') as reopen,  count(*) as totcount,state from INC_Incidents group by state  ";
try {
		UserAccessData uad = new UserAccessData();
		jobj = uad.GetDataAsJsonFormate(sql,  request);
	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return jobj;
	}
	public JSONObject todaydashboard(int id_emp_user,HttpServletRequest request) {
		// TODO Auto-generated method stub
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
	String sql=" select count(id_incident) as todaycount,state from INC_Incidents where cast(dt_update as Date) = cast(getdate() as Date) group by state";
	System.out.println("sql");
	System.out.println(sql);
	try {
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
	    int columns = metaData.getColumnCount();
	    while(rs.next())
	    {
	    	JSONObject jobj2 = new JSONObject();
//	    	
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
	    	 jarr.put(jobj2);
	    	 		
        }	

	    
	    jobj.put("Today", jarr);
	} catch (SQLException | JSONException e) {
		
		e.printStackTrace();
	}
	System.out.println("Today");
	System.out.println("hi");
	return jobj;
	}
	public JSONObject statedash() {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONObject jobj1 = new JSONObject();
		JSONArray jarr1 = new JSONArray();
		ResultSet rs1;
		try
		{
			String tempQuery="select distinct state from INC_Incidents ";
			ps=con.prepareStatement(tempQuery);
			rs=ps.executeQuery();
			while(rs.next()) {
		String sql="select count(*) as totcount from INC_Incidents where state='"+rs.getString("state")+"' ";	
		System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs1=ps.executeQuery();
		 ResultSetMetaData metaData = rs1.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs1.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	JSONObject jobj3 = new JSONObject();
//		    	
			    	for(int i=1;i<=columns;i++)
			    	{
			    		String name=metaData.getColumnName(i);
			    		System.out.println(name);
			    		jobj2.put("state",rs.getString("state"));
			    		jobj3.put("totcount",rs1.getString("totcount"));
			    		
			    		
			    	}
		    	 jarr.put(jobj2);
		    	 jarr1.put(jobj3);
		    	 System.out.println("chaznchal"+jarr);		
	        }
			}
		   
		   
		        	
			jobj.put("data", jarr);
			jobj.put("data1", jarr1);
				//System.out.println(jarr);
					}
					catch(Exception e)
					{
						System.out.println("sql error in incidents."+e);
					}
					 
					return jobj;
	}

	public JSONObject Urgdash() {
		
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONObject jobj1 = new JSONObject();
		JSONArray jarr1 = new JSONArray();
		ResultSet rs1;
		try
		{
			String tempQuery="select val_attr,nm_attribute from M_ChoiceList where nm_field='priority' ";
			System.out.println(tempQuery);
			ps=con.prepareStatement(tempQuery);
			rs=ps.executeQuery();
			while(rs.next()) {
		String sql="select count(*) as totcount from INC_Incidents where priority='"+rs.getString("val_attr")+"' ";	
		System.out.println(sql);
		ps=con.prepareStatement(sql);
		rs1=ps.executeQuery();
		 ResultSetMetaData metaData = rs1.getMetaData();
		    int columns = metaData.getColumnCount();
		    while(rs1.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	JSONObject jobj3 = new JSONObject();
//		    	
			    	for(int i=1;i<=columns;i++)
			    	{
			    		String name=metaData.getColumnName(i);
			    		System.out.println(name);
			    		jobj2.put("urgency",rs.getString("nm_attribute"));
			    		jobj3.put("totcount",rs1.getString("totcount"));
			    		
			    		
			    	}
		    	 jarr.put(jobj2);
		    	 jarr1.put(jobj3);
		    	 System.out.println("chaznchal urg"+jarr);		
	        }
			}
		   
		   
		        	
			jobj.put("data", jarr);
			jobj.put("data1", jarr1);
				//System.out.println(jarr);
					}
					catch(Exception e)
					{
						System.out.println("sql error in incidents."+e);
					}
					 
					return jobj;
		
	}
	public JSONObject DeleteIncidents(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			System.out.println(id);
			String query = "select count (id_incident) as incident from INC_Incidents where parent = "+id+"";
			PreparedStatement ps=con.prepareStatement(query);
			
			rs=ps.executeQuery();
			if(rs.next()) {
		int count= Integer.parseInt(rs.getString("incident"));
		
			if(count!=0)
			{
				j=2;
			}
			else
			{
				
					query = "delete INC_Incidents where id_Incident = "+id+"";
				
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
	public JSONObject UploadIncidentsDocument( HashMap<String, String> map ,String id,int id_emp_user)
	{
		JSONObject json=new JSONObject();
		int j=0;
		String file_name1 = map.get("file_name1");
		try 
		{
			System.out.println(id);
			String query = "update INC_Incidents set file_name1='"+file_name1+"',edit_by="+id_emp_user+",dt_update=getdate() where id_Incident = "+id+"";
			System.out.println(query);
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
	
	public JSONObject ReopenTicket( HashMap<String, String> map ,String id, String state,int id_emp_user)
	{
		JSONObject json=new JSONObject();
		int j=0;
		String worknote = map.get("work_note");
		try 
		{
			System.out.println(id);
			String query = "update INC_Incidents set state='Inprogress',work_note='"+worknote+"',edit_by="+id_emp_user+",reopen_flag=1,dt_closed=null where id_Incident = "+id+"";
			System.out.println(query);
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
	
	public JSONObject DynamicTicketNumber()
	{
		JSONObject json=new JSONObject();
		String finyear="",ticktNo2="";
		int ticktNo=1;
		try 
		{
			ps=con.prepareStatement("select count(id_incident) from INC_Incidents");
			rs=ps.executeQuery();
			
				if(rs.next())
				{
					ticktNo =	rs.getInt(1) +1;
					String formatValue  = String.format("%07d", ticktNo);
					
					ticktNo2="INC-"+formatValue;
				}
				json.put("data", ticktNo2);
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	public JSONObject DetailsforCaller(int id_emp_user,HttpServletRequest request)
	{
		JSONObject json1=new JSONObject();
		String finyear="",ticktNo2="";
		int ticktNo=1;
		try 
		{
			ps=con.prepareStatement("select nm_emp as req_by,m.cont_no,m.id_design as id_design,(select d.nm_design from M_designation d where id_design=m.id_design) as nm_design from M_emp_user m where id_emp_user="+id_emp_user+"");
			rs=ps.executeQuery();
			JSONObject json=new JSONObject();
			while(rs.next()){
				
				String req_by=rs.getString("req_by");
				String ph_no=rs.getString("cont_no");
				String id_design=rs.getString("id_design");
				json.put("req_by",req_by);
				json.put("ph_no", ph_no);	
				json.put("id_design", id_design);
			}
			json1.put("data1", json);	
			System.out.println(json1);
		}
		
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json1;
	}
	public JSONObject ParentList()
	{ 
		JSONObject json=new JSONObject();
		JSONObject jobj=new JSONObject();
		 JSONArray jarr=new JSONArray();
		try 
		{
			ps=con.prepareStatement("select id_Incident, Incident_no,short_description from INC_Incidents");
		
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
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return jobj;
	}
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_Incident from INC_Incidents where "+ColName+" = '"+value+"'";
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
		String sql="select id_Incident,nm_dept from INC_Incidents order by nm_dept";
		
		
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
			System.out.println("sql error in  INC_Incidents.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject UpdateTicket(HashMap<String, String> map , String id,int id_emp_user,HttpServletRequest request)
	{
		String col_Val="",id_incident=id;
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("INC_Incidents",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_Incident")
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
					System.out.println("Some error in New Incident.");
				}
		
		String query="";
		try 
		{
			String assign_grp = null;
			//String assign_grp = map.get("assign_grp");
			if(map.containsKey("assign_grp")) {
				assign_grp = map.get("assign_grp");
				
			}
			System.out.println("assigning null"+assign_grp);
			String assign_to = map.get("assign_to");
			String priority = map.get("priority");
			String state = map.get("state");
			System.out.println(map.get("priority"));
			System.out.println(assign_grp);
			System.out.println(assign_to);
			
			if(!assign_grp.equals("") )
			{
				query ="select assign_grp from INC_Incidents where id_Incident="+id_incident+"";
				PreparedStatement ps=con.prepareStatement(query);
				rs = ps.executeQuery();
				System.out.println(query);
				if(rs.next()) {
				int grp=Integer.parseInt(rs.getString("assign_grp"));
				if(grp== 0) {
					query="select id_SLA,SLA_Defination,target,nm_module,id_priority,totaltime from M_SLA where id_priority="+priority+" AND target='Response' and active='Active' ";
					PreparedStatement ps1=con.prepareStatement(query);
					rs = ps1.executeQuery();
					System.out.println(query);
					while(rs.next()){
						String id_SLA=rs.getString("id_SLA");
						String SLA_Defination=rs.getString("SLA_Defination");
						String target=rs.getString("target");
						String nm_module=rs.getString("nm_module");
						String id_priority1=rs.getString("id_priority");
						String tot=rs.getString("totaltime");
					
					String	query1 = "insert into M_TaskSLA(id_SLA,SLA_Defination,target,nm_module,id_priority,id_Reference,start_time,totaltime,state) values ('"+id_SLA+"','"+SLA_Defination+"','"+target+"','"+nm_module+"','"+id_priority1+"','"+id_incident+"',getDate(),"+tot+",'In-Progress')";
						ps=con.prepareStatement(query1);
						j=ps.executeUpdate();
						System.out.println(query1);
					}
					
						IncidentModel hdm1 = new IncidentModel();
						hdm1.SendEmailToGroupList(assign_grp,id_incident, request);
						
				}
				if(grp!=Integer.parseInt(assign_grp) && grp!=0) {
					IncidentModel hdm1 = new IncidentModel();
					hdm1.SendEmailToGroupList(assign_grp,id_incident, request);
				}
				}
			
					
						 if(!assign_to.equals("")) {
							String sql ="select assign_to from INC_Incidents where id_Incident="+id_incident+"";
							PreparedStatement ps3=con.prepareStatement(sql);
							rs = ps3.executeQuery();
							System.out.println(sql);
							
							if(rs.next()) {
							
							int to=Integer.parseInt(rs.getString("assign_to"));
							if(to== 0) {
								System.out.println(sql);
								query="select id_SLA,SLA_Defination,target,nm_module,id_priority, totaltime from M_SLA where id_priority='"+priority+"' and target='Resolution'";
								PreparedStatement ps2=con.prepareStatement(query);
								rs = ps2.executeQuery();
								System.out.println(query);
								while(rs.next()){
									String id_SLA=rs.getString("id_SLA");
									String SLA_Defination=rs.getString("SLA_Defination");
									String target=rs.getString("target");
									String nm_module=rs.getString("nm_module");
									String id_priority1=rs.getString("id_priority");
									String tot=rs.getString("totaltime");
								String	query1 = "insert into M_TaskSLA(id_SLA,SLA_Defination,target,nm_module,id_priority,id_Reference,start_time,totaltime,state) values ('"+id_SLA+"','"+SLA_Defination+"','"+target+"','"+nm_module+"','"+id_priority1+"','"+id_incident+"',getDate(),'"+tot+"', 'In-Progress')";
								PreparedStatement	ps5=con.prepareStatement(query1);
									j=ps5.executeUpdate();
									System.out.println(query1);
								String	query2 = "update M_TaskSLA set end_time= getDate(),state='Cancelled' where id_Reference='"+id_incident+"' and target='Response' ";
								PreparedStatement	ps6=con.prepareStatement(query2);
									j=ps6.executeUpdate();
									System.out.println(query2);
								}
								IncidentModel hdm6 = new IncidentModel();
								hdm6.SendEmailToAssignee(assign_to,id_incident, request);
							}
							
							else
							{
								if(to!=Integer.parseInt(assign_to)) {
									IncidentModel hdm4 = new IncidentModel();
									hdm4.SendEmailToAssignee(assign_to,id_incident, request);
							}
							}
							
						}
						}
					}
				
					
				
				
			
			
			if(state.equals("Resolved") || state.equals("Cancelled") || state.equals("Closed")) {
				String	query2 = "update M_TaskSLA set end_time= getDate(),state='Cancelled' where id_Reference='"+id_incident+"' ";
				ps=con.prepareStatement(query2);
				j=ps.executeUpdate();
				System.out.println("According to state of incidents  "+query2);
				String	query3 = "update INC_Incidents set dt_closed= getDate(), reopen_flag=0 where id_Incident='"+id_incident+"' ";
				ps=con.prepareStatement(query3);
				j=ps.executeUpdate();
			}
	String query5="update INC_Incidents set "+col_Val+",dt_update=GETDATE(),edit_by="+id_emp_user+" where id_Incident="+id+"";
			
			PreparedStatement ps=con.prepareStatement(query5);
			j=ps.executeUpdate();
			
			String work_note = map.get("work_note");   
			
	         if(!work_note.equals("") || !state.equals("New")){
	        	 
	        	 
	 	         	
		        	 
		        	 IncidentModel hdm = new IncidentModel();
					hdm.SendEmailToCreatorForIncidentUpdate(work_note,id_incident,state, priority, request);
	         }
	         
				
			if(j > 0)
			{
				j=1;
			}
			
			
//		
			json.put("data",j);
			
			}
		catch (Exception e)
		{
			
			e.printStackTrace();
			System.out.println(e);
		}
		
		
		return json;
	}
	
	public JSONObject UpdateAssignTicket(HashMap<String, String> map , String id,int id_emp_user,HttpServletRequest request)
	{
		String col_Val="",id_incident=id;
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("INC_Incidents",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_Incident")
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
					System.out.println("Some error in New Incident.");
				}
		
		String query="";
		try 
		{
			
			String priority = map.get("priority");
			String state = map.get("state");
			System.out.println(map.get("priority"));
			
			
			if(state.equals("Resolved") || state.equals("Cancelled") || state.equals("Closed")) {
				String	query2 = "update M_TaskSLA set end_time= getDate(),state='Cancelled' where id_Reference='"+id_incident+"' ";
				ps=con.prepareStatement(query2);
				j=ps.executeUpdate();
				System.out.println("According to state of incidents  "+query2);
				String	query3 = "update INC_Incidents set dt_closed= getDate(),reopen_flag=0 where id_Incident='"+id_incident+"' ";
				ps=con.prepareStatement(query3);
				j=ps.executeUpdate();
				System.out.println("According to state of incidents  "+query3);
			}
	String query5="update INC_Incidents set "+col_Val+",dt_update=GETDATE(),edit_by="+id_emp_user+" where id_Incident="+id+"";
			
			PreparedStatement ps=con.prepareStatement(query5);
			j=ps.executeUpdate();
			
			String work_note = map.get("work_note");   
			
	         if(!work_note.equals("")){
	        	 
	        	
	     		
	     		IncidentModel hdm = new IncidentModel();
				hdm.SendEmailToCreatorForIncidentUpdate(work_note,id_incident,"WIP", priority, request);
	     			     		
	         }
	         
	         //String state = map.get("state");
	         if(!state.equals("New")){
	         	
	        	 
	        	 IncidentModel hdm = new IncidentModel();
				hdm.SendEmailToCreatorForIncidentUpdate(work_note,id_incident,state, priority, request);
	         }
				
			if(j > 0)
			{
				j=1;
			}
			
			
//		
			json.put("data",j);
			
			}
		catch (Exception e)
		{
			
			e.printStackTrace();
			System.out.println(e);
		}
		
		
		return json;
	}
	
	
	public JSONObject DisplayIncidents(String id,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		//String sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,wh.no_mfr as mfr,wh.serial_no as model,t.* from HD_Tickets t,A_Ware_House wh where t.id_prod=wh.id_wh and id_ticket = "+id+"";
		String sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,t.*,(select emp.nm_emp from M_emp_user emp where t.req_by=emp.id_emp_user)as nm_emp from INC_Incidents t where id_Incident = '"+id+"'";
		
		UserAccessData uad = new UserAccessData();
		jobj = uad.GetDataAsJsonFormate(sql,  request);
    	
		sql="select th.field_name,th.new_value,th.dt_comment as dt_comment,(select emp.nm_emp from M_Emp_User emp where comment_by=emp.id_emp_user)as edit_name, " + 
				"(select ch.nm_attribute from M_ChoiceList ch where th.field_name= 'Priority' and th.new_value =ch.val_attr and ch.nm_field ='Priority') as priority, (select gm.nm_grp from " + 
				"M_Group gm where th.field_name= 'Assign Group' and th.new_value =(cast(gm.id_grp as nvarchar))) as grpname,(select em.nm_emp from M_Emp_User em where " + 
				"th.field_name= 'Assign To' and th.new_value=(cast(em.id_emp_user as nvarchar))) as assignemp from INC_Incident_History th,M_Emp_User emp where  " + 
				"th.comment_by=emp.id_emp_user and id_Incident="+id+" order by th.id DESC";
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
		    		System.out.println(jarr);
	        }
		    jobj.put("history", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in New Incident."+e);
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddNewTicket(HashMap<String, String> map,int id,HttpServletRequest request,String id_parent)
	{
		String colName="",value="",ticktNo2="";
		int j=0,ticktNo=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("INC_Incidents",  request);
				
						
						while (rs.next())
						{
						
							if(!(rs.getString(1).trim().equalsIgnoreCase("id_Incident") || rs.getString(1).trim().equalsIgnoreCase("req_by") ))
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
					System.out.println("Some error in New Incident.");
				}
		String id_incident="";
		try 
		{
		ps=con.prepareStatement("select count(id_incident) from INC_Incidents");
		rs=ps.executeQuery();
		
			if(rs.next())
			{
				ticktNo =	rs.getInt(1) +1;
				String formatValue  = String.format("%07d", ticktNo);
				
				ticktNo2="INC-"+formatValue;
			} 
		String query="insert into INC_Incidents("+colName+",edit_by,dt_update,req_by,Incident_no) values("+value+","+id+",getdate(),"+id+",'"+ticktNo2+"')";
		System.out.println(query);
		
			stmt=con.createStatement();
			 stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			 rs = stmt.getGeneratedKeys();
	            if (rs.next()) {
	            	id_incident = rs.getString(1);
	            	j=1;
	            	System.out.println(id_incident);
	            }
	          int p= Integer.parseInt(id_parent);
	            if(p!=0) {
	            	
	           
	          query="update INC_Incidents set parent ='"+id_parent+"' where id_incident="+id_incident+""; 
	          System.out.println("INC_Parent_Child_Details:"+query);
	          ps=con.prepareStatement(query);
	     		j=ps.executeUpdate();
	            }
	           
	         
	         j=1;
	         
	         
			/*if(j > 0)
			{
				j=1;
			}*/
		
			json.put("data",j);
			String assign_grp = map.get("assign_grp");
			String assign_to = map.get("assign_to");
			
		
				IncidentModel hdm = new IncidentModel();
				hdm.SendEmailForCreation(id,id_incident, request);
		
//			if(!assign_to.equals("")){
//				IncidentModel hdm1 = new IncidentModel();
//				hdm1.SendEmailToAssignee(assign_to,id_incident, request);
//			}
//			if(!assign_grp.equals("")){
//				IncidentModel hdm1 = new IncidentModel();
//				hdm1.SendEmailToGroupList(assign_grp,id_incident, request);
//			}
//			
			
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}


public JSONObject DisplayChildIncidents(String id,HttpServletRequest request) throws SQLException
{
	con=Common.GetConnection(request);
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	ResultSet rs1=null;
	
	
	try
	{
	
	String sql="select (replace(convert(NVARCHAR, t.dt_req, 103), ' ', '-')) as dt_req,id_Incident,t.nm_category,t.short_description,t.assign_grp,t.nm_subcategory,Incident_no,priority,state,reqEmp.nm_emp as reqEmp,ch.nm_attribute,t.dt_update,(select emp.nm_emp from M_emp_user emp where t.edit_by=emp.id_emp_user) as edit_name,(select gm.nm_grp from M_Group gm where t.assign_grp=gm.id_grp) as grpname,(select emp.nm_emp from M_Emp_User emp where t.assign_to=emp.id_emp_user) as assignemp from INC_Incidents t,M_Emp_User reqEmp,M_choicelist ch " + 
			"where t.req_by=reqEmp.id_emp_user and ch.nm_field='priority' and t.priority=ch.val_attr and parent='"+id+"'"; 
		
			System.out.println(sql);
	
	ps=con.prepareStatement(sql);
	rs1=ps.executeQuery();
	
	   ResultSetMetaData metaData = rs1.getMetaData();
	    int columns = metaData.getColumnCount();
	    while(rs1.next())
	    {
	    	JSONObject jobj2 = new JSONObject();
	    	for(int i=1;i<=columns;i++)
	    	{
	    		String name=metaData.getColumnName(i);
	    		jobj2.put(name,rs1.getString(name));
	    	}
	    		jarr.put(jobj2);
	    		System.out.println(jarr);
        }
	    jobj.put("child", jarr);
	
	}
	catch(Exception e)
	{
		System.out.println("sql error in New child Incident."+e);
	}
	con.close();
	return jobj;
	
	
}
public JSONObject DisplaySLA(String priority,HttpServletRequest request,String id) throws SQLException
{
	con=Common.GetConnection(request);
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	ResultSet rs1=null;
	String sql="select * from M_TaskSLA where id_Reference = "+id+"";
	System.out.println(sql);
	try
	{
	ps=con.prepareStatement(sql);
	rs=ps.executeQuery();
	
	
	//String SLA_Defination=rs.getString("SLA_Defination");
		
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
		    		System.out.println(jarr);
	        }
		    jobj.put("SLA", jarr);
	
	
	System.out.println(jobj);
	}
	catch(Exception e)
	{
		System.out.println("sql error in New SLA Incident."+e);
	}
	 con.close();
	return jobj;
	
	
}
}