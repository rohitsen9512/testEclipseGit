package com.Tagging;

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


public class T_Tagg_Print extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	Date currDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		
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
		    
		String action = "",searchWord="",id_wh="",id="",bulkinstallAssetDate="",to_assign="",id_grp="",id_sgrp="",no_inv="",value="";
		String order = "";
		
		int j=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		
		if(request.getParameter("id") !=null)
		{
			id = request.getParameter("id");
		}
		if(request.getParameter("id_wh") !=null)
		{
			id_wh = request.getParameter("id_wh");
		}
		if(request.getParameter("id_grp") !=null)
		{
			id_grp = request.getParameter("id_grp");
		}
		if(request.getParameter("id_sgrp") !=null)
		{
			id_sgrp = request.getParameter("id_sgrp");
		}
		if(request.getParameter("no_inv") !=null)
		{
			no_inv = request.getParameter("no_inv");
		}
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}
		if(request.getParameter("order") !=null)
		{
			order = request.getParameter("order");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		String UserType = (String)session.getAttribute("UserType"); 
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Display":
	            	jobj = DisplayAssetForTaggPrint(id_grp,id_sgrp,searchWord,UserType,request);	
	                break;
	                
	            case "DataForTagPrint":
	            	jobj = DataForTagPrint(id_wh);	
	                break;
	                
	            case "Display1":
	            	jobj = DisplayAssetForTaggPrintByInvoice(no_inv);	
	                break;
	            case "updatetag":
				
				jobj = UpdateTagAtt(id,order,value);	
	                break;
	            case "previewtag":
					
					jobj = TagPrint();	
		                break;
	            case "DeleteTagOrder":
					jobj = DeleteTagOrder(id);
					break;        
	            
	           
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in T_Tagg_Print.");
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
	
	public JSONObject DeleteTagOrder(String id) {
		JSONObject json = new JSONObject();
		int j = 0;
		try {

			String query = "delete from Tag_Attributes where id = " + id + "";
			System.out.println(query);
			ps = con.prepareStatement(query);
			j = ps.executeUpdate();
			j = 1;

			json.put("data", j);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return json;
	}
	


	public JSONObject TagPrint() {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		sql="select column_label,id,column_order,column_name from Tag_Attributes order by column_order";
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
			System.out.println("sql error in T_Tagg_Print.");
		}
		 
		return jobj;
	}





	public JSONObject UpdateTagAtt(String id,String order, String value) {
JSONObject jobj = new JSONObject();
JSONObject json=new JSONObject();
int j=0;
		JSONArray jarr = new JSONArray();
		String sql="";
		try
		{
			sql="select * from Tag_Attributes ";
			PreparedStatement ps3=con.prepareStatement(sql);
			rs=ps3.executeQuery();
			
				
				if(!rs.next())
				{
					sql="insert into Tag_Attributes (column_name, column_order, tag, column_label ) values('"+id+"','"+order+"','1','"+value+"')";
				
				System.out.println(sql);
				try 
				{
					PreparedStatement ps1=con.prepareStatement(sql);
					j=ps1.executeUpdate();
					j=1;
				
					json.put("data",j);
					
				}
				catch (Exception e)
				{
					
					e.printStackTrace();
				}
			}
				else
				{
					sql="select * from Tag_Attributes where column_name='"+id+"'";
					
					PreparedStatement ps=con.prepareStatement(sql);
					rs=ps.executeQuery();
					if(!rs.next())
					{
						sql="select column_order from Tag_Attributes where column_order='"+order+"'";
						
							PreparedStatement ps2=con.prepareStatement(sql);
							rs=ps2.executeQuery();
							System.out.println(sql);
							if(!rs.next())
							{
								
							
					 sql="insert into Tag_Attributes (column_name, column_order, tag, column_label ) values('"+id+"','"+order+"','1','"+value+"')";
					System.out.println(sql);
					try 
					{
						PreparedStatement ps1=con.prepareStatement(sql);
						j=ps1.executeUpdate();
						j=1;
					
						json.put("data",j);
						
					}
					catch (Exception e)
					{
						
						e.printStackTrace();
					}
								}
					
							
							else
							{
								json.put("data",j);
							}
							
							
				}
					
					
					else
					{
						sql="select * from Tag_Attributes where column_order='"+order+"'";
						
						PreparedStatement ps4=con.prepareStatement(sql);
						rs=ps4.executeQuery();
						if(rs.next())
						{
							sql="update Tag_Attributes set column_order=(select column_order from Tag_Attributes where column_name='"+id+"') where id='"+rs.getInt(1)+"'";
							
							PreparedStatement ps5=con.prepareStatement(sql);
							j=ps5.executeUpdate();
								System.out.println(sql);
								if(j>0) {
					 sql="update Tag_Attributes set column_order='"+order+"',tag=1,column_label='"+value+"' where column_name='"+id+"'";
					System.out.println(sql);
					try 
					{
						PreparedStatement ps1=con.prepareStatement(sql);
						j=ps1.executeUpdate();
						j=1;
					
						json.put("data",j);
						
					}
					catch (Exception e)
					{
						
						e.printStackTrace();
					}
								}
								}
					
							
							else
							{
								json.put("data",j);
							}
							}
							
				}
					
					
				
		
		
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
		
	}


	public JSONObject DataForTagPrint(String id_wh)
	{
		JSONObject jobj = new JSONObject();
		
		JSONArray jarr1 = new JSONArray();
		String sql="";
		try
		{
		StringTokenizer st = new StringTokenizer(id_wh , "Patel");
		while (st.hasMoreElements()) 
		{
			sql="select id_wh,nm_com,no_mfr,id_wh_dyn from A_Ware_House hr,(select nm_com from M_Company) comp  where id_wh ="+st.nextElement()+"";
			
			
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			   ResultSetMetaData metaData = rs.getMetaData();
			    int columns = metaData.getColumnCount();
			    JSONArray jarr = new JSONArray();
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
			    jarr1.put(jarr);
		}
		jobj.put("data", jarr1);
		}
		catch(Exception e)
		{
			System.out.println("sql error in T_Tagg_Print.");
		}
		 
		return jobj;
	}

	
	
	public JSONObject DisplayAssetForTaggPrint(String id_grp,String id_sgrp, String searchWord,String UserType,HttpServletRequest request)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		DtoCommon dtoCommon = new DtoCommon();
		String sql="";
		if(!UserType.equals("Super"))
		{
			String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
		
		sql="select id_wh,id_wh_dyn,appNo,no_model,ds_asst,ds_pro,no_mfr,no_inv,serial_no,nm_s_assetdiv from A_Ware_House wh,M_Subasset_Div sd where tag ='Yes' and sd.id_s_assetdiv=wh.id_sgrp and wh.id_grp = "+id_grp+" and wh.id_sgrp = "+id_sgrp+" and (id_wh_dyn like '"+searchWord+"%' or no_model like '"+searchWord+"%' or no_mfr like '"+searchWord+"%' or serial_no like '"+searchWord+"%'  ) "+tempQuery2+" ";
		}
		else {
			sql="select id_wh,id_wh_dyn,appNo,no_model,ds_asst,ds_pro,no_mfr,no_inv,serial_no,nm_s_assetdiv from A_Ware_House wh,M_Subasset_Div sd where tag ='Yes' and sd.id_s_assetdiv=wh.id_sgrp and wh.id_grp = "+id_grp+" and wh.id_sgrp = "+id_sgrp+" and (id_wh_dyn like '"+searchWord+"%' or no_model like '"+searchWord+"%' or no_mfr like '"+searchWord+"%' or serial_no like '"+searchWord+"%'  )  ";
			
		}
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
			System.out.println("sql error in T_Tagg_Print.");
		}
		 
		return jobj;
	}
	
	public JSONObject DisplayAssetForTaggPrintByInvoice(String no_inv)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		
		sql="select id_wh,id_wh_dyn,no_model,ds_asst,ds_pro,no_mfr,no_inv from A_Ware_House wh where tag ='Yes' and wh.no_inv = '"+no_inv+"'";
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
			System.out.println("sql error in T_Tagg_Print.");
		}
		 
		return jobj;
	}
}