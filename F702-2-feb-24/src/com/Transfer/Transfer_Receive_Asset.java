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
import dto.Common.UserAccessData;


public class Transfer_Receive_Asset extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	ResultSet rs2=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
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
		    
		String action = "",id="0",word="",req_no="";
		String dt_tran="",id_wh="",tranType="";
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("dt_tran") !=null)
		{
			dt_tran = request.getParameter("dt_tran");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("id_wh") !=null)
		{
			id_wh = request.getParameter("id_wh");
		}
		if(request.getParameter("id_cc") !=null)
		{
			id = request.getParameter("id_cc");
		}
		if(request.getParameter("dt_tran") !=null)
		{
			dt_tran = request.getParameter("dt_tran");
		}	
		if(request.getParameter("tranType") !=null)
		{
			tranType = request.getParameter("tranType");
		}
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}	
		if(request.getParameter("req_no") !=null)
		{
			req_no = request.getParameter("req_no");
		}	
		try
		{
			String temp="";
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType");
			int UserId = (int)session.getAttribute("UserId");
			String id_dept = (String)session.getAttribute("DeptId");
			String FlrId = (String)session.getAttribute("FlrId");
			int DivId = (int)session.getAttribute("DivId");
			if(!UserType.equals("SUPER")){
				String Query = Common.returnQuery(FlrId);
				temp = Query+" and wh.id_div ="+DivId+"";
			}
			
			
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
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
	        			tempSql= "select distinct(req_no),dt_req,type_tran,dt_start_tran from A_Ware_House wh,A_Iut_History iut where  "+
		    					" wh.id_wh=iut.id_wh and iut_approval =5 and st_gate_pass=0  and st_recv=0  "+temp+" "+tempQuery+" Order by req_no DESC" ;
	        		}
	        		else {
	        			tempSql= "select distinct(req_no),dt_req,type_tran,dt_start_tran from A_Ware_House wh,A_Iut_History iut where  "+
	    						" wh.id_wh=iut.id_wh and iut_approval =5 and st_gate_pass=0   and st_recv=0 Order by req_no DESC " ;
	        		}
//	        		tempSql= "select distinct(req_no),dt_req,type_tran,dt_start_tran from A_Ware_House wh,A_Iut_History iut where (allocate=1 or allocate=2) and "+
//    						" wh.id_wh=iut.id_wh and iut_approval =5 and st_gate_pass=0   and st_recv=0 Order by req_no DESC " ;
//        	
	        		System.out.println(tempSql);
	        		UserAccessData uad = new UserAccessData();
	            	jobj = uad.QueryMacker(tempSql , UserType , word,id_dept,  request);
	                break;
	                
	            case "Update":
	            	jobj = UpdateTransfer(req_no,dt_tran,id_emp_user);	
	                break;
	                
	            case "Edit":
	            	jobj = TransferForEdit(id , tranType);	
	                break;
	                
	            case "CheckDate":
	            	jobj = CheckDate(dt_tran,req_no,id_wh);	
	                break;
	                
	            case "Preview":
	            	 dtoCommon = new DtoCommon();
	            	tempSql= "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst from A_Ware_House where req_no="+req_no+" " ;
	            		jobj = dtoCommon.GetDataForDisplay(tempSql,  request);	
	                break;
	                
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in T_Transfer.");
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
	public JSONObject CheckDate(String dt_tran ,String req_no,String id_wh)
	{
		JSONObject jobj = new JSONObject();
		
		try
		{
		int t=0;
    	String  sql ="",id="";
    	String d = "1900-01-01";
    	int id_iut=0;
    	sql = "select id_wh from A_Ware_House where req_no=" + req_no + "";
		ps = con.prepareStatement(sql);
		rs2 = ps.executeQuery();
		while (rs2.next()) {
			id = rs2.getString(1);
		String sql1 ="select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="+id+" and iut_approval=3";
		PreparedStatement ps1=con.prepareStatement(sql1);
		rs1 = ps1.executeQuery();
		if(rs1.next())
		{
			id_iut = rs1.getInt(1);
		}
		
		    	sql ="select dt_start_tran from A_Iut_History  where dt_start_tran > '"+dt_tran+"' and id_iut="+id_iut+"";
		    	ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
    		    {
    		    	t=1;
    		    	d = rs.getString(1);
    	        }
		}
    			jobj.put("data", t);
        		jobj.put("dt_iut",d);
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in G_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject TransferForEdit(String id , String tranType)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
			String sql="select ds_pro,nm_assetdiv,ds_asst,nm_loc,nm_subl,mfr "+
					"from A_Ware_House wh , M_Asset_Div ad , M_Loc l, M_Subloc sl "+
					"  where  id_wh = "+id+" and wh.id_loc=l.id_loc and wh.id_subl = sl.id_sloc "+
					"   and wh.id_grp = ad.id_assetdiv";
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
			    if(tranType.equals("Temporary"))
			    {
			    	sql="select type_tran,nm_ven from A_Iut_History wh , M_Vendor v where  id_wh = "+id+" and iut_approval = 3 and st_recv=0 and wh.id_ven=v.id_ven";
			    }	
			    else
			    {		   
				 sql="select nm_loc as nm_loc_tran,nm_subl as nm_subl_tran,type_tran "+
					 "from A_Iut_History wh ,M_Loc l, M_Subloc sl  "+
					"  where  id_wh = "+id+" and iut_approval = 3 and type_tran !='Intra' and st_recv=0 and wh.id_loc_tran=l.id_loc and wh.id_subl_tran = sl.id_sloc ";
			    }
 ps=con.prepareStatement(sql);
	rs=ps.executeQuery();
	
	    metaData = rs.getMetaData();
	     columns = metaData.getColumnCount();
	     jobj2 = new JSONObject();
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
			System.out.println("sql error in T_Accept_Reject.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject UpdateTransfer( String req_no ,String dt_tran,int id_emp_user)
	{
		String today = "",id="";
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		today = sdf.format(currDate);
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
		int id_iut=0;
			String sql="select id_wh from A_Ware_House where req_no="+req_no+"";
			sql = "select id_wh from A_Ware_House where req_no=" + req_no + "";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getString(1);
			String sql1 ="select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="+id+" and iut_approval=5";
			PreparedStatement ps1=con.prepareStatement(sql1);
			rs1 = ps1.executeQuery();
			if(rs1.next())
			{
				id_iut = rs1.getInt(1);
			}
			stmt = con.createStatement();
			
			stmt.executeUpdate("update A_Ware_House set st_trvl=0,asset_tran = 'Intra' where id_wh="+id+"");
			
			stmt.executeUpdate("update A_Iut_History set iut_approval=4,dt_recv='"+dt_tran+"',st_recv=1 , recv_by="+id_emp_user+" where id_iut="+id_iut+" and iut_approval=5");
					j=1;					
		
			}
			
			stmt = con.createStatement();
			j=0;
			stmt.executeUpdate("update T_Gate_Pass set st_recv=1 where req_no="+req_no+"");
			j=1;
			
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			System.out.println("Error in receive assets. "+e);
		}
		
		
		return json;
	}
}
