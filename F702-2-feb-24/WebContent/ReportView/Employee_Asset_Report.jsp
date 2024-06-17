<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common,dto.Common.DtoCommon" %>
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
<title>Employee Asset Report</title>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
</style>
<!-- <link href="../CSS/watermark.css" rel="stylesheet" type="text/css">
<script type ="text/javascript" src="../common.js"></script>
<script type ="text/javascript" src="js/jquery1.10.js"></script> -->


<!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="../plugins/fontawesome-free/css/all.min.css">
  <!-- overlayScrollbars -->
  <link rel="stylesheet" href="../plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
   
  <!-- Theme style -->
  <link rel="stylesheet" href="../dist/css/adminlte.min.css">
 
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
 <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="../CSS/home.css" rel="stylesheet" type="text/css">
<link href="../CSS/jquery-ui.css" rel="stylesheet" type="text/css">
<!-- DataTables CSS -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.4/css/jquery.dataTables.min.css">
  <link rel="stylesheet" href="../plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
  <link rel="stylesheet" href="../plugins/datatables-buttons/css/buttons.bootstrap4.min.css">

<!-- jQuery -->
<script src="../plugins/jquery/jquery.min.js"></script> 
  <script type ="text/javascript" src="../js/jquery1.10.js"></script>
<script type ="text/javascript" src="../js/jquery-ui.js"></script>

<!-- Bootstrap 4 -->
<script src="../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- AdminLTE for demo purposes -->
<script src="../dist/js/demo.js"></script>
<!-- DataTables  & Plugins -->
<script src="../plugins/datatables/jquery.dataTables.min.js"></script>
<script src="../plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="../plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="../plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
<script src="../plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
<script src="../plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
<script src="../plugins/jszip/jszip.min.js"></script>
<script src="../plugins/pdfmake/pdfmake.min.js"></script>
<script src="../plugins/pdfmake/vfs_fonts.js"></script>
<script src="../plugins/datatables-buttons/js/buttons.html5.min.js"></script>
<script src="../plugins/datatables-buttons/js/buttons.print.min.js"></script>
<script src="../plugins/datatables-buttons/js/buttons.colVis.min.js"></script>
<!-- overlayScrollbars -->
<script src="../plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>

