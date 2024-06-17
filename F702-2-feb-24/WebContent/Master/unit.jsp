<div style="padding:10px;">
	<!--  <a href="#">Master ></a><a href="#">Unit Master > </a>-->
</div>
<script type="text/javascript">

$(function() {
	DisplayData('M_Unit','displayUnit','createUnit');
	$('button[name="update"]').addClass('hideButton');
	
});

/* $('#nm_UnitId').keyup( function(){
	CheckValWhichAllReadyExit('M_Unit' , 'Unit name already exist.' , 'nm_unit');
	});
	
$('#cd_UnitId').keyup( function(){
	CheckValWhichAllReadyExit('M_Unit' , 'Unit code already exist.' , 'cd_unit');
	}); */

</script>
<div id="displayUnit">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayUnit','createUnit','submitUnit','M_Unit')">Create Currency</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover unitForDisplay">
						<tr class="new">
							<td><strong>Currency Name</strong></td>
							<td><strong>Currency Symbole</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createUnit" style="display:none;">
	<form action="" name="submitUnit" id="submitUnit">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Currency Details</p></td>
			</tr>
			<tr>
				<td><strong>Unit Name<font color="red">*</font></strong></td>
				<td><input type="text" id="nm_UnitId" name="nm_unit" value="" class="common-validation" data-valid="mand"></td>
				
				
			</tr>
			<tr>
				<td><strong>Unit Symbol<font color="red">*</font></strong></td>
				<td><input type="text" id="cd_UnitId" name="cd_unit" value="" class="common-validation" data-valid="mand"></td>
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="0">
			</tr>
			<tr>
				<td><strong>Gate No<font color="red">*</font></strong></td>
				<td><input type="text"  name="no_gate" value="" class="common-validation" data-valid="mand"></td>
				
				
			</tr>
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayUnit','createUnit','submitUnit','M_Unit','','')">save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayUnit','createUnit','submitUnit','M_Unit','','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayUnit','createUnit','submitUnit','M_Unit')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>