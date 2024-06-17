<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.io.*,java.util.*, javax.servlet.*"%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible"
	content="IE=edge,charset=utf-8,text/html;">
<title>HCS Technology | Portal</title>

<!-- Google Font: Source Sans Pro -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
<!-- Font Awesome Icons -->
<link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- DataTables -->
<link rel="stylesheet"
	href="plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
<link rel="stylesheet"
	href="plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
<link rel="stylesheet"
	href="plugins/datatables-buttons/css/buttons.bootstrap4.min.css">

<!-- Theme style -->
<link rel="stylesheet" href="dist/css/adminlte.min.css">

<!-- overlayScrollbars -->
<link rel="stylesheet"
	href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">

<!-- <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">  -->
<link href="CSS/home.css" rel="stylesheet" type="text/css">
<link href="CSS/jquery-ui.css" rel="stylesheet" type="text/css">

<link href="Common.css" rel="stylesheet" type="text/css">
<link href="CSS/homedashboard.css" rel="stylesheet" type="text/css">



</head>




<%
HttpSession session2 = request.getSession();
%>

<body
	class="hold-transition white-mode sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed">
	<%
	String output = "";
	String image = "";
	String logo = "";
	if (null == session2.getAttribute("UserName")) {
		response.sendRedirect("index.html");
	} else {
		String UserName = (String) session2.getAttribute("UserName");
		System.out.println("user name is " + UserName);
		String emp_img = (String) session2.getAttribute("emp_image");
		System.out.println("emp image is " + emp_img);
		String id_logo = (String) session2.getAttribute("company_logo");
		System.out.println("comp image  is " + id_logo);
		String li_enddt = (String) session2.getAttribute("li_enddt");
		System.out.println("end date ids" + li_enddt);
		//logo = (String)session2.getAttribute("emp_image");
		String comp = (String) session2.getAttribute("nm_com");
		System.out.println("comp name" + comp);
		String words[] = UserName.split(" ");
		UserName = "";
		for (String w : words)
			UserName += " " + w.toUpperCase().replace(w.substring(1), w.substring(1).toLowerCase());
		output = UserName;
		image = emp_img;
		logo = id_logo;
	%>
	<script type="text/javascript">
			
			if(<%=li_enddt%>!=null)
			{ 
				var d = new Date();
				var l="<%=li_enddt%>";
		
			var l = new Date(l);
			if (d <= l) {
				l.setDate(l.getDate() - 31);
				if (d >= l) {
					alert("your license is going to expire, Please Connect to sales@hcstechno.com");
					/* $('try').style.display='block'; */

				}

			}

		}
	</script>


	<div class="wrapper">

		<!-- Preloader -->
		<div
			class="preloader flex-column justify-content-center align-items-center">
			<img class="animation__wobble" src="dist/img/ezAtlas.png"
				alt="AdminLTELogo" height="60" width="60">
		</div>

		<!-- Navbar -->
		<nav
			class="main-header navbar navbar-expand navbar-white navbar-light">
		<!-- <nav class="main-header navbar navbar-expand navbar-dark"> --> <!-- Left navbar links -->
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" data-widget="pushmenu"
				href="#" role="button"><i class="fas fa-bars"></i></a></li>
			<li class="nav-item d-none d-sm-inline-block">
				<h2 class="brand-text font-weight" style="color: Black">
					<strong> Welcome To <%=comp %> IT Service Portal</strong>
				</h2>
			</li>

		</ul>

		<!-- Right navbar links -->
		<ul class="navbar-nav ml-auto">
			<!-- Navbar Search -->
			<!-- <li class="nav-item"> -->
			<li><a href="Logout"><i
					class="fa-solid fa-arrow-right-from-bracket"></i>SignOut</a></li>
			


		</ul>


		</nav>
		<!-- /.navbar -->

		<!-- Main Sidebar Container -->
		<aside class="main-sidebar sidebar-dark-primary elevation-4">
		<!-- Brand Logo --> <a href="#" class="brand-link brd1" onClick="window.location='home.jsp'"> 
			<span class="brand-text font-weight-light"><center><%=comp %></center></span>
		</a> <!-- Sidebar -->
		<div class="sidebar">
			<!-- Sidebar user panel (optional) -->
			<div class="user-panel mt-3 pb-3 mb-3 d-flex">
				<div class="image">
					<img id="image"/>
				</div>
				<div class="info">

					<a href="#" class="d-block"
						onclick="changeSubContent('user.jsp','profile')"><%=UserName%></a>
				</div>
			</div>

			<!-- SidebarSearch Form -->
			<div class="form-inline">
				<div class="input-group" data-widget="sidebar-search">
					<input class="form-control form-control-sidebar" type="search"
						placeholder="Search" aria-label="Search">
					<div class="input-group-append">
						<button class="btn btn-sidebar">
							<i class="fas fa-search fa-fw"></i>
						</button>
					</div>
				</div>
			</div>


			<!-- Sidebar Menu -->

			<nav class="mt-2">
			<ul class="nav nav-pills nav-sidebar flex-column"
				data-widget="treeview" role="menu" data-accordion="false">
			
					 <li class="nav-item MyAsset" >
			 <a href="javascript:changeSubContent('MyAsset/myasset.jsp','MyAsset_event')" class="nav-link MyAsset_event"> <i
						class="nav-icon fa fa-user" aria-hidden="true"></i> <span
						class="badge badge-info right"></span>
						<p>
							My Assets 
						</p>
				</a>

				</li>

				
				 <li class="essRaiseIssue nav-item" onclick="">

             <a href="javascript:changeSubContent('HelpDesk/ess_raise_issue.jsp','essRaiseIssue_event')" class="nav-link essRaiseIssue_event">
                  <i class="fas fa-arrow-circle-right nav-icon"></i>
                  <p>Raise Issue</p>
                </a>
              </li>
              <li class="Ess_All_Ticket nav-item " onclick="">

             <a href="javascript:changeSubContent('HelpDesk/ess_all_tickets.jsp','Ess_All_Ticket_event')" class="nav-link Ess_All_Ticket_event">
                  <i class="fas fa-arrow-circle-right nav-icon"></i>
                  <p>Your Tickets</p>
                </a>
              </li> 

				<!-- <li class="nav-header">EXAMPLES</li>
				<li class="nav-item"><a href="pages/calendar.html"
					class="nav-link"> <i class="nav-icon fas fa-calendar-alt"></i>
						<p>
							Calendar <span class="badge badge-info right">2</span>
						</p>
				</a></li>
 -->



			</ul>

			</nav>
			<!-- /.sidebar-menu -->
		</div>
		<!-- /.sidebar --> </aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			
			
	<!--/. container-fluid -->
	</section>
	<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

	<!-- Control Sidebar -->
	<aside class="control-sidebar control-sidebar-dark"> <!-- Control sidebar content goes here -->
	</aside>
	<!-- /.control-sidebar -->

	<!-- Main Footer -->
	<footer class="main-footer"> <strong>Copyright &copy;
		2021-2022 <a href="#">HCS Technology</a>.
	</strong> All rights reserved.
	<div class="float-right d-none d-sm-inline-block">
		<b>Version</b> 3.2.0-rc
	</div>
	</footer>
	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED SCRIPTS -->



	<!-- jQuery -->
	<script src="plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery1.10.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>

	<script type="text/javascript" src="common.js"></script>
	<!-- overlayScrollbars -->
	<script
		src="plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>


	<!-- PAGE PLUGINS -->

	<!-- Bootstrap 4 -->
	<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>




	<!-- DataTables  & Plugins -->
	<script src="plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
	<script
		src="plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
	<script
		src="plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
	<script src="plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
	<script src="plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
	<script src="plugins/jszip/jszip.min.js"></script>
	<script src="plugins/pdfmake/pdfmake.min.js"></script>
	<script src="plugins/pdfmake/vfs_fonts.js"></script>
	<script src="plugins/datatables-buttons/js/buttons.html5.min.js"></script>
	<script src="plugins/datatables-buttons/js/buttons.print.min.js"></script>
	<script src="plugins/datatables-buttons/js/buttons.colVis.min.js"></script>


	<!-- AdminLTE App -->
	<script src="dist/js/adminlte.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="dist/js/demo.js"></script>
	<!-- Page specific script -->
	<!-- bs-custom-file-input -->
	<script src="plugins/bs-custom-file-input/bs-custom-file-input.min.js"></script>
	<!-- jQuery Mapael -->
	<script src="plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
	<script src="plugins/raphael/raphael.min.js"></script>
	<script src="plugins/jquery-mapael/jquery.mapael.min.js"></script>
	<script src="plugins/jquery-mapael/maps/usa_states.min.js"></script>
	<!-- ChartJS -->
	<script src="plugins/chart.js/Chart.min.js"></script>
	<script type="text/javascript" src="All_Js_File/Dashboard/dashboard.js"></script>
	<!-- DataTables  Utlity -->
	<script type="text/javascript" src="js/data.js"></script>

	<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
	<!-- <script src="dist/js/pages/dashboard2.js"></script> -->
	<!--  bootbox purpose-->
	<script type="text/javascript" src="BootBox/bootbox.min.js"></script>
	<!-- pages common js -->
	<script type="text/javascript" src="js/masterCommon.js"></script>
	<script type="text/javascript" src="js/assetCommon.js"></script>
	<script src="Autocomp/jquery.easy-autocomplete.min.js"></script>
	<script type="text/javascript" src="js/reportCommon.js"></script>
	<script type="text/javascript" src="js/commonReport.js"></script>
<script type ="text/javascript" src="js/heplDeskCommon.js"></script>
<script type ="text/javascript" src="All_Js_File/HelpDesk/HD_New_Ticket.js"></script>
<script type ="text/javascript" src="All_Js_File/HelpDesk/HD_Ess_New_Ticket.js"></script>


	<!-- Depreciation Module -->
	<script type="text/javascript" src="js/depreciationCommon.js"></script>
	<script type="text/javascript"
		src="All_Js_File/Depreciation/Depreciation_Master.js"></script>
	<script type="text/javascript"
		src="All_Js_File/Depreciation/Dep_Config.js"></script>
	<script type="text/javascript"
		src="All_Js_File/Depreciation/D_Control_Dprn.js"></script>
	<script type="text/javascript"
		src="All_Js_File/Depreciation/Addition_Deletion.js"></script>
	<script type="text/javascript" src="All_Js_File/Report/D_DprnCommon.js"></script>
	<!-- Dashboard JS -->
	<script type="text/javascript" src="js/chart/Chart.bundle.min.js"></script>
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/highcharts-3d.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script src="https://code.highcharts.com/modules/drilldown.js"></script>

	<script type="text/javascript">
	$(function (){
		  /* $( "
								#menu" ).menu();   */
		var UserType='<%=(String) session2.getAttribute("UserType")%>';
		
								

	

		LoginAccess(UserType, 'M_Main_Menu_Access','mainMenuContent1');
		LoginAccess(UserType, 'M_Sub_Menu_Access','commonActive');

		DisplayDropDownDataForGroup('M_Asset_Div', 'groupDataForModel',	'CapG', function(status) {
					if
								(status) {

						}
					});
		});

		
									document.getElementById("image").src="InvoiceScanFile/<%=emp_img%>";	
			/* subGroup();
			pricechart();
			displayDamageDashboard();
			 displayRecentAssetDashboard();  */
			
								</script>
								

								<%
	}
	%>
							
</body>
</html>
