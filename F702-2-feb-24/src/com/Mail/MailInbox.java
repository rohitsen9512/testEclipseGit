package com.Mail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;

import com.Common.Common;

import dto.Common.DtoCommon;
import dto.Common.UserAccessData;

public class MailInbox {

	public static Connection con = null;
	public static ResultSet rs = null;
	public static PreparedStatement ps = null;
	public static Statement stmt = null;

	private static Properties getServerProperties(String protocol, String host, String port) {
		Properties properties = new Properties();
		properties.put(String.format("mail.%s.host", protocol), host);
		properties.put(String.format("mail.%s.port", protocol), port);
		properties.setProperty(String.format("mail.%s.socketFactory.class", protocol),
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
		properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));

		return properties;
	}

	public static void getMailinboxdata() {
		System.out.println("getMailinboxdata Function Called !!");
		String protocol = "", host = "", port = "", userName = "", password = "";
		try {

			Properties prop = new Properties();
			InputStream inputStream = UserAccessData.class.getClassLoader()
					.getResourceAsStream("com/Resources/mailConfiguration.properties");

			if (inputStream == null) {
				System.out.println("property file 'configuration.properties' not found in the classpath");
			}
			prop.load(inputStream);
			con = Common.GetConnectionForFreeTrail();
			ps = con.prepareStatement("select comp from FreeTrail ");
			ResultSet rs1 = ps.executeQuery();
			String compname = "";
			while (rs1.next()) {

				compname = rs1.getString(1);
				con = Common.GetConnectionCompanyDB(compname);
				System.out.println(compname);
				System.out.println("select * from M_Mail_Config");
				ps = con.prepareStatement("select * from M_Mail_Config");
				ResultSet rs2 = ps.executeQuery();
				if (rs2.next()) {
					System.out.println(rs2.getString("helpdesk_nm_mail"));
					if (rs2.getString("helpdesk_nm_mail").contains("@")) {
						compname = rs1.getString(1);
						protocol = prop.getProperty("protocol");
						host = rs2.getString("helpdesk_nm_host");
						port = rs2.getString("helpdesk_nm_port");
						userName = rs2.getString("helpdesk_nm_mail");
						password = rs2.getString("helpdesk_pwd");
					}

					else {
						compname = "hcstechno";
						protocol = prop.getProperty("protocol");
						host = prop.getProperty("inboxMailhost");
						port = prop.getProperty("inboxMailport");
						userName = prop.getProperty("inboxMailuserName");
						password = prop.getProperty("inboxMailpassword");
					}

				}

				con.close();
				getNewEmails(protocol, host, port, userName, password, compname);
			}

		} catch (Exception e) {
			System.out.println("error db mail");
			e.printStackTrace();
		}

	}

	public static void getNewEmails(String protocol, String host, String port, String userName, String password,
			String compname) {

		Properties properties = getServerProperties(protocol, host, port);
		Session session = Session.getDefaultInstance(properties);

		try {

			Properties prop = new Properties();
			InputStream inputStream = UserAccessData.class.getClassLoader()
					.getResourceAsStream("com/Resources/mailConfiguration.properties");

			if (inputStream == null) {
				System.out.println("property file 'configuration.properties' not found in the classpath");
			}
			prop.load(inputStream);
			Store store = session.getStore(protocol);
			store.connect(userName, password);

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			/*
			 * int count = inbox.getMessageCount();
			 * System.out.println("Count Mail msg---"+count); Message[] messages =
			 * inbox.getMessages(1, count); for (Message message : messages) {
			 * System.out.println("reading msg"); System.out.println(message.getFlags()); if
			 * (!message.getFlags().contains(Flags.Flag.SEEN)) {
			 */

			Flags seen = new Flags(Flags.Flag.FLAGGED);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);

			Message messages[] = inbox.search(unseenFlagTerm);

			System.out.println("No of email has been found: ---" + messages.length);
			if (messages.length > 0) {
				for (int i = 0, n = messages.length; i < n; i++) {

					Message message = messages[i];
					message.setFlag(Flags.Flag.FLAGGED, true);

					String subject = message.getSubject();
					String From = message.getFrom()[0].toString();
					Address[] toList = message.getRecipients(Message.RecipientType.TO);
					String tlist = "", clist = "", bclist = "";

					for (Address address : toList) {
						tlist = tlist + address;
					}
					Address[] ccList = message.getRecipients(Message.RecipientType.CC);
					if (ccList != null) {
						for (Address address : ccList) {
							clist = clist + "," + address;
						}
					}
					Address[] bccList = message.getRecipients(Message.RecipientType.BCC);
					if (bccList != null) {
						for (Address address : bccList) {
							bclist = bclist + "," + address;
						}
					}
					System.out.println("to---" + tlist);
					System.out.println("cc---" + clist);
					System.out.println("bcc---" + bclist);

					String bodyText = "";
					String bodyhtml = "";
					Message message1 = messages[0];
					Object content = message.getContent();

					if (content instanceof String) {
						bodyText = bodyText + content;

					} else if (content instanceof Multipart) {
						Multipart multiPart = (Multipart) content;
						int multiPartCount = multiPart.getCount();
						for (int k = 0; k < multiPartCount; k++) {
							BodyPart bodyPart = multiPart.getBodyPart(k);
							Object multipartobject;
							multipartobject = bodyPart.getContent();
							if (k == 0) {
								if (multipartobject instanceof String) {
									bodyText = bodyText + multipartobject;

								}
							} else {
								if (multipartobject instanceof String) {
									bodyhtml = bodyhtml + multipartobject;

								}
							}

						}

					}

					String mail_type = "";

					if (From.contains(userName)) {
						mail_type = "Sent";
					} else {
						mail_type = "Received";
					}
					System.out.println(From);

					String correctmail = From, likesearchmail = "";
					if (From.contains("<")) {
						String getmail[] = From.split("<");
						likesearchmail = getmail[1].substring(0, getmail[1].length() - 1);
						correctmail = getmail[1].substring(0, getmail[1].length() - 1);

					} else {

						likesearchmail = From;
					}
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
					// insert in company db...
					String sql = "insert into Mail_Inbox( sub_mailinbox,from_mailinbox,cc,bcc,mail_type,body) values('"
							+ subject.replaceAll("'", "\''") + "','" + From + "','" + clist + "','" + bclist + "','"
							+ mail_type + "', '" + likesearchmail + "-" + formatter.format(date) + ".html' )";
					System.out.println(sql);

					// con = Common.GetConnectionForMail();
					con = Common.GetConnectionCompanyDB(compname);
					PreparedStatement ps1 = con.prepareStatement(sql);
					ps1.executeUpdate();

					String MailFilePath = prop.getProperty("MailFilePath");

					MailFilePath = MailFilePath + "\\" + compname;

					File file = new File(MailFilePath);

					// true if the directory was created, false otherwise
					if (file.mkdirs()) {
						System.out.println("Directory is created!");
					} else {
						System.out.println("Already to create directory!");
					}

					/*
					 * File f = new File(MailFilePath + "\\" + correctmail + "-" +
					 * subject.replaceAll("[$&+,:;=?@#|'<>.^*()%!-]\\/", "-") + "-" +
					 * formatter.format(date) + ".html");
					 */
					
					File f = new File(MailFilePath + "\\" + correctmail + "-" + formatter.format(date) + ".html");
					
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					bw.write(bodyText);
					String filePath = f.getAbsolutePath();
					
					bw.close();
					String compdbname = "";
					int validuser = 0;
					
					con = Common.GetConnectionForFreeTrail();
					
					System.out.println("select nm_db_comp from DM_Mail where  (mail_cmp LIKE '%"
							+ likesearchmail.split("@")[1] + "%')");
					ps = con.prepareStatement("select nm_db_comp from DM_Mail where  (mail_cmp LIKE '%"
							+ likesearchmail.split("@")[1] + "%')");
					rs = ps.executeQuery();
					if (rs.next()) {
						compdbname = rs.getString(1);
						validuser = 1;
					}

					if (!From.contains(userName)) {

						if (validuser == 1)
							createticket(likesearchmail, bodyText, subject, compdbname, compdbname,filePath);
						else
							SendEmailForNotAutherizedToCreateTicket(likesearchmail);

					}
					con.close();
				}

			}

			inbox.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void createticket(String mailid, String body, String subject, String comp_db, String compname, String filePath) {
		// con = Common.GetConnectionForMail();
		con = Common.GetConnectionCompanyDB(compname);
		String id = "", ticket_no = "";
		int id_ticket = 0;
		String name = "";
		try {

			ps = con.prepareStatement("select id_emp_user,nm_emp from M_Emp_User where id_emp='" + mailid.trim() + "'");
			ResultSet rs1 = ps.executeQuery();

			if (rs1.next()) {
				id = rs1.getString(1);
				name = rs1.getString(2);

			} else {

				String qry = "insert into M_Emp_User(status_emp,cd_emp,nm_emp,id_emp) values ('Working' , '"
						+ mailid.split("@")[0] + "','" + mailid.split("@")[0] + "','" + mailid.trim() + "')";
				ResultSet rs2 = ps.executeQuery();

				stmt = con.createStatement();
				stmt.executeUpdate(qry, Statement.RETURN_GENERATED_KEYS);
				rs2 = stmt.getGeneratedKeys();
				if (rs2.next()) {
					id = Integer.toString(rs2.getInt(1));
					name = mailid.split("@")[0];
				}
			}
			System.out.println("select max(id_ticket) from HD_Tickets");
			ps = con.prepareStatement("select max(id_ticket) from HD_Tickets");
			rs = ps.executeQuery();
			if (rs.next()) {

				int ticktNo = rs.getInt(1) + 1;
				String formatValue = String.format("%05d", ticktNo);

				ticket_no = "TICKET-" + formatValue;
			}
			body = Jsoup.parse(body).text();
			String query = "insert into HD_Tickets(ticket_no,dt_req,req_by,short_description,filePath) values('"
					+ ticket_no + "',FORMAT(GetDate(), 'yyyy-MM-dd')," + id + ",'" + subject.replaceAll("'", "\''")
					+ "','" +filePath + "' )";
			
			System.out.println(query);
			stmt = con.createStatement();
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {

				id_ticket = rs.getInt(1);

			}

			query = "insert into HD_Ticket_History(work_note,id_ticket,comment_by) values('"
					+ body.replaceAll("'", "\''") + "'," + id_ticket + "," + id + ")";
			System.out.println(query);
			ps = con.prepareStatement(query);
			ps.executeUpdate();
			
			

			MailInbox.SendEmailToOpenByAndAdditional(mailid.trim(), name, ticket_no, compname);

			if(!con.isClosed())
				con.close();
			
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void SendEmailToOpenByAndAdditional(String replyMailId, String name, String ticket_no,
			String compname) {

		String ccMailId = "";
		try {
			DtoCommon dtcm = new DtoCommon();

			List<String> recipients = new ArrayList<String>();

			String link = dtcm.ReturnParticularMessage("link");
			String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
			String mailSubject = "Ticket - "+ticket_no+" has been opened on behalf of you";

			String mailBody = "<b> Hello " + name + "</b>,<br><br><br>"
					+ "Thank you for reaching out to us.<br> Our internal team noticed that a ticket<b>(" + ticket_no
					+ ")</b> has been raised. <br> Soon our production support team will reachout to you in case need any additional information."
					+

					"<p>" +footerMsg+ "</p>";
			
			Common.MailConfigurationForCompanysendbackticket(mailBody, replyMailId, recipients, mailSubject, compname);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void SendEmailForNotAutherizedToCreateTicket(String replyMailId) {

		String ccMailId = "";
		try {
			DtoCommon dtcm = new DtoCommon();

			List<String> recipients = new ArrayList<String>();

			String mailBody = "<b> Hello " + replyMailId + "</b>,<br><br><br>"
					+ "You are noth Authorized to create support ticket !! Please check with your administrator. " +

					"<p>Regards <br>HCS TEAM</p>";

			Common.MailConfigurationForNotAutherizedToCreateTicket(mailBody, replyMailId, recipients,
					"Not Authorized To Create Support Ticket", "");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
