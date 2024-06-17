package com.Common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpHeaders;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import com.Login.LoginValidation;

import dto.Common.UserAccessData;




public class Common 
{
	public static Connection con=null;
	public static ResultSet rs = null;
	public static Statement st=null;
	public static PreparedStatement ps=null;
	Properties emailProperties;
	static Session mailSession;
	static MimeMessage emailMessage;
	public static String UrlDB="";
	public static long cancleBid=0;
	public static String tendor_name="";
	public static Connection GetConnection(HttpServletRequest request)
	{
		
		Properties prop = new Properties();	
		InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
		HttpSession session = request.getSession(); 
		
		if (inputStream == null) {
			System.out.println("property file 'configuration.properties' not found in the classpath");
		}
		try
		{
			String dbname=(String)session.getAttribute("dbname");
			//String dbname="ezAtlas-F7O2";
			System.out.println(dbname+"dbnm");
			prop.load(inputStream);
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			String url = prop.getProperty("url");
			String msg = prop.getProperty("Msg");
			//url =url.concat(dbname);
			
			//System.out.println(url);
			
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 con = DriverManager.getConnection(url, userName, password);
			
		}
		catch(Exception e)
		{
			System.out.println("Connection Error... "+e);
		}
		//System.out.println(con+"ji");
		
		return con;
	}
	
	public static Connection GetConnectionForMail()
	{
		
		Properties prop = new Properties();	
		InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
		 
		if (inputStream == null) {
			System.out.println("property file 'configuration.properties' not found in the classpath");
		}
		try
		{
			 
			prop.load(inputStream);
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			String url = prop.getProperty("url");
			String msg = prop.getProperty("Msg");
			//url =url.concat(dbname);
			//System.out.println("url");
			System.out.println(url);
			
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 con = DriverManager.getConnection(url, userName, password);
		}
		catch(Exception e)
		{
			System.out.println("Connection Error... "+e);
		}
		
		return con;
	}
	public static Connection GetConnectionCompanyDB(String company)
	{
		
		Properties prop = new Properties();	
		InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
		
		if (inputStream == null) {
			System.out.println("property file 'configuration.properties' not found in the classpath");
		}
		try
		{
			prop.load(inputStream);
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			String url = prop.getProperty("url");
			
			//url=url.split("=")[0]+"="+company;
			System.out.println(url);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 con = DriverManager.getConnection(url, userName, password);
		}
		catch(Exception e)
		{
			System.out.println("Connection Error... "+e.toString());
		}
		
		return con;
	}
	public static Connection GetConnectionForFreeTrail()
	{
		
		Properties prop = new Properties();	
		InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/freetrail.properties");
		 
		if (inputStream == null) {
			System.out.println("property file 'configuration.properties' not found in the classpath");
		}
		try
		{
			 
			
			prop.load(inputStream);
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			String url = prop.getProperty("url");
			
			System.out.println(url);
			
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 con = DriverManager.getConnection(url, userName, password);
		}
		catch(Exception e)
		{
			System.out.println("Connection Error... "+e);
		}
		
		return con;
	}
	public static String returnQuery(String FlrId){
		String tempQuery="";
		if(!FlrId.equals(""))
		{
			String id_flr1[] = FlrId.split(",");
			for(int i=0 ; i < id_flr1.length ; i++)
			{
				if(tempQuery.equals(""))
				{
					tempQuery = " and (wh.id_flr = "+id_flr1[i]+"";
				}
				else
				{
					tempQuery += " or wh.id_flr = "+id_flr1[i]+"";
				}
			}
			tempQuery += ")";
		}
		
		return tempQuery;
	}
	
	
	public static JSONObject CheckValWhichAllReadyExit(String Table_Name , String colName , String colValue,HttpServletRequest request)
	{
		JSONObject json=new JSONObject();
		String query="select "+colName+" from "+Table_Name+" where "+colName+" =  '"+colValue+"' ";
		int j=0;
		try{
			con=GetConnection(request);
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
		if(rs.next())
			j=1;
		
		json.put("data", j);
		
		}catch(Exception e)
		{
			System.out.println("Error while CheckValWhichAllReadyExit." +e);
		}
		
		return json;
	}
	
	public static ResultSet GetColumns(String Table_Name,HttpServletRequest request)
	{
		String query="SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='"+Table_Name+"' ";
		//String query="SELECT * FROM  "+Table_Name+" ";
		try{
			con=GetConnection(request);
		ps=con.prepareStatement(query);
		rs=ps.executeQuery();
		
		
		}catch(Exception e)
		{
			System.out.println("Here some error.");
		}
		
		return rs;
	}
	public static JSONObject GetDataForDisplayInJsonFormat(String sql,HttpServletRequest request)
	{
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		try
		{
			con=Common.GetConnection(request);
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
		    json.put("data", jarr);
		}
		catch(Exception e)
		{
			System.out.println("sql error in GetDataForDisplayInJsonFormat while getting data for display. "+e);
		}
		
		
		return json;
		
	}
// Mail Configuration Through Company Network	
	
