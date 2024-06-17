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
		
		DisplayInventoryafterStock('S_Add_To_Stock', 'displayInventoryafterstock', 'EditInventoryafterstock', '','submitInventoryafterstock',
		'inventoryAfterstockForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
		
					//if (status) {
						DropDownDataDisplay("M_Loc","locDataForInvoice",function(status){
																					if (status) {
																						
																						
																								
																								dispalyLineItemInvoiceformodify('');
																								
																					}
																				});
																
					
				
	});
	
	
	function validateInput1(id) {
	    var inputElement = document.getElementById(id);
	    var inputValue = inputElement.value.trim();

	    // Replace characters that are not alphanumeric, dash, special characters, or forward slash
	    var sanitizedValue = inputValue.replace(/[^a-zA-Z0-9\/-]/g, '');

	    // Remove spaces between characters
	    sanitizedValue = sanitizedValue.replace(/\s/g, '');

	    if (sanitizedValue !== inputValue) {
	       // bootbox.alert("Please enter valid characters in the field (i.e., alphabets, numbers, dash, special characters).");
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
			 <!-- 	<h1>
					 Create Lead
					<button type="button" name="create btn" class="btn btn-primary led hideCrled"
						onclick="ControlCrLead('Create','displayInventoryafterstock','createCrLead','submitInventoryafterstock','O_AssignedLlead')">Create
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
	<div id="displayInventoryafterstock">
		<div class="card-body">
		
			<table id="inventoryAfterstockForDisplaytd"
				class="table table-bordered table-hover inventoryAfterstockForDisplaytd">
				
			</table>
		</div>
	</div>
</div>

<div class="card">
<div id="EditInventoryafterstock" style="display: none;">
	
		<form name="submitInventoryafterstock" id="submitInventoryafterstock"
			enctype="multipart/form-data" action="Upload_File" method="post">
			<div id="createInventorystock">
				<div class="card-header">
					<h3 class="card-title1">Invoice Details</h3>
				</div>
				<table class="table table-bordered" id="inventroyafterstockDetails">
				
					<tr>
						<td><strong>Invoice Number<font color="red">*</font>
						</strong></td>
						<td><input id="InvoiceNum" type="text" name="no_inv" value=""
							class="form-control readbaledata reopenState invoiceno"   
							onBlur="return checkInvoiceNo()" onkeyup="validateInput1('InvoiceNum')"></td>
						<td><strong>Invoice Date<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="dt_inv" value="" id="dt_inv" readonly  placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control  readbaledata  datepicker reopenState "></td>
					</tr>
					
			<!-- 		  <tr>
				    
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
				
					</tr> -->
					
		
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
							class="form-control " disabled="true"  data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>
				
					</tr>

				
				
			
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
							class="form-control readbaledata  "  onkeyup="validateInput1('InvoiceNum1')" onClick="AutogenerateInv()"></td>
					
					 <td><strong>Temp Invoice Date<font color="red"></font>
						</strong></td>
						<td><input type="text" name="dt_temp_inv" value="" id="dt_temp_inv" disabled placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control invoicedate readbaledata datepicker" ></td>
				
					</tr>
        </table>
            </div>
        </div>
    </div>
      </div>
			
			
			
				</table>
					<div id="createAccessory" style="display: none;">
					<form name="submitAccessory" id="submitAccessory"
						enctype="multipart/form-data">
						<!-- <button name="save22" type="button" style=""
							class="btn btn-primary" onclick="dispalyLineItemInvoice('Save')">Add</button> -->
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
									value="10"> 
									<input type="hidden" name="action" value="Update" class="form-control"> 
									<input type="hidden" name="invoice_file" value="" class="form-control"> 
									<input id="id" type="hidden" name="id" value="0" class="form-control">
									<input id="invoiceId" type="hidden" name="invoiceId" value="" class="form-control"> 
									<input type="hidden" name="nm_acc_asset" value="Asset" class="form-control">
									<input type="hidden" name="invoiceCheck" value="0">
									
									 <button name="UpdateInvoiceNo" type="button"  style="margin-left: 400px;" class="btn btn-primary invent"
										onclick="ControlInventoryafterstock('Update','displayInventoryafterstock','EditInventoryafterstock','submitInventoryafterstock','S_Add_To_Stock')">Update</button> 
																		<button  name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlInventoryafterstock('Cancel','displayInventoryafterstock','EditInventoryafterstock','submitInventoryafterstock','S_Add_To_Stock','')">Back</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
		
	</div>

