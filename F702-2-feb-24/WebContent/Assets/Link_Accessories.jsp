<!--Install_asset.jsp-->
<style> 
.select2-results__options{ font-size:15px !important; width: 200px; !important;}
.select2-container .select2-selection--single {font-size:15px;  width: 200px; !important}
</style>
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>

<script type ="text/javascript" src="All_Js_File/Asset/Link_Accessories.js"></script>
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
				DisplayAssetForBulkInstall("A_Link_Dlink");
				}});
			
		}});
	
}); 
$(document).ready(function() {
    $('.select2search').select2();
});

</script>
<div class="card">
<div class="card-body">
<div class="commonDiv" id="displayAssetForBulkInstallSearch">
<table>
<tr>
	<td><input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForBulkInstall('A_Link_Dlink')"></td>
<!-- 	<td><select name="id_assetdiv" id="assetDivForSubassetDiv" class="form-control select2search" style="width:100%;" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subgroupDataForModel','M_Subasset_Div')">
	<option value="">Select</option>
							
	</select></td>
	<td><select id="subgroupDataForModel" class="form-control select2search" style="width:200px;" name="id_s_assetdiv" data-valid="mand" onChange="DisplayAssetForBulkInstall('A_Link_Dlink')">
						<option value="">Select</option>
						
	</select></td> -->
	</tr>
	</table>
</div>

<div id="DisplayAssetForBulkInstall">
	<form name="bulkinstallAsset" id="bulkinstallAsset">
		<input type="hidden" name="action" value="BulkInstall">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="upload_asset" value="" class="form-control">
		<table class="table table-bordered DisplayAssetForBulkInstallation">
			
		</table>
		
	</form>
</div>
</div>
</div>


