<style>
.easy-autocomplete-container {
	max-height: 250px;
	overflow: auto;
	overflow-y: auto;
	overflow-x: hidden;
}
.panel-heading.active {
    background-color: #52769b;;
}
</style>
<link rel="stylesheet" href="Autocomp/easy-autocomplete.min.css">
<link rel="stylesheet" href="Autocomp/easy-autocomplete.themes.min.css">
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script type="text/javascript"
		src="All_Js_File/Store/inventory_invoice.js"></script>
<script type="text/javascript">
	$(function() {
		
		DisplayInvoice('S_inventory_invoice', 'displayInvoice', 'createInvoice', '',
				'invoiceForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');

						DropDownDataDisplay("M_Loc","locDataForInvoice",function(status){
																					if (status) {
																					
																						dispalyLineItemInvoice('New');
																								
																					}
																				});
																	
					
	});

	 
 $(function() {
		
		var currentDate = new Date();
		$(".datepicker").datepicker({
			yearRange : '1985:2025',
			changeMonth : true,
			changeYear : true,
			dateFormat : "dd/mm/yy",
			autoSize : true,
			altFormat : "dd/mm/yy",
			 minDate: 0
		});
	
		$('input[name="dt_inv"]').datepicker("setDate", currentDate);
		$('input[name="dt_temp_inv"]').datepicker("setDate", currentDate);
	
		
		
	}); 
 
 function validateInput1() {
	    var inputElement = document.getElementById("InvoiceNum");
	    var inputValue = inputElement.value.trim();

	    var sanitizedValue = inputValue.replace(/[^a-zA-Z0-9\/-]/g, '');

	    if (sanitizedValue !== inputValue) {
	        bootbox.alert("Please enters valid characters in the field i.e. alphabets, number, dash, forward slash.");
	        inputElement.value = sanitizedValue.toUpperCase();
	        return;
	    }
 inputElement.value = sanitizedValue.toUpperCase();
	}

 function validateInput2() {
	    var inputElement = document.getElementById("InvoiceNum1");
	    var inputValue = inputElement.value.trim();

	    var sanitizedValue = inputValue.replace(/[^a-zA-Z0-9\/-]/g, '');

	    if (sanitizedValue !== inputValue) {
	        bootbox.alert("Please enters valid characters in the field i.e. alphabets, number, dash, forward slash.");
	        inputElement.value = sanitizedValue.toUpperCase();
	        return;
	    }
inputElement.value = sanitizedValue.toUpperCase();
	}

	
	 jQuery(function ($) {
		    var $active = $('#accordion .panel-collapse.in').prev().addClass('active');
		    $active.find('a').prepend('<i class="glyphicon fas fa-minus"></i>');
		    $('#accordion .panel-heading').not($active).find('a').prepend('<i class="glyphicon fas fa-plus"></i>');
		    $('#accordion').on('show.bs.collapse', function (e) {
		        $('#accordion .panel-heading.active').removeClass('active').find('.glyphicon').toggleClass(' fa-plus fa-minus');
		        $(e.target).prev().addClass('active').find('.glyphicon').toggleClass(' fa-plus fa-minus');
		    })
		    
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
				
					<button type="button" name="create btn" class="btn btn-primary inv hideinv"
						onclick="ControlInv('Create','displayInvoice','createInvoice','submitInvoice','S_inventory_invoice')">Capture Invoice</button>
				</h1>
			</div>
			<div class="col-sm-6">
				
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="card">
	<div id="displayInvoice">
		<div class="card-body">
		
			<table id="invoiceForDisplaytd"
				class="table table-bordered table-hover invoiceForDisplaytd">
		
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
				    
				  <!--  <td><strong>Type Invoice Nomber<font color="red"></font></strong></td>
			     	<td><select name="type_inv_inventory" class="form-control"  onChange=AutogenerateInv(this) data-valid="mand">
						<option value="with_invoice_no">With Invoice</option>
						<option value="Rent">In Process</option>
						<option value="without_invoice_no">Without Invoice</option>
						</select></td> -->
					
					<td><strong>Invoice Number<font color="red">*</font>
						</strong></td>
						<td><input id="InvoiceNum" type="text" name="no_inv" value="NA"
							class="form-control readbaledata"  onClick="AutoNA()"
							onBlur="return checkInvoiceNo()" onkeyup="validateInput1();"></td>
					
					 <td><strong>Invoice Date<font color="red">*</font>
						</strong></td>
					<td><input type="text" name="dt_inv" class="form-control datepicker" data-valid="mand"  id="dt_inv" placeholder="chose only from the datepicker. Do not paste any data." readonly style="background-color:transparent;font-size: 1em;"></td>

					</tr>
				<!-- 	  <tr>
				    
				
					
					<td><strong>Vendor Name<font color="red">
									* </font>
						</strong></td>
						<td><input id="nm_ven" type="text" name="nm_ven" value="NA"
							class="form-control readbaledata" data-valid="mand"
							onBlur="return checkInvoiceNo()"></td>
					
					 <td><strong>Vendor GST No<font color="red">*</font>
						</strong></td>
						<td><input id="vendor_gst" type="text" name="ven_gst_no" value="NA"
							class="form-control readbaledata" data-valid="mand"></td>
				
					</tr>
					 -->
					
				
		
					<tr>
						<td><b>Entity<font color="red">*</font>
						</b></td>
						<td><select id="locDataForInvoice" name="id_loc"
							class="form-control " data-valid="mand" style="width: 140" onChange="DisplaySubDropDownData(this,'slocDataForInvoice','M_Subloc')">
								<option value="">Select</option>
						</select></td>
						<td><b>City<font color="red">*</font>
						</b></td>
						<td><select id="slocDataForInvoice" name="id_subl"
							class="form-control " data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>
						
						</table> 
			<div class="panel-group" id="accordion">
			 <div class="panel panel-default">
        <div class="panel-heading" style="background-color: #52769b;padding:0.7rem;">
            <h4 class="panel-title">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#panel2">&nbsp;&nbsp;&nbsp;<span class="navpara">Don't Have Invoice No</span></a>
            </h4>
        </div>
        <div id="panel2" class="panel-collapse collapse">
            <div class="panel-body">
                 <table class="table table-bordered" >
               
	      		<td><strong>Temp Invoice Number<font color="red"></font>
						</strong></td>
						<td><input id="InvoiceNum1" type="text" name="temp_no_inv" value="NA"
							class="form-control readbaledata" onkeyup="validateInput2()" onClick="AutogenerateInv()"></td>
					
					 <td><strong>Temp Invoice Date<font color="red"></font>
						</strong></td>
						<td><input type="text" name="dt_temp_inv"  id="dt_temp_inv" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control  datepicker " readonly style="background-color:transparent;font-size: 1em;" ></td>
				
					</tr>
        </table>
            </div>
        </div>
    </div>
      </div>
			
			
			<!-- 	<tr>
						<td><strong>Upload  File</strong></td>
						<td><input id="fileID" type="file" name="file" class="common-validation " value=""><p id="datachment"></p></td>
						<td><div id="FinleName"></div> <input id="fileID" type="file"
							name="file" class="form-control readbaledata" value=""></td>
					</tr> -->
				<!-- 	<tr class="aa hideRowCol">
						<td><b>BOE No<font color="red"> * </font>:
						</b></td>
						<td><input type="text" name="no_boe" value=""
							class="common-hideShow" class="form-control"></td>
						<td><strong>BOE Date<font color="red"> * </font>:
						</strong></td>
						<td><input type="text" name="dt_boe" value=""
							class="common-hideShow invoiceDatepicker1" class="form-control"></td>
					</tr> -->
			
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
						<div style="float:right;margin-right: 22px;"><strong style="margin-right: 100px;">Grand Total</strong>
						<input type="text" name="tot" value="0.0" id=" "
							class="form-control resetAcc1 FieldResize" readonly>
							</div>
						<table class="table table-bordered" id="accessoryDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount"
									value="10"> 
									<input type="hidden" name="action" value="Save" class="form-control"> 
									<input type="hidden" name="invoice_file" value="" class="form-control"> 
									<input id="id" type="hidden" name="id" value="0" class="form-control">
									<input id="invoiceId" type="hidden" name="invoiceId" value="" class="form-control"> 
									<input type="hidden" name="nm_acc_asset" value="Asset" class="form-control">
									<input type="hidden" name="invoiceCheck" value="0">
									<button name="save" type="button" style="margin-left: 420px;"
										class="btn btn-primary inv"
										onclick="ControlInv('Save','displayInvoice','createInvoice','submitInvoice','S_inventory_invoice')">Save</button>
										
									<button name="update" type="button" style="margin-left: 400px;"
										class="btn btn-primary inv"
										onclick="ControlInv('Update','displayInvoice','createInvoice','submitInvoice','S_inventory_invoice')">Update</button>
									<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlInv('Cancel','displayInvoice','createInvoice','submitInvoice','S_inventory_invoice')">Back</button>
									<!-- <button name="delete" type="button" style="margin-left: 0px;"
										class="btn btn-primary inv"
										onclick="DeleteInvoice('S_Add_To_Stock','displayInv','createInvoice',document.getElementById('id').value)">Delete</button> -->
									 <!-- <button name="AddStock" type="button" class="btn btn-primary inv"
										onclick="ControlInv('AddStock','displayInvoice','createInvoice','submitInvoice','S_Add_To_Stock')">Add To Stock</button>  -->
									
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
		
		
	</div>
</section>
