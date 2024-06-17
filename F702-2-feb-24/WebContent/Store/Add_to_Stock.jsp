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
		src="All_Js_File/Store/store.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">
	$(function() {
		
		DisplayInventoryforstock('S_Add_To_Stock', 'displayInventory', 'EditInventory', '','submitInventory',
		'inventoryForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
		
					//if (status) {
					DropDownDataDisplay("M_Subloc","slocDataForInvoice",function(status){
																					if (status) {
																						
																						
																								
																								dispalyLineItemInvoiceformodify('');
																				 				
																					}
																				});
																 
					
				
	});
	
	
	
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
function VN() {
	debugger;
	var numberInput = document.getElementById('warr_amc_day');
	var x = $("#warr_amc_day").val();
    var inputValue = parseFloat(numberInput.value);
    var number = Number(numberInput);
	
    if (!Number.isInteger(inputValue) || isNaN(x)){
    	alert('Please enter a valid number in the field.');
        numberInput.value = ''; // Clear the input field
        numberInput.focus(); // Set focus back to the input field
    	
    }
    else if (inputValue > 100) {
      alert('Max Year(s): 100');
      numberInput.value = ''; // Clear the input field
      numberInput.focus(); // Set focus back to the input field
    }
    else if (inputValue < 0){
    	alert('Please enter a valid number in the field.');
        numberInput.value = ''; // Clear the input field
        numberInput.focus(); // Set focus back to the input field
    	
    }
    else if (inputValue == 0){
    	alert('Min Year(s): 1');
        numberInput.value = ''; // Clear the input field
        numberInput.focus(); // Set focus back to the input field
    	
    }
    else{
    	$(".amcAddToStockCommonClass1").show();
    	$("#startdateamc").show();
    	$("#amcYeardatepicker").show();
    	$("#endatefield").show();
    	$("#endateamc").show();
    	
    }
    	    	
}
function validateInput1() {
	$("#nm_ven").val().replace(/\s+/g, " ").trim();
}

function validateInput(id) {
    var inputElement = document.getElementById(id);
    var inputValue = inputElement.value;
		
    // Replace non-alphanumeric characters with an empty string
      var sanitizedValue = inputValue.replace(/[^a-zA-Z0-9\/\-!@#$%^&*()_+={}[\]:;<>,.?~\\'"]/g, '');
    sanitizedValue = sanitizedValue.replace(/\s/g, '');

    if (sanitizedValue.indexOf(' ') !== -1) {
        alert("Spaces are not allowed. Please remove spaces from the input.");
        // You can choose to clear the input or handle it in another way
    }
    // Update the input field with the sanitized value
    inputElement.value = sanitizedValue.toUpperCase();
}


</script>
<!-- Content Wrapper. Contains page content -->
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
			 <!-- 	<h1>
					 Create Lead
					<button type="button" name="create btn" class="btn btn-primary led hideCrled"
						onclick="ControlCrLead('Create','displayInventory','createCrLead','submitInventory','O_AssignedLlead')">Create
						Lead</button>
				</h1> --> 
			</div>
			<div class="col-sm-6">
			<!-- 	<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">store</a></li>
					<li class="breadcrumb-item">Add To Stock</li>
				</ol> -->
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="card">
	<div id="displayInventory">
		<div class="card-body">
			
			<table id="inventoryForDisplaytd"
				class="table table-bordered table-hover inventoryForDisplaytd">
				
			</table>
		</div>
	</div>
</div>

<div id="serialNo" display="none" class="card">
	<form name="SerialId" id="SerialId"></form>
</div> 

<div class="card">
<div id="EditInventory" style="display: none;">
	
		<form name="submitInventory" id="submitInventory"
			enctype="multipart/form-data" action="Upload_File" method="post">
			<div id="createInventory">
				<div class="card-header">
					<h3 class="card-title1">Invoice Details</h3>
				</div>
			 <input type="hidden" name="serialnumbercount" value=""> 
			 <input type="hidden" name="typeprod" value=""> 
			  <input type="hidden" name="qty_fill" value=""> 
			   <input type="hidden" name="qty_empty" value=""> 
			 <input type="hidden" name="qty_recv" value=""> 
			 <input type="hidden" name="action" value="Save"> 
			 <input type="hidden" name="SerialVal" value=""> 
		      <input type="hidden" name="id_inventory_m" value="">
		       <input type="hidden" name="id_inventory" value="">
		       <input type="hidden" name="id_loc" value=""> 
		      <input type="hidden" name="id_subl" value=""> 
		        <input type="hidden" name="id_product" value=""> 
		    
			    <input type="hidden" name="total_prc" value=""> 
			    <input type="hidden" name="tt_un_prc" value=""> 
			   <input type="hidden" name="st_config" value="">
			<!--    <input type="hidden" name="no_inv" value=""> 
			   <input type="hidden" name="temp_inv_no" value="">  -->
			    <input type="hidden" name="dt_temp_inv" value=""> 
			    <input type="hidden" name="dt_inv" value=""> 
			    
			    
			    
			<table class="table table-bordered" id="inventroyDetails">
				
					<tr>
				
						<td><strong>Invoice Number<font color="red">*</font>
						</strong></td>
						<td><input id="InvoiceNum" type="text" name="no_inv" value=""
							class="form-control readbaledata"  readonly data-valid="mand"
							onBlur="return checkInvoiceNo()" onkeyup="this.value = this.value.toUpperCase();"></td>
						<td><strong>Invoice Date<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="dt_inv_dummy" value="" id="dt_inv" 
							class="form-control readbaledata  " readonly  data-valid="mand" "></td>
					</tr>
						<tr>
				
						<td><strong>Temporary Invoice Number<font color="red">*</font>
						</strong></td>
						<td><input id="InvoiceNum" type="text" name="temp_no_inv" value=""
							class="form-control readbaledata"  readonly data-valid="mand"
							onBlur="return checkInvoiceNo()" onkeyup="this.value = this.value.toUpperCase();"></td>
						<td><strong>Temporary Invoice Date<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="dttemp_no_inv" value="" id="dt_temp_inv" class="form-control readbaledata " readonly  data-valid="mand" onchange="CheckContraDate()"></td>
					</tr>
					  <tr>
				    
				
					
					<td><strong>Vendor Name<font color="red">
									*</font>
						</strong></td>
						<td><input id="nm_ven" type="text" name="nm_ven" value=""
							class="form-control readbaledata" data-valid="mand"
							onBlur="return checkInvoiceNo()" onkeyup="validateInput('nm_ven')"></td>
					
					 <td><strong>Vendor GST No<font color="red">*</font>
						</strong></td>
						<td><input id="vendor_gst" type="text" name="ven_gst_no" value="NA"
							class="form-control readbaledata" data-valid="mand" onkeyup="validateInput('vendor_gst');"></td>
				
					</tr>
					
		          	 <tr>
                       <td><b>Entity<font color="red">*</font>
						</b></td>
						<td><input id="nm_loc" type="text" name="nm_loc" value=""
							class="form-control readbaledata"  disabled="true" data-valid="mand"
							></td>
						</tr>
		          
				   <tr>
                       <td><b>City<font color="red">*</font>
						</b></td>
							<td><input id="nm_subl" type="text" name="nm_subl" value=""
							class="form-control readbaledata"  disabled="true" data-valid="mand"
							></td>
				
					<td><b>AMC/Warranty <font color="red">*</font>
					</b></td>
					<td><select id="war_amc1" name="warr_amc"
						class="form-control"  style="width: 140"
						onChange="ShowRowColumn(this , 'amcAddToStockCommonClass')"
						class="form-control" data-valid="mand">
							<option value="O">NO</option>
							<option value="A">AMC</option>
							<option value="W">Warranty</option>
					</select></td>
					</tr>
					</tr>
		
		       	<tr class="amcAddToStockCommonClass" style="display:none">
					<td id="amcyearwarr"><b>AMC/Warranty Year<font color="red">*</font>
					<td id="waramcday"><input id="warr_amc_day" type="text" name="warr_amc_day" value="" 
							class="form-control readbaledata amcAddToStockCommonClass1"  onChange="VN();calculateAmcDate()"></td>
					</b></td> </tr> 
				<tr class="amcAddToStockCommonClass1" style="display:none">
					<td id ="startdateamc" style="display:none" ><b>Start Date<font color="red">*</font>
					</b></td>
					<td class="amcAddToStockCommonClass1" style="display:none"><input type="text" id="amcYeardatepicker" style="display:none;background-color:transparent;font-size: 1em;"
						name="dt_amc_start" 
						placeholder="chose only from the datepicker. Do not paste any data."
						value="" readonly class="form-control amcAddToStockCommonClass1 " ></td>

					<td id="endatefield" style="display:none"><b>End Date<font color="red">*</font>
					</b></td>
					<td id="endateamc" style="display:none"><input type="text" name="dt_amc_exp" value="" disabled style="background-color:transparent;font-size: 1em;"
						placeholder="chose only from the datepicker. Do not paste any data."
						class="form-control amcAddToStockCommonClass1 amctDatepicker"></td>
			</tr>
		
				<tr>
				
						<td><strong>Product Name<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="nm_product" value=""
							class="form-control readbaledata"  readonly data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
				
						<td><strong>Product Description<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="ds_prod" value=""
							class="form-control readbaledata" readonly  data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
						
					</tr>
				<tr>
				
						<td><strong>Manufacture<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="mfr" value=""
							class="form-control readbaledata" readonly  data-valid="mand"onkeyup="this.value = this.value.toUpperCase();"></td>
				
					
					
				<td ><b>Taggable  <font color="red"> * </font>:</b></td>
				<td >
					<select id="tag" name="tag" class="form-control readbaledata" data-valid="mand">
						<option value="">Select</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
					</select>
				</td></tr>
						<tr>
				
				    	<td><strong>Product Remark<font color="red">*</font>
						</strong></td>
						<td><input type="text" id="remarkid"  name="remarks" value=""
							class="form-control readbaledata"   data-valid="mand" onkeyup="validateInput('remarkid');"></td>
						<td><strong> Unit price<font color="red">*</font>
						</strong></td>
					<td><input type="text" name="un_prc" value=""
							class="form-control readbaledata"  readonly data-valid="mand"></td>
					
						
					</tr>
			<!-- <td><strong>Net Value<font color="red">*</font>
						</strong></td> -->
					<td style="display:none"><input type="text" name="cst_prod" value=""
							class="form-control readbaledata"  readonly data-valid="mand"></td>
				
				
							<tr>
			                   <td colspan="4">
			                      <button name="AddStock" type="button"  style="margin-left: 500px;" class="btn btn-primary invent"
										onclick="Controlinventorystock('Save','displayInventory','EditInventory','submitInventory','S_Add_To_Stock')">Save</button> 
																		<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="Controlinventorystock('Cancel','displayInventory','serialNo','EditInventory','S_Add_To_Stock','')">Back</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
		
	</div>

