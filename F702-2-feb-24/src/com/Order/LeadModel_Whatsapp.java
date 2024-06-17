package com.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.Common.Common;

import dto.Common.DtoCommon;

public class LeadModel_Whatsapp {

	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	
	
	
public void SendWhatsAppToAssignee(String assign_to,int id_lead_m,HttpServletRequest request){
		
		String replynumber="",employeename="",lead_no="",nm_subl="";
		
    	String mailSql =" select nm_emp,cont_no from M_Emp_User where id_emp_user='"+assign_to+"'";
    	try{
    	DtoCommon dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			employeename = rs.getString(1);
			replynumber = rs.getString(2);
		}
		
		mailSql ="select ld.lead_no,ms.nm_subl from O_lead_Master ld,M_Subloc ms where  ld.id_sloc=ms.id_sloc and id_lead_m="+id_lead_m+"";
    	dtcm = new DtoCommon();
		rs = dtcm.GetResultSet(mailSql,  request);
		if(rs.next())
		{
			lead_no = rs.getString(1);
			nm_subl = rs.getString(2);
			System.out.println(lead_no);
		}
		
//		String Message = "<b> Hello "+employeename+"</b>,<br><br><br>"+
//				  "Lead <b>("+lead_no+")</b> has been assign to you for "+nm_subl+" for further processing..";
//		

		String Message = " "+employeename+" "+
				  "Lead ("+lead_no+") has been assign to you for "+nm_subl+" for further processing..";
		

		System.out.println("Message");
		Common.SendwhatsappMassege(Message,employeename,replynumber);
    	}catch(Exception e){
    		System.out.println(e);
    	}
	}
	
public void SendWhatsAppToTagperson(String assign_to,String id_lead_m,HttpServletRequest request){
	
	String replynumber="",employeename="",lead_no="",nm_subl="",address="";
	
	String mailSql =" select nm_emp,cont_no from M_Emp_User where id_emp_user='"+assign_to+"'";
	System.out.println(mailSql);
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		employeename = rs.getString("nm_emp");
		replynumber = rs.getString("cont_no");
		System.out.println(replynumber+"no");
	}
	
	mailSql ="select ld.lead_no,address,ms.nm_subl from O_Assign_Lead_Master ld,M_Subloc ms where  ld.id_sloc=ms.id_sloc and id_lead_m="+id_lead_m+"";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		lead_no = rs.getString("lead_no");
		nm_subl = rs.getString("nm_subl");
		address = rs.getString("address");
		System.out.println(lead_no);
	}
	
	
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String Message = "  "+
				  "Lead ("+lead_no+") has been Tag to you for delivery to "+address+" "+nm_subl+"" ;

	Common.SendwhatsappMassege(Message,employeename,replynumber);
	}catch(Exception e){
		System.out.println("whatsapptag"+e);
	}
}


//
public void SendWhatsAppToToCustomerforTrackingcordential(String id_lead_m,HttpServletRequest request){
	
	String replyMailId="",name="",lead_no="",ccMailId="",nm_pstn="",pstn_ct="",dt_ld="";
	
	//String mailSql ="select id_emp,nm_emp from M_Emp_User where id_emp_user='"+id_lead_m+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
//	rs = dtcm.GetResultSet(mailSql,  request);
//	if(rs.next())
//	{
//		replyMailId = rs.getString(1);
//		name = rs.getString(2);
//	}
	
	String mailSql ="select lead_no,nm_pstn,pstn_ct,dt_ld from O_Assign_Lead_Master where id_lead_m="+id_lead_m+"";
	System.out.println(mailSql);
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		lead_no = rs.getString(1);
		nm_pstn = rs.getString(2);
		pstn_ct = rs.getString(3).replaceAll("\\s", "");
		dt_ld = rs.getString(4);
		//replyMailId = rs.getString(2);
		System.out.println(pstn_ct+"hh");
	}
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String Message = "  "+
				  "Lead ("+lead_no+")  has is confirm for you for this your Userame ("+pstn_ct+")  and  password ("+pstn_ct+")"+
				  " for login  ";

	Common.SendwhatsappMassege(Message,nm_pstn,pstn_ct);
	}catch(Exception e){
		System.out.println("whatsappCustomcordential"+e);
	}
}


public void SendWhatsAppToAssignee1(String tag_by,String id_lead_m,HttpServletRequest request){
	
	String replyMailId="",name="",lead_no="",ccMailId="",cont_no="";
	String mailSql ="select id_emp,nm_emp,cont_no from M_Emp_User where id_emp_user='"+tag_by+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		replyMailId = rs.getString(1);
		name = rs.getString(2);
		cont_no = rs.getString(3);
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
	
	String Message = "   "+
				  "Lead ("+lead_no+") has been successfully deliver to destination..";

	Common.SendwhatsappMassege(Message,name,cont_no);
	}catch(Exception e){
		System.out.println(e);
	}
}



