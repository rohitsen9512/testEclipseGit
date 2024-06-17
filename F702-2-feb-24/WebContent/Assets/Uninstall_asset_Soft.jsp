<!--Install_asset.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Assets > </a><a href="#">Uninstall For Software </a>-->
</div>

<script type="text/javascript">

$(function() {
	
	DisplayDropDownData("M_Loc","locDataForUnInstallAsset",function (status){
		if(status)
		{
			DisplayDropDownDataForGroup("M_Asset_Div","assetDivForSubassetDiv","CapG",function (status){
				if(status)
					{
			DisplayAssetForUnInstallSoft("A_Install");
		}});
		}});
	
	
	
	$('button[name="update"]').addClass('hideButton');
	$('#InstallDetails').addClass('hideButton');
	
	
	
});
</script>

<div class="commonDiv" id="displayAssetForUnInstallSearch">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForUnInstallSoft('A_Install')">
	<select name="id_assetdiv" id="assetDivForSubassetDiv" class="common-validation" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subgroupDataForModel','M_Subasset_Div')">
	<option value="">Select</option>
							
	</select>
	<select id="subgroupDataForModel" class="common-validation" name="id_s_assetdiv" data-valid="mand" onChange="DisplayAssetForUnInstallSoft('A_Install')">
						<option value="">Select</option>
						
	</select>
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







