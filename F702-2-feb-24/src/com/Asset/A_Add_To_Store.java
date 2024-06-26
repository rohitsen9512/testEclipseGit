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
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.UserAccessData;


public class A_Add_To_Store extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
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
		      if((parts[0].equals("dt") || (parts[0].equals("endt")) || (parts[0].equals("std"))) && !paramValues.equals(""))
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
		    
		String id="",action = "",dt_to="",dt_frm="",id_inv="",id_inv_m="",SerialVal="",id_sgrp="",id_grn="",sapNo="";
		
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id_grn") !=null)
		{
			id_grn = request.getParameter("id_grn");
		}
		if(request.getParameter("dt_frm") !=null)
		{
			dt_frm = request.getParameter("dt_frm");
		}
		if(request.getParameter("dt_to") !=null)
		{
			dt_to = request.getParameter("dt_to");
		}
		
		if(request.getParameter("id_inv") !=null)
		{
			id_inv = request.getParameter("id_inv");
		}
		if(request.getParameter("id_inv_m") !=null)
		{
			id_inv_m = request.getParameter("id_inv_m");
		}
		if(request.getParameter("sapNo") !=null)
		{
			sapNo = request.getParameter("sapNo");
		}
		if(request.getParameter("SerialVal") !=null)
		{
			SerialVal = request.getParameter("SerialVal");
		}
		if(request.getParameter("id_sgrp") !=null)
		{
			id_sgrp = request.getParameter("id_sgrp");
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
			temp = " and dt_inv <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			temp = " and dt_inv >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			 temp = " and dt_inv >= '"+dt_frm+"' and dt_inv <= '"+dt_to+"'";
		}
		System.out.println(id_grn);
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType"); 
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        case "Save":
	            	jobj = AddToStore(map,id_inv,id_inv_m,SerialVal,sapNo,id_sgrp,id_emp_user,id_grn,request);	
	                break;
		                
	            case "Display":
	            	String tempSql="";
	            	if(!UserType.equals("SUPER"))
	            		tempSql="select g.*,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn from G_Grn g,A_Invoice inv where inv.id_inv=g.id_inv and g.status_store = 0 and status_approv = 1 and type_grn='INV' and g.typ_po='CapG' and typ_asst='"+UserType + "' "+temp+"";
	            	else
	            		tempSql="select g.*,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn from G_Grn g,A_Invoice inv where inv.id_inv=g.id_inv and g.status_store = 0 and status_approv = 1 and type_grn='INV' and g.typ_po='CapG' "+temp+"";
	            	
	            	
	            	//jobj = DisplayAccessoryForAddToStore(temp);	
	            	UserAccessData uad = new UserAccessData();
	            	jobj = uad.GetDataAsJsonFormate(tempSql,  request);
	        		
	                break;
	                
	            case "Edit":
	            	jobj = EditDataForAddToStore(id_inv,id_inv_m);	
	                break;
	            case "CheckSlAndSpNo":
	            	jobj = CheckExitsVal(SerialVal,sapNo);	
	                break;
	            case "DropDownResult":
	            	jobj = DropDownResult(id);	
	                break;  
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in A_Add_To_Store.");
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
	public JSONObject DropDownResult(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_wh,id_wh_dyn from A_Ware_House where id_sgrp="+id+"";
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
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
		    		jarr.put(jobj2);
		    		
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Ware_House.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject CheckExitsVal(String SerialVal , String sapNo)
	{
		JSONObject json=new JSONObject();
		StringTokenizer st = new StringTokenizer(SerialVal , ",,");
		StringTokenizer st1 = new StringTokenizer(sapNo , ",,");
		String query = "";
		int t=1,Count=0;
		String no_mfr="",serialNo="";
		try 
		{
			
			while (st.hasMoreElements()) 
			{
				Count ++;
				no_mfr = (String) st.nextElement();
				if(!no_mfr.equals("NA"))
				{
					query = "select id_wh from A_Ware_House where no_mfr='"+no_mfr+"'";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						json.put("data",1);
						json.put("Count",Count);
						t=0;
						break;
					}
				}
			}
			if(t == 1)
			{
				Count = 0;
				while (st1.hasMoreElements()) 
				{
					Count ++;
					serialNo = (String) st1.nextElement();
					
						query = "select id_wh from A_Ware_House where serial_no='"+serialNo+"'";
						ps=con.prepareStatement(query);
						rs=ps.executeQuery();
						if(rs.next())
						{
							json.put("data",2);
							json.put("Count",Count);
							t=0;
							break;
						}
					
				}
				if(t == 1)
				{
					json.put("data",0);
				}
				
			}
			
			
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	
	public JSONObject EditDataForAddToStore(String id_inv , String id_inv_m)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			/*sql="select aim.*,inv.id_sgrp,inv.id_grp,ds_pro,inv.mfr,inv.model as no_model,tt_un_prc as cst_asst,typ_asst from A_Invoice_Master aim ,A_Invoice inv  where  aim.id_inv_m =inv.id_inv_m and inv.id_inv = "+id_inv+" ";
		*/
		sql="select aim.*,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,inv.id_sgrp,inv.id_grp,ds_pro,inv.mfr,inv.model "+
			 " as no_model,tt_un_prc as cst_asst,typ_asst,tt_un_prc*rate as val_in_inr,rate as ex_rate"+
			  " from A_Invoice_Master aim ,A_Invoice inv,M_Exchange_Rate er  "+
			 " where er.id_curr=aim.id_curr and aim.id_inv_m =inv.id_inv_m and inv.id_inv = "+id_inv+" ";
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
			System.out.println("sql error in A_Add_To_Store.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayAccessoryForAddToStore(String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
		{
			sql = "select *,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn from G_Grn where status_store = 0 and status_approv = 1 and type_grn='INV' and typ_po='CapG' "+temp+"";
		}
		else
		sql = "select *,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn from G_Grn where status_store = 0 and status_approv = 1 and type_grn='INV' and typ_po='CapG'";

		
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
			System.out.println("sql error in A_Add_To_Store.");
		}
		 
		return jobj;
	}
	


public JSONObject AddToStore(HashMap<String, String> map , String id_inv , String id_inv_m , String SerialVal , String sapNo, String id_sgrp ,int id_emp_user,String id_grn,HttpServletRequest request)
{
	String colName="",value="";
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
			
			
			
			String id_wh_dyn="",preFix="",preFix1="",preFix2="";
			int tempAstId=0;
			
			ps=con.prepareStatement("select ltrim(pre_asset) from M_Subasset_Div where id_s_assetdiv = "+id_sgrp+"");
			rs=ps.executeQuery();
			if(rs.next())
			{
				preFix2  = rs.getString(1);
            }
			preFix1  = "LS";
			
			preFix =preFix1+"/"+preFix2+"-";
			
			
				
			String sql="";
			
			StringTokenizer st = new StringTokenizer(SerialVal , ",,");
			StringTokenizer st1 = new StringTokenizer(sapNo , ",,");
			while (st.hasMoreElements()) 
			{
				
				ps=con.prepareStatement("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
						"from A_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') ");
				
				//sql="select  COUNT(id_wh)+1 from A_Ware_House where substring(id_wh_dyn,1,patindex('%000%', id_wh_dyn)-2) = upper('"+preFix+"')";
				//ps=con.prepareStatement(sql);
				rs1=ps.executeQuery();
				
					if(rs1.next())
					{
						tempAstId =	rs1.getInt(1);
					}
					String formatValue  = String.format("%04d", tempAstId);
					id_wh_dyn	=	preFix + formatValue;
									
				
				String query="insert into A_Ware_House("+colName+" ,no_mfr , id_wh_dyn,add_by,serial_no) values("+value+" ,'"+st.nextElement()+"' , '"+id_wh_dyn+"',"+id_emp_user+",'"+st1.nextElement()+"')";
				
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
			}
			if(j > 0)
			{
				
				j=0;
				String query ="update G_Grn set status_store = 1 where id_grn ="+id_grn+"";
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				
						j=1;
			
			}
			
			
			json.put("data",j);
	
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
		return json;
	}

}

