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
<title>Invoice Detail Report</title>



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
startdate = request.getParameter("startdate");
enddate = request.getParameter("enddate");
startdate1 = startdate;
enddate1 = enddate;
if(!startdate.equals(""))
{
startdate = dateFormatNeeded.format(userDateFormat.parse(startdate));
}
if(!enddate.equals(""))
{
enddate = dateFormatNeeded.format(userDateFormat.parse(enddate));
}

String fin_years	=	request.getParameter("fin_years");

String tempSql = "",temp="";String tempQuery="";
String UserType = (String)session.getAttribute("UserType");
String DeptId = (String)session.getAttribute("DeptId");

String FlrId = (String)session.getAttribute("FlrId");


if(UserType.equals("SUPER"))
sql= "select im.no_inv,im.id_inv_m,im.id_ven, (replace(convert(NVARCHAR, im.dt_inv, 103), ' ', '-')) as dt_inv,im.no_po,(replace(convert(NVARCHAR, im.dt_po, 103), ' ', '-')) as dt_po,(replace(convert(NVARCHAR, im.dt_grn, 103), ' ', '-')) as dtgrn,(replace(convert(NVARCHAR, im.dt_dc, 103), ' ', '-')) as dtdc,i.ds_pro,i.mfr,modl.nm_model,vn.nm_ven,l.nm_loc,"+
" sl.nm_subl,bul.nm_building,i.qty,i.un_prc,i.tt,i.othr,i.duty,upload_inv,nm_div from A_Invoice_Master im ,M_Company_Division cd, A_Invoice i, "+
" M_Vendor vn, M_Loc l,M_Subloc sl , M_Model modl,M_Building bul where im.id_inv_m=i.id_inv_m and vn.id_ven=im.id_ven and cd.id_div=im.id_div and "+
" l.id_loc=im.id_loc and sl.id_sloc=im.id_subl and i.id_model=modl.id_model and bul.id_building=im.id_building and im.dt_inv >='"+startdate+"' and im.dt_inv <= '"+enddate+"' order by dt_inv";

sql="select im.no_inv,im.id_inv_m,im.id_ven, (replace(convert(NVARCHAR, im.dt_inv, 103), ' ', '-')) as dt_inv,(replace(convert(NVARCHAR, im.dt_grn, 103), ' ', '-')) as dtgrn,(replace(convert(NVARCHAR, im.dt_dc, 103), ' ', '-')) as dtdc "+
" ,im.no_po,(replace(convert(NVARCHAR, im.dt_po, 103), ' ', '-')) as dt_po,im.no_grn,im.no_dc,modl.mfr,modl.nm_model,vn.nm_ven, "+
" i.qty,i.un_prc,gr_tot as tt,i.othr,i.duty,invoice_file,l.nm_loc,s.nm_subl,b.nm_building,f.nm_flr from A_Invoice_Master im , "+
" A_Invoice i, M_Vendor vn, M_Model modl,M_Loc l,M_Subloc s,M_building b,M_floor f "+
" where im.id_inv_m=i.id_inv_m and vn.id_ven=im.id_ven and i.id_model=modl.id_model and im.id_loc=l.id_loc and im.id_subl=s.id_sloc and im.id_building=b.id_building and im.id_flr=f.id_flr "+
" and im.dt_inv >='"+startdate+"' and im.dt_inv <= '"+enddate+"' order by dt_inv";

