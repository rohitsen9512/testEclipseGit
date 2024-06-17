<div style="padding:10px;">
	<!-- <a href="#">Organization Master ></a><a href="#"> Geographical Detail > </a><a href="#"> Entity</a> -->
</div>
<script type="text/javascript">

$(function() {
	DisplayData('M_Division','displayDivision','createDivision');
	$('button[name="update"]').addClass('hideButton');
})

/* $('#nm_divisionId').keyup( function(){
	CheckValWhichAllReadyExit('M_Division' , 'Division name already exist.' , 'nm_div');
	});
	
$('#cd_divisionId').keyup( function(){
	CheckValWhichAllReadyExit('M_Division' , 'Division code already exist.' , 'cd_div');
	}); */

</script>

<!-- <div  id="displayDivision">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayData('M_Division','displayDivision','createDivision')">
</div> -->

<div id="displayDivision">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayDivision','createDivision','submitDivision' , 'M_Division')">Create  Entity</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered divisionForDisplay">
						<tr class="new">
							<td><strong> Entity Code</strong></td>
							<td><strong> Entity Name</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createDivision" style="display:none;">
	<form  name="submitDivision" id="submitDivision">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Division Details</p></td>
			</tr>
			
			<tr>
				<td><strong>Entity Code<font color="red">*</font></strong></td>
				<td><input id="cd_divisionId" type="text" name="cd_div" value="" class="common-validation" data-valid="mand"></td>
				
				
			</tr>
			<tr>
				<td><strong>Entity Name<font color="red">*</font></strong></td>
				<td><input id="nm_divisionId" type="text" name="nm_div" value="" class="common-validation" data-valid="mand"></td>
				
				
			</tr>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayDivision','createDivision','submitDivision' , 'M_Division','Division Name all ready exit.,,Division code already exist.' , 'nm_div,,cd_div')">save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayDivision','createDivision','submitDivision','M_Division','Division Name all ready exit.' , 'nm_div')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayDivision','createDivision','submitDivision' , 'M_Division')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>