<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*, java.text.SimpleDateFormat,java.util.*,java.text.DateFormat,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Negotiation Report</title>

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
			String vendor="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Negotiation Report.xls");
		 
		        }
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				
					 /* startdate = request.getParameter("startdate");
				 enddate = request.getParameter("enddate");
				 startdate1 = request.getParameter("startdate");
				 enddate1 = request.getParameter("enddate"); */
				 vendor=request.getParameter("nm_vendor_sales");
				 String tempQuery="";
				 
				 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
			     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
			      /* if(!startdate.equals(""))
					{
					 startdate = dateFormatNeeded.format(userDateFormat.parse(startdate));
					}
				  if(!enddate.equals(""))
					{
					 enddate = dateFormatNeeded.format(userDateFormat.parse(enddate));
					 tempQuery = " AND q.dt_rec_quot  BETWEEN '"+startdate+"' AND '"+enddate+"' ";
					}  */
				 if(!vendor.equals("")){
						tempQuery += " And v.nm_ven = '"+vendor+"' ";		
						}
				String tempSql = "";
				String UserType = (String)session.getAttribute("UserType");
					sql="select n.*,q.no_ind,q.no_quot,m.nm_model"+
						" from Negotiation_History n,P_Quotation_Asset qa,P_Quotation q,M_Model m,M_Vendor v"+
						" where n.id_quot_asst=qa.id_quot_asst and qa.id_prod=m.id_model and qa.id_quot=q.id_quot and q.id_ven=v.id_ven  "+
							"   "+tempQuery+"  ";
					
				System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
		<table border="1" width="100%" height="100%">
		

				<tr >
					<td colspan="12" style="background-color: blue;" width="100%"><center><p  style="background-color: blue; font-size: 24px;color: white;"> Negotiation Report</p></center></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong>S No. &nbsp;&nbsp;</strong></td>
				    <td style="background-color: lavender;"><strong>Quotation No. &nbsp;&nbsp;</strong></td>
				    <td style="background-color: lavender;"><strong>Item Name &nbsp;&nbsp;</strong></td>
				   <td style="background-color: lavender;"><strong>QTY &nbsp;&nbsp;</strong></td>
				 <td style="background-color: lavender;"><strong>Old Unit Price &nbsp;&nbsp;</strong></td>
				 <td style="background-color: lavender;"><strong>Old Tax Value 1 &nbsp;&nbsp;</strong></td>
				 <td style="background-color: lavender;"><strong>Old Value 2 &nbsp;&nbsp;</strong></td>
				 <td style="background-color: lavender;"><strong>Old Total Price &nbsp;&nbsp;</strong></td>
				  <td style="background-color: lavender;"><strong>Latest Unit Price &nbsp;&nbsp;</strong></td>
				 <td style="background-color: lavender;"><strong>Latest Tax Value 1 &nbsp;&nbsp;</strong></td>
				 <td style="background-color: lavender;"><strong>Latest Value 2 &nbsp;&nbsp;</strong></td>
				 <td style="background-color: lavender;"><strong>Latest Total Price &nbsp;&nbsp;</strong></td>
				
				</tr>	
				
			<%
			String dt_dc="";
				while(rs.next())
				{
					
				%>
					<tr>
					<td><%=++slNo%></td>
						
					<td><%=rs.getString("no_quot")%></td>
					<td><%=rs.getString("nm_model")%></td>
					<td><%=rs.getString("qty")%></td>
					<td><%=rs.getString("old_un_prc")%></td>
					<td><%=rs.getString("old_tax_val1")%></td>
					<td><%=rs.getString("old_tax_val2")%></td>
					<td><%=rs.getString("old_tot_prc")%></td>
					<td><%=rs.getString("un_prc")%></td>
					<td><%=rs.getString("tax_val1")%></td>
					<td><%=rs.getString("tax_val2")%></td>
					<td><%=rs.getString("tot_prc")%></td>
					
					
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				out.println("sql error in grn STORE." + e);
			}
		%>
		
	</table>

</div>
</body>
<%
if(slNo !=0)
{
%>
<a href="nego.jsp?exportToExcel=YES">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a>	

<%}
else
{%>
	<br>
		<!--  <b><center>No record(s) is available.</center></b>-->
<%}
	}%>
	
</html>