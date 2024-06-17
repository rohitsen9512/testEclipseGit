package com.FreeTrial;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Common.Common;



/**
 * Servlet implementation class FreeTrail
 */
public class FreeTrail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con=null;
	Statement st=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		Properties prop = new Properties();	
		InputStream inputStream = FreeTrail.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
		
		if (inputStream == null) {
			System.out.println("property file 'configuration.properties' not found in the classpath");
		}
		try
		{
			

			prop.load(inputStream);
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			String url = prop.getProperty("url");
			String link = prop.getProperty("link");
			

		
			System.out.println(url);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		    con = DriverManager.getConnection(url, userName, password);
		    
		    st=con.createStatement();
			
			
			
			
		
		  //copy index.html page with company name
			ServletContext servletContext = request.getSession().getServletContext();
			System.out.println(servletContext);
			String relativeWebPath = "DataBase";
			String strdirName = servletContext.getRealPath(relativeWebPath);
			System.out.println(strdirName);
		
		String pname=request.getParameter("pname");
		String pmobile=request.getParameter("pmobile");
		String email=request.getParameter("email");
		String comp=request.getParameter("comp");
		String AppType=request.getParameter("group");
		String comment=request.getParameter("comment");
		String compfordbandurl=comp;
		  compfordbandurl=compfordbandurl.replaceAll("\\s+","_");
		  System.out.println("comp name "+compfordbandurl);

		
		 String webpwd=RandomPass(5);
		 
		 String checkdbsql="SELECT name FROM  sys.databases where name='"+compfordbandurl+"'";
		 
		 PreparedStatement  ps=con.prepareStatement(checkdbsql);
		 ResultSet  rs=ps.executeQuery();
		 if(!rs.next()) {
			 PreparedStatement stmt=con.prepareStatement("select email,comp from freetrail where email=? and comp=?");	
			 System.out.println("select email,comp from freetrail where email=? and comp=?");
				stmt.setString(1, email);
				stmt.setString(2, comp);
				
				rs = stmt.executeQuery();
				
					if (!rs.next())
					{		 
						 
		String sql="insert into freetrail(pname,pmobile,email,comp,AppType,comment,password) "
				+ "  values('"+pname+"','"+pmobile+"','"+email+"','"+comp+"','"+AppType+"','"+comment+"','"+webpwd+"')";
		System.out.println(sql);
		//st.executeUpdate(sql);
		  st=con.createStatement(); 
		  st.execute(sql);
		
		  //Create Copy of Database url = prop.getProperty("systemDBurl");
		  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); con =
		  DriverManager.getConnection(url, userName, password); String
		  MasterDBurl=prop.getProperty("MasterDBurl");
		  
		  con = DriverManager.getConnection(url, userName, password);
		  sql="restore filelistonly from disk ='"
		  +strdirName+"\\"+AppType+".bak' " +
		  "    RESTORE DATABASE "+compfordbandurl+" FROM DISK='"
		  +strdirName+"\\"+AppType+".bak' " +
		  "    WITH  REPLACE, RECOVERY,   " +
		  "    MOVE '"+AppType+"' TO '"+strdirName+"\\"+compfordbandurl+".mdf', " +
		  "    MOVE '"+AppType+"_LOG' TO '"+strdirName+"\\"+compfordbandurl+".ldf';"; 
		 
		  System.out.println(sql);
		  st=con.createStatement(); 
		  st.execute(sql); // con.close();
		  TimeUnit.SECONDS.sleep(4); 
		  url = prop.getProperty("UrlNewDB");
		  url=url.concat(compfordbandurl); 
		  System.out.println(url);
		  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		  con = DriverManager.getConnection(url, userName, password);
		  
			/*
			 * sql="insert into m_emp_user (cd_emp,nm_emp,id_emp,cont_no,id_design,id_dept,id_div,"
			 * + "  status_emp,repo_mngr,id_loc,id_sloc,id_building,emp_type ) " +
			 * "  values('super','"+pname+"','"+email+"','"+
			 * pmobile+"','1','1','1','1','Working','1','1','1','Employee') ";
			 */	 
		  
		  sql="insert into m_emp_user (cd_emp,nm_emp,id_emp,cont_no"
				  + "  ,id_loc,id_sloc,id_building ) " +
				  "  values('"+pname+"','"+pname+"','"+email+"','"+
				  pmobile+"','1','1','1') ";
		  System.out.println(sql);
		  
			/*
			 * st=con.createStatement(); st.execute(sql);
			 */
		  st=con.createStatement();
		  st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			ResultSet rs1 = st.getGeneratedKeys();
				int id_emp_user=0;	 
	         while (rs1.next()) 
	         {
	        	 id_emp_user = rs1.getInt(1);
	         }
		  
		  
		  String pwd=RandomPass(5);
		  
		  sql="insert into M_User_Login (id_emp_user,nm_login,pwd,type_user,status_user"
		  + "    ) " +
		  "  values('"+id_emp_user+"','Atlas"+pname+"','"+pwd+"','SUPER','1'  ) ";
		  System.out.println(sql);
		  
		  st=con.createStatement();
		  st.execute(sql);
		  SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(System.currentTimeMillis());
//			System.out.println(formatter.format(date));	
			LocalDate dt_register=LocalDate.now();
			System.out.println(dt_register);
			LocalDate dt_trail_end=dt_register.plusDays(15);
			System.out.println(dt_trail_end);
			
			sql="select nm_com from M_Company where id_com=1";
			 
			  ps=con.prepareStatement(sql);
			  rs=ps.executeQuery();
			 if(rs.next()) {
				 sql="Update M_Company set dt_register='"+dt_register+"',FreeTrail='Yes',dt_trail_end='"+dt_trail_end+"'";
						  System.out.println(sql);
						  st=con.createStatement();
						  st.execute(sql);
			 }
			 else {
		  sql="insert into M_Company (dt_register,FreeTrail,dt_trail_end"
				  + "    ) " +
				  "  values('"+dt_register+"','Yes','"+dt_trail_end+"') ";
				  System.out.println(sql);
				  
				  st=con.createStatement();
				  st.execute(sql);
			 }
		  con.close();
		 
		//To fetch root directory of all erp tool
		 
		System.out.println("Stdir name "+strdirName);
		String separator = "\\\\";
		System.out.println(strdirName.replaceAll(Pattern.quote(separator), "\\\\"));
		String dirarr []=strdirName.replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
		
		String AppRootDir="";
		for(int i=0;i<dirarr.length-2;i++) {
			if(i==0) {
				AppRootDir=AppRootDir.concat(dirarr[i]);	
				
			}else {
				AppRootDir=AppRootDir + "\\";
				AppRootDir=AppRootDir.concat(dirarr[i]);
			}
			
			
			
		}
		AppRootDir=AppRootDir+"\\"+AppType;
		System.out.println("Apprrot is"+ AppRootDir);
		
		
		
		 String DirectoryTom=prop.getProperty("ApplicationFolderurl");
		 File source = new File(AppRootDir+"\\"+"index.html");
		 File  Destination = new File(AppRootDir+"\\"+compfordbandurl+".html");
		 Files.copy(source.toPath(), Destination.toPath());
	
		
		 //MailTrigger to client
	
			
			/*
			 * String mailBody = "<b> Hello "+pname+"</b>,<br><br><br>"+
			 * "You have rgisterd on EZatlas for free trail."+ "For Web Portal----<br>"+
			 * "<br>User Name:"+email+"<br>"+ "<br>Password: "+webpwd+"<br><br><br>"+
			 * "For Tools---<br>"+ "<br>User Name: Atlas"+pname+"<br>"+
			 * "<br>Password: "+pwd+"<br><br>"+
			 * "<br>URL<br>"+link+"/"+AppType+"/"+comp+".html<br>"+
			 * "<p>Dont Reply on this mail</p>";
			 */
			  String urllink=""+link+"/"+AppType+"/"+compfordbandurl+".html";
			  
			  jobj.put("data","1"); 
			  List<String> recipients = new ArrayList<String>();
		    	recipients.add(email);
		    	
		    	Common.MailConfiguration(returnbody(pname,email,webpwd,pwd,urllink), email,recipients,"ezAtlas-"+AppType+"");
		 }
		 else {
			 jobj.put("data","2");
			 
		 }
		 }
		}
		catch(Exception e)
		{
			System.out.println("Connection Error... "+e);
		}
	  
		
	   request.setAttribute("data", jobj.toString());
		response.getWriter().write(jobj.toString());
	}

	
	
		  
	    // function to generate a random string of length n
	    static String RandomPass(int n)
	    {
	  
	        // chose a Character random from this String
	        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	                                    + "0123456789"
	                                    + "abcdefghijklmnopqrstuvxyz";
	  
	        // create StringBuffer size of AlphaNumericString
	        StringBuilder sb = new StringBuilder(n);
	  
	        for (int i = 0; i < n; i++) {
	  
	            // generate a random number between
	            // 0 to AlphaNumericString variable length
	            int index
	                = (int)(AlphaNumericString.length()
	                        * Math.random());
	  
	            // add Character one by one in end of sb
	            sb.append(AlphaNumericString
	                          .charAt(index));
	        }
	  
	        return sb.toString();
	    }
	
	   
	    static String returnbody(String pname,String email,String webpwd,String pwd,String urllink) {
	    	String mailbody="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
	    			" <tbody><tr>\r\n" + 
	    			" <td align=\"center\">\r\n" + 
	    			" <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px\">\r\n" + 
	    			" <tbody><tr>\r\n" + 
	    			" <td align=\"center\" valign=\"top\" style=\"padding:20px 10px 20px 10px\">\r\n" + 
	    			" <h2 style=\"text-decoration:none\">\r\n" + 
	    			"<span border=\"0\"><b>Hi from <b><span style=\"color:#bf1319\">HCS Technology !!!</span> </b></b></span>\r\n" + 
	    			" </h2>\r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" </tbody></table>\r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" \r\n" + 
	    			" <tr>\r\n" + 
	    			" <td align=\"center\" style=\"padding:0px 10px 0px 10px\">\r\n" + 
	    			" <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px\">\r\n" + 
	    			" <tbody><tr>\r\n" + 
	    			" <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\">\r\n" + 
	    			" <h2 style=\"font-weight:400;margin:0;padding:20px\"><b>Welcome aboard "+pname+"!</b></h2>\r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" </tbody></table> \r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			"<tr>\r\n" + 
	    			" <td align=\"center\" style=\"padding:0px 10px 0px 10px\"> \r\n" + 
	    			" <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px\">\r\n" + 
	    			" \r\n" + 
	    			" <tbody><tr>\r\n" + 
	    			" <td bgcolor=\"#ffffff\">\r\n" + 
	    			" <p style=\"margin:0;padding:0px 20px 20px 20px\">We Welcome you to the World of Effective Lead &amp; Client Management. You can easily monitor the status of your enquiries and get them converted to your Clients with multiple tools which includes Followup reminders, Future Followup Forecast, etc.,</p>\r\n" + 
	    			" <br><p style=\"margin:0;padding:0px 20px 0px 20px\">We highly recommed you to start using the tool for atleast 7 days and you will surely find a very good growth in your business.</p>\r\n" + 
	    			" <br> <p style=\"margin:0;padding:20px\">Kindly visit our resources section to go through the complete demo video of the Tool. It will take less than 30 minutes to completely understand the functions of every module and START using it effectively</p>\r\n" + 
	    			" \r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" \r\n" + 
	    			" \r\n" + 
	    			" <tr>\r\n" + 
	    			" <td bgcolor=\"#ffffff\">\r\n" + 
	    			" \r\n" + 
	    			" <br> <p style=\"margin:0;padding:20px\">It will be much convinient and simple for you to use ezAtlas tool as it designed by keeping customers prespective in mind.</p>\r\n" + 
	    			" \r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" \r\n" + 
	    			" \r\n" + 
	    			" \r\n" + 
	    			" <tr>\r\n" + 
	    			" <td bgcolor=\"#ffffff\" align=\"left\">\r\n" + 
	    			" <p style=\"margin:0;padding:20px\"><b>You have rgisterd on EZatlas for free trail.For Web Portal---- <br>"
	    			+ "User Name  :"+email+" <br>Password:"+webpwd+"</b>"
	    					+ "<br> For Tools<br>User Name:Atlas"+pname+"<br>Password: "+pwd+"<br>Link :"+urllink+" "
	    					+ ""
	    					+ "</p>\r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" <tr>\r\n" + 
	    			" <td bgcolor=\"#ffffff\" align=\"left\">\r\n" + 
	    			" <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    			" <tbody><tr>\r\n" + 
	    			" <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding:20px 30px 20px 30px\">\r\n" + 
	    			" <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
	    			" <tbody><tr>\r\n" + 
	    			" <td align=\"center\" style=\"border-radius:3px\" bgcolor=\"#BF1319\"><a href=\""+urllink+"\" style=\"font-size:18px;font-family:Helvetica,Arial,sans-serif;color:#ffffff;text-decoration:none;color:#ffffff;text-decoration:none;padding:12px 50px;border-radius:2px;border:1px solid #bf1319;display:inline-block\" target=\"_blank\" "
	    			+ "data-saferedirecturl=\"https://www.google.com/url?q=http://tracking.solidperformers.com/tracking/click?d%3DZrlPpuOK077Kk2ry7NqqQpz_8b0OJI9cvfYgKgprWfJaHdM-y_u9fBDP76Xd6CsenUAXMcyR8Onpf6-Cu0LPxHEtaFahczMcnTk3rAIiUnK-wxRdjRfGWUbHFDbkLu6vfDVY_r39LM-AocZIVbg6Y9w1&amp;source=gmail&amp;ust=1623742818505000&amp;usg=AFQjCNE1AWQkreZLDgYe_CmqY5TRywKj6w\">Get Started Now</a></td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" </tbody></table>\r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" </tbody></table>\r\n" + 
	    			" </td>\r\n" + 
	    			" </tr> \r\n" + 
	    			" \r\n" + 
	    			" \r\n" + 
	    		  
	    			" \r\n" + 
	    			" <tr>\r\n" + 
	    			" <td bgcolor=\"#ffffff\" align=\"left\">\r\n" + 
	    			" <p style=\"margin:0;padding:20px\"><b>Cheers,<br>Team HCS Technology</b></p>\r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" </tbody></table>\r\n" + 
	    			" \r\n" + 
	    			" </td>\r\n" + 
	    			" </tr>\r\n" + 
	    			" \r\n" + 
	    			" <tr>\r\n" + 
	    			" <td align=\"center\" style=\"padding:10px 10px 0px 10px\">\r\n" + 
	    			" \r\n" + 
	    			" <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px\">\r\n" + 
	    			" \r\n" + 
					/*
					 * " <tbody><tr>\r\n" + " <td bgcolor=\"#BF1319\" align=\"center\">\r\n" +
					 * " <p style=\"margin:0;font-size:10px;color:#fff\">Copyright 2021 Solid Performers CRM. All rights reserved.</p>\r\n"
					 * + " </td>\r\n" + " </tr>\r\n" + " </tbody></table>\r\n" +
					 */
	    			" \r\n" + 
	    			" </td>\r\n" + 
	    			" </tr> \r\n" + 
	    			"</tbody></table>";
	    	
	    	
	    	
	    	return mailbody;
	    }

}
