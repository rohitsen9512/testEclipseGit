<!--Edit_from_store.jsp-->
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>

<script type="text/javascript">
//getasseetapi();
$(document).ready(function() {
    $('.select2search').select2();
	
});
async function getjsondataAPI() {
    //
/*    Storing response*/
    const response = await fetch('InvoiceScanFile/I_ChoiceList.json');
    
    /*Storing data in form of JSON*/
    var data = await response.json();
  
    console.log(data);

var ss=document.getElementById("search_state");
    var s=document.getElementById("Dynamic_state");
    var u=document.getElementById("Dynamic_urg");
    var im=document.getElementById("Dynamic_impact");
    var p=document.getElementById("Dynamic_priority");
    var c=document.getElementById("requestPrAssetGroup");
    
    //var d=document.getElementById("requestPrAssetGroupSearch");
    for (var i =0;i<data.length;i++){
    	if(data[i].nm_field=="state" ||data[i].nm_field=="State" ){
    		
    		var option = document.createElement("option");
        	option.text = data[i].nm_attribute;
        	option.value = data[i].val_attr;
        	s.appendChild(option);
			//ss.appendChild(option);
    	}

    	
        	if(data[i].nm_field=="state" || data[i].nm_field=="State"){
        		
        		var option = document.createElement("option");
            	option.text = data[i].nm_attribute;
            	option.value = data[i].val_attr;
            	//s.appendChild(option);
    			ss.appendChild(option);
        	}
	

if(data[i].nm_field=="priority" || data[i].nm_field=="Priority"){
	
	var option = document.createElement("option");
	option.text = data[i].nm_attribute;
	option.value = data[i].val_attr;
	p.appendChild(option);
}


    	
    	 
    }
   
   
} 


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
			   
			    $('input[name="file_name1"]').val(filename.replace("&","AND"));
			    	
			    		/* $('input[name="file_name1"]').val(filename); */
			    		
			    }
			},'json'); 
    }	

}); 


$(function() {
	
	$(".Activity").click(function(){
        $(".activityMessage").toggle(1000);
    });
	
	
	
	
	/* DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroup','CapG',function (status){
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
							/* DisplayDropDownData("M_Emp_User","empForAssignToHD",function (status){
								if(status)
								{ */
									/* DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroupSearch','CapG',function (status){
										if(status)
										{ */
											//ControlHelpDesk('List','DisplayAllTicket','EditDetailsForAllTicket','SubmitFormForEditAllTickets','New_Ticket','displayDataForEditAllTicket','','');
										getResult();	
										
										listToBeParent();
										
										 
										 }}); 
								
								
								/* }}); */
						}});
					
				/* }}); */
			 
}});

$(function(){
				var currentDate = new Date();
					
					$('.datepicker').each(function () {
				        if ($(this).hasClass('hasDatepicker')) {
				            $(this).removeClass('hasDatepicker');
				        } 
				         $(this).datepicker({
				        	 yearRange: '1985:2025',
					      changeMonth: true,
					      changeYear: true,
					      dateFormat: "yy/mm/dd",
					      autoSize: true,
					      altFormat: "yy/mm/dd",
					    });
				    });
					
					/* $('input[name="enddate"]').datepicker("setDate", currentDate);
					currentDate.setMonth(currentDate.getMonth() - 1);
					$('input[name="startdate"]').datepicker("setDate", currentDate);
					 */
				});

	$('#EditDetailsForAllIncident').hide();
	
	
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
	jQuery('.tabs-2 .tab-links-2 a').on('click', function(e) {
		var currentAttrValue = jQuery(this).attr('href');

		// Show/Hide Tabs
		jQuery('.tabs-2 ' + currentAttrValue).show().siblings().hide();

		// Change/remove current tab to active
		jQuery(this).parent('li').addClass('active').siblings().removeClass('active');

		e.preventDefault();
	});
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
						<button class="btn btn-primary" onclick="back('all')"> <i class="fas fa-chevron-left"></i></button>
						<!-- Incident -->
						<button type="button" name="create btn" 
							class="btn btn-primary "
							onclick="GotoCreateIncident()">Create
							Incident</button>
					</h1>
					
				</div>
				<div class="col-sm-6">
					<div class="float-sm-right" id="btnshowhide" style="display:none">
					<button type="button"  class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"><i class="fas fa-paperclip"></i></button>
					
					<button name="update" type="button" style="margin-left:2px;"   class="btn btn-primary update" onclick="ControlIncident('Update','DisplayAllIncident','EditDetailsForAllIncident','SubmitFormForEditAllIncidents','All_Incident','','','')">Update</button>
						<!-- <button name="cancel" type="button"  class="btn btn-primary update" onclick="ControlIncident('Cancel','DisplayAllIncident','EditDetailsForAllIncident','SubmitFormForEditAllIncidents','All_Incident','','','')">Cancel</button>
					 
					 <button name="delete" type="button"  class="btn btn-primary delete" onclick="DeleteIncidents('New_Incident','DisplayAllIncident','EditDetailsForAllIncident',$('#id').val())">Delete</button>
					-->
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

	
 <div class="card" id="DisplayAllIncident">

