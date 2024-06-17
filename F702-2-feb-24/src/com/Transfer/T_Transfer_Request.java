package com.Transfer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

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


public class T_Transfer_Request extends HttpServlet {
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
		    
		String action = "",id="",word="",ids="",id_sloc="",id_loc="",typ_asst="",id_building="";
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id_loc") !=null)
		{
			id_loc = request.getParameter("id_loc");
		}
		if(request.getParameter("id_sloc") !=null)
		{
			id_sloc = request.getParameter("id_sloc");
		}
		if(request.getParameter("typ_asst") !=null)
		{
			typ_asst = request.getParameter("typ_asst");
		}
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("id_building") !=null)
		{
			id_building = request.getParameter("id_building");
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
		if(request.getParameter("ids") !=null)
		{
			ids = request.getParameter("ids");
		}	
		
		try
		{
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType");
			int UserId = (int)session.getAttribute("UserId");
			
			String id_dept = (String)session.getAttribute("DeptId");
			String temp="";
			String FlrId = (String)session.getAttribute("FlrId");
			int DivId = (int)session.getAttribute("DivId");
			if(!UserType.equals("SUPER")){
				String Query = Common.returnQuery(FlrId);
				temp = temp + " "+Query+" and wh.id_div ="+DivId+"";
			}
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        case "Update":
	            	jobj = IntraUnitTransferRequestSave(map,id,id_emp_user,UserId , ids,  request);	
	                break;
		        
		        case "Display":
	            	String tempSql="";
	            	String likeTempQuery="";
	            	String tempqry="";
	            	DtoCommon dtoCommon = new DtoCommon();
	            	if(!word.equals(""))
        			{
        				 likeTempQuery = dtoCommon.MakeLikeTempQuery(word);
        			}
	    			if(!UserType.equals("Super")) {
	    				String tempQuery = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
	    				tempSql="select top 100 id_wh,id_wh_dyn,ds_pro,no_mfr,wh.ds_asst,serial_no,nm_model from A_Ware_House wh,M_Model modl where st_trvl=0 and wh.id_model=modl.id_model and wh.id_building="+id_building+"  "+tempQuery+"" ;
	    			}
	    			else {
	    				tempSql="select top 100 id_wh,id_wh_dyn,ds_pro,no_mfr,wh.ds_asst,serial_no,nm_model from A_Ware_House wh,M_Model modl where st_trvl=0 and wh.id_model=modl.id_model and wh.id_building="+id_building+" " ;
			        	
	    			}
	    		System.out.println(tempSql);
	            	
	            	
		        	UserAccessData uad = new UserAccessData();
	            	jobj = uad.QueryMacker(tempSql , UserType , word,id_dept,  request);
	            	
	                break;
	             
		        case "Edit":
	            	jobj = TRequestTransferForEdit(id);	
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
	
	public JSONObject TRequestTransferForEdit(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			
	String sql="select ds_pro,no_mfr as mfr,nm_assetdiv,ds_asst,nm_loc,nm_flr,nm_subl,wh.id_loc as id_loc_tran,wh.id_subl as id_subl_tran,wh.id_flr "+
	" from A_Ware_House wh , M_Asset_Div ad , M_Loc l, M_Subloc sl ,M_Floor f "+
			" where  id_wh = "+id+" and wh.id_loc=l.id_loc and wh.id_subl = sl.id_sloc "+
					" and wh.id_flr = f.id_flr and wh.id_grp = ad.id_assetdiv";
			
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




	
	public JSONObject IntraUnitTransferRequest(String temp,String UserType)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String sql="",typ_asst="IT";
		
		if(UserType.equals("Admin"))
			typ_asst = "NIT";
		
		if(!temp.equals(""))
		{
			sql= "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst,nm_emp from A_Ware_House wh,M_Emp_User emp where (allocate=1 or allocate=3) and st_trvl=0 and emp.id_emp_user=wh.to_assign and typ_asst='"+typ_asst+"' "+temp+"" ;
			if(UserType.equals("Super"))
				sql= "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst,nm_emp from A_Ware_House wh,M_Emp_User emp where (allocate=1 or allocate=3) and st_trvl=0 and emp.id_emp_user=wh.to_assign "+temp+"" ;
		}
		else
		{
			sql= "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst,nm_emp from A_Ware_House wh,M_Emp_User emp where (allocate=1 or allocate=3) and st_trvl=0 and emp.id_emp_user=wh.to_assign and typ_asst='"+typ_asst+"' " ;
		if(UserType.equals("Super"))
			sql= "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst,nm_emp from A_Ware_House wh,M_Emp_User emp where (allocate=1 or allocate=3) and st_trvl=0 and emp.id_emp_user=wh.to_assign" ;
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
		    if(jarr.length() > 0)
		    {
		    	jobj.put("data", jarr);
		    }
		    else
		    {
		    	jobj.put("data", 1);
		    }
		}
		catch(Exception e)
		{
			System.out.println("sql error in T_Transfer_Request.");
		}
		 
		return jobj;
	}
	


public JSONObject IntraUnitTransferRequestSave(HashMap<String, String> map , String id,int id_emp_user , int UserId , String ids,HttpServletRequest request)
{
	String col_Val="",colName="",value="",col_Name="";
	int j=0;
	String ids_wh="";
	JSONObject json=new JSONObject();
	HashMap<String, String> map1 =new HashMap<String,String>();
	try
	{
			rs = Common.GetColumns("A_Iut_History",  request);
			
					
					while (rs.next())
					{
					
						if(rs.getString(1) !="id_iut")
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
					String req_no="",id_wh="";
					String query ="select MAX(req_no)+1 from A_Ware_House";
					ps=con.prepareStatement(query);
    				rs = ps.executeQuery();
					if(rs.next())
					{
						req_no = rs.getString(1);
					}
					
					
					StringTokenizer st = new StringTokenizer(ids , "patel");
					while (st.hasMoreElements()) 
					{
						id_wh = (String) st.nextElement();
						if(ids_wh.equals(""))
							ids_wh = id;
						else
							ids_wh = ","+id;
						
					String sql ="select MAX(id_iut) as id_iut from A_Iut_History where id_wh ="+id_wh+"";
					int id_iut=0;
    				ps=con.prepareStatement(sql);
    				rs = ps.executeQuery();
    				if(rs.next())
    				{
    					id_iut = rs.getInt(1);
    					/*if(id_iut == 0)
    					{*/
    						sql="INSERT INTO A_Iut_History (id_wh ,id_loc,id_building,id_subl,id_flr,id_subl_tran,iut_flag,iut_approval,id_loc_tran,st_gate_pass,st_recv,type_tran) "+
    								"SELECT id_wh,id_loc,id_building,id_subl,id_flr,0,0,0,0,0,0,'Intra' FROM A_Ware_House where id_wh="+id_wh+"";
    						stmt=con.createStatement();
    						 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
    						 rs1 = stmt.getGeneratedKeys();
    				            while (rs1.next()) {
    				            	id_iut = rs1.getInt(1);
    				            } 
    						
								/* } */
    				}
    			 
				query = "update A_Iut_History set dt_start_tran = '"+today1+"',id_flr_tran="+id+",id_building_tran=(select id_building from A_Ware_House where id_wh="+id_wh+"),id_loc_tran=(select id_loc from A_Ware_House where id_wh="+id_wh+"),id_subl_tran=(select id_subl from A_Ware_House where id_wh="+id_wh+"),iut_approval=1,tran_req_by="+id_emp_user+",req_no_transfer="+req_no+" where id_iut = "+id_iut+"";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();	
				
				stmt = con.createStatement();
				stmt.executeUpdate("update A_Ware_House set st_trvl=1,req_by="+id_emp_user+",req_no="+req_no+",dt_req=GETDATE() where id_wh="+id_wh+"");
				
				j=1;
			}
	if(j > 0)
	{
		j=0;
		j=0;
		query="insert into T_Transfer_Track (id_wh,req_no,req_by) values('"+ids_wh+"','"+req_no+"',"+id_emp_user+")";
		ps=con.prepareStatement(query);
		j=ps.executeUpdate();
		
		j=0;
		//.........for mail ........
    	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="";
    	
    	String Sql ="select id_emp from A_Ware_House wh,M_User_Login l,M_Emp_User emp "+
    				" where l.id_loc like '%'+CONVERT(varchar(10),wh.id_loc)+'%' and l.id_sloc like '%'+CONVERT(varchar(10),wh.id_subl)+'%' "+
    				" and wh.id_wh="+id_wh+" and l.typ_asst like '%'+wh.typ_asst+'%' and emp.id_emp_user=l.id_emp_user "+
    				" and l.type_user='EXCTV'";
    	System.out.println(Sql);
    	DtoCommon dtcm = new DtoCommon();
		List<String> recipients = dtcm.ReturnListData(Sql,  request);
		
	if(!recipients.isEmpty())
	{
		//replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
		
		Sql ="select id_emp from A_Ware_House wh,M_User_Login l,M_Emp_User emp "+
				" where l.id_loc like '%'+CONVERT(varchar(10),wh.id_loc)+'%'  "+
				" and wh.id_wh="+id_wh+" and l.typ_asst like '%'+wh.typ_asst+'%' and emp.id_emp_user=l.id_emp_user "+
				" and  l.type_user='MNGR'";
		System.out.println(Sql);
		recipients = dtcm.ReturnEmail(Sql, recipients,  request);
		
		rs = dtcm.GetResultSet(Sql,  request);
		if(rs.next())
		{
			replyMailId = rs.getString(1);
			recipients.remove(replyMailId);
		}
		
    	
    	/*String mailSql ="select sa.nm_s_assetdiv,no_model,no_mfr,wh.id_wh_dyn from M_Subasset_Div sa, A_Ware_House wh where wh.id_wh='"+id_wh+"' and sa.id_s_assetdiv=wh.id_sgrp";
    
		rs = dtcm.GetResultSet(mailSql);
		if(rs.next())
		{
			itemName = rs.getString(1);
			no_model = rs.getString(2);
			slNo = rs.getString(3);
			id_wh_dyn = rs.getString(4);
			
		}*/
		
		String link = dtcm.ReturnParticularMessage("link");
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		String footerMsg2 = dtcm.ReturnParticularMessage("footerMsg2");
		String mailSubject = dtcm.ReturnParticularMessage("intraTrnReq");
		
		String mailBody = "Hello <b>Manager Team</b>,<br><br><br>"+
					  //"The <b>'"+itemName+"'</b> having Model No <b>'"+no_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b>  has been raised for transfer. Please do the needful.<br>"+
					  "<p>Executive has been raised a request for intra transfer movement having request number <b>(Request No-"+req_no+")</b>. Please approve the same.</p><br>"+
					  "<br><br><br>"+link+""+
					  "<br><br><p>"+footerMsg+"</p>"+
					  "<br><br>"+footerMsg2+"<b>";
		System.out.println(link);
		System.out.println(footerMsg);
		System.out.println(footerMsg2);
		System.out.println(mailSubject);
		Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	
		j=1;
	}
		
		j=1;
	}
		
		json.put("data",j);
		
	}
	catch (Exception e)
	{
		
		System.out.println("Error while transfer request. "+e);
	}
	
	
	return json;
}

}
