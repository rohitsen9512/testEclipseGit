

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

<title>Total Product In Store Report</title>
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
		String  id_subl = "",id_loc="",tempSql1="";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
		
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Toatal_Instore_Product_report.xls");
		 
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
				
				
				 id_loc = request.getParameter("id_loc");
				 id_subl = request.getParameter("id_sloc");
				
				
				
				String tempSql = "";
				if(!id_loc.equals("all"))
				{
					tempSql1=" and  wh.id_loc="+id_loc+"";
				}
				else
				{
					tempSql1 ="";
				} 
				
				if(id_subl.equals("all") || id_subl.equals("") || id_subl.equals(null))
				{
					tempSql ="";
				}
				
				else
				{
					
					tempSql =" and  wh.id_subl="+id_subl+"";
				}
				
				
			
				
				String UserType = (String)session.getAttribute("UserType");
				DtoCommon dtoCommon = new DtoCommon();
				if(UserType.equals("Super"))
				{
				  //sql="select nm_product , tot_aval_qty ,ms.nm_subl,l.nm_loc from S_Store_Qty st ,M_Subloc ms, M_Loc l where  st.id_sloc= ms.id_sloc and st.id_loc= l.id_loc" +tempSql+" " +tempSql1+"" ;
				 
				  sql="select ma.nm_assetdiv , wh.filling_status ,wh.serial_no,ms.nm_subl,l.nm_loc from  M_Subloc ms, M_Loc l ,S_I_Ware_House wh,M_Asset_Div ma where  wh.id_loc = l.id_loc and wh.id_subl= ms.id_sloc and  ma.id_assetdiv=wh.id_product  and  device_status='in_store'  " +tempSql+" " +tempSql1+"" ;
				  System.out.println(sql);
				}
				else
				{
					//doubt
					//String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"led_m","led_m",request);
					
				 sql="select ma.nm_assetdiv , wh.filling_status ,wh.serial_no, ms.nm_subl,l.nm_loc  from M_Subloc ms,M_Loc l , S_I_Ware_House wh ,M_Asset_Div ma where  wh.id_subl= ms.id_sloc and wh.id_loc= l.id_loc and  ma.id_assetdiv=wh.id_product  and  device_status='in_store'   " +tempSql+" " +tempSql1+"" ;
	                  System.out.println(sql);
				}
				
				
				//out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			
				
			{%>
			
			
	
			
			<div class="card"> 

		<div id="RentalProdHistoryReportdiv">
			 <div class="card-body"> 
			
	<table id="RentalProdHistoryReport"
		class="table table-bordered table-hover RentalProdHistoryReport">
		
		<thead><tr>
			 <th><strong><center>SL No.</center></strong></th> 
			
						
			 <th><strong><center>Product Name</center></strong></th>
			
			<th><strong><center>Entity</center></strong></th>
			<th><strong><center>City</center></strong></th>
			<th><strong><center>Serial No</center></strong></th>
			<th><strong><center>Fill/Empty</center></strong></th>
			 
		 </tr>
		 </thead>
		 <tbody>
   
		<%} 
				while(rs.next())
				{
					String status="";
					if(rs.getString("filling_status").equals("1")){
					 status="Filled";
						
					}
					 else if(rs.getString("filling_status").equals("0")){
						 status="Empty";
					}else{
						 status="-";
					} 
	%>		        
 		<tr>
			<td><center><%=++slNo%></center></td> 
			
			<td><center><%=rs.getString("nm_assetdiv")%><center></td> 
			<td><center><%=rs.getString("nm_loc")%><center></td>
			<td><center><%=rs.getString("nm_subl")%><center></td>
			<td><center><%=rs.getString("serial_no")%><center></td>
			<td><center><%=status%><center></td>
			
			
			
		
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

%>
<!-- DataTables  Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('RentalProdHistoryReport','Master_Report_List');

</script>

</body>
</html>