<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common,dto.Common.DtoCommon" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Location Asset Report</title>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
</style>
<script type ="text/javascript" src="js/jquery1.10.js"></script>

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
<div id="PreviewEmployeeAssetReport">
	
		<%
		
		
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Employee_Asset_Report.xls");
		 
		        }
		    
		
		String id_country ="";
		String id_loc ="";
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				id_loc = request.getParameter("id_loc");
				
				String tempSql="";
				String tempSql1="";
				if(!id_loc.equals("all"))
				{
					tempSql =" and  wh.id_loc="+id_loc+"";
				}
				else
				{
					tempSql ="";
				}
				
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				int UserId = (Integer)session.getAttribute("UserId");
				
				String likeTempQuery="";
				DtoCommon dtoCommon = new DtoCommon();
					
						sql="select  id_wh_dyn,ds_pro,serial_no as no_mfr,l.nm_loc, sl.nm_subl,dt_alloc,mdl_num,process_typ,storeage_typ,ram_typ ,device_status from A_Ware_House wh , M_Loc l,M_Subloc sl where   l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl "+tempSql+"  order by dt_alloc";
			System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			{%>
			
			<div class="card-body"> 
			
	<table id="locAssetReport"
		class="table table-bordered table-hover locAssetReport">
		<!-- <table style="border: 1px solid white;" border="1" width="100%" height="100%">
		
				<tr >
					<td colspan="16" class="headerTR"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 500px;">Location Asset Detail Report</p></td>
				</tr>	 -->
				<thead><tr>
				    <th><strong><center>S No. &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Asset ID &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Asset Name &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Serial No. &nbsp;&nbsp;</center></strong></th>
					
						<!-- <th><strong><center>Model No. &nbsp;&nbsp;</center></strong></th> -->
					<th><strong><center>Process Type &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Storage Type &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>RAM Type  &nbsp;&nbsp;</center></strong></th>
				
				 <th><strong><center>Device Status &nbsp;&nbsp;</center></strong></th>
				
					
					<!-- <th><strong><center>Division:&nbsp;&nbsp;</center></strong></th>
				 -->
				 <th><strong><center>City &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>City Sub-location &nbsp;&nbsp;</center></strong></th>
				
				</tr>	</thead><tbody>
				
			<%} 
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
					if(rs.getString("device_status").equals("in_store") || rs.getString("device_status").equals("")){
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
					
						
					<%-- <td><center><%=rs.getString("mdl_num")%></center></td> --%>
					<td><center><%=rs.getString("process_typ")%></center></td>
					<td><center><%=rs.getString("storeage_typ")%></center></td>
					<td><center><%=rs.getString("ram_typ")%></center></td>
				
				<td><center><%=device_status%></center></td>
				
					 <td><center><%=rs.getString("nm_loc")%></center></td>
					<td><center><%=rs.getString("nm_subl")%></center></td>
					
					
										
				</tr>
				<%} 
			}
			catch(Exception e)
			{
				out.println("sql error in Employee Asset View.");
			}
			%>
			
			<%
	        if (exportToExcel == null) {
	    %>
	   <%--  <td colspan="12">
				<a href="Loc_Asset_Report.jsp?exportToExcel=YES&id_loc=<%=id_loc%>">
				<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 600px;">Export To Excel</button>
				</a>		
				</td> --%>
	    
	    <%
	        }
	    %>
		</tbody>
	</table>
</div>
</body>
<%} %>

<!-- DataTables  Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('locAssetReport','Location_Asset_Report_List');

</script>
</html>