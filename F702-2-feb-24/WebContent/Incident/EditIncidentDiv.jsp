<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<!-- <script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'> -->
<script type="text/javascript">



</script>
</head>
<body>
	<div class="maineditbody">
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
			<input type="text" name="dt_req" value="" readonly="readonly" class="form-control datepicker readbaledata" data-valid="mand" >
			</td>
			</tr>
			<tr>
			<!-- <td ><b>Impact<font color="red"> * </font>:</b></td>
				<td >
				<select name="impact" class="form-control readbaledata reopenState" id ="Dynamic_impact"  data-valid="mand" onchange="SetPriority()"> 
						
				</select> 
				</td> -->
			<td ><b>Caller<font color="red"> * </font>:</b></td>
				<td >
					<!-- <select name="req_by" id="empForNewTicketHD" class="form-control readbaledata " disabled data-valid="mand" > 
					
				</select> --> 
									<input type="text" name="nm_emp" id="empForNewTicketHD" class="form-control readbaledata" data-valid="mand" readonly> 
				
				</td>
				<td>
			<b>Phone Number<font color="red"> * </font> </b></td>
				<td><input type="number" name="ph_no" pattern=".{10}" disabled class="form-control readbaledata" data-valid="mand" maxlength="10"  ></td><tr>
			
				
					
			</tr>
			
			<!-- <td><strong>Urgency <font color="red">* </font></strong></td>
			<td>
			<select name="urgency" class="form-control readbaledata reopenState" data-valid="mand" id="Dynamic_urg" onchange="SetPriority()"> 
					 	
				</select> 
			</td> -->	
			
			
			
			<tr>
			<td ><b>Priority<font color="red"> * </font></b></td>
				<td >
				<select name="priority" class="form-control  readbaledata " id ="Dynamic_priority" data-valid="mand" readonly> 
						
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
					<select name="id_sgrp"  style="width:140" id="requestPrAssetSubGroup" class="form-control readbaledata reopenState" data-valid="mand" disabled >
						<option value="">--Select--</option>
						
				</select>
				</td>  -->
				 
				
			
			
		<tr>
				
				
				<td><b>State<font color="red"> * </font></b></td>
				<td >
				<select name="state" id="Dynamic_state" class="form-control readbaledata reopenState" data-valid="mand" onchange="onhold(this)" > 
						 
				</select> 
				</td>
		<!-- <td><strong>Configuration Item</strong></td>
				<td>
				<select name="id_prod"   class="form-control  readbaledata reopenState"  disabled id="proNameForReqPR" >
						<option value="">--Select--</option>
						
				</select>
				</td> -->
				<td><strong>Assign To</strong></td>
			<td>
			<select name="assign_to" id="empForAssignToHD" class="form-control readbaledata reopenState" onchange="StateViceVersa()"  > 
						 <option value="">Select</option>
						 
				</select> 
			</td>
				</tr>
			 <tr id="des_tr" style="display:none;">
			  <td><strong>On Hold Description <font color="red"> * </font></strong></td>
				<td><input type="text" name="hold_desc" id="hold_desc"  class="form-control readbaledata" ></td>
				
				
				</tr>
			
				
				<tr><td>
			<b>Document </b></td>
				<!-- <td><input id="file4ID1" type="file" name="file1" style="margin-left: 5px; width: 300px;" class="form-control" value="" > -->
					<!-- <td><input type="file" id="file4ID1" name="file1" multiple> -->
				<td >
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
				 <td ><b>Role<font color="red"> * </font>:</b></td>
				<td >
					<!-- <input type="text" name="id_design" id="empdesignForNewTicketHD" class="form-control readbaledata" data-valid="mand" readonly> 
						
				 -->
				 <select name="id_design" id="id_design" class="form-control" readonly data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
			
				 
				
				
				<tr>
				<td><strong>Subject<font color="red"> * </font></strong></td>
				<td colspan="3" ><input type="text" name="short_description" data-valid="mand" disabled class="form-control readbaledata reopenState"></td>
				</tr>
			<tr>
				<td><strong>Description</strong></td>
				<td colspan="3" ><textarea style="margin-right: 0px;width:100%;height: 300px;" disabled name="description" id="description" class="form-control readbaledata"></textarea></td>
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
		<td><b>Work Notes</b></td>
				  <td><textarea style="margin-left: 20px;margin-right: 0px;width: 600px;height: 65px;"  name ="work_note" id="work_note" class="form-control readbaledata"></textarea> </td>
			</tr>
			<tr><td colspan="4">
			
			 <button name="post" type="button" id="post_btn"style="
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
				<select name="parent"   class="form-control select2search readbaledata"  id="parent" style="margin-left: 20px;margin-right: 0px;width: 300px;" >
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
				<select name="res_code" class="form-control readbaledata NotBeEditable" id="res_code" style="margin-left: 20px;" > 
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
				  <td colspan="3"><textarea style="margin-left: 20px;margin-right: 0px;width: 600px;height: 65px;"  name ="resolution_note" id="resolution_note" class="form-control readbaledata NotBeEditable"></textarea> </td>
			</tr>
				<tr>
		<td><b>Cancel Notes</b></td>
				  <td colspan="3"><textarea style="margin-left: 20px;margin-right: 0px;width: 600px;height: 65px;"  name ="cancel_note" id="cancel_note" class="form-control readbaledata NotBeEditable"></textarea> </td>
			</tr>
		</table>
				
</div>
</div>
</div>
<input type="hidden" name="id" id="id" value="0">
</div>
</body>
</html>