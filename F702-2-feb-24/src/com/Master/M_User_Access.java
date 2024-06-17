package com.Master;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.Common.Common;
import com.Util.dtoUtils;

import dto.Common.UserAccessData;



public class M_User_Access extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      paramValues=paramValues.replace("'", "''");
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0",value="",ColName="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		//String [] id_asst_ownr				=	request.getParameterValues("id_asst_ownr1");
		String [] id_design				=	request.getParameterValues("id_design1");
		String [] id_loc				=	request.getParameterValues("id_loc1");
		String [] id_subl				=	request.getParameterValues("id_subl1");
		String idDesign="",idLoc="",idSloc="";
		int len1=0,len2=0,len3=0;
//		try{
//			if(id_asst_ownr.length>0)
//				len=id_asst_ownr.length;
//		}catch(Exception e){
//			len=0;
//		}
		try{
			if(id_design.length>0)
				len1=id_design.length;
		}catch(Exception e){
			len1=0;
		}
		try{
			if(id_loc.length>0)
				len2=id_loc.length;
		}catch(Exception e){
			len2=0;
		}
		try{
			if(id_subl.length>0)
				len3=id_subl.length;
		}catch(Exception e){
			len3=0;
		}
		
//		if(len > 0){
//			for(int i=0;i<id_asst_ownr.length;i++)
//			{
//				if(asst_type.equals(""))
//					asst_type = id_asst_ownr[i];
//				else
//					asst_type +=","+ id_asst_ownr[i];
//			}
//		}
		if(len1 > 0){
			for(int i=0;i<id_design.length;i++)
			{
				if(idDesign.equals(""))
					idDesign = id_design[i];
				else
					idDesign +=","+ id_design[i];
			}
		}
		if(len2 > 0){
			for(int i=0;i<id_loc.length;i++)
			{
				if(idLoc.equals(""))
					idLoc = id_loc[i];
				else
					idLoc +=","+ id_loc[i];
			}
		}
		if(len3 > 0){
			for(int i=0;i<id_subl.length;i++)
			{
				if(idSloc.equals(""))
					idSloc = id_subl[i];
				else
					idSloc +=","+ id_subl[i];
			}
		}
		try
		{
			
		con=Common.GetConnection(request);
			
			switch (action)
	        {
	        case "Save":
            	
            	String colName="",value1="";
        		int j=0;
        		JSONObject json1=new JSONObject();
       		
        		try
        		{
        			
            		 
            		 rs = Common.GetColumns("M_User_Permission", request);
        				
        						
        						while (rs.next())
        						{
        						
        							if(!rs.getString(1).equals("id_permission"))
        							{
        								    if (map.containsKey(rs.getString(1)))
        								    {
        								    	if(colName.equals(""))
        								    	{
        									    	colName += rs.getString(1);
        									    	value1 += "'"+ map.get(rs.getString(1))+"'";
        								    	}
        								    	else
        								    	{
        								    		colName +=","+ rs.getString(1);
        									    	value1 +=", '"+ map.get(rs.getString(1))+"'";
        								    		
        								    	}
        								    }
        							  }
        						}
        				
        			}
        			catch(Exception e)
        				{
        					System.out.println(e);
        				}
       		
        		String query="insert into M_User_Permission("+colName+",id_design,id_loc,id_subl) values("+value1+",'"+idDesign+"','"+idLoc+"','"+idSloc+"')";
        		System.out.println(query);
        		PreparedStatement ps=con.prepareStatement(query);
        		ps.executeUpdate();
        		
        		try
        		{
        			UserAccess hdm = new UserAccess();
        	        hdm.doPost(request, response);
        	        
    				}
    			    catch(IOException e){
    			    System.out.println(e.getMessage());
    			    }
                 
//            	   try {
//       					UserAccess hdm = new UserAccess();
//            	        hdm.doPost(request, response);
//        				}
//        			    catch(IOException e){
//        			    System.out.println(e.getMessage());
//        			    }
        				
        		
                break;
	            case "Display":
	            	ServletContext servletContext = request.getSession().getServletContext();
					System.out.println(servletContext);
					String relativeWebPath1 = "";
					String strdirName1 = servletContext.getRealPath(relativeWebPath1);
					System.out.println(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties");  
					 FileInputStream fis = null;
				      Properties prop = null;
				      try {
				         fis = new FileInputStream(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties"); 
				         prop = new Properties();
				         prop.load(fis);
				         prop.list(System.out);
				         
				      }
	         catch(FileNotFoundException fnfe) {
		         fnfe.printStackTrace();
		      } 
	                break;
	                
	            case "Display2":
	            	String sql14="";
	            	sql14="select distinct type_user,id_design,id_loc,id_subl from M_User_Permission ORDER BY type_user ";
	        		System.out.println(sql14);	
	        		jobj=dtoUtils.GetDataForDisplayInJsonFormat(sql14, request);	
	                break;   
	                
	            case "Edit":
	            	jobj = DisplayDivision(id,request,response);	
	                break;
	                
	            case "Update":
	            	ServletContext servletContext1 = request.getSession().getServletContext();
	        		System.out.println(servletContext1);
	        		String relativeWebPath11 = "";
	        		String strdirName11 = servletContext1.getRealPath(relativeWebPath11);
	        		System.out.println(strdirName11);
	        		System.out.println(strdirName11+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties");  
	        		 FileInputStream fis11 = null;
	        		  Properties prop1 = null;

	        			try {
	        				
	        		        fis11 = new FileInputStream(strdirName11+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties"); 
	        		        prop1 = new Properties();
	        		        prop1.load(fis11);
	        		        //directpo="/"
	        		        String master="/",stock="/",order="/",jobwork="/",stocktransfer="/",report="/",tagging="/";
	        		        String[] sub_menu= {},sub_menu1={},sub_menu2={},sub_menu3={},sub_menu4={},sub_menu5={},sub_menu6={},sub_menu7={};
	        		          String key = request.getParameter("type_user");
	        		          String id_usertype = request.getParameter("id_usertype");
	        		        //Write File in  Main Module properties File...
	        		          if(request.getParameter("master") !=null)
	        			  		{
	        		        	  master = request.getParameter("master");
	        			  		}
	        		          if(request.getParameter("stock") !=null)
	        		  		{
	        		        	  stock = request.getParameter("stock");
	        		  		}
//	        		          if(request.getParameter("directpo") !=null)
//	        			  		{
//	        		        	  directpo = request.getParameter("directpo");
//	        			  		}
	        		          if(request.getParameter("order") !=null)
	        			  		{
	        		        	  order = request.getParameter("order");
	        			  		}
	        			        if(request.getParameter("jobwork") !=null)
	        				  		{
	        			        	jobwork = request.getParameter("jobwork");
	        				  		}
	        		          if(request.getParameter("stocktransfer") !=null)
	        			  		{
	        		        	  stocktransfer = request.getParameter("stocktransfer");
	        			  		}
	        		          if(request.getParameter("report") !=null)
	        			  		{
	        		        	  report = request.getParameter("report");
	        			  		}
	        		          if(request.getParameter("tagging1") !=null)
	        			  		{
	        		        	  tagging = request.getParameter("tagging1");
	        			  		}
	        		          if((!master.isEmpty()) && (!stock.isEmpty())  && (!order.isEmpty()) && (!jobwork.isEmpty()) && (!stocktransfer.isEmpty()) && (!report.isEmpty()) && (!tagging.isEmpty()))
	        		          {
	        		          String val = String.join(",", master,stock,order,jobwork,stocktransfer,report,tagging);
	        		          System.out.println("Key is" + key);
	        		          System.out.println("Val is" + val);
	        		          
	        		          try
	        		          {
	        		        	  query = "select User_Type from M_Main_Menu_Access where id_usertype='"+id_usertype+"' ";
	        		        		System.out.println(query);
	        		        	  	ps=con.prepareStatement(query);
	        						rs=ps.executeQuery();
	        						if(rs.next())
	        						{
	        							 query="Update M_Main_Menu_Access set access='"+val+"', User_Type='"+key+"' where id_usertype='"+id_usertype+"' ";
	        				        		System.out.println(query);
	        				        		 ps=con.prepareStatement(query);
	        				        		ps.executeUpdate();
	        							
	        						}
	        						else
	        						{
	        							 query="insert into M_Main_Menu_Access(User_Type,access,id_usertype) values('"+key+"','"+val+"','"+id_usertype+"')";
	        				        		System.out.println(query);
	        				        		 ps=con.prepareStatement(query);
	        				        		ps.executeUpdate();
	        						}
	        		          }
	        		          catch(Exception e)
	        		          {
	        		        	  System.out.println(e);
	        		          }
	        		          
	        		          
	        		          prop1.setProperty(key, val);
	        		        //  prop1.store(new FileOutputStream(strdirName11+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties"),null);
	        		          System.out.println("Operation completly successfuly for Main module!!");
	        		          
	        		        //Write File in Master-Sub-Module properties File...
	        		          ServletContext servletContext12 = request.getSession().getServletContext();
	        					System.out.println(servletContext12);
	        					String relativeWebPath = "";
	        					String strdirName = servletContext12.getRealPath(relativeWebPath);
	        					System.out.println(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties");  
	        					 FileInputStream fis12 = null;
	        					  Properties prop2 = null;
	        					  fis12 = new FileInputStream(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties"); 
	        				        prop2 = new Properties();
	        				        prop2.load(fis12);
	        				         
	        				          String key1 = request.getParameter("type_user");
	        				          if(request.getParameterValues("sub_menu") !=null)
	        					  		{
	        				        	   sub_menu = request.getParameterValues("sub_menu");
	        					  		}
	        				          if(request.getParameterValues("sub_menu1") !=null)
	        					  		{
	        				        	  sub_menu1 = request.getParameterValues("sub_menu1");
	        					  		}
//	        				          if(request.getParameterValues("sub_menu2") !=null)
//	        					  		{
//	        				        	  sub_menu2 = request.getParameterValues("sub_menu2");
//	        					  		}
	        				          if(request.getParameterValues("sub_menu3") !=null)
	        					  		{
	        				        	  sub_menu3 = request.getParameterValues("sub_menu3");
	        					  		}
	        				          if(request.getParameterValues("sub_menu4") !=null)
	        					  		{
	        				        	  sub_menu4 = request.getParameterValues("sub_menu4");
	        					  		}
	        				          if(request.getParameterValues("sub_menu5") !=null)
	        					  		{
	        				        	  sub_menu5 = request.getParameterValues("sub_menu5");
	        					  		}
	        				          if(request.getParameterValues("sub_menu6") !=null)
	        					  		{
	        				        	  sub_menu6 = request.getParameterValues("sub_menu6");
	        					  		}
	        				          if(request.getParameterValues("sub_menu7") !=null)
	        					  		{
	        				        	  sub_menu7 = request.getParameterValues("sub_menu7");
	        					  		}
	        				          List list = new ArrayList(Arrays.asList(sub_menu)); //returns a list view of an array  
	        					      //returns a list view of str2 and adds all elements of str2 into list  
	        					      list.addAll(Arrays.asList(sub_menu1));  
	        					     // list.addAll(Arrays.asList(sub_menu2));     
	        					      list.addAll(Arrays.asList(sub_menu3));     
	        					      list.addAll(Arrays.asList(sub_menu4)); 
	        					      list.addAll(Arrays.asList(sub_menu5));     
	        					      list.addAll(Arrays.asList(sub_menu6));     
	        					      list.addAll(Arrays.asList(sub_menu7));     
	        					      Object[] result1 = list.toArray();  
	        					      String[] result = Arrays.copyOf(result1, result1.length, String[].class);  
	        				          String val1 = String.join(",", result);
	        				         
	        				          System.out.println("Key1 is" + key1);
	        				          System.out.println("Val1 is" + val1);
	        				          try
	        				          {
	        				        	   query = "select User_Type from M_Sub_Menu_Access where id_usertype='"+id_usertype+"' ";
	        				        		System.out.println(query);
	        				        	  	ps=con.prepareStatement(query);
	        								rs=ps.executeQuery();
	        								if(rs.next())
	        								{
	        									 query="Update M_Sub_Menu_Access set access='"+val1+"', User_Type='"+key1+"' where id_usertype='"+id_usertype+"' ";
	        						        		System.out.println(query);
	        						        		 ps=con.prepareStatement(query);
	        						        		ps.executeUpdate();
	        									
	        								}
	        								else
	        								{
	        									 query="insert into M_Sub_Menu_Access(User_Type,access,id_usertype) values('"+key1+"','"+val1+"','"+id_usertype+"')";
	        						        		System.out.println(query);
	        						        		 ps=con.prepareStatement(query);
	        						        		ps.executeUpdate();
	        								}
	        								query="update M_User_Permission set id_design='"+idDesign+"',id_loc='"+idLoc+"',id_subl='"+idSloc+"' where id_usertype='"+id_usertype+"'";
    						        		System.out.println(query);
    						        		 ps=con.prepareStatement(query);
    						        		ps.executeUpdate();
	        				          }
	        				          catch(Exception e)
	        				          {
	        				        	  System.out.println(e);
	        				          }
	        				          
	        				          prop2.setProperty(key1, val1);
	        				          //prop2.store(new FileOutputStream(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties"),null);
	        				          System.out.println("Operation completly successfuly for Master Sub-module!!!");
	        				         
	        		        }
	        		          /* 
	        		        //Write File in Payroll Main Module properties File...
	        		           menu = request.getParameter("payroll");
	        		          if(!menu.isEmpty())
	        		          {
	        		          String val = String.join(",", menu);
	        		          System.out.println("Val is" + val);
	        		          prop1.setProperty(key, val);
	        		          prop1.store(new FileOutputStream(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties"),null);
	        		          System.out.println("Operation completly successfuly for payroll-Main module!!");
	        		          
	        		        
	        		          //Write File in Payroll-Sub-Module properties File...
	        		          ServletContext servletContext = request.getSession().getServletContext();
	        					System.out.println(servletContext);
	        					String relativeWebPath = "";
	        					String strdirName = servletContext.getRealPath(relativeWebPath);
	        					System.out.println(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties");  
	        					 FileInputStream fis1 = null;
	        					  Properties prop2 = null;
	        					  fis1 = new FileInputStream(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties"); 
	        				        prop2 = new Properties();
	        				        prop2.load(fis1);
	        				         
	        				          String key1 = request.getParameter("type_user");
	        				          String[] name1 = request.getParameterValues("sub_menu_payroll");
	        				          String val1 = String.join(",", name1);

	        				          prop2.setProperty(key1, val1);
	        				          prop2.store(new FileOutputStream(strdirName+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties"),null);
	        				          System.out.println("Operation completly successfuly for  payroll-Sub-module!!!");*/
	        		        }  

	        		    catch(IOException e){
	        		    System.out.println(e.getMessage());
	        		    }
	        			
	        			

	        				
	        				
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteDivision(id);	
	                break; 
	                
	            case "DropDownResult":
	            	jobj = DropDownResult();	
	                break; 
	                
//	            case "CheckExitsVal":
//	            	jobj = CheckExitsVal(value,ColName);	
//	                break;
		            case "CheckExitsValues":
	            	jobj = CheckExitsValues(value,ColName);	
	                break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in M_User_access."+e);
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
	
	
	
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_usertype from M_User_Type where "+ColName+" = '"+value+"'";
		try 
		{
			
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("data",1);
			}
			else
			{
				json.put("data",0);
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject CheckExitsValues(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_usertype from M_User_Permission where "+ColName+" = '"+value+"'";
		try 
		{
			System.out.println(query);
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next())
			{
				json.put("data",2);
			}
			else
			{
				json.put("data",0);
			}
		}
			
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject DropDownResult()
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select id_div,nm_div from M_Company_Division" ;
		
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
			System.out.println(e+"sql error in M_User_Access master.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	public JSONObject DeleteDivision(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		String query = "delete  M_User_Permission where type_user= '"+id+"'";
		System.out.println(query);
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				query = "delete  M_User_Login where type_user= '"+id+"'";
				System.out.println(query);
				PreparedStatement ps1=con.prepareStatement(query);
				j=ps1.executeUpdate();
				if(j > 0) {
					j=1;
				}
				else {
					j=1;
				}
			}
		
			json.put("data",j);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject UpdateDivision(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Company_Division", request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_div")
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
					System.out.println("Some error in M_Division.");
				}
		
		String query="update M_Company_Division set "+col_Val+" where id_div="+id+"";
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
	
	public JSONObject DisplayDivision(String id,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException
	{
		JSONObject jobj=new JSONObject();
		JSONArray jarrsubusertype = new JSONArray();
		JSONArray jarrsubusertype3 = new JSONArray();
		JSONArray jarrmasterdata = new JSONArray();
		JSONArray jarrastock = new JSONArray();
		//JSONArray jarrdirectpo = new JSONArray();
		JSONArray jarrorder = new JSONArray();
		JSONArray jarrjobwork = new JSONArray();
		JSONArray jarrstocktransfer = new JSONArray();
		JSONArray jarrreport = new JSONArray();
		JSONArray jarrtagging1 = new JSONArray();
	    JSONArray jarr = new JSONArray();
		ServletContext servletContext1 = request.getSession().getServletContext();
		System.out.println(servletContext1);
		String relativeWebPath1 = "";
		String strdirName1 = servletContext1.getRealPath(relativeWebPath1);
		System.out.println(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\financeAccess.properties");  
		 FileInputStream fis = null;
		 FileInputStream fis1 = null;
		 FileInputStream fis2 = null;
		  Properties prop1 = null;
		  Properties prop2 = null;
		  Properties prop3 = null;

			try {
				String sql="select id_usertype,nm_usertype from M_User_Type where nm_usertype ='"+id+"'";
				System.out.println(sql);
				try {
					ps=con.prepareStatement(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				
		        fis = new FileInputStream(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\financeAccess.properties"); 
		        prop1 = new Properties();
		        prop1.load(fis);
		        
		        String[] master1 = prop1.getProperty("master").split(",");
		        
		        String[] stock1 = prop1.getProperty("stock").split(",");
		        System.out.println(stock1);
		       // String[] directpo1 = prop1.getProperty("DirectPO").split(",");
		       // System.out.println(directpo1);
		        String[] order1 = prop1.getProperty("Lead").split(",");
		        System.out.println(order1);
		        String[] jobwork1 = prop1.getProperty("jobworkmaster").split(",");
		        System.out.println(jobwork1);
		        String[] stocktransfer1 = prop1.getProperty("stocktransfermaster").split(",");
		        System.out.println(stock1);
		        String[] report1 = prop1.getProperty("report").split(",");
		        System.out.println(report1);
		        String[] tagging11 = prop1.getProperty("tagging1").split(",");
		        
		        
		        List list = new ArrayList(Arrays.asList(master1)); //returns a list view of an array  
		      //returns a list view of str2 and adds all elements of str2 into list  
		      list.addAll(Arrays.asList(stock1));  
		     // list.addAll(Arrays.asList(directpo1));     
		      list.addAll(Arrays.asList(order1));     
		      list.addAll(Arrays.asList(jobwork1)); 
		      list.addAll(Arrays.asList(stocktransfer1));     
		      list.addAll(Arrays.asList(report1));     
		      list.addAll(Arrays.asList(tagging11));     
		      Object[] result1 = list.toArray();  
		      String[] result = Arrays.copyOf(result1, result1.length, String[].class);  

//		      InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/mainMenuUserAccess.properties");
//		        prop2 = new Properties();
//		        prop2.load(inputStream);
//		        String[] menu1 = prop2.get(""+id+"").toString().split("[,/]+");
		        
		        // From Databse
		      
		      String access="";
		        String query = "select access from M_Main_Menu_Access where User_Type='"+id+"' ";
      		System.out.println(query);
      	  	ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					 access=rs.getString(1);
					 System.out.println("access is " + access);
				}
				
				 String[] menu1 = access.split(",");
		     
		        System.out.println("Menu is " + menu1);
		        
		        ArrayList<String> menu2 = new ArrayList<String>();
		        JSONObject jobjsubusertype3 = new JSONObject();
		        for (int i = 0; i < menu1.length; i = i+1) {  
		        	jobjsubusertype3 = new JSONObject();
		        	menu2.add(menu1[i]);
		        	jobjsubusertype3.put("usermenu",menu1[i]);
		        	jarrsubusertype3.put(jobjsubusertype3);
		        }    
		        System.out.println(menu2);
		        

//		        InputStream inputStream2 = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/subMenuUserAccess.properties");
//		        prop3 = new Properties();
//		        prop3.load(inputStream2);
//		        String[] submenu1 = prop3.get(""+id+"").toString().split("[,/]+");
		        
		        // From Database
		        
		        String access1="";
		         query = "select access from M_Sub_Menu_Access where User_Type='"+id+"' ";
       		System.out.println(query);
       	  	ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				if(rs.next())
				{
					
					 access1=rs.getString(1);
					 System.out.println("access1 is " + access1);
				}
				
				 String[] submenu1 = access1.split(",");
				 
				 
		        ArrayList<String> submenu3 = new ArrayList<String>();
		        for (int i = 0; i < submenu1.length; i = i+1) {    
		        	submenu3.add(submenu1[i]);
		        }    
		        System.out.println(submenu3);
		        String[] submenu2 = submenu3.toArray(new String[0]);
		        System.out.println("test"+submenu2);

		        JSONObject jobjsubusertype = new JSONObject();
    			
		        for (int j=0; j<submenu2.length;j++) {
		        	jobjsubusertype = new JSONObject();
		        	 for (int i=0; i<master1.length;i++) {
		        		 
		        			String splitarr []=master1[i].split("-");
		        		 
		        			if(splitarr[0].equals(submenu2[j])) {
		        				
		        				System.out.println("classname"+splitarr[1]);
		        				jobjsubusertype.put("userclass",splitarr[0]);
		    					jobjsubusertype.put("username",splitarr[1]);
		    					
		    					
		        			}
		        			
		        	 }
					
		        	 
		        	 jarrsubusertype.put(jobjsubusertype);

			}
JSONObject stock = new JSONObject();
    			
		        for (int j=0; j<submenu2.length;j++) {
		        	stock = new JSONObject();
		        	 for (int i=0; i<stock1.length;i++) {
		        		 
		        			String splitarr []=stock1[i].split("-");
		        		 
		        			if(splitarr[0].equals(submenu2[j])) {
		        				
		        				System.out.println("classname1"+splitarr[1]);
		        				stock.put("userclass",splitarr[0]);
		        				stock.put("username",splitarr[1]);
		    					
		    					
		        			}
		        			
		        	 }
					
		        	 
		        	 jarrastock.put(stock);

			}
//JSONObject directpo = new JSONObject();
//    			
//		        for (int j=0; j<submenu2.length;j++) {
//		        	directpo = new JSONObject();
//		        	 for (int i=0; i<directpo1.length;i++) {
//		        		 
//		        			String splitarr []=directpo1[i].split("-");
//		        		 
//		        			if(splitarr[0].equals(submenu2[j])) {
//		        				
//		        				System.out.println("classname2"+splitarr[1]);
//		        				directpo.put("userclass",splitarr[0]);
//		        				directpo.put("username",splitarr[1]);
//		    					
//		    					
//		        			}
//		        			
//		        	 }
//					
//		        	 
//		        	 jarrdirectpo.put(directpo);
//
//			}
JSONObject order = new JSONObject();
    			
		        for (int j=0; j<submenu2.length;j++) {
		        	order = new JSONObject();
		        	 for (int i=0; i<order1.length;i++) {
		        		 
		        			String splitarr []=order1[i].split("-");
		        		 
		        			if(splitarr[0].equals(submenu2[j])) {
		        				
		        				System.out.println("classname3"+splitarr[1]);
		        				order.put("userclass",splitarr[0]);
		        				order.put("username",splitarr[1]);
		    					
		    					
		        			}
		        			
		        	 }
					
		        	 
		        	 jarrorder.put(order);

			}
JSONObject jobwork = new JSONObject();
    			
		        for (int j=0; j<submenu2.length;j++) {
		        	jobwork = new JSONObject();
		        	 for (int i=0; i<jobwork1.length;i++) {
		        		 
		        			String splitarr []=jobwork1[i].split("-");
		        		 
		        			if(splitarr[0].equals(submenu2[j])) {
		        				
		        				System.out.println("classname4"+splitarr[1]);
		        				jobwork.put("userclass",splitarr[0]);
		        				jobwork.put("username",splitarr[1]);
		    					
		    					
		        			}
		        			
		        	 }
					
		        	 
		        	 jarrjobwork.put(jobwork);

			}
JSONObject stocktransfer = new JSONObject();
    			
		        for (int j=0; j<submenu2.length;j++) {
		        	stocktransfer = new JSONObject();
		        	 for (int i=0; i<stocktransfer1.length;i++) {
		        		 
		        			String splitarr []=stocktransfer1[i].split("-");
		        		 
		        			if(splitarr[0].equals(submenu2[j])) {
		        				
		        				System.out.println("classname5"+splitarr[1]);
		        				stocktransfer.put("userclass",splitarr[0]);
		        				stocktransfer.put("username",splitarr[1]);
		    					
		    					
		        			}
		        			
		        	 }
					
		        	 
		        	 jarrstocktransfer.put(stocktransfer);

			}
		        		        
		        
JSONObject report = new JSONObject();
    			
		        for (int j=0; j<submenu2.length;j++) {
		        	report = new JSONObject();
		        	 for (int i=0; i<report1.length;i++) {
		        		 
		        			String splitarr []=report1[i].split("-");
		        		 
		        			if(splitarr[0].equals(submenu2[j])) {
		        				
		        				System.out.println("classname6"+splitarr[1]);
		        				report.put("userclass",splitarr[0]);
		        				report.put("username",splitarr[1]);
		    					
		    					
		        			}
		        			
		        	 }
					
		        	 
		        	 jarrreport.put(report);

			}
JSONObject tagging1 = new JSONObject();
    			
		        for (int j=0; j<submenu2.length;j++) {
		        	tagging1 = new JSONObject();
		        	 for (int i=0; i<tagging11.length;i++) {
		        		 
		        			String splitarr []=tagging11[i].split("-");
		        		 
		        			if(splitarr[0].equals(submenu2[j])) {
		        				
		        				System.out.println("classname7"+splitarr[1]);
		        				tagging1.put("userclass",splitarr[0]);
		        				tagging1.put("username",splitarr[1]);
		    					
		    					
		        			}
		        			
		        	 }
					
		        	 
		        	 jarrtagging1.put(tagging1);

			}


		        jobj.put("data", jarrsubusertype);
		        jobj.put("data1", jarrsubusertype3);
		        jobj.put("stock", jarrastock);
		       // jobj.put("directpo", jarrdirectpo);
		        jobj.put("order", jarrorder);
		        jobj.put("jobwork", jarrjobwork);
		        jobj.put("stocktransfer", jarrstocktransfer);
		        jobj.put("report", jarrreport);
		        jobj.put("tagging1", jarrtagging1);
		        jobj.put("user", jarr);
			
			}
		    catch(IOException e){
		    System.out.println(e.getMessage());
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

				
			System.out.println(jobj);	 
			return jobj;
	}
	
	
	
//	public JSONObject DisplayDivision(String id,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//	{
//		ServletContext servletContext1 = request.getSession().getServletContext();
//		System.out.println(servletContext1);
//		String relativeWebPath1 = "";
//		String strdirName1 = servletContext1.getRealPath(relativeWebPath1);
//		System.out.println(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\financeAccess.properties");  
//		 FileInputStream fis = null;
//		 FileInputStream fis1 = null;
//		 FileInputStream fis2 = null;
//		  Properties prop1 = null;
//		  Properties prop2 = null;
//		  Properties prop3 = null;
//
//			try {
//				
//		        fis = new FileInputStream(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\financeAccess.properties"); 
//		        prop1 = new Properties();
//		        prop1.load(fis);
//		        String[] master2 = prop1.get("master").toString().split("[,-]+");
//		        ArrayList<String> master1 = new ArrayList<String>();
//		        for (int i = 0; i < master2.length; i = i+2) {    
//		            master1.add(master2[i]);
//		        }    
//		        System.out.println(master1);
//		        
//		        String[] client1 = prop1.get("client").toString().split("[,-]+");
//		        ArrayList<String> client2 = new ArrayList<String>();
//		        for (int i = 0; i < client1.length; i = i+2) {    
//		        	client2.add(client1[i]);
//		        }    
//		        System.out.println(client2);
//		        
//		        String[] helpdesk1 = prop1.get("helpdesk").toString().split("[,-]+");
//		        ArrayList<String> helpdesk2 = new ArrayList<String>();
//		        for (int i = 0; i < helpdesk1.length; i = i+2) {    
//		        	helpdesk2.add(helpdesk1[i]);
//		        }    
//		        System.out.println(helpdesk2);
//		        
//		        String[] report1 = prop1.get("report").toString().split("[,-]+");
//		        ArrayList<String> report2 = new ArrayList<String>();
//		        for (int i = 0; i < report1.length; i = i+2) {    
//		        	report2.add(report1[i]);
//		        }    
//		        System.out.println(report2);
//		        
//		        String[] dashboard1 = prop1.get("dashboard").toString().split("[,-]+");
//		        ArrayList<String> dashboard2 = new ArrayList<String>();
//		        for (int i = 0; i < dashboard1.length; i = i+2) {    
//		        	dashboard2.add(dashboard1[i]);
//		        }    
//		        System.out.println(dashboard2);
//
//		        
//		        fis1 = new FileInputStream(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\mainMenuUserAccess.properties"); 
//		        prop2 = new Properties();
//		        prop2.load(fis1);
//		        String[] menu1 = prop2.get(""+id+"").toString().split("[,/]+");
//		        ArrayList<String> menu2 = new ArrayList<String>();
//		        for (int i = 0; i < menu1.length; i = i+1) {    
//		        	menu2.add(menu1[i]);
//		        }    
//		        System.out.println(menu2);
//		        
//		        fis2 = new FileInputStream(strdirName1+"\\WEB-INF\\classes\\com\\Resources\\subMenuUserAccess.properties"); 
//		        prop3 = new Properties();
//		        prop3.load(fis2);
//		        String[] submenu1 = prop3.get(""+id+"").toString().split("[,/]+");
//		        ArrayList<String> submenu2 = new ArrayList<String>();
//		        for (int i = 0; i < submenu1.length; i = i+1) {    
//		        	submenu2.add(submenu1[i]);
//		        }    
//		        System.out.println(submenu2);
//		       
//		        } 
//			
//
//		    catch(IOException e){
//		    System.out.println(e.getMessage());
//		    }
//			
//			
//
//				
//				response.sendRedirect("home.jsp");
//				return null;
//	}
	
	

	static <T> T concatWithCopy2(T master1, T client1,T helpdesk1, T report1,T dashboard1) {
	    if (!master1.getClass().isArray() || !client1.getClass().isArray()) {
	        throw new IllegalArgumentException("Only arrays are accepted.");
	    }

	    Class<?> compType1 = master1.getClass().getComponentType();
	    Class<?> compType2 = client1.getClass().getComponentType();

	    if (!compType1.equals(compType2)) {
	        throw new IllegalArgumentException("Two arrays have different types.");
	    }

	    int len1 = Array.getLength(master1);
	    int len2 = Array.getLength(client1);
	    int len3 = Array.getLength(helpdesk1);
	    int len4 = Array.getLength(report1);
	    int len5 = Array.getLength(dashboard1);

	    @SuppressWarnings("unchecked")
	    //the cast is safe due to the previous checks
	    T result = (T) Array.newInstance(compType1, len1 + len2 + len3 + len4 +len5);

	    System.arraycopy(master1, 0, result, 0, len1);
	    System.arraycopy(client1, 0, result, len1, len2);
	    System.arraycopy(helpdesk1, 0, result, len2, len3);
	    System.arraycopy(report1, 0, result, len3, len4);
	    System.arraycopy(dashboard1, 0, result,  len4, len5);

	    return result;
	}

	private String[] concatTwoStringArrays(String[] master1, String[] client1, String[] helpdesk1, String[] report1,
			String[] dashboard1) {
		String[] result = new String[master1.length+client1.length+helpdesk1.length+report1.length+dashboard1.length];
	    int i;
	    for (i=0; i<master1.length; i++)
	        result[i] = master1[i];
	    int tempIndex =master1.length; 
	    for (i=0; i<client1.length; i++)
	        result[tempIndex+i] = client1[i];
	     tempIndex +=tempIndex;
	    for (i=0; i<tempIndex; i++)
	        result[tempIndex+i] = helpdesk1[i];
	     tempIndex +=tempIndex;
	    for (i=0; i<tempIndex; i++)
	        result[tempIndex+i] = report1[i];
	     tempIndex+=tempIndex;
	    for (i=0; i<tempIndex; i++)
	        result[tempIndex+i] = dashboard1[i];
	    return result;
	}

	public JSONObject AddDivision(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Company_Division", request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_div")
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
					System.out.println("Some error in M_Division.");
				}
		
		String query="insert into M_Company_Division("+colName+") values("+value+")";
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
	
	
	
	
	
	
	

}
