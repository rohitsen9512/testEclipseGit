

<script type="text/javascript">

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
    
jQuery("input#file4ID1").change(function () {
	
	 var formData = new FormData();	 
		formData.append('file', $('#file4ID1').get(0).files[0]);
		
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
			    	
			    		$('input[name="file_name1"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
			
	//$( "#submitForUploadData" ).trigger( "click" );
}); 
$('input[name="dt_req"]').datepicker("setDate", currentDate);
	 /* $('input[name="dt_to"]').datepicker("setDate", currentDate);
	currentDate.setMonth(currentDate.getMonth() - 1);
	$('input[name="dt_frm"]').datepicker("setDate", currentDate);  */
	//$('input[name="dt_req"]').datepicker("setDate", currentDate);      
	      
	/* $( ".reqDatepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	  $('#purchaseDatePicker').removeClass('error');
	    	  var dt_req=$('#purchaseDatePicker').val();
	    	  $.post('P_Request_PR',{action : 'CheckDate' ,dt_req:dt_req},function (r){
		    		if(r.data == 0)
		    			{
		    			
		    			alert('Requested Date should be between financial year : '+r.std_finance +' - '+r.end_finance);
		    			$('#purchaseDatePicker').focus();
		    			$('#purchaseDatePicker').val('');
		    			$('#purchaseDatePicker').addClass('error');
		    				exit(0);
		    			}
		    		
		    },'json');
	    	  
	    	  
	      }
	    }); */

	
						DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroup','CapG',function (status){
							if(status)
							{
								GetDropDownDataForAssetOwner('typAssetForPrRequest',function (status){
									if(status)
									{
										DisplayDropDownData("M_Emp_User","empForNewTicketHD",function (status){
											if(status)
											{
												DropDownDataDisplay('M_Dept','depDataForNewTicket',function (status){
													if(status)
													{
														ControlHelpDesk('Onload','','','','New_Ticket','','','');
													}});
													
												/* DisplayDropDownData("M_Emp_User","empForAssignToHD",function (status){
													if(status)
													{
														
													}}); */
											}});
										
									}});
								 
			}});
	
		

});


</script>

