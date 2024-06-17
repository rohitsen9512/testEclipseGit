<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,java.util.Date, java.text.SimpleDateFormat,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gate Pass</title>
<script type ="text/javascript" src="jquery1.10.js"></script>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
.tableFormat {
	border-collapse : collapse; 
	border-color : #FFFFFF; 
	font-family : "Trebuchet MS", verdana; 
	font-size : 11px; 
	padding : 2px; 
}
#PreviewInstalledAssetReport
{
border:1px;
}
</style>
<script type="text/javascript">
function printPg()
{
	$('#printPgId').hide();
	window.print();

}

</script>
</head>
<%
   String usertype = (String) session.getAttribute("UserName");
   if(usertype == null)	{
%>
		<script language="JavaScript">
			alert("Session Expired. Please Login");
			parent.location.href="../index.html";
		</script>
<%
	}else{
%>
<body>

	
		<%
		
		
		String id_gp = "",holder="",auth_person="",req_by="",type_tran="",id_wh_dyn="";
		String id_wh = "",id_iut="",nm_holder="",nm_auth_person="",nm_req_by="",ds_pro="";
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				
				
				id_gp = request.getParameter("id_gp");
				id_wh = request.getParameter("id_wh");
				id_iut = request.getParameter("id_iut");
				type_tran = request.getParameter("type_tran");

				sql="select id_wh_dyn,ds_pro from A_Ware_House where id_wh="+id_wh+"";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_wh_dyn = rs.getString(1);	
					ds_pro = rs.getString(2);	
				}
				
				sql="select * from T_Gate_Pass where id_gp="+id_gp+"";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					holder = rs.getString("holder");	
					auth_person = rs.getString("auth_person");	
					req_by = rs.getString("req_by");	
				}
				
				sql="select nm_emp from M_Emp_User where id_emp_user="+holder+"";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					nm_holder = rs.getString(1);
				}sql="select nm_emp from M_Emp_User where id_emp_user="+auth_person+"";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					nm_auth_person = rs.getString(1);
				}
				sql="select nm_emp from M_Emp_User where id_emp_user="+req_by+"";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					nm_req_by = rs.getString(1);
				}
				
			}
			catch(Exception e)
			{
				out.println("sql error in Print gate Pass.");
			}	
			%>

			<center>
			<br><br>
<div id="PreviewInstalledAssetReport">
		<table border="0" width="60%" align="center">
		<tr>
					<td colspan="2">
					<center><b>GATE PASS</b>	<br></center>
						
					</td>
					
				</tr>	
				<tr><td></td></tr>	
				<tr><td></td></tr>	
				<tr><td></td></tr>	
				<tr>
					<td>
					<b>INTUIT</b>	<br>
						simplify the business of life
					</td>
				
				<td style="float:right;">
					
						<b>Date</b>	
						<%=sdf.format(currDate) %><br>
						<b>Transfer Type : </b>	
						<%=type_tran %>
					</td>
				</tr>
		</table>
		<br><br>
		<%-- <div class="gatePassPrintReviewClass">
		<ul>
			<li><strong>Asset Id :</strong></li>
			<li><%=id_wh_dyn%></li>
			<li><strong>Asset Name :</strong></li>
			<li><%=ds_pro%></strong></li>
		</ul>	</div>
		 --%>
		
	
		
		<table  width="60%" align="center" border="1" class="tableFormat">
				<tr>
				   
					<td ><strong>Asset Id </strong></td>
					<td><%=id_wh_dyn%></td>
					<td ><strong>Asset Name </strong></td>
					<td><%=ds_pro%></td>
				</tr>
				<tr>
					<td ><strong>Holder Name </strong></td>
					<td><%=nm_holder%></td>
					<td ><strong>Authorised By </strong></td>
					<td><%=nm_auth_person%></td>
				</tr>
				<tr>
					<td ><strong>Requested by </strong></td>
					<td><%=nm_req_by%></td>
					<td colspan="2"></td>
				</tr>
				
		
			
	</table>
	
	<a href="">
				<button  type="button" id="printPgId" class="btn btn-primary" style="margin-left: 300px;" onClick="printPg()">Take Print</button>
				</a>
</div>
</center>
</body>
<%} %>
</html>