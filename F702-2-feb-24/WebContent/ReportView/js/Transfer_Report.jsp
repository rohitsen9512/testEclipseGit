<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*, java.text.SimpleDateFormat,java.util.*,java.text.DateFormat,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transfer</title>

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
<div id="PreviewGRNApprovePendingReport">
	
		<%
		int slNo    =   0;
		String id_grp = "";
		String id_sgrp = "";
		String sql="";
		String startdate = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
	
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "AssetMasterReport.jsp.xls");
		 
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
					 id_grp = request.getParameter("id_grp");
					 id_sgrp = request.getParameter("id_sgrp");
					 
					 if(!startdate.equals(""))
						{
						 startdate = dateFormatNeeded.format(userDateFormat.parse(startdate));
						}
				 
				String tempSql = "";
				
				String tempSql1 ="";
				if(!id_grp.equals("All"))
				{
					if(!id_sgrp.equals(""))
					{
						tempSql1 =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+"";
					}
					else
					{
						tempSql1 =" and wh.id_grp="+id_grp+"";
					}
				}
				
				
				sql= "select wh.id_wh_dyn, e.nm_emp, wh.ds_pro,(replace(convert(NVARCHAR, ih.dt_start, 103), ' ', '-')) , case when ih.dt_start_tran='' then (replace(convert(NVARCHAR, ih.dt_de_allocate, 103), ' ', '-')) else "+
						"(replace(convert(NVARCHAR, ih.dt_start_tran, 103), ' ', '-')) END as test, l.nm_loc, sl.nm_subl, fl.nm_flr from A_ware_house wh,"+
						"A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl, M_Floor fl where l.id_loc=ih.id_loc "+
						" and sl.id_sloc=ih.id_subl and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh and fl.id_flr=ih.id_flr "+
						" and ih.dt_start >='"+startdate+"' " +tempSql1+"";
						
					//out.print(sql);							
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
		<table border="1" width="120%" height="100%">
		

				<tr >
					<td colspan="10" style="background-color: blue;" width="100%"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;"><strong>Asset History Report</strong></p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong>SL No &nbsp;&nbsp;</strong></td>
				    <td style="background-color: lavender;"><strong>Asset Id &nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Employee Name&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Asset Name&nbsp;&nbsp;</strong></td>
					
					<td style="background-color: lavender;"><strong>Start Date&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>End Date&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>City&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Unit&nbsp;&nbsp;</strong></td>
					<td style="background-color: lavender;"><strong>Block1111111111&nbsp;&nbsp;</strong></td>
					<!-- <td style="background-color: lavender;"><strong>Cost Center&nbsp;&nbsp;</strong></td>
					 -->
				</tr>	
				
			<%
			String dt_end="";
				while(rs.next())
				{
					dt_end = rs.getString(5);
					if(dt_end == null)
						dt_end = "---";
				%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString(1)%></td>
					<td><%=rs.getString(2)%></td>
					<td><%=rs.getString(3)%></td>
					<td><%=rs.getString(4)%></td>
					<td><%=dt_end%></td>
					
					<td><%=rs.getString(6)%></td>
					<td><%=rs.getString(7)%></td>
					<td><%=rs.getString(8)%></td>
					<%-- <td><%=rs.getString(9)%></td>
					 --%>
					
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				out.println("sql error in Invoice Detail");
			}
		%>
		
	</table>

</div>
</body>
<%
if(slNo !=0)
{
%>
<a href="AssetMasterReport.jsp?exportToExcel=YES&startdate=<%=startdate%>">
			<button name="cancel" type="button"  class="btn btn-primary" 
			style="margin-left: 600px;">Export To Excel</button>
</a>	

<%}
else
{%>
	<br>
		<b><center>No record(s) is available.</center></b>
<%}
	}%>
	
</html>