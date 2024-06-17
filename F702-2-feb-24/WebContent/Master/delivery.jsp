<div style="padding:10px;">
	<!--  <a href="#">Master ></a><a href="#">Purchase Master > </a><a href="#">Delivery</a>-->
</div>
<script type="text/javascript">

$(function() {
	DisplayData('M_Delivery','displayDelivery','createDelivery');
	$('button[name="update"]').addClass('hideButton');
	
});

/* $('#nm_deliveryId').keyup( function(){
	CheckValWhichAllReadyExit('M_Delivery' , 'Delivery name already exist.' , 'nm_dlvry');
	});
	
$('#cd_deliveryId').keyup( function(){
	CheckValWhichAllReadyExit('M_Delivery' , 'Delivery code already exist.' , 'cd_dlvry');
	}); */

</script>
<div id="displayDelivery">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayDelivery','createDelivery','submitDelivery','M_Delivery')">Create Payment</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover deliveryForDisplay">
						<tr class="new">
							<td><strong>Delivery Name</strong></td>
							<td><strong>Delivery Symbole</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createDelivery" style="display:none;">
	<form action="" name="submitDelivery" id="submitDelivery">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Delivery Details</p></td>
			</tr>
			<tr>
				<td><strong>Payment Name<font color="red">*</font></strong></td>
				<td><input type="text" id="nm_deliveryId" name="nm_dlvry" value="" class="common-validation" data-valid="mand"></td>
				
				
			</tr>
			<tr>
				<td><strong>Payment Code<font color="red">*</font></strong></td>
				<td><input type="text" id="cd_deliveryId" name="cd_dlvry" value="" class="common-validation" data-valid="mand"></td>
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="0">
			</tr>
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayDelivery','createDelivery','submitDelivery','M_Delivery','','')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayDelivery','createDelivery','submitDelivery','M_Delivery','','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayDelivery','createDelivery','submitDelivery','M_Delivery')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>