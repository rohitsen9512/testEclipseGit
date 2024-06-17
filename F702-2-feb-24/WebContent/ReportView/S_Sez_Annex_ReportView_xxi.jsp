<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*, java.text.SimpleDateFormat,java.util.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SEZ Annexure XXI</title>

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
<div id="PreviewInstalledAssetReport">
	
		<%
		int slNo    =   0;
			String sql="";
		String startdate = "";
		String enddate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "S_Sez_Annex_xxi.xls");
		 
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
				
				sql="select sezApr.no_aprv_sez,sezApr.dt_aprv_sez,nm_ven,sez.type_of_import,sezApr.val_aprv_sez, "+
					" sez.running_balance,wh.no_bd,wh.dt_bd,wh.no_boe,wh.dt_boe,sez.cg_balance "+
					" from A_Ware_House wh,G_Grn g,S_Sez_Annex_ix sez,S_Sez_Annexure_ix_Approval sezApr,M_Vendor v "+
					" where wh.id_grn=g.id_grn and sezApr.id_sez_aprv=g.id_sez_aprv and sezApr.id_annex_ix=sez.id_annex_ix "+
					" and wh.bd='Yes' and sez.id_ven=v.id_ven and sez.dt_doc >='"+startdate+"' and sez.dt_doc <= '"+enddate+"'";
					
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
		<table border="1" width="100%" height="100%">
		
				<tr >
					<td colspan="13" style="background-color: blue;" width="100%"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">SEZ Annexure XXI Report</p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong>S No. &nbsp;&nbsp;</strong></td>
				    <td style="background-color: lavender;"><strong>SEZ Approval No &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>SEZ Approval Date &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Supplier &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Import Details &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Amount as per STPI approval &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Running Balance Of CG in INR &nbsp;&nbsp;</strong></td>
					
					<td style="background-color: lavender;"><strong>Bond No. &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Bond Date &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Bill of Entry No. &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Bill of Entry Date &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>CG Approved in INR &nbsp;&nbsp;</strong></td>
				</tr>	
				
			<%
				while(rs.next())
				{%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString(1)%></td>
					<td><%=rs.getString(2)%></td>
					<td><%=rs.getString(3)%></td>
					<td><%=rs.getString(4)%></td>
					<td><%=rs.getString(5)%></td>
					
					<td><%=rs.getString(6)%></td>
					<td><%=rs.getString(7)%></td>
					<td><%=rs.getString(8)%></td>
					<td><%=rs.getString(9)%></td>
					<td><%=rs.getString(10)%></td>
					<td><%=rs.getString(11)%></td>
					
					
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				out.println("sql error in InstalledAssetView.");
			}
		%>
		
	</table>

</div>
</body>
<%
if(slNo !=0)
{
%>
<a href="S_Sez_Annex_ReportView_xxi.jsp?exportToExcel=YES&enddate=<%=enddate%>&startdate=<%=startdate%>">
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