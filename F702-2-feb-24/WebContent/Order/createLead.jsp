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
		src="All_Js_File/Order/Lead.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">
	$(function() {
		
		DisplayLead('O_Lead', 'displayLead', 'createLead', '',
				'leadForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
		//DisplayDropDownDataForVendor('M_Vendor','vendorDataForInvoice','procured',function(status) {
					//if (status) {
					DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
																if (status) {
																	DisplayDropDownData("M_Emp_User","empdataforCreateLd",function(status) {
																			if (status) {
																						
																				 DisplayDropDownData("M_Designation","dgnForEmp",function(status) {
																					if (status) { 
																				
																						DropDownDataDisplay('M_Source','srcforlead',function (status){
																							if(status)
																							{
																								
																								dispalyLineItemLead('New');
																								}
																						}); 
																					}
																				 });
																					 }
																				}); 
	                                                                    }
                                                                     }); 
																
					
				
	});

	$(function() {
		
		
				
		var currentDate = new Date();
		$(".datepicker1").datepicker({
			yearRange : '1985:2025',
			changeMonth : true,
			changeYear : true,
			dateFormat : "dd/mm/yy",
			autoSize : true,
			altFormat : "dd/mm/yy",
			beforeShow: function(input, inst) {
	            // Disable keyboard input and pasting
	            $(input).prop("readonly", true);
	        },
		});
		$('input[name="dt_ld"]').datepicker("setDate", currentDate);
		currentDate.setMonth(currentDate.getMonth() - 1);
		$('input[name="po_date"]').datepicker("setDate", currentDate);
		
		
		
	});

	
	$(function() {
		debugger;
	    var currentDate = new Date();

	    $(".1datepicker").datepicker({
	        yearRange: '1985:2025',
	        changeMonth: true,
	        changeYear: true,
	        dateFormat: "dd/mm/yy",
	        autoSize: true,
	        altFormat: "dd/mm/yy",
	        minDate: 0,
	        beforeShow: function(input, inst) {
	            // Disable keyboard input and pasting
	            $(input).prop("readonly", true);
	        },// Set minimum date to today
	    });

	    $('input[name="dt_ld"]').datepicker("setDate", currentDate);
	    $('input[name="dt_to"]').datepicker("setDate", currentDate);

	    // Set minimum date to today for the 'dt_frm' input
	    $('input[name="dt_frm"]').datepicker({
	        dateFormat: "dd/mm/yy",
	        autoSize: true,
	        altFormat: "dd/mm/yy",
	        minDate: 0,
	        beforeShow: function(input, inst) {
	            // Disable keyboard input and pasting
	            $(input).prop("readonly", true);
	        },// Set minimum date to today
	    }).datepicker("setDate", currentDate);

	    /*  
	    // Example for 'dt_install' and 'dtexp_install' inputs
	    $('input[name="dt_install"]').datepicker("setDate", currentDate);
	    currentDate.setDate(currentDate.getDate() + 10);
	    $('input[name="dtexp_install"]').datepicker("setDate", currentDate);
	    */
	});	
	
	//Auto Completion of Item Name
	$("#dynData0").easyAutocomplete(option);
	$("#dynData1").easyAutocomplete(option);
	$("#dynData2").easyAutocomplete(option);
	$("#dynData3").easyAutocomplete(option);
	
	var option = {

			url: "InvoiceScanFile/Customer_history.json",
			 
			getValue: "pstn_ct",
			
			template: {
				
				type: "custom",
					method: function(value, item) {
					return value;
					console.log(value);
				}
			},
			list: {
				maxNumberOfElements: 50,
				match: {
					enabled: true
				}
			},
		};

