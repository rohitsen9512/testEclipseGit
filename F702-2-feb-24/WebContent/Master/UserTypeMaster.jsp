<!--designationMaster.jsp-->


<script type="text/javascript">

$(function() {
	DisplayData('M_User_Type','displayUsertype','createUsertype');
	$('button[name="update"]').addClass('hideButton');
});


/* $('#nm_designId').keyup( function(){
	CheckValWhichAllReadyExit('M_Designation' , 'Designation name all ready exit.' , 'nm_design');
	});
	
$('#cd_designId').keyup( function(){
	CheckValWhichAllReadyExit('M_Designation' , 'Designation code all ready exit.' , 'cd_design');
	}); */
	function ValidateDesignation(action){
		debugger;
			var name=$("#nm_designId").val().replace(/\s+/g, " ").trim();
			$("#nm_designId").val(name);
			 if(name=="")
				{
						document.getElementById("hideshowlabel2").style.display = "block";
					    document.getElementById("hideshowlabel3").style.display = "none";
						$("#nm_designId").val('');
						$("#nm_designId").focus();
				}
			 else if(name.length < 2 || name.length > 70) {
				    document.getElementById("hideshowlabel2").style.display = "none";
				    document.getElementById("hideshowlabel3").style.display = "block";
					$("#nm_designId").focus();
				  }	 
			 else if(action = 'Save')
				 {
				 ControlDiv('Save','displayUsertype','createUsertype','submitUsertype','M_User_Type', 'Usertype name already exist.' , 'nm_usertype','Usertype has created successfully.');
				 }
			 else if(action = 'Update'){
				 ControlDiv('Update','displayUsertype','createUsertype','submitUsertype','M_User_Type', 'Usertype name already exist.' , 'nm_usertype','Usertype has updated successfully.');
		 
			 }

		}	
</script>

       <!-- Content Wrapper. Contains page content -->
  <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!-- User Type  -->
						<button type="button" name="create btn"
							 class="btn btn-primary" onclick="ControlDiv('Create','displayUsertype','createUsertype','submitUsertype','M_User_Type')">Create User Type</button>
					
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Master</a></li>
						<li class="breadcrumb-item">User Type</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

   
      <div class="card">
        
                   
                   <div id="displayUsertype">
                   <div class="card-body">
				<table id="UsertypeForDisplay"
					class="table table-bordered table-hover UsertypeForDisplay">
					<thead>
                   
					
						<tr class="new">
							<td><strong>User Name</strong></td>
							<td><strong>Designation Code</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
			</div>
		
	</div></div>

<section class="content">
		
<div id="createUsertype" class="card-small" style="display:none;">
<div class="card-header new">
					<h3 class="card-title1">User Type Details</h3>
				</div>
				<div class="card-body">
	<form action="" name="submitUsertype" id="submitUsertype">	
		<table class="table table-bordered ">
			<tr>
				<td><strong>User Type Name<font color="red">*</font></strong></td>
				<td><input id="nm_designId" class="form-control" type="text" name="nm_usertype" data-valid="mand">
				<div id="hideshowlabel2" style="display:none;"> <label for="password" style="color: red;">Designation name is required.</label>
                       </div>	
                        <div id="hideshowlabel3" style="display:none;"> <label for="nm_designId" style="color: red;font-weight: normal;">User Type name must be between 2 and 70 characters.</label>
                </div>   
				</td>
			</tr>
			<tr>
				<!-- <td><strong>Designation Code <font color="red">*</font></strong></td>
				<td><input id="cd_designId"  class="form-control" type="text" name="cd_design" data-valid="mand"></td> -->
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" id="id" value="0">
			</tr>
			<tr>
				<td colspan="2"><center>
					
						<button name="save" type="button"   class="btn btn-primary" onclick="ValidateDesignation('Save');">Save</button>
						<button name="update" type="button"    class="btn btn-primary" onclick="ValidateDesignation('Update');">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayUsertype','createUsertype','submitUsertype','M_User_Type')">Back</button>
					<button name="delete" type="button"  class="btn btn-primary" onclick="DeleteFun('M_User_Type','displayUsertype','createUsertype',document.getElementById('id').value,'Usertype has deleted successfully.')">Delete</button>
					
				</center></td>
			</tr>
		</table>
	</form>
</div>
 <!-- /.card-body -->
      </div>
      </div>
      </section>
      <!-- /.card -->
    
    <!-- /.content -->

<!-- Page specific script -->

  
  

</body>
</html>