<table>
<tr> <td>
<br>
State  <select name="search_state" class="form-control" style="width:140px; display: inline;" data-valid="mand"  id="search_state"  onChange="getResult()" > 
				<option value="All"selected="selected">All</option>
				</select>
<!-- 	Category: <select name="search_nm_category"   style="width:140px; display: inline;" id="requestPrAssetGroupSearch" class="form-control readbaledata" data-valid="mand"   onChange="getResult()">
						<option value=""selected="selected">--Select--</option>
						<option value="Hardware">IT(Hardware)</option>
						<option value="Software">TechSupport(Software)</option>
				</select> -->		
				
	<!-- Sub Category: <select name="search_id_sgrp"  style="width:140px;display: inline;" id="requestPrAssetSubGroupSearch" class="form-control readbaledata" data-valid="mand" onChange="getResult()">
						<option value="">--Select--</option>
						
				</select>	 -->
		
	

			Start Date<input type="text" name="startdate" class="form-control datepicker" style="width:140px; display: inline;">
			End Date
				<input type="text" name="enddate" class="form-control datepicker"style="width:140px; display: inline;" >
			
				
						<button type="button"   class="btn btn-primary" style="display: inline;" onclick="getResult()">Preview</button>
				
					
					<span style="float: right;font-size: 18px;"> Incident Requested: <b id="tcount">NA</b></span>		
					</td>
					
				</tr>
				
			
			<tr>
				
			</tr>
			<tr>
				
			</tr>
		
			<tr>
				
				</td>
		  </tr>
	</table>

</table>
<hr>
<br>


				<table id="displayDataForEditAllIncident"
					class="table table-bordered table-hover displayDataForEditAllIncident">			

	
		<tr class="info">
			
		</tr>
	</table> 
</div>
<div class="spanner">
  <div class="loader"></div>
  <p>Ticket is being Updating, please be patient.</p>
