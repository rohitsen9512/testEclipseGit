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
		
		DisplayForAprovedelivery('O_AssignedLlead', 'displayforapprv', 'EditCrforapprv', '','submitforapprv',
				'leadapprvtd');
		$('button[name="update"]').addClass('hideButton');
		$('button[name="save1"]').addClass('hideButton');

		
		DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
			if (status) {
				
				DisplayDropDownData("M_Emp_User","empdataforapprvfordelvrLd",function(status) {
					if (status) {
						DisplayDropDownData("M_Emp_User","empdatafortagbyLd",function(status) {
							if (status) {
																								
								dispalyModifyLineCrItemLead('');
			 }
		});
					 }
				});
			}
		});
																
					
				
	});


	
async function getjsondataAPI() {
	    //
	/*    Storing response*/
	    const response = await fetch('InvoiceScanFile/Lead_state.json');
	    
	    /*Storing data in form of JSON*/
	    var data = await response.json();
	  
	    console.log(data);

	var s=document.getElementById("state");
	    
	//var s = $('#state').val();
	    
	  
	    for (var i =0;i<data.length;i++){
	    	if(data[i].state=="state" ||data[i].state=="State" ){
	    		
	    		var option = document.createElement("option");
	        	option.text = data[i].nm_attribute;
	        	option.value = data[i].val_attr;
	        	s.appendChild(option);
				//ss.appendChild(option);
	    	}
	    	 }
	   
	   
	} 


	
	
	

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
	<div id="displayforapprv">
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
							onclick="DisplayCrInprogressLead('O_AssignedLlead','displayforapprv','creaCrteLead','','leadCrForDisplaytd')">Search
						</button>
					</td>
				</tr>
				<tr></tr>
			</table>
			<table id="leadapprvtd"
				class="table table-bordered table-hover leadapprvtd">
				
			</table>
		</div>
	</div>
