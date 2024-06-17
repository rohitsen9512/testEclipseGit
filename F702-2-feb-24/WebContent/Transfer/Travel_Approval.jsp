<!--Intraunit_transfer_approval.jsp-->

<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#"> Transfer > Travel > </a><a href="#">Travel Approval</a>-->
</div>
<script type="text/javascript">

$(function() {
	
	$('#AssetForIntraUnitTravelApprovalView').hide();
	DisplayAssetForTravelAcceptReject();
});
</script>

<div class="commonDiv" id="displayAssetForTravelApproval">
	<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForTravelAcceptReject()">
		
		<table class="table table-bordered displayAssetForTravelApprovalClass">
						<tr class="new">
							<td><strong>Asset ID</strong></td>
							<td><strong>Asset Name</strong></td>
							<td><strong>Serial Number</strong></td>
							<td><strong>Asset Description</strong></td>
							<td><strong>Approve/Reject</strong></td>
						</tr>
					</table>
			
</div>
	
	
	
	
<div id="AssetForIntraUnitTravelApprovalView" class="commonDiv">
		
	<form action="" name="submitAssetForTravelApproval" id="submitAssetForTravelApproval">	
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Travel Request Details</strong></center></td>
				</tr>
				<tr>
				<td ><b>Asset Id</b></td>
					<td ><input type="text"  name="id_wh_dyn" size="25" value="" readonly ></td>
					<td ><b>Asset Name</b></td>
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
						<input type= "text" name="dt_st_trvl" size= "25" value="" class="common-validation" data-valid="mand" readonly>
					</td>
					<td><b>Expected Return Date <font color="red">*</font></b></td>
					<td>
						<input type= "text" name="dt_expc_trvl" size= "25" value="" class="common-validation" data-valid="mand" readonly>
					</td>
				</tr>
					<tr>
					<td><strong>Requested Remarks</strong></td>
					<td>
					<textarea name="remarks" cols="150" rows="2" style="margin: 0px 0px 10px;" value="" readonly>
					</textarea>
					</td>
				<td><strong>Remarks</strong></td>
				<td>
					<textarea name="aprvl_remarks" cols="150" rows="2" style="margin: 0px 0px 10px;" value="">
					</textarea>
				</td>
				<input type="hidden" name="id" value="">
				<input type="hidden" name="id_wh" value="">
			</tr>
				<td colspan="4">
			
					<p style="margin-left: 302px;">
						<button name="save" type="button"  class="btn btn-primary TrvlApprUTBTN" onclick="ControlAccepRejectTravelDiv('Accepted','AssetForIntraUnitTravelApprovalView','displayAssetForTravelApproval','submitAssetForTravelApproval','')">Accept</button>
						<button name="save" type="button"  class="btn btn-primary TrvlApprUTBTN" onclick="ControlAccepRejectTravelDiv('Rejected','AssetForIntraUnitTravelApprovalView','displayAssetForTravelApproval','submitAssetForTravelApproval','')">Reject</button>
						<button name="cancel" type="button"  class="btn btn-primary TrvlApprUTBTN" onclick="ControlAccepRejectTravelDiv('Cancel','AssetForIntraUnitTravelApprovalView','displayAssetForTravelApproval','submitAssetForTravelApproval','')">Cancel</button>
					</p>
				</td>
			</tr>
		</table>
	</form>
</div>

	