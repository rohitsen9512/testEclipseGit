
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Transfer ></a><a href="#">Travel > Travel Print</a>-->
</div>

<script type="text/javascript">

$('#TravelRequestDetails').hide();

$(function() {
	DisplayAssetForTravelPrint();
	$('.datepicker').datepicker({
		yearRange: '1985:2025', 
		changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true
	    });
	
});
</script>

<div class="commonDiv" id="displayAssetForTravelRequestSearch">
	<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForTravelPrint()">
</div>


<div id="DisplayAssetForTravelPrint">
	<table class="table table-bordered DisplayAssetForTravelPrint">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Transfer </strong></td>
		</tr>
	</table>
</div>


<div id="TravelRequestDetails">
	<form name="submitTravelRequest" id="submitTravelRequest">
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Travel Request Details</strong></center></td>
				</tr>
				<tr>
				<td ><b>Asset Id </b></td>
					<td ><input type="text"  name="id_wh_dyn" size="25" value="" readonly ></td>
					<td ><b>Asset Name </b></td>
					<td ><input type= "text" name="ds_pro" size= "25" value="" readonly></td>
					
				</tr>
				<tr>
					
					<td ><b> Employee Name</b></td>
					<td >
					<input type= "text" name="nm_emp" size= "25" value="" readonly>
					<td ><b> Employee ID</b></td>
					<td >
					<input type= "text" name="cd_emp" size= "25" value="" readonly>
					</td>
				</tr>
				
				<tr>
				<td><b>Travel Start Date <font color="red">*</font></b></td>
					<td>
						<input type= "text" name="dt_st_trvl" size= "25" value="" class="common-validation datepicker" data-valid="mand">
					</td>
					<td><b>Expected Return Date <font color="red">*</font></b></td>
					<td>
						<input type= "text" name="dt_retn_trvl" size= "25" value="" class="common-validation datepicker" data-valid="mand">
					</td>
				</tr>
					<tr>
				<td><strong>Remarks</strong></td>
				<td colspan="3">
					<textarea name="remarks" cols="150" rows="2" style="margin: 0px 0px 10px;width: 599px;height: 60px;" value="">
					</textarea>
				</td>
				
			</tr>
				<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="">
				<tr>
					<td colspan="4">
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlTravelPrint('Save','displayAssetForTravelRequestSearch','DisplayAssetForTravelPrint','TravelRequestDetails','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlTravelPrint('Cancel','displayAssetForTravelRequestSearch','DisplayAssetForTravelPrint','TravelRequestDetails','')">Cancel</button>
					</td>
				</tr>
		</table>
	</form>
</div>


