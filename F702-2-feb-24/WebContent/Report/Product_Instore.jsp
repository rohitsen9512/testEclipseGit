<!--  Rental_asset.jsp -->


<script type="text/javascript">

$(function (){
	
	DropDownDataDisplayForReport('M_Loc','locDataForLocReport',function (status){
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
						<!--  Report -->
						<!-- <button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displayLocation','createLocation','submitLocation','M_Loc')">Create
							Location</button> -->
					</h1>
				</div>
				<div class="col-sm-6">
					
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	

<form action="ReportView/Product_Instore_poduct.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayRentProductReport">
	<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
	<h3 class="card-title1">Total Product on Store Report</h3>
				</div>
		<table class="table table-bordered ">
			<!-- <tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Asset Status Report</p></td>
			</tr> -->
			<tr>
				<td ><b>Entity<font color="red">*</font></b></td>
				<td >
					<select id="locDataForLocReport" name="id_loc" class="form-control readbaledata" data-valid="mand" onChange="SubDropDownDataDisplayForReport(this,'sublocDataForLocReport','M_Subloc')"  >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td ><b>City<font color="red">*</font></b></td>
				<td >
					<select id="sublocDataForLocReport" name="id_sloc" class="form-control readbaledata" data-valid="mand">
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
</form>