package com.Purchase;

import java.io.IOException;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;


public class Print_Mail_PO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		HttpSession session = request.getSession(); 
		DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		Enumeration elemet = request.getParameterNames();

		String paramName = "";
		String paramValues = "";
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
		    
		String action = "",dt_to="",dt_frm="",id_po="",id_ven="",no_po="";
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("no_po") !=null)
		{
			no_po = request.getParameter("no_po");
		}
		if(request.getParameter("id_ven") !=null)
		{
			id_ven = request.getParameter("id_ven");
		}
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		if(request.getParameter("id_po") !=null)
		{
			id_po = request.getParameter("id_po");
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
			
		String temp="";
		if(!dt_to.equals(""))
		{
			temp = " and dt_approv <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_approv >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_approv >= '"+dt_frm+"' and dt_approv <= '"+dt_to+"'";
		}
		if(!id_ven.equals(""))
		{
			temp +=" and po.id_ven="+id_ven+"";
		}
		
		
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "Display":
	            	jobj = DisplayPurchaseOrderForPrintMailPurchaseOrder(temp);	
	                break;
	            case "Preview2":
	            	jobj = PreviewPurchaseOrderForPrintMail2(id_emp_user,id_po,id_ven);	
	                break;
	            case "Preview":
	            	jobj = PreviewPurchaseOrderForPrintMail(id_emp_user,id_po,no_po,id_ven);	
	                break;
	                
	           
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in Approval_Quotation.");
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
	
	
	
	public JSONObject PreviewPurchaseOrderForPrintMail(int id_emp_user,String id_po,String no_po,String id_ven)
	{
		int j=0;
		String query="";
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		JSONArray jarr4 = new JSONArray();
		try 
		{
		
			String sql="select po.*,poa.*,(replace(convert(NVARCHAR, poa.dt_recv, 103), ' ', '-')) as dtRecv,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtQuot,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtPo,nm_model,nm_tax "
					+ " from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Model pc,M_Tax t  "
					+ " where t.id_tax=poa.id_tax1 and po.id_po=poa.id_po   and poa.id_prod=pc.id_model  and po.id_po="+id_po+"";
			System.out.println(sql);
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
			    json.put("data", jarr);
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
			json.put("company",jarr1);
			 sql1 = "select * from M_Vendor where id_ven="+id_ven+"";	
				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				
				ResultSetMetaData metaData3 = rs.getMetaData();
				    int columns3 = metaData3.getColumnCount();
				    while(rs.next())
				    {
				    	JSONObject jobj3 = new JSONObject();
				    	for(int i=1;i<=columns3;i++)
				    	{
				    		String name=metaData3.getColumnName(i);
				    		jobj3.put(name,rs.getString(name));
				    	}
				    	jarr4.put(jobj3);
				    }
			json.put("venDetails",jarr4);
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	public JSONObject PreviewPurchaseOrderForPrintMail2(int id_emp_user,String id_po,String id_ven)
	{
		Common dbname=new Common();
		//String DBNM=dbname.dbname();
		
		int j=0;
		String query="";
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		JSONArray jarr1 = new JSONArray();
		JSONArray jarr4 = new JSONArray();
		String sql="";
		try 
		{
		
			/*String sql="select po.*,poa.*,to_char(to_date(dt_recv,'YYYY-MM-DD'),'DD/MM/YYYY') as dtRecv,to_char(to_date(po.dt_po,'YYYY-MM-DD'),'DD/MM/YYYY') as dtQuot,to_char(to_date(po.dt_po,'YYYY-MM-DD'),'DD/MM/YYYY') as dtPo,nm_prod,nm_curr,nm_dlvry,nm_warr,nm_tax,nm_section,nm_dept,nm_org_loc"+ 
					" from P_Purchase_Order po,P_Purchase_Order_Asset poa,M_Currency c,M_Prod_Cart pc,M_Delivery del,M_Warranty w,M_Section s,M_Tax t  ,M_DEPT d, M_ORG_LOCATION l"+
					" where t.id_tax=poa.id_tax and po.id_po=poa.id_po and s.id_section=po.id_section and po.id_curr=c.id_curr and poa.id_prod=pc.id_prod and del.id_dlvry=po.mode_of_delv and w.id_warr=po.warr_guar and po.id_dept=d.id_dept and po.id_org_loc=l.id_org_loc and po.id_po="+id_po+"";
			*/
			/* if(!DBNM.equals("SQL")){
					
			 sql="select FREIGHT_AMOUNT,cd_curr,po.*,poa.*,tt.nm_tax as tttax,to_char(to_date(DT_SCHEDULED_DLVRY,'YYYY-MM-DD'),'DD/MM/YYYY') as DT_SCHEDULED_DLVRY1,poa.frt_rate as frt,poa.add_chrg as adch,poa.frt_text as frtx,poa.buy_back as bbk,pc.cd_prod,pc.uom,pc.description as description1,tod_dest,to_char(to_date(dt_recv,'YYYY-MM-DD'),'DD/MM/YYYY') as dtRecv,to_char(to_date(po.dt_po,'YYYY-MM-DD'),'DD/MM/YYYY') as dtQuot,to_char(to_date(po.dt_po,'YYYY-MM-DD'),'DD/MM/YYYY') as dtPo,nm_prod,nm_curr,t.nm_tax,nm_dept,nm_section"+ 
					" from P_Purchase_Order po,P_Purchase_Order_Asset poa left join m_tax tt on tt.ID_TAX=poa.TAXID_ADD,M_Currency c,M_Prod_Cart pc,M_Tax t  ,M_DEPT d ,m_section s"+
					" where t.id_tax=poa.id_tax and po.id_po=poa.id_po  and po.id_curr=c.id_curr and poa.id_prod=pc.id_prod  and po.id_dept=d.id_dept and po.indent_dept=s.ID_SECTION and po.id_po="+id_po+"";
			
			 }
			 else{
				 sql="select FREIGHT_AMOUNT,cd_curr,po.*,poa.*,tt.nm_tax as tttax, (replace(convert(NVARCHAR, DT_SCHEDULED_DLVRY, 103), ' ', '-')) as DT_SCHEDULED_DLVRY1,poa.frt_rate as frt,poa.add_chrg as adch,poa.frt_text as frtx,poa.buy_back as bbk,pc.cd_prod,pc.uom,pc.description as description1,tod_dest, (replace(convert(NVARCHAR, dt_recv, 103), ' ', '-')) as dtRecv,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtQuot,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtPo,nm_prod,nm_curr,t.nm_tax,nm_dept,nm_section"+ 
							" from P_Purchase_Order po,P_Purchase_Order_Asset poa left join m_tax tt on tt.ID_TAX=poa.TAXID_ADD,M_Currency c,M_Prod_Cart pc,M_Tax t  ,M_DEPT d ,m_section s"+
							" where t.id_tax=poa.id_tax and po.id_po=poa.id_po  and po.id_curr=c.id_curr and poa.id_prod=pc.id_prod  and po.id_dept=d.id_dept and po.indent_dept=s.ID_SECTION and po.id_po="+id_po+"";
					
				 
			 }*/
			sql= "select po.*,(replace(convert(NVARCHAR, po.dt_po, 103), ' ', '-')) as dtpo,pa.gr_tot as grand_total,pa.*,pc.cd_model,pc.nm_model,(pa.qty*pa.un_prc) as total_price,t1.nm_tax as nm_tax1,t1.per_tax as per_tax1,t2.nm_tax as nm_tax2,t2.per_tax as per_tax2,t.t_and_c,t.file_name"+
				" from P_Purchase_Order po,P_Purchase_Order_Asset pa,M_Model pc,M_Tax t1,M_Tax t2,M_Term_Condition t"+
				" where po.id_po="+id_po+" and po.id_po=pa.id_po and pa.id_prod=pc.id_model and pa.id_tax1=t1.id_tax and pa.id_tax2=t2.id_tax and po.id_t_c=t.id_t_c";
			System.out.println(sql);
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
			    		String name=metaData.getColumnName(i).toLowerCase();
			    		jobj2.put(name,rs.getString(name));
			    	}
			    		jarr.put(jobj2);
			    		
		        }
			    json.put("data", jarr);
			    
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
				    		String name=metaData1.getColumnName(i).toLowerCase()
				    				;
				    		jobj3.put(name,rs.getString(name));
				    	}
				    	jarr1.put(jobj3);
				    }
			json.put("company",jarr1);
			
			 sql1 = "select * from M_Vendor where id_ven="+id_ven+"";	
				ps=con.prepareStatement(sql1);
				rs=ps.executeQuery();
				
				ResultSetMetaData metaData3 = rs.getMetaData();
				    int columns3 = metaData3.getColumnCount();
				    while(rs.next())
				    {
				    	JSONObject jobj3 = new JSONObject();
				    	for(int i=1;i<=columns3;i++)
				    	{
				    		String name=metaData3.getColumnName(i).toLowerCase();
				    		jobj3.put(name,rs.getString(name));
				    	}
				    	jarr4.put(jobj3);
				    }
			json.put("venDetails",jarr4);
			
			/*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		    Date date = new Date();  
	
		    json.put("todaydate",formatter.format(date));*/
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	public JSONObject DisplayPurchaseOrderForPrintMailPurchaseOrder(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select po.*,nm_ven,nm_emp from P_Purchase_Order po,M_Emp_User emp,M_Vendor v where emp.id_emp_user=po.approv_by and v.id_ven=po.id_ven "+
					"  "+temp+"";
			System.out.println(sql);
		}
		else
		{
			sql = "select po.*,nm_ven,nm_emp from P_Purchase_Order po,M_Emp_User emp,M_Vendor v where emp.id_emp_user=po.approv_by and v.id_ven=po.id_ven "+
					" ";
			System.out.println(sql);
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
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in Approval_Quotation.");
		}
		 
		return jobj;
		
		
	}
	
	
	}
