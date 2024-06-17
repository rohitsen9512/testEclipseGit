<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script type="text/javascript">
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
	currentDate.setMonth(currentDate.getMonth() );
	$('input[name="startdate"]').datepicker("setDate", currentDate);
	
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForAssetHReport','CapG',function (status){
		if(status)
		{
			
		}});
	
	
	
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
					<!--  	Report-->
						<!-- <button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displayLocation','createLocation','submitLocation','M_Loc')">Create
							Location</button> -->
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Reports</a></li>
						<li class="breadcrumb-item">Accessory</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	



<div id="AssetHistoryReport">
<form action="ReportView/AccessoryHistoryReport.jsp" method="post" target="_new">
<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
					<h3 class="card-title1">Select for Accessory History Detail</h3>
				</div>
	<table class="table table-bordered ">	
		<!-- <table align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 120px;">Select for Accessory History Detail</p></td>
			</tr> -->
			
			<tr>
				<td><b>Select the Asset Group<font color="red">*</font></b></td>
				<td>
					<select id="assetDivForAssetHReport" name="id_grp" class="form-control select2search"  style="width:140" onChange="DisplaySubDropDownDataForReport(this,'subAssetDivForAccessoryHReport','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>Select the Asset Sub Group</b></td>
				<td>
					<select id="subAssetDivForAccessoryHReport" name="id_sgrp" class="form-control select2search"  style="width:140" onChange="SubDropDownDataDisplayForReport(this,'ModelDivForAccessoryHReport','M_Model')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>	
			
			<tr>
				<td><b>Select the Model</b></td>
				<td>
					<select id="ModelDivForAccessoryHReport"class="form-control select2search"  name="id_model"  style="width:140" >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>			
			<tr>
				<td colspan="4" align="center">
					<p style="margin-left: 250px;">
						<button type="submit"   class="btn btn-primary" onclick="return AssetHistDate()">Preview</button>
					</p>
				</td>
		  </tr>
	</table>
	</div>
	</div>
</form>
</div>