<!-- AdminLTE App -->
<script src="../dist/js/adminlte.min.js"></script>
<!-- Page specific script -->
<!-- bs-custom-file-input -->
<script src="../plugins/bs-custom-file-input/bs-custom-file-input.min.js"></script>
<!-- jQuery Mapael -->
<script src="../plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
<script src="../plugins/raphael/raphael.min.js"></script>
<script src="../plugins/jquery-mapael/jquery.mapael.min.js"></script>
<script src="../plugins/jquery-mapael/maps/usa_states.min.js"></script>

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
<div id="PreviewEmployeeAssetReport" class="watermarked" data-watermark="TRAIL..">
	
		<%
		
		int slNo    =   0;
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Employee_Asset_Report.xls");
		 
		        }
		    
		
		String to_assign ="";
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				 to_assign = request.getParameter("to_assign");
				String tempSql="";
				if(!to_assign.equals("all"))
				{
					tempSql =" and  to_assign="+to_assign+"";
				}
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				int UserId = (Integer)session.getAttribute("UserId");
				String tempSql1="";
				String likeTempQuery="";
				DtoCommon dtoCommon = new DtoCommon();
				/* if(UserType.equals("SUPER")){ */
					if(to_assign.equals("all"))	
						sql="select id_wh_dyn,ds_pro,serial_no,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc  ,nm_flr,mdl_num,process_typ,storeage_typ,ram_typ,device_status,appNo  from A_Ware_House wh , M_Floor fl ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building   and fl.id_flr=wh.id_flr order by dt_alloc";
					else
						sql="select  id_wh_dyn,ds_pro,serial_no,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_flr,mdl_num,process_typ,storeage_typ,ram_typ ,device_status,appNo from A_Ware_House wh , M_Floor fl,M_Emp_user e, M_Loc l,M_Subloc sl,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building  and fl.id_flr=wh.id_flr "+tempSql+" order by dt_alloc";
						
				
				
			//	out.print(sql);
				/* } */
				/* if(UserType.equals("SUPER")){
					
					sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept  from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept order by dt_alloc ";
				}
					else
						sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept  from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept "+tempSql+" order by dt_alloc";
				
				
				if(UserType.equals("DEPT")){
					if(to_assign.equals("all"))	
						sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept  from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept wh.id_dept="+id_dept+" "+tempSql+" order by dt_alloc";
					else
						sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept  from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept and  wh.id_dept="+id_dept+"  order by dt_alloc";
					
				}
				if(UserType.equals("DEPT")){
					sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept  from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept and wh.id_dept="+id_dept+" "+tempSql+" order by dt_alloc ";
					}
				else
					sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept  from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept "+tempSql+" order by dt_alloc  ";
				
				
				 if(UserType.equals("IT")){
					if(to_assign.equals("all"))	
						sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept  from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept and typ_asst = '"+UserType+"' "+tempSql+" order by dt_alloc";
					else
						sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept  from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept and typ_asst = '"+UserType+"'  "+tempSql+" order by dt_alloc ";
									
				} */
				
				
				/* if(UserType.equals("NIT")){
					if(to_assign.equals("all"))
						sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept and typ_asst = '"+UserType+"' order by dt_alloc";
					else 
						sql="select id_wh_dyn,ds_pro,no_mfr,l.nm_loc, sl.nm_subl,nm_building,e.nm_emp,dt_alloc,nm_dept from A_Ware_House wh ,M_Emp_user e, M_Loc l,M_Subloc sl,M_Dept de,M_Building bul where allocate = 1 and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and e.id_emp_user=wh.to_assign and bul.id_building=wh.id_building and wh.id_dept=de.id_dept and typ_asst = '"+UserType+"' "+tempSql+" order by dt_alloc";
				} */
				 
		
			
								
				//out.print(sql);
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			{%>
			
			<div class="card-body"> 
			
	<table id="EmployeeAssetReport"
		class="table table-bordered table-hover EmployeeAssetReport">
		<!-- <table style="border: 1px solid white;" border="1" width="100%" height="100%">
		
				<tr >
					<td colspan="16" class="headerTR"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 500px;">Employee Asset Detail Report</p></td>
				</tr> -->	
				<thead><tr>
				    <th><strong><center>S No. &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Asset ID &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Asset Name &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Serial No. &nbsp;&nbsp;</center></strong></th>
					
						<th><strong><center>Asset Ref No &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Process Type &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Storage Type &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>RAM Type  &nbsp;&nbsp;</center></strong></th>
				
				 <th><strong><center>Device Status&nbsp;&nbsp;</center></strong></th>
				
					
					<!-- </th><strong><center>Division &nbsp;&nbsp;</center></strong></th>
				 -->	<th><strong><center>Location &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Sub Location &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Building &nbsp;&nbsp;</center></strong></th>
					
					<th><strong><center>Floor &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Emp Name  &nbsp;&nbsp;</center></strong></th>
				 <!-- </th><strong><center>BU &nbsp;&nbsp;</center></strong></th>  -->
					<th><strong><center>Assigned Date &nbsp;&nbsp;</center></strong></th>
				</tr></thead><tbody>	
				
			<%} 
				while(rs.next())
				{
				
					String device_status="";
					if(rs.getString("device_status").equals("allct_to_emp")){
						device_status="Allocated to employee";
					}
					if(rs.getString("device_status").equals("allct_to_emp_temp")){
						device_status="Allocated to employee Temporary";
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
					<td><center><%=rs.getString(1)%></center></td>
					<td><center><%=rs.getString(2)%></center></td>
					<td><center><%=rs.getString(3)%></center></td>
					<td><center><%=rs.getString("appNo")%></center></td>
					<td><center><%=rs.getString("process_typ")%></center></td>
					<td><center><%=rs.getString("storeage_typ")%></center></td>
					<td><center><%=rs.getString("ram_typ")%></center></td>
				
				<td><center><%=device_status%></center></td>
				
					<%-- <td><center><%=rs.getString(10)%></center></td>
					 --%><td><center><%=rs.getString(4)%></center></td>
					<td><center><%=rs.getString(5)%></center></td>
					<%-- <td><center><%=rs.getString(6)%></center></td> --%>
					<td><center><%=rs.getString(6)%></center></td>
					<td><center><%=rs.getString(9)%></center></td>
					<td><center><%=rs.getString(7)%></center></td>
					<%-- <td><center><%=rs.getString("nm_bu")%></center></td> --%>
					<td><center><%=rs.getString(8)%></center></td>
					
										
				</tr>
				<%}
			}
			catch(Exception e)
			{
				//out.println("sql error in grn STORE." + e);
			}
		%>
		</tbody>
	</table>

</div>

</body>
<%
if(slNo !=0 && name.equals("UAT") || name.equals("TRAIL"))
{
%>
<!-- <button style="margin-left: 400px;" name="UAT" type="button"  class="btn btn-primary" onclick="exportExcel()">Export To Excel</button>
 --><%
}
else if(slNo !=0 && name.equals("PRODUCT"))
{
%>
<%-- <a href="Employee_Asset_Report.jsp?exportToExcel=YES&to_assign=<%=to_assign%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a>	 --%>

<%}
else
{%>
	<!-- <br>
		<b><center>No record(s) is available.</center></b> -->
<%}
	}%>
	
<%
if(name.equals("PRODUCT")){
%>
<!-- <script language="JavaScript">
		$('#PreviewEmployeeAssetReport').removeClass('watermarked');
		</script> -->
<%
	}else{
		%>
		<script>        
		/* Array.from(document.querySelectorAll('.watermarked')).forEach(function(el) {
		        el.dataset.watermark = (el.dataset.watermark + ' ').repeat(10000);
		    }); */
		</script>
		<%
	}
%>	
<!-- DataTables  Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('EmployeeAssetReport','Employee_Asset_Report_List');
/* $('.sorting').show();
$('.sorting1').show(); */
</script>
</html>