</div>
<section class="content">
<div class="card">
<div id="EditCrforapprv" style="display: none;">
	
		<form name="submitforapprv" id="submitforapprv">
		<!-- enctype="multipart/form-data" action="Upload_Fil" method="post" -->
			<div id="createLead" >
				<div class="card-header new">
					<h3 class="card-title1">Lead Details</h3>
				</div>
				         
				<table class="table table-bordered" id="leadCrDetails">
					 <input type="hidden" name="state_aprvdelvr" value="Approved" id="state_aprvdelvr">
					<tr>
						<td><strong>Approved By<font color="red">*</font>
						</strong></td>
					
						<td><select id="empdataforapprvfordelvrLd" name="apprvby_frdelvr"
							class="form-control readbaledata"  data-valid="mand" style="width: 140" disabled="true">
								<option value="">Select</option>
						</select></td> 
					<td><strong>Approved Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_apprv_delvr" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker"   data-valid="mand"  font-size: 1em;"></td>
					</tr>
					<tr>
						<td><strong>Lead Id<font color="red">*</font>
						</strong></td>
						<td><input id="LeadNum" type="text" name="lead_no" value=""
						    class="form-control readbaledata" readonly data-valid="mand"></td>
						  
						  
						   <td><b>Tag By<font color="red">*</font>
						   <td><select id="empdatafortagbyLd" name="tag_by"
							class="form-control" disabled="true" data-valid="mand" style="width: 140">
								<option value="">Select</option>
						</select></td>  
						</tr> 
						<td><strong>Tag Date<font color="red">*</font></strong></td>
						<td><input type="text" name="dt_tag" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata"  data-valid="mand" readonly font-size: 1em;"></td>
					
				
		
						
						
			    
			    	 	<td><b>Source Code<font color="red">*</font></b></td>
						<td><input id="cd_src" class="form-control"  type="text" name="cd_src" readonly data-valid="mand"></td>
						</tr>
					
			<tr>
					 <td><strong>Patient Name<font color="red">*</font></strong></td>
				<td><input id="nm_pstn" class="form-control" type="text" name="nm_pstn" readonly="readonly" data-valid="mand"></td>
				
				
				
				
					 <td><strong>Alternate Contact No.<font color="red">*</font></strong></td>
				<td><input id="alt_pstn_ct" class="form-control" type="text" name="alt_pstn_ct" data-valid="mand" readonly="readonly"></td>
					</tr>
				
				<tr>
					
					 <td><strong>Contact No.<font color="red">*</font></strong></td>
				<td><input id="pstn_ct" class="form-control" type="text" name="pstn_ct" readonly data-valid="mand"></td>
					
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
			       <td><select name="id_design_tagemp" id="dgnForEmp" disabled ="true" class="form-control" data-valid="mand" 
				    style="width: 140" onChange="SubDropDownDataDisplayforemp(this,'EmpdataforCordinator','M_Emp_User',$('#slocDataForLead').val())">
				   <option value="">Select</option></select></td>
					 
					 <td><strong>Assign TO<font color="red">*</font></strong></td>
			        <td><select id="EmpdataforField" disabled ="true"  name="tag_to_emp"
							class="form-control " data-valid="mand" style="width: 140 " onchange="Statelead()">
								<option value="">Select</option>
						</select></td>
					
				</tr>
					
								
					<td><strong>State<font color="red"></font></strong></td>
					 <td><input id="state" class="form-control"  type="text" name="state" value="Inprogress" readonly data-valid="mand"></td>
			     	
			     	
			     	<!-- <td><select name="state" id="state" class="form-control" disabled="true" data-valid="mand">
							    <option value="Inprogress">Inprogress</option>
						<option value="New">New</option>
						<option value="Closed">Closed</option>
				   </select></td>   -->
				   
			
						
				   <!-- <td><strong>Assing Date<font color="red">*</font></strong></td>
						<td><input type="text" name="assign_dt" value="" placeholder="chose only from the datepicker. Do not paste any data."
							class="form-control readbaledata datepicker" disabled="true"  data-valid="mand" readonly font-size: 1em;"></td> -->
				   
			       
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
						<tr><strong>Refundable Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="refund_amt" value="0.0" id="refund "
							class="form-control resetAcc FieldResize" onChange="addinTotLead(this)" readonly></input><tr>
						<tr><strong>Transportation Amount<font color="red">*</font></strong></tr>
						<tr><input type="text" name="trnsprt_amt" value="0.0" id=" trnsprt"
							class="form-control resetAcc FieldResize" onChange="addinTotLead(this)" readonly></input></tr>
						<tr><strong>Grand Total<font color="red">*</font></strong></tr>
							<tr><input type="text" name="tot" value="0.0" id=" "
							class="form-control resetAcc FieldResize" readonly></input></tr>
								<tr><strong><div id="grandtotinword">Zero only</div></strong></tr> 
						</div> 
						<!-- <table class="table table-bordered" id="leadCrDetails2">
						</table> -->
						<input type="hidden" name="gr_tot" value="0.0" id=""
							class="form-control resetAcc FieldResize" readonly>
						<table class="table table-bordered" id="leadCrrDetails">
							<tr>
								<td colspan="4"><input type="hidden" name="itemCount" value="10"> 
									<input type="hidden" name="action" value="Assign" class="form-control"> 
									<!-- <input type="hidden"name="invoice_file" value="" class="form-control"> -->
									 <input id="id" type="hidden" name="id" value="0" class="form-control">
							<button name="Approve" type="button" style="margin-left: 400px;"
										class="btn btn-primary led"
										onclick="ControlForAprovedelivery('Approvefordeliver','displayforapprv','EditCrforapprv','submitforapprv','O_AssignedLlead','')">Approve</button>
									<input type="hidden" name="leadCrCheck" value="0">
								<button name="cancel" type="button"  class="btn btn-primary inv"
										onclick="ControlForAprovedelivery('Cancel','displayforapprv','EditCrforapprv','submitforapprv','O_AssignedLlead','')">Back</button>
									
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</form>
	</div>
	
	
<section>
