package com.Master;

import java.io.IOException;
import java.sql.Connection;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import dto.Common.DtoCommon;
import dto.Common.UserAccessData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;
import com.Util.dtoUtils;


public class M_Vendor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HashMap<String, String> map =new HashMap<String,String>();
		
		 Enumeration elemet = request.getParameterNames();
		 HttpSession session2 = request.getSession();
		 String paramName="";
		 String paramValues="";
		    while(elemet.hasMoreElements())
		    {
		    	 
		      paramName = (String)elemet.nextElement();
		      paramValues = request.getParameter(paramName);
		      paramValues=paramValues.replace("'", "''");
		      map.put(paramName, paramValues);
		      
		    }
		    String UserType =  (String)session2.getAttribute("UserType");
			int id_emp_user = (int)session2.getAttribute("id_emp_user");
		String action = "",id="0",value="",ColName="",type_ven="",searchWord="",word="";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("type_ven") !=null)
		{
			type_ven = request.getParameter("type_ven");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		if(request.getParameter("searchWord") !=null)
		{
			 word = request.getParameter("searchWord");
		}
		if(request.getParameter("value") !=null)
		{
			value = request.getParameter("value");
		}
		if(request.getParameter("ColName") !=null)
		{
			ColName = request.getParameter("ColName");
		}
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
case "SendMailTOVendor":
				
				String currentUrl=request.getParameter("currentUrl");
				//System.out.println(currentUrl);
				currentUrl=currentUrl.substring(0, currentUrl.length() - 9);
				JSONObject json1=new JSONObject();
				DtoCommon dtcm = new DtoCommon();
	    				try 
				{
							String query="select id_emp from M_Emp_User mu where   id_emp_user='"+id_emp_user+"'";
							//System.out.println(query);
							ps=con.prepareStatement(query);
							rs=ps.executeQuery();
							
						if(rs.next())
					{
						String	replyMailId = id;
			    			List<String> recipients = new ArrayList<String>();
			    			recipients.add(rs.getString(1));
			    			
			    			
			    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
			    			String mailSubject = dtcm.ReturnParticularMessage("MailToTheVendor");
			    			
			    			String mailBody = "<b> Hello </b>,<br><br><br>"+
			    						  "TO Register as partner click on this link:"+
			    						  "<br>"+currentUrl+""+
										  "<p>"+footerMsg+"</p>";
			    		
			    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
			    			 
			    			json1.put("data",0);
					}
					else
					{
						    json1.put("data",1);
					}
						jobj=json1;	
						
				}
					
				catch (Exception e)
				{
					
					e.printStackTrace();
				}
			
				break;
	            case "Save":
	            	jobj = AddVendor(map,  request);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayVendor(id,searchWord,UserType,id_emp_user);	
	                break;
	            case "DisplayView":
	            	jobj = DisplayView(id,searchWord,UserType,id_emp_user);	
	                break;
	            case "EditPartner":
	            	String	sql ="select * from M_Vendor where id_ven="+id_emp_user+" ";
	            	 System.out.println(sql);
		            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);
	            case "DisplayApp":
	            	jobj = DisplayVendorApp(id,searchWord,UserType,id_emp_user);	
	                break;
	            case "DisplayVenInfo":
	            	jobj = DisplayVenInfo(id,searchWord,UserType,id_emp_user);	
	                break;
	            case "Edit":
	            	jobj = DisplayVendor(id,searchWord,UserType,id_emp_user);	
	                break;
	            case "auto_jwno":
	            	jobj = Autoworkno();
	            	break;
	            case "Update":
	            	jobj = UpdateVendor(map,id,  request);	
	                break; 
	            case "UpdatePartner":
	            	jobj = UpdatePartner(map,id,id_emp_user,  request);	
	                break; 
	            case "Approve":
	            	jobj = UpdateVendorApp(map,id);	
	                break; 
	            case "Reject":
	            	jobj = UpdateVendorRej(map,id);	
	                break; 
	                
	            case "MultiDropDownResult":
	            jobj= DropDownResultMulti(type_ven,word);
	            break;
	            
	            case "Delete":
	            	jobj = DeleteVendor(id);	
	                break; 
	            case "DropDownResult":
	            	jobj = DropDownResult(type_ven);	
	                break;
	            case "DropDownResultLaunch":
	            		sql ="select id_ven,nm_ven from M_Vendor where approve=1 ";
	            	 System.out.println(sql);
		            	jobj = dtoUtils.GetDataForDisplayInJsonFormat(sql,  request);	
	                break;
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			System.out.println(jobj);
			
		}catch(Exception e)
		{
			System.out.println("Error in M_Vendor.");
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
	public JSONObject Autoworkno()
	{
		String finyear="",poID2="";
		int PoId=1;
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		try
		{
			String year="",end="";
		//	ps=con.prepareStatement("select extract(year from to_date(STD_FINANCE,'yyyy/mm/dd')) as stdate,extract(year from to_date(END_FINANCE,'yyyy/mm/dd')) as endyear from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
			System.out.println("select std_finance as  start , endt_second as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
		 
			
			ps=con.prepareStatement("select std_finance as  start , end_finance as  enddt from M_FINANCE_YEAR where ACTIVE_YEAR='1'");
				rs=ps.executeQuery();
			if(rs.next())
			{
				year=rs.getString("start");
				end=rs.getString("enddt");
			}
		 
			String strt = year.substring (2,4);
			String endt = end.substring (2,4);
		ps=con.prepareStatement("select count(id_ven) from M_Vendor");
		rs=ps.executeQuery();
		System.out.println("select count(id_ven) from M_Vendor");
			if(rs.next())
			{
				PoId =	rs.getInt(1) +1;
				
			}
			
		String 	poID3 = String.format("%03d", PoId);
			poID2= "VEN"+poID3+"/"+strt+"-"+endt;
			  System.out.println(poID2);
			  jobj.put("wo_no",poID2);
			
		}
		
		catch(Exception e)
		{
			System.out.println("Eroor ...  "+e.toString());
		}
		return jobj;
	}
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_ven from M_Vendor where "+ColName+" = '"+value+"'";
		try 
		{
			System.out.println(query);
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
	
	public JSONObject DropDownResult(String type_ven)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(type_ven.equals("service"))
		 sql="select id_ven,nm_ven from M_Vendor ";
		else 
			 sql="select id_ven,nm_ven from M_Vendor ";
		
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
			System.out.println("sql error in M_Vendor.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DeleteVendor(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		try 
		{
		
		String query = "select id_ven from A_Invoice_Master where id_ven = "+id+"";
		PreparedStatement ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		if(rs.next())
		{
			j=2;
		}
		else
			{
			 query = "delete M_Vendor where id_ven = "+id+"";
			
			 	ps=con.prepareStatement(query);
				j=ps.executeUpdate();
				if(j > 0)
				{
					j=1;
				}
			
				json.put("data",j);
			}
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject DropDownResultMulti(String type_ven,String word)
	{
		System.out.println(word);
	Properties prop = new Properties();
	InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");

	if (inputStream == null) {
	System.out.println("property file 'configuration.properties' not found in the classpath");
	}
	try {
	prop.load(inputStream);
	} catch (IOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
	}
	String dbName = prop.getProperty("DbName");



	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	String sql="";
	String tempSql ="";
	if(dbName.equals("SQL")){

	if(!word.isEmpty())
	{
	//tempSql =" and (nm_ven LIKE '%"+word+"%' or cd_ven LIKE '%"+word+"%' or nm_contact LIKE '%"+word+"%' or phone LIKE '%"+word+"%')";
	tempSql =" (nm_ven LIKE '%"+word+"%' or cd_ven LIKE '%"+word+"%')";
	System.out.println(tempSql);
	}
	}
	else{
	tempSql =" and (REGEXP_LIKE(nm_ven,'"+word+"','i') or REGEXP_LIKE(cd_ven,'"+word+"','i') or REGEXP_LIKE(nm_contact,'"+word+"','i') or REGEXP_LIKE(phone,'"+word+"','i') )";

	}

	if(type_ven.equals("service"))
	{
	sql="select id_ven,nm_ven from M_Vendor where service='Service' "+tempSql+" order by nm_ven";
	}
	else
	{
	sql="select id_ven,nm_ven from M_Vendor where procured='procured' "+tempSql+" order by nm_ven";
	}

	sql="select id_ven,nm_ven from M_Vendor where "+tempSql+" order by nm_ven";

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
	    String name=metaData.getColumnName(i).toLowerCase();
	    jobj2.put(name,rs.getString(name));
	    }
	    jarr.put(jobj2);
	   
	       }
	   jobj.put("data", jarr);
	}
	catch(Exception e)
	{
	System.out.println("sql error in M_Vendor.");
	}

	return jobj;


	}
	public JSONObject UpdatePartner(HashMap<String, String> map , String id,int id_emp_user,HttpServletRequest request)
	{
		System.out.println("hhhhh");
		String col_Val="";
		int j=0,t=0,t1=0;
		String file1 = map.get("file_name1");
		String file2 = map.get("file_name2");
		String file3 = map.get("file_name3");
		String file4 = map.get("file_name4");
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Vendor",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_ven")
							{
								    if (map.containsKey(rs.getString(1)))
								    {
								    	if(rs.getString(1).equals("procured"))
								    	{
								    		t=1;
								    	}
								    	if(rs.getString(1).equals("service"))
								    	{
								    		t1=1;
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
				
			}
			
		catch(Exception e)
		{
			System.out.println(e);
		}
		try
		{
		
		String query="update M_Vendor set "+col_Val+" where id_ven="+id_emp_user+" ";
		 if(t != 1)
			{
				query="update M_Vendor set "+col_Val+",procured='' where id_ven="+id_emp_user+"";
				
			}
			 if(t1 != 1)
			{
				query="update M_Vendor set "+col_Val+",service='' where id_ven="+id_emp_user+"";
			}
		if(t != 1 && t1 != 1)
		{
			query="update M_Vendor set "+col_Val+",procured='',service='' where id_ven="+id_emp_user+"";	
		}
		
		if(!file1.isEmpty())
		{
			 query="update M_Vendor set score=score+25 where id_ven="+id_emp_user+" " ;
			 System.out.println(query);
			  ps=con.prepareStatement(query);
				j=ps.executeUpdate();
		}
		if(!file2.isEmpty())
		{
			 query="update M_Vendor set score=score+25 where id_ven="+id_emp_user+"" ;
			 System.out.println(query);
			 ps=con.prepareStatement(query);
				j=ps.executeUpdate();
		}
		if(!file3.isEmpty())
		{
			 query="update M_Vendor set score=score+25 where id_ven="+id_emp_user+"" ;
			 System.out.println(query);
			 ps=con.prepareStatement(query);
				j=ps.executeUpdate();
		}
		if(!file4.isEmpty())
		{
			 query="update M_Vendor set score=score+25 where id_ven="+id_emp_user+"" ;
			 System.out.println(query);
			 ps=con.prepareStatement(query);
				j=ps.executeUpdate();
		}
		json.put("data",j);
		}
		catch (Exception e)
		{
			 System.out.println(e);
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	public JSONObject AddVendor(HashMap<String, String> map,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		int id_ven=0;
		JSONObject json=new JSONObject();
		String file1 = map.get("file_name1");
		String file2 = map.get("file_name2");
		String file3 = map.get("file_name3");
		String file4 = map.get("file_name4");
//		Connection con=null;
		Statement stmt=null;
		try
		{
				rs = Common.GetColumns("M_Vendor",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_ven")
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
					System.out.println("Some error in M_Vendor.");
				}
		
		
		try 
		{
			String query="insert into M_Vendor("+colName+") values("+value+")";
			System.out.println(query);
			 
			stmt=con.createStatement();
			 stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			 rs = stmt.getGeneratedKeys();
					 
	        while (rs.next()) 
	        {
	       	 id_ven = rs.getInt(1);
	       	 j=1;
	        }
			if(j == 1)
			{
				if(!file1.isEmpty())
				{
					 query="update M_Vendor set score=score+25 where id_ven="+id_ven+" " ;
					 System.out.println(query);
					  ps=con.prepareStatement(query);
						j=ps.executeUpdate();
				}
				if(!file2.isEmpty())
				{
					 query="update M_Vendor set score=score+25 where id_ven="+id_ven+"" ;
					 System.out.println(query);
					 ps=con.prepareStatement(query);
						j=ps.executeUpdate();
				}
				if(!file3.isEmpty())
				{
					 query="update M_Vendor set score=score+25 where id_ven="+id_ven+"" ;
					 System.out.println(query);
					 ps=con.prepareStatement(query);
						j=ps.executeUpdate();
				}
				if(!file4.isEmpty())
				{
					 query="update M_Vendor set score=score+25 where id_ven="+id_ven+"" ;
					 System.out.println(query);
					 ps=con.prepareStatement(query);
						j=ps.executeUpdate();
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
	public JSONObject UpdateVendor(HashMap<String, String> map , String id,HttpServletRequest request)
	{
		System.out.println("hhhhh");
		String col_Val="";
		int j=0,t=0,t1=0;
		String file1 = map.get("file_name1");
		String file2 = map.get("file_name2");
		String file3 = map.get("file_name3");
		String file4 = map.get("file_name4");
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_Vendor",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_ven")
							{
								    if (map.containsKey(rs.getString(1)))
								    {
								    	if(rs.getString(1).equals("procured"))
								    	{
								    		t=1;
								    	}
								    	if(rs.getString(1).equals("service"))
								    	{
								    		t1=1;
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
				
			}
			
		catch(Exception e)
		{
			System.out.println(e);
		}
		try
		{
		
		String query="update M_Vendor set "+col_Val+" where id_ven="+id+"";
		
		 if(t != 1)
			{
				query="update M_Vendor set "+col_Val+",procured='' where id_ven="+id+"";
				
			}
			 if(t1 != 1)
			{
				query="update M_Vendor set "+col_Val+",service='' where id_ven="+id+"";
			}
		if(t != 1 && t1 != 1)
		{
			query="update M_Vendor set "+col_Val+",procured='',service='' where id_ven="+id+"";	
			System.out.println(query);
		}
		ps=con.prepareStatement(query);
		j=ps.executeUpdate();
		if(!file1.isEmpty())
		{
			 query="update M_Vendor set score=score+25 where id_ven="+id+" " ;
			 System.out.println(query);
			  ps=con.prepareStatement(query);
				j=ps.executeUpdate();
		}
		if(!file2.isEmpty())
		{
			 query="update M_Vendor set score=score+25 where id_ven="+id+"" ;
			 System.out.println(query);
			 ps=con.prepareStatement(query);
				j=ps.executeUpdate();
		}
		if(!file3.isEmpty())
		{
			 query="update M_Vendor set score=score+25 where id_ven="+id+"" ;
			 System.out.println(query);
			 ps=con.prepareStatement(query);
				j=ps.executeUpdate();
		}
		if(!file4.isEmpty())
		{
			 query="update M_Vendor set score=score+25 where id_ven="+id+"" ;
			 System.out.println(query);
			 ps=con.prepareStatement(query);
				j=ps.executeUpdate();
		}
		json.put("data",j);
		}
		catch (Exception e)
		{
			 System.out.println(e);
			e.printStackTrace();
		}
		
		
		return json;
	}
	
	public JSONObject UpdateVendorApp(HashMap<String, String> map , String id)
	{
		String col_Val="";
		int j=0,t=0,t1=0;;
		JSONObject json=new JSONObject();
		String remarks=map.get("apprejremarks");
		String query="update M_Vendor set apprejremarks='"+remarks+"' , approve=1 where id_ven="+id+"";
		 
		System.out.println(query);
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
	public JSONObject UpdateVendorRej(HashMap<String, String> map , String id)
	{
		String col_Val="";
		int j=0,t=0,t1=0;;
		JSONObject json=new JSONObject();
		String remarks=map.get("apprejremarks");
		String query="update M_Vendor set apprejremarks='"+remarks+"' , approve=0 where id_ven="+id+"";
		 
		System.out.println(query);
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
	public JSONObject DisplayVendorApp(String id , String searchWord,String UserType,int id_emp_user)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(UserType.equals("vend")){
		sql="select * from M_Vendor where id_ven='"+id_emp_user+"' and (nm_ven like '"+searchWord+"%' or cd_ven like '"+searchWord+"%' or phone like '"+searchWord+"%' ) order by nm_ven";
		
		}
		else{
			
			sql="select * from M_Vendor where approve=0 and (nm_ven like '"+searchWord+"%' or cd_ven like '"+searchWord+"%' or phone like '"+searchWord+"%' ) order by nm_ven";
				
		}
			if(!id.equals("0"))
		
		{
			sql ="select * from M_Vendor where id_ven = "+id+" order by nm_ven";
		}
		
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
			System.out.println("sql error in M_Vendor.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject DisplayVenInfo(String id , String searchWord,String UserType,int id_emp_user)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(UserType.equals("vend")){
		sql="select * from M_Vendor where id_ven='"+id_emp_user+"' and (nm_ven like '"+searchWord+"%' or cd_ven like '"+searchWord+"%' or phone like '"+searchWord+"%' ) order by nm_ven";
		
		}
		else{
			
			sql="select * from M_Vendor where approve=1 and (nm_ven like '"+searchWord+"%' or cd_ven like '"+searchWord+"%' or phone like '"+searchWord+"%' ) order by nm_ven";
				
		}
			if(!id.equals("0"))
		
		{
			sql ="select * from M_Vendor where id_ven = "+id+" order by nm_ven";
		}
		
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
			System.out.println("sql error in M_Vendor.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DisplayVendor(String id , String searchWord,String UserType,int id_emp_user)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(UserType.equals("vend")){
		sql="select * from M_Vendor where id_ven='"+id_emp_user+"' and (nm_ven like '%"+searchWord+"%' or cd_ven like '%"+searchWord+"%' or phone like '%"+searchWord+"%' ) order by nm_ven";
		
		}
		else{
			
			sql="select * from M_Vendor where (nm_ven like '%"+searchWord+"%' or cd_ven like '%"+searchWord+"%' or phone like '%"+searchWord+"%' ) and approve=1 order by nm_ven";
				
		}
			if(!id.equals("0"))
		
		{
			sql ="select * from M_Vendor where id_ven = "+id+" order by nm_ven";
		}
		
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
			System.out.println("sql error in M_Vendor.");
		}
		 
		return jobj;
		
		
	}
	public JSONObject DisplayView(String id , String searchWord,String UserType,int id_emp_user)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="";
		if(UserType.equals("vend")){
		sql="select * from M_Vendor where id_ven='"+id_emp_user+"' and (nm_ven like '"+searchWord+"%' or cd_ven like '"+searchWord+"%' or phone like '"+searchWord+"%' ) and approve=1 order by nm_ven";
		
		}
		else{
			
			sql="select * from M_Vendor where (nm_ven like '"+searchWord+"%' or cd_ven like '"+searchWord+"%' or phone like '"+searchWord+"%' ) and approve=1 order by nm_ven";
				
		}
			
		
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
			System.out.println("sql error in M_Vendor.");
		}
		 
		return jobj;
		
		
	}
	
	
	
	
	
	
	
	
	

}
