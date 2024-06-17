<!--Install_asset.jsp-->
<style> 
.select2-results__options{ font-size:15px !important;}
.select2-container .select2-selection--single {font-size:15px;}
.select2-selection{
width:150px;
}
</style>
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>

<script type ="text/javascript" src="All_Js_File/Asset/A_Install.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">

$(function() {
	$('button[name="update"]').addClass('hideButton');
	$('#InstallDetails').addClass('hideButton');
	
/* 	DisplayDropDownData("M_Loc","locDataForBulkInstallAsset",function (status){
	if(status)
		{
		DisplayDropDownDataForGroup("M_Asset_Div","assetDivForSubassetDiv","CapG",function (status){
			if(status)
				{
				DisplayAssetForBulkInstall("A_Install");
				}});
			
		}}); */
	setTimeout(function() {
		DisplayAssetForBulkInstall("A_Install");
			 
	}, 10);
}); 

</script>



<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Allocation  -->
						
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Asset</a></li>
						<li class="breadcrumb-item">Allocate</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>


<div class="card">
<div class="card-body">
<div class="commonDiv" id="displayAssetForBulkInstallSearch">
	<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForBulkInstall('A_Install')">
	
</div>
	<div id="DisplayAssetForBulkInstall">

<div class="card">

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
</div>
