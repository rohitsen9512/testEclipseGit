<div style="padding:10px;">
	<!--  <a href="#">Master ></a><a href="#"> Country Master</a>-->
</div>
<script type="text/javascript">

$(function() {
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true,
	      altFormat: "DD-MMM-YYYY",
	      
	    });
	
	DisplayData('M_CG','displayCG','createCG');
	$('button[name="update"]').addClass('hideButton');
});

</script>
<div id="displayCG">
	
		<table width="100%" height="100%">
			<tr>
				<td>
					<div class="message">Your update was successful</div>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayCG','createCG','submitCG','M_CG')">Create Country</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover cgdataForDisplay">
						<tr class="success new">
							<td><strong>Country Code</strong></td>
							<td><strong>Country Name</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>

<div id="createCG" style="display:none;">

	<form action="" name="submitCG" id="submitCG">
	
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">CG Details</p></td>
			</tr>
			<tr >
			<td  ><b>CG Date<font color="red">*</font></b></td>
			<td  nowrap><input type="text"  name="dt_cg" class="common-validation datepicker" data-valid="mand" value=""> </td>
			</tr>
			<tr>
			<td  ><b>CG Value<font color="red">*</font></b></td>
			<td ><input type="text"  name="value_cg" class="common-validation" data-valid="mand" value=""></td>
			</tr>
			<tr>
			<td  ><b>B17 Number<font color="red">*</font></b></td>
			<td ><input type="text" name="no_b17_number" class="common-validation" data-valid="mand" value=""></td>
			</tr>
			<tr>
			<td  ><b>B17 Value<font color="red">*</font></b></td>
			<td ><input type="text" name="value_b17" class="common-validation" data-valid="mand" value=""></td>
			</tr>
			<tr>
			<td  ><b>Bank Guarantee Number<font color="red">*</font></b></td>
			<td ><input type="text" name="no_bank_gt_num"  name="bank_no" class="common-validation" data-valid="mand" value=""></td>
			</tr>
			<tr>
			<td  ><b>Bank Guarantee Value<font color="red">*</font></b></td>
			<td ><input type="text" name="value_bank_gt" class="common-validation" data-valid="mand" value=""></td>
			</tr>
			<tr>
			<td  ><b>Expiry Date<font color="red">*</font></b></td>
			<td  nowrap><input type="text" name="dt_expiry"  class="common-validation datepicker" data-valid="mand" value=""></td>
			</tr>
			
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayCG','createCG','submitCG','M_CG','','')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayCG','createCG','submitCG','M_CG','','')">Update</button>
						<button type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayCG','createCG','submitCG','M_CG')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>