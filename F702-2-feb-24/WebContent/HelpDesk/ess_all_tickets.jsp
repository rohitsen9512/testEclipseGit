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
		ControlHelpDeskEss('List','DisplayAllTicket','EditDetailsForAllTicket','SubmitFormForEditAllTickets','Ess_New_Ticket','displayDataForEditAllTicket','','');
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

	$('#EditDetailsForAllTicket').hide();
	
	
});


</script>

<!-- <div class="commonDiv" id="SearchFromForEditFromstore">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForEdit('A_Edit_From_Store')">
</div> -->


<div id="DisplayAllTicket" class="card">
	<table class="table table-bordered displayDataForEditAllTicket">
		<tr class="info">
			
		</tr>
	</table>
</div>

<div id="EditDetailsForAllTicket" class="card">
	<form name="SubmitFormForEditAllTickets" id="SubmitFormForEditAllTickets">
		<table class="table table-bordered" id="invoiceDetails">
			<tr>
				<td colspan="4" class="tableHeader"><center><H3 class="tableHeaderContent">Request Details</H3></center></td>
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
			
			<td ><b>Requested by<font color="red">*</font></b></td>
				<td >
					<select name="req_by" id="empForNewTicketHD" class="form-control readbaledata" disabled data-valid="mand" > 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
				</td>
				<td ><b>State<font color="red">*</font></b></td>
				<td >
				<select name="state" class="form-control" data-valid="mand" disabled> 
						 <option value="New">New</option>
						 <option value="Active">In-Progress</option>
						 <option value="Awaiting User Info">Awaiting User Info</option>
						 <option value="Closed">Closed</option>
						  <option value="Closed as in complete">Closed as in complete</option>
						   <option value="Cancelled">Cancelled</option>
				</select> 
				</td>
			</tr>
			<tr>
			<td><strong>Urgency <font color="red">*</font></strong></td>
			<td>
			<select name="urgency" class="form-control readbaledata" disabled data-valid="mand" onchange="SetPriority()"> 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
			</td>
			<tr>
				
				<td><strong>Phone Number<font color="red">*</font> </strong></td>
				<td><input type="text" name="ph_no" class="form-control readbaledata" data-valid="mand" ></td>
				<td colspan="2"></td>
				</tr>
			
					
			</tr>
		
				<tr>
				<td><strong>Short Description<font color="red">*</font></strong></td>
				<td colspan="3"><input type="text" name="short_description" style="width: 530px;" data-valid="mand" class="form-control readbaledata"></td>
				
				
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
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary update" onclick="ControlHelpDeskEss('Update','DisplayAllTicket','EditDetailsForAllTicket','SubmitFormForEditAllTickets','All_Ticket','','','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary update" onclick="ControlHelpDeskEss('Cancel','DisplayAllTicket','EditDetailsForAllTicket','SubmitFormForEditAllTickets','All_Ticket','','','')">Cancel</button>
					</td>
				</tr>
			
			</table>
		
	</form>
</div>
