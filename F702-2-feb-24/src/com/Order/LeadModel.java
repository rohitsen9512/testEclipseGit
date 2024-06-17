package com.Order;
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

public class LeadModel {

	
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	


public void SendEmailToAssignee(String assign_to,int id_lead_m,HttpServletRequest request){
		
		String replyMailId="",name="",lead_no="",ccMailId="";
		
    	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+assign_to+"'";
    	try{
    	DtoCommon dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			replyMailId = rs.getString(1);
			name = rs.getString(2);
		}
		
		mailSql ="select lead_no from O_lead_Master where id_lead_m="+id_lead_m+"";
    	dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			lead_no = rs.getString(1);
			System.out.println(lead_no);
		}
		
		List<String> recipients = new ArrayList<String>();
		
		String link = dtcm.ReturnParticularMessage("link");
		String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
		String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
		
		String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
					  "Lead <b>("+lead_no+")</b> has been assign to you for further processing.."+
					  "<br><br><br>"+link+""+
					  "<p>"+footerMsg+"</p>";
	
		Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
    	}catch(Exception e){
    		System.out.println(e);
    	}
	}
public void SendEmailToCustomerforTrackingcordential(String id_lead_m,String username,String pwd ,HttpServletRequest request){
	
	String replyMailId="",name="",lead_no="",ccMailId="";
	
	//String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+id_lead_m+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
//	rs = dtcm.GetResultSet(mailSql,  request);
//	if(rs.next())
//	{
//		replyMailId = rs.getString(1);
//		name = rs.getString(2);
//	}
	
	String mailSql ="select lead_no,pstn_email from O_lead_Master where id_lead_m="+id_lead_m+"";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		lead_no = rs.getString(1);
		replyMailId = rs.getString(2);
		System.out.println(lead_no);
	}
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String mailBody = "<b> Hello "+username+"</b>,<br><br><br>"+
				  "Lead <b>("+lead_no+")</b> has is confirm for you for this your Userame ("+pwd+")  and  password ("+pwd+")"+
				  "<br> for login  "+
				  "<p>"+footerMsg+"</p>";

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println("customerCordential"+e);
	}
}
public void SendEmailToTagperson(String assign_to,String id_lead_m,HttpServletRequest request){
	
	String replyMailId="",name="",lead_no="",ccMailId="",nm_subl="",address="";
	
	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+assign_to+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		replyMailId = rs.getString(1);
		name = rs.getString(2);
	}
	
	mailSql ="select lead_no,ms.nm_subl,address from O_lead_Master ledm,M_Subloc ms where  ledm.id_sloc=ms.id_sloc and id_lead_m='"+id_lead_m+"'";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		lead_no = rs.getString(1);
		nm_subl = rs.getString(2);
		address= rs.getString(3);
		System.out.println(lead_no);
	}
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
				  "Lead <b>("+lead_no+")</b> has been Tag to you for delivery to "+address+" "+nm_subl+""+
				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println("emailtag"+e);
	}
}

//for delivery mail to cordinator
public void SendEmailToAssignee1(String tag_by,String id_lead_m,HttpServletRequest request){
	
	String replyMailId="",name="",lead_no="",ccMailId="";
	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+tag_by+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		replyMailId = rs.getString(1);
		name = rs.getString(2);
	}
	
	mailSql ="select lead_no from  O_Assign_Lead_Master where id_lead_m='"+id_lead_m+"'";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		lead_no = rs.getString(1);
	}
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
				  "Lead <b>("+lead_no+")</b> has been successfully deliver to destination.."+
				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}

public void SendEmailToCustomer(String deliver_by,String id_lead_m,HttpServletRequest request){
	
	String replyMailId="",name="",customername="",lead_no="",ccMailId="",address="";
	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+deliver_by+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
//		replyMailId = rs.getString(1);
		name = rs.getString(2);
	}
	
	mailSql ="select lead_no,pstn_email,nm_pstn,address from  O_Assign_Lead_Master where id_lead_m='"+id_lead_m+"'";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		lead_no = rs.getString(1);
		replyMailId = rs.getString(2);
		customername = rs.getString(3);
		address = rs.getString(4);
	}
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String mailBody = "<b> Hello "+customername+"</b>,<br><br><br>"+
"Lead <b>("+lead_no+")</b> has been successfully deliver by <b>"+name+"</b> to your location <b>"+address+"</b> "+
				    "<p>"+footerMsg+"</p>";

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}



