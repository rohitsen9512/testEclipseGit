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
		
		DisplayCrLead('O_AssignedLlead', 'displayCrLead', 'EditCrLead', '','submitCrLead',
				'leadCrForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
		//DisplayDropDownDataForVendor('M_Vendor','vendorDataForInvoice','procured',function(status) {
					//if (status) {
									DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
																					if (status) {
																						DisplayDropDownData("M_Emp_User","empdatafortagbyLd",function(status) {
																							if (status) {
																								/* DisplayDropDownData("M_Designation","dgnForEmp",function(status) {
																									if (status) { */		
																								
																								dispalyModifyLineCrItemLead('');
																								  
																						 
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
		$('input[name="dt_tag"]').datepicker("setDate",currentDate);
	
		
	
		
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
			 <!-- 	<h1>
					 Create Lead
					<button type="button" name="create btn" class="btn btn-primary led hideCrled"
						onclick="ControlCrLead('Create','displayCrLead','createCrLead','submitCrLead','O_AssignedLlead')">Create
						Lead</button>
				</h1> --> 
			</div>
			<div class="col-sm-6">
			<!-- 	<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Order</a></li>
					<li class="breadcrumb-item">Cordinator Lead</li>
				</ol> -->
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="card">
	<div id="displayCrLead">
		<div class="card-body">
		<!-- 	<table width="100%" height="100%">
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
							onclick="DisplayCrLead('O_AssignedLlead', 'displayCrLead', 'EditCrLead', '','submitCrLead',
								'leadCrForDisplaytd')">Search
						</button>
					</td>
				</tr>
				<tr></tr>
			</table> -->
			<table id="leadCrForDisplaytd"
				class="table table-bordered table-hover leadCrForDisplaytd">
				
			</table>
		</div>
	</div>
</div>
<section class="content">
<div class="card">
<div id="EditCrLead" style="display: none;">
	
		<form name="submitCrLead" id="submitCrLead">
		<!-- enctype="multipart/form-data" action="Upload_Fil" method="post" -->
			<div id="createLead" >
				<div class="card-header new">
					<h3 class="card-title1">Lead Details</h3>
				</div>
				  <input type="hidden" name="state_aprvdelvr" value="NotApproved" id="state_aprvdelvr">
			     	
				<table class="table table-bordered" id="leadCrDetails">
				  <input type="hidden" name ="tagto_me_state" value="New"></input>
				    <input type="hidden" name ="id_lead_m" value=""></input>
				   <input type="hidden" name ="created_by" value=""></input>
					
					 <tr>
					 <td><b>PO.NO<font color="red">*</font></b></td>
						<td><input id="po_no" class="form-control"  type="text" name="po_no"  data-valid="mand" readonly></td>
						
						<td><strong>PO_Date<font color="red">*</font></strong></td>
				<td><input id="po_date" class="form-control readbaledata datepicker"  readonly placeholder="chose only from the datepicker. Do not paste any data." type="text" name="po_date" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					 
					 
					 </tr>
						<tr>
						 <td><b>Tag By<font color="red">*</font>
					      <td><select id="empdatafortagbyLd" name="tag_by"
							class="form-control" disabled="true"  data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>  
						<td><strong>Lead Id<font color="red">*</font>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" readonly="readonly" data-valid="mand"></td>
						</strong></td>
						</tr>
						<tr>
						
						<td><strong>Create Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_ld" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata"  data-valid="mand" readonly font-size: 1em;"></td>
					
					
			    	 <td><b>Source Code<font color="red">*</font></b></td>
					 <td><input id="cd_src" class="form-control"  type="text" name="cd_src" readonly data-valid="mand"></td>
					</tr>
						<tr>
					<td><strong>TAT<font color="red">*</font></strong></td>
						<td><input type="time" name="ld_time" value="" 
							class="form-control readbaledata "  data-valid="mand"  ></td>
		             <td><strong>Billing Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_billing"  readonly value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand"   ></td>
						  
					</tr>
				<tr>
				  
			<!--      <td><b>Source<font color="red">*</font></b></td>
						<td><select id="srcforlead" name="id_src"
							class="form-control" data-valid="mand" style="width: 140" disabled="true" onchange="getsrcCode(this);">
								<option value="">Select</option>
						</select></td> -->
						
						<td><strong>Patient Name<font color="red">*</font></strong></td>
				     <td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" readonly="readonly" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
				
					 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" readonly="readonly" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					</tr>
					<tr>
					 <td><strong>Attender Name<font color="red">*</font></strong></td>
				<td><input id="att_name" class="form-control" type="text" name="att_name" data-valid="mand" readonly="readonly"></td>
				
					</tr>						
			<tr>
				
					
					 <td><strong>Attender Contact No.<font color="red">*</font></strong></td>
				<td><input id="alt_pstn_ct" class="form-control" type="text" name="alt_pstn_ct" data-valid="mand" readonly="readonly"></td>
					 <td><strong>Email<font color="red"></font></strong></td>
				<td><input id="pstn_email" class="form-control" type="text" name="pstn_email" readonly="readonly" ></td>
					
					</tr>
				
				<tr>
				
					 <td><strong>Adhaar No.<font color="red">*</font></strong></td>
				<td><input id="adhar_no" class="form-control" type="text" name="adhar_no" readonly="readonly" data-valid="mand"></td>
					
					 <td><strong>Address<font color="red">*</font></strong></td>
				<td><input id="address" class="form-control" type="text" name="address" readonly="readonly" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					
					</tr>
				
			<tr>
				
					 <td><strong>Doctor's Name<font color="red">*</font></strong></td>
				<td><input id="nm_dr" class="form-control"  type="text" name="nm_dr" readonly data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					
					 <td><strong>Speciality<font color="red">*</font></strong></td>
				<td><input id="dr_sp" class="form-control"  type="text" name="dr_sp"  readonly data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					
					</tr>
						<tr>
				
					 <td><strong>Clinic/Hospital<font color="red">*</font></strong></td>
				<td><input id="nm_hsptl" class="form-control"  type="text" name="nm_hsptl" readonly data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					
					 <td><strong>Hospital Contact No.<font color="red"></font></strong></td>
				<td><input id="ct_hsptl" class="form-control"  type="text"  readonly name="ct_hsptl"></td>
					
					</tr>
						<tr>
				
					 <td><strong>Hospital Location<font color="red">*</font></strong></td>
				<td><input id="add_hsptl"class="form-control"  type="text" name="add_hsptl" readonly data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
				
				 <td><strong>Remark<font color="red"></font></strong></td>
			       <td><input id="remark" class="form-control"  type="text" name="remark"  onkeyup="this.value = this.value.toUpperCase();"></td>
				</tr>
				   
			       
				 <tr>
	        	<td colspan="4" class="new"><p class="tableHeaderContent" style="font-size: 24px;margin-left: 275px;">Assign Details</p> </td>
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
							class="form-control " disabled="true" data-valid="mand" style="width: 140" onchange="getDATA(this)">
								<option value="">Select</option>
						</select></td>
							<!-- <td><select id="slocDataForLead" name="id_sloc" 
							class="form-control " disabled="true" data-valid="mand" style="width: 140" onchange="getDATA(this); SubDropDownDataDisplay(this,'dgnForEmp','M_Designation')">
								<option value="">Select</option>
						</select></td> -->
					</tr>

				<tr>
				
					 <td><strong>GSTN NO.<font color="red">*</font></strong></td>
				<td><input id="gstin_no" class="form-control"  type="text" name="gstin_no" readonly="readonly" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					
					 <td><strong>DL No.<font color="red">*</font></strong></td>
				<td><input id="dl_no" class="form-control"  type="text" name="dl_no"  readonly="readonly" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
					
					</tr>
					
			
				<tr>
					<td><strong>Designation<font color="red">*</font></strong></td>	
			        <td><select name="id_design_tagemp" id="dgnForEmp" class="form-control" data-valid="mand" 
				    style="width: 140" onChange="SubDropDownDataDisplayforemp(this,'EmpdataforField','M_Emp_User',$('#slocDataForLead').val())">
				   <option value="">Select</option></select></td> 
				  <!--  <td><select name="id_design_tagemp" id="dgnForEmp" class="form-control" data-valid="mand" 
				    style="width: 140"  onChange="SubDropDownDataDisplaybydesingEmp(this,'EmpdataforField','M_Emp_User')">
				   <option value="">Select</option></select></td> -->
					
					 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforField" name="tag_to_emp"
							class="form-control " data-valid="mand" style="width: 140 " onchange="Statelead()">
								<option value="">Select</option>
						</select></td>
					
				</tr>
				<tr>
				
					<td><strong>State<font color="red"></font></strong></td>
					    <td><input id="state" class="form-control"  type="text" name="state" value="New" readonly data-valid="mand"></td>
			     	<!-- <td><select name="state" id="state" class="form-control" data-valid="mand">
						<option value="New">New</option>
						<option value="Inprogress">Inprogress</option>
						<option value="Closed">Closed</option>
				   </select></td>   -->
				   
				   <!--  <td><strong>Assing Date<font color="red">*</font></strong></td> -->
			<td><input type="hidden" name="dt_tag" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand" readonly font-size: 1em;"></td>
	      	
	      		
			<!-- 	<tr>
			   <td colspan="4" class="new"><p class="tableHeaderContent" style="font-size: 24px;margin-left: 490px;">Amount Details</p> </td>
	      	</tr>
	      			<tr>
				
					 <td><strong>Refundable Amount<font color="red">*</font></strong></td>
				<td><input id="refund_amt" class="form-control"  type="text" name="refund_amt"  value="0.0" data-valid="mand" readonly></td>
					
					 <td><strong>Transportation Amount<font color="red"></font></strong></td>
				<td><input id="trnsprt_amt" class="form-control"  type="text" name="trnsprt_amt" value="0.0" data-valid="mand" readonly></td>
					
					</tr> -->
				</table>
				<div id="createLineitemCrLead" style="display: none;">
					<form name="submitCrLineitemLead" id="submitCrLineitemLead"
						enctype="multipart/form-data">
						<!-- <button name="save22" type="button" style=""
							class="btn btn-primary" onclick="dispalyCrLineItemLead('Save')">Add</button> -->
						<table class="table table-bordered EditCrleadOrders">
						</table>
						<table class="table table-bordered" id="leadCrDetails">
						</table>
						<table class="table table-bordered" id="leadCrDetails1">
						</table>
						
							<div style="float:right;margin-right: 22px;">
					<!-- 	<strong style="margin-right: 100px;">Grand Total</strong>  -->
					 <tr><strong  class ="hideshow">Mode of Refundable Amount <font color="red">*</font></strong></tr>
					    <tr><select name="pay_mode_rfd"  id = pay_mode_rfd class="form-control"  data-valid="mand" onChange=Showcolrfdcheque(this) disabled="true">
						<!-- <option value="">Select</option> -->
						<option value="NA">NA</option>
						<option value="FOC">FOC</option>
						<option value="Cash">Cash</option>
						<option value="UPI">UPI/Online</option>
						<option value="Credit">Credit</option>
						<option value="Cheque">Cheque</option>
					</select></tr>
					 <tr ><strong class ="hideshow" >Cheque No.<font color="red"></font></strong></tr>
			     	  <tr><input id="cheque_no_rfd" class="form-control" type="text"  name="cheque_no_rfd"  readonly></tr> 
						<tr><strong class ="hideshow">Refundable Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="refund_amt" value="0.0" id=refund 
							class="form-control resetAcc FieldResize" readonly onChange="addinTotLead(this)"></input><tr>
						<tr><strong>Transportation Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="trnsprt_amt" value="0.0" id=" trnsprt"
							class="form-control resetAcc FieldResize" readonly onChange="addinTotLead(this)" ></input></tr>
						<tr><strong>Grand Total<font color="red">*</font></strong></tr>
					
							<tr><input type="text" name="tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly></input></tr>
								<tr><strong><div id="grandtotinword">Zero only</div></strong></tr> 
						
							
						
							</div> 
						<input type="hidden" name="gr_tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly>
						<table class="table table-bordered" id="leadCrrDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="TagLead" class="form-control"> 
									<!-- <input type="hidden"name="invoice_file" value="" class="form-control"> -->
									 <input id="id" type="hidden" name="id" value="0" class="form-control">
							<!-- 		<input id="invoiceId" type="hidden" name="invoiceId" value=""
									class="form-control"> -->
									<!--  <input type="hidden"
									name="nm_acc_asset" value="Asset" class="form-control"> -->
									<input type="hidden" name="leadCrCheck" value="0">
									<button name="TagLead" type="button" style="margin-left: 420px;"
										class="btn btn-primary led"
										onclick="ControlCrLead('TagLead','displayCrLead','EditCrLead','submitCrLead','O_AssignedLlead','')">Tag</button>
									
									<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlCrLead('Cancel','displayCrLead','EditCrLead','submitCrLead','O_AssignedLlead','')">Back</button>
									
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
	</div>
<section>