</div>
<div id="EditDetailsForAllIncident">
<div class="card">

	<form name="SubmitFormForEditAllIncidents" id="SubmitFormForEditAllIncidents">
		<table class="table table-bordered" >
			<tr>
				<td colspan="4" class="tableHeader"><center><H3 class="tableHeaderContent">Request Details</H3></center></td>
			</tr>
			<tr>
			<td><strong>Incident Number <font color="red">* </font></strong></td>
			<td>
			
			<input type="text" name="Incident_no" value="" readonly="readonly" class="form-control readbaledata" data-valid="mand" ></td>
			<td><strong>Requested date <font color="red">* </font></strong></td>
			<td>
			<input type="text" name="dt_req" value="" readonly="readonly" disabled class="form-control datepicker readbaledata" data-valid="mand" >
			</td>
			</tr>
			<tr>
			<!-- <td ><b>Impact<font color="red"> * </font>:</b></td>
				<td >
				<select name="impact" class="form-control readbaledata reopenState" id ="Dynamic_impact"  data-valid="mand" onchange="SetPriority()"> 
						
				</select> 
				</td> -->
			<td ><b>Caller<font color="red"> * </font></b></td>
				<td >
					<select name="req_by" id="empForNewTicketHD" class="form-control readbaledata" disabled data-valid="mand" > 
					
				</select> 
				</td>
				 <td>
			<b>Phone Number<font color="red"> * </font> </b></td>
				<td><input type="number" name="ph_no" pattern=".{10}" disabled class="form-control readbaledata" data-valid="mand" maxlength="10"  ></td><tr>
			
				
			</tr>
			
			<!-- <td><strong>Urgency <font color="red">* </font></strong></td>
			<td>
			<select name="urgency" class="form-control readbaledata reopenState" data-valid="mand" id="Dynamic_urg" onchange="SetPriority()"> 
					 	
				</select> 
			</td>
		 -->	
		
				
			<tr>
			<td ><b>Priority<font color="red"> * </font></b></td>
				<td >
				<select name="priority" class="form-control  readbaledata" id ="Dynamic_priority" data-valid="mand" readonly> 
						
				</select> 
				</td>
				<td><strong>Assignment Group</strong></td>
			<td>
			<select name="assign_grp" id="depDataForNewTicket" class="form-control readbaledata reopenState" onChange="SubDropDownDataDisplay(this,'empForAssignToHD','M_GroupAssign')"> 
						 <option value="">Select</option>
						 
				</select> 
			</td>
			
			
			</tr>
			
			<!-- <td ><b>Category<font color="red"> * </font>:</b></td>
				<td >
					<select name="nm_category"   style="width:140" id="requestPrAssetGroup" class="form-control  readbaledata reopenState" data-valid="mand" disabled style="width:140" onChange="">
						<option value="">--Select--</option>
						
				</select>
				</td> -->
				
				
			
			
				
			
			  
				  <!-- <td ><b>Sub Category<font color="red"> * </font>:</b></td>
				<td >
					<select name="id_sgrp"  style="width:140" id="requestPrAssetSubGroup" class="form-control  readbaledata reopenState" data-valid="mand" disabled onChange="">
						<option value="">--Select--</option>
						
				</select>
				</td>  -->
				
			
			
		<tr>
				
				
				<td><b>State<font color="red"> * </font></b></td>
				<td >
				<select name="state" id="Dynamic_state" class="form-control readbaledata reopenState" data-valid="mand" onchange="onhold(this) " > 
						 
				</select> 
				</td>
				<td><strong>Assign To</strong></td>
			<td>
			<select name="assign_to" id="empForAssignToHD" class="form-control readbaledata reopenState" onchange="StateViceVersa()"  > 
						 <option value="">Select</option>
						 
				</select> 
			</td>
				<!-- 
				<td><strong>Configuration Item</strong></td>
				<td>
				<select name="id_prod"   class="form-control  readbaledata reopenState"  disabled id="proNameForReqPR" >
						<option value="">--Select--</option>
						
				</select>
				</td> -->
				</tr>
				<tr>
		<td>	<b>Document </b></td>
				
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
				
				</td>
			 <td ><b>Role<font color="red"> * </font></b></td>
				<td >
					<!-- <input type="text" name="id_design" id="empdesignForNewTicketHD" class="form-control readbaledata" data-valid="mand" readonly> 
						
				 -->
				 <select name="id_design" id="id_design" class="form-control" readonly data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
			 <tr id="des_tr" style="display:none;">
			  <td><strong>On Hold Description <font color="red"> * </font></strong></td>
				<td><input type="text" name="hold_desc" id="hold_desc"class="form-control readbaledata" ></td>
				</tr>
			
				
				
			
				 
				
				<!-- <tr>
				</tr> -->
				<tr>
				<td><strong>Subject<font color="red"> * </font></strong></td>
				<td colspan="3" ><input type="text" name="short_description" data-valid="mand" disabled class="form-control readbaledata reopenState"></td>
				</tr>
			<tr>
				<td><strong>Description</strong></td>
				<td colspan="3" ><textarea style="margin-right: 0px;width:100%;height: 300px;" disabled name="description" id="description"  class="form-control readbaledata"></textarea></td>
				</tr>
			</table>
				<hr>
		
<div class="card card-primary card-outline card-outline-tabs">
<div class="card-header p-0 border-bottom-0">
	 <ul class="nav nav-tabs" id="custom-tabs-three-tab" role="tablist">
		<li class="nav-item"><a class="nav-link active" id="custom-tabs-four-home-tab" data-toggle="pill" href="#tab1" role="tab" >Notes</a></li>
		<li class="nav-item"><a class="nav-link " id="custom-tabs-four-home1-tab" data-toggle="pill" href="#tab2" role="tab">Related Records</a></li>
		<li class="nav-item"><a class="nav-link " id="custom-tabs-four-home2-tab" data-toggle="pill" href="#tab3" role="tab">Resolution Information</a></li>
	</ul> 
	</div>
