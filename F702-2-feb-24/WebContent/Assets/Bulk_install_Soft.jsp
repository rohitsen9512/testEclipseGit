<!--Install_asset.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Assets > </a><a href="#">Install > </a><a href="#">Bulk Install For Software</a>-->
</div>
<script type ="text/javascript" src="All_Js_File/Asset/A_Install.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">

$(function() {
	$('button[name="update"]').addClass('hideButton');
	$('#InstallDetails').addClass('hideButton');
	
	DisplayDropDownData("M_Loc","locDataForBulkInstallAsset",function (status){
	if(status)
		{
		DisplayDropDownDataForGroup("M_Asset_Div","assetDivForSubassetDiv","CapG",function (status){
			if(status)
				{
			DisplayAssetForBulkInstallSoft("A_Install");
		}});
		}});
}); 

</script>

<div class="commonDiv" id="displayAssetForBulkInstallSearch">
	<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForBulkInstallSoft('A_Install')">
	<select name="id_assetdiv" id="assetDivForSubassetDiv" class="common-validation" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subgroupDataForModel','M_Subasset_Div')">
	<option value="">Select</option>
							
	</select>
	<select id="subgroupDataForModel" class="common-validation" name="id_s_assetdiv" data-valid="mand" onChange="DisplayAssetForBulkInstallSoft('A_Install')">
						<option value="">Select</option>
						
	</select>
</div>

<div id="DisplayAssetForBulkInstall">
	<form name="bulkinstallAsset" id="bulkinstallAsset">
		<input type="hidden" name="action" value="BulkInstall">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="upload_asset" value="" class="common-validation">
		<table class="table table-bordered DisplayAssetForBulkInstallation">
			
		</table>
		
	</form>
</div>

