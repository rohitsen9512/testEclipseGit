<!--Intraunit_transfer_approval.jsp-->


<script type="text/javascript">

$(function() {
	
	$('#AssetForIntraUnitTransferRequestApprovalView').hide();
	DisplayAssetForTransferRequestAcceptReject();
	
	
});
</script>

<div class="commonDiv" id="displayAssetForIntraUnitTransferRequestApproval">
<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForTransferRequestAcceptReject()">
	
	<div class="card">

		<div id="displayLocation">
			<div class="card-body">
			<div class="card-header new">
					<h3 class="card-title1">Intra Unit Transfer Approval</h3>
				</div>
				<table id="displayAssetForIntraUnitTransferRequestApprovalClass"
					class="table table-bordered table-hover displayAssetForIntraUnitTransferRequestApprovalClass">
					<thead>	
<!-- <table class="table table-bordered displayAssetForIntraUnitTransferRequestApprovalClass"> -->
						<tr class="new">
							<td><strong>Asset ID</strong></td>
							<td><strong>Asset Name</strong></td>
							<td><strong>Serial Number</strong></td>
							<td><strong>Asset Description</strong></td>
							<td><strong>Approve/Reject</strong></td>
						</tr></thead>
</table>
			
</div>
	</div>
	</div>
	</div>
	
	
	
	
<div id="AssetForIntraUnitTransferRequestApprovalView" class="card">
		
	<form action="" name="submitAssetForIntraUnitTransferRequestApproval" id="submitAssetForIntraUnitTransferRequestApproval">	
		<table class="table table-bordered commonTable" align="center" border="1" id="displayAssetForRequestedAssets">
				
					<input type="hidden" name="action" value="Update">
					<input type="hidden" name="id" value="">
					<input type= "hidden" name="nm_loc_tran" size= "25" value="" >
					<input type= "hidden" name="nm_subl_tran" size= "25" value="" >
				
		</table>
	</form>
</div>

	