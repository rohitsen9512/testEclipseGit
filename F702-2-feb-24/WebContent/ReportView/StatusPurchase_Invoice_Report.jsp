<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.text.DecimalFormat, java.text.SimpleDateFormat,java.text.DateFormat,java.util.*,com.Common.Common" %>
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
<jsp:directive.page import="com.Asset.Print_Invoice"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Purchase Invoice Detail Report</title>



<!-- <link href="WebContent/CSS/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"> -->
<!-- <link href="../CSS/watermark.css" rel="stylesheet" type="text/css">
<script type ="text/javascript" src="../common.js"></script>
<script type ="text/javascript" src="js/jquery1.10.js"></script>
<script type="text/javascript">



</script> -->



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

 <script type ="text/javascript" src="js/jquery1.10.js"></script>
<script type ="text/javascript" src="../All_Js_File/Asset/Print_Invoice.js"></script>

<!-- jQuery -->
<script src="../plugins/jquery/jquery.min.js"></script>
<script type ="text/javascript" src="../js/jquery1.10.js"></script>
<script type ="text/javascript" src="../js/jquery-ui.js"></script>



<!-- Bootstrap 4 -->
<script src="../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>



<!-- AdminLTE for demo purposes -->
<script src="../dist/js/demo.js"></script>
<!-- DataTables & Plugins -->
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
String std_finance=(String) session.getAttribute("std_finance");

String end_finance=(String) session.getAttribute("end_finance");
String usertype = (String) session.getAttribute("UserName");
if(usertype == null) {
%>
<script language="JavaScript">
alert("Session Expired. Please Login");
parent.location.href="../index.html";
</script>
<%
}else{
%>
<body>
<div id="PreviewGRNApprovePendingReport" class="watermarked" data-watermark="TRAIL..">

<%
DecimalFormat df = new DecimalFormat("#.00");
int slNo = 0;
String sql="";
String startdate = "",startdate1 = "";
String enddate = "",enddate1 = "";
SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
String Group_id="",fin_id="",curr="";
String exportToExcel = request.getParameter("exportToExcel");

if (exportToExcel != null
&& exportToExcel.toString().equalsIgnoreCase("YES")) {
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "inline; filename="
+ "Invoice_Details_Report.xls");

}
try
{
ResultSet rs=null;
ResultSet rs1=null;
PreparedStatement ps=null;
Connection con=null;
Statement stmt=null;
con=Common.GetConnection(request);

DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");
DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");


//String fin_years	=	request.getParameter("fin_years");

String tempSql = "",temp="";String tempQuery="";
String UserType = (String)session.getAttribute("UserType");
//String DeptId = (String)session.getAttribute("DeptId");

//String FlrId = (String)session.getAttribute("FlrId");


if(UserType.equals("SUPER"))
{
	//this query show one inv many time becuse product name are diffrent for one inv
	/* sql="select si.product,ms.nm_subl,sm.no_inv,(replace(convert(VARCHAR, sm.dt_inv, 106), ' ', '-')) as dt_inv from  S_Inventory_Master sm,S_Inventory si, M_loc ml,M_Subloc ms where sm.id_inventory_m=si.id_inventory_m and sm.id_loc= ml.id_loc and  sm.id_subl=ms.id_sloc "; 
		  System.out.println(sql); */
	sql="select distinct ms.nm_subl,l.nm_loc,sm.no_inv,sm.temp_no_inv,(replace(convert(VARCHAR, sm.dt_inv, 106), ' ', '-')) as dt_inv ,(replace(convert(VARCHAR, sm.dt_temp_inv, 106), ' ', '-')) as dttempinv from  S_Inventory_Master sm,S_Inventory si,M_Subloc ms,M_Loc l where  sm.id_subl=ms.id_sloc and  sm.id_loc=l.id_loc and sm.dt_inv between '"+std_finance+"' and '"+end_finance+"' " ;
	 System.out.println(sql);
}
else{
	sql="select distinct ms.nm_subl,l.nm_loc,sm.no_inv,sm.temp_no_inv,(replace(convert(VARCHAR, sm.dt_inv, 106), ' ', '-')) as dt_inv,(replace(convert(VARCHAR, sm.dt_temp_inv, 106), ' ', '-')) as dttempinv from  S_Inventory_Master sm,S_Inventory si, M_Subloc ms,M_Loc l where  sm.id_subl=ms.id_sloc and  sm.id_loc=l.id_loc and sm.dt_inv between '"+std_finance+"' and '"+end_finance+"' " ;
	 System.out.println(sql);
}

//out.print(sql);

ps=con.prepareStatement(sql);
rs=ps.executeQuery();



%>

<div class="card-body">

<table id="InvoiceDetailReport"
class="table table-bordered table-hover InvoiceDetailReport">
<!-- <table class="table table-bordered" style="border: 1px solid white;" border="1" width="150%" height="100%">



<tr >
<td colspan="19" style="background-color: blue;" width="100%"><p style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">Invoice Detail Report</p></td>
</tr> -->
<thead><tr>
  <th><strong><center>SL No.</center></strong></th> 
			<!-- <th><strong><center>Product Name</center></strong></th> -->
			<th><strong><center>Invoice No. </center></strong></th>
			<th><strong><center>Invoice Date. </center></strong></th>
			<th><strong><center>Temporary Invoice No. </center></strong></th>
			<th><strong><center>Temporary Invoice Date. </center></strong></th>
			<th><strong><center>Entity</center></strong></th>
			<th><strong><center>City</center></strong></th>
</tr></thead><tbody>

<%
while(rs.next())
{
	
	
%>		
<tr>
<td><center><%=++slNo%></center></td> 
<%-- <td><center><%=rs.getString("product")%></center></td> --%>
<td><center><%=rs.getString("no_inv")%></center></td>
<td><center><%=rs.getString("dt_inv")%><center></td>
<td><center><%=rs.getString("temp_no_inv")%></center></td>
<td><center><%=rs.getString("dttempinv")%><center></td>
<td><center><%=rs.getString("nm_loc")%><center></td>

<td><center><%=rs.getString("nm_subl")%><center></td>



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
getButtonsForListView('InvoiceDetailReport','Master_Report_List');

</script>

</body>
</html>