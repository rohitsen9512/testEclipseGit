<div style="padding:10px;">
	<!-- <a href="#">Master > </a><a href="#"> Employee User Master</a> -->
</div>
<script type="text/javascript">

$(function() {
	
	$( ".Datepicker" ).datepicker({
		yearRange: '1985:2025',
	    changeMonth: true,
	    changeYear: true,
	    dateFormat: "yy/mm/dd",
	    autoSize: true,
	    altFormat: "yy/mm/dd",
	      
	});
	
	$('button[name="update"]').addClass('hideButton');
	DisplayDropDownData('M_Loc','locDataForEmpUser',function (status){
		if(status)
		{
			DisplayDropDownData('M_Subloc','slocDataForEmpUser',function (status){
				if(status){
					DisplayDropDownData('M_Floor','floorDataForEmpUser',function (status){
						if(status){
							DropDownDataDisplay('M_Designation','dgnForEmp',function (status){
								if(status)
								{
									DisplayDropDownData('M_Dept','depDataForEmpUser',function (status){
										if(status)
										{
											DisplayDropDownData('M_Emp_User','repoManagerDataForEmpUser',function (status){
												if(status)
												{
													/* DisplayDropDownData('M_Emp_User','deptManagerDataForEmpUser',function (status){
														if(status)
														{
															DisplayData('M_Emp_User','displayEmployee','createEmployee');
														}}); */	
													DisplayData('M_Emp_User','displayEmployee','createEmployee');
												}});	
										}});
								}});
						}});
				}});
		}});	
	});

/* $('#cd_employeeId').keyup( function(){
	CheckValWhichAllReadyExit('M_Emp_User' , 'Employee code already exist.' , 'cd_emp');
	}); */
</script>
	<div id="displayEmployee">
		
		<table width="100%" height="100%">
			<tr>
				<td>
				<input type="text" name="word" value="" placeholder="Search......" onkeyup="DisplayData('M_Emp_User','displayEmployee','createEmployee');">
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayEmployee','createEmployee','submitEmployee','M_Emp_User')">Create Employee</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered empUserForDisplay">
						<tr class="new">
							<td><strong>Employee Name</strong></td>
							<td><strong>Employee Code</strong></td>
							<td><strong>Official Mail ID</strong></td>
							<td><strong>Designation</strong></td>
							<td><strong>Status</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>
	
	<div id="createEmployee" style="display:none;">
	<form name="submitEmployee" id="submitEmployee">
		<table class="table table-bordered" id="employeeDetails">
			<tr>
				<td colspan="4" style="background-color: rgb(255, 136, 51);"><p style="font-size: 24px;background-color: rgb(255, 136, 51);color: white;margin-left: 350px;">Employee Details</p></td>
			</tr>
			<tr>
				<td><b>Employee Name<font color="red">*</font></b></td>
				<td><input type="text" name="nm_emp" class="common-validation" data-valid="mand" autofocus=""></td>
				<td><b>Employee Code<font color="red">*</font></b></td>
				<td><input id="cd_employeeId" type="text" name="cd_emp"  class="common-validation" data-valid="mand"></td>
			</tr>
			<tr>
				<td><b>Official Mail ID <font color="red">*</font></b></td>
				<td><input type="text" name="id_emp" class="common-validation" data-valid="mand"></td>
				<td><b>Contact No :</b></td>
				<td><input type="text" name="cont_no" class="common-validation"></td>
			</tr>
			<tr>
			<td><b>Reporting/Operational Manager <font color="red">*</font></b> </td>
				<td>
					<select  name="repo_mngr" id="repoManagerDataForEmpUser" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
<!-- 			<td><b>Department Manager <font color="red">*</font> :</b> </td>
				<td>
					<select  name="dept_mngr" id="deptManagerDataForEmpUser" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
 -->
 				<td><b>Designation<font color="red">*</font></b></td>
				<td>
					<select name="id_design" id="dgnForEmp" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
 				
			</tr>
			<tr>
				<td><b>Department<font color="red">*</font></b></td>
				<td>
					<select class="common-validation" name="id_dept" id="depDataForEmpUser" data-valid="mand" onChange="SubDropDownDataDisplay(this,'costCenterForEmp','M_Cost_Center')">
						<option value="">Select</option>
						
					</select>
				</td>
<!-- 				<td>
				<td><b>Cost Center<font color="red"> * </font></b></td>
				<td>
					<select name="id_div" id="costCenterForEmp" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						
					</select>
  				</td>
 -->
 				<td><b>Cost Center<font color="red">*</font></b></td>
				<td>
					<select name="id_cc" id="costCenterForEmp" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
 				
			</tr>
			<tr>
				

<!-- 				<td><b>Job Code</b></td>
				<td><input type="text" name="code_job" class="common-validation"></td>
 -->				
				
				
			</tr>
			<tr>
			<td><b>Location<font color="red">*</font></b></td>
				<td>
					<select class="common-validation" name="id_loc" id="locDataForEmpUser" data-valid="mand" onChange="DisplaySubDropDownData(this,'slocDataForEmpUser','M_Subloc')">
						<option value="">Select</option>
						
					</select>
				</td>
			<td><b>Sub Location<font color="red">*</font></b></td>
				<td>
					<select name="id_sloc" id="slocDataForEmpUser" class="common-validation" data-valid="mand"  onChange="DisplaySubDropDownData(this,'floorDataForEmpUser','M_Floor')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
 			<td><b>Employee Work desk<font color="red">*</font></b></td>
				<td><input id="id_wdesk" type="text" name="id_wdesk"  class="common-validation" data-valid="mand">
				</td>
 			
				<td><b>Status<font color="red">*</font></b></td>
				<td>
					<select name="status_emp" class="common-validation" data-valid="mand">
						<option value="">Select</option>

						<option value="Working">Working</option>
						<option value="NonWorking">Non Working</option>
					</select>
				</td>
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="0">
				
			</tr>
			<tr>
				<td><b>Employee Type <font color="red">*</font></b> </td>
				<td>
					<select  name="emp_type" class="common-validation"  data-valid="mand">
						<option value="">Select</option>
						<option value="Employee">Employee</option>
						<option value="Contract">Contract</option>
					</select>
				</td>
				<td><b>Port <font color="red">*</font></b> </td>
				<td>
					<input type="text" name="id_dport" class="common-validation">
				</td>
			</tr>
			<tr>
				<td><b>Employee Start Date <font color="red"></font></b> </td>
				<td>
					<input type="text" name="dt_start" value="" class="common-validation readbaledata Datepicker" readonly>
				</td>
				<td><b>Employee Exit Date <font color="red"></font></b> </td>
				<td>
					<input type="text" name="dt_end" value="" class="common-validation readbaledata Datepicker" readonly>
				</td>
			</tr>
			<tr>
				<td><b>Reason for Exit <font color="red"></font></b> </td>
				<td>
					<textarea rows="3" cols="10" name ="reason_to_exit" id="reason_to_exit" style="width: 210px" class="common-validation"> </textarea>
				</td>
				<td colspan="2"></td>
				
			</tr>
			<tr>
				<td colspan="4">
					
						<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayEmployee','createEmployee','submitEmployee','M_Emp_User','Employee name already exist.,,Employee code already exist.' , 'nm_emp,,cd_emp')">Save</button>
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayEmployee','createEmployee','submitEmployee','M_Emp_User','' , '')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayEmployee','createEmployee','submitEmployee','M_Emp_User')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>
	