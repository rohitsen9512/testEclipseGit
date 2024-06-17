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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Tickets Report</title>






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
String usertype = (String) session.getAttribute("UserName");
		String std_finance=(String) session.getAttribute("std_finance");
		String end_finance=(String) session.getAttribute("end_finance");
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


String tempSql = "",temp="";String tempQuery="";
String UserType = (String)session.getAttribute("UserType");
String DeptId = (String)session.getAttribute("DeptId");



String FlrId = (String)session.getAttribute("FlrId");
//out.println(FlrId);





/* Integer FlrId = (Integer)session.getAttribute("FlrId");
if(!UserType.equals("SUPER"))
temp = " and im.id_flr= "+FlrId+""; */



if(UserType.equals("SUPER"))
sql= "select t.*,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dtreq,e1.nm_emp from HD_Tickets t,M_Emp_User e1 where t.req_by=e1.id_emp_user and (state ='New' or state ='Active') and t.dt_req between '"+std_finance+"' and '"+end_finance+"' order by dt_req";

sql= "select t.*,(replace(convert(NVARCHAR, dt_req, 103), ' ', '-')) as dtreq,e1.nm_emp from HD_Tickets t,M_Emp_User e1 where t.req_by=e1.id_emp_user and (state ='New' or state ='Active') and t.dt_req between '"+std_finance+"' and '"+end_finance+"' order by dt_req";



System.out.println("report query is------"+sql);



ps=con.prepareStatement(sql);
rs=ps.executeQuery();
System.out.println("hevdhjevdhd"+sql);




%>



<div class="card-body">



<table id="InvoiceDetailReport"
class="table table-bordered table-hover InvoiceDetailReport">
<!-- <table class="table table-bordered" style="border: 1px solid white;" border="1" width="150%" height="100%">





<tr >
<td colspan="19" style="background-color: blue;" width="100%"><p style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">Invoice Detail Report</p></td>
</tr> -->
<thead><tr>
<th><strong><center>S No. &nbsp;&nbsp;</center></strong></th>
<th><strong><center>Ticket No. &nbsp;&nbsp;</center></strong></th>
<th><strong><center>Ticket Date&nbsp;&nbsp;</center></strong></th>
<th><strong><center>Requested By&nbsp;&nbsp;</center></strong></th>
<th><strong><center>State &nbsp;&nbsp;</center></strong></th>
<th><strong><center>Short Description&nbsp;&nbsp;</center></strong></th>
<th><strong><center>Work Note&nbsp;&nbsp;</center></strong></th>
<th><strong><center>Attach File&nbsp;&nbsp;</center></strong></th>
</tr> </thead><tbody>



<%
String file_name1="";
while(rs.next())
{
	
	file_name1 = rs.getString("file_name1");
	if(file_name1 == null)
		file_name1 = "---";

%>
<tr>
<td><center><%=++slNo%></center></td>
<td><center><%=rs.getString("ticket_no")%></center></td>
<td><center><%=rs.getString("dtreq")%></center></td>
<td><center><%=rs.getString("nm_emp")%></center></td>
<td><center><%=rs.getString("state")%></center></td>
<td><center><%=rs.getString("short_description")%></center></td>
<td><center><%=rs.getString("work_note")%></center></td>
<td><center><a href="downloadInvoiceDetails.jsp?fileName1=<%=rs.getString("file_name1")%>" target="_blank"> <%=rs.getString("file_name1")%></a></center></td>
</tr>
<%}
}
catch(Exception e)
{
//out.println("sql error in grn STORE." + e);
}
%>



</tbody></table>





</div>





</body>
<%
if(slNo !=0 && name.equals("UAT") || name.equals("TRAIL"))
{
%>
<!-- <button style="margin-left: 400px;" name="UAT" type="button" class="btn btn-primary" onclick="exportExcel()">Export To Excel</button>
-->
<%
}
else if(slNo !=0 && name.equals("PRODUCT"))
{
%>
<%-- <a href="Invoice_Detail_Report.jsp?exportToExcel=YES&enddate=<%=enddate1%>&startdate=<%=startdate1%>">
<button name="cancel" type="button" class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
</a>
--%>
<%}
else
{%>
<br>
<b><!--  <center>No record(s) is available.</center></b>-->
<%}
}%>



<%
if(name.equals("PRODUCT")){
%>
<!-- <script language="JavaScript">
$('#PreviewGRNApprovePendingReport').removeClass('watermarked');
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
<!-- DataTables Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('InvoiceDetailReport','Invoice_Detail_Report_List');
/* $('.sorting').show();
$('.sorting1').show(); */
</script>
</html>