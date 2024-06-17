<%@ page language="java" import="java.sql.*,javax.naming.*,java.text.SimpleDateFormat,java.util.Date,java.text.DateFormat,javax.sql.*,java.text.DecimalFormat, java.text.SimpleDateFormat,java.util.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>All Indent Report</title>

<script type ="text/javascript" src="js/jquery1.10.js"></script>
<script type="text/javascript">

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
<div id="CancelledPOReportView">
	
		<%
		DecimalFormat df = new DecimalFormat("#.00");
		int slNo    =   0;
			String sql="";
		String startdate = "";
		String enddate = "";
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
	     DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String Group_id="",fin_id="",curr="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Indent Report.xls");
		 
		        }
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				
				 startdate = request.getParameter("startdate");
				 enddate = request.getParameter("enddate");
				 
				 String tempSql = "";
				 if(!startdate.equals("") && !enddate.equals(""))
					{
					 tempSql = " and dt_req >='"+ dateFormatNeeded.format(userDateFormat.parse(startdate))+"' and dt_req <= '"+dateFormatNeeded.format(userDateFormat.parse(enddate))+"'";
					}
				 
				sql="select no_req,grand_tot, (replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dt_req ,nm_bu,nm_emp  from P_request pr ,M_BU md,M_Emp_User emp where pr.id_dept=md.id_bu and emp.id_emp_user=pr.req_by "+tempSql+"" ;
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
		<table border="1" width="100%" height="100%">
				<tr>
					<td colspan="9" style="background-color: blue;" width="100%"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 50%;">Indent Report</p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong>S No.</strong></td>
					<td style="background-color: lavender;"><strong>Indent Number</strong></td>
					<td style="background-color: lavender;"><strong>Indent Date</strong></td>
					<td style="background-color: lavender;"><strong>Department</strong></td>
					<td style="background-color: lavender;"><strong>Requested BY</strong></td>
					<td style="background-color: lavender;"><strong>Indent Value</strong></td>
					</tr>	
				
			<%
				while(rs.next())
				{
					
				%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString("no_req")%></td>
					<td><%=rs.getString("dt_req")%></td>
					<td><%=rs.getString("nm_bu")%></td>
				 	<td><%=rs.getString("nm_emp")%></td>
					<td><%=rs.getString("grand_tot")%></td>
				 </tr>
				<%}
			}
			catch(Exception e)
			{
				out.println("sql error in PR Detail");
			}
		%>
		
	</table>

</div>
</body>
<%
if(slNo !=0)
{
%>
<a href="Indent_ReportView.jsp?exportToExcel=YES&enddate=<%=enddate%>&startdate=<%=startdate%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 75%;">Export To Excel</button>
			</a>	

<%}
else
{%>
	<br>
		<!--  <b><center>No record(s) is available.</center></b>-->
<%}
	}%>
	
</html>