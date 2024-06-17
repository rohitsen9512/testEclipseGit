<!--Yearly_fa.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#"> Reports ></a><a href="#">Financial reports ></a><a href="#">Yearly FA For CA</a> -->
</div>
<script type="text/javascript">
$(function (){
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForFaYearlyReport','CapG',function (status){
		if(status)
		{
			DropdownResultForFinanceYear('financialYearDataForReport');
		}
	});
	//DisplayDropDownData('D_Dep_Config','typeIdForFaYearlyReport');
	//DisplayDropDownData('M_Asset_Div','assetDivForFaYearlyReport');
	//DropdownResultForFinanceYear('financialYearDataForReport');
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
						<li class="breadcrumb-item">Master</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

<form action="ReportView/Yearly_Fa_Report_View_CA.jsp" method="POST" target="_new" name="Yearly_Fa_Report_CA" id="Yearly_Fa_Report_CA">
<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
					<h3 class="card-title1">Select Group and Financial Year</h3>
				</div>
	<table class="table table-bordered ">
<div class="commonDiv" id="displayYearlyFa">
	
		<!-- <table class="commonTable" align="center" width="600px" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 115px;">Select Group and Financial Year</p></td>
			</tr> -->
			<tr>
				<td><strong>Select Group</strong></td>
				<td >
					<select id="assetDivForFaYearlyReport" class="form-control" name="group_names"  >
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Select Financial Year</strong></td>
				<td >
					<select id="financialYearDataForReport" class="form-control" name="fin_years" >
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			
		</table>
			
			<br>
			
					<p style="width:180px;margin-left: 357px;float:centre;">
						<button type="submit" style="float:right;"  class="btn btn-primary">Generate report</button>
					</p>
</div>
</div>

</form>
</div>


