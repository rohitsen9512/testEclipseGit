<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,java.util.Date, java.text.SimpleDateFormat,com.Common.Common,java.text.DateFormat" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lease Report</title>
<script type ="text/javascript" src="js/jquery1.10.js"></script>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
</style>
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
   if(usertype == null)	{
%>
		<script language="JavaScript">
			alert("Session Expired. Please Login");
			parent.location.href="../index.html";
		</script>
<%
	}else{
%>
<body >
<div id="PreviewInstalledAssetReport">
	
		<%
		
		String sql="";
		
		/* String date1 = "",date=""; */
		String startdate = "",startdate1 = "";
		String enddate = "",enddate1 = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		String exportToExcel = request.getParameter("exportToExcel");
        
        if (exportToExcel != null
                && exportToExcel.toString().equalsIgnoreCase("YES")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename="
                    + "Lease_Report.xls");
 
        }
			
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				  con=Common.GetConnection(request);
				 
				/*  date1 = request.getParameter("dateMonth"); */
				   
				/*  Date d = userDateFormat.parse(date1);
				 date = dateFormatNeeded.format(d); */
				/* SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm");	
				Date date = formatter.parse(dateMonth); */
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
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				/* if(UserType.equals("SUPER")) */
				sql="select id_wh_dyn,ds_pro,serial_no as no_mfr,nm_cc,ds_asst,nm_ven,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) dt_po,(replace(convert(VARCHAR, dt_inv, 106), ' ', '-')) dt_inv,warr_amc,"+
						"(replace(convert(NVARCHAR, std_lease, 106), ' ', '-')) as std_lease,"+
						"(replace(convert(NVARCHAR, endt_lease, 106), ' ', '-')) as endt_lease,nm_computer"+
						" FROM A_Ware_House wh"+
						" left join M_Company_Costcenter cc on wh.id_cc=cc.id_cc "+
						" left join M_Vendor ven on wh.id_ven_proc=ven.id_ven"+
					" where  endt_lease  >='"+startdate+"' and endt_lease <= '"+enddate+"' order by std_lease";
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			%>
			<div class="card"> 
			<table id="LeaseReport"
		class="table table-bordered table-hover LeaseReport">
		<!-- <table border="1" width="200%" height="100%"> -->
		
				<!-- <tr >
					<td colspan="16" class="headerTR"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 500px;">Lease Report</p></td>
				</tr> -->	
				<thead><tr>
				    <th><strong><center>S No. </strong></center></th>
					<th><strong><center>Asset ID </strong></center></th>
					<th><strong><center>Asset Name </strong></center></th>
					<th><strong><center>Serial No. </strong></center></th>
					<th><strong><center>Asset Description </strong></center></th>
					
					<th><strong><center>Vendor Name </strong></center></th>
					
					<th><strong><center>Purchase Date </strong></center></th>
					<th><strong><center>Invoice Date </strong></center></th>
					<!-- <th><strong><center>Warranty Type </strong></center></td> -->
					<th><strong><center>Lease Start Date </strong></center></th>
					<th><strong><center>Lease End Date </strong></center></th>
					
					
					
					
				</tr></thead>	<tbody>
				
			<%
			
				while(rs.next())
				{
 String nm_cc="",dt_st_lease="",dt_end_lease="";
 
 
					if(rs.getString("nm_cc")== null)
						nm_cc="--";
					else
						nm_cc=rs.getString("nm_cc");
					dt_st_lease = rs.getString("std_lease");
					if(dt_st_lease.equals("01/01/1900"))
						dt_st_lease = "---";
					 
					dt_end_lease= rs.getString("endt_lease");
					if(dt_end_lease.equals("01/01/1900"))
						dt_end_lease = "---";
				%>
					<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
					<td><center><%=rs.getString("ds_pro")%></center></td>
					<td><center><%=rs.getString("no_mfr")%></center></td>
					
					<td><center><%=rs.getString("ds_asst")%></center></td>
					<td><center><%=rs.getString("nm_ven")%></center></td>
					
					<td><center><%=rs.getString("dt_po")%></center></td>
					<td><center><%=rs.getString("dt_inv")%></center></td>
					
					<td><center><%=dt_st_lease%></center></td>
					<td><center><%=dt_end_lease%></center></td>
					
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				//out.println("sql error in AMC Warranty.");
			}
		%>
			
			<%
	        if (exportToExcel == null) {
	    %>
	   <!--  <br>
		<b><center>No record(s) is available.</center></b> -->
	   <%--  <td colspan="16">
				<a href="Lease_Reportview.jsp?exportToExcel=YES&enddate=<%=enddate1%>&startdate=<%=startdate1%>">
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
<%
	        }
	    %>
	    <!-- DataTables  Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('LeaseReport','Lease_Report_List');

</script>
</html>