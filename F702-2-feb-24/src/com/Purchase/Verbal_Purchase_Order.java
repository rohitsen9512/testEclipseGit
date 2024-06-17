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


public class Verbal_Purchase_Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	PreparedStatement ps2=null;
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
		String action = "",word="",id="0",req_ast_id="",id_po="",confirm="1",dt_to="",dt_frm="",value="",ColName="",no_req_val="",dt_req="";
		
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
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
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
			int id_log_user = (int)session.getAttribute("UserId");
			
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
	            case "DisplayDirect":
	            	jobj = DisplayPurchaseRequestdirect(id,dt_frm,dt_to,id_emp_user,User_Type,word);	
	                break;
	            case "Dropwornprocess":
	            	jobj = Dropwornprocess(id);	
	            	
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
	            	jobj = SendForApproval(id);	
	                break;
	            case "Delete":
	            	jobj = DeletePurchaseRequest(id,confirm);	
	                break;
	            case "DeleteAssetPR":
	            	jobj = DeleteAssetPR(id , id_po);	
	                break;
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	            case "CheckDate":
	            	jobj = CheckRequestedDate(dt_req);	
	                break;
	                
	            case "CreatePOIdDynamic":
	            	int id_fincance1=0;
	            	stmt = con.createStatement();
					ps=con.prepareStatement("select id_fincance from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
					rs=ps.executeQuery();
					if(rs.next())
					{
						id_fincance1=rs.getInt(1);
					}
					
					
	            	String PONum = CreatePOIdDynamic(id_fincance1);
	            	jobj.put("PONum",PONum);
	            	
	                break; 
	                
	            case "CreatePOIdDynamicForJW":
	            	int id_fincance2=0;
	            	stmt = con.createStatement();
					ps=con.prepareStatement("select id_fincance from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
					rs=ps.executeQuery();
					if(rs.next())
					{
						id_fincance1=rs.getInt(1);
					}
				
	            	 PONum = CreatePOIdDynamic1(id_fincance2);
	            	jobj.put("PONum",PONum);
	            	
	                break; 
	                
	            case "Save":
	            	//jobj = AddPurchaseRequest(map,no_req_val,id_emp_user);
	            	String colName="",no_req=no_req_val,no_po="";
	        		int j=0,id_req1=0;
	        		value="";
	        		String exchange="";
		        	String col_Val="";
	        		JSONObject json=new JSONObject();
	        		String id_design="",id_cc="",id_section="";
	        		int id_po1=0;
	        		try 
	        		{
	        			
						String gr_tot =request.getParameter("gr_tot");

                    	Double tt =0.0;
	        			
		            	String sql="select A.id_design from M_emp_user A, M_Designation B where A.id_design=B.id_design" + 
		            			"";
		            	ps=con.prepareStatement(sql);
						rs=ps.executeQuery();
						if(rs.next())
						{
							id_design =rs.getString(1);
							
						}
						
	        				rs = Common.GetColumns("P_PURCHASE_ORDER",  request);
	        				
	        				
	        				while (rs.next())
	        				{
	        				
	        					if(rs.getString(1) !="id_po")
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
	        				//String section = map.get("id_section");
	        				int id_fincance=0;
	    					ps=con.prepareStatement("select id_fincance from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
	    					rs=ps.executeQuery();
	    					if(rs.next())
	    					{
	    						id_fincance=rs.getInt(1);
	    					}
	    					String POID="";
	    					String cc=map.get("typ_of_po");
	    					String cc1=map.get("indent_dept");
	    					 exchange = map.get("exchange_rate");
	    					 System.out.println(exchange);
		        		
	    					 
	    						 POID = CreatePOIdDynamic(id_fincance);
	    			      	System.out.println(POID);
	        				no_po = POID;
	        				System.out.println(no_po);
	        				
	        				
	        				  sql="insert into P_PURCHASE_ORDER("+colName+",id_design,rem_val_po,do_direct,no_po) values("+value+","+id_design+",'"+tt+"','1','"+no_po+"')";
	        				  System.out.println(sql);
		        				
	        				stmt=con.createStatement();
	        				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
	        				 rs1 = stmt.getGeneratedKeys();
	        				  System.out.println(sql);
	        					 
	        		         while (rs1.next()) 
	        		         {
	        		        	 id_po1 = rs1.getInt(1);
	        		         }
	        		         
	        			
	        		
	        		}
	        		catch(Exception e)
	        		{
	        			System.out.println("Error in P_Purchase_Order_Asset"+e.toString());
	        		}
	        		try
	        		{
	        			colName="";value="";
	        			String id_prod="",ds_pro="";
	        			String ds_assetdiv="",mfr_assetdiv="";
	        			String count[] = request.getParameterValues("count");
	        			double grand_tot =0.0;
	        			stmt=con.createStatement();
	        			
	        			j=0;
	        			for(int i=0;i<count.length;i++)
	        			{					
	        				
	        				String nm_prod = request.getParameter("id_prod"+count[i]+"");
		        				
	        				if(!nm_prod.isEmpty())
	        				{

	        					String	 sql = "select * from M_Asset_Div where nm_assetdiv='"+ nm_prod+"'";
		        				ps=con.prepareStatement(sql);
		        				rs=ps.executeQuery();
		        				if(rs.next())
		        				{
									id_prod = rs.getString("id_assetdiv");
									ds_pro = rs.getString("nm_assetdiv");
			                        ds_assetdiv = rs.getString("ds_assetdiv");
			                        mfr_assetdiv = rs.getString("mfr_assetdiv");
		        				}
		        				else{
		        					
								    nm_prod = request.getParameter("id_prod" + count[i] + "");
									String mfr = request.getParameter("mfr" + count[i] + "");
									String ds_product = request.getParameter("ds_product" + count[i] + "");
									sql = "select * from M_Asset_Div where nm_assetdiv='" + nm_prod + "'";
									ps = con.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										id_prod = rs.getString("id_assetdiv");
										ds_pro = rs.getString("nm_assetdiv");
				                        ds_assetdiv = rs.getString("ds_assetdiv");
				                        mfr_assetdiv = rs.getString("mfr_assetdiv");
									}
								} 
	        				
	        				String qty = request.getParameter("qty"+count[i]+"");
	                		String un_prc = request.getParameter("un_prc"+count[i]+"");
	                		String others = request.getParameter("others"+count[i]+"");
	                		String id_tax1 = request.getParameter("id_tax1"+count[i]+"");
	                		String id_tax2 = request.getParameter("id_tax2"+count[i]+"");
	                		String tax_val1 = request.getParameter("tax_val1"+count[i]+"");
	                		String tax_val2 = request.getParameter("tax_val2"+count[i]+"");
	                		String buyback = request.getParameter("buyback"+count[i]+"");
	                		String gr_tot = request.getParameter("gr_tot"+count[i]+"");
	                		String dt_scheduled_dlvry = request.getParameter("dt_scheduled_dlvry"+count[i]+"");
	                		String item_remarks=request.getParameter("item_remarks"+count[i]+"");
	                		
	                	if(dt_scheduled_dlvry.equals("") ){
	    	        			dt_scheduled_dlvry="";
		            			 System.out.println("insert into P_Purchase_Order_Asset(item_remarks,id_po,id_prod,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"',"+id_po1+","+id_prod+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+")");
				                	
			                		stmt.executeUpdate("insert into P_Purchase_Order_Asset(item_remarks,id_po,id_prod,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot) values('"+item_remarks+"',"+id_po1+","+id_prod+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+")");
			                		j=1;
	    	        		 }
	    	        		else{
	    	        			dt_scheduled_dlvry = request.getParameter("dt_scheduled_dlvry"+i+"");
		            			 Date d = userDateFormat.parse(dt_scheduled_dlvry); 
		            			 System.out.println("pritesh"+dateFormatNeeded.format(d));
		            			 dt_scheduled_dlvry=dateFormatNeeded.format(d);
		            			 System.out.println("insert into P_Purchase_Order_Asset(item_remarks,id_po,id_prod,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,dt_scheduled_dlvry) values('"+item_remarks+"',"+id_po1+","+id_prod+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+",'"+dateFormatNeeded.format(d)+"')");
				                	
			                		stmt.executeUpdate("insert into P_Purchase_Order_Asset(item_remarks,id_po,id_prod,qty,rem_qty,un_prc,others,id_tax1,id_tax2,tax_val1,tax_val2,buyback,gr_tot,dt_scheduled_dlvry) values('"+item_remarks+"',"+id_po1+","+id_prod+","+qty+","+qty+","+un_prc+","+others+","+id_tax1+","+id_tax2+","+tax_val1+","+tax_val2+","+buyback+","+gr_tot+",'"+dateFormatNeeded.format(d)+"')");
			                		j=1;
	    	        			
	    	        		}
	  		
	        							}
	        		}
	        				
	        			}
	        			catch(Exception e)
	        				{
	        					System.out.println(e);
	        				}
	        			
	        		jobj.put("data",no_po);
	        		
	                break;
	                
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in P_Purchase_Order_Asset.");
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

	public String CreatePOIdDynamic(int id_fincance)
	{
		String finyear="",poID2="";
		int PoId=1;
		
		try
		{
			String year="",end="";
			ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			rs=ps.executeQuery();
		if(rs.next())
		{
			year=rs.getString("start");
			end=rs.getString("enddt");
		}
        
		
		String strt = year.substring (2,4);
		String endt = end.substring (2,4);
	
		ps=con.prepareStatement("select count(id_po) from P_Purchase_Order where SUBSTRING(no_po,1,8)='PO/"+strt+"-"+endt+"'  "  );
		rs=ps.executeQuery();
		
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				//PoId = PoId + 18000000;
				String PoDynId="",secCode="";
				
				String 	poID3 = String.format("%03d", PoId);
				
				poID2="PO/"+strt+"-"+endt+"/"+poID3;
			
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return poID2;
	}
	
	public String CreatePOIdDynamic1(int id_fincance)
	{
		String finyear="",poID2="";
		int PoId=1;
		
		try
		{
			String year="",end="";
			ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			rs=ps.executeQuery();
		if(rs.next())
		{
			year=rs.getString("start");
			end=rs.getString("enddt");
		}
		 
		
		String strt = year.substring (2,4);
		String endt = end.substring (2,4);
		System.out.println("select count(id_po) from P_Purchase_Order where id_fincance="+id_fincance+" and do_direct='0'");
		
		/*ps=con.prepareStatement("select count(id_po) from P_Purchase_Order where id_fincance="+id_fincance+" and do_direct='1' ");*/
		ps=con.prepareStatement("select count(id_po) from P_Purchase_Order ");
		rs=ps.executeQuery();
		
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				//PoId = PoId + 18000000;
				String PoDynId="",secCode="";
				String 	poID3 = String.format("%03d", PoId);
			
				poID2="ESS/JWPO/"+poID3+"/"+strt+"-"+endt+"";
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return poID2;
	}
	
	public JSONObject DisplayRequestForPreview(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
			sql ="select s.nm_section,r.no_req,r.dt_req, pc.nm_prod,pc.cd_prod,pc.uom,pc.description,ra.qty,ra.un_prc,ra.tot_prc from P_PURCHASE_ORDER r, P_Purchase_Order_Asset ra,M_Prod_Cart pc,M_Section s  where r.id_po=ra.id_po and pc.id_prod=ra.id_prod and s.id_section=r.id_section and r.id_po="+id+"";

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
			System.out.println("sql error in P_Purchase_Order_Asset."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject SendForApproval(String id)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		
		String query="update P_PURCHASE_ORDER set send_for_apprvl='Yes' where id_po="+id+"";
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
	public JSONObject UpdatePurchaseRequest(HashMap<String, String> map, String id, String id_req_asst,
			int id_emp_user, String no_req,HttpServletRequest request)
	{
		String col_Val="",col_Val1="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("P_PURCHASE_ORDER",  request);
				
						while (rs.next())
						{
							if(rs.getString(1) !="id_po")
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
							if(rs.getString(1) !="id_req_asst" || rs.getString(1) !="id_po")
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
		
		 query="update P_PURCHASE_ORDER set "+col_Val+" where id_po="+id+"";
		
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
		
		String query = "select id_po from P_PURCHASE_ORDER where "+ColName+" = '"+value+"'";
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
	
	
	public JSONObject DeleteAssetPR(String id ,String id_po)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
			if(!id_po.equals("0"))
			{
					String query = "select COUNT(id_po) from P_Purchase_Order_Asset where id_po = "+id_po+" ";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						int rowCount = rs.getInt(1);
						if(rowCount > 1)
						{
							query = "delete P_Purchase_Order_Asset where id_req_asst = "+id+"";
							ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								query = "select sum(tot_prc) from P_Purchase_Order_Asset where id_po = "+id_po+" ";
								ps=con.prepareStatement(query);
								rs=ps.executeQuery();
								if(rs.next())
								{
									 query="update P_PURCHASE_ORDER set grand_tot ='"+rs.getString(1)+"' where id_po = "+id_po+"";
										
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
				
				String query = "select id_po from P_Purchase_Order_Asset where id_req_asst = "+id+"";
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_po = rs.getString(1);
				}
				
				 query = "delete P_Purchase_Order_Asset where id_req_asst = "+id+"";
				ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					query = "delete P_PURCHASE_ORDER where id_po = "+id_po+"";
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					if(j > 0)
					{
						query = "select sum(tot_prc) from P_Purchase_Order_Asset where id_po = "+id_po+" ";
						ps=con.prepareStatement(query);
						rs=ps.executeQuery();
						if(rs.next())
						{
							 query="update P_PURCHASE_ORDER set grand_tot ='"+rs.getString(1)+"' where id_po = "+id_po+"";
								
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
		String query = "select COUNT(id_req_asst) from P_Purchase_Order_Asset where id_po = "+id+"";
		ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		
				if(rs.next())
				{
					int a= rs.getInt(1);
						if(a > 1)
						{
							query = "delete P_Purchase_Order_Asset where id_po = "+id+"";
							
							PreparedStatement ps=con.prepareStatement(query);
							j=ps.executeUpdate();
							if(j > 0)
							{
								query = "delete P_PURCHASE_ORDER where id_po = "+id+"";
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
			String query = "delete P_Purchase_Order_Asset where id_po = "+id+"";
			
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				query = "delete P_PURCHASE_ORDER where id_po = "+id+"";
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
		String	sql ="select ra.*,ad.NM_ASSETDIV,sd.NM_S_ASSETDIV,nm_prod as nm_prod1 from P_Purchase_Order_Asset ra,M_asset_div ad,m_subasset_div sd,M_PROD_CART pc where ra.id_grp=ad.ID_ASSETDIV and ra.ID_SGRP=sd.ID_S_ASSETDIV and ra.ID_PROD=pc.ID_PROD and id_req_asst="+id+"";
		
		
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
			System.out.println("sql error in P_Purchase_Order_Asset.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DisplayAssetForPR(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		String	sql ="select grand_tot,ra.*,nm_prod,cd_prod from P_Purchase_Order_Asset ra,M_Prod_Cart pc,P_PURCHASE_ORDER r where ra.id_prod=pc.id_prod and r.id_po=ra.id_po and ra.id_po="+id+"";
		
		
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
			System.out.println("sql error in P_Purchase_Order_Asset.");
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
		 tempSql =" and (REGEXP_LIKE(frmSec.nm_section,'"+word+"','i') or REGEXP_LIKE(toSec.nm_section,'"+word+"','i') or REGEXP_LIKE(r.dt_req,'"+word+"','i') or REGEXP_LIKE(r.no_req,'"+word+"','i') or REGEXP_LIKE(emp.nm_emp,'"+word+"','i'))";
		
		//String tempSql =" and (frmSec.nm_section LIKE '%"+word+"%' or toSec.nm_section LIKE '%"+word+"%' or r.dt_req LIKE '%"+word+"%' or r.no_req LIKE '%"+word+"%' or emp.nm_emp LIKE '%"+word+"%')";
		
		if(id.equals("0"))
		{
			if(User_Type.equals("SUPER"))
			sql ="select r.*,grand_tot,frmSec.nm_section as frm_sec,toSec.nm_section as to_sec ,NO_REQ,id_to_section,to_char (to_date(r.dt_req,'YYYY-MM-DD'), 'DD/MM/YYYY') as dt_req,nm_emp,nm_cc,r.id_po,r.ID_DEPT,r.ID_CC,ID_REQ  from P_request r,M_Emp_User emp,M_Company_Costcenter cc , M_Section frmSec, M_Section toSec "+
					" where st_approv ='waiting' and r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc  and frmSec.id_section=r.id_section and toSec.id_section=r.id_to_section" +tempSql;
			else
				sql ="select r.*,grand_tot,frmSec.nm_section as frm_sec,toSec.nm_section as to_sec ,id_to_section,to_char (to_date(r.dt_req,'YYYY-MM-DD'), 'DD/MM/YYYY') as reqDT,nm_emp,nm_cc from P_request r,M_Emp_User emp,M_Company_Costcenter cc , M_Section frmSec, M_Section toSec "+
						" where st_approv ='waiting' and r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc  and frmSec.id_section=r.id_section and toSec.id_section=r.id_to_section and add_by="+id_emp_user+"" +tempSql;

		}
		else
		{
			if(User_Type.equals("SUPER"))
				sql ="select grand_tot,NO_REQ,id_to_section,to_char (to_date(r.dt_req,'YYYY-MM-DD'), 'DD/MM/YYYY') as dt_req,nm_emp,nm_cc,r.ID_REQ,r.NO_REQ,r.REQ_BY,r.ID_DEPT,r.REMARKS,r.ID_CC,r.FIRST_LA,r.SECOND_LA,r.THIRD_LA,r.FOURTH_LA,r.ID_SECTION,r.REPO_MNGR  from P_request r,M_Emp_User emp,M_Company_Costcenter cc "+
					" where st_approv ='waiting' and r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc and send_for_apprvl='No' and id_po="+id+"";
			else
				sql ="select grand_tot,NO_REQ,id_to_section,to_char (to_date(r.dt_req,'YYYY-MM-DD'), 'DD/MM/YYYY') as dt_req,nm_emp,nm_cc,r.ID_REQ,r.NO_REQ,r.REQ_BY,r.ID_DEPT,r.REMARKS,r.ID_CC,r.FIRST_LA,r.SECOND_LA,r.THIRD_LA,r.FOURTH_LA,r.ID_SECTION,r.REPO_MNGR from P_request r,M_Emp_User emp,M_Company_Costcenter cc "+
						" where st_approv ='waiting' and r.req_by=emp.id_emp_user and r.id_cc=cc.id_cc and send_for_apprvl='No' and  add_by="+id_emp_user+" and id_po="+id+"";
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
			System.out.println("sql error in P_Purchase_Order_Asset."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayPurchaseRequest(String id,String dt_frm,String dt_to,int id_emp_user,String User_Type,String word)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		Common dbname=new Common();
		
		
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
					 tempSql =" and (no_po LIKE '%"+word+"%' or dt_po LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' )";
					
					if(id.equals("0"))
					{
						
						sql="select no_po,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtpo,nm_ven,typ_of_po,id_po from P_Purchase_Order p ,S_Store_Qty qt,m_vendor v where p.id_ven=v.id_ven and qt.id_sloc=p.id_sloc and TYP_OF_PO='JobWork' and st_po=0 and do_direct='1' "+tempSql+"";
						
					}
					else
					{
						sql ="select po.*, (replace(convert(NVARCHAR, dt_val_po, 103), ' ', '-')) as dtValPo, (replace(convert(NVARCHAR, dt_sd_amt, 103), ' ', '-')) as dtSdAmt, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-'))  as dtPo, (replace(convert(NVARCHAR, po.dt_quot, 103), ' ', '-'))  as dtQuot from P_Purchase_Order po,S_Store_Qty qt where do_direct=1 and qt.id_sloc=p.id_sloc and st_po=0 and id_po="+id+"  order by  dtValPo desc";
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
			System.out.println("sql error in P_Purchase_Order_Asset."+e.toString());
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject AddPurchaseRequest(HashMap<String, String> map,String no_req , int id_emp_user,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0,id_po=0;
		JSONObject json=new JSONObject();

		try 
		{
			
			if(no_req.equals("new"))
			{
				rs = Common.GetColumns("P_PURCHASE_ORDER",  request);
				
				
				while (rs.next())
				{
				
					if(rs.getString(1) !="id_po")
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
				 String reqNo = getReqNo(id_section);
				 String sql="insert into P_PURCHASE_ORDER("+colName+",add_by,no_req,CREATED_BY) values("+value+","+id_emp_user+",'"+reqNo+"',"+id_emp_user+")";
				 /*stmt=con.createStatement();
				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				 rs1 = stmt.getGeneratedKeys();*/
				 PreparedStatement stmt_pre = con.prepareStatement(sql, new String[] { "id_po" });
				 stmt_pre.executeUpdate();
				 rs1 = stmt_pre.getGeneratedKeys();
				 
		         while (rs1.next()) 
		         {
		            	id_po = rs1.getInt(1);
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
				String sql = "select id_po from P_PURCHASE_ORDER where no_req='"+no_req+"'";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_po=rs.getInt(1);
				}
				
			}
		
		}
		catch(Exception e)
		{
			System.out.println("Error in P_Purchase_Order_Asset"+e.toString());
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
					System.out.println("Some error in P_Purchase_Order_Asset.");
				}
		
		String query="insert into P_Purchase_Order_Asset("+colName+",id_po) values("+value+","+id_po+")";
		System.out.println(query);
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=1;
			}
			
			
			json.put("data",id_po);
			json.put("no_req",no_req);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	
	
public String getReqNo(String id_section) throws SQLException
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
		/*rs=stmt.executeQuery("select year(std_finance) as stdate,year(end_finance) as endyear from M_Finance_Year where active_year='1'");*/
		rs=stmt.executeQuery("select extract(year from to_date(STD_FINANCE,'yyyy/mm/dd')) as stdate,extract(year from to_date(END_FINANCE,'yyyy/mm/dd')) as endyear from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
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
		rs=stmt.executeQuery("select UPPER(SUBSTR(nm_section,1,3)) as section  from M_Section where id_section="+id_section+" ");
		if(rs.next())
		{
			section=rs.getString("section");
			
		}
		
		
		if(!boolStatus)
		{
		rs=stmt.executeQuery("select * from P_request");
		if(rs.next())
		{
			/*rs1=stmt1.executeQuery("select * from P_request where substring(no_req,len(no_req)-8,len(no_req))='"+strfinyear+"'");*/
			rs1=stmt1.executeQuery("select no_req from P_request where SUBSTR(no_req,LENGTH(no_req)-7,4)='"+year+"'");
			if(rs1.next())
			{
				rs2=stmt2.executeQuery("select max(id_po) from P_request where SUBSTR(no_req,LENGTH(no_req)-7,4)='"+year+"'");
				if(rs2.next())
				{
					intPoId=rs2.getInt(1);
					rs3=stmt3.executeQuery("select no_req from P_request r where id_po="+intPoId+" ");
					if(rs3.next())
					{
						strTemp=rs3.getString(1);
						strTemp=strTemp.substring(strTemp.indexOf("q-")+2,strTemp.indexOf("/") );
						int intTempNo = Integer.parseInt(strTemp);
						intTempNo = intTempNo + 1;
						strReqNo="Req-"+intTempNo+"/"+year+"/"+section;
					}
				}
			}
			else
			{
				strReqNo="Req-1/"+year+"/"+section;
			}
		}
		else
		{
			strReqNo="Req-1/"+year+"/"+section;
		}
	}
	return strReqNo ;
	}	
public JSONObject DisplayPurchaseRequestdirect(String id,String dt_frm,String dt_to,int id_emp_user,String User_Type,String word)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	String sql="";
	Common dbname=new Common();
	
	
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
		 tempSql =" and (dt_po LIKE '%"+word+"%' or no_po LIKE '%"+word+"%' or nm_ven LIKE '%"+word+"%' )";
			
			
			if(id.equals("0"))
			{
				
			sql="select id_design,no_po,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtpo,id_po from P_Purchase_Order p where  do_direct='1'";

					
			
			}
			else
			{
					sql ="select po.*, (replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo  from P_Purchase_Order po where do_direct=1 and id_po="+id+"  order by  dtValPo desc";
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
		System.out.println("sql error in P_Purchase_Order_Asset."+e.toString());
	}
	 
	return jobj;
	
	
}
public String CreatePOIdDynamicdirect(String typ_of_po, String fromSection,int id_fincance)
{
	String finyear="",poID2="";
	int PoId=1;
	
	try
	{
		System.out.println(typ_of_po);
			/*if(typ_of_po.equals("OL") || typ_of_po.equals("OM") || typ_of_po.equals("OH")){
			
			ps2=con.prepareStatement("select count(id_po) from P_Purchase_Order where typ_of_po!='O2'and typ_of_po!='O3' and typ_of_po!='OTH' and typ_of_po!='O1' and id_fincance="+id_fincance+" " );
			System.out.println("select count(id_po) from P_Purchase_Order where typ_of_po!='O2'and typ_of_po!='O3' and typ_of_po!='OTH' and typ_of_po!='O1' and id_fincance="+id_fincance+" " );
		}
		else{
			ps2=con.prepareStatement("select count(id_po) from P_Purchase_Order where typ_of_po='"+typ_of_po+"' and id_fincance="+id_fincance+" " );
			System.out.println("select count(id_po) from P_Purchase_Order where typ_of_po='"+typ_of_po+"' and id_fincance="+id_fincance+"");
				
		}
	*/	ps2=con.prepareStatement("select count(id_po) from P_Purchase_Order where id_fincance="+id_fincance+" " );
//	ps=con.prepareStatement("select count(id_po) from P_Purchase_Order where typ_of_po='"+typ_of_po+"' and id_fincance="+id_fincance+" " );
	rs=ps2.executeQuery();
	
		if(rs.next())
		{
			PoId =	rs.getInt(1) +1;
			//PoId = PoId + 18000000;
			String PoDynId="",secCode="";
			String year="";
		/*	rs=stmt.executeQuery("select UPPER(SUBSTR(nm_section,1,3)) as section,cd_section  from M_Section where id_section="+fromSection+" ");
			System.out.println("select UPPER(SUBSTR(nm_section,1,3)) as section,cd_section  from M_Section where id_section="+fromSection+" ");
			if(rs.next())
			{
				secCode=rs.getString("cd_section");
				
			}
			
			ps2=con.prepareStatement("select extract(year from to_date(STD_FINANCE,'yyyy/mm/dd')) as stdate,extract(year from to_date(END_FINANCE,'yyyy/mm/dd')) as endyear from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			rs=ps2.executeQuery();
			if(rs.next())
			{
				year=rs.getString("stdate");
			}
			//year = yearTemp + "000000";
			StringBuilder yearchar = new StringBuilder(year);
			yearchar.deleteCharAt(4-4);
			yearchar.deleteCharAt(3-3);
			
			PoDynId= String.format("%04d", PoId);
			
			//PoId = PoId + Integer.parseInt(year);
			
			poID2="SAL/"+yearchar+"00"+PoDynId+"/"+ typ_of_po;
			ps=con.prepareStatement("select substring(convert(varchar(10),std_finance,101),7,10),substring(convert(varchar(10),end_finance,101),9,10) from M_Finance_Year where active_year=1 ");
			ps=con.prepareStatement("select EXTRACT(YEAR FROM to_date(std_finance,'yyyy-mm-dd')),EXTRACT(YEAR FROM to_date(end_finance,'yyyy-mm-dd')) from M_Finance_Year where ACTIVE_YEAR=1");
			rs1=ps.executeQuery();
			if(rs1.next())
			{
				finyear =rs1.getString(1)+"-"+rs1.getString(2);
			}
			
			if(PoId == 1)
			{
				poID2="SAL/"+typ_of_po+"/18000001";
			}
			else
			{
				poID2="SAL/"+typ_of_po+"/"+ PoId;
			}*/
			poID2="PO-NO/"+ PoId;
		}
		
		
	}
	catch(Exception e)
	{
		System.out.println("Eroor ...  "+e.toString());
	}
	return poID2;
}

public JSONObject Dropwornprocess(String id_proc)
{
	Common dbname=new Common();

	
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	String sql="";
	String tempSql ="";
String id_proc1="";
	try{
		 
		
		System.out.println("select id_prod from m_prod_cart  where nm_prod='"+id_proc+"'");
	ps=con.prepareStatement("select id_prod from m_prod_cart  where nm_prod='"+id_proc+"'");
	rs=ps.executeQuery();
	if(rs.next()){
		id_proc1=rs.getString(1);
	}
	else{
		System.out.println("select id_prod from m_prod_cart  where cd_prod='"+id_proc+"'");
		ps=con.prepareStatement("select id_prod from m_prod_cart  where cd_prod='"+id_proc+"'");
		rs=ps.executeQuery();
		if(rs.next()){
			id_proc1=rs.getString(1);
		}
		
	}
	

		sql="select mm.id_route_card_material,nm_process from M_Route_Card mr ,M_Route_Card_Material mm where mr.id_route_card=mm.id_route_card and id_prod='"+id_proc1+"'";
	
	
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
		System.out.println(e);
	}
	 
	return jobj;
	
	
}


}
