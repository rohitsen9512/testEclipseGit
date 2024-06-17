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
		src="All_Js_File/Order/create_invoice.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">
	$(function() {
		
		DisplayCreateInvoice('O_CreateInvoiceLead', 'displayCreateInvoice', 'CreateInvoice', '','submitCreateLead',
				'CreateInvoiceForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
								DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
																					if (status) {
																						DisplayDropDownData("M_Emp_User","empdataforcreatedByInv",function(status) {
																							if (status) {
																								DisplayDropDownData("M_Designation","dgnForEmp",function(status) {
																									if (status) {	
																		                     dispalyModifyLineInvItemLead('');
																								
																					          }
                                                                                 });
																							 }
		                                                                                 });
																					 }
                               
								 });
					
				
	});

	$(function() {
		//$('#createPrintorMailPurchaseOrder').hide();
		var currentDate = new Date();
		$( ".datepicker" ).datepicker({
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
		
		
	
		
		//DisplayLeadExportInvoiceForPrint();
		
	});


</script>
<script>
</script>
<!-- Content Wrapper. Contains page content -->
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
		
			</div>
			<div class="col-sm-6">
			
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="card">
	<div id="displayCreateInvoice">
		<div class="card-body">
	
			<table id="CreateInvoiceForDisplaytd"
				class="table table-bordered table-hover CreateInvoiceForDisplaytd">
				<thead>
					<tr class="new">
						
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
<section class="content">
<div class="card">
<div id="CreateInvoice" style="display: none;">
	
		<form name="submitCreateLead" id="submitCreateLead"
			enctype="multipart/form-data" action="Upload_Fil" method="post">
			<div id="createInvLead">
				<div class="card-header new">
					<h3 class="card-title1">Create Invoice Details</h3>
				</div>
				
				<table class="table table-bordered" id="InvoiceDetails">
				
					<tr>
						<td><strong>PO Number<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="no_po" value="NA"
							class="form-control readbaledata" readonly data-valid="mand"></td>
						<td><strong>PO Date<font color="red">*</font>
						</strong></td>
						<td><input type="text" name="dt_po" value="" readonly
							class="form-control readbaledata datepicker"  data-valid="mand" placeholder="chose only from the datepicker. Do not paste any data."></td>
					</tr>
					<tr>
						 <td><strong>Invoice Number<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="inv_no" value=""
						    class="form-control readbaledata"  data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>  
						
						
						
					</tr>
						<tr>
						<td><strong>Invoice Date<font color="red">*</font></strong></td>
						<td><input type="text" id="dt_inv" name="dt_inv" value="" onchange="CheckContraDate()" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand"  font-size: 1em;"></td>
							 
							 <td><b>Invoice Created By<font color="red">*</font>
					<td><select id="empdataforcreatedByInv" name="inv_created_by"
							class="form-control" disabled="true" data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td> 
						
							
						
					
					
						
						</tr>
				
					<tr>
						<td><strong>Lead Id<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" readonly data-valid="mand"></td>
						<td><strong>Lead Create Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_ld" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand" readonly font-size: 1em;"></td>
					<!-- 
					 <td><b>Source<font color="red">*</font></b></td>
						<td><select id="srcforlead" name="id_src"
							class="form-control" data-valid="mand" style="width: 140" disabled="true" onchange="getsrcCode(this);">
								<option value="">Select</option>
						</select></td> -->
						</tr>
					<tr>
			    	 	<td><b>Source Code<font color="red">*</font></b></td>
						<td><input id="cd_src" class="form-control"  type="text" name="cd_src" readonly data-valid="mand"></td>
					
		           <td><strong>Patient Name<font color="red">*</font></strong></td>
				    <td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" readonly data-valid="mand"></td>
					</tr>
					<tr>
					
				 <td><strong>Attender Name<font color="red">*</font></strong></td>
				<td><input id="att_name" class="form-control" type="text" name="att_name" data-valid="mand" readonly="readonly"></td>
					
				   <td><strong>Contact No.<font color="red">*</font></strong></td>
				   <td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" readonly data-valid="mand"></td>
				 	
	
					</tr>
					<tr>
				 		
				 <td><strong>Attender Contact No.<font color="red">*</font></strong></td>
				<td><input id="alt_pstn_ct" class="form-control" type="text" name="alt_pstn_ct" readonly data-valid="mand"></td>					
					<td><strong>Email<font color="red">*</font></strong></td>
				<td><input id="pstn_email" class="form-control" type="text" name="pstn_email" readonly data-valid="mand"></td>
				
					
				
					</tr>
					<tr> 
				<td><strong>Aadhaar No.<font color="red">*</font></strong></td>
				<td><input id="adhar_no" class="form-control" type="text" name="adhar_no" readonly data-valid="mand"></td>					
					<td><strong>Address<font color="red">*</font></strong></td>
				<td><input id="address" class="form-control" type="text" name="address" readonly data-valid="mand"></td>
					</tr>
			
			<!--        	<tr> 
					
					<td><strong>Ship To<font color="red">*</font></strong></td>
				<td><textarea id="bill_to" class="form-control" type="text" name="ship_to"  data-valid="mand"></textarea></td>
					
				<td><strong>Bill To<font color="red">*</font></strong></td>
				<td><textarea id="bill_to" class="form-control" type="text" name="bill_to"  data-valid="mand"></textarea></td>
					</tr> -->
			
			
					
					
						<tr>
	        	<td colspan="4" class="new"><p class="tableHeaderContent" style="font-size: 24px;margin-left: 495px;">Assign Details</p> </td>
	      	</tr>
	      	
	      	
	      	
	      	<tr>
						<td><b>Entity<font color="red">*</font></b></td>
						<td><select id="locDataForLead" name="id_loc"
							class="form-control " disabled="true" data-valid="mand" style="width: 140"
							onChange="DisplaySubDropDownData(this,'slocDataForLead','M_Subloc')">
								<option value="">Select</option>
						</select></td> 
						<td><b>City<font color="red">*</font>
						</b></td>
						<td><select id="slocDataForLead" name="id_sloc" 
							class="form-control " disabled="true" data-valid="mand" style="width: 140" onchange="getDATA(this);SubDropDownDataDisplayforemp('','EmpdataforCordinator','M_Emp_User',this)">
								<option value="">Select</option>
						</select></td>
					</tr>

				<tr>
				
					 <td><strong>GSTN NO.<font color="red">*</font></strong></td>
				<td><input id="gstin_no" class="form-control"  type="text" name="gstin_no" readonly data-valid="mand"></td>
					
					 <td><strong>DL No.<font color="red">*</font></strong></td>
				<td><input id="dl_no" class="form-control"  type="text" name="dl_no" readonly data-valid="mand"></td>
					
					</tr>
					
			<tr>
					<td><strong>Designation<font color="red">*</font></strong></td>	
			      
					  <td><select name="id_design_tagemp" id="dgnForEmp" class="form-control" data-valid="mand" disabled="true"
				    style="width: 140"  onChange="SubDropDownDataDisplaybydesingEmp(this,'EmpdataforField','M_Emp_User')">
				   <option value="">Select</option></select></td>
				
					 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforField"  name="tag_to_emp" disabled="true"
							class="form-control " data-valid="mand" style="width: 140 " onchange="Statelead()">
								<option value="">Select</option>
						</select></td>
					
				</tr>
			
					
				
				
					
				
				</table>
				<div id="createinvoicelineitem">
					<form name="submitLineitemLead" id="submitLineiteminvoice"
						enctype="multipart/form-data">
						
						<table class="table table-bordered Editinvoiceld">
						</table>
						<table class="table table-bordered" id="invoiceDetails">
						</table>
						<table class="table table-bordered" id="invoiceDetails1">
						</table>
							<div style="float:right;margin-right: 22px;">
					<!-- 	<strong style="margin-right: 100px;">Grand Total</strong>  -->
					 <tr><strong class ="hideshow">Mode of Refundable Amount <font color="red">*</font></strong></tr>
					    <tr><select name="pay_mode_rfd" class="form-control" id=pay_mode_rfd  data-valid="mand" onChange=Showcolrfdcheque(this) disabled = "true">
						<!-- <option value="">Select</option> -->
						<option value="NA">NA</option>
						<option value="FOC">FOC</option>
						<option value="Cash">Cash</option>
						<option value="UPI">UPI/Online</option>
						<option value="Credit">Credit</option>
						<option value="Cheque">Cheque</option>
					</select></tr>
					 <tr><strong class ="hideshow">Cheque No.<font color="red"></font></strong></tr>
			     	  <tr><input id=cheque_no_rfd class="form-control" type="text"  name="cheque_no_rfd" readonly></tr> 
						<tr><strong class ="hideshow">Refundable Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="refund_amt" value="0.0" id=refund 
							class="form-control resetAcc FieldResize" onChange="addinTotLead(this)" readonly></input><tr>
						<tr><strong>Transportation Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="trnsprt_amt" value="0.0" id=" "
							class="form-control resetAcc FieldResize" onChange="addinTotLead(this)" readonly></input></tr>
						<tr><strong>Grand Total<font color="red">*</font></strong></tr>
							<tr><input type="text" name="tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly></input></tr>
						<tr><strong><div id="grandtotinword">Zero only</div></strong></tr> 
							
						
							</div> 
						<input type="hidden" name="gr_tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly>
						<table class="table table-bordered" id="invoiceDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="Save" class="form-control"> 
									
									 <input id="id" type="hidden" name="id" value="0" class="form-control">
					
									
									<input type="hidden" name="leadCheck" value="0">
									
										<button name=Save type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="ControlCreateInvoice('Save','displayCreateInvoice','CreateInvoice','submitCreateLead','O_CreateInvoiceLead')">Save</button>
									<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlCreateInvoice('Cancel','displayCreateInvoice','CreateInvoice','submitCreateLead','O_CreateInvoiceLead')">Back</button>
									
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
	</div>
</div>
</section>