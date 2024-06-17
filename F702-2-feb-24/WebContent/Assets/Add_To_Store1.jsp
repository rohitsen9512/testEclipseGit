<!--Create_Grn.jsp-->
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<script type="text/javascript" src="All_Js_File/Asset/Upload_Stock.js"></script>
<script type="text/javascript" src="All_Js_File/Asset/A_Add_To_Store.js"></script>
<script src="js/jszip.js"></script>
<script src="js/xlsx.js"></script>
<style>
.ui-dialog-titlebar-close {
  background: url("http://code.jquery.com/ui/1.10.3/themes/smoothness/images/ui-icons_888888_256x240.png") repeat scroll -93px -128px rgba(0, 0, 0, 0);
  border: medium none;
}
.ui-dialog-titlebar-close:hover {
  background: url("http://code.jquery.com/ui/1.10.3/themes/smoothness/images/ui-icons_222222_256x240.png") repeat scroll -93px -128px rgba(0, 0, 0, 0);
}
</style>
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
				<h1><!--  Add to Store --></h1>
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
						File </b><input id="upload" type=file
					style="margin-left: 5px; width: 200px;" name="files[]"><a
					id="downloadexc" href="InvoiceScanFile/AddStore.xls">Download
						Reference File</a></td>
			</tr>
			<tr>
				<td>
					<table id="displayInvoiceForStoretd"
						class="table table-bordered table-hover displayInvoiceForStoretd">
						<thead>
							<tr class="new">
								<td><strong>Invoice Number</strong></td>
								<td><strong>Invoice Date</strong></td>
								<td><strong>PO Number</strong></td>
								<td><strong>PO Date</strong></td>
								<td><strong>Quantity</strong></td>
								<td><strong>Vendor</strong></td>
								<td><strong>Add to store</strong></td>
							</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
</div>
<div id="dialog" style="display: none;" width="500px" height="400px"></div>
<div style="display: none" id="uploaddisplay" class="card">
	<br> <b style="font-size: small; margin-left: 50px;">Upload
		File </b><input id="upload1" type=file
		style="margin-left: 5px; width: 200px; display: inline" name="files[]"><a
		id="downloadexc1" href="">Download Reference File</a>
</div>
<div id="serialNo" display="none" class="card">
	<form name="SerialId" id="SerialId"></form>
