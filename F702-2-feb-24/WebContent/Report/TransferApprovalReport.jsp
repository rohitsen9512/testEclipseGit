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
	
	/* DisplayDropDownDataForReport('M_Loc','assetDivForTransferReport',function (status){
		if(status)
		{
			
		}}); */
		DropDownDataDisplay("M_Loc","assetDivForTransferReport",function (status){
			if(status)
				{
				
				}});
	DisplayDropDownDataForGroup('M_Asset_Div','assetGrpForTransferReport','CapG',function (status){
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
						<li class="breadcrumb-item">Transfer</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
<div class="card">
<form action="ReportView/TransferApproval_ReportView.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayTransferReport">
<div class="card-body">
	<div class="card-small">
	<div class="card-header new">
					<h3 class="card-title1">Transfer Approval Report</h3>
				</div>
	<table class="table table-bordered ">
	
		<!-- <table class="" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:200px">Transfer Approval Report</p></td>
			</tr> -->
			<!-- <tr>

				<td ><b>Type of Asset<font color="red">*</font></b></td>
				<td >
					<select  name="allocate" class="form-control" data-valid="mand" onChange="HideShowFunForReport(this)">
						<option value="a">Installed Asset</option>
						<option value="b">Store Asset</option>
						
						
					</select>
				</td>
			</tr>
			 -->
			
			<tr>
				<td><b>City Location</b></td>
				<td>
					<select id="assetDivForTransferReport" name="id_loc" class="form-control" style="width:140" onChange="SubDropDownDataDisplayForReport(this,'assetStateForTransferReport','M_Subloc')">
			
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>City Sub Location</b></td>
				<td>
					<select id="assetStateForTransferReport"  name="id_sloc"  class="form-control"style="width:140" onChange="DisplaySubDropDownDataForReport(this,'assetCityForTransferReport','M_Building')">
						<option value="">Select</option>
						
					</select>
					</tr>
					<tr>
				<td><b>Building</b></td>
				<td>
					<select id="assetCityForTransferReport"  name="id_building" class="form-control" style="width:140" onChange="DisplaySubDropDownDataForReport(this,'assetStoreForFloorTransferReport','M_Floor')">
						<option value="">Select</option>
						
					</select>
					</tr>
			<tr id="hideShowFloor">
				
				<td><b>Floor</b></td>
				<td>
					<select id="assetStoreForFloorTransferReport" name="id_flr" class="form-control" style="width:140"> 
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Select Asset Group</b></td>
				<td>
					<select id="assetGrpForTransferReport" name="id_grp" class="form-control" style="width:140" onChange="DisplaySubDropDownDataForReport(this,'subAssetSubGrpForTransferReport','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>Select Asset Sub Group</b></td>
				<td>
					<select id="subAssetSubGrpForTransferReport" name="id_sgrp"class="form-control"  style="width:140">
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
			 <tr>
				<td><strong>Start Date</strong></td>
				<td><input type="text" name="startdate" class="form-control datepicker" readonly></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td ><input type="text" name="enddate" class="form-control datepicker" readonly></td>
			</tr>
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