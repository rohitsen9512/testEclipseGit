<!--  Installed_asset.jsp -->
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script type="text/javascript">

$(function (){
	
	DisplayDropDownDataForReport('M_Emp_User','EmplyeeAssetDetailReport');
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
						<!--  Report-->
						<!-- <button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displayLocation','createLocation','submitLocation','M_Loc')">Create
							Location</button> -->
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Reports</a></li>
						<li class="breadcrumb-item">Employee Master</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	
	
<form action="ReportView/Employee_Asset_Report.jsp" method="POST" target="_new">

	<div class="card-small">
	<div class="card-header new">
	<h3 class="card-title1">Employee Report - Assets</h3>
				</div>
<div id="displayEmployeeAssetReport">
	
		<table class="table table-bordered ">
			<!-- <tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Employee Report - Assets</p></td>
			</tr> -->
			<tr>
				<td><b>Select the Employee Name<font color="red">*</font></b></td>
				<td>
					<select name="to_assign" id="EmplyeeAssetDetailReport" class="form-control select2search" data-valid="mand" style="width:100%">
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


</form>