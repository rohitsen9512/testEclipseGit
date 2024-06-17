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
<title>HCS Technology | Dashboard</title>

<!-- Google Font: Source Sans Pro -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
<!-- Font Awesome Icons -->
<link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- DataTables -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.4/css/jquery.dataTables.min.css">

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
<link href="loading.css" rel="stylesheet" type="text/css">
<link href="Common.css" rel="stylesheet" type="text/css">
<link href="CSS/homedashboard.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function disableBackButton()
{
  window.history.forward();
}
setTimeout("disableBackButton()", 0);
 $(document).ready(function () {
    disableBackButton();
 });
</script>



</head>

<style type="text/css">

#image1
{
height: 45px;
    width: 233px;
}
#image2
{
height: 50px;
    width: 50px;
}
</style>

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
		String proof_cltn_file = (String) session2.getAttribute("proof_cltn_file");
		System.out.println("emp proof_cltn_file is " + emp_img);
		String id_logo = (String) session2.getAttribute("company_logo");
		System.out.println("comp image  is " + id_logo);
		String li_enddt = (String) session2.getAttribute("li_enddt");
		System.out.println("end date ids" + li_enddt);
		//logo = (String)session2.getAttribute("emp_image");
		String comp = (String) session2.getAttribute("nm_com");
		System.out.println("comp name" + comp);
		String nm_emp = (String) session2.getAttribute("nm_emp");
		System.out.println("emp name" + nm_emp);
		String UserType = (String) session2.getAttribute("UserType");
		System.out.println("User type is" + UserType);
		String words[] = UserName.split(" ");
		UserName = "";
	  
		for (String w : words)
			UserName += " " + w.toUpperCase().replace(w.substring(1), w.substring(1).toLowerCase());
		output = UserName;
		//image = emp_img;
		  if(emp_img.equals("") || emp_img.equals(null)){
	            emp_img="userimage.png";
	          
	        }
		if(id_logo.equals("") || id_logo.equals(null)){
			id_logo="newlogo.png";
		}else{
			id_logo = id_logo;
		}
	%>
	<%--  document.getElementById("image1").src="InvoiceScanFile/<%=id_logo%>";
	document.getElementById("image2").src="InvoiceScanFile/<%=emp_img%>";  --%>
	<script type="text/javascript">

	
			<%-- if(<%=li_enddt%>!=null)
			{ 
				var d = new Date();
				var l="<%=li_enddt%>";
		
			var l = new Date(l);
			if (d <= l) {
				var pending_day=(l.getDate());
				var today=(d.getDate());
				//alert(pending_day);
				//alert(today);
				l.setDate(l.getDate() - 7);
				if (d >= l) {
					alert("your license is going to expire, " +(pending_day-today)+ "  days left, Please Connect to sales@hcstechno.com");
					/* $('try').style.display='block'; */

				}

			}

		} --%>
			

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
				<h2 >
					<strong style="font-size:25px;"> Welcome To F7 HealthCare Order Management System</strong>
				</h2>
			</li>

		</ul>

		<!-- Right navbar links -->
		<ul class="navbar-nav ml-auto">
			<!-- Navbar Search -->
			<!-- <li class="nav-item"> -->
			<div class="image2">
					<img id="image2"/>
				</div>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" class="d-block"
						onclick="changeSubContent('user.jsp','profile')">USER NAME- <%=nm_emp%>&nbsp;&nbsp;<br>USER TYPE- <%=UserType%></a>&nbsp;&nbsp;&nbsp;&nbsp;
						<li><a href="Logout"><i
					class="fa fa-sign-out"></i>Sign Out</a></li>
					
			


		</ul>


		</nav>
		<!-- /.navbar -->

		<!-- Main Sidebar Container -->
		<aside class="main-sidebar sidebar-dark-primary elevation-4">
		<!-- Brand Logo --><!--  <a href="#" class="brand-link brd1" onClick="window.location='home.jsp'">  -->
