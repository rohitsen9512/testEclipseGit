package com.auction;

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


public class Auction extends HttpServlet {
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
		String action = "",word="",id="0",req_ast_id="",id_req="",confirm="1",dt_to="",dt_frm="",value="",ColName="",no_req_val="",dt_req="";
		
		
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
	            case "auto_jwno":
	            	jobj = Autoworkno();	
	            	
	                break;
	            case "DisplayRequestForPreview":
	            	jobj = DisplayRequestForPreview(id);	
	                break;
	                
	            case "EditLineItem":
	            	jobj = EditLineItem(id);	
	                break;
	            
	            case "Delete":
	            	jobj = DeletePurchaseRequest(id,confirm);	
	                break;
	            
	                
	            case "Save":
	            	String colName="",no_req=no_req_val;
	            	no_req=request.getParameter("no_req");
	        		int j=0,id_req1=0;
	        		value="";
	        		String col_Val="";
	        		JSONObject json=new JSONObject();

	        		try 
	        		{
	        			
	        			if(no_req.equals("new"))
	        			{
	        				rs = Common.GetColumns("B_Auction",  request);
	        				
	        				
	        				while (rs.next())
	        				{
	        				
	        					if(rs.getString(1) !="id_auc")
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
	        				 String sql="insert into B_Auction("+colName+",add_by) values("+value+","+id_emp_user+")";
	        				 System.out.println(sql);
	        				stmt=con.createStatement();
	        				 stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
	        				 rs1 = stmt.getGeneratedKeys();
	        						 
	        		         while (rs1.next()) 
	        		         {
	        		        	 id_req1 = rs1.getInt(1);
	        		         }
	        		         
	        		            
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
	        			System.out.println("Error in B_Auction"+e.toString());
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
	        					
	        				String id_auc_asset = request.getParameter("id_req_asst"+count[i]+"");
	        				
	        			
	        				
	        				String un_prc = request.getParameter("un_prc"+count[i]+"");
	                		String tot_prc = request.getParameter("tot_prc"+count[i]+"");
	                		String qty = request.getParameter("qty"+count[i]+"");
	                		
	                		grand_tot += Double.parseDouble(tot_prc);
	     
	                		if(id_auc_asset == null || id_auc_asset.equals("")){
	                			
	                			System.out.println("insert into B_Auction_Asset(id_auc,un_prc,tot_prc,qty,id_grp,id_sgrp,id_prod) values('"+id_req1+"','"+un_prc+"','"+tot_prc+"','"+qty+"','"+id_grp+"','"+id_sgrp+"','"+id_prod+"'");
	  		              
	                			stmt.executeUpdate("insert into B_Auction_Asset(id_auc,un_prc,tot_prc,qty,id_grp,id_sgrp,id_prod) values('"+id_req1+"','"+un_prc+"','"+tot_prc+"','"+qty+"','"+id_grp+"','"+id_sgrp+"','"+id_prod+"')");
	                		}else{
	                			stmt.executeUpdate("update B_Auction_Asset set un_prc='"+un_prc+"',tot_prc='"+tot_prc+"',qty='"+qty+"',model='"+ds_pro+"',id_prod='"+id_prod+"' where id_auc_asset="+id_auc_asset+"") ;
	                		
	                		j=1;
	                		}
	        				}
	        			}
	        			rs = Common.GetColumns("B_Auction",  request);
        				
						while (rs.next())
						{
							if(rs.getString(1) !="id_auc")
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
	        			String sql="update B_Auction set "+col_Val+",grand_tot="+grand_tot+" where id_auc="+id_req1+"";
	        			con=Common.GetConnection(request);
    					PreparedStatement ps=con.prepareStatement(sql);
    					j=ps.executeUpdate();
	        			
	        			}
	        			catch(Exception e)
	        				{
	        					System.out.println("Some error in B_Auction." +e);
	        				}
	        			
	        		jobj.put("data",id_req1);
	        		
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
		ps=con.prepareStatement("select count(id_auc) from B_Auction  ");
		rs=ps.executeQuery();
		
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				
			}
			
		String 	poID3 = String.format("%03d", PoId);
			poID2= "AUC"+poID3+"/"+strt+"-"+endt;
			  System.out.println(poID2);
			  jobj.put("wo_no",poID2);
			
		}
		
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return jobj;
	}
	public JSONObject DisplayRequestForPreview(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
			//sql ="select r.*,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,s.nm_section,to_sec.nm_section as to_section,emp_req.nm_emp||emp_req.cd_emp as nm_emp_req,r.no_req,r.dt_req, pc.nm_prod,pc.cd_prod,pc.uom,pc.description,ra.qty,ra.un_prc,ra.tot_prc,ra.stock_qty,r.grand_tot,remarks from P_Request r, P_Request_Asset ra,M_Prod_Cart pc,M_Section s,M_Section to_sec, M_Emp_User emp_req  where r.id_req=ra.id_req and pc.id_prod=ra.id_prod and s.id_section=r.id_section and to_sec.id_section=r.id_to_section and r.req_by=emp_req.id_emp_user and r.id_req="+id+"";
sql="select r.*,(replace(convert(NVARCHAR, r.dt_req, 103), ' ', '-')) as reqDT,r.no_req,r.dt_req, pc.nm_model,pc.cd_model,nm_dept, "+
    "   ra.qty,ra.un_prc,ra.tot_prc,r.grand_tot,remarks from P_Request r, P_Request_Asset ra,M_Model pc, M_Dept md, "+
    " M_Emp_User emp_req  where r.id_req=ra.id_req and pc.id_model=ra.id_prod and r.id_dept=md.id_dept"+
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
	public JSONObject UpdatePurchaseRequest(HashMap<String, String> map, String id, String id_req_asst,int id_emp_user, String no_req,HttpServletRequest request)
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
			sql ="select (replace(convert(NVARCHAR, dt_auc, 103), ' ', '-')) as dt_auc,grand_tot,auc_name,id_auc  from b_auction  where st_auc_create='0'    ";

			else
				sql ="select (replace(convert(NVARCHAR, dt_auc, 103), ' ', '-')) as dt_auc,grand_tot,auc_name,id_auc  from b_auction  where  st_auc_create='0'  ";

		}
		else
		{
			if(User_Type.equals("SUPER"))
				sql ="select *   from b_auction   "+
                     "  and id_auc="+id+"";
			else
				sql ="select *   from b_auction   "+
	                     "  and id_auc="+id+"";
			
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
	
	
	
}