<%HttpSession session2 = request.getSession(); %>

		<section class="content-header ">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
					 <!--  Help -->
						
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">HelpDesk</a></li>
						<li class="breadcrumb-item">Create Ticket</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	<form name="submitTicket" id="submitTicket">
	<div id="createPR">
	<div class="card-small">
	
		<table class="table table-bordered" id="invoiceDetails">
			<tr > 
			<div class="new">
			   <h1  ><center>Request Details</center></h1>
			</div>
				<!-- <td colspan="4" class="tableHeader"><center><p class="tableHeaderContent">Request Details</p></center></td> -->
			</tr>
			<tr>
			<td><strong>Ticket Number<font color="red">*</font></strong></td>
			<td>
			
			<input type="text" name="ticket_no" value="" readonly="readonly" class="form-control readbaledata" data-valid="mand" ></td>
			<td><strong>Requested date<font color="red">*</font></strong></td>
			<td>
			<input type="text" name="dt_req" value="" disabled class="form-control datepicker readbaledata" data-valid="mand" >
			</td>
			</tr>
			<!-- <tr>
			<td ><b>Impact<font color="red">*</font></b></td>
				<td >
				<select name="impact" class="form-controlreadbaledata" data-valid="mand" onchange="SetPriority()"> 
						 <option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
				</td>
			<td ><b>Requested by<font color="red">*</font></b></td>
				<td >
					<select name="req_by" id="empForNewTicketHD" class="form-controlreadbaledata" data-valid="mand" > 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
				</td>
			</tr> -->
			<!-- <tr>
			<td><strong>Urgency <font color="red">*</font></strong></td>
			<td>
			<select name="urgency" class="form-controlreadbaledata" data-valid="mand" onchange="SetPriority()"> 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
			</td>	
			<td><strong>Assignment Group <font color="red">*</font></strong></td>
			<td>
			<select name="assign_grp" id="depDataForNewTicket" class="form-controlreadbaledata" data-valid="mand" onChange="SubDropDownDataDisplay(this,'empForAssignToHD','M_Emp_User')"> 
						 <option value="">Select</option>
						 
				</select> 
			</td>	
				
			</tr> -->
			<!-- <tr>
			<td ><b>Priority<font color="red">*</font></b></td>
				<td >
				<input type="hidden" name="priority" class="form-controlreadbaledata" value="3">
				<input type="text" name="priority1" class="form-controlreadbaledata" value="5-Planning" data-valid="mand" readonly>
				</td>
			
			<td><strong>Assign To <font color="red">*</font></strong></td>
			<td>
			<select name="assign_to" id="empForAssignToHD" class="form-controlreadbaledata" data-valid="mand" > 
						 <option value="">Select</option>
						 
				</select> 
			</td>	
			
			</tr> -->
			<!-- <tr>
			<td ><b>Department<font color="red"></font></b></td>
				<td >
					<select name="id_dept" id="requestedPrDept"  style="width:140" class="form-controlreadbaledata"  onChange="SubDropDownDataDisplay(this,'requestedPrCC','M_Cost_Center')"> 
						<option value="">Select</option>
						
				</select>
				<input type="text" name="nm_dept" value="" readonly="readonly">
				<input type="hidden" name="id_dept" value="">
				</td>
			
			  <td ><b>Cost center<font color="red"></font></b></td>
				<td >
				<input type="text" name="nm_cc" value="" readonly="readonly" >
				<input type="hidden" name="id_cc" value="">
					<select name="id_cc" id="requestedPrCC"  style="width:140" class="form-controlreadbaledata" data-valid="mand" > 
						 <option value="">Select</option>
						
				</select> 
				</td>
			  
			</tr> -->
			
		<!-- <tr>
				
				<td ><b>Asset Type <font color="red">*</font></b></td>
				<td >
					 <select name="asst_type" id="typAssetForPrRequest"  style="width:140"  class="form-controlreadbaledata" data-valid="mand">
						<option value="">Select</option>
						<option value="IT">IT</option>
						<option value="NIT">Non IT</option>
						
				</select> 
				</td>
				<td ><b>State<font color="red">*</font></b></td>
				<td >
				<select name="state" class="form-controlreadbaledata" data-valid="mand" > 
						 <option value="New">New</option>
						 <option value="Active">In-Progress</option>
						 <option value="Awaiting User Info">Awaiting User Info</option>
						 <option value="Closed">Closed</option>
						  <option value="Closed as in complete">Closed as in complete</option>
						   <option value="Cancelled">Cancelled</option>
				</select> 
				</td>
			<td ><b>Category<font color="red">*</font></b></td>
				<td >
					<select name="id_grp"   style="width:140" id="requestPrAssetGroup" class="form-controlreadbaledata" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'requestPrAssetSubGroup','M_Subasset_Div')">
						<option value="">Select</option>
						
				</select>
				</td>
				</tr> -->
			 <tr>
			  
				 <!--  <td ><b>Sub Category<font color="red">*</font></b></td>
				<td >
					<select name="id_sgrp"  style="width:140" id="requestPrAssetSubGroup" class="form-controlreadbaledata" data-valid="mand" onChange="DisplaySubDropDownData(this,'proNameForReqPR','A_Add_To_Store')">
						<option value="">Select</option>
						
				</select>
				</td>  -->
				<td ><b>Phone Number</b></td>
				<td><input type="text" name="ph_no" class="form-control readbaledata"  ></td>
				<td><b>Document</b></td>
				<td><input id="file4ID1" type="file" name="file1" class="form-control" value="" ></td>
		
			  <!--  <td><strong>Configuration Item<font color="red">*</font></strong></td>
				<td>
				<select name="id_prod"   class="form-controlreadbaledata" data-valid="mand" id="proNameForReqPR" onChange="onChangeProductName(this)">
						<option value="">Select</option>
						
				</select>
				</td> -->
				</tr>
			<!-- 	<tr>
				
				<td><strong>Manufacturer <font color="red">*</font></strong></td>
				<td><input type="text" name="no_mfr" class="form-controlreadbaledata" data-valid="mand" readonly></td>
				<td><strong>Model Number<font color="red">*</font></strong></td>
				<td><input type="text" name="serial_no"  class="form-controlreadbaledata" data-valid="mand" readonly></td>
				
				</tr> -->
				
				
				<tr>
				<td><strong>Short Description<font color="red">*</font></strong></td>
				<td colspan="3"><input type="text" name="short_description" style="width: 530px;" data-valid="mand" class="form-control readbaledata"></td>
				
				
				</tr>
			<tr>
			
				<td><b>Work Notes</b></td>
				  <td colspan="3"><textarea style="margin-left: 0px;margin-right: 0px;width: 530px;height: 65px;"  name ="work_note" class="form-control readbaledata"></textarea> </td>
			
			</tr>
			<tr>
				<td colspan="4">
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="file_name1" value="" class="form-control">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary req" onclick="ControlHelpDesk('Save','','','submitTicket','New_Ticket','')">Submit</button>
				</td>
			</tr>
			
			</table>
	
	</div>
	</div>
</form>	