<div style="padding:10px;">
	<!-- <a href="#">Organization Master > </a><a href="#"> Asset Classification > </a><a href="#">Sub Group</a> -->
</div>
<script type="text/javascript">

$(function() {
	DisplaySubassetDivView('M_Subasset_Div','displaySubGroup','createSubGroup');
	
	/* DisplayDropDownDataForGroup("M_Asset_Div","assetDivForSubassetDiv","CapG",function (status){
		if(status)
			{
			
			}}); */
	
});

/* $('#nm_subgroupId').keyup( function(){
	CheckValWhichAllReadyExit('M_Subasset_Div' , 'Subgroup name all ready exit.' , 'nm_s_assetdiv');
	});
	
$('#cd_subgroupId').keyup( function(){
	CheckValWhichAllReadyExit('M_Subasset_Div' , 'Subgroup code all ready exit.' , 'cd_s_assetdiv');
	}); */
	
</script>


<div id="displaySubGroup">
		
		<table width="100%" height="100%">
			<tr>
				<td>
				<div  id="displaySubGroup">
	<input type="text" name="searchWord" value="" placeholder="Search......" onkeyup="DisplayData('M_Subasset_Div','displaySubGroup','createSubGroup')">
</div>
					<!-- <p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displaySubGroup','createSubGroup','submitSubGroup','M_Subasset_Div')">Create Sub Group</button>
					</p> -->
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover subassetForDisplay">
						<tr class="new">
							
							<td><strong>Group Name</strong></td>
							<td><strong>Sub Group Name</strong></td>
							<td><strong>Sub Group Code</strong></td>
							
							<td><strong>Modify / Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createSubGroup" style="display:none;">
	<form action="" name="submitSubGroup" id="submitSubGroup">
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 200px;">Sub Group Details</p></td>
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
				<td><input id="nm_subgroupId" type="text" name="nm_s_assetdiv" class="common-validation" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Sub Group Code<font color="red">*</font></strong></td>
				<td><input id="cd_subgroupId" type="text" name="cd_s_assetdiv" class="common-validation" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Asset Prefix <font color="red">*</font></strong></td>
				<td><input type="text" name="pre_asset" class="common-validation" data-valid="mand"></td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="type" value="CapG">
				
			</tr>
			
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:230px;"   class="btn btn-primary" onclick="ControlDiv('Save','displaySubGroup','createSubGroup','submitSubGroup','M_Subasset_Div','' , '')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displaySubGroup','createSubGroup','submitSubGroup','M_Subasset_Div','' , '')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displaySubGroup','createSubGroup','submitSubGroup','M_Subasset_Div')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>