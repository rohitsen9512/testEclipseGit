package com.HelpDesk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.Common.Common;

import dto.Common.DtoCommon;

public class HelpDeskModel {
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	public void SendEmailToAssignmentGroup(int id_emp_user,String id_ticket,HttpServletRequest request){
		
		String replyMailId="",name="",ticket_no="",ccMailId="",nm_db="",support="";
    	String mailSql ="select id_emp,nm_emp from M_EMP_USER where id_emp_user='"+id_emp_user+"'";
    	try{
    	DtoCommon dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			replyMailId = rs.getString(1);
			name = rs.getString(2);
		}
		
		mailSql ="select ticket_no from HD_Tickets where id_ticket='"+id_ticket+"'";
    	dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			ticket_no = rs.getString(1);
		}
		mailSql ="select DB_NAME() AS [Current Database],c.support_mail from M_Mail_Config c";
		 rs1 = dtcm.GetResultSet(mailSql,  request);
			if(rs1.next())
			{
				
				nm_db= rs1.getString(1);
				support=rs1.getString(2);
				
			}
		List<String> recipients = new ArrayList<String>();
		recipients.add(support);
		String link = dtcm.ReturnParticularMessage("baseUrlLink");
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		String mailSubject = dtcm.ReturnParticularMessage("ticketCreation");
		
		String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
					  "Ticket <b>("+ticket_no+")</b> has been created.."+
					  "<br>For login....<a href='"+link+""+nm_db+".html'>Click Here</a>"+
					  "<br><br><p>"+footerMsg+"</p>";
	
		Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
    	}catch(Exception e){
    		System.out.println(e);
    	}
	}
	
public void SendEmailToAssignee(String assign_to,String id_ticket,HttpServletRequest request){
		
		String replyMailId="",name="",ticket_no="",ccMailId="";
    	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+assign_to+"'";
    	try{
    	DtoCommon dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			replyMailId = rs.getString(1);
			name = rs.getString(2);
		}
		
		mailSql ="select ticket_no from HD_Tickets where id_ticket='"+id_ticket+"'";
    	dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			ticket_no = rs.getString(1);
		}
		
		List<String> recipients = new ArrayList<String>();
		
		String link = dtcm.ReturnParticularMessage("link");
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
		
		String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
					  "Ticket <b>("+ticket_no+")</b> has been assign to you.."+
					  "<br><br><br>"+link+""+
					  "<p>"+footerMsg+"</p>";
	
		Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
    	}catch(Exception e){
    		System.out.println(e);
    	}
	}

public void SendEmailToAssignee1(String mail,String id_ven,HttpServletRequest request){
	
	String replyMailId="",nm_ven="",user_name="",otp="",ccMailId="";
	String mailSql ="";
	try{
		replyMailId = mail;
		System.out.println(replyMailId);
	mailSql ="select nm_ven,otp from M_Vendor where id_ven='"+id_ven+"'";
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		//mail = rs.getString(1);
		nm_ven = rs.getString(1);
		otp=rs.getString(2);
	}
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg =dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject =dtcm.ReturnParticularMessage("temporaryPassword");
	
	String mailBody = "<b> Hello "+nm_ven+"</b>,<br><br><br>"+
				  "User Name <b>("+replyMailId+")</b> has been Registered.."+
				  "<br><br><br>"+link+""+
				  "Please use this <b>("+otp+")</b> For Login.."+
				  "<p>"+footerMsg+"</p>";

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}

////

public void SendEmailToCreatorForTicketUpdate(String work_notes,String id_ticket, String state,String priority,HttpServletRequest request){
	
	String replyMailId="",name="",ticket_no="",ccMailId="",short_description="";
	String mailSql ="select ticket_no,emp.id_emp,emp.nm_emp,short_description from HD_Tickets t,M_Emp_User emp where t.req_by=emp.id_emp_user and id_ticket='"+id_ticket+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		replyMailId = rs.getString(2);
		name = rs.getString(3);
		ticket_no = rs.getString(1);
		short_description=rs.getString(4);
	}
	
	
	/*mailSql ="select ticket_no from HD_Tickets where id_ticket='"+id_ticket+"'";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql);
	if(rs.next())
	{
		ticket_no = rs.getString(1);
	}
	*/
	List<String> recipients = new ArrayList<String>();
	
	
	
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketUpdates");
	
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
				  "Your ticket <b>("+ticket_no+")</b> has below updates.<br>"+
				  "<b>Additional details: </b><br>"+
				  "Short Description: <b>("+short_description+")</b> <br>"+
				  "Priority: <b>("+priority+")</b> <br>"+
				  "State: <b>("+state+")</b> <br>"+
				  "Recent Comment: <br><hr>"+
				  "<p><font color =\"red\" face=\"arial\" ><b>"+work_notes+"</b></p></font>"+
				
				  "<p>"+footerMsg+"</p>";
	
	
	
	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	
	}catch(Exception e){
		System.out.println(e);
	}
}

///

public void SendEmailToOpenByAndAdditional(String add_by,String id_ticket,String additional_email_ids,HttpServletRequest request){
	
	String replyMailId="",name="",ticket_no="",ccMailId="";
	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+add_by+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		replyMailId = rs.getString(1);
		name = rs.getString(2);
	}
	
	mailSql ="select ticket_no from HD_Tickets where id_ticket='"+id_ticket+"'";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		ticket_no = rs.getString(1);
	}
	
	List<String> recipients = new ArrayList<String>();
	if(!additional_email_ids.equals(""))
		recipients.add(additional_email_ids);
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketCreated");
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
				  "Ticket <b>("+ticket_no+")</b> has been assign to you.."+
				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}
	
}
