package com.Purchase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;
import com.Util.dtoUtils;

import dto.Common.DtoCommon;


public class P_Create_RFI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String UserName = (String)session.getAttribute("UserName");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		HashMap<String, String> map =new HashMap<String,String>();
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd"); 
		 Enumeration elemet = request.getParameterNames();
		 
		 String paramName="";
		 String paramValues="";
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
		String action = "",word="",id="0",req_ast_id="",id_rfi="",confirm="1",dt_to="",dt_frm="",value="",ColName="",no_req_val="",dt_req="";
		
		if(request.getParameter("dt_req") !=null)
		{
			dt_req = request.getParameter("dt_req");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("no_req_val") !=null)
		{
			no_req_val = request.getParameter("no_req_val");
		}
		if(request.getParameter("invoiceId") !=null)
		{
			req_ast_id = request.getParameter("invoiceId");
		}
		if(request.getParameter("id_rfi") !=null)
		{
			id_rfi = request.getParameter("id_rfi");
		}
		if(request.getParameter("confirm") !=null)
		{
			confirm = request.getParameter("confirm");
		}
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
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
			word = request.getParameter("searchWord");
		}
		try
		{
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String User_Type = (String)session.getAttribute("UserType");
			Date currDate = new Date();
			if(dt_frm.equals(""))
			{
				dt_frm = "1990-01-01";
			}
			else
			{
				dt_frm = dateFormatNeeded.format(userDateFormat.parse(dt_frm));
			}
			if(dt_to.equals(""))
			{
				dt_to = dateFormatNeeded.format(currDate);
			}
			else
			{
				dt_to = dateFormatNeeded.format(userDateFormat.parse(dt_to));
			}
			
			if(!dt_req.equals(""))
			dt_req = dateFormatNeeded.format(userDateFormat.parse(dt_req));
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "LoginUserDetails":
	            	jobj = LoginUserDetails(id_emp_user);	
	                break;
	           
	            case "Update":
	            	jobj = UpdatePurchaseRequest(map,id,req_ast_id,id_emp_user,no_req_val,  request);	
	                break;
	            case "Display":
	            	jobj = DisplayPurchaseRequest(id,dt_frm,dt_to,id_emp_user,User_Type,word);	
	                break;
	            case "DisplayEvaluate":
	            	  String sql3 ="select sr.*,(replace(convert(NVARCHAR, r.dt_rfi, 103), ' ', '-')) as dtrfi,r.no_rfi from P_Submit_RFI sr,P_RFI r where sr.id_rfi=r.id_rfi ";
	            	 System.out.println(sql3);
		            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql3,  request);	
	                break;
	            case "Edit":
	            	jobj = DisplayPurchaseRequest(id,"","",id_emp_user,User_Type,word);	
	                break;
	            case "EditEvaluate":
	            	String no_rfi1=request.getParameter("no_rfi");
	            	 sql3 ="select sr.*,r.no_rfi,q.*,a.*,v.nm_ven "
	            	 		+ " from P_Submit_RFI sr,P_RFI r ,P_RFI_Ques q,P_RFI_Ans a,M_Vendor v "
	            	 		+ "  where sr.id_rfi=r.id_rfi and q.id_rfi=sr.id_rfi and a.id_submit_rfi=sr.id_submit_rfi and sr.id_ven=v.id_ven and r.no_rfi='"+no_rfi1+"' ";
	            	 System.out.println(sql3);
		            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql3,  request);	
	                break;
	            case "DisplayRequestForPrint":
	            	jobj = DisplayRequestForPrint(id,dt_frm,dt_to,id_emp_user,User_Type,word);	
	                break;
	            case "DisplayRequestForPreview":
	            	jobj = DisplayRequestForPreview(id);	
	                break;
	            case "auto_jwno":
	            	jobj = Autoworkno();	
	            	
	                break;
	            case "EditLineItem":
	            	jobj = EditLineItem(id);	
	                break;
	            case "DisplayAssetForPR":
	            	jobj = DisplayAssetForPR(id);	
	                break;
	            case "DisplayQuesForPR":
	            	jobj = DisplayQuesForPR(id);	
	                break;
	            case "SendForApproval":
	            	jobj = SendForApproval(id);	
	                break;
	            case "Delete":
	            	jobj = DeletePurchaseRequest(id,confirm);	
	                break;
	            case "DeleteAssetPR":
	            	jobj = DeleteAssetPR(id , id_rfi);	
	                break;
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	            case "CheckDate":
	            	jobj = CheckRequestedDate(dt_req);	
	                break;
	                
	            case "Save":
	            	//jobj = AddPurchaseRequest(map,no_req_val,id_emp_user);
	            	String colName="",no_rfi=no_req_val;
	        		int j=0,id_rfi1=0;
	        		value="";
	        		String col_Val="";
	        		JSONObject json=new JSONObject();

	        		try 
	        		{
	        			
	        			if(no_rfi.equals("new"))
	        			{
	        				rs = Common.GetColumns("P_RFI",  request);
	        				
	        				
	        				while (rs.next())
	        				{
	        				
	        					if(rs.getString(1) !="id_rfi")
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
							/*
							 * String section = map.get("id_section"); String sectionCode =
							 * map.get("ind_section_code"); String indType = map.get("ind_type");
							 */
	        				
	        				 //String reqNo = getReqNo(section,sectionCode,indType);
	        				 String sql="insert into P_RFI("+colName+",add_by) values("+value+","+id_emp_user+")";
	        				stmt=con.createStatement();
	        				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
	        				 rs1 = stmt.getGeneratedKeys();
	        						 
	        		         while (rs1.next()) 
	        		         {
	        		        	 id_rfi1 = rs1.getInt(1);
	        		         }
	        		         
	        		         //no_rfi=reqNo;
	        		            
	        			}
	        			else
	        			{
	        				String sql = "select id_rfi from P_RFI where no_rfi='"+no_rfi+"'";
	        				ps=con.prepareStatement(sql);
	        				rs=ps.executeQuery();
	        				System.out.println(sql);
	        				if(rs.next())
	        				{
	        					id_rfi1=rs.getInt(1);
	        				}
	        				
	        				
	        				
	        			}
	        		
	        		}
	        		catch(Exception e)
	        		{
	        			System.out.println("Error in P_RFI_Asset"+e.toString());
	        		}
	        		try
	        		{
	        			colName="";value="";
	        			String id_grp="",id_sgrp="",id_prod="",model="",mfr="";
	        			String count[] = request.getParameterValues("count");
	        			String count1[] = request.getParameterValues("count1");
	        			stmt=con.createStatement();
	        			for(int k=0;k<count.length;k++)
	        			{					
	        				j=0;
	        				String nm_prod = request.getParameter("id_prod"+count[k]+"");
	        				if(!nm_prod.isEmpty())
	        				{
	        					id_grp= request.getParameter("id_grp"+count[k]+"");
	        					id_sgrp= request.getParameter("id_sgrp"+count[k]+"");
	        				
	        				String id_rfi_asset = request.getParameter("id_rfi_asset"+count[k]+"");
	        				
	                		String qty = request.getParameter("qty"+count[k]+"");
	                		
	                		if(id_rfi_asset == null || id_rfi_asset.equals("")){
	                			
	                			System.out.println("insert into P_RFI_Asset(id_rfi,qty,id_grp,id_sgrp,id_prod) values('"+id_rfi1+"','"+qty+"','"+id_grp+"','"+id_sgrp+"','"+nm_prod+"')");
	  		              
	                			j=stmt.executeUpdate("insert into P_RFI_Asset(id_rfi,qty,id_grp,id_sgrp,id_prod) values('"+id_rfi1+"','"+qty+"','"+id_grp+"','"+id_sgrp+"','"+nm_prod+"')");
	                			
	                		}else{
	                			stmt.executeUpdate("update P_RFI_Asset set qty='"+qty+"',id_prod='"+id_prod+"' where id_rfi_asset="+id_rfi_asset+"") ;
	                		
	                		j=1;
	                		}
	        				}
	        			}
	        			
	        			for( int i=0;i<count1.length;i++)
	        			{
        					String ques = request.getParameter("ques"+count1[i]+"");
        					
        					if(!ques.isEmpty())
	        				{
        						String id_rfi_question = request.getParameter("id_rfi_question"+count[i]+"");
        						if(id_rfi_question == null || id_rfi_question.equals(""))
        						{
        						System.out.println("insert into P_RFI_Ques(id_rfi,ques) values('"+id_rfi1+"','"+ques+"')");
      	  		              
	                			j=stmt.executeUpdate("insert into P_RFI_Ques(id_rfi,ques) values('"+id_rfi1+"','"+ques+"')");
	                			if(j>0)
	                				j=1;
        						}
        						else{
    	                			stmt.executeUpdate("update P_RFI_Ques set ques='"+ques+"' where id_rfi_question="+id_rfi_question+"") ;
    	                			if(j>0)
    	                			j=1;
        						}
	        				}
	        			}
						
	        			}
	        			catch(Exception e)
	        				{
	        					System.out.println("Some error in P_RFI_Asset." +e);
	        				}
	        			
	        		jobj.put("data",id_rfi1);
	        		jobj.put("no_rfi",no_rfi);
	        		
	                break;
	                
	            case "Approve":
	            	//jobj = AddPurchaseRequest(map,no_req_val,id_emp_user);
	        		 j=0;
	        		try
	        		{
	        			 id = request.getParameter("id");
	        			stmt=con.createStatement();
            			j=stmt.executeUpdate("update P_RFI set approve=1 where id_rfi="+id+"") ;
	                			if(j>0)
	                			{
	                				j=1;
	                			}
	                		
	                		}
						
	        			
	        			catch(Exception e)
	        				{
	        					System.out.println("Some error in P_RFI_Asset." +e);
	        				}
	        			
	        		jobj.put("data",j);
	        		
	                break;
	                
	            case "Reject":
	            	//jobj = AddPurchaseRequest(map,no_req_val,id_emp_user);
	        		 j=0;
	        		try
	        		{
	        			 id = request.getParameter("id");
	        			stmt=con.createStatement();
            			j=stmt.executeUpdate("update P_RFI set approve=0 where id_rfi="+id+"") ;
	                			if(j>0)
	                			{
	                				j=1;
	                			}
	                		
	                		}
						
	        			
	        			catch(Exception e)
	        				{
	        					System.out.println("Some error in P_RFI_Asset." +e);
	        				}
	        			
	        		jobj.put("data",j);
	        		
	                break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in P_RFI_Asset.");
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

	public JSONObject DisplayRequestForPreview(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
			//sql ="select r.*,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,s.nm_section,to_sec.nm_section as to_section,emp_req.nm_emp||emp_req.cd_emp as nm_emp_req,r.no_rfi,r.dt_req, pc.nm_prod,pc.cd_prod,pc.uom,pc.description,ra.qty,ra.un_prc,ra.tot_prc,ra.stock_qty,r.grand_tot,remarks from P_RFI r, P_RFI_Asset ra,M_Prod_Cart pc,M_Section s,M_Section to_sec, M_Emp_User emp_req  where r.id_rfi=ra.id_rfi and pc.id_prod=ra.id_prod and s.id_section=r.id_section and to_sec.id_section=r.id_to_section and r.req_by=emp_req.id_emp_user and r.id_rfi="+id+"";
sql="select r.*,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,r.no_rfi,r.dt_req, pc.nm_model,pc.cd_model,nm_dept, "+
    "   ra.qty,ra.un_prc,ra.tot_prc,r.grand_tot,remarks from P_RFI r, P_RFI_Asset ra,M_Model pc, M_Dept md, "+
    " M_Emp_User emp_req  where r.id_rfi=ra.id_rfi and pc.id_model=ra.id_prod and r.id_dept=md.id_dept"+
    " and r.req_by=emp_req.id_emp_user and r.id_rfi="+id+" " ;
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
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
	
		    jobj.put("todaydate",formatter.format(date));
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in P_RFI_Asset."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject Autoworkno()
	{
		String finyear="",poID2="";
		int PoId=1;
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			String year="",end="";
		//	ps=con.prepareStatement("select extract(year from to_date(STD_FINANCE,'yyyy/mm/dd')) as stdate,extract(year from to_date(END_FINANCE,'yyyy/mm/dd')) as endyear from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			System.out.println("select std_finance as  start , endt_second as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			 
			ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
				rs=ps.executeQuery();
			if(rs.next())
			{
				year=rs.getString("start");
				end=rs.getString("enddt");
			}
			 
			
			String strt = year.substring (2,4);
			String endt = end.substring (2,4);
		ps=con.prepareStatement("select count(id_rfi) from P_RFI  ");
		rs=ps.executeQuery();
		
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				
			}
			
		String 	poID3 = String.format("%03d", PoId);
			poID2= "RFI"+poID3+"/"+strt+"-"+endt;
			  System.out.println(poID2);
			  jobj.put("wo_no",poID2);
			
		}
		
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return jobj;
	}
	public JSONObject SendForApproval(String id)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		
		String query="update P_RFI set send_for_apprvl='Yes' where id_rfi="+id+"";
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
	
	
	public JSONObject LoginUserDetails(int id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String sql ="select * from M_Emp_User where id_emp_user = "+id+"";
		
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
		    		String name=metaData.getColumnName(i).toLowerCase();
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in M_Emp_User."+e.toString());
		}
		 
		return jobj;
		
		
	}
	public JSONObject UpdatePurchaseRequest(HashMap<String, String> map, String id, String id_rfi_asset,int id_emp_user, String no_rfi,HttpServletRequest request)
	{
		String col_Val="",col_Val1="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("P_RFI",  request);
				
						while (rs.next())
						{
							if(rs.getString(1) !="id_rfi")
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
						
						rs = Common.GetColumns("P_RFI_Asset",  request);
						
						while (rs.next())
						{
							if(rs.getString(1) !="id_rfi_asset" || rs.getString(1) !="id_rfi")
							{
								    if (map.containsKey(rs.getString(1)))
								    {
								    	if(col_Val1.equals(""))
								    	{
								    		col_Val1 += rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								    	}
								    	else
								    	{
								    		col_Val1 += ","+ rs.getString(1) + "='" + map.get(rs.getString(1)) + "'";
								    	}
								    }
							  }
						}		
				
			}
			catch(Exception e)
				{
					System.out.println("Some error in P_RFI_Asset.");
				}
		try 
		{
		String query="";
		
		 query="update P_RFI set "+col_Val+" where id_rfi="+id+"";
		
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				query="update P_RFI_Asset set "+col_Val1+" where id_rfi_asset="+id_rfi_asset+"";
				 
				PreparedStatement ps2=con.prepareStatement(query);
				j=ps2.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
			}
		
			json.put("data",id);
			json.put("no_rfi",no_rfi);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	public JSONObject CheckRequestedDate(String dt_req)
	{
		JSONObject json=new JSONObject();
		
		String query = "select std_finance,end_finance from M_Finance_Year where std_finance <= '"+dt_req+"' and end_finance >= '"+dt_req+"' and active_year=1";
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
				query = "select (replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance from M_Finance_Year where active_year=1";
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					json.put("data",0);
					json.put("std_finance",rs.getString(1));
					json.put("end_finance",rs.getString(2));
				}
				
			}
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
		
		String query = "select id_rfi from P_RFI where "+ColName+" = '"+value+"'";
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
	
	
	public JSONObject DeleteAssetPR(String id ,String id_rfi)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			if(!id_rfi.equals("0"))
			{
					String query = "select COUNT(id_rfi) from P_RFI_Asset where id_rfi = "+id_rfi+" ";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						int rowCount = rs.getInt(1);
						if(rowCount > 1)
						{
							query = "delete P_RFI_Asset where id_rfi_asset = "+id+"";
							ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								query = "select sum(tot_prc) from P_RFI_Asset where id_rfi = "+id_rfi+" ";
								ps=con.prepareStatement(query);
								rs=ps.executeQuery();
								if(rs.next())
								{
									 query="update P_RFI set grand_tot ='"+rs.getString(1)+"' where id_rfi = "+id_rfi+"";
										
											PreparedStatement ps=con.prepareStatement(query);
											ps.executeUpdate();
											j=1;
								}
								
							}
							
							
							
							
						}
						else
						{
							j=2;
						}
					}
					
			}
			else
			{
				
				String query = "select id_rfi from P_RFI_Asset where id_rfi_asset = "+id+"";
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_rfi = rs.getString(1);
				}
				
				 query = "delete P_RFI_Asset where id_rfi_asset = "+id+"";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					query = "delete P_RFI where id_rfi = "+id_rfi+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						query = "select sum(tot_prc) from P_RFI_Asset where id_rfi = "+id_rfi+" ";
						ps=con.prepareStatement(query);
						rs=ps.executeQuery();
						if(rs.next())
						{
							 query="update P_RFI set grand_tot ='"+rs.getString(1)+"' where id_rfi = "+id_rfi+"";
								
									PreparedStatement ps=con.prepareStatement(query);
									ps.executeUpdate();
									j=1;
						}
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
	
	
	public JSONObject DeletePurchaseRequest(String id , String confirm)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			if(!confirm.equals("0"))
			{
		String query = "select COUNT(id_rfi_asset) from P_RFI_Asset where id_rfi = "+id+"";
		ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		
				if(rs.next())
				{
					int a= rs.getInt(1);
						if(a > 1)
						{
							query = "delete P_RFI_Asset where id_rfi = "+id+"";
							
							PreparedStatement ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								query = "delete P_RFI where id_rfi = "+id+"";
								ps=con.prepareStatement(query);
								j=ps.executeUpdate();
								if(j > 0)
								{
									j=1;
								}
							}
						}
							else
							{
								j=2;
							}
					
				}
			}
		else
		{
			String query = "delete P_RFI_Asset where id_rfi = "+id+"";
			
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				query = "delete P_RFI where id_rfi = "+id+"";
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
	
	
	public JSONObject EditLineItem(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		String	sql ="select ra.*,ad.NM_ASSETDIV,sd.NM_S_ASSETDIV,nm_prod as nm_prod1 from P_RFI_Asset ra,M_asset_div ad,m_subasset_div sd,M_PROD_CART pc where ra.id_grp=ad.ID_ASSETDIV and ra.ID_SGRP=sd.ID_S_ASSETDIV and ra.ID_PROD=pc.ID_PROD and id_rfi_asset="+id+"";
		
		
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
			System.out.println("sql error in P_RFI_Asset.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DisplayAssetForPR(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		String	sql ="select ra.*,nm_model,cd_model from P_RFI_Asset ra,m_model pc,P_RFI r where ra.id_prod=pc.id_model and r.id_rfi=ra.id_rfi and r.id_rfi="+id+"";
		
		
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
			System.out.println("sql error in P_RFI_Asset.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayQuesForPR(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		String	sql ="select ra.* from P_RFI_ques ra,P_RFI r where r.id_rfi=ra.id_rfi and ra.id_rfi="+id+"";
		
		
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
			System.out.println("sql error in P_RFI_Asset.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayRequestForPrint(String id,String dt_frm,String dt_to,int id_emp_user,String User_Type,String word)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		if(dt_frm.equals(""))
		{
			dt_frm = "1990-01-01";
		}
		
		if(dt_to.equals(""))
		{
			dt_to = sdf.format(currDate);	
		}
		String tempSql ="";
		if(!word.isEmpty())
			 tempSql =" and ( r.dt_req LIKE '%"+word+"%' or r.no_rfi LIKE '%"+word+"%' or emp.nm_emp LIKE '%"+word+"%')";
		
				if(id.equals("0"))
				{
					if(User_Type.equals("SUPER"))
					sql ="select r.*,nm_dept,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,nm_emp,r.id_rfi,r.ID_DEPT,ID_REQ  from P_request r,M_Emp_User emp  "+
		               "  ,m_dept md where st_approv ='waiting' and r.req_by=emp.id_emp_user and md.id_dept=r.id_dept and send_for_apprvl='No'  " +tempSql+ "  Order by r.dt_req DESC  ";
                	else
                		sql ="select r.*,nm_dept,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,nm_emp,r.id_rfi,r.ID_DEPT,ID_REQ  from P_request r,M_Emp_User emp  "+
         		               "  ,m_dept md where st_approv ='waiting' and r.req_by=emp.id_emp_user and md.id_dept=r.id_dept and send_for_apprvl='No'  " +tempSql+ "  Order by r.dt_req DESC  ";
                     
		}
		else
		{
			if(User_Type.equals("SUPER"))
				sql ="select grand_tot,NO_REQ,id_to_section,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as dt_req,nm_emp,nm_cc,r.ID_REQ,r.NO_REQ,r.REQ_BY,r.ID_DEPT,r.REMARKS,r.ID_CC,r.FIRST_LA,r.SECOND_LA,r.THIRD_LA,r.FOURTH_LA,r.ID_SECTION,r.REPO_MNGR  from P_request r,M_Emp_User emp,M_Company_Costcenter cc "+
					" where st_approv ='waiting' and r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc and send_for_apprvl='No' and id_rfi="+id+"";
			else
				sql ="select grand_tot,NO_REQ,id_to_section,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as dt_req,nm_emp,nm_cc,r.ID_REQ,r.NO_REQ,r.REQ_BY,r.ID_DEPT,r.REMARKS,r.ID_CC,r.FIRST_LA,r.SECOND_LA,r.THIRD_LA,r.FOURTH_LA,r.ID_SECTION,r.REPO_MNGR from P_request r,M_Emp_User emp,M_Company_Costcenter cc "+
						" where st_approv ='waiting' and r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc and send_for_apprvl='No' and  add_by="+id_emp_user+" and id_rfi="+id+"";
		}
		
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
			System.out.println("sql error in P_RFI_Asset."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayPurchaseRequest(String id,String dt_frm,String dt_to,int id_emp_user,String User_Type,String word)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		if(dt_frm.equals(""))
		{
			dt_frm = "1990-01-01";
		}
		
		if(dt_to.equals(""))
		{
			dt_to = sdf.format(currDate);	
		}
		String tempSql ="";
		if(!word.isEmpty())
		// tempSql =" and (REGEXP_LIKE(frmSec.nm_section,'"+word+"','i') or REGEXP_LIKE(toSec.nm_section,'"+word+"','i') or REGEXP_LIKE(to_char (to_date(r.dt_req,'YYYY-MM-DD'), 'DD/MM/YYYY'),'"+word+"','i') or REGEXP_LIKE(r.no_rfi,'"+word+"','i') or REGEXP_LIKE(emp.nm_emp,'"+word+"','i'))";
		
		 tempSql =" and ( r.dt_rfi LIKE '%"+word+"%' or r.no_rfi LIKE '%"+word+"%' or emp.nm_emp LIKE '%"+word+"%')";
		
		if(id.equals("0"))
		{
			if(User_Type.equals("SUPER"))
			sql ="select rfi.*,(replace(convert(NVARCHAR, rfi.dt_rfi, 103), ' ', '-')) as dtrfi "
					+ " from P_RFI rfi where rfi.approve=0  ";

			else
				sql ="select rfi.*,(replace(convert(NVARCHAR, rfi.dt_rfi, 103), ' ', '-')) as dtrfi "
						+ " from P_RFI rfi where rfi.approve=0 ";
		}
		else
		{
			if(User_Type.equals("SUPER"))
				sql ="select rfi.*,e.nm_emp,e1.nm_emp,(replace(convert(NVARCHAR, rfi.dt_rfi, 103), ' ', '-')) as dtrfi,(replace(convert(NVARCHAR, rfi.dt_rfi_start, 103), ' ', '-')) as dtstartrfi,(replace(convert(NVARCHAR, rfi.dt_rfi_end, 103), ' ', '-')) as dtendrfi"+
						" from P_Rfi rfi,M_Emp_User e,M_Emp_User e1"+
						 " where rfi.rfi_by=e.id_emp_user and rfi.firstla=e1.id_emp_user and id_rfi="+id+" ";
                    
			else
				sql ="select rfi.*,e.nm_emp,e1.nm_emp,(replace(convert(NVARCHAR, rfi.dt_rfi, 103), ' ', '-')) as dtrfi"+
						" from P_Rfi rfi,M_Emp_User e,M_Emp_User e1"+
						 " where rfi.rfi_by=e.id_emp_user and rfi.firstla=e1.id_emp_user and id_rfi="+id+"";
				}
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
			System.out.println("sql error in P_RFI_Asset."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject AddPurchaseRequest(HashMap<String, String> map,String no_rfi , int id_emp_user,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0,id_rfi=0;
		JSONObject json=new JSONObject();

		try 
		{
			
			if(no_rfi.equals("new"))
			{
				rs = Common.GetColumns("P_RFI",  request);
				
				
				while (rs.next())
				{
				
					if(rs.getString(1) !="id_rfi")
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
				String id_section = map.get("id_section");
				// String reqNo = getReqNo(id_section);
				 String reqNo = "";
				 String sql="insert into P_RFI("+colName+",add_by,no_rfi,CREATED_BY) values("+value+","+id_emp_user+",'"+reqNo+"',"+id_emp_user+")";
				 /*stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs1 = stmt.getGeneratedKeys();*/
				 PreparedStatement stmt_pre = con.prepareStatement(sql, new String[] { "id_rfi" });
				 stmt_pre.executeUpdate();
				 rs1 = stmt_pre.getGeneratedKeys();
				 
		         while (rs1.next()) 
		         {
		            	id_rfi = rs1.getInt(1);
		         }
		         
		         no_rfi=reqNo;
		            
		       // Mail Trigger......
		            
	            	String replyMailId="",name="";
	            	String mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_request r where emp.id_emp_user=r.firstLA and no_rfi='"+no_rfi+"'";
	            	
	            	DtoCommon dtcm = new DtoCommon();
	    			rs = dtcm.GetResultSet(mailSql,  request);
	    			if(rs.next())
	    			{
	    				replyMailId = rs.getString(1);
	    				name = rs.getString(2);
	    			}
	    			mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_request r where emp.id_emp_user=r.req_by and no_rfi='"+no_rfi+"'";
	    			
	    			List<String> recipients = dtcm.ReturnListData(mailSql,  request);
	    			
	    			String link = dtcm.ReturnParticularMessage("link");
	    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	    			String mailSubject = dtcm.ReturnParticularMessage("purchaseReq");
	    			
	    			String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
	    						  "Purchase request <b>("+no_rfi+")</b> has been created. Please approve it.<br>"+
	    						  "<br><br><br>"+link+""+
								  "<p>"+footerMsg+"</p>";
	    		
	    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	    	        j=1;    
		            
			}
			else
			{
				String sql = "select id_rfi from P_RFI where no_rfi='"+no_rfi+"'";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_rfi=rs.getInt(1);
				}
				
			}
		
		}
		catch(Exception e)
		{
			System.out.println("Error in P_RFI_Asset"+e.toString());
		}
		try
		{
			colName="";value="";
				rs = Common.GetColumns("P_RFI_Asset",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_rfi_asset")
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
					System.out.println("Some error in P_RFI_Asset.");
				}
		
		String query="insert into P_RFI_Asset("+colName+",id_rfi) values("+value+","+id_rfi+")";
		System.out.println(query);
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
			}
			
			
			json.put("data",id_rfi);
			json.put("no_rfi",no_rfi);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	
public String getReqNo(String id_section,String sectionCode,String indType) throws SQLException
{
		
		ResultSet rs1=null;
		ResultSet rs2=null;
		ResultSet rs3=null;
		String strReqNo="-" ;
		String strfinyear="";
		String strTemp	=	"",year="",section="";
		int intPoId	=	0;
		boolean boolStatus			=	false;
		Statement stmt = con.createStatement();
		Statement stmt1 = con.createStatement();
		Statement stmt2 = con.createStatement();
		Statement stmt3 = con.createStatement();
		rs=stmt.executeQuery("select year(std_finance) as stdate,year(end_finance) as endyear from M_Finance_Year where active_year='1'");
		//rs=stmt.executeQuery("select extract(year from to_date(STD_FINANCE,'yyyy/mm/dd')) as stdate,extract(year from to_date(END_FINANCE,'yyyy/mm/dd')) as endyear from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
		if(rs.next())
		{
			strfinyear=rs.getString("stdate")+"-"+rs.getString("endyear");
			year=rs.getString("stdate");
			boolStatus=false;
		}
		else
		{
			boolStatus=true;
			return strReqNo ;
		}
		
		
		int norq=0;
		if(!boolStatus)
		{
		rs=stmt.executeQuery("select count(no_rfi) from P_request");
		
		
		if(rs.next())
		{
			norq=rs.getInt(1)+1;
			
			strReqNo="REQ/"+strfinyear+"/"+norq;
			
			/*
			rs1=stmt1.executeQuery("select * from P_request where substring(no_rfi,len(no_rfi)-8,len(no_rfi))='"+strfinyear+"'");
			rs1=stmt1.executeQuery("select no_rfi from P_request where SUBSTR(no_rfi,LENGTH(no_rfi)-7,4)='"+year+"'");
			if(rs1.next())
			{
				rs2=stmt2.executeQuery("select max(id_rfi) from P_request where SUBSTR(no_rfi,LENGTH(no_rfi)-7,4)='"+year+"'");
				if(rs2.next())
				{
					intPoId=rs2.getInt(1);
					rs3=stmt3.executeQuery("select no_rfi from P_request r where id_rfi="+intPoId+" ");
					if(rs3.next())
					{
						strTemp=rs3.getString(1);
						strTemp=strTemp.substring(strTemp.indexOf("D-")+2,strTemp.indexOf("/") );
						int intTempNo = Integer.parseInt(strTemp);
						intTempNo = intTempNo + 1;
						strReqNo="IND-"+intTempNo+"/"+year+"/"+section;
					}
				}
			}
			String yearSec = year+"/"+section;
			
			rs1=stmt1.executeQuery("select no_rfi from P_request where SUBSTR(no_rfi,1,7)='"+yearSec+"'");
			if(rs1.next())
			{
				rs2=stmt2.executeQuery("select max(id_rfi) from P_request where  SUBSTR(no_rfi,1,7)='"+yearSec+"'");
				if(rs2.next())
				{
					intPoId=rs2.getInt(1);
					rs3=stmt3.executeQuery("select SUBSTR(no_rfi,9,4) from P_request r where id_rfi="+intPoId+" ");
					if(rs3.next())
					{
						strTemp=rs3.getString(1);
						//strTemp=strTemp.substring(strTemp.indexOf("D-")+2,strTemp.indexOf("/") );
						int intTempNo = Integer.parseInt(strTemp);
						intTempNo = intTempNo + 1;
						
						strReqNo= year+"/"+section+"/"+String.format("%04d", intTempNo)+"/"+indType;
					}
				}
			}
			else
			{
				//strReqNo="IND-1/"+year+"/"+section;
				strReqNo= year+"/"+section+"/"+"0001"+"/"+indType;
			}
		}
		else
		{
			//strReqNo="IND-1/"+year+"/"+section;
			strReqNo= year+"/"+section+"/"+"0001"+"/"+indType;
		}*/
	}
		}
	return strReqNo ;
	}	
}
