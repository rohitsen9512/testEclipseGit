<div style="padding:10px;color:blue;">
	<!--  Master > Terms & Conditions-->
</div>

<script type="text/javascript">

$(function() {
	DisplayData('M_Terms_Conditions','displaytermsconditions','createTermsConditions');
	$('button[name="update"]').addClass('hideButton');
});

jQuery("input#file4ID").change(function () {
	
	 var formData = new FormData();	 
		formData.append('file', $('#file4ID').get(0).files[0]);
		
			$.ajax({
			  url: 'Upload_File',
			    type: 'POST',
			    data: formData,
			    async: false,
			    cache: false,
			    contentType: false,
			    dataType: "json",
			    processData: false,
			    mimeType: "multipart/form-data",
			    success: function (r) {
			    	
			    		$('input[name="file_name"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
			
	//$( "#submitForUploadData" ).trigger( "click" );
}); 

</script>

<div id="displaytermsconditions">
		<table width="100%" height="100%">
			<tr>
				<td>
					<div class="message">Your update was successful.</div>
					<p style="width:250px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displaytermsconditions','createTermsConditions','submitTermsConditions','M_Terms_Conditions')">Create Terms & Condition</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover termsconditionsForDisplay">
						<tr class="success new">
							<!-- <td><strong>Order Number</strong></td> -->
							<td><strong>Term & Condition Name</strong></td>
							<td><strong>File Name</strong></td>
							<!-- <td><strong>PO Terms</strong></td>
							<td><strong>Quot Terms</strong></td>
							<td><strong>Consumable PO Terms</strong></td>
							<td><strong>Service PO Terms</strong></td> -->
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>

<div id="createTermsConditions" style="display:none;">
	<form action="" name="submitTermsConditions" id="submitTermsConditions">	
		<table align="center" width="100%" border="1">
			<tr>
				<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 400px;">TC Details</p></td>
			</tr>
			<tr>
				<!-- <td><strong>Order Number<font color="red">*</font></strong></td>
				<td><input id="no_ordId" class="common-validation" type="text" name="no_ord" data-valid="mand" value=""></td> -->
				
				<td><strong>Terms & Conditions<font color="red">*</font></strong></td>
				<td><input id="nm_tcId" class="common-validation" data-valid="mand" type="text" name="t_and_c"  value=""></td>
				<td><b>Upload File<font color="red">*</font></b></td>
				<td><input id="file4ID" type="file" name="file4" class="common-validation" data-valid="mand" value="" ></td>
			</tr>
			<!-- <tr>
				<td><strong>PO Terms</strong></td>
				<td><input id="po_tcId" class="common-validation" type="checkbox" name="po_tc" value="yes"></td>
				<td><strong>Quot Terms</strong></td>
				<td><input id="quo_tcId" class="common-validation" type="checkbox" name="quo_tc"  value="yes"></td>
			</tr>
			<tr>
				<td><strong>Consumable PO Terms</strong></td>
				<td><input id="cpo_tcId" class="common-validation" type="checkbox" name="cpo_tc" value="yes"></td>
				<td colspan="2"></td>
				<td><strong>Service PO Terms</strong></td>
				<td><input id="spo_tcId" class="common-validation" type="checkbox" name="spo_tc"  value="yes"></td>
			</tr> -->
			
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="file_name" value="" class="common-validation">
			<tr>
				<td colspan="4">
						<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlDiv('Save','displaytermsconditions','createTermsConditions','submitTermsConditions','M_Terms_Conditions','','','','')">Save</button>
						<button name="update" type="button" style="margin-left:400px;" class="btn btn-primary" onclick="ControlDiv('Update','displaytermsconditions','createTermsConditions','submitTermsConditions','M_Terms_Conditions','' , '')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displaytermsconditions','createTermsConditions','submitTermsConditions','M_Terms_Conditions','' , '')">Back</button>
				</td>
			</tr>
		</table>
	</form>
	</div>