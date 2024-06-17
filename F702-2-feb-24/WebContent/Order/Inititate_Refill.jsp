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
		src="All_Js_File/Order/cordinatorlead.js"></script>

<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">
	$(function() {
		
		DisplayInititatedRefill('O_AssignedLlead', 'displayInititatedRefill', 'EditForRefill', '','submitEditForRefill',
				'EditForRefillForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
		DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
			if (status) {
				DisplayDropDownData("M_Emp_User","empdataforCreateLd",function(status) {
						if (status) {
									
							 DisplayDropDownData("M_Designation","dgnForEmp",function(status) {
								if (status) { 
							
									DropDownDataDisplay('M_Source','srcforlead',function (status){
										if(status)
										{
											
											//dispalyLineItemLead('New');
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
		//$('#createPrintorMailPurchaseOrder').hide();
		var currentDate = new Date();
		$( ".datepicker1" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      beforeShow: function(input, inst) {
		            // Disable keyboard input and pasting
		            $(input).prop("readonly", true);
		        },
		      
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
	<div id="displayInititatedRefill">
		<div class="card-body">
			<table width="100%" height="100%">
			<!-- 	<tr>
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
							onclick="DisplayInititatedRefill('O_AssignedLlead', 'displayInititatedRefill', 'EditForRefill', '','submitEditForRefill',
								'EditForRefillForDisplaytd')">Search
						</button>
					</td>
				</tr> -->
				<tr></tr>
			</table>
			<table id="EditForRefillForDisplaytd"
				class="table table-bordered table-hover EditForRefillForDisplaytd">
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
<div id="EditForRefill" style="display: none;">
	
		<form name="submitEditForRefill" id="submitEditForRefill"
			enctype="multipart/form-data" action="Upload_Fil" method="post">
			<div id="EditRefillLead">
				<div class="card-header new">
					<h3 class="card-title1">Create Refill Lead</h3>
				</div>
				
					<table class="table table-bordered" id="leadDetails">
				
				
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
				
				
						  	<tr>  
						     <input type="hidden" name="id_fincance_lead" value="" class="form-control"> 
						     <input type="hidden" name="lead_no" value="" class="form-control"> 
						      <input type="hidden" name="nm_src" value="" class="form-control"> 
			
					
						
						
			    
			    	 	<td><b>Source Code<font color="red">*</font></b></td>
						<td><input id="cd_src" class="form-control"  type="text" name="cd_src" readonly data-valid="mand"></td>
						
						<td class="srcCommonClass" style="display:none"><b>Others<font color="red">*</font>
					<td class="srcCommonClass" style="display:none"><input id="other_src" type="text" name="other_src" value=""
							class="form-control readbaledata srcCommonClass1"  onkeyup="this.value = this.value.toUpperCase();"></td>
					</b></td>
					<td><b>PO.NO<font color="red">*</font></b></td>
						<td><input id="po_no" class="form-control"  type="text" name="po_no"  data-valid="mand"></td>
					</tr>
					
						
					<tr>
					<td><strong>Installation/Refill Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_ld" value="" disabled="true"  placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker1" readonly style="background-color:transparent;font-size: 1em;" data-valid="mand"></td>
		             
						
					
					
					<td><strong>TAT<font color="red">*</font></strong></td>
						<td><input type="time" name="ld_time" value="" 
							class="form-control readbaledata "  data-valid="mand" style="background-color:transparent;font-size: 1em;"></td>
		             </tr>
					<tr>
		             <td><strong>Billing Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_billing" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker1" readonly style="background-color:transparent;font-size: 1em;" data-valid="mand" ></td>
						  
					
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
					 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" data-valid="mand" ></td>
						
					 <td><strong>Attender Contact No.<font color="red">*</font></strong></td>
				<td><input id="alt_pstn_ct" class="form-control" type="text" name="alt_pstn_ct" data-valid="mand"></td>
				
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
							class="form-control" data-valid="mand" disabled="true"  style="width: 140"
							onChange="DisplaySubDropDownData(this,'slocDataForLead','M_Subloc')">
								<option value="">Select</option>
						</select></td>
					
			       <td><b>City<font color="red">*</font>
						</b></td>
						<td><select id="slocDataForLead" name="id_sloc"  disabled="true"
							class="form-control " data-valid="mand" style="width: 140" onchange="getDATA(this)">
								<option value="">Select</option>
						</select></td>
						
						<tr>
						 <td><strong>GSTN NO.<font color="red">*</font></strong></td>
				<td><input id="gstin_no" class="form-control"  type="text" name="gstin_no" data-valid="mand" readonly ></td>
					

				
				
					
					
				<td><strong>DL No.<font color="red">*</font></strong></td>
				<td><input id="dl_no" class="form-control"  type="text" name="dl_no" data-valid="mand" readonly></td>
					<tr>
				 
				  <td><strong>Designation<font color="red">*</font></strong></td>	
				<td><select name="id_design" id="dgnForEmp" class="form-control" data-valid="mand" 
				style="width: 140" onChange="SubDropDownDataDisplaybydesingEmp(this,'EmpdataforCordinator','M_Emp_User')">
				<option value="">Select</option> 
						
				</select></td> 
				
	
						
				</select></td> 
				 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforCordinator" name="to_asign_cordi"
							class="form-control " data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>
				</td>
					
					</tr>
					
			
				<tr>
		           
						
			
			      <input id="state" type="hidden" name="state" value="New" class="form-control">

						
						
					</tr>
					
	      	
	      	
	      	
	      	
	      	
	      	
	      		
		
				
				</table>
				<div id="createLineitemLeadRefill">
					<form name="submitLineitemLeadRefill" id="submitLineitemLeadRefill"
						enctype="multipart/form-data">
						
						<table class="table table-bordered EditForRefillld">
						</table>
						<table class="table table-bordered" id="leadDetails">
						</table>
						<table class="table table-bordered" id="leadDetails1">
						</table>
							<div style="float:right;margin-right: 22px;">
					<!-- 	<strong style="margin-right: 100px;">Grand Total</strong>  -->
					<!--  <tr><strong>Mode of Refundable Amount <font color="red">*</font></strong></tr>
					    <tr><select name="pay_mode_rfd" class="form-control"  data-valid="mand" onChange=Showcolrfdcheque(this) disabled ="true">
						<option value="">Select</option>
						<option value="FOC">FOC</option>
						<option value="Cash">Cash</option>
						<option value="UPI">UPI/Online</option>
						<option value="Credit">Credit</option>
						<option value="Cheque">Cheque</option>
					</select></tr> -->
					<!--  <tr class="srv" style="display:none;"><strong>Cheque No.<font color="red"></font></strong></tr>
			     	  <tr class="srv" style="display:none;"><input id="cheque_no_rfd" class="form-control" type="text"  name="cheque_no_rfd" readonly></tr> 
						<tr><strong>Refundable Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="refund_amt" value="0.0" id="refund "
							class="form-control resetAcc FieldResize" onChange="addinTotLead(this)" readonly></input><tr> -->
						<input type="hidden" name="refund_amt" value="0.00">
						<tr><strong>Transportation Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="trnsprt_amt" value="0.0" id=" "
							class="form-control resetAcc FieldResize" onChange="addinTotLead(this)" ></input></tr>
						<tr><strong>Grand Total<font color="red">*</font></strong></tr>
							<tr><input type="text" name="tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly></input></tr>
						<tr><strong><div id="grandtotinword">Zero only</div></strong></tr> 
							
							<input type="hidden" name="tot_tot" value="0.00">
							</div> 
						<input type="hidden" name="gr_tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly>
						<table class="table table-bordered" id="leadDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="Save" class="form-control"> 
									
									 <input id="id" type="hidden" name="id" value="0" class="form-control">
					
									
									<input type="hidden" name="leadCheck" value="0">
									
										<button name=Refill type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="ControlInitiatedRefillLead('Save','displayInititatedRefill','EditForRefill','submitEditForRefill','O_AssignedLlead')">Save</button>
									<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlInitiatedRefillLead('Cancel','displayInititatedRefill','EditForRefill','submitEditForRefill','O_AssignedLlead')">Back</button>
									
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