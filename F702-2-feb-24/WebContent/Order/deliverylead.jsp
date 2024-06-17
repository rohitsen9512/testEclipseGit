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
		/* 
		DisplayForDeliveryLead('O_AssignedLlead', 'displayAssigntomefordelverLd', 'EditAssigntomefordeliverld', '','submitAssigntomefordelverLd',
				'lddeliveryForDisplaytd'); */
		DisplayAssignTomeNew('O_AssignedLlead', 'displayAssigntomefordelverLd', 'EditAssigntomefordeliverld', '','submitAssigntomefordelverLd',
				'lddeliveryForDisplaytd');		
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
	
			DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
			                  if (status) {
												
			                		
			      				DisplayDropDownData("M_Emp_User","empdataforapprvfordelvrLd",function(status) {
			      					if (status) {
			      						
					      				DisplayDropDownData("M_Emp_User","empdatafordelvrbyLd",function(status) {
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
		
		$('input[name="dt_deliver"]').datepicker("setDate", currentDate);
		$('input[name="dt_to"]').datepicker("setDate", currentDate);
		//currentDate.setDate(currentDate.getDate() - 10);
	 	currentDate.setMonth(currentDate.getMonth() - 1); 
		$('input[name="dt_frm"]').datepicker("setDate", currentDate);
		
		/*  $('input[name="dt_install"]').datepicker("setDate", currentDate);
		 currentDate.setDate(currentDate.getDate() + 10);
	 	$('input[name="dtexp_install"]').datepicker("setDate", currentDate);  */
				
		
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
	        minDate: 0, // Set minimum date to today
	    });

	    $('input[name="dt_deliver"]').datepicker("setDate", currentDate);
	    $('input[name="dt_to"]').datepicker("setDate", currentDate);

	    // Set minimum date to today for the 'dt_frm' input
	    $('input[name="dt_frm"]').datepicker({
	        dateFormat: "dd/mm/yy",
	        autoSize: true,
	        altFormat: "dd/mm/yy",
	        minDate: 0, // Set minimum date to today
	    }).datepicker("setDate", currentDate);

	    /*  
	    // Example for 'dt_install' and 'dtexp_install' inputs
	    $('input[name="dt_install"]').datepicker("setDate", currentDate);
	    currentDate.setDate(currentDate.getDate() + 10);
	    $('input[name="dtexp_install"]').datepicker("setDate", currentDate);
	    */
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

			    	document.getElementById("image").src = "InvoiceScanFile/"+r.upload_inv;
			    	//document.getElementById("image").innerHTML+=<img src="files[i]"></img>
			       // document.getElementById("image1").src = "InvoiceScanFile/"+r.upload_inv;	
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
					document.getElementById("imageId").src = "InvoiceScanFile/"+r.upload_inv;
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
					document.getElementById("image3").src = "InvoiceScanFile/"+r.upload_inv;
				    }
				},'json'); 
	    }	

	}); 
	

	function calculateTotfordecimal1(event, name){
		debugger;
			var domElement =$(event.target || event.srcElement);
			var totParticular=0.0,total=0.0;
			/*var intRegex = /^\d+$/;
			var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;*/
			var intRegex = /^\d+$/;
		    var floatRegex = /^\d+(\.\d{1,2})?$/;


			var str ='';
			console.log(floatRegex);
			var fieldName= name + domElement.attr('patelUnPrc');
			
			$('input[name="'+fieldName+'"]').removeClass('error');
			
			str = $('input[name="'+fieldName+'"]').val();
			
			if(str != '')
				{
			if(intRegex.test(str) || floatRegex.test(str)) {
				
				
			}
			else
				{
					alert('Please enter valid number in the field.');
					$('input[name="'+fieldName+'"]').addClass('error');
					$('input[name="'+fieldName+'"]').focus();
					$('input[name="'+fieldName+'"]').val(0);
					exit(0);
					
				}
			
				}
			 else
				{
				alert('Please fill-up all the mandatory fields.');
				$('input[name="'+fieldName+'"]').addClass('error');
				$('input[name="'+fieldName+'"]').focus();
				$('input[name="'+fieldName+'"]').val(0);
				exit(0);
			} 
			
			
		}

	
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
	<div id="displayAssigntomefordelverLd">
		<div class="card-body">
		
			<table id="lddeliveryForDisplaytd"
				class="table table-bordered table-hover lddeliveryForDisplaytd">
				
			</table>
		</div>
	</div>