<div class="card-body">
	<div class="tab-content" id="custom-tabs-four-tabContent">
		<div id="tab1" class="tab active">
		<table style="border-spacing: 1em;border-collapse: unset">
		<tr>
		<td><b>Work Notes:</b></td>
				  <td><textarea style="margin-left: 20px;margin-right: 0px;width: 600px;height: 65px;"  name ="work_note" id="work_note"class="form-control readbaledata reopenState"></textarea> </td>
			</tr>
			<tr><td colspan="4">
			
			 <button name="post" type="button" style="
    float: right;" class="btn btn-primary post" onclick="post_worknote($('#id').val())">Post</button>
			</td></tr>			
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
		<td><strong>Parent Incident</strong></td>
				<td>
				<select name="parent"   class="form-control readbaledata select2search"  id="parent" style="margin-left: 20px;margin-right: 0px;width: 300px;" >
						<option value="">--Select--</option>
						
						
				</select>
				</td> 
		<!-- <td><strong>Problem</strong></td>
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
				</td>  -->
				</tr>
					
		</table>
			
				
			</div>

		<div id="tab3" class="tab">
		<table style="border-spacing: 1em;border-collapse: unset">
			<tr>
			
				
			 <td ><b>Resolution Code</b></td>
				<td >
				<select name="res_code" class="form-control readbaledata" id="res_code" style="margin-left: 20px;" > 
						 <option value selected="SELECTED">None</option>
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
		</table>
				
		</div>

		
	</div>
</div>

			<hr>		
					
						<input type="hidden" name="id" id="id" value="0">
						<input type="hidden" name="action" value="Update">
						<input type="hidden" name="file_name1" value="" class="form-control">
						<button name="update" type="button" style="margin-left:10px;width:100px;"   class="btn btn-primary update" onclick="ControlIncident('Update','DisplayAllIncident','EditDetailsForAllIncident','SubmitFormForEditAllIncidents','All_Incident','','','')">Update</button>
						<!-- <button name="cancel" type="button"  class="btn btn-primary update" onclick="ControlIncident('Cancel','DisplayAllIncident','EditDetailsForAllIncident','SubmitFormForEditAllIncidents','All_Incident','','','')">Cancel</button>
					 <button name="delete" type="button"  class="btn btn-primary delete" onclick="DeleteIncidents('New_Incident','DisplayAllIncident','EditDetailsForAllIncident',$('#id').val())">Delete</button>
					-->
					<br>
				</div>
	<div class="card card-primary card-outline card-outline-tabs">
<div class="card-header p-0 border-bottom-0">
	 <ul class="nav nav-tabs" id="custom-tabs-two-tab" role="tablist">
		<li class="nav-item"><a class="nav-link active" id="custom-tabs-all-home-tab" data-toggle="pill" href="#tab5" role="tab" >Task SLA</a></li>
		<li class="nav-item"><a class="nav-link " id="custom-tabs-all-home2-tab" data-toggle="pill" href="#tab6" role="tab">Child Incidents</a></li>
	</ul> 
	</div>
	<div class="card-body">
	<div class="tab-content"id="custom-tabs-two-tabContent">
	
		<div id="tab5" class="tab active">
		<table id="displayDataForSLA"
					class="table table-bordered table-hover displayDataForSLA">	
					</table>
			</div>

		
		<div id="tab6" class="tab">
		
		
				
				 
				<input type="hidden" name="id_parent" id="id_parent"  class="form-control readbaledata" >
				
				
	<button type="button" class="btn btn-primary" name="create btn" style="float: right; margin-bottom:10px;"
						onclick="GoToCreateincident($('#id_parent').val())">Create Incident</button>
				
		<table	id="displayDataForChildIncident"
					class="table table-bordered table-hover displayDataForChildIncident">			

	
		
	</table> 
				
			
				
		</div>

		
	</div>
</div>
<br>
	</div>	
	

</div>

<script>


getjsondataAPI();
SetPriorityForIncident();
ClosedState();
</script>