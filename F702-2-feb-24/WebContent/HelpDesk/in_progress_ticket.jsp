<!--Edit_from_store.jsp-->


<script type="text/javascript">


$(function() {
	
	$(".Activity").click(function(){
        $(".activityMessage").toggle(1000);
    });
	DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroup','CapG',function (status){
		if(status)
		{
			DropDownDataDisplay('M_Group','depDataForNewTicket',function (status){
				if(status)
				{
					DisplayDropDownData("M_Emp_User","empForNewTicketHD",function (status){
						if(status)
						{
							/* DisplayDropDownData("M_Emp_User","empForAssignToHD",function (status){
								if(status)
								{ */
		ControlHelpDesk('List','DisplayInProgressTicket','EditDetailsForInProgressTicket','SubmitFormForEditInProgressTickets','New_Ticket','displayDataForEditInProgressTicket','','');
								/* }}); */
						}});
					
				}});
			 
}});


	$('.grndatepicker').each(function () {
		
        if ($(this).hasClass('hasDatepicker')) {
            $(this).removeClass('hasDatepicker');
        } 
         $(this).datepicker({
        	yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true
	    });
    });

	$('#EditDetailsForInProgressTicket').hide();
	
	
});


</script>
<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Help-->
						
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">HelpDesk</a></li>
						<li class="breadcrumb-item">In Progress Ticket</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
<!-- <div class="commonDiv" id="SearchFromForEditFromstore">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForEdit('A_Edit_From_Store')">
</div> -->

<div class="card ">
<div id="DisplayInProgressTicket">
<div class="card-body">
				<table id="displayDataForEditInProgressTicket"
					class="table table-bordered table-hover displayDataForEditInProgressTicket">

	
		<tr class="info ">
			
		</tr>
	</table>