<%-- 			<span class="brand-text font-weight-light"><center><%=comp %></center></span>
 --%>		</a> <!-- Sidebar -->
		<div style="margin-top:0px;" class="sidebar">
			<!-- Sidebar user panel (optional) -->
			<div class="user-panel mt-3 pb-3 mb-3 d-flex">
				<div class="image1">
					<!-- <img id="image1"/> -->
					<img id="image1"  style="min-width: 231px;
    min-height: 100px;
    max-width: 231px;
    max-height: 100px;border: 2px solid #555;margin-right: 20px; border-radius: 10px;" />
					<br><br>
				</div>
				<%-- <div class="info">

					<a href="#" class="d-block"
						onclick="changeSubContent('user.jsp','profile')"><%=UserName%></a>
				</div> --%>
			</div>

			<!-- SidebarSearch Form -->
			<!-- <div class="form-inline">
				<div class="input-group" data-widget="sidebar-search">
					<input class="form-control form-control-sidebar" type="search"
						placeholder="Search" aria-label="Search">
					<div class="input-group-append">
						<button class="btn btn-sidebar">
							<i class="fas fa-search fa-fw"></i>
						</button>
					</div>
				</div>
			</div> -->


			<!-- Sidebar Menu -->

			<nav class="mt-2">
			<ul class="nav nav-pills nav-sidebar flex-column"
				data-widget="treeview" role="menu" data-accordion="false">
				 <li class="nav-item" onClick="window.location='home.jsp'"> <a
					href="#" class="nav-link"> <i class="nav-icon fas fa-home"></i>
						<p  class="navpara">Home</p>
				</a>


				</li>
				<!--<li class="nav-item" onclick="changeSubContent('Master/AMS_Process.jsp','');">
			 <a href="#" class="nav-link"> <i
						class="nav-icon fa fa-user" aria-hidden="true"></i> <span
						class="badge badge-info right"></span>
						<p  class="navpara">
							AMS Process
						</p>
				</a>

				</li> -->
				<li class="nav-item mainMenuContent1 master" ><a href="#" class="nav-link"> <i
						class="nav-icon fas fa-copy"></i>
						<p class="navpara">
							Master <i class="fas fa-angle-left right"></i> <span
								class="badge badge-info right"></span>
						</p>
				</a> <%@ include file="Master/master.jsp"%></li>
						
						<li class="nav-item mainMenuContent1 stock" ><a href="#" class="nav-link"> <i
						class="nav-icon fas fa-copy"></i>
						<p class="navpara">
							 Stock <i class="fas fa-angle-left right"></i> <span
								class="badge badge-info right"></span>
						</p>
				</a> <%@ include file="Store/Store.jsp"%></li>
 
