<!--Install_asset.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Assets ></a><a href="#">Install ></a><a href="#">Edit Install</a>-->
</div>

<script type="text/javascript">

$(function() {
	
	
	
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
	
	$('button[name="update"]').addClass('hideButton');
	
	$('#EditDetailsForInstalledAsset').addClass('hideButton');
	
	
	DisplayDropDownData("M_Loc","locDataForEditInstallAsset",function (status){
	if(status)
		{
		DisplayDropDownDataForGroup('M_Asset_Div','assetDivForEditInstallAsset','CapG',function (status){
			if(status)
			{
			DisplayDropDownData("M_Emp_User","userForInstallEdit",function (status){
				if(status)
				{
				DisplayDropDownData("M_Loc","locationForInstallEdit",function (status){
					if(status)
					{
						DropDownDataDisplay("M_Cost_Center","costCenterForInstallEdit",function (status){
							if(status)
							{
								DisplayInstalledAssetForEdit("A_Install");
								
							}});
						
					}});
				}});	
			}});
		}});
	
	
	
	
	
	
	//DisplayDropDownData('M_Subloc','subLocationForInstallEdit');
	//DisplayDropDownData("M_Floor","floorForInstallEdit");
	//DropDownDataDisplay("M_Cost_Center","costCenterForInstallEdit");
	
	
});
</script>

<div class="commonDiv" id="EditSearch">
	
		<table class="table table-bordered commonTable" align="center" width="600px" height="100%">
			<tr>
				<td>
				<strong>&nbsp;&nbsp;Group</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="assetDivForEditInstallAsset" name="id_grp1" class="select"  style="width:140" onChange="SubDropDownDataDisplay(this,'subassetDivDataForEditInstallAsset','M_Subasset_Div')">
						<option value="">Select</option>
					</select>
					
				&nbsp;&nbsp;<strong>Sub Group</strong>&nbsp;&nbsp;&nbsp;&nbsp;
					<select id="subassetDivDataForEditInstallAsset" name="id_sgrp1" class="select"  style="width:140">
						<option value="">Select</option>
					</select>
					
				&nbsp;&nbsp;<strong>Asset Type</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="typ_asst1"  style="width:140">
						<option value="">Select</option>
						<option value="IT">IT</option>
						<option value="NIT">Non IT</option>
					</select>
					
				&nbsp;&nbsp;<strong>Location</strong>&nbsp;
					<select id="locDataForEditInstallAsset" name="id_loc1" class="select"  style="width:140" onChange="DisplaySubDropDownData(this,'subLocDataForEditInstallAsset','M_Subloc')">
						<option value="">Select</option>
					</select>
					
				&nbsp;&nbsp;<strong>Sub Location</strong>
					<select id="subLocDataForEditInstallAsset" name="id_subl1" class="select"  style="width:140">
						<option value="">Select</option>
					</select>
					<button type="button" style="margin-left:93px;margin-top: -12px;"  class="btn btn-primary" onclick="DisplayInstalledAssetForEdit('A_Install')">Search</button>
					
				</td>
			</tr>
		</table>
			
</div>


<div id="DisplayInstalledAssetDiv">
	<table class="table table-bordered displayInstalledAssetForEdit">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial No.</strong></td>
			<td><strong>Model Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Edit UnInstall</strong></td>
		</tr>
	</table>
</div>

<div id="EditDetailsForInstalledAsset">
	<form name="EditInstalledAsset" id="EditInstalledAsset">
	<table class="table table-bordered">
			<tr class="tableHeader tableHeaderContent">
				<td colspan="4"><center><strong>Asset  Details</strong></center></td>
			</tr>
				<tr>
					<td ><b>Ware House Id</b></td>
					<td ><input type= "text"  name="id_wh_dyn"   size= "25" value="" readonly class="common-validation" data-valid="mand"></td>
					<td ><b>Asset Name</b></td>
					<td ><input type= "text" name="ds_pro" size= "25" value="" readonly class="common-validation"></td>
				</tr>
				<tr>
					<td ><b>Serial Number</b></td>
					<td ><input type="text"  name="no_mfr" size="25" value="" readonly class="common-validation"></td>
					<td ><b>Manufacturer</b></td>
					<td ><input type="text"  name="mfr" size="25" value="" readonly class="common-validation"></td>
				</tr>
				<tr>
					<td ><b>Asset Remarks</b></td>
					<td ><textarea rows="2" name="rmk_asst" cols="17"  style="width:136" value =""  class="common-validation"></textarea></td>
					<td ><b>Asset Description </b></td>
					<td ><textarea rows="2" name="ds_asst" cols="17"  style="width:136"   value =""  readonly class="common-validation"></textarea></td>
				</tr>
				<tr>
					<td><b>Allocation Date<font color = 'Red'>*</font></b></td>
					<td><input type="text" id="allocatIdForEdit"  name=  "dt_alloc" value="" size="21" class="common-validation" data-valid="mand" readonly></td>
					<td><b>Assign To<font color = 'Red'>*</font></b></td>
					<td> <select id="userForInstallEdit" name = "to_assign" class="common-validation"  data-valid="mand">
						<option value = "" >Select</option>
					</select></td>			
				</tr>
				<tr>

				<td><b>Location</b></td>
				<td>
					<select id="locationForInstallEdit" name = "id_loc" class="common-validation"  data-valid="mand" onChange="DisplaySubDropDownData(this,'subLocationForInstallEdit','M_Subloc')">
							<option value = "" >Select</option>
					</select>
				</td>
				<td><b>Sub Location</b></td>
				<td>
					<select id="subLocationForInstallEdit" name = "id_subl" class="common-validation"  data-valid="mand"  onChange="DisplaySubDropDownData(this,'floorForInstallEdit','M_Floor')">
							<option value = "" >Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Facilities <font color = 'Red'>*</font></b></td>
				<td>
					<select id="floorForInstallEdit" name = "id_flr"  style="width:140" class="common-validation"  data-valid="mand" >
						<option value = "" >Select</option>
					</select>
				</td>
				
				<td><b>Cost Center <font color = 'Red'>*</font></b></td>
				<td><select id="costCenterForInstallEdit" name = "id_cc"  style="width:140" class="common-validation"  data-valid="mand" >
									<option value = "" >Select</option>
				</select></td> 
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="Edit" value="Edit">
				<input type="hidden" name="id" value="">
				<input type="hidden" name="id_grn" value=""> 
				
			</tr>
			<tr>
				<td colspan="4">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlInstallAsset('Update','EditSearch','DisplayInstalledAssetDiv','EditDetailsForInstalledAsset','')">Update</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlInstallAsset('Cancel','EditSearch','DisplayInstalledAssetDiv','EditDetailsForInstalledAsset','')">Cancel</button>
				</td>
			</tr>
	</table>
</form>
</div>

















