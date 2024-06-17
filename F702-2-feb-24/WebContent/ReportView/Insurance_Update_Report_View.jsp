<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*, java.text.SimpleDateFormat,java.text.DateFormat,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Installed Asset Reports</title>

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
<form action="ReportView/Insurance_Update_Report_View.jsp" method="post" target="_new">
<div id="PreviewInstalledAssetReport">
	
		<%
		
			String sql="";
		String id_finance = "",std_finance="",end_finance="",std_finance1="",end_finance1="";
		String id_grp = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Insurance_Update_Report.xls");
		 
		        }
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				id_finance = request.getParameter("id_finance");
				id_grp = request.getParameter("id_grp");
				
				ps=con.prepareStatement("select std_finance,end_finance,(replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance1,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance1 from M_Finance_Year where id_fincance="+id_finance+"");
				rs=ps.executeQuery();
				if(rs.next())
				{
					std_finance = rs.getString(1);
					end_finance = rs.getString(2);
					std_finance1 = rs.getString(3);
					end_finance1 = rs.getString(4);
				}
				sql="select ins_val,COUNT(qty_asst) as qty,(COUNT(qty_asst))*ins_val as totIns,nm_s_assetdiv from Insurance_Update iu, "+
						" A_Ware_House wh ,M_Subasset_Div sgrp where wh.id_sgrp=iu.id_sgrp and sgrp.id_s_assetdiv=wh.id_sgrp and wh.id_grp="+id_grp+" and allocate !=3 and dt_inv >= (replace(convert(NVARCHAR, '"+std_finance+"', 106), ' ', '-')) "+
						"and dt_inv <= (replace(convert(NVARCHAR, '"+end_finance+"', 106), ' ', '-')) group by wh.id_sgrp,ins_val,nm_s_assetdiv";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			%>
		<table border="1" width="100%" height="100%">
		
				<tr >
					<td colspan="5" style="background-color: blue;" width="100%"><center><p style="background-color: blue; font-size: 24px;color: white;">Insurance Report For <b>(<%=std_finance1 %> - <%=end_finance1 %> )</b></p></center></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong>S No. &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Sub Group&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Asset Qty &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Insurance Value &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Total Insurance Value &nbsp;&nbsp;</strong></td>
				</tr>	
				
			<%
				while(rs.next())
				{%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString("nm_s_assetdiv")%></td>
					<td><%=rs.getString("qty")%></td>
					<td><%=rs.getString("ins_val")%></td>
					<td><%=rs.getString("totIns")%></td>
					
					
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
</form>
</body>
<a href="Insurance_Update_Report_View.jsp?exportToExcel=YES&id_grp=<%=id_grp%>&id_finance=<%=id_finance%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a>	
<%} %>
</html>