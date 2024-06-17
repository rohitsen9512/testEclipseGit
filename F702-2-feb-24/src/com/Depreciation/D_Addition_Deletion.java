package com.Depreciation;

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

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

public class D_Addition_Deletion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	Connection con = null;
	Statement stmt = null;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();

		HashMap<String, String> map = new HashMap<String, String>();
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
		    	  paramValues=paramValues.replace("'", "''");
			      map.put(paramName, paramValues);
		      }
		     
		      
		    }
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		
		String action="",id_grp="",id_sgrp="",id_wh="",id_finYear="",assetid="";
		String status="";
		String addAmt="0";
		String addDate="";
		String add_bill="-";
		String add_det="-";
		String staDate="";
		String staAmt="0";
		String del_bill="-";
		String del_det="-";
		String assetname="";
		String assetdesc="-";
		String group="";
		String group_name="-";
		String DepRate="0";
		String Vendor_name="-";
		String del_type="-",subgroup="";
		float Total=0.0f;
		
		 String finstdate1="";
         String ast_id="";
         if (request.getParameter("asset_group_id") != null) {
        	 group= request.getParameter("asset_group_id");
 		}
         if (request.getParameter("asset_subgroup_id") != null) {
        	 subgroup=request.getParameter("asset_subgroup_id");
 		}
         if (request.getParameter("asset_vendor_id") != null) {
        	 Vendor_name =request.getParameter("asset_vendor_id");
 		}
         if (request.getParameter("assetid") != null) {
        	 assetid =request.getParameter("assetid");
 		}
         if (request.getParameter("assetid") != null) {
        	 ast_id =request.getParameter("assetid");
 		}
         if (request.getParameter("assetid1") != null) {
        	 assetname =request.getParameter("assetid1");
 		}
         if (request.getParameter("asset_description") != null) {
        	 assetdesc =request.getParameter("asset_description");
 		}
         if (request.getParameter("status") != null) {
        	 status =request.getParameter("status");
 		}
         if (request.getParameter("add_date") != null) {
        	 addDate =request.getParameter("add_date");
 		}

		//String today=Format.systemDate();

		int j=0;
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		
		if (request.getParameter("id_grp") != null) {
			id_grp = request.getParameter("id_grp");
		}
		if (request.getParameter("id_sgrp") != null) {
			id_sgrp = request.getParameter("id_sgrp");
		}
		
		if (request.getParameter("id_wh") != null) {
			id_wh = request.getParameter("id_wh");
		}
		if (request.getParameter("id_finYear") != null) {
			id_finYear = request.getParameter("id_finYear");
		}
		if (request.getParameter("assetid") != null) {
			assetid = request.getParameter("assetid");
		}
		
		
		try {
			if(!addDate.equals(""))
			{
				addDate = dateFormatNeeded.format(userDateFormat.parse(addDate));
			}
			con = Common.GetConnection(request);

			switch (action) {
			
	                
			case "Display":
				jobj = DisplayAdditionDeletion(id_wh,id_finYear);
				break;
				
			 case "DropDownResult":
	            	jobj = DropDownResult(id_grp,id_sgrp,id_finYear);	
	                break; 
	                
			 case "Save":
	            	
				 stmt = con.createStatement();
				 if(status.equals("add")){

						addAmt =request.getParameter("add_amt");
						add_det =request.getParameter("add_det");
						
						 Total =Float.parseFloat(addAmt);
						 del_type="-";
						 staAmt ="0";
						 del_bill="-";
						 del_det="-";

					}
				 else if(status.equals("sold") || status.equals("writeoff") || status.equals("lost"))
					{
						stmt.executeUpdate("update A_Ware_House set sold='yes',dt_sold='"+addDate+"',soldrem ='"+status+"',allocate='3' where id_wh='"+assetid+"' ");
					}
					if(status.equals("writeoff") || status.equals("delete") || status.equals("sold") || status.equals("lost"))
					{


						 staDate=request.getParameter("add_date");
						 staAmt =request.getParameter("add_amt");
						 del_det =request.getParameter("add_det");
						 if(!staDate.equals(""))
							{
							 staDate = dateFormatNeeded.format(userDateFormat.parse(staDate));
							}
						 
						 Total =Float.parseFloat(staAmt);
						 addAmt ="0";
						 add_bill ="-";
						 add_det ="-";
						 

					}
			if(status.equals("delete") )
					{
						addDate=request.getParameter("add_date");
						if(!addDate.equals(""))
						{
							addDate = dateFormatNeeded.format(userDateFormat.parse(addDate));
						}
					}
					stmt.executeUpdate("insert into addition_deletion (assetid,	assetname,	asset_group_id,asset_subgroup_id,asset_vendor_id,otheramt,	totalamt,	stadate,staAmt,	depmethod,deprate,	addamt,	adddate,asset_status,	sta_det,add_det,asset_description,accessory_id,assetid1)values("+ast_id+",'"+assetname+"','"+group+"','"+subgroup+"','"+Vendor_name+"','0','"+addAmt+"','"+addDate+"','"+staAmt+"','-','"+DepRate+"','"+Total+"','"+addDate+"','"+status+"','"+del_det+"','"+add_det+"','"+assetdesc+"',0,'"+assetid+"')");

					jobj.put("data", 1);
	                break;
	                
	                
	                
	                

			}

			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());

		} catch (Exception e) {
			System.out.println("Error in D_Addition_Deletion.");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	
	public JSONObject DropDownResult(String id_grp , String id_sgrp,String id_finYear)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String end_finance="";
		try
		{
		String sql1="select std_finance,end_finance from M_Finance_Year where id_fincance="+id_finYear+"";
		ps = con.prepareStatement(sql1);
		rs = ps.executeQuery();
		if(rs.next())
		{
			end_finance = rs.getString(2);
		}
		
		String sql="SELECT id_wh,id_wh_dyn FROM A_Ware_House where id_grp='"+id_grp+"' and id_sgrp='"+id_sgrp+"' and sold !='yes' and allocate =1 and dt_alloc < '"+end_finance+"' ORDER BY id_wh_dyn";
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
			System.out.println("sql error in D_Addition_Deletion.");
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject DisplayAdditionDeletion(String id_wh,String id_finYear) {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql = "";

		sql="select id_wh,id_grp,id_sgrp,id_ven_proc,id_wh_dyn,ds_pro,ds_asst,(replace(convert(NVARCHAR(10), dt_allocate, 103), ' ', '-')) as dt_allocate,nm_loc,nm_subl,nm_emp,val_asst "+
				" from A_Ware_House wh,M_Loc loc,M_Subloc sloc,M_Emp_User eu "+
				" where wh.id_loc=loc.id_loc and wh.id_subl=sloc.id_sloc and wh.to_assign=eu.id_emp_user and id_wh ='"+id_wh+"'";
		
		try {
			ps = con.prepareStatement(sql);
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
			
			sql="select std_finance,end_finance from M_Finance_Year where id_fincance="+id_finYear+"";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				jobj.put("std_finance", rs.getString(1));
				jobj.put("end_finance", rs.getString(2));
			}
			
			
		} catch (Exception e) {
			System.out.println("sql error in D_Depreciation_Config.");
		}

		return jobj;

	}

	
	public JSONObject SaveAdditionDeletion(HashMap<String, String> map , String id_wh,HttpServletRequest request)
	{
		String colName="",value="",col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("addition_deletion",  request);
				
						
						while (rs.next())
						{
						
							if((!rs.getString(1).equals("aad_id")))
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
				
			String query="update A_Ware_House set allocate=3 where id_wh ="+id_wh+"";
			ps=con.prepareStatement(query);
			j=ps.executeUpdate();

			 query="insert into addition_deletion("+colName+") values("+value+")";
			 ps=con.prepareStatement(query);
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
	
	
}
