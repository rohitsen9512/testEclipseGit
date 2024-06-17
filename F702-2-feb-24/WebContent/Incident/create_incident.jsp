


<link href="Common.css" rel="stylesheet" type="text/css">
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script src="Autocomp/jquery.easy-autocomplete.min.js"></script>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->
<script type="text/javascript">
//$("#Dynamic_urg").easyAutocomplete(INCchoicelist);

 



//getasseetapi();
getapi();

/* async function GetsubCategory(categoryname,id){
	
	 document.getElementById("requestPrAssetSubGroup").innerHTML =
         null;
const response = await fetch('InvoiceScanFile/I_ChoiceList.json');
    
   
    var data = await response.json();
   
    console.log(data[0],$(id),categoryname);
   
    var sbgrp=document.getElementById("requestPrAssetSubGroup");
    var value = categoryname.options[categoryname.selectedIndex].value;
    
    console.log(value);
   
    console.log(data[0],sbgrp);
    for (var i =0;i<data.length;i++){
    	
    	if(data[i].depending_attr==value){
    		
    		var option = document.createElement("option");
        	option.text = data[i].nm_attribute;
        	option.value = data[i].val_attr;
        	sbgrp.appendChild(option);
    	}
	
	
}
} */

$(document).ready(function() {
     $('.select2search').select2();
	
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
$('input[name="dt_req"]').datepicker("setDate", currentDate);
	 

	
						 /* DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroup','CapG',function (status){
							if(status)
							{ */ 
								GetDropDownDataForAssetOwner('typAssetForPrRequest',function (status){
									if(status)
									{
										DisplayDropDownData("M_Emp_User","empForNewTicketHD",function (status){
											if(status)
											{
												DropDownDataDisplay('M_Group','depDataForNewTicket',function (status){
													if(status)
													{
														DisplayDropDownData('M_Designation','id_design',function (status){
															if(status)
															{
														ControlIncident('Onload','','','','New_Ticket','','','');
															}});
															}});
													
												
											}});
										
									}});
								 
			 /* }});  */
	
		

});
$(function() {
	
	$(".Activity").click(function(){
        $(".activityMessage").toggle(1000);
    });
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


<%HttpSession session2 = request.getSession(); %>

		<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
				<h1>	<button class="btn btn-primary" onclick="back('create')"> <i class="fas fa-chevron-left"></i></button>
					
						<!-- Incident -->
						
					</h1>
				</div>
				<div class="col-sm-6">
					
<!-- 					<button type="button" style="margin-left:500px" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"><i class="fas fa-paperclip"></i></button>
 -->					<!-- <button name="save" type="button"   style="float:right;" class="btn btn-primary req" onclick="ControlIncident('Save','','','submitIncident','New_Incident','',$('#id_parent').val(),'' ,'')">Submit</button>
				 --> <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header backcolor">
        <h3 class="modal-title">Attachments</h3>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
         <div class="modal-body">
         
				<!--<td><input id="file4ID1" type="file" name="file1" style="margin-left: 5px; width: 300px;" class="form-control" value="" >
					<td><input type="file" id="file4ID1" name="file1" multiple>-->
				<!-- <input type="file" id="file4ID1" name="file1" multiple /> -->
				
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
		<!-- /.container-fluid -->
	</section>
	<div class="spanner">
  <div class="loader"></div>
  <p>Ticket is being generating, please be patient.</p>
</div>
	<form name="submitIncident" id="submitIncident">
	<div id="createPR">
	<div class="card">
	<table id="invoiceDetails" class="table table-bordered">
			<tr>
			<h3 class="new"><center>Incident Details</center></h3>
					</tr>
			<tr>
			<td><strong>Incident Number<font color="red">*</font></strong></td>
			<td>
			
			<input type="text" name="Incident_no" value="" disabled class="form-control readbaledata"  data-valid="mand" ></td>
			 <td ><b>State<font color="red">*</font></b></td>
				<td >
				<select name="state" class="form-control "  data-valid="mand" readonly id="Dynamic_state"> 
		
				</select>
				</td>
			
			</tr>
			 <tr>
			 <td ><b>Caller<font color="red">*</font></b></td>
				<td >
					<input type="text" name="req_by" id="empForNewTicketHD" class="form-control readbaledata" data-valid="mand" readonly> 
						
				
				</td>
				<td><strong>Requested date<font color="red">*</font></strong></td>
			<td>
			<input type="text" name="dt_req" value="" readonly="readonly" class="form-control datepicker readbaledata" onkeydown="return false"  disabled data-valid="mand" >
			</td>
				
			
			</tr>
			<!-- <tr>
			<td><strong>Urgency <font color="red">* </font></strong></td>
			<td>
			<select name="urgency" class="form-control  readbaledata" id ="Dynamic_urg" data-valid="mand" onchange="SetPriorityForIncident()"> 
						<option value="">-- Select --</option>
					
						 </select>
				
			</td>
			<td ><b>Impact<font color="red"> * </font>:</b></td>
				<td >
				<select name="impact" class="form-control  readbaledata" id ="Dynamic_impact" data-valid="mand" onchange="SetPriorityForIncident()"> 
						<option value="">-- Select --</option>
				</select> 
				</td>
				
			
				</tr> -->
			
		 <tr>
		 <td ><b>Priority<font color="red">*</font></b></td>
				
				<td >
				<select name="priority" class="form-control readbaledata" id ="Dynamic_priority" data-valid="mand" > 
						
				</select> 
				</td>
			<td>
			<b>Phone Number<font color="red">*</font> </b></td>
				<td><input type="text" name="ph_no" class="form-control readbaledata" data-valid="mand" maxlength="10"  ></td>
		
				<!-- <td ><b>Category<font color="red"> * </font>:</b></td>
				<td >
				
				<select name="nm_category"   style="width:140" id="requestPrAssetGroup" class="form-control select2search readbaledata" data-valid="mand"  style="width:140" onChange="">
						<option value="">--Select--</option>
						
				</select>
				</td> -->
				
				
			</tr>
			
			<!-- <td><strong>Configuration Item</strong></td>
				<td>
				<select name="id_prod"   class="form-control select2search readbaledata"  id="proNameForReqPR" >
						<option value="">--Select--</option>
						
				</select>
				</td> --></tr>
				 <tr>
			<td><strong>Assignment Group</strong></td>
			<td>
			<select name="assign_grp" id="depDataForNewTicket" class="form-control select2search readbaledata"  onChange="SubDropDownDataDisplay(this,'empForAssignToHD','M_GroupAssign')"> 
						 <option value="">Select</option>
						 
				</select> 
			</td>
		<!--	<td><strong>Assign To </strong></td>
		 	<td>
			<select name="assign_to" id="empForAssignToHD" class="form-control select2search readbaledata" onchange="StateViceVersa()" > 
						 <option value="">Select</option>
						 
				</select> 
			</td> -->
			</tr> 
		<tr><td >	<b>Document</b></td>
		<td><input type="file" id="file4ID1" name="file1" multiple></td>
			 
			 <td ><b>Role<font color="red">*</font></b></td>
				<td >
					<!-- <input type="text" name="id_design" id="empdesignForNewTicketHD" class="form-control readbaledata" data-valid="mand" readonly> 
						
				 -->
				 <select name="id_design" id="id_design" class="form-control" readonly data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			 
			 <!-- <td ><b>Sub Category<font color="red"> * </font>:</b></td>
				<td >
					
				<select name="nm_subcategory"  style="width:140" id="requestPrAssetSubGroup" class="form-control select2search readbaledata" data-valid="mand" >
						<option value="">--Select--</option>
						
				</select>
				</td> 
		 -->
		
			 
				</tr>
			
<!--<tr><td>
			<b>Document :</b></td>
						 <td><input id="file4ID1" type="file" name="file1" style="margin-left: 5px; width: 300px;" class="form-control" value="" >
					<td><input type="file" id="file4ID1" name="file1" multiple>
				<td><input type="file" id="file4ID1" name="file1" multiple />
				</td>
				</tr> -->
				<tr>
				<td><strong>Subject<font color="red">*</font></strong></td>
				<td colspan="3"><input type="text" name="short_description" style="width: 100%;" data-valid="mand" class="form-control readbaledata"></td>
				
			
				</tr>
			<tr>
				<td><strong>Description</strong></td>
				<td colspan="3" ><textarea style="margin-right: 0px;width:100%;height: 300px;" name="description"  class="form-control readbaledata"></textarea></td>
				</tr>
		</table>
		<hr>
		
<!-- <div class="tabs">
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
		<td><strong>Problem</strong></td>
				<td>
				<select name="id_problem"   class="form-control readbaledata"  id="proNameForReqPR" style="margin-left: 20px;margin-right: 0px;width: 300px;" >
						<option value="">--Select--</option>
						<option value="1">PR001</option>
						 <option value="2">PR002</option>
						 <option value="3">PR003</option>
						  <option value="4">PR004</option>
						   <option value="5">PR005</option>
						
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
				<select name="res_code" class="form-control readbaledata" style="margin-left: 20px;" > 
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
				  <td colspan="3"><textarea style="margin-left: 20px;margin-right: 0px;width: 600px;height: 65px;"  name ="resolution_note"  id="resolution_note" class="form-control readbaledata"></textarea> </td>
			</tr>
				<tr>
		<td><b>Cancel Notes</b></td>
				  <td colspan="3"><textarea style="margin-left: 20px;margin-right: 0px;width: 600px;height: 65px;"  name ="cancel_note" id="cancel_note" class="form-control readbaledata"></textarea> </td>
			</tr>
		</table>
				
		</div>

		
	</div>
</div> -->
<br>
<br>
<table>
			<tr>
				<td colspan="4">
				
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id_parent" id="id_parent"value="0">
					<input type="hidden" name="id_problem" id="id_problem"value="0">
					<input type="hidden" name="file_name1" value="" class="form-control">
					
				</td>
			</tr>
			
			</table>
	</div>
	
	</div>
	
</form>	
<div style="text-align:center;">
<button name="save" type="button"    class="btn btn-primary req" onclick="ControlIncident('Save','','','submitIncident','New_Incident','',$('#id_parent').val(),$('#id_problem').val() ,'','')">Submit</button>
				
<button name ="back" class="btn btn-primary" onclick="back('create')">Back</button>
</div>