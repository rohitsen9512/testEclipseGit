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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.DtoCommon;


public class temp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	PreparedStatement ps1=null;
	Connection con=null;
	Connection con1=null;
	Statement stmt=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		
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
		 
		String action = "",id="0",id_inv_m="",dt_to="",dt_frm="",value="",ColName="",id_inv="",invType="",dt_grn="";
		
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("dt_grn") !=null)
		{
			dt_grn = request.getParameter("dt_grn");
		}
		if(request.getParameter("id_inv_m") !=null)
		{
			id_inv_m = request.getParameter("id_inv_m");
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
		if(request.getParameter("id_inv") !=null)
		{
			id_inv = request.getParameter("id_inv");
		}
		if(request.getParameter("invType") !=null)
		{
			invType = request.getParameter("invType");
		}
		try
		{
			Date currDate = new Date();
			if(!dt_grn.equals(""))
			{
				dt_grn = dateFormatNeeded.format(userDateFormat.parse(dt_grn));
			}
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
			
			con=Common.GetConnection(request);
			con1=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddGRN(map,id_inv_m,id_inv,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayInvoiceForAddToStore(temp);	
	                break;
	           
	            case "DisplayGrn":
	            	jobj = DisplayGrnForParticularInvoice(id_inv);	
	                break;
	                
	            case "Edit":
	            	jobj = InvoiceDataForGrnEdit(id , id_inv_m,id_inv);	
	                break;
	                
	            case "CountQty":
	            	jobj = CountQty(id_inv);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateGrn(map,id,  request);	
	                break;
	            case "CheckDate":
	            	jobj = CheckDate(dt_grn,id_inv_m);	
	                break;
	              
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in G_Create_Grn.");
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
	
	public JSONObject CheckDate(String dt_grn , String id_inv_m)
	{
		JSONObject jobj = new JSONObject();
		
		String  sql ="select (replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dt_inv from A_Invoice_Master where id_inv_m = "+id_inv_m+" and dt_inv > '"+dt_grn+"' ";
			
			 
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		    if(rs.next())
		    {
		    	jobj.put("data", 0);
		    	jobj.put("dt_inv", rs.getString(1));
	        }
		    else
		    {
		    	jobj.put("data", 1);
		    }
		    
		}
		catch(Exception e)
		{
			System.out.println("sql error in G_Create_Grn." +e);
		}
		 
		return jobj;
		
		
	}
	
	
	public JSONObject UpdateGrn(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("G_Grn",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_grn")
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
					System.out.println("Some error in country master.");
				}
		
		String query="update G_Grn set "+col_Val+" where id_grn="+id+"";
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
	
	
	
	public JSONObject CountQty(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String  sql ="select SUM(qty_recv) from G_Grn where id_inv = "+id+" and status_approv !=2";
			
			 
		try
		{
		ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		    while(rs.next())
		    {
		    	JSONObject jobj2 = new JSONObject();
		    	
		    		jobj2.put("TotQty",rs.getInt(1));
		    	
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
	
	
	
	public JSONObject InvoiceDataForGrnEdit(String id , String id_inv_m,String id_inv)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="",Grn_No="";
		int tempGrnId=0;
		try
		{
		if(id.equals(""))
		{
			
			ps=con.prepareStatement("select max(id_grn) from G_Grn ");
			rs1=ps.executeQuery();
			
				if(rs1.next())
				{
					tempGrnId =	rs1.getInt(1) +1;
					if(tempGrnId == 1)
					{
						tempGrnId =1;
					}
				}
				
				Grn_No	= "GRN-"+tempGrnId;
			
			 sql="select no_inv,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,nm_ven,inv_m.id_ven,no_po,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,typ_po from A_Invoice_Master inv_m , M_Vendor v,A_Invoice inv where "+
			 " inv_m.id_inv_m = "+id_inv_m+" and inv_m.id_ven=v.id_ven and inv.id_inv_m=inv_m.id_inv_m and inv.id_inv="+id_inv+"";
		}
		else
		{
			sql="select g.*,(replace(convert(NVARCHAR, dt_dc, 103), ' ', '-')) as dtDc,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dtPo,nm_ven from G_Grn g , M_Vendor v where g.id_ven=v.id_ven and  id_grn = "+id+"";
			
		}
		
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
		    if(id.equals(""))
		    {
			    jobj2.put("no_grn",Grn_No);
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
	
	
	public JSONObject DisplayGrnForParticularInvoice( String id_inv)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		String  sql ="select *,(replace(convert(NVARCHAR, dt_grn, 103), ' ', '-')) as dtGrn,(replace(convert(NVARCHAR, dt_inv, 103), ' ', '-')) as dtInv from G_Grn where id_inv = "+id_inv+" and status_approv = 0";
			
			 
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
			System.out.println("sql error in G_Create_Grn.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject DisplayInvoiceForAddToStore( String temp)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(!temp.equals(""))
			sql = "select inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,nm_ven,qty,id_inv,nm_s_assetdiv,ds_pro from A_Invoice_Master inv_m ,A_Invoice ai , M_Vendor v ,M_Subasset_Div sgrp "+
					"where inv_m.id_ven=v.id_ven and ai.status_store=0 and ai.id_inv_m = inv_m.id_inv_m "+
					"and sgrp.id_s_assetdiv=ai.id_sgrp "+temp+" order by inv_m.dt_inv";
		else
			sql = "select inv_m.*,(replace(convert(NVARCHAR, inv_m.dt_inv, 103), ' ', '-')) as dtInv,nm_ven,qty,id_inv,nm_s_assetdiv,ds_pro from A_Invoice_Master inv_m ,A_Invoice ai , M_Vendor v ,M_Subasset_Div sgrp "+
			"where inv_m.id_ven=v.id_ven and ai.status_store=0 and ai.id_inv_m = inv_m.id_inv_m "+
			"and sgrp.id_s_assetdiv=ai.id_sgrp order by inv_m.dt_inv";
			/*sql ="select inv_m.*,nm_ven,qty,id_inv from A_Invoice_Master inv_m ,A_Invoice ai , M_Vendor v where inv_m.id_ven=v.id_ven and ai.rec_status_grn=0 and ai.id_inv_m = inv_m.id_inv_m and bd='No'";
	*/	
		
			
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
		    		
		    		
		    		sql ="select SUM(qty_recv) as qty_recv from G_Grn where id_inv = "+rs.getInt("id_inv")+" and status_approv != 2";
					int TotRecvQty = 0;
					int TotQty = rs.getInt("qty");
					ps1=con.prepareStatement(sql);
					rs1=ps1.executeQuery();
					 if(rs1.next())
					 	{
						 	TotRecvQty = rs1.getInt(1);
						 	
						 	
					 	}
					 
					 
					 jobj2.put("ReminQty",(TotQty - TotRecvQty));
					 
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
	
	public JSONObject AddGRN(HashMap<String, String> map,String id_inv_m , String id_inv,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0,qty_recv=0,TotQty=0;
		JSONObject json=new JSONObject();
		try
		{
		
		    
				rs = Common.GetColumns("G_Grn",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_grn")
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
							if(map.containsKey("qty"))
						    	TotQty = Integer.parseInt( map.get("qty"));
							if(map.containsKey("qty_recv"))
							qty_recv = Integer.parseInt( map.get("qty_recv"));
						}
				
		String  sql ="select SUM(qty_recv) from G_Grn where id_inv = "+id_inv+" and status_approv !=2";
		int TotRecvQty = 0,temp=0;
		PreparedStatement ps=con.prepareStatement(sql);
		rs=ps.executeQuery();
		 if(rs.next())
		 	{
			 	TotRecvQty = rs.getInt(1);
			 	
			 	TotRecvQty = TotRecvQty + qty_recv;
			 	if(TotRecvQty == TotQty)
			 	{
			 		sql ="update A_Invoice set rec_status_grn =1 where id_inv = "+id_inv+"";
			 		ps=con.prepareStatement(sql);
					j=ps.executeUpdate();
					temp =1;
					
			 	}
			 	sql ="update A_Invoice_Master set rec_status_grn =1 where id_inv_m = "+id_inv_m+"";
		 		ps=con.prepareStatement(sql);
				j=ps.executeUpdate();
				
			 	
		 	}
		
		String query="insert into G_Grn("+colName+",type_grn) values("+value+",'INV')";
	
			ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				DtoCommon dtcm = new DtoCommon();
				String replyMailId = "";
				String id_loc = "",typ_asst="";
				String id_sloc = "",no_inv="",no_grn="";
				no_grn = map.get("no_grn");
				
				rs = dtcm.GetResultSet("select id_loc,id_subl,typ_asst,no_inv from A_Invoice_Master invm,A_Invoice inv where invm.id_inv_m=inv.id_inv_m and  id_inv="+id_inv+" and invm.id_inv_m = "+id_inv_m+"",  request);
				if(rs.next())
				{
					id_loc = rs.getString(1);
					id_sloc = rs.getString(2);
					typ_asst = rs.getString(3);
					no_inv = rs.getString(4);
				}
				String mailSql = "select  e.id_emp from M_User_Login l,M_Emp_User e where e.id_emp_user=l.id_emp_user and "+
						 "type_user='"+typ_asst+"' and e.status_emp='Working' and l.status_user='1'";
				
					List<String> recipients = dtcm.ReturnListData(mailSql,  request);
					
				if(!recipients.isEmpty())
				{
					replyMailId = dtcm.ReturnReplyMailIdFromList(recipients);
					recipients.remove(0);
					
					String link = dtcm.ReturnParticularMessage("link");
					String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
					String mailSubject = dtcm.ReturnParticularMessage("GRNCreate");
					
					String mailBody = "<b>Hello Executive</b>,<br><br><br>"+
								  "The "+no_grn+" has been created against your invoice number "+no_inv+". Please approve the GRN.<br>"+
								  "<br><br><br>"+link+""+
								  "<p>"+footerMsg+"</p>";
				
					Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
				}
				
				j=1;
			}
		if(temp == 1)
		{
			j=2;
		}
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
	//	Common.MailConfiguration("FromUserID" ,"FromUserPwd" ,"ToUserID","MailBody");
		
		return json;
	}
	
	
}
