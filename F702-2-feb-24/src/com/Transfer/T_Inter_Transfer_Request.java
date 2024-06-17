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


public class T_Inter_Transfer_Request extends HttpServlet {
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
		    
		String action = "",id="",word="",asset_tran="",ids="",id_loc="",id_sloc="",typ_asst="",id_flr="",id_building="",id_dept="";
		
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		
		if(request.getParameter("type_tran") !=null)
		{
			asset_tran = request.getParameter("type_tran");
		}

		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("id_loc") !=null)
		{
			id_loc = request.getParameter("id_loc");
		}
		if(request.getParameter("id_sloc") !=null)
		{
			id_sloc = request.getParameter("id_sloc");
		}
		if(request.getParameter("id_flr") !=null)
		{
			id_flr = request.getParameter("id_flr");
		}
		if(request.getParameter("id_dept") !=null)
		{
			id_dept = request.getParameter("id_dept");
		}
		if(request.getParameter("id_building") !=null)
		{
			id_building = request.getParameter("id_building");
		}
		
		if(request.getParameter("typ_asst") !=null)
		{
			typ_asst = request.getParameter("typ_asst");
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
			String temp="";
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType");
			int UserId = (int)session.getAttribute("UserId");
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        case "Save":
	            	jobj = IntraUnitTransferRequestSave(map,ids,id_emp_user,asset_tran,UserId,  request);	
	                break;
		        
		        case "Display":
	            	//jobj = IntraUnitTransferRequest(temp,UserType);	
	            	
	            	String tempSql="";
	            	String likeTempQuery="";
	            	DtoCommon dtoCommon = new DtoCommon();
	            	if(!word.equals(""))
        			{
        				 likeTempQuery = dtoCommon.MakeLikeTempQuery(word);
        			}
	            	if(UserType.equals("Super")){
	            		tempSql="select top 100 id_wh,id_wh_dyn,ds_pro,no_mfr,modl.ds_asst,serial_no,nm_model from A_Ware_House wh,M_Model modl where wh.id_model=modl.id_model  and id_flr="+id_flr+" and id_dept="+id_dept+" ";
	            	}else {
	            		String tempQuery = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
	            	 tempSql="select top 100 id_wh,id_wh_dyn,ds_pro,no_mfr,modl.ds_asst,serial_no,nm_model from A_Ware_House wh,M_Model modl where  wh.id_model=modl.id_model  and id_flr="+id_flr+" "+tempQuery+" " ;
	            	}
	            	//tempSql="select top 100 id_wh,id_wh_dyn,ds_pro,no_mfr,modl.ds_asst,serial_no,nm_model from A_Ware_House wh,M_Model modl where wh.id_model=modl.id_model and (allocate=1 or allocate=2) and id_flr="+id_flr+" ";
		            
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
				System.out.println("Error in T_Inter_Transfer_Request.");
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
			
	/*String sql="select ds_pro,no_mfr as mfr,nm_assetdiv,ds_asst,nm_loc,nm_flr,nm_subl,wh.id_loc as id_loc_tran,wh.id_subl as id_subl_tran,wh.id_flr "+
	" from A_Ware_House wh , M_Asset_Div ad , M_Loc l, M_Subloc sl ,M_Floor f "+
			" where  id_wh = "+id+" and wh.id_loc=l.id_loc and wh.id_subl = sl.id_sloc "+
					" and wh.id_flr = f.id_flr and wh.id_grp = ad.id_assetdiv";
			*/
			String sql="select ds_pro,no_mfr as mfr,nm_assetdiv,ds_asst,nm_loc,nm_subl,wh.id_loc as id_loc_tran,wh.id_subl as id_subl_tran "+
					" from A_Ware_House wh , M_Asset_Div ad , M_Loc l, M_Subloc sl "+
							" where  id_wh = "+id+" and wh.id_loc=l.id_loc and wh.id_subl = sl.id_sloc "+
									"  and wh.id_grp = ad.id_assetdiv";
							
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




	
	

public JSONObject IntraUnitTransferRequestSave(HashMap<String, String> map , 
		String ids,int id_emp_user,String asset_tran,int UserId,HttpServletRequest request)
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
					
					
					String req_no="",id="";
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
						id = (String) st.nextElement();
					if(ids_wh.equals(""))
						ids_wh = id;
					else
						ids_wh = ","+id;
					
					String sql ="select id_loc,id_subl,id_building,id_div,id_flr,id_dept from A_ware_House where id_wh ="+id+"";
					
    				ps=con.prepareStatement(sql);
    				rs = ps.executeQuery();
    				if(rs.next())
    				{
    					j=0;
    					 query="insert into A_Iut_History("+colName+" ,iut_approval, tran_req_by,id_loc,id_subl,id_wh,id_building,id_div,id_flr,id_dept,req_no_transfer,req_dt_transfer) values("+value+" ,1,"+id_emp_user+","+rs.getString(1)+","+rs.getString(2)+","+id+" ,"+rs.getString(3)+" ,"+rs.getString(4)+" ,"+rs.getString(5)+","+rs.getString(6)+",'"+req_no+"',GETDATE())";
    					ps=con.prepareStatement(query);
    					j=ps.executeUpdate();	
    					if(j>0)
    					{
    						j=0;
    						query="update A_Ware_House set asset_tran='"+asset_tran+"',st_trvl=1,req_by="+id_emp_user+",req_no="+req_no+",dt_req=GETDATE() where id_wh="+id+"";
        					ps=con.prepareStatement(query);
        					j=ps.executeUpdate();
        					
    					}
    				}
					}
	if(j > 0)
	{
		j=0;
		if(asset_tran.equals("Returnable")){
			String id_ven = map.get("id_ven");
			query="insert into T_Transfer_Track (id_wh,req_no,req_by,id_ven) values('"+ids_wh+"','"+req_no+"',"+id_emp_user+","+id_ven+")";
		}else{
		query="insert into T_Transfer_Track (id_wh,req_no,req_by) values('"+ids_wh+"','"+req_no+"',"+id_emp_user+")";
		}
		ps=con.prepareStatement(query);
		j=ps.executeUpdate();
		
		j=0;
		
		
		//.........for mail ........
    	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="",nm_db="";
    	
    	String Sql ="select m.id_emp from M_Emp_User r, M_Emp_User m where r.id_emp_user="+id_emp_user+" and r.repo_mngr=m.id_emp_user";
    	System.out.println("Sql is"+ Sql);
    	DtoCommon dtcm = new DtoCommon();
		List<String> recipients = dtcm.ReturnListData(Sql,  request);
		
	if(!recipients.isEmpty())
	{
		//replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
		
		Sql ="select m.id_emp,m.nm_emp from M_Emp_User r, M_Emp_User m where r.id_emp_user="+id_emp_user+" and r.repo_mngr=m.id_emp_user";
		System.out.println(Sql);
		recipients = dtcm.ReturnEmail(Sql, recipients,  request);
		rs = dtcm.GetResultSet(Sql,  request);
		if(rs.next())
		{
			replyMailId = rs.getString(1);
			recipients.remove(replyMailId);
			name=rs.getString(2);
		}
		String mailSql ="select DB_NAME() AS [Current Database],c.nm_com from M_Company c";
		 rs1 = dtcm.GetResultSet(mailSql,  request);
			if(rs1.next())
			{
				
				nm_db= rs1.getString(1);
				
			}
    	/*String mailSql ="select sa.nm_s_assetdiv,no_model,no_mfr,wh.id_wh_dyn from M_Subasset_Div sa, A_Ware_House wh where wh.id_wh='"+id+"' and sa.id_s_assetdiv=wh.id_sgrp";
    
		rs = dtcm.GetResultSet(mailSql);
		if(rs.next())
		{
			itemName = rs.getString(1);
			no_model = rs.getString(2);
			slNo = rs.getString(3);
			id_wh_dyn = rs.getString(4);
			
		}*/
		
		String link = dtcm.ReturnParticularMessage("baseUrlLink");
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		String footerMsg2 = dtcm.ReturnParticularMessage("footerMsg2");
		String mailSubject = dtcm.ReturnParticularMessage("interTrnReq");
		
		String mailBody = "<b>"+name+" , </b><br><br><br>"+
					  "<p>Executive has been raised a request for transfer movement having request number <b>(Request No-"+req_no+")</b>. Please approve the same.</p><br>"+
					  "<br><br><br>"+link+""+
					  "<br>For login....<a href='"+link+""+nm_db+".html'>Click Here</a>"+
					  "<br><br><p>"+footerMsg+"</p>";
		System.out.println(link);
		System.out.println(footerMsg);
		System.out.println(footerMsg2);
		System.out.println(mailSubject);
		Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}
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
