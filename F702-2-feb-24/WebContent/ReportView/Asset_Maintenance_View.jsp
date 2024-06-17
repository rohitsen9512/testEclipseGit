<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Asset ID Reports</title>
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
<%
String id_grp="",id_sgrp="",id_wh="";
        String exportToExcel = request.getParameter("exportToExcel");
        
        if (exportToExcel != null
                && exportToExcel.toString().equalsIgnoreCase("YES")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename="
                    + "Asset_Maintenance_Report.xls");
 
        }
    %>


<form action="" method="post">
<div id="PreviewAssetForStoreAsset">
	
		<%
		
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
					 
				 id_grp = request.getParameter("id_grp");
				 id_sgrp = request.getParameter("id_sgrp");
				 id_wh = request.getParameter("id_wh");
				String tempSql = "";
				
				if(!id_grp.equals("All"))
				{
					if(!id_sgrp.equals(""))
					{
						tempSql =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+"";
					}
					else
					{
						tempSql =" and wh.id_grp="+id_grp+"";
					}
					if(!id_wh.equals(""))
					{
						tempSql +=" and wh.id_wh="+id_wh+"";
					}
				}
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				if(UserType.equals("SUPER"))
				sql="select wh.id_wh_dyn,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,ds_pro,no_mfr,ds_asst, mfr,val_asst,l.nm_loc, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp,nm_assetdiv "+
				"from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Asset_Div ad,M_Model modl "+
						"where allocate=1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl  and fl.id_flr=wh.id_flr and modl.id_model=wh.id_model and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and ad.id_assetdiv=wh.id_grp  "+tempSql+"";
				
				
				 if(UserType.equals("DEPT"))
					 sql="select wh.id_wh_dyn,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,ds_pro,no_mfr,ds_asst, mfr,val_asst,l.nm_loc, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp,nm_assetdiv "+
								"from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Asset_Div ad,M_Model modl "+
										"where allocate=1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl  and fl.id_flr=wh.id_flr and modl.id_model=wh.id_model and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and ad.id_assetdiv=wh.id_grp and wh.id_dept="+id_dept+"   "+tempSql+"";
								
				 if(UserType.equals("IT"))
					 sql="select wh.id_wh_dyn,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,ds_pro,no_mfr,ds_asst, mfr,val_asst,l.nm_loc, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp,nm_assetdiv "+
								"from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Asset_Div ad,M_Model modl "+
										"where allocate=1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl  and fl.id_flr=wh.id_flr and modl.id_model=wh.id_model and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and ad.id_assetdiv=wh.id_grp and st_config='Yes'  "+tempSql+"";
				 
				 
				 if(UserType.equals("NIT"))
					 sql="select wh.id_wh_dyn,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,ds_pro,no_mfr,ds_asst, mfr,val_asst,l.nm_loc, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp,nm_assetdiv "+
								"from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Asset_Div ad,M_Model modl "+
										"where allocate=1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl  and fl.id_flr=wh.id_flr and modl.id_model=wh.id_model and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and ad.id_assetdiv=wh.id_grp and typ_asst = '"+UserType+"'  "+tempSql+"";
				 
					 
				//	out.print(sql);
					 
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			{%>
		<table class="table table-bordered"  id="testTable" border="1"  width="150%" height="100%">
		
				<tr>
					<td colspan="11"  style="background-color: blue;"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;"> Asset ID Report</p></td>
				</tr>	
				<tr>
				    <td class="headerTD"><strong><center>S No. &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Asset ID  &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Asset Manufacturer &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Asset Name &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Serial No. &nbsp;&nbsp;</center></strong></td>
					<!-- <td class="headerTD"><strong><center>Model No. &nbsp;&nbsp;</center></strong></td> -->
					<td class="headerTD"><strong><center>Purchase Date &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Department &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Computer Name &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Employee Name &nbsp;&nbsp;</center></strong></td>
						<td class="headerTD"><strong><center>Group Name &nbsp;&nbsp;</center></strong></td>
				</tr>	
				
			<%} 
				while(rs.next())
				{%>
					<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
					<td><center><%=rs.getString("mfr")%> 
					<td><center><%=rs.getString("ds_pro")%></center></td>
					<td><center><%=rs.getString("no_mfr")%></center></td>
					<td><center><%=rs.getString("nm_model")%></center></td>
					<td><center><%=rs.getString("dt_po")%></center></td>
					<td><center><%=rs.getString("nm_dept")%></center></td>
					<td><center><%=rs.getString("nm_computer")%></center></td>
					<td><center><%=rs.getString("nm_emp")%></center></td>
					<td><center><%=rs.getString("nm_assetdiv")%></center></td>
				</tr>
				<%} 
			}
			catch(Exception e)
			{
				out.println("sql error in StoreAssetView.");
			}
		%>
		<tr>
		<%
        if (exportToExcel == null) {
    %>
    <td colspan="12">
			<a href="Asset_Maintenance_View.jsp?exportToExcel=YES&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>&id_wh=<%=id_wh%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 600px;">Export To Excel</button>
			</a>		
			</td>
    
    <%
        }
    %>
			
		</tr>
	</table>
</div>
</form>
</body>
<%} %>
</html>