</script>
<script>
</script>
<!-- Content Wrapper. Contains page content -->
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>
					<!--  Create Lead-->
					<button type="button" name="create btn" class="btn btn-primary led hideled"
						onclick="ControlLead('Create','displayLead','createLead','submitLead','O_Lead')">Create
						Lead</button>
				</h1>
			</div>
			<div class="col-sm-6">
			
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="card">
	<div id="displayLead">
		<div class="card-body">
			<!-- <table width="100%" height="100%">
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
							onclick="DisplayLead('O_Lead','displayLead','createLead','','leadForDisplaytd')">Search
						</button>
					</td>
				</tr>
				<tr></tr>
			</table> -->
			<table id="leadForDisplaytd"
				class="table table-bordered table-hover leadForDisplaytd">
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
		<form name="submitLead" id="submitLead"
			enctype="multipart/form-data" action="Upload_Fil" method="post">
			<div id="createLead" style="display: none;">
				<div class="card-header new">
					<h3 class="card-title1">Lead Details</h3>
				</div>
			
				<table class="table table-bordered" id="leadDetails">
				
					<tr>
					 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct"  id="dynData" data-valid="mand"  onchange="changeEventHandlercustomerhist()" autocomplete="off"></td>
						
					 <td><strong>Attender Contact No.<font color="red">*</font></strong></td>
				<td><input id="alt_pstn_ct" class="form-control" type="text" name="alt_pstn_ct" data-valid="mand"></td>
				
					</tr>
				<tr>
					       <td><b>Created By<font color="red">*</font>
					<td><select id="empdataforCreateLd" name="created_by" id="created_by"
							class="form-control"  data-valid="mand" style="width: 140" disabled="true">
								<option value="">Select</option>
						</select></td> 
						
									
				 	<td><b>Source<font color="red">*</font></b></td>
						<td><select id="srcforlead" name="id_src"
							class="form-control" data-valid="mand" style="width: 140" onchange="getsrcCode(this);checkotherSRC($(this).val(),'srcCommonClass','O_Lead')">
								<option value="">Select</option>
						</select></td>
						</tr> 
				
				
						<!-- <td><strong>Lead Id<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" data-valid="mand"></td> -->
						  	<tr>  
						     <input type="hidden" name="id_fincance_lead" value="" class="form-control"> 
						     <input type="hidden" name="lead_no" value="" class="form-control"> 
						      <input type="hidden" name="nm_src" value="" class="form-control"> 
			
					
						
						
			    
			    	 	<td><b>Source Code<font color="red">*</font></b></td>
						<td><input id="cd_src" class="form-control"  type="text" name="cd_src" readonly data-valid="mand"></td>
						
						<td class="srcCommonClass" style="display:none"><b>Others<font color="red">*</font>
					<td class="srcCommonClass" style="display:none"><input id="other_src" type="text" name="other_src" value=""
							class="form-control readbaledata srcCommonClass1" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					</b></td>
					<td><b>PO.NO<font color="red">*</font></b></td>
						<td><input id="po_no" class="form-control"  type="text" name="po_no"  data-valid="mand"></td>
					</tr>
					
						
					<tr>
					<td><strong>Installation/Refill Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_ld" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker1"  readonly style="background-color:transparent;font-size: 1em;" data-valid="mand" ></td>
		             
						
					
					
					<td><strong>TAT<font color="red">*</font></strong></td>
						<td><input type="time" name="ld_time" value="" 
							class="form-control readbaledata "  data-valid="mand" style="background-color:transparent;font-size: 1em;"></td>
		             </tr>
					<tr>
		             <td><strong>Billing Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_billing" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker1"   readonly style="background-color:transparent;font-size: 1em;" data-valid="mand"></td>
						  
					
					<td><strong>Patient Name<font color="red">*</font></strong></td>
				       <td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();" ></td>
					 </tr>
					<tr>
						
					 <td><strong>Attender Name<font color="red">*</font></strong></td>
				<td><input id="att_name" class="form-control" type="text" name="att_name" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
				
				<td><strong>PO_Date<font color="red">*</font></strong></td>
				<td><input id="po_date" class="form-control readbaledata datepicker1" readonly style="background-color:transparent;font-size: 1em;" placeholder="chose only from the datepicker. Do not paste any data." type="text" name="po_date" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
				
					</tr>					 
					

					<tr>
				
				<td><strong>Email<font color="red"></font></strong></td>
				<td><input id="pstn_email" class="form-control" type="text" name="pstn_email" value="NA" data-valid="mand" ></td>
					
					 <td><strong>Adhaar No.<font color="red">*</font></strong></td>
				<td><input id="adhar_no" class="form-control" type="text" name="adhar_no" value ="NA" data-valid="mand" maxlength="12"></td>	
			</tr>
			<tr>
			 <td><strong>Address<font color="red">*</font></strong></td>
				<td><input id="address" class="form-control" type="text" name="address" value="CHENNAI " data-valid="mand" onkeyup="this.value = this.value.toUpperCase();" ></td>	
				 	
				  <td><strong>Doctor's Name<font color="red">*</font></strong></td>
				<td><input id="nm_dr" class="form-control"  type="text" name="nm_dr" value="NA" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();" ></td>
				</tr> <tr>	
				<td><strong>Speciality<font color="red">*</font></strong></td>
				 <td><input id="dr_sp" class="form-control"  type="text" name="dr_sp" value="NA" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					
					 <td><strong>Clinic/Hospital<font color="red">*</font></strong></td>
				<td><input id="nm_hsptl" class="form-control"  type="text" name="nm_hsptl"  value="NA" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					</tr>
					<tr>
					 <td><strong>Hospital Contact No.<font color="red"></font></strong></td>
				<td><input id="ct_hsptl" class="form-control"  type="text" name="ct_hsptl" value="NA" ></td>
					
						
					
					
					
					
					 <td><strong>Hospital Location<font color="red">*</font></strong></td>
				<td><input id="add_hsptl"class="form-control"  type="text" name="add_hsptl" data-valid="mand" value="NA" onkeyup="this.value = this.value.toUpperCase();"></td>
					
		</tr>
				
				

			
					
			
			
					
			<tr>
			   <td colspan="4" class="new"><p class="tableHeaderContent" style="font-size: 24px;margin-left:495px;">Assign Details</p> </td>
	      	</tr>
	      	
	      	
	      	<tr>
				
					
					 
				 	<td><b>Entity<font color="red">*</font></b></td>
						<td><select id="locDataForLead" name="id_loc"
							class="form-control" data-valid="mand" style="width: 140"
							onChange="DisplaySubDropDownData(this,'slocDataForLead','M_Subloc')">
								<option value="">Select</option>
						</select></td>
					
				
					
					
						<td><b>City<font color="red">*</font>
						</b></td>
						<td><select id="slocDataForLead" name="id_sloc" 
							class="form-control " data-valid="mand" style="width: 140" onchange="getDATA(this)">
								<option value="">Select</option>
						</select></td>
							<!-- <td><select id="slocDataForLead" name="id_sloc" 
							class="form-control " data-valid="mand" style="width: 140" onchange="getDATA(this);SubDropDownDataDisplayforemp('','EmpdataforCordinator','M_Emp_User',this)">
								<option value="">Select</option>
						</select></td> -->
					<!-- 	<td><select id="slocDataForLead" name="id_sloc" 
							class="form-control " data-valid="mand" style="width: 140" onchange="getDATA(this); SubDropDownDataDisplay(this,'dgnForEmp','M_Designation')">
								<option value="">Select</option>
						</select></td> -->
						<tr>
						 <td><strong>GSTN NO.<font color="red">*</font></strong></td>
				<td><input id="gstin_no" class="form-control"  type="text" name="gstin_no" data-valid="mand" readonly ></td>
					

				
				
					
					
				<td><strong>DL No.<font color="red">*</font></strong></td>
				<td><input id="dl_no" class="form-control"  type="text" name="dl_no" data-valid="mand" readonly></td>
					<tr>
				 	 <!--<td><strong>Designation<font color="red">*</font></strong></td>
					 <td><input id="id_design" class="form-control" value="Coordinator" type="text" name="id_design" data-valid="mand"></td> -->
				
				  <td><strong>Designation<font color="red">*</font></strong></td>	
				<td><select name="id_design" id="dgnForEmp" class="form-control" data-valid="mand" 
				style="width: 140" onChange="SubDropDownDataDisplaybydesingEmp(this,'EmpdataforCordinator','M_Emp_User')">
				<option value="">Select</option> 
						
				</select></td> 
				
				<!--  <td><select name="id_design" id="dgnForEmp" class="form-control" data-valid="mand" 
				style="width: 140" onChange="SubDropDownDataDisplayforemp(this,'EmpdataforCordinator','M_Emp_User',$('#slocDataForLead').val())">
				<option value="">Select</option> --> 
						
				</select></td> 
				 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforCordinator" name="to_asign_cordi"
							class="form-control " data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>
				</td>
					
					</tr>
					
			
				<tr>
		           
						
				  <!--   <td><strong>State<font color="red"></font></strong></td>
				    <td><input id="state" class="form-control"  type="text" name="state" value="New" readonly data-valid="mand"></td> -->
			      <input id="state" type="hidden" name="state" value="New" class="form-control">
