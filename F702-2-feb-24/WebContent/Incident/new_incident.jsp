<!--Edit_from_store.jsp-->

<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script type="text/javascript">

$(document).ready(function() {
    $('.select2search').select2();
	
});
//getasseetapi();
getapi();
$(function() {
	
	$(".Activity").click(function(){
        $(".activityMessage").toggle(1000);
    });
	
	
	jQuery("input#file4ID1").change(function () {
		var filename="";
		var files = $("#file4ID1").get(0).files;
	    var fileData = new FormData();
	
	    for (var i = 0; i < files.length; i++) {
	    	
	        
	         var formData = new FormData();	 
	 		formData.append('file', $('#file4ID1').get(0).files[i]);
	        
			
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
				   
				    $('input[name="file_name1"]').val(filename);
				    	
				    		/* $('input[name="file_name1"]').val(filename); */
				    		
				    }
				},'json'); 
	    }	

	}); 
/* 	DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroup','CapG',function (status){
		if(status)
		{ */
			DropDownDataDisplay('M_Group','depDataForNewTicket',function (status){
				if(status)
				{
					DisplayDropDownData("M_Emp_User","empForNewTicketHD",function (status){
						if(status)
						{
							DisplayDropDownData('M_Designation','id_design',function (status){
								if(status)
								{
		ControlIncident('List','DisplayNewIncident','EditDetailsForNewIncident','SubmitFormForEditNewIncident','New_Incident','displayDataForEditNewIncident','','');
								/* }}); */
						
						if(status)
									{
									listToBeParent();
									}
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

	$('#EditDetailsForNewIncident').hide();
	
	
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
jQuery(document).ready(function() {
	jQuery('.tabs .tab-links a').on('click', function(e) {
		var currentAttrValue = jQuery(this).attr('href');

		// Show/Hide Tabs
		jQuery('.tabs ' + currentAttrValue).show().siblings().hide();

		// Change/remove current tab to active
		jQuery(this).parent('li').addClass('active').siblings().removeClass('active');

		e.preventDefault();
	});
});

</script>
<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6"><h1>
					<button class="btn btn-primary" onclick="back('new')"> <i class="fas fa-chevron-left"></i></button>
					
						<!-- Incident -->
						<button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="GotoCreateIncident()">Create
							Incident</button>
					</h1>
					
					
				</div>
				<div class="col-sm-6">
					<div class="float-sm-right" id="btnshowhide" style="display:none">
					<button type="button"  class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"><i class="fas fa-paperclip"></i></button>
					
					<button name="update" type="button" style="margin-left:2px;"   class="btn btn-primary update" onclick="ControlIncident('Update','DisplayNewIncident','EditDetailsForNewIncident','SubmitFormForEditNewIncidents','New_Incident','','','')">Update</button>
					<!-- 	<button name="cancel" type="button"  class="btn btn-primary update" onclick="ControlIncident('Cancel','DisplayNewIncident','EditDetailsForNewIncident','SubmitFormForEditNewIncidents','In_Progress_Incident','','','')">Cancel</button>
					<button name="delete" type="button"  class="btn btn-primary delete" onclick="ControlIncident('Delete','DisplayNewIncident','EditDetailsForNewIncident','SubmitFormForEditNewIncidents','In_Progress_Incident','','','')">Delete</button> -->
					<!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h3 class="modal-title">Attachments</h3>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
         
				<!-- <td><input id="file4ID1" type="file" name="file1" style="margin-left: 5px; width: 300px;" class="form-control" value="" > -->
					<!-- <td><input type="file" id="file4ID1" name="file1" multiple> -->
				<input type="file" id="file4ID1" name="file1" multiple />
				
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
				
					
					</div>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>


<div class="card">
<div id="DisplayNewIncident">
<div class="card-body">
				<table id="displayDataForEditNewIncident"
					class="table table-bordered table-hover displayDataForEditNewIncident">

	
		
	</table>
</div>
</div>
</div>
<div class="spanner">
  <div class="loader"></div>
  <p>Ticket is being Updating, please be patient.</p>
</div>
<div id="EditDetailsForNewIncident">
<div class="card">
	<form name="SubmitFormForEditNewIncidents" id="SubmitFormForEditNewIncidents">
		
<div>
			<jsp:include page="EditIncidentDiv.jsp" />	
		</div>
		<!-- <table class="table table-bordered">
			<tr>
				<td colspan="4" class="tableHeader"><center><H3 class="tableHeaderContent">Request Details</H3></center></td>
			</tr>
			<tr>
			<td><strong>Incident Number <font color="red">* </font></strong></td>
			<td>
			
			<input type="text" name="Incident_no" value="" readonly="readonly" class="form-control readbaledata" data-valid="mand" ></td>
			<td><strong>Requested date <font color="red">* </font></strong></td>
			<td>
			<input type="text" name="dt_req" value="" readonly="readonly" class="form-control datepicker readbaledata" data-valid="mand" >
			</td>
			</tr>
			<tr>
			<td ><b>Impact<font color="red"> * </font>:</b></td>
				<td >
				<select name="impact" class="form-control readbaledata" id ="Dynamic_impact"  data-valid="mand" onchange="SetPriority()"> 
						
				</select> 
				</td>
			<td ><b>Requested by<font color="red"> * </font>:</b></td>
				<td >
					<select name="req_by" id="empForNewTicketHD" class="form-control readbaledata" disabled data-valid="mand" > 
						
				</select> 
				</td>
			</tr>
			<tr>
			<td><strong>Urgency <font color="red">* </font></strong></td>
			<td>
			<select name="urgency" class="form-control readbaledata" data-valid="mand"   id="Dynamic_urg" onchange="SetPriority()"> 
						
				</select> 
			</td>	
			<td><strong>Assignment Group <font color="red">* </font></strong></td>
			<td>
			<select name="assign_grp" id="depDataForNewTicket" class="form-control readbaledata" data-valid="mand" disabled onChange="SubDropDownDataDisplay(this,'empForAssignToHD','M_GroupAssign')"> 
						 <option value="">Select</option>
						 
				</select> 
			</td>	
				
			</tr>
			<tr>
			<td ><b>Priority<font color="red"> * </font>:</b></td>
				<td >
				<select name="priority" class="form-control  readbaledata" id ="Dynamic_priority" data-valid="mand" disabled> 
						<option value="">--Select--</option>
				</select> </td>
			<td><strong>Assign To <font color="red">* </font></strong></td>
			<td>
			<select name="assign_to" id="empForAssignToHD" class="form-control readbaledata" data-valid="mand" > 
						 <option value="">Select</option>
						 
				</select> 
			</td>
			</tr>
			<tr>
			<td ><b>Category<font color="red"> * </font>:</b></td>
				<td >
					<select name="id_grp"   style="width:140" id="requestPrAssetGroup" class="form-control select2search readbaledata" data-valid="mand" disabled style="width:140" onChange="SubDropDownDataDisplay(this,'requestPrAssetSubGroup','M_Subasset_Div')">
						<option value="">--Select--</option>
						
				</select>
				</td>
				
				
			
				
			
			  
				  <td ><b>Sub Category<font color="red"> * </font>:</b></td>
				<td >
					<select name="id_sgrp"  style="width:140" id="requestPrAssetSubGroup" class="form-control select2search readbaledata" data-valid="mand" disabled>
						<option value="">--Select--</option>
						
				</select>
				</td> 
				</tr>
			
			
		<tr>
				
				
				 <td ><b>State<font color="red"> * </font>:</b></td>
				<td >
				<select name="state" class="form-control select2search"  data-valid="mand"  id="Dynamic_state"  onchange="onhold(this)" > 
				</select>
				</td>
					 <td><strong>Configuration Item</strong></td>
				<td>
				<select name="id_prod"   class="form-control  readbaledata"   id="proNameForReqPR" >
						<option value="">--Select--</option>
						
				</select>
				</td>
				</tr>
			 
			  <tr id="des_tr"style="display:none;">
			  
			  <td colspan="2"><strong>On Hold Description <font color="red"> * </font></strong></td>
				<td colspan="2"><input type="text" name="hold_desc" id="hold_desc" class="form-control readbaledata" ></td>
				</tr>
			<tr><td>
			<b>Document :</b></td>
				<td><input id="file4ID1" type="file" name="file1" style="margin-left: 5px; width: 300px;" class="form-control" value="" >
					<td><input type="file" id="file4ID1" name="file1" multiple>
				<td>
				<div id="FinleName0"></div>
						<div id="FinleName1"></div>
						<div id="FinleName2"></div>
						<div id="FinleName3"></div>
						<div id="FinleName4"></div>
						<div id="FinleName5"></div>
						<div id="FinleName6"></div>
						<div id="FinleName7"></div>
						<div id="FinleName8"></div>
						<div id="FinleName9"></div>
				<input type="file" id="file4ID1" name="file1" multiple />
				</td>
				</tr>
				
				
			
				
			
				<tr>
				<td><strong>Short Description<font color="red"> * </font></strong></td>
				<td colspan="3"><input type="text" name="short_description" style="width: 100%;" data-valid="mand" class="form-control readbaledata"></td>
				
			
				</tr>
			<tr>
				<td><strong>Description</strong></td>
				<td colspan="3" ><textarea style="margin-right: 0px;width:100%;height: 65px;" name="description"  class="form-control readbaledata"></textarea></td>
				</tr>
				</table>
				<hr>
		
<div class="tabs">
	 <ul class="tab-links">
		<li class="active"><a href="#tab1">Notes</a></li>
		<li><a href="#tab2">Related Records</a></li>
		<li><a href="#tab3">Resolution Information</a></li>
		
	</ul> 

	<div class="tab-content">
	
		<div id="tab1" class="tab active">
		<table style="border-spacing: 1em;border-collapse: unset">
		<tr>
		<td><b>Work Notes:</b></td>
				  <td><textarea style="margin-left: 20px;margin-right: 0px;width: 600px;height: 65px;"  name ="work_note" class="form-control readbaledata"></textarea> </td>
			</tr>
			<tr>
			<td><b style="float: right;"><a href="#" class="Activity">Activity</a></b></td>
				<td colspan="3" class="activityLogs" style="border-left-style: hidden;">
					
				</td>
				</tr>
		</table>
			</div>

		<div id="tab2" class="tab">
		
		<table style="border-spacing: 1em;border-collapse: unset">
		<tr>
		<td><strong>Problem</strong></td>
				<td>
				<select name="id_problem"   class="form-control readbaledata"  id="proNameForReqPR" style="margin-left: 20px;margin-right: 0px;width: 300px;" >
						<option value="">--Select--</option>
						
				</select>
				</td> 
				<td><strong>Change Request</strong></td>
				<td>
				<select name="id_CR"   class="form-control readbaledata"  id="proNameForReqPR" style="margin-left: 20px;margin-right: 0px;width: 300px;">
						<option value="">--Select--</option>
						
				</select>
				</td> 
				</tr>
					
		</table>
			
				
			</div>

		<div id="tab3" class="tab">
		<table style="border-spacing: 1em;border-collapse: unset">
			<tr>
			
				
			 <td ><b>Resolution Code</b></td>
				<td >
				<select name="res_code" class="form-control readbaledata"  id="res_code"style="margin-left: 20px;" > 
						  <option value ="">None</option>
						 <option value="Solved_WorkAround">Solved (Work Around)</option>
						 <option value="Solved_Permanently">Solved (Permanently)</option>
						 <option value="NotSolved_NotReproducible">Not Solved (Not Reproducible)</option>
						  <option value="NotSolved_TooCostly">Not Solved (Too Costly)</option>
						   <option value="Closed_ResolvedbyCaller">Closed/Resolved by Caller</option>
						   
				</select> 
				</td>
				</tr>
				
		<tr>
		<td><b>Resolution Notes </b></td>
				  <td colspan="3"><textarea style="margin-left: 20px;margin-right: 0px;width: 600px;height: 65px;"  name ="resolution_note" id="resolution_note" class="form-control readbaledata"></textarea> </td>
			</tr>
				<tr>
		<td><b>Cancel Notes</b></td>
				  <td colspan="3"><textarea style="margin-left: 20px;margin-right: 0px;width: 600px;height: 65px;"  name ="cancel_note" id="cancel_note" class="form-control readbaledata"></textarea> </td>
			</tr>
		</table> -->
				
		

	
<hr>
<table>
			
			
			
			
			<tr>
					
					<td colspan="4">
						<input type="hidden" name="id" value="">
						<input type="hidden" name="action" value="UpdateAssign">
						<input type="hidden" name="file_name1" value="" class="form-control">
						<button name="update" type="button"  class="btn btn-primary update" onclick="ControlIncident('UpdateAssign','DisplayNewIncident','EditDetailsForNewIncident','SubmitFormForEditNewIncidents','New_Incident','','','')">Update</button>
						<!-- <button name="cancel" type="button"  class="btn btn-primary cancel" onclick="ControlIncident('Cancel','DisplayNewIncident','EditDetailsForNewIncident','SubmitFormForEditNewIncidents','In_Progress_Incident','','','')">Cancel</button>
					
					 <button name="delete" type="button"  class="btn btn-primary delete" onclick="DeleteIncidents('New_Incident','DisplayAllIncident','EditDetailsForAllIncident',$('#id').val())">Delete</button>
					 -->
					 </td>
				</tr>
			
			</table>
	<hr>
<div class="card">
<jsp:include page="RelatedInformationDiv.jsp" />
</div>
</form>
	</div>
</div>

<!-- <!Problem page open incident list!> -->
<div id="IncidentforproblemModule" style="display:none">
<div class="card">
<div id="DisplayNewIncidentforProblem">
<div class="card-body">
				<table id="displayDataForEditNewIncidentforProblem"
					class="table table-bordered table-hover displayDataForEditNewIncidentforProblem">

	
		
	</table>
	</div>
</div>
</div>
</div>

