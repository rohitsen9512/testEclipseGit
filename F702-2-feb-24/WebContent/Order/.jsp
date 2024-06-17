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
																						
																						/* DisplayDropDownData('M_Designation','dgnForEmp',function (status){
																							if(status)
																							{ */
																								
																								dispalyModifyLineCrItemLead('');
																								/* }
																						}); */
																					}
																				});
																
					
				
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
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Order</a></li>
					<li class="breadcrumb-item">Cordinator Lead</li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="card">
	<div id="displayCrLead">
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
							onclick="DisplayCrLead('O_AssignedLlead','displayCrLead','creaCrteLead','','leadCrForDisplaytd')">Search
						</button>
					</td>
				</tr>
				<tr></tr>
			</table>
			<table id="leadCrForDisplaytd"
				class="table table-bordered table-hover leadCrForDisplaytd">
				
			</table>
		</div>
	</div>
</div>

<div class="card">
<div id="EditCrLead" style="display: none;">
	
		<form name="submitCrLead" id="submitCrLead">
		<!-- enctype="multipart/form-data" action="Upload_Fil" method="post" -->
			<div id="createLead" >
				<div class="card-header new">
					<h3 class="card-title1">Lead Details</h3>
				</div>
				<table class="table table-bordered" id="leadCrDetails">
					<tr>
						<td><strong>Lead Id<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" readonly="readonly" data-valid="mand"></td>
						<td><strong>Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dtld_create" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand" readonly style="background-color:transparent;font-size: 1em;"></td>
					</tr>
				
					<tr>
				   <td><strong>Saurce<font color="red"></font></strong></td>
			     	<td><select name="src" class="form-control" disabled="true" data-valid="mand">
						<option value="jd">Just dial</option>
						<option value="cl">Call</option>
						<option value="tmp">Template</option>
						<option value="hord">Hording</option>
						<option value="Rfrnc">Reference from Patient</option>
						<option value="gogl">Google</option>
					</select></td>
					
					 <td><strong>Patient Name<font color="red">*</font></strong></td>
				<td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" readonly="readonly" data-valid="mand"></td>
				
					</tr>
					
			<tr>
				
					 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" readonly="readonly" data-valid="mand"></td>
					
					 <td><strong>Email<font color="red">*</font></strong></td>
				<td><input id="pstn_email" class="form-control" type="text" name="pstn_email" readonly="readonly" data-valid="mand"></td>
					
					</tr>
				
				<tr>
				
					 <td><strong>Adhaar No.<font color="red">*</font></strong></td>
				<td><input id="adhar_no" class="form-control" type="text" name="adhar_no" readonly="readonly" data-valid="mand"></td>
					
					 <td><strong>Address<font color="red">*</font></strong></td>
				<td><input id="address" class="form-control" type="text" name="address" readonly="readonly" data-valid="mand"></td>
					
					</tr>
				
					<tr>
						<td><b>State<font color="red">*</font></b></td>
						<td><select id="locDataForLead" name="id_loc"
							class="form-control " disabled="true" data-valid="mand" style="width: 140"
							onChange="DisplaySubDropDownData(this,'slocDataForLead','M_Subloc')">
								<option value="">Select</option>
						</select></td>
						<td><b>City<font color="red">*</font>
						</b></td>
						<td><select id="slocDataForLead" name="id_sloc" 
							class="form-control " disabled="true" data-valid="mand" style="width: 140" onchange="getDATA(this); SubDropDownDataDisplay(this,'dgnForEmp','M_Designation')">
								<option value="">Select</option>
						</select></td>
					</tr>

				<tr>
				
					 <td><strong>GSTN NO.<font color="red">*</font></strong></td>
				<td><input id="gstin_no" class="form-control"  type="text" name="gstin_no" readonly="readonly" data-valid="mand"></td>
					
					 <td><strong>DL No.<font color="red">*</font></strong></td>
				<td><input id="dl_no" class="form-control"  type="text" name="dl_no"  readonly="readonly" data-valid="mand"></td>
					
					</tr>
					
			
				<tr>
				 <td><strong>Designation<font color="red">*</font></strong></td>
				<td>
				<select name="id_design" id="dgnForEmp" class="form-control" data-valid="mand" 
				style="width: 140" onChange="SubDropDownDataDisplayforemp(this,'EmpdataforField','M_Emp_User',$('#slocDataForLead').val())">
				<option value="">Select</option>
						
				</select>
				</td>
					 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforField" name="assing_to_emp"
							class="form-control " data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>
					</tr>
					<tr>
					
					</td>
					
					 <td><strong>Remark<font color="red">*</font></strong></td>
			       <td><input id="remark" class="form-control"  type="text" name="remark" data-valid="mand"></td></tr>
						<tr>
	        	<td colspan="4" class="new"><p class="tableHeaderContent" style="font-size: 24px;margin-left: 275px;">Doctor's Details</p> </td>
	      	</tr>
	      	
	      		<tr>
				
					 <td><strong>Doctor's Name<font color="red">*</font></strong></td>
				<td><input id="nm_dr" class="form-control"  type="text" name="nm_dr" readonly data-valid="mand"></td>
					
					 <td><strong>Speciality<font color="red">*</font></strong></td>
				<td><input id="dr_sp" class="form-control"  type="text" name="dr_sp"  readonly data-valid="mand"></td>
					
					</tr>
						<tr>
				
					 <td><strong>Clinic/Hospital<font color="red">*</font></strong></td>
				<td><input id="nm_hsptl" class="form-control"  type="text" name="nm_hsptl" readonly data-valid="mand"></td>
					
					 <td><strong>Hospital Contact No.<font color="red"></font></strong></td>
				<td><input id="ct_hsptl" class="form-control"  type="text"  readonly name="ct_hsptl"></td>
					
					</tr>
						<tr>
				
					 <td><strong>Hospital Location<font color="red">*</font></strong></td>
				<td><input id="add_hsptl"class="form-control"  type="text" name="add_hsptl" readonly data-valid="mand"></td>
				</tr>
				
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
						<input type="hidden" name="gr_tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly>
						<table class="table table-bordered" id="leadCrrDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="Assign" class="form-control"> 
									<!-- <input type="hidden"name="invoice_file" value="" class="form-control"> -->
									 <input id="id" type="hidden" name="id" value="0" class="form-control">
							<!-- 		<input id="invoiceId" type="hidden" name="invoiceId" value=""
									class="form-control"> -->
									<!--  <input type="hidden"
									name="nm_acc_asset" value="Asset" class="form-control"> -->
									<input type="hidden" name="leadCrCheck" value="0">
									<button name="Assign" type="button" style="margin-left: 420px;"
										class="btn btn-primary led"
										onclick="ControlCrLead('Assign','displayCrLead','EditCrLead','submitCrLead','O_AssignedLlead','')">Assign</button>
									<!-- <button name="update" type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="ControlCrLead('Update','displayCrLead','EditCrLead','submitCrLead','O_AssignedLlead','')">Update</button> -->
										<!-- <button name=Proceed type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="ControlCrLead('Proceed','displayCrLead','EditCrLead','submitCrLead','O_AssignedLlead','')">Assign Lead</button> -->
									<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlCrLead('Cancel','displayCrLead','EditCrLead','submitCrLead','O_AssignedLlead','')">Back</button>
									<!-- <button name="delete" type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="DeleteCrLead('O_AssignedLlead','displayLead','createLead',document.getElementById('id').value)">Delete</button> -->
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
	</div>

