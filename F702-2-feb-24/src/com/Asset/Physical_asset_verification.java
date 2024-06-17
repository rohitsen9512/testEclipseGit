package com.Asset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


public class Physical_asset_verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	Date currDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		HashMap<String, String> map =new HashMap<String,String>();
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  

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
		    
		String id="",action="";
		String id_grp="",id_sgrp="",id_loc="",id_subl="",typ_asst="",searchWord="";
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("action") !=null)
		{
			action = request.getParameter("action");
		}
		if(request.getParameter("id_grp") !=null)
		{
			id_grp = request.getParameter("id_grp");
		}
		if(request.getParameter("id_sgrp") !=null)
		{
			id_sgrp = request.getParameter("id_sgrp");
		}
		if(request.getParameter("id_loc") !=null)
		{
			id_loc = request.getParameter("id_loc");
		}
		if(request.getParameter("id_subl") !=null)
		{
			id_subl = request.getParameter("id_subl");
		}
		if(request.getParameter("typ_asst") !=null)
		{
			typ_asst = request.getParameter("typ_asst");
		}	
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}	
		String temp="";
		if(!id_grp.equals(""))
		{
			if(!id_sgrp.equals(""))
			{
				temp += " and wh.id_grp = "+id_grp+" and wh.id_sgrp = "+id_sgrp+"";
			}
			else
			{
				temp += " and wh.id_grp = "+id_grp+"";
			}
			
		}
		if(!id_loc.equals(""))
		{
			if(!id_subl.equals(""))
			{
				temp += " and wh.id_loc = "+id_loc+" and wh.id_subl = "+id_subl+"";
			}
			else
			{
				temp += " and wh.id_loc = "+id_loc+"";
			}
		}
		if(!typ_asst.equals(""))
		{
			temp += " and wh.typ_asst = '"+typ_asst+"'";
		}
			
		int id_emp_user = (int)session.getAttribute("id_emp_user");
		String UserType = (String)session.getAttribute("UserType"); 
		
		String FlrId = (String)session.getAttribute("FlrId");
		int DivId = (int)session.getAttribute("DivId");
		if(!UserType.equals("SUPER")){
			String Query = Common.returnQuery(FlrId);
			temp = temp + " "+Query+" and wh.id_div ="+DivId+"";
		}
		  
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        
	            case "Display":
	            	jobj = DisplayAssetFromStoreForEdit(temp , searchWord);	
	                break;
	                
	            case "Edit":
	            	jobj = DataForEditFromStore(id);	
	                break;
	            case "MobilePhysicalVarification":
	            	
	            	
					
	               
	                String id_flr_mob = request.getParameter("id_flr");
	                String id_building = request.getParameter("id_building");
	                String id_loc_mob = request.getParameter("id_loc");
	                String id_sloc_mob = request.getParameter("id_subl");
	                String id_wh_dyn_mob=request.getParameter("id_wh_dyn");
	                String dt_phy_vrf=request.getParameter("dt_phy_vrf");
	                
	                String arrasset[] =id_wh_dyn_mob.split(",");
	                String str = "";
	                
	                if(!dt_phy_vrf.equals(""))
        			{
                		dt_phy_vrf = dateFormatNeeded.format(userDateFormat.parse(dt_phy_vrf));
        			}
            	   
	                ServletContext servletContext3 = getServletConfig().getServletContext();
				    String relativeWebPath3 = "ScanFile/Physical-Verification-"+dt_phy_vrf+".txt";
					String strdirName3 = servletContext3.getRealPath(relativeWebPath3);
					 Path path  = Paths.get(strdirName3);
						
	                	
	               try {
	            	   
	            	   int j=0;
	            	   String yearmob="",monthmob="";
	            	   String query="",id_wh_mob="";
	            	   for(int i=0;i<arrasset.length;i++) {
		                	str=str+arrasset[i]+"\n";
		                	
		                	
		                
		            		
		            		
		            	 
		            		 yearmob = dt_phy_vrf.split("-")[0];
		            		 monthmob =  dt_phy_vrf.split("-")[1];
		            		
		            		String yearAndMonth= yearmob+"-"+monthmob;
		            		
		            		String sql="select id_ph_vrfn_history from Physical_Verification_History where convert(varchar(7), getdate(), 126)='"+yearAndMonth+"'  and id_wh_dyn='"+arrasset[i]+"'";
		            		PreparedStatement ps=con.prepareStatement(sql);
			    			rs=ps.executeQuery();
			    			if(rs.next())
			    			{
			    				 query =" update Physical_Verification_History  set verified_by= "+id_emp_user+", dt_verified = '"+dt_phy_vrf+"',verified_status='Yes' where id_ph_vrfn_history = '"+rs.getString(1)+"'";
			    		    			
			    			
			    			}
			    			else
			    			{
			    				ps=con.prepareStatement("select id_wh from A_Ware_House where id_wh_dyn='"+arrasset[i]+"'");
				    			rs1=ps.executeQuery();
				    			if(rs1.next())
				    			{
				    				
				    				id_wh_mob=rs1.getString(1);
				    			}
				    			
 
			    				query = "insert into Physical_Verification_History (id_wh_dyn,id_wh,verified_by,dt_verified,id_flr,verified_status,id_loc,id_subl,id_building) "
			    						+ "values('"+arrasset[i]+"',"+id_wh_mob+",'"+id_emp_user+"','"+dt_phy_vrf+"',"+id_flr_mob+",'Yes',"+id_loc_mob+","+id_sloc_mob+","+id_building+") ";
			    				System.out.println(query);
			    				ps=con.prepareStatement(query);
			    				j=ps.executeUpdate();
			    			}
			    			
			    			query = " update A_Ware_House  set physical_verification_date ='"+dt_phy_vrf+"',physical_verification_status='Yes' where id_wh_dyn='"+arrasset[i]+"'";
		    				
			    			
			    			ps=con.prepareStatement(query);
		    				j=ps.executeUpdate();
			    			
				    			
				     			
			    				j=1;
			    			}
		            	jobj.put("data",j);
		                	
		                	
		                
	            	   byte[] arr = str.getBytes();      
	               
	                   Files.write(path, arr);
	                   
	                   
	                   
	               }
	      
	               catch (IOException ex) {
	                   
	                   System.out.print("Invalid Path"+ex);
	               }
	                break;
	                
	            case "Update":
	            	//jobj = UpdateDataForEditFromStore(map , id,id_emp_user);	
	            	
	            	String chk[] = request.getParameterValues("PhysicalVerification");
	            	int j=0,year,month;
	            	
	            	String verified_status="",dt_verified="",id_flr="",query="",id_wh_dyn="",id_wh="";
	            	for(int i=0; i<chk.length;i++)
	            	{
	            		
	            		j=0;
	            		id=chk[i];
	            		verified_status =request.getParameter("verified_status"+chk[i]);
	            		id_flr =request.getParameter("id_flr"+chk[i]);
	            		id_wh_dyn =request.getParameter("id_wh_dyn"+chk[i]);
	            		id_wh =request.getParameter("id_wh"+chk[i]);
	            		verified_status =request.getParameter("verified_status"+chk[i]);
	            		
	            		dt_verified =request.getParameter("verified_status"+chk[i]);
	            		
	            		if(!dt_verified.equals(""))
	        			{
	            			dt_verified = dateFormatNeeded.format(userDateFormat.parse(dt_verified));
	        			}
	            		
	            		@SuppressWarnings("deprecation")
						Date date= new Date(dt_verified); // your date
	            		Calendar cal = Calendar.getInstance();
	            		cal.setTime(date);
	            		 year = cal.get(Calendar.YEAR);
	            		 month = cal.get(Calendar.MONTH);
	            		
	            		String yearAndMonth= year+"-"+month;
	            		
	            		String sql="select id_ph_vrfn_history from Physical_Verification_History where convert(varchar(7), getdate(), 126)='"+yearAndMonth+"'  and id_wh="+chk[i]+"";
	            		PreparedStatement ps=con.prepareStatement(sql);
		    			rs=ps.executeQuery();
		    			if(rs.next())
		    			{
		    				 query =" update Physical_Verification_History  set verified_by= "+id_emp_user+", dt_verified = '"+dt_verified+"',verified_status='"+verified_status+"' where id_ph_vrfn_history = "+rs.getString(1)+"";
		    		    			
		    			}
		    			else
		    			{
		    				query = "insert into Physical_Verification_History (id_wh_dyn,id_wh,id_wh,verified_by,dt_verified,id_flr,verified_status) values("+id_wh_dyn+","+id_wh+",'"+id_emp_user+"','"+dt_verified+"',"+id_flr+",'"+verified_status+"') ";
		    				 ps=con.prepareStatement(query);
		    				j=ps.executeUpdate();
		    			}
		    			
			    			
			     			
		    				j=1;
		    			}
	            	jobj.put("data",j);
	            	
	                break;
	            
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in physical verification."+e);
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
	
	
	public JSONObject UpdateDataForEditFromStore(HashMap<String, String> map , String id,int id_emp_user,HttpServletRequest request)
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
					System.out.println("Some error in M_Asset_Div.");
				}
		
		String query="update A_Ware_House set "+col_Val+",edit_by="+id_emp_user+" where id_wh="+id+"";
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
	
	
	public JSONObject DataForEditFromStore(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			sql="select * from A_Ware_House  where id_wh = "+id+" ";
		
		
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
			System.out.println("sql error in A_Install.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayAssetFromStoreForEdit(String temp , String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		//String sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where allocate = 0 and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven";
		String sql="";
		if(!temp.equals(""))
		{
			 sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model,wh.id_flr from A_Ware_House wh , A_Invoice_master inv , M_Vendor v ,M_Model m where (allocate = 0 or allocate = 2) and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and wh.serial_no like '"+searchWord+"%' "+temp+"";
				
		}
		else
		 sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc,serial_no,nm_model,wh.id_flr from A_Ware_House wh , A_Invoice_master inv , M_Vendor v ,M_Model m where (allocate = 0 or allocate = 2) and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and m.id_model=wh.id_model and st_trvl=0 and wh.serial_no like '"+searchWord+"%'";
		
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
			System.out.println("sql error in A_Install.");
		}
		 
		return jobj;
	}
	
}