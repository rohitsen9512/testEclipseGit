<!--  Installed_asset.jsp -->


<script type="text/javascript">

$(function (){
	
	//DisplayDropDownDataForReport('M_Emp_User','EmplyeeAssetDetailReport');
});

$(function (){
	
	DropDownDataDisplayForReport('M_Loc','locDataForLocReport',function (status){
		if(status)
			{
			
			}});
});

$(function(){
	var currentDate = new Date();
		
		$('.datepicker').each(function () {
	        if ($(this).hasClass('hasDatepicker')) {
	            $(this).removeClass('hasDatepicker');
	        } 
	         $(this).datepicker({
	        	 yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		    });
	    });
		
		$('input[name="enddate"]').datepicker("setDate", currentDate);
		currentDate.setMonth(currentDate.getMonth() - 1);
		$('input[name="startdate"]').datepicker("setDate", currentDate);
		
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
	

<form action="ReportView/PayModeWise_Report.jsp" method="POST" target="_new">

<div class="commonDiv" id="displaySourceReport">
	<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
	<h3 class="card-title1">Payment Mode Wise Report</h3>
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
			<tr>
				<td><b>Select the Mode<font color="red">*</font></b></td>
				<td>
					<select name="pay_mode" class="form-control"  data-valid="mand">
						<option value="Due">Payment Due</option>
						<option value="FOC">FOC</option>
						<option value="Cash">Cash</option>
						<option value="UPI">UPI/Online</option>
						<option value="Credit">Credit</option>
						<option value="Cheque">Cheque</option>
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