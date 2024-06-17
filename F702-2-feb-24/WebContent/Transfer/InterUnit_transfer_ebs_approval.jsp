<!--Intraunit_transfer_approval.jsp-->

<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#"> Transfer > </a><a href="#">EBS Approval</a> -->
</div>
<script type="text/javascript">

$(function() {
	$('.HideShowVenForIUTApprov').hide();
	$('.hideLocAdnSubLocForIUTApprov').hide();
	
	$('#AssetForIntraUnitTransferRequestApprovalView').hide();
	DisplayAssetForInterTransferRequestEBSAcceptReject();
	
	
});
</script>

<div class="commonDiv" id="displayAssetForIntraUnitTransferRequestApproval">
	<!-- <input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForInterTransferRequestEBSAcceptReject()">
	 -->	
		<table class="table table-bordered displayAssetForIntraUnitTransferRequestEBSApprovalClass">
						<tr class="new">
							<td><strong>Asset ID</strong></td>
							<td><strong>Asset Name</strong></td>
							<td><strong>Serial Number</strong></td>
							<td><strong>Asset Description</strong></td>
							<td><strong>Approve/Reject</strong></td>
						</tr>
					</table>
			
</div>
	
	
	
	
<div id="AssetForIntraUnitTransferRequestApprovalView" class="commonDiv">
<table class="table table-bordered commonTable" align="center" border="1" id="displayAssetForInterRequestedAssets">
				
					<input type="hidden" name="action" value="Update">
					<input type="hidden" name="id" value="">
					<input type= "hidden" name="nm_loc_tran" size= "25" value="" >
					<input type= "hidden" name="nm_subl_tran" size= "25" value="" >
				
		</table>
</div>

	