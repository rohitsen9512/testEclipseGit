<%@ page language="java" import="java.sql.*,java.text.*,javax.naming.*,javax.sql.*, java.text.SimpleDateFormat,java.util.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment Details Report</title>

<script type ="text/javascript" src="js/jquery1.10.js"></script>
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
<div id="PreviewGRNClosedReport">
	
		<%
		String id_po="",value="";
		int slNo    =   0;
			String sql="";
			double total=0.00;
		String startdate = "";
		String enddate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
		DecimalFormat f = new DecimalFormat("##.00");
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Payment_Details_Report.xls");
		 
		        }
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				 id_po=request.getParameter("id_po");
				 value=request.getParameter("value");
				sql="select po.no_po,no_cheque,po.gr_tot*rate as gr_tot,amount,nm_ven,(replace(convert(NVARCHAR, dt_pmt, 103), ' ', '-')) as dt_pmt "+
						 "from P_Payment p,P_Purchase_Order po,M_Vendor v ,P_Payment_Details pd ,M_Exchange_Rate er "+
						 "where po.id_po=p.id_po and v.id_ven=po.id_ven and p.id_pmt=pd.id_pmt and er.id_curr=po.id_curr and po.id_po="+id_po+"";

						//out.print(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
		<table border="1" width="100%" height="100%">
		

				<tr >
					<td colspan="6" style="background-color: blue;" width="100%"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 300px;">Payment Details Report &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PO Value(INR) : <%=value%></p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong>S No. </strong></td>
				    <td style="background-color: lavender;"><strong>PO Number </strong></td>
				    <td style="background-color: lavender;"><strong>Wire Transfer No.</strong></td>
					<td style="background-color: lavender;"><strong>Vendor Name&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Payment Date&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Paid Amount(INR)</strong></td>
				</tr>	
				
			<%
				while(rs.next())
				{%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString("no_po")%></td>
					<td><%=rs.getString("no_cheque")%></td>
					<td><%=rs.getString("nm_ven")%></td>
					<td><%=rs.getString("dt_pmt")%></td>
					<td><%=f.format(Double.parseDouble(rs.getString("amount")))%></td>
					
				</tr>
				<%
				total += Double.parseDouble(rs.getString("amount"));
				}
			%>
			<tr>
			<td colspan="5"><b style="float: right;">Total Paid Amount</b></td>
			<td><b><%=f.format(total)%></b></td>
			</tr>
			<%
			}
			catch(Exception e)
			{
				out.println("sql error in Payment_Details_Report.");
			}
		%>
		
	</table>

</div>
</body>
<%
if(slNo !=0)
{
%>
<a href="Payment_Details_Report.jsp?exportToExcel=YES&id_po=<%=id_po%>&value=<%=value%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a>	

<%}
else
{%>
	<br>
	<!-- 	<b><center>No record(s) is available.</center></b> -->
<%}
	}%>
	
</html>