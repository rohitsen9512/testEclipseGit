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
									DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroupSearch','CapG',function (status){
										if(status)
										{
											//ControlHelpDesk('List','DisplayAllTicket','EditDetailsForAllTicket','SubmitFormForEditAllTickets','New_Ticket','displayDataForEditAllTicket','','');
										getResult();	
										 ticketDashboard()
										}});
									
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

</script>

<style>
.dot {
  height: 80px;
    width: 165px;
    background-color: cadetblue;
    border-radius: 50%;
    display: inline-block;
}
.dot b{
    margin-left: 46px;
    margin-top: 16px;
    display: block;
    color: white;
    font-size: large;
}
.dot span{
	margin-left: 65px;
    margin-top: 8px;
    display: block;
    font-size: large;
    font-weight: bold;
    color: white;
}


</style>

<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!-- Help -->
						
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">HelpDesk</a></li>
						<li class="breadcrumb-item">All Ticket</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
<!-- <div class="commonDiv" id="SearchFromForEditFromstore">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForEdit('A_Edit_From_Store')">
</div> -->
<div class="card">
<div id="ticketDashboard">
<table>
<tr><center> <h3>Raise Tickets Status</h3></center></tr><hr>
<tr>
	<td>
	<span class="dot" style="background-color: orange;"><b style="margin-left: 56px;">New</b><span id="pending">0</span></span>
	</td>
	<td>
	<span class="dot"><b style="margin-left: 31px;">In-Progress</b><span id="inprogress">0</span></span>
	</td>
	<td>
	<span class="dot" style="background-color: red;"><b style="margin-left: 31px;">Awaiting Info</b><span id="awaitingInfo">0</span></span>
	</td>
	<td>
	<span class="dot" style="background-color: green;"><b>Closed</b><span id="closed">0</span></span>
	</td>
	<td>
	<span class="dot" style="background-color: currentColor;"><b style="margin-left: 31px;">Incomplete</b><span id="incomplete">0</span></span>
	</td>
	<td>
	<span class="dot" style="background-color: black;"><b style="margin-left: 35px;">Cancelled</b><span id="cancelled">0</span></span>
	</td>
</tr>

</table>
<hr>
<br>
<!-- <span class="dot">In-Progress</span>
<span class="dot">Closed</span>
<span class="dot">Cancelled</span>
<span class="dot">Awaiting Info</span>
<span class="dot">Incomplete</span> -->
	
</div>

<div>
State <select name="search_state" class="form-control readbaledata" style="width:140px;display: inline;"data-valid="mand" onChange="getResult()"> 
						 <option value="All">All</option>
						 <option value="New">Pending</option>
						 <option value="Active">In-Progress</option>
						 <option value="Awaiting_User_Info">Awaiting User Info</option>
						 <option value="Closed">Closed</option>
						  <option value="Closed_as_in_complete">Closed as in complete</option>
						   <option value="Cancelled">Cancelled</option>
				</select> 
				
	Category <select name="search_id_grp"   style="width:140px;display: inline;" id="requestPrAssetGroupSearch" class="form-control readbaledata" data-valid="mand"   onChange="SubDropDownDataDisplay(this,'requestPrAssetSubGroupSearch','M_Subasset_Div');getResult()">
						<option value="">Select</option>
						
				</select>		
				
	Sub Category <select name="search_id_sgrp"  style="width:140px;display: inline;" id="requestPrAssetSubGroupSearch" class="form-control readbaledata" data-valid="mand" onChange="getResult()">
						<option value="">Select</option>
						
				</select>	
	<span style="float: right;font-size: 18px;"> Ticket Requested: <b id="tcount">NA</b></span>			
</div>	



<div id="DisplayAllTicket">
<div class="card-body">
				<table id="displayDataForEditAllTicket"
					class="table table-bordered table-hover displayDataForEditAllTicket">			

	
		<tr class="info">
			
		</tr>
	</table>
</div>
</div>
</div>
<div class="card">
<div id="EditDetailsForAllTicket">
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
			<td ><b>Impact<font color="red">*</font></b></td>
				<td >
				<select name="impact" class="form-control readbaledata" data-valid="mand" onchange="SetPriority()"> 
						 <option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
				</td>
			<td ><b>Requested by<font color="red">*</font></b></td>
				<td >
					<select name="req_by" id="empForNewTicketHD" class="form-control readbaledata" disabled data-valid="mand" > 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
				</td>
			</tr>
			<tr>
			<td><strong>Urgency <font color="red">*</font></strong></td>
			<td>
			<select name="urgency" class="form-control readbaledata" data-valid="mand" onchange="SetPriority()"> 
						<option value="3">3-Low</option>
						 <option value="2">2- Medium</option>
						 <option value="1">1-High</option>
				</select> 
			</td>	
			<td><strong>Assignment Group <font color="red">*</font></strong></td>
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
			<td ><b>Department<font color="red"> </font></b></td>
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
						 <option value="Awaiting_User_Info">Awaiting User Info</option>
						 <option value="Closed">Closed</option>
						  <option value="Closed_as_in_complete">Closed as in complete</option>
						   <option value="Cancelled">Cancelled</option>
				</select> 
				</td>
				<td ><b>Phone Number</b></td>
				<td><input type="text" name="ph_no" class="form-control readbaledata"  ></td>
			<!-- <td ><b>Category<font color="red">*</font></b></td>
				<td >
					<select name="id_grp"   style="width:140" id="requestPrAssetGroup" class="form-control readbaledata" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'requestPrAssetSubGroup','M_Subasset_Div')">
						<option value="">Select</option>
						
				</select>
				</td> -->
				</tr>
			 <tr>
			  
				 <!--  <td ><b>Sub Category<font color="red"></font></b></td>
				<td >
					<select name="id_sgrp"  style="width:140" id="requestPrAssetSubGroup" class="form-control readbaledata" data-valid="mand" onChange="DisplaySubDropDownData11(this,'proNameForReqPR','A_Add_To_Store')">
						<option value="">Select</option>
						
				</select>
				</td>  -->
				
				
			   <!-- <td><strong>Product Name<font color="red">*</font></strong></td>
				<td>
				<select name="id_prod"   class="form-control readbaledata" data-valid="mand" id="proNameForReqPR" onChange="onChangeProductName(this)">
						<option value="">Select</option>
						
				</select>
				</td> -->
				</tr> 
				
				<!-- <tr>
				
				<td><strong>Manufacturer <font color="red">*</font></strong></td>
				<td><input type="text" name="no_mfr" class="form-control readbaledata" data-valid="mand" readonly></td>
				<td><strong>Model Number<font color="red"> * </font></strong></td>
				<td><input type="text" name="serial_no"  class="form-control readbaledata" data-valid="mand" readonly></td>
				
				</tr> -->
				<tr>
				<td><strong>Short Description<font color="red">*</font></strong></td>
				<td ><input type="text" name="short_description" style="width: 330px;" data-valid="mand" class="form-control readbaledata"></td>
				
				
			<td><b>Document</b></td>
			<td id="FileName"></td>
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
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary update" onclick="ControlHelpDesk('Update','DisplayAllTicket','EditDetailsForAllTicket','SubmitFormForEditAllTickets','All_Ticket','','','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary update" onclick="ControlHelpDesk('Cancel','DisplayAllTicket','EditDetailsForAllTicket','SubmitFormForEditAllTickets','All_Ticket','','','')">Cancel</button>
					</td>
				</tr>
			
			</table>
		
	</form>
</div>
</div>