	public static void MailConfiguration(String MailBody , String replyMailId , List<String> recipients , String mailSubject)
{

try
{
	String portNo = "587";
	String hostName = "smtp.office365.com";
	String fromMailId = "no_reply@ezatlas.com";
	final String password = "&F8oD!e9GK}1";

Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");
props.put("mail.smtp.host", hostName);
props.put("mail.smtp.port", portNo);
props.put("mail.smtp.ssl.trust", hostName);
props.put("mail.debug", "true");

Session session = Session.getInstance(props);

		session = Session.getInstance(props, new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() { return new
PasswordAuthentication(fromMailId, password); } });


try{
	MimeMessage message = new MimeMessage(session);
	message.setFrom(new InternetAddress(fromMailId));
	//message.setReplyTo(new Address[]{new InternetAddress(replyMailId)});
	message.addRecipient(Message.RecipientType.TO, new InternetAddress(replyMailId));
		for(String recipient: recipients){
				message.addRecipient(Message.RecipientType.CC,new InternetAddress(recipient));
}
					message.setSubject(mailSubject);
					message.setContent(MailBody,"text/html");
Transport.send(message);

System.out.println("Mail Sent...");

}catch (MessagingException mex) {
mex.printStackTrace();

}
}
catch(Exception e)
{
System.out.println("Mail Configuration Error... "+e.toString());
}

}
	// Mail Configuration for Notify
//		public static void GetwhatsappMessage(String Message,String employeename,String number)
//		{
//			System.out.println("GetwhatsappMessage");
//			System.out.println(number);
//			
//			Properties prop = new Properties();	
//			InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/mailConfiguration.properties");
//			
//			if (inputStream == null) {
//				System.out.println("property file 'mailConfiguration.properties' not found in the classpath");
//			}
//			try
//			{
//				prop.load(inputStream);
//				String portNo = prop.getProperty("portNo");
//				String hostName = prop.getProperty("hostName");
//				String fromMailId = "";
//			    String access_token = prop.getProperty("access_token_whatsapp");
//		    	String url = prop.getProperty("url_whatsapp");
//		    	String encodedBody = "{\r\n"
//		    			+ "                \"company-name\" : \"www.ezatlas.com\",\r\n"
//		    			+ "                \"event-data\": {\r\n"
//		    			+ "                    \"event-name\" : \"test\",\r\n"
//		    			+ "                    \"event-id\": \"5902\"\r\n"
//		    			+ "                },\r\n"
//		    			+ "                \"parameters\":{\r\n"
//		    			+ "                    \"phone-number\":\""+number+"\",\r\n"
//		    			+ "                    \"variables\":[\"Hi "+employeename+",\""+Message+"\",,,,,],\r\n"
//		    			+ "                    \"url-variable\":\"url-endpoint\",\r\n"
//		    			+ "                    \"header\" : {\r\n"
//		    			+ "                        \"link\" : \"https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aHVtYW58ZW58MHx8MHx8fDA%3D&w=1000&q=80\",\r\n"
//		    			+ "                    \"filename\":\"required if the media type is of document type\"\r\n"
//		    			+ "                    }\r\n"
//		    			+ "                }\r\n"
//		    			+ "            }";
//		    	 HttpClient httpClient = HttpClientBuilder.create().build();
//		            HttpPost request = new HttpPost(url);
//		            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
//		             	request.addHeader("Authorization", "Bearer"+access_token);
//		            StringEntity params = new StringEntity(encodedBody);
//		            request.setEntity(params);
//
//		            HttpResponse response = httpClient.execute(request);
//		            HttpEntity entity = response.getEntity();
//		            if (entity != null) {
//		                String responseString = EntityUtils.toString(entity);
//		                // Parse the JSON response to retrieve the refreshed access token
//		                // Sample response: {"access_token":"YOUR_REFRESHED_ACCESS_TOKEN","expires_in_sec":3600}
//		                // Extract the access token value from the response
//		                ObjectMapper objectMapper = new ObjectMapper();
//		                JsonNode jsonNode = objectMapper.readTree(responseString);
//		                String refreshedAccessToken = jsonNode.get("access_token").asText();   
//		            }     
//	    	    
//			}
//			catch(Exception e)
//			{
//				System.out.println("Mail Configuration Error... "+e);
//			}
//			
//		}	
// Mail Configuration for Notify
//	public static void MailConfigurationForNotify(String MailBody , String replyMailId , List<String> recipients , String mailSubject , int id_emp_user)
//	{
//		Properties prop = new Properties();	
//		InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/mailConfiguration.properties");
//		
//		if (inputStream == null) {
//			System.out.println("property file 'mailConfiguration.properties' not found in the classpath");
//		}
//		try
//		{
//			prop.load(inputStream);
//			String portNo = prop.getProperty("portNo");
//			String hostName = prop.getProperty("hostName");
//			String fromMailId = "";
//			
//				String sql="select id_emp from M_Emp_User where id_emp_user="+id_emp_user+"";
//				con=GetConnection(request);
//				ps=con.prepareStatement(sql);
//				rs=ps.executeQuery();
//			if(rs.next()){
//				fromMailId = rs.getString(1);
//			}
//			
//		//String content = "Testing.";
//    	//List<String> recipients = new ArrayList<String>();
//    	//recipients.add("shebeer_hassain@intuit.com");
//		
//    	    Properties props = new Properties();
//    	    props.put("mail.smtp.auth", "false");
//    	    props.put("mail.smtp.starttls.enable", "true");
//    	    props.put("mail.smtp.host", hostName);	
//    	    props.put("mail.smtp.port", portNo);
//    	    props.put("mail.smtp.ssl.trust", hostName);
//    	    props.put("mail.debug", "true");
//
//    	    Session session = Session.getInstance(props);
//    	    try{
//    	         MimeMessage message = new MimeMessage(session);
//    	         message.setFrom(new InternetAddress(fromMailId));
//    	         //message.setReplyTo(new Address[]{new InternetAddress(replyMailId)});
//    	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(replyMailId));
//    	         for(String recipient: recipients){
//    	             message.addRecipient(Message.RecipientType.CC,new InternetAddress(recipient));
//    	         }
//    	         message.setSubject(mailSubject);
//    	         message.setContent(MailBody,"text/html");
//    	         Transport.send(message);
//    	         
//    	      }catch (MessagingException mex) {
//    	         mex.printStackTrace();
//    	         
//    	      } 
//		}
//		catch(Exception e)
//		{
//			System.out.println("Mail Configuration Error... "+e);
//		}
//		
//	}
	
	
	
