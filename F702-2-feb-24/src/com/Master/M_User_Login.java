/*package com.Master;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.Common.Common;


public class M_User_Login extends HttpServlet {
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
		      map.put(paramName, paramValues);
		      
		    }
		    
		String action = "",id="0";
		int json=0;
		if(request.getParameter("action") !=null)
		{
			 action = request.getParameter("action");
		}
		if(request.getParameter("id") !=null)
		{
			 id = request.getParameter("id");
		}
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	            case "Save":
	            	jobj = AddUser(map);	
	                break;
	                
	            case "Display":
	            	jobj = DisplayUser(id);	
	                break;
	                
	            case "Edit":
	            	jobj = DisplayUser(id);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateUser(map,id);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteUser(id);	
	                break; 
	                
	             
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in User Master.");
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
	

	
	public JSONObject DeleteUser(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		
		String query = "delete M_User_Login where id_log_user = "+id+"";
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
	
	public JSONObject UpdateUser(HashMap<String, String> map , String id)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_User_Login");
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_log_user")
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
					System.out.println("Some error in User master.");
				}
		
		String query="update M_User_Login set "+col_Val+" where id_log_user="+id+"";
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
	
	
	
	public JSONObject DisplayUser(String id)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select * ,nm_emp from M_Emp_User emp, M_User_Login log where emp.id_emp_user=log.id_emp_user";
		if(!id.equals("0"))
		{
			sql ="select * ,nm_emp from M_Emp_User emp, M_User_Login log where emp.id_emp_user=log.id_emp_user and id_log_user = "+id+"";
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
			System.out.println("sql error in User master.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddUser(HashMap<String, String> map)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_User_Login");
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_log_user")
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
					System.out.println("Some error in User master.");
				}
		
		String query="insert into M_User_Login("+colName+") values("+value+")";
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
*/
package com.Master;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;

import dto.Common.DtoCommon;

public class M_User_Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ResultSet rs=null;
	PreparedStatement ps=null;
	Connection con=null;
	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		HttpSession session = request.getSession();
		int UserId=0;
		 session = request.getSession(true);  
