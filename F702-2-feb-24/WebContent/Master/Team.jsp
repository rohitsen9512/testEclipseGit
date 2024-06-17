<div style="padding:10px;">
	<!--  <a href="#">Organization Master > </a><a href="#"> Employee Detail</a>-->
</div>

<script type="text/javascript">

$(function() {
	
	$('button[name="update"]').addClass('hideButton');
	  
							
									DropDownDataDisplay('M_Dept','deptForEmp',function (status){
									if(status)
									{
										
												DisplayData('M_Team','displayTeam','createTeam');
										}});
									 
		                       });
		
jQuery("input#file4ID").change(function () {
	
	 var formData = new FormData();	 
		formData.append('file', $('#file4ID').get(0).files[0]);
		
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
			    	
			    		$('input[name="emp_image"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
			
	//$( "#submitForUploadData" ).trigger( "click" );
}); 



</script>


	<div id="displayTeam">
		<table width="100%" height="100%">
			<tr>
				<td>
				<div id="displayDesignation">
   <input type="text" name="searchWord" value="" placeholder="Search......" onkeyup="DisplayData('Team','displayTeam','createTeam');">
   </div>
				
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayTeam','createTeam','submitTeam','Team')">Create Team</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered teamForDisplay">
						<tr class="new">
							<td><strong>Team Initials</strong></td>
							<td><strong>Team Name</strong></td>
							
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="createTeam" style="display:none;">
	<form name="submitTeam" id="submitTeam">
		<table  id="employeeDetails"  align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 230px;">Team Details</p></td>
			</tr>
		
			
			<tr>
				
				
				
				<td><b>BU<font color="red">*</font></b></td>
				<td>
				<select name="id_dept" id="deptForEmp" class="common-validation" data-valid="mand" onChange="DisplaySubDropDownData(this,'nmfunction','M_Cost_Center')">
				<option value="">Select</option>
						
				</select>
				</td>
				</tr>
				<tr>
				
				<td><b>Function<font color="red">*</font></b> </td>
				<td>
				<select name="id_cc" id="nmfunction" class="common-validation" data-valid="mand" onChange="DisplaySubDropDownData(this,'subfunction','M_S_Function')">
				<option value="">Select</option>
						
				</select>
				
			</tr>
			<tr>
			
				<td><strong> Sub Function<font color="red">*</font></strong></td>
				<td>
					<select id="subfunction" class="common-validation" name="id_s_function" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subbu','M_BU')">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
				<tr>
			<td><strong> Department<font color="red">*</font></strong></td>
				<td>
					<select id="subbu" class="common-validation" name="id_bu" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			
			</tr>
			<tr>
			<td><strong> Team<font color="red">*</font></strong></td>
				
			<td><input id="nm_team" class="common-validation" type="text" name="nm_team" data-valid="mand"></td>
				
			
			</tr>
		
			
		
			<tr>
		
			<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="0">
					  <input type="hidden" name="emp_image" value="" class="common-validation">
			<!-- <tr>
				
				
				<td colspan="2"></td>
			</tr> -->
			
			<tr>
				<td colspan="2">
					
						<button name="save" type="button" style="margin-left:230px;" class="btn btn-primary" onclick="ControlDiv('Save','displayTeam','createTeam','submitTeam','M_Team','Team name already exist.' , 'nm_team')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"  class="btn btn-primary" onclick="ControlDiv('Update','displayTeam','createTeam','submitTeam','M_Team','' , '')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayTeam','createTeam','submitTeam','M_Team')">Back</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>
	
