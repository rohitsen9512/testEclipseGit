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
		
		DisplayAssignTomeNew('O_AssignedLlead', 'displayAssigntomeNewLd', 'EditAssigntomeNewLd', '','submitAssigntomeNewLd',
				'ldassignNewForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
	
			DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
			                  if (status) {
												
			                	  DisplayDropDownData("M_Emp_User","empdatafortagbyLd",function(status) {
			      					if (status) {
			      						DisplayDropDownData("M_Designation","dgnForEmp",function(status) {
			    							if (status) {	
			        						
			                	  dispalyModifyLineCrItemLead('');
			 }
		}); 
			      					}
			              		}); 
			                  }
			});
																	
					
				
	});
		
	
	$(function() {
		
		
	
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
		//currentDate.setDate(currentDate.getDate() - 10);
	 	currentDate.setMonth(currentDate.getMonth() - 1); 
		$('input[name="dt_frm"]').datepicker("setDate", currentDate);
		
		/*  $('input[name="dt_install"]').datepicker("setDate", currentDate);
		 currentDate.setDate(currentDate.getDate() + 10);
	 	$('input[name="dtexp_install"]').datepicker("setDate", currentDate);  */
		
		
        
		
	});
		
	
	
	
	
	jQuery("input#fileId").change(function () {
		var filename="";
		var files = $("#fileId").get(0).files;
	    var fileData = new FormData();
	
	    for (var i = 0; i < files.length; i++) {
	    	
	        
	         var formData = new FormData();	 
	 		formData.append('file', $('#fileId').get(0).files[i]);
	        
			
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
				    	
				    	if(i==0){
				        	filename=r.upload_inv;
				        }
				        else{
				        	filename+=","+r.upload_inv;
				        }
				   
				    $('input[name="proof_cltn_file"]').val(filename.replace("&","AND"));
				    	
				    		/* $('input[name="file_name1"]').val(filename); */
				    		
				    }
				},'json'); 
	    }	

	}); 
	
	
	
	jQuery("input#fileId_proof").change(function () {
		var filename="";
		var files = $("#fileId_proof").get(0).files;
	    var fileData = new FormData();
	
	    for (var i = 0; i < files.length; i++) {
	    	
	        
	         var formData = new FormData();	 
	 		formData.append('file', $('#fileId_proof').get(0).files[i]);
	        
			
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
				    	
				    	if(i==0){
				        	filename=r.upload_inv;
				        }
				        else{
				        	filename+=","+r.upload_inv;
				        }
				   
				    $('input[name="idproof_file"]').val(filename.replace("&","AND"));
				    	
				    		/* $('input[name="file_name1"]').val(filename); */
				    		
				    }
				},'json'); 
	    }	

	}); 
	
	
	jQuery("input#fileId_prod_proof").change(function () {
		var filename="";
		var files = $("#fileId_prod_proof").get(0).files;
	    var fileData = new FormData();
	
	    for (var i = 0; i < files.length; i++) {
	    	
	        
	         var formData = new FormData();	 
	 		formData.append('file', $('#fileId_prod_proof').get(0).files[i]);
	        
			
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
				    	
				    	if(i==0){
				        	filename=r.upload_inv;
				        }
				        else{
				        	filename+=","+r.upload_inv;
				        }
				   
				    $('input[name="equpment_file"]').val(filename.replace("&","AND"));
				    	
				    }
				},'json'); 
	    }	

	}); 
	

</script>
<script>
</script>

<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<!-- <h1>
					 Create Lead
					<button type="button" name="create btn" class="btn btn-primary led hideled"
						onclick="ControlLead('Create','displayLead','createLead','submitLead','O_Lead')">Create
						Lead</button>
				</h1> -->
			</div>
			<div class="col-sm-6">
			
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<!-- Content Wrapper. Contains page content -->

<div class="card">
	<div id="displayAssigntomeNewLd">
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
							onclick="DisplayAssignTomeNew('O_AssignedLlead', 'displayAssigntomeNewLd', 'EditAssigntomeNewLd', '','submitAssigntomeNewLd',
								'ldassignNewForDisplaytd')">Search
						</button>
					</td>
				</tr>
				<tr></tr>
			</table> -->
			<table id="ldassignNewForDisplaytd"
				class="table table-bordered table-hover ldassignNewForDisplaytd">
				
			</table>
		</div>
	</div>
