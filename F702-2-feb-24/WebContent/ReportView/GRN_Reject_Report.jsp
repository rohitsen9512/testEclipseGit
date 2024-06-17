<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*, java.text.SimpleDateFormat,java.util.*,java.text.DateFormat,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GRN Reject Report</title>

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
		                    + "GRN_Reject_Report.xls");
		 
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
				if(UserType.equals("SUPER"))
				sql="select distinct(g.no_grn), inv.ds_pro, inv.mfr, (replace(convert(NVARCHAR, g.dt_grn, 103), ' ', '-')) as dt_grn,g.no_po,(replace(convert(NVARCHAR, g.dt_po, 103), ' ', '-')) as dt_po,g.no_inv,(replace(convert(NVARCHAR, g.dt_inv, 103), ' ', '-')) as dt_inv, g.no_dc,(replace(convert(NVARCHAR, g.dt_dc, 103), ' ', '-')) as dt_dc, "+
						" l.nm_loc, sl.nm_subl,v.nm_ven,g.remarks,inm.bd,inm.no_boe,g.no_aprv_sez,qty_recv from G_Grn g, M_Loc l,M_Subloc sl, A_Invoice_Master inm, A_Invoice inv, "+
						" M_Vendor v where l.id_loc=inm.id_loc and sl.id_sloc=inm.id_subl and v.id_ven=inm.id_ven and inm.id_inv_m=inv.id_inv_m "+
						" and g.id_inv=inv.id_inv and g.id_inv_m=inm.id_inv_m and g.status_approv=2 and g.dt_grn >='"+startdate+"' and g.dt_grn <= '"+enddate+"'";
						else
							sql="select distinct(g.no_grn), inv.ds_pro, inv.mfr, (replace(convert(NVARCHAR, g.dt_grn, 103), ' ', '-')) as dt_grn,g.no_po,(replace(convert(NVARCHAR, g.dt_po, 103), ' ', '-')) as dt_po,g.no_inv,(replace(convert(NVARCHAR, g.dt_inv, 103), ' ', '-')) as dt_inv, g.no_dc,(replace(convert(NVARCHAR, g.dt_dc, 103), ' ', '-')) as dt_dc, "+
									" l.nm_loc, sl.nm_subl,v.nm_ven,g.remarks,inm.bd,inm.no_boe,g.no_aprv_sez,qty_recv from G_Grn g, M_Loc l,M_Subloc sl, A_Invoice_Master inm, A_Invoice inv, "+
									" M_Vendor v where l.id_loc=inm.id_loc and sl.id_sloc=inm.id_subl and v.id_ven=inm.id_ven and inm.id_inv_m=inv.id_inv_m and inv.typ_asst = '"+UserType+"' "+
									" and g.id_inv=inv.id_inv and g.id_inv_m=inm.id_inv_m and g.status_approv=2 and g.dt_grn >='"+startdate+"' and g.dt_grn <= '"+enddate+"'";
									
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
		<table border="1" width="130%" height="100%">
		

				<tr >
					<td colspan="19" style="background-color: blue;" width="100%"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">GRN Reject Report</p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong>S No. &nbsp;&nbsp;</strong></td>
				    <td style="background-color: lavender;"><strong>GRN No. &nbsp;&nbsp;</strong></td>
				    <td style="background-color: lavender;"><strong>GRN Date &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Asset Name&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Manufacture&nbsp;&nbsp;</strong></td>
					
					<td style="background-color: lavender;"><strong>Bonded&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>BOE No.&nbsp;&nbsp;</strong></td>
					
					<td style="background-color: lavender;"><strong>Qty&nbsp;&nbsp;</strong></td>
					
					
					<td style="background-color: lavender;"><strong>PO No.&nbsp;&nbsp;</strong></td>
					
					<td style="background-color: lavender;"><strong>PO Date&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Invoice No.&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Invoice Date&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>DC No&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>DC Date&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>INV City&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>INV Unit&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Vendor Name&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Remarks&nbsp;&nbsp;</strong></td>
				</tr>	
				
			<%
			String dt_dc="";
				while(rs.next())
				{
					dt_dc = rs.getString(10);
				if(dt_dc.equals("01/01/1900"))
					dt_dc = "---";
				%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString(1)%></td>
					<td><%=rs.getString(4)%></td>
					<td><%=rs.getString(2)%></td>
					<td><%=rs.getString(3)%></td>
					
					<td><%=rs.getString(15)%></td>
					<td><%=rs.getString(16)%></td>
					<td><%=rs.getString(18)%></td>
					<td><%=rs.getString(5)%></td>
					
					<td><%=rs.getString(6)%></td>
					<td><%=rs.getString(7)%></td>
					<td><%=rs.getString(8)%></td>
					<td><%=rs.getString(9)%></td>
					<td><%=dt_dc%></td>
					<td><%=rs.getString(11)%></td>
					<td><%=rs.getString(12)%></td>
					<td><%=rs.getString(13)%></td>
					<td><%=rs.getString(14)%></td>
					
					
					
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
<a href="GRN_Reject_Report.jsp?exportToExcel=YES&enddate=<%=enddate1%>&startdate=<%=startdate1%>">
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