<!--                 <li class="nav-item mainMenuContent1 DirectPO" >
			 <a href="javascript:changeSubContent('Master/DirectPO.jsp','DirectPO_event')" class="nav-link DirectPO_event "> <i
						class="nav-icon fas fa-copy"></i> 
						<p class="navpara">
							Direct PO
						</p>
				</a>

				</li>  -->

				
					<li class="nav-item mainMenuContent1 Lead" ><a href="#" class="nav-link"> <i
						class="nav-icon fas fa-copy"></i>
						<p class="navpara">
							 Order <i class="fas fa-angle-left right"></i> <span
								class="badge badge-info right"></span>
						</p>
				</a> <%@ include file="Order/leadmaster.jsp"%></li>
			
			<li class="nav-item mainMenuContent1 jobworkmaster"><a href="#" class="nav-link "> <i
						class="nav-icon fas fa-file-alt"></i> 
						<p   class="navpara">
							Job Work<i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="jobwork/jobwork_master.jsp"%>
				

				</li>
					<li class="nav-item mainMenuContent1 stocktransfermaster"><a href="#" class="nav-link "> <i
						class="nav-icon fas fa-file-alt"></i> 
						<p   class="navpara">
							Stock Transfer<i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="stocktransfer/stocktransferMaster.jsp"%>
				

				</li>
			
				<li class="nav-item mainMenuContent1 report"><a href="#" class="nav-link "> <i
						class="nav-icon fas fa-file-alt"></i> 
						<p   class="navpara">
							Analytics/Reports <i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="Report/report_master.jsp"%>
				

				</li>
			   
			   	<li class="nav-item mainMenuContent1 tagging1"><a href="#" class="nav-link "> <i
						class="nav-icon fas fa-file-alt"></i> 
						<p   class="navpara">
							Tagging<i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="Tagging/Tagmaster.jsp"%>
				

				</li>
			
			<%-- 		 <li class="nav-item MyAsset" >
			 <a href="javascript:changeSubContent('MyAsset/myasset.jsp','MyAsset_event')" class="nav-link MyAsset_event "> <i
						class="nav-icon fa fa-user" aria-hidden="true"></i> <span
						class="badge badge-info right"></span>
						<p class="navpara">
							My Assets 
						</p>
				</a>

				</li>

				<li class="nav-item mainMenuContent1 assets"><a href="#" class="nav-link "> <i
						class="nav-icon fas fa-italic"></i> <span
						class="badge badge-info right"></span>
						<p   class="navpara">
							Asset <i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="Assets/asset_master.jsp"%>

				</li>

				<li class="nav-item mainMenuContent1 dashboard"><a href="#" class="nav-link "> <i
						class="nav-icon fas fa-chart-pie"></i> <span
						class="badge badge-info right"></span>
						<p  class="navpara">
							Dashboard <i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="Dashboard/dashboard_master.jsp"%>

				</li>

			
				<li class="nav-item mainMenuContent1 depreciation"><a href="#" class="nav-link "> <i
						class="nav-icon fas fa-money-check-alt"></i> <span
						class="badge badge-info right"></span>
						<p   class="navpara">
							Depreciation <i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="Depreciation/depreciation_master.jsp"%>

				</li>

				<li class="nav-item mainMenuContent1 transfer"><a href="#" class="nav-link"> <i
						class="nav-icon fa fa-exchange" aria-hidden="true"></i> <span
						class="badge badge-info right"></span>
						<p  class="navpara">
							Transfer <i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="Transfer/transfer_master.jsp"%>

				</li>

				<div id="VIP" style="display:none">
				<li class="nav-item Onboarding">
			 <a href="javascript:changeSubContent('FreeTrial/Onboarding.jsp','Onboarding_event')" class="nav-link Onboarding_event">
						 <i class="nav-icon fas fa-tags" aria-hidden="true"></i> <span
						class="badge badge-info right"></span>
						<p>
							Free Trail
						</p>
				</a>

				</li>
				</div> --%>

				
				<!-- <li class="nav-item mainMenuContent1 index" >
			 <a href="javascript:changeSubContent('index3.jsp','index_event')" class="nav-link index_event">
						 <i class="nav-icon fas fa-tags" aria-hidden="true"></i> <span
						class="badge badge-info right"></span>
						<p>
							Portal
						</p>
				</a>

				</li> -->
				<%-- <li class="nav-item mainMenuContent1 helpdesk"><a href="#" class="nav-link helpdesk"> <i
						class="nav-icon fas fa-question" aria-hidden="true"></i> <span
						class="badge badge-info right"></span>
						<p class="navpara">
							Help <i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="HelpDesk/help_desk.jsp"%>

				</li> --%>
               <%--  <li class="nav-item mainMenuContent1 incident"><a href="#" class="nav-link incident"> <i
						class="nav-icon fas fa-question" aria-hidden="true"></i> 
						<p class="navpara">
							Incident <i class="right fas fa-angle-left"></i>
						</p>
				</a> <%@ include file="Incident/incident.jsp"%>

				</li> --%>
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
			<div class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="m-0">Dashboard</h1>
						</div>
						<!-- /.col -->
						<!-- <div class="downloaddHelpDocument" >
							 <a id="downloadHelpFile" href="#">Download Help Document</a> 
						</div> -->
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<!-- Main content -->
			<section class="content">
			<div class="container-fluid">
				<!-- Info boxes -->
				<div class="row">
					<div class="col-12 col-sm-6 col-md-4">
						<div class="info-box">
							<span class="info-box-icon bg-info elevation-1"><i
								class="fas fa-users"></i></span>

							<div class="info-box-content"><a href="#"
									onclick="controlreport()">
								<span class="info-box-text">Total Rental
										Product</span>
								<div class="Rental_Product" style= "font-weight: bold;" data-fresh-interval="20"></div></a>



							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
						<div class="col-12 col-sm-6 col-md-4">
						<div class="info-box mb-3">
							<span class="info-box-icon bg-success elevation-1"><i
								class="fas fa-link"></i></span>

							<div class="info-box-content"><a href="#"
									onclick="controlreportsale()">
								<span class="info-box-text">Total Sale Product</span>
								<div class="Total_sale" style= "font-weight: bold;" data-fresh-interval="20"></div></a>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-12 col-sm-6 col-md-4">
						<div class="info-box mb-3">
							<span class="info-box-icon bg-danger elevation-1"><i
								class="fas fa-store"></i></span>

							<div class="info-box-content"><a href="#"
									onclick="controlreportinstore('in_store')">
								<span class="info-box-text">Product In Store</span>
								<div class="In_store_Product"style= "font-weight: bold;" data-fresh-interval="20"></div></a>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					
						<div class="col-12 col-sm-6 col-md-4">
						<div class="info-box mb-3">
							<span class="info-box-icon bg-success elevation-1"><i
								class="fas fa-warehouse"></i></span>

							<div class="info-box-content"><a href="#"
									onclick="controlAccereport()">
								<span class="info-box-text">Purchase Invoice</span>
								<div class="invoicepending" style= "font-weight: bold;" data-fresh-interval="20"></div></a>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->

					<!-- fix for small devices only -->
					<div class="clearfix hidden-md-up"></div>

					<div class="col-12 col-sm-6 col-md-4">
						<div class="info-box mb-3">
							<span class="info-box-icon bg-success elevation-1"><i
								class="fas fa-shopping-cart"></i></span>

							<div class="info-box-content"><a href="#"
									onclick="invoivereport()">
								<span class="info-box-text">Total Create Invoice</span>
								<div class="create_inv"style= "font-weight: bold;" data-fresh-interval="20"></div></a>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
				
				
				<!-- 	<div class="col-12 col-sm-6 col-md-4">
						<div class="info-box mb-3">
							<span class="info-box-icon bg-success elevation-1"><i
								class="fas fa-keyboard"></i></span>

							<div class="info-box-content"><a href="#"
									onclick="controlAccereport('2')">
								<span class="info-box-text"></span>
								<div class="totalaccess" style= "font-weight: bold;" data-fresh-interval="20"></div> </a>
							</div>
							/.info-box-content
						</div>  -->
						<!-- /.info-box -->
					<!-- </div>
					<div class="col-12 col-sm-6 col-md-3">
						<div class="info-box mb-3">
							<span class="info-box-icon bg-success elevation-1"><i
								class="fas fa-house-damage"></i></span>

							<div class="info-box-content" ><a href="#"
									onclick="controlreport('allDamage')">
								<span class="info-box-text">Damaged Asset</span>
								<div class="damagedAsset" style= "font-weight: bold;" data-fresh-interval="20"></div> </a>
							</div>
							/.info-box-content
						</div> -->
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<!-- <div class="col-12 col-sm-6 col-md-3">
						<div class="info-box mb-3">
							<span class="info-box-icon bg-warning elevation-1"><i
								class="fas fa-coins"></i></span>

							<div class="info-box-content">
							<a href="#"
									onclick="controlreportHelpDesk()">
								<span class="info-box-text">Total Open Tickets</span>
								<div class="payments" style= "font-weight: bold;" data-fresh-interval="20"></div></a>
							</div>
							/.info-box-content
						</div> -->
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			<!-- 	<div class="row">
					<div class="col-md-12">
						BAR CHART

						<div class="card card-success">
							<div class="card-header">
								<h3 class="card-title">Yearly Invoice Chart</h3>

								<div class="card-tools">
									<button type="button" class="btn btn-tool"
										data-card-widget="collapse">
										<i class="fas fa-minus"></i>
									</button>
									<button type="button" class="btn btn-tool"
										data-card-widget="remove">
										<i class="fas fa-times"></i>
									</button>
								</div>
							</div>
							<div class="card-body">
								<div class="chart">

									<div class="card">
										<div class="card-body">
											<h5 class="card-title"></h5>
											<div id=barchart class="baryear">
											
										</div>
										<canvas id="barChart" width="400" height="100"></canvas> 
									</div>