//for customer
public void SendEmailToCustomerleadConfirmdelivery(String id_lead_m,String tag_to_emp,HttpServletRequest request){
	
	String replyMailId="",name="",lead_no="",ccMailId="",ld_time="";
	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+tag_to_emp+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
//		replyMailId = rs.getString(1);
		name = rs.getString(2);
	}
	 mailSql ="select pstn_email,nm_pstn , lead_no,ld_time from O_Assign_Lead_Master where id_lead_m='"+id_lead_m+"'";
	
	
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		replyMailId = rs.getString(1);
		name = rs.getString(2);
		lead_no=rs.getString(3);
		ld_time=rs.getString(4);
	}
	
	
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
				  "Lead  <b>("+lead_no+")</b> is deliver to your home at "+ld_time+" deliver by "+name+" "+
//				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}



public void SendEmailToinformReturntoStore(String assign_by,String id_lead_m,HttpServletRequest request){
	
	String replyMailId="",name="",lead_no="",ccMailId="";
	String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+assign_by+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		replyMailId = rs.getString(1);
		name = rs.getString(2);
	}
	
	mailSql ="select lead_no from O_Assign_Lead_Master where id_lead_m='"+id_lead_m+"'";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		lead_no = rs.getString(1);
	}
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
				  "Lead <b>("+lead_no+")</b> has been closed now and Rental Product return to store.."+
				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}



