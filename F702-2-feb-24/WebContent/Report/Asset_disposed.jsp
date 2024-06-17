<!--Asset_disposed.jsp-->

<script type="text/javascript">
$(function (){
	
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForFaYearlyReport','CapG',function(status){
				if(status)
					{
					DropdownResultForFinanceYear('financialYearDataForReport');
					}
			});
			
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
						<li class="breadcrumb-item">Asset Disposed</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

<div class="commonDiv" id="displayAssetDisposed">
<form action="ReportView/Asset_Disposed_Report_view.jsp" method="POST" target="_blank">
<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
					<h3 class="card-title1">Select For Disposed Assets</h3>
				</div>
	<table class="table table-bordered ">
	
		<!-- <table class="commonTable" align="center" width="600px" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 130px;">Select For Disposed Assets</p></td>
			</tr> -->
			<tr>
				<td><strong>Select Dispose Type</strong></td>
				<td >
						<select  name="dispose" class="form-control select" data-valid="mand"  style="width:140">
						<option value="sold">Sold</option>
						<option value="add">Addition</option>
						<option value="delete">Deletion</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Select Asset Group</strong></td>
				<td >
					<select id="assetDivForFaYearlyReport" class="form-control select" name="group_name" >
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Select Financial Year</strong></td>
				<td >
					<select id="financialYearDataForReport" class="form-control select" name="fin_year" >
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<p style="width:180px;margin-left: 159px;float:centre;">
					<button type="submit" style="float:right;"  class="btn btn-primary">Generate Report</button>
					</p>
				</td>
			</tr>
		</table>
		</div>
		</div>
		</form>
		</div>


<div class="commonDiv" id="createAssetDisposed" style="display:none;">
	<form action="" name="submitAssetDisposed" >	
		<br><br><center><b><font color="red" class=navigate>No Assets Are sold During This Financial Year</font></b></center>
	</form>
</div>