</div>
<section class="content">
<div class="card">
<div id="EditAssigntomefordeliverld" style="display: none;">
	
		<form name="submitAssigntomefordelverLd" id="submitAssigntomefordelverLd">
		<!--enctype="multipart/form-data" action="Upload_Fil" method="post"  -->
			<div id="createassignTomLead" >
				<div class="card-header new">
					<h3 class="card-title1">Lead Details</h3>
				</div>
				
				<table class="table table-bordered" id="leadCrDetails">
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
						<td><strong>Lead Id<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" readonly data-valid="mand"></td>
						    
						<!-- <td><strong>Tag Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_tag" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata"  data-valid="mand" readonly font-size: 1em;"></td> -->
					
					<!-- 	<td><strong>Approved By<font color="red">*</font>
						</strong></td>
					
						<td><select id="empdataforapprvfordelvrLd" name="apprvby_frdelvr"
							class="form-control readbaledata" disabled="true" data-valid="mand" style="width: 140" disabled="true">
								<option value="">Select</option>
						</select></td>  -->
						
					<td><strong>Tag Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_tag" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata"  readonly data-valid="mand"  font-size: 1em;"></td>
					
				<!--      <td><b>Source<font color="red">*</font></b></td>
						<td><select id="srcforlead" name="id_src"
							class="form-control" data-valid="mand" style="width: 140" disabled="true" onchange="getsrcCode(this);">
								<option value="">Select</option>
						</select></td> -->
						
						</tr>
					 <tr> 
			    
			    	 <td><b>Source Code<font color="red">*</font></b></td>
					 <td><input id="cd_src" class="form-control"  type="text" name="cd_src" readonly data-valid="mand"></td>
					
					 <td><strong>Patient Name<font color="red">*</font></strong></td>
				<td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" readonly="readonly" data-valid="mand"></td>
				
					</tr> 
					<tr>
				 <td><strong>Attender Name<font color="red">*</font></strong></td>
				<td><input id="att_name" class="form-control" type="text" name="att_name" data-valid="mand" readonly="readonly"></td>					
					 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" readonly data-valid="mand"></td>
					
					
					</tr>
					
			<tr>
					 <td><strong>Attender Contact No.<font color="red">*</font></strong></td>
				<td><input id="alt_pstn_ct" class="form-control" type="text" name="alt_pstn_ct" data-valid="mand" readonly="readonly"></td>			
					 <td><strong>Email<font color="red"></font></strong></td>
				<td><input id="pstn_email" class="form-control" type="text" name="pstn_email" readonly data-valid="mand"></td>
					
				

					</tr>
					<tr> 
									
					 <td><strong>Aadhaar No.<font color="red">*</font></strong></td>
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
							class="form-control " disabled="true" data-valid="mand" style="width: 140" onchange="getDATA(this); SubDropDownDataDisplay(this,'dgnForEmp','M_Designation')">
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
			       <td><select name="id_design_tagemp" id="dgnForEmp" class="form-control" data-valid="mand" 
				    style="width: 140"  disabled="true" onChange="SubDropDownDataDisplayforemp(this,'EmpdataforField','M_Emp_User',$('#slocDataForLead').val())">
				   <option value="">Select</option></select></td>
					 
					 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforField" name="tag_to_emp"
							class="form-control " disabled="true" data-valid="mand" style="width: 140 " onchange="Statelead()">
								<option value="">Select</option>
						</select></td>
					
				</tr>
				
				  <!--  <td><strong>State<font color="red"></font></strong></td> -->
			     	 <td><input id="state" class="form-control"  type="hidden" name="tagto_me_state" value="New"></td>
				<!--  <input id="id_lead_m" type="hidden" name="id_lead_m" value="" class="form-control"> -->
	      	
	      	
					
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
					<tr><strong class ="hideshow">Mode of Refundable Amount <font color="red">*</font></strong></tr>
					    <tr><select name="pay_mode_rfd" class="form-control"  id=pay_mode_rfd data-valid="mand" onChange=Showcolrfdcheque(this) disabled ="true">
						<option value="">Select</option>
						<option value="FOC">FOC</option>
						<option value="Cash">Cash</option>
						<option value="UPI">UPI/Online</option>
						<option value="Credit">Credit</option>
						<option value="Cheque">Cheque</option>
						<option value="not_avilable">NA</option>
						<option value="payment_due">Due</option>
						
					</select></tr>
					 <tr><strong class ="hideshow">Cheque No.<font color="red"></font></strong></tr>
			     	  <tr><input id=cheque_no_rfd class="form-control" type="text" name="cheque_no_rfd" readonly></tr> 
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
										onclick="ControlForAprovedelivery('Cancel','displayAssigntomefordelverLd','EditAssigntomefordeliverld','submitAssigntomefordelverLd','O_AssignedLlead','')">Back</button>
									
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
<section class="content">	
	<div class="card">
