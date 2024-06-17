<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,java.util.Date, java.text.SimpleDateFormat,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calibration PM  Reports</title>
<script type ="text/javascript" src="js/jquery1.10.js"></script>
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
<div id="PreviewInstalledAssetReport">
	
		<%
		
		
		String cal_pm = "";
		String calDateMonth = "";
		String exportToExcel = request.getParameter("exportToExcel");
        
        if (exportToExcel != null
                && exportToExcel.toString().equalsIgnoreCase("YES")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename="
                    + "Calibration_PM_Report.xls");
 
        }
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				
				 cal_pm = request.getParameter("cal_pm");
				 calDateMonth = request.getParameter("calDateMonth");
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm");	
				Date date = formatter.parse(calDateMonth);
				
				
				
				sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,no_model,l.nm_loc, sl.nm_subl,"+
				"  dt_calb from A_Ware_House wh,M_Loc l,M_Subloc sl where l.id_loc=wh.id_loc and "+
				"sl.id_sloc=wh.id_subl and wh.calb='"+cal_pm+"' "+
				" and (CONVERT(CHAR(5), dt_calb, 120) + CONVERT(CHAR(2), dt_calb, 101) ) =  replace(convert(NVARCHAR, '"+formatter.format(date)+"', 101), ' ', '-')";
			
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			%>
		<table border="1" width="100%" height="100%">
		
				<tr >
					<td colspan="10" class="headerTR"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 500px;">Calibration PM Report</p></td>
				</tr>	
				<tr>
				    <td class="headerTD"><strong>S No. &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Asset ID &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Asset Name &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Serial No. &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Asset Description &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Manufacturer &nbsp;&nbsp;</strong></td>
					<!-- <td class="headerTD"><strong>Model No. &nbsp;&nbsp;</strong></td> -->
					<td class="headerTD"><strong>Location &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Sub Location &nbsp;&nbsp;</strong></td>
					<td class="headerTD"><strong>Date &nbsp;&nbsp;</strong></td>
				</tr>	
				
			<%
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
					<td><%=rs.getDate(9)%></td>
										
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				out.println("sql error in AMC Warranty.");
			}
		%>
			<tr>
			<%
	        if (exportToExcel == null) {
	    %>
	    <td colspan="10">
				<a href="Calibration_PM_Report.jsp?exportToExcel=YES&cal_pm=<%=cal_pm%>&calDateMonth=<%=calDateMonth%>">
				<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 600px;">Export To Excel</button>
				</a>		
				</td>
	    
	    <%
	        }
	    %>
		
	</table>
</div>
</body>
<%
	        }
	    %>
</html>