<!-- <td><select name="state" class="form-control"  disabled="true" data-valid="mand">
						<option value="New">New</option>
						<option value="Inprogress">Inprogress</option>
						<option value="Closed">Closed</option>
					
					</select></td>   -->
						
						
					</tr>
					
	      	
	      	
	      	
	      	
	      	
	      	
	      		
				
				<!-- <tr>
			   <td colspan="4" class="new"><p class="tableHeaderContent" style="font-size: 24px;margin-left: 490px;">Amount Details</p> </td>
	      	</tr>
	      			<tr>
				
					 <td><strong>Refundable Amount<font color="red">*</font></strong></td>
				<td><input id="refund_amt" class="form-control"  type="text" name="refund_amt"  value="0.0" data-valid="mand"></td>
					
					 <td><strong>Transportation Amount<font color="red"></font></strong></td>
				<td><input id="trnsprt_amt" class="form-control"  type="text" name="trnsprt_amt" value="0.0"></td>
					
					</tr> -->
	      	
				
				</table>
				<div id="createLineitemLead" style="display: none;">
					<form name="submitLineitemLead" id="submitLineitemLead"
						enctype="multipart/form-data">
						<button name="save22" type="button" style=""
							class="btn btn-primary" onclick="dispalyLineItemLead('Save')">Add</button>
						<table class="table table-bordered EditleadOrders">
						</table>
						<table class="table table-bordered" id="leadDetails">
						</table>
						<table class="table table-bordered" id="leadDetails1">
						</table>
						<div style="float:right;margin-right: 22px;">
					<!-- - 	<strong style="margin-right: 100px;">Grand Total</strong>  -->
					    <tr><strong class ="hideshow">Mode of Refundable Amount <font color="red">*</font></strong></tr>
					    <tr><select name="pay_mode_rfd"  id = pay_mode_rfd class="form-control"  data-valid="mand" onChange=Showcolrfdcheque(this)>
						<option value="">Select</option>
						<option value="NA">NA</option>
						<option value="FOC">FOC</option>
						<option value="Cash">Cash</option>
						<option value="UPI">UPI/Online</option>
						<option value="Credit">Credit</option>
						<option value="Cheque">Cheque</option>
					</select></tr>
					 <tr><strong strong class ="hideshow" >Cheque No.<font color="red"></font></strong></tr>
			     	  <tr ><input id="cheque_no_rfd"  class="form-control"    type="text" name="cheque_no_rfd" ></tr> 
						<tr><strong strong class ="hideshow">Refundable Amount<font color="red">*</font></strong></tr>
						<tr ><input type="text" name="refund_amt" value="0.00"  id=refund 
							class="form-control resetAcc FieldResize"   onChange="addinTotLead(this)"></input><tr>
						<tr><strong>Transportation Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="trnsprt_amt" value="0.00" id=" "
							class="form-control resetAcc FieldResize" onChange="addinTotLead(this)" ></input></tr>
						<tr><strong>Grand Total<font color="red">*</font></strong></tr>
							<tr><input type="text" name="tot" value="0.00" id=" "
							class="form-control resetAcc FieldResize" readonly></input></tr>
							<tr><strong><div id="grandtotinword">Zero only</div><strong></tr> 
						
							<input type="hidden" name="tot_tot" value="0.00">
						
							</div> 
						<table class="table table-bordered" id="leadDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="Save" class="form-control"> 
									<!-- <input type="hidden"name="invoice_file" value="" class="form-control"> -->
									 <input id="id" type="hidden" name="id" value="0" class="form-control">
							<!-- 		<input id="invoiceId" type="hidden" name="invoiceId" value=""
									class="form-control"> -->
									<!--  <input type="hidden"
									name="nm_acc_asset" value="Asset" class="form-control"> -->
									<input type="hidden" name="leadCheck" value="0">
									<button name="save" type="button" style="margin-left: 420px;"
										class="btn btn-primary led"
										onclick="ControlLead('Save','displayLead','createLead','submitLead','O_Lead','Lead has created successfullly.',document.getElementById('id').value,'')">Save</button>
									<button name="update" type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="ControlLead('Update','displayLead','createLead','submitLead','',document.getElementById('id').value,'Lead has updated successfully.')">Update</button>
										
									<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlLead('Cancel','displayLead','createLead','submitLead','O_Lead')">Back</button>
									<!-- <button name="delete" type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="DeleteLead('O_Lead','displayLead','createLead',document.getElementById('id').value)">Delete</button> -->
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
	</div>
</section>