//out.print(sql);

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
<th><strong><center>Invoice No. &nbsp;&nbsp;</center></strong></th>
<th><strong><center>Invoice Date&nbsp;&nbsp;</center></strong></th>
<th><strong><center>PO No. &nbsp;&nbsp;</center></strong></th>
<th><strong><center>PO Date &nbsp;&nbsp;</center></strong></th>
<th><strong><center>GRN No.&nbsp;&nbsp;</center></strong></th>
<th><strong><center>GRN Date &nbsp;&nbsp;</center></strong></th>
<th><strong><center>DC No.&nbsp;&nbsp;</center></strong></th>
<th><strong><center>DC Date &nbsp;&nbsp;</center></strong></th>
<th><strong><center>Location&nbsp;&nbsp;</center></strong></th>
<th><strong><center>Sub Location&nbsp;&nbsp;</center></strong></th>
<th><strong><center>Building&nbsp;&nbsp;</center></strong></th>
<th><strong><center>Floor&nbsp;&nbsp;</center></strong></th>
<th><strong><center>Asset Name&nbsp;&nbsp;</center></strong></th>

<th><strong><center>Manufacture&nbsp;&nbsp;</center></strong></th>
<!-- <th><strong><center>Model No.&nbsp;&nbsp;</center></strong></th> -->
<th><strong><center>Vendor Name&nbsp;&nbsp;</center></strong></th>
<th><strong><center>Quantity&nbsp;&nbsp;</center></strong></th>
<th><strong><center>Unit Price&nbsp;&nbsp;</center></strong></th>


<th><strong><center>Total Cost&nbsp;&nbsp;</center></strong></th>

<th><strong><center>Download File&nbsp;&nbsp;</center></strong></th>
</tr> </thead><tbody>

<%
String dtdc="",dtgrn="",no_grn="";
while(rs.next())
{
	dtdc = rs.getString("dtdc");
	if(dtdc.equals("01/01/1900"))
		dtdc = "---";
	dtgrn = rs.getString("dtgrn");
	if(dtgrn.equals("01/01/1900"))
		dtgrn = "---";
	no_grn = rs.getString("no_grn");
	if(no_grn.equals("01/01/1900"))
		dtgrn = "---";
%>
<tr>
<td><center><%=++slNo%></center></td>
<td><center><a href="#" onclick="ControlExportInvoice3('PreviewGRNApprovePendingReport','createPrintorMailPurchaseOrder',<%=rs.getString("id_inv_m")%>,<%=rs.getString("id_ven")%>)"><%=rs.getString("no_inv")%></a></center></td>
<td><center><%=rs.getString("dt_inv")%></center></td>
<td><center><%=rs.getString("no_po")%></center></td>
<td><center><%=rs.getString("dt_po")%></center></td>
<td><center><%=rs.getString("no_grn")%></center></td>
<td><center><%=dtgrn%></center></td>
<td><center><%=rs.getString("no_dc")%></center></td>
<td><center><%=dtdc%></center></td>
<td><center><%=rs.getString("nm_loc")%></center></td>
<td><center><%=rs.getString("nm_subl")%></center></td>
<td><center><%=rs.getString("nm_building")%></center></td>
<td><center><%=rs.getString("nm_flr")%></center></td>
<td><center><%=rs.getString("nm_model")%></center></td>
<td><center><%=rs.getString("mfr")%></center></td>
<td><center><%=rs.getString("nm_model")%></center></td>
<td><center><%=rs.getString("nm_ven")%></center></td>
<td><center><%=rs.getString("qty")%></center></td>
<td><center><%=rs.getString("un_prc")%></center></td>
<td><center><%=rs.getString("tt")%></center></td>
<td><center><a href="downloadInvoiceDetails.jsp?fileName1=<%=rs.getString("invoice_file")%>" target="_blank"> <%=rs.getString("invoice_file")%></a></center></td>
</tr>
<%}
}
catch(Exception e)
{
out.println("sql error in grn STORE." + e);
}
%>

</tbody></table>



</div>
<div  id="PrewiewPO"></div>
<div class="commonDiv" id="createPrintorMailPurchaseOrder">
<div class="commonDiv DisplayAmendPurchaseOrder" id="tab_customers">
<table class="table table-bordered DisplayAmendPurchaseOrder"  id="tab_customers" align="center" style="width:92%;">
</table>
</div>
</div>
<div id="buttonForPoPrint">

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
<!-- <b><center>No record(s) is available.</center></b> -->s
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