public void SendEmailforintimationforrentalexpiry(String id_lead_m){
	
	String replyMailId="",name="",lead_no="",ccMailId="",dt_exprent="";
	String mailSql = 
			" select  distinct nm_pstn,pstn_email ,(replace(convert(NVARCHAR, ld.dt_exp_rent, 103), ' ', '-')) as dt_exprent,  emp.id_emp as mailidcreatedby,emp.nm_emp as createdby,ld.nm_prod,sr_no,lead_no"  
			+" ,emp2.id_emp as mailidtagby," 
			+" emp2.nm_emp as tagBy,emp4.id_emp as mailiddeliverBy ,emp4.nm_emp as deliverBY "
			+" from M_Emp_User emp,O_Assign_lead ld, O_Assign_lead_master ldm, M_Emp_User emp1,M_Emp_User emp2,M_Emp_User emp3,M_Emp_User emp4 " 
			+" where emp.id_emp_user=ldm.created_by  "
			+" and emp2.id_emp_user=ldm.tag_by  and ld.id_lead_m=ldm.id_lead_m  and emp4.id_emp_user=ldm.deliver_by  and ld.id_lead_m='"+id_lead_m+"'";
		System.out.println(mailSql);
	try{
	DtoCommon dtcm = new DtoCommon();
	con=Common.GetConnectionForMail();
	ps=con.prepareStatement(mailSql);
	rs=ps.executeQuery();
	List<String> recipients = new ArrayList<String>();
	String itemname="<table> <tr>\r\n" + 
			"    <th>Sr. No.</th>\r\n" + 
			"    <th>Product name</th>\r\n" + 
			"    <th>Serial Number</th>\r\n" + 
			"  </tr>";
	int srno=0;
	while(rs.next())
	{
		
		if(recipients==null) {
			recipients.add(rs.getString("mailidcreatedby"));
			recipients.add(rs.getString("mailidassignby"));
			recipients.add(rs.getString("mailidtagby"));
//	recipients.add(rs.getString("mailidapprvalBY"));
			recipients.add(rs.getString("mailiddeliverBy"));
			
		}
		//itemname=itemname+"<br> Product Name"+rs.getString("nm_prod")+" Serial Number"+rs.getString("sr_no");
		itemname= itemname+"<tr>\r\n" + 
				"		    <td>"+ ++srno+"</td>\r\n" + 
				"		    <td>"+rs.getString("nm_prod")+"</td>" + 
				"		    <td>"+rs.getString("sr_no")+"</td>" +
				"		  </tr>";
		replyMailId = rs.getString("pstn_email");
		name = rs.getString("nm_pstn");
		dt_exprent = rs.getString("dt_exprent");
	    lead_no=rs.getString("lead_no");
	    System.out.println(lead_no);
	}
	
	itemname= itemname+"</table>";
	
	
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br>"+
				  "Lead <b>("+lead_no+")</b> Will Expired on <b>("+dt_exprent+")</b> "+
				  "<br>Product mention below. <br> "+itemname+""+
				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";
	System.out.println(mailBody);

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}



public void SendEmailforReturntoStocktoCustomer(String id_lead_m,String id_lead) {
	
	String replyMailId="",name="",lead_no="",ccMailId="",dtreturn="";
	String mailSql = 
			" select  distinct nm_pstn,pstn_email ,(replace(convert(NVARCHAR, rt.dt_return_str, 103), ' ', '-')) as dtreturn, " + 
			" emp.id_emp as mailidcreatedby,emp.nm_emp as createdby,ld.nm_prod,sr_no,ldm.lead_no, " + 
			" emp2.id_emp as mailidtagby, emp2.nm_emp as tagBy," + 
			" emp4.id_emp as mailiddeliverBy ,emp4.nm_emp as deliverBY from " + 
			" M_Emp_User emp,O_Assign_lead ld, O_Assign_lead_master ldm,O_Return_To_Store rt, M_Emp_User emp1," + 
			" M_Emp_User emp2,M_Emp_User emp3,M_Emp_User emp4  where emp.id_emp_user=ldm.created_by" + 
			" and emp2.id_emp_user=ldm.tag_by  and ld.id_lead_m=ldm.id_lead_m  and  rt.id_lead_m=ldm.id_lead_m and " + 
			" emp4.id_emp_user=ldm.deliver_by  and ld.id_lead_m='"+id_lead_m+"' and ld.id_lead ='"+id_lead+"'";
		System.out.println("this is me"+mailSql);
	try{
	DtoCommon dtcm = new DtoCommon();
	con=Common.GetConnectionForMail();
	ps=con.prepareStatement(mailSql);
	rs=ps.executeQuery();
	List<String> recipients = new ArrayList<String>();
	String itemname="<table> <tr>\r\n" + 
			"    <th>Sr. No.</th>\r\n" + 
			"    <th>Product name</th>\r\n" + 
			"    <th>Serial Number</th>\r\n" + 
			"  </tr>";
	int srno=0;
	while(rs.next())
	{
		
	
		//itemname=itemname+"<br> Product Name"+rs.getString("nm_prod")+" Serial Number"+rs.getString("sr_no");
		itemname= itemname+"<tr>\r\n" + 
				"		    <td>"+ ++srno+"</td>\r\n" + 
				"		    <td>"+rs.getString("nm_prod")+"</td>" + 
				"		    <td>"+rs.getString("sr_no")+"</td>" +
				"		  </tr>";
		replyMailId = rs.getString("pstn_email");
		name = rs.getString("nm_pstn");
		dtreturn = rs.getString("dtreturn");
	    lead_no=rs.getString("lead_no");
	    System.out.println(lead_no);
	}
	
	itemname= itemname+"</table>";
	
	
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br> from"+
				  "Lead <b>("+lead_no+")</b> has return item from you on <b>"+dtreturn+"</b> "+
				  "<br>Product mention below. <br> "+itemname+""+
				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";
	System.out.println(mailBody);

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}

public void SendEmailforReturntoStocktoF7team(String id_lead_m,String id_lead) {
	
	String replyMailId="",name="",lead_no="",ccMailId="",dtreturn="";
	String mailSql = 
			" select  distinct nm_pstn,pstn_email ,(replace(convert(NVARCHAR, rt.dt_return_str, 103), ' ', '-')) as dtreturn, " + 
			" emp.id_emp as mailidcreatedby,emp.nm_emp as createdby,ld.nm_prod,sr_no,ldm.lead_no, " + 
			" emp2.id_emp as mailidtagby, emp2.nm_emp as tagBy," + 
			" emp4.id_emp as mailiddeliverBy ,emp4.nm_emp as deliverBY from " + 
			" M_Emp_User emp,O_Assign_lead ld, O_Assign_lead_master ldm,O_Return_To_Store rt, M_Emp_User emp1," + 
			" M_Emp_User emp2,M_Emp_User emp3,M_Emp_User emp4  where emp.id_emp_user=ldm.created_by" + 
			" and emp2.id_emp_user=ldm.tag_by  and ld.id_lead_m=ldm.id_lead_m  and  rt.id_lead_m=ldm.id_lead_m and " + 
			" emp4.id_emp_user=ldm.deliver_by  and ld.id_lead_m='"+id_lead_m+"' and ld.id_lead ='"+id_lead+"'";
		System.out.println(mailSql);
	try{
	DtoCommon dtcm = new DtoCommon();
	con=Common.GetConnectionForMail();
	ps=con.prepareStatement("this is for stck"+mailSql);
	rs=ps.executeQuery();
	List<String> recipients = new ArrayList<String>();
	String itemname="<table> <tr>\r\n" + 
			"    <th>Sr. No.</th>\r\n" + 
			"    <th>Product name</th>\r\n" + 
			"    <th>Serial Number</th>\r\n" + 
			"  </tr>";
	int srno=0;
	while(rs.next())
	{
		
		if(recipients==null) {
			recipients.add(rs.getString("mailidcreatedby"));
			//recipients.add(rs.getString("mailidassignby"));
		//	recipients.add(rs.getString("mailidtagby"));
//	recipients.add(rs.getString("mailidapprvalBY"));
			recipients.add(rs.getString("mailiddeliverBy"));
			
		}
		//itemname=itemname+"<br> Product Name"+rs.getString("nm_prod")+" Serial Number"+rs.getString("sr_no");
		itemname= itemname+"<tr>\r\n" + 
				"		    <td>"+ ++srno+"</td>\r\n" + 
				"		    <td>"+rs.getString("nm_prod")+"</td>" + 
				"		    <td>"+rs.getString("sr_no")+"</td>" +
				"		  </tr>";
		replyMailId = rs.getString("mailidtagby");
		name = rs.getString("nm_pstn");
		dtreturn = rs.getString("dtreturn");
	    lead_no=rs.getString("lead_no");
	    System.out.println(lead_no);
	}
	
	itemname= itemname+"</table>";
	
	
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	// "<b> Hello "+name+"</b>,<br><br><br> from"+
	 String mailBody = "Lead <b>("+lead_no+")</b> has return item from '"+name+"' on <b>"+dtreturn+"</b> "+
				  "<br>Product mention below. <br> "+itemname+""+
				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";
	System.out.println(mailBody);

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}





public void SendEmailforRenewitemtoF7team(String id_lead_m,String id_lead) {
	
	String replyMailId="",name="",lead_no="",ccMailId="",dtreturn="";
	String mailSql = 
			" select  distinct nm_pstn,pstn_email ,(replace(convert(NVARCHAR, rn.dt_extend_str, 103), ' ', '-')) as dtextend,  " + 
			"  rn.extend_days,rn.dt_exprent,rn.current_inv_no, emp.id_emp as mailidcreatedby,emp.nm_emp as createdby,ld.nm_prod,ld.sr_no,ldm.lead_no, " + 
			" emp2.id_emp as mailidtagby, emp2.nm_emp as tagBy," + 
			" emp4.id_emp as mailiddeliverBy ,emp4.nm_emp as deliverBY from " + 
			" M_Emp_User emp,O_Assign_lead ld, O_Assign_lead_master ldm,O_Renew_lead rn, M_Emp_User emp1," + 
			" M_Emp_User emp2,M_Emp_User emp3,M_Emp_User emp4  where emp.id_emp_user=ldm.created_by" + 
			" and emp2.id_emp_user=ldm.tag_by  and ld.id_lead_m=ldm.id_lead_m  and rn.id_lead_m=ldm.id_lead_m and  " + 
			" emp4.id_emp_user=ldm.deliver_by  and ld.id_lead_m='"+id_lead_m+"' and id_lead ='"+id_lead+"'";
		System.out.println(mailSql);
	try{
	DtoCommon dtcm = new DtoCommon();
	con=Common.GetConnectionForMail();
	ps=con.prepareStatement(mailSql);
	rs=ps.executeQuery();
	List<String> recipients = new ArrayList<String>();
	String itemname="<table> <tr>\r\n" + 
			"    <th>Sr. No.</th>\r\n" + 
			"    <th>Product name</th>\r\n" + 
			"    <th>Serial Number</th>\r\n" + 
			"  </tr>";
	int srno=0;
	while(rs.next())
	{
		
		if(recipients==null) {
			recipients.add(rs.getString("mailidcreatedby"));
			//recipients.add(rs.getString("mailidassignby"));
			recipients.add(rs.getString("mailidtagby"));
//	recipients.add(rs.getString("mailidapprvalBY"));
			recipients.add(rs.getString("mailiddeliverBy"));
			
		}
		//itemname=itemname+"<br> Product Name"+rs.getString("nm_prod")+" Serial Number"+rs.getString("sr_no");
		itemname= itemname+"<tr>\r\n" + 
				"		    <td>"+ ++srno+"</td>\r\n" + 
				"		    <td>"+rs.getString("nm_prod")+"</td>" + 
				"		    <td>"+rs.getString("sr_no")+"</td>" +
				"		  </tr>";
		replyMailId = rs.getString("pstn_email");
		name = rs.getString("nm_pstn");
		dtreturn = rs.getString("dtreturn");
	    lead_no=rs.getString("lead_no");
	    System.out.println(lead_no);
	}
	
	itemname= itemname+"</table>";
	
	
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String mailBody = "<b> Hello "+name+"</b>,<br><br><br> from"+
				  "Lead <b>("+lead_no+")</b> has return item from you on <b>"+dtreturn+"</b> "+
				  "<br>Product mention below. <br> "+itemname+""+
				  "<br><br><br>"+link+""+
				  "<p>"+footerMsg+"</p>";
	System.out.println(mailBody);

	Common.MailConfiguration(mailBody, replyMailId, recipients , mailSubject);
	}catch(Exception e){
		System.out.println(e);
	}
}

	
}
	
	
	
	