<div id="Editdelivery" style="display:none;">
	
		<form name="submitdelivery" id="submitdelivery">

			<div id="createassignTome">
				<div class="card-header new">
					<h3 class="card-title1">Delivery Detail</h3>
				</div>
				<table class="table table-bordered" id="leadCrDetails">
				   <input type="hidden" name="id_lead_m" value="">
				    <input type="hidden" name="tag_by" value="">
				      
				       
				   
				   <tr>
				   	<td><strong>Deliver By<font color="red">*</font>
						</strong></td>
					
						<td><select id="empdatafordelvrbyLd" name="deliver_by"
							class="form-control readbaledata" disabled="true" data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>
						<td><strong>Delivery Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_deliver" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker" readonly style="background-color:transparent;font-size: 1em;"  data-valid="mand"  font-size: 1em;"></td>
				  </tr>
				
					<tr>
					<td><strong>TAT<font color="red">*</font></strong></td>
						<td><input type="text" name="deliver_time" value="" 
							class="form-control readbaledata " readonly  data-valid="mand" ></td> 
		 	    <input type="hidden" name="delivery_time" value=""></input>
		 	    
		 	    <td><strong>Reached Location<font color="red"></font></strong></td>
		 	    <td><input type="text" name="reach_lnformation" readonly value="" class="form-control" data-valid="mand"></input></td>
			  <!--    <td><select name="reach_lnformation" class="form-control"   disabled="true"  onChange=Showcolfortat(this) data-valid="mand">
						<option value="On_Time">On Time</option>
						<option value="Rent">In Process</option>
						<option value="Delay_in_TAT">Delay in TAT</option></select>
						</td>  -->
					<tr>	 
					<td class="reached_location"><strong>Reason For TAT<font color="red"></font></strong></td>
			     	  <td class="reached_location"><input id="reson_tat" class="form-control" type="text" name="reson_tat" data-valid="mand" ></td>
					
					
					
					 
					<td><strong>Reason For Visit<font color="red"></font></strong></td>
			     	<td><select name="reason_visit" class="form-control" data-valid="mand">
						<option value="">Select</option>
						<option value="both_Installation">Sale & Rental Installation</option>
						<option value="New_Installation">Sale Installation</option>
						<option value="Rental_Installation">Rental Installation</option>
						<option value="Demonstration">Demonstration</option>
						<option value="Refill_Installation">Refill Installation</option>
						<option value="troubleshoot">Troubleshoot</option>
						<option value="Service">Service</option>
						
					</select></td>
					</tr>
				<tr>	
						
				
					 
					
						
			    
				
		
		 	 <td><strong>Amount Collected<font color="red"></font></strong></td>
			     	<td><select name="amt_clctn" class="form-control"  data-valid="mand">
						<option value="">Select</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
					</select></td>
					
						 	 <td><strong>ADHAR VERIFICATION<font color="red"></font></strong></td>
			     	<td><select name="adhar_varify" class="form-control"  data-valid="mand" onChange=ShowcolforAdhar(this)>
						<option value="">Select</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
					</select></td>
			   </tr>
			   
			 
			    <tr>
					 <td><strong>Mode of Payment<font color="red"></font></strong></td>
			     	<td><select name="pay_mode" class="form-control"   onChange=Showcolforcheque(this)>
						<option value="">Select</option>
						<!-- <option value="NA">NA</option> -->
						<option value="FOC">FOC</option>
						<option value="Cash">Cash</option>
						<option value="UPI">UPI/Online</option>
						<option value="Cash&online">Cash&Online</option>
						<option value="Credit">Credit</option>
						<option value="Cheque">Cheque</option>
						<option value="not_avilable">NA</option>
						<option value="payment_due">Due</option>
					</select></td>
		 	
			
			   
			  <td><strong>Total Invoice Amount<font color="red"></font>*</strong></td>
			   <td><input id="amount" class="form-control" type="text" name="amount"  value="0.00"  data-valid="mand" readonly></td> 
			 
			 </tr>
			   <tr>
			       <td><strong>Collect Amount<font color="red"></font></strong></td>
			        <td><input id="colct_amount" class="form-control" type="text" name="colct_amount"  value="0.00" patelUnPrc="" onChange="calculateTotfordecimal1(event,'colct_amount')" data-valid="mand" ></td> 
			        
			         <td><strong>Due Amount<font color="red"></font></strong></td>
			        <td><input id="due_amount" class="form-control" type="text" name="due_amount"  value="0.00" patelUnPrc="" onChange="calculateTotfordecimal1(event,'due_amount')" data-valid="mand"></td> 
			   </tr>
			   
			  <tr>
			   <td class="srv" style="display:none;"><strong>Cheque No.<font color="red"></font></strong></td>
			     	  <td class="srv" style="display:none;"><input id="cheque_no" class="form-control" type="text" data-valid="mand" name="cheque_no" ></td>
			 <td><strong>Refundable Amount<font color="red">*</font></strong></td>
			  <td><input id="ref_amt" class="form-control" type="text" name="ref_amt" value="0.00"  data-valid="mand" readonly></td> 			   
			   </tr>
	              <tr>
			   <td><strong>Transportation Amount<font color="red"></font></strong></td>
			  <td><input id="trns_amt" class="form-control" type="text" name="trns_amt" value="0.00"  data-valid="mand" readonly></td> 
	           
			
			 
			     <!-- <td><strong>Additional Product Handed Over<font color="red"></font></strong></td>
	             <td><input id="add_hsptl"class="form-control"  type="text" name="add_prod"  data-valid="mand"></td> -->
	        <td><strong>Remark<font color="red"></font></strong></td>
          <td><input id="rmk" class="form-control" type="text" name="remark" ></td>
	  <!--       <td><strong>Additional Product Handed Over<font color="red"></font></strong></td>
	       <td><select multiple="multiple" id="subgroupDataForMaster" name="add_prod" style="height: 90px; width: 250px" class="form-control">
	                    <option value="mask"> Mask</option>
	                    <option value="spanner">Spanner</option>
	                    <option value="bag">Bag</option></select></td> -->
	         
	 </tr>  
	             <tr>
	                  <td><strong>Payment Proof<font color="red"></font>*</strong></td>
				<td> 
				
				<img id="image"  style="min-width: 120px;
    min-height: 70px;
  max-width: 70px;box-shadow: 0 4px 8px 10px rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
      max-height: 70px;border: 2px solid #555;margin-right: 20px; border-radius: 10px;"/> 
      
      						
				    <input id="fileId" type="file"  class="form-control" multiple accept="image/png, image/gif, image/jpeg ,image/jpg ,image/bmp ,image/jpe ,image/jfif ,image/wepb" />  
				    
			
				 <input type="hidden" name="proof_cltn_file" value="" class="form-control"> 
	           
				
				</td>
	               <!--  <td><strong>Payment Proof<font color="red"></font>*</strong></td>
	               <input type="hidden" name="proof_cltn_file" value="" class="form-control"  >
	               <td><input id="fileId" type="file"  class="form-control" multiple ></td>   -->
	             
	              
	               <td class="adhar" style="display:none;"><strong>Id Proof<font color="red"></font></strong></td>
	             <td class="adhar" style="display:none;">
	              <img id="imageId"  style="min-width: 120px;
    min-height: 70px;
  max-width: 70px;box-shadow: 0 4px 8px 10px rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
      max-height: 70px;border: 2px solid #555;margin-right: 20px; border-radius: 10px;"/> 
	             
	              <input type="hidden" name="idproof_file" value="" class="form-control" >
	             <input id="fileId_proof" type="file"  class="form-control" multiple accept="image/png, image/gif, image/jpeg ,image/jpg ,image/bmp ,image/jpe ,image/jfif ,image/wepb" />  </td> 
	         </tr>
	              <td><strong>Equipment Image<font color="red"></font>*</strong></td>
	              <td> 
	              <img id="image3"  style="min-width: 120px;
    min-height: 70px;
  max-width: 70px;box-shadow: 0 4px 8px 10px rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
      max-height: 70px;border: 2px solid #555;margin-right: 20px; border-radius: 10px;"/> 
	             <input type="hidden" name="equpment_file" value="" class="form-control" >
	                <input id="fileId_prod_proof" type="file"  class="form-control" multiple accept="image/png, image/gif, image/jpeg ,image/jpg ,image/bmp ,image/jpe ,image/jfif ,image/wepb" />  </td> 
	             
	
	</table>
	<table class="table table-bordered" id="leaddelivrDetail">
						</table>
	 <table class="table table-bordered" id="leadCrDetails1">
							<tr>
						<input type="hidden" name="action" value="" class="form-control"> 
								<td>
									<button name="Update" type="button" class="btn btn-primary led" style="margin-left: 500px;"
										onclick="ControlNewleaddeliver('Update','displayAssigntomefordelverLd','EditAssigntomefordeliverld','submitAssigntomefordelverLd','O_AssignedLlead','','Editdelivery','submitdelivery')">Update</button>	
								<button name="cancel" type="button" class="btn btn-primary led" 
										onclick="ControlNewleaddeliver('Cancel','displayAssigntomefordelverLd','EditAssigntomefordeliverld','submitAssigntomefordelverLd','O_AssignedLlead','','Editdelivery','submitdelivery')">Back</button>
							
								</td>
							</tr>
						</table>
	
	</div>
				
				
				
</form>
				
				
</div>
				
			
</div>
	</section>	

	
