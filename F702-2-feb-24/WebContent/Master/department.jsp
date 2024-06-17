<!--department.jsp-->

<script type="text/javascript">

$(function() {
	DisplayData('M_Dept','displayDepartment','createDepartment');
	$('button[name="update"]').addClass('hideButton');
});

</script>

<!-- Content Wrapper. Contains page content -->
  
    <!-- Content Header (Page header) -->
    <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1><!--  Department-->  <button type="button" name="create btn" class="btn btn-primary" onclick="ControlDiv('Create','displayDepartment','createDepartment','submitDepartment','M_Dept')">Create Department</button>
					</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Department</a></li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    
      <div class="card">
       <div id="displayDepartment">
        <div class="card-body">
     
            
     
		
		
			<table id="deptForDisplay"
					class="table table-bordered table-hover deptForDisplay">
					<thead>
					<tr class="new">
							<td><strong>Department Name</strong></td>
							<td><strong>Department Code</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
						</thead>
					</table>
				
	
		
	</div>
	</div>
	</div>

<section class="content">
		
<div id="createDepartment" style="display:none;" class="card-small">
<div class="card-header new">
					<h3 class="card-title1">Department Details</h3>
				</div>
				<div class="card-body">
	<form action="" name="submitDepartment" id="submitDepartment">	
		<table class="table table-bordered ">
			
			<tr>
				<td><strong>Department Name<font color="red">*</font></strong></td>
				<td><input id="nm_departmentId" class="form-control" type="text" name="nm_dept" data-valid="mand"></td>
			</tr>
			<tr >
				 <!-- <td><strong>Department Code <font color="red">*</font> :</strong></td>
				<td><input id="cd_departmentId" class="form-control" type="text" name="cd_dept"  data-valid="mand"></td> -->
			 	<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" id="id" value="0">
			</tr>
			<tr>
				<td colspan="2"><center>
					
						<button name="save" type="button"  class="btn btn-primary" onclick="ControlDiv('Save','displayDepartment','createDepartment','submitDepartment','M_Dept','Department name already exist.,,Department code already exist.' , ',,')">Save</button>
						<button name="update" type="button"   class="btn btn-primary" onclick="ControlDiv('Update','displayDepartment','createDepartment','submitDepartment','M_Dept','Department name already exist.' , 'nm_dept')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayDepartment','createDepartment','submitDepartment','M_Dept')">Back</button>
					<button name="delete" type="button"  class="btn btn-primary" onclick="DeleteFun('M_Dept','displayDepartment','createDepartment',document.getElementById('id').value)">Delete</button>
					
				</center></td>
			</tr>
			
		</table>
	</form>
</div>
 <!-- /.card-body -->
      </div>
      <!-- /.card -->
      </div>
      </section>
 
<!-- Page specific script -->
<script>
  
</script>
  
  
  

</body>
</html>
