<div style="padding:10px;">
	<!-- <a href="#">Master > </a><a href="#">Consumable Group > </a><a href="#"> Consumable Sub Group</a> -->
</div>
<script type="text/javascript">

$(function() {
	DisplayData('M_Consumable_Sub_Div','displayConsumableSubGroup','createConsumableSubGroup');
	
	DisplayDropDownDataForGroup("M_Asset_Div","assetDivForSubassetDiv","ConG",function (status){
		if(status)
			{
			
			}});
	
});

/* $('#nm_ConSubGrpId').keyup( function(){
	CheckValWhichAllReadyExit('M_Consumable_Sub_Div' , 'Subgroup name all ready exit.' , 'nm_s_assetdiv');
	});
	
$('#cd_ConSubGrpId').keyup( function(){
	CheckValWhichAllReadyExit('M_Consumable_Sub_Div' , 'Subgroup code all ready exit.' , 'cd_s_assetdiv');
	}); */
	
</script>
<div id="displayConsumableSubGroup">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayConsumableSubGroup','createConsumableSubGroup','submitConsumableSubGroup','M_Consumable_Sub_Div')">Create Sub Group</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover subConsumableForDisplay">
						<tr class="new">
							
							<td><strong>Group Name</strong></td>
							<td><strong>Sub Group Name</strong></td>
							<td><strong>Sub Group Code</strong></td>
							
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createConsumableSubGroup" style="display:none;">
	<form action="" name="submitConsumableSubGroup" id="submitConsumableSubGroup">
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Sub Group Details</p></td>
			</tr>
			
			<tr>
				<td><strong>Group Name<font color="red">*</font></strong></td>
				<td>
					<select name="id_assetdiv" autofocus="" id="assetDivForSubassetDiv" class="common-validation" data-valid="mand">
							<option value="">Select</option>
							
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Sub Group Name<font color="red">*</font></strong></td>
				<td><input id="nm_ConSubGrpId" type="text" name="nm_s_assetdiv" class="common-validation" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Sub Group Code<font color="red">*</font></strong></td>
				<td><input id="cd_ConSubGrpId" type="text" name="cd_s_assetdiv" class="common-validation" data-valid="mand"></td>
			</tr>
			<!-- <tr>
				<td><strong>Asset Prefix</strong></td>
				<td><input type="text" name="pre_asset" class="common-validation"></td>
				
			</tr> -->
			<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayConsumableSubGroup','createConsumableSubGroup','submitConsumableSubGroup','M_Consumable_Sub_Div','Subgroup Name all ready exit.,,Subgroup code already exist.' , 'nm_s_assetdiv,,cd_s_assetdiv')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayConsumableSubGroup','createConsumableSubGroup','submitConsumableSubGroup','M_Consumable_Sub_Div','Subgroup Name all ready exit.' , 'nm_s_assetdiv')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayConsumableSubGroup','createConsumableSubGroup','submitConsumableSubGroup','M_Consumable_Sub_Div')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>