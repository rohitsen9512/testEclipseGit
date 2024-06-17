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

import dto.Common.DtoCommon;


public class P_Request_PR extends HttpServlet {
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
		String action = "",mailcontant="",word="",id="0",req_ast_id="",id_req="",confirm="1",dt_to="",dt_frm="",value="",ColName="",no_req_val="",dt_req="";
		if(request.getParameter("mailcontant") !=null)
		{
			mailcontant = request.getParameter("mailcontant");
		}
		
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
		if(request.getParameter("id_req") !=null)
		{
			id_req = request.getParameter("id_req");
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
	                
	            case "Edit":
	            	jobj = DisplayPurchaseRequest(id,"","",id_emp_user,User_Type,word);	
	                break;
	                
	            case "DisplayRequestForPrint":
	            	jobj = DisplayRequestForPrint(id,dt_frm,dt_to,id_emp_user,User_Type,word);	
	                break;
	            case "DisplayRequestForPreview":
	            	jobj = DisplayRequestForPreview(id);	
	                break;
	                
	            case "EditLineItem":
	            	jobj = EditLineItem(id);	
	                break;
	            case "DisplayAssetForPR":
	            	jobj = DisplayAssetForPR(id);	
	                break;
	            case "SendForApproval":
	            	
	            	jobj = SendForApproval(id,mailcontant,  request);	
	                break;
	            case "Delete":
	            	jobj = DeletePurchaseRequest(id,confirm);	
	                break;
	            case "DeleteAssetPR":
	            	jobj = DeleteAssetPR(id , id_req);	
	                break;
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	            case "CheckDate":
	            	jobj = CheckRequestedDate(dt_req);	
	                break;
	                
	            case "Save":
	            	//jobj = AddPurchaseRequest(map,no_req_val,id_emp_user);
	            	/*String colName="",no_req=no_req_val;
	        		int j=0,id_req1=0;
	        		value="";
	        		String col_Val="";
	        		JSONObject json=new JSONObject();

	        		try 
	        		{
	        			
	        			if(no_req.equals("new"))
	        			{
	        				rs = Common.GetColumns("P_REQUEST");
	        				
	        				
	        				while (rs.next())
	        				{
	        				
	        					if(rs.getString(1) !="id_req")
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
	        				String section = map.get("id_section");
	        				String sectionCode = map.get("ind_section_code");
	        				String indType = map.get("ind_type");
	        				
	        				 String reqNo = getReqNo(section,sectionCode,indType);
	        				 String sql="insert into P_Request("+colName+",add_by,no_req) values("+value+","+id_emp_user+",'"+reqNo+"')";
	        				stmt=con.createStatement();
	        				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
	        				 rs1 = stmt.getGeneratedKeys();
	        						 
	        		         while (rs1.next()) 
	        		         {
	        		        	 id_req1 = rs1.getInt(1);
	        		         }
	        		         
	        		         no_req=reqNo;
	        		            
	        		         
	        		            
	        			}
	        			else
	        			{
	        				String sql = "select id_req from P_Request where no_req='"+no_req+"'";
	        				ps=con.prepareStatement(sql);
	        				rs=ps.executeQuery();
	        				if(rs.next())
	        				{
	        					id_req1=rs.getInt(1);
	        				}
	        				
	        				
	        				
	        			}
	        		
	        		}
	        		catch(Exception e)
	        		{
	        			System.out.println("Error in P_Request_Asset"+e.toString());
	        		}
	        		try
	        		{
	        			colName="";value="";
	        			String id_grp="",id_sgrp="",id_prod="",model="",mfr="",ds_pro="";
	        			String count[] = request.getParameterValues("count");
	        			double grand_tot =0.0;
	        			stmt=con.createStatement();
	        			for(int i=0;i<count.length;i++)
	        			{					
	        				j=0;
	        				String nm_prod = request.getParameter("id_prod"+count[i]+"");
	        				if(!nm_prod.isEmpty())
	        				{
	        					id_grp= request.getParameter("id_grp"+count[i]+"");
	        					id_sgrp= request.getParameter("id_sgrp"+count[i]+"");
	        				
	        		String	 sql = "select * from M_Model where NM_MODEL='"+nm_prod+"'";
	        				ps=con.prepareStatement(sql);
	        				rs=ps.executeQuery();
	        				if(rs.next())
	        				{
	        						id_prod=rs.getString("id_model");
	        					ds_pro=rs.getString("nm_model");
	        				}
	        				else{
	        					
	        					stmt.executeUpdate("insert into M_Model (nm_model,id_s_assetdiv,id_assetdiv,cd_model) values('"+nm_prod+"','"+id_sgrp+"','"+id_grp+"','NA')");
	        					 sql = "select * from M_Model where NM_MODEL='"+nm_prod+"'";
	             				ps=con.prepareStatement(sql);
	             				rs=ps.executeQuery();
	             				if(rs.next())
	             				{
	             						id_prod=rs.getString("id_model");
	             					ds_pro=rs.getString("nm_model");
	             				}
	        				
	        				}
	        					
	        					
	        					
	        				String id_req_asst = request.getParameter("id_req_asst"+count[i]+"");
	        				
	        			
	        				
	        				String un_prc = request.getParameter("un_prc"+count[i]+"");
	                		String tot_prc = request.getParameter("tot_prc"+count[i]+"");
	                		String qty = request.getParameter("qty"+count[i]+"");
	                		String asset_consumable = request.getParameter("asset_consumable"+count[i]+"");
	                		String uom = request.getParameter("uom"+count[i]+"");
	                		String stock_qty = request.getParameter("stock_qty"+count[i]+"");
	                		
	                		grand_tot += Double.parseDouble(tot_prc);
	        // sql="insert into P_Request_Asset(id_req,un_prc,tot_prc,qty,uom,description,asset_consumable,id_grp,id_sgrp,id_prod,model,mfr) values('"+id_req1+"','"+un_prc+"','"+tot_prc+"','"+qty+"','"+uom+"','"+description+"','"+asset_consumable+"','"+id_grp+"','"+id_sgrp+"','"+id_prod+"','"+model+"','"+mfr+"')";
	 // System.out.println(sql);
	                		if(id_req_asst == null || id_req_asst.equals("")){
	                			
	                			System.out.println("insert into P_Request_Asset(id_req,un_prc,tot_prc,qty,model,id_grp,id_sgrp,id_prod) values('"+id_req1+"','"+un_prc+"','"+tot_prc+"','"+qty+"','"+ds_pro+"','"+id_grp+"','"+id_sgrp+"','"+id_prod+"'");
	  		              
	                			stmt.executeUpdate("insert into P_Request_Asset(id_req,un_prc,tot_prc,qty,model,id_grp,id_sgrp,id_prod) values('"+id_req1+"','"+un_prc+"','"+tot_prc+"','"+qty+"','"+ds_pro+"','"+id_grp+"','"+id_sgrp+"','"+id_prod+"')");
	                		}else{
	                			stmt.executeUpdate("update P_Request_Asset set un_prc='"+un_prc+"',tot_prc='"+tot_prc+"',qty='"+qty+"',model='"+ds_pro+"',id_prod='"+id_prod+"' where id_req_asst="+id_req_asst+"") ;
	                		
	                		j=1;
	                		}
	        				}
	        			}
	        			rs = Common.GetColumns("P_REQUEST");
        				
						while (rs.next())
						{
							if(rs.getString(1) !="id_req")
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
	        			String sql="update P_REQUEST set "+col_Val+",grand_tot="+grand_tot+" where id_req="+id_req1+"";
	        			con=Common.GetConnection(request);
    					PreparedStatement ps=con.prepareStatement(sql);
    					j=ps.executeUpdate();
	        			
	        			}
	        			catch(Exception e)
	        				{
	        					System.out.println("Some error in P_Request_Asset." +e);
	        				}
	        			
	        		jobj.put("data",id_req1);
	        		jobj.put("no_req",no_req);*/
	        		
	            	
	            	String colName="",no_req=no_req_val;
	        		int j=0,id_req1=0;
	        		value="";
	        		String col_Val="";
	        		JSONObject json=new JSONObject();

	        		try 
	        		{
	        			
	        			if(no_req.equals("new"))
	        			{
	        				rs = Common.GetColumns("P_REQUEST",  request);
	        				
	        				
	        				while (rs.next())
	        				{
	        				
	        					if(rs.getString(1) !="id_req")
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
	        				String section = map.get("id_section");
	        				String sectionCode = map.get("ind_section_code");
	        				String indType = map.get("ind_type");
	        				
	        				 String reqNo = getReqNo(section,sectionCode,indType);
	        				 String sql="insert into P_Request("+colName+",add_by,no_req) values("+value+","+id_emp_user+",'"+reqNo+"')";
	        				stmt=con.createStatement();
	        				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
	        				 rs1 = stmt.getGeneratedKeys();
	        						 
	        		         while (rs1.next()) 
	        		         {
	        		        	 id_req1 = rs1.getInt(1);
	        		         }
	        		         
	        		         no_req=reqNo;
	        		            
	        		         
	        		            
	        			}
	        			else
	        			{
	        				String sql = "select id_req from P_Request where no_req='"+no_req+"'";
	        				ps=con.prepareStatement(sql);
	        				rs=ps.executeQuery();
	        				if(rs.next())
	        				{
	        					id_req1=rs.getInt(1);
	        				}
	        				
	        				
	        				
	        			}
	        		
	        		}
	        		catch(Exception e)
	        		{
	        			System.out.println("Error in P_Request_Asset"+e.toString());
	        		}
	        		try
	        		{
	        			colName="";value="";
	        			String id_grp="",id_sgrp="",id_prod="",model="",mfr="",ds_pro="";
	        			String count[] = request.getParameterValues("count");
	        			double grand_tot =0.0;
	        			stmt=con.createStatement();
	        			for(int i=0;i<count.length;i++)
	        			{					
	        				j=0;
	        				String nm_prod = request.getParameter("id_prod"+count[i]+"");
	        				if(!nm_prod.isEmpty())
	        				{
	        					id_grp= request.getParameter("id_grp"+count[i]+"");
	        					id_sgrp= request.getParameter("id_sgrp"+count[i]+"");
	        				
	        		String	 sql = "select * from M_Model where NM_MODEL='"+nm_prod+"'";
	        				ps=con.prepareStatement(sql);
	        				rs=ps.executeQuery();
	        				if(rs.next())
	        				{
	        						id_prod=rs.getString("id_model");
	        					ds_pro=rs.getString("nm_model");
	        				}
	        				else{
	        					
	        					stmt.executeUpdate("insert into M_Model (nm_model,cd_model) values('"+nm_prod+"','"+nm_prod+"')");
	        					 sql = "select * from M_Model where NM_MODEL='"+nm_prod+"'";
	             				ps=con.prepareStatement(sql);
	             				rs=ps.executeQuery();
	             				if(rs.next())
	             				{
	             						id_prod=rs.getString("id_model");
	             					ds_pro=rs.getString("nm_model");
	             				}
	        				
	        				}
	        				
	        				String id_req_asst = request.getParameter("id_req_asst"+count[i]+"");
	        				
	        			
	        				
	        				String un_prc = request.getParameter("un_prc"+count[i]+"");
	                		String tot_prc = request.getParameter("tot_prc"+count[i]+"");
	                		String qty = request.getParameter("qty"+count[i]+"");
	                		String asset_consumable = request.getParameter("asset_consumable"+count[i]+"");
	                		String uom = request.getParameter("uom"+count[i]+"");
	                		String stock_qty = request.getParameter("stock_qty"+count[i]+"");
	                		
	                		grand_tot += Double.parseDouble(tot_prc);
	        // sql="insert into P_Request_Asset(id_req,un_prc,tot_prc,qty,uom,description,asset_consumable,id_grp,id_sgrp,id_prod,model,mfr) values('"+id_req1+"','"+un_prc+"','"+tot_prc+"','"+qty+"','"+uom+"','"+description+"','"+asset_consumable+"','"+id_grp+"','"+id_sgrp+"','"+id_prod+"','"+model+"','"+mfr+"')";
	 // System.out.println(sql);
	                		if(id_req_asst == null || id_req_asst.equals("")){
	                			
	                			System.out.println("insert into P_Request_Asset(id_req,un_prc,tot_prc,qty,model,id_prod) values('"+id_req1+"','"+un_prc+"','"+tot_prc+"','"+qty+"','"+ds_pro+"','"+id_prod+"'");
	  		              
	                			stmt.executeUpdate("insert into P_Request_Asset(id_req,un_prc,tot_prc,qty,model,id_prod) values('"+id_req1+"','"+un_prc+"','"+tot_prc+"','"+qty+"','"+ds_pro+"','"+id_prod+"')");
	                		}else{
	                			stmt.executeUpdate("update P_Request_Asset set un_prc='"+un_prc+"',tot_prc='"+tot_prc+"',qty='"+qty+"',model='"+ds_pro+"',id_prod='"+id_prod+"' where id_req_asst="+id_req_asst+"") ;
	                		
	                		j=1;
	                		}
	        				}
	        			}
	        			rs = Common.GetColumns("P_REQUEST",  request);
        				
						while (rs.next())
						{
							if(rs.getString(1) !="id_req")
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
	        			String sql="update P_REQUEST set "+col_Val+",grand_tot="+grand_tot+" where id_req="+id_req1+"";
	        			con=Common.GetConnection(request);
    					PreparedStatement ps=con.prepareStatement(sql);
    					j=ps.executeUpdate();
	        			
	        			}
	        			catch(Exception e)
	        				{
	        					System.out.println("Some error in P_Request_Asset." +e);
	        				}
	        			
	        		jobj.put("data",id_req1);
	        		jobj.put("no_req",no_req);
	                break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in P_Request_Asset.");
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
		JSONArray jarr1 = new JSONArray();
		String sql="";
		try
		{
			//sql ="select r.*,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,s.nm_section,to_sec.nm_section as to_section,emp_req.nm_emp||emp_req.cd_emp as nm_emp_req,r.no_req,r.dt_req, pc.nm_prod,pc.cd_prod,pc.uom,pc.description,ra.qty,ra.un_prc,ra.tot_prc,ra.stock_qty,r.grand_tot,remarks from P_Request r, P_Request_Asset ra,M_Prod_Cart pc,M_Section s,M_Section to_sec, M_Emp_User emp_req  where r.id_req=ra.id_req and pc.id_prod=ra.id_prod and s.id_section=r.id_section and to_sec.id_section=r.id_to_section and r.req_by=emp_req.id_emp_user and r.id_req="+id+"";
sql="select r.*,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,r.no_req,r.dt_req, pc.nm_model,pc.cd_model,nm_bu as nm_dept,emp_req.nm_emp, "+
    "   ra.qty,ra.un_prc,ra.tot_prc,r.grand_tot,remarks from P_Request r, P_Request_Asset ra,M_Model pc, m_bu md, "+
    " M_Emp_User emp_req  where r.id_req=ra.id_req and pc.id_model=ra.id_prod and r.id_bu=md.id_bu"+
    " and r.req_by=emp_req.id_emp_user and r.id_req="+id+" " ;
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
		    String sql1 = "select * from M_Company";	
						ps=con.prepareStatement(sql1);
						rs=ps.executeQuery();
						
						   ResultSetMetaData metaData1 = rs.getMetaData();
						    int columns1 = metaData1.getColumnCount();
						    while(rs.next())
						    {
						    	JSONObject jobj3 = new JSONObject();
						    	for(int i=1;i<=columns1;i++)
						    	{
						    		String name=metaData1.getColumnName(i);
						    		jobj3.put(name,rs.getString(name));
						    	}
						    	jarr1.put(jobj3);
						    }
						    jobj.put("company",jarr1);
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
	
		    jobj.put("todaydate",formatter.format(date));
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in P_Request_Asset."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject SendForApproval(String id,String mailcontant,HttpServletRequest request)
	{
		System.out.println(mailcontant);
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		
		String query="update P_REQUEST set send_for_apprvl='Yes' where id_req="+id+"";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				
				// Mail Trigger......
				String replyMailId="",name="",no_po="";
	        	String mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_REQUEST po where emp.id_emp_user=po.repo_mngr and id_req='"+id+"'";
	        	
	        	DtoCommon dtcm = new DtoCommon();
				rs = dtcm.GetResultSet(mailSql,  request);
				if(rs.next())
				{
					replyMailId = rs.getString(1);
					name = rs.getString(2);
					 
				}
				  mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_REQUEST po where emp.id_emp_user=po.req_by and id_req='"+id+"'";
	        	
				List<String> recipients = dtcm.ReturnListData(mailSql,  request);
				
				String link = dtcm.ReturnParticularMessage("link");
				String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
				String mailSubject = dtcm.ReturnParticularMessage("purchaseReqPO");
				
				String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
							  "Indent is pending for approval.<br><br><table style='margin-left: auto;\r\n" + 
							  "  margin-right: auto;'> "+mailcontant+"</table>"+
							  "<br><br><br>"+link+""+
							  "<p>"+footerMsg+"</p>";
			
				Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
				
				
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
		
		//String sql ="select * from M_Emp_User where id_emp_user = "+id+"";
		String sql ="select emp.*, ( SELECT repo_mngr FROM M_Emp_User where id_emp_user = emp.repo_mngr ) as secnd_app from M_Emp_User emp where id_emp_user = "+id+"";
		
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
	public JSONObject UpdatePurchaseRequest(HashMap<String, String> map, String id, String id_req_asst,int 
			id_emp_user, String no_req,HttpServletRequest request)
	{
		String col_Val="",col_Val1="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("P_REQUEST",  request);
				
						while (rs.next())
						{
							if(rs.getString(1) !="id_req")
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
						
						rs = Common.GetColumns("P_REQUEST_ASSET",  request);
						
						while (rs.next())
						{
							if(rs.getString(1) !="id_req_asst" || rs.getString(1) !="id_req")
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
					System.out.println("Some error in P_REQUEST_ASSET.");
				}
		try 
		{
		String query="";
		
		 query="update P_REQUEST set "+col_Val+" where id_req="+id+"";
		
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				query="update P_REQUEST_ASSET set "+col_Val1+" where id_req_asst="+id_req_asst+"";
			 
				PreparedStatement ps2=con.prepareStatement(query);
				j=ps2.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
			}
		
			json.put("data",id);
			json.put("no_req",no_req);
			
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
		
		String query = "select id_req from P_Request where "+ColName+" = '"+value+"'";
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
	
	
	public JSONObject DeleteAssetPR(String id ,String id_req)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			if(!id_req.equals("0"))
			{
					String query = "select COUNT(id_req) from P_Request_Asset where id_req = "+id_req+" ";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						int rowCount = rs.getInt(1);
						if(rowCount > 1)
						{
							query = "delete P_Request_Asset where id_req_asst = "+id+"";
							ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								query = "select sum(tot_prc) from P_Request_Asset where id_req = "+id_req+" ";
								ps=con.prepareStatement(query);
								rs=ps.executeQuery();
								if(rs.next())
								{
									 query="update P_Request set grand_tot ='"+rs.getString(1)+"' where id_req = "+id_req+"";
										
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
				
				String query = "select id_req from P_Request_Asset where id_req_asst = "+id+"";
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_req = rs.getString(1);
				}
				
				 query = "delete P_Request_Asset where id_req_asst = "+id+"";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					query = "delete P_Request where id_req = "+id_req+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						query = "select sum(tot_prc) from P_Request_Asset where id_req = "+id_req+" ";
						ps=con.prepareStatement(query);
						rs=ps.executeQuery();
						if(rs.next())
						{
							 query="update P_Request set grand_tot ='"+rs.getString(1)+"' where id_req = "+id_req+"";
								
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
		String query = "select COUNT(id_req_asst) from P_Request_Asset where id_req = "+id+"";
		ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		
				if(rs.next())
				{
					int a= rs.getInt(1);
						if(a > 1)
						{
							query = "delete P_Request_Asset where id_req = "+id+"";
							
							PreparedStatement ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								query = "delete P_Request where id_req = "+id+"";
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
			String query = "delete P_Request_Asset where id_req = "+id+"";
			
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				query = "delete P_Request where id_req = "+id+"";
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
		String	sql ="select ra.*,ad.NM_ASSETDIV,sd.NM_S_ASSETDIV,nm_prod as nm_prod1 from P_Request_Asset ra,M_asset_div ad,m_subasset_div sd,M_PROD_CART pc where ra.id_grp=ad.ID_ASSETDIV and ra.ID_SGRP=sd.ID_S_ASSETDIV and ra.ID_PROD=pc.ID_PROD and id_req_asst="+id+"";
		
		
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
			System.out.println("sql error in P_Request_Asset.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DisplayAssetForPR(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		String	sql ="select grand_tot,ra.*,nm_model,cd_model from P_Request_Asset ra,m_model pc,P_Request r where ra.id_prod=pc.id_model and r.id_req=ra.id_req and ra.id_req="+id+"";
		
		
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
			System.out.println("sql error in P_Request_Asset.");
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
			 tempSql =" and ( r.dt_req LIKE '%"+word+"%' or r.no_req LIKE '%"+word+"%' or emp.nm_emp LIKE '%"+word+"%')";
		
				if(id.equals("0"))
				{
					if(User_Type.equals("SUPER"))
					sql ="select r.*,nm_dept,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,nm_emp,r.id_req,r.ID_DEPT,ID_REQ  from P_request r,M_Emp_User emp  "+
		               "  ,m_dept md where st_approv ='waiting' and r.req_by=emp.id_emp_user and md.id_dept=r.id_dept   " +tempSql+ "  Order by r.dt_req DESC  ";
                	else
                		sql ="select r.*,nm_dept,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,nm_emp,r.id_req,r.ID_DEPT,ID_REQ  from P_request r,M_Emp_User emp  "+
         		               "  ,m_dept md where st_approv ='waiting' and r.req_by=emp.id_emp_user and md.id_dept=r.id_dept   " +tempSql+ "  Order by r.dt_req DESC  ";
                 
					
					sql ="select r.*,nm_bu as nm_dept,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,nm_emp,r.id_req,r.ID_DEPT,ID_REQ  from P_request r,M_Emp_User emp  "+
      		               "  ,m_bu md where st_approv ='waiting' and r.req_by=emp.id_emp_user and md.id_bu=r.id_bu   " +tempSql+ "  Order by r.dt_req DESC  ";
                  
		}
		else
		{
			if(User_Type.equals("SUPER"))
				sql ="select grand_tot,NO_REQ,id_to_section,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as dt_req,nm_emp,nm_cc,r.ID_REQ,r.NO_REQ,r.REQ_BY,r.ID_DEPT,r.REMARKS,r.ID_CC,r.FIRST_LA,r.SECOND_LA,r.THIRD_LA,r.FOURTH_LA,r.ID_SECTION,r.REPO_MNGR  from P_request r,M_Emp_User emp,M_Company_Costcenter cc "+
					" where st_approv ='waiting' and r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc and send_for_apprvl='No' and id_req="+id+"";
			else
				sql ="select grand_tot,NO_REQ,id_to_section,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as dt_req,nm_emp,nm_cc,r.ID_REQ,r.NO_REQ,r.REQ_BY,r.ID_DEPT,r.REMARKS,r.ID_CC,r.FIRST_LA,r.SECOND_LA,r.THIRD_LA,r.FOURTH_LA,r.ID_SECTION,r.REPO_MNGR from P_request r,M_Emp_User emp,M_Company_Costcenter cc "+
						" where st_approv ='waiting' and r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc and send_for_apprvl='No' and  add_by="+id_emp_user+" and id_req="+id+"";
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
			System.out.println("sql error in P_Request_Asset."+e.toString());
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
		// tempSql =" and (REGEXP_LIKE(frmSec.nm_section,'"+word+"','i') or REGEXP_LIKE(toSec.nm_section,'"+word+"','i') or REGEXP_LIKE(to_char (to_date(r.dt_req,'YYYY-MM-DD'), 'DD/MM/YYYY'),'"+word+"','i') or REGEXP_LIKE(r.no_req,'"+word+"','i') or REGEXP_LIKE(emp.nm_emp,'"+word+"','i'))";
		
		 tempSql =" and ( r.dt_req LIKE '%"+word+"%' or r.no_req LIKE '%"+word+"%' or emp.nm_emp LIKE '%"+word+"%')";
		
		if(id.equals("0"))
		{
			if(User_Type.equals("SUPER"))
			sql ="select r.*,nm_bu as nm_dept,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,nm_emp,r.id_req,r.ID_DEPT,ID_REQ  from P_request r,M_Emp_User emp  "+
               "  ,m_bu md where st_approv ='waiting' and r.req_by=emp.id_emp_user and md.id_bu=r.id_bu and send_for_apprvl='No'  " +tempSql+ "  Order by r.dt_req DESC  ";

			else
				sql ="select r.*,nm_bu as nm_dept,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,nm_emp,r.id_req,r.ID_DEPT,ID_REQ  from P_request r,M_Emp_User emp  "+
			               "  , ,m_bu md where st_approv ='waiting' and r.req_by=emp.id_emp_user and md.id_bu=r.id_bu and send_for_apprvl='No'  " +tempSql+ "  Order by r.dt_req DESC  ";

			
			sql ="select r.*,nm_bu as nm_dept,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,nm_emp,r.id_req,r.ID_DEPT,ID_REQ  from P_request r,M_Emp_User emp  "+
		               "  ,m_bu md where st_approv ='waiting' and r.req_by=emp.id_emp_user and md.id_bu=r.id_bu and send_for_apprvl='No'  " +tempSql+ "  Order by r.dt_req DESC  ";

				
		}
		else
		{
			if(User_Type.equals("SUPER"))
				sql ="select r.*,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as dtreq,nm_emp,nm_bu  from P_request r,M_Emp_User emp,M_BU cc  where st_approv ='waiting' and r.req_by=emp.id_emp_user  "+
                     " and r.id_bu=cc.id_bu and send_for_apprvl='No' and id_req="+id+"";
			else
				sql ="select r.*,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as dtreq,nm_emp,nm_bu  from P_request r,M_Emp_User emp,m_dept cc  where st_approv ='waiting' and r.req_by=emp.id_emp_user  "+
	                     " and r.id_bu=cc.id_bu and send_for_apprvl='No' and id_req="+id+"";
			
			sql ="select r.*,grand_tot,NO_REQ,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as dtreq,nm_emp,nm_bu  from P_request r,M_Emp_User emp,m_bu cc  where st_approv ='waiting' and r.req_by=emp.id_emp_user  "+
	                " and r.id_bu=cc.id_bu and send_for_apprvl='No' and id_req="+id+"";
		
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
			System.out.println("sql error in P_Request_Asset."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject AddPurchaseRequest(HashMap<String, String> map,String no_req , int id_emp_user,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0,id_req=0;
		JSONObject json=new JSONObject();

		try 
		{
			
			if(no_req.equals("new"))
			{
				rs = Common.GetColumns("P_REQUEST",  request);
				
				
				while (rs.next())
				{
				
					if(rs.getString(1) !="id_req")
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
				 String sql="insert into P_Request("+colName+",add_by,no_req,CREATED_BY) values("+value+","+id_emp_user+",'"+reqNo+"',"+id_emp_user+")";
				 /*stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs1 = stmt.getGeneratedKeys();*/
				 PreparedStatement stmt_pre = con.prepareStatement(sql, new String[] { "id_req" });
				 stmt_pre.executeUpdate();
				 rs1 = stmt_pre.getGeneratedKeys();
				 
		         while (rs1.next()) 
		         {
		            	id_req = rs1.getInt(1);
		         }
		         
		         no_req=reqNo;
		            
		       // Mail Trigger......
		            
	            	String replyMailId="",name="";
	            	String mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_request r where emp.id_emp_user=r.firstLA and no_req='"+no_req+"'";
	            	
	            	DtoCommon dtcm = new DtoCommon();
	    			rs = dtcm.GetResultSet(mailSql,  request);
	    			if(rs.next())
	    			{
	    				replyMailId = rs.getString(1);
	    				name = rs.getString(2);
	    			}
	    			mailSql ="select id_emp,nm_emp from M_Emp_User emp,P_request r where emp.id_emp_user=r.req_by and no_req='"+no_req+"'";
	    			
	    			List<String> recipients = dtcm.ReturnListData(mailSql,  request);
	    			
	    			String link = dtcm.ReturnParticularMessage("link");
	    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	    			String mailSubject = dtcm.ReturnParticularMessage("purchaseReq");
	    			
	    			String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
	    						  "Purchase request <b>("+no_req+")</b> has been created. Please approve it.<br>"+
	    						  "<br><br><br>"+link+""+
								  "<p>"+footerMsg+"</p>";
	    		
	    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	    	        j=1;    
		            
			}
			else
			{
				String sql = "select id_req from P_Request where no_req='"+no_req+"'";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_req=rs.getInt(1);
				}
				
			}
		
		}
		catch(Exception e)
		{
			System.out.println("Error in P_Request_Asset"+e.toString());
		}
		try
		{
			colName="";value="";
				rs = Common.GetColumns("P_REQUEST_ASSET",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_req_asst")
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
					System.out.println("Some error in P_Request_Asset.");
				}
		
		String query="insert into P_Request_Asset("+colName+",id_req) values("+value+","+id_req+")";
		System.out.println(query);
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
			}
			
			
			json.put("data",id_req);
			json.put("no_req",no_req);
			
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
		rs=stmt.executeQuery("select count(no_req) from P_request");
		
		
		if(rs.next())
		{
			norq=rs.getInt(1)+1;
			
			strReqNo="REQ/"+strfinyear+"/"+norq;
			
			/*
			rs1=stmt1.executeQuery("select * from P_request where substring(no_req,len(no_req)-8,len(no_req))='"+strfinyear+"'");
			rs1=stmt1.executeQuery("select no_req from P_request where SUBSTR(no_req,LENGTH(no_req)-7,4)='"+year+"'");
			if(rs1.next())
			{
				rs2=stmt2.executeQuery("select max(id_req) from P_request where SUBSTR(no_req,LENGTH(no_req)-7,4)='"+year+"'");
				if(rs2.next())
				{
					intPoId=rs2.getInt(1);
					rs3=stmt3.executeQuery("select no_req from P_request r where id_req="+intPoId+" ");
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
			
			rs1=stmt1.executeQuery("select no_req from P_request where SUBSTR(no_req,1,7)='"+yearSec+"'");
			if(rs1.next())
			{
				rs2=stmt2.executeQuery("select max(id_req) from P_request where  SUBSTR(no_req,1,7)='"+yearSec+"'");
				if(rs2.next())
				{
					intPoId=rs2.getInt(1);
					rs3=stmt3.executeQuery("select SUBSTR(no_req,9,4) from P_request r where id_req="+intPoId+" ");
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
