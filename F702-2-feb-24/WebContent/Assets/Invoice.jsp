<style>
.easy-autocomplete-container {
	max-height: 250px;
	overflow: auto;
	overflow-y: auto;
	overflow-x: hidden;
}
</style>

<link rel="stylesheet" href="Autocomp/easy-autocomplete.min.css">
<link rel="stylesheet" href="Autocomp/easy-autocomplete.themes.min.css">
<script type="text/javascript"
		src="All_Js_File/Asset/A_Invoice.js"></script>
<script type="text/javascript">
	$(function() {
		
		DisplayInvoice('A_Invoice', 'displayInvoice', 'createInvoice', '',
				'invoiceForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
		DisplayDropDownDataForVendor('M_Vendor','vendorDataForInvoice','procured',function(status) {
					if (status) {
						DropDownDataDisplay("M_Loc","locDataForInvoice",function(status) {
																					if (status) {
																						DropDownDataDisplay("M_Dept","deptDataForEmpUser",function (status){
																							if(status)
																								{ 
																						dispalyLineItemInvoice('New');
																								}
																						});
																					}
																				});
																	}
					
				});
	});
	jQuery("input#fileID").change(function () {
		
		 var formData = new FormData();	 
			formData.append('file', $('#fileID').get(0).files[0]);
			
				$.ajax({
				  url: 'Upload_File',
				    type: 'POST',
				    data: formData,
				    async: false,
				    cache: false,
				    contentType: false,
				    dataType: "json",
				    processData: false,
				    mimeType: "multipart/form-data",
				    success: function (r) {
				    	
				    		$('input[name="invoice_file"]').val(r.upload_inv);
				    		//bootbox.alert("File Uploaded successfully");
				    }
				},'json'); 
				
		//$( "#submitForUploadData" ).trigger( "click" );
				
	}); 

</script>
<script>
</script>
<!-- Content Wrapper. Contains page content -->
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>
					<!--  Invoice-->
					<button type="button" name="create btn" class="btn btn-primary inv hideinv"
						onclick="ControlAsset('Create','displayInvoice','createInvoice','submitInvoice','A_Invoice')">Create
						Invoice</button>
				</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Asset</a></li>
					<li class="breadcrumb-item">Invoice</li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="card">
	<div id="displayInvoice">
		<div class="card-body">
			<table width="100%" height="100%">
				<tr>
					<td colspan="4">
					<td><strong>From Date</strong></td>
					<td><input id="dt_frm" type="text" name="dt_frm" value=""
						class="form-control datepicker" data-valid="mand" readonly style="background-color:transparent;font-size: 1em;"></td>
					<td><strong>&nbsp;&nbsp;To Date</strong></td>
					<td><input id="dt_to" type="text" name="dt_to" value=""
						class="form-control datepicker" data-valid="mand" readonly style="background-color:transparent;font-size: 1em;"></td>
					<td>
						<button type="button" style="margin-top: 10px; margin-left: 25px;"
							class="btn btn-primary"
							onclick="DisplayInvoice('A_Invoice','displayInvoice','createInvoice','','invoiceForDisplaytd')">Search
						</button>
					</td>
				</tr>
				<tr></tr>
			</table>
			<table id="invoiceForDisplaytd"
				class="table table-bordered table-hover invoiceForDisplaytd">
				<thead>
					<tr class="new">
						<th><strong>Invoice Number</strong></th>
						<th><strong>Invoice Date</strong></th>
						<th><strong>Vendor</strong></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
<section class="content">
	<div class="card">
		<form name="submitInvoice" id="submitInvoice"
			enctype="multipart/form-data" action="Upload_File" method="post">
			<div id="createInvoice" style="display: none;">
				<div class="card-header new">
					<h3 class="card-title1">Invoice Details</h3>
				</div>
				<table class="table table-bordered" id="invoiceDetails">
					<tr>
						<td><strong>PO Number<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="no_po" value="NA"
							class="form-control readbaledata" data-valid="mand"></td>
						<td><strong>PO Date<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="dt_po" value=""
							class="form-control datepicker"  data-valid="mand" readonly style="background-color:transparent;font-size: 1em;"></td>
					</tr>
					<tr>
						<td><strong>Invoice Number<font color="red">*</font>
						</strong></td>
						<td><input id="InvoiceNum" type="text" name="no_inv" value=""
							class="form-control readbaledata" data-valid="mand"
							onBlur="return checkInvoiceNo()"></td>
						<td><strong>Invoice Date<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="dt_inv" value="" id="dt_inv" 
							class="form-control datepicker " data-valid="mand" onchange="CheckContraDate()" readonly style="background-color:transparent;font-size: 1em;"></td>
					</tr>
					<tr>
						<td><strong>GRN Number</strong></td>
						<td><input id="no_grn" type="text" name="no_grn" value="NA"
							class="form-control readbaledata"></td>
						<td><strong>GRN Date</strong></td>
						<td><input type="text" name="dt_grn" value="" 
							class="form-control datepicker"onchange="CheckgrnaDate()" readonly style="background-color:transparent;font-size: 1em;"></td>
					</tr>
					<tr>
						<td><strong>DC Number</strong></td>
						<td><input id="no_dc" type="text" name="no_dc" value="NA"
							class="form-control readbaledata"></td>
						<td><strong>DC Date</strong></td>
						<td><input type="text" name="dt_dc" value="" 
							class="form-control datepicker" readonly style="background-color:transparent;font-size: 1em;"></td>
					</tr>
					<tr>
						<td><b>Location<font color="red">*</font>
						</b></td>
						<td><select id="locDataForInvoice" name="id_loc"
							class="form-control " data-valid="mand" style="width: 140"
							onChange="DisplaySubDropDownData(this,'slocDataForInvoice','M_Subloc')">
								<option value="">Select</option>
						</select></td>
						<td><b>Sub Location<font color="red">*</font>
						</b></td>
						<td><select id="slocDataForInvoice" name="id_subl"
							class="form-control " data-valid="mand" style="width: 140" onChange="SubDropDownDataDisplay(this,'buildingDataForEmpUser','M_Building')">
								<option value="">Select</option>
						</select></td>
					</tr>
<tr>
			<td ><b>Building<font color="red">*</font></b></td>
				<td >
					<select id="buildingDataForEmpUser" name="id_building" class="form-control " data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'floorDataForEmpUser','M_Floor')">
						<option value="">Select</option>
					</select>
				</td>
			<td ><b>Floor<font color="red">*</font></b></td>
				<td >
					<select id="floorDataForEmpUser" name="id_flr" class="form-control " data-valid="mand"  style="width:140">
						<option value="">Select</option>
					</select>
				</td>
				</tr>
					<tr>
						<td><b>Vendor<font color="red">*</font>
						</b></td>
						<td><select id="vendorDataForInvoice" name="id_ven"
							style="width: 140" class="form-control readbaledata"
							data-valid="mand">
								<option value="">Select</option>
						</select></td>
						<td ><b>Department<font color="red">*</font></b></td>
					<td><select id="deptDataForEmpUser" name="id_dept" class="form-control " data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
				<tr>
						<td><strong>Upload  File</strong></td>
						<td><input id="fileID" type="file" name="file" class="common-validation " value=""><p id="datachment"></p></td>
						<!-- <td><div id="FinleName"></div> <input id="fileID" type="file"
							name="file" class="form-control readbaledata" value=""></td> -->
					</tr>
					<tr class="aa hideRowCol">
						<td><b>BOE No<font color="red">*</font>
						</b></td>
						<td><input type="text" name="no_boe" value=""
							class="common-hideShow" class="form-control"></td>
						<td><strong>BOE Date<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="dt_boe" value=""
							class="common-hideShow invoiceDatepicker1" class="form-control"></td>
					</tr>
				</table>
				<div id="createAccessory" style="display: none;">
					<form name="submitAccessory" id="submitAccessory"
						enctype="multipart/form-data">
						<button name="save22" type="button" style=""
							class="btn btn-primary" onclick="dispalyLineItemInvoice('Save')">Add</button>
						<table class="table table-bordered EditSalesOrder">
						</table>
						<table class="table table-bordered" id="accessoryDetails">
						</table>
						<table class="table table-bordered" id="accessoryDetails1">
						</table>
						<input type="hidden" name="gr_tot" value="0.0" id=" "
							class="form-control resetAcc1 FieldResize" readonly>
						<table class="table table-bordered" id="accessoryDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount"
									value="10"> <input type="hidden" name="action"
									value="Save" class="form-control"> <input type="hidden"
									name="invoice_file" value="" class="form-control"> <input
									id="id" type="hidden" name="id" value="0" class="form-control">
									<input id="invoiceId" type="hidden" name="invoiceId" value=""
									class="form-control"> <input type="hidden"
									name="nm_acc_asset" value="Asset" class="form-control">
									<input type="hidden" name="invoiceCheck" value="0">
									<button name="save" type="button" style="margin-left: 420px;"
										class="btn btn-primary inv"
										onclick="ControlAsset('Save','displayInvoice','createInvoice','submitInvoice','A_Invoice')">Save</button>
									<button name="update" type="button" style="margin-left: 400px;"
										class="btn btn-primary inv"
										onclick="ControlAsset('Update','displayInvoice','createInvoice','submitInvoice','A_Invoice')">Update</button>
									<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlAsset('Cancel','displayInvoice','createInvoice','submitInvoice','A_Invoice')">Back</button>
									<button name="delete" type="button" style="margin-left: 400px;"
										class="btn btn-primary inv"
										onclick="DeleteInvoice('A_Invoice','displayInvoice','createInvoice',document.getElementById('id').value)">Delete</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
	</div>
</section>
