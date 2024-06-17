<!--Install_asset.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Assets > </a><a href="#">To Be Sold / To Be Disposed </a> -->
</div>

<script type="text/javascript">

$(function() {
	
	
			DisplayAssetForDispose("A_Dispose_Sold");
	
	
	
	
	
	$('button[name="update"]').addClass('hideButton');
	$('#InstallDetails').addClass('hideButton');
	
	
	
});
</script>

<div class="commonDiv" id="displayAssetForUnInstallSearch">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForDispose('A_Dispose_Sold')">
</div>


<div id="DisplayAssetForUnInstall">
	<form name="uninstallAsset" id="uninstallAsset">
	<input type="hidden" name="action" value="">
		<table class="table table-bordered DisplayAssetForUnInstallation">
			<tr class="info new">
				<td><strong>Asset ID</strong></td>
				<td><strong>Asset Name</strong></td>
				<td><strong>Model Number</strong></td>
				<td><strong>Asset Description</strong></td>
				<td><strong>Install It</strong></td>
			</tr>
		</table>
	</form>
</div>







