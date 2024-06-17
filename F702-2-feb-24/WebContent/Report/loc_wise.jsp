<!--  Installed_asset.jsp -->
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">MIS Data > </a><a href="#">Asset Report > </a><a href="#">Employee Wise</a> -->
</div>
<script type="text/javascript">

$(function (){
	
	DropDownDataDisplayForReport('M_Loc','locDataForLocReport');
});
$(document).ready(function() {
    $('.select2search').select2();
});
</script>
<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!-- Report -->
						<!-- <button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displayLocation','createLocation','submitLocation','M_Loc')">Create
							Location</button> -->
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Reports</a></li>
						<li class="breadcrumb-item">Location Master</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	
	
<form action="ReportView/Loc_Asset_Report.jsp" method="POST" target="_new">

<div id="displayEmployeeAssetReport">
	<div class="card-body">
	<div class="card-small">
	<div class="card-header">
	<h3 class="card-title1">Location Wise Report - Assets</h3>
				</div>
		<table class="table table-bordered ">
			<!-- <tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Location Wise Report - Assets</p></td>
			</tr> -->
			
			<tr>
				<td ><b>City<font color="red">*</font></b></td>
				<td >
					<select id="locDataForLocReport" name="id_loc" class="form-control select2search readbaledata" data-valid="mand"   >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 500px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary" onclick="return ControleEmployee()">Report</button>
			</p>
</div>
</div>
</div>
</form>
