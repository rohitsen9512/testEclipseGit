<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*, java.text.SimpleDateFormat,java.util.*,java.text.DateFormat,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RFI Report</title>

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
		int slNo    =   0;
			String sql="";
			String startdate = "",startdate1 = "";
			String enddate = "",enddate1="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "rfi.xls");
		 
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
				 startdate1 = request.getParameter("startdate");
				 enddate1 = request.getParameter("enddate");
				 
				 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
			     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
				 if(!startdate.equals(""))
					{
					 startdate = dateFormatNeeded.format(userDateFormat.parse(startdate));
					}
				 if(!enddate.equals(""))
					{
					 enddate = dateFormatNeeded.format(userDateFormat.parse(enddate));
					}
				String tempSql = "";
				String UserType = (String)session.getAttribute("UserType");
					sql="select no_rfi,(replace(convert(NVARCHAR, dt_rfi, 103), ' ', '-')) as dt_rfi,(replace(convert(NVARCHAR, dt_rfi_start, 103), ' ', '-')) as dtrfistart,(replace(convert(NVARCHAR, dt_rfi_end, 103), ' ', '-')) as dt_rfiend,nm_model from p_rfi pf ,"+
							 "   P_RFI_Asset pfa , m_model md where pfa.id_prod=md.id_model and   pf.id_rfi=pfa.id_rfi  "+
                             "   and dt_rfi >='"+startdate+"' and dt_rfi <= '"+enddate+"'";
					
				System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
		<table border="1" width="100%" height="100%">
		

				<tr >
					<td colspan="6" style="background-color: blue;" width="100%"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">RFI Report</p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong>S No. &nbsp;&nbsp;</strong></td>
				    <td style="background-color: lavender;"><strong>RFI No. &nbsp;&nbsp;</strong></td>
				    <td style="background-color: lavender;"><strong>FRI Date &nbsp;&nbsp;</strong></td>
				     <td style="background-color: lavender;"><strong>RFI Start Date &nbsp;&nbsp;</strong></td>
				      <td style="background-color: lavender;"><strong>RFI End Date &nbsp;&nbsp;</strong></td>
					   <td style="background-color: lavender;"><strong>Item Name &nbsp;&nbsp;</strong></td>
				
				</tr>	
				
			<%
			String dt_dc="";
				while(rs.next())
				{
					
				%>
					<tr>
					<td><%=++slNo%></td>
						
					<td><%=rs.getString("no_rfi")%></td>
					<td><%=rs.getString("dt_rfi")%></td>
					<td><%=rs.getString("dtrfistart")%></td>
					<td><%=rs.getString("dt_rfiend")%></td>
					<td><%=rs.getString("nm_model")%></td>
					
					
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				out.println("sql error in grn STORE.");
			}
		%>
		
	</table>

</div>
</body>
<%
if(slNo !=0)
{
%>
<a href="rfi.jsp?exportToExcel=YES&enddate=<%=enddate1%>&startdate=<%=startdate1%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a>	

<%}
else
{%>
	<br>
		<!-- <b><center>No record(s) is available.</center></b> -->
<%}
	}%>
	
</html>