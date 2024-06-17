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
<title>Status Asset Report</title>
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
		                    + "Status_Asset_Report.xls");
		 
		        }
		    
		
		String device_status1 ="";
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				device_status1 = request.getParameter("parent");
				System.out.println(device_status1);
				int p=Integer.parseInt(device_status1);
				String tempSql="";
				if(device_status1!=null){
				
				if(p==1){
					
					tempSql ="wh.typ_asst = 'accessories'and wh.id_model=m.id_model  and  parent! = 0 ";
				}
				else
				{ if(p==0){
					tempSql ="wh.typ_asst = 'accessories'and wh.id_model=m.id_model  and  parent ='"+device_status1+"' ";
				}
				else{
					tempSql ="wh.typ_asst = 'accessories'and wh.id_model=m.id_model";
				}
				}
				}
				
				
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				int UserId = (Integer)session.getAttribute("UserId");
				String tempSql1="";
				String likeTempQuery="";
				DtoCommon dtoCommon = new DtoCommon();
				/* if(UserType.equals("SUPER")){ */
						sql="select wh.* from A_Ware_House wh, M_Model m where "+tempSql+" ";
				
		
			
								
				System.out.print(sql);
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			{%>
			
			<div class="card-body"> 
			
	<table id="StatusAssetReport"
		class="table table-bordered table-hover StatusAssetReport">
		<!-- <table style="border: 1px solid white;" border="1" width="100%" height="100%">
		
				<tr >
					<td colspan="9" class="headerTR"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 500px;"> Asset Status Report</p></td>
				</tr>	 -->
				<thead><tr>
				    <th><strong><center>S No.</center></strong></th>
					<th><strong><center>Ware House ID</center></strong></th>
					<th><strong><center>Accessories Name</center></strong></th>
					<th><strong><center> Serial No. </center></strong></th>
					
						<!-- <th><strong><center>Model No.</center></strong></th> -->
					<th><strong><center>Process Type</center></strong></th>
					<th><strong><center>Storage Type</center></strong></th>
					<th><strong><center>RAM Type</center></strong></th>
				
				 <th><strong><center>Device Status</center></strong></th>
					
				</tr>	</thead><tbody>
				
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
				
				%>
					<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString(1)%></center></td>
					<td><center><%=rs.getString(2)%></center></td>
					<td><center><%=rs.getString("serial_no")%></center></td>
					
						
					<%-- <td><center><%=rs.getString("mdl_num")%></center></td> --%>
					<td><center><%=rs.getString("process_typ")%></center></td>
					<td><center><%=rs.getString("storeage_typ")%></center></td>
					<td><center><%=rs.getString("ram_typ")%></center></td>
				
				<td><center><%=device_status%></center></td>
				
					
										
				</tr>
				<%}
			}
			catch(Exception e)
			{
				//out.println("sql error in grn STORE." + e);
			}
		%>
		
</tbody>	</table>

</div>

</body>
<%
if(slNo !=0 && name.equals("UAT") || name.equals("TRAIL"))
{
%>
<!-- <button style="margin-left: 400px;" name="UAT" type="button"  class="btn btn-primary" onclick="exportExcel()">Export To Excel</button>
 -->
 <%
}
else if(slNo !=0 && name.equals("PRODUCT"))
{
%>
<%-- <a href="Status_Asset_Report.jsp?exportToExcel=YES&device_status=<%=device_status1%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a> --%>	

<%}
else
{%>
	<br>
		<b><center>No record(s) is available.</center></b>
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
getButtonsForListView('StatusAssetReport','Asset_Status_Report_List');
/* $('.sorting').show();
$('.sorting1').show(); */
</script>

</html>