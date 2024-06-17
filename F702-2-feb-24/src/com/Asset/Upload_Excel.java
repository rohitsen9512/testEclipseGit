package com.Asset;

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
import org.json.JSONObject;

import com.Common.Common;
import com.lowagie.text.Cell;
import com.lowagie.text.Row;
import jxl.write.Label;
import jxl.write.biff.RowsExceededException;


public class Upload_Excel extends HttpServlet {
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
	 
		    
		String action = "";
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}

		try {
		
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	           case "uploditem":
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
            		
            		String id_loc = request.getParameter("Location"+count[i]+"");
            		id_loc="select id_loc from M_Loc where UPPER(nm_loc)='"+id_loc.toUpperCase()+"'";
            		id_loc= ReturnID(id_loc,"","","","","","M_Loc",request.getParameter("Location"+count[i]+""));
            		
            		
            		String id_subl = request.getParameter("Sub_Location"+count[i]+"");
            		id_subl="select id_sloc from M_Subloc where UPPER(nm_subl)='"+id_subl.toUpperCase()+"'  and id_loc='"+id_loc+"'" ;
            		id_subl= ReturnID(id_subl,"","",id_loc,"","","M_Subloc",request.getParameter("Sub_Location"+count[i]+""));
            		
            		String id_building = request.getParameter("Building"+count[i]+"");
            		id_building="select id_building from M_Building where UPPER(nm_building)='"+id_building.toUpperCase()+"' and id_loc='"+id_loc+"' and id_sloc='"+id_subl+"'";
            		id_building= ReturnID(id_building,"","",id_loc,id_subl,"","M_Building",request.getParameter("Building"+count[i]+""));
            		
            		String id_flr = request.getParameter("Floor"+count[i]+"");
            		id_flr="select id_flr from M_Floor where UPPER(nm_flr)='"+id_flr.toUpperCase()+"'   and id_loc='"+id_loc+"' and id_sloc='"+id_subl+"' and id_building='"+id_building+"'";
            		id_flr= ReturnID(id_flr,"","",id_loc,id_subl,id_building,"M_Floor",request.getParameter("Floor"+count[i]+""));
                	
            		String id_dept1 = request.getParameter("Department"+count[i]+"");
            		id_dept1="select id_dept from M_Dept where UPPER(nm_dept)='"+id_dept1.toUpperCase()+"'  " ;
            		id_dept1= ReturnID(id_dept1,"","","","","","M_Dept",request.getParameter("Department"+count[i]+""));
            		
            		System.out.println(request.getParameter("Vendor"+count[i]+""));
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
            		 sql="insert into A_Invoice_Master(no_inv,dt_inv,no_po,dt_po,id_loc,id_subl,id_building,id_flr,id_ven,add_by,id_dept,dt_grn,dt_dc)" +
            				" values('"+no_inv+"','"+dt_inv+"','"+no_po+"','"+dt_po+"','"+id_loc+"','"+id_subl+"','"+id_building+"','"+id_flr+"','"+id_ven+"','"+id_emp_user+"',"+id_dept1+",'1900-01-01','1900-01-01')";
					 
            		
            		System.out.println(sql);
            		stmt=con.createStatement();
   				    stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
   				
   				 
   				    rs = stmt.getGeneratedKeys();
   		            while (rs.next()) {
   		            	id_inv_m= rs.getInt(1);
   		            } 
            		}
            		//item details////
            		String id_grp="",id_sgrp="",id_prod="",ds_pro="";
            		String cd_prod = request.getParameter("Model"+count[i]+"");
            		String make = request.getParameter("Make"+count[i]+"");
        				id_grp= request.getParameter("Group"+count[i]+"");
        				String Asset_Type=request.getParameter("Asset_Type"+count[i]);
                		if(Asset_Type=="NON-IT") {
                			Asset_Type="NON-IT";
                		}
                		else if(Asset_Type=="Software") {
                			Asset_Type="Software";
                		}
                		else if(Asset_Type=="Accessories") {
                			Asset_Type="accessories";
                		}
    					id_grp="select id_assetdiv from M_Asset_Div where UPPER(nm_assetdiv)='"+id_grp.toUpperCase()+"'";
    					id_grp= ReturnID(id_grp,"","","","","","M_Asset_Div",request.getParameter("Group"+count[i]+""));
                	
    					id_sgrp= request.getParameter("Sub_Group"+count[i]+"");
    					id_sgrp="select id_s_assetdiv from M_Subasset_Div where UPPER(nm_s_assetdiv)='"+id_sgrp.toUpperCase()+"'  and id_assetdiv='"+id_grp+"'";
    					id_sgrp= ReturnID(id_sgrp,id_grp,id_grp,"","","","M_Subasset_Div",request.getParameter("Sub_Group"+count[i]+""));
                	
    			 sql = "select * from M_Model where UPPER(NM_MODEL)='"+cd_prod.toUpperCase()+"'";
    			 System.out.println(sql);
    				ps=con.prepareStatement(sql);
    				rs=ps.executeQuery();
    				if(rs.next())
    				{
    						id_prod=rs.getString("id_model");
    					ds_pro=rs.getString("nm_model");
    				}
    				else{
    					System.out.println("insert into M_Model (nm_model,id_s_assetdiv,id_assetdiv,cd_model,typ_asst,mfr) values('"+ request.getParameter("Model"+count[i]+"")+"','"+id_sgrp+"','"+id_grp+"','NA','"+Asset_Type+"','"+make+"')");
    					stmt.executeUpdate("insert into M_Model (nm_model,id_s_assetdiv,id_assetdiv,cd_model,typ_asst,mfr) values('"+ request.getParameter("Model"+count[i]+"")+"','"+id_sgrp+"','"+id_grp+"','NA','"+Asset_Type+"','"+make+"')");
    					 sql = "select * from M_Model where UPPER(NM_MODEL)='"+cd_prod.toUpperCase()+"'";
    					 System.out.println(sql);
         				ps=con.prepareStatement(sql);
         				rs=ps.executeQuery();
         				if(rs.next())
         				{
         						id_prod=rs.getString("id_model");
         					ds_pro=rs.getString("nm_model");
         				}
    				
    				}
    				
    				
    				String un_prc = request.getParameter("Unit_Price"+count[i]+"");
            		String qty = "1";
            		String NM_ACC_ASSET = "Asset";

            		double per_tax1 = Double.parseDouble(request.getParameter("Tax1_Percentage"+count[i]+""));
            		String id_tax1 = request.getParameter("Tax1_Name"+count[i]+"");
            		double per_tax2 = Double.parseDouble(request.getParameter("Tax2_Percentage"+count[i]+""));
            		String id_tax2 = request.getParameter("Tax2_Name"+count[i]+"");
            		id_tax1="select id_tax from m_tax where UPPER(nm_tax1)='"+id_tax1.toUpperCase()+"' and per_tax1="+per_tax1+" and UPPER(nm_tax2)='"+id_tax2.toUpperCase()+"' and per_tax2="+per_tax2+"";
            		id_tax1= ReturnID(id_tax1,Double.toString(per_tax1),Double.toString(per_tax2),request.getParameter("Tax1_Name"+count[i]+""),"","","m_tax",request.getParameter("Tax2_Name"+count[i]+""));
            		
            		
            		
            		
            		double tax_val1 = (Double.parseDouble(un_prc)*per_tax1)/100;
            		double tax_val2 = (Double.parseDouble(un_prc)*per_tax1)/100;
            		String othr = "0";
            		String duty ="0";
            		double tt_un_prc = (Double.parseDouble(un_prc))*(Double.parseDouble(qty));
            		double tt = tt_un_prc+tax_val1+tax_val2;
            		System.out.println("insert into A_INVOICE(model,id_dept,created_by,config,st_config,typ_asst,gr_tot,tt_un_prc,duty,othr,tax_val1,id_tax1,tax_val2,id_tax2,id_inv_m,id_model,id_grp,id_sgrp,qty,un_prc,NM_ACC_ASSET) " +
            				"values('"+id_dept+"','"+id_emp_user+"','Yes','Yes','"+Asset_Type+"','"+tt+"','"+tt_un_prc+"','"+duty+"','"+othr+"','"+tax_val1+"','"+id_tax1+"','"+tax_val1+"','"+id_tax1+"','"+id_inv_m+"','"+id_prod+"','"+id_grp+"','"+id_sgrp+"','"+qty+"','"+un_prc+"','"+NM_ACC_ASSET+"')");
                	
            		String qryINV="insert into A_INVOICE(model,ds_pro,created_by,config,st_config,typ_asst,gr_tot,tt_un_prc,duty,othr,tax_val1,id_tax1,tax_val2,id_tax2,id_inv_m,id_model,id_grp,id_sgrp,qty,un_prc,NM_ACC_ASSET) " +
                				"values('"+ds_pro+"','"+ds_pro+"','"+id_emp_user+"','Yes','Yes','"+Asset_Type+"','"+(new Double(tt)).longValue()+"'" +
                						"  ,'"+(new Double(tt_un_prc)).longValue()+"','"+duty+"','"+othr+"','"+tax_val1+"','"+id_tax1+"','"+tax_val1+"','"+id_tax1+"','"+id_inv_m+"','"+id_prod+"','"+id_grp+"'," +
                								"  '"+id_sgrp+"','"+qty+"','"+un_prc+"','"+NM_ACC_ASSET+"')";
            			 stmt.executeUpdate(qryINV,Statement.RETURN_GENERATED_KEYS);
            				
           				 int id_inv=0;
        				    rs = stmt.getGeneratedKeys();
        		            while (rs.next()) {
        		            	id_inv= rs.getInt(1);
        		            } 
            			
            			///ADD to store............................
        		           String sl_no = request.getParameter("Serial_No"+count[i]+"");
						  System.out.println(request.getParameter("Quantity"+count[i]+"")); 
						  
							  
        		            String ds_asst = "NA";
    	            	    
        		        	String tag = "Yes";
        		        	String id_service_ven = "";
        		        	
        		        	id_service_ven=id_ven;
        		        
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
        		        	//String remarks = "NA";
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
                        "     typ_asst,rmk_asst,tt_un_prc,typ_proc,id_inv_m,id_inv,nm_acc_asset,val_asst,id_ven_proc, "+
                        "     dt_amc_exp,dt_inv,std_lease,endt_lease,dt_po,dt_amc_start,id_grn,id_model,id_loc,id_subl,id_building,id_flr,id_dept";
             		
             		value="'"+ds_pro+"','"+ds_asst+"','"+id_grp+"','"+id_sgrp+"','"+no_po+"','"+no_inv+"','"+tag+"','','"+make+"','"+warr_amc+"'" +
             				" ,'"+(new Double(tt_un_prc)).longValue()+"','"+cst_asst_add+"','','"+st_lease+"','NA','"+Asset_Type+"','--','"+(new Double(tt_un_prc)).longValue()+"','"+typ_proc+"'" +
             						" ,'"+id_inv_m+"','"+id_inv+"','','"+(new Double(val_asst)).longValue()+"','"+id_ven+"','','"+dt_inv+"','','','"+dt_po+"','','','"+id_prod+"','"+id_loc+"','"+id_subl+"','"+id_building+"','"+id_flr+"',"+id_dept1+"  ";
             		String id_wh_dyn="",preFix="",preFix1="",preFix2="",pre="";
        			int tempAstId=0;
        			System.out.println("select ltrim(cd_s_assetdiv),ltrim(cd_assetdiv) from M_Subasset_Div,M_Asset_Div gp where id_s_assetdiv = "+id_sgrp+" and gp.id_assetdiv="+id_grp+"");
        			
        			ps=con.prepareStatement("select ltrim(cd_s_assetdiv),ltrim(cd_assetdiv) from M_Subasset_Div,M_Asset_Div gp where id_s_assetdiv = "+id_sgrp+" and gp.id_assetdiv="+id_grp+"");
        			rs=ps.executeQuery();
        			if(rs.next())
        			{
        				preFix2  = rs.getString(2);
        				pre  = rs.getString(1);
        				//pre = preFix2+"/"+preFix3;
                    }
        			ps=con.prepareStatement("select ltrim(asset_prefix) from M_Company");
        			rs=ps.executeQuery();
        			if(rs.next())
        			{
        				preFix1 =  rs.getString(1);
        				
                    }
        			String preFix3="";
        			preFix1= preFix1.toUpperCase();
        			System.out.println("Prefix is"+ preFix1);
        			//System.out.println("PRE****"+pre);
        			if(pre.length() <= 3)
        			{
        				preFix3=pre;
        			}
        			else
        			{
        				preFix3=pre.substring(0, Math.min(pre.length(), 3));
        			}
        			preFix =preFix1+"/"+preFix2+"/"+preFix3+"-";
            			
            			
            			String max = "",model=""; 
            			ps=con.prepareStatement("select st_config,model from A_Invoice where id_inv_m="+id_inv_m);
            			rs=ps.executeQuery();
            			if(rs.next())
            			{
            				max = rs.getString(1);
            				model = rs.getString(2);
                        }			
            				
            			 sql="";
            			
            			 
             			String st = "NA";
             			String st1 = "NA";
             			String st2 = "NA";
            				ps=con.prepareStatement("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
            						"from A_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') and id_dept='"+id_dept1+"'");
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
            			}
            				if(j>0)
            				{
            					 query ="update A_Invoice_Master set status_store = 1 where id_inv_m ="+id_inv_m+"";
            					 ps=con.prepareStatement(query);
            					j=ps.executeUpdate();
            					
            				
            				}
            		
            			
    				}
    				
    				catch(Exception e){
    					System.out.println("Some error in upload excel."+e);
    				}
    				j=1;
    			}
	        	   
	            	
	                break;
	           case "UpdateUploadExcel":
	        	   jobj = UpdateUploadExcel();	
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
/*	public JSONObject UpdateUploadExceljxl( ) throws RowsExceededException, WriteException
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		 Workbook workbook = null;
		 
	        try {

	            workbook = Workbook.getWorkbook(new File("E:\\Upload_Stock.xls"));
	            WritableWorkbook copy = Workbook.createWorkbook(new File("E:\\Upload_Stock.xls"), workbook);
	            
	            Sheet sheet = workbook.getSheet(1);
	            WritableSheet wshTemp = copy.getSheet(1);
		          
	            for (int j = 0; j < sheet.getColumns(); j++) {
	                for (int i = 1; i < sheet.getRows(); i++) {
	                    jxl.Cell cell = sheet.getCell(j, i);
	                   
	                        System.out.println("I got a label "
	                                + ((jxl.Cell) cell).getContents());
	                        Label labTemp = new Label(j, i, "pritesh"+i+"");
	                        wshTemp.addCell(labTemp);
	                        System.out.println("I got a number "
	                                + ((jxl.Cell) cell).getContents());
	                   
	                }
	            }
	          
	       
	            copy.write();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (BiffException e) {
	            e.printStackTrace();
	        } finally {

	        	 workbook.close();

	        }

		return jobj;
		}*/
	public JSONObject UpdateUploadExcel( )
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try {
		    FileInputStream file = new FileInputStream(new File("E:\\Upload_Stock.xls"));

		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		    
		    HSSFSheet sheet = workbook.getSheetAt(1);
		    HSSFCell cell = null;
		    HSSFCell cellup = null;
		   /* cellup = sheet.getRow(0).getCell((short) 0);
		     
		    System.out.println(cell);
		     
		    cell.setCellValue("PRITESH");*/
		 
          
	           
             String sql="";
             ArrayList <String> list=new ArrayList<String>();
		    	
		    //Update the value of cell
		   for(short i=0;i<1;i++){
			   cell = sheet.getRow(0).getCell((short) i);
				  
			   String caseval=cell.getStringCellValue();
			  
				
			switch(caseval){
			       case "Division_Name":
			    	 sql="select nm_model from m_model";
			    	 list=ExcelValue(sql); 
			    	 System.out.println(list);
			    	  System.out.println(list.size());
			    	 Iterator<String> iter = list.iterator(); 
			    	 int k=1,t=1;
			    	 for (int p=0;p<list.size();p++) { 
			    		 cellup= sheet.createRow(k).createCell(i);
			    		  // cellup = sheet.getRow(3).getCell((short) i);
			    		 //  cellup= sheet.getRow(1).getCell(i);
						    System.out.println(t);
						    System.out.println(cellup);
						   // System.out.println(list.get(p));
						    
						    cellup.setCellValue(list.get(p));
						k++;
						 t++;
			               //System.out.print(iter.next() + " "); 
			           } 
			    	   break;
					
			
			}
		  	   
			    
		   }
		    
		    file.close();

		    FileOutputStream outFile = new FileOutputStream(new File("E:\\Upload_Stock.xls"));
		    workbook.write(outFile);
		    outFile.close();

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
		    e.printStackTrace();
		}
		
		
		return jobj;
	}
	public String ReturnID(String sql,String Entity_Name,String Country_Name,String City,String City_Sub_Location,String Store,String nm_table,String nm_colmn){
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
				
				if(nm_table.equals("M_Depreciation_Rate")){
					qry="insert into M_Depreciation_Rate (depri_rate,depri_rate_name) values('"+nm_colmn+"','"+nm_colmn+"%')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Loc")){
					qry="insert into M_Loc (cd_loc,nm_loc) values('"+nm_colmn+"','"+nm_colmn+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				//id_flr id_building  //
			
				if(nm_table.equals("M_Subloc")){
					qry="insert into M_Subloc (id_loc,cd_subl,nm_subl) values('"+City+"' ,'"+nm_colmn+"','"+nm_colmn+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Building")){
					qry="insert into M_Building (nm_building,cd_building,id_loc,id_sloc) values('"+nm_colmn+"' ,'"+nm_colmn+"',"+City+","+City_Sub_Location+" )";
					System.out.println(qry);
					 stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Floor")){
					qry="insert into M_Floor (nm_flr,cd_flr,id_loc,id_sloc,id_building) values('"+nm_colmn+"' ,'"+nm_colmn+"',"+City+","+City_Sub_Location+","+Store+" )";
					System.out.println(qry);
					 stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
//				if(nm_table.equals("M_Floor")){
//					qry="insert into M_Floor (id_loc,id_sloc,cd_flr,nm_flr,id_country,id_div,id_building) values('"+Region+"' ,'"+State+"','"+nm_colmn+"','"+nm_colmn+"','1' ,'"+Division_Name+"','"+City+"' )";
//					System.out.println(qry);
//					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
//	 				   rs = stmt.getGeneratedKeys();
//			            while (rs.next()) {
//			            	ID= rs.getString(1);
//			            } 
//					}
				if(nm_table.equals("M_S_Function")){
					qry="insert into M_S_Function (nm_s_function) values('"+nm_colmn+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				//id_flr id_building  //
			
				if(nm_table.equals("M_Dept")){
					String cd = nm_colmn;     //substring containing first 4 characters
					 
					if (cd.length() > 3) 
					{
						cd = nm_colmn.substring(0, 3);
					} 
					else
					{
						cd = nm_colmn;
					}
					qry="insert into M_Dept (nm_dept,cd_dept) values('"+nm_colmn+"','"+cd.toUpperCase()+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Company_costcenter")){
					qry="insert into M_Company_costcenter (nm_cc,cd_cc,id_sub,id_dept) values('"+nm_colmn+"' ,'"+nm_colmn+"',"+City+","+City_Sub_Location+" )";
					System.out.println(qry);
					 stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				
				if(nm_table.equals("M_Asset_Div")){
					String cd = "";     //substring containing first 4 characters
					 
					if (cd.length() > 2) 
					{
						cd = nm_colmn.substring(0, 2);
					} 
					else
					{
						cd = nm_colmn;
					}
					qry="insert into M_Asset_Div (cd_assetdiv,ds_assetdiv,nm_assetdiv) values('"+cd.toUpperCase()+"' ,'"+nm_colmn+"','"+nm_colmn+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_Subasset_Div")){
					String pre_asset = nm_colmn;     //substring containing first 4 characters
					 
					if (nm_colmn.length() > 3) 
					{
						pre_asset = nm_colmn.substring(0, 3);
					} 
					else
					{
						pre_asset = nm_colmn;
					}
					
					qry="insert into M_Subasset_Div (id_assetdiv,nm_s_assetdiv,cd_s_assetdiv,pre_asset) values('"+Entity_Name+"','"+nm_colmn+"' ,'"+pre_asset.toUpperCase()+"','"+pre_asset.toUpperCase()+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				
				if(nm_table.equals("Vendor")){
					qry="insert into M_Vendor (nm_ven,cd_ven,add1,add2,procured,phone,city,state,country,service,nm_contact) values('"+nm_colmn+"' ,'NA','NA','NA','procured','0','NA','NA','NA','service','NA')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				if(nm_table.equals("M_CURRENCY")){
					qry="insert into M_CURRENCY (nm_curr,cd_curr) values('"+nm_colmn+"' ,'"+nm_colmn+"')";
					System.out.println(qry);
					stmt.executeUpdate(qry,Statement.RETURN_GENERATED_KEYS);
	 				   rs = stmt.getGeneratedKeys();
			            while (rs.next()) {
			            	ID= rs.getString(1);
			            } 
					}
				
				if(nm_table.equals("m_tax")){
					qry="insert into m_tax (nm_tax1,per_tax1,nm_tax2,per_tax2) values('"+nm_colmn+"' ,'"+Entity_Name+"','"+City+"','"+Country_Name+"')";
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
