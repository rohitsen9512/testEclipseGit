<!-- <style>
.easy-autocomplete-container {
	max-height: 250px;
	overflow: auto;
	overflow-y: auto;
	overflow-x: hidden;
}-->
</style>

<link rel="stylesheet" href="Autocomp/easy-autocomplete.min.css">
<link rel="stylesheet" href="Autocomp/easy-autocomplete.themes.min.css">
<script type="text/javascript"
		src="All_Js_File/Order/cordinatorlead.js"></script>
<script type ="text/javascript" src="common.js"></script>
<!-- <script type="text/javascript">
	$(function() {
		
		DisplayAssignTome('O_AssignedLlead', 'displayAssigntomeLd', 'EditAssigntomeLd', '','submitAssigntomeLd',
				'ldassignForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
		//DisplayDropDownDataForVendor('M_Vendor','vendorDataForInvoice','procured',function(status) {
					//if (status) {
						DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
																					if (status) {
																						
																						/* DisplayDropDownData('M_Designation','dgnForEmp',function (status){
																							if(status)
																							{ */
																								//
																								//dispalyModifyLineCrItemLead('');
																								/* }
																						}); */
																					}
																				});
																
					
				
	});
		
		
	$(function() {
		//$('#createPrintorMailPurchaseOrder').hide();
		var currentDate = new Date();
		$( ".datepicker" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      
		    });
		$('input[name="dt_to"]').datepicker("setDate", currentDate);
		currentDate.setMonth(currentDate.getMonth() - 1);
		$('input[name="dt_frm"]').datepicker("setDate", currentDate);
		

		
	});
		
/* 	jQuery("input#fileID").change(function () {
		
		 var formData = new FormData();	 
			formData.append('file', $('#fileID').get(0).files[0]);
			
				$.ajax({
				  url: 'Upload_Fil',
				    type: 'POST',
				    data: formData,
				    async: false,
				    cache: false,
				    contentType: false,
				    dataType: "json",
				    processData: false,
				    mimeType: "multipart/form-data",
				    success: function (r) {
				    	
				    		$('input[name="invoice_file"]').val(r.upload_led);
				    		//bootbox.alert("File Uploaded successfully");
				    }
				},'json'); 
				
		//$( "#submitForUploadData" ).trigger( "click" );
				
	});  

</script>
 -->
 <script>
</script>
<!-- <div class="card">
<div id="EditAssigntome" style="display:none;">
	
		<form name="submitAssigntome" id="submitAssigntome">
	
			<div id="createassignTome">
				<div class="card-header new">
					<h3 class="card-title1">Delivery Detail</h3>
				</div>
				<table class="table table-bordered" id="leadCrDetails">
				
				   <tr>
				   <td><strong>Reached Location<font color="red"></font></strong></td>
			     	<td><select name="src" class="form-control" disabled="true" data-valid="mand">
						<option value="Rent">Select</option>
						<option value="Rent">Reached Location</option>
						<option value="Rent">In Process</option>
						<option value="Rent">Delay for one day</option>
					</select></td>
					
					 <td><strong>Reason for visit<font color="red"></font></strong></td>
			     	<td><select name="src" class="form-control" disabled="true" data-valid="mand">
						<option value="Rent">Select</option>
						<option value="Rent">New Installation</option>
						<option value="Rent">Rental Installation</option>
						<option value="Rent">Demonstration</option>
					</select></td>
				
					</tr>
				
					<tr>
				    <td><strong>Date of Installation<font color="red">*</font></strong></td>
				<td><input type="text" name="tag_dt" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata"  data-valid="mand" readonly font-size: 1em;"></td>
			     <td><strong>Rental Expire on<font color="red"></font></strong></td>
			     <td><input type="text" name="tag_dt" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata"  data-valid="mand" readonly font-size: 1em;"></td>
					</tr>
					
					 <tr>
				   <td><strong>Amount Collect<font color="red"></font></strong></td>
			     	<td><select name="src" class="form-control" disabled="true" data-valid="mand">
						<option value="Rent">Select</option>
						<option value="Rent">Yes</option>
						<option value="Rent">No</option>
					</select></td>
					
					 <td><strong>Mode of Payment<font color="red"></font></strong></td>
			     	<td><select name="src" class="form-control" disabled="true" data-valid="mand">
						<option value="Rent">Select</option>
						<option value="Rent">Cash</option>
						<option value="Rent">UPI</option>
						<option value="Rent">GPay</option>
						<option value="Rent">Check</option>
					</select></td>
				
					</tr>
				
		 	<tr>
			    <td><strong>Amount<font color="red"></font></strong></td>
			   <td><input id="pstn_email" class="form-control" type="text" name="pstn_email" readonly data-valid="mand"></td>
					
			    <td><strong>Refundable Amount<font color="red"></font></strong></td>
			  <td><input id="pstn_email" class="form-control" type="text" name="pstn_email" readonly data-valid="mand"></td>
		</tr>		
		
		<tr>
			  	<td><strong>Remark<font color="red"></font></strong></td>
          <td><input id="pstn_email" class="form-control" type="text" name="pstn_email" readonly data-valid="mand"></td>
			    <td><strong>Proof of collection<font color="red"></font></strong></td>
			  <td><input id="pstn_email" class="form-control" type="text" name="pstn_email" readonly data-valid="mand"></td>
			     
			    
			</tr>
	
					
				</table>
				</div>
				
				
				
				</form>
				
				
				</div>
				
				
				</div>
		

 -->
