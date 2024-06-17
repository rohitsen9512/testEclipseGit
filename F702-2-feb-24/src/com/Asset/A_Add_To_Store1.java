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

import dto.Common.DtoCommon;


public class A_Add_To_Store1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	
	PreparedStatement ps=null;
	PreparedStatement ps1=null;
	
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
		    	  System.out.println("date d is " + d);
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
		    
		String mdl_num = "",process_typ="",storeage_typ="",ram_typ="",searchWord="",   action = "",id_div="",dt_to="",dt_frm="",id_grn_asset="",id_grn="",SerialVal="",id_sgrp="",id_grp="",sapNo="",nm_strg1="",appNo="";
		
		
		if(request.getParameter("mdl_num11") !=null)
		{
			mdl_num = request.getParameter("mdl_num11");
		}
		if(request.getParameter("process_typ11") !=null)
		{
			process_typ = request.getParameter("process_typ11");
		}
		if(request.getParameter("storeage_typ11") !=null)
		{
			storeage_typ = request.getParameter("storeage_typ11");
		}
		if(request.getParameter("ram_typ11") !=null)
		{
			ram_typ = request.getParameter("ram_typ11");
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
		
		if(request.getParameter("id_grn_asset") !=null)
		{
			id_grn_asset = request.getParameter("id_grn_asset");
		}
		if(request.getParameter("id_grn") !=null)
		{
			id_grn = request.getParameter("id_grn");
		}
		if(request.getParameter("sapNo") !=null)
		{
			sapNo = request.getParameter("sapNo");
		}
		if(request.getParameter("appNo") !=null)
		{
			appNo = request.getParameter("appNo");
		}		
		if(request.getParameter("SerialVal") !=null)
		{
			SerialVal = request.getParameter("SerialVal");
		}
		if(request.getParameter("id_sgrp") !=null)
		{
			id_sgrp = request.getParameter("id_sgrp");
		}
		if(request.getParameter("id_grp") !=null)
		{
			id_grp = request.getParameter("id_grp");
		}
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}	
		if(request.getParameter("nm_strg") !=null)
		{
			 nm_strg1 = request.getParameter("nm_strg");
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
			//temp = " and dt_inv <= '"+dt_to+"'";
			temp = " and dt_grn <= '"+dt_to+"'";
		}
		if(!dt_frm.equals(""))
		{
			//temp = " and dt_inv >= '"+dt_frm+"'";
			temp = " and dt_grn >= '"+dt_frm+"'";
		}
		
		if(!dt_frm.equals("") && !dt_to.equals(""))
		{
			// temp = " and dt_inv >= '"+dt_frm+"' and dt_inv <= '"+dt_to+"'";
			 temp = " and dt_grn >= '"+dt_frm+"' and dt_grn <= '"+dt_to+"'";
		}
		String tempQuery="";
			int id_emp_user = (int)session.getAttribute("id_emp_user");
			String UserType = (String)session.getAttribute("UserType"); 
			String id_dept = (String)session.getAttribute("DeptId");
			
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
		        case "Save": 
	            	jobj = AddToStore(mdl_num,process_typ,storeage_typ,ram_typ,map,id_grn_asset,id_grn,SerialVal,sapNo,id_sgrp,id_grp,id_emp_user,appNo,id_dept,request);	
	                break;
		                
		        case "Display":
	            	jobj = DisplayInvoiceForAddToStore(temp,id_dept,UserType , tempQuery, searchWord, request);	
	                break;
	                
	            case "Edit":
	            	jobj = EditDataForAddToStore(id_grn_asset,id_grn);	
	                break;
	            case "CheckSlAndSpNo":
	            	jobj = CheckExitsVal(SerialVal,sapNo,appNo);	
	                break;
	                
	            case "SerialNoGeneration":
	            	jobj = GenerateSerialNoAndSAPNo(id_sgrp);
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
	
	public JSONObject GenerateSerialNoAndSAPNo(String id_sgrp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String sql = "select COUNT(id_wh)+1 as slNo from A_Ware_House wh";
		try
		{
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
		
		    ResultSetMetaData metaData = rs.getMetaData();
		    int columns = metaData.getColumnCount();
		    if(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	for(int i=1;i<=columns;i++)
		    	{
		    		String name=metaData.getColumnName(i);
		    		jobj2.put(name,rs.getString(name));
		    	}
		    	jarr.put(jobj2);
	        }
		    else
		    {
	        	sql = "select 1 as slNo ";
	        	System.out.println(sql);
	        	ps=con.prepareStatement(sql);
	    		rs=ps.executeQuery();
	    		
	    		    metaData = rs.getMetaData();
	    		     columns = metaData.getColumnCount();
	    		    if(rs.next())
	    		    {
	    		    	JSONObject jobj2 = new JSONObject();
	    		    	for(int i=1;i<=columns;i++)
	    		    	{
	    		    		String name=metaData.getColumnName(i);
	    		    		jobj2.put(name,rs.getString(name));
	    		    	}
	    		    		jarr.put(jobj2);
	    		    		
	    	        }	
	        	
	        }
		    jobj.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in A_Add_To_Store."+e.toString());
		}
		 
		return jobj;
	}
	
	public JSONObject CheckExitsVal(String SerialVal , String sapNo, String appNo)
	{
		JSONObject json=new JSONObject();
		StringTokenizer st = new StringTokenizer(SerialVal , ",,");
		StringTokenizer st1 = new StringTokenizer(sapNo , ",,");
		/*StringTokenizer st2 = new StringTokenizer(appNo , ",,");*/
		String query = "";
		int t=1,Count=0;
		String no_mfr="",serialNo=""/*,appno=""*/;
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
			
/*			while (st2.hasMoreElements()) 
			{
				Count ++;
				appno = (String) st2.nextElement();
				if(!appno.equals("NA"))
				{
					query = "select id_wh from A_Ware_House where appNo='"+appno+"'";
					ps=con.prepareStatement(query);
					rs=ps.executeQuery();
					if(rs.next())
					{
						json.put("data",3);
						json.put("Count",Count);
						t=0;
						break;
					}
				}
			}*/
			
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
	
	
	public JSONObject EditDataForAddToStore(String id_grn_asset , String id_grn)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
			/*sql="select aim.*,inv.id_sgrp,inv.id_grp,ds_pro,inv.mfr,inv.model as no_model,tt_un_prc as cst_asst,typ_asst from A_Invoice_Master aim ,A_Invoice inv  where  aim.id_grn =inv.id_grn and inv.id_grn_asset = "+id_grn_asset+" ";
		*/
	/*	sql="select aim.*,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) "+
				" as dtInv,inv.id_sgrp,inv.id_grp,ds_pro,inv.mfr,inv.model  as no_model,tt_un_prc as cst_asst,typ_asst,tt_un_prc "+
				" as val_in_inr,inv.st_config,inv.id_model  from g_grn aim ,g_grn_asset inv   where  aim.id_grn =inv.id_grn and inv.id_grn_asset = "+id_grn_asset+" ";
		*/
		//sql="select id_grn_asset,no_po,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtgrn,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-'))  as dtInv,inv.id_sgrp,inv.id_grp,ds_pro,inv.mfr,inv.model  as no_model,tt_un_prc as cst_asst,typ_asst,tt_un_prc  as val_in_inr,inv.st_config,inv.id_model,aim.*  from g_grn aim ,g_grn_asset inv   where   aim.id_grn =inv.id_grn and inv.id_grn_asset= "+id_grn_asset+" ";
		sql="select id_inv,no_po,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtgrn,(replace(convert(NVARCHAR, dt_dc, 103), ' ', '-')) as dtdc,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,inv.id_sgrp,inv.id_grp,ds_pro,ds_asst,mm.mfr,inv.model  as no_model,un_prc as cst_asst,mm.typ_asst,un_prc   as val_in_inr,inv.st_config,inv.id_model,aim.*  from A_Invoice_Master aim ,A_Invoice inv ,m_model mm where   aim.id_inv_m =inv.id_inv_m  and inv.id_model=mm.id_model and inv.id_inv= "+id_grn_asset+" ";
		
		try
		{
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
	


public JSONObject AddToStore(String mdl_num,String process_typ,String storeage_typ,String ram_typ,HashMap<String, String> map , String id_grn_asset , String id_grn , String SerialVal , String sapNo, String id_sgrp , String id_grp ,int id_emp_user, String appNo, String id_dept,HttpServletRequest request)
{
	String colName="",value="",model="";
	int j=0;
	JSONObject json=new JSONObject();
	
	try 
	{
			rs = Common.GetColumns("A_Ware_House",request);
			
			
/*			while (rs.next())
			{
			
				if(rs.getString(1) !="id_wh" && rs.getString(1) !="appNo")
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
			}*/
			
			while (rs.next())
            {
           
                    if(!(rs.getString(1).trim().equalsIgnoreCase("id_wh") || rs.getString(1).trim().equalsIgnoreCase("appNo") || rs.getString(1).trim().equalsIgnoreCase("st_config")))
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
			
			System.out.println("COL NAME:"+colName);
			System.out.println("VALUE:"+value);
			
			
			
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
			
			
//			String max = ""; 
//			ps=con.prepareStatement("select st_config,model from g_grn_asset where id_grn_asset="+id_grn_asset);
//			rs=ps.executeQuery();
//			if(rs.next())
//			{
//				max = rs.getString(1);
//				model = rs.getString(2);
//            }			
				
			String sql="";
			
			StringTokenizer st = new StringTokenizer(sapNo , ",,");
			StringTokenizer st1 = new StringTokenizer(SerialVal , ",,");
			StringTokenizer st2 = new StringTokenizer(appNo , ",,");
		 
//			StringTokenizer mdl_num1 = new StringTokenizer(mdl_num , ",,");
//			StringTokenizer process_typ1 = new StringTokenizer(process_typ , ",,");
//			StringTokenizer storeage_typ1 = new StringTokenizer(storeage_typ , ",,");
//			StringTokenizer ram_typ1 = new StringTokenizer(ram_typ , ",,");
			
			while (st.hasMoreElements()) 
			{
				System.out.println("select  isnull(max(CAST(substring(id_wh_dyn,patindex('%-%', id_wh_dyn)+1,100)+1 AS INTEGER)),1) "+
						"from A_Ware_House where substring(id_wh_dyn,1,patindex('%-%', id_wh_dyn)) = upper('"+preFix+"') ");
				
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
									
				
				//String query="insert into A_Ware_House("+colName+",no_mfr , id_wh_dyn,add_by,serial_no,appNo,st_config,no_model,mdl_num,process_typ,storeage_typ,ram_typ) values("+value+" ,'"+st.nextElement()+"' , '"+id_wh_dyn+"',"+id_emp_user+",'"+st1.nextElement()+"','"+st2.nextElement()+"','"+max+"','"+model+"','"+mdl_num1.nextElement()+"','"+process_typ1.nextElement()+"','"+storeage_typ1.nextElement()+"','"+ram_typ1.nextElement()+"')";
				String query="insert into A_Ware_House("+colName+",no_mfr , id_wh_dyn,add_by,serial_no,appNo) values("+value+" ,'"+st2.nextElement()+"' , '"+id_wh_dyn+"',"+id_emp_user+",'"+st1.nextElement()+"','"+st.nextElement()+"')";
					
				System.out.println(query);
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				System.out.println("J:"+j);
			}
			if(j > 0)
			{
				
				
				j=0;
				String query ="update  A_Invoice set status_store = 1  where id_inv ="+id_grn_asset+"";
				System.out.println(query);
				PreparedStatement ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j>0)
				{
					query ="update A_Invoice_Master set status_store = 1 where id_inv_m ="+id_grn+"";
					System.out.println(query);
					 ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					j=1;
					/*query="select id_grn from A_Invoice where status_store = 0 and id_grn="+id_grn+"";
					 ps=con.prepareStatement(query);
					 rs=ps.executeQuery();
					 if(!rs.next())
					 {
						 
						 query ="update A_Invoice_Master set status_store = 1 where id_grn ="+id_grn+"";
						 ps=con.prepareStatement(query);
						j=ps.executeUpdate();
						j=1;
							 
					 }*/
				}
			}
			json.put("data",j);
	}
	catch(Exception e)
	{
		System.out.println("SAVE:"+e.toString());
	}
		return json;
}


public JSONObject DisplayInvoiceForAddToStore( String temp , String id_dept , String UserType , String tempQuery, String searchWord,HttpServletRequest request)
{
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	String tempSql="";
	DtoCommon dtoCommon = new DtoCommon();
	if(!UserType.equals("Super"))
	{
    	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"ga","g", request);
    	
    		tempSql="select d.nm_dept,g.*,ga.*,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as " + 
					" dtgrn,m.nm_model,v.nm_ven  from a_invoice_master g,a_invoice ga,M_Model m,M_Vendor v,M_Dept d where d.id_dept=g.id_dept and g.id_inv_m=ga.id_inv_m and ga.id_model=m.id_model " + 
					"  and g.id_ven=v.id_ven  and ga.status_store=0  "+tempQuery2+"   order by g.dt_inv ";
    }
	else {
		tempSql="select d.nm_dept,g.*,ga.*,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as " + 
				" dtgrn,m.nm_model,v.nm_ven  from a_invoice_master g,a_invoice ga,M_Model m,M_Vendor v,M_Dept d where d.id_dept=g.id_dept and g.id_inv_m=ga.id_inv_m and ga.id_model=m.id_model " + 
				"  and g.id_ven=v.id_ven  and ga.status_store=0     order by g.dt_inv ";
	}
	System.out.println("ADDTOSTORE:"+tempSql);	
	try
	{
	ps=con.prepareStatement(tempSql);
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
	    		
	    	/*	
	    		sql ="select SUM(qty_recv) as qty_recv from G_Grn where id_grn_asset = "+rs.getInt("id_grn_asset")+" and status_approv != 2";
				
	    		System.out.println(sql);
	    		int TotRecvQty = 0;
				int TotQty = rs.getInt("qty");
				ps1=con.prepareStatement(sql);
				rs1=ps1.executeQuery();
				 if(rs1.next())
				 	{
					 	TotRecvQty = rs1.getInt(1);
					 	
					 	
				 	}
				 
				 
				 jobj2.put("ReminQty",(TotQty - TotRecvQty));*/
				 
				 jarr.put(jobj2);
        }
	    
	      
	    
	    jobj.put("data", jarr);
	}
	catch(Exception e)
	{
		System.out.println("sql error in G_Create_Grn.");
	}
	 
	return jobj;
	
	
}

}

