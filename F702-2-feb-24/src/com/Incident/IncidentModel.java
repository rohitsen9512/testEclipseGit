package com.Incident;

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

public class IncidentModel {
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	public void SendEmailForCreation(int id_emp_user,String id_ticket,HttpServletRequest request){
		
		String replyMailId="",name="",incident_no="",description="",short_description="",ccMailId="",nm_db="",support="";
    	String mailSql ="select id_emp,nm_emp from M_EMP_USER where id_emp_user='"+id_emp_user+"'";
    	try{
    	DtoCommon dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			replyMailId = rs.getString(1);
			name = rs.getString(2);
		}
		
		mailSql ="select Incident_no, description,short_description from INC_Incidents where id_Incident='"+id_ticket+"'";
    	dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			incident_no = rs.getString(1);
			description = rs.getString(2);
			short_description = rs.getString(3);
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
		String link = dtcm.ReturnParticularMessage("link");
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		String mailSubject = dtcm.ReturnParticularMessage("IncidentCreation");
		
		String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
					  "Incident <b>("+incident_no+")</b> has been created with following details<br>"+
					  "Subject: <b>"+short_description+"</b> <br>"+
					  "Description: <b>"+description+"</b> <br>"+
					  "<br><br><br>"+link+""+
					  "<p>The average turnaround time (TAT) for resolution of tickets will be 24 hrs. for tickets raised on Mon - Fri.</br></p>"+
		               "<p>If the issue/ ticket raised is HIGH PRIORITY in nature, please reach out to Jijiush 99597 20466  or Vidya 88929 71412.</br></p>"+
					  "<p>In case of no response, kindly reach out to SG (99724 55536)<br></p>"+
					  "<br><br><p>"+footerMsg+"<br><br></p>";
	
		Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
    	}catch(Exception e){
    		System.out.println(e);
    	}
	}
public void SendEmailToGroupList(String assign_grp,String id_ticket,HttpServletRequest request){
		
		String replyMailId="",name="",incident_no="",ccMailId="",nm_db="",support="";
		
		
		String mailSql ="select Incident_no from INC_Incidents where id_Incident='"+id_ticket+"'";
		try{
			DtoCommon dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			incident_no = rs.getString(1);
		}
		mailSql ="select DB_NAME() AS [Current Database],c.support_mail from M_Mail_Config c";
		 rs1 = dtcm.GetResultSet(mailSql,  request);
			if(rs1.next())
			{
				
				nm_db= rs1.getString(1);
				support=rs1.getString(2);
				
			}
			 mailSql="select distinct emp.id_emp,emp.nm_emp  from M_Emp_User emp,M_Group g,M_GroupMember mem,INC_Incidents inc where  mem.id_grp=inc.assign_grp and mem.id_emp_user=emp.id_emp_user and inc.assign_grp='"+assign_grp+"'";
	    	//String mailSql ="select id_emp,nm_emp from M_EMP_USER where id_emp_user='"+id_emp_user+"'";
	    	
	    	dtcm = new DtoCommon();
			rs = dtcm.GetResultSet(mailSql,  request);
			while(rs.next())
			{
				replyMailId = rs.getString(1);
				name = rs.getString(2);
			
		List<String> recipients = new ArrayList<String>();
		recipients.add(support);
		String link = dtcm.ReturnParticularMessage("link");
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		String mailSubject = dtcm.ReturnParticularMessage("IncidentCreationForGroup");
		
		String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
					  "Incident <b>("+incident_no+")</b> has been generated for your group.."+
					  "<br><br><br>"+link+""+
					  "<br><br><p>"+footerMsg+"</p>";
	
		Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
    	}
		}
		catch(Exception e){
    		System.out.println(e);
    	}
	}
public void SendEmailToAssignee(String assign_to,String id_incident,HttpServletRequest request){
		
		String replyMailId="",name="",incident_no="",ccMailId="";
    	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+assign_to+"'";
    	try{
    	DtoCommon dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			replyMailId = rs.getString(1);
			name = rs.getString(2);
		}
		
		mailSql ="select Incident_no from INC_Incidents where id_Incident='"+id_incident+"'";
    	dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			incident_no = rs.getString(1);
		}
		
		List<String> recipients = new ArrayList<String>();
		
		String link = dtcm.ReturnParticularMessage("link");
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
		
		String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
					  "Incident <b>("+incident_no+")</b> has been assign to you.."+
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

public void SendEmailToCreatorForIncidentUpdate(String work_notes,String id_ticket, String state,String priority,HttpServletRequest request){
	
	String replyMailId="",name="",Incident_no="",ccMailId="",short_description="",resolution_note="",hold_desc="",cancel_note="",res_code="";
	String mailSql ="select Incident_no,emp.id_emp,emp.nm_emp,short_description,resolution_note, cancel_note,res_code,hold_desc  from INC_Incidents t,M_Emp_User emp where t.req_by=emp.id_emp_user and id_Incident='"+id_ticket+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		replyMailId = rs.getString(2);
		name = rs.getString(3);
		Incident_no = rs.getString(1);
		short_description=rs.getString(4);
		resolution_note = rs.getString(5);
		cancel_note=rs.getString(6);
		res_code=rs.getString(7);
		hold_desc=rs.getString(8);
	}
	
	
	/*mailSql ="select ticket_no from HD_Tickets where id_ticket='"+id_ticket+"'";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql);
	if(rs.next())
	{
		ticket_no = rs.getString(1);
	}
	
	*/
	String res_desbody="";
	 if(state.equals("Resolved"))
		{
		 res_desbody =res_code +" \n "+ resolution_note;
		}
	 if(state.equals("Cancelled"))
		{
		 res_desbody =cancel_note;
		}
	 if(state.equals("Hold"))
		{
		 res_desbody =hold_desc;
		}
	 System.out.println(res_desbody);
	List<String> recipients = new ArrayList<String>();
	
	
	
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("IncidentUpdates");
	
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
				  "Your Incident <b>"+Incident_no+"</b> has below updates.<br>"+
				  "<b>Additional details: </b><br>"+
				  "Short Description: <b>"+short_description+"</b> <br>"+
				  "Priority: <b>"+priority+"</b> <br>"+
				  "State: <b>"+state+"</b> <br>"+
				  "Recent Comment: <br><hr>"+
				  "Work Note:<p><font color =\"red\" face=\"arial\"><b>"+work_notes+"</b></p></font>"+
				  "State Note:<br>"+
				  "<p> <font color=\"Black\" face=\"calibri\"><b>"+res_desbody+"</b></p>"+
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
	
	mailSql ="select Incident_no from INC_Incident_History where id_Incident='"+id_ticket+"'";
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
				  "Incident <b>("+ticket_no+")</b> has been assign to you.."+
				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}


	
}
