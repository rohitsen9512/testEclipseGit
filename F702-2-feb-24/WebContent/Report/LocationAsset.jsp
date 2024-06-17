<!--  LoacationAsset.jsp -->



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
		currentDate.setMonth(currentDate.getMonth() - 1);
		$('input[name="startdate"]').datepicker("setDate", currentDate);
		
	});


function HideShowFunForReport(val)
{
if(val.value == 'b')
	{
	$('#hideShowFloor').hide();
	}
else
	{
	$('#hideShowFloor').show();
	}
	
}

$(function (){
	
	/* DisplayDropDownDataForReport('M_Loc','assetDivForLocAssetReport',function (status){
		if(status)
		{
			
		}}); */
		DropDownDataDisplayForReport("M_Division","divDataForlocReport",function (status){
			if(status)
				{
				
				}});
		
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForStoreAssetLReport','CapG',function (status){
		if(status)
		{
			DropDownDataDisplayForReport('M_Loc','locDataForLocReport');
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
						<li class="breadcrumb-item">Master</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>


<form action="ReportView/Location_Asset_report1.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayStoresAsset">
	<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
					<h3 class="card-title1">Master Asset Report</h3>
				</div>
	<table class="table table-bordered ">
	
			<tr>
				<td><b>Select Asset Group</b></td>
				<td>
					<select id="assetDivForStoreAssetLReport" name="id_grp" class="form-control" style="width:140" onChange="DisplaySubDropDownDataForReport(this,'subAssetDivForStoreAssetReport','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>Select Asset Sub Group</b></td>
				<td>
					<select id="subAssetDivForStoreAssetReport"  class="form-control" name="id_sgrp"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td ><b>Location<font color="red">*</font></b></td>
				<td >
					<select id="locDataForLocReport" name="id_loc" class="form-control readbaledata" data-valid="mand" onChange="SubDropDownDataDisplayForReport(this,'sublocDataForLocReport','M_Subloc')"  >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td ><b>Sub Location<font color="red">*</font></b></td>
				<td >
					<select id="sublocDataForLocReport" name="id_subl" class="form-control readbaledata" data-valid="mand" onChange="DisplaySubDropDownDataForReport(this,'buildingDataForLocReport','M_Building')"  >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td ><b>Building<font color="red">*</font></b></td>
				<td >
					<select id="buildingDataForLocReport" name="id_building" class="form-control readbaledata" data-valid="mand" onChange="DisplaySubDropDownDataForReport(this,'floorDataForLocReport','M_Floor')"  >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td ><b>Floor<font color="red">*</font></b></td>
				<td >
					<select id="floorDataForLocReport" name="id_flr" class="form-control readbaledata" data-valid="mand"   >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<!-- <tr>
			<td><b>Select AMC/Warranty<font color="red">*</font></b></td>
				<td>
					<select  name="warr_amc" class="form-control select" required data-valid="mand"  style="width:140">
					<option value = "A" >AMC</option>
                    <option value = "W" >Warranty</option>
						
					</select>
				</td>
			
			<tr> -->
			<!-- <tr>
				<td><strong>Start Date</strong></td>
				<td><input type="text" name="startdate" class="datepicker"></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td ><input type="text" name="enddate" class="datepicker"></td>
			</tr>
		 -->
			<tr>
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 520px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary">Report</button>
			</p>
</div>
</div>
</div>
</form>
</div>
