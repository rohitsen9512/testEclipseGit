
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Asset ></a><a href="#">Asset Maintenance </a>-->
</div>
<script type ="text/javascript" src="All_Js_File/Asset/Asset_Maintenance.js"></script>
<script type="text/javascript">

$(function() {
	
	$('#createAssetMaintenance').hide();
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForMaintenance','CapG',function (status){
		if(status)
		{
			
		}});
	
	
	
	$( ".datepicker" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
	//$('input[name="dt_to"]').datepicker("setDate", currentDate);
});



</script>
<center>
<div class="commonDiv" id="displayAssetMaintenance">
	
	<form action="" name="submitDisplayAssetMaintenance" id="submitDisplayAssetMaintenance">
		<input type="hidden" name="action" value="Display">
		<table class="commonTable table table-bordered" style="width:600px;">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:179px">Select Asset For Maintenance</p></td>
			</tr>
			<tr>
				<td  style="text-align: right;"><b>Asset Group<font color="red">*</font></b></td>
				<td  style="text-align: left;">
					<select id="assetDivForMaintenance" name="id_grp" class="common-validation" data-valid="mand" style="width:140" onChange="SubDropDownDataDisplay(this,'subGroupDataForMaintenance','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td  style="text-align: right;"><b>Asset SubGroup<font color="red">*</font></b></td>
				<td  style="text-align: left;">
					<select id="subGroupDataForMaintenance" name="id_sgrp" class="common-validation" data-valid="mand" style="width:140" onChange="GetDynamicAssetId('assetIdForAssetMaintenance','submitDisplayAssetMaintenance')">
						<option value="">Select</option>
						
					</select>
					<input type="hidden" name="action" value="DropDownResult">
				</td>
			</tr>
			<tr>
				<td  style="text-align: right;"><b>Select Asset ID<font color="red">*</font></b></td>
				<td  style="text-align: left;">
					<select name="id_wh"  style="width:140" id="assetIdForAssetMaintenance" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
			<td colspan="2">
			
					<button type="button" style="float:center;margin-left:300px;"  class="btn btn-primary" onclick="ControlAssetMaintenance('Next','createAssetMaintenance','displayAssetMaintenance')">Next</button>
				
			</td>
			</tr>
	  </table>
	</form>	
	
</div>


<div class="commonDiv" id="createAssetMaintenance" style="display:none;">
	<form action="" name="submitAssetMaintenance" id="submitAssetMaintenance">	
	
	<input type="hidden" name="action" value="Save">
	<input type="hidden" name="id_wh" value="">
	
	
	
		<table class="commonTable table table-bordered" align="center" width="800px" height="100%">
			<tr>
				<td colspan="8" class="tableHeader"><center><p class="tableHeaderContent">Maintenance Details</p></center></td>
			</tr>
			<tr>
				<td><strong>Asset ID</strong></td>
				<td><input type="text" name="id_wh_dyn" value="" readonly></td>
				<td ><strong>Asset Name</strong></td>
				<td><input type="text" name="ds_pro" value="" readonly></td>
			</tr>
			<tr>
				<td><strong>Asset Description</strong></td>
				<td><input type="text" name="ds_asst" value="" readonly></td>
				<td><b>Maintenance Date</b><font color="#FF0000">*</font></td>
				<td><input type="text" name="dt_mntnc" size="15" class="common-validation datepicker" data-valid="mand"></td>
			</tr>
			<tr>
				<td><b>Maintenance Remarks</b><font color="#FF0000">*</font></td>
				<td colspan="3">
				<textarea cols ="100" rows="3" name ="mntnc_remk" class="common-validation" data-valid="mand" style="width: 581px;height: 83px;"></textarea>
				</td>
				
			</tr>
		
			<tr>
				<td colspan="4">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlAssetMaintenance('Save','displayAssetMaintenance','createAssetMaintenance')">Save</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlAssetMaintenance('Back','displayAssetMaintenance','createAssetMaintenance')">Back</button>
				</td>
			</tr>
	  </table>
	</form>
</div>
</center>	