<%@ page language="java" import="java.sql.*,javax.naming.*,java.text.SimpleDateFormat,java.util.Date,java.text.DateFormat,javax.sql.*,java.text.DecimalFormat, java.text.SimpleDateFormat,java.util.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Amend PO Report</title>

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
		                    + "Amend PO Report.xls");
		 
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
					 tempSql = " and p.dt_po >='"+ dateFormatNeeded.format(userDateFormat.parse(startdate))+"' and p.dt_po <= '"+dateFormatNeeded.format(userDateFormat.parse(enddate))+"'";
					}
				 
				sql="Select amend_remrks,nm_ven,p.no_po,(replace(convert(NVARCHAR, p.dt_amend, 103), ' ', '-')) as dt_amend,(replace(convert(NVARCHAR, p.dt_po, 103), ' ', '-')) as dt_po,p.tot,p.rem_val_po,p.typ_pmt,p.no_quot "+
						" from P_Purchase_Order_amend p,m_vendor ven where ven.id_ven=p.id_ven "+tempSql+"" ;
				
				
				//out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
		<table border="1" width="100%" height="100%">
				<tr>
					<td colspan="9" style="background-color: blue;" width="100%"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 50%;">Amend PO Report</p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong>S No.</strong></td>
					<td style="background-color: lavender;"><strong>PO No.</strong></td>
					<td style="background-color: lavender;"><strong>PO Date</strong></td>
					<td style="background-color: lavender;"><strong>Amendment Date</strong></td>
					<td style="background-color: lavender;"><strong>Amendment Remarks</strong></td>
					<td style="background-color: lavender;"><strong>Vendor Name</strong></td>
					<td style="background-color: lavender;"><strong>Quotation No.</strong></td>
				 	<td style="background-color: lavender;"><strong>PO Value</strong></td>
			 	</tr>	
				
			<%
				while(rs.next())
				{
					
				%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString("no_po")%></td>
					<td><%=rs.getString("dt_po")%></td>
					<td><%=rs.getString("dt_amend")%></td>
					<td><%=rs.getString("amend_remrks")%></td>
					<td><%=rs.getString("nm_ven")%></td>
						<td><%=rs.getString("no_quot")%></td>
						
				 	<td><%=rs.getString("tot")%></td>
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
<a href="AmendPO_ReportView.jsp?exportToExcel=YES&enddate=<%=enddate%>&startdate=<%=startdate%>">
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