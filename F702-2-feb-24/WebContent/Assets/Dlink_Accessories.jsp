<!--Install_asset.jsp-->
<style> 
.select2-results__options{ font-size:15px !important; width: 200px; !important;}
.select2-container .select2-selection--single {font-size:15px;  width: 200px; !important}
</style>
<script type ="text/javascript" src="All_Js_File/Asset/Link_Accessories.js"></script>
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script type="text/javascript">

$(function() {
	
	DisplayDropDownData("M_Loc","locDataForUnInstallAsset",function (status){
		if(status)
		{
			setTimeout(function() {
			DisplayDropDownDataForGroup("M_Asset_Div","assetDivForSubassetDiv","CapG",function (status){
				if(status)
					{
					setTimeout(function() {
					DisplayAssetDlink("A_Link_Dlink");
					
					}, 10)
					}});
			}, 10);
		}});
	
	
	
	
	$('button[name="update"]').addClass('hideButton');
	$('#InstallDetails').addClass('hideButton');
	
	
	
});
$(document).ready(function() {
    $('.select2search').select2();
});
</script>
<div class="card">
<div class="card-body">
<div class="commonDiv" id="displayAssetForUnInstallSearch">
<table>
<tr>
	<td><input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetDlink('A_Link_Dlink')"></td>
	<!-- <td><select name="id_assetdiv" id="assetDivForSubassetDiv" class="form-control select2search"  data-valid="mand"  style="width: 100%"onChange="SubDropDownDataDisplay(this,'subgroupDataForModel','M_Subasset_Div')">
	<option value="">Select</option>
							
	</select></td>
	<td><select id="subgroupDataForModel" class="form-control select2search" name="id_s_assetdiv" data-valid="mand" style="width: 100%" onChange="DisplayAssetDlink('A_Link_Dlink')">
						<option value="">Select</option>
						
	</select></td> -->
	</tr>
	</table>
</div>


<div id="DisplayAssetForUnInstall">
	<form name="uninstallAsset" id="uninstallAsset">
	<input type="hidden" name="action" value="">
		<table class="table table-bordered DisplayAssetForUnInstallation">
			<tr class="info new">
				<td><strong>Accessories ID</strong></td>
				<td><strong>Accessories Name</strong></td>
				<td><strong>Model Number</strong></td>
				<td><strong>Accessories Description</strong></td>
				<td><strong>Install It </strong></td>
			</tr>
		</table>
	</form>
</div>
</div>
</div>






