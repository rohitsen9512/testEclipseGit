<!--Consolidated(ca).jsp-->



<script type="text/javascript">
$(function (){
	
	DropdownResultForFinanceYear('financialYearDataForConslidatedITReport');
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
						<li class="breadcrumb-item">Consolidated IT</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

<div class="commonDiv" id="displayConsolidatedCA">
<form action="ReportView/consolidated_it_reports.jsp" method="post" target="_new">
<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
					<h3 class="card-title1">Select Financial Year</h3>
				</div>
	<table class="table table-bordered ">		
		<!-- <table class="commonTable" align="center" width="600px" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 163px;">Select Financial Year</p></td>
			</tr> -->
			<tr>
				<td><strong>Select Financial Year</strong></td>
				<td >
					<select name="fin_years"class="form-control" id="financialYearDataForConslidatedITReport" required>
						<option> value="select">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<p style="width:180px;margin-left: 159px;float:centre;">
					<button type="submit" style="float:right;"  class="btn btn-primary" >Generate Report</button>
					</p>
				</td>
			</tr>
		</table>
</form>
</div>

<div class="commonDiv" id="createConsolidatedCA" style="display:none;">
	<form action="" name="submitConsolidatedCA" >	
		<table class="commonTable" align="center" width="600px" border="1">
			<tr>
			<td class="tableHeader" colspan="12"><p class="tableHeaderContent" style="margin-left: 163px;">Consolidated Total For The Year  To</p> </td>
			</tr>
			<tr>
				<td nowrap rowspan="3">					</td>
				<td nowrap colspan="5">	Asset Value(RS)	</td>
				<td nowrap colspan="5" >	Depreciation	</td>
				<td nowrap rowspan="3" >	WDV				</td>
			</tr>
			<tr>
				<td nowrap rowspan="2">	Opening Blance	</td>
				<td nowrap rowspan="2">	Addition		</td>
				<td nowrap rowspan="2">	Deletion		</td>
				<td nowrap rowspan="2">	Written off		</td>
				<td nowrap rowspan="2">	Total			</td>
				<td nowrap rowspan="2">	As on 			</td>
				<td nowrap colspan="4">	For the period </td>
			</tr>
			<tr >
				<td nowrap >	Opening		</td>
				<td nowrap >	Additions	</td>
				<td nowrap >	Deductions	</td>
				<td nowrap >	Total		</td>
			</tr>
		</table>
			
			<br>
			
				
					<p style="width:180px;margin-left: 222px;float:center;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','createConsolidatedCA','displayExcelConsolidatedCA','')">Export to excel</button>
					</p>
				
					<p style="width:180px;margin-left: 381px;float:center;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','createConsolidatedCA','displayPrintConsolidatedCA','')">Print</button>
					</p>
			</div>
			</div>	
			
	</form>
</div>
