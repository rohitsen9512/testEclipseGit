

<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*, java.text.SimpleDateFormat,com.Common.Common" %>
<%@ page import="dto.Common.DtoCommon" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- <link href="WebContent/CSS/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"> -->

<title>Category Asset Report</title>
<!-- <script type ="text/javascript" src="js/jquery1.10.js"></script> -->
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
<!-- <script src="../dist/js/demo.js"></script>  -->
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
		int UserId = (int)session.getAttribute("UserId");	
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
<div id="PreviewAssetForLocAsset">
	
	
		<%
		
		String label = "";
		String nmsgrp="";
		String id_grp = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
		
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Location_Asset_report.xls");
		 
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
				
				
				String tempSql = "";
				
				String tempSql1 ="";
				System.out.println(request.getParameter("id_grp"));
				if(request.getParameter("id_grp")!=null ){
					if(!(request.getParameter("id_grp")).equals("-Select--")){
					tempSql="and nm_assetdiv='"+request.getParameter("id_grp")+"'";
					}
				}
				
				if(request.getParameter("id_sgrp")!=null){
					nmsgrp="and nm_s_assetdiv='"+request.getParameter("id_sgrp")+"'";
				}
				
				if(request.getParameter("id_loc")!=null){
					nmsgrp="and wh.id_loc='"+request.getParameter("id_loc")+"'";
				}
				
				label = request.getParameter("label");
				if(label.equals("Allocated to employee")){
					tempSql1=" and device_status='allct_to_emp'";
				}
				if(label.equals("Under service")){
					tempSql1=" and device_status='under_service'";
				}
				if(label.equals("Not Working")){
					tempSql1=" and device_status='not_workin'";
				}
				if(label.equals("In Store")){
					tempSql1=" and device_status='in_store'";
				}
				if(label.equals("Temporary Laptop")){
					tempSql1=" and device_status='temporary_laptop'";
				}
				if(label.equals("Buy back")){
					tempSql1=" and device_status='buyback'";
				}
				if(label.equals("Physical Damage (Major)")){
					tempSql1=" and device_status='physical_dmg_mjr'";
				}
				if(label.equals("Physical Damage (Minor)")){
					tempSql1=" and device_status='physical_dmg_mnr'";
				}
				if(label.equals("Scrapped / disposed")){
					tempSql1=" and device_status='scraped'";
				}
				if(label.equals("Advance Laptop")){
					tempSql1=" and device_status='advance_laptop'";
				}
				if(label.equals("Refreshment - working")){
					tempSql1=" and device_status='refreshment_working'";
				}
				if(label.equals("Refreshment - Faulty")){
					tempSql1=" and device_status='refreshment_faulty'";
				}
				
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				DtoCommon dtoCommon = new DtoCommon();
				if(!UserType.equals("Super"))
				{
					String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh",request);
					sql="select device_status,nm_assetdiv, mdl_num,process_typ,storeage_typ,ram_typ,id_wh_dyn, ds_pro, mm.mfr, no_mfr,qty_asst, dt_allocate,val_asst,wh.to_assign,wh.serial_no,wh.no_po,no_model,nm_model,wh.allocate ,wh.no_inv,(replace(convert(NVARCHAR, wh.dt_inv, 103), ' ', '-')) as dt_inv,l.nm_loc, sl.nm_subl,mm.typ_asst, nm_model, wh.no_bd,wh.appNo ,nm_s_assetdiv ,nm_emp from A_Ware_House wh left join M_Emp_User emp on wh.to_assign=emp.id_emp_user  ,M_Model mm,M_Asset_Div ad,M_Subasset_Div sd, M_Loc l,M_Subloc sl where l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl  and mm.id_model= wh.id_model and ad.id_assetdiv=wh.id_grp and sd.id_s_assetdiv=wh.id_sgrp  AND mm.typ_asst !='soft'	" +tempSql1+" "+nmsgrp+" " +tempSql+" "+tempQuery2+" ";
				}
				else
				{
					sql="select device_status,nm_assetdiv, mdl_num,process_typ,storeage_typ,ram_typ,id_wh_dyn, ds_pro, mm.mfr, no_mfr,qty_asst, dt_allocate,val_asst,wh.to_assign,wh.serial_no,wh.no_po,no_model,nm_model,wh.allocate ,wh.no_inv,(replace(convert(NVARCHAR, wh.dt_inv, 103), ' ', '-')) as dt_inv,l.nm_loc, sl.nm_subl,mm.typ_asst, nm_model, wh.no_bd,wh.appNo ,nm_s_assetdiv ,nm_emp from A_Ware_House wh left join M_Emp_User emp on wh.to_assign=emp.id_emp_user  ,M_Model mm,M_Asset_Div ad,M_Subasset_Div sd, M_Loc l,M_Subloc sl where l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and mm.id_model= wh.id_model and ad.id_assetdiv=wh.id_grp and sd.id_s_assetdiv=wh.id_sgrp  AND mm.typ_asst !='soft'	" +tempSql1+" "+nmsgrp+" " +tempSql+" ";
						
				}
				System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			
				
			{%>
			<section class="content-header">
		<!-- <div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
					Category Asset Report
						
					</h1>
				</div>
				
			</div>
		</div>
		/.container-fluid
	</section> -->
			<div class="card">
			<table id="hardwarereport"
		class="table table-bordered table-hover hardwarereport">
	<!-- <table class="table" style="border: 1px solid white;" border="1" width="150%"> -->
		
				<!-- <tr >
					<td colspan="22" style="background-color: blue;"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">Master Asset Report</p></td>
				</tr> -->	
			<thead>	<tr>
				    <th><strong><center>S No. &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Asset ID &nbsp;&nbsp;</center></strong></th>
								<th><strong><center>Group Name &nbsp;&nbsp;</center></strong></th>
				<th><strong><center>Sub Group Name &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Item Name &nbsp;&nbsp;</center></strong></th>
					
					<!-- <th><strong><center>Model No. &nbsp;&nbsp;</center></strong></th> -->
					<th><strong><center>Process Type &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Storage Type &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>RAM Type &nbsp;&nbsp;</center></strong></th>
				
				
					<th><strong><center>Quantity &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Serial No. &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Status  &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Location &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Sub Location &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Assign To &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>PO No. &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Invoice No. &nbsp;&nbsp;</center></strong></th>
					<th><strong><center>Invoice Date &nbsp;&nbsp;</center></strong></th>
			<!-- 		<th><strong><center>Asset Value &nbsp;&nbsp;</center></strong></th>
			 -->		<th><strong><center>IT/NON-IT/Software &nbsp;&nbsp;</center></strong></th>
				</tr>	</thead><tbody>
				
			<%} 
				while(rs.next())
				{
					String status = "";
				 if(rs.getString("device_status")!=null){
					status = rs.getString("device_status");
				if(status.equals("allct_to_emp")){
					status="Allocated to employee";
				}
				if(status.equals("under_service")){
					status="Under service";
				}
				if(status.equals("not_workin")){
					status="Not Working";
				}
				if(status.equals("in_store")){
					status="In Store";
				}
				if(status.equals("temporary_laptop")){
					status="Temporary Laptop";
				} 
				if(status.equals("buyback")){
					status="Buy back";
				}  
				if(status.equals("physical_dmg_mjr")){
					status="Physical Damage (Major)";
				} 
				if(status.equals("physical_dmg_mnr")){
					status="Physical Damage (Minor)";
				} 
				if(status.equals("scraped")){
					status="Scrapped / disposed";
				} 
				if(status.equals("advance_laptop")){
					status="Advance Laptop";
				}
				if(status.equals("refreshment_working")){
					status="Refreshment - working";
				} 
				if(status.equals("refreshment_faulty")){
					status="Refreshment - Faulty";
				} 
				 
				 }
				String it="";
				if(rs.getString("typ_asst").equals("IT") && rs.getString("typ_asst")!=null){
					it="IT";
				}
				if(rs.getString("typ_asst").equals("soft") && rs.getString("typ_asst")!=null){
					it="Software";
				}
				if(rs.getString("typ_asst").equals("nonit") && rs.getString("typ_asst")!=null){
					it="NON IT";
				}
				
				String nm_emp="";
				if(rs.getString("nm_emp")!=null && rs.getString("device_status").equals("allct_to_emp")){
					nm_emp=rs.getString("nm_emp");
				}
				else{
					nm_emp=status;
				}
				
				
				%>
				
				
				
				
				
				
				
				<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
						<td><center><%=rs.getString("nm_assetdiv")%><center></td>
				
					<td><center><%=rs.getString("nm_s_assetdiv")%><center></td>
					<td><center><%=rs.getString("nm_model")%><center></td>
					
					<%-- <td><center><%=rs.getString("mdl_num")%></center></td> --%>
					<td><center><%=rs.getString("process_typ")%></center></td>
					<td><center><%=rs.getString("storeage_typ")%></center></td>
					<td><center><%=rs.getString("ram_typ")%></center></td>
				
					
				
					
					<td><center><%=rs.getString("qty_asst")%><center></td>
						<td><center><%=rs.getString("serial_no")%><center></td>
			
						<td><center><%=status%><center></td>
						<td><center><%=rs.getString("nm_loc")%></center></td>
					<td><center><%=rs.getString("nm_subl")%></center></td>
						<td><center><%=nm_emp%><center></td>
					<td><center><%=rs.getString("no_po")%><center></td>
					<td><center><%=rs.getString("no_inv")%><center></td>
					<td><center><%=rs.getString("dt_inv")%><center></td>
					  	<td><center><%=it%><center></td>
				</tr>
				<%}
				}
				catch(Exception e)
				{
					out.println("sql error in Employee Asset View." +e);
				}
			
			%>			
			</tbody>
		</table>
				
			
	</div>	
</div>
	
</body>
<%-- <a href="hardwareStatus.jsp?exportToExcel=YES&label=<%=label%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 500px;">Export To Excel</button>
			</a> --%>	
			<%} %>
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('hardwarereport','hardwarereport_List');

</script>
</html>

