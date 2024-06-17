<!--Intraunit_transfer_approval.jsp-->


<script type="text/javascript">

$(function() {
	$('.HideShowVenForIUTApprov').hide();
	$('.hideLocAdnSubLocForIUTApprov').hide();
	
	$('#AssetForIntraUnitTransferRequestApprovalView').hide();
	DisplayAssetForInterTransferRequestAcceptReject();
	/* getButtonsForListView('displayAssetForIntraUnitTransferRequestApprovalClass','EditFrom'); */
	
});
</script>
<section class="content">
<div class="card">
		<div class="card-body">
			
				<div class="card-header new">
					<h3 class="card-title1">Inter Unit Transfer Approval</h3>
				</div>


<div id="card" id="displayAssetForIntraUnitTransferRequestApproval">
	<!-- <input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForInterTransferRequestAcceptReject()">
	 -->	
	 <table id="displayAssetForIntraUnitTransferRequestApprovalClass"
		class="table table-bordered table-hover displayAssetForIntraUnitTransferRequestApprovalClass">
		
						<tr class="new">
							<td><strong>Asset ID</strong></td>
							<td><strong>Asset Name</strong></td>
							<td><strong>Serial Number</strong></td>
							<td><strong>Asset Description</strong></td>
							<td><strong>Approve/Reject</strong></td>
						</tr>
					</table>
			
</div>
</div>
</div>
	</section>
	<div class="card-body">
	
<div id="AssetForIntraUnitTransferRequestApprovalView" class="card">
<table class="table table-bordered commonTable" align="center" border="1" id="displayAssetForInterRequestedAssets">
				
					<input type="hidden" name="action" value="Update">
					<input type="hidden" name="id" value="">
					<input type= "hidden" name="nm_loc_tran" size= "25" value="" >
					<input type= "hidden" name="nm_subl_tran" size= "25" value="" >
				
		</table>
</div>
</div>
	