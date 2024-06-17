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
		DropDownDataDisplay("M_Division","assetDivisionForTransferReport11",function (status){
			if(status)
				{
				
				}});
	DisplayDropDownDataForGroup('M_Asset_Div','assetGrpForTransferReport11','CapG',function (status){
		if(status)
		{
			
		}});	
	
});

</script>
<form action="ReportView/Transfer_Report.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayTransferReport1">
	
		<table class="" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:200px">Transfer Report</p></td>
			</tr>
			<!-- <tr>

				<td ><b>Type of Asset<font color="red"> * </font>:</b></td>
				<td >
					<select  name="allocate" class="common-validation" data-valid="mand" onChange="HideShowFunForReport(this)">
						<option value="a">Installed Asset</option>
						<option value="b">Store Asset</option>
						
						
					</select>
				</td>
			</tr>
			 -->
			<tr>
				<td><b>Select Division</b></td>
				<td>
					<select id="assetDivisionForTransferReport11" name="id_div"  style="width:140" onChange="DisplaySubDropDownData(this,'assetDivForTransferReport','M_Loc')">
			<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Select Region</b></td>
				<td>
					<select id="assetDivForTransferReport" name="id_loc"  style="width:140" onChange="SubDropDownDataDisplayForReport(this,'assetStateForTransferReport','M_Subloc')">
			<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>Select State</b></td>
				<td>
					<select id="assetStateForTransferReport"  name="id_sloc"  style="width:140" onChange="DisplaySubDropDownDataForReport(this,'assetCityForTransferReport','M_Building')">
						<option value="">Select</option>
						
					</select>
					</tr>
					<tr>
				<td><b>Select City</b></td>
				<td>
					<select id="assetCityForTransferReport"  name="id_building"  style="width:140" onChange="DisplaySubDropDownDataForReport(this,'assetStoreForFloorTransferReport','M_Floor')">
						<option value="">Select</option>
						
					</select>
					</tr>
			<tr id="hideShowFloor">
				
				<td><b>Select Store</b></td>
				<td>
					<select id="assetStoreForFloorTransferReport" name="id_flr"  style="width:140"> 
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Select Asset Group</b></td>
				<td>
					<select id="assetGrpForTransferReport11" name="id_grp"  style="width:140" onChange="DisplaySubDropDownDataForReport(this,'subAssetSubGrpForTransferReport','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>Select Asset Sub Group</b></td>
				<td>
					<select id="subAssetSubGrpForTransferReport" name="id_sgrp"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<!-- <tr>
			<td><b>Select AMC/Warranty<font color="red"> * </font>:</b></td>
				<td>
					<select  name="warr_amc" class="common-validation select" required data-valid="mand"  style="width:140">
					<option value = "A" >AMC</option>
                    <option value = "W" >Warranty</option>
						
					</select>
				</td>
			
			<tr> -->
			 <tr>
				<td><strong>Start Date</strong></td>
				<td><input type="text" name="startdate" class="datepicker" readonly></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td ><input type="text" name="enddate" class="datepicker" readonly></td>
			</tr>
			<tr>
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 520px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary">Report</button>
			</p>
</div>
</form>
