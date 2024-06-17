<!--Install_asset.jsp-->

<!--Install_asset.jsp-->
<style> 
.select2-results__options{ font-size:12px !important;}
.select2-container .select2-selection--single {font-size:12px;}
</style>
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>

<script type ="text/javascript" src="All_Js_File/Asset/A_Install.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">

$(function() {
	
	/* DisplayDropDownData("M_Loc","locDataForUnInstallAsset",function (status){
		if(status)
		{
			DisplayDropDownDataForGroup("M_Asset_Div","assetDivForSubassetDiv","CapG",function (status){
				if(status)
					{
			DisplayAssetForUnInstall("A_Install");
					}});
		}}); */
		setTimeout(function() {
	DisplayAssetForUnInstall("A_Install");
		 
}, 10);
	
	$('button[name="update"]').addClass('hideButton');
	$('#InstallDetails').addClass('hideButton');
	
	
	
});
</script>
<div class="card">
<div class="card-body">
<div class="commonDiv" id="displayAssetForUnInstallSearch">
	<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForUnInstall('A_Install')">
	<!-- <select name="id_assetdiv" id="assetDivForSubassetDiv" class="common-validation" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subgroupDataForModel','M_Subasset_Div')">
	<option value="">Select</option>
							
	</select>
	<select id="subgroupDataForModel" class="common-validation" name="id_s_assetdiv" data-valid="mand" onChange="DisplayAssetForUnInstall('A_Install')">
						<option value="">Select</option>
						
	</select> -->
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

</div>
</div>
</div>
</div>