</div>
</div>
</div>
<div class="card ">
<div id="EditDetailsForInProgressTicket">
	<form name="SubmitFormForEditInProgressTickets" id="SubmitFormForEditInProgressTickets">
		<table class="table table-bordered " id="invoiceDetails">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent">Request Details</p></td>
			</tr>
			<tr>
			<td><strong>Ticket Number <font color="red">*</font></strong></td>
			<td>
			
			<input type="text" name="ticket_no" value="" readonly="readonly" class="form-control readbaledata" data-valid="mand" ></td>
			<td><strong>Requested date <font color="red">*</font></strong></td>
			<td>
			<input type="text" name="dt_req" value="" readonly="readonly" class="form-control datepicker readbaledata" data-valid="mand" >
			</td>
			</tr>
			<tr>
			<td ><b>Impact<font color="red">*</font></b></td>
				<td >
				<select name="impact" class="form-control readbaledata" data-valid="mand" onchange="SetPriority()"> 
						 <option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
				</td>
			<td ><b>Requested by<font color="red"> * </font>:</b></td>
				<td >
					<select name="req_by" id="empForNewTicketHD" class="form-control readbaledata" disabled data-valid="mand" > 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
				</td>
			</tr>
			<tr>
			<td><strong>Urgency<font color="red">*</font></strong></td>
			<td>
			<select name="urgency" class="form-control readbaledata" data-valid="mand" onchange="SetPriority()"> 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
			</td>	
			<td><strong>Assignment Group<font color="red">*</font></strong></td>
			<td>
			<select name="assign_grp" id="depDataForNewTicket" class="form-control readbaledata" data-valid="mand" onChange="SubDropDownDataDisplay(this,'empForAssignToHD','M_GroupAssign')"> 
						 <option value="">Select</option>
						 
				</select> 
			</td>	
				
			</tr>
			<tr>
			<td ><b>Priority<font color="red">*</font></b></td>
				<td >
				<input type="hidden" name="priority" class="form-control readbaledata" value="5">
				<input type="text" name="priority1" class="form-control readbaledata" value="5-Planning" data-valid="mand" readonly>
				</td>
			<td><strong>Assign To <font color="red">*</font></strong></td>
			<td>
			<select name="assign_to" id="empForAssignToHD" class="form-control readbaledata" data-valid="mand" > 
						 <option value="">Select</option>
						 
				</select> 
			</td>
			</tr>
			<!-- <tr>
			<td ><b>Department<font color="red"></font></b></td>
				<td >
					<select name="id_dept" id="requestedPrDept"  style="width:140" class="form-control readbaledata"  onChange="SubDropDownDataDisplay(this,'requestedPrCC','M_Cost_Center')"> 
						<option value="">Select</option>
						
				</select>
				<input type="text" name="nm_dept" value="" readonly="readonly">
				<input type="hidden" name="id_dept" value="">
				</td>
			
			  <td ><b>Cost center<font color="red"></font></b></td>
				<td >
				<input type="text" name="nm_cc" value="" readonly="readonly" >
				<input type="hidden" name="id_cc" value="">
					<select name="id_cc" id="requestedPrCC"  style="width:140" class="form-control readbaledata" data-valid="mand" > 
						 <option value="">Select</option>
						
				</select> 
				</td>
			  
			</tr> -->
			
		<tr>
				
				<!-- <td ><b>Asset Type <font color="red">*</font></b></td>
				<td >
					 <select name="asst_type" id="typAssetForPrRequest"  style="width:140"  class="form-control readbaledata" data-valid="mand">
						<option value="">Select</option>
						<option value="IT">IT</option>
						<option value="NIT">Non IT</option>
						
				</select> 
				</td> -->
				<td ><b>State<font color="red">*</font></b></td>
				<td >
				<select name="state" class="form-control readbaledata" data-valid="mand" > 
						 <option value="New">New</option>
						 <option value="Active">In-Progress</option>
						 <option value="Awaiting User Info">Awaiting User Info</option>
						 <option value="Closed">Closed</option>
						  <option value="Closed as in complete">Closed as in complete</option>
						   <option value="Cancelled">Cancelled</option>
				</select> 
				</td>
				<td ><b>Phone Number</b></td>
				<td><input type="text" name="ph_no" class="form-control readbaledata" data-valid="mand" ></td>
			<!-- <td ><b>Category<font color="red">*</font></b></td>
				<td >
					<select name="id_grp"   style="width:140" id="requestPrAssetGroup" class="form-control readbaledata" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'requestPrAssetSubGroup','M_Subasset_Div')">
						<option value="">Select</option>
						
				</select>
				</td> -->
				</tr>
			  <tr>
			  
				 <!--  <td ><b>Sub Category<font color="red">*</font></b></td>
				<td >
					<select name="id_sgrp"  style="width:140" id="requestPrAssetSubGroup" class="form-control readbaledata" data-valid="mand" onChange="DisplaySubDropDownData(this,'proNameForReqPR','A_Add_To_Store')">
						<option value="">Select</option>
						
				</select>
				</td>  -->
				
				
			  <!--  <td><strong>Product Name<font color="red">*</font></strong></td>
				<td>
				<select name="id_prod"   class="form-control readbaledata" data-valid="mand" id="proNameForReqPR" onChange="onChangeProductName(this)">
						<option value="">Select</option>
						
				</select>
				</td> -->
				</tr>
			<!--	<tr>
				
				<td><strong>Manufacturer <font color="red">*</font></strong></td>
				<td><input type="text" name="mfr" class="form-control readbaledata" data-valid="mand" readonly></td>
				<td><strong>Model Number<font color="red">*</font></strong></td>
				<td><input type="text" name="model"  class="form-control readbaledata" data-valid="mand" readonly></td>
				
				</tr> -->
				<tr>
				<td><strong>Short Description<font color="red">*</font></strong></td>
				<td colspan="3"><input type="text" name="short_description" style="width: 530px;" data-valid="mand" class="form-control readbaledata"></td>
				
				
				</tr>
				<tr>
				<td><strong>Document</strong></td>
				<td colspan="3" id="FileName"></td>
				
				
				</tr>
			<tr>
			
				<td><b>Work Notes</b></td>
				  <td colspan="3"><textarea style="margin-left: 0px;margin-right: 0px;width: 530px;height: 65px;"  name ="work_note" class="form-control readbaledata"></textarea> </td>
			
			</tr>
			
			<tr>
			<td><b style="float: right;"><a href="#" class="Activity">Activity</a></b></td>
				<td colspan="3" class="activityLogs" style="border-left-style: hidden;">
					
				</td>
			</tr>
			
			
			<tr>
					
					<td colspan="4">
						<input type="hidden" name="id" value="">
						<input type="hidden" name="action" value="Update">
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary update" onclick="ControlHelpDesk('Update','DisplayInProgressTicket','EditDetailsForInProgressTicket','SubmitFormForEditInProgressTickets','In_Progress_Ticket','','','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary update" onclick="ControlHelpDesk('Cancel','DisplayInProgressTicket','EditDetailsForInProgressTicket','SubmitFormForEditInProgressTickets','In_Progress_Ticket','','','')">Cancel</button>
					</td>
				</tr>
			
			</table>
		
	</form>
</div>
</div>
