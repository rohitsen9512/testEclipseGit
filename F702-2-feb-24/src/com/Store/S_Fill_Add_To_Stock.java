package com.Store;

import java.io.IOException;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.DtoCommon;

/**
 * Servlet implementation class S_Fill_Add_To_Stock
 */
public class S_Fill_Add_To_Stock extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	Connection con = null;
	Statement stmt = null;
	
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		String UserName = (String)session.getAttribute("UserName");
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		Enumeration elemet = request.getParameterNames();
		HashMap<String, String> map =new HashMap<String,String>();
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
	 	JSONObject json=new JSONObject();
		String paramName = "";
		String paramValues = "";
		 try
		 {
		        
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      String[] parts = paramName.split("_");
		      paramValues = request.getParameter(paramName);
		      if(paramName.equals("approve_dt"))
		      {
		    	  paramValues = request.getParameter(paramName);
		    	  System.out.println(paramValues);
		    	  Date d = userDateFormat.parse(paramValues);  
			      map.put(paramName,dateFormatNeeded.format(d));
		      }
		      else if(parts[0].equals("dt") && !paramValues.equals(""))
		      {
		    	  paramValues = request.getParameter(paramName);
		    	  System.out.println(paramValues);
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
		String action = "", temp1 = "",dt_snd_service="", id = "0", word = "", id_loc = "", id_sloc = "", DisplayType = "",
				value = "", ColName = "", id_lead_m = "00";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		if (request.getParameter("searchWord") != null) {
			word = request.getParameter("searchWord");
		}
		if (request.getParameter("id") != null) {
			id = request.getParameter("id");
		}
		if (request.getParameter("id_lead_m") != null) {
			id_lead_m = request.getParameter("id_lead_m");
		}
		if (request.getParameter("id_sloc") != null) {
			id_sloc = request.getParameter("id_sloc");
		}
		if (request.getParameter("id_loc") != null) {
			id_loc = request.getParameter("id_loc");
		}
		if(request.getParameter("DisplayType") !=null)
		{
			DisplayType = request.getParameter("DisplayType");
		}
		if (request.getParameter("value") != null) {
			value = request.getParameter("value");
		}
		if (request.getParameter("ColName") != null) {
			ColName = request.getParameter("ColName");
		}
		
			String chk[] = request.getParameterValues("addstockrefill");
		
			System.out.println(chk);
			//System.out.println("e"+request.getParameterValues("productcheck"));
			try
			{	
			String tempSql = "";
			
			

			String tempQuery = "";
			int id_emp_user = (int) session.getAttribute("id_emp_user");
			
			String UserType = (String) session.getAttribute("UserType");
			
			
			con = Common.GetConnection(request);
			switch (action) {
	         case "Search":
	            	String tempSub="";
	            	
	            DtoCommon dtoCommon = new DtoCommon();
	            try {	
				
				if(DisplayType.equals("Instore_Empty"))
     			{
					if(!UserType.equals("Super"))
                	{
            			//String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
            	         tempSql="select distinct top 100 (id_wh_dyn),id_iwh,ds_pro,id_product,no_mfr,nm_product,wh.serial_no,remarks from S_I_Ware_House wh ,M_Asset_Div modl where  wh.id_product=modl.id_assetdiv and  wh.id_loc='"+id_loc+"'and  wh.id_subl='"+id_sloc+"' and device_status='In_store' and wh.filling_status=0 and wh.typeprod='Type-Cylinder'and(wh.ds_pro LIKE '%"+word+"%' or wh.mfr LIKE '%"+word+"%' or " 
            	         		+"wh.serial_no LIKE '%"+word+"%' or wh.nm_product LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%' )";
                    	
            		
                	}
            		else {
           	         tempSql="select distinct top 100 (id_wh_dyn),id_iwh,ds_pro,id_product,no_mfr,nm_product,wh.serial_no,remarks from S_I_Ware_House wh ,M_Asset_Div modl where  wh.id_product=modl.id_assetdiv and  wh.id_loc='"+id_loc+"'and  wh.id_subl='"+id_sloc+"' and device_status='In_store' and  wh.filling_status=0 and wh.typeprod='Type-Cylinder' and(wh.ds_pro LIKE '%"+word+"%' or wh.mfr LIKE '%"+word+"%' or " 
         	         		+"wh.serial_no LIKE '%"+word+"%' or wh.nm_product LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%' )";
            		
     			}
					}
     			else {
     				if(!UserType.equals("Super"))
                	{
            			//String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
            	         tempSql="select distinct top 100 (id_wh_dyn),id_iwh,ds_pro,id_product,no_mfr,nm_product,wh.serial_no,remarks from S_I_Ware_House wh ,M_Asset_Div modl where  wh.id_product=modl.id_assetdiv and  wh.id_loc='"+id_loc+"'and  wh.id_subl='"+id_sloc+"' and device_status='In_store' and wh.filling_status=0 and wh.typeprod='Type-Cylinder'and(wh.ds_pro LIKE '%"+word+"%' or wh.mfr LIKE '%"+word+"%' or " 
            	         		+"wh.serial_no LIKE '%"+word+"%' or wh.nm_product LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%' )";
                    	
            		
                	}
            		else {
           	         tempSql="select distinct top 100 (id_wh_dyn),id_iwh,ds_pro,id_product,no_mfr,nm_product,wh.serial_no,remarks from S_I_Ware_House wh ,M_Asset_Div modl where  wh.id_product=modl.id_assetdiv and  wh.id_loc='"+id_loc+"'and  wh.id_subl='"+id_sloc+"' and device_status='In_store' and wh.filling_status=0 and wh.typeprod='Type-Cylinder' and(wh.ds_pro LIKE '%"+word+"%' or wh.mfr LIKE '%"+word+"%' or " 
         	         		+"wh.serial_no LIKE '%"+word+"%' or wh.nm_product LIKE '%"+word+"%' or wh.id_wh_dyn LIKE '%"+word+"%' )";
            		
     			}
     			}
				
     				//jobj = uad.QueryMacker1(tempSql , UserType , word ,id_dept,  request);
     				System.out.println(tempSql);
     				ps = con.prepareStatement(tempSql);
     				rs = ps.executeQuery();
     				ResultSetMetaData metaData = rs.getMetaData();
     				int columns = metaData.getColumnCount();
     				while (rs.next()) {
     					JSONObject jobj2 = new JSONObject();
     					for (int i = 1; i <= columns; i++) {
     						String name = metaData.getColumnName(i);
     						jobj2.put(name, rs.getString(name));
     					}
     					jarr.put(jobj2);
     				}
     				jobj.put("data", jarr);
     			} catch (Exception e) {
     				System.out.println(e+"sql error in S_Fill_Add_To_Stock.");
     			}
     			System.out.println(jobj+"hr");
     		
     			break; 	
			
		          case "sendforserviceDate":
		            	jobj = CheckDate(dt_snd_service,id);	
		                break;
		          case "RestoretostockRefill": 
						
		        		try
						{

									
		        			int j = 0;		
		        		String colName="",sql1="";
		        			List<String> recipients = new ArrayList<String>();
		        			
		        			stmt = con.createStatement();
		        			rs = Common.GetColumns("S_Refill_history",  request);
		        			
		    				while (rs.next())
		    				{
		    				
		    					if(rs.getString(1) !="id_refill_hist")
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
		    		
					
		    				
		    				int serialNo    =   0;		
		            	for(int i=0; i<chk.length;i++)
		            	{
		            		System.out.println("E"+chk[i]);
		            		
		            		
		            		String rmk =request.getParameter("rmk"+chk[i]);
		            		String id_prod =request.getParameter("id_prod"+chk[i]);
		            		String id_iwh =request.getParameter("id_iwh"+chk[i]);
		            		String  nm_prod =request.getParameter("nm_prod"+chk[i]);
		            		System.out.println("e"+id_prod);
		            		String dtfill = request.getParameter("dt_fill"+chk[i]);
		            		dtfill = dateFormatNeeded.format(userDateFormat.parse(dtfill));
		            		
		            		sql1="insert into S_Refill_history("+colName+", rmk,dt_fill,id_prod,id_iwh,nm_prod,serial_no) values("+value+" , '"+rmk+"','"+dtfill+"','"+id_prod+"','"+id_iwh+"','"+nm_prod+"','"+chk[i]+"')";
		            		//sql1="update S_Refill_history  set "+col_Val1+",rmk='"+rmk+"',dt_fill='"+dtfill+"' , id_prod='"+id_prod+"'  where serial_no='"+chk[i]+"'";
		            		 System.out.println(sql1);
		            		//sql1="insert into  J_Servicing_history (rmk,id_prod,dt_restore,serial_no) values("+value+",'"+rmk+"','"+restoredt+"','"+chk1[i]+"')";
	    	                System.out.println(sql1);
	    	            	PreparedStatement ps=con.prepareStatement(sql1);
	    	    			j=ps.executeUpdate();
	    	    			if(j>0)
	    	    			{
	    	    				j=0;
	    	    				sql1=" update S_I_Ware_House  set  filling_status = '1' where serial_no = '"+chk[i]+"'";
	    	    			
	    	    				ps=con.prepareStatement(sql1);
	    		    			j=ps.executeUpdate();  
	    		    			System.out.println(sql1);
	    		    			  if(j>0) {
	        						    j=0;
	        								sql1 = "select tot_aval_qty ,tot_aval_empty  from S_Store_Qty  where id_sloc="+id_sloc+" and id_loc="+id_loc+"  and id_product="+id_prod+"";
	        								System.out.println("select tot_aval_qty , tot_aval_empty from S_Store_Qty  where id_sloc="+id_sloc+" and id_loc="+id_loc+"   and id_product="+id_prod+"");
	        								ps = con.prepareStatement(sql1);
	        								rs = ps.executeQuery();
	        								if (rs.next()) {
	        									System.out.println("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"+1) ,tot_aval_empty =("+rs.getInt(2)+"-1) where id_sloc="+id_sloc+" and id_loc="+id_loc+"   and id_product="+id_prod+"" );
	        									
	        									stmt.executeUpdate("update  S_Store_Qty set tot_aval_qty=("+rs.getInt(1)+"+1) , tot_aval_empty =("+rs.getInt(2)+"-1) where id_sloc="+id_sloc+" and id_loc="+id_loc+"  and id_product="+id_prod+"" );
	        									j=1;
	        									 
	      							}
	        		    				
	        						}
	    	    			}
		            	
		   
		            	}//loop
				    			
		       
		                
		            
						jobj.put("data",j);
						}
		        		
		        		catch(Exception e)
						{
							System.out.println("Error "+e);
						}
		        	
		        		
						break;
		                
     		
			}//
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
		} catch (Exception e) {
			System.out.println(e+"Error in A_Invoice.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

public JSONObject CheckDate(String dt_snd_service , String id_iwh)
		{
			JSONObject jobj = new JSONObject();
			
			String  sql ="select (replace(convert(NVARCHAR, sh.dt_snd_service, 103), ' ', '-')) as dtsend from J_Servicing_history sh where id_iwh = '"+id_iwh+"' and dt_snd_service > '"+dt_snd_service+"' ";
			System.out.println(sql);	
				 
			try
			{
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			    if(rs.next())
			    {
			    	jobj.put("data", 0);
			    	jobj.put("dt_snd_service", rs.getString(1));
		        }
			    else
			    {
			    	jobj.put("data", 1);
			    	
			    }
			    
			}
			catch(Exception e)
			{
				System.out.println(e+"sql error in j_servicing");
			}
			 
			return jobj;
			
			
		}

}
