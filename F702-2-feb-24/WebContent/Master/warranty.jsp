<div style="padding:10px;">
	<!-- <a href="#">Master ></a><a href="#">Purchase Master > </a><a href="#">Warranty</a> -->
</div>
<script type="text/javascript">

$(function() {
	DisplayData('M_Warranty','displayWarranty','createWarranty');
	$('button[name="update"]').addClass('hideButton');
	
});

/* $('#nm_warrantyId').keyup( function(){
	CheckValWhichAllReadyExit('M_Warranty' , 'Warranty name already exist.' , 'nm_dlvry');
	});
	
$('#cd_warrantyId').keyup( function(){
	CheckValWhichAllReadyExit('M_Warranty' , 'Warranty code already exist.' , 'cd_dlvry');
	}); */

</script>
<div id="displayWarranty">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayWarranty','createWarranty','submitWarranty','M_Warranty')">Create Warranty</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover warrantyForDisplay">
						<tr>
							<td><strong>Delivery Name</strong></td>
							<td><strong>Delivery Symbole</strong></td>
							<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createWarranty" style="display:none;">
	<form action="" name="submitWarranty" id="submitWarranty">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Warranty Details</p></td>
			</tr>
			<tr>
				<td><strong>Warranty Name<font color="red">*</font></strong></td>
				<td><input type="text" id="nm_warrantyId" name="nm_warr" value="" class="common-validation" data-valid="mand"></td>
				
				
			</tr>
			<tr>
				<td><strong>Warranty Code<font color="red">*</font></strong></td>
				<td><input type="text" id="cd_warrantyId" name="cd_warr" value="" class="common-validation" data-valid="mand"></td>
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="0">
			</tr>
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayWarranty','createWarranty','submitWarranty','M_Warranty','','')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayWarranty','createWarranty','submitWarranty','M_Warranty','','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayWarranty','createWarranty','submitWarranty','M_Warranty')">Cancel</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>