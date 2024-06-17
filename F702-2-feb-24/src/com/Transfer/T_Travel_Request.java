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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;

import dto.Common.DtoCommon;


public class T_Travel_Request extends HttpServlet {
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
		    
		String action = "",id="",word="",dt_st_trvl="";
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
	        			tempSql= "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst,nm_emp from A_Ware_House wh,M_Emp_User emp where allocate=1 and st_trvl=0 and emp.id_emp_user=wh.to_assign and "+tempQuery+" "+likeTempQuery+"" ;
	        		}
	        		else
	        			tempSql= "select id_wh,id_wh_dyn,ds_pro,no_mfr,ds_asst,nm_emp from A_Ware_House wh,M_Emp_User emp where allocate=1 and st_trvl=0 and emp.id_emp_user=wh.to_assign "+likeTempQuery+"" ;
	            				
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
	
	
	public JSONObject CheckDate(String dt_alloc , String id_wh)
	{
		JSONObject jobj = new JSONObject();
		
 		
	//	  sql ="select dt_alloc from A_Ware_House where id_wh = "+id_wh+" and dt_alloc > '"+dt_alloc+"' ";
		  String sql = "select max(dt_start) from A_Iut_History where id_wh = "+id_wh+"";
			 
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sdf.parse(dt_alloc);
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		    if(rs.next())
		    {
		    	if(d.before(rs.getDate(1)))
		    	{
		    		jobj.put("data", 0);
			    	jobj.put("dt_alloc", rs.getString(1));
		    	}
		    	else
			    {
			    	jobj.put("data", 1);
			    }
		    	
		    	
	        }
		    
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install.");
		}
		 
		return jobj;
		
		
	}
	
	/*public JSONObject CheckDate(String dt_alloc ,String id)
	{
		JSONObject jobj = new JSONObject();
		
		try
		{
		int t=1;
    	String  sql ="";
    	String d = "1900-01-01";
    	sql ="select dt_alloc from A_Ware_House where allocate=1 and dt_alloc > '"+dt_alloc+"' and id_wh="+id+" ";
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
        		jobj.put("dt_alloc",d);
    	}	
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in G_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	*/
	
	
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
					
				String query="insert into T_Travel("+colName+",req_by,id_wh) values("+value+","+id_emp_user+","+id+")";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();	
				stmt = con.createStatement();
				stmt.executeUpdate("update A_Ware_House set st_trvl=1 where id_wh="+id+"");
				j=1;
		
	if(j > 0)
	{
		j=0;
		
		//.........for mail ........
    	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="";
    	
    	String Sql ="select id_emp from A_Ware_House wh,M_User_Login l,M_Emp_User emp "+
    				" where wh.id_wh="+id+" and l.typ_asst like '%'+wh.typ_asst+'%' and emp.id_emp_user=l.id_emp_user "+
    				" and (l.type_user='EXCTV' or l.type_user='MNGR')";
    	
    	DtoCommon dtcm = new DtoCommon();
		List<String> recipients = dtcm.ReturnListData(Sql,  request);
		
	if(!recipients.isEmpty())
	{
		//replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
		
		Sql ="select id_emp from A_Ware_House wh,M_User_Login l,M_Emp_User emp "+
				" where wh.id_wh="+id+" and l.typ_asst like '%'+wh.typ_asst+'%' and emp.id_emp_user=l.id_emp_user "+
				" and  l.type_user='MNGR'";
		rs = dtcm.GetResultSet(Sql,  request);
		if(rs.next())
		{
			replyMailId = rs.getString(1);
			recipients.remove(rs.getString(1));
		}
    	String mailSql ="select sa.nm_s_assetdiv,no_model,no_mfr,wh.id_wh_dyn from M_Subasset_Div sa, A_Ware_House wh where wh.id_wh='"+id+"' and sa.id_s_assetdiv=wh.id_sgrp";
    
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			itemName = rs.getString(1);
			no_model = rs.getString(2);
			slNo = rs.getString(3);
			id_wh_dyn = rs.getString(4);
			
		}
		String link = dtcm.ReturnParticularMessage("link");
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		
		String mailBody = "Hello <b>Manager Team</b>,<br><br><br>"+
					  "The <b>'"+itemName+"'</b> having Model No <b>'"+no_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b>  has been raised for travel. Please do the needful.<br>"+
					  "<br><br><br>Click here for login....<a href='"+link+"'>Click Here</a>"+
					  "<br><br>"+footerMsg+""+
					  "<br><br><b>Thanks & Regards, <b>"+
					  "<br>Intuit India";
	
		Common.MailConfiguration(mailBody, replyMailId, recipients , "");
		
		j=1;
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

}