</div>
<div id="AddToStore">
	<div class="card">
		<form name="AddToWH" id="AddToWH">
			<input type="hidden" name="mdl_num11" value=""> <input
				type="hidden" name="process_typ11" value=""> <input
				type="hidden" name="storeage_typ11" value=""> <input
				type="hidden" name="ram_typ11" value=""> <input
				type="hidden" name="serialnumbercount" value=""> <input
				type="hidden" name="id_grn_asset" value=""> <input
				type="hidden" name="qty" value=""> <input type="hidden"
				name="action" value="Save"> <input type="hidden"
				name="SerialVal" value=""> <input type="hidden" name="appNo"
				value=""> <input type="hidden" name="sapNo" value="">
			<input type="hidden" name="id_grp" value=""> <input
				type="hidden" name="id_model" value=""> <input type="hidden"
				name="id_sgrp" value=""> <input type="hidden" name="id_loc"
				value=""> <input type="hidden" name="id_inv_m" value="">
			<input type="hidden" name="id_flr" value=""> <input
				type="hidden" name="id_subl" value=""> <input type="hidden"
				name="id_building" value=""> <input type="hidden"
				name="id_grn" value=""> <input type="hidden" name="no_grn"
				value=""> <input type="hidden" name="no_dc" value="">
			<input type="hidden" name="dt_grn" value=""> <input
				type="hidden" name="dt_dc" value=""> <input type="hidden"
				name="qty_recv" value=""> <input type="hidden"
				name="tt_un_prc" value=""> <input type="hidden"
				name="nm_acc_asset" value=""> <input type="hidden"
				name="total_prc" value=""> <input type="hidden"
				name="id_ven_proc" value=""> <input type="hidden"
				name="dt_inv" value=""> <input type="hidden" name="dt_po"
				value=""> <input type="hidden" name="id_curr" value="">
			<input type="hidden" name="typ_asst" value=""> <input
				type="hidden" name="id_dept" value=""> <input type="hidden"
				name="st_config" value=""> <input type="hidden"
				name="id_div" value=""> <input type="hidden" name="bd"
				value=""> <input type="hidden" name="id_cc" value="0">
			<table class="table table-bordered  AddToStoreDisplay">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center>
							<strong> Add Asset To Store</strong>
						</center></td>
				</tr>
				<tr>
					<td><strong>GRN Number</strong></td>
					<td><input type="text" name="no_grn" value=""
						readonly="readonly" class="form-control"></td>
					<td><strong>GRN Date</strong></td>
					<td><input type="text" name="dt_grn_dummy" value=""
						readonly="readonly" disabled="true" class="form-control"
						data-valid="mand"></td>
				</tr>
				<tr>
					<td><strong>DC Number</strong></td>
					<td><input type="text" name="no_dc" value=""
						readonly="readonly" class="form-control"></td>
					<td><strong>DC Date</strong></td>
					<td><input type="text" name="dt_dc_dummy" value=""
						readonly="readonly" disabled="true" class="form-control"
						data-valid="mand"></td>
				</tr>
				<tr>
					<td style="width: 160px;"><strong>Invoice Number <font
							color="red">*</font>
					</strong></td>
					<td style="width: 400px;"><input type="text" name="no_inv"
						value="" readonly="readonly" class="form-control"
						data-valid="mand"></td>
					<td><strong>Invoice Date <font color="red">*
						</font>
					</strong></td>
					<td><input type="text" name="dt_inv" value=""
						readonly="readonly" disabled="true"
						class="form-control grndatepicker" data-valid="mand"></td>
				</tr>
				<tr>
					<td><strong>PO Number<font color="red">*</font>
					</strong></td>
					<td><input type="text" name="no_po" value=""
						readonly="readonly" class="form-control" data-valid="mand"></td>
					<td><strong>PO Date<font color="red">*</font>
					</strong></td>
					<td><input type="text" name="dt_po" value=""
						readonly="readonly" disabled="true"
						class="form-control grndatepicker" data-valid="mand"></td>
				</tr>
				<tr>
					<td><b>Service Vendor</b></td>
					<td><select id="vendorDataForAddToStore" name="id_service_ven"
						class="form-control" style="width: 140">
							<option value="">Select</option>
					</select></td>
					<td><strong>Additional Cost</strong></td>
					<td><input type="text" name="cst_asst_add" value="0"
						class="form-control" data-valid="num"
						onBlur="return CalcTotPriceForAddToStore()"></td>
				</tr>
				<tr>
					<td><strong>Manufacture</strong></td>
					<td><input type="text" name="mfr" class="form-control"
						value=""></td>
					<td><strong>Asset Name<font color="red">*</font>
					</strong></td>
					<td><select id="modelDataForAddToStore" name="id_model"
						class="form-control resetAcc" readonly="readonly" disabled="true"
						style="width: 140">
							<option value="">Select</option>
					</select></td>
				</tr>
				<tr>
					<td><strong>Asset Description</strong></td>
					<td><input type="text" name="ds_asst" class="form-control"
						value=""></td>
					<td><strong>Asset Remarks</strong></td>
					<td><input type="text" name="rmk_asst" class="form-control"
						value=""></td>
				</tr>
				<tr style="display: none;">
					<td><strong>Asset Name<font color="red">*</font>
					</strong></td>
					<td><input type="text" name="ds_pro" value=""
						class="form-control" data-valid="mand"></td>
					<td><strong>Asset Type<font color="red">*</font></strong></td>
					<td><select class="form-control" name="typ_asst"
						data-valid="mand">
							<option value="IT">IT</option>
							<option value="nonit">NON IT</option>
							<option value="soft">Software</option>
							<option value="accessories">Accessories</option>
					</select></td>
				</tr>
				<tr>
					<td style="display: none"><strong>Model No.</strong></td>
					<td style="display: none"><input type="text" name="mdl_num"
						class="form-control" value=""></td>
					<td id="process1" style="display: none;"><strong>Processor:</strong></td>
					<td id="process2" style="display: none;"><input type="text"
						name="process_typ" class="form-control" value=""></td>
				</tr>
				<tr id="storage_ram" style="display: none;">
					<td><strong>Storage Type</strong></td>
					<td><input type="text" name="storeage_typ"
						class="form-control" value=""></td>
					<td><strong>RAM Type</strong></td>
					<td><input type="text" name="ram_typ" class="form-control"
						value=""></td>
				</tr>
				<tr>
					<td><b>Taggable <font color="red">*</font>
					</b></td>
					<td><select id="tag" name="tag" style="width: 140"
						class="form-control" data-valid="mand">
							<option value="">Select</option>
							<option value="Yes">Yes</option>
							<option value="No">No</option>
					</select></td>
					<td><b>AMC / Warranty <font color="red">*</font>
					</b></td>
					<td><select id="warr_amc1" name="warr_amc"
						class="form-control" style="width: 140"
						onChange="ShowRowColumn(this , 'amcAddToStoreCommonClass')"
						class="form-control" data-valid="mand">
							<option value="O">NO</option>
							<option value="A">AMC</option>
							<option value="W">Warranty</option>
					</select></td>
				<tr class="amcAddToStoreCommonClass">
					<td><b>Start Date<font color="red">*</font>
					</b></td>
					<td><input type="text" id="amcYeardatepicker"
						name="dt_amc_start"
						placeholder="chose only from the datepicker. Do not paste any data."
						value="" class="form-control amcAddToStoreCommonClass1"></td>
					<td><b>End Date<font color="red">*</font>
					</b></td>
					<td><input type="text" name="dt_amc_exp" value=""
						placeholder="chose only from the datepicker. Do not paste any data."
						class="form-control amcAddToStoreCommonClass1 amctDatepicker"></td>
				</tr>
				<tr>
					<td><b>Lease Status<font color="red">*</font>
					</b></td>
					<td><select id="st_lease1" name="st_lease" style="width: 140"
						class="form-control"
						onChange="ShowRowColumn(this , 'leaseAddToStoreCommonClass');">
							<option value="NUL">Not under lease</option>
							<option value="UL">Under lease</option>
					</select></td>
					<td><b>Type of Procurement<font color="red">*</font>
					</b></td>
					<td><select name="typ_proc" class="form-control select"
						data-valid="mand" style="width: 140">
							<option value="">Select</option>
							<option value="OP">Outright Purchase</option>
							<option value="LB">Loan Basis</option>
							<option value="FOC">Add-On</option>
					</select></td>
				<tr class="leaseAddToStoreCommonClass">
					<td><b>Lease Start Date<font color="red">*</font>
					</b></td>
					<td><input type="text" id="leasecYeardatepicker"
						placeholder="chose only from the datepicker. Do not paste any data."
						name="std_lease" value=""
						class=" form-control leaseCommonClass leaseCommonClass1 "></td>
					<td><b>Lease End Date<font color="red">*</font>
					</b></td>
					<td><input type="text" name="endt_lease" value=""
						placeholder="chose only from the datepicker. Do not paste any data."
						class="form-control leaseCommonClass leaseCommonClass1 leasedatepicker"></td>
				</tr>
				<!-- hide and show for AMC / Warranty -->
				<tr>
					<td><strong>Total Unit Price<font color="red">
								*</font>
					</strong></td>
					<td><input type="text" name="cst_asst" value=""
						readonly="readonly" class="form-control" data-valid="num"></td>
					<td><strong>Net Value<font color="red">*</font>
					</strong></td>
					<td><input type="text" name="val_asst" value=""
						readonly="readonly" class="form-control" data-valid="num"></td>
				</tr>
				<tr>
					<td colspan="4">
						<button name="save" type="button" style="margin-left: 500px;"
							class="btn btn-primary store"
							onclick="ControlStore('Save','displayInvoiceForStore','serialNo','AddToStore','')">Save</button>
						<button name="cancel" type="button" class="btn btn-primary store"
							onclick="ControlStore('Back','displayInvoiceForStore','serialNo','AddToStore','')">Back</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
