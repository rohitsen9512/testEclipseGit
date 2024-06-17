<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vendor Report</title>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
</style>
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
<div id="PreviewEmployeeAssetReport">
	
		<%
		
		
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Vendor_Report.xls");
		 
		        }
		    
		
		String venType ="";
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				venType = request.getParameter("venType");
				//out.print(venType);
				if(venType.equals("All"))
				{
					sql="select * from M_Vendor order by nm_ven";
				}
				else if(venType.equals("Service"))
					{
						sql="select * from M_Vendor where service = '"+venType+"' order by nm_ven";
					}
				else
				{
					sql="select * from M_Vendor where Procured = '"+venType+"' order by nm_ven";	
					
				}
				
				
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			{%>
		<table  width="100%" height="100%" border="1">
		
				<tr >
					<td colspan="7" class="headerTR"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 500px;">Vendor Detail Report</p></td>
				</tr>	
				<tr>
                <td ><b>S No.</b></td>
                <td style="width:100px;"><b>Vendor Name</b></td>
                <td style="width:100px;"><b>Vendor Address</b></td>
                <td style="width:100px;"><b>Url</b></td>
                <td ><b>Phone No.</b></td>
                <td ><b>Pan No.</b></td>
                 <td ><b>Contact Name</b></td>
            </tr>
				
			<%} 
				while(rs.next())
				{
					String url="--";
					String pan="--";
					String nm_contact="--";
					if(rs.getString("url") == null)  url="--"; else url = rs.getString("url");
					if(rs.getString("pan") == null)  pan="--"; else pan = rs.getString("pan");
					if(rs.getString("nm_contact") == null)  nm_contact="--"; else nm_contact = rs.getString("nm_contact");
					
				%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString("nm_ven")%></td>
					<td><%=rs.getString("add1")%></td>
					<td><%=url%></td>
					<td><%=rs.getString("phone")%></td>
					<td><%=pan%></td>
					<td><%=nm_contact%></td>
				</tr>
				<%} 
			}
			catch(Exception e)
			{
				out.println("sql error in Employee Asset View.");
			}
			%>
			<tr>
			<%
	        if (exportToExcel == null) {
	    %>
	    <td colspan="10">
				<a href="Vendor_Report_View.jsp?exportToExcel=YES&venType=<%=venType%>">
				<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 600px;">Export To Excel</button>
				</a>		
				</td>
	    
	    <%
	        }
	    %>
		
	</table>
</div>
</body>
<%} %>
</html>