</div>
<section class="content">
<div class="card">
<div id="EditAssigntomeNewLd" style="display: none;">
	
		<form name="submitAssigntomeNewLd" id="submitAssigntomeNewLd">
		<!--enctype="multipart/form-data" action="Upload_Fil" method="post"  -->
			<div id="createassignTomLead" >
				<div class="card-header new">
					<h3 class="card-title1">Lead Details</h3>
				</div>
				<table class="table table-bordered" id="leadCrDetails">
					<tr>
						<td><strong>Lead Id<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" readonly data-valid="mand"></td>
						<td><strong>Tag Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_tag" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata"  data-valid="mand" readonly font-size: 1em;"></td>
					</tr>
				
					<tr>
		
						 <td><b>Tag By<font color="red">*</font>
					     <td><select id="empdatafortagbyLd" name="tag_by"
							class="form-control" disabled="true" data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>  
						
						
			    
			    	 <td><b>Source Code<font color="red">*</font></b></td>
					 <td><input id="cd_src" class="form-control"  type="text" name="cd_src" readonly data-valid="mand"></td>
						</tr>
					<tr>
					 <td><strong>Patient Name<font color="red">*</font></strong></td>
				<td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" readonly="readonly" data-valid="mand"></td>
				
				
				
					 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" readonly data-valid="mand"></td>
					
						</tr>
					<tr>
					 <td><strong>Alternate Contact No.<font color="red">*</font></strong></td>
				<td><input id="alt_pstn_ct" class="form-control" type="text" name="alt_pstn_ct" data-valid="mand" readonly="readonly"></td>
				
					 <td><strong>Email<font color="red"></font></strong></td>
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
				
					
				 <td><strong>Remark<font color="red"></font></strong></td>
			       <td><input id="remark" class="form-control"  type="text" name="remark" readonly></td>
				
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
					</tr>

				<tr>
				
					 <td><strong>GSTN NO.<font color="red">*</font></strong></td>
				<td><input id="gstin_no" class="form-control"  type="text" name="gstin_no" readonly data-valid="mand"></td>
					
					 <td><strong>DL No.<font color="red">*</font></strong></td>
				<td><input id="dl_no" class="form-control"  type="text" name="dl_no"  readonly data-valid="mand"></td>
					
					</tr>
					
				<tr>
					<td><strong>Designation<font color="red">*</font></strong></td>	
			    
					  <td><select name="id_design_tagemp" id="dgnForEmp"  disabled ="true" class="form-control" data-valid="mand" 
				    style="width: 140"  onChange="SubDropDownDataDisplaybydesingEmp(this,'EmpdataforField','M_Emp_User')">
				   <option value="">Select</option></select></td>
					
					 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforField" name="tag_to_emp"
							class="form-control " disabled="true" data-valid="mand" style="width: 140 " onchange="Statelead()">
								<option value="">Select</option>
						</select></td>
					
					
				</tr>
				<tr>
				     <td><strong>State<font color="red"></font></strong></td>
			     	 <td><input id="state" class="form-control"  type="text" name="tagto_me_state" value="New" readonly data-valid="mand"></td>
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
						<div style="float:right;margin-right: 22px;">
					<!-- 	<strong style="margin-right: 100px;">Grand Total</strong>  -->
					 <tr><strong>Mode of Refundable Amount <font color="red">*</font></strong></tr>
					    <tr><select name="pay_mode_rfd" class="form-control"  data-valid="mand" onChange=Showcolrfdcheque(this)>
						<option value="">Select</option>
						<option value="NA">NA</option>
						<option value="FOC">FOC</option>
						<option value="Cash">Cash</option>
						<option value="UPI">UPI/Online</option>
						<option value="Credit">Credit</option>
						<option value="Cheque">Cheque</option>
					</select></tr>
					 <tr class="srv" style="display:none;"><strong>Cheque No.<font color="red"></font></strong></tr>
			     	  <tr class="srv" style="display:none;"><input id="cheque_no_rfd" class="form-control" type="text"  name="cheque_no_rfd" ></tr> 
						<tr><strong>Refundable Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="refund_amt" value="0.0" id="refund "
							class="form-control resetAcc FieldResize" readonly onChange="addinTotLead(this)"></input><tr>
						<tr><strong>Transportation Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="trnsprt_amt" value="0.0" id=" trnsprt"
							class="form-control resetAcc FieldResize" readonly onChange="addinTotLead(this)" ></input></tr>
						<tr><strong>Grand Total<font color="red">*</font></strong></tr>
							<tr><input type="text" name="tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly></input></tr>
								<tr><strong><div id="grandtotinword">Zero only</div></strong></tr> 
						
							
						
							</div> 
						<table class="table table-bordered" id="leadCrDetails2">
						</table>
						<input type="hidden" name="gr_tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly>
						<table class="table table-bordered" id="leadCrrDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="Assign" class="form-control"> 
									<input type="hidden"name="invoice_file" value="" class="form-control">
									 <input id="id" type="hidden" name="id" value="0" class="form-control">
									<input id="invoiceId" type="hidden" name="invoiceId" value=""
									class="form-control">
									 <input type="hidden"
									name="nm_acc_asset" value="Asset" class="form-control">
									<input type="hidden" name="leadCrCheck" value="0">
								<button name="cancel" type="button" class="btn btn-primary led" style="margin-left: 500px;"
										onclick="ControlAssingTomeNewlead('Cancel','displayAssigntomeNewLd','EditAssigntomeNewLd','submitAssigntomeNewLd','O_AssignedLlead','')">Back</button>
									
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

	