// Mail Configuration Through Gmail	
	
//	public static void MailConfiguration(String MailBody , String replyMailId , List<String> recipients , String mailSubject)
//	{
//		//ToUserID = "dsptesting2@gmail.com";
//		 try {                        
//		    	Common javaEmail = new Common();
//		        javaEmail.setMailServerProperties();            
//
//		        javaEmail.createEmailMessage(replyMailId , MailBody, recipients , mailSubject) ;
//		        javaEmail.sendEmail();
//		    } catch (AddressException ex) {
//		        System.out.println("ADDRESS EXCEPTION  "+ex);
//		    } catch (MessagingException ex) {
//		        System.out.println("MESSAGING EXCEPTION  "+ex);
//		    }        
//		
//		
//		
//	}
	
	public static void sendEmail() throws AddressException, MessagingException {

		 String fromUser = "";
	      
	        String fromUserEmailPassword = "";
		Properties prop = new Properties();	
		InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/mailConfiguration.properties");
		
		if (inputStream == null) {
			System.out.println("property file 'mailConfiguration.properties' not found in the classpath");
		}
		try
		{
			prop.load(inputStream);
			fromUser = prop.getProperty("gmailid");
			fromUserEmailPassword= prop.getProperty("gmailpassword");
		
		}
		catch(Exception e)
		{
			System.out.println("Mail Configuration Error... "+e);
		}
		
		
        String emailHost = "smtp.gmail.com";
		//String emailHost="email-smtp.us-west-2.amazonaws.com";
		
      //  String fromUser = "hcstechnotest@gmail.com";
       // String fromUser = "intuitfam@gmail.com";//just the id alone without @gmail.com
      //  String fromUserEmailPassword = "12345!@#$%hcstechno";

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserEmailPassword);
       // transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
	public void createEmailMessage(String replyMailId , String emailBody , List<String> recipients , String mailSubject) throws AddressException,
    MessagingException {

    //String emailBody = "Just For test perpose.";

    mailSession = Session.getDefaultInstance(emailProperties, null);
    emailMessage = new MimeMessage(mailSession);
   // emailMessage.setReplyTo(new Address[]{new InternetAddress(replyMailId)});
    emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(replyMailId));
    for(String recipient: recipients){
    	emailMessage.addRecipient(Message.RecipientType.CC,new InternetAddress(recipient));
    }

    emailMessage.setSubject(mailSubject);
    emailMessage.setContent(emailBody, "text/html");//for a html email
   // emailMessage.setText(emailBody);// for a text email

}
	
	 public void setMailServerProperties() {

	        //String emailPort = "933";//gmail's smtp port
	       String emailPort = "587";//gmail's smtp port
	       // System.setProperty("java.net.preferIPv4Stack" , "true");
	        emailProperties = System.getProperties();
	        //emailProperties =  new Properties();
	        emailProperties.put("mail.smtp.port", emailPort);
	        emailProperties.put("mail.smtp.auth", "false");
	        emailProperties.put("mail.smtp.starttls.enable", "true");
	       // emailProperties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	    }   
	 
		// (Mail Through Company Network)
//	 public static void MailConfiguration(String MailBody , String replyMailId , List<String> recipients , String mailSubject)
//		{
//		 String portNo="",hostName="",fromMailId1="",password1="";
//		 
//			Properties prop = new Properties();	
//			InputStream inputStream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/mailConfiguration.properties");
//			
//			if (inputStream == null) {
//				System.out.println("property file 'mailConfiguration.properties' not found in the classpath");
//			}
//			try
//			{
//				String mailSql ="select no_port,mail_id,mail_pwd,nm_host from M_Mail_Config";
//				ps=con.prepareStatement(mailSql);
//				rs=ps.executeQuery();
//				if(rs.next())
//				{
//					portNo = rs.getString(1);
//					hostName=rs.getString(4);
//					fromMailId1=rs.getString(2);
//					password1=rs.getString(3);
//				}
//				prop.load(inputStream);
//			//String portNo = prop.getProperty("portNo");
//			//String hostName = prop.getProperty("hostName");
//		final String fromMailId = fromMailId1;
////
//		final String password = password1;
//
//		Properties props = new Properties();
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", hostName);
//		props.put("mail.smtp.port", portNo);
//		props.put("mail.smtp.ssl.trust", hostName);
//		props.put("mail.debug", "true");
//		
//		Session session = Session.getInstance(props);
//		
//		session = Session.getInstance(props, new javax.mail.Authenticator() {
//		protected PasswordAuthentication getPasswordAuthentication() { return new
//		PasswordAuthentication(fromMailId, password); } });
//		
//		
//		try{
//		MimeMessage message = new MimeMessage(session);
//		message.setFrom(new InternetAddress(fromMailId));
//		//message.setReplyTo(new Address[]{new InternetAddress(replyMailId)});
//		message.addRecipient(Message.RecipientType.TO, new InternetAddress(replyMailId));
//		for(String recipient: recipients){
//		message.addRecipient(Message.RecipientType.CC,new InternetAddress(recipient));
//		}
//		message.setSubject(mailSubject);
//		message.setContent(MailBody,"text/html");
//		Transport.send(message);
//		
//		System.out.println("Mail Sent...");
//		
//		}catch (MessagingException mex) {
//		mex.printStackTrace();
//		
//		}
//		}
//		catch(Exception e)
//		{
//		System.out.println("Mail Configuration Error... "+e.toString());
//		}
//		
//		}

	 
	 public static void MailConfigurationForCompanysendbackticket(String MailBody , String replyMailId , List<String> recipients , String mailSubject,String comp_db)
		{
		 String portNo="",hostName="",fromMailId1="",password1="";
			Properties prop = new Properties();	
			con = Common.GetConnectionForMail();

			try
			{
				String mailSql ="select helpdesk_nm_port_smtp,helpdesk_nm_mail,helpdesk_pwd,helpdesk_nm_host_smtp from M_Mail_Config";
				ps=con.prepareStatement(mailSql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					portNo = rs.getString(1);
					
					fromMailId1=rs.getString(2);
					password1=rs.getString(3);
					hostName=rs.getString(4);
				}
				final String fromMailId = fromMailId1;
				final String password = password1;
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", hostName);
				props.put("mail.smtp.port", portNo);
				props.put("mail.smtp.ssl.trust", hostName);
				props.put("mail.debug", "true");
				
				Session session = Session.getInstance(props);
				
				session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() { return new
				PasswordAuthentication(fromMailId, password); } });
				
				
				try{
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(fromMailId));
				//message.setReplyTo(new Address[]{new InternetAddress(replyMailId)});
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(replyMailId));
				for(String recipient: recipients){
				message.addRecipient(Message.RecipientType.CC,new InternetAddress(recipient));
				}
				message.setSubject(mailSubject);
				message.setContent(MailBody,"text/html");
				Transport.send(message);
				
				System.out.println("Mail Sent...");
				
				}catch (MessagingException mex) {
				mex.printStackTrace();
				
				}
				
				con.close();
				}
				catch(Exception e)
				{
				System.out.println("Mail Configuration Error... "+e.toString());
				}
			
		}
	 public static void MailConfigurationForNotAutherizedToCreateTicket(String MailBody , String replyMailId , List<String> recipients , String mailSubject,String compname)
		{
		 String portNo="",hostName="",fromMailId1="",password1="";
			Properties prop = new Properties();	
			con = Common.GetConnectionForFreeTrail();
			

			try
			{
				String mailSql ="select helpdesk_nm_port_smtp,helpdesk_nm_mail,helpdesk_pwd,helpdesk_nm_host_smtp from M_Mail_Config";
				ps=con.prepareStatement(mailSql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					portNo = rs.getString(1);
					
					fromMailId1=rs.getString(2);
					password1=rs.getString(3);
					hostName=rs.getString(4);
				}
				final String fromMailId = fromMailId1;
				final String password = password1;
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", hostName);
				props.put("mail.smtp.port", portNo);
				props.put("mail.smtp.ssl.trust", hostName);
				props.put("mail.debug", "true");
				
				Session session = Session.getInstance(props);
				
				session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() { return new
				PasswordAuthentication(fromMailId, password); } });
				
				
				try{
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(fromMailId));
				//message.setReplyTo(new Address[]{new InternetAddress(replyMailId)});
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(replyMailId));
				for(String recipient: recipients){
				message.addRecipient(Message.RecipientType.CC,new InternetAddress(recipient));
				}
				message.setSubject(mailSubject);
				message.setContent(MailBody,"text/html");
				Transport.send(message);
				
				System.out.println("Mail Sent...");
				
				}catch (MessagingException mex) {
				mex.printStackTrace();
				
				}
				
				con.close();
				}
				catch(Exception e)
				{
				System.out.println("Mail Configuration Error... "+e.toString());
				}
			
		}

	
	 public static void SendwhatsappMassege(String Message,String employeename,String replynumber) {
		 
		 System.out.println(replynumber+"what");
		 System.out.println(Message+"what");
	        String url = "https://app.kwiqreply.io/v2.0/trigger_event?token=b2daCK1eXFL7qwVC6uCeDRPUEiihkv";
	        String postData = "{\r\n" + 
	        		"                \"company-name\" : \"www.ezatlas.com\",\r\n" + 
	        		"                \"event-data\": {\r\n" + 
	        		"                    \"event-name\" : \"test\",\r\n" + 
	        		"                    \"event-id\": \"5902\"\r\n" + 
	        		"                },\r\n" + 
	        		"                \"parameters\":{\r\n" + 
	        		"                    \"phone-number\":\""+replynumber+"\",\r\n" + 
	        		"                    \"variables\":[\" "+employeename+"\",\""+Message+"\",\"sad\",\"dfg\",\"gfh\",\"fgh\",\"fgh\"],\r\n" + 
	        		"                    \"url-variable\":\"url-endpoint\",\r\n" + 
	        		"                    \"header\" : {\r\n" + 
	        		"                        \"link\" : \"https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aHVtYW58ZW58MHx8MHx8fDA%3D&w=1000&q=80\",\r\n" + 
	        		"                    \"filename\":\"required if the media type is of document type\"\r\n" + 
	        		"                    }\r\n" + 
	        		"                }\r\n" + 
	        		"            }"; // Your POST data
	        
	        try {
	            URL urlObj = new URL(url);
	            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
	            
	            // Set the necessary HTTP Method and headers
	            connection.setRequestMethod("POST");
	            connection.setRequestProperty("Content-Type", "application/json");
	            connection.setRequestProperty("Content-Length", String.valueOf(postData.length()));
	            connection.setRequestProperty("Authorization", "Bearer 53522eb8-bac3-4f13-bf24-6880ac3798ef"); // Add your custom header
	            
	            connection.setDoOutput(true);
	            
	            // Write the POST data to the connection
	            try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
	                dataOutputStream.writeBytes(postData);
	                dataOutputStream.flush();
	            }
	            
	            // Get the response from the server
	            int responseCode = connection.getResponseCode();
	            
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                String inputLine;
	                StringBuilder response = new StringBuilder();
	                
	                while ((inputLine = in.readLine()) != null) {
	                    response.append(inputLine);
	                }
	                in.close();
	                
	                System.out.println("Response: " + response.toString());
	            } else {
	                System.out.println("HTTP POST request failed with response code: " + responseCode);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
}
