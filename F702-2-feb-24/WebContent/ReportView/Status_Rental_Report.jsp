<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common,dto.Common.DtoCommon" %>
<%@page import="java.io.InputStream" %>
<%@page import="java.util.Properties" %>
<%@page import="dto.Common.UserAccessData" %>
<%@ page import="dto.Common.DtoCommon" %>
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
<title>Status Rental On Product Report</title>
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
		 String std_finance=(String) session.getAttribute("std_finance");
		
			String end_finance=(String) session.getAttribute("end_finance");
		
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
<div id="PreviewEmployeeAssetReport" class="watermarked" data-watermark="TRAIL..">
	
		<%
		int slNo    =   0;
		
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Status Rental On Product Report.xls");
		 
		        }
		    
		
		String Product_status1 ="";
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				Product_status1 = request.getParameter("Product_status");
				String tempSql="";
				
				String UserType = (String)session.getAttribute("UserType");
				//String id_dept = (String)session.getAttribute("DeptId");
				String tempSql1="";
				String likeTempQuery="";
				DtoCommon dtoCommon = new DtoCommon();
				
					 if(UserType.equals("Super")){
						 
							
							
							sql="select distinct led.* ,led_m.*,ms.nm_subl ,(replace(convert(VARCHAR, led_m.dt_ld, 106), ' ', '-')) as dt_install, (replace(convert(VARCHAR, led.current_extend_exp_dt, 106), ' ', '-')) as dt_expinstall from O_Assign_Lead_Master led_m,O_Assign_Lead led , M_Subloc ms, O_Deliver_Detail_Lead del where  led.state ='Inprogress' and led.tagto_me_state ='Inprogress' and  led_m.id_sloc=ms.id_sloc and led_m.id_lead_m=led.id_lead_m  and led.typ_service='Rental' and led_m.dt_ld between '"+std_finance+"' and '"+end_finance+"' ";  
							  
							
						
					 }
					 else
					 {
						 //String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh",request);
						 sql="select distinct led.* ,led_m.*,ms.nm_subl ,(replace(convert(VARCHAR, led_m.dt_ld, 106), ' ', '-')) as dt_install, (replace(convert(VARCHAR, led.current_extend_exp_dt, 106), ' ', '-')) as dt_expinstall from O_Assign_Lead_Master led_m,O_Assign_Lead led , M_Subloc ms, O_Deliver_Detail_Lead del where  led.state ='Inprogress' and led.tagto_me_state ='Inprogress' and led_m.id_sloc=ms.id_sloc and led_m.id_lead_m=led.id_lead_m and led.typ_service='Rental'  and led_m.dt_ld between '"+std_finance+"' and '"+end_finance+"' " ;
					 }
				
				
		/* 		else {
					
					
					 if(UserType.equals("Super")){
						 
							
							
							sql="select distinct led.* ,led_m.*,l.nm_loc,ms.nm_subl ,(replace(convert(VARCHAR, del.dt_install, 106), ' ', '-')) as dt_install, (replace(convert(VARCHAR, del.dt_install, 106), ' ', '-')) as dt_expinstall from O_Assign_Lead_Master led_m,O_Assign_Lead led , M_Subloc ms, M_Loc l,O_Deliver_Detail_Lead del where  led_m.state ='Closed' and led_m.tagto_me_state ='Closed' and led_m.id_loc=l.id_loc and led_m.id_sloc=ms.id_sloc and led_m.id_lead_m=led.id_lead_m  " +tempSql+"" ;
							  
							
						
					 }
					 else
					 {
						 //String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh",request);
						 sql="select distinct led.* ,led_m.*,l.nm_loc,ms.nm_subl ,(replace(convert(VARCHAR, del.dt_install, 106), ' ', '-')) as dt_install, (replace(convert(VARCHAR, del.dt_install, 106), ' ', '-')) as dt_expinstall from O_Assign_Lead_Master led_m,O_Assign_Lead led , M_Subloc ms, M_Loc l,O_Deliver_Detail_Lead del where  led_m.state ='Closed' and led_m.tagto_me_state ='Closed' and led_m.id_loc=l.id_loc and led_m.id_sloc=ms.id_sloc and led_m.id_lead_m=led.id_lead_m  " +tempSql+"" ;
					 }
				} */
				
			
				
				System.out.print(sql);
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			{%>
			
			<div class="card-body"> 
			
	<table id="StatutusRenatalProdReport"
		class="table table-bordered table-hover StatutusRenatalProdReport">
		<!-- <table style="border: 1px solid white;" border="1" width="100%" height="100%">
		
				<tr >
					<td colspan="9" class="headerTR"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 500px;"> Status Rental Report</p></td>
				</tr>	 -->
				<thead><tr>
				   <th><strong><center>SL No.</center></strong></th> 
			<th><strong><center>Lead No. </center></strong></th>
						
			 <th><strong><center>Product Name</center></strong></th>
			 	 <th><strong><center>Serial No</center></strong></th>
			<th><strong><center>Patient Name</center></strong></th>
			<th><strong><center>Adhaar card	</center></strong></th>
			
			<th><strong><center>City</center></strong></th>
			<th><strong><center>Service Type</center></strong></th>
			 <th><strong><center>Installation Date</center></strong></th>
			<th><strong><center>Installation Expire Date</center></strong></th> 
			<!-- <th><strong><center>Quantity</center></strong></th>  -->
					
				</tr>	</thead><tbody>
				
			<%} 
				while(rs.next())
				{
					
					String Product_status="",mdl_num="",process_typ="" ,storeage_typ="",ram_typ="";
					
					if(rs.getString("typ_service").equals("Rental")){
						Product_status="Product On Rental Period";
					}
					if(rs.getString("typ_service").equals("Sale")){
						Product_status="Sale";
					}
				
					/* if(rs.getString("ram_typ")==null){
						ram_typ="NA";
				
					} */
					//else
						//ram_typ=rs.getString("ram_typ");
					
				%>
					<tr>
				<td><center><%=++slNo%></center></td> 
			<td><center><%=rs.getString("lead_no")%></center></td>
			<td><center><%=rs.getString("nm_prod")%><center></td> 
			<td><center><%=rs.getString("sr_no")%><center></td> 
			<td><center><%=rs.getString("nm_pstn")%><center></td>
			
			<td><center><%=rs.getString("adhar_no")%><center></td>
			
				<td><center><%=rs.getString("nm_subl")%><center></td>
			<td><center><%=rs.getString("typ_service")%><center></td>
			<td><center><%=rs.getString("dt_install")%><center></td>
			<td><center><%=rs.getString("dt_expinstall")%><center></td>
			<%-- <td><center><%=rs.getString("quantity")%><center></td> --%>

				
					
										
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
<%-- <a href="Status_Asset_Report.jsp?exportToExcel=YES&Product_status=<%=Product_status1%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a> --%>	

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
getButtonsForListView('StatutusRenatalProdReport','ASSET_STATUS_REPORT_List');
/* $('.sorting').show();
$('.sorting1').show(); */
</script>

</html>