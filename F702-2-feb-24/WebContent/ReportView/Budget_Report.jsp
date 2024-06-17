<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Budget Report</title>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
</style>
<script type ="text/javascript" src="js/jquery1.10.js"></script></head>
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
		                    + "Budget_Report.xls");
		 
		        }
		    
		
		String id_finance ="",adhoc="";
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				 id_finance = request.getParameter("id_finance");
				 adhoc = request.getParameter("adhoc");
				
						String UserType = (String)session.getAttribute("UserType");
						
							sql="select nm_cc,nm_dept,nm_s_function,nm_bu,nm_model,b.id_model,(replace(convert(NVARCHAR, fy.std_finance, 103), ' ', '-')) std_finance,(replace(convert(NVARCHAR, fy.end_finance, 103), ' ', '-')) end_finance ,annual_bud,annual_bud-annual_bud_done as remaininngBgt,annual_bud_done,trans,category from M_Budget b,M_Company_Costcenter cc,M_Dept d,M_S_Function ms,M_BU bu,M_Model m,M_Finance_Year fy where b.id_dept=d.id_dept and b.id_cc=cc.id_cc and b.id_finance=fy.id_fincance and b.id_s_function=ms.id_s_function and b.id_bu=bu.id_bu and b.id_model=m.id_model and b.adhoc='"+adhoc+"' and id_finance="+id_finance+" ";
		
						System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			{%>
		<table border="1" width="100%" height="100%">
		
				<tr >
					<td colspan="12" class="headerTR"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 500px;">Budget Detail Report</p></td>
				</tr>	
				<tr>
				    <td class="headerTD"><strong>S No. &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Finance Year &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Department Name &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>BU Name &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Function Name &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>SubFunction Name &nbsp;&nbsp;</strong></td>
					<!-- <td class="headerTD"><strong>Model No. &nbsp;&nbsp;</strong></td> -->
					<td class="headerTD"><strong>Total Budget &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Total Budget Done &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Total Budget Remaining &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Transaction &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Category &nbsp;&nbsp;</strong></td>
					
				</tr>	
				
			<%} 
			String nm_model="";
				while(rs.next())
				{
					nm_model = rs.getString("nm_model");
					if(nm_model == null)
						nm_model = "---";
				
				%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString("std_finance")%> <b>-</b> <%=rs.getString("end_finance")%></td>
					<td><%=rs.getString("nm_dept")%></td>
					<td><%=rs.getString("nm_cc")%></td>
					<td><%=rs.getString("nm_s_function")%></td>
					<td><%=rs.getString("nm_bu")%></td>
					<td><%=nm_model%></td>
					<td><%=rs.getString("annual_bud")%></td>
					<td><%=rs.getString("annual_bud_done")%></td>
					<td><%=rs.getString("remaininngBgt")%></td>
					<td><%=rs.getString("trans")%></td>
					<td><%=rs.getString("category")%></td>
				</tr>
				<%} 
			}
			catch(Exception e)
			{
				out.println(e);
			}
			%>
			<tr>
			<%
	        if (exportToExcel == null) {
	    %>
	    <td colspan="12">
				<a href="Budget_Report.jsp?exportToExcel=YES&id_finance=<%=id_finance%>&adhoc=<%=adhoc%>">
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