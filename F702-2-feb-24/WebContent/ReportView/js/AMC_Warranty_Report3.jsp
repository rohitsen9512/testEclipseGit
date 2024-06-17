
<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,java.util.Date, java.text.SimpleDateFormat,com.Common.Common,java.text.DateFormat" %>
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
<body >
	
		<%
		
		String sql="";
		String warr_amc1 = "";
		/* String date1 = "",date=""; */
		String startdate = "",startdate1 = "";
		String enddate = "",enddate1 = "";
		String end="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		String exportToExcel = request.getParameter("exportToExcel");
        
        if (exportToExcel != null
                && exportToExcel.toString().equalsIgnoreCase("YES")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename="
                    + "AMC_Warranty_Report.xls");
 
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
				// end = request.getParameter("end");
				 startdate1 = startdate;
				 enddate1 = enddate;
				 
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				if(request.getParameter("end").equals("") || request.getParameter("end").equals(null)){
				sql="select id_wh_dyn,warr_amc,ds_pro,serial_no as no_mfr,nm_cc,ds_asst,nm_ven,nm_loc,nm_subl,nm_building,nm_dept,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) dt_po,no_po,(replace(convert(VARCHAR, dt_inv, 106), ' ', '-')) dt_inv,no_inv,warr_amc,"+
						"(replace(convert(VARCHAR, dt_amc_start, 106), ' ', '-')) as dt_amc_start,"+
						"(replace(convert(VARCHAR, dt_amc_exp, 106), ' ', '-')) as dt_amc_exp,nm_computer"+
						" FROM A_Ware_House wh"+
						" left join M_Company_Costcenter emp on wh.id_cc=emp.id_cc "+
						" left join M_Dept dept on wh.id_dept=dept.id_dept "+
						" left join M_Loc loc on wh.id_loc=loc.id_loc "+
						" left join M_Subloc subl on wh.id_subl=subl.id_sloc"+
								" LEFT JOIN M_Building bul on wh.id_building=bul.id_building "+
						" left join M_Vendor ven on wh.id_ven_proc=ven.id_ven"+
					" where wh.dt_amc_exp  >='"+startdate+"' and wh.dt_amc_exp <= '"+enddate+"' ";
				}
				else{
					sql="select id_wh_dyn,warr_amc,ds_pro,serial_no as no_mfr,nm_cc,ds_asst,nm_ven,nm_loc,nm_subl,nm_building,nm_dept,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) dt_po,no_po,(replace(convert(VARCHAR, dt_inv, 106), ' ', '-')) dt_inv,no_inv,warr_amc,"+
							"(replace(convert(VARCHAR, dt_amc_start, 106), ' ', '-')) as dt_amc_start,"+
							"(replace(convert(VARCHAR, dt_amc_exp, 106), ' ', '-')) as dt_amc_exp,nm_computer"+
							" FROM A_Ware_House wh"+
							" left join M_Company_Costcenter emp on wh.id_cc=emp.id_cc "+
							" left join M_Dept dept on wh.id_dept=dept.id_dept "+
							" left join M_Loc loc on wh.id_loc=loc.id_loc "+
							" left join M_Subloc subl on wh.id_subl=subl.id_sloc"+
									" LEFT JOIN M_Building bul on wh.id_building=bul.id_building "+
							" left join M_Vendor ven on wh.id_ven_proc=ven.id_ven"+
						" where wh.dt_amc_exp  ='"+request.getParameter("end")+"'";
				}
				 //out.print(sql); 
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			%>
			<div class="card"> 

		<div id="locationAssetReportdiv">
			 <div class="card-body">
			<table id="locationAssetReport"
		class="table table-bordered table-hover locationAssetReport">
		
				
				<thead>	
				<tr>
				    <th ><strong><center>S No. </center></strong></th>
					<th ><strong><center>Asset ID</center></strong></th>
					<th ><strong><center>Asset Name </center></strong></th>
					<th ><strong><center>Serial No. </center></strong></th>
					<th ><strong><center>Asset Description</center></strong></th>
					
					<th ><strong><center>Vendor Name</center></strong></th>
					<!-- <th ><strong><center><center>Locatio</center></strong></th>
					<th ><strong><center><center>Sub Location</center></strong></th>
					<th ><strong><center><center>Building</center></strong></th>
					<th ><strong><center><center>Department Name :&nbsp;&nbsp;</center></strong></center></th> -->
                                        <th ><strong><center> PO No.</center></strong></th>
					<th ><strong><center> PO Date</center></strong></th>
                                        <th ><strong><center>Invoice No.</center></strong></th>
					<th ><strong><center>Invoice Date</center></strong></th>
					<!-- <th ><strong><center><center>Warranty Type :&nbsp;&nbsp;</center></strong></center></th> -->
					<th ><strong><center>AMC/Warranty</center></strong></th>
					<th ><strong><center>Warranty Start Date</center></strong></th>
					<th ><strong><center>Warranty End Date</center></strong></th>
					<!-- <th ><strong><center>Computer Name;</center></strong></th> -->
					
					
					
				</tr></thead><tbody>
			<%
			
				while(rs.next())
				{
 String nm_cc="";
   if(rs.getString("warr_amc").equals("W")){
		warr_amc1 = "Warranty";
  }
	if(rs.getString("warr_amc").equals("A"))
		warr_amc1 = "AMC"; 
		/* if(warr_amc1 == "W"){
			warr_amc1 = "Warranty";
		}
		if(warr_amc1 == "A")
			warr_amc1 = "AMC"; */
			
			
	
					if(rs.getString("nm_cc")== null)
						nm_cc="--";
					else
						nm_cc=rs.getString("nm_cc");
				%>
					<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
					<td><center><%=rs.getString("ds_pro")%></center></td>
					<td><center><%=rs.getString("no_mfr")%></center></td>
					<%-- <td><center><%=nm_cc%></center></center></td> --%>
					<td><center><%=rs.getString("ds_asst")%></center></td>
					<td><center><%=rs.getString("nm_ven")%></center></td>
					<%-- <td><center><center><%=rs.getString("nm_loc")%></center></center></td>
					<td><center><center><%=rs.getString("nm_subl")%></center></center></td>
					<td><center><center><%=rs.getString("nm_building")%></center></center></td>
					<td><center><center><%=rs.getString("nm_dept")%></center></center></td> --%>
                                        <td><center><%=rs.getString("no_po")%></center></td>
					<td><center><%=rs.getString("dt_po")%></center></td>
                                         <td><center><%=rs.getString("no_inv")%></center></td>
					<td><center><%=rs.getString("dt_inv")%></center></td>
						<td><center><%=warr_amc1%></center></center></td>
					<td><center><%=rs.getString("dt_amc_start")%></center></td>
					<td><center><%=rs.getString("dt_amc_exp")%></center></td>
					
					<%-- <td><center><%=rs.getString("nm_computer")%></center></td> --%>
					
										
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				out.println("sql error in AMC Warranty.");
			}
		%>
			</tbody>
	</table>
	</div>
	 </div>
	</div> 
			<%
	        if (exportToExcel == null) {
	    %>
	   
					
				
	    
	    <%
	        }
	    %>
		
	
</body>

<%
	        }
	    %>
</html>