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
<script type="text/javascript"
		src="All_Js_File/Order/cordinatorlead.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">
	$(function() {
		
		DisplayLead2('O_Lead', 'displayLead', 'createLead', 'submitLead',
				'leadForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
		//DisplayDropDownDataForVendor('M_Vendor','vendorDataForInvoice','procured',function(status) {
					//if (status) {
					DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
																if (status) {
																	DisplayDropDownData("M_Emp_User","empdataforCreateLd",function(status) {
																			if (status) {
																				DisplayDropDownData("M_Exchange","serialnoDivForlead",function(status) {
																					if (status) {
																								 
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
			dateFormat : "yy-mm-dd",
			autoSize : true,
			altFormat : "yy-mm-dd",
		});
		$('input[name="date"]').datepicker("setDate", currentDate);
		
		
	});

</script>
<script>
</script>
<!-- Content Wrapper. Contains page content -->

<div class="card">
	<div id="displayLead">
		<div class="card-body">
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
					       <td><b>Exchange By<font color="red">*</font>
					<td><select id="empdataforCreateLd" name="created_by"
							class="form-control"  data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td> 
<!-- 					       <td><b>Equipment Status:<font color="red">*</font>
					<td><select id="equip_status" name="equip_status"
							class="form-control"  data-valid="mand" style="width: 140">
								<option value="">Select</option>
								<option value="out for Servicing">Out For Service</option>
								<option value="Rental">On rent</option>
								<option value="in_store">In Store</option>
								<option value="Exchange">Exchange</option>
						</select></td>  -->						
						<td><strong>Date<font color="red">*</font></strong></td>
						<td><input type="text" name="date" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker1"  data-valid="mand" style="background-color:transparent;font-size: 1em;"></td>
						</tr> 
				
				
						<!-- <td><strong>Lead Id<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" data-valid="mand"></td> -->
						  	<tr>  
						     <input type="hidden" name="id_fincance_lead" value="" class="form-control"> 
						     <input type="hidden" name="lead_no" value="" class="form-control"> 
						      <input type="hidden" name="nm_src" value="" class="form-control"> 
			
						</tr>

					<tr>
		               <td><strong>Old Serial Number<font color="red">*</font></strong></td>
				       <td><input id="sr_no" class="form-control" type="text" name="sr_no" data-valid="mand"></td>
		               <td><strong>New Serial Number<font color="red">*</font></strong></td>
					   <td><select style="width: 120px !important;" id="serialnoDivForlead" name="sr_no_new" class="form-control" data-valid="">
					<option value="">Select</option></select>
				</td> 
				       
					</tr>


					
					<tr>
		               <td><strong>Patient Name<font color="red">*</font></strong></td>
				       <td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" data-valid="mand"></td>
		               <td><strong>Product<font color="red">*</font></strong></td>
				       <td><input id="nm_prod" class="form-control" type="text" name="nm_prod" data-valid="mand"></td>
				       
					</tr>
					<tr>
				<td><strong>Attender Name<font color="red">*</font></strong></td>
				<td><input id="att_name" class="form-control" type="text" name="att_name" data-valid="mand" ></td>					
					 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" data-valid="mand"></td>
					
					</tr>
					
				<tr>
					 <td><strong>Attender Contact No.<font color="red">*</font></strong></td>
				<td><input id="alt_pstn_ct" class="form-control" type="text" name="alt_pstn_ct" data-valid="mand"></td>
				<td><strong>Email<font color="red"></font></strong></td>
				<td><input id="pstn_email" class="form-control" type="text" name="pstn_email" ></td>
	
									
				
				
					
					
				</tr>
				<tr>
					 <td><strong>Aadhaar No.<font color="red">*</font></strong></td>
				<td><input id="adhar_no" class="form-control" type="text" name="adhar_no" data-valid="mand"></td>
				 <td><strong>Address<font color="red">*</font></strong></td>
				<td><input id="address" class="form-control" type="text" name="address" data-valid="mand"></td>				   
					
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
							class="form-control " data-valid="mand" style="width: 140" onchange="getDATA(this);SubDropDownDataDisplayforemp('','EmpdataforCordinator','M_Emp_User',this)">
								<option value="">Select</option>
						</select></td>
					<!-- 	<td><select id="slocDataForLead" name="id_sloc" 
							class="form-control " data-valid="mand" style="width: 140" onchange="getDATA(this); SubDropDownDataDisplay(this,'dgnForEmp','M_Designation')">
								<option value="">Select</option>
						</select></td> -->
						<tr>
						 <td><strong>GSTN NO.<font color="red">*</font></strong></td>
				<td><input id="gstin_no" class="form-control"  type="text" name="gstin_no" data-valid="mand"></td>
					

				
				
					
					
				<td><strong>DL No.<font color="red">*</font></strong></td>
				<td><input id="dl_no" class="form-control"  type="text" name="dl_no" data-valid="mand"></td>
					<tr>
				<!-- 	 <td><strong>Designation<font color="red">*</font></strong></td>
					 <td><input id="id_design" class="form-control" value="Coordinator" type="text" name="id_design" data-valid="mand"></td> -->
					
			<!-- 	<td><select name="id_design" id="dgnForEmp" class="form-control" data-valid="mand" 
				style="width: 140" onChange="SubDropDownDataDisplayforemp(this,'EmpdataforCordinator','M_Emp_User',$('#slocDataForLead').val())">
				<option value="">Select</option> 
						
				</select>-->
				
					
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
						<table class="table table-bordered EditleadOrders">
						</table>
						<table class="table table-bordered" id="leadDetails">
						</table>
						<table class="table table-bordered" id="leadDetails1">
						</table>
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
										onclick="ControlLead('Save','displayLead','createLead','submitLead','O_Lead')">Save</button>
									<button name="update" type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="ControlLead('Save','displayLead','createLead','submitLead','O_Lead')">Save</button>
										
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
				<input type="hidden" name="lead_no" value="" class="form-control"> 
													<button name="save" type="button" style="margin-left: 420px;"
										class="btn btn-primary led"
										onclick="ControlLead('Save','displayLead','createLead','submitLead','M_Exchange')">Save</button>
									<button name="update" type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="ControlLead('Save','displayLead','createLead','submitLead','M_Exchange')">Save</button>
										
									<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlLead('Cancel','displayLead','createLead','submitLead','M_Exchange')">Back</button>
			</div>
		</form>
	</div>
</section>