public void SendWhatsAppToCustomer(String deliver_by,String id_lead_m,HttpServletRequest request){
	
	String replycontactno="",name="",customername="",lead_no="",ccMailId="",address="";
	String mailSql ="select id_emp,nm_emp ,cont_no from M_Emp_User where id_emp_user='"+deliver_by+"'";
	try{
	DtoCommon dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		//replycontactno = rs.getString("cont_no");
		name = rs.getString("nm_emp");
	}
	
	mailSql ="select lead_no,pstn_ct,nm_pstn,address from  O_Assign_Lead_Master where id_lead_m='"+id_lead_m+"'";
	dtcm = new DtoCommon();
	rs = dtcm.GetResultSet(mailSql,  request);
	if(rs.next())
	{
		lead_no = rs.getString("lead_no");
		replycontactno = rs.getString("pstn_ct").replaceAll("\\s", "");
		customername = rs.getString("nm_pstn");
		address = rs.getString("address");
	}
	
	List<String> recipients = new ArrayList<String>();
	
	String link = dtcm.ReturnParticularMessage("link");
	String footerMsg = dtcm.ReturnParticularMessage("footerMsg");
	String mailSubject = dtcm.ReturnParticularMessage("ticketAssignToGRP");
	
	String Mesaage = "   "+
      "Lead ("+lead_no+") has been successfully deliver by "+name+" to your location "+address+" ";

	Common.SendwhatsappMassege(Mesaage,customername,replycontactno);
	}catch(Exception e){
		System.out.println(e);
	}
}



public void SendWhatsappforReturntoStocktoCustomer(String id_lead_m,String id_lead) {
	
	String replyMailId="",name="",lead_no="",ccMailId="",dtreturn="",pstn_ct="",nm_prod="",sr_no="",
			 Mesaage ="";
	String mailSql = 
			" select  distinct nm_pstn,pstn_email ,pstn_ct,(replace(convert(NVARCHAR, ld.dt_return_str, 103), ' ', '-')) as dtreturn, " + 
			"emp.cont_no as createdby,ld.nm_prod,sr_no,ldm.lead_no, emp2.cont_no as tagby, " 
			+ "  emp4.cont_no as deliverBy  from " + 
			" M_Emp_User emp,O_Assign_lead ld, O_Assign_lead_master ldm, M_Emp_User emp1," + 
			" M_Emp_User emp2,M_Emp_User emp3,M_Emp_User emp4  where emp.id_emp_user=ldm.created_by" + 
			" and emp2.id_emp_user=ldm.tag_by  and ld.id_lead_m=ldm.id_lead_m  and " + 
			" emp4.id_emp_user=ldm.deliver_by  and ld.id_lead_m='"+id_lead_m+"' and ld.id_lead ='"+id_lead+"'";
		System.out.println(mailSql);
	try{

	con=Common.GetConnectionForMail();
	ps=con.prepareStatement(mailSql);
	rs=ps.executeQuery();
	List<String> recipients = new ArrayList<String>();

	int srno=0;
	while(rs.next())
	{
		
		if(recipients==null) {
			recipients.add(rs.getString("createdby"));
		
			recipients.add(rs.getString("tagby"));

			recipients.add(rs.getString("deliverBy"));
			
		}
	
		nm_prod=rs.getString("nm_prod");
		sr_no=rs.getString("sr_no");
		replyMailId = rs.getString("pstn_email");
		name = rs.getString("nm_pstn");
		pstn_ct = rs.getString("pstn_ct").replaceAll("\\s", "");
		dtreturn = rs.getString("dtreturn");
	    lead_no=rs.getString("lead_no");
	    System.out.println(lead_no);
	    

	
	}
	

	

 Mesaage = "   from"+
				  "Lead ("+lead_no+")  has  "
				  + "return item  on "+dtreturn+" "+
				  " Product mention below. "
				  + "  Sr. No.:"+ ++srno+ " "
				  +"    Product name :" + nm_prod + ""
				  +"    Serial Number : "  + sr_no +" ";


	Common.SendwhatsappMassege(Mesaage,name,pstn_ct);
	}catch(Exception e){
		System.out.println(e);
	}
}


