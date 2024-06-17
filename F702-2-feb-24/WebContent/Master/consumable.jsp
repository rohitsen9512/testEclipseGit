<div style="padding:10px;">
	<!--  <a href="#">Master > </a><a href="#"> Group > </a><a href="#">Create Consumable Group</a>-->
</div>
<script type="text/javascript">
$(function() {
	
	DisplayData('M_Consumable_Div','displayConsumable','createConsumable');
});

/* $('#nm_consumableId').keyup( function(){
	CheckValWhichAllReadyExit('M_Consumable_Div' , 'Consumable name already exist.' , 'nm_assetdiv');
	});
	
$('#cd_consumableId').keyup( function(){
	CheckValWhichAllReadyExit('M_Consumable_Div' , 'Consumable code already exist.' , 'cd_assetdiv');
	}); */
</script>
<div id="displayConsumable">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayConsumable','createConsumable','submitConsumable','M_Consumable_Div')">Create Group</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover ConsumableForDisplay">
						<tr class="new">
							<td><strong>Group Name</strong></td>
							<td><strong>Group Code</strong></td>
							<td><strong>Group Description</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createConsumable" style="display:none;">
	<form action="" name="submitConsumable" id="submitConsumable">
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Group Details</p></td>
			</tr>
			
			<tr>
				<td><strong>Group Name<font color="red">*</font></strong></td>
				<td><input id="nm_consumableId" class="common-validation" type="text" name="nm_assetdiv" data-valid="mand" value=""></td>
			</tr>
			<tr>
				<td><strong>Group code<font color="red">*</font></strong></td>
				<td><input id="cd_consumableId" class="common-validation" type="text" name="cd_assetdiv" data-valid="mand" value=""></td>
				
				
			</tr>
			<tr>
				<td><strong>Group Description</strong></td>
				<td><input class="common-validation" type="text" name="ds_assetdiv" value=""></td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="type_grp" value="ConG">
			</tr>
			
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayConsumable','createConsumable','submitConsumable','M_Consumable_Div','Consumable Name all ready exit.,,Consumable code already exist.' , 'nm_cons_div,,cd_cons_div')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayConsumable','createConsumable','submitConsumable','M_Consumable_Div','' , '')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayConsumable','createConsumable','submitConsumable','M_Consumable_Div')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>