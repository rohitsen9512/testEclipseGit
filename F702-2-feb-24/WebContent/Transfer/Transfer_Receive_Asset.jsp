<!--Intraunit_transfer.jsp-->


<script type="text/javascript">

$(function() {
	
	
	
	$('#ReceiveDetails').hide();
	DisplayAssetForReceiveTransferedAsset();
	
	/* getButtonsForListView('DisplayAssetForTransferring','EditFrom');
 */	
});
</script>


<div id="DisplayAssetForReceiveAsset" class="card">
	<!-- <input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForReceiveTransferedAsset()">
 -->
 <table id="DisplayAssetForTransferring"
		class="table table-bordered table-hover DisplayAssetForTransferring">
	
		<thead ><tr class="new">
			<th><strong>Asset ID</strong></th>
			<th><strong>Asset Name</strong></th>
			<th><strong>Serial Number</strong></th>
			<th><strong>Asset Description</strong></th>
			<th><strong>ReceiveAssetTransfer </strong></th>
		</tr></thead>
	</table>

</div>


<div id="ReceiveDetails" class="card">
<form name="ReceiveAssetTransfer" id="ReceiveAssetTransfer">
	<table class="table table-bordered" id="displayAssetForReceiveAssets">
			
			<tr>
				<td colspan="4">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlReceiveTransferAsset('Update','DisplayAssetForReceiveAsset','ReceiveDetails','ReceiveAssetTransfer')">Receive</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlReceiveTransferAsset('Cancel','DisplayAssetForReceiveAsset','ReceiveDetails','ReceiveAssetTransfer')">Cancel</button>
				</td>
			</tr>
	</table>
</form>
</div>