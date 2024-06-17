<!--  Installed_asset.jsp -->


<script type="text/javascript">

$(function (){
	
	//DisplayDropDownDataForReport('M_Emp_User','EmplyeeAssetDetailReport');
});

</script>
<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Report -->
						<!-- <button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displayLocation','createLocation','submitLocation','M_Loc')">Create
							Location</button> -->
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Reports</a></li>
						<li class="breadcrumb-item">Asset Master</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	

<form action="ReportView/Status_Asset_Report.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayEmployeeAssetReport">
	<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
	<h3 class="card-title1">Asset Status Report</h3>
				</div>
		<table class="table table-bordered ">
			<!-- <tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Asset Status Report</p></td>
			</tr> -->
			<tr>
				<td><b>Select the Status<font color="red">*</font></b></td>
				<td>
					<select  name = "device_status" class="form-control"   data-valid="mand"    >
								<option value = "allct_to_emp" selected="selected" >Allocated to employee Permanent</option>
								<option value = "allct_to_emp_temp" >Allocated to employee Temporary</option>
								<option value="under_service">Under service </option>
								<option value="not_workin">Not Working </option>
								<option value="in_store">In Store </option>
								<option value="temporary_laptop">Temporary Laptop</option>
								<option value="buyback">Buy back </option>
								<option value="physical_dmg_mjr" >Physical Damage (Major)</option>
								<option value="physical_dmg_mnr">Physical Damage (Minor) </option>		
								<option value="scraped">Scrapped / disposed </option>
								<option value="advance_laptop">Advance Laptop </option>
								<option value="refreshment_working">Refreshment - working </option>
								<option value="refreshment_faulty">Refreshment - Faulty </option>
					
						</select>
				</td>
			</tr>
			
			
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 500px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary" onclick="return ControleEmployee()">Report</button>
			</p>
</div>
</form>