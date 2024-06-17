<!--  LoacationAsset11.jsp -->

<script type="text/javascript">

$(function (){
	
		DisplayDropDownDataForGroup('M_Asset_Div','assetDivForStoreAssetProcReport','CapG',function (status){
			if(status)
			{
				
			}});
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
						<li class="breadcrumb-item">Financial Reports</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

<form action="ReportView/Procurement_Type_Report.jsp" method="POST" target="_new">
<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
					<h3 class="card-title1">Procurement Type Report - Assets</h3>
				</div>
	<table class="table table-bordered ">

<div class="commonDiv" id="displayProcAsset">
	
		<!-- <table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Procurement Type Report - Assets</p></td>
			</tr> -->
			<tr>
				<td><b>Select the Asset Group<font color="red">*</font></b></td>
				<td>
					<select id="assetDivForStoreAssetProcReport" name="id_grp"class="form-control"  style="width:140" >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			<tr>

				<td><b>Select Type of Import <font color="red">*</font></b></td>
				<td>
					<select  name="typ_proc" class="form-control select" data-valid="mand"  style="width:140">
						<option value="OP">Outright Purchase</option>
						<option value="FOC">Free of Cost</option>
						<option value="LB">Loan Basis</option>
						
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td ><b>Type of Asset<font color="red">*</font></b></td>
				<td >
					<select  name="allocate" class="form-control select" data-valid="mand"  style="width:140">
						<option value="1">Installed Asset</option>
						<option value="0">Store Asset</option>
						<option value="2">Un-Installed Asset</option>
						<option value="3">Sold Right OFF</option>
						
						
					</select>
				</td>
			</tr>
			
			
			
			
			
			
			
		</table>
		
	
		
			<p style="width: 85px;margin-left: 443px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary" onclick="return ControleProc()">Report</button>
			</p>
</div>
</div>

</form>
</div>
