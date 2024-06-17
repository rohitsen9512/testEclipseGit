<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>

<script type="text/javascript">

function myFunction() {
	/// how to get the value
	
	var x = $("#nm_emp").val();
	
	
    var text = x + "@novonordisk.com";
    
    $("#id_emp").val(text);
}

</script>
<script type="text/javascript">

$(function() {
	
	$('button[name="update"]').addClass('hideButton');
	DropDownDataDisplay('M_Loc','locDataForEmpUser',function (status){
		if(status)
		{
			DisplayDropDownData('M_Designation','dgnForEmp',function (status){
				if(status)
				{
					
					DropDownDataDisplay('M_Division','devisionForEmp',function (status){
						if(status)
						{
							
							/* DropDownDataDisplay('M_StorageDetail','strgDataForEmpUser',function (status){
								if(status)
								{ */
									DropDownDataDisplay('M_Cost_Center','nmfunction',function (status){
										if(status)
										{
									/* DropDownDataDisplay('M_Dept','deptForEmp',function (status){
									if(status)
									{ */
										DisplayDropDownData('M_Emp_User','repoManagerDataForEmpUser',function (status){
											if(status)
											{
												DisplayDropDownData('M_User_Type','usertypeForEmpUser',function (status){
													if(status)
													{
														
														DisplayData('M_Emp_User','displayEmployee','createEmployee');
													}});
												
											}});	 
									//}});
									 }}); 
										}});
							
						
				}});
		}}); 
		});
	
	
	
		function Emailsave()
		{
			t=checkEmail();
			if (t){
				ControlDiv('Save','displayEmployee','createEmployee','submitEmployee','M_Emp_User','Employee Mail ID already exist.,,Employee Code already exist.' , 'id_emp,,cd_emp','Employee has created successfully.');
		}
		}
		function checkEmail()
		{	debugger;
			t=true;
			/*var str=$("#userEmail").val();*/
			var str=$("#id_emp").val();
		    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		    if(!re.test(str)){
		    	 t=false;
		   
		    bootbox.alert("Please enter a valid email address");
		    /*$('input[name="id_email"]').val("");*/
		    }
		    return t
		}
	
		
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


