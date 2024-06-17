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
		
		DisplayApproveLead('O_Lead', 'displayAprvLead', 'EditAprvLead', '','submitAprvLead',
				'AprvleadForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
								DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
																					if (status) {
																						DisplayDropDownData("M_Emp_User","empdataforCreateLd",function(status) {
																							if (status) {
																								DisplayDropDownData("M_Emp_User","empdataforAssingLd",function(status) {
																									if (status) {		
																						DropDownDataDisplay('M_Source','srcforlead',function (status){
																							if(status)
																							{
																								
																					dispalyLineItemLeadformodify('');
																								
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
		$( ".datepicker" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      
		    });
		$('input[name="approve_dt"]').datepicker("setDate",currentDate);
		
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
			<!-- 	<h1>
					 Create Lead
					<button type="button" name="create btn" class="btn btn-primary led hideled"
						onclick="ControlLead('Create','displayAprvLead','EditAprvLead','submitAprvLead','O_Lead')">Create
						Lead</button>
				</h1> -->
			</div>
			<div class="col-sm-6">
				<!-- <ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Order</a></li>
					<li class="breadcrumb-item">Lead</li>
				</ol> -->
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="card">
	<div id="displayAprvLead">
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
							onclick="DisplayApproveLead('O_Lead','displayAprvLead','EditAprvLead','','AprvleadForDisplaytd')">Search
						</button>
					</td>
				</tr>
				<tr></tr>
			</table>
			<table id="AprvleadForDisplaytd"
				class="table table-bordered table-hover AprvleadForDisplaytd">
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
<div id="EditAprvLead" style="display: none;">
	
		<form name="submitAprvLead" id="submitAprvLead"
			enctype="multipart/form-data" action="Upload_Fil" method="post">
			<div id="createAprvLead">
				<div class="card-header new">
					<h3 class="card-title1">Lead Details</h3>
				</div>
				
				<table class="table table-bordered" id="leadDetails">
					<tr>
					
					 <td><b>Lead Assign By<font color="red">*</font>
					<td><select id="empdataforAssingLd" name="approve_by"
							class="form-control" data-valid="mand" style="width: 140" disabled="true">
								<option value="">Select</option>
						</select></td> 
					 <td><b>Created By<font color="red">*</font>
					<td><select id="empdataforCreateLd" name="created_by"
							class="form-control" disabled="true" data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td> 
						</tr>
						<tr>
						<td><strong>Lead Id<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" readonly data-valid="mand"></td>
						
						
						<td><strong>Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_ld" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand" readonly font-size: 1em;"></td>
					</tr>
						  <tr>
					 <td><b>Source<font color="red">*</font></b></td>
						<td><select id="srcforlead" name="id_src"
							class="form-control" data-valid="mand" style="width: 140" disabled="true" onchange="getsrcCode(this);">
								<option value="">Select</option>
						</select></td>
						
			    	 	<td><b>Source Code<font color="red">*</font></b></td>
						<td><input id="cd_src" class="form-control"  type="text" name="cd_src" readonly data-valid="mand"></td>
					</tr>
					<tr>
					
				
					 <td><strong>Patient Name<font color="red">*</font></strong></td>
				<td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" readonly data-valid="mand"></td>
					
				 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" readonly data-valid="mand"></td>
				 	
				 	</tr>
					<tr>	
				 <td><strong>Alternate Contact No.<font color="red">*</font></strong></td>
				<td><input id="alt_pstn_ct" class="form-control" type="text" name="alt_pstn_ct" data-valid="mand"></td>	
					
					<td><strong>Email<font color="red">*</font></strong></td>
				<td><input id="pstn_email" class="form-control" type="text" name="pstn_email" readonly data-valid="mand"></td>
				
					
					</tr>
					<tr> 
					
				<td><strong>Adhaar No.<font color="red">*</font></strong></td>
				<td><input id="adhar_no" class="form-control" type="text" name="adhar_no" readonly data-valid="mand"></td>
					
					<td><strong>Address<font color="red">*</font></strong></td>
				<td><input id="address" class="form-control" type="text" name="address" readonly data-valid="mand"></td>
					</tr>
					<tr>
					 <td><strong>Doctor's Name<font color="red">*</font></strong></td>
				<td><input id="nm_dr" class="form-control"  type="text" name="nm_dr" readonly data-valid="mand"></td>
					
					 <td><strong>Speciality<font color="red">*</font></strong></td>
				<td><input id="dr_sp" class="form-control"  type="text" name="dr_sp" readonly data-valid="mand"></td>
					</tr>
					<tr>
					<td><strong>Clinic/Hospital<font color="red">*</font></strong></td>
				<td><input id="nm_hsptl" class="form-control"  type="text" name="nm_hsptl" readonly data-valid="mand"></td>
					
					
					 <td><strong>Hospital Contact No.<font color="red"></font></strong></td>
				<td><input id="ct_hsptl" class="form-control"  type="text" name="ct_hsptl" readonly></td>
					</tr>
				 <td><strong>Hospital Location<font color="red">*</font></strong></td>
				<td><input id="add_hsptl"class="form-control"  type="text" name="add_hsptl" readonly data-valid="mand"></td>
				
					
					
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
					<!--  <td><strong>Designation<font color="red">*</font></strong></td>
			<td>
				<select name="id_design" id="dgnForEmp" class="form-control" disabled="true" data-valid="mand" 
				style="width: 140" onChange="SubDropDownDataDisplayforemp(this,'EmpdataforCordinator','M_Emp_User',$('#slocDataForLead').val())">
				<option value="">Select</option>
						
				</select>
				</td> -->
					 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforCordinator" name="to_asign_cordi"
							class="form-control " disabled="true" data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>
						<td><strong>State<font color="red"></font></strong></td>
			     	  <td><input id="state" class="form-control"  type="text" name="state" value="New" readonly data-valid="mand"></td>
					</tr>
					
					<tr>	
					
			     	<!-- <td><select name="state" class="form-control"  disabled="true"  data-valid="mand">
						<option value="New">New</option>
						<option value="Inprogress">Inprogress</option>
						<option value="Closed">Closed</option>
					</select></td> --> 
					
					<!-- <td><strong>Approve Date<font color="red">*</font></strong></td> -->
						<td><input type="text" name="approve_dt" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand" style = "display:none";readonly font-size: 1em;"></td>
					
					</tr>
	      		
				
					
				
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
					<!-- 	<strong style="margin-right: 100px;">Grand Total</strong>  -->
						<tr><strong>Refundable Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="refund_amt" value="0.0" id="refund "
							class="form-control resetAcc FieldResize" onChange="addinTotLead(this)"></input><tr>
						<tr><strong>Transportation Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="trnsprt_amt" value="0.0" id=" "
							class="form-control resetAcc FieldResize" onChange="addinTotLead(this)" ></input></tr>
						<tr> <strong>Grand Total<font color="red">*</font></strong></tr>
							<tr><input type="text" name="tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly></input></tr>
					  
							<tr><strong><div id="grandtotinword">Zero only</div></strong></tr> 
					</div> 
						<table class="table table-bordered" id="leadDetails2">
						</table>	
						<input type="hidden" name="gr_tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly>
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
									
										<button name=Approve type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="ControlAprvlead('Approve','displayAprvLead','EditAprvLead','submitAprvLead','O_Lead')">Assign Lead</button>
									<button name="cancel" type="button" class="btn btn-primary inv"
										onclick="ControlAprvlead('Cancel','displayAprvLead','EditAprvLead','submitAprvLead','O_Lead')">Back</button>
									
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