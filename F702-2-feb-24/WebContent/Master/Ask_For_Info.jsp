<<div style="padding:10px;">
	<!--  <a href="#"> Vendor Details</a>-->
</div>
<script type="text/javascript">

$(function() {
	
	$('button[name="update"]').addClass('hideButton');
	
	DisplayDataForInfo('M_Vendor','displayVendor','createVendor');
	
	
});
function ControlInfo()
{
	alert("Information has been Successfully Sent to Selected Partners");
}
/* $('#nm_vendorId').keyup( function(){
	CheckValWhichAllReadyExit('M_Vendor' , 'Vendor name already exist.' , 'nm_ven');
	});
	
$('#cd_vendorId').keyup( function(){
	CheckValWhichAllReadyExit('M_Vendor' , 'Vendor code already exist.' , 'cd_ven');
	}); */
</script>


	<div id="displayVendor">
		
		<table width="100%" height="100%">
			<tr>
				<td>
				<div  id="displayVendor">
			<input type="text" name="searchWord" value="" placeholder="Search......" onkeyup="DisplayDataApp('M_Vendor','displayVendor','createVendor')">
</div>
					
					<!-- <button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDivApp('Create','displayVendor','createVendor','submitVendor','M_Vendor')">Create Partner</button>
					<button type="button" style="float:left;"  class="btn btn-primary" >Send Link</button>
				 --></td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered vendorForDisplay">
						<tr class="new">
							<td><strong>Vendor Name</strong></td>
							<td><strong>Vendor Code</strong></td>
							<td><strong>Type Of Vendor</strong></td>
							<td><strong>Contact Person Name</strong></td>
							<td><strong>Phone Number</strong></td>
							<td><strong>E-mail Id</strong></td>
							<td><strong>Approve/Reject</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>
	
	
	
	<div id="createVendor" style="display:none;">
	<form name="submitVendor" id="submitVendor">
		<table  class="table table-bordered">
		<tr>
 			<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 420px;">Ask Any Information</p></td>
 		</tr>
		<tr>
				<td><strong>Ask The Information</strong></td>
				<td colspan="3">
					<textarea name="remarks" cols="150" rows="2" style="margin: 0px 0px 10px;width: 599px;height: 60px;" value="">
				
					</textarea>
				</td>
				
			</tr>

		<tr>
			<td  colspan="4">
				<button name="Approve" type="button" style="margin-left:450px;"   class="btn btn-primary" onclick="ControlInfo();">Send</button>
				<!-- <button name="Reject" type="button"   class="btn btn-primary" onclick="ControlDivApp('Reject','displayVendor','createVendor','submitVendor','M_Vendor','' , '')">Reject</button>
				 --><button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDivApp('Cancel','displayVendor','createVendor','submitVendor','M_Vendor');">Back</button>
					
			</td>
		</tr>

		</table>
	</form>
	</div>
	