public void SendWhatsappforReturntoStocktoF7team(String id_lead_m,String id_lead) {
	
	String replyMailId="",name="",lead_no="",ccMailId="",dtreturn="",pstn_ct="",nm_prod="",sr_no="",Mesaage="";
	String mailSql = 
			" select  distinct nm_pstn,pstn_email ,pstn_ct,(replace(convert(NVARCHAR, ld.dt_return_str, 103), ' ', '-')) as dtreturn, " + 
			"emp.cont_no as createdbyno,ld.nm_prod,sr_no,ldm.lead_no, emp2.cont_no as tagbyno, "
			+ " emp4.cont_no as deliverByno from  M_Emp_User emp,O_Assign_lead ld, O_Assign_lead_master ldm,O_Return_To_Store rt, M_Emp_User emp1, M_Emp_User emp2,M_Emp_User emp3,M_Emp_User emp4  where emp.id_emp_user=ldm.created_by " 
			+ " and emp2.id_emp_user=ldm.tag_by  and ld.id_lead_m=ldm.id_lead_m  and  rt.id_lead_m=ldm.id_lead_m and  " 
			+ " emp4.id_emp_user=ldm.deliver_by  and ld.id_lead_m='"+id_lead_m+"' and ld.id_lead ='"+id_lead+"'";
		System.out.println(mailSql);
	try{

	con=Common.GetConnectionForMail();
	ps=con.prepareStatement(mailSql);
	rs=ps.executeQuery();
	List<String> recipients = new ArrayList<String>();

	int srno=0;
	while(rs.next())
	{
		
		if(recipients==null) {
			recipients.add(rs.getString("createdbyno"));
		
			recipients.add(rs.getString("tagbyno"));

			recipients.add(rs.getString("deliverByno"));
			
		}

		nm_prod=rs.getString("nm_prod");
		sr_no=rs.getString("sr_no");
		replyMailId = rs.getString("tagbyno");
		name = rs.getString("nm_pstn");
		pstn_ct = rs.getString("pstn_ct").replaceAll("\\s", "");
		dtreturn = rs.getString("dtreturn");
	    lead_no=rs.getString("lead_no");
	    System.out.println(lead_no);
	}
	

	
	

	 Mesaage = "   from"+
					  "Lead ("+lead_no+")  has  "
					  + "return item from "+name+" "
					  + " on "+dtreturn+" "+
					  " Product mention below. "
					  + "  Sr. No.:"+ ++srno+ " "
					  +"    Product name :" + nm_prod + ""
					  +"    Serial Number : "  + sr_no +" ";

	System.out.println(Mesaage);

	Common.SendwhatsappMassege(Mesaage,replyMailId,pstn_ct);
	}catch(Exception e){
		System.out.println(e);
	}
}

public void SendWhatsappforRenewitemF7team(String id_lead_m,String id_lead) {
	
	String replyMailId="",name="",lead_no="",ccMailId="",dtextend="",pstn_ct="",extend_days="",nm_prod="",sr_no="",Mesaage ="";
	String mailSql = 
			" select  distinct nm_pstn,pstn_email ,pstn_ct,(replace(convert(NVARCHAR, rn.dt_extend_str, 103), ' ', '-')) as dtextend, " + 
			"   rn.extend_days,rn.dt_exprent,rn.current_inv_no, emp.cont_no as createdby,emp.nm_emp as createdby,ld.nm_prod,ld.sr_no,ldm.lead_no, " + 
			"emp2.cont_no as tagby, emp2.nm_emp as tagBy, "
			+ " emp4.cont_no as deliverBy ,emp4.nm_emp as deliverBY from " + 
			" M_Emp_User emp,O_Assign_lead ld, O_Assign_lead_master ldm,O_Renew_lead rn, M_Emp_User emp1," + 
			" M_Emp_User emp2,M_Emp_User emp3,M_Emp_User emp4  where emp.id_emp_user=ldm.created_by" + 
			" and emp2.id_emp_user=ldm.tag_by  and ld.id_lead_m=ldm.id_lead_m  and rn.id_lead_m=ldm.id_lead_m and  " + 
			" emp4.id_emp_user=ldm.deliver_by  and ld.id_lead_m='"+id_lead_m+"' and ld.id_lead ='"+id_lead+"'";
		System.out.println(mailSql);
	try{
	//DtoCommon dtcm = new DtoCommon();
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
			recipients.add(rs.getString("createdby"));
			//recipients.add(rs.getString("assignby"));
			recipients.add(rs.getString("tagby"));
//	recipients.add(rs.getString("mailidapprvalBY"));
			recipients.add(rs.getString("deliverBy"));
			
		}
		//itemname=itemname+"<br> Product Name"+rs.getString("nm_prod")+" Serial Number"+rs.getString("sr_no");
		itemname= itemname+"<tr>\r\n" + 
				"		    <td>"+ ++srno+"</td>\r\n" + 
				"		    <td>"+rs.getString("nm_prod")+"</td>" + 
				"		    <td>"+rs.getString("sr_no")+"</td>" +
				"		  </tr>";
		nm_prod=rs.getString("nm_prod");
		sr_no=rs.getString("sr_no");
		replyMailId = rs.getString("pstn_email");
		name = rs.getString("nm_pstn");
		pstn_ct = rs.getString("pstn_ct").replaceAll("\\s", "");
		dtextend = rs.getString("dtextend");
		extend_days = rs.getString("extend_days");
	    lead_no=rs.getString("lead_no");
	    System.out.println(lead_no);
	}
	
	itemname= itemname+"</table>";
	
	
	

	 Mesaage ="   from"+
			  "Lead ("+lead_no+")  is  "
			  + "extended to "+name+" "
			  + " on "+extend_days+" "+
			  " Product mention below. "
			  + "  Sr. No.:"+ ++srno+ " "
			  +"    Product name :" + nm_prod + ""
			  +"    Serial Number : "  + sr_no +" ";
	
//	"   for"+
//	  "Lead ("+lead_no+") is extend for "+extend_days+" days for item  to Date "+dtextend+" "+
//	  " Product mention below.  "+itemname+"";
	
	
	//System.out.println(Mesaage);

	Common.SendwhatsappMassege(Mesaage,name,pstn_ct);
	}catch(Exception e){
		System.out.println(e);
	}
}



}
