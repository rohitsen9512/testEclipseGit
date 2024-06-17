<div style="padding:10px;">
		<!--  <a href="#"> Admin> </a><a href="#"> View Group > </a>-->
</div>
<script type="text/javascript">
$(function() {
	
	DisplayAssetDivView('M_Asset_Div','displayGroup','createGroup');
});

/* $('#nm_groupId').keyup( function(){
	CheckValWhichAllReadyExit('M_Asset_Div' , 'Group name already exist.' , 'nm_assetdiv');
	});
	
$('#cd_groupId').keyup( function(){
	CheckValWhichAllReadyExit('M_Asset_Div' , 'Group code already exist.' , 'cd_assetdiv');
	}); */
</script>
<!-- <div  id="displayGroup">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayData('M_Asset_Div','displayGroup','createGroup')">
</div> -->

<div id="displayGroup">
		
		<table width="100%" height="100%">
			<tr>
				<!-- <td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayGroup','createGroup','submitGroup','M_Asset_Div')">Create Group</button>
					</p>
				</td> -->
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover assetForDisplay">
						<tr class="new">
							<td><strong>Group Name</strong></td>
							<td><strong>Group Code</strong></td>
							<td><strong>Group Description</strong></td>
							<td><strong>Modify/elete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createGroup" style="display:none;">
	<form action="" name="submitGroup" id="submitGroup">
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 210px;">Group Details</p></td>
			</tr>
			
			<tr>
				<td><strong>Group Name<font color="red">*</font></strong></td>
				<td><input id="nm_groupId" class="common-validation" type="text" name="nm_assetdiv" data-valid="mand" value=""></td>
			</tr>
			<tr>
				<td><strong>Group Code<font color="red">*</font></strong></td>
				<td><input id="cd_groupId" class="common-validation" type="text" name="cd_assetdiv" data-valid="mand" value=""></td>
				
				
			</tr>
			<tr>
				<td><strong>Group Description</strong></td>
				<td><input class="common-validation" type="text" name="ds_assetdiv" value=""></td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="type" value="CapG">
			</tr>
			
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:230px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayGroup','createGroup','submitGroup','M_Asset_Div','Group name already exist.,,Group code already exist.' , 'nm_assetdiv,,cd_assetdiv')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayGroup','createGroup','submitGroup','M_Asset_Div','' , '')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayGroup','createGroup','submitGroup','M_Asset_Div')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>