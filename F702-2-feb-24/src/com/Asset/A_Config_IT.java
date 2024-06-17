package com.Asset;

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

import dto.Common.UserAccessData;


public class A_Config_IT extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	PreparedStatement ps1=null;
	Connection con1=null;
	
	Statement stmt=null;
	
	Date currDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		 HttpSession session = request.getSession();  

		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  

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
		    	  paramValues=paramValues.replace("'", "''");
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		    
		    
		String id="",temp="",action="",dept="",Config_file="",id_wh_dyn="",dt_grn="",ds_pro="",no_mfr="",no_model="",ds_asst="",rmk_asst="",dt_config="",warr_amc="",dt_amc_start="",dt_amc_exp="";
		String id_grp="",id_sgrp="",id_loc="",id_subl="",typ_asst="",word="",nm_table="",ColName="",value="";
		if(request.getParameter("searchWord") !=null)
		{
			word = request.getParameter("searchWord");
		}	
		if(request.getParameter("nm_table") !=null)
		{
			nm_table = request.getParameter("nm_table");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
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
		
		if(request.getParameter("id_wh_dyn") !=null)
		{
			id_wh_dyn = request.getParameter("id_wh_dyn");
		}
		if(request.getParameter("ds_pro") !=null)
		{
			ds_pro = request.getParameter("ds_pro");
		}
		if(request.getParameter("no_mfr") !=null)
		{
			no_mfr = request.getParameter("no_mfr");
		}
		if(request.getParameter("no_model") !=null)
		{
			no_model = request.getParameter("no_model");
		}
		if(request.getParameter("ds_asst") !=null)
		{
			ds_asst = request.getParameter("ds_asst");
		}
		if(request.getParameter("rmk_asst") !=null)
		{
			rmk_asst = request.getParameter("rmk_asst");
		}
		if(request.getParameter("dt_config") !=null)
		{
			dt_config = request.getParameter("dt_config");
		}
		if(request.getParameter("warr_amc") !=null)
		{
			warr_amc = request.getParameter("warr_amc");
		}
		if(request.getParameter("dt_amc_start") !=null)
		{
			dt_amc_start = request.getParameter("dt_amc_start");
		}
		if(request.getParameter("dt_amc_exp") !=null)
		{
			dt_amc_exp = request.getParameter("dt_amc_exp");
		}
		if(request.getParameter("dept") !=null)
		{
			dept = request.getParameter("dept");
		}
		if(request.getParameter("Config_file") !=null)
		{
			Config_file = request.getParameter("Config_file");
		}
		if(request.getParameter("dt_grn") !=null)
		{
			dt_grn = request.getParameter("dt_grn");
		}
		
		try
		{
		
			if(!dt_grn.equals(""))
			{
				dt_grn = dateFormatNeeded.format(userDateFormat.parse(dt_grn));
			}
		int id_emp_user = (int)session.getAttribute("id_emp_user");
		String UserType = (String)session.getAttribute("UserType"); 
		String id_dept = (String)session.getAttribute("DeptId");
		
		String FlrId = (String)session.getAttribute("FlrId");
		int DivId = (int)session.getAttribute("DivId");
		if(!UserType.equals("SUPER")){
			String Query = Common.returnQuery(FlrId);
			temp = temp + " "+Query+" and wh.id_div ="+DivId+"";
		}
		
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        
	            case "Display":
	            	//String tempSql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,ds_asst,serial_no,nm_ven,wh.id_loc,nm_dept,nm_computer as nm_com,nm_flr,rmk_asst from A_Ware_House wh , A_Invoice_master inv , M_Vendor v,M_Dept d,M_Floor mf,M_Model modl where (allocate = 4) and wh.id_flr=mf.id_flr and d.id_dept=wh.id_dept and wh.id_model=modl.id_model and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and st_trvl=0 "+temp+"";
	            	
	            	String tempSql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_model,modl.ds_asst,serial_no,id_subl,rmk_asst,remarks,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dtAlloc from A_Ware_House wh,M_Model modl  where (allocate=4)  and modl.typ_asst IN('IT','soft','nonit') and modl.id_model=wh.id_model and st_trvl=0 and st_config='Yes' "+temp+" ";
	        		
	            	//jobj = DisplayAssetFromStoreForEdit(temp);
	            	System.out.println(tempSql);
	            	UserAccessData uad = new UserAccessData();
	            	jobj = uad.QueryMacker(tempSql , UserType , word , id_dept,  request);
	                break;
	                
	            case "Edit":
	            	jobj = DataForEditFromStore(id);	
	                break;
	                
	            case "Save" :
	            	jobj = SaveITConfiguration(map,request);
	            	break;
	            	
	            case "Update":
	            	jobj = UpdateDataForEditFromStore(map , id,id_emp_user,request);	
	                break;
	            case "Install":
	            	
	            	dt_config = dateFormatNeeded.format(userDateFormat.parse(dt_config));
	            	jobj = Install(id,map,request);	
	                break;
	                
	            case "CheckExitsVal":
	            	jobj = Common.CheckValWhichAllReadyExit("A_Ware_House" , ColName , value,request);	
	                break;
	                
	            case "CheckDate":
	            	jobj = CheckDate(dt_grn,id);	
	                break;
	            
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in A_Install.");
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
	
	public JSONObject CheckDate(String dt_grn , String id)
	{
		JSONObject jobj = new JSONObject();
		
		try
		{
		int t=1;
    	String  sql ="";
    	String d = "1900-01-01",d2="1900-01-01";
    	String d1 = "1900-01-01",d3="1900-01-01";
    	String d4 = "1900-01-01",d5="1900-01-01";
    		String SQL1 ="select typ_asst from A_Ware_House where id_wh="+id+" and allocate=4 and to_assign=0 and dt_alloc='1900-01-01'";
    		ps=con.prepareStatement(SQL1);
    		rs=ps.executeQuery();
    		if(rs.next())
    		{
        			sql ="select dt_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dt_inv from A_Ware_House where id_wh="+id+" and dt_inv > '"+dt_grn+"' ";
      			  ps=con.prepareStatement(sql);
              		rs=ps.executeQuery();
              		    if(rs.next())
              		    {
              		    	t=0;
              		    	d = rs.getString(1);
              		    	d2 = rs.getString(2);
              	        }
    		}else
    		{
            			sql ="select dt_de_allocate,(replace(convert(NVARCHAR, dt_de_allocate, 103), ' ', '-')) as dt_de_allocate from A_Ware_House where id_wh="+id+" and dt_de_allocate > '"+dt_grn+"' ";
          			  ps=con.prepareStatement(sql);
                  		rs=ps.executeQuery();
                  		    if(rs.next())
                  		    {
                  		    	t=0;
                  		    	d1 = rs.getString(1);
                  		    	d3 = rs.getString(2);
                  	        }
    			
    		}
    	if(t == 1)
    	{
    		jobj.put("data", 1);
    	}
    	else
    	{
    		if(sdf.parse(d).before(sdf.parse(d1)))
    		{
        			jobj.put("data", 3);
            		jobj.put("dt_grn",d3);
    		}
    		else
    		{
        			jobj.put("data", 2);
            		jobj.put("dt_grn",d2);
    		}
    		

    	}	
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Install while checking installation date.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject SaveITConfiguration(HashMap<String , String> map,HttpServletRequest request)
	{
		JSONObject json=new JSONObject(); 
		String colName="",value="";
		int j=0;
		rs = Common.GetColumns("A_Config",request);
		
		try
		{
		while (rs.next())
		{
		
			if(!rs.getString(1).equals("id"))
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
		String id_wh = map.get("id_wh");
		
		String query="insert into A_Config("+colName+") values("+value+")";
		String nm_computer=map.get("nm_com");
		String rmk_asst=map.get("rmk_asst");
		String ds_asst=map.get("ds_asst");
		PreparedStatement ps=con.prepareStatement(query);
		j=ps.executeUpdate();
		if(j > 0)
		{
			j=0;
			query = "update A_Ware_House set allocate=5,ds_asst='"+ds_asst+"',nm_computer='"+nm_computer+"',rmk_asst='"+rmk_asst+"' where id_wh="+id_wh+"";
			ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				/*	//.........for mail ........
            	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="",id_wh_dyn="";
            	String mailSql="select emp.nm_emp,emp.id_emp,id_wh_dyn,ds_pro,no_model,no_mfr from A_Ware_House wh , M_Emp_User emp where id_wh="+id_wh+" and wh.id_dept=emp.id_dept";
            	
            	DtoCommon dtcm = new DtoCommon();
    			rs = dtcm.GetResultSet(mailSql);
    			if(rs.next())
    			{
    				replyMailId = rs.getString("id_emp");
    				name = rs.getString("nm_emp");
    				itemName = rs.getString("ds_pro");
    				no_model = rs.getString("no_model");
    				slNo = rs.getString("no_mfr");
    				id_wh_dyn = rs.getString("id_wh_dyn");
    				
    			}
    			List<String> recipients = new ArrayList<String>();
    			while(rs.next())
    			{
    				recipients.add(rs.getString("id_emp"));
    			}
    			
    			 mailSql ="select emp.id_emp from A_Ware_House wh,M_Emp_User emp,M_User_Login ul "+
             			" where id_wh="+id_wh+" and ul.type_user=wh.typ_asst and ul.id_emp_user=emp.id_emp_user";
    					 
             	
             	 dtcm = new DtoCommon();
     			rs = dtcm.GetResultSet(mailSql);
     			while(rs.next())
    			{
    				recipients.add(rs.getString("id_emp"));
    			}
    			
    			//recipients.remove(replyMailId);
    			
    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
    			String mailSubject = dtcm.ReturnParticularMessage("notifyAsset");
    			
    			String mailBody = "<b>Hello "+name+" , </b><br><br><br>"+
    							"Below asset has been notified to you. Please collect the same from <a href='#'>IT Help Desk.</a><br>"+
    							"<br>The <b>'"+itemName+"'</b> having Model No/Version <b>'"+no_model+"'</b> ,Serial No <b>'"+slNo+"'</b> and asset id <b>'"+id_wh_dyn+"'</b><br>"+
    						  "<p>"+footerMsg+"</p>";
    		
    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
				*/
				j=1;
			}
				
		}
		
		json.put("data",j);
		}catch(Exception e)
		{
			System.out.println("Error while data inserting in it configuration. "+e);
		}
		
		
		
		return json;
	}
	
	
	public JSONObject UpdateDataForEditFromStore(HashMap<String, String> map , String id,int id_emp_user,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("A_Ware_House",request);
				
						
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
		
			sql="select *,(replace(convert(NVARCHAR, std_lease, 103), ' ', '-')) as stdLease,(replace(convert(NVARCHAR, endt_lease, 103), ' ', '-')) as endtLease,(replace(convert(NVARCHAR, dt_amc_exp, 103), ' ', '-')) as dtAmcExp,(replace(convert(NVARCHAR, dt_amc_start, 103), ' ', '-')) as dtAmcStart,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,nm_computer as nm_com from A_Ware_House  where id_wh = "+id+" ";
		
		
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
	
	public JSONObject DisplayAssetFromStoreForEdit(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		//String sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where allocate = 0 and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven";
		String sql="";
		if(!temp.equals(""))
		{
			 sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where (allocate = 0 or allocate = 2) and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and st_trvl=0 "+temp+"";
				
		}
		else
		 sql="select distinct(id_wh_dyn),id_wh,ds_pro,no_mfr,nm_ven,wh.id_loc from A_Ware_House wh , A_Invoice_master inv , M_Vendor v where (allocate = 0 or allocate = 2) and inv.id_inv_m=wh.id_inv_m and v.id_ven=inv.id_ven and st_trvl=0";
		
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
	public JSONObject Install(String id,HashMap<String, String> map,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONObject json=new JSONObject();
		String colName="",value="";
		int j=0;
		try
		{
				rs = Common.GetColumns("A_Config",request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id")
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
		
		String query= "insert into A_Config("+colName+") values("+value+")";
		try{
			PreparedStatement ps1=con1.prepareStatement(query);
			 j=ps1.executeUpdate();
			 if(j>0){
			 String sql1="update A_Ware_House set allocate=5 where id_wh ="+id+"";
			 try{
			 PreparedStatement ps=con.prepareStatement(sql1);
			 
				j=ps.executeUpdate();
				
				
				if(j>0)
				{
				json.put("data",1);
				}
			 }
			 catch(Exception e){
				 
			 }
			 }
		} 
		catch(Exception e){
			
		}	
		return json;
		
		
	}
	
	
	
}