document.getElementById("file4ID").onchange = function () {
    var reader = new FileReader();

    reader.onload = function (e) {
        // get loaded data and render thumbnail.
        document.getElementById("image").src = e.target.result;
    };

    // read the image file as a data URL.
    reader.readAsDataURL(this.files[0]);
};
</script>
 <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
					<!--  	Employee -->
						<button type="button" name="create btn"
							  class="btn btn-primary" onclick="ControlDiv('Create','displayEmployee','createEmployee','submitEmployee','M_Emp_User')">Create Employee</button>
					
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Master</a></li>
						<li class="breadcrumb-item">Employee</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

    <!-- Main content -->
   <div class="card">
     

	<div id="displayEmployee">
	<div class="card-body">
		<table id="empUserForDisplay"
					class="table table-bordered empUserForDisplay">
						<tr class="new">
							<td><strong>Employee Initials</strong></td>
							<td><strong>Employee Name</strong></td>
							<td><strong>Official Mail ID</strong></td>
							<td><strong>Designation</strong></td>
							<td><strong>Status</strong></td>
							<td><strong>Modify/ Delete</strong></td>
						</tr>
					</table>
				</div>
	</div>
	</div>
	
	
	
	<section class="content">
		
	<div id="createEmployee" class="card" style="display:none;">
	<div class="card-header new">
					<h3 class="card-title1">Employee Details</h3>
				</div>
				<div class="card-body">
	<form name="submitEmployee" id="submitEmployee">
		<table class="table table-bordered" id="employeeDetails">
			
			<tr>
				<td><b>Employee Name<font color="red">*</font></b></td>
				<td><input type="text" name="nm_emp" id="nm_emp"  class="form-control" data-valid="mand"></td>
				<td><b>Employee Code<font color="red">*</font></b></td>
				<td><input id="cd_employeeId" type="text" name="cd_emp"  class="form-control" data-valid="mand"></td>
			</tr>
			<tr>
				<td><b>Official Mail ID<font color="red">*</font></b></td>
				<td><input type="text" name="id_emp" id="id_emp" class="form-control" data-valid="mand"  onblur="checkEmail(this.value)"> </td>
				<td><b>Contact No</b></td>
				<td><input type="text" name="cont_no" class="form-control" data-valid="num" pattern="\d*" maxlength="10"></td>
			</tr>
			<tr>
				<td><b>Designation<font color="red">*</font></b> </td>
				<td>
				<select name="id_design" id="dgnForEmp" class="form-control" data-valid="mand" >
				<option value="">Select</option>
						
				</select>
				</td>
				<td><b>Reporting Manager<font color="red">*</font></b> </td>
				<td>
					<select class="form-control" name="repo_mngr" id="repoManagerDataForEmpUser" data-valid="mand">
						<option value="">Select</option>
						
				</select>
				</td>	
			</tr>
			<tr>
				
				
				
			<!-- 	<td><b>Department<font color="red">*</font></b></td>
				<td>
				<select name="id_dept" id="deptForEmp" class="form-control" data-valid="mand">
				<option value="">Select</option>
						
				</select>
				</td> -->
				<td ><b>Entity<font color="red">*</font></b></td>
				<td >
					<select id="locDataForEmpUser" name="id_loc" class="form-control " data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'slocDataForEmpUser','M_Subloc')">
						<option value="">Select</option>
						
					</select>
				</td> 
				
				<td ><b>City<font color="red">*</font></b></td>
				<td >
					<!-- <select id="slocDataForEmpUser" name="id_sloc" class="form-control " data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'buildingDataForEmpUser','M_Building')"> -->
					<select id="slocDataForEmpUser" name="id_sloc" class="form-control " data-valid="mand"  style="width:140" >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<!-- <tr>
			
				<td><strong>Function <font color="red">*</font></strong></td>
				<td>
					<select id="subfunction" class="form-control" name="id_s_function" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subbu','M_BU')">
						<option value="">Select</option>
						
					</select>
				</td>
			<td><strong>Department <font color="red">*</font></strong></td>
				<td>
					<select id="subbu" class="form-control" name="id_bu" data-valid="mand" onChange="SubDropDownDataDisplay(this,'id_team','M_Team')">
						<option value="">Select</option>
						
					</select>
				</td>
				<td><strong>Sub-Function <font color="red">*</font></strong></td>
				<td>
					<select id="subbu" class="form-control" name="id_bu" data-valid="mand" >
						<option value="">Select</option>
						
					</select>
				</td>
			
			</tr> -->
		<!-- 	<tr>
			
				<td><strong> Team <font color="red">*</font></strong></td>
				<td>
					<select id="id_team" class="form-control" name="id_team" data-valid="mand" >
						<option value="">Select</option>
						
					</select>
				</td>
			     <td></td>
				<td></td>
			
			</tr> -->
			<tr>
					
					
	           <!--   <td ><b>Building<font color="red">*</font></b></td>
				<td >
					<select id="buildingDataForEmpUser" name="id_building" class="form-control " data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'floorDataForEmpUser','M_Floor')">
						<option value="">Select</option>
						
					</select>
				</td> -->
			</tr>
			<tr>
				
				<!-- <td ><b>Floor<font color="red">*</font></b></td>
				<td >
					<select id="floorDataForEmpUser" name="id_flr" class="form-control " data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td> -->
		        <td><b>Employee Type <font color="red">*</font></b> </td>
				<td>
					<select  name="emp_type" class="form-control"  data-valid="mand">
						<option value="">Select</option>
						<option value="Employee">Employee</option>
						<option value="Contract">Contract</option>
					</select>
				</td>
				
				
				<td><b>Status<font color="red">*</font></b></td>
				<td>
					<select name="status_emp" class="form-control" data-valid="mand">
						<option value="">Select</option>

						<option value="Working">Working</option>
						<option value="NonWorking">Resigned</option>
					</select>
				</td>
				</tr>
		
				
				<!-- <td><b>Cost Center/Project <font color="red">*</font></b> </td>
				<td>
				<select name="id_cc" id="nmfunction" class="form-control" data-valid="mand" >
				<option value="">Select</option>
						
				</select> 
				</td>
				 -->
				
			<tr>
			 <td><b>Upload Profile Image</b></td>
				<td><img id="image" src="image/usericon.png" style="min-width: 100px;
    min-height: 100px;
    max-width: 100px;box-shadow: 0 4px 8px 10px rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    max-height: 100px;border: 2px solid #555;margin-right: 20px; border-radius: 10px;"/><input id="file4ID" type="file" name="file4" class="form-control" value="" ></td>
			
			<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" id="id" value="0">
					  <input type="hidden" name="emp_image" value="" class="form-control">
			<!-- <tr>
				
				
				<td colspan="2"></td>
			</tr> -->
			<!-- <td><b>User Type :</b> </td>
				<td>
					<select class="form-control" name="id_usertype" id="usertypeForEmpUser" >
						<option value="">Select</option>
						
				</select>
				</td> -->
			<tr>
			
				<td colspan="4"><center>
					
						<button name="save" type="button"    class="btn btn-primary" onclick="Emailsave();">Save</button>
						<button name="update" type="button"    class="btn btn-primary" onclick="ControlDiv('Update','displayEmployee','createEmployee','submitEmployee','M_Emp_User','Employee Mail ID already exist.,,Employee Code already exist.' , 'id_emp,,cd_emp','Employee has updated successfully.')">Update</button>
						<!-- <button name="update" type="button"    class="btn btn-primary" onclick="ControlDiv('Update','displayEmployee','createEmployee','submitEmployee','M_Emp_User','Employee Mail ID already exist.,,Employee Code already exist.' , 'id_emp,,cd_emp')">Update</button> -->
						
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayEmployee','createEmployee','submitEmployee','M_Emp_User')">Back</button>
					<button name="delete" type="button"  class="btn btn-primary" onclick="DeleteFun('M_Emp_User','displayEmployee','createEmployee',document.getElementById('id').value,'Employee has deleted successfully.')">Delete</button>
					
				</center></td>
			</tr>
			
		</table>
	</form>
	</div>
	 <!-- /.card-body -->
      </div>
      <!-- /.card -->
    </section>
    <!-- /.content -->
 

  
  
  

</body>
</html>
	
