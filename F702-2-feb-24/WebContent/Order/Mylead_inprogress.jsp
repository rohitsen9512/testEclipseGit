<style>
.easy-autocomplete-container {
	max-height: 250px;
	overflow: auto;
	overflow-y: auto;
	overflow-x: hidden;
}


</style>
</style>

<link rel="stylesheet" href="Autocomp/easy-autocomplete.min.css">
<link rel="stylesheet" href="Autocomp/easy-autocomplete.themes.min.css">
<script type="text/javascript"
		src="All_Js_File/Order/cordinatorlead.js"></script>
<script type ="text/javascript" src="common.js"></script>
<script type="text/javascript">
	$(function() {
		
		DisplayAssignTomeinprogress('O_AssignedLlead', 'displayAssigntomeINPLd', 'EditAssigntomeINPLd', '','submitAssigntomeINPLd',
				'ldassignINPForDisplaytd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');
	
																
					
						DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
			                 if (status) {
			                		DisplayDropDownData("M_Emp_User","empdatafordelvrbyLd",function(status) {
				      					if (status) {
				      						DisplayDropDownData("M_Emp_User","empdataforreturnbyLd",function(status) {
						      					if (status) {
																								
			                	 dispalyModifyLineReturnItemLead('');
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
		//currentDate.setDate(currentDate.getDate() - 10);
	 	currentDate.setMonth(currentDate.getMonth() - 1); 
		$('input[name="dt_frm"]').datepicker("setDate", currentDate);
		
		/*   $('input[name="dt_install"]').datepicker("setDate", currentDate);
		 currentDate.setDate(currentDate.getDate() + 10);
	 	$('input[name="dtexp_install"]').datepicker("setDate", currentDate);   */
		
		
        
		
	});
		
	
	 jQuery(function ($) {
		    var $active = $('#accordion .panel-collapse.in').prev().addClass('active');
		    $active.find('a').prepend('<i class="glyphicon fas fa-minus"></i>');
		    $('#accordion .panel-heading').not($active).find('a').prepend('<i class="glyphicon fas fa-plus"></i>');
		    $('#accordion').on('show.bs.collapse', function (e) {
		        $('#accordion .panel-heading.active').removeClass('active').find('.glyphicon').toggleClass(' fa-plus fa-minus');
		        $(e.target).prev().addClass('active').find('.glyphicon').toggleClass(' fa-plus fa-minus');
		    })
		});

	
	
/* 	proof_colctnId.onchange = function(e){
		
		  e.preventDefault();
		  var file = this.files[0];
		  if(file.type.indexOf('image/') !== 0) {
		    this.value = null;
		    alert(file.name +" has an invalid extention. \nValid extension(s):bmp, gif, jpeg, jpg, jpe, jfif, png, webp.");
		  }
		  else {
		    console.log('valid file');
		    var formData = new FormData();	 
			formData.append('file', $('#proof_colctnId').get(0).files[0]);
			
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
				    	
				    	$('input[name="file_name"]').val(r.upload_inv);
				    	document.getElementById("image").src = "InvoiceScanFile/"+r.upload_led;
				        document.getElementById("image1").src = "InvoiceScanFile/"+r.upload_led;
				    		
				    }
				},'json');
		  }
		}
 */
	
	
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
				   
				    $('input[name="file_name"]').val(filename.replace("&","AND"));
				    	
				    		/* $('input[name="file_name1"]').val(filename); */
				    		
				    }
				},'json'); 
	    }	

	}); 



</script>
<script>
</script>
<!-- Content Wrapper. Contains page content -->

<div class="card">
	<div id="displayAssigntomeINPLd" >
		<div class="card-body">
			
			<table id="ldassignINPForDisplaytd"
				class="table table-bordered table-hover ldassignINPForDisplaytd">
				
			</table>
		</div>
	</div>
</div>
<div class="card">
	<div id="displayRefillHistory" style="display:none; >
		<div class="card-body">
			
			<table id="ldaRefillForDisplaytd"
				class="table table-bordered table-hover ldaRefillForDisplaytd">
				
			</table>
		</div>
	</div>
</div>

<section class="content">
<div class="card">
<div id="EditAssigntomeINPLd" style="display:none;">
	
		<form name="submitAssigntomeINPLd" id="submitAssigntomeINPLd">
		<!--enctype="multipart/form-data" action="Upload_Fil" method="post"  -->
			<div id="createassignTomLead" >
			
				<div class="card-header new">
					<h3 class="card-title1">Return to Store </h3>
				</div>
				
				<table class="table table-bordered" id="leadCrDetails">
				   <input type="hidden" name="id_lead_m" value="">
				    <input type="hidden" name="lead_no" value="">
				    
				 
				    
				   <tr>
				   <!-- <td><strong>Return Date<font color="red">*</font></strong></td>
			     	<td><input type="text" name="dt_return" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand"  font-size: 1em;"></td>
					  -->
			  
					</tr>
				
				<tr>
				 <td><strong>First Total Invoice Amount<font color="red">*</font></strong></td>
			   <td><input id="amount" class="form-control" type="text" name="amount"  value="0.00" readonly  data-valid="mand"></td> 
		 	   <input type="hidden" name="amt" value="0.00">
		 	   
		 	     <td><strong>Collect Amount<font color="red"></font></strong></td>
			      <td><input id="colct_amount" class="form-control" type="text" name="colct_amount"  value="0.0"  data-valid="mand" ></td> 
		     
		</tr>	
		
				
		<tr>
			     
			        
			         <td><strong>due Amount<font color="red"></font></strong></td>
			        <td><input id="due_amount" class="form-control" type="text" name="due_amount"  value="0.0"  data-valid="mand"></td> 
			  <td><strong>Refundable Amount<font color="red"></font>*</strong></td>
			  <td><input id="refund_amt" class="form-control" type="text" name="refund_amt" value="0.00" readonly data-valid="mand"></td> 
		        <input type="hidden" name="refund_amount" value="0.00">
			  
			   </tr>
		
		<tr>
		<td><strong>Adjustment Refundable amount<font color="red"></font></strong></td> 
			   <td><input id="refund_amt_adjust" class="form-control" type="text" name="refund_amt_adjust" value="" onchange="CalculateRefundamt(this)"></td>  
		     
         	 <td><strong>Total Refundable amount<font color="red"></font></strong></td> 
			   <td><input id="tot_ref_amt" class="form-control" type="text" name="tot_ref_amt" value=""  readonly></td>  
			
			
			</tr>
		 	<tr>
			<td><strong>Transportation Amount<font color="red">*</font></strong></td>
				<td><input id="trnsprt_amt" class="form-control"  type="text" name="trnsprt_amt" value="0.0" data-valid="mand" ></td>
		<!-- 	<td><strong>Remark<font color="red"></font></strong></td>
          <td><input id="rmk" class="form-control" type="text" name="rmk"   data-valid=""></td> -->
          </tr> 
	<!-- </table> -->
				<!-- 
				<div class="card-header new">
					<h3 class="card-title1">Lead Details</h3>
				</div>
				<table class="table table-bordered" id="leadCrDetails"> -->
						
				<tr>
	        	<td colspan="4" class="new"><p class="tableHeaderContent" style="font-size: 24px;margin-left: 275px;">Lead Details</p> </td>
	      	</tr>
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
						<td><strong>Return By<font color="red">*</font>
						</strong></td>
						<td><select id="empdataforreturnbyLd" name="return_by" disabled="true"
							class="form-control readbaledata"  data-valid="mand" style="width: 140" >
								<option value="">Select</option>
						</select></td> 
						
				
						<td><strong>Lead Id<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" readonly data-valid="mand"></td>
						
							</tr>
					
				
					<tr>
					<td><strong>Tag Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_tag" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata"  data-valid="mand" readonly font-size: 1em;"></td>
					
				<!--      <td><b>Source<font color="red">*</font></b></td>
						<td><select id="srcforlead" name="id_src"
							class="form-control" data-valid="mand" style="width: 140" disabled="true" onchange="getsrcCode(this);">
								<option value="">Select</option>
						</select></td> -->
						
						
			    
			    	 <td><b>Source Code<font color="red">*</font></b></td>
					 <td><input id="cd_src" class="form-control"  type="text" name="cd_src" readonly data-valid="mand"></td>
					</tr>
							<tr>
					<td><strong>TAT<font color="red">*</font></strong></td>
						<td><input type="time" name="ld_time" value="" 
							class="form-control readbaledata "  data-valid="mand" readonly></td>
		             <td><strong>Billing Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_billing" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand" readonly></td>
						  
					</tr>
			<tr>
					 <td><strong>Patient Name<font color="red">*</font></strong></td>
				<td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" readonly="readonly" data-valid="mand"></td>
				
				<td><strong>Attender Name<font color="red">*</font></strong></td>
				<td><input id="att_name" class="form-control" type="text" name="att_name" data-valid="mand" readonly="readonly"></td>		

					</tr>
				
				<tr>
				
					 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" readonly data-valid="mand"></td>
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
				
				
					 <td><strong>Hospital Location<font color="red">*</font></strong></td>
				<td><input id="add_hsptl"class="form-control"  type="text" name="add_hsptl" readonly data-valid="mand"></td>				
				 <td><strong>Remark<font color="red"></font></strong></td>
			       <td><input id="remark" class="form-control"  type="text" name="remark" readonly></td>
				
				
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
						<td><select id="slocDataForLead" name="id_sloc"  disabled="true" 
							class="form-control "  data-valid="mand" style="width: 140" onchange="getDATA(this); SubDropDownDataDisplayforemp(this,'EmpdataforCordinator','M_Emp_User')">
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
				<td>
				<select name="id_design_tagemp" id="dgnForEmp" class="form-control" disabled="true" data-valid="mand" 
				style="width: 140" onChange="SubDropDownDataDisplayforemp(this,'EmpdataforField','M_Emp_User',$('#slocDataForLead').val())">
				<option value="">Select</option>
						
				</select>
				</td>
					 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforField" name="tag_to_emp"
							class="form-control " disabled="true" data-valid="mand" style="width: 140 " onchange="Statelead()">
								<option value="">Select</option>
						</select></td>
					
				</tr>
				<tr>
				
					<td><strong>State<font color="red"></font></strong></td>
			     	 <td><input id="state" class="form-control"  type="text" name="tagto_me_state" value="Inprogress" readonly data-valid="mand"></td>
				   </tr>
	      		
				
				</table>
				<div id="createLineitemCrLead" style="display: none;">
					<form name="submitCrLineitemLead" id="submitCrLineitemLead"
						enctype="multipart/form-data">
						
					<!-- 	<table class="table table-bordered EditCrleadOrders">
						</table>
						<table class="table table-bordered" id="leadCrDetails">
						</table> -->
						<table class="table table-bordered" id="leadCrDetails1">
						</table>
					
							<table class="table table-bordered" id="leadCrDetails2">
						
						</table>
					
						<!-- <input type="hidden" name="gr_tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly> -->
						<table class="table table-bordered" id="leadCrrDetails1">
							<tr>
							<input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="" class="form-control"> 
									<input id="id" type="hidden" name="id" value="0" class="form-control">
										
							<!-- 	<td colspan="4"><input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="" class="form-control"> 
									<input id="id" type="hidden" name="id" value="0" class="form-control">
									
									<input type="hidden" name="action" value="ReturnStore" class="form-control"> 
									<input type="hidden" name="leadCrCheck" value="0">
									<!-- <button name="ReturnStore" type="button" style="margin-left: 450px;" class="btn btn-primary led" 
										onclick="ControlAssingTomeInprogresslead('ReturnStore','displayAssigntomeINPLd','EditAssigntoINPLd','submitAssigntomeINPLd','','O_AssignedLlead','')">Return To Store</button>	
								<button name="cancel" type="button" class="btn btn-primary led"
										onclick="ControlAssingTomeInprogresslead('Cancel','displayAssigntomeINPLd','EditAssigntomeINPLd','submitAssigntomeINPLd','O_AssignedLlead','')">Back</button> -->
									
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
<div id="EditAssign" style="display:none;">
	
		<form name="submitAssign" id="submitAssign">
	
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
							class="form-control readbaledata "   readonly data-valid="mand"  font-size: 1em;"></td>
				  </tr>
				    
				   <tr>
				   <td><strong>TAT<font color="red">*</font></strong></td>
						<td><input type="text" name="deliver_time" value="" 
							class="form-control readbaledata "  data-valid="mand"  readonly></td>
				  <td><strong>Reached Location<font color="red"></font></strong></td>
			        <td><input type="text" name="reach_lnformation" readonly value="" class="form-control" data-valid="mand"></input></td>
			     <!-- 	<td><select name="reach_lnformation" class="form-control"  onChange=Showcolfortat(this) data-valid="mand" readonly>
						<option value="On_Time">On Time</option>
				       <option value="Delay_in_TAT">Delay in TAT</option>
					</select></td> -->
					 </tr>
				<tr>
				<td class="reached_location"><strong>Reason for TAT<font color="red"></font></strong></td>
			     	  <td class="reached_location"><input id="reson_tat" class="form-control" type="text" name="reson_tat" readonly data-valid="mand" ></td>
					
					
				
					
						 <td><strong>Reason for visit<font color="red"></font></strong></td>
			     	<td><select name="reason_visit" class="form-control" disabled="true"="true"  data-valid="mand">
						<option value="">Select</option>
						<option value="both_Installation">Sale & Rental Installation</option>
						<option value="Sale_Installation">Sale Installation</option>
						<option value="Rental_Installation">Rental Installation</option>
						<option value="Demonstration">Demonstration</option>
						<option value="troubleshoot">Troubleshoot</option>
							<option value="Service">Service</option>
					</select></td>
					   </tr>
					
					 <tr>
					
						<td><strong>Service Type<font color="red"></font></strong></td>
			     	<td><select name="services" class="form-control" onChange=installeddate(this) readonly data-valid="mand">
						
						<option value="Rental_Only">Rental Only</option>
						<option value="Sale_Only">Sale Only</option>
						<option value="Sale_and_Rental">Sale and Rental</option>
					</select></td>
					
				    
			 
					 
							<!--<td><strong>Rental Days<font color="red">*</font></strong></td>
			         <td><input id="rental_day" class="form-control" type="text" name="rental_day" readonly data-valid="mand"></td> 
			   <td><strong>Date of Installation<font color="red">*</font></strong></td>
				     <td><input type="text" name="dt_install" value="" placeholder="chose only from the datepicker. Do not paste any data."
						id="rentdatepicker"	class="form-control readbaledata datepicker"  data-valid="mand"  readonly font-size: 1em;"></td>  -->
					 <td><strong>Amount Collect<font color="red"></font></strong></td>
			     	<td><select name="amt_clctn" class="form-control" disabled="true"="true" data-valid="mand">
						<option value="">Select</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
						
					</select></td>
				   </tr>
				   <tr>
						<td><strong>Mode of Payment<font color="red"></font></strong></td>
			     	<td><select name="pay_mode" class="form-control" disabled="true"="true"  data-valid="mand">
						<option value="">Select</option>
						<option value="FOC">FOC</option>
						<option value="Cash">Cash</option>
						<option value="UPI">UPI/Online</option>
						<option value="Cash&online">Cash&Online</option>
						<option value="Credit">Credit</option>
						<option value="Cheque">Cheque</option>
						<option value="not_avilable">NA</option>
						<option value="payment_due">Due</option>
					</select></td>
					
				
					
				
		 	
		 	<!--  <td><strong>Rental Expire on<font color="red"></font></strong></td>
			  <td><input type="text" name="dt_expinstall" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand"  readonly font-size: 1em;"></td>  -->
		 	
		 	 
			 
			
		
		
		
                  <td><strong>Amount<font color="red"></font></strong></td>
			   <td><input id="amount" class="form-control" type="text" name="amount"  value="0.0"  readonly data-valid="mand"></td> 
			        
			 </tr>
			   <tr>
			       <td><strong>Collect Amount<font color="red"></font></strong></td>
			        <td><input id="colct_amount" class="form-control" type="text" name="colct_amount"  value="0.0"  data-valid="mand" readonly ></td> 
			        
			         <td><strong>due Amount<font color="red"></font></strong></td>
			        <td><input id="due_amount" class="form-control" type="text" name="due_amount"  value="0.0"  data-valid="mand" readonly></td> 
			   </tr>
			 <tr> 
			      <td class="srv" style="display:none;"><strong>Cheque No.<font color="red"></font></strong></td>
			     	  <td class="srv" style="display:none;"><input id="cheque_no" class="form-control" type="text" name="cheque_no" readonly></td> 
			 
		
			     <td><strong>Refundable Amount<font color="red"></font></strong></td>
			  <td><input id="ref_amt" class="form-control" type="text" name="ref_amt" value="0.0" readonly data-valid="mand"></td> 
			   </tr>
	   
	<tr>
			   <td><strong>Transportation Amount<font color="red"></font></strong></td>
			  <td><input id="trns_amt" class="form-control" type="text" name="trns_amt" value="0.0" readonly data-valid="mand"></td> 
				
	<td><strong>Remark<font color="red"></font></strong></td>
          <td><input id="rmk" class="form-control" type="text" name="remark" readonly  data-valid="mand"></td>
	<!--     <td><strong>Additional Product Handed Over<font color="red"></font></strong></td>
	       <td><select multiple="multiple" id="subgroupDataForMaster" name="add_prod" style="height: 90px; width: 250px" class="form-control">
	                    <option value="mask"> Mask</option>
	                    <option value="spanner">Spanner</option>
	                    <option value="bag">Bag</option></select></td> -->
		  </tr>
	   
	<tr>
		
		  <td><strong>Payment Proof<font color="red"></font></strong></td>
		    <td>
				<div id="FinleName0" style="min-width: 150px;
    min-height: 130px;
  max-width: 150px;
      max-height: 130px;border: 2px solid #555;margin-right: 20px; border-radius: 2px;" >
				
				</div>
						<!-- <div id="FinleName1"></div>
						<div id="FinleName2"></div>
						<div id="FinleName3"></div>
						<div id="FinleName4"></div>
						<div id="FinleName5"></div>
						<div id="FinleName6"></div>
						<div id="FinleName7"></div>
						<div id="FinleName8"></div>
						<div id="FinleName9"></div>
				 -->
				</td>
				
				  <td><strong>Id Proof<font color="red"></font></strong></td>
		    <td>
				
						
					<div id="Filename" style="min-width: 150px;
    min-height: 130px;
  max-width: 150px;
      max-height: 130px;border: 2px solid #555;margin-right: 20px; border-radius: 2px;" >
				
				</div>
					
				
				</td>
				</tr>
				<tr>
		  <td><strong>Equipment Proof<font color="red"></font></strong></td>
		    <td>
				
					
								<div id="File_name" style="min-width: 150px;
    min-height: 130px;
  max-width: 150px;
      max-height: 130px;border: 2px solid #555;margin-right: 20px; border-radius: 2px;" >
				
				</div>	
					
				
				</td></tr>
		
	</table>
	
	
	    
	 <table class="table table-bordered" id="leadCrrDetails">
							<tr>
						<input type="hidden" name="action" value="" class="form-control"> 
								<td>
									
								<button name="cancel" type="button" style="margin-left: 500px;" class="btn btn-primary led" 
										onclick="ControlNewleaddeliver('Cancel','displayAssigntomeINPLd','EditAssigntoINPLd','submitAssigntomeINPLd','O_AssignedLlead','','EditAssign','submitAssign')">Back</button>
							
								</td>
							</tr>
						</table>
	
	</div>
				
				
	</form>
				
			</div>
				
		</div>
		
</section>
<!-- <div class="card">
<div id="EditReturntoStore" style="display:none;">
	
		<form name="submitReturntoStore" id="submitReturntoStore">
	
			<div id="createReturntoStore">
				<div class="card-header new">
					<h3 class="card-title1">Delivery Detail</h3>
				</div>
				<table class="table table-bordered" id="leadCrDetails">
				   <input type="hidden" name="id_lead_m" value="">
				    <input type="hidden" name="tag_by" value="">
				    
				   <tr>
				   <td><strong>Return Date<font color="red">*</font></strong></td>
			     	<td><input type="text" name="return_dt" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"  data-valid="mand"  font-size: 1em;"></td>
					 
					 <td><strong>Amount<font color="red">*</font></strong></td>
			   <td><input id="amount" class="form-control" type="text" name="amount"  value="0.0" readonly  data-valid="mand"></td> 
					</tr>
				
				<tr>
		 	   <td><strong>Refundable Amount<font color="red"></font>*</strong></td>
			  <td><input id="refund_amt" class="form-control" type="text" name="refund_amt" value="0.0" readonly data-valid="mand"></td> 
		 
		     <td><strong>Adjustment Refundable amount<font color="red">*</font></strong></td> 
			   <td><input id="refund_amt_adjust" class="form-control" type="text" name="refund_amt_adjust" value="" onchange="CalculateRefundamt(this)" data-valid="mand"></td>  
		</tr>	
		
		<tr>
		     
         	 <td><strong>Total Refundable amount<font color="red">*</font></strong></td> 
			   <td><input id="tot_ref_amt" class="form-control" type="text" name="tot_ref_amt" value=""  data-valid="mand"></td>  
			
			<td><strong>Remark<font color="red"></font></strong></td>
          <td><input id="rmk" class="form-control" type="text" name="rmk"   data-valid=""></td>
			</tr>
			
	</table>
	
	 <table class="table table-bordered" id="leadCrrDetails">
							<tr>
						<input type="hidden" name="action" value="" class="form-control"> 
								<td>
										
								<button name="ReturnStore" type="button" class="btn btn-primary led" 
										onclick="ControlReturntoStore('ReturnStore','displayAssigntomeINPLd','EditAssigntoINPLd','submitAssigntomeINPLd','O_AssignedLlead','','EditReturntoStore','submitReturntoStore')">Return To Store</button>	
								<button name="cancel" type="button" class="btn btn-primary led" 
										onclick="ControlReturntoStore('Cancel','displayAssigntomeINPLd','EditAssigntoINPLd','submitAssigntomeINPLd','O_AssignedLlead','','EditReturntoStore','submitReturntoStore')">Back</button>
							
								</td>
							</tr>
						</table>
	
	</div>
				
				
				
</form>
				
				
</div>
				
			
</div> -->
	
