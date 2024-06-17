<!--Monthly_fa.jsp-->

<script type="text/javascript">
$(function (){
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForMonthlyFaReport','CapG',function (status){
		if(status)
		{
			DropDownForDeprnType('D_Dep_Config','DataForMonthlyFaReport','YearOnly');
		}
	});
	
	//DisplayDropDownData('M_Asset_Div','assetDivForMonthlyFaReport');
})

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
						<li class="breadcrumb-item">Master</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

<div class="commonDiv" id="displayMonthlyFa">
<form action="ReportView/Monthly_Fa_Report_view.jsp" method="POST" target="_new" name="Monthly_Fa_Report" id="Monthly_Fa_Report">
	<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
					<h3 class="card-title1">Monthly FA Report</h3>
				</div>
	<table class="table table-bordered ">
		<!-- <table class="commonTable" align="center" width="600px" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 170px;">Monthly FA Report</p></td>
			</tr> -->
			<tr>
				<td><strong>Select Month</strong></td>
				<td >
					<select name="monthForFa"   class="form-control" data-valid="mand" required>
						<option value="">Select</option>
						<option value="01">January</option>
						 <option value="02">February</option>
						 <option value="03">March</option>
						 <option value="04">April</option>
						 <option value="05">May</option>
						 <option value="06">June</option>
						 <option value="07">July</option>
						 <option value="08">August</option>
						 <option value="09">September</option>
						 <option value="10">October</option>
						 <option value="11">November</option>
						 <option value="12">December</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Select Year</strong></td>
				<td >
					<select id="DataForMonthlyFaReport" name="yearForFa" class="form-control" data-valid="mand">
						<option value="select">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Select Group</strong></td>
				<td >
					<select id="assetDivForMonthlyFaReport" name="groupForFa" class="form-control" data-valid="mand">
						<option value="select">Select</option>
					</select>
				</td>
			</tr>
		</table>
			
			<br>
			
					<p style="width:180px;margin-left: 357px;float:centre;">
					<button type="submit" style="float:right;"  class="btn btn-primary" onclick="return ControleDprnReport('Monthly_Fa_Report')">Report</button>
					</p>
		</div>
		</div>
					
				
</form>		
</div>

