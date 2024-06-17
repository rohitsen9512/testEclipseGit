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
<div id="PreviewInstalledAssetReport">
	
		<%
		
		
		String id_trvl = "",holder="",auth_person="",req_by="",empName="",id_wh_dyn="";
		String id_wh = "",id_iut="",emp_code="",startTravelDate="",expectedDate="",ds_pro="";
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
				
				
				id_trvl = request.getParameter("id_trvl");
				id_wh = request.getParameter("id_wh");
				id_iut = request.getParameter("id_iut");

				sql="select id_wh_dyn,ds_pro,nm_emp,cd_emp,dt_st_trvl,dt_expc_trvl from A_Ware_House wh,M_Emp_User emp,"+
						"T_Travel trvl where emp.id_emp_user=wh.to_assign and wh.id_wh=trvl.id_wh and id_trvl="+id_trvl+"";
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next())
				{
					id_wh_dyn = rs.getString(1);	
					ds_pro = rs.getString(2);	
					empName = rs.getString(3);	
					emp_code = rs.getString(4);	
					startTravelDate = rs.getString(5);	
					expectedDate = rs.getString(6);	
					
				}
				
				
				
			}
			catch(Exception e)
			{
				out.println("sql error in Print gate Pass.");
			}	
			%>
			<center>
		<table border="0" width="60%">
		
				<tr>
					<td><center>
					<b>EMPLOYEE TRAVEL DETAILS</b>	<br>
						</center>
					</td>
					
				</tr>
				<tr>
					<td>
					<b>INTUIT</b>	<br>
						simplify the business of life
					</td>
					
				</tr>	
				<tr>
				
				<td style="float:right;">
					
						<b>Date</b>	
						<%=sdf.format(currDate) %>
					</td>
				</tr>
		</table>
		<table  width="60%">
				<tr>
				   
					<td ><strong>Asset Id &nbsp;&nbsp;</strong></td>
					<td><%=id_wh_dyn%></td>
					<td ><strong>Asset Name &nbsp;&nbsp;</strong></td>
					<td><%=ds_pro%></td>
				</tr>
				<tr>
					<td ><strong>Employee Name &nbsp;&nbsp;</strong></td>
					<td><%=empName%></td>
					<td ><strong>Employee ID &nbsp;&nbsp;</strong></td>
					<td><%=emp_code%></td>
				</tr>
				<tr>
					<td ><strong>Travel Start Date &nbsp;&nbsp;</strong></td>
					<td><%=startTravelDate%></td>
					<td ><strong>Expected Return Date &nbsp;&nbsp;</strong></td>
					<td><%=expectedDate%></td>
				</tr>
				
		
			<tr>
			<td colspan="4">
				<a href="">
				<button  type="button" id="printPgId" class="btn btn-primary" style="margin-left: 300px;" onClick="printPg()">Take Print</button>
				</a>	
			</td>	
		</tr>
	</table>
	</center>
</div>
</body>
<%} %>
</html>