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
<form action="../Dashboard/Group_Asset_Report_Excel.jsp" method="post">
<div id="PreviewInstalledAssetReport">
	
		<%
		
			String sql="";
		//String id_sgrp = "";
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
		                    + "Group_Asset_Report.xls");
		 
		        }
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				 id_grp = request.getParameter("id_grp");
				 //id_sgrp = request.getParameter("id_sgrp");
				String tempSql = "";
				if(!id_grp.equals("All"))
				{
					/* if(!id_sgrp.equals(""))
					{
						tempSql =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+"";
					} */
					
						tempSql =" and wh.id_grp="+id_grp+"";
					
				}
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				if(UserType.equals("SUPER"))
					sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,emp.nm_emp,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dt_alloc, val_asst,nm_dept,nm_model,dt_po from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl,	M_Emp_User emp, M_Dept d,M_Model modl,M_Building bul where  allocate = 1 and wh.id_dept=d.id_dept and modl.id_model=wh.id_model and st_trvl = 0 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and fl.id_flr=wh.id_flr and emp.id_emp_user=wh.to_assign "+tempSql+"";
				 if(UserType.equals("DEPT"))
					sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,emp.nm_emp,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dt_alloc, val_asst,nm_dept,nm_model,dt_po from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl,	M_Emp_User emp, M_Dept d,M_Model modl,M_Building bul where allocate = 1 and wh.id_dept=d.id_dept and modl.id_model=wh.id_model and st_trvl = 0 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and fl.id_flr=wh.id_flr and emp.id_emp_user=wh.to_assign and wh.id_dept="+id_dept+" "+tempSql+"";
				 if(UserType.equals("IT"))
					sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,emp.nm_emp,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dt_alloc, val_asst,nm_dept,nm_model,dt_po from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl,	M_Emp_User emp, M_Dept d,M_Model modl,M_Building bul where allocate = 1 and wh.id_dept=d.id_dept and modl.id_model=wh.id_model and st_trvl = 0 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and fl.id_flr=wh.id_flr and emp.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' "+tempSql+"";
				
				 if(UserType.equals("NIT"))
						sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,emp.nm_emp,(replace(convert(NVARCHAR, dt_alloc, 103), ' ', '-')) as dt_alloc, val_asst,nm_dept,nm_model,dt_po from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl,	M_Emp_User emp, M_Dept d,M_Model modl,M_Building bul where allocate = 1 and wh.id_dept=d.id_dept and modl.id_model=wh.id_model and st_trvl = 0 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and fl.id_flr=wh.id_flr and emp.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' "+tempSql+"";
					
			//out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			%>
		<table border="1" width="200%" height="100%">
		
				<tr >
					<td colspan="16" style="background-color: blue;" ><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">Installed Asset Report</p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong><center>S No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset Id &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>S No. &nbsp;&nbsp;</center></strong></td>
							<td style="background-color: lavender;"><strong><center>Purchase Date &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset Description &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Manufacturer &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Model &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Location &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Sub Location &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Building &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Floor &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Employee &nbsp;&nbsp;</center></strong></td>
					<!-- <td style="background-color: lavender;"><strong><center>Cost Center:&nbsp;&nbsp;</center></strong></td> -->
					<td style="background-color: lavender;"><strong><center>Allocate Date &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Unit Cost in RS &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Department &nbsp;&nbsp;</center></strong></td>
				</tr>	
				
			<%
				while(rs.next())
				{%>
					<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString(1)%></center></td>
					<td><center><%=rs.getString(2)%></center></td>
					<td><center><%=rs.getString(3)%></center></td>
					<td><center><%=rs.getString(15)%></center></td>
					<td><center><%=rs.getString(4)%></center></td>
					<td><center><%=rs.getString(5)%></center></td>
					<td><center><%=rs.getString(14)%></center></td>
					<td><center><%=rs.getString(6)%></center></td>
					<td><center><%=rs.getString(7)%></center></td>
					<td><center><%=rs.getString(8)%></center></td>
					<td><center><%=rs.getString(9)%></center></td>
					<td><center><%=rs.getString(10)%></center></td>
					<%-- <td><center><%=rs.getString(10)%></center></td> --%>
					<td><center><%=rs.getString(11)%></center></td>
					<td><center><%=rs.getString(12)%></center></td>
					<td><center><%=rs.getString(13)%></center></td>
					
					
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
<a href="Group_Asset_Report.jsp?exportToExcel=YES&id_grp=<%=id_grp%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a>	
<%} %>
</html>