</div>
								</div>
							</div>
							/.card-body
						</div>
					</div>
				</div> -->

			<!-- 	<div class="row">
					<div class="col-md-6">
						<div class="card">

							<div class="card-header new">
								<h5 class="card-title">Category Wise Asset Details</h5>

								<div class="card-tools">
									<button type="button" class="btn btn-tool"
										data-card-widget="collapse">
										<i class="fas fa-minus"></i>
									</button>
									
									<button type="button" class="btn btn-tool"
										data-card-widget="remove">
										<i class="fas fa-times"></i>
									</button>
								</div>
							</div>

							<div class="chart" style="display: inline-flex;">
								Sales Chart Canvas
								<div id="container2" class="containeritem "></div>

							</div>
						</div>
					</div>

					/.card
					<div class="col-md-6">
						<div class="card">

							<div class="card-header new">
								<h5 class="card-title">Sub-Category Wise Asset Details</h5>

								<div class="card-tools">
									<button type="button" class="btn btn-tool"
										data-card-widget="collapse">
										<i class="fas fa-minus"></i>
									</button>
									
									<button type="button" class="btn btn-tool"
										data-card-widget="remove">
										<i class="fas fa-times"></i>
									</button>
								</div>
							</div>

							<div class="chart" style="display: inline-flex;">
								Sales Chart Canvas
								<div id="container3" class="containeritem"></div>

							</div>
						</div>
					</div>
				chart1 
