<style>
.row{
display:inherit;
}
.nav-item.active {
    background-color: #3f6791 !important;
}
</style>
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<link rel="stylesheet" href="Autocomp/easy-autocomplete.min.css">
<link rel="stylesheet" href="Autocomp/easy-autocomplete.themes.min.css">
<script type="text/javascript" src="All_Js_File/Asset/Add_item_to_store.js"></script>
<script src="js/jszip.js"></script>
<script src="js/xlsx.js"></script>
<script type="text/javascript">
	$(function() {
		var currentDate = new Date();
		$(".datepicker").datepicker({
			yearRange : '1985:2025',
			changeMonth : true,
			changeYear : true,
			dateFormat : "dd/mm/yy",
			autoSize : true,
			altFormat : "dd/mm/yy",
		});
		
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
		DisplayDropDownDataForVendor('M_Vendor','vendorDataForInvoice','procured',function(status) {
					if (status) {
						DropDownDataDisplay("M_Loc","locDataForInvoice",function(status) {
																					if (status) {
																						DropDownDataDisplay("M_Dept","deptDataForEmpUser",function (status){
																							if(status)
																								{ 
																								DisplayDropDownDataForGroup('M_Asset_Div','groupDataForAddAsset','CapG',function (status){
																									if(status)
																									{
																										DropDownDataDisplay("M_Tax","Tax1DataForInvoice",function(status) {
																											if (status) {
																												DropDownDataDisplayForTax2("M_Tax","Tax2DataForInvoice",function(status) {
																													if (status) {
																										getModel();
																										$('.leaseAddToStoreCommonClass').hide();
																										$('.amcAddToStoreCommonClass').hide();
																													}});
																											}});
																									}});
																								
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
	
	
	//Check AMC start date with Invoice date
	$("#amcYeardatepicker").datepicker({
    	yearRange: '1985:2025',
    	 changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
        onSelect: function(dateText, instance) {
        	 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
        	 var dt_inv=$('input[name="dt_inv"]').val();
     			var dt_amc_start=$('input[name="dt_amc_start"]').val();
     			if(dt_amc_start < dt_inv){
     		alert("'DATE MUST BE GREATER OR EQUAL TO INVOICE DATE. '");
     		$('#amcYeardatepicker').focus();
     		$('#amcYeardatepicker').val('');
     		$('#amcYeardatepicker').addClass('error');
     		}
     		else
     			{
     			$('#amcYeardatepicker').removeClass('error');
        	 $('input[name="dt_amc_start"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
             date.setDate(date.getDate() - 1);
              date.setMonth(date.getMonth() + 12);
              $('input[name="dt_amc_exp"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
     			}
        }
    });

//Check Lease start date with Invoice date
	$("#leasecYeardatepicker").datepicker({
    	yearRange: '1985:2025',
    	 changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
        onSelect: function(dateText, instance) {
        	 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
        	 var dt_inv=$('input[name="dt_inv"]').val();
        		var std_lease=$('input[name="std_lease"]').val();
        		if(std_lease < dt_inv){
        		alert("'LEASE DATE MUST BE GREATER OR EQUAL TO INVOICE DATE. '");
        		$('#leasecYeardatepicker').focus();
        		$('#leasecYeardatepicker').val('');
        		$('#leasecYeardatepicker').addClass('error');
        		}
        		else
        			{
        			$('#leasecYeardatepicker').removeClass('error');
        	 $('input[name="std_lease"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
             date.setDate(date.getDate() - 1);
              date.setMonth(date.getMonth() + 12);
              $('input[name="endt_lease"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
        			}
        }
    }); 
var currentDate = new Date();
	$('.grndatepicker').each(function () {
            $(this).removeClass('hasDatepicker');
    });

	$('.grndatepicker').datepicker({
		yearRange: '1985:2025',  
		changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
	$('input[name="dt_to"]').datepicker("setDate", currentDate);
	currentDate.setMonth(currentDate.getMonth() - 1);
	$('input[name="dt_frm"]').datepicker("setDate", currentDate);
	
	//Check Lease/AMC end date with Lease/AMC start date	 	
	 $( ".leasedatepicker" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  $('input[name="std_lease"]').removeClass('error');
		    	  var std_lease=$('input[name="std_lease"]').val();
		    	  var std_lease1=$('input[name="std_lease"]').val();
		    	  var dt_end = $(this).val();
		    	  if(std_lease == '')
		    		  {
		    		  	alert('First filled start  date.');
		    		  	$('input[name="std_lease"]').focus();
		    		  	$('input[name="std_lease"]').addClass('error');
		    		  	$('input[name="std_lease"]').val('');
		    		  	$(this).val('');
		    		  	exit(0);
		    		  }
		    	  else
		    		  {
		    		  var temp_strt = std_lease.split("/");
					  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
						var temp_end = dt_end.split("/");
						var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
						std_lease = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
						dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		    		  if(std_lease > dt_end)
		    			  {
		    			  	alert('End date should be greater or equal to start date : '+std_lease1);
		    			  	$(this).focus();
		    			  	$(this).val('');
		    			  	$(this).addClass('error');
		    			  	exit(0);
		    			  }
		    		  }
		      }
		    });
	

	
	

</script>
<!------ Include the above in your HEAD tag ---------->

<!-- Tabs -->
<div class="card">
<div style="display: none;display: inline;" id="uploaddisplay" class="card">
	<br> <b style="font-size: small; margin-left: 50px;">Upload
		File </b><input id="upload1" type=file
		style="margin-left: 5px; width: 200px; display: inline" name="files[]"><a
		id="downloadexc12" href="">Download Reference File</a>
</div>
<div id="serialNo" display="none" class="card">
	<form name="SerialId" id="SerialId"></form>
</div>
<form name="submitInvoice" id="submitInvoice"
			enctype="multipart/form-data" action="Upload_File" method="post">
<section id="tabs">
	<div class="container" id="displayInvoiceForStore">
		<h6 class="section-title h1">Tabs</h6>
		<div class="row">
			<div class="col-xs-12 ">
				<nav>
					<div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
						<a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true"><b>Item Details</b></a>
						<a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false"><b>Invoice Details</b></a>
						<a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false"><b>Location Details</b></a>
						<a class="nav-item nav-link" id="nav-about-tab" data-toggle="tab" href="#nav-about" role="tab" aria-controls="nav-about" aria-selected="false"><b>Price Details</b></a>
						<a class="nav-item nav-link" id="nav-others-tab" data-toggle="tab" href="#nav-others" role="tab" aria-controls="nav-others" aria-selected="false"><b>Others</b></a>
					</div>
				</nav>
				<div class="tab-content py-3 px-3 px-sm-0" id="nav-tabContent">
					<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
					<table class="table table-bordered" id="invoiceDetails">
					<tr>
						<td style="width: 20%;"><strong>Item/Model Name<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="nm_model" id="modelDataForAddItem"
							class="form-control readbaledata" data-valid="mand" onChange="getDetails()" ></td>
						<input type="hidden" name="id_model" value="">
						<td><strong>Category<font color="red">*</font>
						</strong></td>
						<td style="width: 35%;">
 					<select id="groupDataForAddAsset" class="form-control" name="id_grp" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subgroupDataForAddAsset','M_Subasset_Div')" >
					
 				<option value="">Select</option>
						
					</select>
					
				</td>
					</tr>
					<tr>
						<td><strong>Sub-Category<font color="red">*</font>
						</strong></td>
						<td>
					<select id="subgroupDataForAddAsset" class="form-control" name="id_sgrp" data-valid="mand" >
						<option value="">Select</option>
						
					</select>
				</td>
						<td><strong>Asset Type<font color="red">*</font>
						</strong></td>
						<td><center><select id="assettypeDivForInvoice" name="typ_asst"  class="form-control" data-valid=""   >
				<option value="">Select</option>
				<option value="IT">IT</option>
				<option value="NON-IT">NON IT</option>
				<option value="Software">Software</option>
				<option value="accessories">Accessories</option></select>
				</center></td>
					</tr>
					
				</table>
					</div>
					<div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
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
	                <td><b>Vendor<font color="red">*</font>
						</b></td>
						<td><select id="vendorDataForInvoice" name="id_ven"
							style="width: 140" class="form-control readbaledata"
							data-valid="mand">
								<option value="">Select</option>
						</select></td>
						<td><strong>Upload  File</strong></td>
						<td><input id="fileID" type="file" name="file" class="common-validation " value=""><p id="datachment"></p></td>
					</tr>
					</table>
					
					</div>
					<div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
					<table class="table table-bordered" id="invoiceDetails">
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
						<td ><b>Department<font color="red">*</font></b></td>
						
					<td><select id="deptDataForEmpUser" name="id_dept" class="form-control " data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
				</table>
					</div>
					<div class="tab-pane fade" id="nav-about" role="tabpanel" aria-labelledby="nav-about-tab">
					<table class="table table-bordered" id="invoiceDetails">
					<tr>
					<td><strong>Quantity</strong><font color="red">*</font></td>
					<td><input type="number" id="qtyserial" name="qty"
						class="form-control" value="" data-valid="mand" onkeydown="if(event.key==='.'){event.preventDefault();}"  oninput="event.target.value = event.target.value.replace(/[^0-9]*/g,'');" onchange="CalculateTotalPriceItem();"></td>
					<td><strong>Unit Price</strong><font color="red">*</font></td>
					<td><input type="number" name="un_prc" class="form-control" data-valid="mand"
						value="0" onchange="CalculateTotalPriceItem();"></td>
				</tr>
				<tr>
					<td><strong>Others</strong></td>
					<td><input type="number" name="others"
						class="form-control" value="0" onchange="CalculateTotalPriceItem();"></td>
					<td><strong>Tax1 Name</strong><font color="red">*</font></td>
					<td><select id="Tax1DataForInvoice" name="id_tax1"
							class="form-control " data-valid="mand" style="width: 140"  onchange="getTax2Details();">
								<option value="">Select</option>
						</select></td>
				</tr>
				<tr>
					<td><strong>Tax2 Name</strong></td>
					<td><select id="Tax2DataForInvoice" name="id_tax2"
							class="form-control " data-valid="mand" style="width: 140" disabled>
								<option value="">Select</option>
						</select></td>
					<td><strong>Tax1 Value</strong></td>
					<td><input type="text" name="tax_val1" class="form-control"
						value="0" readonly></td>
				</tr>
				<tr>
					<td><strong>Tax2 Value</strong></td>
					<td><input type="text" name="tax_val2"
						class="form-control" value="0" readonly></td>
					<td><strong>Discount</strong></td>
					<td><input type="number" name="discount" class="form-control"
						value="0" onchange="CalculateTotalPriceItem();"></td>
				</tr>
				<tr>
					<td><strong>Grand Total</strong></td>
					<td><input type="text" name="gr_tot"
						class="form-control" value="0" readonly></td>
					<td><strong>Value Per Item</strong></td>
					<td><input type="text" name="val_asst"
						class="form-control" value="0" readonly></td>	
				</tr>
				
				</table>
					</div>
					<div class="tab-pane fade" id="nav-others" role="tabpanel" aria-labelledby="nav-others-tab">
					<table class="table table-bordered" id="invoiceDetails">
					<tr>
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
						class="form-control datepicker"></td>
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
				</table>
				<input
				type="hidden" name="serialnumbercount" value="">
				<input type="hidden"
				name="SerialVal" value=""> <input type="hidden" name="sapNo"
				value="">
				<input type="hidden"
				name="action" value="Save">
					</div>
				</div>
			
			</div>
		</div>
		<div style="text-align:center;">
<button name="save" type="button" class="btn btn-primary inv" onclick="ControlNextSerial($('#qtyserial').val())">Next</button>
</div>
	</div>
</section>

</form>
</div>
<!-- ./Tabs -->


