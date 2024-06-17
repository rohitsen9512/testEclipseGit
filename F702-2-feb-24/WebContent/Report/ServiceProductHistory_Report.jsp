<script type="text/javascript">

$(function (){
	DropDownDataDisplay('M_Loc','locDataForLocReport',function (status){
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
	
/* function VF() {
	var numberInput = document.getElementById('warr_amc_day');
    var inputValue = parseFloat(numberInput.value);
	
    	    	
} */

</script>
<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						
					</h1>
				</div>
				<div class="col-sm-6">
					
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	

<form action="ReportView/ServiceProductHistory_Report.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayserviceReport">
	<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
	<h3 class="card-title1">Service History Report</h3>
				</div>
		<table class="table table-bordered ">
			<tr>
				<td ><b>Entity<font color="red">*</font></b></td>
				<td >
					<select id="locDataForLocReport" name="id_loc" class="form-control readbaledata" onChange="SubDropDownDataDisplayForReport(this,'sublocDataForLocReport','M_Subloc');" data-valid="mand">
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
				<td><strong>From Date</strong></td>
				<td><input type="text" name="startdate" class="form-control datepicker" readonly></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td><input type="text" name="enddate" class="form-control datepicker" readonly></td>
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