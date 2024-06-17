<!--Create_Grn.jsp-->
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<script type="text/javascript" src="All_Js_File/Asset/Upload_Asset.js"></script>
<script src="js/jszip.js"></script>
<script src="js/xlsx.js"></script>
<script>
	$(function() {
		DropDownDataDisplay('M_Model', 'modelDataForAddToStore', function(
				status) {
			if (status) {
				setTimeout(function() {
					DisplayDropDownDataForVendor('M_Vendor',
							'vendorDataForAddToStore', 'service');
				}, 100);
			}
		});
		setTimeout(function() {
			DisplayInvoiceForAddToStore('A_Add_To_Store1',
					'displayInvoiceForStoretd');
		}, 10);
	});
</script>
<!-- Content Wrapper. Contains page content -->
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1><!-- Add to Store  --></h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Asset</a></li>
					<li class="breadcrumb-item">Add To Store</li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div id="displayInvoiceForStore" class="commonDiv">
	<div class="card">
		<table width="100%" height="100%">
			<tr>
				<td><b style="font-size: small; margin-left: 50px;">Upload
						File </b><input id="upload1" type=file
					style="margin-left: 5px; width: 200px;" name="files[]"><a
					id="downloadexc" href="InvoiceScanFile/Upload_Asset.xlsx">Download
						Reference File</a></td>
			</tr>
			
		</table>
	</div>
</div>
</div>
<div id="dialogforAsset" style="display: none;" width="500px" height="400px"></div>