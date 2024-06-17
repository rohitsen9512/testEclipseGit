

<%@ page language="java"
	import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*, java.text.SimpleDateFormat,com.Common.Common"%>
	<%@ page import="dto.Common.DtoCommon" %>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Properties"%>
<%@page import="dto.Common.UserAccessData"%>
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

<!-- <link href="WebContent/CSS/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"> -->

<title>Master Asset Report</title>
<!-- <link href="../CSS/watermark.css" rel="stylesheet" type="text/css"> -->

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



	<!-- <div id="PreviewAssetForLocAsset" class="watermarked" data-watermark="TRAIL.."> -->


	<%
		int slNo    =   0;
		String id_loc = "",id_div="",id_dept="";
		String id_subl = "";
		String id_flr = "";
		String id_building = "";
		String id_grp = "";
		String id_sgrp = "";
		String allocate = "";
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
				
				 id_div = request.getParameter("id_div");
				 id_loc = request.getParameter("id_loc");
				 id_subl = request.getParameter("id_subl");
				 id_flr = request.getParameter("id_flr");
				 id_building = request.getParameter("id_building"); 
				 id_grp = request.getParameter("id_grp");
				 id_sgrp = request.getParameter("id_sgrp");
				
				
				String tempSql = "";
				
				String tempSql1 ="";
				String tempSql2 ="";
				String tempSql3 ="";
				String tempSql4 ="";
				String tempSql5 ="";
				String tempSql6 ="";
				if(!id_grp.equals("All"))
				{
					if(!id_sgrp.equals(""))
					{
						tempSql1 =" and mm.id_assetdiv="+id_grp+" and mm.id_s_assetdiv="+id_sgrp+"";
					}
					else
					{
						tempSql1 =" and mm.id_assetdiv="+id_grp+"";
					}
				}
				if(!id_loc.equals("all"))
				{
					tempSql2 =" and  wh.id_loc="+id_loc+"";
				}
				else
				{
					tempSql2 ="";
				}
				
				if(id_subl.equals("all") || id_subl.equals("") || id_subl.equals(null))
				{
					tempSql3 ="";
				}
				
				else
				{
					
					tempSql3 =" and  wh.id_subl="+id_subl+"";
				}
				
				if(id_building.equals("all") || id_building.equals("") || id_building.equals(null))
				{
					tempSql4 ="";
				}
				
				else
				{
					
					tempSql4 =" and  wh.id_building="+id_building+"";
				}
				
				if(id_flr.equals("all") || id_flr.equals("") || id_flr.equals(null))
				{
					tempSql5 ="";
				}
				
				else
				{
					
					tempSql5 =" and  wh.id_flr="+id_flr+"";
				}
				
				String UserType = (String)session.getAttribute("UserType");
				DtoCommon dtoCommon = new DtoCommon();
				if(UserType.equals("Super"))
				{
				  sql="select  nm_emp,nm_dept,sd.nm_s_assetdiv,l.nm_loc, sl.nm_subl,mdl_num,process_typ,storeage_typ,ram_typ,id_wh_dyn, ds_pro, mm.mfr, no_mfr,qty_asst,"+
					" dt_allocate,val_asst,wh.to_assign,wh.serial_no,wh.no_po,no_model,nm_model,wh.allocate ,wh.no_inv,(replace(convert(NVARCHAR, wh.dt_inv, 103), ' ', '-')) as dt_inv,(replace(convert(NVARCHAR, wh.dt_po, 103), ' ', '-')) as dt_po,"+
					" mm.typ_asst, nm_model, wh.no_bd,wh.appNo,b.nm_building,f.nm_flr,device_status "+
					" from A_Ware_House wh LEFT JOIN M_Emp_User eu ON wh.to_assign=eu.id_emp_user AND wh.to_assign IS NOT NULL,M_Model mm, M_Loc l,M_Subloc sl,M_Building b,M_Floor f,M_Subasset_Div sd,M_Dept d "+
					" where d.id_dept=wh.id_dept and sd.id_s_assetdiv=mm.id_s_assetdiv and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and mm.id_model= wh.id_model and wh.id_building=b.id_building and wh.id_flr=f.id_flr " +tempSql1+"" +tempSql2+"" +tempSql3+"" +tempSql4+"" +tempSql5+" "+tempSql6+"";
				}
				else
				{
					String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh",request);
					sql="select  nm_emp,nm_dept,sd.nm_s_assetdiv,l.nm_loc, sl.nm_subl,mdl_num,process_typ,storeage_typ,ram_typ,id_wh_dyn, ds_pro, mm.mfr, no_mfr,qty_asst,"+
							" dt_allocate,val_asst,wh.to_assign,wh.serial_no,wh.no_po,no_model,nm_model,wh.allocate ,wh.no_inv,(replace(convert(NVARCHAR, wh.dt_inv, 103), ' ', '-')) as dt_inv,(replace(convert(NVARCHAR, wh.dt_po, 103), ' ', '-')) as dt_po,"+
							" mm.typ_asst, nm_model, wh.no_bd,wh.appNo,b.nm_building,f.nm_flr,device_status "+
							" from A_Ware_House wh LEFT JOIN M_Emp_User eu ON wh.to_assign=eu.id_emp_user AND wh.to_assign IS NOT NULL,M_Model mm, M_Loc l,M_Subloc sl,M_Building b,M_Floor f,M_Subasset_Div sd,M_Dept d "+
							" where d.id_dept=wh.id_dept and sd.id_s_assetdiv=mm.id_s_assetdiv and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and mm.id_model= wh.id_model and wh.id_building=b.id_building and wh.id_flr=f.id_flr " +tempSql1+"" +tempSql2+"" +tempSql3+"" +tempSql4+"" +tempSql5+" "+tempQuery2+"";
	
				}
				
				
				//out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			
				
			{%>
			
			
	
			
			<div class="card"> 

		<div id="locationAssetReportdiv">
			 <div class="card-body"> 
			
	<table id="locationAssetReport"
		class="table table-bordered table-hover locationAssetReport">
		<!-- <table class="table" style="border: 1px solid white;" border="1" width="150%"> -->

		<!-- <tr>
			<td colspan="22" style="background-color: blue;"><p
					style="background-color: blue; font-size: 24px; color: white; margin-left: 500px;">Master
					Asset Report</p></td>
		</tr> -->
		<thead><tr>
			 <th><strong><center>S No.</center></strong></th> 
			<th><strong><center>Asset ID </center></strong></th>
						
			 <th><strong><center>Sub Category Name</center></strong></th>
			<th><strong><center>Item Name </center></strong></th>
			<th><strong><center>Assign To</center></strong></th>
			<th><strong><center>Dept</center></strong></th>
			<th><strong><center>Serial No. </center></strong></th>
			<th><strong><center>Status</center></strong></th>
			<th><strong><center>PO No. </center></strong></th>
			<th><strong><center>PO Date </center></strong></th>
			<th><strong><center>Invoice No. </center></strong></th>
			<th><strong><center>Invoice Date</center></strong></th>
			<th><strong><center>Location</center></strong></th>
			<th><strong><center>Sub Location</center></strong></th>
			<th><strong><center>Building</center></strong></th>
			<th><strong><center>Floor</center></strong></th>
						
						
			<th><strong><center>IT/NON-IT/Software</center></strong></th> 
			<!-- <th><strong><center>Mode No.</center></strong></th> -->
			<th><strong><center>Process</center></strong></th>
			<th><strong><center>Storage Type </center></strong></th>
			<th><strong><center>RAM Type </center></strong></th>
		 </tr>
		 </thead>
		 <tbody>

		<%} 
				while(rs.next())
				{
					String device_status="";
					if(rs.getString("device_status").equals("allct_to_emp")){
						device_status="Allocated to employee Permamnent";
					}
					if(rs.getString("device_status").equals("allct_to_emp_temp")){
						device_status="Allocated to employee Temporary";
					}
					if(rs.getString("device_status").equals("link_to_asset")){
						device_status="Linked To Asset";
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
					if(rs.getString("device_status").equals("advance_laptop")){
						device_status="Advance Laptop ";
					} 
					if(rs.getString("device_status").equals("refreshment_working")){
						device_status="Refreshment-Working ";
					} 
					if(rs.getString("device_status").equals("refreshment_faulty")){
						device_status="Refresh-Faulty";
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
				if(rs.getString("typ_asst").equals("accessories") && rs.getString("typ_asst")!=null){
					it="ACCESSORIES";
				}
				String to_assign=rs.getString("nm_emp");
				 if(to_assign ==null || to_assign.equals("0"))
					 if(rs.getString("typ_asst").equals("IT")){
						 if(rs.getString("device_status").equals("physical_dmg_mnr")){
							 to_assign="Physical Damage Minor";
						
						 }
						 else if(rs.getString("device_status").equals("physical_dmg_mjr")){
							 to_assign="Physical Damage Major";
						
						 }
						 else
							 to_assign="Store";
						 
					 }
					 else{
						 to_assign="---";
					 }
				%>
		<tr>
			<td><center><%=++slNo%></center></td> 
			<td><center><%=rs.getString("id_wh_dyn")%></center></td>
			<td><center><%=rs.getString("nm_s_assetdiv")%><center></td> 
			<td><center><%=rs.getString("nm_model")%><center></td>
			<td><center><%=to_assign%><center></td>
			<%-- <td><center><%=rs.getString("no_bd")%><center></td> --%>
			<%-- <td><center><%=rs.getString("val_asst")%><center></td>  --%>
				<td><center><%=rs.getString("nm_dept")%><center></td>
			<td><center><%=rs.getString("serial_no")%><center></td>

			<td><center><%=device_status%><center></td>
			<td><center><%=rs.getString("no_po")%><center></td>
			<td><center><%=rs.getString("dt_po")%><center></td>
			<td><center><%=rs.getString("no_inv")%><center></td>
			<td><center><%=rs.getString("dt_inv")%><center></td>
			<td><center><%=rs.getString("nm_loc")%><center></td>
			<td><center><%=rs.getString("nm_subl")%><center></td>
			<td><center><%=rs.getString("nm_building")%><center></td>
			<td><center><%=rs.getString("nm_flr")%><center></td>
			
			
			<td><center><%=it%><center></td>
			<%-- <td><center><%=rs.getString("mdl_num")%></center></td> --%>
			<td><center><%=rs.getString("process_typ")%></center></td>
			<td><center><%=rs.getString("storeage_typ")%></center></td>
			<td><center><%=rs.getString("ram_typ")%></center></td>
		
		 </tr>
		<%}
				
			}
			catch(Exception e)
			{
				out.println("sql error in grn STORE." + e);
			}
		%>
</tbody>
	</table>
	</div>
	 </div>
	</div> 
	

	<!-- </div> -->

<%
if(slNo !=0 && name.equals("UAT") || name.equals("TRAIL"))
{
%>
<!-- <button style="margin-left: 400px;" name="UAT" type="button"
	class="btn btn-primary" onclick="exportExcel()">Export To
	Excel</button> -->
<%
}
else if(slNo !=0 && name.equals("PRODUCT"))
{
%>
<%-- <a
	href="Location_Asset_report1.jsp?exportToExcel=YES&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>&id_loc=<%=id_loc%>&id_subl=<%=id_subl%>">
	<button name="cancel" type="button" class="btn btn-primary"
		style="margin-left: 400px;">Export To Excel</button>
</a> --%>

<%}
else
{%>
<br>
 <b><center><!-- No record(s) is available. --></center></b>
<%}
	}%>

<%
if(name.equals("PRODUCT")){
%>
<!-- <script language="JavaScript">
		$('#PreviewAssetForLocAsset').removeClass('watermarked');
		</script> -->
<%
	}else{
		%>
<!-- <script>        
		Array.from(document.querySelectorAll('.watermarked')).forEach(function(el) {
		        el.dataset.watermark = (el.dataset.watermark + ' ').repeat(10000);
		    });
		</script> -->
<%
	}
%>
<!-- DataTables  Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('locationAssetReport','Master_Report_List');

</script>

</body>
</html>