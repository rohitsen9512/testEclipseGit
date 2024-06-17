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
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


public class Create_Indent extends HttpServlet {
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
		    
		String action = "",id="0",id_req="",id_req_asst="",confirm="1",dt_to="",no_ind="",dt_frm="",value="",ColName="",no_req_val="";
		if(request.getParameter("id_req_asst") !=null)
		{
			id_req_asst = request.getParameter("id_req_asst");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("no_ind") !=null)
		{
			no_ind = request.getParameter("no_ind");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("no_req_val") !=null)
		{
			no_req_val = request.getParameter("no_req_val");
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
		try
		{
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
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        case "Save":
	            	jobj = SaveIndent(map,id_emp_user,no_ind,id_req,id_req_asst,  request);	
	                break;  
		        case "CreateIndent":
	            	jobj = CreateInden(id_req,id_emp_user);	
	                break;
		        case "CheckBudget":
            		 id_req= request.getParameter("ids");
            		jobj = CheckBudget(id_req);	
	                break;
	            case "Display":
	            	jobj = DisplayPurchaseRequestForCreateIndent(id,dt_frm,dt_to,id_emp_user);	
	                break;
	            
	           
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Create_Indent.");
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
	
	
	
	
	
	
	public JSONObject SaveIndent(HashMap<String, String> map , int id_emp_user,String no_ind,String id_req,String id_req_asst,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		String budget_type =map.get("budget_type");
		String[] parts = id_req.split("Patel");
		String[] reqAsstArr = id_req_asst.split("Patel");
		
		id_req="";
		for(int i=0; i<parts.length;i++)
		{
			if(id_req.equals(""))
				id_req = parts[i];
			else
				id_req +=","+ parts[i];
		}
		try
		{
			stmt = con.createStatement();
				rs = Common.GetColumns("P_Create_indent",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_ind")
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
					System.out.println("Some error in country master.");
				}
		
		String query="insert into P_Create_indent("+colName+",ind_add_by) values("+value+","+id_emp_user+")";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				String id_prod="",id_grp="",id_sgrp="",nm_model;
				for(int i=0; i<reqAsstArr.length;i++)
				{
				id_prod =map.get("id_prod"+reqAsstArr[i]);
//					id_grp =map.get("id_grp"+reqAsstArr[i]);
//					id_sgrp =map.get("id_sgrp"+reqAsstArr[i]);
//					nm_model =map.get("nm_model"+reqAsstArr[i]);
//					//budget_type =map.get("budget_type"+reqAsstArr[i]);
//					/* id_prod= map.get("id_prod"+i+"");
//					 id_grp= map.get("id_grp"+i+"");
//					 id_sgrp= map.get("id_sgrp"+i+"");*/
//						if(!id_prod.isEmpty() && !id_grp.isEmpty() && !id_sgrp.isEmpty())
//						{
					
					System.out.println("nm_model is "+ id_prod);
				String sql = "select id_assetdiv,id_s_assetdiv from M_Model where id_model='"+id_prod+"'";
				System.out.println(sql);	
				ps=con.prepareStatement(sql);
    				rs=ps.executeQuery();
   				if(rs.next())
    				{
   					id_grp=rs.getString("id_assetdiv");
   					id_sgrp=rs.getString("id_s_assetdiv");
    					
    				}
    				//System.out.println("update P_request_Asset set id_prod="+id_prod+" where id_req_asst = "+reqAsstArr[i]+" ");
				//	stmt.executeUpdate("update P_request_Asset set id_prod="+id_prod+" where id_req_asst = "+reqAsstArr[i]+" ");
					/*System.out.println("insert into P_Indent_Asset (no_ind,id_req,asset_consumable,id_grp,id_sgrp,"+
							" id_prod,mfr,description,model,qty,un_prc,tot_prc,typ_asst,id_bu,,id_req_asst,budget_type)"+
							" select '"+no_ind+"',id_req,asset_consumable,"+id_grp+","+id_sgrp+","+
							" id_prod,mfr,description,model,qty,un_prc,tot_prc,typ_asst,id_cc,id_req_asst,'"+budget_type+"' FROM P_Request_Asset WHERE id_req_asst = "+reqAsstArr[i]+" ");*/

    				System.out.println("insert into P_Indent_Asset (no_ind,id_req,asset_consumable,id_grp,id_sgrp,"+
							" id_prod,mfr,description,model,qty,un_prc,tot_prc,typ_asst,id_bu,,id_req_asst)"+
							" select '"+no_ind+"',id_req,asset_consumable,"+id_grp+","+id_sgrp+","+
							" id_prod,mfr,description,model,qty,un_prc,tot_prc,typ_asst,id_cc,id_req_asst FROM P_Request_Asset WHERE id_req_asst = "+reqAsstArr[i]+" ");

    				
    				/*stmt.executeUpdate("insert into P_Indent_Asset (no_ind,id_req,asset_consumable,id_grp,id_sgrp,"+
						" id_prod,mfr,description,model,qty,un_prc,tot_prc,typ_asst,id_bu,id_req_asst,budget_type)"+
						" select '"+no_ind+"',id_req,asset_consumable,"+id_grp+","+id_sgrp+","+
						" id_prod,mfr,description,model,qty,un_prc,tot_prc,typ_asst,id_cc,id_req_asst,'"+budget_type+"' FROM P_Request_Asset WHERE id_req_asst = "+reqAsstArr[i]+" ");*/
				
				stmt.executeUpdate("insert into P_Indent_Asset (no_ind,id_req,asset_consumable,id_grp,id_sgrp,"+
						" id_prod,mfr,description,model,qty,un_prc,tot_prc,typ_asst,id_bu,id_req_asst)"+
						" select '"+no_ind+"',id_req,asset_consumable,"+id_grp+","+id_sgrp+","+
						" id_prod,mfr,description,model,qty,un_prc,tot_prc,typ_asst,id_cc,id_req_asst FROM P_Request_Asset WHERE id_req_asst = "+reqAsstArr[i]+" ");
				
				//stmt.executeUpdate("update P_Request_Asset set st_indent='Yes',budget_type='"+budget_type+"' WHERE id_req_asst = "+reqAsstArr[i]+"");
				stmt.executeUpdate("update P_Request_Asset set st_indent='Yes' WHERE id_req_asst = "+reqAsstArr[i]+"");

//				}
				for(int k=0; k<parts.length;k++)
				{
				 sql ="select id_req from P_request_Asset WHERE id_req = "+parts[k]+" and st_indent='No' and asset_bud_type='non_budget' ";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(!rs.next())
				{
					stmt.executeUpdate("update P_request set st_ind='Yes',budget_type='"+budget_type+"' WHERE id_req = "+parts[k]+" ");
					j=1;
				}
				}
			//	stmt.executeUpdate("update P_request set st_ind='Yes' WHERE id_req IN ("+id_req+")");
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
	
	public JSONObject DisplayPurchaseRequestForCreateIndent(String id,String dt_frm,String dt_to,int id_emp_user)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(dt_frm.equals(""))
		{
			dt_frm = "1990-01-01";
		}
		
		if(dt_to.equals(""))
		{
			dt_to = sdf.format(currDate);	
		}
		
		/*
		 * sql
		 * ="select r.id_req,id_req_asst,no_req,asset_consumable, nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_bu,pr.description,(replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv from P_Request r"
		 * + " ,P_Request_Asset pr,M_Emp_User emp,M_BU cc "+
		 * " where r.req_by=emp.id_emp_user and r.id_bu=cc.id_bu and r.st_ind='No' and "
		 * + " pr.id_req=r.id_req and " +
		 * " (pr.st_apprv_rm='Accepted' and pr.st_apprv_it='Accepted' or (aprvl_by_pass='Yes' and send_for_apprvl='Yes')) "
		 * ;
		 */
		
		sql ="select r.id_req,r.budget_type,id_req_asst,no_req,asset_consumable, nm_emp,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req,nm_bu,pr.description,(replace(convert(NVARCHAR, dt_approv, 103), ' ', '-')) as dt_approv from P_Request r" +
					" ,P_Request_Asset pr,M_Emp_User emp,M_BU cc "+
					" where r.req_by=emp.id_emp_user and r.id_bu=cc.id_bu and r.st_ind='No' and "+
					" pr.id_req=r.id_req and " +
					" pr.st_apprv_it='Accepted'  and  aprvl_by_pass='Yes' ";
		
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
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Create_Indent.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject CheckBudget(String ids2 )
	{
		JSONObject json=new JSONObject();
		System.out.println(ids2);
		String id_dcs2[]= ids2.split("Patel");
    	String tempSql2="";
    	for(int i=0; i<id_dcs2.length; i++){
    		if(tempSql2.equals(""))
    			tempSql2=" dc.id_req="+id_dcs2[i];
    		else
    			tempSql2+=" or dc.id_req="+id_dcs2[i];
    	}
		String query = "select count(distinct dc.budget_type) from P_request dc where "+tempSql2+"   ";
		System.out.println(query);
		try 
		{
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				int count = rs.getInt(1);
				System.out.println(count);
				if(count==1)
				{
				json.put("data",1);
				}
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
	
	public JSONObject CreateInden(String id_req , int id_emp_user)
	{
		Date today = new Date();
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		
		String colName="",sql="",tempIndentId="",Indent_No="";
		
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		double budg_util=0.0,budg_allo=0.0,budg_rem=0.0;
		try 
		{
			ps=con.prepareStatement("select max(id_ind) from P_Create_Indent");
			rs1=ps.executeQuery();
			int indId =1;
				if(rs1.next())
				{
					indId =	rs1.getInt(1) +1;
					
				}
				
				Indent_No	= "RFQ-"+ indId;
				
				String[] parts = id_req.split("Patel");
				
				/*
				 * for(int j=0; j<parts.length;j++) {
				 */
				/*	sql="select pra.*,nm_prod,nm_assetdiv,nm_s_assetdiv from "+
					" P_Request_Asset pra,P_request pr,M_Prod_Cart pc,M_Asset_Div ad,M_Subasset_Div sad where "+
					" pr.id_req=pra.id_req and pr.id_req="+parts[j]+" and pra.id_prod=pc.id_prod and pra.id_grp=ad.id_assetdiv and pra.id_sgrp=sad.id_s_assetdiv and st_apprv_fd='Accepted'";
				*/	
					/* sql="select pra.*,no_req,nm_model,nm_assetdiv,nm_s_assetdiv from  P_Request_Asset pra,P_request pr,m_model pc,M_Asset_Div  "+
                         "  ad,M_Subasset_Div sad where  pr.id_req=pra.id_req and pr.id_req="+parts[j]+" and pra.id_prod=pc.id_model and  "+
                    " pra.id_grp=ad.id_assetdiv and pra.id_sgrp=sad.id_s_assetdiv and st_indent='No' and (pra.st_apprv_rm='Accepted' or (aprvl_by_pass='Yes' and send_for_apprvl='Yes'))  ";
					*/
					
					sql="select r.id_req,b.annual_bud,b.annual_bud_done,m.nm_model,m.id_model,bu.nm_bu "
							+ " from P_request r,M_Budget b,M_Model m,M_BU bu,M_Finance_Year fy "
							+ " where b.id_finance=fy.id_fincance and active_year=1 and r.id_bu=b.id_bu and b.id_model=m.id_model and  r.id_bu=bu.id_bu and id_req="+id_req+"" ;
					System.out.println(sql);
					System.out.println(sql);
					ps=con.prepareStatement(sql);
					rs=ps.executeQuery();
					
					   ResultSetMetaData metaData1 = rs.getMetaData();
					    int columns1 = metaData1.getColumnCount();
					    while(rs.next())
					    {
					    	JSONObject jobj3 = new JSONObject();
					    	for(int i=1;i<=columns1;i++)
					    	{
					    		budg_allo=rs.getDouble("annual_bud");
								budg_util=rs.getDouble("annual_bud_done");
								String nm_bu=rs.getString("nm_bu");
								budg_rem=budg_allo-budg_util;
								 jobj3.put("nm_cc",nm_bu);
								 jobj3.put("nm_model",rs.getString("nm_model"));
								    jobj3.put("budg_allo",budg_allo);
								    jobj3.put("budg_util",budg_util);
								    jobj3.put("budg_rem",budg_rem);
					    	}
					    		jarr1.put(jobj3);
					    		
				        }
					    
					sql="select pra.*,no_req,nm_model,pr.budget_type from  P_Request_Asset pra,P_request pr,m_model pc  "+
	                         "  where  pr.id_req=pra.id_req and pr.id_req="+id_req+" and pra.id_prod=pc.id_model and  "+
	                    "  st_indent='No' and pra.st_apprv_rm='Accepted' and aprvl_by_pass='Yes' and send_for_apprvl='Yes'  ";
						
					System.out.println(sql);
					ps=con.prepareStatement(sql);
					rs=ps.executeQuery();
					
					   ResultSetMetaData metaData = rs.getMetaData();
					    int columns = metaData.getColumnCount();
					    while(rs.next())
					    {
					    	String asset_bud_type=rs.getString("asset_bud_type");
					    	String st_apprv_super=rs.getString("st_apprv_super");
					    	System.out.println(asset_bud_type + st_apprv_super);
					    	if(asset_bud_type.equals("budget") || st_apprv_super.equals("Accepted")) {
					    	JSONObject jobj2 = new JSONObject();
					    	for(int i=1;i<=columns;i++)
					    	{
					    		String name=metaData.getColumnName(i);
					    		jobj2.put(name,rs.getString(name));
					    	}
					    	jarr.put(jobj2);
					    	}
					    }
					
					
				/*}*/
				
				json.put("data",jarr);
				json.put("budget", jarr1);
				json.put("dt_ind",userDateFormat.format(today));
				json.put("Indent_No",Indent_No);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
			
			
		return json;
	}
	
	
}