</div>
			<!-- <div class="row">
					<div class="col-md-12">
						<div class="card">

							<div class="card-header new">
								<h5 class="card-title">AMC/Warranty Details</h5>

								<div class="card-tools">
									<button type="button" class="btn btn-tool"
										data-card-widget="collapse">
										<i class="fas fa-minus"></i>
									</button>
									<button type="button" class="btn btn-tool"
										data-card-widget="remove">
										<i class="fas fa-times"></i>
									</button>
								</div>
							
								</div>
									<div id="container4" class="containeritem"></div>
							</div>

						</div>

					</div> -->



				
				<!-- row -->


		<!-- 		<div class="row">
					<div class="col-md-6">
						<div class="card">
							<div class="card-header new">
								<h3 class="card-title">Recently Added Asset</h3>

								<div class="card-tools">
									<button type="button" class="btn btn-tool"
										data-card-widget="collapse">
										<i class="fas fa-minus"></i>
									</button>
									<button type="button" class="btn btn-tool"
										data-card-widget="remove">
										<i class="fas fa-times"></i>
									</button>
								</div>
							</div>
							/.card-header
							<div class="card-body p-0" id="RecentAsset">
								recently Added Product
							</div>

						</div>
					</div>
					/.card-header
					<div class="col-md-6">

						<div class="card">
							<div class="card-header new">
								<h3 class="card-title">Damaged Asset</h3>

								<div class="card-tools">
									<button type="button" class="btn btn-tool"
										data-card-widget="collapse">
										<i class="fas fa-minus"></i>
									</button>
									<button type="button" class="btn btn-tool"
										data-card-widget="remove">
										<i class="fas fa-times"></i>
									</button>
								</div>
							</div> -->
							<!-- /.card-header -->
							
						<!-- <form action="Assets/Damage_asset.jsp" method="post" target="_new"> -->
							<!-- <div class="card-body p-0" id="trycatch">



								/.card-body
							</div> -->
							<!-- </form> -->
							</div>
						</div>
						<!-- /.card -->
					</div>
				</div>
			</div>
			<!-- /.row -->
		</div>






	<!-- Main row -->
	<div class="row">
		<!-- Left col -->



	</div>
	<!-- /.row -->
	</div>
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
		2022-2023 <a href="#">HCS Technologies Pvt. Ltd</a>.
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
	<script type="text/javascript" src="bootbox1/bootbox.min.js"></script>
	<!-- pages common js -->
	<script type="text/javascript" src="js/masterCommon.js"></script>
	<script src="js/sweetalert2.all.min.js"></script>
	<!-- <script type="text/javascript" src="js/assetCommon.js"></script> -->
	<script src="Autocomp/jquery.easy-autocomplete.min.js"></script>
	<script type="text/javascript" src="js/reportCommon.js"></script>
	<script type="text/javascript" src="js/commonReport.js"></script>



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
	<script type="text/javascript" src="js/chart/highcharts-3d.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script src="https://code.highcharts.com/modules/drilldown.js"></script>

	<script type="text/javascript">
	
	
	
	$('#downloadEsclationMatrix').click(function(e) {
	    e.preventDefault();  //stop the browser from following
	    window.location.href = 'InvoiceScanFile/Escalation Matrix.pdf';
		
	});
	
	$(function (){
		  /* $( "
								#menu" ).menu();   */
		var UserType='<%=(String) session2.getAttribute("UserType")%>';
		
								

	
        //debugger;
		LoginAccess(UserType, 'M_Main_Menu_Access','mainMenuContent1');
		LoginAccess(UserType, 'M_Sub_Menu_Access','commonActive');

		DisplayDropDownDataForGroup('M_Asset_Div', 'groupDataForModel',	'CapG', function(status) {
					if
								(status) {

						}
					});
		});

		$(function() {
			DashCountTotal(function(status) {
				//
				if 
								(status) {
					setTimeout(function() {
						//do what you need here
						
								
								groupWise(function(status) {
							
							if(status) {
								 setTimeout(function() {
									subGroup(function(status) {
										if
										(status) {
										setTimeout(function() {
											pricechart(function(status) {
												if
												(status) {
												setTimeout(function() {
													displayDamageDashboard(function(status) {
														if(status) {
															
															AMCDash(function(status) {
																if(status) {
																	
																	 displayRecentAssetDashboard(); 
																}
															});
															
														}
													});
												}, 100);
											}
											});
										}, 100);
									}
								});
							}, 100);
							}
						});
					}, 100);
				}
			});

			/*
								*/

		});
	<%-- --%>
		function Readnotification() {

			var noticnt=$( '#noticnt').val();
			if (noticnt=='0') {

				$('#NotiTable').show();
				$('#noticnt').val('1');
			} else {
				$('#NotiTable').hide();
				$('#noticnt').val('0');
			}

			//alert("xxx");
		}
		/* $(document).click(function(e) {
			   $('#somediv').hide();
			}); */
		function ShowNotification() {
			$.post('Submit_RFI', {
				action	: 'notification'}, 
				function(r) {
				console.log(r.data[0].count);
				$("#countnoti").text(r.data[r.data.length - 1].count);
				var list="" ;
				if (r.data.length>
								0) { for (var i = 0; i < r.data.length; i++) { params =
								r.data[i]; list += '<tr>' + '<td>' + r.data[0].dt_rfi + '</td>' + '	<td>' + r.data[0].no_rfi + '</td>' + '<td>' + r.data[0].nm_emp + '</td>' + '</tr>';
								} 
								}
				$('#NotiTable').html(list);
				},
				'json');
			} 
			
			function controlreport() { 
				SessionCheck(function (ses){
					if(ses)
					{
				var currentLocation = window.location.href;
				
								//url = currentLocation.slice(0, -9) +"/ReportView/Status_Asset_Report.jsp?device_status=" + sts + "";
								url = currentLocation.slice(0, -9) +"/ReportView/Status_Rental_Report.jsp";
								window.open(url, '_blank'); 
					}
				});
								}
			
			function controlreportsale() { 
				SessionCheck(function (ses){
					if(ses)
					{
				var currentLocation = window.location.href;
				
								//url = currentLocation.slice(0, -9) +"/ReportView/Status_Asset_Report.jsp?device_status=" + sts + "";
								url = currentLocation.slice(0, -9) +"/ReportView/Status_Sale_Report.jsp";
								window.open(url, '_blank'); 
					}
				});
								}
			function controlreportinstore(sts) { 
				SessionCheck(function (ses){
					if(ses)
					{
				var currentLocation = window.location.href;
				
								//url = currentLocation.slice(0, -9) +"/ReportView/Status_Asset_Report.jsp?device_status=" + sts + "";
								url = currentLocation.slice(0, -9) +"/ReportView/Status_Instore_Report.jsp?Product_status=" + sts + "";
								window.open(url, '_blank'); 
					}
				});
								} 
			function controlAccereport() {
				SessionCheck(function (ses){
					if(ses)
					{
								var currentLocation = window.location.href; 
								//url =currentLocation.slice(0, -9) +	"/ReportView/DashboardAccessories.jsp?parent=" + sts + "";
								url =currentLocation.slice(0, -9) +	"/ReportView/StatusPurchase_Invoice_Report.jsp";
								window.open(url, '_blank');
					}
				});
								} 
			
								 function invoivereport() {
									 SessionCheck(function (ses){
										 if(ses)
										 {
									var currentLocation = window.location.href; 
									url =currentLocation.slice(0, -9) +	"/ReportView/Status_Invoice_Report.jsp";
									window.open(url, '_blank'); 
										 }
									 });
									}  
								 function controlreportHelpDesk() { 
									 SessionCheck(function (ses){
										 if(ses)
										 {	
									 var currentLocation = window.location.href;
										
														url = currentLocation.slice(0, -9) +"/ReportView/HelpDesk_Report.jsp?";
														window.open(url, '_blank'); 
										 }
									 });				
										 }
								  document.getElementById("image1").src="InvoiceScanFile/<%=id_logo%>";
									document.getElementById("image2").src="InvoiceScanFile/<%=emp_img%>";  
			/* subGroup();
			pricechart();
			displayDamageDashboard();
			 displayRecentAssetDashboard();  */
			 
			my();
									function my(){
										
										var l="<%=comp%>";
									var cc="HCS TECHNOLOGY PVT LTD";
									
												if(l===cc)
													{
													//$('#VIP').show();
													  var x = document.getElementById("VIP");
													 
													 x.style.display = "block"; 
													 //document.getElementById().style.display = "block";

													}
									}
								</script>
								

								<%
	}
	%>
							
</body>
</html>