//		 if(request.getParameter("fromPhone") ==null)
//			{
//			 UserId = (int)session.getAttribute("UserId");
//			}
//		 else {
//			    System.out.println(request.getParameter("dbname"));
//				session.setAttribute("dbname", request.getParameter("dbname"));  
//			 
//			 
//		 }
		 session.setAttribute("dbname", request.getParameter("dbname")); 
		
		HashMap<String, String> map =new HashMap<String,String>();
		// HttpSession mailSession = request.getSession();  
		Enumeration elemet = request.getParameterNames();
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
		    
		String action = "",id="0",value="",ColName="",floors="",searchWord="",user="",email="",oldPwd="",newPwd="",confirmPwd="",pwd="";
		int json=0;
		if(request.getParameter("name") !=null)
		{
			user = request.getParameter("name");
		}
		if(request.getParameter("mail_id") !=null)
		{
			email = request.getParameter("mail_id");
		}
		
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
		if(request.getParameter("searchWord") !=null)
		{
			searchWord = request.getParameter("searchWord");
		}
		if(request.getParameter("oldPwd") !=null)
		{
			oldPwd = request.getParameter("oldPwd");
		}
		if(request.getParameter("newPwd") !=null)
		{
			newPwd = request.getParameter("newPwd");
		}
		if(request.getParameter("confirmPwd") !=null)
		{
			confirmPwd = request.getParameter("confirmPwd");
		}
		if(request.getParameter("pass") !=null)
		{
			pwd = request.getParameter("pass");
		}
		int len=0;
		String [] id_flr 	=	request.getParameterValues("id_flr1");
		
		try{
			if(id_flr.length>0)
				len=id_flr.length;
		}catch(Exception e){
			len=0;
		}
		
		if(len > 0){
			for(int i=0;i<id_flr.length;i++)
			{
				if(floors.equals(""))
					floors = id_flr[i];
				else
					floors +=","+ id_flr[i];
			}
		}
		
		try
		{
			
			con=Common.GetConnection(request);
			
			switch (action)
	        {
	        case "ChangePassword":
	        	int id_emp_user = (int)session.getAttribute("id_emp_user");
            	jobj = ChangePassword(oldPwd,newPwd,id_emp_user);	
                break;
	        case "ForgotPassword":
            	jobj = ForgotPassword(user,email);	
                break;
	            case "Save":
	            	jobj = AddUser(map , floors,  request);
	            	
	                break;
	                
	            case "Display":
	            	jobj = DisplayUser(id,searchWord);	
	            	/*String content = "Testing.";
	            	List<String> recipients = new ArrayList<String>();
	            	recipients.add("muralidhar@jockeyindia.com");
	            	    Properties props = new Properties();
	            	    props.put("mail.smtp.auth", "false");
	            	    props.put("mail.smtp.starttls.enable", "true");
	            	    props.put("mail.smtp.host", "172.16.5.75");	
	            	    props.put("mail.smtp.port", "25");
	            	    props.put("mail.smtp.ssl.trust", "172.16.5.75");
	            	    props.put("mail.debug", "true");

	            	    Session session = Session.getInstance(props);
	            	    try{
	            	         MimeMessage message = new MimeMessage(session);
	            	         message.setFrom(new InternetAddress("muralidhar@jockeyindia.com"));
	            	         message.setReplyTo(new Address[]{new InternetAddress("muralidhar@jockeyindia.com")});
	            	         for(String recipient: recipients){
	            	             message.addRecipient(Message.RecipientType.BCC,new InternetAddress(recipient));
	            	         }
	            	         message.setSubject("No-Reply");
	            	         message.setContent(content,"text/html");
	            	         Transport.send(message);
	            	         
	            	      }catch (MessagingException mex) {
	            	         mex.printStackTrace();
	            	         
	            	      }*/
	                break;
	                
	            case "Edit":
	            	jobj = DisplayUser(id,searchWord);	
	            	/*try
	            	{
	            		String toMail="runjay301291@gmail.com";
	            		M_User_Login javaEmail = new M_User_Login();
	    				javaEmail.setMailServerProperties();            
	        	        javaEmail.createEmailMessage(toMail) ;
	        	        javaEmail.sendEmail();
	            	}
	            	catch(Exception e)
	            	{
	            		System.out.println(e);
	            	}*/
	                break;
	                
	            case "checkuser_pass":
	            	jobj = checkuserpass(user,pwd);	
	                break;
	                
	            case "Update":
	            	jobj = UpdateUser(map,id, floors,  request);	
	                break; 
	                
	            case "Delete":
	            	jobj = DeleteUser(id);	
	                break; 
	                
	            case "CheckExitsVal":
	            	jobj = CheckExitsVal(value,ColName);	
	                break;
	            case "DisplayEmp":
	            	jobj = DisplayEmp(id);
	            	
	                break;
	             
	        }
		
			request.setAttribute("data", jobj.toString());
			response.getWriter().write(jobj.toString());
			
			
		}catch(Exception e)
		{
			System.out.println("Error in User Master.");
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
	public JSONObject checkuserpass(String name , String pass)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_log_user from M_User_Login where NM_LOGIN='"+name+"' and pwd= '"+pass+"'";
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
	public JSONObject CheckExitsVal(String value , String ColName)
	{
		JSONObject json=new JSONObject();
		
		String query = "select id_log_user from M_User_Login where "+ColName+" = '"+value+"'";
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
	
	public JSONObject DeleteUser(String id)
	{
		JSONObject json=new JSONObject();
		int j=0;
		
		String query = "delete M_User_Login where id_log_user = "+id+"";
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
	
	public JSONObject UpdateUser(HashMap<String, String> map , String id , String floors,HttpServletRequest request)
	{
		String col_Val="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_User_Login",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_log_user")
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
					System.out.println("Some error in User master.");
				}
		
		String query="update M_User_Login set "+col_Val+", id_flr='"+floors+"' where id_log_user="+id+"";
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
	
	
	
	public JSONObject DisplayUser(String id, String searchWord)
	{
		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		String sql="select log.* ,nm_emp,id_emp from M_Emp_User emp, M_User_Login log where emp.id_emp_user=log.id_emp_user and (nm_emp like '"+searchWord+"%' or id_emp like '"+searchWord+"%' or nm_login like '"+searchWord+"%' ) order by status_user desc, nm_emp";
		System.out.println(sql);
		if(!id.equals("0"))
		{
			sql ="select id_log_user,log.id_emp_user,nm_login,pwd,type_user,status_user,log.id_loc,log.id_subl,log.id_flr,log.id_div,nm_emp,emp.id_emp as id_email,(replace(convert(NVARCHAR, dt_disable, 103), ' ', '-')) as dt_disable,log.id_usertype from M_Emp_User emp, M_User_Login log where emp.id_emp_user=log.id_emp_user and id_log_user = "+id+"";
			System.out.println(sql);
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
			System.out.println("sql error in User master.");
		}
		 
		return jobj;
		
		
	}
	
	public JSONObject AddUser(HashMap<String, String> map , String floors,HttpServletRequest request)
	{
		String colName="",value="";
		int j=0;
		JSONObject json=new JSONObject();
		try
		{
				rs = Common.GetColumns("M_User_Login",  request);
				
						
						while (rs.next())
						{
						
							if(rs.getString(1) !="id_log_user")
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
					System.out.println("Some error in User master.");
				}
		
		String query="insert into M_User_Login("+colName+" , id_flr) values("+value+", '"+floors+"' )";
		System.out.println(query);
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			j=ps.executeUpdate();
			if(j > 0)
			{
				j=0;
				String id = map.get("id_emp_user");
				String nm_login = map.get("nm_login");
				String pwd = map.get("pwd");
				//.........for mail ........
            	String replyMailId="",name="",assetHolderId="",itemName="",slNo="",no_model="";
            	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user="+id+"";
            	
            	
            	DtoCommon dtcm = new DtoCommon();
    			rs = dtcm.GetResultSet(mailSql,  request);
    			if(rs.next())
    			{
    				replyMailId = rs.getString(1);
    				name = rs.getString(2);
    			}
    			List<String> recipients = new ArrayList<String>();
    			//recipients.add(assetHolderId);
    			
    			String link = dtcm.ReturnParticularMessage("link");
    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
    			String mailSubject = dtcm.ReturnParticularMessage("loginDetails");
    			
    			String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
    						  "Below are the login credential details for Order Management tool:<br>"+
    						  "User Name : <b>"+nm_login+"</b><br>"+
    						  "Password : <b>"+pwd+"</b><br>"+
    						  "<br><br><br>"+link+""+
							  "<p>"+footerMsg+"</p>";
    		
    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
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
	
	//SMTP Configuration
	public void sendEmail() throws AddressException, MessagingException {

        String emailHost = "smtp.gmail.com";
        String fromUser = "runjay301291@gmail.com";//just the id alone without @gmail.com
        String fromUserEmailPassword = "mrp9934913958";

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
	public void createEmailMessage(String toEmails) throws AddressException,
    MessagingException {

    String emailSubject = "no reply-Verification Mail";
    String emailBody = "Just For test perpose.";

    mailSession = Session.getDefaultInstance(emailProperties, null);
    emailMessage = new MimeMessage(mailSession);
    emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails));


    emailMessage.setSubject(emailSubject);
    emailMessage.setContent(emailBody, "text/html");//for a html email
   // emailMessage.setText(emailBody);// for a text email

}
	
	 public void setMailServerProperties() {

	        String emailPort = "587";//gmail's smtp port

	        emailProperties = System.getProperties();
	        emailProperties.put("mail.smtp.port", emailPort);
	        emailProperties.put("mail.smtp.auth", "true");
	        emailProperties.put("mail.smtp.starttls.enable", "true");
	    } 
	 public JSONObject DisplayEmp(String id)
		{
			JSONObject json=new JSONObject();
			JSONObject jobj = new JSONObject();
			JSONArray jarr = new JSONArray();
			String query = "select emp.nm_emp,emp.id_emp,mu.nm_usertype,emp.cd_emp from M_Emp_User emp, M_User_Type mu where id_emp_user= "+id+"";
			System.out.println(query );
			try
			{
			ps=con.prepareStatement(query);
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
			    System.out.println(json);
			}
			catch(Exception e)
			{
				System.out.println(e+"employee name not selected");
			}
			 
			return jobj;
			
		}
	 public JSONObject ForgotPassword(String user , String email)
		{
			DtoCommon dtcm = new DtoCommon();
    		
			JSONObject json=new JSONObject();
					try 
			{
						String query="select id_email,nm_login,nm_emp from M_User_Login ml ,M_Emp_User mu where ml.id_emp_user=mu.id_emp_user and id_email='"+email+"' and nm_login='"+user+"'";
					/*	PreparedStatement stmt=con.prepareStatement(query);	
						stmt.setString(1, email);
						stmt.setString(2, user);
					*/	ps=con.prepareStatement(query);
						rs=ps.executeQuery();
						
					if(rs.next())
				{
					String	replyMailId = email;
		    			List<String> recipients = new ArrayList<String>();
		    			//recipients.add(assetHolderId);
		    			char[] pwd=generatePassword(8);
		    			String str = String.valueOf(pwd);
		    		//	String pwew=Character.toString(pwd);
		    			String link = dtcm.ReturnParticularMessage("link");
		    			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		    			String mailSubject = dtcm.ReturnParticularMessage("ForgotPassword");
		    			
		    			String mailBody = "<b> Hello "+rs.getString("nm_emp")+"</b>,<br><br><br>"+
		    						  "Your Password has been changed:<br>"+
		    						  "User Name : <b>"+user+"</b><br>"+
		    						  "Password : <b>"+str+"</b><br>"+
		    						  "<br><br><br>"+link+""+
									  "<p>"+footerMsg+"</p>";
		    		
		    			Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
		    			PreparedStatement ps=con.prepareStatement("update M_User_Login set pwd='"+str+"' where nm_login='"+user+"'");
		    			ps.executeUpdate();
						System.out.println(generatePassword(8));
					json.put("data",2);
				}
				else
				{
					json.put("data",1);
				}
			}
				
			catch (Exception e)
			{
				
				e.printStackTrace();
			}
			return json;
		}
	 public JSONObject ChangePassword(String oldpass , String newpass,int id_emp_user )
		{
		 int j=0;
			JSONObject json=new JSONObject();
		 
			try{
			String query="select id_log_user from M_User_Login where pwd='"+oldpass+"' and id_emp_user="+id_emp_user+" ";
			//System.out.println(query);
			PreparedStatement ps=con.prepareStatement(query);
			
			rs = ps.executeQuery();
			if(rs.next())
				{
					
					query="update M_User_Login set pwd ='"+newpass+"' where id_emp_user="+id_emp_user+"";
					System.out.println(query);
					ps=con.prepareStatement(query);
					j=ps.executeUpdate();
					
						j=2;
					
				
				}
			else
			{
				j=1;
			}
			json.put("data",j);
		}
		catch(Exception e)
		{
			System.out.println("Error "+e);
		}
		
		
			return json;
		}
		   private static char[] generatePassword(int length) {
		      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		      String specialCharacters = "!@#$";
		      String numbers = "1234567890";
		      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		      Random random = new Random();
		      char[] password = new char[length];

		      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		      password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		      password[3] = numbers.charAt(random.nextInt(numbers.length()));
		   
		      for(int i = 4; i< length ; i++) {
		         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		      }
		      return password;
		   }
		
}
