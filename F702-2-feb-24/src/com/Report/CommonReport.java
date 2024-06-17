package com.Report;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;


public class CommonReport extends HttpServlet {
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
		 Enumeration elemet = request.getParameterNames();
		
			
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",dt_to="",dt_frm="",id_po="",dateMonth="",warr_amc="",to_assign="";
		
		if(request.getParameter("to_assign") !=null)
		{
			to_assign = request.getParameter("to_assign");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("warr_amc") !=null)
		{
			warr_amc = request.getParameter("warr_amc");
		}
		if(request.getParameter("dateMonth") !=null)
		{
			dateMonth = request.getParameter("dateMonth");
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
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	                
	            case "ClosedGRN":
	            	jobj = DisplayClosedGRN(dt_frm,dt_to);	
	                break;
	                
	            case "AMCWarranty":
	            	jobj = DisplayAMCWarranty(warr_amc,dateMonth);	
	                break;
	                
	            case "EmpDetails":
	            	jobj = DisplayEmpDetailsReport(to_assign);	
	                break;
	                
	            case "RejectGRN":
	            	jobj = DisplayRejectGRN(dt_frm,dt_to);	
	                break;
	                
	            case "PendingGRNApproval":
	            	jobj = DisplayPendingGRNApproval(dt_frm,dt_to);	
	                break;
	                
	            case "InvoiceDetails":
	            	jobj = DisplayInvoiceDetails(dt_frm,dt_to);	
	                break;
	                
	            case "LocationAsset":
	            	jobj = DisplayLocationAsset(dt_frm,dt_to);	
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

	
	
public JSONObject DisplayLocationAsset(String startdate,String enddate)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	String sql="";
	
	sql="select distinct(g.no_grn), inv.ds_pro, inv.mfr, g.dt_grn,g.no_po,g.dt_po,g.no_inv,g.dt_inv, g.no_dc,g.dt_dc, "+
			" l.nm_loc, sl.nm_subl,v.nm_ven,g.remarks,inm.bd,inm.no_boe,g.no_aprv_sez,qty,qty_recv from G_Grn g, M_Loc l,M_Subloc sl, A_Invoice_Master inm, A_Invoice inv, "+
			" M_Vendor v where l.id_loc=inm.id_loc and sl.id_sloc=inm.id_subl and v.id_ven=inm.id_ven and inm.id_inv_m=inv.id_inv_m "+
			" and g.id_inv=inv.id_inv and g.id_inv_m=inm.id_inv_m and g.status_approv=0 and g.dt_grn >='"+startdate+"' and g.dt_grn <= '"+enddate+"'";
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
public JSONObject DisplayInvoiceDetails(String startdate,String enddate)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	String sql="";
	
	sql= "select im.no_inv, im.dt_inv,im.no_po,im.dt_po,i.ds_pro,i.mfr,i.model,vn.nm_ven,l.nm_loc,"+
			" sl.nm_subl,i.qty,i.un_prc,i.tt,qty*tax_per*un_prc/100 as tt_val,i.othr,i.duty, tt,nm_curr,upload_inv from A_Invoice_Master im, A_Invoice i,M_currency c, "+
			" M_Vendor vn,  M_Loc l,M_Subloc sl where im.id_inv_m=i.id_inv_m and vn.id_ven=im.id_ven and c.id_curr=im.id_curr and "+
			" l.id_loc=im.id_loc and sl.id_sloc=im.id_subl and im.dt_inv  >='"+startdate+"' and im.dt_inv <= '"+enddate+"' order by dt_inv";
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

public JSONObject DisplayPendingGRNApproval(String startdate,String enddate)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	String sql="";
	
	sql="select distinct(g.no_grn), inv.ds_pro, inv.mfr, g.dt_grn,g.no_po,g.dt_po,g.no_inv,g.dt_inv, g.no_dc,g.dt_dc, "+
			" l.nm_loc, sl.nm_subl,v.nm_ven,g.remarks,inm.bd,inm.no_boe,g.no_aprv_sez,qty,qty_recv from G_Grn g, M_Loc l,M_Subloc sl, A_Invoice_Master inm, A_Invoice inv, "+
			" M_Vendor v where l.id_loc=inm.id_loc and sl.id_sloc=inm.id_subl and v.id_ven=inm.id_ven and inm.id_inv_m=inv.id_inv_m "+
			" and g.id_inv=inv.id_inv and g.id_inv_m=inm.id_inv_m and g.status_approv=0 and g.dt_grn >='"+startdate+"' and g.dt_grn <= '"+enddate+"'";
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

	public JSONObject DisplayRejectGRN(String startdate,String enddate)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		sql="select distinct(g.no_grn), inv.ds_pro, inv.mfr, g.dt_grn,g.no_po,g.dt_po,g.no_inv,g.dt_inv, g.no_dc,g.dt_dc, "+
				" l.nm_loc, sl.nm_subl,v.nm_ven,g.remarks,inm.bd,inm.no_boe,g.no_aprv_sez,qty_recv from G_Grn g, M_Loc l,M_Subloc sl, A_Invoice_Master inm, A_Invoice inv, "+
				" M_Vendor v where l.id_loc=inm.id_loc and sl.id_sloc=inm.id_subl and v.id_ven=inm.id_ven and inm.id_inv_m=inv.id_inv_m "+
				" and g.id_inv=inv.id_inv and g.id_inv_m=inm.id_inv_m and g.status_approv=2 and g.dt_grn >='"+startdate+"' and g.dt_grn <= '"+enddate+"'";
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
	
	

	public JSONObject DisplayEmpDetailsReport(String to_assign)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		String sql="";
		if(to_assign.equals("all"))
		{
			sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,cc.nm_cc,e.nm_emp,dt_alloc from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl, M_Company_Costcenter cc where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and cc.id_cc=wh.id_cc";
			}
			else
			{
				sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,cc.nm_cc,e.nm_emp,dt_alloc from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl, M_Company_Costcenter cc where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and cc.id_cc=wh.id_cc and to_assign="+to_assign+"";
			}
		
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
	public JSONObject DisplayAMCWarranty(String warr_amc,String dateMonth)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
		String sql="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm");	
		Date date = formatter.parse(dateMonth);
		
		sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,no_model,l.nm_loc, sl.nm_subl,"+
				"  dt_amc_start,dt_amc_exp,nm_ven from A_Ware_House wh,M_Loc l,M_Subloc sl,M_Vendor v where l.id_loc=wh.id_loc and v.id_ven=wh.id_ven_proc and "+
				"sl.id_sloc=wh.id_subl and wh.warr_amc='"+warr_amc+"' "+
				" and (CONVERT(CHAR(5), dt_amc_exp, 120) + CONVERT(CHAR(2), dt_amc_exp, 101) ) =  replace(convert(NVARCHAR, '"+formatter.format(date)+"', 101), ' ', '-')";
		
			 
		
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
	
	public JSONObject DisplayClosedGRN(String startdate,String enddate)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			sql="select distinct(id_wh_dyn) as id_wh_dyn, g.no_grn, wh.ds_pro, wh.mfr, g.dt_grn,g.no_po,g.dt_po,g.no_inv,g.dt_inv, "+
					" g.no_dc,g.dt_dc ,l.nm_loc, sl.nm_subl,v.nm_ven,g.remarks,inm.bd,inm.no_boe,g.no_aprv_sez,qty,qty_recv from A_Ware_House wh, M_Loc l,M_Subloc sl,G_Grn g, A_Invoice_Master inm, M_Vendor v,A_Invoice inv "+
					" where l.id_loc=inm.id_loc and sl.id_sloc=inm.id_subl and wh.id_grn=g.id_grn and v.id_ven=inm.id_ven and g.id_inv_m=inm.id_inv_m "+
					" and inv.id_inv_m=inm.id_inv_m and g.dt_grn >='"+startdate+"' and g.dt_grn <= '"+enddate+"'";
		
			 
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
