

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
$('input[name="dt_req"]').datepicker("setDate", currentDate);

ControlHelpDeskEss('Onload','','','','Ess_New_Ticket','','','');
	
					/* 	DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroup','CapG',function (status){
							if(status)
							{
								GetDropDownDataForAssetOwner('typAssetForPrRequest',function (status){
									if(status)
									{
										DisplayDropDownData("M_Emp_User","empForNewTicketHD",function (status){
											if(status)
											{
												DisplayDropDownData('M_Dept','depDataForNewTicket',function (status){
													if(status)
													{
														ControlHelpDeskEss('Onload','','','','Ess_New_Ticket','','','');
													}});
													
												 DisplayDropDownData("M_Emp_User","empForAssignToHD",function (status){
													if(status)
													{
														
													}}); 
											}});
										
									}});
								 
			}}); */
	
		

});


</script>

<%HttpSession session2 = request.getSession(); %>
<div class="card">
	
	<form name="submitTicket" id="submitTicket">
	<div id="createPR">
	
		<table class="table table-bordered" id="invoiceDetails">
			<tr>
				<td colspan="4" class="tableHeader"><center><H3 class="tableHeaderContent">Request Details</H3></center></td>
			</tr>
			<tr>
			<td><strong>Ticket Number <font color="red">* </font></strong></td>
			<td>
			
			<input type="text" name="ticket_no" value="" readonly="readonly" class="form-control readbaledata" data-valid="mand" ></td>
			<td><strong>Requested date <font color="red">* </font></strong></td>
			<td>
			<input type="text" name="dt_req" value="" readonly="readonly" class="form-control datepicker readbaledata" data-valid="mand" >
			</td>
			</tr>
			<!-- <tr>
			<td ><b>Impact<font color="red"> * </font>:</b></td>
				<td >
				<select name="impact" class="form-control readbaledata" data-valid="mand" onchange="SetPriority()"> 
						 <option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
				</td>
			<td ><b>Requested by<font color="red"> * </font>:</b></td>
				<td >
					<select name="req_by" id="empForNewTicketHD" class="form-control readbaledata" data-valid="mand" > 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
				</td>
			</tr>
			<tr>
			<td><strong>Urgency <font color="red">* </font></strong></td>
			<td>
			<select name="urgency" class="form-control readbaledata" data-valid="mand" onchange="SetPriority()"> 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
			</td>	
			<td><strong>Assignment Group <font color="red">* </font></strong></td>
			<td>
			<select name="assign_grp" id="depDataForNewTicket" class="form-control readbaledata" data-valid="mand" onChange="DisplaySubDropDownData(this,'empForAssignToHD','M_Emp_User')"> 
						 <option value="">Select</option>
						 
				</select> 
			</td>	
				
			</tr>
			<tr>
			<td ><b>Priority<font color="red"> * </font>:</b></td>
				<td >
				<input type="hidden" name="priority" class="form-control readbaledata" value="3">
				<input type="text" name="priority1" class="form-control readbaledata" value="5-Planning" data-valid="mand" readonly>
				</td>
			
			<td><strong>Assign To <font color="red">* </font></strong></td>
			<td>
			<select name="assign_to" id="empForAssignToHD" class="form-control readbaledata" data-valid="mand" > 
						 <option value="">Select</option>
						 
				</select> 
			</td>	
			
			</tr> -->
			<!-- <tr>
			<td ><b>Department<font color="red"> </font>:</b></td>
				<td >
					<select name="id_dept" id="requestedPrDept"  style="width:140" class="form-control readbaledata"  onChange="SubDropDownDataDisplay(this,'requestedPrCC','M_Cost_Center')"> 
						<option value="">--Select--</option>
						
				</select>
				<input type="text" name="nm_dept" value="" readonly="readonly">
				<input type="hidden" name="id_dept" value="">
				</td>
			
			  <td ><b>Cost center<font color="red">  </font>:</b></td>
				<td >
				<input type="text" name="nm_cc" value="" readonly="readonly" >
				<input type="hidden" name="id_cc" value="">
					<select name="id_cc" id="requestedPrCC"  style="width:140" class="form-control readbaledata" data-valid="mand" > 
						 <option value="">--Select--</option>
						
				</select> 
				</td>
			  
			</tr> -->
			
		<!-- <tr>
				
				<td ><b>Asset Type <font color="red"> * </font>:</b></td>
				<td >
					 <select name="asst_type" id="typAssetForPrRequest"  style="width:140"  class="form-control readbaledata" data-valid="mand">
						<option value="">--Select--</option>
						<option value="IT">IT</option>
						<option value="NIT">Non IT</option>
						
				</select> 
				</td>
				<td ><b>State<font color="red"> * </font>:</b></td>
				<td >
				<select name="state" class="form-control readbaledata" data-valid="mand" > 
						 <option value="New">New</option>
						 <option value="Active">Active</option>
						 <option value="Awaiting User Info">Awaiting User Info</option>
						 <option value="Closed">Closed</option>
				</select> 
				</td>
			<td ><b>Category<font color="red"> * </font>:</b></td>
				<td >
					<select name="id_grp"   style="width:140" id="requestPrAssetGroup" class="form-control readbaledata" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'requestPrAssetSubGroup','M_Subasset_Div')">
						<option value="">--Select--</option>
						
				</select>
				</td>
				</tr>
			 <tr>
			  
				  <td ><b>Sub Category<font color="red"> * </font>:</b></td>
				<td >
					<select name="id_sgrp"  style="width:140" id="requestPrAssetSubGroup" class="form-control readbaledata" data-valid="mand" onChange="DisplaySubDropDownData(this,'proNameForReqPR','A_Add_To_Store')">
						<option value="">--Select--</option>
						
				</select>
				</td> 
			   <td><strong>Configuration Item<font color="red"> * </font></strong></td>
				<td>
				<select name="id_prod"   class="form-control readbaledata" data-valid="mand" id="proNameForReqPR" onChange="onChangeProductName(this)">
						<option value="">--Select--</option>
						
				</select>
				</td>
				</tr>
				<tr>
				
				<td><strong>Manufacturer <font color="red"> * </font></strong></td>
				<td><input type="text" name="no_mfr" class="form-control readbaledata" data-valid="mand" readonly></td>
				<td><strong>Model Number<font color="red"> * </font></strong></td>
				<td><input type="text" name="serial_no"  class="form-control readbaledata" data-valid="mand" readonly></td>
				
				</tr> -->
				<tr>
				<td ><b>Impact<font color="red"> * </font>:</b></td>
					<td>
						<select name="urgency" class="form-control readbaledata" data-valid="mand" onchange="SetPriority()"> 
								<option value="3">3-Low</option>
								 <option value="2">2- Medium</option>
								 <option value="1">1-High</option>
						</select> 
					</td>	
					<td><strong>Email Id (, Separated)</strong></td>
					<td>
					
					<input type="text" name="email_ids" value=""  class="form-control readbaledata"></td>
					
				</tr>
				<tr>
				
				<td><strong>Phone Number<font color="red">* </font> </strong></td>
				<td><input type="text" name="ph_no" class="form-control readbaledata" data-valid="mand" ></td>
				<td colspan="2"></td>
				</tr>
				
				<tr>
				<td><strong>Short Description<font color="red"> * </font></strong></td>
				<td colspan="3"><textarea  rows="4" cols="50" name="short_description" style="margin: 0px 0px 10px;width: 547px; height: 98px;" data-valid="mand" class="form-control readbaledata"></textarea></td>
				
				
				</tr>
			<tr style="display:none;">
			
				<td><b>Work Notes</b></td>
				  <td colspan="3"><textarea style="margin-left: 0px;margin-right: 0px;width: 530px;height: 65px;"  name ="work_note" class="form-control readbaledata"></textarea> </td>
			
			</tr> 
			<tr>
				<td colspan="4">
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="assign_to" value="">
					<input type="hidden" name="assign_grp" value="">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary req" onclick="ControlHelpDeskEss('Save','','','submitTicket','Ess_New_Ticket','')">Submit</button>
				</td>
			</tr>
			
			</table>
	</div>
	
</form>	
</div>