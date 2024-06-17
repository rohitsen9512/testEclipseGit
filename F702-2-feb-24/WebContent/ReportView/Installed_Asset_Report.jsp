<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*, java.text.SimpleDateFormat,java.text.DateFormat,com.Common.Common" %>
<%@page import="java.io.InputStream" %>
<%@page import="java.util.Properties" %>
<%@page import="dto.Common.UserAccessData" %>
<%
    InputStream stream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
    Properties props = new Properties();
    props.load(stream);
    String name = props.getProperty("ProductVersion");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Installed Asset Reports</title>
<link href="../CSS/watermark.css" rel="stylesheet" type="text/css">
<script type ="text/javascript" src="../common.js"></script>
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
<form action="../ReportViewExcel/Installed_Asset_Report_Excel.jsp" method="post">
<div id="PreviewInstalledAssetReport"  class="watermarked" data-watermark="TRAIL..">
	
		<%
		int slNo    =   0;
			String sql="";
		String id_sgrp = "";
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
		                    + "Installed_Asset_Report.xls");
		 
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
				 id_sgrp = request.getParameter("id_sgrp");
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
				}
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				
				String tempQuery="";
				String FlrId = (String)session.getAttribute("FlrId");
				if(!UserType.equals("SUPER"))
				if(!FlrId.equals(""))
				{
					String id_flr1[] = FlrId.split(",");
					for(int i=0 ; i < id_flr1.length ; i++)
					{
						if(tempQuery.equals(""))
						{
							tempQuery = " and (wh.id_flr = "+id_flr1[i]+"";
						}
						else
						{
							tempQuery += " or wh.id_flr = "+id_flr1[i]+"";
						}
					}
					tempQuery += ")";
				}
				
				//if(UserType.equals("SUPER"))
					/* sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,l.nm_loc, sl.nm_subl,bul.nm_building,emp.nm_emp,(replace(convert(VARCHAR, dt_alloc, 106), ' ', '-')) as dt_alloc, val_asst,nm_dept,nm_model,dt_po,nm_div"+
					" from A_Ware_House wh ,M_Company_Division cd, M_Loc l,M_Subloc sl,	M_Emp_User emp, M_Dept d,M_Model modl,M_Building bul "+
					" where  allocate = 1 and wh.id_dept=d.id_dept and modl.id_model=wh.id_model and st_trvl = 0 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building  and emp.id_emp_user=wh.to_assign and cd.id_div=wh.id_div "+tempSql+" "+tempQuery+" ";
				 */	
				
				sql="select device_status,mdl_num,process_typ,storeage_typ,ram_typ,id_wh_dyn,serial_no, ds_pro,no_mfr,modl.ds_asst, modl.mfr,l.nm_loc, sl.nm_subl,bul.nm_building,emp.nm_emp,mf.nm_flr, "+
				"   (replace(convert(VARCHAR, dt_alloc, 106), ' ', '-')) as dt_alloc, (replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,val_asst,nm_model,dt_po from A_Ware_House wh , "+
				"	M_Loc l,M_Subloc sl,	M_Emp_User emp,M_Model modl,M_Building bul,M_Floor mf where  allocate = 1  "+
				"  and modl.id_model=wh.id_model and st_trvl = 0  and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building  "+
			    "   and emp.id_emp_user=wh.to_assign and wh.id_flr=mf.id_flr";
				System.out.println(sql);
				/*  if(UserType.equals("DEPT"))
					sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,emp.nm_emp,(replace(convert(VARCHAR, dt_alloc, 106), ' ', '-')) as dt_alloc, val_asst,nm_dept,nm_model,dt_po from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl,	M_Emp_User emp, M_Dept d,M_Model modl,M_Building bul where allocate = 1 and wh.id_dept=d.id_dept and modl.id_model=wh.id_model and st_trvl = 0 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and fl.id_flr=wh.id_flr and emp.id_emp_user=wh.to_assign and wh.id_dept="+id_dept+" "+tempSql+"";
				 if(UserType.equals("IT"))
					sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,emp.nm_emp,(replace(convert(VARCHAR, dt_alloc, 106), ' ', '-')) as dt_alloc, val_asst,nm_dept,nm_model,dt_po from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl,	M_Emp_User emp, M_Dept d,M_Model modl,M_Building bul where allocate = 1 and wh.id_dept=d.id_dept and modl.id_model=wh.id_model and st_trvl = 0 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and fl.id_flr=wh.id_flr and emp.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' "+tempSql+"";
				
				 if(UserType.equals("NIT"))
						sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,emp.nm_emp,(replace(convert(VARCHAR, dt_alloc, 106), ' ', '-')) as dt_alloc, val_asst,nm_dept,nm_model,dt_po from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl,	M_Emp_User emp, M_Dept d,M_Model modl,M_Building bul where allocate = 1 and wh.id_dept=d.id_dept and modl.id_model=wh.id_model and st_trvl = 0 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and fl.id_flr=wh.id_flr and emp.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' "+tempSql+"";
					 */
			//
			//out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
				
			
				
			%>
		<table style="border: 1px solid white;" border="1" width="200%" height="100%">
		
				<tr >
					<td colspan="22" style="background-color: blue;" ><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">Installed Asset Report</p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong><center>S No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset ID &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Serial No. &nbsp;&nbsp;</center></strong></td>
					
					<!-- <td style="background-color: lavender;"><strong><center>Model No.&nbsp;&nbsp;</center></strong></td> -->
					<td style="background-color: lavender;"><strong><center>Process Type &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Storage Type &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>RAM Type  &nbsp;&nbsp;</center></strong></td>
				
				    <td style="background-color: lavender;"><strong><center>Device Status &nbsp;&nbsp;</center></strong></td>
				
					<td style="background-color: lavender;"><strong><center>Purchase Date &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset Description &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Manufacturer &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>City &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Sub Location &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Building &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Floor &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Employee  &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Allocate Date &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Unit Cost in RS  &nbsp;&nbsp;</center></strong></td>
					<!-- <td style="background-color: lavender;"><strong><center>Sub-Function &nbsp;&nbsp;</center></strong></td> -->
				</tr>	
				
			<%
				while(rs.next())
				{
					String device_status="";
					if(rs.getString("device_status").equals("allct_to_emp")){
						device_status="Allocated to employee";
					}
					if(rs.getString("device_status").equals("under_service")){
						device_status="Under service ";
					}
					if(rs.getString("device_status").equals("not_workin")){
						device_status="Not Working ";
					}
					if(rs.getString("device_status").equals("in_store")){
						device_status="In Store  ";
					}
					if(rs.getString("device_status").equals("temporary_laptop")){
						device_status="Temporary Laptop  ";
					}
					if(rs.getString("device_status").equals("buyback")){
						device_status="Buy back  ";
					}
					if(rs.getString("device_status").equals("physical_dmg_mjr")){
						device_status="Physical Damage (Major) ";
					}
					if(rs.getString("device_status").equals("physical_dmg_mnr")){
						device_status="Physical Damage (Minor) ";
					}
					if(rs.getString("device_status").equals("scraped")){
						device_status="Scrapped / disposed ";
					} 
				%>
					<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
					<td><center><%=rs.getString("nm_model")%></center></td>
					<td><center><%=rs.getString("serial_no")%></center></td>
					
					<%-- <td><center><%=rs.getString("mdl_num")%></center></td> --%>
					<td><center><%=rs.getString("process_typ")%></center></td>
					<td><center><%=rs.getString("storeage_typ")%></center></td>
					<td><center><%=rs.getString("ram_typ")%></center></td>
				<td><center><%=device_status%></center></td>
				
					
					
					<td><center><%=rs.getString("dt_po")%></center></td>
					<td><center><%=rs.getString("ds_pro")%></center></td>
					<td><center><%=rs.getString("no_mfr")%></center></td>
					<td><center><%=rs.getString("nm_loc")%></center></td>
					<td><center><%=rs.getString("nm_subl")%></center></td>
					<td><center><%=rs.getString("nm_building")%></center></td>
					<td><center><%=rs.getString("nm_flr")%></center></td>
					<td><center><%=rs.getString("nm_emp")%></center></td>
					<td><center><%=rs.getString("dt_alloc")%></center></td>
					<%-- <td><center><%=rs.getString(10)%></center></td> --%>
					<td><center><%=rs.getString("val_asst")%></center></td>
					<%-- <td><center><%=rs.getString("nm_bu")%></center></td> --%>
					
					
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				//out.println("sql error in grn STORE." + e);
			}
		%>
		
	</table>

</div>

</body>
<%
if(slNo !=0 && name.equals("UAT") || name.equals("TRAIL"))
{
%>
<button style="margin-left: 400px;" name="UAT" type="button"  class="btn btn-primary" onclick="exportExcel()">Export To Excel</button>
<%
}
else if(slNo !=0 && name.equals("PRODUCT"))
{
%>
<a href="Installed_Asset_Report.jsp?exportToExcel=YES&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a>	

<%}
else
{%>
	<br>
		<!--  <b><center>No record(s) is available.</center></b>-->
<%}
	}%>
	
<%
if(name.equals("PRODUCT")){
%>
<script language="JavaScript">
		$('#PreviewInstalledAssetReport').removeClass('watermarked');
		</script>
<%
	}else{
		%>
		<script>        
		Array.from(document.querySelectorAll('.watermarked')).forEach(function(el) {
		        el.dataset.watermark = (el.dataset.watermark + ' ').repeat(10000);
		    });
		</script>
		<%
	}
%>	

</html>