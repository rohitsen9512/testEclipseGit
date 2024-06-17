package com.Master;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Common.Common;
import com.lowagie.text.Cell;
import com.lowagie.text.Row;
import jxl.write.Label;
import jxl.write.biff.RowsExceededException;


public class M_Upload_Master_Data extends HttpServlet {
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
		Enumeration elemet = request.getParameterNames();
		int id_emp_user = (int)session.getAttribute("id_emp_user");
		String id_dept = (String)session.getAttribute("DeptId");
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
	 
		    
		String action = "",module="";
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("module") !=null)
		{
			module = request.getParameter("module");
		}

		try {
		
			con=Common.GetConnection(request);
			stmt=con.createStatement();
			switch (action)
	        {
	           case "uplodmasterdata":
	       		String count[] = request.getParameterValues("count");
    			
        		stmt=con.createStatement();
        		int id_inv_m=0;
				
    			int j=0;
    			for(int i=0;i<count.length;i++)
    			{
    				try{
    					String no_inv0 ="";
    					String dt_inv0 ="";
        				String no_po0 =""; 
        				String dt_po0 ="";
        				if(i!=0){
                	//	dt_po0 = request.getParameter("PO_Date"+count[i-1]+"");
                		no_inv0 = request.getParameter("Invoice_Number"+count[i-1]+"");
                	//	dt_inv0 = request.getParameter("Invoice_Date"+count[i-1]+"");
                	//	no_po0 = request.getParameter("PO_Number"+count[i-1]+"");
        				}
    				String no_inv = request.getParameter("Invoice_Number"+count[i]+"");
    				String dt_inv = request.getParameter("Invoice_Date"+count[i]+"");
    				String no_po = request.getParameter("PO_Number"+count[i]+"");
            		String dt_po = request.getParameter("PO_Date"+count[i]+"");
            		String id_ven = request.getParameter("Vendor"+count[i]+"");
            		id_ven="select id_ven from M_Vendor where UPPER(nm_ven)='"+id_ven.toUpperCase()+"'";
            		id_ven= ReturnID(id_ven,"","","","","","Vendor",request.getParameter("Vendor"+count[i]+""));
            		//&& !dt_inv.equals(dt_inv0) && !no_po.equals(no_po0) && !dt_po.equals(dt_po0)
            		String sql = "select id_inv_m from A_Invoice_Master where UPPER(no_inv)='"+no_inv.toUpperCase()+"' and id_ven='"+id_ven+"'";
            		System.out.println(sql);
            		ps=con.prepareStatement(sql);
    				rs=ps.executeQuery();
    				if(rs.next())
    				{
    					
    					id_inv_m= rs.getInt(1);
    				}
    				else
            		{
            		 sql="insert into A_Invoice_Master(no_inv,dt_inv,no_po,dt_po,id_ven,add_by,dept_id,id_dept)" +
            				" values('"+no_inv+"','"+dt_inv+"','"+no_po+"','"+dt_po+"','"+id_ven+"','"+id_emp_user+"','"+id_dept+"','"+id_dept+"')";
					 
            		
            		System.out.println(sql);
            		stmt=con.createStatement();
   				    stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
   				
   				 
   				    rs = stmt.getGeneratedKeys();
   		            while (rs.next()) {
   		            	id_inv_m= rs.getInt(1);
   		            	no_inv= no_inv;
   		            } 
            		}
    				
            		//item details////
            		String id_grp="",id_sgrp="",id_prod="",ds_pro="",typ_asst="";
            		String cd_prod = request.getParameter("Model"+count[i]+"");
            		String Asset_Type=request.getParameter("Asset_Type"+count[i]);
            		if(Asset_Type=="NON-IT") {
            			Asset_Type="nonit";
            		}
            		else if(Asset_Type=="Software") {
            			Asset_Type="soft";
            		}
            		else if(Asset_Type=="Accessories") {
            			Asset_Type="accessories";
            		}
        				id_grp= request.getParameter("Group"+count[i]+"");
    					id_grp="select id_assetdiv from M_Asset_Div where UPPER(nm_assetdiv)='"+id_grp.toUpperCase()+"'";
    					id_grp= ReturnID(id_grp,"","","","","","M_Asset_Div",request.getParameter("Group"+count[i]+""));
                	
    					id_sgrp= request.getParameter("Sub_Group"+count[i]+"");
    					id_sgrp="select id_s_assetdiv from M_Subasset_Div where UPPER(nm_s_assetdiv)='"+id_sgrp.toUpperCase()+"'  and id_assetdiv='"+id_grp+"'";
    					id_sgrp= ReturnID(id_sgrp,id_grp,id_grp,"","","","M_Subasset_Div",request.getParameter("Sub_Group"+count[i]+""));
                	
    			 sql = "select * from M_Model where UPPER(NM_MODEL)='"+cd_prod.toUpperCase()+"'";
    				ps=con.prepareStatement(sql);
    				rs=ps.executeQuery();
    				if(rs.next())
    				{
    						id_prod=rs.getString("id_model");
    					ds_pro=cd_prod;
    					typ_asst=Asset_Type;
    				}
    				else{
    					
    					stmt.executeUpdate("insert into M_Model (nm_model,id_s_assetdiv,id_assetdiv,cd_model,typ_asst) values('"+ request.getParameter("Model"+count[i]+"")+"','"+id_sgrp+"','"+id_grp+"','NA','"+Asset_Type+"')");
    					 sql = "select * from M_Model where UPPER(NM_MODEL)='"+cd_prod.toUpperCase()+"'";
         				ps=con.prepareStatement(sql);
         				rs=ps.executeQuery();
         				if(rs.next())
         				{
         						id_prod=rs.getString("id_model");
         					ds_pro=rs.getString("nm_model");
         					typ_asst=rs.getString("typ_asst");
         				}
    				
    				}
    				
                    
    				String un_prc = request.getParameter("Unit_Price"+count[i]+"");
            		String qty = request.getParameter("Quantity"+count[i]+"");
            		String NM_ACC_ASSET = "Asset";

            		
            	
            		String id_tax1 = "SGST";
            		String id_tax2 = "CGST";
            		double per_tax1=9;
            		double per_tax2=9;		
            		id_tax1="select id_tax from m_tax where (UPPER(nm_tax1)='"+id_tax1.toUpperCase()+"' and UPPER(nm_tax2)='"+id_tax2.toUpperCase()+"') or (UPPER(nm_tax1)='"+id_tax2.toUpperCase()+"' and UPPER(nm_tax2)='"+id_tax1.toUpperCase()+"')";
            		id_tax1= ReturnID(id_tax1,Double.toString(per_tax1),"","","","","m_tax","SGST");
            		
            		double tax_val1 = (Double.parseDouble(un_prc)*per_tax1)/100;
            		
            		String othr = "0";
            		String duty ="0";
            		double tt_un_prc = (Double.parseDouble(un_prc))*(Double.parseDouble(qty));
            		double tt = tt_un_prc+tax_val1+tax_val1;
            		System.out.println("insert into A_INVOICE(model,id_dept,created_by,config,st_config,typ_asst,gr_tot,tt_un_prc,duty,othr,tax_val1,id_tax1,tax_val2,id_tax2,id_inv_m,id_model,id_grp,id_sgrp,qty,un_prc,NM_ACC_ASSET) " +
            				"values('"+id_dept+"','"+id_emp_user+"','Yes','Yes','"+typ_asst+"','"+tt+"','"+tt_un_prc+"','"+duty+"','"+othr+"','"+tax_val1+"','"+id_tax1+"','"+tax_val1+"','"+id_tax1+"','"+id_inv_m+"','"+id_prod+"','"+id_grp+"','"+id_sgrp+"','"+qty+"','"+un_prc+"','"+NM_ACC_ASSET+"')");
                	
            		String qryINV="insert into A_INVOICE(model,ds_pro,id_dept,created_by,config,st_config,typ_asst,gr_tot,tt_un_prc,duty,othr,tax_val1,id_tax1,tax_val2,id_tax2,id_inv_m,id_model,id_grp,id_sgrp,qty,un_prc,NM_ACC_ASSET) " +
                				"values('"+ds_pro+"','"+ds_pro+"','"+id_dept+"','"+id_emp_user+"','Yes','Yes','"+typ_asst+"','"+tt+"'" +
                						"  ,'"+tt_un_prc+"','"+duty+"','"+othr+"','"+tax_val1+"','"+id_tax1+"','"+tax_val1+"','"+id_tax1+"','"+id_inv_m+"','"+id_prod+"','"+id_grp+"'," +
                								"  '"+id_sgrp+"','"+qty+"','"+un_prc+"','"+NM_ACC_ASSET+"')";
            			 stmt.executeUpdate(qryINV,Statement.RETURN_GENERATED_KEYS);
            				
           				 int id_inv=0;
        				    rs = stmt.getGeneratedKeys();
        		            while (rs.next()) {
        		            	id_inv= rs.getInt(1);
        		            } 
            			
            			///ADD to store............................
        		            
        		        	String ds_asst = "NA";
        	            	    
        		        	String tag = "NO";
        		        	String id_service_ven = request.getParameter("Service_Vendor"+count[i]+"");
        		        	
//        		        	id_service_ven="select id_ven from M_Vendor where UPPER(nm_ven)='"+id_service_ven.toUpperCase()+"'";
//        		        
//        		        	id_service_ven= ReturnID(id_service_ven,"","","","","","Vendor",request.getParameter("Service_Vendor"+count[i]+""));
            	            
        		        	//id_service_ven= ReturnID(id_service_ven,"","","","","","","");
                    	
        		        	String warr_amc = "IT NO";
        		        	if(warr_amc.equals("IT NO")){
        		        		warr_amc="O";
            				}
                            if(warr_amc.equals("AMC")){
                            	warr_amc="A";
            				}
                            if(warr_amc.equals("Warranty")){
                            	warr_amc="W";
            				}
        		        	
        		        	String cst_asst_add = "0";
        		        	String remarks = "NA";
        		        	String typ_proc = "Outright Purchase";
        		        	if(typ_proc.equals("Outright Purchase")){
        		        		typ_proc="OP";
            				}
                            if(typ_proc.equals("Loan Basis")){
                            	typ_proc="LB";
            				}
                            if(typ_proc.equals("Free Of Cost")){
                            	typ_proc="FOC";
            				}
        		        	
        		        	String st_lease = "Not under lease";
        		        	  if(st_lease.equals("Not under lease")){
        		        		  st_lease="NUL";
              				}
                              if(st_lease.equals("Under lease")){
                            	  st_lease="UL";
              				}
        		        	
        		        	
        		        	double val_asst = tt_un_prc+0;
                    	       
            		String 	colName="",value="";
             		colName="ds_pro,ds_asst,id_grp,id_sgrp,no_po,no_inv,tag,mfr,id_service_ven,warr_amc,cst_asst,cst_asst_add,bd,st_lease,remarks, "+
                        "     id_cc,id_curr,typ_asst,rmk_asst,tt_un_prc,typ_proc,id_inv_m,id_inv,nm_acc_asset,val_asst,id_ven_proc, "+
                        "     dt_amc_exp,dt_inv,std_lease,endt_lease,dt_po,dt_amc_start,id_grn,id_dept,id_model";
             		
             		value="'"+ds_pro+"','"+ds_asst+"','"+id_grp+"','"+id_sgrp+"','"+no_po+"','"+no_inv+"','"+tag+"','','"+id_service_ven+"','"+warr_amc+"'" +
             				" ,'"+tt_un_prc+"','"+cst_asst_add+"','','"+st_lease+"','"+remarks+"','0','0','"+typ_asst+"','--','"+tt_un_prc+"','"+typ_proc+"'" +
             						" ,'"+id_inv_m+"','"+id_inv+"','','"+val_asst+"','"+id_ven+"','','"+dt_inv+"','','','"+dt_po+"','','','"+id_dept+"','"+id_prod+"'  ";
            			String id_wh_dyn="",preFix="",preFix1="",preFix2="",pre="";
            			int tempAstId=0;
            			ps=con.prepareStatement("select ltrim(pre_asset),ltrim(cd_assetdiv) from M_Subasset_Div,M_Asset_Div gp where id_s_assetdiv = "+id_sgrp+" and gp.id_assetdiv="+id_grp+"");
            			rs=ps.executeQuery();
            			if(rs.next())
            			{
            				preFix2  = rs.getString(2);
            				pre  = rs.getString(1);
            				//pre = preFix2+"/"+preFix3;
                        }
            			preFix1  = "CF";
            			
            			preFix =preFix1+"/"+preFix2+"/"+pre+"-";
            			
            			
            			String max = "",model=""; 
            			ps=con.prepareStatement("select st_config,model from A_Invoice where id_inv_m="+id_inv_m);
            			rs=ps.executeQuery();
            			if(rs.next())
            			{
            				max = rs.getString(1);
            				model = rs.getString(2);
                        }			
            				
            			 sql="";
            			 String sl_no = request.getParameter("Serial_No"+count[i]+"");
            			String st = "NA";
            			String st1 = "NA";
            			String st2 = "NA";
            				ps=con.prepareStatement("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
            						"from A_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') ");
            				rs1=ps.executeQuery();
            				
            					if(rs1.next())
            					{
            						tempAstId =	rs1.getInt(1);
            					}
            					String formatValue  = String.format("%04d", tempAstId);
            					id_wh_dyn	=	preFix + formatValue;
            									
            				
            				String query="insert into A_Ware_House("+colName+",no_mfr , id_wh_dyn,add_by,serial_no,appNo,st_config,no_model) values("+value+" ,'"+st+"' , '"+id_wh_dyn+"',"+id_emp_user+",'"+sl_no+"','"+st2+"','"+max+"','"+model+"')";
            				System.out.println(query);
            				PreparedStatement ps=con.prepareStatement(query);
            				j=ps.executeUpdate();
            				System.out.println("J:"+j);
            			if(j > 0)
            			{
            				
            				
            				j=0;
            				 query ="update A_Invoice set status_store = 1  where id_inv ="+id_inv+"";
            				 ps=con.prepareStatement(query);
            				j=ps.executeUpdate();
            				if(j>0)
            				{
            					query ="update A_Invoice_Master set status_store = 1 where id_inv_m ="+id_inv_m+"";
            					 ps=con.prepareStatement(query);
            					j=ps.executeUpdate();
            					j=1;
            				
            				}
            		
            			}
    				}
    				catch(Exception e){
    					System.out.println("Some error in upload excel."+e);
    				}
    			}
	        	   
	            	
	                break;
	           case "location":
	        	   jobj = location(request);	
	        	   break; 
	           case "sub_location":
	        	   jobj = sub_location(request);	
	        	   break;  
	           case "building":
	        	   jobj = building(request);	
	        	   break; 
	           case "floor":
	        	   jobj = floor(request);	
	        	   break; 
	           case "category":
	        	   jobj = category(request);	
	        	   break; 
	           case "sub_category":
	        	   jobj = sub_category(request);	
	        	   break; 
	           case "model":
	        	   jobj = model(request);	
	        	   break; 
	           case "department":
	        	   jobj = department(request);	
	        	   break;   
	           case "cost_center":
	        	   jobj = cost_center(request);	
	        	   break;   
	           case "user_type":
	        	   jobj = user_type(request);	
	        	   break;   
	           case "designation":
	        	   jobj = designation(request);	
	        	   break;   
	           case "employee":
	        	   jobj = employee(request);	
	        	   break; 
	           case "tax":
	        	   jobj = tax(request);	
	        	   break;    
	          
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in uload_excel."+e);
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

	public JSONObject location(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_loc = request.getParameter("nm_loc"+count[i]+"");
        		id_loc="select id_loc from M_Loc where UPPER(nm_loc)='"+id_loc.toUpperCase()+"'";
        		id_loc= ReturnID(id_loc,"","","","",request.getParameter("cd_loc"+count[i]+""),"M_Loc",request.getParameter("nm_loc"+count[i]+""));
        		
        		
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject sub_location(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_loc = request.getParameter("nm_loc"+count[i]+"");
        		id_loc="select id_loc from M_Loc where UPPER(nm_loc)='"+id_loc.toUpperCase()+"'";
        		id_loc= ReturnID(id_loc,"","","","","NA","M_Loc",request.getParameter("nm_loc"+count[i]+""));
			
        		String id_subl = request.getParameter("nm_subl"+count[i]+"");
        		id_subl="select id_sloc from M_Subloc where UPPER(nm_subl)='"+id_subl.toUpperCase()+"'  and id_loc='"+id_loc+"'" ;
        		id_subl= ReturnID(id_subl,"","",id_loc,"","","M_Subloc",request.getParameter("nm_subl"+count[i]+""));
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	public JSONObject building(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_loc = request.getParameter("nm_loc"+count[i]+"");
        		id_loc="select id_loc from M_Loc where UPPER(nm_loc)='"+id_loc.toUpperCase()+"'";
        		id_loc= ReturnID(id_loc,"","","","","NA","M_Loc",request.getParameter("nm_loc"+count[i]+""));
			
        		String id_subl = request.getParameter("nm_subl"+count[i]+"");
        		id_subl="select id_sloc from M_Subloc where UPPER(nm_subl)='"+id_subl.toUpperCase()+"'  and id_loc='"+id_loc+"'" ;
        		id_subl= ReturnID(id_subl,"","",id_loc,"","","M_Subloc",request.getParameter("nm_subl"+count[i]+""));
        		
        		String id_building = request.getParameter("nm_building"+count[i]+"");
        		id_building="select id_building from M_Building where UPPER(nm_building)='"+id_building.toUpperCase()+"'  and id_loc='"+id_loc+"' and id_sloc='"+id_subl+"'";
        		id_building= ReturnID(id_building,"","",id_loc,id_subl,"","M_Building",request.getParameter("nm_building"+count[i]+""));
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject floor(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_loc = request.getParameter("nm_loc"+count[i]+"");
        		id_loc="select id_loc from M_Loc where UPPER(nm_loc)='"+id_loc.toUpperCase()+"'";
        		id_loc= ReturnID(id_loc,"","","","","NA","M_Loc",request.getParameter("nm_loc"+count[i]+""));
			
        		String id_subl = request.getParameter("nm_subl"+count[i]+"");
        		id_subl="select id_sloc from M_Subloc where UPPER(nm_subl)='"+id_subl.toUpperCase()+"'  and id_loc='"+id_loc+"'" ;
        		id_subl= ReturnID(id_subl,"","",id_loc,"","","M_Subloc",request.getParameter("nm_subl"+count[i]+""));
        		
        		String id_building = request.getParameter("nm_building"+count[i]+"");
        		id_building="select id_building from M_Building where UPPER(nm_building)='"+id_building.toUpperCase()+"'  and id_loc='"+id_loc+"' and id_sloc='"+id_subl+"'";
        		id_building= ReturnID(id_building,"","",id_loc,id_subl,"","M_Building",request.getParameter("nm_building"+count[i]+""));
			
        		String id_flr = request.getParameter("nm_flr"+count[i]+"");
        		id_flr="select id_flr from M_Floor where UPPER(nm_flr)='"+id_flr.toUpperCase()+"' and id_loc='"+id_loc+"' and id_sloc='"+id_subl+"' and id_building='"+id_building+"'";
        		id_flr= ReturnID(id_flr,"","",id_loc,id_subl,id_building,"M_Floor",request.getParameter("nm_flr"+count[i]+""));
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	public JSONObject category(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_grp = request.getParameter("nm_assetdiv"+count[i]+"");
				id_grp="select id_assetdiv from M_Asset_Div where UPPER(nm_assetdiv)='"+id_grp.toUpperCase()+"'";
				id_grp= ReturnID(id_grp,"","","","",request.getParameter("cd_assetdiv"+count[i]+""),"M_Asset_Div",request.getParameter("nm_assetdiv"+count[i]+""));
        		
        		
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject sub_category(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_grp = request.getParameter("nm_assetdiv"+count[i]+"");
				id_grp="select id_assetdiv from M_Asset_Div where UPPER(nm_assetdiv)='"+id_grp.toUpperCase()+"'";
				id_grp= ReturnID(id_grp,"","","","",request.getParameter("cd_assetdiv"+count[i]+""),"M_Asset_Div",request.getParameter("nm_assetdiv"+count[i]+""));
        		
				String id_sgrp= request.getParameter("nm_s_assetdiv"+count[i]+"");
				id_sgrp="select id_s_assetdiv from M_Subasset_Div where UPPER(nm_s_assetdiv)='"+id_sgrp.toUpperCase()+"'  and id_assetdiv='"+id_grp+"'";
				id_sgrp= ReturnID(id_sgrp,id_grp,id_grp,"","",request.getParameter("cd_s_assetdiv"+count[i]+""),"M_Subasset_Div",request.getParameter("nm_s_assetdiv"+count[i]+""));
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject model(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_grp = request.getParameter("nm_assetdiv"+count[i]+"");
				id_grp="select id_assetdiv from M_Asset_Div where UPPER(nm_assetdiv)='"+id_grp.toUpperCase()+"'";
				id_grp= ReturnID(id_grp,"","","","",request.getParameter("cd_assetdiv"+count[i]+""),"M_Asset_Div",request.getParameter("nm_assetdiv"+count[i]+""));
        		
				String id_sgrp= request.getParameter("nm_s_assetdiv"+count[i]+"");
				id_sgrp="select id_s_assetdiv from M_Subasset_Div where UPPER(nm_s_assetdiv)='"+id_sgrp.toUpperCase()+"'  and id_assetdiv='"+id_grp+"'";
				id_sgrp= ReturnID(id_sgrp,id_grp,id_grp,"","",request.getParameter("cd_s_assetdiv"+count[i]+""),"M_Subasset_Div",request.getParameter("nm_s_assetdiv"+count[i]+""));
			
				String Asset_Type=request.getParameter("typ_asst"+count[i]+"");
				if(Asset_Type.equals("NONIT")) {
					Asset_Type="nonit";
				}
				else if(Asset_Type.equals("SOFTWARE")) {
					Asset_Type="soft";
				}
				else if(Asset_Type.equals("ACCESSORIES")) {
					Asset_Type="accessories";
				}
				String mfr=request.getParameter("mfr"+count[i]+"");
				String ds_asst=request.getParameter("ds_asst"+count[i]+"");
				String cd_prod = request.getParameter("nm_model"+count[i]+"");
				String sql = "select * from M_Model where UPPER(NM_MODEL)='"+cd_prod.toUpperCase()+"'";
   			 System.out.println(sql);
   				ps=con.prepareStatement(sql);
   				rs=ps.executeQuery();
   				if(rs.next())
   				{
   						
   				}
   				else{
   					System.out.println("insert into M_Model (nm_model,id_s_assetdiv,id_assetdiv,typ_asst,mfr,ds_asst) values('"+ request.getParameter("nm_model"+count[i]+"")+"','"+id_sgrp+"','"+id_grp+"','"+Asset_Type+"','"+mfr+"','"+ds_asst+"')");
   					stmt.executeUpdate("insert into M_Model (nm_model,id_s_assetdiv,id_assetdiv,typ_asst,mfr,ds_asst) values('"+ request.getParameter("nm_model"+count[i]+"")+"','"+id_sgrp+"','"+id_grp+"','"+Asset_Type+"','"+mfr+"','"+ds_asst+"')");
   					 sql = "select * from M_Model where UPPER(NM_MODEL)='"+cd_prod.toUpperCase()+"'";
   					 System.out.println(sql);
        				ps=con.prepareStatement(sql);
        				rs=ps.executeQuery();
			}
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject department(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_dept = request.getParameter("nm_dept"+count[i]+"");
        		id_dept="select id_dept from M_Dept where UPPER(nm_dept)='"+id_dept.toUpperCase()+"'";
        		id_dept= ReturnID(id_dept,"","","","","","M_Dept",request.getParameter("nm_dept"+count[i]+""));
        		
        		
        		
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject cost_center(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_cc = request.getParameter("nm_cc"+count[i]+"");
        		id_cc="select id_cc from M_Company_Costcenter where UPPER(nm_cc)='"+id_cc.toUpperCase()+"'";
        		id_cc= ReturnID(id_cc,"","","","","","M_Company_Costcenter",request.getParameter("nm_cc"+count[i]+""));
        		
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject user_type(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_usertype = request.getParameter("nm_usertype"+count[i]+"");
				id_usertype="select id_usertype from M_USER_TYPE where UPPER(nm_usertype)='"+id_usertype.toUpperCase()+"'";
				id_usertype= ReturnID(id_usertype,"","","","","","M_USER_TYPE",request.getParameter("nm_usertype"+count[i]+""));
        		
        		
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject designation(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String id_design = request.getParameter("nm_design"+count[i]+"");
        		id_design="select id_design from M_Designation where UPPER(nm_design)='"+id_design.toUpperCase()+"'";
        		id_design= ReturnID(id_design,"","","","","","M_Designation",request.getParameter("nm_design"+count[i]+""));
        		
        		
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject tax(HttpServletRequest request) throws JSONException
	{
		
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String nm_tax1 = request.getParameter("nm_tax1"+count[i]+"");
				String per_tax1 = request.getParameter("per_tax1"+count[i]+"");
				String nm_tax2 = request.getParameter("nm_tax2"+count[i]+"");
				String per_tax2 = request.getParameter("per_tax2"+count[i]+"");
        		String id_tax = "";
        		id_tax="select id_tax from M_Tax where UPPER(nm_tax1)='"+nm_tax1.toUpperCase()+"' and per_tax1='"+per_tax1+"' and nm_tax2='"+nm_tax2+"' and per_tax2='"+per_tax2+"'" ;
        		id_tax= ReturnID(id_tax,"",per_tax1,nm_tax2,per_tax2,"","M_Tax",request.getParameter("nm_tax1"+count[i]+""));
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public JSONObject employee(HttpServletRequest request) throws JSONException
	{
		String count[] = request.getParameterValues("count");
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		int j=0;
		for(int i=0;i<count.length;i++)
		{
			try{
				String nm_emp = request.getParameter("Employee_Name"+count[i]+"");
				String cd_emp = request.getParameter("Employee_Code"+count[i]+"");
				String id_emp = request.getParameter("Mail_id"+count[i]+"");
				String cont_no = request.getParameter("Contact_No"+count[i]+"");
        		
        		
				String id_loc = request.getParameter("nm_loc"+count[i]+"");
        		id_loc="select id_loc from M_Loc where UPPER(nm_loc)='"+id_loc.toUpperCase()+"'";
        		id_loc= ReturnID(id_loc,"","","","","NA","M_Loc",request.getParameter("nm_loc"+count[i]+""));
			
        		String id_subl = request.getParameter("nm_subl"+count[i]+"");
        		id_subl="select id_sloc from M_Subloc where UPPER(nm_subl)='"+id_subl.toUpperCase()+"'  and id_loc='"+id_loc+"'" ;
        		id_subl= ReturnID(id_subl,"","",id_loc,"","","M_Subloc",request.getParameter("nm_subl"+count[i]+""));
        		
        		String id_building = request.getParameter("nm_building"+count[i]+"");
        		id_building="select id_building from M_Building where UPPER(nm_building)='"+id_building.toUpperCase()+"'  and id_loc='"+id_loc+"' and id_sloc='"+id_subl+"'";
        		id_building= ReturnID(id_building,"","",id_loc,id_subl,"","M_Building",request.getParameter("nm_building"+count[i]+""));
			
        		String id_flr = request.getParameter("nm_flr"+count[i]+"");
        		id_flr="select id_flr from M_Floor where UPPER(nm_flr)='"+id_flr.toUpperCase()+"' and id_loc='"+id_loc+"' and id_sloc='"+id_subl+"' and id_building='"+id_building+"'";
        		id_flr= ReturnID(id_flr,"","",id_loc,id_subl,id_building,"M_Floor",request.getParameter("nm_flr"+count[i]+""));
        		
        		String id_design = request.getParameter("Designation"+count[i]+"");
        		id_design="select id_design from M_Designation where UPPER(nm_design)='"+id_design.toUpperCase()+"'";
        		id_design= ReturnID(id_design,"","","","","","M_Designation",request.getParameter("Designation"+count[i]+""));
        		
        		String id_dept = request.getParameter("Department"+count[i]+"");
        		id_dept="select id_dept from M_Dept where UPPER(nm_dept)='"+id_dept.toUpperCase()+"'";
        		id_dept= ReturnID(id_dept,"","","","","","M_Dept",request.getParameter("Department"+count[i]+""));
        		
        		String id_cc = request.getParameter("Cost_Center"+count[i]+"");
        		id_cc="select id_cc from M_Company_Costcenter where UPPER(nm_cc)='"+id_cc.toUpperCase()+"' and id_dept='"+id_dept+"'";
        		id_cc= ReturnID(id_cc,id_dept,"","","","","M_Company_Costcenter",request.getParameter("Cost_Center"+count[i]+""));
        		
        		String repo_mngr = request.getParameter("Repoting_Manager"+count[i]+"");
        		String sql = "select id_emp_user from M_EMP_USER where UPPER(nm_emp)='"+repo_mngr.toUpperCase()+"'";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					repo_mngr=rs.getString("id_emp_user");
					
				}
				else
        		{
        		 sql="insert into M_EMP_USER(nm_emp,id_loc,id_sloc,id_building,id_flr,repo_mngr,id_dept,id_design,id_cc,emp_type,status_emp,cd_emp,id_emp,cont_no)" +
        				" values('"+repo_mngr+"','"+id_loc+"','"+id_subl+"','"+id_building+"','"+id_flr+"','','"+id_dept+"','"+id_design+"','"+id_cc+"','Employee','Working','NA','NA','')";
				 
        		
        		System.out.println(sql);
        		stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
				   rs = stmt.getGeneratedKeys();
		            while (rs.next()) {
		            	repo_mngr= rs.getString(1);
		            } 
        		}
        		
        		
        		 sql = "select id_emp_user from M_EMP_USER where UPPER(nm_emp)='"+nm_emp.toUpperCase()+"' and id_emp='"+id_emp+"'";
        		System.out.println(sql);
        		ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					
				}
				else
        		{
        		 sql="insert into M_EMP_USER(nm_emp,id_loc,id_sloc,id_building,id_flr,repo_mngr,id_dept,id_design,id_cc,emp_type,status_emp,cd_emp,id_emp,cont_no)" +
        				" values('"+nm_emp+"','"+id_loc+"','"+id_subl+"','"+id_building+"','"+id_flr+"','"+repo_mngr+"','"+id_dept+"','"+id_design+"','"+id_cc+"','Employee','Working','"+cd_emp+"','"+id_emp+"','"+cont_no+"')";
				 
        		
        		System.out.println(sql);
        		stmt=con.createStatement();
				    stmt.executeUpdate(sql);
				
		            } 
        		
			}
			catch(Exception e){
				System.out.println("Some error in Location upload excel."+e);
			}
			jobj.put("data", 1);
		}
		
		
		return jobj;
	}
	
	public String ReturnID(String sql,String Division_Name,String Region,String State,String City,String Store,String nm_table,String nm_colmn){
		System.out.println(sql);
		String ID="";
		String qry="";
		try {
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next())
			{
				ID=rs.getString(1);
			}
			else{
				if(nm_table.equals("M_Loc")){
					qry="insert into M_Loc (nm_loc,cd_loc) values('"+nm_colmn+"' ,'"+Store+"' )";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Subloc")){
					qry="insert into M_Subloc (id_loc,cd_subl,nm_subl) values('"+State+"' ,'"+nm_colmn+"','"+nm_colmn+"' )";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				//id_flr id_building  //
			
				if(nm_table.equals("M_Building")){
					qry="insert into M_Building (id_loc,id_sloc,cd_building,nm_building,id_country,id_div) values('"+State+"','"+City+"' ,'"+nm_colmn+"','"+nm_colmn+"','1' ,'"+Division_Name+"' )";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Floor")){
					qry="insert into M_Floor (id_loc,id_sloc,cd_flr,nm_flr,id_building) values('"+State+"' ,'"+City+"','"+nm_colmn+"','"+nm_colmn+"','"+Store+"' )";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Asset_Div")){
					qry="insert into M_Asset_Div (cd_assetdiv,nm_assetdiv) values('"+Store+"' ,'"+nm_colmn+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Subasset_Div")){
					String pre_asset = "";     //substring containing first 4 characters
					 
					if (nm_colmn.length() > 3) 
					{
						pre_asset = nm_colmn.substring(0, 3);
					} 
					else
					{
						pre_asset = nm_colmn;
					}
					
					qry="insert into M_Subasset_Div (id_assetdiv,nm_s_assetdiv,cd_s_assetdiv) values('"+Division_Name+"','"+nm_colmn+"' ,'"+Store+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_EMP_USER")){
					qry="insert into M_EMP_USER (nm_emp,emp_type,status_emp,id_design) values('"+nm_colmn+"' ,'Employee','Working','"+Division_Name+"' )";
					System.out.println(qry);
					 stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("Vendor")){
					qry="insert into M_Vendor (nm_ven,cd_ven,add1,add2,procured,phone,city,state,country,service) values('"+nm_colmn+"' ,'NA','NA','NA','procured','0','NA','NA','NA','service')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				
				if(nm_table.equals("M_Tax")){
					qry="insert into m_tax (nm_tax1,per_tax1,nm_tax2,per_tax2) values('"+nm_colmn+"' ,'"+Region+"','"+State+"','"+City+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Dept")){
					qry="insert into M_Dept (cd_dept,nm_dept) values('"+nm_colmn+"' ,'"+nm_colmn+"' )";
					System.out.println(qry);
					 stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
					if(nm_table.equals("M_Company_Costcenter")){
						qry="insert into M_Company_Costcenter (nm_cc,cd_cc) values('"+nm_colmn+"' ,'"+nm_colmn+"')";
						System.out.println(qry);
						stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
		 				   rs = stmt.getGeneratedKeys();
				            while (rs.next()) {
				            	ID= rs.getString(1);
				            } 
						}
					if(nm_table.equals("M_Designation")){
						qry="insert into M_Designation (cd_design,nm_design) values('"+nm_colmn+"' ,'"+nm_colmn+"' )";
						System.out.println(qry);
						 stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
		 				   rs = stmt.getGeneratedKeys();
				            while (rs.next()) {
				            	ID= rs.getString(1);
				            } 
						}
					if(nm_table.equals("M_USER_TYPE")){
						qry="insert into M_USER_TYPE (cd_usertype,nm_usertype) values('"+nm_colmn+"' ,'"+nm_colmn+"' )";
						System.out.println(qry);
						 stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
		 				   rs = stmt.getGeneratedKeys();
				            while (rs.next()) {
				            	ID= rs.getString(1);
				            } 
						}
				
				
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return ID;
	}	
	

	public ArrayList<String> ExcelValue(String sql){
		ArrayList<String> arr= new ArrayList<String>();
		
		try {
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next())
			{
				//arr.rs.getString(1);
				arr.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return arr;
	}
	
}
