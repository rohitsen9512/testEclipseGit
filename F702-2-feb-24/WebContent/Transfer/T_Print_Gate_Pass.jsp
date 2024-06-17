<!--Intraunit_transfer_approval.jsp-->


<script type="text/javascript">

$(function() {
	$('.HideShowVenForIUTApprov').hide();
	$('.hideLocAdnSubLocForIUTApprov').hide();
	
	$('#AssetForIntraUnitTransferRequestApprovalView').hide();
	DisplayAssetForInterTransferRequestAcceptRejectGatePassPrint();
	/* getButtonsForListView('displayAssetForIntraUnitTransferRequestApprovalClass','EditFrom'); */
	
});
</script>
<div class="card">
<div class="card-body">
<div class="commonDiv" id="displayAssetForIntraUnitTransferRequestApproval">
	<!-- <input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForInterTransferRequestAcceptReject()">
	 -->	
	 <table id="displayAssetForIntraUnitTransferRequestApprovalClass"
		class="table table-bordered table-hover displayAssetForIntraUnitTransferRequestApprovalClass">
		
		<thead>				<tr class="new">
							<th><strong>Asset ID</strong></th>
							<th><strong>Asset Name</strong></th>
							<th><strong>Serial Number</strong></th>
							<th><strong>Asset Description</strong></th>
							<th><strong>Approve/Reject</strong></th>
						</tr></thead>
					</table>
			
</div>
	
	
	
	</div>
	</div>
	
	<div class="card-body">
<div id="AssetForIntraUnitTransferRequestApprovalView" class="commonDiv">
<table class="table table-bordered commonTable" align="center" border="1" id="displayAssetForInterRequestedAssets">
				
					<input type="hidden" name="action" value="Update">
					<input type="hidden" name="id" value="">
					<input type= "hidden" name="nm_loc_tran" size= "25" value="" >
					<input type= "hidden" name="nm_subl_tran" size= "25" value="" >
				
		</table>
</div>

	</div>
